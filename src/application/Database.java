package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/** 
 * The Database class will be home to all methods which  
 * pertain to the local MySQL database for this project.
 * @author derekdileo */
public class Database {

	// Declare variables
	protected static Connection conn;
	
	/** 
	 * Method establishes a connection with local MySQL database
	 * @return returns a database Connection to the caller
	 * @throws Exception */
	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/word_occurrences"; 
//			String url = "jdbc:mysql://24.196.52.166:3306/database_name"; used for an online db
			String username = "root";
			String password = "rootpassword";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
 		} 
		// Return null if not successful
		return null;
	}
	
	/**
	 * Method creates a table within the database (if it does not exist already) with two columns and a primary key (more can be added later).
	 * @param tableName is the desired name of the table to be created
	 * @param columnOne is the desired name, data type and other identifiers for the first column in the table
	 * @param columnTwo is the desired name, data type and other identifiers for the first column in the table
	 * @param primaryKey is the desired primaryKey and any additional information which can be appended to the end of the PreparedStatement
	 * @throws Exception */
	public static void createTable(String tableName, String columnOne, String columnTwo, String primaryKey) throws Exception {
		try {
			// Establish a connection
			conn = getConnection();
			
			// Create PreparedStatement and Execute
			String create = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + columnOne + ", " + columnTwo + ", " + primaryKey + ")";
			PreparedStatement pstmt = conn.prepareStatement(create);
 		 	pstmt.executeUpdate();
 		 	
 		 	// Close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: createTable() is complete!");
		}
		
	}
	
	/**
	 * Method creates the standard words table within the database (if it does not exist already).
	 * @param primaryKey is the desired primaryKey and any additional information which can be appended to the end of the PreparedStatement
	 * @throws Exception */
	public static void createWordsTable(String tableName) throws Exception {
		try {
			// Establish a connection
			conn = getConnection();
			
			// Create PreparedStatement and Execute
			String create = "CREATE TABLE IF NOT EXISTS " + tableName + " (word varchar(255) NOT NULL UNIQUE, frequency int NOT NULL, PRIMARY KEY(word))";
			PreparedStatement pstmt = conn.prepareStatement(create);
 		 	pstmt.executeUpdate();
 		 	
 		 	// Close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: createTable() is complete!");
		}
		
	}
	
	/**
	 * Method drops a table within the database (if it exists).
	 * @param tableName is the name of the table to be deleted (if it exists).
	 * @throws Exception */
	public static void deleteTable(String tableName) throws Exception {
		try {
			// Establish a connection
			conn = getConnection();
			
			// Create PreparedStatement and Execute
			String delete = "DROP TABLE IF EXISTS " + tableName + "";
			PreparedStatement pstmt = conn.prepareStatement(delete);
 		 	pstmt.executeUpdate();
 		 	
 		 	// Close the connection
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: deleteTable() is complete!");
		}
		
	}
	
	/**
	 * Method to check databse 
	 * @param word
	 * @return
	 */
	public static int queryFrequency(String word) {
		
		try {
			conn = getConnection();
			String post = "SELECT frequency FROM words WHERE word = '" + word + "'";
			PreparedStatement pstmt = conn.prepareStatement(post);
			ResultSet rs = pstmt.executeQuery();
			int frequency = 0;
			if(rs.next()) {
				frequency = rs.getInt(1);
				System.out.println("Frequency: " + frequency);
				conn.close();
				return frequency;
			} else {
				frequency = -1;
				conn.close();
				System.out.println("Error: " + word + " is not in database...");
				return frequency;
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: queryFrequency("+word+") is complete!");
		}
		return -1;
		
	}
	
	
	
	/**
	 * Method posts (inserts) desired word and frequency values into the words table
	 * @param word is the desired word to be posted to the words table
	 * @param frequency is the frequency of occurrence of the word in our program 
	 * @throws Exception */
	public static void post(String word, int frequency) throws Exception {
		try {
			conn = getConnection();
			String post = "INSERT INTO words (word, frequency) VALUES ('" + word +"', '" + frequency + "')";
			PreparedStatement pstmt = conn.prepareStatement(post);
			pstmt.executeUpdate();
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: post("+word+") is complete!");
		}
		
	}
	
	/**
	 * Method updates the frequency (occurrences) of the selected word in the table
	 * @param word is the target key whose frequency is to be updated
	 * @param frequency is the new frequency of occurrence of the word in our program 
	 * @throws Exception */
	public static void update(String word, int frequency) throws Exception {
		try {
			conn = getConnection();
			String update = "UPDATE words SET frequency = " + frequency + " WHERE word  = '" + word + "'";
			PreparedStatement pstmt = conn.prepareStatement(update);
			pstmt.executeUpdate();
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: update("+word+", "+frequency+") is complete!");
		}
		
	}
	
	/**
	 * Method uses prepareStatement(statement).executeUpdate() to update the database. 
	 * @param statement is the desired prepared statement to be passed in and executed.
	 * @throws Exception */
	public static void preparedUpdate(String statement) throws Exception {
		try {
			conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(statement);
			pstmt.executeUpdate();
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("The Method: preparedUpdate() is complete!");
		}
		
	}

	
	// Variables for testing createTable method
	private static String columnOne = "word varchar(255) NOT NULL UNIQUE";
	private static String columnTwo = "frequency int NOT NULL";
	private static String primaryKey = "PRIMARY KEY(word)";
	
	public static void main(String[] args) {
		try {
			deleteTable("words");
			createTable("words", columnOne, columnTwo, primaryKey);
			// Should pass and return 2
			post("The", 2);
			int freq = queryFrequency("The");
			System.out.println(freq);
			
			// Should fail and return -1
			post("He", 2);
			freq = queryFrequency("Alas");
			System.out.println(freq);
			deleteTable("words");
			createWordsTable("words");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
