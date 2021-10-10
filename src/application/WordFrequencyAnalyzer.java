package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A Class which instantiates a Word object with each entry of HashMap (K/V = word/frequency) 
 * and adds to an ArrayList (of Word) to enable sorting and printing (Word implements Comparable<Word>).   
 * @author Derek DiLeo */
public class WordFrequencyAnalyzer {
	
	/**
	 * Method instantiates a Word object with each entry of HashMap (K/V = word/frequency) 
     * then adds all to (and returns) an ArrayList(of Word).  
	 * @param hm is a HashMap(of String, Integer) with Key/Value = word/frequency 
	 * @return ArrayList(of Word) which enables the sorting and printing of each 
	 * entry(Word implements Comparable(for Word)). */
	static ArrayList<Word> processHashMap(HashMap<String, Integer> hm) {

		// ArrayList to hold Word objects for later comparison
		ArrayList<Word> wordsUnsorted = new ArrayList<Word>();

		// Loop through each entry in HashMap
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
