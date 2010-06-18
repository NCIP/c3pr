/**
 * 
 */
package edu.duke.cabig.c3pr.web.registration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.apache.commons.collections15.list.LazyList;



import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.OffEpochReason;
import edu.duke.cabig.c3pr.domain.OffTreatmentReason;
import edu.duke.cabig.c3pr.domain.Reason;
import edu.duke.cabig.c3pr.domain.StudySubject;

/**
 * @author Himanshu
 *
 */
public class StudySubjectWrapper {
	
	private StudySubject studySubject ;
	
	// used for displaying participant data in UI
	private Object participant;
	
	private List<Integer> waiveEligibilityCrieteria = new ArrayList<Integer>();
	
	private Reason[] reasons;
	
	private List<OffEpochReason> offEpochReasons = LazyList.decorate(new ArrayList<OffEpochReason>(), new InstantiateFactory<OffEpochReason>(OffEpochReason.class){
		@Override
		public OffEpochReason create() {
			OffEpochReason offEpochReason = super.create();
			offEpochReason.setReason(new OffTreatmentReason());
			return offEpochReason;
		}
	});
	
	private Date offEpochDate;
	
	public Object getParticipant() {
		return participant;
	}

	public void setParticipant(Object participant) {
		this.participant = participant;
	}

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
		return (this.studySubject.getScheduledEpoch().getEpoch().getType() == EpochType.RESERVING
				&& (this.studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) 
				&& (this.studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING));
	}
	
	public Boolean getShouldRegister(){
		if(!this.studySubject.getDataEntryStatus()){
			return false;
		}
		
		boolean reservationIndicator = this.studySubject.getScheduledEpoch().getEpoch().getType() == EpochType.RESERVING ;
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
	
	public boolean getCanAllowEligibilityWaiver(){
		return this.studySubject.canAllowEligibilityWaiver();
	}

	public List<Integer> getWaiveEligibilityCrieteria() {
		return waiveEligibilityCrieteria;
	}

	public void setWaiveEligibilityCrieteria(List<Integer> waiveEligibilityCrieteria) {
		this.waiveEligibilityCrieteria = waiveEligibilityCrieteria;
	}

	public Reason[] getReasons() {
		return reasons;
	}

	public void setReasons(Reason[] reasons) {
		this.reasons = reasons;
	}

	public Date getOffEpochDate() {
		return offEpochDate;
	}

	public void setOffEpochDate(Date offEpochDate) {
		this.offEpochDate = offEpochDate;
	}

	public List<OffEpochReason> getOffEpochReasons() {
		return offEpochReasons;
	}

	public void setOffEpochReasons(List<OffEpochReason> offEpochReasons) {
		this.offEpochReasons = offEpochReasons;
	}

}
