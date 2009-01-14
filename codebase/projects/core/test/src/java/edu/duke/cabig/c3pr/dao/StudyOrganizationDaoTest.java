package edu.duke.cabig.c3pr.dao;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.domain.StudyOrganization;

public class StudyOrganizationDaoTest extends TestCase {
	private StudyOrganizationDao studyOrganizationDao = new StudyOrganizationDao();
	
	public void  testGetDoaminClass(){
		assertEquals("Wrong domain class",StudyOrganization.class,studyOrganizationDao.domainClass());
	}

}
