/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import junit.framework.TestCase;

public class EligibilityCriteriaTestCase extends TestCase {

	public void testEquals(){
		EligibilityCriteria eligibilityCriteria1 = new InclusionEligibilityCriteria();
		EligibilityCriteria eligibilityCriteria2 = eligibilityCriteria1;
		assertTrue(eligibilityCriteria1.equals(eligibilityCriteria2));
		eligibilityCriteria2 = new ExclusionEligibilityCriteria();
		assertFalse(eligibilityCriteria1.equals(eligibilityCriteria2));
		eligibilityCriteria2 = new InclusionEligibilityCriteria();
		assertFalse(eligibilityCriteria1.equals(eligibilityCriteria2));
		eligibilityCriteria1.setQuestionText("ABC");
		eligibilityCriteria2.setQuestionText("DEF");
		assertFalse(eligibilityCriteria1.equals(eligibilityCriteria2));
		eligibilityCriteria2.setQuestionText("ABC");
		assertTrue(eligibilityCriteria1.equals(eligibilityCriteria2));
	}
}
