package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Customer extends User {
	
	protected List <Account> accounts=new ArrayList<Account>();
	
	public Customer(String firstName, String lastName, int idnumber) {
		super(firstName, lastName, idnumber);
		collectAccounts();
	}//end of super constructor.
	
	public void collectAccounts() {
		Connection conn = cu.getConnection();
		accounts.clear();
		
		try {
			Statement collectAccounts = conn.createStatement();
			ResultSet retrieval = collectAccounts.executeQuery("select accountnumber, balance, checking from accounts where customerid = "+idnumber);
			
			while (retrieval.next()) {
				int number=retrieval.getInt("accountnumber");
				double bal=retrieval.getDouble("balance");
				boolean type=retrieval.getBoolean("checking");
				accounts.add(new Account(number,bal,type));
				//System.out.println("Remove:accounts were successfully added");//debug confirmation line ONLY
			}//end of while
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		
		
		
		
	}//end of collectAccounts

	public void options(Scanner scan) {
		int input = -1;
		boolean exitFlag=false;
		
		System.out.println("Welcome, "+firstName+".");
		while (!exitFlag) {
		System.out.println("Your accounts:\n"+seeAccounts()
				+ "What action do you wish to take?\n" + "(1) Request to open a new account\n"
				+ "(2) View Balance of an account\n" + "(3) Make transfer\n" + "(4) Accept transfer\n" + "(5) Exit");
		if (scan.hasNextInt())
			input=scan.nextInt();
		else
			scan.next();//
		
		switch (input) {
		case 1: {
			Account.createNewAccount(scan, idnumber);
			collectAccounts();//regenerates the list of accounts to include the newly added one.  Employee approval not programmed, no filter applied.
			break;
		}//end of case
		case 2: {
			//View accounts
			break;
		}//end of case
		case 3: {
			//specify transfer details
			break;
		}//end of case
		case 4: {
			//accept/reject transfers?
			break;
		}//end of case
		case 5: {
			//exit
			exitFlag=true;
			break;
		}//end of case
		default:
			System.out.println("Unexpected value: " + input+". Must be numeric character between 1-5");
		}
		
		
		}//end of while loop
		
		
		
	}//end of option
	
	
	private void transferOptions() {
		
		System.out.println("Enter destination account:");
		
		
	}//end of transferOption
	
	
	
	
	
	
	
	
	
	
	private String seeAccounts() {
		String output="";
		if (accounts.isEmpty())
			return "No accounts attatched to this account!\nTalk to our bankers to setup one!";
		else
			for (int index=0;index<accounts.size();index++)
				 output+=accounts.get(index).toString()+"\n";
		return output;
	}//end of seeAccounts
	
	
	
}//end of class