package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "SCHEDULED_ARMS")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SCHEDULED_ARMS_ID_seq") })
public class ScheduledArm extends AbstractMutableDeletableDomainObject {

	private Date startDate;

	private Boolean eligibilityIndicator;

	private String eligibilityWaiverReasonText;

	private Arm arm;
	
	private String kitNumber;

	public ScheduledArm() {
		this.startDate=new Date();
		this.eligibilityIndicator=Boolean.TRUE;
	}

	@ManyToOne
	@JoinColumn (name="ARM_ID")
	public Arm getArm() {
		return arm;
	}

	public void setArm(Arm arm) {
		this.arm = arm;
	}

	public Boolean getEligibilityIndicator() {
		return eligibilityIndicator;
	}

	public void setEligibilityIndicator(Boolean eligibilityIndicator) {
		this.eligibilityIndicator = eligibilityIndicator;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getEligibilityWaiverReasonText() {
		return eligibilityWaiverReasonText;
	}

	public void setEligibilityWaiverReasonText(String eligibilityWaiverReasonText) {
		this.eligibilityWaiverReasonText = eligibilityWaiverReasonText;
	}

	public String getKitNumber() {
		return kitNumber;
	}

	public void setKitNumber(String kitNumber) {
		this.kitNumber = kitNumber;
	}
}
