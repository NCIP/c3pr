package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
/** 
 * @author Himanshu
 */

@Entity
@Table(name = "STUDY_VERSIONS")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_VERSIONS_ID_SEQ") })
public class StudyVersion extends AbstractMutableDeletableDomainObject implements Comparable<Study> {
	
	public int compareTo(Study o) {
		// TODO Auto-generated method stub
		return 0;
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
	private Date versiontDate;

	public Date getVersionDate() {
		return versiontDate;
	}

	public void setVersionDate(Date versiontDate) {
		this.versiontDate = versiontDate;
	}
	@Transient
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}

	public StatusType getVersionStatus() {
		return versionStatus;
	}
	
	@Enumerated(EnumType.STRING)
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
		lazyListHelper.add(AmendmentReason.class,new InstantiateFactory<AmendmentReason>(AmendmentReason.class));
		lazyListHelper.add(CompanionStudyAssociation.class,new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(CompanionStudyAssociation.class, this,"ParentStudyVersion"));
		
		dataEntryStatus = StudyDataEntryStatus.INCOMPLETE;
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
		lazyListHelper.add(AmendmentReason.class,new InstantiateFactory<AmendmentReason>(AmendmentReason.class));
		lazyListHelper.add(CompanionStudyAssociation.class,new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(CompanionStudyAssociation.class, this,"ParentStudyVersion"));
		
		dataEntryStatus = StudyDataEntryStatus.INCOMPLETE;
	}
	
	public void addAmendmentReason(final AmendmentReason amendment) {
		getAmendmentReasons().add(amendment);
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "stu_version_id", nullable = false)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<AmendmentReason> getAmendmentReasonsInternal() {
		return lazyListHelper.getInternalList(AmendmentReason.class);
	}

	public void setAmendmentReasonsInternal(final List<AmendmentReason> amendments) {
		lazyListHelper.setInternalList(AmendmentReason.class, amendments);
	}

	@Transient
	public List<AmendmentReason> getAmendmentReasons() {
		return lazyListHelper.getLazyList(AmendmentReason.class);
	}
	
	public void setAmendmentReasons(final List<AmendmentReason> amendments) {
		setAmendmentReasonsInternal(amendments);
	}
	
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

//	@Transient
//	public boolean hasCompanions() {
//		List<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
//		companionStudyAssociations.addAll(this.getCompanionStudyAssociations());
//		if (companionStudyAssociations.size() > 0) {
//			return true;
//		}
//		return false;
//	}

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
		return Integer.parseInt(this.c3prErrorMessages.getMessage(
				errortypeString, null, null));
	}
	
	private Study study;
	private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return false;
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

	public void addCompanionStudyAssociation(CompanionStudyAssociation companionStudyAssociation) {
		this.getCompanionStudyAssociations().add(companionStudyAssociation);
		companionStudyAssociation.setParentStudyVersion(this);
	}



}
