package application;
	


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	// Local Lists and Maps to hold return values from Class methods
	protected ArrayList<String> wordsArrayListStrings;
	protected HashMap<String, Integer> wordFrequencyHashMap;
	protected ArrayList<Word> wordsArrayListWords;

	// Declare stage (window) outside of start() method
	//to make accessible to closeProgram() method
	Stage window;
	
	@Override
	public void start(Stage primaryStage) {
		
		// Rename stage to window for simplicity
		window = primaryStage;
		
		// Set stage title
		window.setTitle("Word Frequency Analyzer");
		
		// Handle close button request. Launch ConfirmBox to confirm if user wishes to quit
		window.setOnCloseRequest(e -> {
			// Consume the event to allow ConfirmBox to do its job
			e.consume();
			closeProgram();
		});
		
		// Scrape website to create text file
		try {
			WebScraperInputStreamToFile.createFile();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			ioe.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		processText();
		
		
		try {
			StackPane root = (StackPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}






	private void processText() {
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

		// Print after sort
		System.out.println("\nSorted:");
		
		for (Word word : wordsArrayListWords) {
			
			// Get value of index location to pass into Word.toString(int index)
			int index = wordsArrayListWords.indexOf(word);
			
			// Print each Word in wordsArrayListWords
			System.out.println(wordsArrayListWords.get(index).toString(index));

		}
	}
	
	
	
	
	
	
	/** Method uses ConfirmBox class to confirm if user wants to quit
    */
	private void closeProgram() {
       // Ask if user wants to exit
       Boolean answer = ConfirmBox.display("", "Are you sure you want to quit?");
       if (answer) {
           // Run any necessary code before window closes:
           // Save / transfer files, etc...
           System.out.println("Window Closed!");
           window.close();
       }
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
