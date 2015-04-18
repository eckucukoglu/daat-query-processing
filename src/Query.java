/**
 *  This class represents the Query.
 *  Each query has term or terms.
 *  
 *  Seperator to split terms from query, are defined statically.
 */
public class Query {
	
	public static final char WORD_SEPERATOR = ' ';
	
	private Term[] terms;
	
	/**
	 * Constructor for query.
	 * 
	 * @param query	raw query string.
	 */
	public Query(String query) {
		
		
	}
	
	
	
	
	
	/**
	 * @return the terms
	 */
	public Term[] getTerms() {
		return terms;
	}

	
	
	
	
	
}
