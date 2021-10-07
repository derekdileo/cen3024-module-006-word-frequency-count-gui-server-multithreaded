package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/* A Class that will scrape text from given URI and write 
 * and write resultant text to a file.
 * */

public class WebScrapeToFile {

	public static final int DEFAULT_BUFFER_SIZE = 4096;
	public static final String filepath = "/Users/derekdileo/Documents/Software Development/Workspaces/Java-Programming-For-Beginners/cen3024-module-006-word-frequency-count-gui/src/application/scrape.txt";
	public static final String website = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
	
	
    public static void createFile() throws IOException, URISyntaxException {

    	// Declare URI from given URL
    	URI u = new URI(website);
    	
    	// Initiate input stream from uri location (to URL)
    	try (InputStream inputStream = u.toURL().openStream()) {

        	// Create file object using filepath location
            File file = new File(filepath);

            // Create text file from i/o stream
            copyInputStreamToFile(inputStream, file);
        }
    }
    
    private static void copyInputStreamToFile(InputStream inputStream, File file)
            throws IOException {
    	
        // Create a file output stream to write to the file represented by the 
    	// specified File object. If the second argument is true, then bytes will
    	// be written to the end of the file (appended) rather than the beginning 
        try (FileOutputStream outputStream = new FileOutputStream(file, false)) {
            int read;
            byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }
}
