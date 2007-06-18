package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

@Entity
@Table(name = "SCHEDULED_ARMS")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SCHEDULED_ARMS_ID_seq") })
public class ScheduledArm extends AbstractMutableDomainObject {

	private Date startDate;

	private String eligibilityIndicator;

	private String eligibilityWaiverReasonText;

	private StudyParticipantAssignment studyParticipantAssignment;
	
	private Arm arm;

	public ScheduledArm() {
		this.startDate=new Date();
		this.eligibilityIndicator="true";
	}
	public ScheduledArm(StudyParticipantAssignment studyParticipantAssignment) {
		this.studyParticipantAssignment=studyParticipantAssignment;
		this.startDate=new Date();
		this.eligibilityIndicator="true";
	}

	@ManyToOne
	@JoinColumn (name="ARM_ID")
	public Arm getArm() {
		return arm;
	}

	public void setArm(Arm arm) {
		this.arm = arm;
	}

	public String getEligibilityIndicator() {
		return eligibilityIndicator;
	}

	public void setEligibilityIndicator(String eligibilityIndicator) {
		this.eligibilityIndicator = eligibilityIndicator;
	}


	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SPA_ID")
	public StudyParticipantAssignment getStudyParticipantAssignment() {
		return studyParticipantAssignment;
	}

	public void setStudyParticipantAssignment(
			StudyParticipantAssignment studyParticipantAssignment) {
		this.studyParticipantAssignment = studyParticipantAssignment;
	}

	public String getEligibilityWaiverReasonText() {
		return eligibilityWaiverReasonText;
	}

	public void setEligibilityWaiverReasonText(String eligibilityWaiverReasonText) {
		this.eligibilityWaiverReasonText = eligibilityWaiverReasonText;
	}

}
