package edu.duke.cabig.c3pr.domain;

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
import org.hibernate.annotations.Where;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

@Entity
@DiscriminatorValue(value = "TREATMENT")
public class TreatmentEpoch extends Epoch {
	private LazyListHelper lazyListHelper;

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
	@Where(clause = "DTYPE = 'E'")
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
	@Where(clause = "DTYPE = 'I'")
	public List<InclusionEligibilityCriteria> getInclusionEligibilityCriteriaInternal() {
		return lazyListHelper
				.getInternalList(InclusionEligibilityCriteria.class);
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


}
