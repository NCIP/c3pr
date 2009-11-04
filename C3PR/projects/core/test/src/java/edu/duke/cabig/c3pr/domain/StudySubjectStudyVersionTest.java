package edu.duke.cabig.c3pr.domain;

import java.util.Date;
import java.util.GregorianCalendar;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.utils.DateUtil;

public class StudySubjectStudyVersionTest extends AbstractTestCase {

	private StudySubjectStudyVersion studySubjectStudyVersion;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		studySubjectStudyVersion= new StudySubjectStudyVersion();
	}
	
	public void testGetCurrentScheduledEpoch() throws Exception{
		ScheduledEpoch scheduledEpoch1= new ScheduledEpoch();
		scheduledEpoch1.setStartDate(new Date());
		scheduledEpoch1.setId(1);
		ScheduledEpoch scheduledEpoch2= new ScheduledEpoch();
		scheduledEpoch2.setStartDate((new GregorianCalendar(1990, 1, 2)).getTime());
		scheduledEpoch2.setId(2);
		studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch1);
		studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch2);
		ScheduledEpoch scheduledEpoch= studySubjectStudyVersion.getCurrentScheduledEpoch();
		assertEquals(DateUtil.formatDate(new Date(), "MM/dd/yyyy"), DateUtil.formatDate(scheduledEpoch.getStartDate(), "MM/dd/yyyy"));
	}
	
	public void testGetScheduledEpochEmptyScheduledEpochs() {
		assertNull(studySubjectStudyVersion.getScheduledEpoch(new Epoch()));
	}
	
	public void testGetScheduledEpochNotMatchingEpoch() {
		ScheduledEpoch scheduledEpoch= new ScheduledEpoch();
		
		assertNull(studySubjectStudyVersion.getScheduledEpoch(new Epoch()));
	}
	
}