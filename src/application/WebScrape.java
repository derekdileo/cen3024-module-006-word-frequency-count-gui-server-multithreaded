package application;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A Class that will scrape text from given URL, add to a HashMap(of String, Integer) with all word entries and, 
 * finally, add each key/value pair to an ArrayList(of Word (Word implements Comparable(of Word))) 
 * so the ArrayList can be sorted by number of occurrences.
 * @author derekdileo */
public class WebScrape {

	// HashMap to store words and their occurances parsed from textfile
	protected static HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();
	
	/**
	 * A Method for scrubbing text from a user-requested URL which removes HTML tags from each line, 
	 * splits each line into individual words, adds all to a String array and, finally, returns the array.   
	 * @param website is the URL that the user wants to process
	 * @param sourceHead is the first line of text to be processed by the application
	 * @param sourceEnd is the last line of text to be processed by the application
	 * @throws IOException
	 * @return String[] which contains every word on the requested site
	 * @author Derek DiLeo */
	public static HashMap<String, Integer> parseSite(String website, String sourceHead, String sourceEnd) {
		
		try {
		// Instantiate the URL class
		URL url = new URL(website);
		
		// Retrieve the contents of the page
		Scanner sc = new Scanner(url.openStream());
		
		// Instantiate the StringBuffer class to hold the result
		StringBuffer sb = new StringBuffer();
		
		while(sc.hasNextLine()) {
			
			String line = sc.nextLine();
			
			// Detect user-indicated start
			if(line.toString().contains(sourceHead)) {
				
				//System.out.println("Line containing sourceHead: " + line);
				sb.append(" " + line);
				
				while(sc.hasNextLine()) {
					
					line = sc.nextLine();
					sb.append(" " + line);
					
					// Reached the user-indicated end
					if(line.toString().contains(sourceEnd)) {
						break;
					}
					
				}
				
			}
			
		}
		
		// Retrieve the String from the StringBuffer object
		String result = sb.toString();
		String[] words = null;
		
		// Remove the HTML tags
		String nohtml = result.toLowerCase().replaceAll("\\<.*?>", "");
		
		// Split string, ignoring all but letters of alphabet and apostrophe (to allow contractions)
		words = nohtml.split("[^a-zA-Z’]+"); 

		// Add all to HashMap<String, Integer> (word, frequency of occurence) 
		for (String word : words) {
			// Do not allow white blank white space or "mdash"
			if (word.toString() != "" && word.toString() != " " && !word.toString().contains("mdash")
					&& !word.toString().contains("	")) {
				
				int frequency = Database.queryFrequency(word);
				
				// If word already in database...
				if (frequency != -1) {
					// Increment the frequency
					Database.update(word, ++frequency); 
				} else {
					// Add to database
					Database.post(word, 1);
				}
				
			}
		}
		
        sc.close();
        return wordFrequency;
        
		} catch (IOException e) {
			System.out.println("IOException in WebScrape.parseSite(): " + e);
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
