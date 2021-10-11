package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/** Application scrapes website to a file and prints a 
 * list of top 10 word occurrences to a JavaFX GUI.
 * @author derekdileo */
public class Main extends Application {
	
	/** Main method calls WebScrapeToFile and launch() to start JavaFX GUI.
	 * @param args mandatory parameters for command line method call */
	public static void main(String[] args) {
		// Launch JavaFX GUI 
		launch();
	}
	
	// Declare stage (window) outside of start() method
	// so it is accessible to closeProgram() method
	static Stage window;
	
	/** start() launches the GUI */
	@Override
	public void start(Stage primaryStage) {
		
		// Rename stage to window for sanity
		window = primaryStage;
		
		// Set stage title
		window.setTitle("Word Frequency Analyzer");
		
		// Handle close button request. 
		// Launch ConfirmBox to confirm if user wishes to quit
		window.setOnCloseRequest(e -> {
			// Consume the event to allow closeProgram() to do its job
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
	 *  and if true, deletes scrape.txt (if exists). */
	protected static void closeProgram() {
       // Ask if user wants to exit
       Boolean answer = ConfirmBox.display("", "Are you sure you want to quit?");
       if (answer) {
           // Run any necessary code before window closes:
           System.out.println("Window Closed!");
           window.close();
       }
	}

}
