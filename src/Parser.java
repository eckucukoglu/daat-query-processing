import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *  Common Parser class.
 *  To not to mixed up file operations with others.
 */
public class Parser {

	public static final String DATADIR = "data";
	public static final String LENGTHSFILE = "doc_lengths.txt";
	public static final String QUERYFILE = "hw500queries.txt";
	public static final String DICTIONARYFILE = "wordlist.txt";
	public static final String POSTINGSFILE = "postings.bin";
	
	
	/**
	 * Return document length, read from LENGTHSFILE file with document id.
	 * For each line:
	 * <int document-id> <float document-length>
	 * 
	 * @param docid
	 * @return document length.
	 */
	public static float getDocLength (int docid) {
		
		
		return 0;
	}
	
	/**
	 * Return dictionary terms, read from DICTIONARYFILE file.
	 * For each line:
	 * <string token> <int length-of-postings-list> <float precomputed-idf> 
	 * 
	 * @return dictionary terms.
	 */
	public static Term[] getDictionaryTerms () {

		Path path = Paths.get(System.getProperty("user.dir"), DATADIR, DICTIONARYFILE);
		InputStream in;
		try {
			in = new FileInputStream(new File(path.toString()));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        String line;
	        while ((line = reader.readLine()) != null) {
	        	
	        	
	        	System.out.println(line);
	        }
	        
	        reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		
		return null;
	}
	
	/**
	 * Return query list, read from QUERYFILE file.
	 * Each line is a <string query>
	 * 
	 * @return queries.
	 */
	public static Query[] getQueries () {
		
		
		return null;
	}
	
	/**
	 * Return posting list, read from POSTINGSFILE.
	 * Each posting is a form of:
	 * <int document-id, int term-frequency>
	 * and each integer takes 4 bytes.
	 * 
	 * Binary data file (POSTINGSFILE), is produced by C in a Linux system,
	 * hence if you attempt to read it in Windows, you may need to assume Little Endian.
	 * 
	 * @param beginningOfPostingList from which byte position should be read.
	 * @param lengthOfPostingsList length of postings list, read this number of times postings.
	 * @return
	 */
	public static Posting[] getPostings (int beginningOfPostingList, int lengthOfPostingsList) {
		
		
		return null;
	}
}
