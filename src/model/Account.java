package model;

public class Account {
	
	private int number;//could be made an int, could be made a string, string feels more secure and immutable.  May need to be final
	private double balance;//how much money is in the account, cannot be zero.
	private Logfile transactions;//should link to a file that is filled with in formation of all transactions.  Unknown implementation.
	private boolean checking;//is it a checking account?
	
	public Account() {
		
	}//end of default constructor
	
	
	
	
}//end of class