/**
 *  This class represents the Query.
 *  Each query has word or words.
 *  
 *  Seperator to split words from query, are defined statically.
 */
public class Query {
	
	private String[] words;
	private int EvalPostingNo;
	
	/**
	 * Constructor for query.
	 * 
	 * @param query	raw query string.
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
}
