package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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

import edu.duke.cabig.c3pr.domain.factory.ParameterizedInstantiateFactory;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * The Class Epoch.
 *
 * @author Ram Chilukuri, Priyatam
 */
@Entity
@Table(name = "epochs", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"stu_version_id", "name" }) })
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "EPOCHS_ID_SEQ") })
public class Epoch extends AbstractMutableDeletableDomainObject{

	/** The name. */
	private String name;

	/** The description text. */
	private String descriptionText;

	/** The epoch order. */
	private Integer epochOrder;

	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;

	/** The randomization. */
	private Randomization randomization;

	/** The randomized indicator. */
	private Boolean randomizedIndicator = false;

	/** The eligibility criteria. */
	private List<EligibilityCriteria> eligibilityCriteria;

	/** The c3 pr exception helper. */
	private C3PRExceptionHelper c3PRExceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;

	/** The accrual ceiling. */
	private Integer accrualCeiling;

	/** The current book randomization entry position. */
	private Integer currentBookRandomizationEntryPosition;

	/** The accrual indicator. */
	private Boolean accrualIndicator = false;

	/** The reservation indicator. */
	private Boolean reservationIndicator = false;

	/** The enrollment indicator. */
	private Boolean enrollmentIndicator = false;

	/** The stratification indicator. */
	private Boolean stratificationIndicator = false;

	/** The treatment indicator. */
	private Boolean treatmentIndicator = false;

	/**
	 * Gets the requires arm.
	 *
	 * @return the requires arm
	 */
	@Transient
	public Boolean getRequiresArm() {
		return (this.getRandomizedIndicator() || this.getArms().size() > 0);
	}

	/**
	 * Checks if is reserving.
	 *
	 * @return true, if is reserving
	 */
	@Transient
	public boolean isReserving() {
		return this.reservationIndicator;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the current book randomization entry position.
	 *
	 * @return the current book randomization entry position
	 */
	@Column(name = "current_bk_rand_entry")
	public Integer getCurrentBookRandomizationEntryPosition() {
		return currentBookRandomizationEntryPosition;
	}

	/**
	 * Sets the current book randomization entry position.
	 *
	 * @param currentBookRandomizationEntryPosition the new current book randomization entry position
	 */
	public void setCurrentBookRandomizationEntryPosition(
			Integer currentBookRandomizationEntryPosition) {
		this.currentBookRandomizationEntryPosition = currentBookRandomizationEntryPosition;
	}

	/**
	 * Sets the description text.
	 *
	 * @param descriptionText the new description text
	 */
	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	/**
	 * Gets the description text.
	 *
	 * @return the description text
	 */
	public String getDescriptionText() {
		return descriptionText;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
	 */
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

	/**
	 * Gets the epoch order.
	 *
	 * @return the epoch order
	 */
	public Integer getEpochOrder() {
		return epochOrder;
	}

	/**
	 * Sets the epoch order.
	 *
	 * @param epochOrder the new epoch order
	 */
	public void setEpochOrder(Integer epochOrder) {
		this.epochOrder = epochOrder;
	}

	/**
	 * Gets the accrual ceiling.
	 *
	 * @return the accrual ceiling
	 */
	public Integer getAccrualCeiling() {
		return accrualCeiling;
	}

	/**
	 * Sets the accrual ceiling.
	 *
	 * @param accrualCeiling the new accrual ceiling
	 */
	public void setAccrualCeiling(Integer accrualCeiling) {
		this.accrualCeiling = accrualCeiling;
	}

	/**
	 * Gets the accrual indicator.
	 *
	 * @return the accrual indicator
	 */
	public Boolean getAccrualIndicator() {
		return accrualIndicator;
	}

	/**
	 * Sets the accrual indicator.
	 *
	 * @param accrualIndicator the new accrual indicator
	 */
	public void setAccrualIndicator(Boolean accrualIndicator) {
		this.accrualIndicator = accrualIndicator;
	}

	/**
	 * Gets the enrollment indicator.
	 *
	 * @return the enrollment indicator
	 */
	public Boolean getEnrollmentIndicator() {
		return enrollmentIndicator;
	}

	/**
	 * Sets the enrollment indicator.
	 *
	 * @param enrollmentIndicator the new enrollment indicator
	 */
	public void setEnrollmentIndicator(Boolean enrollmentIndicator) {
		this.enrollmentIndicator = enrollmentIndicator;
	}

	/**
	 * Gets the reservation indicator.
	 *
	 * @return the reservation indicator
	 */
	public Boolean getReservationIndicator() {
		return reservationIndicator;
	}

	/**
	 * Sets the reservation indicator.
	 *
	 * @param reservationIndicator the new reservation indicator
	 */
	public void setReservationIndicator(Boolean reservationIndicator) {
		this.reservationIndicator = reservationIndicator;
	}

	// No-Arg Constructor for Hibernate

	/**
	 * Instantiates a new epoch.
	 */
	public Epoch() {

		currentBookRandomizationEntryPosition = new Integer(0);
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
		lazyListHelper.add(Arm.class, new InstantiateFactory<Arm>(Arm.class));
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

	/**
	 * Adds the arm.
	 *
	 * @param arm the arm
	 */
	public void addArm(Arm arm) {
		for (Arm armPresent : getArms()) {
			if (armPresent.equals(arm)) {
				throw new RuntimeException(
						"arm with same name already exists in epoch");
			}
		}
		getArms().add(arm);
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#setRetiredIndicatorAsTrue()
	 */
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

	/**
	 * Checks if is multiple arms.
	 *
	 * @return true, if is multiple arms
	 */
	@Transient
	public boolean isMultipleArms() {
		return getArms().size() > 1;
	}

	/**
	 * Gets the arms internal.
	 *
	 * @return the arms internal
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "eph_id")
	@Where(clause = "retired_indicator  = 'false'")
	public List<Arm> getArmsInternal() {
		return lazyListHelper.getInternalList(Arm.class);
	}

	/**
	 * Gets the arms.
	 *
	 * @return the arms
	 */
	@Transient
	public List<Arm> getArms() {
		return lazyListHelper.getLazyList(Arm.class);
	}

	/**
	 * Sets the arms internal.
	 *
	 * @param arms the new arms internal
	 */
	public void setArmsInternal(List<Arm> arms) {
		lazyListHelper.setInternalList(Arm.class, arms);
	}

	/**
	 * Sets the arms.
	 *
	 * @param arms the new arms
	 */
	public void setArms(List<Arm> arms) {
		lazyListHelper.setInternalList(Arm.class, arms);
	}

	/**
	 * Adds the eligibility criterion.
	 *
	 * @param eligibilityCriterion the eligibility criterion
	 */
	public void addEligibilityCriterion(EligibilityCriteria eligibilityCriterion) {
		this.getEligibilityCriteria().add(eligibilityCriterion);
	}

	/**
	 * Gets the stratification criteria internal.
	 *
	 * @return the stratification criteria internal
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "EPH_ID")
	@Where(clause = "retired_indicator  = 'false'")
	public List<StratificationCriterion> getStratificationCriteriaInternal() {
		return lazyListHelper.getInternalList(StratificationCriterion.class);
	}

	/**
	 * Gets the stratification criteria.
	 *
	 * @return the stratification criteria
	 */
	@Transient
	public List<StratificationCriterion> getStratificationCriteria() {
		return lazyListHelper.getLazyList(StratificationCriterion.class);
	}

	/**
	 * Sets the stratification criteria.
	 *
	 * @param stratificationCriteria the new stratification criteria
	 */
	public void setStratificationCriteria(
			List<StratificationCriterion> stratificationCriteria) {
		lazyListHelper.setInternalList(StratificationCriterion.class,
				stratificationCriteria);
	}

	/**
	 * Sets the stratification criteria internal.
	 *
	 * @param stratificationCriteria the new stratification criteria internal
	 */
	public void setStratificationCriteriaInternal(
			List<StratificationCriterion> stratificationCriteria) {
		lazyListHelper.setInternalList(StratificationCriterion.class,
				stratificationCriteria);
	}

	/**
	 * Adds the stratification criterion.
	 *
	 * @param stratificationCriterion the stratification criterion
	 */
	public void addStratificationCriterion(
			StratificationCriterion stratificationCriterion) {
		this.getStratificationCriteria().add(stratificationCriterion);
	}

	/**
	 * Gets the eligibility criteria.
	 *
	 * @return the eligibility criteria
	 */
	@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "EPH_ID")
	@Where(clause = "retired_indicator  = 'false'")
	public List<EligibilityCriteria> getEligibilityCriteria() {
		return eligibilityCriteria;
	}

	/**
	 * Sets the eligibility criteria.
	 *
	 * @param eligibilityCriteria the new eligibility criteria
	 */
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

	/**
	 * Gets the inclusion eligibility criteria.
	 *
	 * @return the inclusion eligibility criteria
	 */
	@Transient
	public List<InclusionEligibilityCriteria> getInclusionEligibilityCriteria() {
		return lazyListHelper.getLazyList(InclusionEligibilityCriteria.class);
	}

	/**
	 * Sets the inclusion eligibility criteria.
	 *
	 * @param inclusionEligibilityCriteria the new inclusion eligibility criteria
	 */
	public void setInclusionEligibilityCriteria(
			List<InclusionEligibilityCriteria> inclusionEligibilityCriteria) {
		// do nothing
	}

	/**
	 * Gets the exclusion eligibility criteria.
	 *
	 * @return the exclusion eligibility criteria
	 */
	@Transient
	public List<ExclusionEligibilityCriteria> getExclusionEligibilityCriteria() {
		return lazyListHelper.getLazyList(ExclusionEligibilityCriteria.class);
	}

	/**
	 * Sets the exclusion eligibility criteria.
	 *
	 * @param exclusionEligibilityCriteria the new exclusion eligibility criteria
	 */
	public void setExclusionEligibilityCriteria(
			List<ExclusionEligibilityCriteria> exclusionEligibilityCriteria) {
		// do nothing
	}



	/**
	 * Gets the stratum groups internal.
	 *
	 * @return the stratum groups internal
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "epochs_id")
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy("stratumGroupNumber")
	public List<StratumGroup> getStratumGroupsInternal() {
		return lazyListHelper.getInternalList(StratumGroup.class);
	}
	
	public void addStratumGroup(StratumGroup stratumGroup){
		getStratumGroups().add(stratumGroup);
	}

	/**
	 * Sets the stratum groups internal.
	 *
	 * @param stratumGroup the new stratum groups internal
	 */
	public void setStratumGroupsInternal(List<StratumGroup> stratumGroup) {
		lazyListHelper.setInternalList(StratumGroup.class, stratumGroup);
	}

	/**
	 * Gets the stratum groups.
	 *
	 * @return the stratum groups
	 */
	@Transient
	public List<StratumGroup> getStratumGroups() {
		return lazyListHelper.getLazyList(StratumGroup.class);
	}

	/**
	 * Sets the stratum groups.
	 *
	 * @param stratumGroup the new stratum groups
	 */
	public void setStratumGroups(List<StratumGroup> stratumGroup) {
	}



	/**
	 * Gets the randomization.
	 *
	 * @return the randomization
	 */
	@OneToOne
	@JoinColumn(name = "rndm_id")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public Randomization getRandomization() {
		return randomization;
	}

	/**
	 * Sets the randomization.
	 *
	 * @param randomization the new randomization
	 */
	public void setRandomization(Randomization randomization) {
		this.randomization = randomization;
	}

	/**
	 * Checks for book randomization entry.
	 *
	 * @return true, if successful
	 */
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

	/**
	 * Gets the stratum group for ans combination.
	 *
	 * @param scacList the scac list
	 *
	 * @return the stratum group for ans combination
	 */
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

	/**
	 * Gets the stratum group by number.
	 *
	 * @param number the number
	 *
	 * @return the stratum group by number
	 */
	@Transient
	/*
	 * This method iterates thru the stratum groups for the Epoch and
	 * finds the one that has the same stratum group number as the one passed in
	 * and returns it. returns null if no matching stratum group is found
	 */
	public StratumGroup getStratumGroupByNumber(Integer number) {
		for (StratumGroup sgCurr : this.getStratumGroups()) {
			if (sgCurr.getStratumGroupNumber().equals(number)) {
				return sgCurr;
			}
		}
		return null;
	}

	/**
	 * Checks for stratification.
	 *
	 * @return true, if successful
	 */
	@Transient
	public boolean hasStratification() {
		return (getStratificationCriteria().size() > 0);
	}

	/**
	 * Checks for stratum groups.
	 *
	 * @return true, if successful
	 */
	@Transient
	public boolean hasStratumGroups() {
		return (getStratumGroups().size() > 0);
	}

	/**
	 * Checks for eligibility.
	 *
	 * @return true, if successful
	 */
	@Transient
	public boolean hasEligibility() {
		return (getEligibilityCriteria().size() > 0);
	}

	/**
	 * Gets the randomized indicator.
	 *
	 * @return the randomized indicator
	 */
	public Boolean getRandomizedIndicator() {
		return randomizedIndicator;
	}

	/**
	 * Sets the randomized indicator.
	 *
	 * @param randomizedIndicator the new randomized indicator
	 */
	public void setRandomizedIndicator(Boolean randomizedIndicator) {
		this.randomizedIndicator = randomizedIndicator;
	}

	/**
	 * Evaluate status.
	 *
	 * @param errors the errors
	 *
	 * @return true, if successful
	 *
	 * @throws C3PRCodedRuntimeException the c3 pr coded runtime exception
	 */
	public void evaluateStatus(List<Error> errors) throws C3PRCodedRuntimeException {

	}





	/**
	 * Gets the code.
	 *
	 * @param errortypeString the errortype string
	 *
	 * @return the code
	 */
	@Transient
	public int getCode(String errortypeString) {
		return Integer.parseInt(this.c3prErrorMessages.getMessage(
				errortypeString, null, null));
	}

	/**
	 * Gets the c3 pr exception helper.
	 *
	 * @return the c3 pr exception helper
	 */
	@Transient
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}

	/**
	 * Sets the exception helper.
	 *
	 * @param c3PRExceptionHelper the new exception helper
	 */
	public void setExceptionHelper(C3PRExceptionHelper c3PRExceptionHelper) {
		this.c3PRExceptionHelper = c3PRExceptionHelper;
	}

	/**
	 * Gets the c3pr error messages.
	 *
	 * @return the c3pr error messages
	 */
	@Transient
	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	/**
	 * Sets the c3pr error messages.
	 *
	 * @param errorMessages the new c3pr error messages
	 */
	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	/**
	 * Checks if is enrolling.
	 *
	 * @return true, if is enrolling
	 */
	@Transient
	public boolean isEnrolling() {
		return enrollmentIndicator;
	}

	/**
	 * Gets the stratification indicator.
	 *
	 * @return the stratification indicator
	 */
	public Boolean getStratificationIndicator() {
		return stratificationIndicator;
	}

	/**
	 * Sets the stratification indicator.
	 *
	 * @param stratificationIndicator the new stratification indicator
	 */
	public void setStratificationIndicator(Boolean stratificationIndicator) {
		this.stratificationIndicator = stratificationIndicator;
	}

	/**
	 * Gets the treatment indicator.
	 *
	 * @return the treatment indicator
	 */
	public Boolean getTreatmentIndicator() {
		return treatmentIndicator;
	}

	/**
	 * Sets the treatment indicator.
	 *
	 * @param treatmentIndicator the new treatment indicator
	 */
	public void setTreatmentIndicator(Boolean treatmentIndicator) {
		this.treatmentIndicator = treatmentIndicator;
	}

	/**
	 * Gets the arm by name.
	 *
	 * @param armName the arm name
	 *
	 * @return the arm by name
	 */
	@Transient
	public Arm getArmByName(String armName){
		for(Arm localArm:this.getArms()){
			if(armName.equals(localArm.getName())){
				return localArm;
			}
		}
		return null;
	}

	/**
	 * Generate stratum groups.
	 *
	 * @param request the request
	 * @param commandObj the command obj
	 * @param error the error
	 * @param epochCountIndex the epoch count index
	 */
	public void generateStratumGroups() {
		ArrayList<StratumGroup> stratumGroupList;

		this.getStratumGroups().clear();

		int stratificationCriterionSize = this.getStratificationCriteria().size();
		if (stratificationCriterionSize > 0) {
			StratificationCriterionPermissibleAnswer permissibleAnswersArray[][] = new StratificationCriterionPermissibleAnswer[stratificationCriterionSize][];
			List<StratificationCriterionPermissibleAnswer> tempAnswersList;

			// creating a 2d array of answers for every treatment epoch
			for (int i = 0; i < stratificationCriterionSize; i++) {
				tempAnswersList = this.getStratificationCriteria().get(i)
						.getPermissibleAnswers();
				permissibleAnswersArray[i] = new StratificationCriterionPermissibleAnswer[tempAnswersList
						.size()];
				for (int j = 0; j < tempAnswersList.size(); j++) {
					permissibleAnswersArray[i][j] = tempAnswersList.get(j);
				}
			}

			stratumGroupList = stratumGroupCombinationGenerator(permissibleAnswersArray, 0, new ArrayList<StratumGroup>(),
					new ArrayList<StratificationCriterionAnswerCombination>());
			this.getStratumGroups().addAll(stratumGroupList);
		}
	}

	/**
	 * Stratum group combination generator.
	 * recursive method which computes all possible combinations
	 * of sc and scpa and creates a list of stratum Groups for the same.
	 *
	 * @param epoch the epoch
	 * @param permissibleAnswersArray the permissible answers array
	 * @param intRecurseLevel the int recurse level
	 * @param stratumGroupList the stratum group list
	 * @param generatedAnswerCombinationList the generated answer combination list
	 *
	 * @return the array list< stratum group>
	 */
	private ArrayList<StratumGroup> stratumGroupCombinationGenerator(StratificationCriterionPermissibleAnswer[][] permissibleAnswersArray,
			int intRecurseLevel, ArrayList<StratumGroup> stratumGroupList,
			ArrayList<StratificationCriterionAnswerCombination> generatedAnswerCombinationList) {
		StratificationCriterionAnswerCombination stratificationCriterionAnswerCombination ;
		ArrayList<StratificationCriterionAnswerCombination> stratificationCriterionAnswerCombinationList;
		int numberOfStratumGroups = 0;

		for (int i = 0; i < permissibleAnswersArray[intRecurseLevel].length; i++) {
			stratificationCriterionAnswerCombination = new StratificationCriterionAnswerCombination();
			stratificationCriterionAnswerCombinationList = new ArrayList<StratificationCriterionAnswerCombination>();
			stratificationCriterionAnswerCombination
					.setStratificationCriterionPermissibleAnswer(permissibleAnswersArray[intRecurseLevel][i]);
			stratificationCriterionAnswerCombination.setStratificationCriterion(this
					.getStratificationCriteria().get(intRecurseLevel));

			if (!generatedAnswerCombinationList.isEmpty()) {
				stratificationCriterionAnswerCombinationList.addAll(generatedAnswerCombinationList);
			}
			stratificationCriterionAnswerCombinationList.add(stratificationCriterionAnswerCombination);

			if (intRecurseLevel < permissibleAnswersArray.length - 1) {
				// stepping into the next question
				stratumGroupList = stratumGroupCombinationGenerator(permissibleAnswersArray, intRecurseLevel + 1, stratumGroupList,
						stratificationCriterionAnswerCombinationList);
			} else {
				// ran out of questions and hence now i have a combination of answers to save.
				numberOfStratumGroups = stratumGroupList.size();
				stratumGroupList.add(numberOfStratumGroups, new StratumGroup());
				stratumGroupList.get(numberOfStratumGroups)
						.getStratificationCriterionAnswerCombination().addAll(
								cloneStratificationCriterionAnswerCombination(stratificationCriterionAnswerCombinationList));
				stratumGroupList.get(numberOfStratumGroups).setStratumGroupNumber(numberOfStratumGroups);
			}
		}
		return stratumGroupList;
	}

	/**
	 * Clones the StratificationCriterionAnswerCombination for the comboGenerator.
	 *
	 * @param stratificationCriterionAnswerCombinationList the stratification criterion answer combination list
	 *
	 * @return the list< stratification criterion answer combination>
	 */
	private List<StratificationCriterionAnswerCombination> cloneStratificationCriterionAnswerCombination(
			List<StratificationCriterionAnswerCombination> stratificationCriterionAnswerCombinationList) {

		List<StratificationCriterionAnswerCombination> clonedList = new ArrayList<StratificationCriterionAnswerCombination>();
		Iterator<StratificationCriterionAnswerCombination> iter = stratificationCriterionAnswerCombinationList
				.iterator();
		while (iter.hasNext()) {
			clonedList.add(new StratificationCriterionAnswerCombination(iter
					.next()));
		}
		return clonedList;
	}
}