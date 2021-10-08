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
		
		// Launch JavaFX GUI 
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
