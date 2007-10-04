package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@DiscriminatorValue(value = "TREATMENT")
public class ScheduledTreatmentEpoch extends ScheduledEpoch {
	private LazyListHelper lazyListHelper;
    private String eligibilityWaiverReasonText;
    private Boolean eligibilityIndicator;

	// / LOGIC
	public ScheduledTreatmentEpoch() {
		super();
		lazyListHelper = new LazyListHelper();
    	lazyListHelper.add(SubjectEligibilityAnswer.class, new InstantiateFactory<SubjectEligibilityAnswer>(SubjectEligibilityAnswer.class));
    	lazyListHelper.add(SubjectStratificationAnswer.class, new InstantiateFactory<SubjectStratificationAnswer>(SubjectStratificationAnswer.class));
    	lazyListHelper.add(ScheduledArm.class, new InstantiateFactory<ScheduledArm>(ScheduledArm.class));
    	eligibilityIndicator=false;
    	setScEpochDataEntryStatus(ScheduledEpochDataEntryStatus.INCOMPLETE);
	}
	public ScheduledTreatmentEpoch(boolean forExample) {
		super(forExample);
	}
	
	@Transient
	public TreatmentEpoch getTreatmentEpoch(){
		return (TreatmentEpoch)getEpoch();
	}

	public void setTreatmentEpoch(TreatmentEpoch treatmentEpoch){
		setEpoch(treatmentEpoch);
	}

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
	public List<SubjectEligibilityAnswer> getInclusionEligibilityAnswers(){
		List<SubjectEligibilityAnswer> inclusionCriteriaAnswers=new ArrayList<SubjectEligibilityAnswer>();
		for(int i=0 ; i<getSubjectEligibilityAnswers().size() ; i++){
			if(getSubjectEligibilityAnswers().get(i).getEligibilityCriteria() instanceof InclusionEligibilityCriteria){
				inclusionCriteriaAnswers.add(getSubjectEligibilityAnswers().get(i));
			}
		}
		return inclusionCriteriaAnswers;
	}
	@Transient
	public List<SubjectEligibilityAnswer> getExclusionEligibilityAnswers(){
		List<SubjectEligibilityAnswer> exclusionCriteriaAnswers=new ArrayList<SubjectEligibilityAnswer>();
		for(int i=0 ; i<getSubjectEligibilityAnswers().size() ; i++){
			if(getSubjectEligibilityAnswers().get(i).getEligibilityCriteria() instanceof ExclusionEligibilityCriteria){
				exclusionCriteriaAnswers.add(getSubjectEligibilityAnswers().get(i));
			}
		}
		return exclusionCriteriaAnswers;
	}

	@OneToMany
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "SCEPH_ID", nullable=false)
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
	public void addSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer){
		lazyListHelper.getLazyList(SubjectEligibilityAnswer.class).add(subjectEligibilityAnswer);
	}
	public void removeSubjectEligibilityAnswers(SubjectEligibilityAnswer subjectEligibilityAnswer){
		lazyListHelper.getLazyList(SubjectEligibilityAnswer.class).remove(subjectEligibilityAnswer);
	}
	

	@OneToMany
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "SCEPH_ID", nullable=false)
    public List<SubjectStratificationAnswer> getSubjectStratificationAnswersInternal() {
		return lazyListHelper.getInternalList(SubjectStratificationAnswer.class);
	}
	public void setSubjectStratificationAnswersInternal(
			List<SubjectStratificationAnswer> subjectStratificationAnswers) {
		lazyListHelper.setInternalList(SubjectStratificationAnswer.class, subjectStratificationAnswers);
	}
	@Transient
	public List<SubjectStratificationAnswer> getSubjectStratificationAnswers() {
		return lazyListHelper.getLazyList(SubjectStratificationAnswer.class);
	}
	public void addSubjectStratificationAnswers(SubjectStratificationAnswer subjectStratificationAnswer){
		lazyListHelper.getLazyList(SubjectStratificationAnswer.class).add(subjectStratificationAnswer);
	}
	
	public void removeSubjectStratificationAnswers(SubjectStratificationAnswer subjectStratificationAnswer){
		lazyListHelper.getLazyList(SubjectStratificationAnswer.class).remove(subjectStratificationAnswer);
	}
    
	@OneToMany
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "SCEPH_ID", nullable=false)	    
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

	public void addScheduledArm(ScheduledArm scheduledArm){
		lazyListHelper.getLazyList(ScheduledArm.class).add(scheduledArm);
	}

	public void removeScheduledArm(ScheduledArm scheduledArm){
		lazyListHelper.getLazyList(ScheduledArm.class).remove(scheduledArm);
	}

	@Transient
	public ScheduledArm getScheduledArm() {
		List<ScheduledArm> scList=getScheduledArms();
		if(scList.size()==0)
			return null;
		return scList.get(scList.size()-1);
	}
	public ScheduledArm removeScheduledArm() {
		return getScheduledArms().remove(getScheduledArms().size()-1);
	}
}
