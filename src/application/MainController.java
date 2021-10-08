package application;

import java.awt.Desktop;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainController implements Initializable {
	
	@FXML Button topTenButton;
	@FXML Button allButton;
	@FXML Hyperlink hyperlink;
	@FXML ImageView image;
	@FXML Label labelText;
	@FXML MenuBar menuBar;
	@FXML MenuItem fileCloseButton;
	@FXML MenuItem helpAboutButton;
	@FXML TextField textField;
	

	// Local Lists and Maps to hold return values from Class methods
	private static ArrayList<String> wordsArrayListStrings;
	private HashMap<String, Integer> wordFrequencyHashMap;
	private ArrayList<Word> wordsArrayListWords;
	public boolean displayText = false;
	private StringBuilder sb;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Loading user data!");
		
		Image imageFile = new Image("/resources/image.png");
		
		ImageView iv1 = new ImageView();
		iv1.setImage(imageFile);
		
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

	private void processText() {

		// Print after sort
		System.out.println("\nSorted:");
		
		for (Word word : wordsArrayListWords) {
			
			// Get value of index location to pass into Word.toString(int index)
			int index = wordsArrayListWords.indexOf(word);
			
			// Print each Word in wordsArrayListWords
			System.out.println(wordsArrayListWords.get(index).toString(index));
			
		}

		sb = new StringBuilder();
		for (int index = 0; index < 10; index++) {
			
			sb.append(wordsArrayListWords.get(index).toString(index) + "\n");
			//textField.setText(textField.getText() + wordsArrayListWords.get(index).toString(index) + "\n");
		}
		
		labelText.setText(sb.toString());
		displayText = true;
		labelText.setStyle("-fx-font-alignment: center");
	}
	
	
	@FXML public void handleFileClose(ActionEvent event) {
		Main.closeProgram();
	}


	@FXML public void handleHelpAbout(ActionEvent event) {
		try {
			  Desktop desktop = java.awt.Desktop.getDesktop();
			  URI oURL = new URI("https://github.com/derekdileo/cen3024-module-006-word-frequency-count-gui");
			  desktop.browse(oURL);
			} catch (Exception e) {
			  e.printStackTrace();
			}
	}


	@FXML public void handleHyperlink(ActionEvent event) {
		try {
			  Desktop desktop = java.awt.Desktop.getDesktop();
			  URI oURL = new URI(WebScrapeToFile.website);
			  desktop.browse(oURL);
			} catch (Exception e) {
			  e.printStackTrace();
			}
	}


	@FXML public void handleTopTenButton(ActionEvent event) {
		if (displayText) {
			labelText.setText("");
			displayText = false;
		} else {
			labelText.setText(sb.toString());
			displayText = true;
		}
		
	}

}
