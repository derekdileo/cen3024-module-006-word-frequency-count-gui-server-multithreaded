package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencyAnalyzer {

	
	// Declare HashMap with word=key, frequency=value
	public static HashMap<String, Integer> wordFrequency = new HashMap<String, Integer>();

	// Method to process String array created by readFile()
	public static HashMap<String, Integer> wordFrequencyCounter(ArrayList<String> words) {

		// loop through every word in array
		for (int i = 0; i < words.size(); i++) {

			// If HashMap contains the key, increment the value
			if (wordFrequency.containsKey(words.get(i))) {
				// Get the current word frequency count
				int n = wordFrequency.get(words.get(i));

				// Increment and replace count
				wordFrequency.put(words.get(i), ++n);
			}

			// Otherwise, place word in Hashmap, set count to 1
			else {
				wordFrequency.put(words.get(i), 1);
			}
		}
		return wordFrequency;
	}
	
	
	// Method to parse HashMap key/value pairs and push to an ArrayList
	static ArrayList<Word> processHashMap(HashMap<String, Integer> hm) {

		// ArrayList to hold all new Word objects created prior to sorting by frequency (value)
		ArrayList<Word> wordsUnsorted = new ArrayList<Word>();

		for (@SuppressWarnings("rawtypes")
		Map.Entry entry : hm.entrySet()) {

			// Pull key/value pairs for each word in wordFrequencyComplete HashMap
			String wordKey = entry.getKey().toString();
			String wordValue = entry.getValue().toString();
			int wordValueInt = Integer.parseInt(wordValue);

			// Use pairs to create new instances of Word class
			// (which implements Comparable<Word>)
			Word word = new Word(wordKey, wordValueInt);
			wordsUnsorted.add(word);
		}
		return wordsUnsorted;
	}

}
