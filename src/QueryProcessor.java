import java.util.ArrayList;

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
 * > "DAAT+WAND":	Document-at-a-time with WAND optimization in heap selection 
 * 	elemination.
 * > "DAAT"		:	Default document-at-a-time query processing.
 * 
 * 
 * @author Emre Can Kucukoglu
 *
 */
public class QueryProcessor {

	private Dictionary dictionary;
	private Query[] queries;
	private float[] vectorLengths;
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
	public QueryProcessor (Dictionary dictionary, Query[] queries, float[] vectorLengths) {
		this.dictionary = dictionary;
		this.queries = queries;
		this.vectorLengths = vectorLengths;
		this.bracket = 0;
	}
	
	/**
	 * Constructor for QueryProcessor with mode selection.
	 * 
	 * @param mode
	 * @param dictionary
	 * @param queries
	 * @param vectorLengths
	 */
	public QueryProcessor (String mode, Dictionary dictionary, Query[] queries, float[] vectorLengths) {
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
		
		
		Heap<Document> winners = new Heap<Document>(new Document()); 
		
			
		
		
			
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
	@SuppressWarnings("unchecked")
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
	public float[] getVectorLengths() {
		return vectorLengths;
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
}
