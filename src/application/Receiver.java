package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.Date;

public class Receiver extends Thread {

	// Instance variables
	private Socket socket;
	
	// String array to hold QuestionBox.display() responses from Client.
	private String[] userResponses;
	
	// Local Lists and Maps to hold return values from Class methods
	private String[] wordsArray;
	
	// Varibles used to show / hide text on GUI
	private StringBuilder sbTen;
	private StringBuilder sbAll;
	
	// These are accessed by the four Controller classes to print to GUI 
	protected String sbTenString;
	protected String sbAllString;
	
	// int variable to keep track of tables
	private static int tables = 97;
	
	// SQL tables cannot be named with ints, 
	// so using char tableId = (char) tables ((97 == 'a'))
	protected char tableId;
	
	// Constructor
	public Receiver(Socket socket) {
		this.socket = socket;
		this.tableId = (char) tables++;
	}

//	public static int getTables() {
//		return tables;
//	}
//
//	public static void setTables(int tables) {
//		Receiver.tables = tables;
//	}

	@Override
	public void run() {
		try {
			// Wrap input stream with a buffered reader
			BufferedReader fromClient = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			
			// Wrap output stream with a print writer
			// true = auto-flush output stream to ensure data is sent
			PrintWriter toClient = new PrintWriter(socket.getOutputStream(), true);
			
			// Print start time to server TextArea
			Main.ta.appendText("Client " + tableId + " Connected at " + new Date() + '\n');
			
			// Create table for THIS Client connection
			try {
				Database.deleteTable(this.tableId);
				Database.createTable(this.tableId);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// Inform Client of its ConnectionID == tableNumber
			//toClient.print(this.tableNumber);
			
			// String array to hold QuestionBox.display() responses from Client. 
			userResponses = new String[4];
			
			// Infinite loop to collect input from user
			// Not necessary here, but useful when in enclosing loop is unknown
			while(true) {
				
				for(int i = 0; i < 5; i++) {
					
					// Read and store each response line sent by Client
					String response = fromClient.readLine();

					userResponses[i] = response; 
					
					// Display inputs to Server text area for troubleshooting purposes
					Main.ta.appendText("\n " + i + ": " + response);
					
					// Fourth response should terminate the infinte loop
					if(userResponses[i].equals("quit...")) {
						 Main.ta.appendText("\nuserResponses == quit...");
						 break;
					}
					 
				}
				
				break;
			}
			
			// String array created by WebScrape.parseSite() 
			// which contains every word (and multiples)
			this.wordsArray = WebScrape.parseSite(userResponses[0], userResponses[1], userResponses[2]);
			
			// Process wordsArray and push to database. 
			// If word exists in DB, increment its frequency
			WebScrape.wordsToDB(wordsArray, this.tableId);
			
			// SELECT * FROM words ORDER BY DESC and return ResultSet
			ResultSet results = Database.getResults(this.tableId);
			
			// Use StringBuilder to Convert ResultSet into sbTen and sbAll Strings
			displayResults(results);
			 
			// Send top ten results back to client
			toClient.println(sbTenString);
			toClient.println("pause...");

			// Send all results back to client
			toClient.println(sbAllString);
			toClient.println("pause...");
			
			// Drop table associated with Client connection
			try {
				Database.deleteTable(this.tableId);
			} catch (Exception e) {
				Main.ta.appendText("Error deleting client-specific table: " + this.tableId + "..." + e.getMessage());
				e.printStackTrace();
			}
			
		} catch(IOException e) {
			System.out.println("Exception in Receiver " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			try {
				socket.close();
			} catch(IOException e) {
				System.out.println("Exception in Receiver (finally block) " + e.getMessage());
				
			}
			
		}
		
	}

	/** Method to convert printed database contents to topTen and All windows on JavaFX GUI.
	 *  @param rs is the ResultSet returned from Database.getResults() method. */
	private void displayResults(ResultSet rs) {
		try {
			// Build a string of top 10 results to push to Main.fxml GUI
			sbTen = new StringBuilder();
			sbTen.append("\nTop Ten Results\n\n");
			sbTen.append(",");
			
			// Build a string of all results to push to AllResults.fxml GUI
			sbAll = new StringBuilder();
			sbAll.append("\nAll Results\n\n");
			sbAll.append(",");
			
			// Variables for buildString()
			String word = null;
			int frequency = 0;
			int wordCount = 0;
			
			// Scan through result set
			while(rs.next()) {
				word = rs.getString(1);
				frequency = rs.getInt(2);
			
				String line = buildString(word, frequency, wordCount);
			
				// Handle top10 and all results lists
				if (wordCount < 10) {
					sbTen.append(line);
					sbTen.append(",");
					sbAll.append(line);
					sbAll.append(",");
				} else {
					sbAll.append(line);
					sbAll.append(",");
				}
				
				wordCount++;
				
			}
			
			// Save results to String variables which are called from either:
			// MainC-, MainDefaultC-, AllResultsC- or AllResultsDefaultC- ontrollers to push to GUI
			sbTenString = sbTen.toString();
			sbAllString = sbAll.toString();
		
			rs.close();
		} catch (Exception e) {
			System.out.println("Exception in Main.displayResults()" + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/** Method to create a string for each word/frequency set in database which uses \t to account for word size 
	 *  and places a blank space before the numbers 1-9 in order to make top10 results more uniform.
	 *  @param word is the word pulled from the database.
	 *  @param frequency is the number of times the word occurred on the parsed site.
	 *  @param count keeps track of the position in the list (which is in descending order by frequency). 
	 *  @return String to append to StringBuilder for top10 and/or all results which is pushed to GUI. */
	private String buildString(String word, int frequency, int count) {
		
		int size = word.length();
		
		// Add a space before numbers 1-9 to make top10 list appear more uniform
		if (count < 9) {
			// An attempt to make the word / frequency printouts more uniform, regardless of word length
			if(size <= 4) {
				return "\n " + (count + 1) + ") Word: " + word + "\t\t\t\tFrequency: " + frequency;
			} else if (size > 4 && size <= 11) {
				return "\n " + (count + 1) + ") Word: " + word + "\t\t\tFrequency: " + frequency;
			} else if (size > 11 && size <= 13){
				return "\n " + (count + 1) + ") Word: " + word + "\t\tFrequency: " + frequency;
			} else {
				return "\n " + (count + 1) + ") Word: " + word + "\tFrequency: " + frequency;
			}
		} else {
			if(size <= 4) {
				return "\n" + (count + 1) + ") Word: " + word + "\t\t\t\tFrequency: " + frequency;
			} else if (size > 4 && size <= 11) {
				return "\n" + (count + 1) + ") Word: " + word + "\t\t\tFrequency: " + frequency;
			} else if (size > 11 && size <= 13){
				return "\n" + (count + 1) + ") Word: " + word + "\t\tFrequency: " + frequency;
			} else {
				return "\n" + (count + 1) + ") Word: " + word + "\tFrequency: " + frequency;
			}
		
		}
		
	}
	
}
