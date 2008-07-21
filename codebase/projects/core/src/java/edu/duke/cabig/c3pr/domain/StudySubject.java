package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections15.functors.InstantiateFactory;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.ProjectedList;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;
import gov.nih.nci.cabig.ctms.domain.DomainObjectTools;

/**
 * @author Ram Chilukuri
 */

/**
 * @author Kruttik Aggarwal
 *
 */
@Entity
@Table(name = "STUDY_SUBJECTS")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_SUBJECTS_ID_SEQ") })
public class StudySubject extends CCTSAbstractMutableDeletableDomainObject {
    private LazyListHelper lazyListHelper;

    private List<ScheduledEpoch> scheduledEpochs = new ArrayList<ScheduledEpoch>();

    private String name;

    private String offStudyReasonText;

    private Date offStudyDate;

    private StudySite studySite;

    private Participant participant;

    private Date startDate;

    private Date informedConsentSignedDate;

    private String informedConsentVersion;

    private String primaryIdentifier;

    private StudyInvestigator treatingPhysician;

    private String otherTreatingPhysician;

    private RegistrationDataEntryStatus regDataEntryStatus;

    private RegistrationWorkFlowStatus regWorkflowStatus;

    private DiseaseHistory diseaseHistory;

    private List<Identifier> identifiers;

    private Integer stratumGroupNumber;
    
    private String paymentMethod;
    
    private String disapprovalReasonText;
    
    private List<StudySubject> childStudySubjects ;
    
    private StudySubject parentStudySubject ;
    
	public StudySubject() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(ScheduledEpoch.class,
                        new InstantiateFactory<ScheduledEpoch>(
                                        ScheduledEpoch.class));
        lazyListHelper.add(ScheduledEpoch.class,
                        new InstantiateFactory<ScheduledEpoch>(
                                        ScheduledEpoch.class));
        this.startDate = new Date();
        this.primaryIdentifier = "SysGen";
        this.regDataEntryStatus = RegistrationDataEntryStatus.INCOMPLETE;
        this.regWorkflowStatus = RegistrationWorkFlowStatus.UNREGISTERED;
        lazyListHelper.add(OrganizationAssignedIdentifier.class,
                        new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(
                                        OrganizationAssignedIdentifier.class));
        lazyListHelper.add(SystemAssignedIdentifier.class,
                        new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(
                                        SystemAssignedIdentifier.class));
        setIdentifiers(new ArrayList<Identifier>());
        // mandatory, so that the lazy-projected list is managed properly.
    }

    // / BEAN PROPERTIES
    public StudySubject(boolean forExample) {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(ScheduledEpoch.class,
                        new InstantiateFactory<ScheduledEpoch>(
                                        ScheduledEpoch.class));
        lazyListHelper.add(ScheduledEpoch.class,
                        new InstantiateFactory<ScheduledEpoch>(
                                        ScheduledEpoch.class));
        lazyListHelper.add(OrganizationAssignedIdentifier.class,
                        new ParameterizedInstantiateFactory<OrganizationAssignedIdentifier>(
                                        OrganizationAssignedIdentifier.class));
        lazyListHelper.add(SystemAssignedIdentifier.class,
                        new ParameterizedInstantiateFactory<SystemAssignedIdentifier>(
                                        SystemAssignedIdentifier.class));
        setIdentifiers(new ArrayList<Identifier>());
        if (!forExample) {
            this.startDate = new Date();
            this.primaryIdentifier = "SysGen";
        }
    }

    @OneToMany
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "SPA_ID", nullable = false)
    public List<ScheduledEpoch> getScheduledEpochs() {
        return scheduledEpochs;
    }

    private void setScheduledEpochs(List<ScheduledEpoch> scheduledEpochs) {
        this.scheduledEpochs = scheduledEpochs;
        lazyListHelper.setInternalList(ScheduledEpoch.class,
                        new ProjectedList<ScheduledEpoch>(this.scheduledEpochs,
                                        ScheduledEpoch.class));
        lazyListHelper.setInternalList(ScheduledEpoch.class,
                        new ProjectedList<ScheduledEpoch>(this.scheduledEpochs,
                                        ScheduledEpoch.class));
    }

    public void addScheduledEpoch(ScheduledEpoch scheduledEpoch) {
        getScheduledEpochs().add(scheduledEpoch);
    }

    @Transient
    public ScheduledEpoch getScheduledEpoch() {
        return getCurrentScheduledEpoch();
    }

    private void setScheduledEpoch(ScheduledEpoch scheduledEpoch) {
    }

    @Transient
    public ScheduledEpoch getCurrentScheduledEpoch() {
        List<ScheduledEpoch> tempList = new ArrayList<ScheduledEpoch>();
        tempList.addAll(getScheduledEpochs());
        Collections.sort(tempList);
        if (tempList.size() == 0) return null;
        return tempList.get(tempList.size() - 1);
    }

    public void setCurrentScheduledEpoch(ScheduledEpoch scheduledEpoch) {

    }

    @Transient
    public DiseaseHistory getDiseaseHistory() {
        if (this.diseaseHistory == null) this.diseaseHistory = new DiseaseHistory();
        return diseaseHistory;
    }

    public void setDiseaseHistory(DiseaseHistory diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    @OneToOne
    @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "DISEASE_HISTORY_ID")
    public DiseaseHistory getDiseaseHistoryInternal() {
        return diseaseHistory;
    }

    public void setDiseaseHistoryInternal(DiseaseHistory diseaseHistory) {
        this.diseaseHistory = diseaseHistory;
    }

    public void setStudySite(StudySite studySite) {
        this.studySite = studySite;
    }

    @ManyToOne
    @JoinColumn(name = "STO_ID", nullable = false)
    @Cascade( { CascadeType.LOCK })
    public StudySite getStudySite() {
        return studySite;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    @ManyToOne
    @JoinColumn(name = "PRT_ID", nullable = false)
    @Cascade( { CascadeType.LOCK })
    public Participant getParticipant() {
        return participant;
    }

    public Date getInformedConsentSignedDate() {
        return informedConsentSignedDate;
    }

    public void setInformedConsentSignedDate(Date informedConsentSignedDate) {
        this.informedConsentSignedDate = informedConsentSignedDate;
    }

    public String getDisapprovalReasonText() {
        return disapprovalReasonText;
    }

    public void setDisapprovalReasonText(String disapprovalReasonText) {
        this.disapprovalReasonText = disapprovalReasonText;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final StudySubject that = (StudySubject) o;

        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (studySite != null ? !studySite.equals(that.studySite) : that.studySite != null) return false;
        // Participant#equals calls this method, so we can't use it here
        if (!DomainObjectTools.equalById(participant, that.participant)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (studySite != null ? studySite.hashCode() : 0);
        result = 29 * result + (participant != null ? participant.hashCode() : 0);
        result = 29 * result + (startDate != null ? startDate.hashCode() : 0);
        return result;
    }

    @Transient
    public String getInformedConsentSignedDateStr() {
        if (informedConsentSignedDate == null) {
            return "";
        }
        else if (informedConsentSignedDate.equals("")) {
            return "";
        }
        try {
            return DateUtil.formatDate(informedConsentSignedDate, "MM/dd/yyyy");
        }
        catch (ParseException e) {
            // do nothing
        }
        return null;
    }

    @Transient
    public String getStartDateStr() {
        if (startDate == null) {
            return "";
        }
        else if (startDate.equals("")) {
            return "";
        }
        try {
            return DateUtil.formatDate(startDate, "MM/dd/yyyy");
        }
        catch (ParseException e) {
            // do nothing
        }
        return "";
    }

    @OneToMany
    @Cascade( { CascadeType.MERGE, CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "SPA_ID")
    @Where(clause = "retired_indicator  = 'false'")
    @OrderBy
    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    private void setIdentifiers(List<Identifier> identifiers) {
        this.identifiers = identifiers;
        // initialize projected list for OrganizationAssigned and SystemAssignedIdentifier
        lazyListHelper.setInternalList(OrganizationAssignedIdentifier.class,
                        new ProjectedList<OrganizationAssignedIdentifier>(this.identifiers,
                                        OrganizationAssignedIdentifier.class));
        lazyListHelper.setInternalList(SystemAssignedIdentifier.class,
                        new ProjectedList<SystemAssignedIdentifier>(this.identifiers,
                                        SystemAssignedIdentifier.class));
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

    public void addIdentifier(Identifier identifier) {
        getIdentifiers().add(identifier);
    }

    public void removeIdentifier(Identifier identifier) {
        getIdentifiers().remove(identifier);
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

    @Column(name = "informedConsentVersion", nullable = true)
    public String getInformedConsentVersion() {
        return informedConsentVersion;
    }

    public void setInformedConsentVersion(String informedConsentVersion) {
        this.informedConsentVersion = informedConsentVersion;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STI_ID")
    public StudyInvestigator getTreatingPhysician() {
        return treatingPhysician;
    }

    public void setTreatingPhysician(StudyInvestigator treatingPhysician) {
        this.treatingPhysician = treatingPhysician;
    }

    @Transient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherTreatingPhysician() {
        return otherTreatingPhysician;
    }

    public void setOtherTreatingPhysician(String otherTreatingPhysician) {
        this.otherTreatingPhysician = otherTreatingPhysician;
    }

    @Transient
    public String getTreatingPhysicianFullName() {
        if (getTreatingPhysician() != null) return getTreatingPhysician()
                        .getHealthcareSiteInvestigator().getInvestigator().getFullName();
        return getOtherTreatingPhysician();
    }

    public void setTreatingPhysicianFullName(String s) {

    }

    @Enumerated(EnumType.STRING)
    public RegistrationWorkFlowStatus getRegWorkflowStatus() {
        return regWorkflowStatus;
    }

    public void setRegWorkflowStatus(RegistrationWorkFlowStatus registrationWorkFlowStatus) {
        this.regWorkflowStatus = registrationWorkFlowStatus;
    }

    @Enumerated(EnumType.STRING)
    public RegistrationDataEntryStatus getRegDataEntryStatus() {
        return regDataEntryStatus;
    }

    public void setRegDataEntryStatus(RegistrationDataEntryStatus registrationDataEntryStatus) {
        this.regDataEntryStatus = registrationDataEntryStatus;
    }

    @Transient
    public StratumGroup getStratumGroup() throws C3PRBaseException {
        StratumGroup stratumGroup = null;
        if (this.stratumGroupNumber != null) {
            stratumGroup = ((ScheduledEpoch) getScheduledEpoch()).getEpoch()
                            .getStratumGroupByNumber(this.stratumGroupNumber);
        }
        else {
            List<SubjectStratificationAnswer> ssaList = ((ScheduledEpoch) getScheduledEpoch())
                            .getSubjectStratificationAnswers();
            if (ssaList != null) {
                Iterator iter = ssaList.iterator();
                List<StratificationCriterionAnswerCombination> scacList = new ArrayList<StratificationCriterionAnswerCombination>();
                while (iter.hasNext()) {
                    scacList.add(new StratificationCriterionAnswerCombination(
                                    (SubjectStratificationAnswer) iter.next()));
                }
                stratumGroup = (getScheduledEpoch()).getEpoch()
                                .getStratumGroupForAnsCombination(scacList);
            }
        }
        if (stratumGroup == null) {
            throw new C3PRBaseException(
                            "No startum group found. Maybe the answer combination does not have a valid startum group");
        }
        return stratumGroup;
    }

    @Transient
    public String getDataEntryStatusString() {
        return this.regDataEntryStatus == RegistrationDataEntryStatus.COMPLETE
                        && this.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE ? "Complete"
                        : "Incomplete";
    }

    @Transient
    public String getCoOrdinatingCenterIdentifier() {
        if (getOrganizationAssignedIdentifiers().size() == 0) return null;
        return getOrganizationAssignedIdentifiers().get(0).getValue();
    }

    public void setCoOrdinatingCenterIdentifier(String value) {
        OrganizationAssignedIdentifier identifier = getOrganizationAssignedIdentifiers().get(0);
        identifier.setHealthcareSite(this.getStudySite().getStudy().getStudyCoordinatingCenters()
                        .get(0).getHealthcareSite());
        identifier.setType("Coordinating Center Identifier");
        identifier.setValue(value);
    }

    @Transient
    public String getC3DIdentifier() {
        if (getSystemAssignedIdentifiers().size() == 0) return null;
        return getSystemAssignedIdentifiers().get(0).getValue();
    }

    public void setC3DIdentifier(String value) {
        SystemAssignedIdentifier identifier = getSystemAssignedIdentifiers().get(0);
        identifier.setSystemName("C3D");
        identifier.setType("C3D Identifier");
        identifier.setValue(value);
    }

    public Date getOffStudyDate() {
        return offStudyDate;
    }

    public void setOffStudyDate(Date offStudyDate) {
        this.offStudyDate = offStudyDate;
    }

    public String getOffStudyReasonText() {
        return offStudyReasonText;
    }

    public void setOffStudyReasonText(String offStudyReasonText) {
        this.offStudyReasonText = offStudyReasonText;
    }

    public Integer getStratumGroupNumber() {
        return stratumGroupNumber;
    }

    public void setStratumGroupNumber(Integer stratumGroupNumber) {
        this.stratumGroupNumber = stratumGroupNumber;
    }

    //Adding refactored code
    public RegistrationDataEntryStatus evaluateRegistrationDataEntryStatus() {
        if (this.getInformedConsentSignedDateStr().equals("")) return RegistrationDataEntryStatus.INCOMPLETE;
        if (StringUtils.getBlankIfNull(this.getInformedConsentVersion()).equals("")) return RegistrationDataEntryStatus.INCOMPLETE;
        return RegistrationDataEntryStatus.COMPLETE;
    }

    public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus() {
        return this.getScheduledEpoch().evaluateScheduledEpochDataEntryStatus(this.stratumGroupNumber);
    }
    
    @Transient
    public boolean isStudySite(String nciCode) {
        return this.getStudySite().getHealthcareSite().getNciInstituteCode().equals(nciCode);
    }
    
    
    /**
     * Data Entry is considered complete if both Registrations and Scheduled Epoch data entry status are complete
     */
    @Transient
    public boolean isDataEntryComplete() {
        if (this.getRegDataEntryStatus() == RegistrationDataEntryStatus.COMPLETE
                        && this.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE) {
            return true;
        }
        return false;
    }
    
    public boolean canRandomize() {
        return regDataEntryStatus == RegistrationDataEntryStatus.COMPLETE
                        && getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE;
    }
    
    /**
     * This methods computes if a a study subject instance is registerable which is true
     * if the data entry is complete, not alrerady registered and the epoch is enrolling 
     */
    @Transient
    public boolean isRegisterable() {
        if (this.isDataEntryComplete()
                        && this.getRegWorkflowStatus() != RegistrationWorkFlowStatus.REGISTERED
                        && this.getScheduledEpoch().getEpoch().isEnrolling()) {
            return true;
        }
        return false;
    }

    @Transient
    public boolean isReservable() {
        if (this.isDataEntryComplete()
                        && this.getRegWorkflowStatus() != RegistrationWorkFlowStatus.REGISTERED
                        && this.getScheduledEpoch().getEpoch().isReserving()) {
            return true;
        }
        return false;
    }
    
    /**
     * Computes if co-ordinating center needs to approve a record for successful registration. which is true
     * if the study is multisite and the epoch is enrolling.
     */
    public boolean requiresCoordinatingCenterApproval() {
        return this.getStudySite().getStudy().getMultiInstitutionIndicator()
                        && this.getScheduledEpoch().getEpoch().isEnrolling();
    }
    
    @Transient
    public boolean isCoOrdinatingCenter(String nciCode) {
        return this.getStudySite().getStudy().getStudyCoordinatingCenters().get(0)
                        .getHealthcareSite().getNciInstituteCode()
                        .equals(nciCode);
    }
    
    public void updateDataEntryStatus(){
        this.setRegDataEntryStatus(this.evaluateRegistrationDataEntryStatus());
        this.getScheduledEpoch().setScEpochDataEntryStatus(
                        this.evaluateScheduledEpochDataEntryStatus());
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
	
    public void disapprove(String disapprovalReasonText){
        if(this.regWorkflowStatus!=RegistrationWorkFlowStatus.REGISTERED){
            this.regWorkflowStatus=RegistrationWorkFlowStatus.DISAPPROVED;
            this.disapprovalReasonText=disapprovalReasonText;
        }
        getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.DISAPPROVED);
        getScheduledEpoch().setDisapprovalReasonText(disapprovalReasonText);
    }
    
    public boolean requiresAffiliateSiteResponse(){
        if(this.getMultisiteWorkflowStatus()==CCTSWorkflowStatusType.MESSAGE_RECIEVED)
            return true;
        return false;
    }
    
    @OneToMany(mappedBy = "parentStudySubject" )
	public List<StudySubject> getChildStudySubjects() {
		return childStudySubjects;
	}

	public void setChildStudySubjects(List<StudySubject> childStudySubjects) {
		this.childStudySubjects = childStudySubjects;
	}

	
	@ManyToOne
	@Cascade(value = { CascadeType.LOCK})
    @JoinTable(name="stu_sub_associations",
        joinColumns = @JoinColumn(name="child_stu_sub_id"),
        inverseJoinColumns = @JoinColumn(name="parent_stu_sub_id")
    )	
	public StudySubject getParentStudySubject() {
		return parentStudySubject;
	}
	
	public void setParentStudySubject(StudySubject parentStudySubject) {
		this.parentStudySubject = parentStudySubject;
	}
    
//	@ManyToOne
//    @Cascade( { CascadeType.ALL})
//    @JoinColumn(name = "parent_stu_sub_id", nullable = false)
//	public StudySubject getParentStudySubject() {
//		return parentStudySubject;
//	}
//
//	public void setParentStudySubject(StudySubject parentStudySubject) {
//		this.parentStudySubject = parentStudySubject;
//	}

}