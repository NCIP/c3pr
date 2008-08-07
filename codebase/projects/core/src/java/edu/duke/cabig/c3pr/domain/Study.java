package edu.duke.cabig.c3pr.domain;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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

import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

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
public class Study extends CCTSAbstractMutableDeletableDomainObject implements
		Comparable<Study> {

	private Boolean blindedIndicator;

	private Boolean multiInstitutionIndicator;

	private Boolean randomizedIndicator;
	
	private Boolean companionIndicator;
	
	private Boolean stratificationIndicator;

	private String shortTitleText;

	private String longTitleText;

	private String descriptionText;

	private String precisText;

	private String phaseCode;

	// private String status;

	private String type;

	private String primaryIdentifier;

	// This is for the bookRandomizationEntried File
	private String file;

	// This is for the CADSR exclusion/inclusion criteria file
	private byte[] criteriaFile;

	private Integer targetAccrualNumber;

	private RandomizationType randomizationType;

	private StudyDataEntryStatus dataEntryStatus;

	private CoordinatingCenterStudyStatus coordinatingCenterStudyStatus;

	private List<StudyDisease> studyDiseases = new ArrayList<StudyDisease>();

	private List<StudyOrganization> studyOrganizations;

	private List<Identifier> identifiers;
	
	// TODO move into Command Object
	private String[] diseaseTermIds;

	private String diseaseCategoryAsText;

	private String consentVersion;

	private LazyListHelper lazyListHelper;
	
	private C3PRExceptionHelper c3PRExceptionHelper;
	
	private MessageSource c3prErrorMessages;

    @Transient
    private int acrrualsWithinLastWeek;  
    
    private Boolean standaloneIndicator;
    
    private List<CompanionStudyAssociation> parentStudyAssociations ;
    
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
		stratificationIndicator = true;
		dataEntryStatus = StudyDataEntryStatus.INCOMPLETE;
		standaloneIndicator = true ;
		companionIndicator = false ;

		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(StudySite.class,
				new ParameterizedBiDirectionalInstantiateFactory<StudySite>(
						StudySite.class, this));
		lazyListHelper
				.add(
						StudyFundingSponsor.class,
						new ParameterizedBiDirectionalInstantiateFactory<StudyFundingSponsor>(
								StudyFundingSponsor.class, this));
		lazyListHelper
				.add(
						StudyCoordinatingCenter.class,
						new ParameterizedBiDirectionalInstantiateFactory<StudyCoordinatingCenter>(
								StudyCoordinatingCenter.class, this));
		lazyListHelper.add(SystemAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(
						SystemAssignedIdentifier.class));
		lazyListHelper
				.add(
						OrganizationAssignedIdentifier.class,
						new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(
								OrganizationAssignedIdentifier.class));
		lazyListHelper.add(StudyAmendment.class,
				new InstantiateFactory<StudyAmendment>(StudyAmendment.class));
		lazyListHelper.add(PlannedNotification.class,
				new InstantiateFactory<PlannedNotification>(PlannedNotification.class));
		lazyListHelper
		.add(
				Epoch.class,
				new ParameterizedBiDirectionalInstantiateFactory<Epoch>(
						Epoch.class, this));
		// mandatory, so that the lazy-projected list is managed properly.
		setStudyOrganizations(new ArrayList<StudyOrganization>());
		setIdentifiers(new ArrayList<Identifier>());
		lazyListHelper
		.add(
				CompanionStudyAssociation.class,
				new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(
						CompanionStudyAssociation.class, this, "ParentStudy"));

	}

	public Study(boolean forSearchByExample) {

		lazyListHelper = new LazyListHelper();
		lazyListHelper
		.add(
				CompanionStudyAssociation.class,
				new ParameterizedBiDirectionalInstantiateFactory<CompanionStudyAssociation>(
						CompanionStudyAssociation.class, this, "ParentStudy"));

		lazyListHelper.add(StudySite.class,
				new ParameterizedBiDirectionalInstantiateFactory<StudySite>(
						StudySite.class, this));
		lazyListHelper
				.add(
						StudyFundingSponsor.class,
						new ParameterizedBiDirectionalInstantiateFactory<StudyFundingSponsor>(
								StudyFundingSponsor.class, this));
		lazyListHelper
				.add(
						StudyCoordinatingCenter.class,
						new ParameterizedBiDirectionalInstantiateFactory<StudyCoordinatingCenter>(
								StudyCoordinatingCenter.class, this));
		lazyListHelper
				.add(
				Epoch.class,
				new ParameterizedBiDirectionalInstantiateFactory<Epoch>(
						Epoch.class, this));
		lazyListHelper.add(SystemAssignedIdentifier.class,
				new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(
						SystemAssignedIdentifier.class));
		lazyListHelper
				.add(
						OrganizationAssignedIdentifier.class,
						new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(
								OrganizationAssignedIdentifier.class));
		lazyListHelper.add(StudyAmendment.class,
				new InstantiateFactory<StudyAmendment>(StudyAmendment.class));
		lazyListHelper.add(PlannedNotification.class,
				new InstantiateFactory<PlannedNotification>(PlannedNotification.class));
		// mandatory, so that the lazy-projected list is managed properly.
		setStudyOrganizations(new ArrayList<StudyOrganization>());
		setIdentifiers(new ArrayList<Identifier>());
		if (!forSearchByExample) {
			blindedIndicator = false;
			multiInstitutionIndicator = false;
			stratificationIndicator = true;
		}

	}

	@Transient
	public List<Identifier> getLocalIdentifiers() {
		List<Identifier> localIdentifiers = new ArrayList<Identifier>();
		for (Identifier identifier : getIdentifiers()) {
			if ("Protocol Authority Identifier".equals(identifier.getType())
					|| "Coordinating Center Identifier".equals(identifier
							.getType())) {
				// nothing
			} else {
				localIdentifiers.add(identifier);
			}
		}
		return localIdentifiers;
	}

	@Transient
	public List<StudySite> getStudySites() {
		return lazyListHelper.getLazyList(StudySite.class);
	}

	public void setStudySites(List<StudySite> studySites) {
		// do nothing
	}

	@Transient
	public List<StudyFundingSponsor> getStudyFundingSponsors() {
		return lazyListHelper.getLazyList(StudyFundingSponsor.class);
	}

	public void setStudyFundingSponsors(
			List<StudyFundingSponsor> studyFundingSponsors) {
		// do nothing
	}

	@Transient
	public List<StudyCoordinatingCenter> getStudyCoordinatingCenters() {
		return lazyListHelper.getLazyList(StudyCoordinatingCenter.class);
	}

	public void setStudyCoordinatingCenters(
			List<StudyCoordinatingCenter> studyCoordinatingCenters) {
		// do nothing
	}

	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@OrderBy
	public List<StudyOrganization> getStudyOrganizations() {
		return studyOrganizations;
	}

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

	public void addStudyOrganization(StudyOrganization so) {
		this.getStudyOrganizations().add(so);
		so.setStudy(this);
	}

	public void removeStudyOrganization(StudyOrganization so) {
		this.getStudyOrganizations().remove(so);
	}

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

	public void removeEpoch(Epoch epoch) {
		lazyListHelper.getLazyList(Epoch.class).remove(epoch);
	}

	public void addStudySite(StudySite studySite) {
		studySite.setStudy(this);
		lazyListHelper.getLazyList(StudySite.class).add(studySite);
	}

	public void removeStudySite(StudySite studySite) {
		lazyListHelper.getLazyList(StudySite.class).remove(studySite);
	}

	public void addStudyDisease(StudyDisease studyDisease) {
		studyDisease.setStudy(this);
		studyDiseases.add(studyDisease);
	}

	public void addIdentifier(Identifier identifier) {
		getIdentifiers().add(identifier);
	}

	public void removeIdentifier(Identifier identifier) {
		getIdentifiers().remove(identifier);
	}

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

	@Transient
	public String getPrincipalInvestigatorFullName() {
		for (StudyOrganization studyOrganization : this.getStudyOrganizations()) {
			for (StudyInvestigator studyInvestigator : studyOrganization
					.getStudyInvestigators()) {
				if (studyInvestigator.getRoleCode().equals(
						"Principal Investigator")) {
					return studyInvestigator.getHealthcareSiteInvestigator()
							.getInvestigator().getFullName();
				}
			}
		}
		return null;
	}
	
	@Transient
	public HealthcareSiteInvestigator getPrincipalInvestigator() {
		for (StudyOrganization studyOrganization : this.getStudyOrganizations()) {
			for (StudyInvestigator studyInvestigator : studyOrganization
					.getStudyInvestigators()) {
				if (studyInvestigator.getRoleCode().equals(
						"Principal Investigator")) {
					return studyInvestigator.getHealthcareSiteInvestigator();
				}
			}
		}
		return null;
	}
	

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

	// / BEAN PROPERTIES

	// TODO: this stuff should really, really not be in here. It's
	// web-view/entry specific.

	@Transient
	public String[] getDiseaseTermIds() {
		return diseaseTermIds;
	}

	@Transient
	public String getDiseaseCategoryAsText() {
		return diseaseCategoryAsText;
	}

	@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "STU_ID")
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

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

	@Transient
	public List<SystemAssignedIdentifier> getSystemAssignedIdentifiers() {
		return lazyListHelper.getLazyList(SystemAssignedIdentifier.class);
	}

	public void setSystemAssignedIdentifiers(
			List<SystemAssignedIdentifier> systemAssignedIdentifiers) {
		// do nothing
	}

	@Transient
	public List<OrganizationAssignedIdentifier> getOrganizationAssignedIdentifiers() {
		return lazyListHelper.getLazyList(OrganizationAssignedIdentifier.class);
	}

	public void setOrganizationAssignedIdentifiers(
			List<OrganizationAssignedIdentifier> organizationAssignedIdentifiers) {
		// do nothing
	}

	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy("epochOrder")
	public List<Epoch> getEpochsInternal() {
		return lazyListHelper.getInternalList(Epoch.class);
	}
	
	@Transient
	public List<Epoch> getEpochs() {
		return lazyListHelper.getLazyList(Epoch.class);
	}

	public void setEpochs(List<Epoch> epochs) {
		setEpochsInternal(epochs);
	}

	public void addAmendment(final StudyAmendment amendment) {
		getStudyAmendments().add(amendment);
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "stu_id", nullable = false)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudyAmendment> getStudyAmendmentsInternal() {
		return lazyListHelper.getInternalList(StudyAmendment.class);
	}

	public void setStudyAmendmentsInternal(final List<StudyAmendment> amendments) {
		lazyListHelper.setInternalList(StudyAmendment.class, amendments);
	}

	@Transient
	public List<StudyAmendment> getStudyAmendments() {
		return lazyListHelper.getLazyList(StudyAmendment.class);
	}

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

	@Transient
	public StudyAmendment getCurrentStudyAmendment() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING) {
			return this.getStudyAmendments().get(
					getStudyAmendments().size() - 1);
		} else
			return null;
	}

	public void setStudyAmendments(final List<StudyAmendment> amendments) {
		setStudyAmendmentsInternal(amendments);
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "stu_id", nullable = true)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy("id")
	public List<PlannedNotification> getPlannedNotificationsInternal() {
		return lazyListHelper.getInternalList(PlannedNotification.class);
	}

	public void setPlannedNotificationsInternal(final List<PlannedNotification> plannedNotifications) {
		lazyListHelper.setInternalList(PlannedNotification.class, plannedNotifications);
	}

	@Transient
	public List<PlannedNotification> getPlannedNotifications() {
		return lazyListHelper.getLazyList(PlannedNotification.class);
	}

	public void setPlannedNotifications(final List<PlannedNotification> plannedNotifications) {
	}

	public void setEpochsInternal(final List<Epoch> epochs) {
		lazyListHelper.setInternalList(Epoch.class, epochs);
	}

	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudyDisease> getStudyDiseases() {
		return studyDiseases;
	}

	public void setStudyDiseases(List<StudyDisease> studyDiseases) {
		this.studyDiseases = studyDiseases;
	}

	public void setDiseaseTermIds(String[] diseaseTermIds) {
		this.diseaseTermIds = diseaseTermIds;
	}

	public void setDiseaseCategoryAsText(String diseaseCategoryAsText) {
		this.diseaseCategoryAsText = diseaseCategoryAsText;
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

	public String getPhaseCode() {
		return phaseCode;
	}

	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}

	public String getPrecisText() {
		return precisText;
	}

	public void setPrecisText(String precisText) {
		this.precisText = precisText;
	}

	public String getShortTitleText() {
		return shortTitleText;
	}

	public void setShortTitleText(String shortTitleText) {
		this.shortTitleText = shortTitleText;
	}

	public Integer getTargetAccrualNumber() {
		return targetAccrualNumber;
	}

	public void setTargetAccrualNumber(Integer targetAccrualNumber) {
		this.targetAccrualNumber = targetAccrualNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int compareTo(Study o) {
		if (this.equals(o))
			return 0;

		return 1;
	}

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

	@Transient
	public String getTrimmedShortTitleText() {
		return StringUtils.getTrimmedText(shortTitleText, 40);
	}

	@Transient
	public String getPrimaryIdentifier() {
		for (Identifier identifier : getIdentifiers()) {
			if (identifier.getPrimaryIndicator().booleanValue() == true) {
				return identifier.getValue();
			}
		}

		return primaryIdentifier;
	}

	public Boolean getBlindedIndicator() {
		return blindedIndicator;
	}

	public void setBlindedIndicator(Boolean blindedIndicator) {
		this.blindedIndicator = blindedIndicator;
	}

	public Boolean getRandomizedIndicator() {
		return randomizedIndicator;
	}

	public void setRandomizedIndicator(Boolean randomizedIndicator) {
		this.randomizedIndicator = randomizedIndicator;
	}

	public Boolean getMultiInstitutionIndicator() {
		return multiInstitutionIndicator;
	}

	public void setMultiInstitutionIndicator(Boolean multiInstitutionIndicator) {
		this.multiInstitutionIndicator = multiInstitutionIndicator;
	}

	public RandomizationType getRandomizationType() {
		return randomizationType;
	}

	public void setRandomizationType(RandomizationType randomizationType) {
		this.randomizationType = randomizationType;
	}

	@Transient
	public String getFile() {

		if (file != null) {
			return file;
		}
		return null;
	}

	public void setFile(String file) {
		this.file = file;
	}

	@Transient
	public int getFundingSponsorIdentifierIndex() {

		if (this.getOrganizationAssignedIdentifiers().size() > 0) {
			{
				for (int index = 0; index < this
						.getOrganizationAssignedIdentifiers().size(); index++) {
					if ((this.getOrganizationAssignedIdentifiers().get(index)
							.getType() != null)
							&& (this.getOrganizationAssignedIdentifiers().get(
									index).getType()
									.equalsIgnoreCase("Protocol Authority Identifier"))) {
						return index;
					}
				}
			}
		}
		return -1;
	}

	@Transient
	public boolean hasElligibility() {

		for(Epoch epoch:this.getEpochs()){
			if(epoch.hasEligibility())
					return true;
			}
		return false;
	}

	@Transient
	public boolean hasRandomizedEpoch() {
		for(Epoch epoch:this.getEpochs()){
			if(epoch.getRandomizedIndicator())
					return true;
			}
		return false;
	}
	
	@Transient
	public boolean hasStratifiedEpoch() {
		for(Epoch epoch:this.getEpochs()){
			if(epoch.getStratificationIndicator())
					return true;
			}
		return false;
	}

	@Transient
	public boolean hasEnrollingEpoch() {
		for(Epoch epoch:this.getEpochs()){
			if(epoch.getEnrollmentIndicator())
				return true;
	}
		return false;
	}

	@Transient
	public boolean getHasRegisteredParticipants() {

		if (getStudySites() != null && getStudySites().size() > 0) {
			Iterator iterSite = getStudySites().iterator();
			StudySite studySite;
			List<StudySubject> studySubjects;
			while (iterSite.hasNext()) {
				studySite = (StudySite) iterSite.next();
				studySubjects = studySite.getStudySubjects();
				if (studySubjects.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	@Enumerated(EnumType.STRING)
	public StudyDataEntryStatus getDataEntryStatus() {
		return dataEntryStatus;
	}

	public void setDataEntryStatus(StudyDataEntryStatus dataEntryStatus) {
		this.dataEntryStatus = dataEntryStatus;
	}

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	public CoordinatingCenterStudyStatus getCoordinatingCenterStudyStatus() {
		return coordinatingCenterStudyStatus;
	}

	public void setCoordinatingCenterStudyStatus(
			CoordinatingCenterStudyStatus coordinatingCenterStudyStatus) {
		this.coordinatingCenterStudyStatus = coordinatingCenterStudyStatus;
	}

	public String getConsentVersion() {
		return consentVersion;
	}

	public void setConsentVersion(String consentVersion) {
		this.consentVersion = consentVersion;
	}

	@Transient
	public byte[] getCriteriaFile() {
		return criteriaFile;
	}

	public void setCriteriaFile(byte[] criteriaFile) {
		this.criteriaFile = criteriaFile;
	}

	@Transient
	public Reader getCriteriaReader() {
		if (criteriaFile != null) {
			return new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(criteriaFile)));
		}
		return null;
	}

	@Transient
	public InputStream getCriteriaInputStream() {
		if (criteriaFile != null) {
			return new ByteArrayInputStream(criteriaFile);
		}
		return null;
	}

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

	public void setDataEntryStatus(boolean throwException)
			throws C3PRCodedException {

		if (throwException == false) {
			try {
				this.setDataEntryStatus(evaluateDataEntryStatus());
			} catch (Exception e) {
			}
		} else {
			this.setDataEntryStatus(evaluateDataEntryStatus());
		}
	}

	public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus() throws C3PRCodedException {
		if (evaluateDataEntryStatus() != StudyDataEntryStatus.COMPLETE) {
			return CoordinatingCenterStudyStatus.PENDING;
		}
		if (this.getId() != null) {
			if (!evaluateAmendmentStatus()) {
				return CoordinatingCenterStudyStatus.AMENDMENT_PENDING;
			}
		}
		
		if(this.getCompanionIndicator() && !this.standaloneIndicator){
			return CoordinatingCenterStudyStatus.READY_FOR_ACTIVATION;
		}else{
			return CoordinatingCenterStudyStatus.ACTIVE;
		}
		
	}

	public boolean evaluateAmendmentStatus()
			throws C3PRCodedException {
		
		if (this.getStudyAmendments().size() > 0) {
			StudyAmendment latestAmendment = this.getStudyAmendments().get(
					this.getStudyAmendments().size() - 1);

				if ((this.getId() != null && (latestAmendment
						.getAmendmentDate() == null))) {
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

	public StudyDataEntryStatus evaluateDataEntryStatus()
			throws C3PRCodedException {

		if ((this.getStudySites().size() == 0)) {
				throw getC3PRExceptionHelper()
						.getException(
								getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STUDY_SITE.CODE"));
		}
				else if ((!this.hasEnrollingEpoch())) {
				throw getC3PRExceptionHelper()
						.getException(
								getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.ENROLLING_EPOCH.CODE"));
			}
		
    	if(this.getRandomizedIndicator()){
    		 if (!(this.hasRandomizedEpoch())){
    				 throw getC3PRExceptionHelper()
 					.getException(
 							getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.RANDOMIZED_EPOCH_FOR_RANDOMIZED_STUDY.CODE"));
    		 }
    	}
    	if(this.getStratificationIndicator()){
   		  if (!(this.hasStratifiedEpoch())){
   				 throw getC3PRExceptionHelper()
					.getException(
							getCode("C3PR.EXCEPTION.STUDY.DATAENTRY.MISSING.STRATIFIED_EPOCH_FOR_STRATIFIED_STUDY.CODE"));
   		 }
    	}
    	
    	for(CompanionStudyAssociation compStudyAssoc : this.getCompanionStudyAssociations()){
			 if(compStudyAssoc.getMandatoryIndicator() 
					&& !(compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.READY_FOR_ACTIVATION
					|| compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE)){
				throw getC3PRExceptionHelper().getException(getCode("C3PR.EXCEPTION.STUDY.STATUS.COMPANION_STUDY.CODE"));
			}
		}
		if (!evaluateEpochsDataEntryStatus()){
			return StudyDataEntryStatus.INCOMPLETE;
		}

		return StudyDataEntryStatus.COMPLETE;
	}

	public Study setStatuses(boolean throwException)
			throws C3PRCodedException {
		if (!throwException) {
			try {
				this.setDataEntryStatus(evaluateDataEntryStatus());
			} catch (Exception e1) {
				this.setDataEntryStatus(StudyDataEntryStatus.INCOMPLETE);
			}
		} else {
			this.setDataEntryStatus(evaluateDataEntryStatus());
		}

		// For a new study, the coordingating center status should be set to
		// Pending.
		if (this.getId() == null) {
			this
					.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
		} else {
			if (!throwException) {
				try {
					this
							.setCoordinatingCenterStudyStatus(evaluateCoordinatingCenterStudyStatus());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				this
						.setCoordinatingCenterStudyStatus(evaluateCoordinatingCenterStudyStatus());
			}
		}

		if (!throwException) {
			try {
				for (int i = 0; i < this.getStudySites().size(); i++) {
					this.getStudySites().get(i).setSiteStudyStatus(
							this.getStudySites().get(i).evaluateSiteStudyStatus());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			for (int i = 0; i < this.getStudySites().size(); i++) {
				this.getStudySites().get(i).setSiteStudyStatus(
						this.getStudySites().get(i).evaluateSiteStudyStatus());
			}
		}

		return this;
	}

	public Study setStatuses(CoordinatingCenterStudyStatus targetStatus)
			throws C3PRCodedException {

		this.setDataEntryStatus(evaluateDataEntryStatus());
		CoordinatingCenterStudyStatus oldStatus = this
				.getCoordinatingCenterStudyStatus();

		// For a new study, the coordingating center status should be set to
		// Pending.
		if (this.getId() == null) {
			this.setCoordinatingCenterStudyStatus(targetStatus);
			if(targetStatus == CoordinatingCenterStudyStatus.ACTIVE && this.getCompanionStudyAssociations().size() > 0 ){
				for(CompanionStudyAssociation compStudyAssoc : this.getCompanionStudyAssociations()){
					if(compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.READY_FOR_ACTIVATION
							|| compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE){
						compStudyAssoc.getCompanionStudy().setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
					}else if(compStudyAssoc.getMandatoryIndicator() 
							&& !(compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.READY_FOR_ACTIVATION
							|| compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE)){
						throw getC3PRExceptionHelper().getException(getCode("C3PR.EXCEPTION.STUDY.STATUS.COMPANION_STUDY.CODE"));
					}
				}
			}
		} else {
			if (statusSettable(this, targetStatus)) {
				this.setCoordinatingCenterStudyStatus(targetStatus);
				// check to make companion study as active in one shot along with parent study
				if(targetStatus == CoordinatingCenterStudyStatus.ACTIVE && this.getCompanionStudyAssociations().size() > 0 ){
					for(CompanionStudyAssociation compStudyAssoc : this.getCompanionStudyAssociations()){
						if(compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.READY_FOR_ACTIVATION
								|| compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE){
							compStudyAssoc.getCompanionStudy().setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
						}else if(compStudyAssoc.getMandatoryIndicator() 
								&& !(compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.READY_FOR_ACTIVATION
								|| compStudyAssoc.getCompanionStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE)){
							throw getC3PRExceptionHelper().getException(getCode("C3PR.EXCEPTION.STUDY.STATUS.COMPANION_STUDY.CODE"));
						}
					}
				}
			} else {
				this.setCoordinatingCenterStudyStatus(oldStatus);
			}
		}
		return this;
	}

	public boolean statusSettable(Study study,
			CoordinatingCenterStudyStatus newCoordinatingCenterStatus)
			throws C3PRCodedException {

		// For a new study, the coordingating center status should not be
		// settable.
		if (study.getId() == null) {
			return false;
		}

		CoordinatingCenterStudyStatus evaluatedStatus = evaluateCoordinatingCenterStudyStatus();
		CoordinatingCenterStudyStatus currentStatus = study
				.getCoordinatingCenterStudyStatus();
		if (newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.ACTIVE) {

			if (((evaluatedStatus) == (CoordinatingCenterStudyStatus.PENDING))
					|| ((evaluatedStatus) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
					|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL))
					|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
				{
					if ((study.getId() != null)) {
						throw getC3PRExceptionHelper()
								.getException(
										getCode("C3PR.EXCEPTION.STUDY.STATUS_CANNOT_SET_TO_ACTIVE.CODE"),
										new String[] { currentStatus
												.getDisplayName() });
					}
					return false;
				}
			}
		}

		if ((newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL)
				|| (newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
			if (((currentStatus) == (CoordinatingCenterStudyStatus.PENDING))
					|| ((currentStatus) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
					|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL))
					|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
				if ((study.getId() != null)) {
					throw getC3PRExceptionHelper()
							.getException(
									getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
									new String[] { newCoordinatingCenterStatus
											.getDisplayName() });
				}
				return false;
			}
		}

		if ((newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL)
				|| (newCoordinatingCenterStatus == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
			if (((currentStatus) == (CoordinatingCenterStudyStatus.PENDING))
					|| ((currentStatus) == (CoordinatingCenterStudyStatus.AMENDMENT_PENDING))
					|| ((currentStatus) == (CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
				if ((study.getId() != null)) {
					throw getC3PRExceptionHelper()
							.getException(
									getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_OR_TEMPORARILY_CLOSED_FIRST.CODE"),
									new String[] { currentStatus
											.getDisplayName() });
				}
				return false;
			}
		}

		return true;
	}
	

    public void setSiteStudyStatuses() throws C3PRCodedException {
        for (int i = 0; i < this.getStudySites().size(); i++) {
            this.getStudySites().get(i).setSiteStudyStatus(
            		this.getStudySites().get(i).evaluateSiteStudyStatus());
        }
    }
    
    public boolean evaluateEpochsDataEntryStatus()
                    throws C3PRCodedException {
            for (Epoch epoch : this.getEpochs()) {
            	if (!epoch.evaluateStatus())
           return false;
            }
        return true;
    }
    @Transient
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}

	public void setC3PRExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.c3PRExceptionHelper = exceptionHelper;
	}
	@Transient
	 public int getCode(String errortypeString) {
	        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
	    }
	 @Transient
	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	public Boolean getStratificationIndicator() {
		return stratificationIndicator;
	}

	public void setStratificationIndicator(Boolean stratificationIndicator) {
		this.stratificationIndicator = stratificationIndicator;
	}

    @Transient
    public int getAcrrualsWithinLastWeek() {
        return this.acrrualsWithinLastWeek;
    }

    public void setAcrrualsWithinLastWeek(int acrrualsWithinLastWeek) {
        this.acrrualsWithinLastWeek = acrrualsWithinLastWeek;
    }

	public Boolean getStandaloneIndicator() {
		return standaloneIndicator;
	}

	public void setStandaloneIndicator(Boolean standaloneIndicator) {
		this.standaloneIndicator = standaloneIndicator;
	}
	
	@OneToMany(mappedBy = "parentStudy", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	public List<CompanionStudyAssociation> getCompanionStudyAssociationsInternal() {
		return lazyListHelper.getInternalList(CompanionStudyAssociation.class);
	}
	
	@Transient
	public List<CompanionStudyAssociation> getCompanionStudyAssociations() {
		return lazyListHelper.getLazyList(CompanionStudyAssociation.class);
	}

	public void setCompanionStudyAssociationsInternal( List<CompanionStudyAssociation> companionStudyAssociations) {
		lazyListHelper.setInternalList(CompanionStudyAssociation.class, companionStudyAssociations);
	}

	public Boolean getCompanionIndicator() {
		return companionIndicator;
	}

	public void setCompanionIndicator(Boolean companionIndicator) {
		this.companionIndicator = companionIndicator;
	}
	
	@OneToMany(mappedBy = "companionStudy")
	@Cascade(value = { CascadeType.LOCK })
	@Where(clause = "retired_indicator  = 'false'")
	public List<CompanionStudyAssociation> getParentStudyAssociations() {
		return parentStudyAssociations;
	}
	
	private void setParentStudyAssociations(
			List<CompanionStudyAssociation> parentStudyAssociations) {
		this.parentStudyAssociations = parentStudyAssociations;
	}	
	
}
