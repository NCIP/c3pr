package edu.duke.cabig.c3pr.domain;

public class UserCredentials {

	private Integer id 					=null;
	private String systemID 			=null;
	private String c3prUser				=null;
	private String systemUserName		=null;
	private String systemPassword 		=null;

	public UserCredentials(){
		
	}
	
	public UserCredentials(String systemID,String c3prUser, String systemUserName, String systemPassword){
		this.systemID = systemID;
		this.c3prUser = c3prUser;		
		this.systemUserName = systemUserName;
		this.systemPassword = systemPassword;
		
	}



	public String getC3prUser() {
		return c3prUser;
	}



	public void setC3prUser(String user) {
		c3prUser = user;
	}



	public String getSystemID() {
		return systemID;
	}



	public void setSystemID(String systemID) {
		this.systemID = systemID;
	}



	public String getSystemPassword() {
		return systemPassword;
	}



	public void setSystemPassword(String systemPassword) {
		this.systemPassword = systemPassword;
	}



	public String getSystemUserName() {
		return systemUserName;
	}



	public void setSystemUserName(String systemUserName) {
		this.systemUserName = systemUserName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	
	
	


}
