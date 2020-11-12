package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Employee extends User {
	
	

	private List<Register> awaitingRegisters=new ArrayList<Register>();//class pointing to a list of accounts awaiting denial/approval.
	//read from the database, and hold the access address elsewhere, if trying to be efficient for security purposes.
	//on a logout, the information should hopefully be slated for deallocation.
	private List<Account> awaitingAccounts=new ArrayList<Account>();
	
	private List<Integer> customerids = new ArrayList<Integer>();
	
	private String employeeID;//ID string of the employee
	
	public Employee(String firstName, String lastName, int idnumber) {
		super(firstName, lastName, idnumber);
		
	}//end of constructor
	
	public void options(Scanner scan) {
		int input=-1;
		boolean exitFlag=false;
		while (!exitFlag) {
		System.out.println("Welcome "+lastName+".  What actions do you wish to take?\n"
				+ "(1) Review Registrations\n"
				+ "(2) Review waiting accounts\n"
				+ "(3) View Customer account\n"//requires account id to be passed in, that will retrieve the user's details and 
				+ "(4) View Transaction \nlog"
				+ "(5) Exit");
		if (scan.hasNextInt())
			input=scan.nextInt();
		else
			scan.next();//
		
			switch (input) {
			case 1: {
				registersApproval(scan);//this is done.
				break;
			}//end of case
			case 2: {
				accountsApproval(scan);//this is done.
				break;
			}//end of case
			case 3: {
				reviewCustomer(scan);//this is done.
				break;
			}//end of case
			case 4: {
				//view transaction log : will just print out all the transactions from the query.
				break;
			}//end of case
			case 5: {
				//exit
				exitFlag=true;
				break;
			}//end of case
			default:
				System.out.println("Unexpected value: " + input+". Must be numeric character between 1-5");
			}//end of switch
		}//end of while
		
	}//end of options
	
	private void reviewCustomer(Scanner scan) {
		System.out.println("*******************************************************");
		Connection conn = cu.getConnection();
		boolean exitFlag=false;
		try {
			while (!exitFlag) {
				System.out.println("Enter customerid to review:");
				int userid = scan.nextInt();
				PreparedStatement fetch = conn.prepareStatement("select * from registrations where registrationid = ?");
				fetch.setInt(1, userid);
				ResultSet results = fetch.executeQuery();
				
				if (results.next()) {
					System.out.print(results.getString("firstname")+" ");
					System.out.print(results.getString("lastname")+"  ");
					System.out.print(results.getString("username")+"  ");
					System.out.print(results.getString("email")+"  ");
					System.out.println(results.getInt("registrationid"));
				}//end of if
				System.out.println("*******************************************************");
				System.out.println("Accounts:");
				fetch = conn.prepareStatement("select * from accounts where customerid = ?");
				fetch.setInt(1, userid);
				results = fetch.executeQuery();
				while (results.next()) {
					System.out.print(results.getInt("accountnumber")+"  ");
					System.out.print(results.getDouble("balance")+"  ");
					System.out.print(results.getBoolean("checking")+"  ");
					System.out.println(results.getInt("approvedbyemployee"));
					
				}//end of while
				System.out.println("*******************************************************");
				System.out.println("Review another account?");
				if (scan.next().equalsIgnoreCase("Y"))
					;//go around again
				else {
					System.out.println("Returning to previous menu...");
					exitFlag=true;
				}//end of else
			}//end  of while(!exitflag)
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
		
	}//end of reviewCustomer
	
	private void accountsApproval(Scanner scan) {
		System.out.println("*******************************************************");
		Connection conn = cu.getConnection();
		try {
			Statement fetch = conn.createStatement();
			ResultSet results = fetch.executeQuery("select * from accounts where approvedbyemployee is null;");
			while (results.next()) {
				System.out.print(results.getInt("accountnumber")+"  ");
				System.out.print(results.getDouble("balance")+"  ");
				System.out.print(results.getBoolean("checking")+"  ");
				System.out.println(results.getInt("customerid"));
			}//end of while
			System.out.println("*******************************************************");
			boolean exitFlag=false;
			while (!exitFlag) {
				System.out.println("Would you like to act on these accounts? ");
				if (scan.next().equalsIgnoreCase("Y")) {//yep, into another set of ifs
					System.out.println("Approve an account?");
					if (scan.next().equalsIgnoreCase("Y")) {
						System.out.println("Enter accountid to approve:");
						fetch.execute("update accounts set approvedbyemployee = "+idnumber+" where accountnumber = "+scan.nextInt()+";");//customer approved
					}//end of if
					else {
						System.out.println("Enter registrationid of account to deny:");
						fetch.execute("delete from accounts where accountnumber = "+scan.nextInt()+";");//customer denied, remove from database. Crude, but it works.
					}//end of else
				}//end of if
				else {
					System.out.println("Returning to previous menu...");
					exitFlag=true;
				}//end of else
			}//end of while
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
	}//end of accountsApproval
	
	
	
	public void registersApproval(Scanner scan) {
		System.out.println("*******************************************************");
		Connection conn = cu.getConnection();
		try {
			Statement fetch = conn.createStatement();
			ResultSet results = fetch.executeQuery("select * from registrations where approvedbyemployee is null;");
			
			while (results.next()) {
				System.out.print(results.getString("firstname")+" ");
				System.out.print(results.getString("lastname")+"  ");
				System.out.print(results.getInt("SSN")+"  ");
				System.out.print(results.getString("username")+"  ");
				System.out.print(results.getString("email")+"  ");
				System.out.print(results.getInt("registrationid")+"  ");
				System.out.println(results.getInt("approvedbyemployee"));
				
			}//end of while
			System.out.println("*******************************************************");
			boolean exitFlag=false;
			while (!exitFlag) {
				System.out.println("Do you wish to approve or deny one of these accounts? ");
				if (scan.next().equalsIgnoreCase("Y")) {//yep, into another set of ifs
					System.out.println("Approve an account?");
					if (scan.next().equalsIgnoreCase("Y")) {
						System.out.println("Enter registrationid of account to approve:");
						fetch.execute("update registrations set approvedbyemployee = "+idnumber+" where registrationid = "+scan.nextInt()+";");//customer approved
					}//end of if
					else {
						System.out.println("Enter registrationid of account to deny:");
						fetch.execute("delete from registrations where registrationid = "+scan.nextInt()+";");//customer denied, remove from database. Crude, but it works.
					}//end of else
				}//end of if
				else {
					System.out.println("Returning to previous menu...");
					exitFlag=true;
				}//end of else
			}//end of while
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//end of catch
	}//end of registersApproval
	
	
	
}//end of class