package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

/**
 * A Class that will scrape text from given URI and write to a file.
 * @author derekdileo */
public class WebScrape {

	// Initialize variables (website used in Main)
	private static final String website = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
	
	// HashMap to store words and their occurances parsed from textfile
	protected static HashMap<String, Integer> wordFrequency = new HashMap<>();
	
	protected static String[] words;
	

	/**
	 * A method for scrubbing text from a website by opening an InputStream with a given URI, 
	 * creates a File at a given filepath and calls copyInputStreamToFile write to that file. 
	 * 
	 * @throws IOException
	 * @throws URISyntaxException
	 * @author Derek DiLeo */
    
	
	
	
	public static void parseSiteNumbers() {
		
		try {
			// Navigate to site
			URL url = new URL(website);
			
			// Read text returned by server
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			// Track lines to locate start and finish of poem
			int lineCount = 0;
			String line;
			
            while ((line = in.readLine()) != null) {
                
            	lineCount++;
            	if(lineCount > 77 && lineCount <= 243) {
            		
            		System.out.println(lineCount + ") " + line);
            		
            	}
            	
            }
            in.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("MalformedURLException in WebScrape.parseSite(): " + e);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException in WebScrape.parseSite(): " + e);
		}
		
	}
	
	public static void parseSite() {
		
		try {
			// Navigate to site
			URL url = new URL(website);
			
			// Read text returned by server
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			// Track lines to locate start and finish of poem
			int lineCount = 0;
			String line;
			//String nohtml;
			//String[] words = null;
			String sourceHead = "<h1>The Raven</h1>";
			String sourceEnd = "<!--end chapter-->";
			
            while ((line = in.readLine()) != null) {
            	
            	if(line.toString().contains(sourceHead)) {
            		
            		String nohtml = noHtml(line);
            		addToWordsArray(nohtml);
            		
            		System.out.println("First line: " + line);
            		while ((line = in.readLine()) != null) {
            			
            			nohtml = noHtml(line);
                		addToWordsArray(nohtml);
            			
            			lineCount++;
            			System.out.println(lineCount + ") " + line);
            			
            			if(line.toString().contains(sourceEnd)) {
            				break;
            			}
            			
            		}
            	
            	}
            	
            }
            in.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("MalformedURLException in WebScrape.parseSite(): " + e);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("IOException in WebScrape.parseSite(): " + e);
		}
		
	}
	
	private static String noHtml(String line) {
		String nohtml = line.toString().toLowerCase().replaceAll("\\<.*?>", "");
		return nohtml;
	}
	
	private static void addToWordsArray(String nohtml) {
		words = nohtml.split("[^a-zA-Z’]+");
	}
	
	public static void main(String[] args) {
		parseSite();
	}
	
	
}
