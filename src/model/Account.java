package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import util.ConnectionUtil;

public class Account {
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	private int number;// could be made an int, could be made a string, string feels more secure and
						// immutable. May need to be final
	private double balance;// how much money is in the account, cannot be zero.
	private boolean checking;// is it a checking account?

	public Account(int number, double balance, boolean checking) {
		this.number = number;
		this.balance = balance;
		this.checking = checking;
	}// end of default constructor

	public static void createNewAccount(Scanner scan, int customerid) {

		Connection conn = cu.getConnection();

		try {
			PreparedStatement ps = conn
					.prepareStatement("insert into accounts (balance, checking, customerid) values (?,?,?)");

			System.out.println("Enter balance you wish to use as seed:");
			if (scan.hasNextDouble())
				ps.setDouble(1, scan.nextDouble());
			else
				System.out.println("Invalid entry.  Must be decimal number");
			
			System.out.println("Is this a checking account?  Y/N");
			if (scan.next().equalsIgnoreCase("Y"))
				ps.setBoolean(2, true);
			else if (scan.next().equalsIgnoreCase("N"))
				ps.setBoolean(2, false);
			else
				System.out.println("invalid entry.");
			
			ps.setInt(3, customerid);
			
			ps.execute();

		} catch (SQLException e) {

			e.printStackTrace();
		} // end of catch

	}// end of createNewAccount menu

	@Override
	public String toString() {
		if (checking)
			return number + ": " + balance + " | Checking";
		else
			return number + ": " + balance + " | Savings";
	}//end of toString
	
	
	

}// end of class