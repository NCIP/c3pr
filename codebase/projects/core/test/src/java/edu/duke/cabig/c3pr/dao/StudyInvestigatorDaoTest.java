/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import junit.framework.TestCase;

/**
 * The Class StudyInvestigatorDaoTest.
 */
public class StudyInvestigatorDaoTest extends TestCase {

	/** The study investigator dao. */
	private StudyInvestigatorDao studyInvestigatorDao= new StudyInvestigatorDao();
	
	/**
	 * Test domain class.
	 */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", StudyInvestigator.class, studyInvestigatorDao.domainClass());
	}
}
