package application;
	


import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	// Local Lists and Maps to hold return values from Class methods
	public static ArrayList<String> wordsArrayListStrings;
	public static HashMap<String, Integer> wordFrequencyHashMap;
	public static ArrayList<Word> wordsArrayListWords;

	// Declare new stage (window) outside of start() method to make accessible to closeProgram() method
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
