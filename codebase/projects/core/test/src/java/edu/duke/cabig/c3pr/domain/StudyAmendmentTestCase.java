package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.Calendar;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;

public class StudyAmendmentTestCase extends AbstractTestCase {
	
	private Study amendedStudy ;
	private StudyCreationHelper studyCreationHelper = new StudyCreationHelper();
	public void setStudyCreationHelper(StudyCreationHelper studyCreationHelper) {
		this.studyCreationHelper = studyCreationHelper;
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		amendedStudy = studyCreationHelper.buildStudyWithAmendment();
	}
	
	public void testGetAmendmentDateStr(){
		try {
			assertEquals("Amendment date should be current date",  DateUtil.formatDate(Calendar.getInstance().getTime(), "MM/dd/yyyy"), amendedStudy.getCurrentStudyAmendment().getAmendmentDateStr());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void testGetAmendmentDateStrThrowsException(){
		Study amendedStudyWithNoAmendmentDate =studyCreationHelper.buildStudyWithAmendmentWithNoAmendmentDate();
		assertEquals("Amendment date should be current date",  "", amendedStudyWithNoAmendmentDate.getCurrentStudyAmendment().getAmendmentDateStr());
	}

}
