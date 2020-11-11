package controller;

import java.sql.*;
import java.util.Scanner;

import model.Customer;
import util.ConnectionUtil;

public class MenuUI {
	// front-end display class.
	// filling this out first, to figure out what needs to be done in the back-end.
	// and since the back-end is awaiting lessons on hold to connect the great two
	// eldritch entities together.

	// this is little more than a launcher for the various menus and submenus. I
	// don't believe there is any need for a permanent field,
	// just a looped execution handing continue/quit operations.

	private Scanner input;
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	// immediately below before the constructor, are misc implementation planning
	// and notes, specifically about sections not implemented yet.
	// completed chunks are stripped out and removed.

	// if employee
	private String empAccount = "Welcome.  Are you here to\n" + "(1) review and approve customer registrations\n"
			+ "(2) review and approve account requests\n" + "(3) review all accounts";// scatter some personalizing
																						// information in the greeting.
	// 1 would be the facilitating of the creation of customer login details
	// formally
	// 2 would be the facilitating the openning of new accounts.
	// 3 would be for any other general action, which may be something to combine 2
	// and 3 in order to subdivide further. Not sure.

	// if customer

	// 1 start with zero accounts, so setting up an account can be a two step process.
	// 2 needs to open up a sub menu for another entry to be read in the account to be viewed, also printing a list
	// 3 transfer to internal or internal specified accounts.
	// 4 not sure how to enfore acceptance of transfers. Sounds unusual. Is rejection from receiver possible?
	// 5 Logout from the console, return to first menu, strip future access.

	public MenuUI() {
		input = new Scanner(System.in);
	}// end of default constructor

	public void run() {
		int option = 0;

		while (true) {
			System.out.println(
					"Welcome to ASCII Banking.  Select an option to proceed:\n(1) Register account\n(2) Login");
			if (input.hasNextInt()) {
				option = input.nextInt();
				if (option == 1)
					register();
				else if (option == 2)
					login();
				else
					System.out.println("Warning invalid option.");
			} // end of if (input.hasNextInt())
			else {
				System.out.println("Warning: invalid entry.  Numeric characters only.");
				input.next();

			} // end of else
		} // end of while
	}// end of run

	public void register() {
		String[][] details = new String[6][2];// only five fields for now, but there could be any number.
		details[0][0] = "First name:";// First name
		details[1][0] = "Last name:";// Last name
		details[2][0] = "Enter last four digits of SSN:";// SSN, just as a verification of ID for the employees.
		details[3][0] = "Enter username:";// Username
		details[4][0] = "Enter passcode:";// Passcode
		details[5][0] = "Enter Email";// email

		Connection conn = cu.getConnection();

		// may steal some elements from what would be various background check forms, to
		// arrange in a faux design.
		// There would be a series of asks, to which the user would have to fill in
		// sequence. No going back, at least at this time.

		while (true) {
			System.out.println(
					"Enter the following information for application registration.  Denial is at bank discretion.");
			for (int x = 0; x < details.length; x++) {
				System.out.println(details[x][0]);
				details[x][1] = input.next();
			} // end of for loop

			System.out.println("Is the above information correctly entered? Y/N");
			if (input.next().equalsIgnoreCase("Y")) {
				System.out.println("Thank you " + details[0][1]
						+ ", your information will be reviewed by an employee within 10 business days.");
				break;
			} // end of if
			else {
				System.out.println("Do you wish to attempt to reenter your information? Y/N");

				if (input.next().equals("Y"))
					continue;
				else
					break;
			} // end of else

		} // end of while

		try {
			PreparedStatement ps = conn.prepareStatement(
					"insert into registrations (firstname, lastname,\"SSN\",username,passcode,email) values (?,?,?,?,?,?);");
			ps.setString(1, details[0][1]);
			ps.setString(2, details[1][1]);
			ps.setInt(3, Integer.parseInt(details[2][1]));
			ps.setString(4, details[3][1]);
			ps.setString(5, details[4][1]);
			ps.setString(6, details[5][1]);
			ps.execute();// this works altogether surprisingly well, ignoring my idiocy to start off.
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("An error occurred.");

		} // end of catch
		System.out.println("Returning to main menu...");

		// TODO: pass information into a DB or local class for local access. Favoring
		// direct into DB.
		// return to the run menu is normal operation.

	}// end of register menu

	public void login() {
		String[] details = new String[2];
		Connection conn = cu.getConnection();

		while (true) {
			System.out.println("Enter your username:");
			details[0] = input.next();
			System.out.println("Enter your passcode:");
			details[1] = input.next();

			try {//start of customer try block
				PreparedStatement search = conn.prepareStatement("select * from registrations where username like (?) and passcode like(?);");// searching customers
				search.setString(1, details[0]);
				search.setString(2, details[1]);
				ResultSet returns = search.executeQuery();// handling of returns is a question. Need to ignore some
				
				//I'm slow sometimes, okay.
				//PreparedStatement test = conn.prepareStatement("select * from \"registrations\" where username like ('JSmith') and passcode like('password');");
				//ResultSet returns = test.executeQuery();
				// security questions that I can think of.
				
				//Statement test = conn.createStatement();
				//ResultSet returns = test.executeQuery("select * from \"registrations\" where username like ('"+details+"') and passcode like('password');");
				//if returns has anything at all, it works.
				//IT WORKS NOW, DON'T QUESTION ME
				
				
				if (returns.next()) {
					Customer login = new Customer(returns.getString("firstname"),returns.getString("lastname"),returns.getInt("registrationid"));
					login.options(input);
					break;//logout kicks back to the home screen.
				} // end of if
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Customer not Found");
				e.printStackTrace();
			}
			try {//start of employee try block
				PreparedStatement search = conn.prepareStatement("select * from \"employee\" where username "
								+ "like (?) and passcode like(?);");// searching employees
				search.setString(1, details[0]);
				search.setString(2, details[1]);
				;
				ResultSet returns = search.executeQuery();
				
				if (returns.next()) {
					
				} // end of if
				else {

				} // end of else
				
			} catch (SQLException e) {
				System.out.println("Employee not Found");
				e.printStackTrace();
				
			} // end of catch
			System.out.println("Login failed, retry?");
			if (input.next().equalsIgnoreCase("Y"))
				;//go around again
			else {
				System.out.println("Returning to homepage...");
				break;
			}
			
		} // end of while

		// if the returned user is Employee or customer, check which and then deliver
		// them to the correct interface.

		// TODO: further development or testing requires a backend to search and
		// retrieve credential information for comparison.
		// failure message is the same if it fails on user or passcode.

	}// end of login menu

	public void employeeAccountMenu() {// I think there is need for an input to be parsed through here.

	}// end of employeeAccountMenu

	public void customerAccountMenu() {// I think there is need for an input to be parsed through here.

	}// end of customerAccountMenu

}// end of MenuUI