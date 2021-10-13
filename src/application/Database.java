package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

	protected static Connection conn;
	
	// Establish a connection with database
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
	
	
	public static void main(String[] args) {
		try {
			getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
