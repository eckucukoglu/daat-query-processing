import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class processes the queries.
 * Each query processor have to defined with
 * Dictionary, Query List, Document Vector Length List
 * Dictonary should be term ascending order, Document
 * Vector List should be document-id ascending order.
 * 
 * Furthermore, Each query processor can be defined with 
 * a mode selection. Default one is Document-at-a-time "DAAT",
 * for more selection:
 * 
 * > "DAAT+WAND": Document-at-a-time with WAND optimization in heap selection elemination.
 * > "DAAT" : Default document-at-a-time query processing.
 * 
 * 
 * @author Emre Can Kucukoglu
 *
 */
public class QueryProcessor {

	private Dictionary dictionary;
	private Query[] queries;
	private double[] vectorLengths;
	private int k = 2; /** max heap space, 2 as default **/
	private String mode = "DAAT"; /** Default selection **/
	private int bracket; /** Iteration point for a query list **/
	
	/**
	 * Constructor for QueryProcessor.
	 * Default mode used.
	 * 
	 * @param dictionary
	 * @param queries
	 * @param vectorLengths
	 */
	public QueryProcessor (Dictionary dictionary, Query[] queries, double[] vectorLengths) {
		this.dictionary = dictionary;
		this.queries = queries;
		this.vectorLengths = vectorLengths;
		this.bracket = 0;
	}
	
	/**
	 * Constructor for QueryProcessor with mode selection
	 * and max heap size option.
	 * 
	 * @param mode
	 * @param dictionary
	 * @param queries
	 * @param vectorLengths
	 */
	public QueryProcessor (String mode, Dictionary dictionary, Query[] queries, double[] vectorLengths, int k) {
		this.dictionary = dictionary;
		this.queries = queries;
		this.vectorLengths = vectorLengths;
		this.mode = mode;
		this.bracket = 0;
		this.k = k;
	}
	
	/**
	 * Constructor for QueryProcessor with mode selection.
	 * Default max heap size assigned.
	 * 
	 * @param mode
	 * @param dictionary
	 * @param queries
	 * @param vectorLengths
	 */
	public QueryProcessor (String mode, Dictionary dictionary, Query[] queries, double[] vectorLengths) {
		this.dictionary = dictionary;
		this.queries = queries;
		this.vectorLengths = vectorLengths;
		this.mode = mode;
		this.bracket = 0;
	}
	
	/**
	 * Runs the processor one times and return resultant 
	 * documents in a heap structure.
	 * 
	 * @return
	 */
	public Heap<Document> iterate () {

		if (this.bracket >= this.queries.length)
			return null;
		
		ArrayList<Term> terms = this.findMatchingTerms(this.queries[this.bracket]);
		
		if (terms.size() == 0)
			return null;
		
		Heap<Document> winners = null;
		
		if (this.mode.equals(Parser.DAAT)) {
			winners = this.daatProcessing(terms);
		} else if (this.mode.equals(Parser.DAAT_WITH_WAND)) {
			winners = this.daatProcessing("WAND", terms);
		}
		
		this.bracket = this.bracket + 1;
		return winners;
	}
	
	/**
	 * If iterate function is called with integer parameter;
	 * 
	 * 0	:	Iterate from last query to the end of query list
	 * n>0	:	Iterate from last query to the n next queries.
	 * n<0	:	Erronous result.	
	 * 
	 * @param count
	 * @return Winning documents in a heap structure
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Heap<Document>[] iterate (int count) {
		Heap[] winningDocuments = null;
		int times = 0;
		
		if (count < 0)
			return null;
		else if (count == 0)
			times = this.queries.length - this.bracket;
		else if (count > 0)
			times = count;
		
		winningDocuments = new Heap[times];
		
		for (int i = 0; i < times; ++i)
			winningDocuments[i] = this.iterate();
		
		return winningDocuments;
	}
	
	/**
	 * Sequential searchs term list to find given word
	 * in the tokens.
	 * On-the-fly compute term's postings file index.
	 * 
	 * @param words
	 * @return matching term
	 */
	private Term findMatchingTerm(String word) {
		Term term = null;
		int postingsIndex = 0;
		
		/** Binary search can be used to get matching term's index **/
		/** int index = Arrays.binarySearch(this.dictionary.getTermlist(), new Term(word)); **/
		
		/** Sequential search is obligatory to compute postings file index **/
		for (int i = 0; i < this.dictionary.getTermlist().length; ++i) {
			if (this.dictionary.getTermlist()[i].getToken().equals(word)) {
				term = this.dictionary.getTermlist()[i];
				term.setPostingsIndex(postingsIndex);
				return term;
			} else
				postingsIndex = postingsIndex + this.dictionary.getTermlist()[i].getLength();
		}
		
		return null;
	}
	
	/**
	 * Sequential searchs term list to find given query's 
	 * words in the tokens.
	 * On-the-fly compute term's postings file index.
	 * 
	 * @param words
	 * @return matching terms
	 */
	private ArrayList<Term> findMatchingTerms(Query query) {
		ArrayList<Term> terms = new ArrayList<Term>();
		String[] words = query.getWords();
		
		for (int i = 0; i < words.length; ++i) {
			Term queryTerm = this.findMatchingTerm(words[i]);
			
			/** No matching term for a given word **/
			if (queryTerm == null) 
				continue;
			
			terms.add(queryTerm);
		}
		
		return terms;
	}
	
	/**
	 * Document-at-a-time processing with given terms.
	 * 
	 * @param terms
	 * @return
	 */
	private Heap<Document> daatProcessing (ArrayList<Term> terms) {
		int docId = 0;
		double documentScore = 0.0;
		int evalPostingNo = 0;
		int[] docidIndexes = new int[terms.size()];
		Posting[][] postings = new Posting[terms.size()][];
		Heap<Document> minHeap = new Heap<Document>(new Document());
				
		for (int i = 0; i < terms.size(); ++i) {
			postings[i] = Parser.getPostings(terms.get(i).getPostingsIndex(), terms.get(i).getLength());
			docidIndexes[i] = 0;
		}
		
		docidIndexes = this.assignNextDocIds(postings, docidIndexes);
		
		do {
			docId = docidIndexes[findMin(docidIndexes)];
			documentScore = 0.0;
					
			for (int i = 0; i < terms.size(); ++i) {
				int index = Arrays.binarySearch(postings[i], new Posting(docId));
				
				/** Term's postings list does not have specified document-id **/
				if (index < 0) {
					continue;
				}
				
				documentScore = documentScore + (terms.get(i).getIdf() * postings[i][index].getTf());
				evalPostingNo = evalPostingNo + 1;
			}
			
			documentScore = this.normalizeScore(docId, documentScore);
			documentScore = documentScore * (-1);
			minHeap.insert(new Document(docId, documentScore));
			
			docidIndexes = this.assignNextDocIds(postings, docidIndexes);
		} while (this.checkDocIndexes(docidIndexes));
		
		this.queries[this.bracket].setEvalPostingNo(evalPostingNo);
		
		return minHeap;
	}

	/**
	 * Document-at-a-time processing with given terms and
	 * optional optimization selection.
	 * 
	 * @param optimization
	 * @param terms
	 * @return
	 */
	private Heap<Document> daatProcessing (String optimization, ArrayList<Term> terms) {
		
		
		return null;
		
	}
	
	/**
	 * Normalize document score.
	 * 
	 * @param docId
	 * @param documentScore
	 * @return normalized score
	 */
	private double normalizeScore(int docId, double documentScore) {
		return (documentScore/this.vectorLengths[docId-1]);
	}
	
	/**
	 * Returns the index array if next document-id 
	 * in the postings according to given document-id
	 * list's minimum doc-id.
	 * 
	 * @param postings
	 * @param docidIndexes
	 * @return array of next document ids
	 */
	private int[] assignNextDocIds (Posting[][] postings, int[] docidIndexes) {
		int[] retVal = new int[docidIndexes.length];
		retVal = docidIndexes;
		
		int docId = docidIndexes[findMin(docidIndexes)];
		
		for (int i = 0; i < postings.length; ++i) {
			if (docId == 0)
				retVal[i] = postings[i][0].getDocid();
			else {
				int index = Arrays.binarySearch(postings[i], new Posting(docId));
				if (index < 0)
					continue;
				
				if (index == (postings[i].length)-1) {
					retVal[i] = -1; 
				} else
					retVal[i] = postings[i][index+1].getDocid();
			}
			
		}
		
		
//		for (int i = 0; i < postings.length; ++i) {
//			if (docId == 0)
//				retVal[i] = postings[i][0].getDocid();
//			else { 
//				for (int j = 0; j < postings[i].length; ++j) {
//					if (postings[i][j].getDocid() > docId) {
//						break;
//					} else if (postings[i][j].getDocid() == docId) {
//						if (j == (postings[i].length)-1) {
//							retVal[i] = -1; 
//						} else
//							retVal[i] = postings[i][j+1].getDocid();
//						break;
//					}
//				}
//			}
//		}
		
		return retVal;
	}
	
	/**
	 * Document-id indexes are set -1 if all postings processed.
	 * This determines the continuity of process loop.
	 * 
	 * @param array
	 * @return false if all elements -1, true otherwise. 
	 */
	private boolean checkDocIndexes (int[] array) {
		for (int i = 0; i < array.length; ++i) {
			if (array[i] != -1)
				return true;
		}
		
		return false;
	}
	
	/**
	 * @return the dictionary
	 */
	public Dictionary getDictionary() {
		return dictionary;
	}

	/**
	 * @return the queries
	 */
	public Query[] getQueries() {
		return queries;
	}

	/**
	 * @return the vectorLengths
	 */
	public double[] getVectorLengths() {
		return vectorLengths;
	}

	/**
	 * @return max heap size
	 */
	public int getK() {
		return k;
	}
	
	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}
	
	/**
	 * @return iteration point for a query list
	 */
	public int getBracket() {
		return bracket;
	}
	
	/**
	 * Find min value in the array and return its index.
	 * Discard negative values.
	 * 
	 * @param array
	 * @return index of minimum element
	 */
	static int findMin (int[] array) {
		int index = 0;
		int min = array[index];
		
		for (int i = 0; i < array.length; ++i) {
			if (min > array[i] && array[i] > 0) {
				min = array[i];
				index = i;
			}
		}
		
		return index;
	}
}
