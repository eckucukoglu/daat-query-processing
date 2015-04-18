/**
 *  This class represents the Query.
 *  Each query has word or words.
 *  
 *  Seperator to split words from query, are defined statically.
 */
public class Query {
	
	private String[] words;
	
	/**
	 * Constructor for query.
	 * 
	 * @param query	raw query string.
	 */
	public Query(String[] words) {
		this.words = words;
		
	}
	
	
	
	
	
	/**
	 * @return the words
	 */
	public String[] getTerms() {
		return words;
	}

	
	
	
	
	
}
