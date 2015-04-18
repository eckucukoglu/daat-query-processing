import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *  Common Parser class.
 *  To not to mixed up file operations with others.
 *  
 *  Set all file constants in here.
 */
public class Parser {

	public static final int NUMBEROFTERMS = 3560655; // in dictionary file
	public static final int NUMBEROFQUERIES = 500;
	public static final int NUMBEROFDOCUMENTS = 2236050;
	
	public static final String DATADIR = "data";
	public static final String LENGTHSFILE = "doc_lengths.txt";
	public static final String QUERYFILE = "hw500queries.txt";
	public static final String DICTIONARYFILE = "wordlist.txt";
	public static final String POSTINGSFILE = "postings.bin";
	
	public static final String WORD_SEPERATOR = " ";
	
	/**
	 * Return document length, read from LENGTHSFILE file with document id.
	 * For each line:
	 * <int document-id> <float document-length>
	 * 
	 * @return document lengths.
	 */
	public static float[] getDocLength () {
		float[] docLength = new float[NUMBEROFDOCUMENTS];
		
		Path path = Paths.get(System.getProperty("user.dir"), DATADIR, LENGTHSFILE);
		InputStream in;
		try {
			in = new FileInputStream(new File(path.toString()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        String line;
	        
	        int i = 0;
	        while ((line = reader.readLine()) != null) {
	        	String [] res = line.split(WORD_SEPERATOR);
	        	docLength[i++] = Float.parseFloat(res[1]);
	        }
	        
	        reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return docLength;
	}
	
	/**
	 * Return dictionary terms, read from DICTIONARYFILE file.
	 * For each line:
	 * <string token> <int length-of-postings-list> <float precomputed-idf> 
	 * 
	 * @return dictionary terms.
	 */
	public static Term[] getDictionaryTerms () {
		Term[] dictionaryTerms = new Term[NUMBEROFTERMS];
		
		Path path = Paths.get(System.getProperty("user.dir"), DATADIR, DICTIONARYFILE);
		InputStream in;
		try {
			in = new FileInputStream(new File(path.toString()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        String line;
	        
	        int i = 0;
	        while ((line = reader.readLine()) != null) {
	        	String token;
	        	int lengthOfPostingsList;
	        	float idf;
	        	
	        	String [] res = line.split(WORD_SEPERATOR);
	        	token = res[0];
	        	lengthOfPostingsList = Integer.parseInt(res[1]);
	        	idf = Float.parseFloat(res[2]);	        	
	        	
	        	dictionaryTerms[i++] = new Term(token, lengthOfPostingsList, idf);
	        }
	        
	        reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		
		return dictionaryTerms;
	}
	
	/**
	 * Return query list, read from QUERYFILE file.
	 * Each line is a <string query>
	 * 
	 * @return queries.
	 */
	public static Query[] getQueries () {
		Query[] queries = new Query[NUMBEROFQUERIES];
		
		Path path = Paths.get(System.getProperty("user.dir"), DATADIR, QUERYFILE);
		InputStream in;
		try {
			in = new FileInputStream(new File(path.toString()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        String line;
	        
	        int i = 0;
	        while ((line = reader.readLine()) != null) {
	        	String [] res = line.split(WORD_SEPERATOR);
	        	queries[i++] = new Query(res);
	        }
	        
	        reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return queries;
	}
	
	/**
	 * Return posting list, read from POSTINGSFILE.
	 * Each posting is a form of:
	 * <int document-id, int term-frequency>
	 * and each integer takes 4 bytes. Totally 8 bytes per posting.
	 * 
	 * Binary data file (POSTINGSFILE), is produced by C in a Linux system,
	 * hence if you attempt to read it in Windows, you may need to assume Little Endian.
	 * 
	 * @param fromPosting from which posting position should be read.
	 * @param lengthOfPostingsList length of postings list, read this number of times postings.
	 * @return
	 */
	public static Posting[] getPostings (int fromPosting, int lengthOfPostingsList) {
		Posting[] postings = new Posting[lengthOfPostingsList];
		
		Path path = Paths.get(System.getProperty("user.dir"), DATADIR, POSTINGSFILE);
		DataInputStream dis;
		try {
			
			dis = new DataInputStream(new FileInputStream(new File(path.toString())));
			dis.skipBytes(fromPosting * 8);	
			
			for (int i = 0; i < lengthOfPostingsList; ++i) {
				postings[i] = new Posting(readIntLittleEndian(dis), readIntLittleEndian(dis));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		
		return postings;
	}
	
	/**
	 * Read a 32-bit signed int, in little-endian binary format.
	 * 
	 * Reference:
	 * http://mindprod.com/jgloss/endian.html
	 *
	 * @param dis stream to read from.
	 * @return binary value.
	 * @throws IOException 
	 */
	static int readIntLittleEndian(DataInputStream dis) throws IOException {
		// get 4 unsigned byte components, and accumulate into an int.
		int accum = 0;
		
		for (int shiftBy = 0; shiftBy < 32; shiftBy += 8 ) {
		    accum |= ( dis.readByte() & 0xff ) << shiftBy;
		}
		
		return accum;
	}
}


