package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
@GenericGenerator(name = "id-generator", strategy = "native", parameters = {@Parameter(name = "sequence", value = "STUDIES_ID_SEQ")})
public class Study extends CCTSAbstractMutableDeletableDomainObject implements
        Comparable<Study> {

    private Boolean blindedIndicator;

    private Boolean multiInstitutionIndicator;

    private Boolean randomizedIndicator;

    private String shortTitleText;

    private String longTitleText;

    private String descriptionText;

    private String precisText;

    private String phaseCode;

    // private String status;

    private String type;

    private String primaryIdentifier;

    //This is for the bookRandomizationEntried File
    private String file;
    
    //This is for the CADSR exclusion/inclusion criteria file
    private byte[] criteriaFile;

    private Integer targetAccrualNumber;

    private List<Epoch> epochs;

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

    // private ParameterizedInstantiateFactory<Identifier> identifierFactory;

    public Study() {
        blindedIndicator = false;
        multiInstitutionIndicator = false;
        multiInstitutionIndicator = false;
        dataEntryStatus = StudyDataEntryStatus.INCOMPLETE;

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

        // lazyListHelper.add(Epoch.class, epochFactory);
        lazyListHelper
                .add(
                        TreatmentEpoch.class,
                        new ParameterizedBiDirectionalInstantiateFactory<TreatmentEpoch>(
                                TreatmentEpoch.class, this));
        lazyListHelper
                .add(
                        NonTreatmentEpoch.class,
                        new ParameterizedBiDirectionalInstantiateFactory<NonTreatmentEpoch>(
                                NonTreatmentEpoch.class, this));
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
        lazyListHelper.add(Notification.class,
                new InstantiateFactory<Notification>(Notification.class));
        // mandatory, so that the lazy-projected list is managed properly.
        setStudyOrganizations(new ArrayList<StudyOrganization>());
        setEpochs(new ArrayList<Epoch>());
        setIdentifiers(new ArrayList<Identifier>());

    }

    public Study(boolean forSearchByExample) {

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

        // lazyListHelper.add(Epoch.class, epochFactory);
        lazyListHelper
                .add(
                        TreatmentEpoch.class,
                        new ParameterizedBiDirectionalInstantiateFactory<TreatmentEpoch>(
                                TreatmentEpoch.class, this));
        lazyListHelper
                .add(
                        NonTreatmentEpoch.class,
                        new ParameterizedBiDirectionalInstantiateFactory<NonTreatmentEpoch>(
                                NonTreatmentEpoch.class, this));
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
        lazyListHelper.add(Notification.class,
                new InstantiateFactory<Notification>(Notification.class));
        // mandatory, so that the lazy-projected list is managed properly.
        setStudyOrganizations(new ArrayList<StudyOrganization>());
        setEpochs(new ArrayList<Epoch>());
        setIdentifiers(new ArrayList<Identifier>());
        if (!forSearchByExample) {
            blindedIndicator = false;
            multiInstitutionIndicator = false;
            multiInstitutionIndicator = false;
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
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
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
        for (StudyOrganization studyOrganization : this
                .getStudyOrganizations()) {
        	for(StudyInvestigator studyInvestigator : studyOrganization.getStudyInvestigators()){
        		if (studyInvestigator.getRoleCode().equals("Principal Investigator")){
        			return studyInvestigator.getHealthcareSiteInvestigator().getInvestigator().getFullName();
        		}
        	}
        }
        return null;
    }
    
    @Transient
    public StudyOrganization getPrincipalInvestigatorStudyOrganization() {
        for (StudyOrganization studyOrganization : this
                .getStudyOrganizations()) {
        	for(StudyInvestigator studyInvestigator : studyOrganization.getStudyInvestigators()){
        		if (studyInvestigator.getRoleCode().equals("Principal Investigator")){
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
    @Cascade({CascadeType.ALL, CascadeType.DELETE_ORPHAN})
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
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @Where(clause = "retired_indicator  = 'false'")
    @OrderBy("epochOrder")
    public List<Epoch> getEpochs() {
        return epochs;
    }

    public void addAmendment(final StudyAmendment amendment) {
        getStudyAmendments().add(amendment);
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "stu_id", nullable = false)
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
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
    @JoinColumn(name = "stu_id", nullable=false)
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
    @Where(clause = "retired_indicator  = 'false'")
    public List<Notification> getNotificationsInternal() {
        return lazyListHelper.getInternalList(Notification.class);
    }

    public void setNotificationsInternal(final List<Notification> notifications) {
        lazyListHelper.setInternalList(Notification.class, notifications);
    }
    
    @Transient
    public List<Notification> getNotifications() {
        return lazyListHelper.getLazyList(Notification.class);
    }
    
    public void setNotifications(final List<Notification> notifications) {
    }    

    public void setEpochs(List<Epoch> epochs) {
        this.epochs = epochs;
        lazyListHelper.setInternalList(TreatmentEpoch.class,
                new ProjectedList<TreatmentEpoch>(this.epochs,
                        TreatmentEpoch.class));
        lazyListHelper.setInternalList(NonTreatmentEpoch.class,
                new ProjectedList<NonTreatmentEpoch>(this.epochs,
                        NonTreatmentEpoch.class));
    }

    @Transient
    public List<TreatmentEpoch> getTreatmentEpochs() {
        return lazyListHelper.getLazyList(TreatmentEpoch.class);
    }

    public void setTreatmentEpochs(List<TreatmentEpoch> treatmentEpochs) {
        // do nothing
    }

    @Transient
    public List<NonTreatmentEpoch> getNonTreatmentEpochs() {
        return lazyListHelper.getLazyList(NonTreatmentEpoch.class);
    }

    public void setNonTreatmentEpochs(List<NonTreatmentEpoch> nonTreatmentEpochs) {
        // do nothing
    }

    @OneToMany(mappedBy = "study", fetch = FetchType.LAZY)
    @Cascade(value = {CascadeType.ALL, CascadeType.DELETE_ORPHAN})
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

    /*
      * public String getStatus() { return status; }
      *
      * public void setStatus(String status) { this.status = status; }
      */

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
                .equalsIgnoreCase(other.getCoordinatingCenterAssignedIdentifier().getValue()))) {
            return false;
        } else if (!(this.getCoordinatingCenterAssignedIdentifier()
                .equals(other
                .getCoordinatingCenterAssignedIdentifier()))) {
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
    	
		if (file!=null) {
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
                for (int index = 0; index < this.getOrganizationAssignedIdentifiers().size(); index++) {
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
    public boolean hasEnrollingNonTreatmentEpoch() {

        if (this.getNonTreatmentEpochs() != null) {
            NonTreatmentEpoch nonTreatmentEpoch;
            Iterator<NonTreatmentEpoch> nonTreatmentEpochIter = this
                    .getNonTreatmentEpochs().iterator();
            while (nonTreatmentEpochIter.hasNext()) {
                nonTreatmentEpoch = nonTreatmentEpochIter.next();
                if ((nonTreatmentEpoch.getEnrollmentIndicator() != null)
                        && (nonTreatmentEpoch.getEnrollmentIndicator()
                ))
                    return true;
            }
        }
        return false;
    }

    @Transient
    public boolean hasElligibility() {

        if (this.getTreatmentEpochs().size() > 0) {
            TreatmentEpoch treatmentEpoch;
            Iterator<TreatmentEpoch> treatmentEpochIter = this
                    .getTreatmentEpochs().iterator();
            while (treatmentEpochIter.hasNext()) {
                treatmentEpoch = treatmentEpochIter.next();
                if (treatmentEpoch.getEligibilityCriteria().size() > 0)
                    return true;
            }
        }
        return false;
    }

    @Transient
    public boolean hasStratification() {

        if (this.getTreatmentEpochs().size() > 0) {
            TreatmentEpoch treatmentEpoch;
            Iterator<TreatmentEpoch> treatmentEpochIter = this
                    .getTreatmentEpochs().iterator();
            while (treatmentEpochIter.hasNext()) {
                treatmentEpoch = treatmentEpochIter.next();
                if (treatmentEpoch.getStratificationCriteria().size() > 0)
                    return true;
            }
        }
        return false;
    }
    
    @Transient
    public boolean hasRandomizedEpoch() {
        if (this.getTreatmentEpochs().size() > 0) {
            TreatmentEpoch treatmentEpoch;
            Iterator<TreatmentEpoch> treatmentEpochIter = this
                    .getTreatmentEpochs().iterator();
            while (treatmentEpochIter.hasNext()) {
                treatmentEpoch = treatmentEpochIter.next();
                if (treatmentEpoch.getRandomizedIndicator())
                    return true;
            }
        }
        return false;
    }

    @Transient
    public boolean hasEnrollingEpoch() {

        if (this.getTreatmentEpochs().size() > 0
                || (this.hasEnrollingNonTreatmentEpoch())) {
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
		if (criteriaFile!=null) {
			return new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(criteriaFile)));
		}  
		return null;
    }
	 @Transient
    public InputStream getCriteriaInputStream() {
		 if (criteriaFile != null){
			 return new ByteArrayInputStream(criteriaFile);
		 }
		 return null;
    }
	 
	 @Transient
	 public TreatmentEpoch getTreatmentEpochByName(String name){
		 List <Epoch> epochList = getEpochs();
		 Epoch epoch = null;
		 Iterator eIter = epochList.iterator();
		 while(eIter.hasNext()){
			 epoch = (Epoch)eIter.next();
			 if(epoch instanceof TreatmentEpoch){
				 if(epoch.getName().equalsIgnoreCase(name)){
					 return (TreatmentEpoch)epoch;
				 }
			 }
		 }
		 return null;
	 }

}