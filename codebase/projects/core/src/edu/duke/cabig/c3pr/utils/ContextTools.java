package edu.duke.cabig.c3pr.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Note - this classpath pattern has a problem with ant builds. Use below format
//public class ContextTools {
//    public static ApplicationContext createDeployedApplicationContext() {
//        return new ClassPathXmlApplicationContext(new String[] {
//            "classpath:/applicationContext.xml",
//            "classpath*:/applicationContext-*.xml"
//        });
//    }
//
//    private ContextTools() { }
//}

public class ContextTools {
    public static ApplicationContext createDeployedApplicationContext() {
    	   
    	  return new ClassPathXmlApplicationContext (new String[] {
    			 "classpath*:applicationContext.xml",
    			 "classpath*:applicationContext-core.xml", 
    			 "classpath*:applicationContext-configProperties-esb.xml",
    			 "classpath*:applicationContext-configProperties-gridSecurity.xml",    			
    	  }); 
     }

    private ContextTools() { }
}


