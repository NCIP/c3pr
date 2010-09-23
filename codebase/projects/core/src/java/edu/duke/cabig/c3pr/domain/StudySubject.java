package edu.duke.cabig.c3pr.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.EpochType;
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
import edu.emory.mathcs.backport.java.util.TreeSet;
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
@Where(clause = "reg_workflow_status  != 'INVALID'")
public class StudySubject extends
		InteroperableAbstractMutableDeletableDomainObject implements Customizable,IdentifiableObject{

	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;

	/** The participant. */
	private StudySubjectDemographics studySubjectDemographics;

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
	
	private String invalidationReasonText;
	
	public String getInvalidationReasonText() {
		return invalidationReasonText;
	}

	public void setInvalidationReasonText(String invalidationReasonText) {
		this.invalidationReasonText = invalidationReasonText;
	}

	public static final String C3D_SYSTME_NAME="C3D";
	
	public static final String C3D_IDENTIFIER_TYPE="C3D Patient Position";
	
	public static final String MEDIDATA_SYSTME_NAME="Medidata";
	
	public static final String MEDIDATA_IDENTIFIER_TYPE="Medidata Patient Position";
	
	private List<StudySubjectRegistryStatus> studySubjectRegistryStatusHistory = new ArrayList<StudySubjectRegistryStatus>();
	
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

	private void addStudySubjectStudyVersion(StudySubjectStudyVersion studySubjectStudyVersion){
		studySubjectStudyVersion.setStudySubject(this);
		getStudySubjectStudyVersions().add(studySubjectStudyVersion);
	}

	@Transient
	private StudySubjectStudyVersion getLatestStudySubjectVersion(){
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
	public StudySubjectStudyVersion getFirstStudySubjectVersion(){
        if( getStudySubjectStudyVersions().size() == 0){
        	StudySubjectStudyVersion studySubjectStudyVersion= new StudySubjectStudyVersion();
        	addStudySubjectStudyVersion(studySubjectStudyVersion);
			return studySubjectStudyVersion;
        }else{
        	List<StudySubjectStudyVersion> sortedSubejctStudyVersions = new ArrayList<StudySubjectStudyVersion>();
            sortedSubejctStudyVersions.addAll(this.getStudySubjectStudyVersions());
            Collections.sort(sortedSubejctStudyVersions);
			return sortedSubejctStudyVersions.get(0);
		}
	}

	// The scheduled epochs are always created and attached only to the 1st study subject study version.
	// The rest of the study subject study versions only capture consents
	@Transient
	public StudySubjectStudyVersion getStudySubjectStudyVersion() {
		if(studySubjectStudyVersion == null){
			studySubjectStudyVersion = getFirstStudySubjectVersion();
		}
		return studySubjectStudyVersion;
	}

	public void clearAllAndAddStudySubjectStudyVersion(
			StudySubjectStudyVersion studySubjectStudyVersion) {
		this.studySubjectStudyVersions.clear();
		this.addStudySubjectStudyVersion(studySubjectStudyVersion);
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
/*	public void setParticipant(Participant participant) {
		this.participant = participant;
	}*/

	/**
	 * Gets the participant.
	 *
	 * @return the participant
	 */
	/*@ManyToOne
	@JoinColumn(name = "PRT_ID", nullable = false)
	@Cascade( { CascadeType.LOCK })
	public Participant getParticipant() {
		return participant;
	}*/
	
	@ManyToOne
	@JoinColumn(name = "stu_sub_dmgphcs_id", nullable = false)
	@Cascade( { CascadeType.ALL })
	public StudySubjectDemographics getStudySubjectDemographics() {
		return studySubjectDemographics;
	}

	public void setStudySubjectDemographics(
			StudySubjectDemographics studySubjectDemographics) {
		this.studySubjectDemographics = studySubjectDemographics;
	}
	
	@Transient
	public Participant getParticipant(){
		return studySubjectDemographics != null ? studySubjectDemographics.getMasterSubject():null;
	}
	
	@Transient
	public void setParticipant (Participant participant){
		this.setStudySubjectDemographics(participant.createStudySubjectDemographics());
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
		if (!DomainObjectTools.equalById(studySubjectDemographics.getMasterSubject(), that.getStudySubjectDemographics().getMasterSubject()))
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
				+ (studySubjectDemographics.getMasterSubject() != null ? studySubjectDemographics.getMasterSubject().hashCode() : 0);
		return result;
	}

	/**
	 * Gets the off study date str.
	 *
	 * @return the off study date str
	 */
	@Transient
	public String getOffStudyDateStr() {
		Date OffStudyDate = getOffStudyDate();
		if (OffStudyDate != null) {
			return DateUtil.formatDate(OffStudyDate, "MM/dd/yyyy");
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
	 * Gets the primary identifier object. Currently used only in Subject Registry Code
	 *
	 * @return the primary identifier
	 */
	@Transient
	public Identifier getPrimaryIdentifierObject() {
		for (Identifier identifier : getIdentifiers()) {
			if (identifier.getPrimaryIndicator().booleanValue() == true) {
				return identifier;
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
	 * Gets the C3D identifier.
	 *
	 * @return the C3D identifier
	 */
	@Transient
	public String getC3DIdentifier() {
		if (getSystemAssignedIdentifiers().size() == 0)
			return null;
		for(SystemAssignedIdentifier systemAssignedIdentifier : getSystemAssignedIdentifiers()){
			if (systemAssignedIdentifier.getSystemName().equals(C3D_SYSTME_NAME))
				return systemAssignedIdentifier.getValue();
		}
		return null;
	}

	/**
	 * Sets the C3D identifier.
	 *
	 * @param value the new c3 d identifier
	 */
	public void setC3DIdentifier(String value) {
		SystemAssignedIdentifier identifier = new SystemAssignedIdentifier();
		identifier.setSystemName(C3D_SYSTME_NAME);
		identifier.setType(C3D_IDENTIFIER_TYPE);
		identifier.setValue(value);
		this.getSystemAssignedIdentifiers().add(identifier);
	}
	
	/**
	 * Gets the medidata identifier.
	 *
	 * @return the medidata identifier
	 */
	@Transient
	public String getMedidataIdentifier() {
		if (getSystemAssignedIdentifiers().size() == 0)
			return null;
		for(SystemAssignedIdentifier systemAssignedIdentifier : getSystemAssignedIdentifiers()){
			if (systemAssignedIdentifier.getSystemName().equals(MEDIDATA_SYSTME_NAME))
				return systemAssignedIdentifier.getValue();
		}
		return null;
	}
	
	/**
	 * Sets the Medidata identifier.
	 *
	 * @param value the new medidata identifier
	 */
	public void setMedidataIdentifier(String value) {
		SystemAssignedIdentifier identifier = new SystemAssignedIdentifier();
		identifier.setSystemName(MEDIDATA_SYSTME_NAME);
		identifier.setType(MEDIDATA_IDENTIFIER_TYPE);
		identifier.setValue(value);
		this.getSystemAssignedIdentifiers().add(identifier);
	}

	/**
	 * Gets the off study date.
	 *
	 * @return the off study date
	 */
	@Transient
	public Date getOffStudyDate() {
		if(this.regWorkflowStatus == RegistrationWorkFlowStatus.OFF_STUDY){
			return getScheduledEpoch().getOffEpochDate();
		}
		return null;
	}

	/**
	 * Gets the of study reason text.
	 *
	 * @return the off study reason text
	 */
	@Transient
	public List<OffEpochReason> getOffStudyReasons() {
		if(this.regWorkflowStatus == RegistrationWorkFlowStatus.OFF_STUDY){
			return getScheduledEpoch().getOffEpochReasons();
		}
		return null;
	}

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
		
	for(StudySubjectConsentVersion studySubjectConsentVersion : this.getLatestStudySubjectVersion().getStudySubjectConsentVersions()){
		if(studySubjectConsentVersion.getConsent().getMandatoryIndicator() && StringUtils.isBlank(studySubjectConsentVersion.getInformedConsentSignedDateStr())){
			errors.add(new Error("Mandatory informed consent signed date for consent " + studySubjectConsentVersion.getConsent().getName() + " is missing."));
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
		map.put(NotificationEmailSubstitutionVariablesEnum.PARTICIPANT_MRN.toString(), studySubjectDemographics.getMRN().getValue() == null ? "MRN" : studySubjectDemographics.getMRN().getValue());
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
		if (scheduledEpoch.getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH) {
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
									ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH);
					// only if the study subject is still unregistered(i.e. for
					// the 1st epoch), we update it's status.
					// else, the study subject continues to have his/her
					// previous registration status.
					if (this.getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING || this.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED) {
						this
								.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
					}

				} else {
					if(!epoch.getEnrollmentIndicator()){
						scheduledEpoch.setScEpochWorkflowStatus(
							ScheduledEpochWorkFlowStatus.ON_EPOCH);
					}
					// only if the study subject is still unregistered(i.e. for
					// the 1st epoch), we update it's status.
					// else, the study subject continues to have his/her
					// previous registration status.
					if (this.getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING || this.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED) {
						this
								.setRegWorkflowStatus(RegistrationWorkFlowStatus.PENDING_ON_STUDY);
						if(this.getParentStudySubject()!=null){
							this.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
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
			if (scheduledEpoch.getEpoch().getType() != EpochType.RESERVING) {
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
					scheduledEpoch.setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
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

	public void takeSubjectOffStudy(List<OffEpochReason> offStudyReasons, Date offStudyDate) {
		if (getRegWorkflowStatus() != RegistrationWorkFlowStatus.ON_STUDY) {
			throw new C3PRBaseRuntimeException(
					"The subject has to be enrolled before being taken off study");
		}
		for(OffEpochReason offEpochReason : offStudyReasons){
			if (! (offEpochReason.getReason() instanceof OffStudyReason)) {
				throw new C3PRBaseRuntimeException(
				"Invalid reason type. Expected OffStudyReason but was "+offEpochReason.getReason().getClass());
			}
		}
		this.getScheduledEpoch().takeSubjectOffEpoch(offStudyReasons, offStudyDate);
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.OFF_STUDY);
	}
	
	public void takeSubjectOffCurrentEpoch(List<OffEpochReason> offEpochReasons, Date offEpochDate) {
		if (this.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.ON_EPOCH) {
			throw new C3PRBaseRuntimeException(
					"The subject has to be successfully registered on the epoch before being taken off epoch");
		}
		this.getScheduledEpoch().takeSubjectOffEpoch(offEpochReasons, offEpochDate);
	}

	public void failScreening(List<OffEpochReason> offScreeningReasons, Date failScreeningDate) {
		ScheduledEpoch scheduledEpoch = getScheduledEpoch();
		if(canFailScreening()){
			scheduledEpoch.takeSubjectOffEpoch(offScreeningReasons, failScreeningDate);
			setRegWorkflowStatus(RegistrationWorkFlowStatus.NOT_REGISTERED);
		}else{
			throw new C3PRBaseRuntimeException(
					"Cannot fail screening. The subject is not registered successfully on a screening epoch.");
		}
		
	}
	
	/**
	 * Transfer.
	 *
	 * @return the study subject
	 */
	public StudySubject transfer() {
		if (getRegWorkflowStatus() != RegistrationWorkFlowStatus.ON_STUDY) {
			throw new C3PRBaseRuntimeException(
					"The subject has to be enrolled before being transferred");
		}
		if (!isTransferrable()) {
			throw this
					.getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.REGISTRATION.TRANSFER.CANNOT_TO_LOWER_ORDER_EPOCH.CODE"));
		}

		if (getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.ON_EPOCH) {
			if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH) {
				register();
			}
			if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH) {
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
					ScheduledEpochWorkFlowStatus.ON_EPOCH);
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

		if (!this.getStudySite().getStudy().getStandaloneIndicator() && this.getParentStudySubject() != null && this.getParentStudySubject().regWorkflowStatus!=RegistrationWorkFlowStatus.ON_STUDY) {
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

		if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH) {
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
		if (scEpochWorkflowStatus != ScheduledEpochWorkFlowStatus.ON_EPOCH) {
			if (scEpochWorkflowStatus == ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH){
				doLocalRandomization();
			}
			this.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
		}
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
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
		this.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);

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
		this.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.ON_EPOCH);
	}

	/**
	 * Do local transfer.
	 */
	public void doLocalTransfer() {
		if (getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.ON_EPOCH) {
				if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_RANDOMIZATION_ON_EPOCH){
					doLocalRandomization();
				}
			this.getScheduledEpoch().setScEpochWorkflowStatus(
					ScheduledEpochWorkFlowStatus.ON_EPOCH);
		}
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.ON_STUDY);
	}

	/**
	 * Prepare for transfer.
	 */
	public void prepareForTransfer() {
		if (getRegWorkflowStatus() != RegistrationWorkFlowStatus.ON_STUDY) {
			throw new C3PRBaseRuntimeException(
					"The subject has to be enrolled before being transferred");
		}
		if (!isTransferrable()) {
			throw this
					.getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.REGISTRATION.TRANSFER.CANNOT_TO_LOWER_ORDER_EPOCH.CODE"));
		}

		if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH) {
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
		
		if(this.startDate == null){
			errors.add(new Error("Registration start date is missing"));
		}
		
		StudySiteStudyVersion studySiteStudyVersion = this.getStudySubjectStudyVersion().getStudySiteStudyVersion();
		
		for(StudySubjectConsentVersion studySubjectConsentVersion : this.getStudySubjectStudyVersion().getStudySubjectConsentVersions()){
			if (studySubjectConsentVersion.getInformedConsentSignedDate()!=null &&!studySiteStudyVersion.getStudySite().canEnroll(studySiteStudyVersion.getStudyVersion() , studySubjectConsentVersion.getInformedConsentSignedDate())){
				errors.add(new Error("The informed consent signed date does not correspond to the current study site study version. This seems to be a back dated registration"));
			}
			
			if(this.getStartDate()!=null && studySubjectConsentVersion.getInformedConsentSignedDate()!=null && this.getStartDate().before(studySubjectConsentVersion.getInformedConsentSignedDate())){
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
			if(childStudySubject.getRegWorkflowStatus()!=RegistrationWorkFlowStatus.ON_STUDY){
				CompanionStudyAssociation studyAssociation = getMatchingCompanionStudyAssociation(childStudySubject);
				if (studyAssociation != null) {
					if (studyAssociation.getMandatoryIndicator()) {
						if (!childStudySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()) {
							return true;
						}
						if (childStudySubject.getRegWorkflowStatus()!=RegistrationWorkFlowStatus.PENDING_ON_STUDY || childStudySubject.getScheduledEpoch().getScEpochWorkflowStatus()==ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH){
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
		this.clearAllAndAddStudySubjectStudyVersion(studySubjectStudyVersion);
		// adding informed consent question answers
		 for(int i=0; i<studySubjectStudyVersion.getStudySiteStudyVersion().getStudyVersion().getConsents().size();i++){
			 studySubjectStudyVersion.getStudySubjectConsentVersions().get(i).
			 setConsent(studySubjectStudyVersion.getStudySiteStudyVersion().getStudyVersion().getConsents().get(i));
			 for(ConsentQuestion question:studySubjectStudyVersion.getStudySiteStudyVersion().getStudyVersion().getConsents().get(i).getQuestions()){
	    			SubjectConsentQuestionAnswer subjectConsentQuestionAnswer = new SubjectConsentQuestionAnswer();
	    			subjectConsentQuestionAnswer.setConsentQuestion(question);
	    			studySubjectStudyVersion.getStudySubjectConsentVersions().get(i)
	    			.addSubjectConsentAnswer(subjectConsentQuestionAnswer);
	    		}
	         }
	}
	
	/**
	 * Checks if passed in staff is assigned and active personnel on the site associated to the studySub or the coordinating center associated to the studySub.
	 * Called by the StudySubjectSecurityFilter to enforce site level security on StudySubjects.
	 *
	 * @param researchStaff the research staff
	 * @return true, if is assigned and active personnel
	 */
	@Transient
	public boolean isAssignedAndActivePersonnel(ResearchStaff researchStaff){
		//Checking if staff belongs to the site of registration
		for(StudyPersonnel studyPersonnel : getStudySite().getActiveStudyPersonnel()){
			if(studyPersonnel.getResearchStaff().equals(researchStaff)){
				return true;
			}
		}
		
		//If the site of registration is not the coordinating center then checking if staff belongs to the coordinating center(CC) of the studySubject.
		if(!getStudySite().getIsCoordinatingCenter()){
			//Note: The CC can just be a studyOrg or can be added to the study as a studySite 
			//and the user can add study personnel from either the CC as a studyOrg or the CC as a studySite.
			//Hence, we need to check for studyPersonnel under both the studySite and the studyOrg
			
			
			//Checking for CC under studySite
			StudySite coordinatingCenterStudySite = null;
			for(StudySite studySite : getStudySite().getStudy().getStudySites()){
				if(studySite.getIsCoordinatingCenter()){
					coordinatingCenterStudySite = studySite;
					break;
				}
			}
			if(coordinatingCenterStudySite != null){
				for(StudyPersonnel studyPersonnel : coordinatingCenterStudySite.getActiveStudyPersonnel()){
					if(studyPersonnel.getResearchStaff().equals(researchStaff)){
						return true;
					}
				}
			}
			
			//Checking for CC under studyOrg
			StudyOrganization studyOrganizationCoordinatingCenter = getStudySite().getStudy().getStudyCoordinatingCenter();
			if(studyOrganizationCoordinatingCenter != null){
				for(StudyPersonnel studyPersonnel : studyOrganizationCoordinatingCenter.getActiveStudyPersonnel()){
					if(studyPersonnel.getResearchStaff().equals(researchStaff)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	@Transient
	public boolean isCurrentEpochWorkflowComplete(){
		return getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.ON_EPOCH || 
		getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.OFF_EPOCH;
	}
	
	@Transient
	public boolean canChangeEpoch(){
		return isCurrentEpochWorkflowComplete() && 
				getStudySite().getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN &&
				getStudySite().getStudy().getIfHigherOrderEpochExists(getScheduledEpoch().getEpoch()) &&
				regWorkflowStatus != RegistrationWorkFlowStatus.OFF_STUDY &&
				regWorkflowStatus != RegistrationWorkFlowStatus.NOT_REGISTERED;
	}
	
	@Transient
	public boolean canTakeSubjectOffStudy() {
		if (regWorkflowStatus == RegistrationWorkFlowStatus.ON_STUDY) {
			return true;
		}
		return false;
	}
	
	@Transient
	public boolean canFailScreening() {
		if (regWorkflowStatus != RegistrationWorkFlowStatus.ON_STUDY &&
				regWorkflowStatus != RegistrationWorkFlowStatus.OFF_STUDY &&
				getScheduledEpoch().getEpoch().getType() == EpochType.SCREENING &&
				getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.ON_EPOCH) {
			return true;
		}
		return false;
	}
	
	public void addOriginalStudySubjectConsentVersion(StudySubjectConsentVersion studySubjectConsentVersion) {
		this.getStudySubjectStudyVersion().addStudySubjectConsentVersion(studySubjectConsentVersion);
	}
	
	public boolean canAllowEligibilityWaiver(){
		if(getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING_ON_EPOCH
				&& getScheduledEpoch().hasWaivableEligibilityAnswers()){
			return true;
		}
		return false;
	}
	
	public void allowEligibilityWaiver(List<EligibilityCriteria> eligibilityCriteriaList, ResearchStaff waivedBy){
		if(!canAllowEligibilityWaiver()){
			throw new C3PRBaseRuntimeException("Cannot allow waiver. Either there are no invalid eligibility answers or the scheduled epoch is not in pending state.");
		}
		if(waivedBy == null){
			throw new C3PRBaseRuntimeException("Cannot allow waiver. Research staff is null.");
		}
		List<SubjectEligibilityAnswer> subjectEligibilityAnswers = getScheduledEpoch().getSubjectEligibilityAnswers();
		for(EligibilityCriteria eligibilityCriteria : eligibilityCriteriaList){
			for(SubjectEligibilityAnswer subjectEligibilityAnswer : subjectEligibilityAnswers){
				if(subjectEligibilityAnswer.getEligibilityCriteria().equals(eligibilityCriteria)){
					if(!subjectEligibilityAnswer.canAllowWaiver()){
						throw new C3PRBaseRuntimeException("Eligibility criteria does not qualify for waiver.");
					}
					subjectEligibilityAnswer.setAllowWaiver(true);
					subjectEligibilityAnswer.setWaivedBy(waivedBy);
				}
			}
		}
	}
	
	public void waiveEligibility(List<SubjectEligibilityAnswer> subjectEligibilityAnswersInput){
		List<SubjectEligibilityAnswer> subjectEligibilityAnswers = getScheduledEpoch().getSubjectEligibilityAnswers();
		for(SubjectEligibilityAnswer subjectEligibilityAnswerInput : subjectEligibilityAnswersInput){
			for(SubjectEligibilityAnswer subjectEligibilityAnswer : subjectEligibilityAnswers){
				if(subjectEligibilityAnswer.getEligibilityCriteria().equals(subjectEligibilityAnswerInput.getEligibilityCriteria())){
					if(!subjectEligibilityAnswer.getAllowWaiver()){
						throw new C3PRBaseRuntimeException("Eligibility criteria cannot be waved. Please contact the study coordinator to initiate the waiver process.");
					}
					if(StringUtils.isBlank(subjectEligibilityAnswerInput.getWaiverId())){
						throw new C3PRBaseRuntimeException("Cannot waive eligibility without a waiver id");
					}
					if(StringUtils.isBlank(subjectEligibilityAnswerInput.getWaiverReason())){
						throw new C3PRBaseRuntimeException("Cannot waive eligibility without a waiver reason");
					}
					subjectEligibilityAnswer.setWaiverId(subjectEligibilityAnswerInput.getWaiverId());
					subjectEligibilityAnswer.setWaiverReason(subjectEligibilityAnswerInput.getWaiverReason());
				}
			}
		}
	}
	
	@Transient
	public Identifier getUniqueIdentifier(){
		return getSystemAssignedIdentifiers().get(0);
	}
	
	
	@Transient
	public StudyVersion getLastConsentedStudyVersion(){
		
		TreeSet uniqueStudyVersions = new TreeSet();
		List<StudySubjectConsentVersion> allStudySubjectConsentVersions = getAllConsents();
		
		for(StudySubjectConsentVersion studySubjectConsentVersion : allStudySubjectConsentVersions){
			uniqueStudyVersions.add(studySubjectConsentVersion.getConsent().getStudyVersion());
		}
		
		return uniqueStudyVersions.isEmpty()? null:(StudyVersion)uniqueStudyVersions.last();
	}
	
	@Transient
	public StudyVersion getFirstConsentedStudyVersion(){
		
		TreeSet uniqueStudyVersions = new TreeSet();
		List<StudySubjectConsentVersion> allStudySubjectConsentVersions = getAllConsents();
		for(StudySubjectConsentVersion studySubjectConsentVersion : allStudySubjectConsentVersions){
			uniqueStudyVersions.add(studySubjectConsentVersion.getConsent().getStudyVersion());
		}
		
		return uniqueStudyVersions.isEmpty()? null:(StudyVersion)uniqueStudyVersions.first();
	}
	
	// returns all signed study subject consent versions across different study subject versions
	@Transient
	public List<StudySubjectConsentVersion> getAllSignedConsents(){
		
		List<StudySubjectConsentVersion> allStudySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
		for(StudySubjectStudyVersion studySubjectStudyVersion : getStudySubjectStudyVersions()){
			for(StudySubjectConsentVersion studySubjectConsentVersion : studySubjectStudyVersion.getStudySubjectConsentVersions()){
				if(studySubjectConsentVersion.getInformedConsentSignedDate()!=null){
					allStudySubjectConsentVersions.add(studySubjectConsentVersion);
				}
			}
		}
		return allStudySubjectConsentVersions;
	}
	

	// returns all signed and unsigned study subject consent versions across different study subject versions (thought there will
	// be only 1 study subject version )
	@Transient
	public List<StudySubjectConsentVersion> getAllConsents(){
		
		List<StudySubjectConsentVersion> allStudySubjectConsentVersions = new ArrayList<StudySubjectConsentVersion>();
		for(StudySubjectStudyVersion studySubjectStudyVersion : getStudySubjectStudyVersions()){
			allStudySubjectConsentVersions.addAll(studySubjectStudyVersion.getStudySubjectConsentVersions());
			}
		return allStudySubjectConsentVersions;
	}
	
	
	
	// ReConsent API
	// Begin
	
	// gets original consents signed by a subject i.e. first time he/she is registered on a study.
	@Transient
	public List<StudySubjectConsentVersion> getOriginalSignedConsents() {
		
		StudyVersion firstConsentedStudyVersion = getFirstConsentedStudyVersion();
		List<StudySubjectConsentVersion> originalStudySubjectConsnetVersions = new ArrayList<StudySubjectConsentVersion>();
		
		for(StudySubjectConsentVersion studySubjectConsentVersion :getAllSignedConsents()){
			if(firstConsentedStudyVersion.equals(studySubjectConsentVersion.getConsent().getStudyVersion())){
				originalStudySubjectConsnetVersions.add(studySubjectConsentVersion);
			}
		}
		
		return originalStudySubjectConsnetVersions;
	}
	
	// gets original signed and unsigned consents first time he/she is registered on a study.
	@Transient
	public List<StudySubjectConsentVersion> getOriginalConsents() {
		
		StudyVersion firstConsentedStudyVersion = getFirstConsentedStudyVersion();
		List<StudySubjectConsentVersion> originalStudySubjectConsnetVersions = new ArrayList<StudySubjectConsentVersion>();
		
		for(StudySubjectConsentVersion studySubjectConsentVersion :getAllConsents()){
			if(firstConsentedStudyVersion.equals(studySubjectConsentVersion.getConsent().getStudyVersion())){
				originalStudySubjectConsnetVersions.add(studySubjectConsentVersion);
			}
		}
		
		return originalStudySubjectConsnetVersions;
	}
	
	// returns the study subject signed consent versions on latest study version
	@Transient
	public List<StudySubjectConsentVersion> getLatestSignedConsents() {
		
		StudyVersion lastConsentedStudyVersion = getLastConsentedStudyVersion();
		List<StudySubjectConsentVersion> latestStudySubjectConsnetVersions = new ArrayList<StudySubjectConsentVersion>();
		
		for(StudySubjectConsentVersion studySubjectConsentVersion :getAllSignedConsents()){
			if(lastConsentedStudyVersion.equals(studySubjectConsentVersion.getConsent().getStudyVersion())){
				latestStudySubjectConsnetVersions.add(studySubjectConsentVersion);
			}
		}
		
		return latestStudySubjectConsnetVersions;
	}
	
	// returns the study subject signed and unsigned consent versions on latest study version
	@Transient
	public List<StudySubjectConsentVersion> getLatestConsents() {
		
		StudyVersion lastConsentedStudyVersion = getLastConsentedStudyVersion();
		List<StudySubjectConsentVersion> latestStudySubjectConsnetVersions = new ArrayList<StudySubjectConsentVersion>();
		
		for(StudySubjectConsentVersion studySubjectConsentVersion :getAllConsents()){
			if(lastConsentedStudyVersion.equals(studySubjectConsentVersion.getConsent().getStudyVersion())){
				latestStudySubjectConsnetVersions.add(studySubjectConsentVersion);
			}
		}
		
		return latestStudySubjectConsnetVersions;
	}
	
	
	// returns signed study subject consent versions on a given study version
	@Transient
	public List<StudySubjectConsentVersion> getSignedConsents(String studyVersionName) {
		StudyVersion studyVersion = getStudySite().getStudy().getStudyVersion(studyVersionName);
		
		// throw exception when no study version with the name is found
		if(studyVersion == null){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.VERSION_WITH_NAME_NOT_FOUND.CODE"),
					new String[] {studyVersionName });
		}
		
		List<Consent> consentsOnGivenStudyVersion = studyVersion.getConsents();
		List<StudySubjectConsentVersion> informedConsentsOnStudyVersion = new ArrayList<StudySubjectConsentVersion>();
		for(StudySubjectConsentVersion studySubjectConsentVersion :this.getAllSignedConsents()){
			if(consentsOnGivenStudyVersion.contains(studySubjectConsentVersion.getConsent())){
				informedConsentsOnStudyVersion.add(studySubjectConsentVersion);
			}
		}
		
		return informedConsentsOnStudyVersion;
	}
	
	// returns signed and unsigned study subject consent versions on a given study version
	@Transient
	public List<StudySubjectConsentVersion> getConsents(String studyVersionName) {
		StudyVersion studyVersion = getStudySite().getStudy().getStudyVersion(studyVersionName);
		
		// throw exception when no study version with the name is found
		if(studyVersion == null){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.VERSION_WITH_NAME_NOT_FOUND.CODE"),
					new String[] {studyVersionName });
		}
		
		List<Consent> consentsOnGivenStudyVersion = studyVersion.getConsents();
		List<StudySubjectConsentVersion> informedConsentsOnStudyVersion = new ArrayList<StudySubjectConsentVersion>();
		for(StudySubjectConsentVersion studySubjectConsentVersion :this.getAllConsents()){
			if(consentsOnGivenStudyVersion.contains(studySubjectConsentVersion.getConsent())){
				informedConsentsOnStudyVersion.add(studySubjectConsentVersion);
			}
		}
		
		return informedConsentsOnStudyVersion;
	}
	
	// returns null if a study version with the given name cannot be found in the study subject study version
	@Transient
	public Boolean hasSignedConsents(String studyVersionName) {
		
		return (getSignedConsents(studyVersionName).size() > 0);
		
	}

	@Transient
	public Boolean canReConsent(String studyVersionName) {
		
		// a subject can re consent only if his/her registration is in reserved, registered but not 
		//enrolled or enrolled status. 
		
		if(getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING || getRegWorkflowStatus() == 
			RegistrationWorkFlowStatus.NOT_REGISTERED || getRegWorkflowStatus() == RegistrationWorkFlowStatus.OFF_STUDY || 
			getRegWorkflowStatus() == RegistrationWorkFlowStatus.INVALID ){
				return false;
			}
		
		// obtain the study version object from study based on study version name
		StudyVersion reConsentingStudyVersion = this.getStudySite().getStudy().getStudyVersion(studyVersionName);
		
		// throw exception when no study version is found in study with given name
		if(reConsentingStudyVersion == null){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.VERSION_WITH_NAME_NOT_FOUND.CODE"),
					new String[] {studyVersionName });
		}
		
		// Subject cannot re consent on a study version if he/she already signed consent forms on this version.
				if(hasSignedConsents(studyVersionName)) {
			return false;
		}
				
		// Subject cannot re consent on a study version if he/she signed consent forms on a study version after this one.
				for (StudyVersion studyVersion :this.getStudySite().getStudy().getStudyVersions()){
			if(studyVersion.getVersionDate().after(reConsentingStudyVersion.getVersionDate()) && hasSignedConsents(studyVersion.getName()))
				return false;
		}
		
		return true;
		
	}
	
	@Transient
	public void reConsent(String studyVersionName, List<StudySubjectConsentVersion> studySubjectConsentVersionsHolder) {
		
		log.debug("Calling ReConsent API");
		if(studySubjectConsentVersionsHolder == null || studySubjectConsentVersionsHolder.size()==0){
			throw new C3PRBaseRuntimeException("Null consent(s) passed in the arguments of reConsent() API");
		}
		if(canReConsent(studyVersionName)){
			
			// obtain the study version object from study based on study version name
			StudyVersion reConsentingStudyVersion = this.getStudySite().getStudy().getStudyVersion(studyVersionName);
		
			for(StudySubjectConsentVersion studySubjectConsentVersionHolder :studySubjectConsentVersionsHolder){
				
				StudySubjectConsentVersion newStudySubjectConsentVersion = new StudySubjectConsentVersion();
				
				// retrieve consent in the study version based on name 
				if(studySubjectConsentVersionHolder.getConsent() == null || StringUtils.isBlank(studySubjectConsentVersionHolder.getConsent().getName())){
					throw new C3PRBaseRuntimeException("Null consent or consent name passed in arguments of reConsent() API");
				}
				Consent newConsent = reConsentingStudyVersion.getConsentByName(studySubjectConsentVersionHolder.getConsent().getName());
				
				// throw exception if no consent is found in study version with the given name
				if(newConsent == null){
					throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.VERSION_CONSENT_WITH_NAME_NOT_FOUND.CODE"),
							new String[] {studySubjectConsentVersionHolder.getConsent().getName(),studyVersionName });
				}
				
				// set the retrieved consent to the new study subject consent version
				newStudySubjectConsentVersion.setConsent(newConsent);
				
				// validate consenting method, consent delivery and signed dates
				if(studySubjectConsentVersionHolder.getConsentingMethod()!=null){
					validateConsentingMethod(reConsentingStudyVersion, newConsent, studySubjectConsentVersionHolder.getConsentingMethod());
				}
				validateInformedConsentSignedDateAndDeliveryDate(reConsentingStudyVersion, newConsent, studySubjectConsentVersionHolder
						.getConsentDeliveryDate(), studySubjectConsentVersionHolder.getInformedConsentSignedDate());
				
				
				// copy other information into new study subject consent version from the consent data holder
				newStudySubjectConsentVersion.setConsentingMethod(studySubjectConsentVersionHolder.getConsentingMethod());
				newStudySubjectConsentVersion.setConsentPresenter(studySubjectConsentVersionHolder.getConsentPresenter());
				newStudySubjectConsentVersion.setConsentDeliveryDate(studySubjectConsentVersionHolder.getConsentDeliveryDate());
				
				newStudySubjectConsentVersion.setInformedConsentSignedDate(studySubjectConsentVersionHolder.getInformedConsentSignedDate());
				
				// Create new study subject study version and add study subject consent versions to it.
				StudySubjectStudyVersion studySubjectStudyVersion = new StudySubjectStudyVersion();
				studySubjectStudyVersion.setStudySiteStudyVersion(this.getStudySite().getStudySiteStudyVersionGivenStudyVersionName(studyVersionName));
				this.getStudySubjectStudyVersion().addStudySubjectConsentVersion(newStudySubjectConsentVersion);
				this.addStudySubjectStudyVersion(studySubjectStudyVersion);
			}
			// validate mandatory indicator
			validateMandatoryInformedConsents(reConsentingStudyVersion);
		} else{
		
			// throw exception when study subject cannot consent on the given study version.
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.REGISTRATION.CANNOT_RECONSENT.CODE"),
					new String[] {studyVersionName });
		}
		
	}
	
	@Transient
	private void validateConsentingMethod(StudyVersion studyVersion, Consent consent, ConsentingMethod consentingMethod){
		if(!consent.getConsentingMethods().contains(consentingMethod)){
			// throw exception when consenting method is not found in the consent from study version.
			throw getC3PRExceptionHelper().getRuntimeException(getCode
					("C3PR.EXCEPTION.REGISTRATION.CONSENTING_METHOD_NOT_FOUND_IN_STUDY_VERSION_CONSENT.CODE"),
					new String[] {consentingMethod.getName(),consent.getName(),studyVersion.getName()}); 
		}
	
	}
	
	@Transient
	private void validateInformedConsentSignedDateAndDeliveryDate(StudyVersion studyVersion, Consent consent, Date consentDeliveryDate, Date
			consentSignedDate){
		
		if (consentDeliveryDate !=null && consentDeliveryDate.after(new Date())){
			throw getC3PRExceptionHelper().getRuntimeException(getCode
					("C3PR.EXCEPTION.REGISTRATION.CONSENT_DELIVERY_DATE_CANNOT_BE_IN_FUTURE.CODE"),
					new String[] {consent.getName()}); 
		}
		
		if (consentSignedDate !=null && consentSignedDate.after(new Date())){
			throw getC3PRExceptionHelper().getRuntimeException(getCode
					("C3PR.EXCEPTION.REGISTRATION.CONSENT_SIGNED_DATE_CANNOT_BE_IN_FUTURE.CODE"),
					new String[] {consent.getName()}); 
		}
		if(consentSignedDate !=null && consentDeliveryDate !=null && consentDeliveryDate.after(consentSignedDate)){
			throw getC3PRExceptionHelper().getRuntimeException(getCode
					("C3PR.EXCEPTION.REGISTRATION.CONSENT_SIGNED_DATE_CANNOT_BE_BEFORE_DELIVERY_DATE.CODE"),
					new String[] {consent.getName()}); 
		}
		
		if(consentSignedDate !=null){
			if (!getStudySite().canEnroll(studyVersion , consentSignedDate)){
				throw getC3PRExceptionHelper().getRuntimeException(getCode
						("C3PR.EXCEPTION.REGISTRATION.CONSENT_SIGNED_DATE_DOES_NOT_BELONG_TO_STUDY_SITE_VERSION.CODE"),
						new String[] {consent.getName()}); 
			}
		}
		
	}
	
	@Transient
	private void validateMandatoryInformedConsents(StudyVersion studyVersion){
		for(Consent consent: studyVersion.getConsents()){
			if(consent.getMandatoryIndicator()){
				if(!getStudySubjectStudyVersion().hasSignedConsent(consent)){
					throw getC3PRExceptionHelper().getRuntimeException(getCode
							("C3PR.EXCEPTION.REGISTRATION.MANDATORY_CONSENT_NOT_SIGNED_IN_STUDY_VERSION.CODE"),
							new String[] {consent.getName()}); 
				}
			}
		}
	}
	
	// END
	// ReConsent API
	
	@Transient
	public boolean canDiscontinueEnrollment(){
		return (this.regWorkflowStatus==RegistrationWorkFlowStatus.PENDING || this.regWorkflowStatus
				==RegistrationWorkFlowStatus.RESERVED || this.regWorkflowStatus==RegistrationWorkFlowStatus.PENDING_ON_STUDY);
	}
	
	@Transient
	public void discontinueEnrollment(List<OffEpochReason> discontinueEpochReasons, Date discontinueEpochDate) {
		ScheduledEpoch scheduledEpoch = getScheduledEpoch();
		if(canDiscontinueEnrollment()){
			scheduledEpoch.takeSubjectOffEpoch(discontinueEpochReasons, discontinueEpochDate);
			setRegWorkflowStatus(RegistrationWorkFlowStatus.NOT_REGISTERED);
		}else{
			throw new C3PRBaseRuntimeException(
					"The subject is not on-study yet. The subject cannot discontinue registration but only be taken off study.");
		}
		
	}
	
		@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_sub_id", nullable=false)
	public List<StudySubjectRegistryStatus> getStudySubjectRegistryStatusHistoryInternal() {
		return studySubjectRegistryStatusHistory;
	}

	public void setStudySubjectRegistryStatusHistoryInternal(
			List<StudySubjectRegistryStatus> studySubjectRegistryStatusHistory) {
		this.studySubjectRegistryStatusHistory = studySubjectRegistryStatusHistory;
	}
	
	@Transient
	public void updateRegistryStatus(String code, Date effectiveDate, List<RegistryStatusReason> reasons){
		PermissibleStudySubjectRegistryStatus status = null;
		for(PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus : getStudySite().getStudy().getPermissibleStudySubjectRegistryStatuses()){
			if(permissibleStudySubjectRegistryStatus.getRegistryStatus().getCode().equals(code)){
				status = permissibleStudySubjectRegistryStatus;
				break;
			}
		}
		if(status == null){
			throw getC3PRExceptionHelper().getRuntimeException(getCode
					("C3PR.EXCEPTION.REGISTRY.INVALID_STATUS.CODE"),
					new String[] {code});
		}
		if(reasons!=null && reasons.size()>0){
			List<RegistryStatusReason> primaryReasons = new ArrayList<RegistryStatusReason>();
			List<RegistryStatusReason> secondaryReasons = new ArrayList<RegistryStatusReason>();
			for(RegistryStatusReason reason : reasons){
				if(reason.getPrimaryIndicator()){
					primaryReasons.add(reason);
				}else{
					secondaryReasons.add(reason);
				}
			}
			if(primaryReasons.size()!=0 && !status.getRegistryStatus().getPrimaryReasons().containsAll(primaryReasons)){
				throw getC3PRExceptionHelper().getRuntimeException(getCode
						("C3PR.EXCEPTION.REGISTRY.INVALID_STATUS_REASON.CODE"),
						new String[] {code});
			}else if(secondaryReasons.size()!=0 && !status.getSecondaryReasons().containsAll(secondaryReasons)){
				throw getC3PRExceptionHelper().getRuntimeException(getCode
						("C3PR.EXCEPTION.REGISTRY.INVALID_STATUS_REASON.CODE"),
						new String[] {code});
			}
			studySubjectRegistryStatusHistory.add(new StudySubjectRegistryStatus(effectiveDate, status, reasons));
		}else{
			studySubjectRegistryStatusHistory.add(new StudySubjectRegistryStatus(effectiveDate, status));
		}
	}
	
	@Transient
	public StudySubjectRegistryStatus getStudySubjectRegistryStatus(){
		return getStudySubjectRegistryStatusHistory().get(0);
	}
	
	@Transient
	public List<StudySubjectRegistryStatus> getStudySubjectRegistryStatusHistory() {
		List<StudySubjectRegistryStatus> sorted = new ArrayList<StudySubjectRegistryStatus>(studySubjectRegistryStatusHistory);
		Collections.sort(sorted, new Comparator<StudySubjectRegistryStatus>(){
			public int compare(StudySubjectRegistryStatus o1,
					StudySubjectRegistryStatus o2) {
				return o1.getEffectiveDate().compareTo(o2.getEffectiveDate());
			}
		});
		Collections.reverse(sorted);
		return sorted;
	} 
		
	}
