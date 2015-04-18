/**
 *  This class represents Term.
 *  Each Term has token as a string value,
 *  how many postings has this term and
 *  precomputed idf value.
 *  
 */
public class Term {

	private String token;
	private int lengthOfPostingsList;
	private float idf;
	
	/**
	 * Constructor for term.
	 * 
	 * @param token	token of the term.
	 * @param lengthOfPostingsList	length of postings list.
	 * @param idf	inverse document frequency
	 */
	public Term(String token, int lengthOfPostingsList, float idf) {
		this.token = token;
		this.lengthOfPostingsList = lengthOfPostingsList;
		this.idf = idf;
	}
	
	/**
	 * Copy constructor for term.
	 * 
	 * @param term	term.
	 */
	public Term(Term term) {
		this.token = term.getToken();
		this.lengthOfPostingsList = term.getLength();
		this.idf = term.getIdf();
	}
	
	
	/**
	 * Return token of a term
	 * 
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Return the length of postings list
	 * 
	 * @return the lengthOfPostingsList
	 */
	public int getLength() {
		return lengthOfPostingsList;
	}

	
	/**
	 * Return precomputed idf value of a term
	 * 
	 * @return the idf
	 */
	public float getIdf() {
		return idf;
	}

}
