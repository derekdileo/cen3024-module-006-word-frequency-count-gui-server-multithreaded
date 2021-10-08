package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * A Class that will scrape text from given URI and write to a file.
 * @author derekdileo */
public class WebScrapeToFile {

	// Initialize variables (website used in Main)
	private static final int DEFAULT_BUFFER_SIZE = 4096;
	private static final String filepath = "/Users/derekdileo/Documents/Software Development/Workspaces/Java-Programming-For-Beginners/cen3024-module-006-word-frequency-count-gui/src/application/scrape.txt";
	protected static final String website = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
	
	/**
	 * A method for scrubbing text from a website by opening an InputStream with a given URI, 
	 * creates a File at a given filepath and calls copyInputStreamToFile write to that file. 
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 * @author Derek DiLeo */
    protected static void createFile() throws IOException, URISyntaxException {

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
    
    /**
     * Method called by createFile() to use a FileOutputStream to write text to the generated file. 
     * @param inputStream
     * @param file
     * @throws IOException
     * @author Derek DiLeo */
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
