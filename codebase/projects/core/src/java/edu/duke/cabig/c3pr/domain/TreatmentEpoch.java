package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;



@Entity(name="TreatmentEpoch")
@DiscriminatorValue(value="TREATMENT")
public class TreatmentEpoch extends Epoch {
	private LazyListHelper lazyListHelper;
	
//	 / LOGIC
	public TreatmentEpoch() {
		lazyListHelper = new LazyListHelper();
        lazyListHelper.add(StratificationCriterion.class, new InstantiateFactory<StratificationCriterion>(StratificationCriterion.class));
        lazyListHelper.add(EligibilityCriteria.class, new InstantiateFactory<EligibilityCriteria>(EligibilityCriteria.class));
        lazyListHelper.add(Arm.class, new BiDirectionalInstantiateFactory<Arm>(Arm.class,this));
	}

	public void addNewArm(String armName) {
		Arm arm = new Arm();
		arm.setName(armName);
		addArm(arm);
	}

	public void addArm(Arm arm) {
		getArms().add(arm);
		arm.setTreatmentEpoch(this);
	}

	@Transient
	public boolean isMultipleArms() {
		return getArms().size() > 1;
	}
	
	@OneToMany(mappedBy = "treatmentEpoch", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<Arm> getArmsInternal() {
		return lazyListHelper.getInternalList(Arm.class);
	}
	
	@Transient
	public List<Arm> getArms() {
		return lazyListHelper.getLazyList(Arm.class);
	}
	
	public void setArmsInternal(List<Arm> arms) {
		lazyListHelper.setInternalList(Arm.class, arms);
	}
	
	public void setArms(List<Arm> arms) {
		lazyListHelper.setInternalList(Arm.class, arms);
	}
	
	public void addEligibilityCriterion(EligibilityCriteria eligibilityCriterion){
		this.getEligibilityCriteria().add(eligibilityCriterion);
	}

	@Transient
	public List<EligibilityCriteria> getEligibilityCriteria() {
		return lazyListHelper.getLazyList(EligibilityCriteria.class);
	}
	
	@OneToMany (fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "EPH_ID")
	public List<EligibilityCriteria> getEligibilityCriteriaInternal() {
		return lazyListHelper.getInternalList(EligibilityCriteria.class);
	}

	public void setEligibilityCriteria(List<EligibilityCriteria> eligibilityCriteria) {
		lazyListHelper.setInternalList(EligibilityCriteria.class,eligibilityCriteria);
	}
	
	public void setEligibilityCriteriaInternal(List<EligibilityCriteria> eligibilityCriteria) {
		lazyListHelper.setInternalList(EligibilityCriteria.class,eligibilityCriteria);
	}
	
	@Transient
	public List<InclusionEligibilityCriteria> getInclusionEligibilityCriteria(){
		List<InclusionEligibilityCriteria> inclusionCriteria = new ArrayList<InclusionEligibilityCriteria>();
		if (this.getEligibilityCriteria()!=null)
		for(EligibilityCriteria eligibilityCriterion: this.getEligibilityCriteria()){
			if (eligibilityCriterion instanceof InclusionEligibilityCriteria  ){
				inclusionCriteria.add((InclusionEligibilityCriteria)eligibilityCriterion);
			}
		}
		return inclusionCriteria;
	}
	
	@Transient
	public List<ExclusionEligibilityCriteria> getExclusionEligibilityCriteria(){
		List<ExclusionEligibilityCriteria> exclusionCriteria = new ArrayList<ExclusionEligibilityCriteria>();
		if (this.getEligibilityCriteria()!=null)
		for(EligibilityCriteria eligibilityCriterion: this.getEligibilityCriteria()){
			if (eligibilityCriterion instanceof ExclusionEligibilityCriteria  ){
				exclusionCriteria.add((ExclusionEligibilityCriteria)eligibilityCriterion);
			}
		}
		return exclusionCriteria;
	}

	@OneToMany (fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "EPH_ID")
	public List<StratificationCriterion> getStratificationCriteriaInternal() {
		return lazyListHelper.getInternalList(StratificationCriterion.class);
	}

	@Transient
	public List<StratificationCriterion> getStratificationCriteria() {
		return lazyListHelper.getLazyList(StratificationCriterion.class);
	}
	
	public void setStratificationCriteria(
			List<StratificationCriterion> stratificationCriteria) {
		lazyListHelper.setInternalList(StratificationCriterion.class, stratificationCriteria);
	}
	
	public void setStratificationCriteriaInternal(
			List<StratificationCriterion> stratificationCriteria) {
		lazyListHelper.setInternalList(StratificationCriterion.class, stratificationCriteria);
	}
	
	public void addStratificationCriterion(StratificationCriterion stratificationCriterion){
		this.getStratificationCriteria().add(stratificationCriterion);
	}
}
