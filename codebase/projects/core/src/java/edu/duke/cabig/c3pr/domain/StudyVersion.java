package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

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
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.constants.RaceCode;
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

    public int compareTo(StudyVersion studyVersion) {
    	return this.versionDate.compareTo(studyVersion.getVersionDate());
   	}

	private StatusType versionStatus;
	private StudyDataEntryStatus dataEntryStatus;
	private String  descriptionText;
	private String  longTitleText;
	private String  shortTitleText;
	private String precisText;
	private RandomizationType randomizationType;
	private Integer targetAccrualNumber;
	private LazyListHelper lazyListHelper;
	private C3PRExceptionHelper c3PRExceptionHelper;
	private MessageSource c3prErrorMessages;
	private Date versionDate;
	private String name;
    private String comments;
    private List<StudyPart> amendmentReasons ;

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

	public Integer getTargetAccrualNumber() {
		return targetAccrualNumber;
	}

	public void setTargetAccrualNumber(Integer targetAccrualNumber) {
		this.targetAccrualNumber = targetAccrualNumber;
	}

	public StudyVersion(){
		lazyListHelper = new LazyListHelper();
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);

		lazyListHelper.add(Epoch.class,new ParameterizedBiDirectionalInstantiateFactory<Epoch>(Epoch.class, this));
		lazyListHelper.add(Consent.class,new ParameterizedBiDirectionalInstantiateFactory<Consent>(Consent.class, this));
		lazyListHelper.add(CompanionStudyAssociation.class,new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(CompanionStudyAssociation.class, this,"ParentStudyVersion"));

		dataEntryStatus = StudyDataEntryStatus.INCOMPLETE;
        versionStatus = StatusType.IN ;
        amendmentReasons = new ArrayList<StudyPart>();
        versionDate = new Date();
	}

	public StudyVersion(boolean forSearchByExample){
		lazyListHelper = new LazyListHelper();
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);

		lazyListHelper.add(Epoch.class,new ParameterizedBiDirectionalInstantiateFactory<Epoch>(Epoch.class, this));
		lazyListHelper.add(Consent.class,new ParameterizedBiDirectionalInstantiateFactory<Consent>(Consent.class, this));
		lazyListHelper.add(CompanionStudyAssociation.class,new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(CompanionStudyAssociation.class, this,"ParentStudyVersion"));

		dataEntryStatus = StudyDataEntryStatus.INCOMPLETE;
	}

//	public void addAmendmentReason(final AmendmentReason amendment) {
//		getAmendmentReasons().add(amendment);
//	}
//
//	@OneToMany(fetch = FetchType.LAZY)
//	@JoinColumn(name = "stu_version_id", nullable = false)
//	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
//	public List<AmendmentReason> getAmendmentReasonsInternal() {
//		return lazyListHelper.getInternalList(AmendmentReason.class);
//	}
//
//	public void setAmendmentReasonsInternal(final List<AmendmentReason> amendments) {
//		lazyListHelper.setInternalList(AmendmentReason.class, amendments);
//	}
//
//	@Transient
//	public List<AmendmentReason> getAmendmentReasons() {
//		return lazyListHelper.getLazyList(AmendmentReason.class);
//	}
//
//	public void setAmendmentReasons(final List<AmendmentReason> amendments) {
//		setAmendmentReasonsInternal(amendments);
//	}

	@OneToMany(mappedBy = "studyVersion", fetch = FetchType.LAZY)
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
		for (Epoch epochPresent : getEpochs()) {
			if (epochPresent.equals(epoch)) {
				throw new RuntimeException("epoch with same name already exists in study");
			}
		}
		epoch.setStudyVersion(this);
		getEpochs().add(epoch);
	}

	public void removeEpoch(Epoch epoch) {
		lazyListHelper.getLazyList(Epoch.class).remove(epoch);
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

	@OneToMany(mappedBy = "studyVersion", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
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
		this.getConsents().add(consent);
		consent.setStudyVersion(this);
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

	@OneToMany(mappedBy = "studyVersion", fetch = FetchType.LAZY)
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
		studyDisease.setStudyVersion(this);
		studyDiseases.add(studyDisease);
	}

	public StudyDataEntryStatus evaluateDataEntryStatus(List<Error> errors) {
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
	public boolean hasElligibility() {
		for (Epoch epoch : this.getEpochs()) {
			if (epoch.hasEligibility())
				return true;
		}
		return false;
	}

	@Transient
	public boolean hasCompanions() {
		List<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
		companionStudyAssociations.addAll(this.getCompanionStudyAssociations());
		if (companionStudyAssociations.size() > 0) {
			return true;
		}
		return false;
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

	public void evaluateEpochsDataEntryStatus(List<Error> errors) throws C3PRCodedRuntimeException {
		for (Epoch epoch : this.getEpochs()) {
			epoch.evaluateStatus(errors);
		}
	}

	@Transient
	public int getCode(String errortypeString) {
		return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
	}

	private Study study;
	private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();

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
        clone.setTargetAccrualNumber(this.getTargetAccrualNumber());
        clone.setVersionDate(new Date());

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

        for(Consent consent : this.getConsents()){
        	Consent cloneConsent =  new Consent();
        	cloneConsent.setName(consent.getName());
        	for(ConsentVersion consentVersion : consent.getConsentVersions()){
        		ConsentVersion cloneConsentVersion = new ConsentVersion();
        		cloneConsentVersion.setEffectiveDate(consentVersion.getEffectiveDate());
        		cloneConsentVersion.setName(consentVersion.getName());
        		cloneConsent.addConsentVersion(cloneConsentVersion);
        	}
        	clone.addConsent(cloneConsent);
        }

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

	public String getAmendmentReason() {
		String amendmentReason = "" ;
		for(StudyPart reason : amendmentReasons){
			if( reason != null ){
				if(amendmentReason != "" ){
					amendmentReason =  amendmentReason + " : " + reason.getName();
				}else{
					amendmentReason = amendmentReason + reason.getName();
				}
			}
		}
		return amendmentReason;
	}

	/**
	 * Sets the race code.
	 *
	 * @param raceCode the new race code
	 */
	public void setAmendmentReason(String amendmentReason) {
		amendmentReasons = new ArrayList<StudyPart>();
		if (!StringUtils.isBlank(amendmentReason)) {
			StringTokenizer tokenizer = new StringTokenizer(amendmentReason, " : ");
			while (tokenizer.hasMoreTokens()) {
				StudyPart reason = (StudyPart) Enum.valueOf(StudyPart.class, tokenizer.nextToken());
				amendmentReasons.add(reason);
			};
		}
	}
}
