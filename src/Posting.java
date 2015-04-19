/**
 *  This class represents the Posting.
 *  Each posting has document id value and 
 *  term frequency value for a term.
 */
public class Posting {

	private int docid;
	private int tf;
	
	/**
	 * Constructor for Posting.
	 * 
	 * @param docid
	 * @param tf
	 */
	public Posting(int docid, int tf) {
		this.docid = docid;
		this.tf = tf;
	}
	
	/**
	 * @return the tfForTerm
	 */
	public int getTf() {
		return tf;
	}

	/**
	 * @return the docid
	 */
	public int getDocid() {
		return docid;
	}
}
