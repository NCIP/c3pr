/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.domain.StudyOrganization;

public class StudyOrganizationDaoTest extends TestCase {
	private StudyOrganizationDao studyOrganizationDao = new StudyOrganizationDao();
	
	public void  testGetDoaminClass(){
		assertEquals("Wrong domain class",StudyOrganization.class,studyOrganizationDao.domainClass());
	}

}
