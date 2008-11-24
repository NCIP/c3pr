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
 * @author Ram Chilukuri, Priyatam
 */
@Entity
@Table(name = "scheduled_epochs")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "SCHEDULED_EPOCHS_ID_SEQ") })
public class ScheduledEpoch extends AbstractMutableDeletableDomainObject implements
                Comparable<ScheduledEpoch> {

    private Epoch epoch;

    private Date startDate;

    private ScheduledEpochDataEntryStatus scEpochDataEntryStatus;

    private ScheduledEpochWorkFlowStatus scEpochWorkflowStatus;

    private String disapprovalReasonText;
    
    public Integer getStratumGroupNumber() {
		return stratumGroupNumber;
	}

	public void setStratumGroupNumber(Integer stratumGroupNumber) {
		this.stratumGroupNumber = stratumGroupNumber;
	}
	
	 @Transient 
	    public StratumGroup getStratumGroup() throws C3PRBaseException {
	        StratumGroup stratumGroup = null;
	        if (this.stratumGroupNumber != null) {
	            stratumGroup = getEpoch()
	                            .getStratumGroupByNumber(this.stratumGroupNumber);
	        }
	        else {
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
	        }
	        if (stratumGroup == null) {
	            throw new C3PRBaseException(
	                            "No startum group found. Maybe the answer combination does not have a valid startum group");
	        }
	        return stratumGroup;
	    }

	private Integer stratumGroupNumber;

    public String getDisapprovalReasonText() {
        return disapprovalReasonText;
    }

    public ScheduledEpoch() {
        this.startDate = new Date();
     //   this.scEpochWorkflowStatus = ScheduledEpochWorkFlowStatus.UNAPPROVED;
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

    public ScheduledEpoch(boolean forExample) {
        if (!forExample) {
            this.startDate = new Date();
    //        this.scEpochWorkflowStatus = ScheduledEpochWorkFlowStatus.UNAPPROVED;
            this.scEpochWorkflowStatus = ScheduledEpochWorkFlowStatus.PENDING;

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

    public void setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus scheduledEpochWorkFlowStatus) {
        this.scEpochWorkflowStatus = scheduledEpochWorkFlowStatus;
    }

    @ManyToOne
    @JoinColumn(name = "eph_id",nullable = false)
    public Epoch getEpoch() {
        return epoch;
    }

    public void setEpoch(Epoch epoch) {
        this.epoch = epoch;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartDate() {
        return startDate;
    }

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

    public int compareTo(ScheduledEpoch scheduledEpoch) {
        return this.startDate.compareTo(scheduledEpoch.getStartDate());
    }

    @Transient
    public boolean getRequiresArm() {
        return this.getEpoch().getRequiresArm();
    }

    @Transient
    public boolean isReserving() {
        return this.getEpoch().isReserving();
    }

    @Transient
    public Boolean getRequiresRandomization() {
        return this.getEpoch().getRandomizedIndicator();
    }
    
    private LazyListHelper lazyListHelper;

    private String eligibilityWaiverReasonText;

    private Boolean eligibilityIndicator;
    
    private Integer currentPosition;

    // / LOGIC
    public void setEligibilityWaiverReasonText(String eligibilityWaiverReasonText) {
        this.eligibilityWaiverReasonText = eligibilityWaiverReasonText;
    }

    public String getEligibilityWaiverReasonText() {
        return eligibilityWaiverReasonText;
    }

    public Boolean getEligibilityIndicator() {
        return eligibilityIndicator;
    }

    public void setEligibilityIndicator(Boolean eligibilityIndicator) {
        this.eligibilityIndicator = eligibilityIndicator;
    }

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

    @OneToMany
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "SCEPH_ID", nullable = false)
    public List<SubjectEligibilityAnswer> getSubjectEligibilityAnswersInternal() {
        return lazyListHelper.getInternalList(SubjectEligibilityAnswer.class);
    }

    public void setSubjectEligibilityAnswersInternal(
                    List<SubjectEligibilityAnswer> subjectEligibilityAnswers) {
        lazyListHelper.setInternalList(SubjectEligibilityAnswer.class, subjectEligibilityAnswers);
    }

    @Transient
    public List<SubjectEligibilityAnswer> getSubjectEligibilityAnswers() {
        return lazyListHelper.getLazyList(SubjectEligibilityAnswer.class);
    }

    public void addSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer) {
        lazyListHelper.getLazyList(SubjectEligibilityAnswer.class).add(subjectEligibilityAnswer);
    }

    public void removeSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer) {
        lazyListHelper.getLazyList(SubjectEligibilityAnswer.class).remove(subjectEligibilityAnswer);
    }

    @OneToMany
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "SCEPH_ID", nullable = false)
    public List<SubjectStratificationAnswer> getSubjectStratificationAnswersInternal() {
        return lazyListHelper.getInternalList(SubjectStratificationAnswer.class);
    }

    public void setSubjectStratificationAnswersInternal(
                    List<SubjectStratificationAnswer> subjectStratificationAnswers) {
        lazyListHelper.setInternalList(SubjectStratificationAnswer.class,
                        subjectStratificationAnswers);
    }

    @Transient
    public List<SubjectStratificationAnswer> getSubjectStratificationAnswers() {
        return lazyListHelper.getLazyList(SubjectStratificationAnswer.class);
    }

    public void addSubjectStratificationAnswers(
                    SubjectStratificationAnswer subjectStratificationAnswer) {
        lazyListHelper.getLazyList(SubjectStratificationAnswer.class).add(
                        subjectStratificationAnswer);
    }

    public void removeSubjectStratificationAnswers(
                    SubjectStratificationAnswer subjectStratificationAnswer) {
        lazyListHelper.getLazyList(SubjectStratificationAnswer.class).remove(
                        subjectStratificationAnswer);
    }

    @OneToMany
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "SCEPH_ID", nullable = false)
    public List<ScheduledArm> getScheduledArmsInternal() {
        return lazyListHelper.getInternalList(ScheduledArm.class);
    }

    public void setScheduledArmsInternal(List<ScheduledArm> scheduledArms) {
        lazyListHelper.setInternalList(ScheduledArm.class, scheduledArms);
    }

    @Transient
    public List<ScheduledArm> getScheduledArms() {
        return lazyListHelper.getLazyList(ScheduledArm.class);
    }

    public void addScheduledArm(ScheduledArm scheduledArm) {
        lazyListHelper.getLazyList(ScheduledArm.class).add(scheduledArm);
    }

    public void removeScheduledArm(ScheduledArm scheduledArm) {
        lazyListHelper.getLazyList(ScheduledArm.class).remove(scheduledArm);
    }

    @Transient
    public ScheduledArm getScheduledArm() {
        List<ScheduledArm> scList = getScheduledArms();
        if (scList.size() == 0) return null;
        return scList.get(scList.size() - 1);
    }

    public ScheduledArm removeScheduledArm() {
        return getScheduledArms().remove(getScheduledArms().size() - 1);
    }

    public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(Integer stratumGroupNumber) {
    	if (this.getEpoch().getStratificationIndicator()){
	        if (!this.evaluateStratificationIndicator(stratumGroupNumber)) {
	            return ScheduledEpochDataEntryStatus.INCOMPLETE;
	        }
    	}
        if (!this.getEligibilityIndicator()) {
            return ScheduledEpochDataEntryStatus.INCOMPLETE;
        }
        if (this.getRequiresArm()
                        && !this.getRequiresRandomization()
                        && (this.getScheduledArm() == null || this
                                        .getScheduledArm().getArm() == null)) {
            return ScheduledEpochDataEntryStatus.INCOMPLETE;
        }

        return ScheduledEpochDataEntryStatus.COMPLETE;
    }
    
    private boolean evaluateStratificationIndicator(Integer stratumGroupNumber) {
        return (stratumGroupNumber==null)?false:true;
    }
    
    public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(List<Error>  errors) {
    	if (this.getEpoch().getStratificationIndicator()){
	        if (!this.evaluateStratificationIndicator(stratumGroupNumber)) {
	        	errors.add(new Error("The subject needs to be assgined a  stratum group number on scheduled epoch :" + this.getEpoch().getName()));
	            return ScheduledEpochDataEntryStatus.INCOMPLETE;
	        }
    	}
        if (!this.getEligibilityIndicator()) {
        	errors.add(new Error("The subject does not meet the eligibility criteria on scheduled epoch :" + this.getEpoch().getName()));
            return ScheduledEpochDataEntryStatus.INCOMPLETE;
        }
        if (this.getRequiresArm()
                        && !this.getRequiresRandomization()
                        && (this.getScheduledArm() == null || this
                                        .getScheduledArm().getArm() == null)) {
        	errors.add(new Error("The subject is not assigned to a scheduled arm"));
            return ScheduledEpochDataEntryStatus.INCOMPLETE;
        }

        return ScheduledEpochDataEntryStatus.COMPLETE;
    }

	public Integer getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Integer currentPosition) {
		this.currentPosition = currentPosition;
	}
    
}