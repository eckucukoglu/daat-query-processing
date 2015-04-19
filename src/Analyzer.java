import java.util.Arrays;
import java.util.Comparator;

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
	 * @param Comparator 
	 */
	public static void main(String[] args) {
		
		Dictionary dictionary = new Dictionary(Parser.getDictionaryTerms());
		Query[] queries = Parser.getQueries();
		double[] documentLengths = Parser.getDocLengths(); 
		
		QueryProcessor qp = new QueryProcessor(dictionary, queries, documentLengths);
		// QueryProcessor qpw = new QueryProcessor("DAAT+WAND", dictionary, queries, documentLengths);
		
		//Heap<Document>[] resultants = qp.iterate(0);
		Heap<Document> winningDocuments = qp.iterate();
		System.out.println(1);
	}

}


