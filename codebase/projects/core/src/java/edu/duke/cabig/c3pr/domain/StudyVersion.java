package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.constants.AmendmentType;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.constants.StudyPart;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
/**
 * @author Himanshu
 */

@Entity
@Table(name = "STUDY_VERSIONS")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_VERSIONS_ID_SEQ") })
public class StudyVersion extends AbstractMutableDeletableDomainObject implements Comparable<StudyVersion>, Cloneable {

	private StatusType versionStatus;
	private StudyDataEntryStatus dataEntryStatus;
	private String  descriptionText;
	private String  longTitleText;
	private String  shortTitleText;
	private String precisText;
	private RandomizationType randomizationType;
	private LazyListHelper lazyListHelper;
	private C3PRExceptionHelper c3PRExceptionHelper;
	private MessageSource c3prErrorMessages;

	private Date versionDate;
	private String name;
    private String comments;
    private List<StudyPart> amendmentReasons  ;
	private Study study;
	private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();
	private AmendmentType amendmentType;
	private Integer gracePeriod;
	private Boolean originalIndicator;

	public StudyVersion(){
		lazyListHelper = new LazyListHelper();
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);

		lazyListHelper.add(Epoch.class,new InstantiateFactory<Epoch>(Epoch.class));
		lazyListHelper.add(Consent.class,new InstantiateFactory<Consent>(Consent.class));
		lazyListHelper.add(CompanionStudyAssociation.class,new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(CompanionStudyAssociation.class, this,"ParentStudyVersion"));
		lazyListHelper.add(StudySiteStudyVersion.class,new ParameterizedBiDirectionalInstantiateFactory<StudySiteStudyVersion>(StudySiteStudyVersion.class, this));

		dataEntryStatus = StudyDataEntryStatus.INCOMPLETE;
        versionStatus = StatusType.IN ;
        amendmentReasons = new ArrayList<StudyPart>();
        amendmentType = AmendmentType.IMMEDIATE_AFTER_GRACE_PERIOD;
        originalIndicator = false;
	}

	public StudyVersion(boolean forSearchByExample){
		amendmentReasons = new ArrayList<StudyPart>();
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(Epoch.class,new ParameterizedBiDirectionalInstantiateFactory<Epoch>(Epoch.class, this));
		lazyListHelper.add(Consent.class,new ParameterizedBiDirectionalInstantiateFactory<Consent>(Consent.class, this));
		lazyListHelper.add(CompanionStudyAssociation.class,new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(CompanionStudyAssociation.class, this,"ParentStudyVersion"));
		lazyListHelper.add(StudySiteStudyVersion.class,new ParameterizedBiDirectionalInstantiateFactory<StudySiteStudyVersion>(StudySiteStudyVersion.class, this));
	}

	public Integer getGracePeriod() {
		return gracePeriod;
	}

	public void setGracePeriod(Integer gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	@Enumerated(EnumType.STRING)
	public AmendmentType getAmendmentType() {
		return amendmentType;
	}

	public void setAmendmentType(AmendmentType amendmentType) {
		this.amendmentType = amendmentType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getVersionDate() {
		return versionDate;
	}

	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}

	@Transient
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}

	public void setC3PRExceptionHelper(C3PRExceptionHelper c3prExceptionHelper) {
		c3PRExceptionHelper = c3prExceptionHelper;
	}

    @Enumerated(EnumType.STRING)
	public StatusType getVersionStatus() {
		return versionStatus;
	}

	public void setVersionStatus(StatusType versionStatus) {
		this.versionStatus = versionStatus;
	}

	@Enumerated(EnumType.STRING)
	public StudyDataEntryStatus getDataEntryStatus() {
		return dataEntryStatus;
	}

	public void setDataEntryStatus(StudyDataEntryStatus dataEntryStatus) {
		this.dataEntryStatus = dataEntryStatus;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public String getLongTitleText() {
		return longTitleText;
	}

	public void setLongTitleText(String longTitleText) {
		this.longTitleText = longTitleText;
	}

	public String getShortTitleText() {
		return shortTitleText;
	}

	public void setShortTitleText(String shortTitleText) {
		this.shortTitleText = shortTitleText;
	}

	public String getPrecisText() {
		return precisText;
	}

	public void setPrecisText(String precisText) {
		this.precisText = precisText;
	}

	public RandomizationType getRandomizationType() {
		return randomizationType;
	}

	public void setRandomizationType(RandomizationType randomizationType) {
		this.randomizationType = randomizationType;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="stu_version_id")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy("epochOrder")
	public List<Epoch> getEpochsInternal() {
		return lazyListHelper.getInternalList(Epoch.class);
	}

	public void setEpochsInternal(final List<Epoch> epochs) {
		lazyListHelper.setInternalList(Epoch.class, epochs);
	}

	@Transient
	public List<Epoch> getEpochs() {
		return lazyListHelper.getLazyList(Epoch.class);
	}

	public void setEpochs(List<Epoch> epochs) {
		setEpochsInternal(epochs);
	}

	public void addEpoch(Epoch epoch) throws RuntimeException {
		if(getEpochs().contains(epoch)){
			throw new RuntimeException("epoch with same name already exists in study");
		}else{
			getEpochs().add(epoch);
		}
	}

	@Transient
	public Epoch getEpochByName(String name) {
		for(Epoch epoch : getEpochs()){
			if(StringUtils.equals(name, epoch.getName())){
				return epoch;
			}
		}
		return null;
	}

	@OneToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_version_id", nullable = false)
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy ("id")
	public List<Consent> getConsentsInternal() {
		return lazyListHelper.getInternalList(Consent.class);
	}

	@Transient
	public List<Consent> getConsents() {
		return lazyListHelper.getLazyList(Consent.class);
	}

	public void setConsentsInternal(List<Consent> consents) {
		lazyListHelper.setInternalList(Consent.class,consents);
	}

	public void addConsent(Consent consent) {
		if(getConsents().contains(consent)){
			throw new RuntimeException("Consent with same name already exists in study");
		}else{
			this.getConsents().add(consent);
		}
	}

	public void setConsents(List<Consent> consents) {
		setConsentsInternal(consents);
	}

	@ManyToOne
    @JoinColumn(name = "study_id", nullable = false)
	public Study getStudy() {
		return study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="stu_version_id")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudyDisease> getStudyDiseases() {
		return studyDiseases;
	}

	public void setStudyDiseases(List<StudyDisease> studyDiseases) {
		this.studyDiseases = studyDiseases;
	}

	public void removeStudyDisease(StudyDisease studyDisease) {
		this.getStudyDiseases().remove(studyDisease);
	}

	public void removeAllStudyDisease() {
		this.getStudyDiseases().removeAll(this.getStudyDiseases());
	}

	public void addStudyDisease(StudyDisease studyDisease) {
		studyDiseases.add(studyDisease);
	}

	public StudyDataEntryStatus evaluateDataEntryStatus(List<Error> errors) {
		if(StringUtils.isBlank(this.getName())){
			errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.VERSION_NAME.CODE")).getMessage()));
		}
		if(this.getVersionDate() == null){
			errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.VERSION_DATE.CODE")).getMessage()));
		}
		if ((!this.hasEnrollingEpoch())) {
			errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE")).getMessage()));
		}

		if (getStudy().getRandomizedIndicator()) {
			if (!(this.hasRandomizedEpoch())) {
				errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.RANDOMIZED_EPOCH_FOR_RANDOMIZED_STUDY.CODE")).getMessage()));
			}
		}
		if (getStudy().getStratificationIndicator()) {
			if (!(this.hasStratifiedEpoch())) {
				errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STRATIFIED_EPOCH_FOR_STRATIFIED_STUDY.CODE")).getMessage()));
			}
		}

//		for (CompanionStudyAssociation compStudyAssoc : this.getCompanionStudyAssociations()) {
//			if (compStudyAssoc.getMandatoryIndicator() != null) {
//				if (compStudyAssoc.getMandatoryIndicator() && !(compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.READY_TO_OPEN
//						|| compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN)) {
//					errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS.COMPANION_STUDY.CODE")).getMessage()));
//				}
//			}
//
//		}
		evaluateEpochsDataEntryStatus(errors);

		return errors.size() == 0 ? StudyDataEntryStatus.COMPLETE : StudyDataEntryStatus.INCOMPLETE;
	}

    @Transient
	public boolean hasRandomizedEpoch() {
		for (Epoch epoch : this.getEpochs()) {
			if (epoch.getRandomizedIndicator())
				return true;
		}
		return false;
	}

	@Transient
	public boolean hasStratifiedEpoch() {
		for (Epoch epoch : this.getEpochs()) {
			if (epoch.getStratificationIndicator())
				return true;
		}
		return false;
	}

	@Transient
	public boolean hasEnrollingEpoch() {
		for (Epoch epoch : this.getEpochs()) {
			if (epoch.getEnrollmentIndicator())
				return true;
		}
		return false;
	}

	private void evaluateEpochsDataEntryStatus(List<Error> errors) throws C3PRCodedRuntimeException {
		for (Epoch epoch : this.getEpochs()) {
			evaluateStratificationDataEntryStatus(epoch, errors);
			evaluateRandomizationDataEntryStatus(epoch, errors);
		}
	}

	/**
	 * Evaluate stratification data entry status.
	 *
	 * @param errors the errors
	 *
	 * @return true, if successful
	 *
	 * @throws C3PRCodedRuntimeException the c3 pr coded runtime exception
	 */

	private boolean evaluateStratificationDataEntryStatus(Epoch epoch, List<Error> errors)
			throws C3PRCodedRuntimeException {
		if (epoch.getStratificationIndicator()) {
			if (!epoch.hasStratification() || !epoch.hasStratumGroups()) {
				errors.add(new Error(
								getC3PRExceptionHelper().getRuntimeException(
												getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STRATIFICATION_CRITERIA_OR_STRATUM_GROUPS_FOR_RANDOMIZED_EPOCH.CODE"),
												new String[] { this.getName() }).getMessage()));
			}
		}
		return true;
	}

	/**
	 * Evaluate randomization data entry status.
	 *
	 * @param errors the errors
	 *
	 * @return true, if successful
	 *
	 * @throws C3PRCodedRuntimeException the c3 pr coded runtime exception
	 */
	public boolean evaluateRandomizationDataEntryStatus(Epoch epoch, List<Error> errors) throws C3PRCodedRuntimeException {

		if (epoch.getRandomizedIndicator()) {
			if ((epoch.getArms().size() < 2)||(epoch.getRandomization() == null)) {
				if (epoch.getArms().size() < 2) {
					errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ATLEAST_2_ARMS_FOR_RANDOMIZED_EPOCH.CODE"),
							new String[] { this.getName() }).getMessage()));
				}
			}else{
				if (this.getRandomizationType() == (RandomizationType.BOOK)) {
						if (!epoch.hasBookRandomizationEntry()) {
							errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(
									getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.BOOK_ENTRIES_FOR_BOOK_RANDOMIZED_EPOCH.CODE"),
									new String[] { this.getName() }).getMessage()));
						}
				}else if(this.getRandomizationType() == (RandomizationType.PHONE_CALL)) {
						Randomization randomization = epoch.getRandomization();
						if (randomization instanceof PhoneCallRandomization) {
							if (StringUtils.isBlank(((PhoneCallRandomization) randomization).getPhoneNumber())) {
								errors.add(new Error(getC3PRExceptionHelper().getRuntimeException(
										getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.PHONE_NUMBER_FOR_PHONE_CALL_RANDOMIZED_EPOCH.CODE"),
										new String[] { this.getName() }).getMessage()));
						}
					}
				}
			}
		}
		return true;
	}

	@Transient
	public int getCode(String errortypeString) {
		return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
	}


    @OneToMany(mappedBy = "parentStudyVersion", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	public List<CompanionStudyAssociation> getCompanionStudyAssociationsInternal() {
		return lazyListHelper.getInternalList(CompanionStudyAssociation.class);
	}

	@Transient
	public List<CompanionStudyAssociation> getCompanionStudyAssociations() {
		return lazyListHelper.getLazyList(CompanionStudyAssociation.class);
	}

	public void setCompanionStudyAssociationsInternal(
			List<CompanionStudyAssociation> companionStudyAssociations) {
		lazyListHelper.setInternalList(CompanionStudyAssociation.class,
				companionStudyAssociations);
	}

    public void setCompanionStudyAssociations(List<CompanionStudyAssociation> companionStudyAssociations) {
		setCompanionStudyAssociationsInternal(companionStudyAssociations);
	}

	public void addCompanionStudyAssociation(CompanionStudyAssociation companionStudyAssociation) {
		this.getCompanionStudyAssociations().add(companionStudyAssociation);
		companionStudyAssociation.setParentStudyVersion(this);
	}

    public Object clone() throws CloneNotSupportedException{
        StudyVersion clone = new StudyVersion();
        // create a copy of current object in clone object and return clone.
        clone.setDescriptionText(this.getDescriptionText());
        clone.setLongTitleText(this.getLongTitleText());
        clone.setPrecisText(this.getPrecisText());
        clone.setRandomizationType(this.getRandomizationType());
        clone.setShortTitleText(this.getShortTitleText());

        for(StudyDisease disease : this.getStudyDiseases()){
        	StudyDisease cloneDisease = new StudyDisease();
        	cloneDisease.setDiseaseTerm(disease.getDiseaseTerm());
        	clone.addStudyDisease(cloneDisease);
        }

        for(CompanionStudyAssociation association : this.getCompanionStudyAssociations()){
        	CompanionStudyAssociation cloneAssociation = new CompanionStudyAssociation();
        	cloneAssociation.setMandatoryIndicator(association.getMandatoryIndicator());
        	cloneAssociation.setCompanionStudy(association.getCompanionStudy());
        	clone.addCompanionStudyAssociation(cloneAssociation);
        }

//        for(Consent consent : this.getConsents()){
//        	Consent cloneConsent =  new Consent();
//        	cloneConsent.setName(consent.getName());
//        	for(ConsentVersion consentVersion : consent.getConsentVersions()){
//        		ConsentVersion cloneConsentVersion = new ConsentVersion();
//        		cloneConsentVersion.setEffectiveDate(consentVersion.getEffectiveDate());
//        		cloneConsentVersion.setName(consentVersion.getName());
//        		cloneConsent.addConsentVersion(cloneConsentVersion);
//        	}
//        	clone.addConsent(cloneConsent);
//        }

        for(Epoch epoch : this.getEpochs()){
        	Epoch cloneEpoch = new Epoch();
        	cloneEpoch.setAccrualCeiling(epoch.getAccrualCeiling());
        	cloneEpoch.setAccrualIndicator(epoch.getAccrualIndicator());
        	cloneEpoch.setDescriptionText(epoch.getDescriptionText());
        	cloneEpoch.setEnrollmentIndicator(epoch.getEnrollmentIndicator());
        	cloneEpoch.setEpochOrder(epoch.getEpochOrder());
        	cloneEpoch.setName(epoch.getName());
        	cloneEpoch.setRandomizedIndicator(epoch.getRandomizedIndicator());
        	cloneEpoch.setReservationIndicator(epoch.getReservationIndicator());
        	cloneEpoch.setStratificationIndicator(epoch.getStratificationIndicator());
        	cloneEpoch.setTreatmentIndicator(epoch.getTreatmentIndicator());

        	for(Arm arm : epoch.getArms()){
        		Arm cloneArm = new Arm();
        		cloneArm.setDescriptionText(arm.getDescriptionText());
        		cloneArm.setName(arm.getName());
        		cloneArm.setTargetAccrualNumber(arm.getTargetAccrualNumber());
        		cloneEpoch.addArm(cloneArm);
        	}

        	for(StratificationCriterion criteria : epoch.getStratificationCriteria()){
        		StratificationCriterion cloneCriteria = new StratificationCriterion();
        		cloneCriteria.setQuestionNumber(criteria.getQuestionNumber());
        		cloneCriteria.setQuestionText(criteria.getQuestionText());
        		for(StratificationCriterionPermissibleAnswer permissibleAnswer : criteria.getPermissibleAnswers()){
        			StratificationCriterionPermissibleAnswer clonePermissibleAnswer = new StratificationCriterionPermissibleAnswer();
        			clonePermissibleAnswer.setPermissibleAnswer(permissibleAnswer.getPermissibleAnswer());
        			cloneCriteria.addPermissibleAnswer(clonePermissibleAnswer);
        		}
        		cloneEpoch.addStratificationCriterion(cloneCriteria);
        	}

        	for(EligibilityCriteria inclusionEligibility : epoch.getInclusionEligibilityCriteria()){
        		EligibilityCriteria cloneInclusionEligibility = new InclusionEligibilityCriteria() ;
        		cloneInclusionEligibility.setNotApplicableIndicator(inclusionEligibility.getNotApplicableIndicator());
        		cloneInclusionEligibility.setQuestionNumber(inclusionEligibility.getQuestionNumber());
        		cloneInclusionEligibility.setQuestionText(inclusionEligibility.getQuestionText());
        		cloneEpoch.addEligibilityCriterion(cloneInclusionEligibility);
        	}

        	for(EligibilityCriteria exclusionEligibility : epoch.getExclusionEligibilityCriteria()){
        		EligibilityCriteria cloneExclusionEligibility = new ExclusionEligibilityCriteria() ;
        		cloneExclusionEligibility.setNotApplicableIndicator(exclusionEligibility.getNotApplicableIndicator());
        		cloneExclusionEligibility.setQuestionNumber(exclusionEligibility.getQuestionNumber());
        		cloneExclusionEligibility.setQuestionText(exclusionEligibility.getQuestionText());
        		cloneEpoch.addEligibilityCriterion(cloneExclusionEligibility);
        	}

        	Randomization randomization = epoch.getRandomization() ;
        	if( randomization instanceof PhoneCallRandomization){
        		PhoneCallRandomization phoneCallRandomization = (PhoneCallRandomization) randomization ;
        		PhoneCallRandomization cloneRandomization =  new PhoneCallRandomization();
        		cloneRandomization.setPhoneNumber(phoneCallRandomization.getPhoneNumber());
        		cloneEpoch.setRandomization(cloneRandomization);
        	}
//        	if( randomization instanceof BookRandomization){
//        		BookRandomization bookRandomization = (BookRandomization) randomization ;
//        		BookRandomization cloneRandomization =  new BookRandomization();
//        		for(BookRandomizationEntry bookRandomizationEntry : bookRandomization.getBookRandomizationEntry()){
//        			BookRandomizationEntry cloneBookRandomizationEntry = new BookRandomizationEntry();
//        			cloneBookRandomizationEntry.setPosition(bookRandomizationEntry.getPosition());
//        			cloneBookRandomizationEntry.set
//        		}
//
//
//
//        		cloneEpoch.setRandomization(cloneRandomization);
//        	}


//        	for(StratumGroup group : epoch.getStratumGroups()){
//        		StratumGroup cloneGroup = new StratumGroup();
//        		cloneGroup.setStratumGroupNumber(group.getStratumGroupNumber());
//        		cloneGroup.setCurrentPosition(group.getCurrentPosition());
//
//
//        		for(StratificationCriterionAnswerCombination answerCombination : group.getStratificationCriterionAnswerCombination()){
//        			StratificationCriterionAnswerCombination cloneAnswerCombination = new StratificationCriterionAnswerCombination();
//        			cloneAnswerCombination.se
//        		}
//
//
//        	}
        	clone.addEpoch(cloneEpoch);
        }
        return clone ;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Transient
    public String getVersionDateStr(){
        return CommonUtils.getDateString(versionDate);
    }

	public void setAmendmentReasons(List<StudyPart> amendmentReasons) {
		this.amendmentReasons = amendmentReasons;
	}

	@Transient
	public List<StudyPart> getAmendmentReasons() {
		return amendmentReasons;
	}

	public void addAmendmentReason(StudyPart studyPart){
		this.amendmentReasons.add(studyPart);
	}

	@Column(name = "amendment_reason")
	public String getAmendmentReasonInternal() {
		String amendmentReason = null ;
		for(StudyPart reason : amendmentReasons){
				if(amendmentReason != null ){
					amendmentReason =  amendmentReason + " : " + reason.getName();
				}else{
					amendmentReason = reason.getName();
				}
		}
		return amendmentReason;
	}

	public void setAmendmentReasonInternal(String amendmentReason) {
		amendmentReasons = new ArrayList<StudyPart>();
		if (!StringUtils.isBlank(amendmentReason)) {
			StringTokenizer tokenizer = new StringTokenizer(amendmentReason, " : ");
			while (tokenizer.hasMoreTokens()) {
				StudyPart reason = (StudyPart) Enum.valueOf(StudyPart.class, tokenizer.nextToken());
				amendmentReasons.add(reason);
			};
		}
	}

	@OneToMany(mappedBy = "studyVersion")
	@Cascade(value = { CascadeType.LOCK, CascadeType.DELETE_ORPHAN})
	@Where(clause = "retired_indicator  = 'false'")
	public List<StudySiteStudyVersion> getStudySiteStudyVersionsInternal() {
		return lazyListHelper.getInternalList(StudySiteStudyVersion.class);
	}

	@Transient
	public List<StudySiteStudyVersion> getStudySiteStudyVersions() {
		return lazyListHelper.getLazyList(StudySiteStudyVersion.class);
	}

	public void setStudySiteStudyVersionsInternal(List<StudySiteStudyVersion> studySiteStudyVersions) {
		lazyListHelper.setInternalList(StudySiteStudyVersion.class,studySiteStudyVersions);
	}

	public void addStudySiteStudyVersion(StudySiteStudyVersion studySiteStudyVersion) {
		this.getStudySiteStudyVersions().add(studySiteStudyVersion);
		studySiteStudyVersion.setStudyVersion(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final StudyVersion other = (StudyVersion) obj;
		if (this.getStudy().equals(other.getStudy()))  {
			if(this.getName() == other.getName()){
				return true;
			}else{
				return false ;
			}
		}else{
			return false;
		}
	}

	@Transient
	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource c3prErrorMessages) {
		this.c3prErrorMessages = c3prErrorMessages;
	}

	public int compareTo(StudyVersion studyVersion) {
    	if(this.versionDate == null && studyVersion.getVersionDate() == null){
    		return 0;
    	}else if(this.versionDate == null && studyVersion.getVersionDate() != null){
    		return 1;
    	}else if(this.versionDate != null && studyVersion.getVersionDate() == null){
    		return -1;
    	}else{
    		return this.versionDate.compareTo(studyVersion.getVersionDate());
    	}
   	}

//	@Transient
//	public ConsentVersion getLatestConsentVersion(){
//		// this method is applicable only of there is 1 onsent available.
//		List<Consent> consents = this.getConsents();
//		if(consents.size() == 1){
//			Consent consent = consents.get(0);
//			List<ConsentVersion> consentVersions = consent.getConsentVersions();
//			Collections.sort(consentVersions);
//			int size = consentVersions.size();
//			if(size >0){
//				return consentVersions.get(size - 1);
//			}
//		}
//		return null;
//	}
	
	public Boolean getOriginalIndicator() {
		return originalIndicator;
	}

	public void setOriginalIndicator(Boolean originalIndicator) {
		this.originalIndicator = originalIndicator;
	}

}