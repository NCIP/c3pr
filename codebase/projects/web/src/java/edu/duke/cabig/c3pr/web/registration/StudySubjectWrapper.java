/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.Reason;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.utils.StringUtils;

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
	
	 private List<PersonUser> notifiedPersonUsers = new ArrayList<PersonUser>();
	
	public List<PersonUser> getNotifiedPersonUsers() {
		return notifiedPersonUsers;
	}

	public void setNotifiedPersonUsers(List<PersonUser> notifiedPersonUsers) {
		this.notifiedPersonUsers = notifiedPersonUsers;
	}

	private List<StudySubjectConsentVersion> reConsentingStudySubjectConsentVersions =
		new ArrayList<StudySubjectConsentVersion>();
	
	public List<StudySubjectConsentVersion> getReConsentingStudySubjectConsentVersions() {
		return reConsentingStudySubjectConsentVersions;
	}

	public String getTimeLineDescriptionsOfRegistrationDetails() {
		StringBuffer sb = new StringBuffer();
		sb.append("Subject is officially enrolled.");
		if(studySubject.getTreatingPhysician() !=null){
			sb.append("<br> Treating Physican: " + studySubject.getTreatingPhysicianFullName());
		}
		if(studySubject.getOtherTreatingPhysician() !=null){
			sb.append("<br> Treating physican: " + studySubject.getOtherTreatingPhysician());
		}
		if(studySubject.getDiseaseHistory().getIcd9DiseaseSite() !=null){
			sb.append("<br> Disease site: " + studySubject.getDiseaseHistory().getIcd9DiseaseSite().getName());
		}
		if(!StringUtils.isBlank(studySubject.getPaymentMethod())){
			sb.append("<br> Payment method: " + studySubject.getPaymentMethod());
		}
		return sb.toString();
	}
	
	public String getTimelineDescriptionOfOffStudyReasons() {
		StringBuffer sb = new StringBuffer();
		if(studySubject.getOffStudyDate() != null) {
			sb.append("Subject is Off-Study");
			if(studySubject.getOffStudyReasons().size() > 0){
				sb.append("<br> Off Study reason(s): ");
				for(int i =0; i< studySubject.getOffStudyReasons().size(); i ++){
					if(i==0){
						sb.append(studySubject.getOffStudyReasons().get(i).getReason().getDescription());
					}else{
						sb.append(", " + studySubject.getOffStudyReasons().get(i).getReason().getDescription());
					}
				}
			}
		}
		return sb.toString();
	}
	
	public List<String> getTimeLineDescriptionsOfScheduledEpochs(){
		List<String> timeLineDescriptionsOfScheduledEpochs = new ArrayList<String>();
		for(ScheduledEpoch scheduledEpoch : studySubject.getScheduledEpochs()){
			StringBuffer sb = new StringBuffer();
			sb.append("Epoch type: " + scheduledEpoch.getEpoch().getType().getDisplayName());
			sb.append("<br>Scheduled epoch status: " +scheduledEpoch.getScEpochWorkflowStatus().getDisplayName());
			if(scheduledEpoch.getStratumGroupNumber() !=null){
				sb.append("<br> Stratum group: " + scheduledEpoch.getStratumGroupNumber());
			}
			if(scheduledEpoch.getScheduledArm() !=null && scheduledEpoch.getScheduledArm().getArm()!=null){
				sb.append("<br> Arm assigned: " + scheduledEpoch.getScheduledArm().getArm().getName());
			}
			if(scheduledEpoch.getOffEpochReasons().size() > 0){
				sb.append("<br> Off Epoch reason(s): ");
				for(int i =0; i< scheduledEpoch.getOffEpochReasons().size(); i ++){
					if(i==0){
						sb.append(scheduledEpoch.getOffEpochReasons().get(i).getReason().getDescription());
					}else{
						sb.append(", " + scheduledEpoch.getOffEpochReasons().get(i).getReason().getDescription());
					}
				}
			}
			timeLineDescriptionsOfScheduledEpochs.add(sb.toString());
		}
		return timeLineDescriptionsOfScheduledEpochs;
	}
	
	public List<String> getTimeLineDescriptionsOfSignedConsents(){
		List<String> timeLineDescriptionsOfSignedConsents = new ArrayList<String>();
		for(StudySubjectConsentVersion studySubjectConsentVersion : studySubject.getAllSignedConsents()){
			StringBuffer sb = new StringBuffer();
			sb.append("Mandatory: " + (studySubjectConsentVersion.getConsent().getMandatoryIndicator()?"Yes":"No"));
			if(studySubjectConsentVersion.getConsentPresenter() !=null){
				sb.append("<br> Consent presenter: " + studySubjectConsentVersion.getConsentPresenter());
			}
			if(studySubjectConsentVersion.getConsentDeliveryDate() !=null){
				sb.append("<br> Delivery date: " + studySubjectConsentVersion.getConsentDeliveryDateStr());
			}
			if(studySubjectConsentVersion.getConsentingMethod() !=null){
				sb.append("<br> Consenting method: " + studySubjectConsentVersion.getConsentingMethod().getDisplayName());
			}
			timeLineDescriptionsOfSignedConsents.add(sb.toString());
		}
		return timeLineDescriptionsOfSignedConsents;
		
		
	}

	public void setReConsentingStudySubjectConsentVersons(
			List<StudySubjectConsentVersion> reConsentingStudySubjectConsentVersons) {
		this.reConsentingStudySubjectConsentVersions = reConsentingStudySubjectConsentVersons;
	}
	
	public void addReConsentingStudySubjectConsentVersion(StudySubjectConsentVersion studySubjectConsentVersion){
		this.getReConsentingStudySubjectConsentVersions().add(studySubjectConsentVersion);
	}
	
	public void removeReConsentingStudySubjectConsentVersion(StudySubjectConsentVersion studySubjectConsentVersion){
		this.getReConsentingStudySubjectConsentVersions().remove(studySubjectConsentVersion);
	}

	private List<OffEpochReason> offEpochReasons = LazyList.decorate(new ArrayList<OffEpochReason>(), new InstantiateFactory<OffEpochReason>(OffEpochReason.class){
		@Override
		public OffEpochReason create() {
			OffEpochReason offEpochReason = super.create();
			offEpochReason.setReason(new OffTreatmentReason());
			return offEpochReason;
		}
	});
	
	private Date offEpochDate;
	
	private StudyVersion reConsentingVersion;
	
	public StudyVersion getReConsentingVersion() {
		return reConsentingVersion;
	}

	public void setReConsentingVersion(StudyVersion reConsentingVersion) {
		this.reConsentingVersion = reConsentingVersion;
	}

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
				&& (this.studySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH) 
				&& (this.studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING));
	}
	
	public Boolean getShouldRegister(){
		if(!this.studySubject.getDataEntryStatus()){
			return false;
		}
		
		boolean reservationIndicator = this.studySubject.getScheduledEpoch().getEpoch().getType() == EpochType.RESERVING ;
		boolean enrollmentIndicator = this.studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator() ;
		
		if(this.studySubject.getParentStudySubject() != null){
			if((reservationIndicator || enrollmentIndicator) && this.studySubject.getParentStudySubject().getRegWorkflowStatus() == RegistrationWorkFlowStatus.ON_STUDY){
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
		if(this.studySubject.getStartDate() == null){
			return false;
		}
		boolean enrollmentIndicator  = this.studySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator() ;
		if(this.studySubject.getParentStudySubject() != null && enrollmentIndicator){
			if(this.studySubject.getParentStudySubject().getRegWorkflowStatus() == RegistrationWorkFlowStatus.ON_STUDY){
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
		
		if(this.studySubject.getStartDate() == null){
			return false;
		}

		Boolean requiresRandomization = this.getStudySubject().getScheduledEpoch().getRequiresRandomization();

		if(this.studySubject.getParentStudySubject() != null && requiresRandomization){
			if(this.studySubject.getParentStudySubject().getRegWorkflowStatus() == RegistrationWorkFlowStatus.ON_STUDY){
				return true ;
			}else{
				return false ;
			}
		}
		
		return requiresRandomization;
	}
	
	public Boolean getShouldTransfer(){
		if (this.studySubject.getRegWorkflowStatus()==RegistrationWorkFlowStatus.ON_STUDY){
			if(this.studySubject.getDataEntryStatus()){
				return true;
			}
		}
		return false;
	}
	
	
	public Boolean getCanReConsent(){
		
		return getReConsentableStudyVersions().size() > 0;
	}
	
	public List<StudyVersion> getReConsentableStudyVersions(){
		List<StudyVersion> reConsentableStudyVersions = new ArrayList<StudyVersion>();
		
		for(StudyVersion studyVersion : this.studySubject.getStudySite().getStudy().getStudyVersions()){
			if (this.studySubject.canReConsent(studyVersion.getName())){
				reConsentableStudyVersions.add(studyVersion);
			}
		}
		return reConsentableStudyVersions;
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
