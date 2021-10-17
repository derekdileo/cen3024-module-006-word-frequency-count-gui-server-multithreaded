package application;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WordManager {
	
	Map<String, Word> wordList = new ConcurrentHashMap<String, Word>();
	
	public void addWord(String word, int frequency) {
		Word newWord = new Word(word, frequency);
		validateWord(newWord);
		checkIfWordAlreadyExists(newWord);
		wordList.put(generateKey(newWord), newWord);
	}

	public Collection<Word> getAllWords() {
		return wordList.values();
	}
	
	private void checkIfWordAlreadyExists(Word newWord) {
		if(wordList.containsKey(generateKey(newWord))) {
			throw new RuntimeException("Word Already Exists");
		}
	}
	
	private void validateWord(Word newWord) {
		newWord.validateWord();
		newWord.validateFrequency();
	}
	
    private String generateKey(Word newWord) {
        return String.format("%s-%d", newWord.getWord(), newWord.getFrequency());
    }
	
}
