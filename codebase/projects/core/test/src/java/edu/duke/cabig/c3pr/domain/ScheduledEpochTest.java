/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.constants.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;

/**
 * The Class ScheduledEpochTest.
 */
public class ScheduledEpochTest extends AbstractTestCase {

	private ScheduledEpoch scheduledEpoch;
	
	private Epoch epoch;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		scheduledEpoch= new ScheduledEpoch();
		epoch= registerMockFor(Epoch.class);
		scheduledEpoch.setEpoch(epoch);
	}
	
	/**
	 * Test get stratum group.
	 * stratumGroupNumber: null
	 * subjectStratificationAnswers: not null
	 */
	public void testGetStratumGroupNullStratumGroupNumber(){
		EasyMock.expect(epoch.getStratumGroupForAnsCombination(EasyMock.isA(List.class))).andReturn(null);
		replayMocks();
		try {
			scheduledEpoch.getStratumGroup();
		} catch (C3PRBaseException e) {
			assertEquals("No stratum group found. Maybe the answer combination does not have a valid startum group", e.getMessage());
			return;
		}catch (Exception e){
			e.printStackTrace();
			fail("wrong error");
		}
		finally{
			verifyMocks();
		}
		fail("Should have failed");
	}
	
	/**
	 * Test get stratum group.
	 */
	public void testGetStratumGroup(){
		StratumGroup stratumGroup= new StratumGroup();
		stratumGroup.setId(1);
		EasyMock.expect(epoch.getStratumGroupForAnsCombination(EasyMock.isA(List.class))).andReturn(stratumGroup);
		replayMocks();
		try {
			stratumGroup= scheduledEpoch.getStratumGroup();
		}catch (Exception e){
			e.printStackTrace();
			fail("Should'nt have failed");
		}
		finally{
			verifyMocks();
		}
		assertEquals(1, stratumGroup.getId().intValue());
	}
	
	/**
	 * Test compare to.
	 */
	public void testCompareTo(){
		ScheduledEpoch scheduledEpoch1= new ScheduledEpoch();
		Epoch epoch1 = new Epoch();
		epoch1.setEpochOrder(1);
		scheduledEpoch1.setEpoch(epoch1);
		scheduledEpoch1.setStartDate(new Date());
		scheduledEpoch1.setId(1);
		ScheduledEpoch scheduledEpoch2= new ScheduledEpoch();
		Epoch epoch2 = new Epoch();
		epoch2.setEpochOrder(2);
		scheduledEpoch2.setEpoch(epoch2);
		scheduledEpoch2.setStartDate((new GregorianCalendar(1990, 1, 2)).getTime());
		scheduledEpoch2.setId(2);
		assertEquals(-1, scheduledEpoch1.compareTo(scheduledEpoch2));
		List<ScheduledEpoch> schList= new ArrayList<ScheduledEpoch>();
		schList.add(scheduledEpoch1);
		schList.add(scheduledEpoch2);
		Collections.sort(schList);
		assertEquals(1, schList.get(0).getId().intValue());
	}
	
	/**
	 * Test get requires arm.
	 * epoch.getRequiresArm: false
	 */
	public void testGetRequiresArm1(){
		EasyMock.expect(epoch.getRequiresArm()).andReturn(false).times(2);
		replayMocks();
		assertEquals(false, scheduledEpoch.getRequiresArm());
		verifyMocks();
	}
	
	/**
	 * Test get requires arm.
	 * epoch.getRequiresArm: true
	 */
	public void testGetRequiresArm2(){
		EasyMock.expect(epoch.getRequiresArm()).andReturn(true).times(2);
		replayMocks();
		assertEquals(true, scheduledEpoch.getRequiresArm());
		verifyMocks();
	}
	
	/**
	 * Test get requires arm.
	 * epoch.getRequiresArm: null
	 */
	public void testGetRequiresArmNull(){
		EasyMock.expect(epoch.getRequiresArm()).andReturn(null);
		replayMocks();
		assertEquals(false, scheduledEpoch.getRequiresArm());
		verifyMocks();
	}
	
	/**
	 * Test is reserving.
	 * epoch.isReserving: false
	 */
	public void testIsReserving1(){
		EasyMock.expect(epoch.getType()).andReturn(EpochType.TREATMENT);
		replayMocks();
		assertEquals(false, scheduledEpoch.isReserving());
		verifyMocks();
	}
	
	/**
	 * Test is reserving.
	 * epoch.isReserving: true
	 */
	public void testIsReserving2(){
		EasyMock.expect(epoch.getType()).andReturn(EpochType.RESERVING);
		replayMocks();
		assertEquals(true, scheduledEpoch.isReserving());
		verifyMocks();
	}
	
	/**
	 * Test get requires randomization.
	 * epoch.getRandomizedIndicator: false
	 */
	public void testGetRequiresRandomization1(){
		EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(false);
		replayMocks();
		assertEquals(false, scheduledEpoch.getRequiresRandomization().booleanValue());
		verifyMocks();
	}
	
	/**
	 * Test get requires randomization.
	 * epoch.getRandomizedIndicator: true
	 */
	public void testGetRequiresRandomization2(){
		EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(true);
		replayMocks();
		assertEquals(true, scheduledEpoch.getRequiresRandomization().booleanValue());
		verifyMocks();
	}
	
	/**
	 * Test get requires randomization.
	 * epoch.getRandomizedIndicator: null
	 */
	public void testGetRequiresRandomization3(){
		EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(null);
		replayMocks();
		assertNull(scheduledEpoch.getRequiresRandomization());
		verifyMocks();
	}
	
	/**
	 * Test get inclusion eligibility answers.
	 */
	public void testGetInclusionEligibilityAnswers(){
		List<SubjectEligibilityAnswer> suList= scheduledEpoch.getSubjectEligibilityAnswers();
		SubjectEligibilityAnswer subjectEligibilityAnswer= new SubjectEligibilityAnswer();
		subjectEligibilityAnswer.setEligibilityCriteria(new InclusionEligibilityCriteria());
		suList.add(subjectEligibilityAnswer);
		subjectEligibilityAnswer= new SubjectEligibilityAnswer();
		subjectEligibilityAnswer.setEligibilityCriteria(new ExclusionEligibilityCriteria());
		suList.add(subjectEligibilityAnswer);
		subjectEligibilityAnswer= new SubjectEligibilityAnswer();
		subjectEligibilityAnswer.setEligibilityCriteria(new ExclusionEligibilityCriteria());
		suList.add(subjectEligibilityAnswer);
		subjectEligibilityAnswer= new SubjectEligibilityAnswer();
		subjectEligibilityAnswer.setEligibilityCriteria(new InclusionEligibilityCriteria());
		suList.add(subjectEligibilityAnswer);
		assertEquals(2, scheduledEpoch.getInclusionEligibilityAnswers().size());
	}
	
	/**
	 * Test get exclusion eligibility answers.
	 */
	public void testGetExclusionEligibilityAnswers(){
		List<SubjectEligibilityAnswer> suList= scheduledEpoch.getSubjectEligibilityAnswers();
		SubjectEligibilityAnswer subjectEligibilityAnswer= new SubjectEligibilityAnswer();
		subjectEligibilityAnswer.setEligibilityCriteria(new InclusionEligibilityCriteria());
		suList.add(subjectEligibilityAnswer);
		subjectEligibilityAnswer= new SubjectEligibilityAnswer();
		subjectEligibilityAnswer.setEligibilityCriteria(new ExclusionEligibilityCriteria());
		suList.add(subjectEligibilityAnswer);
		subjectEligibilityAnswer= new SubjectEligibilityAnswer();
		subjectEligibilityAnswer.setEligibilityCriteria(new ExclusionEligibilityCriteria());
		suList.add(subjectEligibilityAnswer);
		subjectEligibilityAnswer= new SubjectEligibilityAnswer();
		subjectEligibilityAnswer.setEligibilityCriteria(new InclusionEligibilityCriteria());
		suList.add(subjectEligibilityAnswer);
		assertEquals(2, scheduledEpoch.getExclusionEligibilityAnswers().size());
	}
	
	/**
	 * Test get scheduled arm.
	 */
	public void testGetScheduledArm(){
		ScheduledArm scheduledArm= new ScheduledArm();
		scheduledArm.setId(1);
		scheduledEpoch.addScheduledArm(scheduledArm);
		scheduledArm= new ScheduledArm();
		scheduledArm.setId(2);
		scheduledEpoch.addScheduledArm(scheduledArm);
		assertEquals(2, scheduledEpoch.getScheduledArm().getId().intValue());
	}
	
	/**
	 * Test evaluate scheduled epoch data entry status.
	 * epoch.getStratificationIndicator(): true
	 * stratification group number: null
	 * eligibility: true
	 */
	public void testEvaluateScheduledEpochDataEntryStatusNullStratumGroupNumber(){
		EasyMock.expect(epoch.getStratificationIndicator()).andReturn(true);
		EasyMock.expect(epoch.getName()).andReturn("Test");
		EasyMock.expect(epoch.getRequiresArm()).andReturn(false).times(2);
		replayMocks();
		scheduledEpoch.setEligibilityIndicator(true);
		List<Error> errors=new ArrayList<Error>();
		ScheduledEpochDataEntryStatus scheduledEpochDataEntryStatus= scheduledEpoch.evaluateScheduledEpochDataEntryStatus(errors);
		assertEquals(ScheduledEpochDataEntryStatus.INCOMPLETE, scheduledEpochDataEntryStatus);
		assertEquals("The subject needs to be assgined a  stratum group number on scheduled epoch :Test", errors.get(0).getErrorMessage());
		verifyMocks();
	}
	
	/**
	 * Test evaluate scheduled epoch data entry status.
	 * epoch.getStratificationIndicator(): true
	 * stratification group number: not null
	 * eligibility: false
	 */
	public void testEvaluateScheduledEpochDataEntryStatusInEligible1(){
		EasyMock.expect(epoch.getStratificationIndicator()).andReturn(true);
		EasyMock.expect(epoch.getName()).andReturn("Test");
		EasyMock.expect(epoch.getRequiresArm()).andReturn(false).times(2);
		replayMocks();
		scheduledEpoch.setStratumGroupNumber(1);
		scheduledEpoch.setEligibilityIndicator(false);
		List<Error> errors=new ArrayList<Error>();
		ScheduledEpochDataEntryStatus scheduledEpochDataEntryStatus= scheduledEpoch.evaluateScheduledEpochDataEntryStatus(errors);
		assertEquals(ScheduledEpochDataEntryStatus.INCOMPLETE, scheduledEpochDataEntryStatus);
		assertEquals("The subject does not meet the eligibility criteria on scheduled epoch :Test", errors.get(0).getErrorMessage());
		verifyMocks();
	}
	
	/**
	 * Test evaluate scheduled epoch data entry status.
	 * epoch.getStratificationIndicator(): false
	 * eligibility: false
	 */
	public void testEvaluateScheduledEpochDataEntryStatusInEligible2(){
		EasyMock.expect(epoch.getStratificationIndicator()).andReturn(false);
		EasyMock.expect(epoch.getName()).andReturn("Test");
		EasyMock.expect(epoch.getRequiresArm()).andReturn(false).times(2);
		replayMocks();
		scheduledEpoch.setEligibilityIndicator(false);
		List<Error> errors=new ArrayList<Error>();
		ScheduledEpochDataEntryStatus scheduledEpochDataEntryStatus= scheduledEpoch.evaluateScheduledEpochDataEntryStatus(errors);
		assertEquals(ScheduledEpochDataEntryStatus.INCOMPLETE, scheduledEpochDataEntryStatus);
		assertEquals("The subject does not meet the eligibility criteria on scheduled epoch :Test", errors.get(0).getErrorMessage());
		verifyMocks();
	}
	
	/**
	 * Test evaluate scheduled epoch data entry status.
	 * epoch.getStratificationIndicator(): false
	 * eligibility: true
	 * epoch.getRequiresArm(): true
	 * epoch.getRandomizedIndicator(): false
	 * scheduleArm: null
	 */
	public void testEvaluateScheduledEpochDataEntryIncompleteArm(){
		EasyMock.expect(epoch.getStratificationIndicator()).andReturn(false);
		EasyMock.expect(epoch.getRequiresArm()).andReturn(true).times(2);
		EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(false);
		replayMocks();
		scheduledEpoch.setEligibilityIndicator(true);
		List<Error> errors=new ArrayList<Error>();
		ScheduledEpochDataEntryStatus scheduledEpochDataEntryStatus= scheduledEpoch.evaluateScheduledEpochDataEntryStatus(errors);
		assertEquals(ScheduledEpochDataEntryStatus.INCOMPLETE, scheduledEpochDataEntryStatus);
		assertEquals("The subject is not assigned to a scheduled arm", errors.get(0).getErrorMessage());
		verifyMocks();
	}
	
	/**
	 * Test evaluate scheduled epoch data entry status.
	 * epoch.getStratificationIndicator(): true
	 * stratification group number: null
	 * eligibility: false
	 * epoch.getRequiresArm(): true
	 * epoch.getRandomizedIndicator(): false
	 * scheduleArm: null
	 */
	public void testEvaluateScheduledEpochDataEntryAllErrors(){
		EasyMock.expect(epoch.getStratificationIndicator()).andReturn(true);
		EasyMock.expect(epoch.getName()).andReturn("Test").times(2);
		scheduledEpoch.setEligibilityIndicator(false);
		EasyMock.expect(epoch.getRequiresArm()).andReturn(true).times(2);
		EasyMock.expect(epoch.getRandomizedIndicator()).andReturn(false);
		replayMocks();
		List<Error> errors=new ArrayList<Error>();
		ScheduledEpochDataEntryStatus scheduledEpochDataEntryStatus= scheduledEpoch.evaluateScheduledEpochDataEntryStatus(errors);
		assertEquals(ScheduledEpochDataEntryStatus.INCOMPLETE, scheduledEpochDataEntryStatus);
		assertEquals("The subject needs to be assgined a  stratum group number on scheduled epoch :Test", errors.get(0).getErrorMessage());
		assertEquals("The subject does not meet the eligibility criteria on scheduled epoch :Test", errors.get(1).getErrorMessage());
		assertEquals("The subject is not assigned to a scheduled arm", errors.get(2).getErrorMessage());
		
		verifyMocks();
	}
	
	public void testHasWaivableEligibilityAnswers1(){
		SubjectEligibilityAnswer subjectEligibilityAnswer1 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer2 = registerMockFor(SubjectEligibilityAnswer.class);
		List<SubjectEligibilityAnswer> subjectStratificationAnswers = new ArrayList<SubjectEligibilityAnswer>();
		subjectStratificationAnswers.add(subjectEligibilityAnswer1);
		subjectStratificationAnswers.add(subjectEligibilityAnswer2);
		scheduledEpoch.setSubjectEligibilityAnswersInternal(subjectStratificationAnswers);
		EasyMock.expect(subjectEligibilityAnswer1.canAllowWaiver()).andReturn(false);
		EasyMock.expect(subjectEligibilityAnswer2.canAllowWaiver()).andReturn(true);
		replayMocks();
		assertTrue(scheduledEpoch.hasWaivableEligibilityAnswers());
		verifyMocks();
	}
	
	public void testHasWaivableEligibilityAnswers2(){
		SubjectEligibilityAnswer subjectEligibilityAnswer1 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer2 = registerMockFor(SubjectEligibilityAnswer.class);
		List<SubjectEligibilityAnswer> subjectStratificationAnswers = new ArrayList<SubjectEligibilityAnswer>();
		subjectStratificationAnswers.add(subjectEligibilityAnswer1);
		subjectStratificationAnswers.add(subjectEligibilityAnswer2);
		scheduledEpoch.setSubjectEligibilityAnswersInternal(subjectStratificationAnswers);
		EasyMock.expect(subjectEligibilityAnswer1.canAllowWaiver()).andReturn(true);
		replayMocks();
		assertTrue(scheduledEpoch.hasWaivableEligibilityAnswers());
		verifyMocks();
	}
	
	public void testHasWaivableEligibilityAnswers3(){
		SubjectEligibilityAnswer subjectEligibilityAnswer1 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer2 = registerMockFor(SubjectEligibilityAnswer.class);
		List<SubjectEligibilityAnswer> subjectStratificationAnswers = new ArrayList<SubjectEligibilityAnswer>();
		subjectStratificationAnswers.add(subjectEligibilityAnswer1);
		subjectStratificationAnswers.add(subjectEligibilityAnswer2);
		scheduledEpoch.setSubjectEligibilityAnswersInternal(subjectStratificationAnswers);
		EasyMock.expect(subjectEligibilityAnswer1.canAllowWaiver()).andReturn(false);
		EasyMock.expect(subjectEligibilityAnswer2.canAllowWaiver()).andReturn(false);
		replayMocks();
		assertFalse(scheduledEpoch.hasWaivableEligibilityAnswers());
		verifyMocks();
	}
	
	public void testEvaluateEligibilityIndicator1(){
		SubjectEligibilityAnswer subjectEligibilityAnswer1 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer2 = registerMockFor(SubjectEligibilityAnswer.class);
		List<SubjectEligibilityAnswer> subjectStratificationAnswers = scheduledEpoch.getSubjectEligibilityAnswers();
		subjectStratificationAnswers.add(subjectEligibilityAnswer1);
		subjectStratificationAnswers.add(subjectEligibilityAnswer2);
		EasyMock.expect(subjectEligibilityAnswer1.getEligibilityCriteria()).andReturn(new InclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer1.getAnswerText()).andReturn("Yes");
		EasyMock.expect(subjectEligibilityAnswer2.getEligibilityCriteria()).andReturn(new ExclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer2.getAnswerText()).andReturn("No");
		replayMocks();
		assertTrue(scheduledEpoch.evaluateEligibilityIndicator());
		verifyMocks();
	}
	
	public void testEvaluateEligibilityIndicator2(){
		SubjectEligibilityAnswer subjectEligibilityAnswer1 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer2 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer3 = registerMockFor(SubjectEligibilityAnswer.class);
		List<SubjectEligibilityAnswer> subjectStratificationAnswers = scheduledEpoch.getSubjectEligibilityAnswers();
		subjectStratificationAnswers.add(subjectEligibilityAnswer1);
		subjectStratificationAnswers.add(subjectEligibilityAnswer2);
		subjectStratificationAnswers.add(subjectEligibilityAnswer3);
		EasyMock.expect(subjectEligibilityAnswer1.getEligibilityCriteria()).andReturn(new InclusionEligibilityCriteria());
		EasyMock.expect(subjectEligibilityAnswer1.getAnswerText()).andReturn("Yes");
		EasyMock.expect(subjectEligibilityAnswer3.getEligibilityCriteria()).andReturn(new InclusionEligibilityCriteria());
		EasyMock.expect(subjectEligibilityAnswer3.getAnswerText()).andReturn("No");
		EasyMock.expect(subjectEligibilityAnswer3.getAllowWaiver()).andReturn(false);
		EasyMock.expect(subjectEligibilityAnswer2.getEligibilityCriteria()).andReturn(new ExclusionEligibilityCriteria());
		replayMocks();
		assertFalse(scheduledEpoch.evaluateEligibilityIndicator());
		verifyMocks();
	}
	
	public void testEvaluateEligibilityIndicator3(){
		SubjectEligibilityAnswer subjectEligibilityAnswer1 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer2 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer3 = registerMockFor(SubjectEligibilityAnswer.class);
		List<SubjectEligibilityAnswer> subjectStratificationAnswers = scheduledEpoch.getSubjectEligibilityAnswers();
		subjectStratificationAnswers.add(subjectEligibilityAnswer1);
		subjectStratificationAnswers.add(subjectEligibilityAnswer2);
		subjectStratificationAnswers.add(subjectEligibilityAnswer3);
		EasyMock.expect(subjectEligibilityAnswer1.getEligibilityCriteria()).andReturn(new InclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer1.getAnswerText()).andReturn("Yes");
		EasyMock.expect(subjectEligibilityAnswer2.getEligibilityCriteria()).andReturn(new ExclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer2.getAnswerText()).andReturn("No");
		EasyMock.expect(subjectEligibilityAnswer3.getEligibilityCriteria()).andReturn(new ExclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer3.getAnswerText()).andReturn("Yes");
		EasyMock.expect(subjectEligibilityAnswer3.getAllowWaiver()).andReturn(false);
		replayMocks();
		assertFalse(scheduledEpoch.evaluateEligibilityIndicator());
		verifyMocks();
	}
	
	public void testEvaluateEligibilityIndicatorAllowWaiver1(){
		SubjectEligibilityAnswer subjectEligibilityAnswer1 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer2 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer3 = registerMockFor(SubjectEligibilityAnswer.class);
		List<SubjectEligibilityAnswer> subjectStratificationAnswers = scheduledEpoch.getSubjectEligibilityAnswers();
		subjectStratificationAnswers.add(subjectEligibilityAnswer1);
		subjectStratificationAnswers.add(subjectEligibilityAnswer2);
		subjectStratificationAnswers.add(subjectEligibilityAnswer3);
		EasyMock.expect(subjectEligibilityAnswer1.getEligibilityCriteria()).andReturn(new InclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer1.getAnswerText()).andReturn("Yes");
		EasyMock.expect(subjectEligibilityAnswer2.getEligibilityCriteria()).andReturn(new ExclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer2.getAnswerText()).andReturn("No");
		EasyMock.expect(subjectEligibilityAnswer3.getEligibilityCriteria()).andReturn(new ExclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer3.getAnswerText()).andReturn("Yes");
		EasyMock.expect(subjectEligibilityAnswer3.getAllowWaiver()).andReturn(true);
		replayMocks();
		assertTrue(scheduledEpoch.evaluateEligibilityIndicator());
		verifyMocks();
	}
	
	public void testEvaluateEligibilityIndicatorAllowWaiver2(){
		SubjectEligibilityAnswer subjectEligibilityAnswer1 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer2 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer3 = registerMockFor(SubjectEligibilityAnswer.class);
		List<SubjectEligibilityAnswer> subjectStratificationAnswers = scheduledEpoch.getSubjectEligibilityAnswers();
		subjectStratificationAnswers.add(subjectEligibilityAnswer1);
		subjectStratificationAnswers.add(subjectEligibilityAnswer2);
		subjectStratificationAnswers.add(subjectEligibilityAnswer3);
		EasyMock.expect(subjectEligibilityAnswer1.getEligibilityCriteria()).andReturn(new InclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer1.getAnswerText()).andReturn("Yes");
		EasyMock.expect(subjectEligibilityAnswer3.getEligibilityCriteria()).andReturn(new InclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer3.getAnswerText()).andReturn("No");
		EasyMock.expect(subjectEligibilityAnswer3.getAllowWaiver()).andReturn(true);
		EasyMock.expect(subjectEligibilityAnswer2.getEligibilityCriteria()).andReturn(new ExclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer2.getAnswerText()).andReturn("No");
		replayMocks();
		assertTrue(scheduledEpoch.evaluateEligibilityIndicator());
		verifyMocks();
	}
	
	public void testEvaluateEligibilityIndicatorAllowWaiver3(){
		SubjectEligibilityAnswer subjectEligibilityAnswer1 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer2 = registerMockFor(SubjectEligibilityAnswer.class);
		SubjectEligibilityAnswer subjectEligibilityAnswer3 = registerMockFor(SubjectEligibilityAnswer.class);
		List<SubjectEligibilityAnswer> subjectStratificationAnswers = scheduledEpoch.getSubjectEligibilityAnswers();
		subjectStratificationAnswers.add(subjectEligibilityAnswer1);
		subjectStratificationAnswers.add(subjectEligibilityAnswer2);
		subjectStratificationAnswers.add(subjectEligibilityAnswer3);
		EasyMock.expect(subjectEligibilityAnswer1.getEligibilityCriteria()).andReturn(new InclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer1.getAnswerText()).andReturn("Yes");
		EasyMock.expect(subjectEligibilityAnswer3.getEligibilityCriteria()).andReturn(new InclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer3.getAnswerText()).andReturn("No");
		EasyMock.expect(subjectEligibilityAnswer3.getAllowWaiver()).andReturn(true);
		EasyMock.expect(subjectEligibilityAnswer2.getEligibilityCriteria()).andReturn(new ExclusionEligibilityCriteria()).times(2);
		EasyMock.expect(subjectEligibilityAnswer2.getAnswerText()).andReturn("Yes");
		EasyMock.expect(subjectEligibilityAnswer2.getAllowWaiver()).andReturn(false);
		replayMocks();
		assertFalse(scheduledEpoch.evaluateEligibilityIndicator());
		verifyMocks();
	}
	
	public void testTakeSubjectOffEpoch(){
		Date date = new Date();
		List<OffEpochReason> offEpochReasons = new ArrayList<OffEpochReason>();
		OffEpochReason offEpochReason1 = new OffEpochReason();
		OffEpochReason offEpochReason2 = new OffEpochReason();
		offEpochReasons.add(offEpochReason1);
		offEpochReasons.add(offEpochReason2);
		scheduledEpoch.takeSubjectOffEpoch(offEpochReasons, date);
		assertEquals(ScheduledEpochWorkFlowStatus.OFF_EPOCH, scheduledEpoch.getScEpochWorkflowStatus());
		assertEquals(date, scheduledEpoch.getOffEpochDate());
		assertEquals(2, scheduledEpoch.getOffEpochReasons().size());
	}
}
