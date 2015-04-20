import java.util.Comparator;

/**
 *  This class represents Term.
 *  Each Term has token as a string value,
 *  how many postings has this term and
 *  precomputed idf value.
 *  
 */
public class Term implements Comparable<Term>, Comparator<Term> {

	private String token;
	private int lengthOfPostingsList;
	private double idf;
	/** 
	 * Term's starting position for a postings file. 
	 * It should be computed on-the-fly from the beginning
	 * of the dictionary. However if one times computed, since 
	 * it can not changed, remembering it is a good idea.
	 */
	private int postingsIndex; 
	
	/**
	 * Constructor for term.
	 * 
	 * @param token	token of the term.
	 */
	public Term(String token) {
		this.token = token;
		this.lengthOfPostingsList = 0;
		this.idf = 0;
		this.postingsIndex = -1;
	}
	
	/**
	 * Constructor for term.
	 * 
	 * @param token	token of the term.
	 * @param lengthOfPostingsList	length of postings list.
	 * @param idf	inverse document frequency
	 */
	public Term(String token, int lengthOfPostingsList, double idf) {
		this.token = token;
		this.lengthOfPostingsList = lengthOfPostingsList;
		this.idf = idf;
		this.postingsIndex = -1;
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
		this.postingsIndex = term.getPostingsIndex();
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
	public double getIdf() {
		return idf;
	}

	/**
	 * @return Term's starting posting position for the postings file.
	 */
	public int getPostingsIndex() {
		return postingsIndex;
	}

	/**
	 * Set term's posting list location 
	 * in postings file, if computed.
	 */
	public void setPostingsIndex(int index) {
		this.postingsIndex = index;
	}

	@Override
	public int compare(Term o1, Term o2) {
		return o1.getToken().compareTo(o2.getToken());
	}

	@Override
	public int compareTo(Term arg0) {
		return this.token.compareTo(arg0.getToken());
	}
}
