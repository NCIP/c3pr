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
	
	public void testGetBySubnames() {
		List<StudyPersonnel> studyPersonnel = new ArrayList<StudyPersonnel>();
		studyPersonnel = studyPersonnelDao.getBySubnames(new String[]{"RE"}, 1000);
		assertEquals("Wrong number of study personnel", 2, studyPersonnel.size());
	}
}
