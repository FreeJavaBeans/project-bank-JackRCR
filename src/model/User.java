package model;

import util.ConnectionUtil;

public abstract class User {
	//may be made abstract, to segregate between customer and employee
	
	protected ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	protected String firstName;
	protected String lastName;
	//private String username;//ripping these out, as a minor security thing of "need to know"
	//private String password;
	protected int idnumber;//used for identifying where the hell it came from or needs to go.
	
	public User(String firstName, String lastName, int idnumber) {
		
		this.firstName=firstName;
		this.lastName=lastName;
		this.idnumber=idnumber;
		
		
	}//end of default constructor
	
	
	
}//end of class