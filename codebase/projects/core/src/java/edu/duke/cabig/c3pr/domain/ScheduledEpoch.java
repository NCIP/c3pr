package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * The Class ScheduledEpoch.
 * 
 * @author Ram Chilukuri, Priyatam
 */
@Entity
@Table(name = "scheduled_epochs")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SCHEDULED_EPOCHS_ID_SEQ") })
public class ScheduledEpoch extends AbstractMutableDeletableDomainObject implements
                Comparable<ScheduledEpoch> {

    /** The epoch. */
    private Epoch epoch;

    /** The start date. */
    private Date startDate;

    /** The sc epoch data entry status. */
    private ScheduledEpochDataEntryStatus scEpochDataEntryStatus;

    /** The sc epoch workflow status. */
    private ScheduledEpochWorkFlowStatus scEpochWorkflowStatus;

    /** The disapproval reason text. */
    private String disapprovalReasonText;
    
    /**
     * Gets the stratum group number.
     * 
     * @return the stratum group number
     */
    public Integer getStratumGroupNumber() {
		return stratumGroupNumber;
	}

	/**
	 * Sets the stratum group number.
	 * 
	 * @param stratumGroupNumber the new stratum group number
	 */
	public void setStratumGroupNumber(Integer stratumGroupNumber) {
		this.stratumGroupNumber = stratumGroupNumber;
	}
	
	 /**
 	 * Gets the stratum group.
 	 * 
 	 * @return the stratum group
 	 * 
 	 * @throws C3PRBaseException the c3 pr base exception
 	 */
 	@Transient 
    public StratumGroup getStratumGroup() throws C3PRBaseException {
        StratumGroup stratumGroup = null;
        // deleted if condition, it should always get the stratum group based on answer combination
        List<SubjectStratificationAnswer> ssaList = getSubjectStratificationAnswers();
        if (ssaList != null) {
            Iterator iter = ssaList.iterator();
            List<StratificationCriterionAnswerCombination> scacList = new ArrayList<StratificationCriterionAnswerCombination>();
            while (iter.hasNext()) {
                scacList.add(new StratificationCriterionAnswerCombination(
                                (SubjectStratificationAnswer) iter.next()));
            }
            stratumGroup = getEpoch()
                            .getStratumGroupForAnsCombination(scacList);
        }
        if (stratumGroup == null) {
            throw new C3PRBaseException(
                            "No stratum group found. Maybe the answer combination does not have a valid startum group");
        }
        return stratumGroup;
    }

	/** The stratum group number. */
	private Integer stratumGroupNumber;

    /**
     * Gets the disapproval reason text.
     * 
     * @return the disapproval reason text
     */
    public String getDisapprovalReasonText() {
        return disapprovalReasonText;
    }

    /**
     * Instantiates a new scheduled epoch.
     */
    public ScheduledEpoch() {
        this.startDate = new Date();
        this.scEpochWorkflowStatus = ScheduledEpochWorkFlowStatus.PENDING;
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(SubjectEligibilityAnswer.class,
                        new InstantiateFactory<SubjectEligibilityAnswer>(
                                        SubjectEligibilityAnswer.class));
        lazyListHelper.add(SubjectStratificationAnswer.class,
                        new InstantiateFactory<SubjectStratificationAnswer>(
                                        SubjectStratificationAnswer.class));
        lazyListHelper.add(ScheduledArm.class, new InstantiateFactory<ScheduledArm>(
                        ScheduledArm.class));
        eligibilityIndicator = false;
        setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
        currentPosition = new Integer(0);
   }

    /**
     * Instantiates a new scheduled epoch.
     * 
     * @param forExample the for example
     */
    public ScheduledEpoch(boolean forExample) {
        if (!forExample) {
            this.startDate = new Date();
            this.scEpochWorkflowStatus = ScheduledEpochWorkFlowStatus.PENDING;

        }
    }

    /**
     * Sets the disapproval reason text.
     * 
     * @param disapprovalReasonText the new disapproval reason text
     */
    public void setDisapprovalReasonText(String disapprovalReasonText) {
        this.disapprovalReasonText = disapprovalReasonText;
    }

    /**
     * Gets the sc epoch data entry status.
     * 
     * @return the sc epoch data entry status
     */
    @Enumerated(EnumType.STRING)
    public ScheduledEpochDataEntryStatus getScEpochDataEntryStatus() {
        return scEpochDataEntryStatus;
    }

    /**
     * Sets the sc epoch data entry status.
     * 
     * @param scheduledEpochDataEntryStatus the new sc epoch data entry status
     */
    public void setScEpochDataEntryStatus(
                    ScheduledEpochDataEntryStatus scheduledEpochDataEntryStatus) {
        this.scEpochDataEntryStatus = scheduledEpochDataEntryStatus;
    }

    /**
     * Gets the sc epoch workflow status.
     * 
     * @return the sc epoch workflow status
     */
    @Enumerated(EnumType.STRING)
    public ScheduledEpochWorkFlowStatus getScEpochWorkflowStatus() {
        return scEpochWorkflowStatus;
    }

    /**
     * Sets the sc epoch workflow status.
     * 
     * @param scheduledEpochWorkFlowStatus the new sc epoch workflow status
     */
    public void setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus scheduledEpochWorkFlowStatus) {
        this.scEpochWorkflowStatus = scheduledEpochWorkFlowStatus;
    }

    /**
     * Gets the epoch.
     * 
     * @return the epoch
     */
    @ManyToOne
    @JoinColumn(name = "eph_id",nullable = false)
    public Epoch getEpoch() {
        return epoch;
    }

    /**
     * Sets the epoch.
     * 
     * @param epoch the new epoch
     */
    public void setEpoch(Epoch epoch) {
        this.epoch = epoch;
    }

    /**
     * Gets the start date.
     * 
     * @return the start date
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date.
     * 
     * @param startDate the new start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /*
     * @Override public int hashCode() { final int PRIME = 31; int result = super.hashCode(); result =
     * PRIME * result + ((arms == null) ? 0 : arms.hashCode()); result = PRIME * result + ((name ==
     * null) ? 0 : name.hashCode()); return result; }
     * 
     * @Override public boolean equals(Object obj) { if (this == obj) return true; if
     * (!super.equals(obj)) return false; if (getClass() != obj.getClass()) return false; final
     * Epoch other = (Epoch) obj; if (arms == null) { if (other.arms != null) return false; } else
     * if (!arms.equals(other.arms)) return false; if (name == null) { if (other.name != null)
     * return false; } else if (!name.equals(other.name)) return false; return true; }
     */

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(ScheduledEpoch scheduledEpoch) {
        return this.startDate.compareTo(scheduledEpoch.getStartDate());
    }

    /**
     * Gets the requires arm.
     * 
     * @return true, if the epoch requires arm
     */
    @Transient
    public boolean getRequiresArm() {
        return this.getEpoch().getRequiresArm()==null?false:this.getEpoch().getRequiresArm();
    }

    /**
     * Checks if is reserving.
     * 
     * @return true, if epoch is reserving
     */
    @Transient
    public boolean isReserving() {
        return this.getEpoch().isReserving();
    }

    /**
     * Gets the requires randomization.
     * 
     * @return the requires randomization
     */
    @Transient
    public Boolean getRequiresRandomization() {
        return this.getEpoch().getRandomizedIndicator();
    }
    
    /** The lazy list helper. */
    private LazyListHelper lazyListHelper;

    /** The eligibility waiver reason text. */
    private String eligibilityWaiverReasonText;

    /** The eligibility indicator. */
    private Boolean eligibilityIndicator;
    
    /** The current position. */
    private Integer currentPosition;

    // / LOGIC
    /**
     * Sets the eligibility waiver reason text.
     * 
     * @param eligibilityWaiverReasonText the new eligibility waiver reason text
     */
    public void setEligibilityWaiverReasonText(String eligibilityWaiverReasonText) {
        this.eligibilityWaiverReasonText = eligibilityWaiverReasonText;
    }

    /**
     * Gets the eligibility waiver reason text.
     * 
     * @return the eligibility waiver reason text
     */
    public String getEligibilityWaiverReasonText() {
        return eligibilityWaiverReasonText;
    }

    /**
     * Gets the eligibility indicator.
     * 
     * @return the eligibility indicator
     */
    public Boolean getEligibilityIndicator() {
        return eligibilityIndicator;
    }

    /**
     * Sets the eligibility indicator.
     * 
     * @param eligibilityIndicator the new eligibility indicator
     */
    public void setEligibilityIndicator(Boolean eligibilityIndicator) {
        this.eligibilityIndicator = eligibilityIndicator;
    }

    /**
     * Gets the inclusion eligibility answers.
     * 
     * @return the inclusion eligibility answers
     */
    @Transient
    public List<SubjectEligibilityAnswer> getInclusionEligibilityAnswers() {
        List<SubjectEligibilityAnswer> inclusionCriteriaAnswers = new ArrayList<SubjectEligibilityAnswer>();
        for (int i = 0; i < getSubjectEligibilityAnswers().size(); i++) {
            if (getSubjectEligibilityAnswers().get(i).getEligibilityCriteria() instanceof InclusionEligibilityCriteria) {
                inclusionCriteriaAnswers.add(getSubjectEligibilityAnswers().get(i));
            }
        }
        return inclusionCriteriaAnswers;
    }

    /**
     * Gets the exclusion eligibility answers.
     * 
     * @return the exclusion eligibility answers
     */
    @Transient
    public List<SubjectEligibilityAnswer> getExclusionEligibilityAnswers() {
        List<SubjectEligibilityAnswer> exclusionCriteriaAnswers = new ArrayList<SubjectEligibilityAnswer>();
        for (int i = 0; i < getSubjectEligibilityAnswers().size(); i++) {
            if (getSubjectEligibilityAnswers().get(i).getEligibilityCriteria() instanceof ExclusionEligibilityCriteria) {
                exclusionCriteriaAnswers.add(getSubjectEligibilityAnswers().get(i));
            }
        }
        return exclusionCriteriaAnswers;
    }

    /**
     * Gets the subject eligibility answers internal.
     * 
     * @return the subject eligibility answers internal
     */
    @OneToMany
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "SCEPH_ID", nullable = false)
    public List<SubjectEligibilityAnswer> getSubjectEligibilityAnswersInternal() {
        return lazyListHelper.getInternalList(SubjectEligibilityAnswer.class);
    }

    /**
     * Sets the subject eligibility answers internal.
     * 
     * @param subjectEligibilityAnswers the new subject eligibility answers internal
     */
    public void setSubjectEligibilityAnswersInternal(
                    List<SubjectEligibilityAnswer> subjectEligibilityAnswers) {
        lazyListHelper.setInternalList(SubjectEligibilityAnswer.class, subjectEligibilityAnswers);
    }

    /**
     * Gets the subject eligibility answers.
     * 
     * @return the subject eligibility answers
     */
    @Transient
    public List<SubjectEligibilityAnswer> getSubjectEligibilityAnswers() {
        return lazyListHelper.getLazyList(SubjectEligibilityAnswer.class);
    }

    /**
     * Adds the subject eligibility answers.
     * 
     * @param subjectEligibilityAnswer the subject eligibility answer
     */
    public void addSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer) {
        lazyListHelper.getLazyList(SubjectEligibilityAnswer.class).add(subjectEligibilityAnswer);
    }

    /**
     * Removes the subject eligibility answers.
     * 
     * @param subjectEligibilityAnswer the subject eligibility answer
     */
    public void removeSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer) {
        lazyListHelper.getLazyList(SubjectEligibilityAnswer.class).remove(subjectEligibilityAnswer);
    }

    /**
     * Gets the subject stratification answers internal.
     * 
     * @return the subject stratification answers internal
     */
    @OneToMany
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "SCEPH_ID", nullable = false)
    public List<SubjectStratificationAnswer> getSubjectStratificationAnswersInternal() {
        return lazyListHelper.getInternalList(SubjectStratificationAnswer.class);
    }

    /**
     * Sets the subject stratification answers internal.
     * 
     * @param subjectStratificationAnswers the new subject stratification answers internal
     */
    public void setSubjectStratificationAnswersInternal(
                    List<SubjectStratificationAnswer> subjectStratificationAnswers) {
        lazyListHelper.setInternalList(SubjectStratificationAnswer.class,
                        subjectStratificationAnswers);
    }

    /**
     * Gets the subject stratification answers.
     * 
     * @return the subject stratification answers
     */
    @Transient
    public List<SubjectStratificationAnswer> getSubjectStratificationAnswers() {
        return lazyListHelper.getLazyList(SubjectStratificationAnswer.class);
    }

    /**
     * Adds the subject stratification answers.
     * 
     * @param subjectStratificationAnswer the subject stratification answer
     */
    public void addSubjectStratificationAnswers(
                    SubjectStratificationAnswer subjectStratificationAnswer) {
        lazyListHelper.getLazyList(SubjectStratificationAnswer.class).add(
                        subjectStratificationAnswer);
    }

    /**
     * Removes the subject stratification answers.
     * 
     * @param subjectStratificationAnswer the subject stratification answer
     */
    public void removeSubjectStratificationAnswers(
                    SubjectStratificationAnswer subjectStratificationAnswer) {
        lazyListHelper.getLazyList(SubjectStratificationAnswer.class).remove(
                        subjectStratificationAnswer);
    }

    /**
     * Gets the scheduled arms internal.
     * 
     * @return the scheduled arms internal
     */
    @OneToMany
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "SCEPH_ID", nullable = false)
    public List<ScheduledArm> getScheduledArmsInternal() {
        return lazyListHelper.getInternalList(ScheduledArm.class);
    }

    /**
     * Sets the scheduled arms internal.
     * 
     * @param scheduledArms the new scheduled arms internal
     */
    public void setScheduledArmsInternal(List<ScheduledArm> scheduledArms) {
        lazyListHelper.setInternalList(ScheduledArm.class, scheduledArms);
    }

    /**
     * Gets the scheduled arms.
     * 
     * @return the scheduled arms
     */
    @Transient
    public List<ScheduledArm> getScheduledArms() {
        return lazyListHelper.getLazyList(ScheduledArm.class);
    }

    /**
     * Adds the scheduled arm.
     * 
     * @param scheduledArm the scheduled arm
     */
    public void addScheduledArm(ScheduledArm scheduledArm) {
        lazyListHelper.getLazyList(ScheduledArm.class).add(scheduledArm);
    }

    /**
     * Removes the scheduled arm.
     * 
     * @param scheduledArm the scheduled arm
     */
    public void removeScheduledArm(ScheduledArm scheduledArm) {
        lazyListHelper.getLazyList(ScheduledArm.class).remove(scheduledArm);
    }

    /**
     * Gets the scheduled arm.
     * 
     * @return the scheduled arm
     */
    @Transient
    public ScheduledArm getScheduledArm() {
        List<ScheduledArm> scList = getScheduledArms();
        if (scList.size() == 0) return null;
        return scList.get(scList.size() - 1);
    }

    /**
     * Removes the scheduled arm.
     * 
     * @return the scheduled arm
     */
    public ScheduledArm removeScheduledArm() {
        return getScheduledArms().remove(getScheduledArms().size() - 1);
    }

    /**
     * Evaluate stratification indicator.
     * 
     * @return true, if successful
     */
    private boolean evaluateStratificationIndicator() {
        return (this.stratumGroupNumber==null)?false:true;
    }
    
    /**
     * Evaluate scheduled epoch data entry status.
     * 
     * @param errors the errors
     * 
     * @return the scheduled epoch data entry status
     */
    public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(List<Error>  errors) {
    	boolean hasError= false;
    	if (this.getEpoch().getStratificationIndicator()){
	        if (!this.evaluateStratificationIndicator()) {
	        	hasError= true;
	        	errors.add(new Error("The subject needs to be assgined a  stratum group number on scheduled epoch :" + this.getEpoch().getName()));
	        }
    	}
        if (!this.getEligibilityIndicator()) {
        	hasError= true;
        	errors.add(new Error("The subject does not meet the eligibility criteria on scheduled epoch :" + this.getEpoch().getName()));
        }
        if (this.getRequiresArm()
                        && !this.getRequiresRandomization()
                        && (this.getScheduledArm() == null || this
                                        .getScheduledArm().getArm() == null)) {
        	hasError= true;
        	errors.add(new Error("The subject is not assigned to a scheduled arm"));
        }
        if(hasError){
        	return ScheduledEpochDataEntryStatus.INCOMPLETE;
        }
        return ScheduledEpochDataEntryStatus.COMPLETE;
    }

	/**
	 * Gets the current position.
	 * 
	 * @return the current position
	 */
	public Integer getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Sets the current position.
	 * 
	 * @param currentPosition the new current position
	 */
	public void setCurrentPosition(Integer currentPosition) {
		this.currentPosition = currentPosition;
	}
    
}