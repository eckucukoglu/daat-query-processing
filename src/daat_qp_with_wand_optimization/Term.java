package daat_qp_with_wand_optimization;

public class Term {

	private String token;
	private int df;
	private float idf;
	
	/**
	 * Constructor for term.
	 * 
	 * @param token	token of the term.
	 * @param df	document frequency.
	 * @param idf	inverse document frequency
	 */
	public Term(String token, int df, float idf) {
		this.token = token;
		this.df = df;
		this.idf = idf;
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
	 * Return document frequency of a term
	 * 
	 * @return the df
	 */
	public int getDf() {
		return df;
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
