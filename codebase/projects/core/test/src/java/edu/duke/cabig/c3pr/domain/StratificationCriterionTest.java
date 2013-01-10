/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

/**
 * The Class StratificationCriterionTest.
 */
public class StratificationCriterionTest extends AbstractTestCase{
	
	/** The Constant QUESTION_1. */
	public static final String QUESTION_1 = "question 1";
	
	/** The Constant QUESTION_2. */
	public static final String QUESTION_2 = "question 2";
	
	/** The Constant ANSWER_1. */
	public static final String ANSWER_1 = "answer_1";
	
	/** The Constant ANSWER_2. */
	public static final String ANSWER_2 = "answer_2";
	
	/* (non-Javadoc)
	 * @see edu.nwu.bioinformatics.commons.testing.CoreTestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Test stratification criterion equals with same question and answer.
	 * 
	 * @throws Exception the exception
	 */
	public void testStratificationCriterionEquals() throws Exception {
		StratificationCriterion sc1 = new StratificationCriterion();
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);
		
		StratificationCriterion sc2 = new StratificationCriterion();		
		StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
		
		sc2.setQuestionNumber(1);
		sc2.setQuestionText(QUESTION_1);
		scpa2.setPermissibleAnswer(ANSWER_1);
		sc2.getPermissibleAnswers().add(scpa2);
		
		assertTrue("The two criteria should be equal",sc1.equals(sc2));
	}

	
	/**
	 * Test stratification criterion equals with different question.
	 * 
	 * @throws Exception the exception
	 */
	public void testStratificationCriterionEqualsWithDifferentQuestion() throws Exception {
		StratificationCriterion sc1 = new StratificationCriterion();
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);
		
		StratificationCriterion sc2 = new StratificationCriterion();		
		StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
		
		sc2.setQuestionNumber(1);
		sc2.setQuestionText(QUESTION_2);
		scpa2.setPermissibleAnswer(ANSWER_1);
		sc2.getPermissibleAnswers().add(scpa2);
		
		assertFalse("The two criteria should not be equal",sc1.equals(sc2));
	}
	
	/**
	 * Test stratification criterion equals with different answer.
	 * 
	 * @throws Exception the exception
	 */
	public void testStratificationCriterionEqualsWithDifferentAnswer() throws Exception {
		StratificationCriterion sc1 = new StratificationCriterion();
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);
		
		StratificationCriterion sc2 = new StratificationCriterion();		
		StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
		
		sc2.setQuestionNumber(1);
		sc2.setQuestionText(QUESTION_1);
		scpa2.setPermissibleAnswer(ANSWER_2);
		sc2.getPermissibleAnswers().add(scpa2);
		
		assertFalse("The two criteria should not be equal",sc1.equals(sc2));
	}
	
	/**
	 * Test stratification criterion equals with different answer.
	 * 
	 * @throws Exception the exception
	 */
	public void testRemovePermissibleAnswer() throws Exception {
		StratificationCriterion sc1 = new StratificationCriterion();
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);
		
		sc1.removePermissibleAnswer(scpa1);
		
		assertTrue("The two criteria should not be equal",sc1.getPermissibleAnswers().size() == 0);
	}
	
}
