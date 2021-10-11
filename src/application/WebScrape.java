package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
		
		int lineCount = 0;
		
		while(sc.hasNextLine()) {
			
			String line = sc.nextLine();
			lineCount++;
			
			// Detect user-indicated start
			if(line.toString().contains(sourceHead)) {
				
				//System.out.println("Line containing sourceHead: " + line);
				sb.append(" " + line);
				lineCount++;
				
				
				while(sc.hasNextLine()) {
					
					line = sc.nextLine();
					System.out.println("Line: " + lineCount++ + "Inside while: " + line);
					sb.append(" " + line);
					
					// Reached the user-indicated end
					if(line.toString().contains(sourceEnd)) {
						System.out.println("Reached the end!");
						break;
					}
				}
				
			}
			
		}
		
		// Retrieve the String from the String Buffere object
		String result = sb.toString();
		//System.out.println(result);
		String[] words = null;
		// Remove the HTML tags
		//result = result.toLowerCase().replaceAll("<[^>]*>", "");
		String nohtml = result.toLowerCase().replaceAll("\\<.*?>", "");
		
		// Split string, ignoring all but letters of alphabet and apostrophe (to allow contractions)
		words = nohtml.split("[^a-zA-Z’]+"); 

		// Add all to HashMap<String, Integer> (word, frequency of occurence) 
		for (String word : words) {
			// Do not allow white blank white space or "mdash"
			if (word.toString() != "" && word.toString() != " " && !word.toString().contains("mdash")
					&& !word.toString().contains("	")) {
				
				if (wordFrequency.containsKey(word)) { // If word already in HashMap
					// Get the current word frequency count
					int n = wordFrequency.get(word);

					// Increment, then replace count
					wordFrequency.put(word, ++n);
				}

				// Otherwise, place word in Hashmap, set count to 1
				else {
					wordFrequency.put(word, 1);
				}
			}
		}
		//System.out.print("Contents of the web page: " + result);
	
        sc.close();
        return wordFrequency;
        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public static void printArrays(String[] words) {
		for (String word : words) {
			System.out.println(word.toString());
		}
	}
	
	public static ArrayList<Word> wordsArrayList;
	
	
	// To test the Methods as they are written
	public static void main(String[] args) {
		
		// Testing parseSiteByLineNumbers() 
		String website = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
//		String[] wordsArray = parseSiteByLineNumbers(website, 77, 243);
//		System.out.println("parseSiteByLineNumbers() Results: \n");
//		System.out.println(Arrays.asList(wordsArray));
		
		
		
		// Testing parseSite() 
		String sourceHead = "<h1>The Raven</h1>";
		String sourceEnd = "<!--end chapter-->";
		HashMap<String, Integer> wordMap = parseSite(website, sourceHead, sourceEnd);
		
		wordsArrayList = WordFrequencyAnalyzer.processHashMap(wordMap);
		
		Collections.sort(wordsArrayList);
		Collections.reverse(wordsArrayList);
		
		for (Word word : wordsArrayList) {
			int index = wordsArrayList.indexOf(word);
			
			//Print each Word in wordsArrayListWords
			System.out.println(wordsArrayList.get(index).toString(index));
			
		}
	}
}



/*
 * 
 * 
 * /**
	 * A method for scrubbing text from a user-entered website using with advanced knowledge of 
	 * the number of the first and last lines of text to be processed by the application. 
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Derek DiLeo */
//	public static String[] parseSiteByLineNumbers(String website, int firstLine, int lastLine) {
//		String[] words = null;
//		try {
//			// Navigate to site
//			URL url = new URL(website);
//			
//			// Read text returned by server
//			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
//			
//			// Track lines to locate start and finish of poem
//			int lineCount = 0;
//			String line;
//			//words = null;
//			
//            while ((line = in.readLine()) != null) {
//                
//            	lineCount++;
//            	// firstLine = 77, lastLine = 243?
//            	if(lineCount >= firstLine && lineCount <= lastLine) {
//            		
//            		String nohtml = line.toLowerCase().replaceAll("\\<.*?>", "");
//            		// Split string, ignoring all but letters of alphabet and apostrophe (to allow contractions)
//					words = nohtml.split("[^a-zA-Z’]+");
//            		
//            	}
//            	
//            }
//            in.close();
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//			System.out.println("MalformedURLException in WebScrape.parseSite(): " + e);
//		} catch (IOException e) {
//			e.printStackTrace();
//			System.out.println("IOException in WebScrape.parseSite(): " + e);
//		}
//		return words;
//	}

