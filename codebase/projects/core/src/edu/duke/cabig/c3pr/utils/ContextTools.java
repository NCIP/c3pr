package edu.duke.cabig.c3pr.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Note - this classpath pattern has a problem with ant builds. Use below format
//      "classpath*:/applicationContext-*.xml"
public class ContextTools {
    public static ApplicationContext createDeployedApplicationContext() {
    	  return new ClassPathXmlApplicationContext (new String[] {
    			 "classpath*:context/applicationContext-core.xml", 
    			 "classpath*:context/applicationContext-configProperties.xml",
    			 "classpath*:context/applicationContext-esb.xml",
    			 "classpath*:context/applicationContext-web.xml",
    			 "classpath*:context/applicationContext-web-sso.xml"
    			 
    	  }); 
     }
       
    public static ApplicationContext createDeployedCoreApplicationContext() {
  	  return new ClassPathXmlApplicationContext (new String[] {
  			 "classpath*:context/applicationContext-esb.xml",
  			 "classpath*:context/applicationContext-core.xml"
  	  }); 
   }

    private ContextTools() { }
}
