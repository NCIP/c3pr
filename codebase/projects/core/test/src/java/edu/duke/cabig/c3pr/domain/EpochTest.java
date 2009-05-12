package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

// TODO: Auto-generated Javadoc
/**
 * The Class EpochTest.
 */
public class EpochTest extends TestCase{
	
	/**
	 * Test create epoch with arms.
	 * 
	 * @throws Exception the exception
	 */
	public void testCreateEpochWithArms() throws Exception{
		
		Epoch epoch = Epoch.createEpochWithArms("treatment", new String[0]);
		
		assertEquals("Wrong number of arms",1,epoch.getArms().size());
		assertEquals("Wrong arm name in epoch","treatment",epoch.getArms().get(0).getName());
		
	}
	
	/**
	 * Test compare to.
	 * 
	 * @throws Exception the exception
	 */
	public void testCompareTo() throws Exception{
		
		Epoch epoch1 = new Epoch();
		epoch1.setName("epochA");
		
		Epoch epoch2 = new Epoch();
		epoch2.setName("epochB");
		
		Epoch epoch3 = new Epoch();
		epoch3.setName("epochA");
		
		assertEquals("The 2 epochs cannot be same",1, epoch1.compareTo(epoch2));
		assertEquals("The 2 epochs should have been same",0, epoch1.compareTo(epoch3));
	}
	
	/**
	 * Test equals.
	 * 
	 * @throws Exception the exception
	 */
	public void testEquals() throws Exception{
		Epoch epoch1 = new Epoch();
		
		Epoch epoch2 = new Epoch();
		epoch2.setName("epochB");
		
		assertFalse("The two epochs cannot be equal",epoch1.equals(epoch2));
	}
	
	/**
	 * Test is multiple arms.
	 * 
	 * @throws Exception the exception
	 */
	public void testIsMultipleArms() throws Exception{
		Epoch epoch = Epoch.createEpochWithArms("epochA", new String[]{"Arm A","Arm B"});
		assertTrue("The epoch should have multiple arms",epoch.isMultipleArms());
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
	 * Test evaluate randomization status.
	 * 
	 * @throws Exception the exception
	 */
	public void testEvaluateRandomizationStatus() throws Exception{
		
		Study study = new Study();
		study.setRandomizedIndicator(true);
		study.setRandomizationType(RandomizationType.BOOK);
		Epoch epoch = new Epoch();
		epoch.setName("epoch");
		
		study.addEpoch(epoch);
		epoch.setRandomizedIndicator(true);
		epoch.setRandomization(new BookRandomization());
		List<Error> errors = new ArrayList<Error>();
		epoch.evaluateRandomizationDataEntryStatus(errors);
		assertEquals("Wrong number of error messages",2,errors.size());
	}
	
	/**
	 * Test get arm by name.
	 * 
	 * @throws Exception the exception
	 */
	public void testGetArmByName() throws Exception{
		Epoch epoch = Epoch.createEpochWithArms("epochA", new String[]{"Arm A","Arm B","Arm C"});
		assertEquals("Wrong arm","Arm C",epoch.getArmByName("Arm C").getName());
		
		Epoch epochA = new Epoch();
		assertNull("Unexpected arm",epochA.getArmByName("Arm A"));
	}
	
	/**
	 * Test evaluate call out randomization errors.
	 * 
	 * @throws Exception the exception
	 */
	public void testEvaluateCallOutRandomizationErrors() throws Exception{
		
		Study study = new Study();
		study.setRandomizedIndicator(true);
		study.setRandomizationType(RandomizationType.CALL_OUT);
		Epoch epoch = new Epoch();
		epoch.setName("epoch");
		
		study.addEpoch(epoch);
		epoch.setRandomizedIndicator(true);
		epoch.setRandomization(new CalloutRandomization());
		List<Error> errors = new ArrayList<Error>();
		epoch.evaluateRandomizationDataEntryStatus(errors);
		assertEquals("Wrong number of error messages",2,errors.size());
	}

}
