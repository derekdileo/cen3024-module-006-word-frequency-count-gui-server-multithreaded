package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;

public class MainController implements Initializable {
	
	@FXML TextArea textArea;
	@FXML MenuBar menuBar;
	@FXML MenuItem fileCloseButton;
	@FXML MenuItem helpAboutButton;

	// Local Lists and Maps to hold return values from Class methods
	private static ArrayList<String> wordsArrayListStrings;
	private HashMap<String, Integer> wordFrequencyHashMap;
	private ArrayList<Word> wordsArrayListWords;

	private void processText() {

		// Print after sort
		System.out.println("\nSorted:");
		
		for (Word word : wordsArrayListWords) {
			
			// Get value of index location to pass into Word.toString(int index)
			int index = wordsArrayListWords.indexOf(word);
			
			// Print each Word in wordsArrayListWords
			System.out.println(wordsArrayListWords.get(index).toString(index));
			
		}

		for (int index = 0; index < 10; index++) {
			textArea.setText(textArea.getText() + wordsArrayListWords.get(index).toString(index) + "\n");
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Loading user data!");
		
		// ArrayList of Strings returned from local file
		wordsArrayListStrings = new ArrayList<String>(TextAnalyzer.formatFile());
		
		// Process ArrayList and move into HashMap with key = Word and value = frequency of Word 
		wordFrequencyHashMap = new HashMap<String, Integer>(WordFrequencyAnalyzer.wordFrequencyCounter(wordsArrayListStrings));
		
		// Process HashMap<Word> and return to ArrayList<Word> to be sorted
		wordsArrayListWords = new ArrayList<Word>(WordFrequencyAnalyzer.processHashMap(wordFrequencyHashMap));
		
		// Sort wordsArrayList by frequency
		Collections.sort(wordsArrayListWords);

		// Reverse for highest frequency first
		Collections.reverse(wordsArrayListWords);
		
		// New versions of Java do not add the word "" to the list. However, Java version 1.8 does
		// This "" occurs 73 times and, therefore, appears at the top of the words list.
		// I tried for hours to fix it, but in the end, I had to bandaid with this. 
		wordsArrayListWords.remove(0);
		
		processText();
		
	}


	@FXML public void handleFileClose(ActionEvent event) {
		Main.closeProgram();
	}


	@FXML public void handleHelpAbout(ActionEvent event) {
		
	}
	
	
}
