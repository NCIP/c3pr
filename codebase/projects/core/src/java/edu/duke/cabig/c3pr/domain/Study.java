/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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

import com.semanticbits.coppa.domain.annotations.RemoteProperty;

import edu.duke.cabig.c3pr.constants.AmendmentType;
import edu.duke.cabig.c3pr.constants.ConsentRequired;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.NCIRecognizedProgramName;
import edu.duke.cabig.c3pr.constants.NotificationEmailSubstitutionVariablesEnum;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RandomizationType;
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.constants.StudyCategory;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.constants.StudySponsorType;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldAuthorable;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldDefinition;
import edu.duke.cabig.c3pr.domain.customfield.Customizable;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedInstantiateFactory;
import edu.duke.cabig.c3pr.domain.factory.StudySiteBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.C3PRInvalidDataEntryException;
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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDIES_ID_SEQ") })
public abstract class Study extends InteroperableAbstractMutableDeletableDomainObject
		implements Comparable<Study> , Customizable, CustomFieldAuthorable, IdentifiableObject, CCTSBroadcastEnabledDomainObject {

	private Boolean blindedIndicator;
	private Boolean multiInstitutionIndicator;
	private Boolean randomizedIndicator;
	private Boolean companionIndicator;
	private Boolean stratificationIndicator;
	private String phaseCode;
	private String type;
	private ConsentRequired consentRequired;
    private StudyVersion studyVersion;
	
	// This is for the CADSR exclusion/inclusion criteria file
	/** The criteria file. */
	private byte[] criteriaFile;
	private CoordinatingCenterStudyStatus coordinatingCenterStudyStatus;
	private List<StudyOrganization> studyOrganizations;
	private List<Identifier> identifiers;

	private LazyListHelper lazyListHelper;
	private C3PRExceptionHelper c3PRExceptionHelper;
	private MessageSource c3prErrorMessages;

	@Transient
	private int accrualCount;
	private Boolean standaloneIndicator;
	private Integer targetAccrualNumber;
	
	private Boolean therapeuticIntentIndicator;
	
	private String targetRegistrationSystem;
	
	private StudyCategory category;
	private StudySponsorType sponsorType;
	private NCIRecognizedProgramName nciRecognizedProgramName;
	

	@Enumerated(EnumType.STRING)
	public NCIRecognizedProgramName getNciRecognizedProgramName() {
		return nciRecognizedProgramName;
	}

	public void setNciRecognizedProgramName(
			NCIRecognizedProgramName nciRecognizedProgramName) {
		this.nciRecognizedProgramName = nciRecognizedProgramName;
	}

	private Boolean investigatorInitiated;

	public Boolean getInvestigatorInitiated() {
		return investigatorInitiated;
	}

	public void setInvestigatorInitiated(Boolean investigatorInitiated) {
		this.investigatorInitiated = investigatorInitiated;
	}

	@Enumerated(EnumType.STRING)
	public StudyCategory getCategory() {
		return category;
	}

	public void setCategory(StudyCategory category) {
		this.category = category;
	}

	@Enumerated(EnumType.STRING)
	public StudySponsorType getSponsorType() {
		return sponsorType;
	}

	public void setSponsorType(StudySponsorType sponsorType) {
		this.sponsorType = sponsorType;
	}

	/** The parent study associations. */
	private List<CompanionStudyAssociation> parentStudyAssociations = new ArrayList<CompanionStudyAssociation>();
	
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
		standaloneIndicator = true;
		companionIndicator = false;
		consentRequired = ConsentRequired.AS_MARKED_BELOW;
		therapeuticIntentIndicator = false;

		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(StudySite.class,new StudySiteBiDirectionalInstantiateFactory(StudySite.class, this, "Study", Study.class));
		lazyListHelper.add(StudyFundingSponsor.class,new ParameterizedBiDirectionalInstantiateFactory<StudyFundingSponsor>(StudyFundingSponsor.class, this, "Study", Study.class));
		lazyListHelper.add(StudyCoordinatingCenter.class,new ParameterizedBiDirectionalInstantiateFactory<StudyCoordinatingCenter>(StudyCoordinatingCenter.class, this, "Study", Study.class));
		lazyListHelper.add(SystemAssignedIdentifier.class, new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(SystemAssignedIdentifier.class));
		lazyListHelper.add(OrganizationAssignedIdentifier.class,new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(OrganizationAssignedIdentifier.class));
		lazyListHelper.add(PlannedNotification.class,new InstantiateFactory<PlannedNotification>(PlannedNotification.class));
		lazyListHelper.add(PermissibleStudySubjectRegistryStatus.class,new InstantiateFactory<PermissibleStudySubjectRegistryStatus>(PermissibleStudySubjectRegistryStatus.class));

		setStudyOrganizations(new ArrayList<StudyOrganization>());
		setIdentifiers(new ArrayList<Identifier>());
		coordinatingCenterStudyStatus = CoordinatingCenterStudyStatus.PENDING;
		lazyListHelper.add(CustomFieldDefinition.class,new ParameterizedBiDirectionalInstantiateFactory<CustomFieldDefinition>(CustomFieldDefinition.class, this));
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));
		lazyListHelper.add(StudyVersion.class,new ParameterizedBiDirectionalInstantiateFactory<StudyVersion>(StudyVersion.class, this, "Study", Study.class));
	}

	/**
	 * Instantiates a new study.
	 *
	 * @param forSearchByExample the for search by example
	 */
	public Study(boolean forSearchByExample) {
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(StudySite.class, new StudySiteBiDirectionalInstantiateFactory(StudySite.class, this, "Study", Study.class));
		lazyListHelper.add(StudyFundingSponsor.class,new ParameterizedBiDirectionalInstantiateFactory<StudyFundingSponsor>(StudyFundingSponsor.class, this, "Study", Study.class));
		lazyListHelper.add(StudyCoordinatingCenter.class, new ParameterizedBiDirectionalInstantiateFactory<StudyCoordinatingCenter>(StudyCoordinatingCenter.class, this, "Study", Study.class));
		lazyListHelper.add(SystemAssignedIdentifier.class,new ParameterizedInstantiateFactory<SystemAssignedIdentifier>( SystemAssignedIdentifier.class));
		lazyListHelper.add(OrganizationAssignedIdentifier.class,new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(OrganizationAssignedIdentifier.class));
		lazyListHelper.add(PlannedNotification.class,new InstantiateFactory<PlannedNotification>(PlannedNotification.class));
		lazyListHelper.add(PermissibleStudySubjectRegistryStatus.class,new InstantiateFactory<PermissibleStudySubjectRegistryStatus>(PermissibleStudySubjectRegistryStatus.class));

		setStudyOrganizations(new ArrayList<StudyOrganization>());
		setIdentifiers(new ArrayList<Identifier>());
		if (!forSearchByExample) {
			blindedIndicator = false;
			multiInstitutionIndicator = false;
			stratificationIndicator = true;
			consentRequired = ConsentRequired.ONE;
		}
		lazyListHelper.add(CustomFieldDefinition.class,new ParameterizedBiDirectionalInstantiateFactory<CustomFieldDefinition>(CustomFieldDefinition.class, this));
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));
		lazyListHelper.add(StudyVersion.class,new ParameterizedBiDirectionalInstantiateFactory<StudyVersion>(StudyVersion.class, this, "Study", Study.class));
		addStudyVersion(new StudyVersion(true));
	}

	public List<Error> canOpen() {
		List<Error> errors = new ArrayList<Error>();
		evaluateDataEntryStatus(errors);
		return errors;
	}

	@Transient
	public List<Identifier> getLocalIdentifiers() {
		List<Identifier> localIdentifiers = new ArrayList<Identifier>();
		for (Identifier identifier : getIdentifiers()) {
			if (!(OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER.getName()).equals(identifier.getTypeInternal())
					&& !(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER.getName()).equals(identifier.getTypeInternal())) {
				localIdentifiers.add(identifier);
			}
		}
		return localIdentifiers;
	}
	
	@Transient
	public List<StudySite> getAccruingStudySites(){
		List<StudySite> accruingStudySites = new ArrayList<StudySite>();
		for(StudySite studySite:this.getStudySites()){
			if((studySite.getStudySiteStudyVersion(new Date())!= null)){
				if(studySite.getStudySiteStudyVersion(new Date()).getIrbApprovalDate() !=null){
					accruingStudySites.add(studySite);
				}
			}
		}
		return accruingStudySites;
	}
	
	@Transient
	public List<StudySite> getStudySites() {
		return lazyListHelper.getLazyList(StudySite.class);
	}

	@Transient
	public List<StudyFundingSponsor> getStudyFundingSponsors() {
		return lazyListHelper.getLazyList(StudyFundingSponsor.class);
	}

	@Transient
	public List<StudyCoordinatingCenter> getStudyCoordinatingCenters() {
		return lazyListHelper.getLazyList(StudyCoordinatingCenter.class);
	}

	@OneToMany(mappedBy = "studyInternal", fetch = FetchType.LAZY, orphanRemoval = true)
	@Cascade(value = { CascadeType.ALL})
	@Where(clause = "comp_assoc_id  is null")
	@OrderBy
	public List<StudyOrganization> getStudyOrganizations() {
		return studyOrganizations;
	}

	public void setStudyOrganizations(List<StudyOrganization> studyOrganizations) {
		this.studyOrganizations = studyOrganizations;
		// initialize projected list for StudySite, StudyFundingSponsor and StudyCoordinatingCenter
		lazyListHelper.setInternalList(StudySite.class, new ProjectedList<StudySite>(this.studyOrganizations,StudySite.class));
		lazyListHelper.setInternalList(StudyFundingSponsor.class,new ProjectedList<StudyFundingSponsor>(this.studyOrganizations,StudyFundingSponsor.class));
		lazyListHelper.setInternalList(StudyCoordinatingCenter.class,new ProjectedList<StudyCoordinatingCenter>(this.studyOrganizations,StudyCoordinatingCenter.class));
	}

	public void addStudyOrganization(StudyOrganization studyOrganization) {
		this.getStudyOrganizations().add(studyOrganization);
		studyOrganization.setStudy(this);
	}
	
	public void addStudyOrganizations(List<StudyOrganization> studyOrganizations) {
		for(StudyOrganization studyOrg : studyOrganizations){
			if(studyOrg instanceof StudySite){
				this.addStudySite((StudySite)studyOrg);
			}else{
				this.addStudyOrganization(studyOrg);
			}
		}
	}

	public void removeStudyOrganization(StudyOrganization studyOrganization) {
		this.getStudyOrganizations().remove(studyOrganization);
	}

	public void addEpoch(Epoch epoch) throws RuntimeException {
		getStudyVersion().addEpoch(epoch);
	}

	public void removeStudyDisease(StudyDisease studyDisease) {
		this.getStudyDiseases().remove(studyDisease);
	}

	public void removeAllStudyDisease() {
		this.getStudyDiseases().removeAll(this.getStudyDiseases());
	}

	public void addStudySite(StudySite studySite) {
		studySite.setup(this);
		lazyListHelper.getLazyList(StudySite.class).add(studySite);
		for(CompanionStudyAssociation compStudyAssoc : getCompanionStudyAssociations()){
			Study compStudy = compStudyAssoc.getCompanionStudy();
			if(compStudy.getIsEmbeddedCompanionStudy()){
				StudySite stuSite = new StudySite();
				stuSite.setHealthcareSite(studySite.getHealthcareSite());
				compStudy.addStudySite(stuSite);
			}
		}
	}

	public void removeStudySite(StudySite studySite) {
		lazyListHelper.getLazyList(StudySite.class).remove(studySite);
	}

	public void addStudyDisease(StudyDisease studyDisease) {
		getStudyDiseases().add(studyDisease);
	}

	public void addIdentifier(Identifier identifier) {
		getIdentifiers().add(identifier);
	}

	@Transient
	public OrganizationAssignedIdentifier getFundingSponsorAssignedIdentifier() {
		for (OrganizationAssignedIdentifier orgIdentifier : this.getOrganizationAssignedIdentifiers()) {
			if ((orgIdentifier.getType() != null)
					&& (orgIdentifier.getType().equals(OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER)))
				return orgIdentifier;
		}
		return null;
	}

	@Transient
	public OrganizationAssignedIdentifier getCoordinatingCenterAssignedIdentifier() {
		for (OrganizationAssignedIdentifier orgIdentifier : this.getOrganizationAssignedIdentifiers()) {
			if ((orgIdentifier.getType() != null)
					&& (orgIdentifier.getType().equals(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER)))
				return orgIdentifier;
		}
		return null;
	}

	@Transient
	public String getPrincipalInvestigatorFullName() {
		HealthcareSiteInvestigator studyInv = getPrincipalInvestigator();
		if(studyInv != null){
			return studyInv.getInvestigator().getFullName();
		}
		return null ;
	}

	@Transient
	public HealthcareSiteInvestigator getPrincipalInvestigator() {
		StudyInvestigator studyInv = getPrincipalStudyInvestigator();
		if(studyInv != null){
			return studyInv.getHealthcareSiteInvestigator();
		}
		return null ;
	}

	@Transient
	public StudyInvestigator getPrincipalStudyInvestigator() {
		for (StudyOrganization studyOrganization : this.getStudyOrganizations()) {
			for (StudyInvestigator studyInvestigator : studyOrganization.getStudyInvestigators()) {
				if (StringUtils.equals(studyInvestigator.getRoleCode(), StudyInvestigator.PRINCIPAL_INVESTIGATOR)) {
					return studyInvestigator;
				}
			}
		}
		return null;
	}

	@Transient
	public StudyOrganization getPrincipalInvestigatorStudyOrganization() {
		StudyInvestigator studyInvestigator = getPrincipalStudyInvestigator();
		if(studyInvestigator != null){
			return studyInvestigator.getStudyOrganization();
		}
		return null;
	}

	@OneToMany(orphanRemoval=true)
	@Cascade( { CascadeType.ALL})
	@JoinColumn(name = "STU_ID")
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
		lazyListHelper.setInternalList(SystemAssignedIdentifier.class,new ProjectedList<SystemAssignedIdentifier>(this.identifiers, SystemAssignedIdentifier.class));
		lazyListHelper.setInternalList(OrganizationAssignedIdentifier.class,new ProjectedList<OrganizationAssignedIdentifier>(this.identifiers,OrganizationAssignedIdentifier.class));
	}

	@Transient
	public List<SystemAssignedIdentifier> getSystemAssignedIdentifiers() {
		return lazyListHelper.getLazyList(SystemAssignedIdentifier.class);
	}

	@Transient
	public List<OrganizationAssignedIdentifier> getOrganizationAssignedIdentifiers() {
		return lazyListHelper.getLazyList(OrganizationAssignedIdentifier.class);
	}

	@Transient
	public List<Epoch> getEpochs() {
		return getStudyVersion().getEpochs();
	}

	public void setEpochs(List<Epoch> epochs) {
		getStudyVersion().setEpochs(epochs);
	}

	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true)
	@JoinColumn(name = "stu_id", nullable = true)
	@Cascade(value = { CascadeType.ALL})
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy("id")
	public List<PlannedNotification> getPlannedNotificationsInternal() {
		return lazyListHelper.getInternalList(PlannedNotification.class);
	}

	public void setPlannedNotificationsInternal(
			final List<PlannedNotification> plannedNotifications) {
		lazyListHelper.setInternalList(PlannedNotification.class,
				plannedNotifications);
	}

	@Transient
	public List<PlannedNotification> getPlannedNotifications() {
		return lazyListHelper.getLazyList(PlannedNotification.class);
	}

	@Transient
	public List<StudyDisease> getStudyDiseases(){
		return getStudyVersion().getStudyDiseases();
	}

	@Transient
	public String getDescriptionText() {
		return getStudyVersion().getDescriptionText();
	}

	@Transient
	public String getLongTitleText() {
		return getStudyVersion().getLongTitleText();
	}

	@RemoteProperty
	public String getPhaseCode() {
		return phaseCode;
	}

	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}

	@Transient
	public String getPrecisText() {
		return getStudyVersion().getPrecisText();
	}

	@Transient
	public String getShortTitleText() {
		return getStudyVersion().getShortTitleText();
	}

	@RemoteProperty
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int compareTo(Study o) {
		if (this.equals(o)){
			return 0;
		}
		return 1;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((getCoordinatingCenterAssignedIdentifier() == null) ? 0 : getCoordinatingCenterAssignedIdentifier().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		final Study other = (Study) obj;
		if ((this.getCoordinatingCenterAssignedIdentifier() == null) || (other.getCoordinatingCenterAssignedIdentifier() == null)) {
			return false;
		} else if (!((this.getCoordinatingCenterAssignedIdentifier().getValue()).equalsIgnoreCase(other.getCoordinatingCenterAssignedIdentifier().getValue()))) {
			return false;
		} else if (!(this.getCoordinatingCenterAssignedIdentifier().equals(other.getCoordinatingCenterAssignedIdentifier()))) {
			return false;
		}
		return true;
	}

	@Transient
	public String getTrimmedShortTitleText() {
		return StringUtils.getTrimmedText(StringUtils.removeCarriageReturnCharacters(getShortTitleText()), 40);
	}
	
	@Transient
	public String getPrimaryIdentifier() {
		for (Identifier identifier : getIdentifiers()) {
			if (identifier.getPrimaryIndicator().booleanValue() == true) {
				return identifier.getValue();
			}
		}
		return null;
	}
	
	@Transient
	public Identifier getPrimaryIdentifierObject() {
		for (Identifier identifier : getIdentifiers()) {
			if (identifier.getPrimaryIndicator().booleanValue() == true) {
				return identifier;
			}
		}
		return null;
	}

	public Boolean getBlindedIndicator() {
		return blindedIndicator;
	}

	public void setBlindedIndicator(Boolean blindedIndicator) {
		this.blindedIndicator = blindedIndicator;
	}

	public Boolean getTherapeuticIntentIndicator() {
		return therapeuticIntentIndicator;
	}

	public void setTherapeuticIntentIndicator(Boolean therapeuticIntentIndicator) {
		this.therapeuticIntentIndicator = therapeuticIntentIndicator;
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

	@Transient
	public RandomizationType getRandomizationType() {
		return getStudyVersion().getRandomizationType();
	}

	@Transient
	public List<Consent> getConsents() {
		return getStudyVersion().getConsents();
	}
	
	@Transient
	public Consent getConsent(String name, String versionId) {
		for(Consent consent : getConsents()){
			if(consent.getName().equalsIgnoreCase(name)){ 
				if(StringUtils.isBlank(consent.getVersionId())){
					if(StringUtils.isBlank(versionId)){
						return consent;
					}
				}else{
					if(!StringUtils.isBlank(versionId) && consent.getVersionId().equalsIgnoreCase(versionId)){
						return consent;
					}
				}
			}
		}
		return null;
	}

	public void addConsent(Consent consent) {
		getStudyVersion().addConsent(consent);
	}

	@Transient
	public int getFundingSponsorIdentifierIndex() {
		int i = -1 ;
		for(OrganizationAssignedIdentifier identifier : this.getOrganizationAssignedIdentifiers()){
			i++ ;
			if(identifier.getType() != null && identifier.getType().equals(OrganizationIdentifierTypeEnum.PROTOCOL_AUTHORITY_IDENTIFIER)){
				return i ;
			}
		}

		return -1;
	}

	@Transient
	public boolean getHasRegisteredParticipants() {
		for(StudySite studySite : getStudySites()){
			if (studySite.getStudySubjects().size() > 0) {
				return true;
			}
		}
		return false;
	}

	@Transient
	public StudyDataEntryStatus getDataEntryStatus() {
		return getStudyVersion().getDataEntryStatus();
	}

//	@Transient
//	public CoordinatingCenterStudyStatus getCoordinatingCenterStudyStatus() {
//		return this.getCoordinatingCenterStudyStatusInternal();
//	}
//
//	public void setCoordinatingCenterStudyStatus(
//		CoordinatingCenterStudyStatus coordinatingCenterStudyStatus) {
//        this.setCoordinatingCenterStudyStatusInternal(coordinatingCenterStudyStatus);
//	}

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	public CoordinatingCenterStudyStatus getCoordinatingCenterStudyStatus() {
		return coordinatingCenterStudyStatus;
	}

	public void setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus coordinatingCenterStudyStatus) {
		this.coordinatingCenterStudyStatus=coordinatingCenterStudyStatus;
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
			return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(criteriaFile)));
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
		return getStudyVersion().getEpochByName(name);
	}

	public void updateDataEntryStatus() {
		List<Error> errors = new ArrayList<Error>();
		setDataEntryStatus(evaluateDataEntryStatus(errors));
	}

	public CoordinatingCenterStudyStatus evaluateCoordinatingCenterStudyStatus() throws C3PRCodedException {
		List<Error> errors = new ArrayList<Error>();
		if (evaluateDataEntryStatus(errors) != StudyDataEntryStatus.COMPLETE) {
			return CoordinatingCenterStudyStatus.PENDING;
		}

		if (this.getCompanionIndicator() && !this.standaloneIndicator) {
			return CoordinatingCenterStudyStatus.READY_TO_OPEN;
		} else {
			return CoordinatingCenterStudyStatus.OPEN;
		}

	}

	public StudyDataEntryStatus evaluateDataEntryStatus(List<Error> errors) {
		return getStudyVersion().evaluateDataEntryStatus(errors);
	}

	public void open() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING) {
			this.readyToOpen();
		}
		if (!(coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.READY_TO_OPEN
				|| coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL
                || coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
			throw getC3PRExceptionHelper().getRuntimeException( getCode("C3PR.EXCEPTION.STUDY.STATUS_CANNOT_SET_TO_ACTIVE.CODE"),
							            new String[] { this.getCoordinatingCenterStudyStatus() .getDisplayName() });
		}

		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE"),
					new String[] { this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}

		if (this.companionIndicator && !this.standaloneIndicator) {
			for (int i = 0; i < this.parentStudyAssociations.size(); i++) {
				if (this.parentStudyAssociations.get(i).getParentStudy().getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.OPEN) {
					throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS.COMPANION_STUDY_OPEN.CODE"));
				}
			}
		}
		
		// Updating study site start date to study open date. This is to fix issue when study site is added while study is in pending status and current date becomes study site start date while study open date is something different.
        // TODO Test for companion study also.
		if(coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.READY_TO_OPEN){
			for(StudySite studySite: this.getStudySites()){
				if(studySite.getStudySiteStudyVersions().size() > 0){
					for(StudySiteStudyVersion studySiteStudyVersion : studySite.getStudySiteStudyVersions()){
						studySiteStudyVersion.setStartDate(this.getVersionDate());
					}
				}
			}
		}

        if (this.getStudyVersion().getCompanionStudyAssociations().size() > 0) {
			for (CompanionStudyAssociation compStudyAssoc : this.getStudyVersion().getCompanionStudyAssociations()) {
				Study localCompanionStudy = compStudyAssoc.getCompanionStudy();
				// We support only open stand alone companions, so no need to open stand alone studies.
				if(!localCompanionStudy.getStandaloneIndicator()){
					if (localCompanionStudy.getDataEntryStatus() == StudyDataEntryStatus.COMPLETE) {
						localCompanionStudy.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
						localCompanionStudy.getStudyVersion().setVersionStatus(StatusType.AC);
					} else if (compStudyAssoc.getMandatoryIndicator()) {
						throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS.COMPANION_STUDY.CODE"));
					}
				}
			}
		}
        
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        this.getStudyVersion().setVersionStatus(StatusType.AC);
	}
	
	public void readyToOpen() throws C3PRInvalidDataEntryException {

		if (!(this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT )) {
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_CANNOT_SET_TO_ACTIVE.CODE"),
							new String[] { this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}

		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE"),
					new String[] { this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}

		List<Error> errors = new ArrayList<Error>();

		errors = canOpen();
		if (errors.size() > 0) {
			throw new C3PRInvalidDataEntryException(" Study is invalid because data entry is not complete", errors);
		}
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.READY_TO_OPEN);
	}

	public void closeToAccrual() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE"),
					new String[] { this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}

		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING){
			throw getC3PRExceptionHelper()
					.getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
							new String[] {this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
	}

	public void closeToAccrualAndTreatment() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			throw getC3PRExceptionHelper()
			.getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE"),
					new String[] { this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}

		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
							new String[] { this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
	}

	public void temporarilyCloseToAccrualAndTreatment() {
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING) {
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
							new String[] {this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE"),
					new String[] { this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
	}

	public void temporarilyCloseToAccrual() {

		if (((this.getCoordinatingCenterStudyStatus()) == (CoordinatingCenterStudyStatus.PENDING))) {
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
							new String[] {this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		if (this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT
				|| this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STATUS_ALREADY_CLOSED.CODE"),
					new String[] { this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		this.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
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
		return Integer.parseInt(this.c3prErrorMessages.getMessage(
				errortypeString, null, null));
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
	public int getAccrualCount() {
		return this.accrualCount;
	}

	public void setAccrualCount(int accrualCount) {
		this.accrualCount = accrualCount;
	}

	public Boolean getStandaloneIndicator() {
		return standaloneIndicator;
	}

	public void setStandaloneIndicator(Boolean standaloneIndicator) {
		this.standaloneIndicator = standaloneIndicator;
	}

	public Boolean getCompanionIndicator() {
		return companionIndicator;
	}

	public void setCompanionIndicator(Boolean companionIndicator) {
		this.companionIndicator = companionIndicator;
	}

	@OneToMany(mappedBy = "companionStudy")
	@Cascade(value = { CascadeType.LOCK})
	@Where(clause = "retired_indicator  = 'false'")
	public List<CompanionStudyAssociation> getParentStudyAssociations() {
		return parentStudyAssociations;
	}

	@SuppressWarnings("unused")
	private void setParentStudyAssociations(List<CompanionStudyAssociation> parentStudyAssociations) {
		this.parentStudyAssociations = parentStudyAssociations;
	}

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
	@Transient
	public Integer getCurrentAccrualCount() {
		int currentAccrual = 0 ;
		for(StudySite studySite : this.getStudySites() ){
			currentAccrual = currentAccrual + studySite.getCurrentAccrualCount();
		}
		return currentAccrual ;
	}

	@Transient
	public boolean isCoOrdinatingCenter(String nciCode) {
		return this.getStudyCoordinatingCenters().get(0).getHealthcareSite()
				.getPrimaryIdentifier().equals(nciCode);
	}

	@Transient
	public boolean isMultisite() {
		return this.getMultiInstitutionIndicator() && !this.companionIndicator;
	}

	@Transient
	public List<StudyOrganization> getAffiliateStudySites() {
		List<StudyOrganization> studyOrganizations = new ArrayList<StudyOrganization>();
		for (StudySite studySite : this.getStudySites()) {
			if (!studySite.getHealthcareSite().getPrimaryIdentifier()
					.equalsIgnoreCase(
							this.getStudyCoordinatingCenter()
									.getHealthcareSite().getPrimaryIdentifier())) {
				studyOrganizations.add(studySite);
			}
		}
		return studyOrganizations;
	}

	@Transient
	public StudyOrganization getStudyCoordinatingCenter() {
		return this.getStudyCoordinatingCenters().get(0);
	}
	
	@Transient
	public StudySite getStudySite(String primaryIdentifier) {
		for (StudySite studySite : this.getStudySites()) {
			if (studySite.getHealthcareSite().getPrimaryIdentifier()
					.equalsIgnoreCase(primaryIdentifier)) {
				return studySite;
			}
		}
		return null;
	}

        @Transient
        public StudyOrganization getStudyOrganization(String nciCode) {
                for (StudyOrganization studyOrganization : this.getStudyOrganizations()) {
                    if (studyOrganization.getHealthcareSite().getNCICode()
                                        .equalsIgnoreCase(nciCode)) {
                                return studyOrganization;
                        }
                }
                throw this.c3PRExceptionHelper
                                .getRuntimeException(
                                                getCode("C3PR.EXCEPTION.STUDY.STUDYSITE_NOT_FOUND_INVALID_NCICODE.CODE"),
                                                new String[] { nciCode });
        }

	@Transient
	public String getCompanionIndicatorDisplayValue() {
		if (getCompanionIndicator()) {
			return "Yes";
		} else {
			return "No";
		}

	}

	@OneToMany(orphanRemoval=true)
	@Cascade(value = { CascadeType.ALL})
	@JoinColumn(name = "stu_id")
	public List<EndPoint> getEndpoints() {
		return endpoints;
	}

	@Transient
	public List<CoordinatingCenterStudyStatus> getPossibleStatusTransitions() {
		List<CoordinatingCenterStudyStatus> statuses = new ArrayList<CoordinatingCenterStudyStatus>();
		if (this.coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.PENDING) {
			if (this.companionIndicator && !this.standaloneIndicator) {
				boolean flag = true;
				flag = isParentStudyOpen(flag);
				if (flag){
					statuses.add(CoordinatingCenterStudyStatus.OPEN);
				}else{
					statuses.add(CoordinatingCenterStudyStatus.READY_TO_OPEN);
				}
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
		if (this.coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL
				|| this.coordinatingCenterStudyStatus == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT) {
			statuses.add(CoordinatingCenterStudyStatus.OPEN);
			statuses.add(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL);
			statuses.add(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
			return statuses;
		}
		return statuses;
	}

	private boolean isParentStudyOpen(boolean flag) {
		for (CompanionStudyAssociation association : this.parentStudyAssociations) {
			if (association.getParentStudy().getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.OPEN) {
				flag = false;
			}
		}
		return flag;
	}
	
	@Transient
	public boolean getIsParentStudyOpen() {
		return isParentStudyOpen(true);
	}

	@Transient
	public StudySite getCompanionStudySite(String nciCode) {
		if(this.getCompanionIndicator()){
			for(CompanionStudyAssociation parentStudyAssociation : this.getParentStudyAssociations()){
				for(StudySite studySite : parentStudyAssociation.getStudySites()){
					if(StringUtils.equals(nciCode, studySite.getHealthcareSite().getCtepCode())){
						return studySite ;
					}
				}
			}
		}else{
			for(CompanionStudyAssociation companionStudyAssociation : this.getStudyVersion().getCompanionStudyAssociations()){
				for(StudySite studySite : companionStudyAssociation.getStudySites()){
					if(StringUtils.equals(nciCode, studySite.getHealthcareSite().getCtepCode())){
						return studySite ;
					}
				}
			}
		}
		return null;
	}


	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY, orphanRemoval=true)
	@Cascade(value = { CascadeType.ALL})
	public List<CustomFieldDefinition> getCustomFieldDefinitionsInternal() {
		return lazyListHelper.getInternalList(CustomFieldDefinition.class);
	}

	@Transient
	public List<CustomFieldDefinition> getCustomFieldDefinitions() {
		return lazyListHelper.getLazyList(CustomFieldDefinition.class);
	}

	public void setCustomFieldDefinitionsInternal(List<CustomFieldDefinition> customFieldDefinitions) {
		lazyListHelper.setInternalList(CustomFieldDefinition.class,customFieldDefinitions);
	}

	public void addCustomFieldDefinition(CustomFieldDefinition customFieldDefinition) {
		this.getCustomFieldDefinitions().add(customFieldDefinition);
		customFieldDefinition.setStudy(this);
	}

	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY, orphanRemoval=true)
	@Cascade(value = { CascadeType.ALL})
	public List<CustomField> getCustomFieldsInternal() {
		return lazyListHelper.getInternalList(CustomField.class);
	}

	@Transient
	public List<CustomField> getCustomFields() {
		return lazyListHelper.getLazyList(CustomField.class);
	}

	public void setCustomFieldsInternal(List<CustomField> customFields) {
		lazyListHelper.setInternalList(CustomField.class,customFields);
	}

	public void addCustomField(CustomField customField) {
		this.getCustomFields().add(customField);
		customField.setStudy(this);
	}

	@Transient
	public CompanionStudyAssociation getParentStudyAssociation(int parentStudyVersionId){
		for(CompanionStudyAssociation companionStudyAssociation : this.getParentStudyAssociations()){
			if(companionStudyAssociation.getParentStudyVersion().getId() == parentStudyVersionId){
				return companionStudyAssociation;
			}
		}
		return null;
	}

	@Transient
	public List<StudySubject> getAllStudySubjects(){
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		for(StudySite studySite: this.getStudySites()){
			studySubjects.addAll(studySite.getStudySubjects());
		}
		return studySubjects;
	}

	@Transient
	public List<ICD9DiseaseSite> getDiseaseSites(){
		List<ICD9DiseaseSite> diseaseSites = new ArrayList<ICD9DiseaseSite>();
		for(StudySubject studySubject: this.getAllStudySubjects()){
			if(studySubject.getDiseaseHistory()!=null && studySubject.getDiseaseHistory().getIcd9DiseaseSite()!=null){
				diseaseSites.add(studySubject.getDiseaseHistory().getIcd9DiseaseSite());
			}
		}
		return diseaseSites;

	}

	@OneToMany(mappedBy = "study", fetch = FetchType.LAZY, orphanRemoval=true)
	@Cascade(value = { CascadeType.ALL})
	@Where(clause = "retired_indicator  = 'false'")
	@OrderBy ("versionDate")
	public List<StudyVersion> getStudyVersionsInternal() {
		return lazyListHelper.getInternalList(StudyVersion.class);
	}

	@Transient
	public List<StudyVersion> getStudyVersions() {
		return lazyListHelper.getLazyList(StudyVersion.class);
	}

	public void setStudyVersionsInternal(List<StudyVersion> studyVersions) {
		lazyListHelper.setInternalList(StudyVersion.class,studyVersions);
	}
	
	public void addStudyVersion(StudyVersion studyVersion) {
		this.lazyListHelper.getInternalList(StudyVersion.class).add(studyVersion);
		studyVersion.setStudy(this);
	}

	public void setStudyVersions(List<StudyVersion> studyVersions) {
		setStudyVersionsInternal(studyVersions);
	}

	@Transient
	public StudyVersion getLatestStudyVersion(){
        List<StudyVersion> studyVersions = this.getSortedStudyVersions();
        int size = studyVersions.size();
        if( size == 0){
        	StudyVersion localStudyVersion= getStudyVersions().get(0);
        	localStudyVersion.setOriginalIndicator(true);
        	localStudyVersion.setVersionDate(new Date());
        	localStudyVersion.setAmendmentType(AmendmentType.IMMEDIATE);
        	localStudyVersion.setName("Original version");
			return localStudyVersion;
        }else{
			return  studyVersions.get(size - 1 );
		}
	}

	// this method is required for study site study version default association.
	@Transient
	public StudyVersion getLatestActiveStudyVersion(){
	   for(StudyVersion studyVersion : getReverseSortedStudyVersions()){
		   if(studyVersion.getVersionStatus() == StatusType.AC){
			   return studyVersion ;
		   }
	   }
	   return null;
	}

	@OneToMany(orphanRemoval=true)
	@Cascade(value = { CascadeType.ALL})
	@JoinColumn(name = "study_id", nullable=false)
	@Where(clause = "retired_indicator  = 'false'")
	public List<PermissibleStudySubjectRegistryStatus> getPermissibleStudySubjectRegistryStatusesInternal() {
		return lazyListHelper.getInternalList(PermissibleStudySubjectRegistryStatus.class);
	}

	@Transient
	public List<PermissibleStudySubjectRegistryStatus> getPermissibleStudySubjectRegistryStatuses() {
		return lazyListHelper.getLazyList(PermissibleStudySubjectRegistryStatus.class);
	}
	
	@Transient
	public PermissibleStudySubjectRegistryStatus getPermissibleStudySubjectRegistryStatus(String statusCode) {
		for(PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus : getPermissibleStudySubjectRegistryStatuses()){
			if(permissibleStudySubjectRegistryStatus.getRegistryStatus().getCode().equalsIgnoreCase(statusCode)){
				return permissibleStudySubjectRegistryStatus;
			}
		}
		return null;
	}

	public void setPermissibleStudySubjectRegistryStatusesInternal(List<PermissibleStudySubjectRegistryStatus> permissibleStudySubjectRegistryStatuses) {
		lazyListHelper.setInternalList(PermissibleStudySubjectRegistryStatus.class,permissibleStudySubjectRegistryStatuses);
	}
	
	public void setShortTitleText(String shortTitleText){
		this.getStudyVersion().setShortTitleText(shortTitleText);
	}

	public void setLongTitleText(String longTitleText){
		this.getStudyVersion().setLongTitleText(longTitleText);
	}

	public void setDescriptionText(String descriptionText){
		this.getStudyVersion().setDescriptionText(descriptionText);
	}

	public void setPrecisText(String precisText){
		this.getStudyVersion().setPrecisText(precisText);
	}

	public void setDataEntryStatus(StudyDataEntryStatus dataEntryStatus){
		this.getStudyVersion().setDataEntryStatus(dataEntryStatus);
	}

	public void setRandomizationType(RandomizationType randomizationType){
		this.getStudyVersion().setRandomizationType(randomizationType);
	}

	public void addCompanionStudyAssociation(CompanionStudyAssociation companionStudyAssociation) {
		getStudyVersion().addCompanionStudyAssociation(companionStudyAssociation);
	}

	@Transient
	public List<CompanionStudyAssociation> getCompanionStudyAssociations() {
		return getStudyVersion().getCompanionStudyAssociations();
	}

	public void setStudyDiseases(List<StudyDisease> studyDiseases) {
		getStudyVersion().setStudyDiseases(studyDiseases);
	}

	@Transient
	public String getVersionName() {
		return getStudyVersion().getName();
	}

	public void setVersionName(String name) {
		getStudyVersion().setName(name);
	}
	
	@Transient
	public String getVersionDateStr() {
		return getStudyVersion().getVersionDateStr();
	}
	
	@Transient
	public Date getVersionDate() {
		return getStudyVersion().getVersionDate();
	}

    @Transient
	public StudyVersion getStudyVersion() {
		if(studyVersion == null){
            studyVersion= getLatestStudyVersion();
        }
		return studyVersion;
	}
    
    @Transient
	public StudyVersion getStudyVersion(String name) {
    	for(StudyVersion studyVersion : getStudyVersions()){
			if(StringUtils.equals(name, studyVersion.getName())){
				return studyVersion;
			}
		}
		return null;
	}

	public void setStudyVersion(StudyVersion studyVersion) {
		this.studyVersion = studyVersion;

	}

    @Transient
    public StudyVersion getCurrentStudyAmendment(){
    	List<StudyVersion> amendmentVersions = new ArrayList<StudyVersion>();
    	for(StudyVersion version : getStudyVersions()){
    		if(version.getVersionStatus() == StatusType.IN){
    			amendmentVersions.add(version);
    		}
    	}
    	if(amendmentVersions.size() > 1){
    		throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDY_IS_CORRUPTED_MULTIPLE_INCOMPLETE_AMENDMENT"),
					new String[] {this.getCoordinatingCenterStudyStatus().getDisplayName() });
    	}else if(amendmentVersions.size() == 1){
    		return amendmentVersions.get(0);
    	}
        return null ;
    }

	@Transient
	public List<StudyVersion> getStudyAmendments() {
		int size = this.getStudyVersions().size();
		if (size > 1) {
			return this.getStudyVersions().subList(1, size);
		}
		return null;
	}

	@Transient
	public List<StudyVersion> getSortedStudyVersions() {
		List<StudyVersion> studyVersions = new ArrayList<StudyVersion>();
		studyVersions.addAll(this.getStudyVersions());
		Collections.sort(studyVersions);
		return studyVersions;
	}

	@Transient
	public List<StudyVersion> getReverseSortedStudyVersions() {
		List<StudyVersion> studyVersions = this.getSortedStudyVersions();
		Collections.reverse(studyVersions);
		return studyVersions;
	}
	
	public void setTargetAccrualNumber(Integer targetAccrualNumber) {
		this.targetAccrualNumber = targetAccrualNumber;
	}

	public Integer getTargetAccrualNumber() {
		return targetAccrualNumber;
	}

	@Enumerated(EnumType.STRING)
	public ConsentRequired getConsentRequired() {
		return consentRequired;
	}

	public void setConsentRequired(ConsentRequired consentRequired) {
		this.consentRequired = consentRequired;
	}

	public void applyAmendment() {
		if(this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL ||
				this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT ||
				this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING ){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDY_NOT_AMENDABLE.CODE"),
					new String[] {this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		if(this.getStudyVersion().getVersionStatus() == StatusType.AC){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDY_NO_EXISTING_AMENDMENT.CODE"),
					new String[] {this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		List<Error> errors = new ArrayList<Error>();
		evaluateDataEntryStatus(errors) ;
		if (errors.size() > 0) {
			throw new C3PRInvalidDataEntryException("C3PR.EXCEPTION.STUDY.STUDY.AMENDMENT.DATAENTRY.INCOMPLETE.CODE", errors);
		}
		this.getStudyVersion().setVersionStatus(StatusType.AC);
	}

	public void applyAmendment(StudyVersion studyVersion){
		if(this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL ||
				this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT ||
				this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING ){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDY_NOT_AMENDABLE.CODE"),
					new String[] {this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		List<Error> errors = new ArrayList<Error>();
		studyVersion.evaluateDataEntryStatus(errors) ;
		if (errors.size() > 0) {
			throw new C3PRInvalidDataEntryException("C3PR.EXCEPTION.STUDY.STUDY.AMENDMENT.DATAENTRY.INCOMPLETE.CODE", errors);
		}
		studyVersion.setVersionStatus(StatusType.AC);
		this.addStudyVersion(studyVersion);
	}

	public void createAmendment() {
		if(this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL ||
				this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT ||
				this.getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.PENDING ){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDY_NOT_AMENDABLE.CODE"),
					new String[] {this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		if(this.getStudyVersion().getVersionStatus() != StatusType.AC){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDY_EXISTING_AMENDMENT.CODE"),
					new String[] {this.getCoordinatingCenterStudyStatus().getDisplayName() });
		}
		try {
			this.addStudyVersion((StudyVersion) getLatestStudyVersion().clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the study version that's applicable for a date.
	 * This can be used to fetch the correct amendment that should be
	 * used for a given date.
	 *
	 * @param date the date
	 *
	 * @return the study version
	 */
	@Transient
	public StudyVersion getStudyVersion(Date date) {
		for(StudyVersion studyVersion : getReverseSortedStudyVersions()){
			if(studyVersion.getVersionDate().before(date)){
				return studyVersion;
			}
		}
		return null;
	}
	
	@Transient
	public Boolean getIfHigherOrderEpochExists(Epoch epoch){
		Integer order = epoch.getEpochOrder();
		for(Epoch epochInStudy : getEpochs() ){
			 if(epochInStudy.getEpochOrder()> order){
				 return true;
			 }
		}
		return false;
	}
	
	@Transient
	public Boolean getOriginalIndicator() {
		 return getStudyVersion().getOriginalIndicator();
	}

	public void setOriginalIndicator(Boolean originalIndicator) {
		this.getStudyVersion().setOriginalIndicator(originalIndicator);
	}
	
	@Transient
	public Boolean getIsEmbeddedCompanionStudy(){
		return companionIndicator && !standaloneIndicator ;
	}
	
	@Transient
	public Study getParentStudy(){
		// this method is applicable for embedded studies
		if(getParentStudyAssociations().size() > 0){
			return getParentStudyAssociations().get(0).getParentStudy();
		}
		return null ;
	}
	
	public boolean isAssignedAndActivePersonnel(PersonUser researchStaff){
		List<StudyPersonnel> studyPersonnelList = new ArrayList<StudyPersonnel>();
		for(StudyOrganization studyOrganization : getStudyOrganizations()){
			studyPersonnelList.addAll(studyOrganization.getActiveStudyPersonnel());
		}
		for(StudyPersonnel studyPersonnel : studyPersonnelList){
			if(studyPersonnel.getPersonUser().equals(researchStaff)){
				return true;
			}
		}
		return false;
	}
	
	@Transient
	public boolean getHasStratifiedEpoch(){
		return this.getStudyVersion().hasStratifiedEpoch();
	}
	
	@Transient
	public boolean getHasRandomizedEpoch(){
		return this.getStudyVersion().hasRandomizedEpoch();
	}

	/**
	 * @return the targetRegistrationSystem
	 */
	public String getTargetRegistrationSystem() {
		return targetRegistrationSystem;
	}

	/**
	 * @param targetRegistrationSystem the targetRegistrationSystem to set
	 */
	public void setTargetRegistrationSystem(String targetRegistrationSystem) {
		this.targetRegistrationSystem = targetRegistrationSystem;
	}
	
	
}
