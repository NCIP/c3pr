/**
 * 
 */
package edu.duke.cabig.c3pr.web.registration;

import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
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
	
	public Boolean getShouldReserve() {
		if (!this.studySubject.getDataEntryStatus()) {
			return false;
		}
		return (this.studySubject.getScheduledEpoch().getEpoch().getReservationIndicator()
				&& (this.studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) 
				&& (this.studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING));
	}
	
	public Boolean getShouldRegister(){
		if(!this.studySubject.getDataEntryStatus()){
			return false;
		}
		
		boolean reservationIndicator = this.studySubject.getScheduledEpoch().getEpoch().getReservationIndicator() ;
		boolean enrollmentIndicator = this.studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator() ;
		
		if(this.studySubject.getParentStudySubject() != null){
			if((reservationIndicator || enrollmentIndicator) && this.studySubject.getParentStudySubject().getRegWorkflowStatus() == RegistrationWorkFlowStatus.ENROLLED){
				return false;
			}else{
				return true;
			}
		}
		
		return !this.studySubject.getWorkPendingOnMandatoryCompanionRegistrations() && !enrollmentIndicator ;
	}
	
	public Boolean getShouldEnroll(){
		if(!this.studySubject.getDataEntryStatus()){
			return false;
		}
		
		boolean enrollmentIndicator  = this.studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator() ;
		if(this.studySubject.getParentStudySubject() != null && enrollmentIndicator){
			if(this.studySubject.getParentStudySubject().getRegWorkflowStatus() == RegistrationWorkFlowStatus.ENROLLED){
				return true ;
			}else{
				return false ;
			}
		}

		return(!this.studySubject.getWorkPendingOnMandatoryCompanionRegistrations() && enrollmentIndicator);
	}
	
	public Boolean getShouldRandomize(){
		if(!this.studySubject.getDataEntryStatus()){
			return false;
		}

		Boolean requiresRandomization = this.getStudySubject().getScheduledEpoch().getRequiresRandomization();

		if(this.studySubject.getParentStudySubject() != null && requiresRandomization){
			if(this.studySubject.getParentStudySubject().getRegWorkflowStatus() == RegistrationWorkFlowStatus.ENROLLED){
				return true ;
			}else{
				return false ;
			}
		}
		
		return requiresRandomization;
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
