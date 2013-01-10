/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import junit.framework.TestCase;

/**
 * The Class StratificationCriterionAnswerDaoTest.
 */
public class StratificationCriterionAnswerDaoTest extends TestCase {

	/** The stratification criterion answer dao. */
	private StratificationCriterionAnswerDao stratificationCriterionAnswerDao= new StratificationCriterionAnswerDao();
	
	/**
	 * Test domain class.
	 */
	public void testDomainClass() {
		assertEquals("Wrong Domain Class", StratificationCriterionPermissibleAnswer.class, stratificationCriterionAnswerDao.domainClass());
	}
	
}
