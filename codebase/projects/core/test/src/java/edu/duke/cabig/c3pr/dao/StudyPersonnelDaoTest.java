/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * The Class StudyPersonnelDaoTest.
 */
public class StudyPersonnelDaoTest extends DaoTestCase {

	/** The study personnel dao. */
	private StudyPersonnelDao studyPersonnelDao =(StudyPersonnelDao) getApplicationContext().getBean("studyPersonnelDao");;
	
	/**
	 * Test domain class.
	 */
	public void testSuperDomainClass() {
		assertEquals("Wrong Domain Class", StudyPersonnel.class, studyPersonnelDao.domainClass());
	}
	
	public void testGetBySubnamesWithHealthcareSite() {
		List<StudyPersonnel> studyPersonnel = new ArrayList<StudyPersonnel>();
		studyPersonnel = studyPersonnelDao.getBySubnames(new String[]{"RE"}, 1000);
		assertEquals("Wrong number of study personnel", 2, studyPersonnel.size());
	}
	
	public void testGetBySubnamesWithoutHealthcareSite1() {
		List<StudyPersonnel> studyPersonnel = new ArrayList<StudyPersonnel>();
		studyPersonnel = studyPersonnelDao.getBySubnames(new String[]{"Staff4"});
		assertEquals("Un expected study person with this name", 0, studyPersonnel.size());
	}
	
	
	public void testGetBySubnamesWithoutHealthcareSite2() {
		List<StudyPersonnel> studyPersonnel = new ArrayList<StudyPersonnel>();
		studyPersonnel = studyPersonnelDao.getBySubnames(new String[]{"Staff2"});
		assertEquals("Wrong number of study persons with this name", 1, studyPersonnel.size());
	}
	
	public void testGetByExample() {
		String sitePrimaryIdentifiers = "code";
		String studyPrimaryId = "grid";
		Integer personUserId = 1000;
		String roleCode = "role1";
		
		List<StudyPersonnel> studyPersonnel = studyPersonnelDao.getByExample(sitePrimaryIdentifiers, studyPrimaryId, personUserId, roleCode);
		assertEquals("Wrong number of study persons with these values", 1, studyPersonnel.size());
	}	
		
}
