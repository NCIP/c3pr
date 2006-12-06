package edu.duke.cabig.c3pr.utils;


import java.util.Properties;

/**
 * This class loads all properties from the c3pr.properties file into the cache.
 * 
 * @author Rangaraju Gadiraju
 */
public class PropertyFileManager {
	
	//Define all the keys which are added to c3pr.properties 
	public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
	public static final String MULTIPLE_ROLES = "MULTIPLE_ROLES";
	public static final String NO_ROLE = "NO_ROLE";
	public static final String UNABLE_TO_LOGIN = "UNABLE_TO_LOGIN";
	
	Properties properties = null; 
	
	public PropertyFileManager(){
		
	}
	
	/**
	 * Returns the value for the given key.
	 * @param i_key, the key for which the value to be returned
	 * @return String, the value for the given key
	 */
	public String getValue(String i_key){
		
		String value = "";
		try{
			if(properties == null){
				properties = new Properties();
				properties.load(this.getClass().getClassLoader().getResourceAsStream("c3pr.properties"));
			}
		}catch(Exception e){
			LogWriter.getInstance().log(LogWriter.SEVERE, e.getMessage(), e);
		}
		
		if(properties.getProperty(i_key) != null){
			value = properties.getProperty(i_key);
		}
		
		return value;	    
		
	}
	
 


}
