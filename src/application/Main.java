package application;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	
	
	public static void main(String[] args) {
		try {
			WebScrapeToFile.createFile();
		} catch (IOException e) {
			System.out.println("IOException at WebScrapeToFile.createFile()!");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			System.out.println("URISyntaxException at WebScrapeToFile.createFile()!");// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch();
	}
	
	// Declare stage (window) outside of start() method
	//to make accessible to closeProgram() method
	static Stage window;
	
	@Override
	public void start(Stage primaryStage) {
		
		// Rename stage to window for simplicity
		window = primaryStage;
		
		// Set stage title
		window.setTitle("Word Frequency Analyzer");
		
		// Handle close button request. 
		// Launch ConfirmBox to confirm if user wishes to quit
		window.setOnCloseRequest(e -> {
			// Consume the event to allow ConfirmBox to do its job
			e.consume();
			closeProgram();
		});
		
		
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,800,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// Local Lists and Maps to hold return values from Class methods
//	private static ArrayList<String> wordsArrayListStrings;
//	private static HashMap<String, Integer> wordFrequencyHashMap;
//	private static ArrayList<Word> wordsArrayListWords;

//	private static void processText() {
//		// ArrayList of Strings returned from local file
//		wordsArrayListStrings = new ArrayList<String>(TextAnalyzer.formatFile());
//		
//		// Process ArrayList and move into HashMap with key = Word and value = frequency of Word 
//		wordFrequencyHashMap = new HashMap<String, Integer>(WordFrequencyAnalyzer.wordFrequencyCounter(wordsArrayListStrings));
//		
//		// Process HashMap<Word> and return to ArrayList<Word> to be sorted
//		wordsArrayListWords = new ArrayList<Word>(WordFrequencyAnalyzer.processHashMap(wordFrequencyHashMap));
//		
//		// Sort wordsArrayList by frequency
//		Collections.sort(wordsArrayListWords);
//
//		// Reverse for highest frequency first
//		Collections.reverse(wordsArrayListWords);
//		
//		// New versions of Java do not add the word "" to the list. However, Java version 1.8 does
//		// This "" occurs 73 times and, therefore, appears at the top of the words list.
//		// I tried for hours to fix it, but in the end, I had to bandaid with this. 
//		wordsArrayListWords.remove(0);
//
//		// Print after sort
//		System.out.println("\nSorted:");
//		
//		for (Word word : wordsArrayListWords) {
//			
//			// Get value of index location to pass into Word.toString(int index)
//			int index = wordsArrayListWords.indexOf(word);
//			
//			// Print each Word in wordsArrayListWords
//			System.out.println(wordsArrayListWords.get(index).toString(index));	
//		}
//	}
	
	/** Method uses ConfirmBox class to confirm if user wants to quit
    */
	protected static void closeProgram() {
       // Ask if user wants to exit
       Boolean answer = ConfirmBox.display("", "Are you sure you want to quit?");
       if (answer) {
           // Run any necessary code before window closes:

    	   // delete scrape.txt if it exists
    	   if(TextAnalyzer.file.exists()) {
    		   TextAnalyzer.file.delete();
    	   }
           System.out.println("Window Closed!");
           window.close();
       }
	}
	

}
