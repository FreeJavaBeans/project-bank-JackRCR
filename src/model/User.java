package model;

public abstract class User {
	//may be made abstract, to segregate between customer and employee
	
	
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private int idnumber;//used for identifying where the hell it came from or needs to go.
	
	public User() {
		
	}//end of default constructor
	
	
	
}//end of class