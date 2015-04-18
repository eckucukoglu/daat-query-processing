/**
 *  This class represents the Document.
 *  Each document has document id value, 
 *  term frequency value for a term, and 
 *  precomputed vector length value.
 * 
 *  For each Document declaration, vector length is *not computed*, is retrieved from the file on-the fly.
 *
 */
public class Document {

	private int docid;
	private Term forTerm;
	private int tfForTerm;
	private float vectorLength;
	
	public Document(int docid, Term term, int tf) {
		this.docid = docid;
		this.forTerm = new Term(term);
		this.tfForTerm = tf;
		
		this.vectorLength = Parser.getDocLength(this.docid); 
	}
	
	/**
	 * @return the tfForTerm
	 */
	public int getTfForTerm() {
		return tfForTerm;
	}
	
	/**
	 * @return the forTerm
	 */
	public Term getForTerm() {
		return forTerm;
	}


	/**
	 * @return the docid
	 */
	public int getDocid() {
		return docid;
	}
	
	/**
	 * @return the vectorLength
	 */
	public float getVectorLength() {
		return vectorLength;
	}
	
	
}
