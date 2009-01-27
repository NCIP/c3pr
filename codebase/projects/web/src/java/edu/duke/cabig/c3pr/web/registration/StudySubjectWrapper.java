/**
 * 
 */
package edu.duke.cabig.c3pr.web.registration;

import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.ScheduledEpochWorkFlowStatus;
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
	
	public Boolean getShouldReserve(){
		if(!this.studySubject.getDataEntryStatus()){
			return false;
		}
		return(this.studySubject.getScheduledEpoch().getEpoch().getReservationIndicator()&& (this.studySubject.getScheduledEpoch().getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.PENDING) &&(this.studySubject.getRegWorkflowStatus()==RegistrationWorkFlowStatus.PENDING));
	}
	
	public Boolean getShouldRegister(){
		if(!this.studySubject.getDataEntryStatus()){
			return false;
		}
		
		if(this.studySubject.getParentStudySubject()!=null && this.studySubject.getParentStudySubject().getRegWorkflowStatus()== RegistrationWorkFlowStatus.ENROLLED && this.studySubject.getMatchingCompanionStudyAssociation(studySubject) != null && !this.studySubject.getMatchingCompanionStudyAssociation(studySubject).getMandatoryIndicator()){
			return false;
		}
		
		
		if(this.studySubject.getParentStudySubject()!=null && this.studySubject.getParentStudySubject().getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED){
			return true;
		}
		return this.studySubject.getWorkPendingOnMandatoryCompanionRegistrations() || (!this.studySubject.getScheduledEpoch().getEpoch().getReservationIndicator() && !this.studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator());
	}
	
	public Boolean getShouldEnroll(){
		if(!this.studySubject.getDataEntryStatus()){
			return false;
		}
		return(!this.studySubject.getWorkPendingOnMandatoryCompanionRegistrations() && this.studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator());
	}
	
	public Boolean getShouldRandomize(){
		if(!this.studySubject.getDataEntryStatus()){
			return false;
		}
		return(this.getStudySubject().getScheduledEpoch().getRequiresRandomization());
	}
	
	public Boolean getShouldTransfer(){
		if (this.studySubject.getRegWorkflowStatus()==RegistrationWorkFlowStatus.ENROLLED){
			if(this.studySubject.getDataEntryStatus()){
				return true;
			}
		}
		return false;
	}
}
