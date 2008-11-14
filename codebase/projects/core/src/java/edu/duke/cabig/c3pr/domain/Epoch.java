package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * @author Ram Chilukuri, Priyatam
 */
@Entity
@Table(name = "epochs", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"stu_id", "name" }) })
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "EPOCHS_ID_SEQ") })
public class Epoch extends AbstractMutableDeletableDomainObject implements
		Comparable<Epoch> {

	private String name;

	private String descriptionText;

	private Study study;

	private Integer epochOrder;

	private LazyListHelper lazyListHelper;

	private Randomization randomization;

	private Boolean randomizedIndicator = false;

	private List<EligibilityCriteria> eligibilityCriteria;

	private C3PRExceptionHelper c3PRExceptionHelper;

	private MessageSource c3prErrorMessages;

	private Integer accrualCeiling;

	private Boolean accrualIndicator = false;

	private Boolean reservationIndicator = false;

	private Boolean enrollmentIndicator = false;

	private Boolean stratificationIndicator = false;

	private Boolean treatmentIndicator = false;

	/**
	 * Factory method
	 * 
	 * @param epochName
	 * @param armNames
	 * @return
	 */

	public static Epoch createEpochWithArms(String epochName,
			String... armNames) {
		Epoch epoch = new Epoch();
		epoch.setName(epochName);
		if (armNames.length == 0) {
			epoch.addNewArm(epochName);
		} else {
			for (String armName : armNames) {
				epoch.addNewArm(armName);
			}
		}
		return epoch;
	}

	public static Epoch createEpoch(String epochName) {
		Epoch epoch = new Epoch();
		epoch.setName(epochName);
		return epoch;
	}

	@Transient
	public Boolean getRequiresArm() {
		return (this.getRandomizedIndicator() || this.getArms().size() > 0);
	}

	@Transient
	public boolean isReserving() {
		return this.reservationIndicator;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stu_id", nullable = false)
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public int compareTo(Epoch o) {
		if (this.equals(o))
			return 0;
		else
			return 1;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		final Epoch other = (Epoch) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equalsIgnoreCase(other.name))
			return false;
		return true;
	}

	public Integer getEpochOrder() {
		return epochOrder;
	}

	public void setEpochOrder(Integer epochOrder) {
		this.epochOrder = epochOrder;
	}

	// Code from Non-Treatment Epoch

	public Integer getAccrualCeiling() {
		return accrualCeiling;
	}

	public void setAccrualCeiling(Integer accrualCeiling) {
		this.accrualCeiling = accrualCeiling;
	}

	public Boolean getAccrualIndicator() {
		return accrualIndicator;
	}

	public void setAccrualIndicator(Boolean accrualIndicator) {
		this.accrualIndicator = accrualIndicator;
	}

	public Boolean getEnrollmentIndicator() {
		return enrollmentIndicator;
	}

	public void setEnrollmentIndicator(Boolean enrollmentIndicator) {
		this.enrollmentIndicator = enrollmentIndicator;
	}

	public Boolean getReservationIndicator() {
		return reservationIndicator;
	}

	public void setReservationIndicator(Boolean reservationIndicator) {
		this.reservationIndicator = reservationIndicator;
	}

	// No-Arg Constructor for Hibernate

	public Epoch() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1
				.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(StratificationCriterion.class,
				new InstantiateFactory<StratificationCriterion>(
						StratificationCriterion.class));
		lazyListHelper.add(InclusionEligibilityCriteria.class,
				new ParameterizedInstantiateFactory<InclusionEligibilityCriteria>(
						InclusionEligibilityCriteria.class));
		lazyListHelper
				.add(
						ExclusionEligibilityCriteria.class,
						new ParameterizedInstantiateFactory<ExclusionEligibilityCriteria>(
								ExclusionEligibilityCriteria.class));
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
		setEligibilityCriteria(new ArrayList<EligibilityCriteria>());

	}

	// / LOGIC

	public void addNewArm(String armName) {
		Arm arm = new Arm();
		arm.setName(armName);
		addArm(arm);
	}

	public void addArm(Arm arm) {
		for (Arm armPresent : getArms()) {
			if (armPresent.equals(arm)) {
				throw new RuntimeException(
						"arm with same name already exists in epoch");
			}
		}
		getArms().add(arm);
		arm.setEpoch(this);
	}

	@Override
	@Transient
	/*
	 * settting the retired_indicator for every child object to true
	 */
	public void setRetiredIndicatorAsTrue() {
		// setting the indicator for epoch
		super.setRetiredIndicatorAsTrue();
		// setting the indicator for its arms
		List<Arm> armList = this.getArms();
		Arm arm;
		Iterator armIter = armList.iterator();
		while (armIter.hasNext()) {
			arm = (Arm) armIter.next();
			arm.setRetiredIndicatorAsTrue();
		}
		// setting the indicator for its eligibilityCriteria
		List<EligibilityCriteria> ecList = this.getEligibilityCriteria();
		EligibilityCriteria ec;
		Iterator ecIter = ecList.iterator();
		while (ecIter.hasNext()) {
			ec = (EligibilityCriteria) ecIter.next();
			ec.setRetiredIndicatorAsTrue();
		}

		List<InclusionEligibilityCriteria> iecList = this
				.getInclusionEligibilityCriteria();
		InclusionEligibilityCriteria iec;
		Iterator iecIter = iecList.iterator();
		while (iecIter.hasNext()) {
			iec = (InclusionEligibilityCriteria) iecIter.next();
			iec.setRetiredIndicatorAsTrue();
		}

		List<ExclusionEligibilityCriteria> eecList = this
				.getExclusionEligibilityCriteria();
		ExclusionEligibilityCriteria eec;
		Iterator eecIter = eecList.iterator();
		while (eecIter.hasNext()) {
			eec = (ExclusionEligibilityCriteria) eecIter.next();
			eec.setRetiredIndicatorAsTrue();
		}

		// set strGrps and Randomizations
		if (this.getRandomization() != null) {
			this.getRandomization().setRetiredIndicatorAsTrue();
		}
		// set strCri
		List<StratificationCriterion> scList = this.getStratificationCriteria();
		StratificationCriterion sc;
		Iterator scIter = scList.iterator();
		while (scIter.hasNext()) {
			sc = (StratificationCriterion) scIter.next();
			sc.setRetiredIndicatorAsTrue();
		}

		List<StratumGroup> sgList = this.getStratumGroups();
		StratumGroup sg;
		Iterator sgIter = sgList.iterator();
		while (sgIter.hasNext()) {
			sg = (StratumGroup) sgIter.next();
			sg.setRetiredIndicatorAsTrue();
		}
	}

	@Transient
	public boolean isMultipleArms() {
		return getArms().size() > 1;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "eph_id")
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
	
	@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "EPH_ID")
	@Where(clause = "retired_indicator  = 'false'")
	public List<EligibilityCriteria> getEligibilityCriteria() {
		return eligibilityCriteria;
	}

	public void setEligibilityCriteria(List<EligibilityCriteria> eligibilityCriteria) {
		this.eligibilityCriteria = eligibilityCriteria;
		lazyListHelper.setInternalList(InclusionEligibilityCriteria.class,
				new ProjectedList<InclusionEligibilityCriteria>(this.eligibilityCriteria,
						InclusionEligibilityCriteria.class));
		lazyListHelper
				.setInternalList(ExclusionEligibilityCriteria.class,
						new ProjectedList<ExclusionEligibilityCriteria>(
								this.eligibilityCriteria,
								ExclusionEligibilityCriteria.class));
	}

	@Transient
	public List<InclusionEligibilityCriteria> getInclusionEligibilityCriteria() {
		return lazyListHelper.getLazyList(InclusionEligibilityCriteria.class);
	}

	public void setInclusionEligibilityCriteria(
			List<InclusionEligibilityCriteria> inclusionEligibilityCriteria) {
		// do nothing
	}

	@Transient
	public List<ExclusionEligibilityCriteria> getExclusionEligibilityCriteria() {
		return lazyListHelper.getLazyList(ExclusionEligibilityCriteria.class);
	}

	public void setExclusionEligibilityCriteria(
			List<ExclusionEligibilityCriteria> exclusionEligibilityCriteria) {
		// do nothing
	}

	

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "epochs_id")
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy("stratumGroupNumber")
	public List<StratumGroup> getStratumGroupsInternal() {
		return lazyListHelper.getInternalList(StratumGroup.class);
	}
	
	public void setStratumGroupsInternal(List<StratumGroup> stratumGroup) {
		lazyListHelper.setInternalList(StratumGroup.class, stratumGroup);
	}

	@Transient
	public List<StratumGroup> getStratumGroups() {
		return lazyListHelper.getLazyList(StratumGroup.class);
	}

	public void setStratumGroups(List<StratumGroup> stratumGroup) {
	}

	

	@OneToOne
	@JoinColumn(name = "rndm_id")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public Randomization getRandomization() {
		return randomization;
	}

	public void setRandomization(Randomization randomization) {
		this.randomization = randomization;
	}

	@Transient
	public boolean hasBookRandomizationEntry() {

		if (this.getRandomization() instanceof BookRandomization) {
			if (((((BookRandomization) this.getRandomization())
					.getBookRandomizationEntry()) != null)
					&& ((BookRandomization) this.getRandomization())
							.getBookRandomizationEntry().size() > 0) {
				return true;
			}
		}

		return false;
	}

	@Transient
	/*
	 * This method iterates thru the stratum groups for the treatmentEpoch and
	 * finds the one that has the same stratificationCriAnsCombination as the
	 * one passed in and returns it. returns null if no matching stratum group
	 * is found
	 */
	public StratumGroup getStratumGroupForAnsCombination(
			List<StratificationCriterionAnswerCombination> scacList) {
		StratumGroup sg = new StratumGroup();
		sg.getStratificationCriterionAnswerCombination().addAll(scacList);

		List<StratumGroup> sgList;
		sgList = this.getStratumGroups();
		Iterator iter = sgList.iterator();
		StratumGroup sgCurr;
		while (iter.hasNext()) {
			sgCurr = (StratumGroup) iter.next();
			if (sgCurr.equals(sg)) {
				return sgCurr;
			}
		}
		return null;
	}

	@Transient
	/*
	 * This method iterates thru the stratum groups for the treatmentEpoch and
	 * finds the one that has the same stratum group number as the one passed in
	 * and returns it. returns null if no matching stratum group is found
	 */
	public StratumGroup getStratumGroupByNumber(Integer number) {
		StratumGroup sg = new StratumGroup();
		for (StratumGroup sgCurr : this.getStratumGroups()) {
			if (sgCurr.getStratumGroupNumber().equals(number)) {
				return sgCurr;
			}
		}
		return null;
	}

	@Transient
	public boolean hasStratification() {
		return (getStratificationCriteria().size() > 0);
	}

	@Transient
	public boolean hasStratumGroups() {
		return (getStratumGroups().size() > 0);
	}

	@Transient
	public boolean hasEligibility() {
		return (getEligibilityCriteria().size() > 0);
	}

	public Boolean getRandomizedIndicator() {
		return randomizedIndicator;
	}

	public void setRandomizedIndicator(Boolean randomizedIndicator) {
		this.randomizedIndicator = randomizedIndicator;
	}

	public boolean evaluateStatus(List<Error> errors) throws C3PRCodedRuntimeException {
		if (!evaluateStratificationDataEntryStatus(errors))
			return false;
		if (!evaluateRandomizationDataEntryStatus(errors)) {
			return false;
		}
		if (!evaluateEligibilityDataEntryStatus(errors))
			return false;

		return true;
	}

	public boolean evaluateStratificationDataEntryStatus(List<Error> errors)
			throws C3PRCodedRuntimeException {
		
			if (this.getStratificationIndicator()) {
				if (!this.hasStratification() || !this.hasStratumGroups()) {
					errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STRATIFICATION_CRITERIA_OR_STRATUM_GROUPS_FOR_RANDOMIZED_EPOCH.CODE"),
							new String[] { this.getName() }).getMessage()));
				}
			}

		return true;

	}

	public boolean evaluateRandomizationDataEntryStatus(List<Error> errors)
			throws C3PRCodedRuntimeException {
		
		if (this.getRandomizedIndicator()) {
			if ((this.getArms().size() < 2)||(this.getRandomization() == null)) {
				if (this.getArms().size() < 2) {
					errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ATLEAST_2_ARMS_FOR_RANDOMIZED_EPOCH.CODE"),
							new String[] { this.getName() }).getMessage()));
				}


			}
		}
		
		if (this.study.getRandomizationType() == (RandomizationType.BOOK)) {
			if (this.getRandomizedIndicator()) {
				if (!this.hasBookRandomizationEntry()) {
					errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.BOOK_ENTRIES_FOR_BOOK_RANDOMIZED_EPOCH.CODE"),
							new String[] { this.getName() }).getMessage()));
				}
			}
		}

		if (this.study.getRandomizationType() == (RandomizationType.PHONE_CALL)) {
			if (this.getRandomizedIndicator()) {
				Randomization randomization = this.getRandomization();
				if (randomization instanceof PhoneCallRandomization) {
					if (StringUtils
							.isBlank(((PhoneCallRandomization) randomization)
									.getPhoneNumber())) {
						errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(
								getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.PHONE_NUMBER_FOR_PHONE_CALL_RANDOMIZED_EPOCH.CODE"),
								new String[] { this.getName() }).getMessage()));
					}
				}
			}
		}

		if (this.study.getRandomizationType() == (RandomizationType.CALL_OUT)) {
			if (this.getTreatmentIndicator()) {
				Randomization randomization = this.getRandomization();
				if (randomization instanceof CalloutRandomization) {
					if (StringUtils
							.isBlank(((CalloutRandomization) randomization)
									.getCalloutUrl())) {
						errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(
								getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.CALL_OUT_URL_FOR_CALL_OUT_RANDOMIZED_EPOCH.CODE"),
								new String[] { this.getName() }).getMessage()));
					}
				}
			}
		}

		return true;

	}

	public boolean evaluateEligibilityDataEntryStatus(List<Error> errors)
			throws C3PRCodedRuntimeException {
		// Default returns true unless more information is obtained
		return true;
	}

	@Transient
	public int getCode(String errortypeString) {
		return Integer.parseInt(this.c3prErrorMessages.getMessage(
				errortypeString, null, null));
	}

	@Transient
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}

	public void setExceptionHelper(C3PRExceptionHelper c3PRExceptionHelper) {
		this.c3PRExceptionHelper = c3PRExceptionHelper;
	}

	@Transient
	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	@Transient
	public boolean isEnrolling() {
		return enrollmentIndicator;
	}

	public Boolean getStratificationIndicator() {
		return stratificationIndicator;
	}

	public void setStratificationIndicator(Boolean stratificationIndicator) {
		this.stratificationIndicator = stratificationIndicator;
	}

	public Boolean getTreatmentIndicator() {
		return treatmentIndicator;
	}

	public void setTreatmentIndicator(Boolean treatmentIndicator) {
		this.treatmentIndicator = treatmentIndicator;
	}
}