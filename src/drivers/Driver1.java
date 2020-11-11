package drivers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import controller.*;
import model.Account;
import util.ConnectionUtil;

public class Driver1 {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		/*
		 * WARNING: this is an experimental driver for the banking app. DO NOT use this
		 * to initialize out to the full program. Random bits of experimentation and
		 * reminds will make there way into here as I try and figure out what the heck
		 * needs to be done
		 */
		MenuUI basicUI = new MenuUI();

		ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
		Connection conn = cu.getConnection();
		conn.setSchema("banking");

		if (conn != null && !conn.isClosed()) {
			System.out.println("Connection Established.");
		} // end of if
/*
		// testing some database stuff, things aren't working or adding up.
		Statement statementObject = conn.createStatement();

		String queryString = "select * from \"registrations\";";//\" is necessary around table names.  WHY?  WHO THE ^%# KNOWS!
		// execute the statement
		ResultSet results = statementObject.executeQuery(queryString);
		System.out.println("Printing collected results:");

		while (results.next()) {
			System.out.print(results.getInt("registrationid"));
			System.out.println(results.getString("lastname"));
		} // end of test while for results
*/
		Statement collectAccounts = conn.createStatement();
		ResultSet retrieval = collectAccounts.executeQuery("select accountnumber, balance, checking from accounts where customerid = 1;");
		while (retrieval.next()) {
			int number=retrieval.getInt("accountnumber");
			double bal=retrieval.getDouble("balance");
			boolean type=retrieval.getBoolean("checking");
			
			System.out.println(number +" | "+bal+" | "+type);
			//System.out.println("Remove:accounts were successfully added");//debug confirmation line ONLY
		}//end of while
		
		
		
		
		// end of testing
 
		while (true) {
			basicUI.run();
		} // end of while loop

	}// end of main

}// end of class