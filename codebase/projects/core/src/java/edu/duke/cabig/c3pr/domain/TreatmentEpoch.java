package edu.duke.cabig.c3pr.domain;

import java.util.Iterator;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@DiscriminatorValue(value = "TREATMENT")
public class TreatmentEpoch extends Epoch {
	private LazyListHelper lazyListHelper;
	private Randomization randomization;
	private Boolean randomizedIndicator = false;

	private ParameterizedInstantiateFactory<EligibilityCriteria> eligibilityFactory;

	// / LOGIC
	public TreatmentEpoch() {
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(StratificationCriterion.class,
				new InstantiateFactory<StratificationCriterion>(
						StratificationCriterion.class));
		eligibilityFactory = new ParameterizedInstantiateFactory();
		lazyListHelper.add(EligibilityCriteria.class, eligibilityFactory);
		// lazyListHelper.add(EligibilityCriteria.class, new
		// InstantiateFactory<EligibilityCriteria>(EligibilityCriteria.class));
		lazyListHelper.add(Arm.class, new BiDirectionalInstantiateFactory<Arm>(
				Arm.class, this));
		lazyListHelper.add(InclusionEligibilityCriteria.class,
				new InstantiateFactory<InclusionEligibilityCriteria>(
						InclusionEligibilityCriteria.class));
		lazyListHelper.add(ExclusionEligibilityCriteria.class,
				new InstantiateFactory<ExclusionEligibilityCriteria>(
						ExclusionEligibilityCriteria.class));
		lazyListHelper.add(StratumGroup.class,
				new InstantiateFactory<StratumGroup>(StratumGroup.class));		
	}

	public void addNewArm(String armName) {
		Arm arm = new Arm();
		arm.setName(armName);
		addArm(arm);
	}

	public void addArm(Arm arm) {
		for (Arm armPresent : getArms()) {
			if (armPresent.equals(arm)) {
				throw new RuntimeException("arm with same name already exists in epoch");
			}
		}
		getArms().add(arm);
		arm.setTreatmentEpoch(this);
	}
	
	@Override
	@Transient
	/*
	 * settting the retired_indicator for every child object to true
	 */
	public void setRetiredIndicatorAsTrue(){
		//setting the indicator for epoch
		super.setRetiredIndicatorAsTrue();
		//setting the indicator for its arms
		List<Arm> armList = this.getArms();
		Arm arm;
		Iterator armIter = armList.iterator();
		while(armIter.hasNext()){
			arm = (Arm)armIter.next();
			arm.setRetiredIndicatorAsTrue();
		}
		//setting the indicator for its eligibilityCriteria
		List<EligibilityCriteria> ecList = this.getEligibilityCriteria();
		EligibilityCriteria ec;
		Iterator ecIter = ecList.iterator();
		while(ecIter.hasNext()){
			ec = (EligibilityCriteria)ecIter.next();
			ec.setRetiredIndicatorAsTrue();
		}
		
		List<InclusionEligibilityCriteria> iecList = this.getInclusionEligibilityCriteria();
		InclusionEligibilityCriteria iec;
		Iterator iecIter = iecList.iterator();
		while(iecIter.hasNext()){
			iec = (InclusionEligibilityCriteria)iecIter.next();
			iec.setRetiredIndicatorAsTrue();
		}
		
		List<ExclusionEligibilityCriteria> eecList = this.getExclusionEligibilityCriteria();
		ExclusionEligibilityCriteria eec;
		Iterator eecIter = eecList.iterator();
		while(eecIter.hasNext()){
			eec = (ExclusionEligibilityCriteria)eecIter.next();
			eec.setRetiredIndicatorAsTrue();
		}
		
		//set strGrps and Randomizations
		this.getRandomization().setRetiredIndicatorAsTrue();
		//set strCri
		List<StratificationCriterion> scList = this.getStratificationCriteria();
		StratificationCriterion sc;
		Iterator scIter = scList.iterator();
		while(scIter.hasNext()){
			sc = (StratificationCriterion)scIter.next();
			sc.setRetiredIndicatorAsTrue();
		}
		
		List<StratumGroup> sgList = this.getStratumGroups();
		StratumGroup sg;
		Iterator sgIter = sgList.iterator();
		while(sgIter.hasNext()){
			sg = (StratumGroup)sgIter.next();
			sg.setRetiredIndicatorAsTrue();
		}
	}
	

	@Transient
	public boolean isMultipleArms() {
		return getArms().size() > 1;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name="eph_id")
	@Where(clause = "retired_indicator  = 'false'")
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

	public void addEligibilityCriterion(EligibilityCriteria eligibilityCriterion) {
		this.getEligibilityCriteria().add(eligibilityCriterion);
	}

	@Transient
	public List<EligibilityCriteria> getEligibilityCriteria() {
		return lazyListHelper.getLazyList(EligibilityCriteria.class);
	}

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "EPH_ID")
	@Where(clause = "retired_indicator  = 'false'")
	public List<EligibilityCriteria> getEligibilityCriteriaInternal() {
		return lazyListHelper.getInternalList(EligibilityCriteria.class);
	}

	public void setEligibilityCriteria(
			List<EligibilityCriteria> eligibilityCriteria) {
		lazyListHelper.setInternalList(EligibilityCriteria.class,
				eligibilityCriteria);
	}

	public void setEligibilityCriteriaInternal(
			List<EligibilityCriteria> eligibilityCriteria) {
		lazyListHelper.setInternalList(EligibilityCriteria.class,
				eligibilityCriteria);
	}

		
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "EPH_ID")
	@Where(clause = "DTYPE = 'E' and retired_indicator  = 'false'")
	public List<ExclusionEligibilityCriteria> getExclusionEligibilityCriteriaInternal() {
		return lazyListHelper
				.getInternalList(ExclusionEligibilityCriteria.class);
	}

	@Transient
	public List<ExclusionEligibilityCriteria> getExclusionEligibilityCriteria() {
		return lazyListHelper.getLazyList(ExclusionEligibilityCriteria.class);
	}

	public void setExclusionEligibilityCriteria(
			List<ExclusionEligibilityCriteria> exclusionEligibilityCriteria) {
		lazyListHelper.setInternalList(ExclusionEligibilityCriteria.class,
				exclusionEligibilityCriteria);
	}

	public void setExclusionEligibilityCriteriaInternal(
			List<ExclusionEligibilityCriteria> exclusionEligibilityCriteria) {
		lazyListHelper.setInternalList(ExclusionEligibilityCriteria.class,
				exclusionEligibilityCriteria);

	}

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "EPH_ID")
	@Where(clause = "retired_indicator  = 'false'")
	public List<StratificationCriterion> getStratificationCriteriaInternal() {
		return lazyListHelper.getInternalList(StratificationCriterion.class);
	}

	@Transient
	public List<StratificationCriterion> getStratificationCriteria() {
		return lazyListHelper.getLazyList(StratificationCriterion.class);
	}

	public void setStratificationCriteria(
			List<StratificationCriterion> stratificationCriteria) {
		lazyListHelper.setInternalList(StratificationCriterion.class,
				stratificationCriteria);
	}

	public void setStratificationCriteriaInternal(
			List<StratificationCriterion> stratificationCriteria) {
		lazyListHelper.setInternalList(StratificationCriterion.class,
				stratificationCriteria);
	}

	public void addStratificationCriterion(
			StratificationCriterion stratificationCriterion) {
		this.getStratificationCriteria().add(stratificationCriterion);
	}

				
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "EPH_ID")
	@Where(clause = "DTYPE = 'I' and retired_indicator  = 'false'")
	public List<InclusionEligibilityCriteria> getInclusionEligibilityCriteriaInternal() {
		return lazyListHelper
				.getInternalList(InclusionEligibilityCriteria.class);
	}
	
	@OneToMany (fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "epochs_id")
    @Where(clause = "retired_indicator  = 'false'")
    @OrderBy("stratumGroupNumber")
	public List<StratumGroup> getStratumGroupsInternal() {
		return lazyListHelper.getInternalList(StratumGroup.class);
	}

	public void setStratumGroupsInternal(
			List<StratumGroup> stratumGroup) {
		lazyListHelper.setInternalList(StratumGroup.class, stratumGroup);
	}
	
	@Transient
	public List<StratumGroup> getStratumGroups() {
		return lazyListHelper.getLazyList(StratumGroup.class);
	}

	public void setStratumGroups(
			List<StratumGroup> stratumGroup) {	
	}	

	@Transient
	public List<InclusionEligibilityCriteria> getInclusionEligibilityCriteria() {
		return lazyListHelper.getLazyList(InclusionEligibilityCriteria.class);
	}

	public void setInclusionEligibilityCriteria(
			List<InclusionEligibilityCriteria> inclusionEligibilityCriteria) {
		lazyListHelper.setInternalList(InclusionEligibilityCriteria.class,
				inclusionEligibilityCriteria);
	}

	public void setInclusionEligibilityCriteriaInternal(
			List<InclusionEligibilityCriteria> inclusionEligibilityCriteria) {
		lazyListHelper.setInternalList(InclusionEligibilityCriteria.class,
				inclusionEligibilityCriteria);

	}

	@OneToOne
	@JoinColumn(name = "rndm_id")
/*	@JoinColumns ({
        @JoinColumn(name="ID", referencedColumnName = "eph_id")
    })
*/	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public Randomization getRandomization() {
		return randomization;
	}

	public void setRandomization(Randomization randomization) {
		this.randomization = randomization;
	}
		
	@Transient
	public boolean hasBookRandomizationEntry(){
		
		if(this.getRandomization()instanceof BookRandomization){
			if(((((BookRandomization) this.getRandomization()).getBookRandomizationEntry())!=null)&&((BookRandomization) this.getRandomization()).getBookRandomizationEntry().size()>0){
				return true;
			}
		}
		
		return false;
	}

	@Transient
	/*
	 * This method iterates thru the stratum groups for the treatmentEpoch and finds the
	 * one that has the same stratificationCriAnsCombination as the one passed in and returns it.
	 * returns null if no matching stratum group is found
	 */
	public StratumGroup getStratumGroupForAnsCombination(List<StratificationCriterionAnswerCombination> scacList){
		StratumGroup sg = new StratumGroup();
		sg.getStratificationCriterionAnswerCombination().addAll(scacList);
		
		List <StratumGroup>sgList;
		sgList = this.getStratumGroups();
		Iterator iter = sgList.iterator();
		StratumGroup sgCurr;
		while(iter.hasNext()){
			sgCurr = (StratumGroup)iter.next();
			if(sgCurr.equals(sg)){
				return sgCurr;
			}
		}
		return null;		
	}		
	
	@Transient
	/*
	 * This method iterates thru the stratum groups for the treatmentEpoch and finds the
	 * one that has the same stratum group number as the one passed in and returns it.
	 * returns null if no matching stratum group is found
	 */
	public StratumGroup getStratumGroupByNumber(Integer number){
		StratumGroup sg = new StratumGroup();
		for(StratumGroup sgCurr:this.getStratumGroups()){
			if(sgCurr.getStratumGroupNumber().equals(number)){
				return sgCurr;
			}
		}
		return null;		
	}		

	@Transient
	public boolean hasStratification(){
	return (getStratificationCriteria().size()>0);
	}
	
	@Transient
	public boolean hasStratumGroups(){
		return (getStratumGroups().size()>0);
	}
	
	@Transient
	public boolean hasEligibility(){
		return (getEligibilityCriteria().size()>0);
	}
	
	@Override
	@Transient
	public boolean isEnrolling() {
		return true;
	}

	public Boolean getRandomizedIndicator() {
		return randomizedIndicator;
	}

	public void setRandomizedIndicator(Boolean randomizedIndicator) {
		this.randomizedIndicator = randomizedIndicator;
	}

	@Override
	@Transient
	public boolean getRequiresRandomization() {
		return this.randomizedIndicator;
	}
}