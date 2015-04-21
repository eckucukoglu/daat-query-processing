/**
 *  Document-at-a-time (DAAT) query processing with WAND optimization
 *  @author Emre Can Kucukoglu
 *  eckucukoglu@gmail.com
 *  https://github.com/eckucukoglu/daat-query-processing
 * 
 *  Set all file constants in Parser class.
 */
public class Analyzer {

	/**
	 * Main function of project.
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		int k = 10;
		Dictionary dictionary = new Dictionary(Parser.getDictionaryTerms());
		Query[] queries = Parser.getQueries();
		double[] documentLengths = Parser.getDocLengths(); 
		
		//QueryProcessor qp = new QueryProcessor(dictionary, queries, documentLengths);
		QueryProcessor qpw = new QueryProcessor("DAAT+WAND", dictionary, queries, documentLengths, k);
		
		Heap<Document>[] resultants = qpw.iterate(0);
//		Heap<Document> winningDocuments = qpw.iterate();
//		for (int i = 0; i < k; ++i) {
//			Document doc = winningDocuments.pop();
//			System.out.println("Top " + (i+1) + ": " + doc.getDocid() + " " + doc.getScore());
//		}
		
		return;
	}

}


