package model;

import java.util.*;

public class Employee extends User {
	
	private List<Register> awaitingRegisters=new ArrayList<Register>();//class pointing to a list of accounts awaiting denial/approval.
	//read from the database, and hold the access address elsewhere, if trying to be efficient for security purposes.
	//on a logout, the information should hopefully be slated for deallocation.
	private List<Account> awaitingAccounts=new ArrayList<Account>();
	
	private String employeeID;//ID string of the employee
	
	
	
	
}//end of class