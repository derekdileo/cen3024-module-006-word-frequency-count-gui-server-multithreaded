package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * A Class that will scrape text from given URL, add to a HashMap(of String, Integer) with all word entries and, 
 * finally, add each key/value pair to an ArrayList(of Word (Word implements Comparable(of Word))) 
 * so the ArrayList can be sorted by number of occurrences.
 * @author derekdileo */
public class WebScrape {

	// Initialize variables (website used in Main)
	//private static final String website = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
	
	// Array of Strings to hold words parsed from site
	// This array is used by 
	//protected static String[] words;
	

	/**
	 * A method for scrubbing text from a user-entered website using with advanced knowledge of 
	 * the number of the first and last lines of text to be processed by the application. 
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author Derek DiLeo */
	public static String[] parseSiteByLineNumbers(String website, int firstLine, int lastLine) {
		String[] words = null;
		try {
			// Navigate to site
			URL url = new URL(website);
			
			// Read text returned by server
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			// Track lines to locate start and finish of poem
			int lineCount = 0;
			String line;
			//words = null;
			
            while ((line = in.readLine()) != null) {
                
            	lineCount++;
            	// firstLine = 77, lastLine = 243?
            	if(lineCount >= firstLine && lineCount <= lastLine) {
            		
            		String nohtml = stripHtmlTags(line);
            		// Split string, ignoring all but letters of alphabet and apostrophe (to allow contractions)
					words = nohtml.split("[^a-zA-Z’]+");
            		
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
		return words;
	}
	
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
	public static HashMap<String, Integer> parseSite(String website, String sourceHead, String sourceEnd) throws IOException {
		
		// Navigate to site
		URL url = new URL(website);
		
		// Read text returned by server
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		
		
		// String variable to hold each line read by BufferedReader
		String line;
		
		// Array to hold strings from line.split()
		String[] words; 
		
		int lineCount = 0;
        while ((line = in.readLine()) != null) {
        	
        	
        	if(line.toString().contains(sourceHead)) {
        		
        		// Strip first line of html tags using RegEx, print for testing
        		String nohtml = line.toString().toLowerCase().replaceAll("\\<.*?>", "");
        		System.out.println("LineCount: " + lineCount + ", "+ nohtml);
        		
        		// Split first line into separate words and add all to String[]
        		words = nohtml.split("[^a-zA-Z’]+");
        		int size = words.length;
        		
        		for(int i = 0; i < size; i++) {
        			//System.out.println("First Line, word number: " + i + ", word: " + words[i]);
        		}
//       
        		
        		while ((line = in.readLine()) != null) {
        			// Strip HTML tags from subsequent lines,
            		// split into separate words and add all to String[]
        			nohtml = line.toString().toLowerCase().replaceAll("\\<.*?>", "");
        			//System.out.println(nohtml);
        			words = nohtml.split("[^a-zA-Z’]+");
        			
        			
        			
        			// Run until user-chosen endpoint
        			if(line.toString().contains(sourceEnd)) {
        				size = words.length;
        				for(int i = 0; i < size; i++) {
                			//System.out.println("Word number: " + i + ", word: " + words[i]);
                		}
        				
        				
        				System.out.println(line.toString() + "     the end" );
        				
        				System.out.println("Length of String[] words: " + words.length);
        				
        				System.out.println("Did I get here?");
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
        				return wordFrequency;
        			}
        			
        		}
        		
        	}
        	
        }
        in.close();
        
        return wordFrequency;
	}
	
	private static String stripHtmlTags(String line) {
		String nohtml = line.toString().toLowerCase().replaceAll("\\<.*?>", "");
		return nohtml;
	}
	
	
	
		
	/**
	 * Method to parse wordsArray returned by parseSite() into HashMap(of String, Integer)
	 * with key=word, value=occurrences
	 * @param words String[] returned by parseSite() method
	 * @return HashMap (of String, Integer) with key=word, value=occurrences */
//	protected static HashMap<String, Integer> arrayToMap(String[] words) {
//		
//		// add all to HashMap (word, frequency of occurrence)
//		for (String word : words) {
//			System.out.println("In arrayToMap()" + word.toString());
//			// Do not allow blank (white) space or "mdash"
//			if (word.toString() != "" && word.toString() != " " && !word.toString().contains("mdash") && !word.toString().contains("	")) {
//				
//				if (wordFrequency.containsKey(word)) { // If word already in HashMap
//					// Get the current word frequency count (value)
//					int n = wordFrequency.get(word);
//					
//					// Increment, then update count (value) in HashMap
//					wordFrequency.put(word,  ++n);
//				} else {
//					// Otherwise, place word in Hashmap, set count to 1
//					wordFrequency.put(word, 1);
//				}
//			}
//			
//		}
//		return wordFrequency;
//	}
	
	
	public static void printArrays(String[] words) {
		for (String word : words) {
			System.out.println(word.toString());
		}
	}
	
	public static ArrayList<Word> wordsArrayList;
	
	
	// To test the Methods as they are written
	public static void main(String[] args) throws IOException {
		// Testing parseSiteByLineNumbers() 
		String website = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
//		String[] wordsArray = parseSiteByLineNumbers(website, 77, 243);
//		System.out.println("parseSiteByLineNumbers() Results: \n");
//		System.out.println(Arrays.asList(wordsArray));
		
		
		
		// Testing parseSite() 
		String sourceHead = "<h1>The Raven</h1>";
		String sourceEnd = "<!--end chapter-->";
		HashMap<String, Integer> wordMap = parseSite(website, sourceHead, sourceEnd);
		
		
		
		
		System.out.println("parseSite() Results: \n");
		
		wordsArrayList = WordFrequencyAnalyzer.processHashMap(wordMap);
		
		Collections.sort(wordsArrayList);
		Collections.reverse(wordsArrayList);
		
		for (Word word : wordsArrayList) {
			int index = wordsArrayList.indexOf(word);
			
			//Print each Word in wordsArrayListWords
			System.out.println(wordsArrayList.get(index).toString(index));
			
		}
		
		
		//printArrays(wordsList);
		
		
	}
	
}


/*
 * Collections.sort(wordsList);
		Collections.reverse(wordsList);
		int listSize = wordsList.size();
		System.out.println("wordsList.size() = " + listSize);
		for(Word word : wordsList) {
			int index = wordsList.indexOf(word);
			
			System.out.println(wordsList.get(index).toString(index));
			
		}
 * */

