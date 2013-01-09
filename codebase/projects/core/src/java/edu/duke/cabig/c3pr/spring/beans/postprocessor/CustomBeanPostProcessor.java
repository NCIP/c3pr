/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.spring.beans.postprocessor;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.factory.ObjectFactoryOracle;
import edu.duke.cabig.c3pr.domain.factory.ObjectFactoryPostgres;

public class CustomBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware{
	
	ApplicationContext applicationContext;
	
	private BasicDataSource dataSource = null;
	

	public Object postProcessAfterInitialization(Object arg0, String arg1)
			throws BeansException {
		return arg0;
	}

	public Object postProcessBeforeInitialization(Object arg0, String arg1)
			throws BeansException {
		if(arg0 instanceof StudySubjectDao){
		dataSource = (BasicDataSource) applicationContext.getBean("dataSource");
			if(dataSource.getDriverClassName().toLowerCase().contains("oracle")){
				((StudySubjectDao) arg0).setObjectFactory(new ObjectFactoryOracle());
			} else if(dataSource.getDriverClassName().toLowerCase().contains("postgres")){
					((StudySubjectDao) arg0).setObjectFactory(new ObjectFactoryPostgres());
			} 
		}
		return arg0;
	}

	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.applicationContext = arg0;
		
	}

}
