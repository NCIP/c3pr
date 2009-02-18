package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
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
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.constants.NotificationEmailSubstitutionVariablesEnum;
import edu.duke.cabig.c3pr.domain.customfield.CustomField;
import edu.duke.cabig.c3pr.domain.customfield.Customizable;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.exception.C3PRInvalidDataEntryException;
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
public class StudySubject extends
		InteroperableAbstractMutableDeletableDomainObject implements Customizable{

	private LazyListHelper lazyListHelper;

	private List<ScheduledEpoch> scheduledEpochs = new ArrayList<ScheduledEpoch>();
	
	private List<ConsentHistory> consentHistoryList = new ArrayList<ConsentHistory>();

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

	// TODO going to be removed
	private Integer stratumGroupNumber;

	private String paymentMethod;

	private String disapprovalReasonText;

	private List<StudySubject> childStudySubjects = new ArrayList<StudySubject>();

	private StudySubject parentStudySubject;

	private C3PRExceptionHelper c3PRExceptionHelper;

	private MessageSource c3prErrorMessages;

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
		lazyListHelper.add(ScheduledEpoch.class,
				new InstantiateFactory<ScheduledEpoch>(ScheduledEpoch.class));
		lazyListHelper.add(ScheduledEpoch.class,
				new InstantiateFactory<ScheduledEpoch>(ScheduledEpoch.class));
		this.startDate = new Date();
		this.primaryIdentifier = "SysGen";
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
		lazyListHelper.add(ConsentHistory.class, new ParameterizedBiDirectionalInstantiateFactory<ConsentHistory>(ConsentHistory.class, this));
		// mandatory, so that the lazy-projected list is managed properly.
	}

	// / BEAN PROPERTIES
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
		if (!forExample) {
			this.startDate = new Date();
			this.primaryIdentifier = "SysGen";
		}
		lazyListHelper.add(CustomField.class,new ParameterizedBiDirectionalInstantiateFactory<CustomField>(CustomField.class, this));
		lazyListHelper.add(ConsentHistory.class, new ParameterizedBiDirectionalInstantiateFactory<ConsentHistory>(ConsentHistory.class, this));
	}

	@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "SPA_ID", nullable = false)
	public List<ScheduledEpoch> getScheduledEpochs() {
		return scheduledEpochs;
	}

	private void setScheduledEpochs(List<ScheduledEpoch> scheduledEpochs) {
		this.scheduledEpochs = scheduledEpochs;
		lazyListHelper.setInternalList(ScheduledEpoch.class,new ProjectedList<ScheduledEpoch>(this.scheduledEpochs,ScheduledEpoch.class));
		lazyListHelper.setInternalList(ScheduledEpoch.class,new ProjectedList<ScheduledEpoch>(this.scheduledEpochs,ScheduledEpoch.class));
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
		if (tempList.size() == 0)
			return null;
		return tempList.get(tempList.size() - 1);
	}

	public void setCurrentScheduledEpoch(ScheduledEpoch scheduledEpoch) {

	}

	@Transient
	public DiseaseHistory getDiseaseHistory() {
		if (this.diseaseHistory == null)
			this.diseaseHistory = new DiseaseHistory();
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

//	@Transient
	public Date getInformedConsentSignedDate() {
		return informedConsentSignedDate ;
//		List<ConsentHistory> tempList = new ArrayList<ConsentHistory>();
//		tempList.addAll(getConsentHistoryList());
//		Collections.sort(tempList);
//		if (tempList.size() == 0)
//			return null;
//		ConsentHistory consentHistory = tempList.get(tempList.size() - 1);
//		return consentHistory.getConsentSignedDate();
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
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final StudySubject that = (StudySubject) o;

		if (startDate != null ? !startDate.equals(that.startDate)
				: that.startDate != null)
			return false;
		if (studySite != null ? !studySite.equals(that.studySite)
				: that.studySite != null)
			return false;
		// Participant#equals calls this method, so we can't use it here
		if (!DomainObjectTools.equalById(participant, that.participant))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		result = (studySite != null ? studySite.hashCode() : 0);
		result = 29 * result
				+ (participant != null ? participant.hashCode() : 0);
		result = 29 * result + (startDate != null ? startDate.hashCode() : 0);
		return result;
	}

	@Transient
	public String getInformedConsentSignedDateStr() {
		if (informedConsentSignedDate == null) {
			return "";
		} else if (informedConsentSignedDate.equals("")) {
			return "";
		}
		try {
			return DateUtil.formatDate(informedConsentSignedDate, "MM/dd/yyyy");
		} catch (ParseException e) {
			// do nothing
		}
		return null;
	}
	
	@Transient
	public String getOffStudyDateStr() {
		if (offStudyDate == null) {
			return "";
		} else if (offStudyDate.equals("")) {
			return "";
		}
		try {
			return DateUtil.formatDate(offStudyDate, "MM/dd/yyyy");
		} catch (ParseException e) {
			// do nothing
		}
		return null;
	}
	
	@Transient
	public String getStartDateStr() {
		if (startDate == null) {
			return "";
		} else if (startDate.equals("")) {
			return "";
		}
		try {
			return DateUtil.formatDate(startDate, "MM/dd/yyyy");
		} catch (ParseException e) {
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

	@OneToOne
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
		if (getTreatingPhysician() != null)
			return getTreatingPhysician().getHealthcareSiteInvestigator()
					.getInvestigator().getFullName();
		return getOtherTreatingPhysician();
	}

	public void setTreatingPhysicianFullName(String s) {

	}

	@Enumerated(EnumType.STRING)
	public RegistrationWorkFlowStatus getRegWorkflowStatus() {
		return regWorkflowStatus;
	}

	public void setRegWorkflowStatus(
			RegistrationWorkFlowStatus registrationWorkFlowStatus) {
		this.regWorkflowStatus = registrationWorkFlowStatus;
	}

	@Enumerated(EnumType.STRING)
	public RegistrationDataEntryStatus getRegDataEntryStatus() {
		return regDataEntryStatus;
	}

	public void setRegDataEntryStatus(
			RegistrationDataEntryStatus registrationDataEntryStatus) {
		this.regDataEntryStatus = registrationDataEntryStatus;
	}

	@Transient
	public String getDataEntryStatusString() {
		return this.regDataEntryStatus == RegistrationDataEntryStatus.COMPLETE
				&& this.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE ? "Complete"
				: "Incomplete";
	}
	
	@Transient
    public boolean getDataEntryStatus() {
        return this.regDataEntryStatus == RegistrationDataEntryStatus.COMPLETE
                        && this.getScheduledEpoch().getScEpochDataEntryStatus() == ScheduledEpochDataEntryStatus.COMPLETE ? true
                        : false;
    }

	@Transient
	public OrganizationAssignedIdentifier getCoOrdinatingCenterIdentifier() {
		for (OrganizationAssignedIdentifier organizationAssignedIdentifier : getOrganizationAssignedIdentifiers()) {
			if (organizationAssignedIdentifier.getType().equalsIgnoreCase(
					"Coordinating Center Assigned Study Subject Identifier")) {
				return organizationAssignedIdentifier;
			}
		}
		return null;
	}

	public void setCoOrdinatingCenterIdentifier(String value) {
		OrganizationAssignedIdentifier identifier = getOrganizationAssignedIdentifiers()
				.get(0);
		identifier.setHealthcareSite(this.getStudySite().getStudy()
				.getStudyCoordinatingCenters().get(0).getHealthcareSite());
		identifier.setType("Coordinating Center Assigned Study Subject Identifier");
		identifier.setValue(value);
	}

	@Transient
	public String getC3DIdentifier() {
		if (getSystemAssignedIdentifiers().size() == 0)
			return null;
		return getSystemAssignedIdentifiers().get(0).getValue();
	}

	public void setC3DIdentifier(String value) {
		SystemAssignedIdentifier identifier = getSystemAssignedIdentifiers()
				.get(0);
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

	// TODO to be deleted
	public Integer getStratumGroupNumber() {
		return stratumGroupNumber;
	}

	// TODO to be deleted
	public void setStratumGroupNumber(Integer stratumGroupNumber) {
		this.stratumGroupNumber = stratumGroupNumber;
	}

	// Adding refactored code
	public RegistrationDataEntryStatus evaluateRegistrationDataEntryStatus() {
		if (this.getInformedConsentSignedDateStr().equals(""))
			return RegistrationDataEntryStatus.INCOMPLETE;
		if (StringUtils.getBlankIfNull(this.getInformedConsentVersion())
				.equals(""))
			return RegistrationDataEntryStatus.INCOMPLETE;
		return RegistrationDataEntryStatus.COMPLETE;
	}

	// Adding refactored code
	public void evaluateRegistrationDataEntryStatus(List<Error> errors) {
		if (this.getInformedConsentSignedDateStr().equals("")) {
			errors.add(new Error("Informed consent signed date is missing"));
		}
		if (StringUtils.getBlankIfNull(this.getInformedConsentVersion())
				.equals("")) {
			errors.add(new Error("Informed consent version is missing"));
		}
		
	}

	// TODO to be deleted and merged with the overloaded method
	public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus() {
		return this.getScheduledEpoch().evaluateScheduledEpochDataEntryStatus(
				this.stratumGroupNumber);
	}

	public ScheduledEpochDataEntryStatus evaluateScheduledEpochDataEntryStatus(
			List<Error> errors) {
		return this.getScheduledEpoch().evaluateScheduledEpochDataEntryStatus(
				errors);
	}

	@Transient
	public boolean isStudySite(String nciCode) {
		return this.getStudySite().getHealthcareSite().getNciInstituteCode()
				.equals(nciCode);
	}

	/**
	 * Data Entry is considered complete if both Registrations and Scheduled
	 * Epoch data entry status are complete
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
	 * Computes if co-ordinating center needs to approve a record for successful
	 * registration. which is true if the study is multisite and the epoch is
	 * enrolling.
	 */
	public boolean requiresCoordinatingCenterApproval() {
		return this.getStudySite().getStudy().getMultiInstitutionIndicator()
				&& this.getScheduledEpoch().getEpoch().isEnrolling();
	}

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

	public List<Error> updateDataEntryStatus() {
		List<Error> errors = new ArrayList<Error>();
		this.evaluateRegistrationDataEntryStatus(errors);
		this.setRegDataEntryStatus((errors.size() > 0) ? RegistrationDataEntryStatus.INCOMPLETE : RegistrationDataEntryStatus.COMPLETE);
		this.getScheduledEpoch().setScEpochDataEntryStatus(this.evaluateScheduledEpochDataEntryStatus(errors));
		return errors;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public boolean requiresAffiliateSiteResponse() {
		if (this.getMultisiteWorkflowStatus() == WorkFlowStatusType.MESSAGE_RECIEVED)
			return true;
		return false;
	}

	@OneToMany(mappedBy = "parentStudySubject")
	@Cascade(value = { CascadeType.ALL })
	public List<StudySubject> getChildStudySubjects() {
		return childStudySubjects;
	}
	
	public void removeChildStudySubject(StudySubject studySubject) {
		getChildStudySubjects().remove(studySubject);
	}
	
	public void addChildStudySubject(StudySubject studySubject) {
		getChildStudySubjects().add(studySubject);
	}
	
	public void setChildStudySubjects(List<StudySubject> childStudySubjects) {
		this.childStudySubjects = childStudySubjects;
	}

	@ManyToOne
	@Cascade(value = { CascadeType.LOCK })
	@JoinTable(name = "stu_sub_associations", joinColumns = @JoinColumn(name = "child_stu_sub_id"), inverseJoinColumns = @JoinColumn(name = "parent_stu_sub_id"))
	public StudySubject getParentStudySubject() {
		return parentStudySubject;
	}

	public void setParentStudySubject(StudySubject parentStudySubject) {
		this.parentStudySubject = parentStudySubject;
	}

	@SuppressWarnings("unused")
	@Transient
	/*
	 * Used by the notifications use case to compose the email message by
	 * replacing the sub vars.
	 */
	public Map<Object, Object> buildMapForNotification() {

		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put(NotificationEmailSubstitutionVariablesEnum.PARTICIPANT_MRN
				.toString(),
				getParticipant().getMRN().getValue() == null ? "MRN"
						: getParticipant().getMRN().getValue());
		map.put(NotificationEmailSubstitutionVariablesEnum.REGISTRATION_STATUS
				.toString(),
				getRegWorkflowStatus().getDisplayName() == null ? "site name"
						: getRegWorkflowStatus().getDisplayName());
		map
				.put(
						NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE
								.toString(), getStudySite().getStudy()
								.getShortTitleText() == null ? "Short Title"
								: getStudySite().getStudy().getShortTitleText());
		map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_ID.toString(),
				getStudySite().getStudy().getId() == null ? "Study Id"
						: getStudySite().getStudy().getId().toString());

		map
				.put(
						NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD
								.toString(),
						getStudySite().getStudy().getTargetAccrualNumber() == null ? "Study Target Accrual"
								: getStudySite().getStudy()
										.getTargetAccrualNumber());
		map
				.put(
						NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_ACCRUAL_THRESHOLD
								.toString(),
						getStudySite().getTargetAccrualNumber() == null ? "Site Target Accrual"
								: getStudySite().getTargetAccrualNumber());
		map
				.put(
						NotificationEmailSubstitutionVariablesEnum.STUDY_CURRENT_ACCRUAL
								.toString(),
						getStudySite().getStudy().getCurrentAccrualCount() == null ? "Study Current Accrual"
								: getStudySite().getStudy()
										.getCurrentAccrualCount());
		map
				.put(
						NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_CURRENT_ACCRUAL
								.toString(), getStudySite()
								.getCurrentAccrualCount());
		return map;
	}

	@OneToMany
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "stu_sub_id")
	public List<EndPoint> getEndpoints() {
		return endpoints;
	}

	public ScheduledEpoch createScheduledEpoch(Epoch epoch) {
		ScheduledEpoch scheduledEpoch = new ScheduledEpoch();
		scheduledEpoch.setEpoch(epoch);
		return scheduledEpoch;
	}

	public boolean ifScheduledEpochCreatedForThisEpoch(Epoch epoch) {
		for (ScheduledEpoch scheduledEpoch : this.getScheduledEpochs())
			if (scheduledEpoch.getEpoch().equals(epoch)) {
				return true;
			}

		return false;
	}

	public ScheduledEpoch getMatchingScheduledEpoch(Epoch epoch) {
		for (ScheduledEpoch scheduledEpoch : this.getScheduledEpochs())
			if (scheduledEpoch.getEpoch().equals(epoch)) {
				return scheduledEpoch;
			}
		return null;
	}

	// returns errors if cannot register.
	public List<Error> canRegister() {
		return updateDataEntryStatus();
	}

	public List<Error> canReserve() {
		return updateDataEntryStatus();
	}

	public void register() {
		if (getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.PENDING) {
			throw new C3PRBaseRuntimeException(
					"StudySubject already registered on the epoch :"
							+ getScheduledEpoch().getEpoch().getName());
		} else {
			// This returns errors
			List<Error> errors = new ArrayList<Error>();
			errors = canRegister();

			if (errors.size() == 0) {

				// if the epoch requires randomization set it status to
				// 'Registered But Not Randomized', else set it status to
				// 'Registered'
				if (getScheduledEpoch().getEpoch().getRandomizedIndicator()) {
					getScheduledEpoch()
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
					if(!getScheduledEpoch().getEpoch().getEnrollmentIndicator()){
						getScheduledEpoch().setScEpochWorkflowStatus(
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

	public void reserve() {

		if (this.getRegWorkflowStatus() != RegistrationWorkFlowStatus.PENDING) {
			throw new C3PRBaseRuntimeException(
					"The subject cannot be reserved a spot on the study site. The subject is already registered or enrolled on the study site.");
		} else if (!this.getScheduledEpoch().getEpoch().getReservationIndicator()) {
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
				this.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
				this.setRegWorkflowStatus(RegistrationWorkFlowStatus.RESERVED);
			}
		}
	}

	@Transient
	public boolean isRandomizedOnScheduledEpoch() {
		return (getScheduledEpoch().getScheduledArm() != null && getScheduledEpoch()
				.getScheduledArm().getArm() != null);
	}

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

	private void doPhoneCallRandomization() {
		if (this.getScheduledEpoch().getScheduledArm() == null) {
			if(!this.getStudySite().getStudy().getBlindedIndicator())
			throw new C3PRBaseRuntimeException(
					"The subject should have been already assigned to a Scheduled Arm for the Scheduled Epoch :"
							+ getScheduledEpoch().getEpoch().getName());
		}
	}

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
				if (breTemp.getPosition().equals(
						(getScheduledEpoch()).getCurrentPosition())) {
					synchronized (this) {
						(getScheduledEpoch()).setCurrentPosition(breTemp
								.getPosition() + 1);
						arm = breTemp.getArm();
						break;
					}
				}
			}
		}

		if (arm == null) {
			throw new C3PRBaseRuntimeException(
					"No Arm avalable for this Epoch. Maybe the Randomization Book is exhausted");
		}
		return arm;
	}

	public void takeSubjectOffStudy(String offStudyReasonText, Date offStudyDate) {
		if (getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED) {
			throw new C3PRBaseRuntimeException(
					"The subject has to be enrolled before being taken off study");
		}
		this.setOffStudyReasonText(offStudyReasonText);
		this.setOffStudyDate(offStudyDate);
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.OFF_STUDY);
	}

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

	@Transient
	public boolean isStandAloneStudySubject() {
		return this.getStudySite().getStudy().getStandaloneIndicator();
	}

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

	@Transient
	public boolean isTransferrable() {
		List<ScheduledEpoch> tempList = new ArrayList<ScheduledEpoch>();
		tempList.addAll(getScheduledEpochs());
		Collections.sort(tempList);
		if (tempList.size() < 2)
			return false;
		if (tempList.get(tempList.size() - 1).getEpoch().getEpochOrder() < tempList
				.get(tempList.size() - 2).getEpoch().getEpochOrder()) {
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

	public void prepareForEnrollment() {

		if (!this.getStudySite().getStudy().getStandaloneIndicator() && this.getParentStudySubject() != null && this.getParentStudySubject().regWorkflowStatus!=RegistrationWorkFlowStatus.ENROLLED) {
			throw new C3PRBaseRuntimeException(" Cannot directly register on the embedded study. The registration can happen only through the parent");
		}
		
		
		if(getWorkPendingOnMandatoryCompanionRegistrations()){
			throw new C3PRBaseRuntimeException(" First register on the mandatory companions before enrolling on the parent");
		}
		for (StudySubject childStudySubject : this.getChildStudySubjects()) {
			if (getMatchingCompanionStudyAssociation(childStudySubject) != null) {
				if (getMatchingCompanionStudyAssociation(childStudySubject).getMandatoryIndicator()) {
					if (!childStudySubject.getScheduledEpoch().getEpoch().getEnrollmentIndicator()) {
						throw new C3PRBaseRuntimeException(" First register the subject on the enrolling epoch of the mandatory companions before proceeding with enrollment");
					}
				}
			}
		}
		

		if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.PENDING) {
			register();
			List<Error> errors = new ArrayList<Error>();
			canEnroll(errors);
		}

	}

	public void doLocalEnrollment() {
		if (getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.REGISTERED) {
			if (getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED){
				doLocalRandomization();
			}
			this.addIdentifier(IdentifierGenerator
					.generateOrganizationAssignedIdentifier(this));
			this.getScheduledEpoch().setScEpochWorkflowStatus(
					ScheduledEpochWorkFlowStatus.REGISTERED);
		}
		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);

		// TODO This should also set all the child studysubjects which are in
		// 'REGISTERED_BUT_NOT_ENROLLED' state to enrolled"

		for (StudySubject childStudySubject : this.getChildStudySubjects()) {
			if (childStudySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED && childStudySubject.getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.PENDING) {
				if (childStudySubject.getScheduledEpoch().getScEpochWorkflowStatus() == ScheduledEpochWorkFlowStatus.REGISTERED_BUT_NOT_RANDOMIZED){
					childStudySubject.doLocalRandomization();
				}
				childStudySubject.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
				childStudySubject.getScheduledEpoch().setScEpochWorkflowStatus(ScheduledEpochWorkFlowStatus.REGISTERED);
			}
		}
	}

	public void doMutiSiteEnrollment(
			ScheduledEpoch coordinatingCenterReturnedScheduledEpoch,
			OrganizationAssignedIdentifier coordinatingCenterAssignedIdentifier) {
		if (getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.REGISTERED) {
			ScheduledEpoch localScheduledEpoch = this
					.getScheduledEpochByEpochName(coordinatingCenterReturnedScheduledEpoch);
			if (this.getCurrentScheduledEpoch().getRequiresArm()) {
				Arm arm = getCurrentScheduledEpoch().getEpoch().getArmByName(
						coordinatingCenterReturnedScheduledEpoch
								.getScheduledArm().getArm());
				ScheduledArm scheduledArm = new ScheduledArm();
				scheduledArm.setArm(arm);
				localScheduledEpoch.addScheduledArm(scheduledArm);
			}
			this.addIdentifier(coordinatingCenterAssignedIdentifier);
			this.getScheduledEpoch().setScEpochWorkflowStatus(
					ScheduledEpochWorkFlowStatus.REGISTERED);
		}

		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);

		// TODO This should also set all the child studysubjects which are in
		// 'REGISTERED_BUT_NOT_ENROLLED' state to enrolled"

		for (StudySubject childStudySubject : this.getChildStudySubjects()) {
			if (childStudySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED) {
				childStudySubject
						.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
			}
		}

	}

	public ScheduledEpoch getScheduledEpochByEpochName(ScheduledEpoch schEpoch) {
		for (ScheduledEpoch scheduledEpoch : this.getScheduledEpochs()) {
			if (scheduledEpoch.getEpoch().getName().equalsIgnoreCase(
					schEpoch.getEpoch().getName())) {
				return scheduledEpoch;
			}
		}

		return null;
	}

	public void doMutiSiteTransfer(
			ScheduledEpoch coordinatingCenterReturnedScheduledEpoch,
			OrganizationAssignedIdentifier coordinatingCenterAssignedIdentifier) {
		if (getScheduledEpoch().getScEpochWorkflowStatus() != ScheduledEpochWorkFlowStatus.REGISTERED) {
			ScheduledEpoch localScheduledEpoch = this
					.getScheduledEpochByEpochName(coordinatingCenterReturnedScheduledEpoch);
			if (this.getCurrentScheduledEpoch().getRequiresArm()) {
				Arm arm = getCurrentScheduledEpoch().getEpoch().getArmByName(
						coordinatingCenterReturnedScheduledEpoch
								.getScheduledArm().getArm());
				ScheduledArm scheduledArm = new ScheduledArm();
				scheduledArm.setArm(arm);
				localScheduledEpoch.addScheduledArm(scheduledArm);
			}
			this.addIdentifier(coordinatingCenterAssignedIdentifier);
			this.getScheduledEpoch().setScEpochWorkflowStatus(
					ScheduledEpochWorkFlowStatus.REGISTERED);
		}

		this.setRegWorkflowStatus(RegistrationWorkFlowStatus.ENROLLED);
	}

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
	
	public List<Error> canEnroll(List<Error> errors){
		
		for (StudySubject childStudySubject : this.getChildStudySubjects()) {
			if (getMatchingCompanionStudyAssociation(childStudySubject) != null) {
				if (getMatchingCompanionStudyAssociation(childStudySubject)
						.getMandatoryIndicator()) {
					childStudySubject.evaluateRegistrationDataEntryStatus(errors);
					childStudySubject.evaluateScheduledEpochDataEntryStatus(errors);
					
				}
			}
		}
		return errors;
	}
	
	@OneToMany(mappedBy = "studySubject", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
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
		customField.setStudySubject(this);
	}
	
	@Transient
	public boolean getWorkPendingOnMandatoryCompanionRegistrations(){
		for(CompanionStudyAssociation companionStudyAssociation : this.getStudySite().getStudy().getCompanionStudyAssociations()){
			if (companionStudyAssociation.getMandatoryIndicator()) {
				boolean hasCorrespondingStudySubject = false;
				for (StudySubject childStudySubject : this
						.getChildStudySubjects()) {
					if (childStudySubject.getStudySite().getStudy().equals(
							companionStudyAssociation.getCompanionStudy())) {
						hasCorrespondingStudySubject = true;
					}
				}
				if (!hasCorrespondingStudySubject)
					return true;
			}
		}	
		for (StudySubject childStudySubject : this.getChildStudySubjects()) {
			if(childStudySubject.getRegWorkflowStatus()!=RegistrationWorkFlowStatus.ENROLLED){
				if (getMatchingCompanionStudyAssociation(childStudySubject) != null) {
					if (getMatchingCompanionStudyAssociation(childStudySubject)
							.getMandatoryIndicator()) {
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
	
	@Transient
	public boolean hasMandatoryCompanions(){
		
		for(CompanionStudyAssociation companionStudyAssociation:this.getStudySite().getStudy().getCompanionStudyAssociations()){
			if(companionStudyAssociation.getMandatoryIndicator()){
				return true;
			}
		}
		return false;
	}
	
	@OneToMany
	@Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@JoinColumn(name = "STU_SUB_ID", nullable = false)
	public List<ConsentHistory> getConsentHistoryList() {
		return consentHistoryList;
	}

	private void setConsentHistoryList(List<ConsentHistory> consentHistoryList) {
		this.consentHistoryList = consentHistoryList;
		lazyListHelper.setInternalList(ConsentHistory.class,new ProjectedList<ConsentHistory>(this.consentHistoryList,ConsentHistory.class));
	}

	public void addConsentHistory(ConsentHistory consentHistory) {
		getConsentHistoryList().add(consentHistory);
	}
	
	@Transient
	public ConsentHistory getCurrentConsentHistory() {
		return this.getConsentHistoryList().get(getConsentHistoryList().size() - 1);
	}

	@Transient
	public boolean getIsDirectArmAssigment(){
		if(this.getScheduledEpoch().getRequiresArm() && !this.getScheduledEpoch().getRequiresRandomization()){
			return true ;
		}
		return false ;
	}

	
}