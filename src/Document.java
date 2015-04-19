import java.util.Comparator;

/**
 *  This class represents the Document.
 *  Each document has a document-id and score value.
 *  
 *  Comparator prefers higher scores.
 */
public class Document implements Comparator<Document> {

	private int docid;
	private float score;
	
	/**
	 * Constructor for Document.
	 *  
	 * @param docid document-id
	 * @param score score of a document
	 */
	public Document (int docid, float score) {
		this.docid = docid;
		this.score = score;
	}
	
	/**
	 * Default constructor for Document.
	 */
	public Document() {
		this.docid = 0;
		this.score = 0;
	}

	/**
	 * @return the docid
	 */
	public int getDocid() {
		return docid;
	}

	/**
	 * @return the score
	 */
	public float getScore() {
		return score;
	}

	@Override
	public int compare(Document o1, Document o2) {
		int retVal;
		float scoreDifference = o1.getScore() - o2.getScore();
		
		if (scoreDifference == 0)
			retVal = 0;
		else if (scoreDifference > 0)
			retVal = 1;
		else
			retVal = -1;
		
		return retVal;
	}
}
