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
}
