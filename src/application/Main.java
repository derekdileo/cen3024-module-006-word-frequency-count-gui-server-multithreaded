package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/** Server side of an application which scrapes text from a user-selected 
 *  website and displays top10 (and all) word occurrences to a Client-side JavaFX GUI.
 *  @author derekdileo */
public class Main extends Application {
	
	/** Main method calls launch() to start JavaFX GUI.
	 *  @param args mandatory parameters for command line method call */
	public static void main(String[] args) {
		launch();
	}
	
	// Declare Stage outside of start() Method so it is accessible to closeProgram() method
	protected static Stage window;
	protected static TextArea ta;
	
	/** The start method (which is called on the JavaFX Application Thread) 
	 * is the entry point for this application and is called after the init 
	 * method has returned- most of the application logic takes place here. */
	@Override
	public void start(Stage primaryStage) {
		
		// Rename stage to window for sanity
		window = primaryStage;
		
		// Text area for displaying Server contents
		ta = new TextArea();
		
		// Create a scene and place it in the stage
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		
		// Set stage (window) title & scene
		window.setTitle("Word Frequency Analyzer Server");
		window.setScene(scene);
		
		// Show GUI
		window.show();
		
		// Handle close button request. 
		// Launch ConfirmBox to confirm if user wishes to quit
		window.setOnCloseRequest(e -> {
			// Consume the event to allow closeProgram() to do its job
			e.consume();
			closeProgram();
		});
		
		// Create ServerSocket
		// server.accept() blocks JavaFX application thread.
		// So, accept() must be invoked on separe thread.
		new Thread(() -> {
			
			try (ServerSocket serverSocket = new ServerSocket(8000)){
				
				ta.appendText("Server started at: " + new Date());
				
				while(true) {
					// Accept connection request from Client by instantiating
					// Reciever Object (which extends Thread)
					Socket socket = serverSocket.accept();
					Receiver receiver = new Receiver(socket);
					receiver.start();
					
					// Same can be achieved by the following
					// new Receiver(serverSocket.accept()).start();
					
				}
				
			} catch(IOException ex) {
				ta.appendText("Error in Server start(): " + ex.getMessage());
				System.out.println("Error in Server start()");
				ex.printStackTrace();
			}
			
		}).start();
		
	}
		
	/** closeProgram() Method uses ConfirmBox class to confirm is user wants to quit */
	protected static void closeProgram() {
       // Ask if user wants to exit (no title necessary, leave blank)
       Boolean answer = ConfirmBox.display("", "Are you sure you want to quit?");
       if (answer) {
           // Run any necessary code before window closes:
    	   window.close();
    	   System.exit(0);
    	   
       }
       
	}
	
}
