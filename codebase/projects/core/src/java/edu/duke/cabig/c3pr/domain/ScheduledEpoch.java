package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Ram Chilukuri, Priyatam
 */
@Entity
@Table(name = "scheduled_epochs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SCHEDULED_EPOCHS_ID_SEQ") })
public abstract class ScheduledEpoch extends AbstractMutableDeletableDomainObject implements Comparable<ScheduledEpoch>{

	private Epoch epoch;

	private Date startDate;
	
	private ScheduledEpochDataEntryStatus scEpochDataEntryStatus;
	
	private ScheduledEpochWorkFlowStatus scEpochWorkflowStatus;
	
	private String disapprovalReasonText;

	public String getDisapprovalReasonText() {
		return disapprovalReasonText;
	}

	public ScheduledEpoch() {
		this.startDate=new Date();
		this.scEpochWorkflowStatus=ScheduledEpochWorkFlowStatus.UNAPPROVED;
	}

	public ScheduledEpoch(boolean forExample) {
		if(!forExample){
			this.startDate=new Date();
			this.scEpochWorkflowStatus=ScheduledEpochWorkFlowStatus.UNAPPROVED;
		}
	}

	public void setDisapprovalReasonText(String disapprovalReasonText) {
		this.disapprovalReasonText = disapprovalReasonText;
	}

	@Enumerated(EnumType.STRING)
	public ScheduledEpochDataEntryStatus getScEpochDataEntryStatus() {
		return scEpochDataEntryStatus;
	}

	public void setScEpochDataEntryStatus(
			ScheduledEpochDataEntryStatus scheduledEpochDataEntryStatus) {
		this.scEpochDataEntryStatus = scheduledEpochDataEntryStatus;
	}

	@Enumerated(EnumType.STRING)
	public ScheduledEpochWorkFlowStatus getScEpochWorkflowStatus() {
		return scEpochWorkflowStatus;
	}

	public void setScEpochWorkflowStatus(
			ScheduledEpochWorkFlowStatus scheduledEpochWorkFlowStatus) {
		this.scEpochWorkflowStatus = scheduledEpochWorkFlowStatus;
	}

	@ManyToOne
	@JoinColumn(name = "eph_id")
	public Epoch getEpoch() {
		return epoch;
	}

	public void setEpoch(Epoch epoch) {
		this.epoch = epoch;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	/*
	 * @Override public int hashCode() { final int PRIME = 31; int result =
	 * super.hashCode(); result = PRIME * result + ((arms == null) ? 0 :
	 * arms.hashCode()); result = PRIME * result + ((name == null) ? 0 :
	 * name.hashCode()); return result; }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return
	 * true; if (!super.equals(obj)) return false; if (getClass() !=
	 * obj.getClass()) return false; final Epoch other = (Epoch) obj; if (arms ==
	 * null) { if (other.arms != null) return false; } else if
	 * (!arms.equals(other.arms)) return false; if (name == null) { if
	 * (other.name != null) return false; } else if (!name.equals(other.name))
	 * return false; return true; }
	 */

	public int compareTo(ScheduledEpoch scheduledEpoch) {
		return this.startDate.compareTo(scheduledEpoch.getStartDate());
	}
	
	@Transient
	public boolean getRequiresArm(){
		return this.getEpoch().getRequiresArm();
	}
	
	@Transient
	public boolean isReserving(){
		return this.getEpoch().isReserving();
	}
	@Transient
	public boolean getRequiresRandomization(){
		return this.getEpoch().getRequiresRandomization();
	}
}