package model;

public class Employee extends User {
	
	private Register waitingAccount;//class pointing to a list of accounts awaiting denial/approval.
	//read from the database, and hold the access address elsewhere, if trying to be efficient for security purposes.
	//on a logout, the information should hopefully be slated for deallocation.
	private String employeeID;//ID string of the employee
	
	
	
	
}//end of class