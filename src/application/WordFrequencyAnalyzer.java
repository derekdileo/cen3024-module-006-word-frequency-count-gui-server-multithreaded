package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordFrequencyAnalyzer {
	
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
