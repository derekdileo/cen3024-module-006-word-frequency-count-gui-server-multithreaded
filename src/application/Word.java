package application;

public class Word implements Comparable<Word> {

	// Local variables
	private String word;
	private int frequency;

	// Constructor
	public Word(String key, int value) {
		super();
		this.word = key;
		this.frequency = value;
	}
	
	// Getters and Setters
	public String getKey() {
		return word;
	}

	public void setKey(String key) {
		this.word = key;
	}

	public int getValue() {
		return frequency;
	}

	public void setValue(int value) {
		this.frequency = value;
	}

	// Implemented Method that allows Word objects to be compared
	// by frequency (value)
	@Override
	public int compareTo(Word word) {
		return this.frequency - word.getValue();
	}

	// toString Method takes index value for final output of values to console
	public String toString(int index) {
		return "\n" + (index + 1) + ") Word: " + word + "\t\tFrequency: " + frequency;
	}

}
