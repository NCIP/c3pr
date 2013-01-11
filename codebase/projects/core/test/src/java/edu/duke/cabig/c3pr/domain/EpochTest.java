/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;

/**
 * The Class EpochTest.
 */
public class EpochTest extends TestCase{

	/** The Constant QUESTION_1. */
	public static final String QUESTION_1 = "question 1";

	/** The Constant QUESTION_2. */
	public static final String QUESTION_2 = "question 2";

	/** The Constant ANSWER_1. */
	public static final String ANSWER_1 = "answer_1";

	/** The Constant ANSWER_2. */
	public static final String ANSWER_2 = "answer_2";

	StudyCreationHelper studyCreationHelper = new StudyCreationHelper();
	/**
	 * Test equals.
	 *
	 * @throws Exception the exception
	 */
	public void testEquals1() throws Exception{
		Epoch epoch1 = new Epoch();

		Epoch epoch2 = new Epoch();
		epoch2.setName("epochB");

		assertFalse("The two epochs cannot be equal",epoch1.equals(epoch2));
	}

	public void testEquals2() throws Exception{
		Epoch epoch1 = new Epoch();

		Epoch epoch2 = new Epoch();

		assertTrue("The two epochs have be equal",epoch1.equals(epoch2));
	}

	/**
	 * Test is multiple arms.
	 *
	 * @throws Exception the exception
	 */
	public void testIsMultipleArms() throws Exception{
		Epoch epoch = studyCreationHelper.createEpochWithArms("epochA", new String[]{"Arm A","Arm B"});
		assertTrue("The epoch should have multiple arms",epoch.isMultipleArms());
	}

	public void testIsMultipleArmsWhenNoArmsPresent() throws Exception{
		Epoch epoch =new Epoch();
		assertFalse("The epoch cannot have multiple arms",epoch.isMultipleArms());
	}

	/**
	 * Test set arms.
	 *
	 * @throws Exception the exception
	 */
	public void testSetArms() throws Exception{
		Epoch epoch = new Epoch();
		Arm armA = new Arm();
		Arm armB = new Arm();
		armB.setName("armB");
		List<Arm> arms = new ArrayList<Arm>();
		arms.add(armA);arms.add(armB);
		epoch.setArms(arms);

		assertEquals("Wrong number of arms",2,epoch.getArms().size());
		assertEquals("Wrong arm",armB, epoch.getArms().get(1));
	}


	/**
	 * Test has book randomization entry.
	 *
	 * @throws Exception the exception
	 */
	public void testHasBookRandomizationEntry() throws Exception{
		Epoch epoch = new Epoch();
		assertFalse("There should not have been any book randomization entries",epoch.hasBookRandomizationEntry());
	}

	/**
	 * Test get stratum group by number.
	 *
	 * @throws Exception the exception
	 */
	public void testGetStratumGroupByNumber() throws Exception{
		StratumGroup sg1 = new StratumGroup();
		sg1.setStratumGroupNumber(new Integer(1));

		StratumGroup sg2 = new StratumGroup();
		sg2.setStratumGroupNumber(new Integer(2));

		StratumGroup sg3 = new StratumGroup();
		sg3.setStratumGroupNumber(new Integer(3));

		List<StratumGroup> stratumGroups = new ArrayList<StratumGroup>();
		stratumGroups.add(sg1);stratumGroups.add(sg2);stratumGroups.add(sg3);

		Epoch epoch1 = new Epoch();

		epoch1.setStratumGroupsInternal(stratumGroups);

		assertEquals("Wrong stratum group",sg2,epoch1.getStratumGroupByNumber(new Integer(2)));

		Epoch epoch2 = new Epoch();
		assertNull("Should have returned null",epoch2.getStratumGroupByNumber(new Integer(1)));

	}

	/**
	 * Test has eligibility.
	 *
	 * @throws Exception the exception
	 */
	public void testHasEligibility() throws Exception{

		Epoch epoch1 = new Epoch();
		assertFalse("The epoch should not have had eligibility criteria", epoch1.hasEligibility());
		EligibilityCriteria eligCritera1 = new InclusionEligibilityCriteria();
		epoch1.addEligibilityCriterion(eligCritera1);
		assertTrue("Theepoch should have had eligibility criteria",epoch1.hasEligibility());

	}

	/**
	 * Test get arm by name.
	 *
	 * @throws Exception the exception
	 */
	public void testGetArmByName() throws Exception{
		Epoch epoch = studyCreationHelper.createEpochWithArms("epochA", new String[]{"Arm A","Arm B","Arm C"});
		assertEquals("Wrong arm","Arm C",epoch.getArmByName("Arm C").getName());

		Epoch epochA = new Epoch();
		assertNull("Unexpected arm",epochA.getArmByName("Arm A"));
	}


	/**
	 * Test generate stratum groups. This calls the combinationGenerator internally.
	 */
	public void testGenerateStratumGroups(){
		Epoch epoch = new Epoch();

		StratificationCriterion sc1 = new StratificationCriterion();
		sc1.setQuestionNumber(1);
		sc1.setQuestionText(QUESTION_1);

		StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
		scpa1.setPermissibleAnswer(ANSWER_1);
		sc1.getPermissibleAnswers().add(scpa1);

		StratificationCriterion sc2 = new StratificationCriterion();
		sc2.setQuestionNumber(1);
		sc2.setQuestionText(QUESTION_1);

		StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
		scpa2.setPermissibleAnswer(ANSWER_1);
		sc2.getPermissibleAnswers().add(scpa2);
		sc2.getPermissibleAnswers().add(scpa1);

		sc1.getPermissibleAnswers().add(scpa2);

		epoch.getStratificationCriteria().add(sc1);
		epoch.getStratificationCriteria().add(sc2);

		epoch.generateStratumGroups();
		assertEquals(4, epoch.getStratumGroups().size());

	}


}



