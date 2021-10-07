package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextAnalyzer {

	// Declare filepath
	private static final String filepath = "/Users/derekdileo/Documents/Software Development/Workspaces/Java-Programming-For-Beginners/cen3024-module-006-word-frequency-count-gui/src/application/scrape.txt";

	// ArrayList to store individual words pulled from text file
	public static ArrayList<String> wordsList = new ArrayList<>();

	// Scanner for text file input
	static Scanner sc;

	// Method to parse text file into ArrayList<String> using File and Scanner
	public static ArrayList<String> formatFile() {

		try {

			// Create file object from file at filepath location
			File file = new File(filepath);

			// Initialize scanner for text file input
			sc = new Scanner(file);

			// Initialize String array to hold strings from line.split()
			String[] words = null;

			// Counter to track where poem starts and finishes on site
			int lineCount = 0;


			// Loop until end of poem
			while (sc.hasNextLine()) {

				// Create a string from current line
				String line = sc.nextLine();

				// Increment line counter
				lineCount++;

				// Poem occurs on site between these lines
				if (lineCount >= 68 && lineCount <= 242) {

					// Strip line of html tags
					String nohtml = line.toString().toLowerCase().replaceAll("\\<.*?>", "");

					// Split string, ignoring all but letters of alphabet and apostrophe (to allow contractions)
					words = nohtml.split("[^a-zA-Z’]+");

					// Add all to ArrayList<String> wordsList
					for (String word : words) {
						// Do not allow white blank white space or "mdash"
						if (word.toString() != "" && word.toString() != " " && !word.toString().contains("mdash")
								&& !word.toString().contains("	")) {
							wordsList.add(word.toString());
						}
					}
				}
			}
			return wordsList;
		} catch (FileNotFoundException fnfe) {
			sc.close();
			fnfe.printStackTrace();
			System.out.println(fnfe.getMessage());
		}
		sc.close();			
		return null;
	}

}
