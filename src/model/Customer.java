package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
				+ "(2) View Balance of an account\n" + "(3) Make transfer\n" + "(4) Accept transfer\n" + "(5) Withdraw or Deposit money" + "(6) Exit");
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
			transferOptions(scan);//this is done (ish)
			break;
		}//end of case
		case 4: {
			confirmTransfer(scan);//this is messy... but done.
			break;
		}//end of case
		case 5: {
			depositAndWithdrawal(scan);//make withdraw/deposits
			break;
		}//end of case
		case 6: {
			//exit
			exitFlag=true;
			break;
		}//end of case
		default:
			System.out.println("Unexpected value: " + input+". Must be numeric character between 1-5");
		}
		
		
		}//end of while loop
		
	}//end of option
	
	private void depositAndWithdrawal(Scanner scan) {
		Connection conn = cu.getConnection();
		System.out.println("Make a deposit?");
		if (scan.next().equalsIgnoreCase("Y")) {
			System.out.println("Enter account number to deposit to:");
			int number=scan.nextInt();
			System.out.println("Enter ammount:");
			double ammount = scan.nextDouble();
			PreparedStatement fetch;
			try {
				fetch = conn.prepareStatement("select balance from accounts where accountnumber = ?");
				fetch.setInt(1, number);
				double retrievedBal=-1;
				ResultSet rs =fetch.executeQuery();
				if (!rs.next())
					return;
				retrievedBal=rs.getDouble("balance");
				PreparedStatement alter = conn.prepareStatement("update accounts set balance = ? where accountnumber = ?");
				alter.setDouble(1, (retrievedBal+ammount));
				alter.setInt(2, number);
				alter.execute();
				System.out.println("Funds added.  Returning to previous menu...");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of catch
		}//end of if
		
		System.out.println("Withdraw funds?");
		if (scan.next().equalsIgnoreCase("Y")) {
			System.out.println("Enter account number to withdraw from:");
			int number=scan.nextInt();
			System.out.println("Enter ammount:");
			double ammount = scan.nextDouble();
			PreparedStatement fetch;
			try {
				fetch = conn.prepareStatement("select balance from accounts where accountnumber = ?");
				fetch.setInt(1, number);
				double retrievedBal=-1;
				ResultSet rs =fetch.executeQuery();
				if (!rs.next())
					return;
				retrievedBal=rs.getDouble("balance");
				PreparedStatement alter = conn.prepareStatement("update accounts set balance = ? where accountnumber = ?");
				alter.setDouble(1, (retrievedBal-ammount));
				
				alter.setInt(2, number);
				alter.execute();
				System.out.println("Funds removed.  Returning to previous menu...");
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end of catch
		}//end of if
		
		else {
			System.out.println("Returning to homepage...");
		}
		
	}//end of depositAndWithdrawl
	
	private void confirmTransfer(Scanner scan) {
		//for all accounts
		
		System.out.println("************************************************************");
		
		Connection conn = cu.getConnection();
		PreparedStatement fetch;
		try {
			fetch = conn.prepareStatement("select * from transactions where receiver in ("
					+ "select accountnumber from accounts where customerid="+idnumber+");");
			ResultSet rs = fetch.executeQuery();
			while (rs.next()) {
				System.out.print(rs.getInt("sender")+"  ");
				System.out.print(rs.getInt("receiver")+"  ");
				System.out.println(rs.getDouble("ammount"));
				
			}//end of while
			System.out.println("************************************************************");
			boolean exitFlag=false;
			while (!exitFlag) {
				System.out.println("Accept one of these?");
				if (scan.next().equalsIgnoreCase("Y")) {
					System.out.println("Enter sending account number:");
					int temp = scan.nextInt();
					System.out.println("Enter receiving account number:");
					int temp2 = scan.nextInt();
					
					fetch = conn.prepareStatement("select balance from accounts where accountnumber = ?");
					fetch.setInt(1, temp2);
					rs = fetch.executeQuery();
					double oldBalance=-1;
					if (rs.next()) {
						oldBalance = rs.getDouble("balance");
					}//end of if
					
					PreparedStatement fetch2 = conn.prepareStatement("select ammount from transactions where sender = ? and receiver = ?");
					fetch2.setInt(1,temp);
					fetch2.setInt(2,temp2);
					rs = fetch2.executeQuery();
					if (rs.next()) {
						oldBalance = rs.getDouble("ammount")+oldBalance;
					}//end of if
					System.out.println("odbalance:" +oldBalance);
					
					PreparedStatement submit = conn.prepareStatement("update transactions set accepted = true where sender = ? and receiver = ?;"
							+ "update accounts set balance = ? where accountnumber = ?;");
					submit.setInt(1, temp);
					submit.setInt(2, temp2);
					submit.setDouble(3, oldBalance);
					submit.setInt(4, temp2);
					submit.execute();
				}//end of if
				else {
					//System.out.println("Returning to previous menu...");
					//exitFlag=true;
				}//end of else
				System.out.println("Reject one of these?");
				
				
				if (scan.next().equalsIgnoreCase("Y")) {
					//need to kick the money back to the sender.
					//delete the transaction record.
					
					System.out.println("Enter sending account number:");
					int temp = scan.nextInt();
					System.out.println("Enter receiving account number:");
					int temp2 = scan.nextInt();
					
					fetch = conn.prepareStatement("select balance from accounts where accountnumber = ?");
					fetch.setInt(1, temp);
					rs = fetch.executeQuery();
					
					
					
					double oldBalance=-1;
					if (rs.next()) {
						oldBalance = rs.getDouble("balance");
					}//end of if
					
					PreparedStatement fetch2 = conn.prepareStatement("select ammount from transactions where sender = ? and receiver = ?");
					fetch2.setInt(1,temp);
					fetch2.setInt(2,temp2);
					rs = fetch2.executeQuery();
					if (rs.next()) {
						oldBalance = rs.getDouble("ammount")+oldBalance;
					}//end of if
					
					
					
					PreparedStatement submit = conn.prepareStatement("delete from transactions where sender = ? and receiver = ?;"
							+ "update accounts set balance = ? where accountnumber = ?;");
					submit.setInt(1, temp);
					submit.setInt(2, temp2);
					submit.setDouble(3, oldBalance);//need to fetch the value of the transaction and the user
					submit.setInt(4, temp);
					submit.execute();
					
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
		
		
	}//end of confirmTransfer
	
	
	private void transferOptions(Scanner scan) {
		
		Connection conn = cu.getConnection();
		double senderbalance = 0;
		
		System.out.println("Enter source account:");
		int source=scan.nextInt();
		
		System.out.println("Enter destination account:");
		int destination=scan.nextInt();
		if (source==destination)
			System.out.println("WARNING: you cannot send money to the source account");
		else {
			System.out.println("Enter ammount:");
			
			double balance=scan.nextDouble();
			PreparedStatement fetch;
			try {
				fetch = conn.prepareStatement("select balance from accounts where accountnumber = ?;");
				fetch.setInt(1, source);
				ResultSet rs=fetch.executeQuery();
				if (rs.next())
					senderbalance=rs.getDouble("balance");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}//end of catch
			
			if (validate(senderbalance,balance)) {//I actually don't know if validate would return a good answer there, so ... 
				System.out.println("Legal balance accepted.");
				try {
					
					
					PreparedStatement insert = conn.prepareStatement("update accounts set balance = ? where accountnumber = ?;"
							+ "insert into transactions (sender, receiver,ammount, accepted) values (?,?,?,false);");
					//making transaction, good
					//updating account, good, once I recalled how to do SQL again.
					insert.setDouble(1,(senderbalance-balance));//this needs to subtract out the balance
					insert.setInt(2, source);
					insert.setInt(3, source);
					insert.setInt(4, destination);
					insert.setDouble(5, balance);//balance to be transferred/locked into the system.
					insert.execute();
					for (int x=0;x<accounts.size();x++) {//seek the source account, update the values therein
						if (accounts.get(x).getNumber()==source)
							accounts.get(x).setBalance(senderbalance-balance);
					}//end of for loop
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//end of if (validate(balance))
		}//end of else
		
	}//end of transferOption
	
	public boolean validate(double senderBalance,double balance) {
		//running system checks that an invalid number entry doesn't get passed for transfers.
		if (balance<=0) {
			System.out.println("cannot transfer zero or negative ammount");
			return false;
		}//end of else
		if ((senderBalance-balance)<0) {
			System.out.println("Cannot transfer more money than account holds.");
			return false;
		}
			
		
		return true;
		
	}//end of validate
	
	
	
	
	
	
	
	
	private String seeAccounts() {
		
		collectAccounts();
		String output="";
		if (accounts.isEmpty())
			return "No accounts attatched to this account!\nTalk to our bankers to setup one!";
		else
			for (int index=0;index<accounts.size();index++)
				 output+=accounts.get(index).toString()+"\n";
		return output;
	}//end of seeAccounts
	
	
	
}//end of class