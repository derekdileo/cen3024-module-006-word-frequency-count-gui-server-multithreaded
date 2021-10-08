package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * A Class to analyze text from a local text file, strip html tags, 
 * and generate a HashMap<String, Integer> with key=word, value=occurrences 
 * (requires knowledge of line numbers to be scanned). 
 * @author derekdileo */
public class TextAnalyzer {

	// Declare filepath and file to be written
	protected static final String filepath = "/Users/derekdileo/Documents/Software Development/Workspaces/Java-Programming-For-Beginners/cen3024-module-006-word-frequency-count-gui/src/application/scrape.txt";
	protected static File file;
	
	// HashMap to store words and their occurances parsed from textfile
	protected static HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();

	// Scanner for text file input
	static Scanner sc;

	/**
	 * Method to parse text file into HashMap<String, Integer> 
	 * with key=word, value=occurrences using File and Scanner. 
	 * @return HashMap containing words and their occurrences in the File
	 * @author Derek DiLeo */
	public static HashMap<String, Integer> formatFileToMap() {

		try {
			// Create file object from text file at filepath
			file = new File(filepath);

			// Initialize scanner to read file
			sc = new Scanner(file);

			// Array to hold strings from line.split()
			String[] words = null;

			// Track lines to locate start and finish of poem
			int lineCount = 0;

			// Loop until end of poem
			while (sc.hasNextLine()) {

				// Create a string from current line
				String line = sc.nextLine();

				// Increment line counter
				lineCount++;

				// Poem occurs in text file between these lines
				if (lineCount >= 68 && lineCount <= 242) {

					// Strip line of html tags using RegEx
					String nohtml = line.toString().toLowerCase().replaceAll("\\<.*?>", "");

					// Split string, ignoring all but letters of alphabet and apostrophe (to allow contractions)
					words = nohtml.split("[^a-zA-Z’]+");

					// Add all to HashMap<String, Integer> (word, frequency of occurance) 
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
				}
			}
			return wordFrequency;
		} catch (FileNotFoundException fnfe) {
			sc.close();
			fnfe.printStackTrace();
			System.out.println(fnfe.getMessage());
		}
		sc.close();			
		return null;
	}

}
