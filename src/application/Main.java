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
	
	//Variables to copy from when running for THIS site

	// Declare variables
	protected static String userWebsite = null;
	protected static String sourceHead = null;
	protected static String sourceEnd = null;
	private String defaultWebsite =  "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";
	private String defaultSourceHead = "<h1>The Raven</h1>";
	private String defaultSourceEnd = "<!--end chapter-->";
	
	private static String sitePrompt = "Please enter a website to evaluate";
	private static String startPrompt = "Please paste some text from the first line of text to be evaluated.";
	private static String endPrompt = "Please paste some text from the last line of text to be evaluated.";
	
	
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
		
		userWebsite = QuestionBox.display("Website to Process?", sitePrompt, defaultWebsite);
		sourceHead = QuestionBox.display("Start of Processing?", startPrompt, defaultSourceHead);
		sourceEnd = QuestionBox.display("End of Processing?", endPrompt, defaultSourceEnd);
		
		
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
