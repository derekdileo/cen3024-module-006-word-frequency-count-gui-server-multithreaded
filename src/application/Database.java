package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


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
//			String url = "jdbc:mysql://24.196.52.166:3306/database_name";
			String username = "root";
			String password = "rootpassword";
			Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connection to database established!");
			return conn;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
 		} 
		// Return null if not successful
		return null;
	}
	
	/**
	 * Method creates a table within the database (if it does not exist already).
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
			System.out.println("Function complete.");
		}
		
	}
	
	/**
	 * Method drops a table within the database (if it exists).
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
			System.out.println("Function complete.");
		}
		
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
			System.out.println("Post complete!");
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
			System.out.println("Post complete!");
		}
		
	}
	
	
	// Variables for testing createTable method
	public static String columnOne = "word varchar(255) NOT NULL UNIQUE";
	public static String columnTwo = "frequency int NOT NULL";
	public static String primaryKey = "PRIMARY KEY(word)";
	
	
	public static void main(String[] args) {
		try {
			
			//createTable("new_table", columnOne, columnTwo, primaryKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
