package edu.duke.cabig.c3pr.domain;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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

import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.NotificationEmailSubstitutionVariablesEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldAuthorable;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldDefinition;
import edu.duke.cabig.c3pr.domain.customfield.Customizable;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedInstantiateFactory;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.C3PRInvalidDataEntryException;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

// TODO: Auto-generated Javadoc
/**
 * A systematic evaluation of an observation or an intervention (for example,
 * treatment, drug, device, procedure or system) in one or more subjects.
 * Frequently this is a test of a particular hypothesis about the treatment,
 * drug, device, procedure or system. [CDAM] A study can be either primary or
 * correlative. A study is considered a primary study if it has one or more
 * correlative studies. A correlative study extends the objectives or
 * observations of a primary study, enrolling the same, or a subset of the same,
 * subjects as the primary study. A Clinical Trial is a Study with type=
 * "intervention" with subjects of type="human".
 * 
 * @author Priyatam
 */

@Entity
@Table(name = "STUDIES")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDIES_ID_SEQ") })
public class Study extends InteroperableAbstractMutableDeletableDomainObject
		implements Comparable<Study> , Customizable, CustomFieldAuthorable{

	/** The blinded indicator. */
	private Boolean blindedIndicator;

	/** The multi institution indicator. */
	private Boolean multiInstitutionIndicator;

	/** The randomized indicator. */
	private Boolean randomizedIndicator;

	/** The companion indicator. */
	private Boolean companionIndicator;

	/** The stratification indicator. */
	private Boolean stratificationIndicator;

	/** The short title text. */
	private String shortTitleText;

	/** The long title text. */
	private String longTitleText;

	/** The description text. */
	private String descriptionText;

	/** The precis text. */
	private String precisText;

	/** The phase code. */
	private String phaseCode;

	/** The type. */
	private String type;

	/** The primary identifier. */
	private String primaryIdentifier;

	// This is for the CADSR exclusion/inclusion criteria file
	/** The criteria file. */
	private byte[] criteriaFile;

	/** The target accrual number. */
	private Integer targetAccrualNumber;

	/** The randomization type. */
	private RandomizationType randomizationType;

	/** The data entry status. */
	private StudyDataEntryStatus dataEntryStatus;

	/** The coordinating center study status. */
	private CoordinatingCenterStudyStatus coordinatingCenterStudyStatus;

	/** The study diseases. */
	private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();

	/** The study organizations. */
	private List<StudyOrganization> studyOrganizations;

	/** The identifiers. */
	private List<Identifier> identifiers;

	// TODO move into Command Object
	/** The disease term ids. */
	private String[] diseaseTermIds;

	/** The disease category as text. */
	private String diseaseCategoryAsText;

	/** The consent version. */
	private String consentVersion;
	
	/** The consent validity period. */
	private Integer consentValidityPeriod;
	
	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;

	/** The c3 pr exception helper. */
	private C3PRExceptionHelper c3PRExceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;

	/** The acrruals within last week. */
	@Transient
	private int acrrualsWithinLastWeek;

	/** The standalone indicator. */
	private Boolean standaloneIndicator;

	/** The parent study associations. */
	private List<CompanionStudyAssociation> parentStudyAssociations = new ArrayList<CompanionStudyAssociation>();
	
	private ConsentRequired consentRequired ;
	
    @Enumerated(EnumType.STRING)
	public ConsentRequired getConsentRequired() {
		return consentRequired;
	}

	public void setConsentRequired(ConsentRequired consentRequired) {
		this.consentRequired = consentRequired;
	}

	/**
	 * Instantiates a new study.
	 */
	public Study() {
		
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
		blindedIndicator = false;
		multiInstitutionIndicator = false;
		dataEntryStatus = StudyDataEntryStatus.INCOMPLETE;
		standaloneIndicator = true;
		companionIndicator = false;
		setConsentValidityPeriod(90);
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(StudySite.class,new ParameterizedBiDirectionalInstantiateFactory<StudySite>(StudySite.class, this));
		lazyListHelper.add(StudyFundingSponsor.class,new ParameterizedBiDirectionalInstantiateFactory<StudyFundingSponsor>(StudyFundingSponsor.class, this));
		lazyListHelper.add(StudyCoordinatingCenter.class,new ParameterizedBiDirectionalInstantiateFactory<StudyCoordinatingCenter>(StudyCoordinatingCenter.class, this));
		lazyListHelper.add(SystemAssignedIdentifier.class, new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(SystemAssignedIdentifier.class));
		lazyListHelper.add(OrganizationAssignedIdentifier.class,new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(OrganizationAssignedIdentifier.class));
		lazyListHelper.add(StudyAmendment.class,new InstantiateFactory<StudyAmendment>(StudyAmendment.class));
		lazyListHelper.add(PlannedNotification.class,new InstantiateFactory<PlannedNotification>(PlannedNotification.class));
		lazyListHelper.add(Epoch.class,new ParameterizedBiDirectionalInstantiateFactory<Epoch>(Epoch.class, this));
		// mandatory, so that the lazy-projected list is managed properly.
		setStudyOrganizations(new ArrayList<StudyOrganization>());
		setIdentifiers(new ArrayList<Identifier>());
		lazyListHelper.add(CompanionStudyAssociation.class,new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(CompanionStudyAssociation.class, this,"ParentStudy"));
		coordinatingCenterStudyStatus = CoordinatingCenterStudyStatus.PENDING;
		lazyListHelper.add(CustomFieldDefinition.class,new ParameterizedBiDirectionalInstantiateFactory<CustomFieldDefinition>(CustomFieldDefinition.class, this));
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));
		lazyListHelper.add(Consent.class,new ParameterizedBiDirectionalInstantiateFactory<Consent>(Consent.class, this));
	}

	/**
	 * Instantiates a new study.
	 * 
	 * @param forSearchByExample the for search by example
	 */
	public Study(boolean forSearchByExample) {

		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(CompanionStudyAssociation.class,new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(CompanionStudyAssociation.class, this,"ParentStudy"));
		lazyListHelper.add(StudySite.class, new ParameterizedBiDirectionalInstantiateFactory<StudySite>(StudySite.class, this));
		lazyListHelper.add(StudyFundingSponsor.class,new ParameterizedBiDirectionalInstantiateFactory<StudyFundingSponsor>(StudyFundingSponsor.class, this));
		lazyListHelper.add(StudyCoordinatingCenter.class, new ParameterizedBiDirectionalInstantiateFactory<StudyCoordinatingCenter>(StudyCoordinatingCenter.class, this));
		lazyListHelper.add(Epoch.class,new ParameterizedBiDirectionalInstantiateFactory<Epoch>(Epoch.class, this));
		lazyListHelper.add(SystemAssignedIdentifier.class,new ParameterizedInstantiateFactory<SystemAssignedIdentifier>( SystemAssignedIdentifier.class));
		lazyListHelper.add(OrganizationAssignedIdentifier.class,new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(OrganizationAssignedIdentifier.class));
		lazyListHelper.add(StudyAmendment.class,new InstantiateFactory<StudyAmendment>(StudyAmendment.class));
		lazyListHelper.add(PlannedNotification.class,new InstantiateFactory<PlannedNotification>(PlannedNotification.class));
		// mandatory, so that the lazy-projected list is managed properly.
		setStudyOrganizations(new ArrayList<StudyOrganization>());
		setIdentifiers(new ArrayList<Identifier>());
		if (!forSearchByExample) {
			blindedIndicator = false;
			multiInstitutionIndicator = false;
			stratificationIndicator = true;
		}
		lazyListHelper.add(CustomFieldDefinition.class,new ParameterizedBiDirectionalInstantiateFactory<CustomFieldDefinition>(CustomFieldDefinition.class, this));
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));
		lazyListHelper.add(Consent.class,new InstantiateFactory<Consent>(Consent.class));
	}

	/**
	 * Can open.
	 * 
	 * @return the list< error>
	 */
	public List<Error> canOpen() {
		List<Error> errors = new ArrayList<Error>();
		evaluateDataEntryStatus(errors);
		return errors;
	}

	/**
	 * Gets the local identifiers.
	 * 
	 * @return the local identifiers
	 */
	@Transient
	public List<Identifier> getLocalIdentifiers() {
		List<Identifier> localIdentifiers = new ArrayList<Identifier>();
		for (Identifier identifier : getIdentifiers()) {
			if ("Protocol Authority Identifier".equals(identifier.getType()) || "Coordinating Center Identifier".equals(identifier
							.getType())) {
				// nothing
			} else {
				localIdentifiers.add(identifier);
			}
		}
		return localIdentifiers;
	}

	/**
	 * Gets the study sites.
	 * 
	 * @return the study sites
	 */
	@Transient
	public List<StudySite> getStudySites() {
		return lazyListHelper.getLazyList(StudySite.class);
	}

	/**
	 * Gets the study funding sponsors.
	 * 
	 * @return the study funding sponsors
	 */
	@Transient
	public List<StudyFundingSponsor> getStudyFundingSponsors() {
		return lazyListHelper.getLazyList(StudyFundingSponsor.class);
	}

	
	/**
	 * Gets the study coordinating centers.
	 * 
	 * @return the study coordinating centers
	 */
	@Transient
	public List<StudyCoordinatingCenter> getStudyCoordinatingCenters() {
		return lazyListHelper.getLazyList(StudyCoordinatingCenter.class);
	}

	/**
	 * Gets the study organizations.
	 * 
	 * @return the study organizations
	 */
	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "comp_assoc_id  is null")
	@OrderBy
	public List<StudyOrganization> getStudyOrganizations() {
		return studyOrganizations;
	}

	/**
	 * Sets the study organizations.
	 * 
	 * @param studyOrganizations the new study organizations
	 */
	public void setStudyOrganizations(List<StudyOrganization> studyOrganizations) {
		this.studyOrganizations = studyOrganizations;
		// initialize projected list for StudySite, StudyFundingSponsor and
		// StudyCoordinatingCenter
		lazyListHelper.setInternalList(StudySite.class,
				new ProjectedList<StudySite>(this.studyOrganizations,
						StudySite.class));
		lazyListHelper.setInternalList(StudyFundingSponsor.class,
				new ProjectedList<StudyFundingSponsor>(this.studyOrganizations,
						StudyFundingSponsor.class));
		lazyListHelper
				.setInternalList(StudyCoordinatingCenter.class,
						new ProjectedList<StudyCoordinatingCenter>(
								this.studyOrganizations,
								StudyCoordinatingCenter.class));
	}

	/**
	 * Adds the study organization.
	 * 
	 * @param studyOrganization the study organization
	 */
	public void addStudyOrganization(StudyOrganization studyOrganization) {
		this.getStudyOrganizations().add(studyOrganization);
		studyOrganization.setStudy(this);
	}

	/**
	 * Removes the study organization.
	 * 
	 * @param studyOrganization the study organization
	 */
	public void removeStudyOrganization(StudyOrganization studyOrganization) {
		this.getStudyOrganizations().remove(studyOrganization);
	}

	/**
	 * Adds the epoch.
	 * 
	 * @param epoch the epoch
	 * 
	 * @throws RuntimeException the runtime exception
	 */
	public void addEpoch(Epoch epoch) throws RuntimeException {
		for (Epoch epochPresent : getEpochs()) {
			if (epochPresent.equals(epoch)) {
				throw new RuntimeException(
						"epoch with same name already exists in study");
			}
		}
		epoch.setStudy(this);
		getEpochs().add(epoch);
	}

	/**
	 * Removes the epoch.
	 * 
	 * @param epoch the epoch
	 */
	public void removeEpoch(Epoch epoch) {
		lazyListHelper.getLazyList(Epoch.class).remove(epoch);
	}
	
	/**
	 * Removes the study disease.
	 * 
	 * @param studyDisease the study disease
	 */
	public void removeStudyDisease(StudyDisease studyDisease) {
		this.getStudyDiseases().remove(studyDisease);
	}
	
	/**
	 * Removes the all study disease.
	 */
	public void removeAllStudyDisease() {
		this.getStudyDiseases().removeAll(this.getStudyDiseases());
	}
	
	/**
	 * Adds the study site.
	 * 
	 * @param studySite the study site
	 */
	public void addStudySite(StudySite studySite) {
		studySite.setStudy(this);
		lazyListHelper.getLazyList(StudySite.class).add(studySite);
	}

	/**
	 * Removes the study site.
	 * 
	 * @param studySite the study site
	 */
	public void removeStudySite(StudySite studySite) {
		lazyListHelper.getLazyList(StudySite.class).remove(studySite);
	}

	/**
	 * Adds the study disease.
	 * 
	 * @param studyDisease the study disease
	 */
	public void addStudyDisease(StudyDisease studyDisease) {
		studyDisease.setStudy(this);
		studyDiseases.add(studyDisease);
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
	 * Gets the funding sponsor assigned identifier.
	 * 
	 * @return the funding sponsor assigned identifier
	 */
	@Transient
	public OrganizationAssignedIdentifier getFundingSponsorAssignedIdentifier() {
		for (OrganizationAssignedIdentifier orgIdentifier : this
				.getOrganizationAssignedIdentifiers()) {
			if ((orgIdentifier.getType() != null)
					&& (orgIdentifier.getType()
							.equalsIgnoreCase("Protocol Authority Identifier")))
				return orgIdentifier;
		}
		return null;
	}

	/**
	 * Gets the coordinating center assigned identifier.
	 * 
	 * @return the coordinating center assigned identifier
	 */
	@Transient
	public OrganizationAssignedIdentifier getCoordinatingCenterAssignedIdentifier() {
		for (OrganizationAssignedIdentifier orgIdentifier : this
				.getOrganizationAssignedIdentifiers()) {
			if ((orgIdentifier.getType() != null)
					&& (orgIdentifier.getType()
							.equalsIgnoreCase("Coordinating Center Identifier")))
				return orgIdentifier;
		}
		return null;
	}

	/**
	 * Gets the principal investigator full name.
	 * 
	 * @return the principal investigator full name
	 */
	@Transient
	public String getPrincipalInvestigatorFullName() {
		HealthcareSiteInvestigator studyInv = getPrincipalInvestigator();
		if(studyInv != null){
			return studyInv.getInvestigator().getFullName();
		}
		return null ;
	}

	/**
	 * Gets the principal investigator.
	 * 
	 * @return the principal investigator
	 */
	@Transient
	public HealthcareSiteInvestigator getPrincipalInvestigator() {
		StudyInvestigator studyInv = getPrincipalStudyInvestigator();
		if(studyInv != null){
			return studyInv.getHealthcareSiteInvestigator();
		}
		return null ;
	}

	/**
	 * Gets the principal study investigator.
	 * 
	 * @return the principal study investigator
	 */
	@Transient
	public StudyInvestigator getPrincipalStudyInvestigator() {
		for (StudyOrganization studyOrganization : this.getStudyOrganizations()) {
			for (StudyInvestigator studyInvestigator : studyOrganization
					.getStudyInvestigators()) {
				if (studyInvestigator.getRoleCode().equals(
						"Principal Investigator")) {
					return studyInvestigator;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the principal investigator study organization.
	 * 
	 * @return the principal investigator study organization
	 */
	@Transient
	public StudyOrganization getPrincipalInvestigatorStudyOrganization() {
		for (StudyOrganization studyOrganization : this.getStudyOrganizations()) {
			for (StudyInvestigator studyInvestigator : studyOrganization
					.getStudyInvestigators()) {
				if (studyInvestigator.getRoleCode().equals(
						"Principal Investigator")) {
					return studyInvestigator.getStudyOrganization();
				}
			}
		}
		return null;
	}

	/**
	 * Gets the identifiers.
	 * 
	 * @return the identifiers
	 */
	@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "STU_ID")
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
	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
		lazyListHelper.setInternalList(SystemAssignedIdentifier.class,
				new ProjectedList<SystemAssignedIdentifier>(this.identifiers,
						SystemAssignedIdentifier.class));
		lazyListHelper
				.setInternalList(OrganizationAssignedIdentifier.class,
						new ProjectedList<OrganizationAssignedIdentifier>(
								this.identifiers,
								OrganizationAssignedIdentifier.class));
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
	 * Gets the epochs internal.
	 * 
	 * @return the epochs internal
	 */
	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy("epochOrder")
	public List<Epoch> getEpochsInternal() {
		return lazyListHelper.getInternalList(Epoch.class);
	}

	/**
	 * Gets the epochs.
	 * 
	 * @return the epochs
	 */
	@Transient
	public List<Epoch> getEpochs() {
		return lazyListHelper.getLazyList(Epoch.class);
	}

	/**
	 * Sets the epochs.
	 * 
	 * @param epochs the new epochs
	 */
	public void setEpochs(List<Epoch> epochs) {
		setEpochsInternal(epochs);
	}

	/**
	 * Adds the amendment.
	 * 
	 * @param amendment the amendment
	 */
	public void addAmendment(final StudyAmendment amendment) {
		getStudyAmendments().add(amendment);
	}

	/**
	 * Gets the study amendments internal.
	 * 
	 * @return the study amendments internal
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "stu_id", nullable = false)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudyAmendment> getStudyAmendmentsInternal() {
		return lazyListHelper.getInternalList(StudyAmendment.class);
	}

	/**
	 * Sets the study amendments internal.
	 * 
	 * @param amendments the new study amendments internal
	 */
	public void setStudyAmendmentsInternal(final List<StudyAmendment> amendments) {
		lazyListHelper.setInternalList(StudyAmendment.class, amendments);
	}

	/**
	 * Gets the study amendments.
	 * 
	 * @return the study amendments
	 */
	@Transient
	public List<StudyAmendment> getStudyAmendments() {
		return lazyListHelper.getLazyList(StudyAmendment.class);
	}

	/**
	 * Gets the previous study amendments.
	 * 
	 * @return the previous study amendments
	 */
	@Transient
	public List<StudyAmendment> getPreviousStudyAmendments() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING) {
			if (getStudyAmendments().size() > 1) {
				return this.getStudyAmendments().subList(0,
						getStudyAmendments().size() - 1);
			} else {
				return null;
			}
		} else
			return getStudyAmendments();
	}

	/**
	 * Gets the current study amendment.
	 * 
	 * @return the current study amendment
	 */
	@Transient
	public StudyAmendment getCurrentStudyAmendment() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING) {
			return this.getStudyAmendments().get(
					getStudyAmendments().size() - 1);
		} else
			return null;
	}

	/**
	 * Sets the study amendments.
	 * 
	 * @param amendments the new study amendments
	 */
	public void setStudyAmendments(final List<StudyAmendment> amendments) {
		setStudyAmendmentsInternal(amendments);
	}

	/**
	 * Gets the planned notifications internal.
	 * 
	 * @return the planned notifications internal
	 */
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "stu_id", nullable = true)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy("id")
	public List<PlannedNotification> getPlannedNotificationsInternal() {
		return lazyListHelper.getInternalList(PlannedNotification.class);
	}

	/**
	 * Sets the planned notifications internal.
	 * 
	 * @param plannedNotifications the new planned notifications internal
	 */
	public void setPlannedNotificationsInternal(
			final List<PlannedNotification> plannedNotifications) {
		lazyListHelper.setInternalList(PlannedNotification.class,
				plannedNotifications);
	}

	/**
	 * Gets the planned notifications.
	 * 
	 * @return the planned notifications
	 */
	@Transient
	public List<PlannedNotification> getPlannedNotifications() {
		return lazyListHelper.getLazyList(PlannedNotification.class);
	}

	/**
	 * Sets the epochs internal.
	 * 
	 * @param epochs the new epochs internal
	 */
	public void setEpochsInternal(final List<Epoch> epochs) {
		lazyListHelper.setInternalList(Epoch.class, epochs);
	}

	/**
	 * Gets the study diseases.
	 * 
	 * @return the study diseases
	 */
	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudyDisease> getStudyDiseases() {
		return studyDiseases;
	}

	/**
	 * Sets the study diseases.
	 * 
	 * @param studyDiseases the new study diseases
	 */
	public void setStudyDiseases(List<StudyDisease> studyDiseases) {
		this.studyDiseases = studyDiseases;
	}

	/**
	 * Gets the description text.
	 * 
	 * @return the description text
	 */
	public String getDescriptionText() {
		return descriptionText;
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
	 * Gets the long title text.
	 * 
	 * @return the long title text
	 */
	public String getLongTitleText() {
		return longTitleText;
	}

	/**
	 * Sets the long title text.
	 * 
	 * @param longTitleText the new long title text
	 */
	public void setLongTitleText(String longTitleText) {
		this.longTitleText = longTitleText;
	}

	/**
	 * Gets the phase code.
	 * 
	 * @return the phase code
	 */
	public String getPhaseCode() {
		return phaseCode;
	}

	/**
	 * Sets the phase code.
	 * 
	 * @param phaseCode the new phase code
	 */
	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}

	/**
	 * Gets the precis text.
	 * 
	 * @return the precis text
	 */
	public String getPrecisText() {
		return precisText;
	}

	/**
	 * Sets the precis text.
	 * 
	 * @param precisText the new precis text
	 */
	public void setPrecisText(String precisText) {
		this.precisText = precisText;
	}

	/**
	 * Gets the short title text.
	 * 
	 * @return the short title text
	 */
	public String getShortTitleText() {
		return shortTitleText;
	}

	/**
	 * Sets the short title text.
	 * 
	 * @param shortTitleText the new short title text
	 */
	public void setShortTitleText(String shortTitleText) {
		this.shortTitleText = shortTitleText;
	}

	/**
	 * Gets the target accrual number.
	 * 
	 * @return the target accrual number
	 */
	public Integer getTargetAccrualNumber() {
		return targetAccrualNumber;
	}

	/**
	 * Sets the target accrual number.
	 * 
	 * @param targetAccrualNumber the new target accrual number
	 */
	public void setTargetAccrualNumber(Integer targetAccrualNumber) {
		this.targetAccrualNumber = targetAccrualNumber;
	}

	/**
	 * Gets the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 * 
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Study o) {
		if (this.equals(o))
			return 0;

		return 1;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME
				* result
				+ ((getCoordinatingCenterAssignedIdentifier() == null) ? 0
						: getCoordinatingCenterAssignedIdentifier().hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final Study other = (Study) obj;
		if ((this.getCoordinatingCenterAssignedIdentifier() == null)
				|| (other.getCoordinatingCenterAssignedIdentifier() == null)) {
			return false;
		} else if (!((this.getCoordinatingCenterAssignedIdentifier().getValue())
				.equalsIgnoreCase(other
						.getCoordinatingCenterAssignedIdentifier().getValue()))) {
			return false;
		} else if (!(this.getCoordinatingCenterAssignedIdentifier()
				.equals(other.getCoordinatingCenterAssignedIdentifier()))) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the trimmed short title text.
	 * 
	 * @return the trimmed short title text
	 */
	@Transient
	public String getTrimmedShortTitleText() {
		return StringUtils.getTrimmedText(shortTitleText, 40);
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

		return primaryIdentifier;
	}

	/**
	 * Gets the blinded indicator.
	 * 
	 * @return the blinded indicator
	 */
	public Boolean getBlindedIndicator() {
		return blindedIndicator;
	}

	/**
	 * Sets the blinded indicator.
	 * 
	 * @param blindedIndicator the new blinded indicator
	 */
	public void setBlindedIndicator(Boolean blindedIndicator) {
		this.blindedIndicator = blindedIndicator;
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
	 * Gets the multi institution indicator.
	 * 
	 * @return the multi institution indicator
	 */
	public Boolean getMultiInstitutionIndicator() {
		return multiInstitutionIndicator;
	}

	/**
	 * Sets the multi institution indicator.
	 * 
	 * @param multiInstitutionIndicator the new multi institution indicator
	 */
	public void setMultiInstitutionIndicator(Boolean multiInstitutionIndicator) {
		this.multiInstitutionIndicator = multiInstitutionIndicator;
	}

	/**
	 * Gets the randomization type.
	 * 
	 * @return the randomization type
	 */
	public RandomizationType getRandomizationType() {
		return randomizationType;
	}

	/**
	 * Sets the randomization type.
	 * 
	 * @param randomizationType the new randomization type
	 */
	public void setRandomizationType(RandomizationType randomizationType) {
		this.randomizationType = randomizationType;
	}

	/**
	 * Gets the funding sponsor identifier index.
	 * 
	 * @return the funding sponsor identifier index
	 */
	@Transient
	public int getFundingSponsorIdentifierIndex() {
		int i = -1 ;
		for(OrganizationAssignedIdentifier identifier : this.getOrganizationAssignedIdentifiers()){
			i++ ;
			if(identifier.getType() != null && StringUtils.equals("Protocol Authority Identifier", identifier.getType())){
				return i ;
			}
		}
		
		return -1;
	}

	/**
	 * Checks for elligibility.
	 * 
	 * @return true, if successful
	 */
	@Transient
	public boolean hasElligibility() {

		for (Epoch epoch : this.getEpochs()) {
			if (epoch.hasEligibility())
				return true;
		}
		return false;
	}

	/**
	 * Checks for companions.
	 * 
	 * @return true, if successful
	 */
	@Transient
	public boolean hasCompanions() {
		List<CompanionStudyAssociation> companionStudyAssociations = new ArrayList<CompanionStudyAssociation>();
		companionStudyAssociations.addAll(this.getCompanionStudyAssociations());
		if (companionStudyAssociations.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Checks for randomized epoch.
	 * 
	 * @return true, if successful
	 */
	@Transient
	public boolean hasRandomizedEpoch() {
		for (Epoch epoch : this.getEpochs()) {
			if (epoch.getRandomizedIndicator())
				return true;
		}
		return false;
	}

	/**
	 * Checks for stratified epoch.
	 * 
	 * @return true, if successful
	 */
	@Transient
	public boolean hasStratifiedEpoch() {
		for (Epoch epoch : this.getEpochs()) {
			if (epoch.getStratificationIndicator())
				return true;
		}
		return false;
	}

	/**
	 * Checks for enrolling epoch.
	 * 
	 * @return true, if successful
	 */
	@Transient
	public boolean hasEnrollingEpoch() {
		for (Epoch epoch : this.getEpochs()) {
			if (epoch.getEnrollmentIndicator())
				return true;
		}
		return false;
	}

	/**
	 * Gets the checks for registered participants.
	 * 
	 * @return the checks for registered participants
	 */
	@Transient
	public boolean getHasRegisteredParticipants() {
		for(StudySite studySite : getStudySites()){
			if (studySite.getStudySubjects().size() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the data entry status.
	 * 
	 * @return the data entry status
	 */
	@Enumerated(EnumType.STRING)
	public StudyDataEntryStatus getDataEntryStatus() {
		return dataEntryStatus;
	}

	/**
	 * Sets the data entry status.
	 * 
	 * @param dataEntryStatus the new data entry status
	 */
	public void setDataEntryStatus(StudyDataEntryStatus dataEntryStatus) {
		this.dataEntryStatus = dataEntryStatus;
	}

	/**
	 * Gets the coordinating center study status.
	 * 
	 * @return the coordinating center study status
	 */
	@Transient
	public CoordinatingCenterStudyStatus getCoordinatingCenterStudyStatus() {
		return this.getCoordinatingCenterStudyStatusInternal();
	}

	/**
	 * Sets the coordinating center study status.
	 * 
	 * @param coordinatingCenterStudyStatus the new coordinating center study status
	 */
	public void setCoordinatingCenterStudyStatus(
			CoordinatingCenterStudyStatus coordinatingCenterStudyStatus) {
            for(StudySite studySite: this.getStudySites()){
                if(studySite.getHostedMode() || studySite.getIsCoordinatingCenter())
                    studySite.setCoordinatingCenterStudyStatus(coordinatingCenterStudyStatus);
            }
            for(CompanionStudyAssociation parentStudyAssociation : this.getParentStudyAssociations()){
            	for(StudySite studySite : parentStudyAssociation.getStudySites()){
                    if(studySite.getHostedMode() || studySite.getIsCoordinatingCenter())
                        studySite.setCoordinatingCenterStudyStatus(coordinatingCenterStudyStatus);
                }
            }
            this.setCoordinatingCenterStudyStatusInternal(coordinatingCenterStudyStatus);
	}
	
	/**
	 * Gets the coordinating center study status internal.
	 * 
	 * @return the coordinating center study status internal
	 */
	@Column(name = "status")
        @Enumerated(EnumType.STRING)
        public CoordinatingCenterStudyStatus getCoordinatingCenterStudyStatusInternal() {
                return coordinatingCenterStudyStatus;
        }

        /**
         * Sets the coordinating center study status internal.
         * 
         * @param coordinatingCenterStudyStatus the new coordinating center study status internal
         */
        public void setCoordinatingCenterStudyStatusInternal(
                        CoordinatingCenterStudyStatus coordinatingCenterStudyStatus) {
            this.coordinatingCenterStudyStatus=coordinatingCenterStudyStatus;
        }

	/**
	 * Gets the consent version.
	 * 
	 * @return the consent version
	 */
	public String getConsentVersion() {
		return consentVersion;
	}

	/**
	 * Sets the consent version.
	 * 
	 * @param consentVersion the new consent version
	 */
	public void setConsentVersion(String consentVersion) {
		this.consentVersion = consentVersion;
	}

	/**
	 * Gets the criteria file.
	 * 
	 * @return the criteria file
	 */
	@Transient
	public byte[] getCriteriaFile() {
		return criteriaFile;
	}

	/**
	 * Sets the criteria file.
	 * 
	 * @param criteriaFile the new criteria file
	 */
	public void setCriteriaFile(byte[] criteriaFile) {
		this.criteriaFile = criteriaFile;
	}

	/**
	 * Gets the criteria reader.
	 * 
	 * @return the criteria reader
	 */
	@Transient
	public Reader getCriteriaReader() {
		if (criteriaFile != null) {
			return new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(criteriaFile)));
		}
		return null;
	}

	/**
	 * Gets the criteria input stream.
	 * 
	 * @return the criteria input stream
	 */
	@Transient
	public InputStream getCriteriaInputStream() {
		if (criteriaFile != null) {
			return new ByteArrayInputStream(criteriaFile);
		}
		return null;
	}

	/**
	 * Gets the epoch by name.
	 * 
	 * @param name the name
	 * 
	 * @return the epoch by name
	 */
	@Transient
	public Epoch getEpochByName(String name) {
		List<Epoch> epochList = getEpochs();
		Epoch epoch = null;
		Iterator eIter = epochList.iterator();
		while (eIter.hasNext()) {
			epoch = (Epoch) eIter.next();
			if (epoch.getName().equalsIgnoreCase(name)) {
				return epoch;
			}
		}
		return null;
	}

	/**
	 * Update data entry status.
	 */
	public void updateDataEntryStatus() {
		List<Error> errors = new ArrayList<Error>();
		this.setDataEntryStatus(evaluateDataEntryStatus(errors));
	}

	/**
	 * Evaluate coordinating center study status.
	 * 
	 * @return the coordinating center study status
	 * 
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus()
			throws C3PRCodedException {

		List<Error> errors = new ArrayList<Error>();
		if (evaluateDataEntryStatus(errors) != StudyDataEntryStatus.COMPLETE) {
			return CoordinatingCenterStudyStatus.PENDING;
		}
		if (this.getId() != null) {
			if (!evaluateAmendmentStatus()) {
				return CoordinatingCenterStudyStatus.AMENDMENT_PENDING;
			}
		}

		if (this.getCompanionIndicator() && !this.standaloneIndicator) {
			return CoordinatingCenterStudyStatus.READY_TO_OPEN;
		} else {
			return CoordinatingCenterStudyStatus.OPEN;
		}

	}

	/**
	 * Evaluate amendment status.
	 * 
	 * @return true, if successful
	 * 
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	public boolean evaluateAmendmentStatus() throws C3PRCodedException {

		if (this.getStudyAmendments().size() > 0) {
			StudyAmendment latestAmendment = this.getStudyAmendments().get(
					this.getStudyAmendments().size() - 1);

			if ((this.getId() != null && (latestAmendment.getAmendmentDate() == null))) {
				throw getC3PRExceptionHelper()
						.getException(
								getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.MISSING.INVALID_AMENDMENT_DATE.CODE"));
			}
			if ((this.getId() != null && (latestAmendment.getVersion() == null))) {
				throw getC3PRExceptionHelper()
						.getException(
								getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.MISSING.VERSION.CODE"));
			}
			if ((latestAmendment.getAmendmentDate() != null)
					&& (latestAmendment.getAmendmentDate().after(new Date()))) {
				if ((this.getId() != null)) {
					throw getC3PRExceptionHelper()
							.getException(
									getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.MISSING.INVALID.EXPIRED.AMENDMENT_DATE.CODE"));
				}
				return false;
			}
			if ((latestAmendment.getConsentChangedIndicator())
					|| (latestAmendment.getDiseasesChangedIndicator())
					|| (latestAmendment.getEligibilityChangedIndicator())
					|| (latestAmendment.getEaChangedIndicator())
					|| (latestAmendment.getStratChangedIndicator())
					|| (latestAmendment.getPiChangedIndicator())
					|| (latestAmendment.getCompanionChangedIndicator())
					|| (latestAmendment.getRandomizationChangedIndicator())) {
				return true;
			} else {
				if ((this.getId() != null)) {
					throw getC3PRExceptionHelper()
							.getException(
									getCode("C3PR.EXCEPTION.STUDY.AMENDMENT.MISSING.AMENDED_ITEMS.CODE"));
				}
				return false;
			}

		}
		return true;
	}

	/**
	 * Evaluate data entry status.
	 * 
	 * @param errors the errors
	 * 
	 * @return the study data entry status
	 */
	public StudyDataEntryStatus evaluateDataEntryStatus(List<Error> errors) {
//		if ((!this.hasConsentVersion())) {
//			errors
//					.add(new Error(
//							getC3PRExceptionHelper()
//									.getRuntimeException(
//											getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.CONSENT_VERSION.CODE"))
//									.getMessage()));
//		}

		if ((!this.hasEnrollingEpoch())) {
			errors
					.add(new Error(
							getC3PRExceptionHelper()
									.getRuntimeException(
											getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE"))
									.getMessage()));
		}

		if (this.getRandomizedIndicator()) {
			if (!(this.hasRandomizedEpoch())) {
				errors
						.add(new Error(
								getC3PRExceptionHelper()
										.getRuntimeException(
												getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.RANDOMIZED_EPOCH_FOR_RANDOMIZED_STUDY.CODE"))
										.getMessage()));
			}
		}
		if (this.getStratificationIndicator()) {
			if (!(this.hasStratifiedEpoch())) {
				errors
						.add(new Error(
								getC3PRExceptionHelper()
										.getRuntimeException(
												getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STRATIFIED_EPOCH_FOR_STRATIFIED_STUDY.CODE"))
										.getMessage()));
			}
		}

		for (CompanionStudyAssociation compStudyAssoc : this
				.getCompanionStudyAssociations()) {
			if (compStudyAssoc.getMandatoryIndicator() != null) {
				if (compStudyAssoc.getMandatoryIndicator()
						&& !(compStudyAssoc.getCompanionStudy()
								.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.READY_TO_OPEN || compStudyAssoc
								.getCompanionStudy()
								.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN)) {
					errors
							.add(new Error(
									getC3PRExceptionHelper()
											.getRuntimeException(
													getCode("C3PR.EXCEPTION.STUDY.STATUS.COMPANION_STUDY.CODE"))
											.getMessage()));
				}
			}

		}
		evaluateEpochsDataEntryStatus(errors);
		
		return errors.size() == 0 ? StudyDataEntryStatus.COMPLETE
				: StudyDataEntryStatus.INCOMPLETE;
	}

	/**
	 * Checks for consent version.
	 * 
	 * @return true, if successful
	 */
	private boolean hasConsentVersion() {
		boolean hasConsentVersion = false ;
		for(Consent consent : this.getConsents()){
			if(consent.getConsentVersions().size() == 0){
				hasConsentVersion = false;
				break;
			}else{
				hasConsentVersion = true;
			}
		}
		return hasConsentVersion;
	}

	/**
	 * Open.
	 */
	public void open() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING) {
			this.readyToOpen();
		}
		if (!(this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.READY_TO_OPEN
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL || this
				.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
			throw getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.STATUS_CANNOT_SET_TO_ACTIVE.CODE"),
							new String[] { this
									.getCoordinatingCenterStudyStatus()
									.getDisplayName() });
		}
		if (this.companionIndicator && !this.standaloneIndicator) {
			for (int i = 0; i < this.parentStudyAssociations.size(); i++) {
				if (this.parentStudyAssociations.get(i).getParentStudy()
						.getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.OPEN) {
					throw getC3PRExceptionHelper()
							.getRuntimeException(
									getCode("C3PR.EXCEPTION.STUDY.STATUS.COMPANION_STUDY_OPEN.CODE"));
				}
			}
		}
		if (this.getCompanionStudyAssociations().size() > 0) {
			for (CompanionStudyAssociation compStudyAssoc : this
					.getCompanionStudyAssociations()) {
				if (compStudyAssoc.getCompanionStudy()
						.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.READY_TO_OPEN
						|| compStudyAssoc.getCompanionStudy()
								.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN) {
					compStudyAssoc.getCompanionStudy()
							.setCoordinatingCenterStudyStatus(
									CoordinatingCenterStudyStatus.OPEN);
				} else if (compStudyAssoc.getMandatoryIndicator()) {
					throw getC3PRExceptionHelper()
							.getRuntimeException(
									getCode("C3PR.EXCEPTION.STUDY.STATUS.COMPANION_STUDY.CODE"));
				}
			}
		}
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
	}

	/**
	 * Ready to open.
	 * 
	 * @throws C3PRInvalidDataEntryException the c3 pr invalid data entry exception
	 */
	public void readyToOpen() throws C3PRInvalidDataEntryException {

		if (!(this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT || this
				.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING)) {
			throw getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.STATUS_CANNOT_SET_TO_ACTIVE.CODE"),
							new String[] { this
									.getCoordinatingCenterStudyStatus()
									.getDisplayName() });
		}

		List<Error> errors = new ArrayList<Error>();

		errors = canOpen();
		if (errors.size() > 0) {
			throw new C3PRInvalidDataEntryException(
					" Study is invalid because data entry is not complete",
					errors);
		}
		
		this.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);

	}

	/**
	 * Close to accrual.
	 */
	public void closeToAccrual() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			throw getC3PRExceptionHelper()
			.getRuntimeException(
					getCode("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE"),
					new String[] { CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL
							.getDisplayName() });
		}
		
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING)
			throw getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
							new String[] { CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL
									.getDisplayName() });
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	}

	/**
	 * Close to accrual and treatment.
	 */
	public void closeToAccrualAndTreatment() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			throw getC3PRExceptionHelper()
			.getRuntimeException(
					getCode("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE"),
					new String[] { CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT
							.getDisplayName() });
		}

		if (((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.PENDING))
				|| ((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING)))
			throw getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
							new String[] { CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT
									.getDisplayName() });
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
	}

	/**
	 * Pending.
	 */
	public void pending() {
		this
				.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
	}

	/**
	 * Temporarily close to accrual and treatment.
	 */
	public void temporarilyCloseToAccrualAndTreatment() {

		if (((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.PENDING))
				|| ((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
				|| ((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL))
				|| ((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
			throw getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
							new String[] { CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT
									.getDisplayName() });
		}
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
	}

	/**
	 * Temporarily close to accrual.
	 */
	public void temporarilyCloseToAccrual() {

		if (((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.PENDING))
				|| ((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
				|| ((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL))
				|| ((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
			throw getC3PRExceptionHelper()
					.getRuntimeException(
							getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
							new String[] { CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL
									.getDisplayName() });
		}
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
	}

	/**
	 * Pending amendment.
	 */
	public void pendingAmendment() {
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.AMENDMENT_PENDING);

	}

	/**
	 * Evaluate epochs data entry status.
	 * 
	 * @param errors the errors
	 * 
	 * @throws C3PRCodedRuntimeException the c3 pr coded runtime exception
	 */
	public void evaluateEpochsDataEntryStatus(List<Error> errors)
			throws C3PRCodedRuntimeException {
		for (Epoch epoch : this.getEpochs()) {
			epoch.evaluateStatus(errors);
		}
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
	 * Gets the acrruals within last week.
	 * 
	 * @return the acrruals within last week
	 */
	@Transient
	public int getAcrrualsWithinLastWeek() {
		return this.acrrualsWithinLastWeek;
	}

	/**
	 * Sets the acrruals within last week.
	 * 
	 * @param acrrualsWithinLastWeek the new acrruals within last week
	 */
	public void setAcrrualsWithinLastWeek(int acrrualsWithinLastWeek) {
		this.acrrualsWithinLastWeek = acrrualsWithinLastWeek;
	}

	/**
	 * Gets the standalone indicator.
	 * 
	 * @return the standalone indicator
	 */
	public Boolean getStandaloneIndicator() {
		return standaloneIndicator;
	}

	/**
	 * Sets the standalone indicator.
	 * 
	 * @param standaloneIndicator the new standalone indicator
	 */
	public void setStandaloneIndicator(Boolean standaloneIndicator) {
		this.standaloneIndicator = standaloneIndicator;
	}

	/**
	 * Gets the companion study associations internal.
	 * 
	 * @return the companion study associations internal
	 */
	@OneToMany(mappedBy = "parentStudy", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	public List<CompanionStudyAssociation> getCompanionStudyAssociationsInternal() {
		return lazyListHelper.getInternalList(CompanionStudyAssociation.class);
	}

	/**
	 * Gets the companion study associations.
	 * 
	 * @return the companion study associations
	 */
	@Transient
	public List<CompanionStudyAssociation> getCompanionStudyAssociations() {
		return lazyListHelper.getLazyList(CompanionStudyAssociation.class);
	}

	/**
	 * Sets the companion study associations internal.
	 * 
	 * @param companionStudyAssociations the new companion study associations internal
	 */
	public void setCompanionStudyAssociationsInternal(
			List<CompanionStudyAssociation> companionStudyAssociations) {
		lazyListHelper.setInternalList(CompanionStudyAssociation.class,
				companionStudyAssociations);
	}

	/**
	 * Adds the companion study association.
	 * 
	 * @param companionStudyAssociation the companion study association
	 */
	public void addCompanionStudyAssociation(
			CompanionStudyAssociation companionStudyAssociation) {
		this.getCompanionStudyAssociations().add(companionStudyAssociation);
		companionStudyAssociation.setParentStudy(this);
	}

	/**
	 * Gets the companion indicator.
	 * 
	 * @return the companion indicator
	 */
	public Boolean getCompanionIndicator() {
		return companionIndicator;
	}

	/**
	 * Sets the companion indicator.
	 * 
	 * @param companionIndicator the new companion indicator
	 */
	public void setCompanionIndicator(Boolean companionIndicator) {
		this.companionIndicator = companionIndicator;
	}

	/**
	 * Gets the parent study associations.
	 * 
	 * @return the parent study associations
	 */
	@OneToMany(mappedBy = "companionStudy")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	public List<CompanionStudyAssociation> getParentStudyAssociations() {
		return parentStudyAssociations;
	}

	/**
	 * Sets the parent study associations.
	 * 
	 * @param parentStudyAssociations the new parent study associations
	 */
	private void setParentStudyAssociations(
			List<CompanionStudyAssociation> parentStudyAssociations) {
		this.parentStudyAssociations = parentStudyAssociations;
	}

	/**
	 * Builds the map for notification.
	 * 
	 * @return the map< object, object>
	 */
	@SuppressWarnings("unused")
	@Transient
	public Map<Object, Object> buildMapForNotification() {

		Map<Object, Object> map = new HashMap<Object, Object>();
		map
				.put(
						NotificationEmailSubstitutionVariablesEnum.COORDINATING_CENTER_STUDY_STATUS
								.toString(), getCoordinatingCenterStudyStatus()
								.getDisplayName() == null ? "status"
								: getCoordinatingCenterStudyStatus()
										.getDisplayName());
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_ID.toString(),
				getId() == null ? "id" : getId().toString());
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE.toString(), 
				getShortTitleText() == null ? "Short Title": getShortTitleText().toString());
		
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_CURRENT_ACCRUAL.toString(),
				getCurrentAccrualCount() == null ? "current accrual" : getCurrentAccrualCount().toString());
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD.toString(),
				getTargetAccrualNumber() == null ? "target accrual" : getTargetAccrualNumber().toString());
		
		return map;
	}

	/*
	 * Study utility method to return the current accruals for the study
	 */
	/**
	 * Gets the current accrual count.
	 * 
	 * @return the current accrual count
	 */
	@Transient
	public Integer getCurrentAccrualCount() {
		int currentAccrual = 0 ;
		for(StudySite studySite : this.getStudySites() ){
			currentAccrual = currentAccrual + studySite.getCurrentAccrualCount();
		}
		return currentAccrual ;
	}

	// @Transient
	// public boolean isCreatable() {
	// return this.evaluateDataEntryStatus() == StudyDataEntryStatus.COMPLETE;
	// }

	/**
	 * Checks if is co ordinating center.
	 * 
	 * @param nciCode the nci code
	 * 
	 * @return true, if is co ordinating center
	 */
	@Transient
	public boolean isCoOrdinatingCenter(String nciCode) {
		return this.getStudyCoordinatingCenters().get(0).getHealthcareSite()
				.getNciInstituteCode().equals(nciCode);
	}

	/**
	 * Checks if is multisite.
	 * 
	 * @return true, if is multisite
	 */
	@Transient
	public boolean isMultisite() {
		return this.getMultiInstitutionIndicator() && !this.companionIndicator;
	}

	/**
	 * Gets the affiliate study sites.
	 * 
	 * @return the affiliate study sites
	 */
	@Transient
	public List<StudyOrganization> getAffiliateStudySites() {
		List<StudyOrganization> studyOrganizations = new ArrayList<StudyOrganization>();
		for (StudySite studySite : this.getStudySites()) {
			if (!studySite.getHealthcareSite().getNciInstituteCode()
					.equalsIgnoreCase(
							this.getStudyCoordinatingCenter()
									.getHealthcareSite().getNciInstituteCode())) {
				studyOrganizations.add(studySite);
			}
		}
		return studyOrganizations;
	}

	/**
	 * Gets the study coordinating center.
	 * 
	 * @return the study coordinating center
	 */
	@Transient
	public StudyOrganization getStudyCoordinatingCenter() {
		return this.getStudyCoordinatingCenters().get(0);
	}

	/**
	 * Gets the study site.
	 * 
	 * @param nciCode the nci code
	 * 
	 * @return the study site
	 */
	@Transient
	public StudySite getStudySite(String nciCode) {
		for (StudySite studySite : this.getStudySites()) {
			if (studySite.getHealthcareSite().getNciInstituteCode()
					.equalsIgnoreCase(nciCode)) {
				return studySite;
			}
		}
		throw this.c3PRExceptionHelper
				.getRuntimeException(
						getCode("C3PR.EXCEPTION.STUDY.STUDYSITE_NOT_FOUND_INVALID_NCICODE.CODE"),
						new String[] { nciCode });
	}

        /**
         * Gets the study organization.
         * 
         * @param nciCode the nci code
         * 
         * @return the study organization
         */
        @Transient
        public StudyOrganization getStudyOrganization(String nciCode) {
                for (StudyOrganization studyOrganization : this.getStudyOrganizations()) {
                        if (studyOrganization.getHealthcareSite().getNciInstituteCode()
                                        .equalsIgnoreCase(nciCode)) {
                                return studyOrganization;
                        }
                }
                throw this.c3PRExceptionHelper
                                .getRuntimeException(
                                                getCode("C3PR.EXCEPTION.STUDY.STUDYSITE_NOT_FOUND_INVALID_NCICODE.CODE"),
                                                new String[] { nciCode });
        }

	/**
	 * Gets the companion indicator display value.
	 * 
	 * @return the companion indicator display value
	 */
	@Transient
	public String getCompanionIndicatorDisplayValue() {
		if (getCompanionIndicator()) {
			return "Yes";
		} else {
			return "No";
		}

	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.InteroperableAbstractMutableDeletableDomainObject#getEndpoints()
	 */
	@OneToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_id")
	public List<EndPoint> getEndpoints() {
		return endpoints;
	}

	/**
	 * Gets the possible status transitions.
	 * 
	 * @return the possible status transitions
	 */
	@Transient
	public List<CoordinatingCenterStudyStatus> getPossibleStatusTransitions() {
		List<CoordinatingCenterStudyStatus> statuses = new ArrayList<CoordinatingCenterStudyStatus>();
		if (this.coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.PENDING) {
			if (this.companionIndicator) {
				statuses.add(CoordinatingCenterStudyStatus.READY_TO_OPEN);
				boolean flag = true;
				flag = isParentStudyOpen(flag);
				if (flag)
					statuses.add(CoordinatingCenterStudyStatus.OPEN);
			} else {
				statuses.add(CoordinatingCenterStudyStatus.OPEN);
			}
			return statuses;
		}
		if (this.coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.READY_TO_OPEN) {
			if (this.companionIndicator && !this.standaloneIndicator) {
				boolean flag = true;
				flag = isParentStudyOpen(flag);
				if (flag)
					statuses.add(CoordinatingCenterStudyStatus.OPEN);
			} else {
				statuses.add(CoordinatingCenterStudyStatus.OPEN);
			}
			return statuses;
		}
		if (this.coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.OPEN) {
			statuses.add(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
			statuses
					.add(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
			statuses
					.add(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
			statuses
					.add(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
			return statuses;
		}
		if (this.coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.AMENDMENT_PENDING) {
			statuses.add(CoordinatingCenterStudyStatus.OPEN);
			return statuses;
		}
		if (this.coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL
				|| this.coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			statuses.add(CoordinatingCenterStudyStatus.OPEN);
			statuses.add(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
			statuses
					.add(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
			return statuses;
		}
		return statuses;
	}

	/**
	 * Checks if is parent study open.
	 * 
	 * @param flag the flag
	 * 
	 * @return true, if is parent study open
	 */
	private boolean isParentStudyOpen(boolean flag) {
		for (CompanionStudyAssociation association : this.parentStudyAssociations) {
			if (association.getParentStudy().getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.OPEN) {
				flag = false;
			}
		}
		return flag;
	}
	
	/**
	 * Gets the companion study site.
	 * 
	 * @param nciCode the nci code
	 * 
	 * @return the companion study site
	 */
	@Transient
	public StudySite getCompanionStudySite(String nciCode) {
		if(this.getCompanionIndicator()){
			for(CompanionStudyAssociation parentStudyAssociation : this.getParentStudyAssociations()){
				for(StudySite studySite : parentStudyAssociation.getStudySites()){
					if(StringUtils.equals(nciCode, studySite.getHealthcareSite().getNciInstituteCode())){
						return studySite ;
					}
				}
			}
		}else{
			for(CompanionStudyAssociation companionStudyAssociation : this.getCompanionStudyAssociations()){
				for(StudySite studySite : companionStudyAssociation.getStudySites()){
					if(StringUtils.equals(nciCode, studySite.getHealthcareSite().getNciInstituteCode())){
						return studySite ;
					}
				}
			}
		}
		return null;
	}
	
	
	/**
	 * Gets the custom field definitions internal.
	 * 
	 * @return the custom field definitions internal
	 */
	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<CustomFieldDefinition> getCustomFieldDefinitionsInternal() {
		return lazyListHelper.getInternalList(CustomFieldDefinition.class);
	}

	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.domain.customfield.CustomFieldAuthorable#getCustomFieldDefinitions()
	 */
	@Transient
	public List<CustomFieldDefinition> getCustomFieldDefinitions() {
		return lazyListHelper.getLazyList(CustomFieldDefinition.class);
	}

	/**
	 * Sets the custom field definitions internal.
	 * 
	 * @param customFieldDefinitions the new custom field definitions internal
	 */
	public void setCustomFieldDefinitionsInternal(List<CustomFieldDefinition> customFieldDefinitions) {
		lazyListHelper.setInternalList(CustomFieldDefinition.class,customFieldDefinitions);
	}

	/**
	 * Adds the custom field definition.
	 * 
	 * @param customFieldDefinition the custom field definition
	 */
	public void addCustomFieldDefinition(CustomFieldDefinition customFieldDefinition) {
		this.getCustomFieldDefinitions().add(customFieldDefinition);
		customFieldDefinition.setStudy(this);
	}
	
	/**
	 * Gets the custom fields internal.
	 * 
	 * @return the custom fields internal
	 */
	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
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
		customField.setStudy(this);
	}
	
	/**
	 * Gets the parent study association.
	 * 
	 * @param parentStudyId the parent study id
	 * 
	 * @return the parent study association
	 */
	@Transient
	public CompanionStudyAssociation getParentStudyAssociation(int parentStudyId){
		for(CompanionStudyAssociation companionStudyAssociation : this.getParentStudyAssociations()){
			if(companionStudyAssociation.getParentStudy().getId() == parentStudyId){
				return companionStudyAssociation;
			}
		}
		return null;
	}
	
	/**
	 * Gets the latest consent version.
	 * 
	 * @return the latest consent version
	 */
	@Transient
	public String getLatestConsentVersion(){
		String latestConsentVersion=this.consentVersion;
			for(StudyAmendment studyAmendment: this.getStudyAmendments()){
				if(studyAmendment.getConsentChangedIndicator())
					latestConsentVersion=studyAmendment.getConsentVersion();
			}
		return latestConsentVersion;
	}
	

	/**
	 * Gets the consents internal.
	 * 
	 * @return the consents internal
	 * 
	 */
	
	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy ("id")
	public List<Consent> getConsentsInternal() {
		return lazyListHelper.getInternalList(Consent.class);
	}

	/**
	 * Gets the consents.
	 * 
	 * @return the consents
	 */
	@Transient
	public List<Consent> getConsents() {
		return lazyListHelper.getLazyList(Consent.class);
	}

	/**
	 * Sets the consents internal.
	 * 
	 * @param consents the new consents internal
	 */
	public void setConsentsInternal(List<Consent> consents) {
		lazyListHelper.setInternalList(Consent.class,consents);
	}
	
	
	/**
	 * Adds the consent.
	 * 
	 * @param consent the consent
	 */
	public void addConsent(Consent consent) {
		this.getConsents().add(consent);
		consent.setStudy(this);
	}
	
	/**
	 * Sets the consents.
	 * 
	 * @param consents the new consents
	 */
	public void setConsents(List<Consent> consents) {
		setConsentsInternal(consents);
	}

	/**
	 * Sets the consent validity period.
	 * 
	 * @param consentValidityPeriod the new consent validity period
	 */
	public void setConsentValidityPeriod(Integer consentValidityPeriod) {
		this.consentValidityPeriod = consentValidityPeriod;
	}

	/**
	 * Gets the consent validity period.
	 * 
	 * @return the consent validity period
	 */
	public Integer getConsentValidityPeriod() {
		return consentValidityPeriod;
	}
	
}
