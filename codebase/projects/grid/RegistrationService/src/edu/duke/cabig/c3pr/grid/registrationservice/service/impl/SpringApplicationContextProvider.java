/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.grid.registrationservice.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringApplicationContextProvider {

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		if(applicationContext==null){
			if(SpringApplicationContextProvider.class.getClassLoader().getResourceAsStream("applicationContext-grid-c3prRegistrationService.xml")!=null)
				applicationContext=new ClassPathXmlApplicationContext("applicationContext-grid-c3prRegistrationService.xml");
		}
		return applicationContext;
	}
}
