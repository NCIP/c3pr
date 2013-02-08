/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
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
		Epoch epoch1 = new Epoch();
		epoch1.setEpochOrder(3);
		scheduledEpoch1.setEpoch(epoch1);
		scheduledEpoch1.setStartDate(new Date());
		scheduledEpoch1.setId(1);
		ScheduledEpoch scheduledEpoch2= new ScheduledEpoch();
		Epoch epoch2 = new Epoch();
		epoch2.setEpochOrder(1);
		scheduledEpoch2.setEpoch(epoch2);
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
