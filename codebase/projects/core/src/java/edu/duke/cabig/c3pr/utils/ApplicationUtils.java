package edu.duke.cabig.c3pr.utils;

public class ApplicationUtils 
{
  
  public static boolean isUnix()
    {
      boolean test = false;
    String str = System.getProperty("os.name");
    int i = str.indexOf("Win");
    if (i!=-1){
       test= false;
    }else
    {
      test = true;
    }
      return test;
    }
  
  public static String convertToString(String[] i_strArray, String i_delimiter){
  	String strValue = "";
  	for(int i=0; i<i_strArray.length; i++){
  		if(i != 0){
  			strValue = strValue + i_delimiter;
  		}
  		strValue = strValue + i_strArray[i];  		
  	}
  	return strValue;  	
  }
  
  public static boolean isNullorBlank(String str){
  	boolean retVal = false;
    if(str==null){
    	retVal= true;
    }else{
      if(str.equals("")){
      	retVal = true;
      }
    }
    return retVal;
  }
}