/**
 *  This class represents the Query.
 *  Each query has word or words.
 *  
 *  Seperator to split words from query, are defined statically.
 */
public class Query {
	
	private String[] words;
	private int EvalPostingNo;
	private int NonEvalPostingNo;
	
	/**
	 * Constructor for query.
	 * 
	 * @param words	raw query string.
	 */
	public Query(String[] words) {
		
		this.words = words;
		this.EvalPostingNo = 0;
	}
	
	/**
	 * @return the words
	 */
	public String[] getWords() {
		return words;
	}

	/**
	 * @return the evalPostingNo
	 */
	public int getEvalPostingNo() {
		return EvalPostingNo;
	}

	/**
	 * @param evalPostingNo the evalPostingNo to set
	 */
	public void setEvalPostingNo(int evalPostingNo) {
		EvalPostingNo = evalPostingNo;
	}

	/**
	 * @return the nonEvalPostingNo
	 */
	public int getNonEvalPostingNo() {
		return NonEvalPostingNo;
	}

	/**
	 * @param nonEvalPostingNo the nonEvalPostingNo to set
	 */
	public void setNonEvalPostingNo(int nonEvalPostingNo) {
		NonEvalPostingNo = nonEvalPostingNo;
	}
}
