import java.util.Comparator;

/**
 *  This class represents the Posting.
 *  Each posting has document id value and 
 *  term frequency value for a term.
 */
public class Posting implements Comparable<Posting>, Comparator<Posting> {

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
	 * Constructor for Posting.
	 * 
	 * @param docid
	 */
	public Posting(int docid) {
		this.docid = docid;
		this.tf = 0;
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

	@Override
	public int compare(Posting o1, Posting o2) {
		return (o1.getDocid() - o2.getDocid());
	}

	@Override
	public int compareTo(Posting o) {
		return (this.docid - o.getDocid());
	}
}
