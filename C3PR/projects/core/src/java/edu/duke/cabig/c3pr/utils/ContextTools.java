package edu.duke.cabig.c3pr.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Note - this classpath pattern has a problem with ant builds. Use below format
//      "classpath*:/applicationContext-*.xml"
public class ContextTools {
    public static ApplicationContext createDeployedApplicationContext() {
        return new ClassPathXmlApplicationContext(
                        new String[] { "classpath*:edu/duke/cabig/c3pr/applicationContext-*.xml",

                        });
    }

    public static ApplicationContext createDeployedCoreApplicationContext() {
        return new ClassPathXmlApplicationContext(new String[] {
                "classpath*:edu/duke/cabig/c3pr/applicationContext-core.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-core-db.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-core-aspects.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-csm.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-config.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-esb.xml",
                "classpath*:edu/duke/cabig/c3pr/applicationContext-scheduler-quartz.xml",

        });
    }

    private ContextTools() {
    }
}