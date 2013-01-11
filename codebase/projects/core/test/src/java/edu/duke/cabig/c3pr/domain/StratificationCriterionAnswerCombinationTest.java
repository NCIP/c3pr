/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

/**
 * The Class StratificationCriterionAnswerCombinationTest.
 */
public class StratificationCriterionAnswerCombinationTest extends AbstractTestCase{
	
	/** The Constant QUESTION_1. */
	public static final String QUESTION_1 = "question 1";
	
	/** The Constant QUESTION_2. */
	public static final String QUESTION_2 = "question 2";
	
	/** The Constant ANSWER_1. */
	public static final String ANSWER_1 = "answer_1";
	
	/** The Constant ANSWER_2. */
	public static final String ANSWER_2 = "answer_2";
	
	
	/**
	 * Test stratification criterion asnwer combination equals with same question and answer.
	 * 
	 * @throws Exception the exception
	 */
	public void testStratificationCriterionAnswerCombinationEquals() throws Exception {
		StratificationCriterionAnswerCombination scac1 = new StratificationCriterionAnswerCombination();
		
		StratificationCriterion sc1 = new StratificationCriterion();
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);
		
		scac1.setStratificationCriterion(sc1);
		scac1.setStratificationCriterionPermissibleAnswer(scpa1);
		
		StratificationCriterionAnswerCombination scac2 = new StratificationCriterionAnswerCombination();
		
		StratificationCriterion sc2 = new StratificationCriterion();		
		StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
		
		sc2.setQuestionNumber(1);
		sc2.setQuestionText(QUESTION_1);
		scpa2.setPermissibleAnswer(ANSWER_1);
		sc2.getPermissibleAnswers().add(scpa2);
		
		scac2.setStratificationCriterion(sc2);
		scac2.setStratificationCriterionPermissibleAnswer(scpa2);
		
		assertTrue("The two criteria should be equal",scac1.equals(scac2));
	}

	
	/**
	 * Test stratification criterion asnwer combination equals with same object.
	 * 
	 * @throws Exception the exception
	 */
	public void testStratificationCriterionAnswerCombinationEqualsWithSameObject() throws Exception {
		StratificationCriterionAnswerCombination scac1 = new StratificationCriterionAnswerCombination();
		
		StratificationCriterion sc1 = new StratificationCriterion();
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);
		
		scac1.setStratificationCriterion(sc1);
		scac1.setStratificationCriterionPermissibleAnswer(scpa1);
		
		StratificationCriterionAnswerCombination scac2 = scac1;
		
		assertTrue("The two criteria should be equal",scac1.equals(scac2));
	}

	
	/**
	 * Test stratification criterion equals with different question.
	 * 
	 * @throws Exception the exception
	 */
	public void testStratificationCriterionEqualsWithDifferentQuestion() throws Exception {
		StratificationCriterionAnswerCombination scac1 = new StratificationCriterionAnswerCombination();
		
		StratificationCriterion sc1 = new StratificationCriterion();
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);
		
		scac1.setStratificationCriterion(sc1);
		scac1.setStratificationCriterionPermissibleAnswer(scpa1);
		
		StratificationCriterionAnswerCombination scac2 = new StratificationCriterionAnswerCombination();
		StratificationCriterion sc2 = new StratificationCriterion();		
		StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
		
		sc2.setQuestionNumber(1);
		sc2.setQuestionText(QUESTION_2);
		scpa2.setPermissibleAnswer(ANSWER_1);
		sc2.getPermissibleAnswers().add(scpa2);
		
		scac2.setStratificationCriterion(sc2);
		scac2.setStratificationCriterionPermissibleAnswer(scpa2);
		
		assertFalse("The two criteria should not be equal",scac1.equals(scac2));
	}
	
	/**
	 * Test stratification criterion equals with different answer.
	 * 
	 * @throws Exception the exception
	 */
	public void testStratificationCriterionEqualsWithDifferentAnswer() throws Exception {
		StratificationCriterionAnswerCombination scac1 = new StratificationCriterionAnswerCombination();
		
		StratificationCriterion sc1 = new StratificationCriterion();
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);
		
		scac1.setStratificationCriterion(sc1);
		scac1.setStratificationCriterionPermissibleAnswer(scpa1);
	
		StratificationCriterionAnswerCombination scac2 = new StratificationCriterionAnswerCombination();
		StratificationCriterion sc2 = new StratificationCriterion();		
		StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
		
		sc2.setQuestionNumber(1);
		sc2.setQuestionText(QUESTION_1);
		scpa2.setPermissibleAnswer(ANSWER_2);
		sc2.getPermissibleAnswers().add(scpa2);
		
		scac2.setStratificationCriterion(sc2);
		scac2.setStratificationCriterionPermissibleAnswer(scpa2);
		
		assertFalse("The two criteria should not be equal",scac1.equals(scac2));
	}
	
	public void testHashcode(){
		StratificationCriterionAnswerCombination scac1 = new StratificationCriterionAnswerCombination();
		
		StratificationCriterion sc1 = new StratificationCriterion();
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);
		
		scac1.setStratificationCriterion(sc1);
		scac1.setStratificationCriterionPermissibleAnswer(scpa1);
		
		StratificationCriterionAnswerCombination scac2 = new StratificationCriterionAnswerCombination();
		
		StratificationCriterion sc2 = new StratificationCriterion();		
		StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
		
		sc2.setQuestionNumber(1);
		sc2.setQuestionText(QUESTION_1);
		scpa2.setPermissibleAnswer(ANSWER_1);
		sc2.getPermissibleAnswers().add(scpa2);
		
		scac2.setStratificationCriterion(sc2);
		scac2.setStratificationCriterionPermissibleAnswer(scpa2);
		
		assertTrue(scac1.hashCode() == scac2.hashCode());
	}
}
