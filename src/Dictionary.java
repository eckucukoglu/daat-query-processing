/**
 *  This class represents the Dictionary.
 *  Fixed sized dictionary is used.
 */
public class Dictionary {

	private Term[] wordlist;
	
	/**
	 * Constructor for Dictionary.
	 * 
	 * @param dictionaryterms dictionary terms.
	 */
	public Dictionary (Term[] dictionaryterms) {
		
		this.wordlist = dictionaryterms;
		
		
		
	}
	
	
	
	/**
	 * @return the wordlist
	 */
	public Term[] getWordlist() {
		return wordlist;
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
