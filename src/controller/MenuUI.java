package controller;

import java.util.Scanner;

public class MenuUI {
	//front-end display class. 
	//filling this out first, to figure out what needs to be done in the back-end.
	//and since the back-end is awaiting lessons on hold to connect the great two eldritch entities together.
	
	//this is little more than a launcher for the various menus and submenus.  I don't believe there is any need for a permanent field,
	//just a looped execution handing continue/quit operations.
	
	private Scanner input;
	
	
	//immediately below before the constructor, are misc implementation planning and notes, specifically about sections not implemented yet.
	//completed chunks are stripped out and removed.
	
	//if employee
	private String empAccount = "Welcome.  Are you here to\n"
			+ "(1) review and approve customer registrations\n"
			+ "(2) review and approve account requests\n"
			+ "(3) review all accounts";//scatter some personalizing information in the greeting.
	//1 would be the facilitating of the creation of customer login details formally
	//2 would be the facilitating the openning of new accounts.
	//3 would be for any other general action, which may be something to combine 2 and 3 in order to subdivide further.  Not sure.
	
	//if customer
	private String cusAccount = "Welcome.  Your accounts are [list them here by number]\n"
			+ "What action do you wish to take in regards to these accounts?\n"
			+ "(1) Request to open a new account\n"
			+ "(2) View Balance of an account\n"
			+ "(3) Make transfer\n"
			+ "(4) Accept transfer\n"
			+ "(5) Exit";//refer to customer use cases for filling this out here.
	//1 start with zero accounts, so setting up an account can be a two step process.
	//2 needs to open up a sub menu for another entry to be read in the account to be viewed, also printing a list
	//3 transfer to internal or internal specified accounts.  
	//4 not sure how to enfore acceptance of transfers.  Sounds unusual.  Is rejection from reciever possible?
	//5 Logout from the console, return to first menu, strip future access.
	
	public MenuUI() {
		input = new Scanner(System.in);
	}//end of default constructor
	
	public void run() {
		int option=0;
		
		while (true) {
			System.out.println("Welcome to ASCII Banking.  Select an option to proceed:\n(1) Register account\n(2) Login");
			if(input.hasNextInt()) {
				option=input.nextInt();
				if (option==1)
					register();
				else if (option==2)
					login();
				else
					System.out.println("Warning invalid option.");
			}//end of if (input.hasNextInt())
			else {
				System.out.println("Warning: invalid entry.  Numeric characters only.");
				input.next();
				
			}//end of else
		}//end of while
	}//end of run
	
	public void register() {
		String[][] details=new String[6][2];//only five fields for now, but there could be any number.
		details[0][0]="First name:";//First name
		details[1][0]="Last name:";//Last name
		details[2][0]="Enter last four digits of SSN:";//SSN, just as a verification of ID for the employees.
		details[3][0]="Enter username:";//Username
		details[4][0]="Enter passcode:";//Passcode
		details[5][0]="Enter Email";//email
		
		//may steal some elements from what would be various background check forms, to arrange in a faux design.
		//There would be a series of asks, to which the user would have to fill in sequence.  No going back, at least at this time.
		
		while (true) {
			System.out.println("Enter the following information for application registration.  Denial is at bank discretion.");		
			for (int x=0;x<details.length;x++) {
				System.out.println(details[x][0]);
				details[x][1]=input.next();
			}//end of for loop
			
			System.out.println("Is the above information correctly entered? Y/N");
			if (input.next().equalsIgnoreCase("Y")) {
				System.out.println("Thank you "+details[0][1]+
						", your information will be reviewed by an employee within 10 business days.");
				break;
			}//end of if
			else {
				System.out.println("Do you wish to attempt to reenter your information? Y/N");
				if (input.next().equals("Y"))
					continue;
				else
					break;
			}//end of else
		}//end of while
		
		//TODO: pass information into a DB or local class for local access.  Favoring direct into DB.
		//return to the run menu is normal operation.
		
	}//end of register menu
	
	public void login() {
		String[] details = new String[2];

		while (true) {
			System.out.println("Enter your username:");
			details[0]=input.next();
			System.out.println("Enter your passcode:");
			details[0]=input.next();
			
			if (details[0].equals("x"/**/) && details[0].equals("y"/**/)) {//commented zones need to be replaced by a correct database call.
				System.out.println("Login successful welcome...");
				//TODO: by which implementation do we wish to 
			}//end of if
			else {
				System.out.println("Invalid credentials.  Retry? Y/N");
				if (input.next().equalsIgnoreCase("Y"))
					continue;
				else
					break;
			}//end of else
		}//end of while		
		
		//if the returned user is Employee or customer, check which and then deliver them to the correct interface.
		
		//TODO: further development or testing requires a backend to search and retrieve credential information for comparison.
		//failure message is the same if it fails on user or passcode.
		
	}//end of login menu
	
	
	public void employeeAccountMenu(){//I think there is need for an input to be parsed through here.
		
	}//end of employeeAccountMenu
	
	public void customerAccountMenu(){//I think there is need for an input to be parsed through here.
		
	}//end of customerAccountMenu
	
	
	
}//end of MenuUI