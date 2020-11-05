package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	private static ConnectionUtil singleton = new ConnectionUtil();
	private static Connection conn;
	
	private ConnectionUtil() {
		super();
		//getting secrets into javaprogram
		//two ways
		//read from a file, some sort of config like a .xml or .properties or a .yaml
		//or we can get a value from the system environment variables.
		
		String password = System.getenv("DB_PASSWORD");
		String username = System.getenv("DB_USERNAME");
		String url = System.getenv("DB_URL");
		try {
			ConnectionUtil.conn = DriverManager.getConnection(url, username, password);
		}//end of try
		catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("USERNAME:"+username);
			System.out.println("PASSWORD:"+password);
			System.out.println("URL:"+url);
			e.printStackTrace();
			
		}//end of catch SQLException
		
	}//end of private constructor
	
	
	public Connection getConnection() {
		return conn;
	}//end of getConnection


	public static ConnectionUtil getConnectionUtil() {
		// TODO Auto-generated method stub
		return singleton;
	}//end of connectionUtil

}//end of class
