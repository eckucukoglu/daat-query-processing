/**
 *  This class represents the Dictionary.
 *  Fixed sized dictionary is used.
 */
public class Dictionary {

	/* Set the fixed sized wordlist file */
	public static final int NUMBEROFTERMS = 3560655;
	
	private Term[] wordlist;
	
	
	public Dictionary (Term[] dictionaryterms) {
		
		// Check dictionary terms length in case of unexpected results.
		if (dictionaryterms.length > NUMBEROFTERMS)	{
			System.out.println("Dictionary terms are more than fixed wordlist size");
			System.exit(1);
		}
		
		// this.wordlist = new Term[NUMBEROFTERMS];
		
		for (int i = 0; i < NUMBEROFTERMS; ++i) {
			this.wordlist[i] = new Term(dictionaryterms[i]); 
		}
		
		
		
	}
	
	
	
	/**
	 * @return the wordlist
	 */
	public Term[] getWordlist() {
		return wordlist;
	}
	

	
	
}
