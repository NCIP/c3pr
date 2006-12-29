package edu.duke.cabig.c3pr.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Priyatam
 */
public class ContextTools {
    public static ApplicationContext createDeployedApplicationContext() {
    	  return new ClassPathXmlApplicationContext (new String[] {
			    "classpath:/applicationContext.xml",
	            "classpath*:/applicationContext-*.xml"
    }); 
     }

    private ContextTools() { }
}
