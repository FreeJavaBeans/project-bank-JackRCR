package controller;

public class MenuUI {
	//front-end display class. 
	//filling this out first, to figure out what needs to be done in the back-end.
	//and since the back-end is awaiting lessons on hold to connect the great two eldritch entities together.
	
	
	
	
	
	private String menuWelcome = "Welcome to ASCII Banking.  Select an option to proceed:\n"
			+ "(1) Register account\n"
			+ "(2) Login";
	//login would be for any user, assessing password against a stored database vavlue.
	//if the returned user is Employee or customer, check which and then deliver them to the correct interface.
	private String register = "Enter details.";//may steal some elements from what would be various background check forms, to arrange in a faux design.
	//There would be a series of asks, to which the user would have to fill in sequence.  No going back, at least at this time.

	private String login = "Enter Username: ";
	private String login2 = "Enter Password: ";
	//appraise and assess alongside username, for privacy purposes.
	private String failure = "Login failed, incorrect credientials.\n"
			+ "(1) Retry\n"
			+ "(2) Go back";
	//a bad return would offer the option to back out, or try again.
	//return to the previous options if go back punched.
	//successful entries proceed down
	
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
	//5 Logout from the console, return to first menu, strip future access
	
	
	
	
}//end of MenuUI