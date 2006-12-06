/**
 * 
 */
package edu.duke.cabig.c3pr.utils.security;


import java.util.List;
import java.util.Map;

import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * 
 * @author Priyatam
 *
 */
public class User {
	/**
	 * The unique id of the user in the csm user table
	 */
	private String userID;
	
	/**
	 * The login name of the user
	 */
	private String loginName;
	
	/**
	 * The first name of the user
	 */
	private String firstName;
	
	/**
	 * The last name of the user
	 */	
	private String lastName;
	
	/**
	 * Each user can have only one group, this group will be used as role in the C3PR
	 * application
	 */
	private String group;
	
	/**
	 * Contains
	 * Key: The type of the protection element as a String
	 * Value: The list of protection elements object id 
	 */
	private java.util.Map protectionElementsMap;

	//Constants for Protection Elements Type
	public static final String PROTECTION_ELEMENT_TYPE_PROTOCOL = "PROTOCOL";
	
	public User(){

	}

	/**
	 * Returns the first name of the user
	 * @return String, the first name of the user
	 */
	public String getFirstName(){
		return firstName;
	}

	/**
	 * Returns the group of the user
	 * @return String, the group of the user
	 */
	public String getGroup(){
		return group;
	}

	/**
	 * Returns the last name of the user
	 * @return String, the last name of the user
	 */
	public String getLastName(){
		return lastName;
	}

	/**
	 * Returns the login name of the user
	 * @return String, the login name of the user
	 */
	public String getLoginName(){
		return loginName;
	}

	/**
	 * Returns the user id of the user, which is a unique id of the user table
	 * @return String, the user id of the user
	 */
	public String getUserID(){
		return userID;
	}

	/**
	 * Sets the first name of the user to this object with the given vlaue
	 * @param i_firstName, the first name of the user to be set to this object
	 */
	public void setFirstName(String i_firstName){
		firstName = i_firstName;
	}

	/**
	 * Sets the group of the user to this object with the given vlaue
	 * @param i_group, the group of the user to be set to this object
	 */
	public void setGroup(String i_group){
		group = i_group;
	}

	/**
	 * Sets the last name of the user to this object with the given vlaue
	 * @param i_lastName, the last name of the user to be set to this object
	 */
	public void setLastName(String i_lastName){
		lastName = i_lastName;
	}

	/**
	 * Sets the login name of the user to this object with the given vlaue
	 * @param i_loginName, the login name of the user to be set to this object
	 */
	public void setLoginName(String i_loginName){
		loginName = i_loginName;
	}

	/**
	 * Sets the user id of the user to this object with the given vlaue
	 * @param i_userID, the user id of the user to be set to this object
	 */
	public void setUserID(String i_userId){
		userID = i_userId;
	}

	/**
	 * Returns the list of protection element objectid's for the given type
	 * @param type, the type of the protection elements
	 * @return String, the list of protection element objectid's with comma seperated
	 */
	public String getProtectionElements(String type){
		List protectionElementsLst = (List)protectionElementsMap.get(type);
		if(protectionElementsLst != null){
			if(protectionElementsLst.contains("All.Protocols")){
				return "All.Protocols";
			}else{
				return StringUtils.removeFirstAndLastCharacters(protectionElementsLst.toString());
			}
		}else{
			return "";
		}
	}

	/**
	 * Sets the ProtectionElements Map of the user to this object with the given map
	 * @param i_protectionElementsMap, the protection elements map of the user to be set to this object
	 */
	public void setProtectionElements(Map i_protectionElementsMap){
		protectionElementsMap = i_protectionElementsMap;
	}
	
}