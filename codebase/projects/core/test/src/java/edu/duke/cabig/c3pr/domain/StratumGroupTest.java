package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.AbstractTestCase;

/**
 * The Class StratumGroupTest.
 */
public class StratumGroupTest extends AbstractTestCase{
	
	/** The Constant ANSWER_1. */
	public static final String ANSWER_1 = "answer_1";
	
	/** The Constant ANSWER_2. */
	public static final String ANSWER_2 = "answer_2";
	

	/**
	 * Test set retired indicator as true.
	 */
	public void testSetRetiredIndicatorAsTrue(){
		StratumGroup stratumGroup = new StratumGroup();
		StratificationCriterionAnswerCombination scac = new StratificationCriterionAnswerCombination();
		
		stratumGroup.getStratificationCriterionAnswerCombination().add(scac);
		
		stratumGroup.setRetiredIndicatorAsTrue();
		assertEquals("true", stratumGroup.getStratificationCriterionAnswerCombination().get(0).getRetiredIndicator());
	}
	
	
	/**
	 * Test get answer combinations.
	 */
	public void testGetAnswerCombinations(){
		StratificationCriterionAnswerCombination scac1 = new StratificationCriterionAnswerCombination();
		
		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		scpa1.setPermissibleAnswer(ANSWER_1);
		scac1.setStratificationCriterionPermissibleAnswer(scpa1);
		
		StratumGroup stratumGroup = new StratumGroup();
		stratumGroup.getStratificationCriterionAnswerCombination().add(scac1);
		
		assertTrue(stratumGroup.getAnswerCombinations().contains(ANSWER_1));
	}
	
	
	/**
	 * Test get next arm.
	 */
	public void testGetNextArm(){
		StratumGroup stratumGroup = new StratumGroup();
		
		BookRandomizationEntry bookRandomizationEntry = new BookRandomizationEntry();
		Arm arm = new Arm();
		arm.setName("Arm A");
		bookRandomizationEntry.setArm(arm);
		bookRandomizationEntry.setPosition(0);
		bookRandomizationEntry.setStratumGroup(stratumGroup);
		
		BookRandomizationEntry bre = new BookRandomizationEntry();
		Arm a = new Arm();
		a.setName("Arm A");
		bre.setArm(a);
		bre.setPosition(1);
		bre.setStratumGroup(stratumGroup);
		
		stratumGroup.getBookRandomizationEntry().add(bookRandomizationEntry);
		stratumGroup.getBookRandomizationEntry().add(bre);
		
		try{
			assertEquals(arm, stratumGroup.getNextArm());
		} catch(Exception e){
			fail();
		}
	}
	

	/**
	 * Test get next arm exception.
	 */
	public void testGetNextArmException(){
		StratumGroup stratumGroup = new StratumGroup();
		try{
			stratumGroup.getNextArm();
			fail();
		} catch(Exception e){
			
		}
	}
	
	/**
	 * Test clone.
	 */
	public void testClone(){
		
		StratificationCriterionPermissibleAnswer scpa = new StratificationCriterionPermissibleAnswer();
		scpa.setPermissibleAnswer(ANSWER_1);
		
		StratificationCriterionAnswerCombination scac = new StratificationCriterionAnswerCombination();
		scac.setStratificationCriterionPermissibleAnswer(scpa);
		
		StratumGroup stratumGroup = new StratumGroup();
		stratumGroup.getStratificationCriterionAnswerCombination().add(scac);
		stratumGroup.setStratumGroupNumber(0);
		
		StratumGroup stratumGroupClone = stratumGroup.clone();
		
		assertEquals(ANSWER_1, 
				stratumGroupClone.getStratificationCriterionAnswerCombination().get(0).getStratificationCriterionPermissibleAnswer().getPermissibleAnswer());
		assertEquals(0, stratumGroupClone.getStratumGroupNumber().intValue());
	}

	
	/**
	 * Test compare to greater group.
	 */
	public void testCompareToGreaterGroup(){
		StratumGroup stratumGroupZero = new StratumGroup();
		stratumGroupZero.setStratumGroupNumber(new Integer(0));
		
		StratumGroup stratumGroupOne = new StratumGroup();
		stratumGroupOne.setStratumGroupNumber(new Integer(1));
		
		assertEquals(-1, stratumGroupZero.compareTo(stratumGroupOne));
	}
	
	/**
	 * Test compare to lesser group.
	 */
	public void testCompareToLesserGroup(){
		StratumGroup stratumGroupZero = new StratumGroup();
		stratumGroupZero.setStratumGroupNumber(new Integer(0));
		
		StratumGroup stratumGroupOne = new StratumGroup();
		stratumGroupOne.setStratumGroupNumber(new Integer(1));
		
		assertEquals(1, stratumGroupOne.compareTo(stratumGroupZero));
	}
	
	/**
	 * Test compare to equal.
	 */
	public void testCompareToEqual(){
		StratumGroup stratumGroupZero = new StratumGroup();
		stratumGroupZero.setStratumGroupNumber(new Integer(0));
		
		StratumGroup stratumGroupAlsoZero = new StratumGroup();
		stratumGroupAlsoZero.setStratumGroupNumber(new Integer(0));
		
		assertEquals(0, stratumGroupAlsoZero.compareTo(stratumGroupZero));
	}
}






