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
	
//	private List<StratificationCriterion> stratificationCriteria = new ArrayList<StratificationCriterion>();
	
	private List<Arm> arms = new ArrayList<Arm>();
	private List<EligibilityCriteria> eligibilityCriteria= new ArrayList<EligibilityCriteria>();
	private List<StratificationCriterion> stratificationCriteria = new ArrayList<StratificationCriterion>();
	private LazyListHelper lazyListHelper;
	
	public TreatmentEpoch() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(Arm.class, new BiDirectionalInstantiateFactory<Arm>(Arm.class,this));
    }

	
//	 / LOGIC

	public void addNewArm(String armName) {
		Arm arm = new Arm();
		arm.setName(armName);
		addArm(arm);
	}

	public void addArm(Arm arm) {
		arms.add(arm);
		arm.setTreatmentEpoch(this);
	}

	@Transient
	public boolean isMultipleArms() {
		return getArms().size() > 1;
	}
	
	public void addEligibilityCriterion(EligibilityCriteria eligibilityCriterion){
		this.getEligibilityCriteria().add(eligibilityCriterion);
	}
	
	@Transient
	public List<Arm> getArms() {
		return lazyListHelper.getLazyList(Arm.class);
	}
	
	public void setArms(List<Arm> arms) {
		lazyListHelper.setInternalList(Arm.class,arms);
	}
	
	@OneToMany(mappedBy = "treatmentEpoch", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<Arm> getArmsInternal() {
		return lazyListHelper.getInternalList(Arm.class);
	}
	
	public void setArmsInternal(List<Arm> arms) {
		lazyListHelper.setInternalList(Arm.class, arms);
	}
		
	@OneToMany (fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "EPH_ID")
	public List<EligibilityCriteria> getEligibilityCriteria() {
		return eligibilityCriteria;
	}

	public void setEligibilityCriteria(List<EligibilityCriteria> eligibilityCriteria) {
		this.eligibilityCriteria = eligibilityCriteria;
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
	public List<StratificationCriterion> getStratificationCriteria() {
		return stratificationCriteria;
	}

	public void setStratificationCriteria(
			List<StratificationCriterion> stratificationCriteria) {
		this.stratificationCriteria = stratificationCriteria;
	}
	
	public void addStratificationCriterion(StratificationCriterion stratificationCriterion){
		this.getStratificationCriteria().add(stratificationCriterion);
	}
	

}
