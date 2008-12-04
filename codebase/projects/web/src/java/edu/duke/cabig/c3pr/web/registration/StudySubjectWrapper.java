/**
 * 
 */
package edu.duke.cabig.c3pr.web.registration;

import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * @author Himanshu
 *
 */
public class StudySubjectWrapper {
	
	private StudySubject studySubject ;

	public StudySubject getStudySubject() {
		return studySubject;
	}

	public void setStudySubject(StudySubject studySubject) {
		this.studySubject = studySubject;
	}
	
	public Boolean getIsRegisterable(){
		if(!this.studySubject.getDataEntryStatus())
			return null;
		if(this.studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator() || this.getStudySubject().getScheduledEpoch().getRequiresRandomization())
			return true;
		return false;
	}
}
