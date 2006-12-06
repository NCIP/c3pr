/*
 * Created on Jan 5, 2006
 */
package edu.duke.cabig.c3pr.utils;

import gov.nih.nci.security.AuthenticationManager;
import gov.nih.nci.security.AuthorizationManager;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;

/**
 * This class maintains the reference of all the system level objects and 
 * provides a static method to get a reference for each object.
 * 
 * @author Rangaraju Gadiraju
 */

public class SystemManager {

	private static AuthorizationManager authorizationManager = null;
	private static ConfigurationManager configurationManager = null;
	private static UserProvisioningManager userProvisioningManager = null;
	private static AuthenticationManager authenticationManager = null;
	private static PropertyFileManager propertyFileManager = null;
	
	/**
	 * Returns the reference of the AuthorizationManager.
	 * @return the reference of the AuthorizationManager.
	 */
	public static AuthorizationManager getAuthorizationManager()throws Exception {
		
		if(authorizationManager == null)
			setAuthorizationManager();
		
		return authorizationManager;
		
	}
	
	/**
	 * Gets the reference of the AuthorizationManger using SecuritySerivceProvider
	 * and sets the same reference to this class.
	 */
	private static void setAuthorizationManager()throws Exception {
		authorizationManager = SecurityServiceProvider.getAuthorizationManager("c3pr");
	}
	
	/**
	 * Returns the reference of the ConfigurationManager.
	 * @return the reference of the configurationManager.
	 */
	public static ConfigurationManager getConfigurationManager() {
		if(configurationManager == null)
			setConfigurationManager();
		
		return configurationManager;
	}
	
	/**
	 * Creates the instance of the ConfigurationManager and sets the same reference
	 * to this class.
	 */
	private static void setConfigurationManager() {
		configurationManager = new ConfigurationManager();
	}
	
	/**
	 * Returns the reference of the UserProvisioningManager.
	 * @return the reference of the UserProvisioningManager.
	 */
	public static UserProvisioningManager getUserProvisioningManager()throws Exception {
		
		if(userProvisioningManager == null)
			setUserProvisioningManager();
		
		return userProvisioningManager;
		
	}
	
	/**
	 * Gets the reference of the UserProvisioningManager using SecuritySerivceProvider
	 * and sets the same reference to this class.
	 */
	private static void setUserProvisioningManager()throws Exception {
		userProvisioningManager = SecurityServiceProvider.getUserProvisioningManager("c3pr");
	}
	
	/**
	 * Returns the reference of the AuthenticationManager.
	 * @return the reference of the AuthenticationManager.
	 */
	public static AuthenticationManager getAuthenticationManager()throws Exception {
		
		if(authenticationManager == null)
			setAuthenticationManager();
		
		return authenticationManager;
		
	}
	
	/**
	 * Gets the reference of the AuthenticationManager using SecuritySerivceProvider
	 * and sets the same reference to this class.
	 */
	private static void setAuthenticationManager()throws Exception {
		authenticationManager = SecurityServiceProvider.getAuthenticationManager("c3pr");
	}	
	
	/**
	 * Returns the reference of the PropertyFileManager.
	 * @return the reference of the PropertyFileManager.
	 */
	public static PropertyFileManager getPropertyFileManager() {
		
		if(propertyFileManager == null)
			setPropertyFileManager();
		
		return propertyFileManager;
		
	}
	
	/**
	 * Creates the instance of the PropertyFileManager and sets the same reference 
	 * to this class.
	 */
	private static void setPropertyFileManager(){
		propertyFileManager = new PropertyFileManager();
	}
}
