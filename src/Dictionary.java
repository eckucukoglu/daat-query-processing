/**
 *  This class represents the Dictionary.
 *  Fixed sized dictionary is used.
 */
public class Dictionary {

	private Term[] termlist;
	
	/**
	 * Constructor for Dictionary.
	 * 
	 * @param dictionaryterms dictionary terms.
	 */
	public Dictionary (Term[] dictionaryterms) {
		
		this.termlist = dictionaryterms;
		
		
		
	}
	
	
	
	/**
	 * @return the termlist
	 */
	public Term[] getTermlist() {
		return termlist;
	}
	
	/**
	 * Compute byte position of beginning of term's posting list.
	 * Each posting is a <int document-id, int term-frequency> with totally 8 bytes.
	 * 
	 * Postings file is ascending order according to Term token. 
	 * 
	 * @param term
	 * @return byte position of beginning of term's posting list.
	 */
	public int getBeginningOfPostingList (Term term) {
		
		
		return 0;
	}

	
	
}
