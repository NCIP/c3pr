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
