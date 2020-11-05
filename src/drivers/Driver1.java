package drivers;

import java.sql.Connection;
import java.sql.SQLException;

import controller.*;
import util.ConnectionUtil;

public class Driver1 {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		/* WARNING: this is an experimental driver for the banking app.  DO NOT use this to initialize out to the full program.
		 * Random bits of experimentation and reminds will make there way into here as I try and figure out what the heck needs to be done
		 */
		MenuUI basicUI = new MenuUI();
		
		ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
		Connection conn = cu.getConnection();
		
		if (conn != null && !conn.isClosed()) {
			System.out.println("Connection Established.");
		}//end of if
		
		while (true) {
			basicUI.run();
		}//end of while loop
		
		
		
		
		
		
		
	}//end of main

}//end of class