package edu.duke.cabig.c3pr.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.constants.NotificationEmailSubstitutionVariablesEnum;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RegistrationDataEntryStatus;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochDataEntryStatus;
import edu.duke.cabig.c3pr.constants.ScheduledEpochWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.domain.customfield.Customizable;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedInstantiateFactory;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.C3PRInvalidDataEntryException;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import gov.nih.nci.cabig.ctms.domain.DomainObjectTools;

/**
 * The Class StudySubject.
 *
 * @author Ram Chilukuri
 */

/**
 * @author Kruttik Aggarwal
 *
 */
@Entity
@Table(name = "STUDY_SUBJECTS")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_SUBJECTS_ID_SEQ") })
public class StudySubject extends
		InteroperableAbstractMutableDeletableDomainObject implements Customizable{

	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;

	/** The off study reason text. */
	private String offStudyReasonText;

	/** The off study date. */
	private Date offStudyDate;

	/** The participant. */
	private Participant participant;

	/** The start date. */
	private Date startDate;

	/** The treating physician. */
	private StudyInvestigator treatingPhysician;

	/** The other treating physician. */
	private String otherTreatingPhysician;

	/** The reg data entry status. */
	private RegistrationDataEntryStatus regDataEntryStatus;

	/** The reg workflow status. */
	private RegistrationWorkFlowStatus regWorkflowStatus;

	/** The disease history. */
	private DiseaseHistory diseaseHistory;

	/** The identifiers. */
	private List<Identifier> identifiers;

	/** The payment method. */
	private String paymentMethod;

	/** The child study subjects. */
	private List<StudySubject> childStudySubjects = new ArrayList<StudySubject>();

	/** The parent study subject. */
	private StudySubject parentStudySubject;

	/** The c3 pr exception helper. */
	private C3PRExceptionHelper c3PRExceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;

	private List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();

	private StudySubjectStudyVersion studySubjectStudyVersion;

	private StudySite studySite;
	
	private String backDatedReasonText;
	
	public String getBackDatedReasonText() {
		return backDatedReasonText;
	}

	public void setBackDatedReasonText(String backDatedReasonText) {
		this.backDatedReasonText = backDatedReasonText;
	}

	private Logger log = Logger.getLogger(StudySubject.class);
	/**
	 * Instantiates a new study subject.
	 */
	public StudySubject() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1
				.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
		lazyListHelper = new LazyListHelper();
		this.regDataEntryStatus = RegistrationDataEntryStatus.INCOMPLETE;
		this.regWorkflowStatus = RegistrationWorkFlowStatus.PENDING;
		lazyListHelper
				.add(
						OrganizationAssignedIdentifier.class,
						new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(
								OrganizationAssignedIdentifier.class));
		lazyListHelper.add(SystemAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(
						SystemAssignedIdentifier.class));
		setIdentifiers(new ArrayList<Identifier>());
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));

		// mandatory, so that the lazy-projected list is managed properly.
	}

	// / BEAN PROPERTIES
	/**
	 * Instantiates a new study subject.
	 *
	 * @param forExample the for example
	 */
	public StudySubject(boolean forExample) {
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(ScheduledEpoch.class,
				new InstantiateFactory<ScheduledEpoch>(ScheduledEpoch.class));
		lazyListHelper.add(ScheduledEpoch.class,
				new InstantiateFactory<ScheduledEpoch>(ScheduledEpoch.class));
		lazyListHelper
				.add(
						OrganizationAssignedIdentifier.class,
						new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(
								OrganizationAssignedIdentifier.class));
		lazyListHelper.add(SystemAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(
						SystemAssignedIdentifier.class));
		setIdentifiers(new ArrayList<Identifier>());
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));

	}

	/**
	 * Gets the scheduled epochs.
	 *
	 * @return the scheduled epochs
	 */
	@Transient
	public List<ScheduledEpoch> getScheduledEpochs() {
		return getStudySubjectStudyVersion().getScheduledEpochs();
	}

	public void setStudySubjectStudyVersions(
			List<StudySubjectStudyVersion> studySubjectStudyVersions) {
		this.studySubjectStudyVersions = studySubjectStudyVersions;
	}

	@OneToMany(mappedBy = "studySubject")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudySubjectStudyVersion> getStudySubjectStudyVersions() {
		return studySubjectStudyVersions;
	}

	public void addStudySubjectStudyVersion(StudySubjectStudyVersion studySubjectStudyVersion){
		studySubjectStudyVersion.setStudySubject(this);
		getStudySubjectStudyVersions().add(studySubjectStudyVersion);
	}

	@Transient
	public StudySubjectStudyVersion getLatestStudySubjectVersion(){
        if( getStudySubjectStudyVersions().size() == 0){
        	StudySubjectStudyVersion studySubjectStudyVersion= new StudySubjectStudyVersion();
        	addStudySubjectStudyVersion(studySubjectStudyVersion);
			return studySubjectStudyVersion;
        }else{
        	List<StudySubjectStudyVersion> sortedSubejctStudyVersions = new ArrayList<StudySubjectStudyVersion>();
            sortedSubejctStudyVersions.addAll(this.getStudySubjectStudyVersions());
            Collections.sort(sortedSubejctStudyVersions);
			return sortedSubejctStudyVersions.get(sortedSubejctStudyVersions.size() - 1 );
		}
	}

	@Transient
	public StudySubjectStudyVersion getStudySubjectStudyVersion() {
		if(studySubjectStudyVersion == null){
			studySubjectStudyVersion = getLatestStudySubjectVersion();
		}
		return studySubjectStudyVersion;
	}

	public void setStudySubjectStudyVersion(
			StudySubjectStudyVersion studySubjectStudyVersion) {
		this.studySubjectStudyVersion = studySubjectStudyVersion;
	}

	@Transient
	public StudySiteStudyVersion getStudySiteVersion(){
		return getStudySubjectStudyVersion().getStudySiteStudyVersion();
	}

	/**
	 * Adds the scheduled epoch.
	 *
	 * @param scheduledEpoch the scheduled epoch
	 */
	public void addScheduledEpoch(ScheduledEpoch scheduledEpoch) {
		getStudySubjectStudyVersion().addScheduledEpoch(scheduledEpoch);
	}

	/**
	 * Gets the scheduled epoch.
	 *
	 * @return the scheduled epoch
	 */
	@Transient
	public ScheduledEpoch getScheduledEpoch() {
		return getStudySubjectStudyVersion().getCurrentScheduledEpoch();
	}

	/**
	 * Gets the scheduled epoch.
	 *
	 * @return the scheduled epoch
	 */
	@Transient
	public ScheduledEpoch getScheduledEpochByName(String epochName) {
		Epoch epoch= new Epoch();
		epoch.setName(epochName);
		return getStudySubjectStudyVersion().getScheduledEpoch(epoch);
	}

	/**
	 * Gets the disease history.
	 *
	 * @return the disease history
	 */
	@Transient
	public DiseaseHistory getDiseaseHistory() {
		if (this.diseaseHistory == null)
			this.diseaseHistory = new DiseaseHistory();
		return diseaseHistory;
	}

	/**
	 * Sets the disease history.
	 *
	 * @param diseaseHistory the new disease history
	 */
	public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
		this.diseaseHistory = diseaseHistory;
	}

	/**
	 * Gets the disease history internal.
	 *
	 * @return the disease history internal
	 */
	@OneToOne
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "DISEASE_HISTORY_ID")
	public DiseaseHistory getDiseaseHistoryInternal() {
		return diseaseHistory;
	}

	/**
	 * Sets the disease history internal.
	 *
	 * @param diseaseHistory the new disease history internal
	 */
	public void setDiseaseHistoryInternal(DiseaseHistory diseaseHistory) {
		this.diseaseHistory = diseaseHistory;
	}

	/**
	 * Sets the study site.
	 *
	 * @param studySite the new study site
	 */
	public void setStudySite(StudySite studySite) {
		if(studySite!=null){
			this.getStudySubjectStudyVersion().setStudySiteStudyVersion(studySite.getStudySiteStudyVersion());
			this.studySite=studySite;
		}
	}

	/**
	 * Gets the study site.
	 *
	 * @return the study site
	 */
	@Transient
	public StudySite getStudySite() {
		StudySiteStudyVersion studySiteStudyVersion = getStudySubjectStudyVersion().getStudySiteStudyVersion();
		if(studySiteStudyVersion!= null){
			return studySiteStudyVersion.getStudySite();
		}
		return null;
	}

	/**
	 * Sets the participant.
	 *
	 * @param participant the new participant
	 */
	public void setParticipant(Participant participant) {
		this.participant = participant;
	}

	/**
	 * Gets the participant.
	 *
	 * @return the participant
	 */
	@ManyToOne
	@JoinColumn(name = "PRT_ID", nullable = false)
	@Cascade( { CascadeType.LOCK })
	public Participant getParticipant() {
		return participant;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the start date
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Sets the start date.
	 *
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final StudySubject that = (StudySubject) o;

		if (getStudySite() != null ? !getStudySite().equals(that.getStudySite())
				: that.getStudySite() != null)
			return false;
		// Participant#equals calls this method, so we can't use it here
		if (!DomainObjectTools.equalById(participant, that.participant))
			return false;

		return true;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
	 */
	@Override
	public int hashCode() {
		int result =1;
		result = 29*result + (getStudySite() != null ? getStudySite().hashCode() : 0);
		result = 29 * result
				+ (participant != null ? participant.hashCode() : 0);
		return result;
	}

	/**
	 * Gets the off study date str.
	 *
	 * @return the off study date str
	 */
	@Transient
	public String getOffStudyDateStr() {
		if (offStudyDate != null) {
			return DateUtil.formatDate(offStudyDate, "MM/dd/yyyy");
		}
		return "";
	}

	/**
	 * Gets the start date str.
	 *
	 * @return the start date str
	 */
	@Transient
	public String getStartDateStr() {
		if(startDate!=null){
			return DateUtil.formatDate(startDate, "MM/dd/yyyy");
		}
		return "";
	}

	/**
	 * Gets the identifiers.
	 *
	 * @return the identifiers
	 */
	@OneToMany
	@Cascade( { CascadeType.MERGE, CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "SPA_ID")
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	/**
	 * Sets the identifiers.
	 *
	 * @param identifiers the new identifiers
	 */
	private void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
		// initialize projected list for OrganizationAssigned and
		// SystemAssignedIdentifier
		lazyListHelper
				.setInternalList(OrganizationAssignedIdentifier.class,
						new ProjectedList<OrganizationAssignedIdentifier>(
								this.identifiers,
								OrganizationAssignedIdentifier.class));
		lazyListHelper.setInternalList(SystemAssignedIdentifier.class,
				new ProjectedList<SystemAssignedIdentifier>(this.identifiers,
						SystemAssignedIdentifier.class));
	}

	/**
	 * Gets the system assigned identifiers.
	 *
	 * @return the system assigned identifiers
	 */
	@Transient
	public List<SystemAssignedIdentifier> getSystemAssignedIdentifiers() {
		return lazyListHelper.getLazyList(SystemAssignedIdentifier.class);
	}

	/**
	 * Gets the organization assigned identifiers.
	 *
	 * @return the organization assigned identifiers
	 */
	@Transient
	public List<OrganizationAssignedIdentifier> getOrganizationAssignedIdentifiers() {
		return lazyListHelper.getLazyList(OrganizationAssignedIdentifier.class);
	}

	/**
	 * Adds the identifier.
	 *
	 * @param identifier the identifier
	 */
	public void addIdentifier(Identifier identifier) {
		getIdentifiers().add(identifier);
	}

	/**
	 * Removes the identifier.
	 *
	 * @param identifier the identifier
	 */
	public void removeIdentifier(Identifier identifier) {
		getIdentifiers().remove(identifier);
	}

	/**
	 * Gets the primary identifier.
	 *
	 * @return the primary identifier
	 */
	@Transient
	public String getPrimaryIdentifier() {
		for (Identifier identifier : getIdentifiers()) {
			if (identifier.getPrimaryIndicator().booleanValue() == true) {
				return identifier.getValue();
			}
		}

		return null;
	}

	/**
	 * Gets the treating physician.
	 *
	 * @return the treating physician
	 */
	@OneToOne
	@JoinColumn(name = "STI_ID")
	public StudyInvestigator getTreatingPhysician() {
		return treatingPhysician;
	}

	/**
	 * Sets the treating physician.
	 *
	 * @param treatingPhysician the new treating physician
	 */
	public void setTreatingPhysician(StudyInvestigator treatingPhysician) {
		this.treatingPhysician = treatingPhysician;
	}

	/**
	 * Gets the other treating physician.
	 *
	 * @return the other treating physician
	 */
	public String getOtherTreatingPhysician() {
		return otherTreatingPhysician;
	}

	/**
	 * Sets the other treating physician.
	 *
	 * @param otherTreatingPhysician the new other treating physician
	 */
	public void setOtherTreatingPhysician(String otherTreatingPhysician) {
		this.otherTreatingPhysician = otherTreatingPhysician;
	}

	/**
	 * Gets the treating physician full name.
	 *
	 * @return the treating physician full name
	 */
	@Transient
	public String getTreatingPhysicianFullName() {
		if (getTreatingPhysician() != null)
			return getTreatingPhysician().getHealthcareSiteInvestigator()
					.getInvestigator().getFullName();
		return getOtherTreatingPhysician();
	}

	/**
	 * Gets the reg workflow status.
	 *
	 * @return the reg workflow status
	 */
	@Enumerated(EnumType.STRING)
	public RegistrationWorkFlowStatus getRegWorkflowStatus() {
		return regWorkflowStatus;
	}

	/**
	 * Sets the reg workflow status.
	 *
	 * @param registrationWorkFlowStatus the new reg workflow status
	 */
	public void setRegWorkflowStatus(
			RegistrationWorkFlowStatus registrationWorkFlowStatus) {
		this.regWorkflowStatus = registrationWorkFlowStatus;
	}

	/**
	 * Gets the reg data entry status.
	 *
	 * @return the reg data entry status
	 */
	@Enumerated(EnumType.STRING)
	public RegistrationDataEntryStatus getRegDataEntryStatus() {
		return regDataEntryStatus;
	}

	/**
	 * Sets the reg data entry status.
	 *
	 * @param registrationDataEntryStatus the new reg data entry status
	 */
	public void setRegDataEntryStatus(
			RegistrationDataEntryStatus registrationDataEntryStatus) {
		this.regDataEntryStatus = registrationDataEntryStatus;
	}

	/**
	 * Gets the data entry status string.
	 *
	 * @return the data entry status string
	 */
	@Transient
	public String getDataEntryStatusString() {
		return this.regDataEntryStatus == RegistrationDataEntryStatus.COMPLETE
				&& this.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE ? "Complete"
				: "Incomplete";
	}

	/**
	 * Gets the data entry status.
	 *
	 * @return the data entry status
	 */
	@Transient
    public boolean getDataEntryStatus() {
        return this.regDataEntryStatus == RegistrationDataEntryStatus.COMPLETE
                        && this.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE ? true
                        : false;
    }

	/**
	 * Gets the co ordinating center identifier.
	 *
	 * @return the co ordinating center identifier
	 */
	@Transient
	public OrganizationAssignedIdentifier getCoOrdinatingCenterIdentifier() {
		for (OrganizationAssignedIdentifier organizationAssignedIdentifier : getOrganizationAssignedIdentifiers()) {
			if (organizationAssignedIdentifier.getType().equals(
					OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER)) {
				return organizationAssignedIdentifier;
			}
		}
		return null;
	}
	
	/**
	 * Gets the C3PR assigned identifier.
	 *
	 * @return the C3PR assigned identifier
	 */
	@Transient
	public SystemAssignedIdentifier getC3PRAssignedIdentifier() {
		for (SystemAssignedIdentifier systemAssignedIdentifier : getSystemAssignedIdentifiers()) {
			if (systemAssignedIdentifier.getSystemName().equals("C3PR")) {
				return systemAssignedIdentifier;
			}
		}
		return null;
	}

	/**
	 * Sets the co ordinating center identifier.
	 *
	 * @param value the new co ordinating center identifier
	 */
	public void setCoOrdinatingCenterIdentifier(String value) {
		OrganizationAssignedIdentifier identifier = getOrganizationAssignedIdentifiers()
				.get(0);
		identifier.setHealthcareSite(this.getStudySite().getStudy()
				.getStudyCoordinatingCenters().get(0).getHealthcareSite());
		identifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER);
		identifier.setValue(value);
	}

	/**
	 * Gets the c3 d identifier.
	 *
	 * @return the c3 d identifier
	 */
	@Transient
	public String getC3DIdentifier() {
		if (getSystemAssignedIdentifiers().size() == 0)
			return null;
		return getSystemAssignedIdentifiers().get(0).getValue();
	}

	/**
	 * Sets the c3 d identifier.
	 *
	 * @param value the new c3 d identifier
	 */
	public void setC3DIdentifier(String value) {
		SystemAssignedIdentifier identifier = new SystemAssignedIdentifier();
		identifier.setSystemName("C3D");
		identifier.setType("C3D Patient Position");
		identifier.setValue(value);
		this.getSystemAssignedIdentifiers().add(identifier);
	}

	/**
	 * Gets the off study date.
	 *
	 * @return the off study date
	 */
	public Date getOffStudyDate() {
		return offStudyDate;
	}

	/**
	 * Sets the off study date.
	 *
	 * @param offStudyDate the new off study date
	 */
	public void setOffStudyDate(Date offStudyDate) {
		this.offStudyDate = offStudyDate;
	}

	/**
	 * Gets the off study reason text.
	 *
	 * @return the off study reason text
	 */
	public String getOffStudyReasonText() {
		return offStudyReasonText;
	}

	/**
	 * Sets the off study reason text.
	 *
	 * @param offStudyReasonText the new off study reason text
	 */
	public void setOffStudyReasonText(String offStudyReasonText) {
		this.offStudyReasonText = offStudyReasonText;
	}

	// Adding refactored code
	/**
	 * Evaluate registration data entry status.
	 *
	 * @return the registration data entry status
	 */
	public RegistrationDataEntryStatus evaluateRegistrationDataEntryStatus() {
		List<Error> errors = new ArrayList<Error>();
		evaluateRegistrationDataEntryStatus(errors);
		return (errors.size() > 0) ? RegistrationDataEntryStatus.INCOMPLETE : RegistrationDataEntryStatus.COMPLETE;
	}

	// Adding refactored code
	/**
	 * Evaluate registration data entry status.
	 *
	 * @param errors the errors
	 */
	public void evaluateRegistrationDataEntryStatus(List<Error> errors) {
		if(this.getStudySite().getStudy().getConsentRequired() == ConsentRequired.ALL){
			for(StudySubjectConsentVersion studySubjectConsentVersion : this.getStudySubjectStudyVersion().getStudySubjectConsentVersions()){
				if(studySubjectConsentVersion.getConsent() == null){
					errors.add(new Error("Informed consent version is missing"));
				}
				if(StringUtils.isBlank(studySubjectConsentVersion.getInformedConsentSignedDateStr())){
					errors.add(new Error("Informed consent signed date is missing"));
				}
			}
		}else if(this.getStudySite().getStudy().getConsentRequired() == ConsentRequired.ONE){
			boolean consentDataEntryInComplete = true ;
			for(StudySubjectConsentVersion studySubjectConsentVersion : this.getStudySubjectStudyVersion().getStudySubjectConsentVersions()){
				if(studySubjectConsentVersion.getConsent() != null ){
					consentDataEntryInComplete = false ;
					break;
				}
			}
			if(consentDataEntryInComplete){
				errors.add(new Error("Informed consent version is missing"));
			}
			
			boolean consentSignedDataEntryInComplete = true ;
			
			for(StudySubjectConsentVersion studySubjectConsentVersion : this.getStudySubjectStudyVersion().getStudySubjectConsentVersions()){
				if(!StringUtils.isBlank(studySubjectConsentVersion.getInformedConsentSignedDateStr())){
					consentSignedDataEntryInComplete = false ;
					break;
				}
			}
			if(consentSignedDataEntryInComplete){
				errors.add(new Error("Informed consent signed date is missing"));
			}
		}
		// register errors for child registrations 
		for(StudySubject childStudySubject : this.getChildStudySubjects()){
			childStudySubject.evaluateRegistrationDataEntryStatus(errors);
		}
		if(this.getParentStudySubject() == null && getWorkPendingOnMandatoryCompanionRegistrations()){
			errors.add(new Error("Mandatory companion is not registered"));
		}

	}

	/**
	 * Evaluate scheduled epoch data entry status.
	 *
	 * @param errors the errors
	 *
	 * @return the scheduled epoch data entry status
	 */
	public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(
			List<Error> errors) {
		return this.getScheduledEpoch().evaluateScheduledEpochDataEntryStatus(
				errors);
	}

	/**
	 * Checks if is study site.
	 *
	 * @param nciCode the nci code
	 *
	 * @return true, if is study site
	 */
	@Transient
	public boolean isStudySite(String nciCode) {
		return this.getStudySite().getHealthcareSite().getPrimaryIdentifier()
				.equals(nciCode);
	}

	/**
	 * Data Entry is considered complete if both Registrations and Scheduled
	 * Epoch data entry status are complete.
	 *
	 * @return true, if checks if is data entry complete
	 */
	@Transient
	public boolean isDataEntryComplete() {
		if (this.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
				&& this.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE) {
			return true;
		}
		return false;
	}

	/**
	 * Ready for randomization.
	 *
	 * @return true, if successful
	 */
	public boolean readyForRandomization() {
		return regDataEntryStatus == RegistrationDataEntryStatus.COMPLETE
				&& getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE;
	}

	/**
	 * Computes if co-ordinating center needs to approve a record for successful
	 * registration. which is true if the study is multisite and the epoch is
	 * enrolling.
	 *
	 * @return true, if requires coordinating center approval
	 */
	public boolean requiresCoordinatingCenterApproval() {
		return this.getStudySite().getStudy().getMultiInstitutionIndicator()
				&& this.getScheduledEpoch().getEpoch().isEnrolling();
	}

	/**
	 * Checks if is co ordinating center.
	 *
	 * @param nciCode the nci code
	 *
	 * @return true, if is co ordinating center
	 */
	@Transient
	public boolean isCoOrdinatingCenter(String nciCode) {
		return this.getStudySite().getStudy().isCoOrdinatingCenter(nciCode);
	}

	/*
	 * public void updateDataEntryStatus(){
	 * this.setRegDataEntryStatus(this.evaluateRegistrationDataEntryStatus());
	 * this.getScheduledEpoch().setScEpochDataEntryStatus(
	 * this.evaluateScheduledEpochDataEntryStatus()); }
	 */

	/**
	 * Update data entry status.
	 *
	 * @return the list< error>
	 */
	public List<Error> updateDataEntryStatus() {
		List<Error> errors = new ArrayList<Error>();
		this.evaluateRegistrationDataEntryStatus(errors);
		this.setRegDataEntryStatus((errors.size() > 0) ? RegistrationDataEntryStatus.INCOMPLETE : RegistrationDataEntryStatus.COMPLETE);
		this.getScheduledEpoch().setScEpochDataEntryStatus(this.evaluateScheduledEpochDataEntryStatus(errors));
		return errors;
	}

	/**
	 * Gets the payment method.
	 *
	 * @return the payment method
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentMethod the new payment method
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Requires affiliate site response.
	 *
	 * @return true, if successful
	 */
	public boolean requiresAffiliateSiteResponse() {
		if (this.getMultisiteWorkflowStatus() == WorkFlowStatusType.MESSAGE_RECIEVED)
			return true;
		return false;
	}

	/**
	 * Gets the child study subjects.
	 *
	 * @return the child study subjects
	 */
	@OneToMany(mappedBy = "parentStudySubject")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudySubject> getChildStudySubjects() {
		return childStudySubjects;
	}

	/**
	 * Removes the child study subject.
	 *
	 * @param studySubject the study subject
	 */
	public void removeChildStudySubject(StudySubject studySubject) {
		getChildStudySubjects().remove(studySubject);
	}

	/**
	 * Adds the child study subject.
	 *
	 * @param studySubject the study subject
	 */
	public void addChildStudySubject(StudySubject studySubject) {
		getChildStudySubjects().add(studySubject);
	}

	/**
	 * Sets the child study subjects.
	 *
	 * @param childStudySubjects the new child study subjects
	 */
	public void setChildStudySubjects(List<StudySubject> childStudySubjects) {
		this.childStudySubjects = childStudySubjects;
	}

	/**
	 * Gets the parent study subject.
	 *
	 * @return the parent study subject
	 */
	@ManyToOne
	@Cascade(value = { CascadeType.LOCK })
	@JoinTable(name = "stu_sub_associations", joinColumns = @JoinColumn(name = "child_stu_sub_id"), inverseJoinColumns = @JoinColumn(name = "parent_stu_sub_id"))
	public StudySubject getParentStudySubject() {
		return parentStudySubject;
	}

	/**
	 * Sets the parent study subject.
	 *
	 * @param parentStudySubject the new parent study subject
	 */
	public void setParentStudySubject(StudySubject parentStudySubject) {
		this.parentStudySubject = parentStudySubject;
	}

	/**
	 * Builds the map for notification.
	 *
	 * @return the map< object, object>
	 */
	@SuppressWarnings("unused")
	@Transient
	/*
	 * Used by the notifications use case to compose the email message by
	 * replacing the sub vars.
	 */
	public Map<Object, Object> buildMapForNotification() {
		StudySite studySite = getStudySite();
		Study study = studySite.getStudy();

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(NotificationEmailSubstitutionVariablesEnum.PARTICIPANT_MRN.toString(), getParticipant().getMRN().getValue() == null ? "MRN" : getParticipant().getMRN().getValue());
		map.put(NotificationEmailSubstitutionVariablesEnum.REGISTRATION_STATUS.toString(), getRegWorkflowStatus().getDisplayName() == null ? "site name" : getRegWorkflowStatus().getDisplayName());

		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE.toString(), study.getShortTitleText() == null ? "Short Title" : study.getShortTitleText());
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_ID.toString(), study.getId() == null ? "Study Id" : study.getId().toString());
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD.toString(), study.getTargetAccrualNumber() == null ? "Study Target Accrual" : study.getTargetAccrualNumber());
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_ACCRUAL_THRESHOLD.toString(), studySite.getTargetAccrualNumber() == null ? "Site Target Accrual": studySite.getTargetAccrualNumber());
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_CURRENT_ACCRUAL.toString(), study.getCurrentAccrualCount() == null ? "Study Current Accrual" : study.getCurrentAccrualCount());
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_CURRENT_ACCRUAL.toString(), studySite.getCurrentAccrualCount());
		return map;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.InteroperableAbstractMutableDeletableDomainObject#getEndpoints()
	 */
	@OneToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_sub_id")
	public List<EndPoint> getEndpoints() {
		return endpoints;
	}

	/**
	 * Creates the scheduled epoch.
	 *
	 * @param epoch the epoch
	 *
	 * @return the scheduled epoch
	 */
	public ScheduledEpoch createScheduledEpoch(Epoch epoch) {
		ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
		scheduledEpoch.setEpoch(epoch);
		return scheduledEpoch;
	}

	/**
	 * If scheduled epoch created for this epoch.
	 *
	 * @param epoch the epoch
	 *
	 * @return true, if successful
	 */
	public boolean hasScheduledEpoch(Epoch epoch) {
		return getStudySubjectStudyVersion().hasScehduledEpoch(epoch);
	}

//	/**
//	 * Gets the matching scheduled epoch.
//	 *
//	 * @param epoch the epoch
//	 *
//	 * @return the matching scheduled epoch
//	 */
//	public ScheduledEpoch getMatchingScheduledEpoch(Epoch epoch) {
//		return getStudySubjectStudyVersion().getScheduledEpoch(epoch);
//	}

	// returns errors if cannot register.
	/**
	 * Can register.
	 *
	 * @return the list< error>
	 */
	public List<Error> canRegister() {
		return updateDataEntryStatus();
	}

	/**
	 * Can reserve.
	 *
	 * @return the list< error>
	 */
	public List<Error> canReserve() {
		return updateDataEntryStatus();
	}

	/**
	 * Register.
	 */
	public void register() {
		ScheduledEpoch scheduledEpoch = getScheduledEpoch();
		Epoch epoch = scheduledEpoch.getEpoch();
		if (scheduledEpoch.getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.PENDING) {
			throw new C3PRBaseRuntimeException("StudySubject already registered on the epoch :" + epoch.getName());
		} else {
			// This returns errors
			List<Error> errors = new ArrayList<Error>();
			errors = canRegister();

			if (errors.size() == 0) {
				// if the epoch requires randomization set it status to
				// 'Registered But Not Randomized', else set it status to
				// 'Registered'
				if (epoch.getRandomizedIndicator()) {
					scheduledEpoch
							.setScEpochWorkflowStatus(
									ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED);
					// only if the study subject is still unregistered(i.e. for
					// the 1st epoch), we update it's status.
					// else, the study subject continues to have his/her
					// previous registration status.
					if (this.getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING || this.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED) {
						this
								.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED);
					}

				} else {
					if(!epoch.getEnrollmentIndicator()){
						scheduledEpoch.setScEpochWorkflowStatus(
							ScheduledEpochWorkFlowStatus.REGISTERED);
					}
					// only if the study subject is still unregistered(i.e. for
					// the 1st epoch), we update it's status.
					// else, the study subject continues to have his/her
					// previous registration status.
					if (this.getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING || this.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED) {
						this
								.setRegWorkflowStatus(RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED);
						if(this.getParentStudySubject()!=null){
							this.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
						}
					}
				}
			} else {
				throw new C3PRInvalidDataEntryException(
						" Cannot register because data entry is not complete",
						errors);
			}

		}
	}

	/**
	 * Reserve.
	 */
	public void reserve() {

		if (this.getRegWorkflowStatus() != RegistrationWorkFlowStatus.PENDING) {
			throw new C3PRBaseRuntimeException(
					"The subject cannot be reserved a spot on the study site. The subject is already registered or enrolled on the study site.");
		} else {
			ScheduledEpoch scheduledEpoch = this.getScheduledEpoch();
			if (!scheduledEpoch.getEpoch().getReservationIndicator()) {
				throw new C3PRBaseRuntimeException(
				"The epoch has to be reserving in order to reserve a spot for the subject");
			}else {
				List<Error> errors = new ArrayList<Error>();
				errors = canReserve();
				if (errors.size() > 0) {
					throw new C3PRInvalidDataEntryException(
							" Cannot reserve a spot because data entry is not complete",
							errors);
				} else {
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
					this.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
				}
			}
		}
	}

	/**
	 * Checks if is randomized on scheduled epoch.
	 *
	 * @return true, if is randomized on scheduled epoch
	 */
	@Transient
	public boolean isRandomizedOnScheduledEpoch() {
		return (getScheduledEpoch().getScheduledArm() != null && getScheduledEpoch()
				.getScheduledArm().getArm() != null);
	}

	/**
	 * Do local randomization.
	 */
	private void doLocalRandomization() {
		// randomize subject
		switch (this.getStudySite().getStudy().getRandomizationType()) {
		case PHONE_CALL:
			doPhoneCallRandomization();
			break;
		case BOOK:
			doBookRandomization();
			break;
		case CALL_OUT:
			break;
		default:
			break;
		}
	}

	/**
	 * Do book randomization.
	 */
	private void doBookRandomization() {
		ScheduledArm sa = new ScheduledArm();
		ScheduledEpoch ste = getScheduledEpoch();
		if (getScheduledEpoch().getEpoch().getStratificationIndicator()) {
			try {
				sa.setArm(getScheduledEpoch().getStratumGroup().getNextArm());
			} catch (C3PRBaseException e) {
				throw new C3PRBaseRuntimeException(e.getMessage());
			}
			if (sa.getArm() != null) {
				ste.addScheduledArm(sa);
				// stratumGroupDao.merge(studySubject.getStratumGroup());
			}
		} else {
			sa.setArm(getNextArmForUnstratifiedStudy());
			if (sa.getArm() != null) {
				ste.addScheduledArm(sa);
			}
		}
	}

	/**
	 * Do phone call randomization.
	 */
	private void doPhoneCallRandomization() {
		ScheduledEpoch scheduledEpoch = this.getScheduledEpoch();
		if (scheduledEpoch.getScheduledArm() == null) {
			if(!this.getStudySite().getStudy().getBlindedIndicator())
			throw new C3PRBaseRuntimeException(
					"The subject should have been already assigned to a Scheduled Arm for the Scheduled Epoch :"
							+ scheduledEpoch.getEpoch().getName());
		}
	}

	/**
	 * Gets the next arm for unstratified study.
	 *
	 * @return the next arm for unstratified study
	 */
	@Transient
	public Arm getNextArmForUnstratifiedStudy() {
		Arm arm = null;
		if ((getScheduledEpoch()).getEpoch().hasBookRandomizationEntry()) {
			Iterator<BookRandomizationEntry> iter = ((BookRandomization) (getScheduledEpoch())
					.getEpoch().getRandomization()).getBookRandomizationEntry()
					.iterator();
			BookRandomizationEntry breTemp;

			 while (iter.hasNext()) {
		            breTemp = iter.next();
		            if (breTemp.getPosition().equals((this.getScheduledEpoch().getEpoch().getCurrentBookRandomizationEntryPosition()))) {
		                synchronized (this) {
		                	(this.getScheduledEpoch().getEpoch()).setCurrentBookRandomizationEntryPosition(breTemp.getPosition()+1);
		                    arm = breTemp.getArm();
		                    break;
		                }
		            }
		        }
		}

		if (arm == null) {
			throw getC3PRExceptionHelper().getRuntimeException(
                    getCode("C3PR.EXCEPTION.REGISTRATION.NO.ARM.AVAILABLE.BOOK.EXHAUSTED.CODE"));

		}
		return arm;
	}

	/**
	 * Take subject off study.
	 *
	 * @param offStudyReasonText the off study reason text
	 * @param offStudyDate the off study date
	 */
	public void takeSubjectOffStudy(String offStudyReasonText, Date offStudyDate) {
		if (getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED) {
			throw new C3PRBaseRuntimeException(
					"The subject has to be enrolled before being taken off study");
		}
		this.setOffStudyReasonText(offStudyReasonText);
		this.setOffStudyDate(offStudyDate);
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.OFF_STUDY);
	}

	/**
	 * Transfer.
	 *
	 * @return the study subject
	 */
	public StudySubject transfer() {
		if (getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED) {
			throw new C3PRBaseRuntimeException(
					"The subject has to be enrolled before being transferred");
		}
		if (!isTransferrable()) {
			throw this
					.getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.REGISTRATION.TRANSFER.CANNOT_TO_LOWER_ORDER_EPOCH.CODE"));
		}

		if (getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.REGISTERED) {
			if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) {
				register();
			}
			if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED) {
				if (!isRandomizedOnScheduledEpoch()) {
					if (!getStudySite().getHostedMode()
							&& !this.getStudySite().getStudy()
									.getStudyCoordinatingCenter()
									.getHostedMode()
							&& (!getStudySite().getHealthcareSite().equals(
									this.getStudySite().getStudy()
											.getStudyCoordinatingCenter()
											.getHealthcareSite()))) {
						// doCoordinatingCenterRandomization(); // The
						// coordinating
						// center should
						// do the
						// randomization
						// if not in
						// hosted mode
						// or not at the
						// same site.
					} else {
						doLocalRandomization(); // If in hosted mode or if the
						// study site is same as the
						// coordinating center site, it
						// happens locally.
					}

				}
			}
			getScheduledEpoch().setScEpochWorkflowStatus(
					ScheduledEpochWorkFlowStatus.REGISTERED);
		}
		return this;
	}

	/**
	 * Gets the matching companion study association.
	 *
	 * @param childStudySubject the child study subject
	 *
	 * @return the matching companion study association
	 */
	public CompanionStudyAssociation getMatchingCompanionStudyAssociation(
			StudySubject childStudySubject) {
		for (CompanionStudyAssociation companionStudyAssociation : this
				.getStudySite().getStudy().getCompanionStudyAssociations()) {
			if (companionStudyAssociation.getCompanionStudy().equals(
					childStudySubject.getStudySite().getStudy())) {
				return companionStudyAssociation;
			}
		}
		return null;
	}

	/**
	 * Checks if is stand alone study subject.
	 *
	 * @return true, if is stand alone study subject
	 */
	@Transient
	public boolean isStandAloneStudySubject() {
		return this.getStudySite().getStudy().getStandaloneIndicator();
	}

	/**
	 * Checks for c3 pr system identifier.
	 *
	 * @return true, if successful
	 */
	@Transient
	public boolean hasC3PRSystemIdentifier() {
		for (SystemAssignedIdentifier systemAssignedIdentfier : this
				.getSystemAssignedIdentifiers()) {
			if (systemAssignedIdentfier.getSystemName()
					.equalsIgnoreCase("C3PR")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is transferrable.
	 *
	 * @return true, if is transferrable
	 */
	@Transient
	public boolean isTransferrable() {
		return getStudySubjectStudyVersion().isTransferrable();
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
	 * Sets the c3 pr exception helper.
	 *
	 * @param exceptionHelper the new c3 pr exception helper
	 */
	public void setC3PRExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.c3PRExceptionHelper = exceptionHelper;
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
	 * Prepare for enrollment.
	 */
	public void prepareForEnrollment() {

		if (!this.getStudySite().getStudy().getStandaloneIndicator() && this.getParentStudySubject() != null && this.getParentStudySubject().regWorkflowStatus!=RegistrationWorkFlowStatus.ENROLLED) {
			throw new C3PRBaseRuntimeException(" Cannot directly register on the embedded study. The registration can happen only through the parent");
		}

		if(getWorkPendingOnMandatoryCompanionRegistrations()){
			throw new C3PRBaseRuntimeException(" First register on the mandatory companions before enrolling on the parent");
		}
		for (StudySubject childStudySubject : this.getChildStudySubjects()) {
			CompanionStudyAssociation matchingCompanionStudyAssocation = null;
			matchingCompanionStudyAssocation = getMatchingCompanionStudyAssociation(childStudySubject);
			if (matchingCompanionStudyAssocation!= null) {
				if (matchingCompanionStudyAssocation.getMandatoryIndicator()) {
					if (!childStudySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()) {
						throw new C3PRBaseRuntimeException(" First register the subject on the enrolling epoch of the mandatory companions before proceeding with enrollment");
					}
				}
			}
		}
		
		List<Error> errors = new ArrayList<Error>();
		
		if(this.getStartDate() == null){
			errors.add(new Error("Registration start date is missing"));
		}
			this.getScheduledEpoch().setStartDate(this.getStartDate());
			log.debug("Setting the registration start date to scheduled epoch start date");

		if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) {
			register();
			
			canEnroll(errors);
			if(errors.size() > 0){
				throw new C3PRBaseRuntimeException(CommonUtils.listErrors(errors));
			}
		}

	}

	/**
	 * Do local enrollment.
	 */
	public void doLocalEnrollment() {
		ScheduledEpochWorkFlowStatus scEpochWorkflowStatus = getScheduledEpoch().getScEpochWorkflowStatus();
		if (scEpochWorkflowStatus != ScheduledEpochWorkFlowStatus.REGISTERED) {
			if (scEpochWorkflowStatus == ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED){
				doLocalRandomization();
			}
			this.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
		}
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
	}

	/**
	 * Do muti site enrollment.
	 *
	 * @param coordinatingCenterReturnedScheduledEpoch the coordinating center returned scheduled epoch
	 * @param coordinatingCenterAssignedIdentifier the coordinating center assigned identifier
	 */
	public void doMutiSiteEnrollment(
			ScheduledEpoch coordinatingCenterReturnedScheduledEpoch,
			OrganizationAssignedIdentifier coordinatingCenterAssignedIdentifier) {
		ScheduledEpoch scheduledEpoch = this.getStudySubjectStudyVersion().getScheduledEpoch(coordinatingCenterReturnedScheduledEpoch.getEpoch());
		if (scheduledEpoch.getRequiresArm()) {
			Arm arm = scheduledEpoch.getEpoch().getArmByName(
					coordinatingCenterReturnedScheduledEpoch
							.getScheduledArm().getArm().getName());
			ScheduledArm scheduledArm= scheduledEpoch.getScheduledArm()==null?scheduledEpoch.getScheduledArms().get(0):scheduledEpoch.getScheduledArm();
			scheduledArm.setArm(arm);
		}
		OrganizationAssignedIdentifier organizationAssignedIdentifier=new OrganizationAssignedIdentifier();
		organizationAssignedIdentifier.setHealthcareSite(this.getStudySite().getStudy().getStudyCoordinatingCenter().getHealthcareSite());
		organizationAssignedIdentifier.setGridId(coordinatingCenterAssignedIdentifier.getGridId());
		organizationAssignedIdentifier.setType(coordinatingCenterAssignedIdentifier.getType());
		organizationAssignedIdentifier.setValue(coordinatingCenterAssignedIdentifier.getValue());
		this.addIdentifier(organizationAssignedIdentifier);
		this.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);

		// TODO This should also set all the child studysubjects which are in
		// 'REGISTERED_BUT_NOT_ENROLLED' state to enrolled"

	}

	/**
	 * Do muti site transfer.
	 *
	 * @param coordinatingCenterReturnedScheduledEpoch the coordinating center returned scheduled epoch
	 */
	public void doMutiSiteTransfer( ScheduledEpoch coordinatingCenterReturnedScheduledEpoch) {
		ScheduledEpoch scheduledEpoch = this.getStudySubjectStudyVersion().getScheduledEpoch(coordinatingCenterReturnedScheduledEpoch.getEpoch());
		if (scheduledEpoch.getRequiresArm()) {
			Arm arm = scheduledEpoch.getEpoch().getArmByName(
					coordinatingCenterReturnedScheduledEpoch
							.getScheduledArm().getArm().getName());
			ScheduledArm scheduledArm= scheduledEpoch.getScheduledArm()==null?scheduledEpoch.getScheduledArms().get(0):scheduledEpoch.getScheduledArm();
			scheduledArm.setArm(arm);
		}
		this.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
	}

	/**
	 * Do local transfer.
	 */
	public void doLocalTransfer() {
		if (getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.REGISTERED) {
				if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED){
					doLocalRandomization();
				}
			this.getScheduledEpoch().setScEpochWorkflowStatus(
					ScheduledEpochWorkFlowStatus.REGISTERED);
		}
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
	}

	/**
	 * Prepare for transfer.
	 */
	public void prepareForTransfer() {
		if (getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED) {
			throw new C3PRBaseRuntimeException(
					"The subject has to be enrolled before being transferred");
		}
		if (!isTransferrable()) {
			throw this
					.getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.REGISTRATION.TRANSFER.CANNOT_TO_LOWER_ORDER_EPOCH.CODE"));
		}

		if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) {
			register();
		}

	}

	/**
	 * Can enroll.
	 *
	 * @param errors the errors
	 *
	 * @return the list< error>
	 */
	public List<Error> canEnroll(List<Error> errors){
		
		StudySiteStudyVersion studySiteStudyVersion = this.getStudySubjectStudyVersion().getStudySiteStudyVersion();
		
		for(StudySubjectConsentVersion studySubjectConsentVersion : this.getStudySubjectStudyVersion().getStudySubjectConsentVersions()){
			if (!studySiteStudyVersion.getStudySite().canEnroll(studySiteStudyVersion.getStudyVersion() , studySubjectConsentVersion.getInformedConsentSignedDate())){
				errors.add(new Error("The informed consent signed date does not correspond to the current study site study version. This seems to be a back dated registration"));
			}
			
			if(this.getStartDate()!=null && this.getStartDate().before(studySubjectConsentVersion.getInformedConsentSignedDate())){
				errors.add(new Error("Enrollment cannot start before the subject signed the informed consent :" + studySubjectConsentVersion.getConsent().getName()));
			}
		}
		
		for (StudySubject childStudySubject : this.getChildStudySubjects()) {
			CompanionStudyAssociation matchingCompanionStudyAssociation = null;
			matchingCompanionStudyAssociation = getMatchingCompanionStudyAssociation(childStudySubject);
			if (matchingCompanionStudyAssociation != null) {
				if (matchingCompanionStudyAssociation.getMandatoryIndicator()) {
					childStudySubject.evaluateRegistrationDataEntryStatus(errors);
					childStudySubject.evaluateScheduledEpochDataEntryStatus(errors);

				}
			}
		}
		return errors;
	}

	/**
	 * Gets the custom fields internal.
	 *
	 * @return the custom fields internal
	 */
	@OneToMany(mappedBy = "studySubject", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<CustomField> getCustomFieldsInternal() {
		return lazyListHelper.getInternalList(CustomField.class);
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.customfield.Customizable#getCustomFields()
	 */
	@Transient
	public List<CustomField> getCustomFields() {
		return lazyListHelper.getLazyList(CustomField.class);
	}

	/**
	 * Sets the custom fields internal.
	 *
	 * @param customFields the new custom fields internal
	 */
	public void setCustomFieldsInternal(List<CustomField> customFields) {
		lazyListHelper.setInternalList(CustomField.class,customFields);
	}

	/**
	 * Adds the custom field.
	 *
	 * @param customField the custom field
	 */
	public void addCustomField(CustomField customField) {
		this.getCustomFields().add(customField);
		customField.setStudySubject(this);
	}

	/**
	 * Gets the work pending on mandatory companion registrations.
	 *
	 * @return the work pending on mandatory companion registrations
	 */
	@Transient
	public boolean getWorkPendingOnMandatoryCompanionRegistrations(){
		if(!this.getScheduledEpoch().getEpoch().getEnrollmentIndicator()){
			return false ;
		}
		for(CompanionStudyAssociation companionStudyAssociation : this.getStudySite().getStudy().getCompanionStudyAssociations()){
			if (companionStudyAssociation.getMandatoryIndicator()) {
				boolean hasCorrespondingStudySubject = false;
				for (StudySubject childStudySubject : this.getChildStudySubjects()) {
					if (childStudySubject.getStudySite().getStudy().equals(companionStudyAssociation.getCompanionStudy())) {
						hasCorrespondingStudySubject = true;
					}
				}
				if (!hasCorrespondingStudySubject)
					return true;
			}
		}
		for (StudySubject childStudySubject : this.getChildStudySubjects()) {
			if(!childStudySubject.getDataEntryStatus()){
				return true ;
			}
			if(childStudySubject.getRegWorkflowStatus()!=RegistrationWorkFlowStatus.ENROLLED){
				CompanionStudyAssociation studyAssociation = getMatchingCompanionStudyAssociation(childStudySubject);
				if (studyAssociation != null) {
					if (studyAssociation.getMandatoryIndicator()) {
						if (!childStudySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()) {
							return true;
						}
						if (childStudySubject.getRegWorkflowStatus()!=RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED || childStudySubject.getScheduledEpoch().getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.PENDING){
							return true;
						}

					}
				}
			}
		}
		return false;
	}

	/**
	 * Checks for mandatory companions.
	 *
	 * @return true, if successful
	 */
	@Transient
	public boolean hasMandatoryCompanions(){

		for(CompanionStudyAssociation companionStudyAssociation:this.getStudySite().getStudy().getCompanionStudyAssociations()){
			if(companionStudyAssociation.getMandatoryIndicator()){
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the checks if is direct arm assigment.
	 *
	 * @return the checks if is direct arm assigment
	 */
	@Transient
	public boolean getIsDirectArmAssigment(){
		if(this.getScheduledEpoch().getRequiresArm() && !this.getScheduledEpoch().getRequiresRandomization()){
			return true ;
		}
		return false ;
	}
	
	/**
	 * Change study version.
	 * Updates the registration to use the study version valid on a given date.
	 * This method will remove the previous references and add a new valid study version.
	 * @param date the date
	 */
	public void changeStudyVersion(Date date){
		if(this.getStudySubjectStudyVersions().size()>1){
			throw new RuntimeException("Subject registered on multiple study version. Study subject setup incorrectly.");
		}
		StudySiteStudyVersion studySiteStudyVersion = this.getStudySite().getStudySiteStudyVersion(date);
		if (studySiteStudyVersion == null){
			throw this
			.getC3PRExceptionHelper()
			.getRuntimeException(
					getCode("C3PR.EXCEPTION.REGISTRATION.STUDYVERSION.NOTFOUND"), new String[]{new SimpleDateFormat("MM/dd/yyyy").format(date)});
		}
		Epoch epoch = studySiteStudyVersion.getStudyVersion().getEpochByName(this.getScheduledEpoch().getEpoch().getName());
		if(epoch == null){
			throw this
			.getC3PRExceptionHelper()
			.getRuntimeException(
					getCode("C3PR.EXCEPTION.REGISTRATION.EPOCH.NOTFOUND"), new String[]{this.getScheduledEpoch().getEpoch().getName() , this.getStudySiteVersion().getStudyVersion().getName()});
		}
		ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
		scheduledEpoch.setEpoch(epoch);
		StudySubjectStudyVersion studySubjectStudyVersion = new StudySubjectStudyVersion();
		studySubjectStudyVersion.setStudySiteStudyVersion(studySiteStudyVersion);
		studySubjectStudyVersion.addScheduledEpoch(scheduledEpoch);
		this.getStudySubjectStudyVersions().remove(this.getStudySubjectStudyVersion());
		this.addStudySubjectStudyVersion(studySubjectStudyVersion);
		this.setStudySubjectStudyVersion(studySubjectStudyVersion);
	}
	
	@Transient
	public List<StudySubjectConsentVersion> getStudySubjectConsentVersions() {
		return getStudySubjectStudyVersion().getStudySubjectConsentVersions();
	}

	public void addStudySubjectConsentVersion(StudySubjectConsentVersion studySubjectConsentVersion) {
		this.getStudySubjectStudyVersion().addStudySubjectConsentVersion(studySubjectConsentVersion);
	}

}