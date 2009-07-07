package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.NotificationEmailSubstitutionVariablesEnum;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.DateUtil;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class StudySite.
 * 
 * @author Ram Chilukuri, Priyatam
 * @author kherm
 */
@Entity
@DiscriminatorValue(value = "SST")
public class StudySite extends StudyOrganization implements Comparable<StudySite> {
    
    /** The irb approval date. */
    private Date irbApprovalDate;

    /** The role code. */
    private String roleCode;
    
    /** The coordinating center study status. */
    private CoordinatingCenterStudyStatus coordinatingCenterStudyStatus;
    
    /** The companion study association. */
    private CompanionStudyAssociation companionStudyAssociation ;
    
    /** The target accrual number. */
    private Integer targetAccrualNumber;

    /** The site study status. */
    private SiteStudyStatus siteStudyStatus;

    /** The start date. */
    private Date startDate;

    /** The end date. */
    private Date endDate;

    /** The irb approval date str. */
    private String irbApprovalDateStr;

    /** The start date str. */
    private String startDateStr;

    /** The study subjects. */
    private List<StudySubject> studySubjects = new ArrayList<StudySubject>();

    /** The lazy list helper. */
    private LazyListHelper lazyListHelper;

    /** The c3 pr exception helper. */
    private C3PRExceptionHelper c3PRExceptionHelper;

    /** The c3pr error messages. */
    private MessageSource c3prErrorMessages;

    /**
     * Instantiates a new study site.
     */
    public StudySite() {
        coordinatingCenterStudyStatus=CoordinatingCenterStudyStatus.PENDING;
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("error_messages_multisite");
        ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
        resourceBundleMessageSource1.setBasename("error_messages_c3pr");
        resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
        this.c3prErrorMessages = resourceBundleMessageSource1;
        this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
        siteStudyStatus = SiteStudyStatus.PENDING;
    }
    
    /**
     * Adds the study subject.
     * 
     * @param spAssignments the sp assignments
     */
    public void addStudySubject(StudySubject spAssignments) {
        studySubjects.add(spAssignments);
        studySubjects.size();
    }

    /**
     * Removes the study subject.
     * 
     * @param studySubject the study subject
     */
    public void removeStudySubject(StudySubject studySubject) {
        studySubjects.remove(studySubject);
    }

    /**
     * Are there any assignments using this relationship?.
     * 
     * @return true, if checks if is used
     */
    @Transient
    public boolean isUsed() {
        return getStudySubjects().size() > 0;
    }

    // / BEAN PROPERTIES

    /**
     * Sets the study subjects.
     * 
     * @param studySubjects the new study subjects
     */
    public void setStudySubjects(List<StudySubject> studySubjects) {
        this.studySubjects = studySubjects;
    }

    /**
     * Gets the study subjects.
     * 
     * @return the study subjects
     */
    @OneToMany(mappedBy = "studySite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudySubject> getStudySubjects() {
        return studySubjects;
    }

    /**
     * Sets the irb approval date.
     * 
     * @param irbApprovalDate the new irb approval date
     */
    public void setIrbApprovalDate(Date irbApprovalDate) {
        this.irbApprovalDate = irbApprovalDate;
    }

    /**
     * Gets the irb approval date.
     * 
     * @return the irb approval date
     */
    public Date getIrbApprovalDate() {
        return irbApprovalDate;
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

    /**
     * Gets the role code.
     * 
     * @return the role code
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * Sets the role code.
     * 
     * @param roleCode the new role code
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    /**
     * Gets the end date.
     * 
     * @return the end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date.
     * 
     * @param endDate the new end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(StudySite o) {
        if (this.equals(o)) return 0;
        else return 1;
    }

    /**
     * Gets the irb approval date str.
     * 
     * @return the irb approval date str
     */
    @Transient
    public String getIrbApprovalDateStr() {
        try {
            return DateUtil.formatDate(irbApprovalDate, "MM/dd/yyyy");
        }
        catch (Exception e) {
        	return "";
        }
    }

    /**
     * Gets the start date str.
     * 
     * @return the start date str
     */
    @Transient
    public String getStartDateStr() {
        try {
            return DateUtil.formatDate(startDate, "MM/dd/yyyy");
        }
        catch (Exception e) {
        	return "";
        }
    }

    /**
     * Gets the site study status.
     * 
     * @return the site study status
     */
    @Enumerated(EnumType.STRING)
    public SiteStudyStatus getSiteStudyStatus() {
        return siteStudyStatus;
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
     * Gets the current accrual count.
     * 
     * @return the current accrual count
     */
    @Transient
    public int getCurrentAccrualCount() {
        int count = 0;
        for (StudySubject s : this.getStudySubjects()) {
            if (s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.ENROLLED || s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED
                            || s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED) count++;
        }
        return count;
    }

    /**
     * Evaluate site study status.
     * 
     * @return the site study status
     * 
     * @throws C3PRCodedException the c3 pr coded exception
     */
    public SiteStudyStatus evaluateSiteStudyStatus() throws C3PRCodedException {

        if (this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL) {
            return SiteStudyStatus.CLOSED_TO_ACCRUAL;
        }
        if (this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
            return SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT;
        }

        if (this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL) {
            return SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL;
        }
        if (this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT) {
            return SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT;
        }

        if (this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING
                        && this.getSiteStudyStatus() == SiteStudyStatus.ACTIVE) {
            return SiteStudyStatus.AMENDMENT_PENDING;
        }
        if (this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN) {

            Date currentDate = new Date();
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(currentDate);
            calendar.add(calendar.YEAR, -1);
            String allowedOldDate = "";
            String todayDate = "";

            try {
                allowedOldDate = DateUtil.formatDate(calendar.getTime(), "MM/dd/yyyy");
                todayDate = DateUtil.formatDate(currentDate, "MM/dd/yyyy");
            }
            catch (ParseException e) {
                throw getC3PRExceptionHelper().getException(
                                getCode("C3PR.EXCEPTION.STUDYSITE.PARSING.DATE.CODE"),
                                new String[] { this.getHealthcareSite().getName() });
            }

            if (this.getIrbApprovalDate() == null) {
                if (this.getId() != null) {
                    throw getC3PRExceptionHelper()
                                    .getException(
                                                    getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.IRB_APPROVAL_DATE.CODE"),
                                                    new String[] { this.getHealthcareSite()
                                                                    .getName() });
                }
                return SiteStudyStatus.PENDING;
            }
            if (this.getIrbApprovalDate().after(currentDate)) {
                if ((this.getId() != null)) {
                    throw getC3PRExceptionHelper()
                                    .getException(
                                                    getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.INVALID.IRB_APPROVAL_DATE.CODE"),
                                                    new String[] {
                                                            this.getHealthcareSite().getName(),
                                                            todayDate });
                }
                return SiteStudyStatus.PENDING;
            }
            if (this.getIrbApprovalDate().before(calendar.getTime())) {
                if ((this.getId() != null)) {
                    throw getC3PRExceptionHelper()
                                    .getException(
                                                    getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.EXPIRED.IRB_APPROVAL_DATE.CODE"),
                                                    new String[] {
                                                            this.getHealthcareSite().getName(),
                                                            allowedOldDate });
                }
                return SiteStudyStatus.PENDING;
            }
            if ((this.getStartDate() == null) || (this.getStartDate().after(currentDate))) {
                if ((this.getId() != null)) {
                    throw getC3PRExceptionHelper()
                                    .getException(
                                                    getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.INVALID.START_DATE.CODE"),
                                                    new String[] { this.getHealthcareSite()
                                                                    .getName() });
                }
                return SiteStudyStatus.PENDING;
            }
            return SiteStudyStatus.ACTIVE;
        }

        return SiteStudyStatus.PENDING;
    }

    /**
     * Sets the work flow site study status.
     * 
     * @param status the status
     * 
     * @return the study
     * 
     * @throws C3PRCodedException the c3 pr coded exception
     */
//    public Study setWorkFlowSiteStudyStatus(SiteStudyStatus status) throws C3PRCodedException {
//        SiteStudyStatus currentSiteStatus = this.getSiteStudyStatus();
//        if ((status == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL)
//                        || (status == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
//            if (((currentSiteStatus) == (SiteStudyStatus.ACTIVE))
//                            || ((currentSiteStatus) == (SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL))) {
//                this.setSiteStudyStatus(status);
//            }
//        }
//        else if ((status == SiteStudyStatus.CLOSED_TO_ACCRUAL)
//                        || (status == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
//            if (((currentSiteStatus) == (SiteStudyStatus.PENDING))
//                            || ((currentSiteStatus) == (SiteStudyStatus.AMENDMENT_PENDING))
//                            || ((currentSiteStatus) == (SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
//                if ((this.getId() != null)) {
//                    throw getC3PRExceptionHelper()
//                                    .getException(
//                                                    getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
//                                                    new String[] { status.getDisplayName() });
//                }
//                return getStudy();
//            }
//            else this.setSiteStudyStatus(status);
//        }
//        else {
//            if (status == evaluateSiteStudyStatus()) {
//                this.setSiteStudyStatus(status);
//            }
//            else {
//                if ((getStudy().getId() != null)) {
//                    throw getC3PRExceptionHelper()
//                                    .getException(
//                                                    getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
//                                                    new String[] {
//                                                            status.getDisplayName(),
//                                                            getStudy()
//                                                                            .getCoordinatingCenterStudyStatus()
//                                                                            .getDisplayName() });
//                }
//            }
//        }
//        return getStudy();
//    }

    /**
     * Activate.
     */
    public void activate() {
//        if (!(this.siteStudyStatus == SiteStudyStatus.AMENDMENT_PENDING
//                        || this.siteStudyStatus == SiteStudyStatus.PENDING || this.siteStudyStatus == SiteStudyStatus.APPROVED_FOR_ACTIVTION))
    	if (!(this.siteStudyStatus == SiteStudyStatus.AMENDMENT_PENDING || this.siteStudyStatus == SiteStudyStatus.PENDING
    			|| this.siteStudyStatus == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL || this.siteStudyStatus == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)){
            throw getC3PRExceptionHelper().getRuntimeException(
                            getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_CANNOT_SET_STATUS.CODE"),
                            new String[] { this.getSiteStudyStatus().getDisplayName() });
        }
        if (this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN) {
        	
        	Date currentDate = new Date();
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(currentDate);
            calendar.add(calendar.YEAR, -1);
            String allowedOldDate = "";
            String todayDate = "";

            try {
                allowedOldDate = DateUtil.formatDate(calendar.getTime(), "MM/dd/yyyy");
                todayDate = DateUtil.formatDate(currentDate, "MM/dd/yyyy");
            }
            catch (ParseException e) {
                throw getC3PRExceptionHelper().getRuntimeException(
                                getCode("C3PR.EXCEPTION.STUDYSITE.PARSING.DATE.CODE"),
                                new String[] { this.getHealthcareSite().getName() });
            }
 
            if (this.getIrbApprovalDate() == null) {
                throw getC3PRExceptionHelper()
                                .getRuntimeException(
                                                getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.IRB_APPROVAL_DATE.CODE"),
                                                new String[] { this.getHealthcareSite().getName() });
            }
            if (this.getIrbApprovalDate().after(currentDate)) {
                throw getC3PRExceptionHelper()
                                .getRuntimeException(
                                                getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.INVALID.IRB_APPROVAL_DATE.CODE"),
                                                new String[] { this.getHealthcareSite().getName(),
                                                        todayDate });
            }
            if (this.getIrbApprovalDate().before(calendar.getTime())) {
                throw getC3PRExceptionHelper()
                                .getRuntimeException(
                                                getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.EXPIRED.IRB_APPROVAL_DATE.CODE"),
                                                new String[] { this.getHealthcareSite().getName(),
                                                        allowedOldDate });
            }
            if ((this.getStartDate() == null) || (this.getStartDate().after(currentDate))) {
                throw getC3PRExceptionHelper()
                                .getRuntimeException(
                                                getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.INVALID.START_DATE.CODE"),
                                                new String[] { this.getHealthcareSite().getName() });
            }

            this.setSiteStudyStatus(SiteStudyStatus.ACTIVE);
            Study study = this.getStudy();
            if(!study.getCompanionIndicator()){
            	for(CompanionStudyAssociation companionStudyAssociation : study.getLatestStudyVersion().getCompanionStudyAssociations()){
            		for(StudySite studySite : companionStudyAssociation.getStudySites()){
            			if(studySite.getHealthcareSite().getPrimaryIdentifier() == this.getHealthcareSite().getPrimaryIdentifier()){
            				if(studySite.getSiteStudyStatus() != SiteStudyStatus.ACTIVE){
            					studySite.activate();
            				}
            			}
            		}
            	}
            }
        }
        else {
            throw getC3PRExceptionHelper()
                            .getRuntimeException(
                                            getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_CANNOT_BE_SET_WITH_CURRENT_COORDINATING_CENTER_STATUS.CODE"),
                                            new String[] {
                                                    SiteStudyStatus.ACTIVE.getDisplayName(),
                                                    this
                                                                    .getStudy()
                                                                    .getCoordinatingCenterStudyStatus()
                                                                    .getDisplayName() });

        }

    }

	/**
	 * Close to accrual.
	 * 
	 * @throws C3PRCodedRuntimeException the c3 pr coded runtime exception
	 */
	public void closeToAccrual() throws C3PRCodedRuntimeException {
        if (this.siteStudyStatus == SiteStudyStatus.CLOSED_TO_ACCRUAL) throw getC3PRExceptionHelper()
                    .getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_CLOSED_TO_ACCRUAL.CODE"));
        if (this.getSiteStudyStatus() == SiteStudyStatus.PENDING
                        || this.getSiteStudyStatus() == SiteStudyStatus.AMENDMENT_PENDING) throw getC3PRExceptionHelper()
                        .getRuntimeException(
                                        getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
                                        new String[] { SiteStudyStatus.CLOSED_TO_ACCRUAL
                                                        .getDisplayName() });
        this.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL);
    }

    /**
     * Close to accrual and treatment.
     */
    public void closeToAccrualAndTreatment() {
        if (this.siteStudyStatus == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) throw getC3PRExceptionHelper()
        .getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_CLOSED_TO_ACCRUAL_AND_TREATMENT.CODE"));
        if (this.getSiteStudyStatus() == SiteStudyStatus.PENDING
                        || this.getSiteStudyStatus() == SiteStudyStatus.AMENDMENT_PENDING
                        || this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL) throw getC3PRExceptionHelper()
                        .getRuntimeException(
                                        getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
                                        new String[] { SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT
                                                        .getDisplayName() });
        this.setSiteStudyStatus(SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT);
    }

    /**
     * Temporarily close to accrual and treatment.
     */
    public void temporarilyCloseToAccrualAndTreatment() {

        if (this.getSiteStudyStatus() == SiteStudyStatus.PENDING
                        || this.getSiteStudyStatus() == SiteStudyStatus.AMENDMENT_PENDING
                        || this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL
                        || this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
            throw getC3PRExceptionHelper()
                            .getRuntimeException(
                                            getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
                                            new String[] { SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT
                                                            .getDisplayName() });
        }
        this.setSiteStudyStatus(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT);
    }

    /**
     * Temporarily close to accrual.
     * 
     * @throws C3PRCodedRuntimeException the c3 pr coded runtime exception
     */
    public void temporarilyCloseToAccrual() throws C3PRCodedRuntimeException {

        if (this.getSiteStudyStatus() == SiteStudyStatus.PENDING
                        || this.getSiteStudyStatus() == SiteStudyStatus.AMENDMENT_PENDING
                        || this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL
                        || this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
            throw getC3PRExceptionHelper().getRuntimeException(
                            getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
                            new String[] { SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL
                                            .getDisplayName() });
        }
        this.setSiteStudyStatus(SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL);
    }

    /**
     * Pending amendment.
     */
    public void pendingAmendment() {
        this.setSiteStudyStatus(SiteStudyStatus.AMENDMENT_PENDING);
    }

//    public void approveForActivation() {
//        if (this.getStudy().getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.OPEN) {
//            throw getC3PRExceptionHelper().getRuntimeException(
//                            getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_STUDY_NOT_OPEN.CODE"));
//        }
//        if (!(this.siteStudyStatus == SiteStudyStatus.PENDING
//                        || this.siteStudyStatus == SiteStudyStatus.AMENDMENT_PENDING || this.siteStudyStatus == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL)) {
//            throw getC3PRExceptionHelper().getRuntimeException(
//                            getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_CANNOT_SET_STATUS.CODE"),
//                            new String[] { this.getSiteStudyStatus().getDisplayName() });
//        }
//        this.setSiteStudyStatus(SiteStudyStatus.APPROVED_FOR_ACTIVTION);
//        Study study = this.getStudy();
//        if(!study.getCompanionIndicator()){
//        	for(CompanionStudyAssociation companionStudyAssociation : study.getCompanionStudyAssociations()){
//        		for(StudySite studySite : companionStudyAssociation.getStudySites()){
//        			if(studySite.getHealthcareSite().getNciInstituteCode() == this.getHealthcareSite().getNciInstituteCode()){
//        				if(studySite.getSiteStudyStatus() != SiteStudyStatus.APPROVED_FOR_ACTIVTION || studySite.getSiteStudyStatus() != SiteStudyStatus.ACTIVE){
//        					studySite.approveForActivation();		
//        				}
//        			}
//        		}
//        	}
//        }
//    }

    /**
     * Gets the code.
     * 
     * @param errortypeString the errortype string
     * 
     * @return the code
     */
    @Transient
    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
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
     * Sets the exception helper.
     * 
     * @param c3PRExceptionHelper the new exception helper
     */
    public void setExceptionHelper(C3PRExceptionHelper c3PRExceptionHelper) {
        this.c3PRExceptionHelper = c3PRExceptionHelper;
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
     * Sets the site study status.
     * 
     * @param siteStudyStatus the new site study status
     */
    public void setSiteStudyStatus(SiteStudyStatus siteStudyStatus) {
        this.siteStudyStatus = siteStudyStatus;
    }

    /**
     * Builds the map for notification.
     * 
     * @return the map< object, object>
     */
    @SuppressWarnings("unused")
    @Transient
    /*
     * Used by the notifications use case to compose the email message by replacing the sub vars.
     */
    public Map<Object, Object> buildMapForNotification() {

        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_STATUS.toString(),
                        getSiteStudyStatus().getDisplayName() == null ? "status"
                                        : getSiteStudyStatus().getDisplayName());
        map
                        .put(NotificationEmailSubstitutionVariablesEnum.STUDY_ID.toString(),
                                        getHealthcareSite().getName() == null ? "site name"
                                                        : getHealthcareSite().getName().toString());
        map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE.toString(), getStudy()
                        .getShortTitleText() == null ? "Short Title" : getStudy()
                        .getShortTitleText().toString());
        
        map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_CURRENT_ACCRUAL.toString(), 
        		getStudy().getCurrentAccrualCount() == null ? "Study site current accrual" : getStudy().getCurrentAccrualCount().toString());
        map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD.toString(), 
        		getStudy().getTargetAccrualNumber() == null ? "Study site accrual threshold" : getStudy().getTargetAccrualNumber().toString());

        return map;
    }

    /**
     * Gets the possible transitions.
     * 
     * @return the possible transitions
     */
    @Transient
    public List<APIName> getPossibleTransitions(){
        List<APIName> possibleActions=new ArrayList<APIName>();
        List<SiteStudyStatus> statuses=new ArrayList<SiteStudyStatus>();
        if(this.coordinatingCenterStudyStatus!=this.getStudy().getCoordinatingCenterStudyStatus()){
            CoordinatingCenterStudyStatus studyCoordinatingCenterStudyStatus=this.getStudy().getCoordinatingCenterStudyStatus();
            if(studyCoordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.READY_TO_OPEN){
                if(this.coordinatingCenterStudyStatus!=CoordinatingCenterStudyStatus.PENDING){
                	throw getC3PRExceptionHelper().getRuntimeException(
                            getCode("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE"),
                            new String[] { this.getHealthcareSite().getName(), this.coordinatingCenterStudyStatus.getDisplayName(), studyCoordinatingCenterStudyStatus.getDisplayName()});
                }
            	possibleActions.add(APIName.CREATE_STUDY_DEFINITION);
                return possibleActions;
            }else if(studyCoordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.OPEN){
                if(this.coordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.PENDING){
                    possibleActions.add(APIName.CREATE_AND_OPEN_STUDY);
                    return possibleActions;
                }else if(this.coordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.READY_TO_OPEN){
                    possibleActions.add(APIName.OPEN_STUDY);
                    return possibleActions;
                }else{
                	throw getC3PRExceptionHelper().getRuntimeException(
                            getCode("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE"),
                            new String[] { this.getHealthcareSite().getName(), this.coordinatingCenterStudyStatus.getDisplayName(), studyCoordinatingCenterStudyStatus.getDisplayName()});
                }
            }else if(studyCoordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.AMENDMENT_PENDING){
                if(this.coordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.PENDING){
                    possibleActions.add(APIName.CREATE_AND_OPEN_STUDY);
                    return possibleActions;
                }else if(this.coordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.READY_TO_OPEN){
                	possibleActions.add(APIName.OPEN_STUDY);
                    return possibleActions;
                }else if(this.coordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.OPEN){
                    possibleActions.add(APIName.AMEND_STUDY);
                    return possibleActions;
                }else{
                	throw getC3PRExceptionHelper().getRuntimeException(
                            getCode("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE"),
                            new String[] { this.getHealthcareSite().getName(), this.coordinatingCenterStudyStatus.getDisplayName(), studyCoordinatingCenterStudyStatus.getDisplayName()});
                }
            }
//            else if(studyCoordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL){
//                if(this.coordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.PENDING){
//                    possibleActions.add(APIName.CREATE_STUDY_DEFINITION);
//                    possibleActions.add(APIName.OPEN_STUDY);
//                    return possibleActions;
//                }else if(this.coordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.READY_TO_OPEN){
//                    possibleActions.add(APIName.OPEN_STUDY);
//                    return possibleActions;
//                }else if(this.coordinatingCenterStudyStatus==CoordinatingCenterStudyStatus.AMENDMENT_PENDING){
//                    possibleActions.add(APIName.OPEN_STUDY);
//                    return possibleActions;
//                }else{
//                    possibleActions.add(APIName.CLOSE_STUDY_TO_ACCRUAL);
//                    return possibleActions;
//                }
//            }
        }
        if(this.getStudy().getCoordinatingCenterStudyStatus()!=CoordinatingCenterStudyStatus.OPEN)
            return possibleActions;
        if(this.siteStudyStatus==SiteStudyStatus.PENDING
        		|| this.siteStudyStatus==SiteStudyStatus.AMENDMENT_PENDING){
            //possibleActions.add(APIName.APPROVE_STUDY_SITE_FOR_ACTIVATION);
            possibleActions.add(APIName.ACTIVATE_STUDY_SITE);
            return possibleActions;
        }
//        else if(this.siteStudyStatus== SiteStudyStatus.APPROVED_FOR_ACTIVTION){
//            possibleActions.add(APIName.ACTIVATE_STUDY_SITE);
//            return possibleActions;
//        }
        else if(this.siteStudyStatus==SiteStudyStatus.ACTIVE){
            possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL);
            possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT);
            possibleActions.add(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL);
            possibleActions.add(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT);
            return possibleActions;
        }else if(this.siteStudyStatus==SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL || this.siteStudyStatus==SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT){
        	possibleActions.add(APIName.ACTIVATE_STUDY_SITE);
        	possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL);
            possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT);
            return possibleActions;
        }
        return possibleActions;
    }
    
    /**
     * Gets the coordinating center study status.
     * 
     * @return the coordinating center study status
     */
    @Column(name = "study_status")
    @Enumerated(EnumType.STRING)
    public CoordinatingCenterStudyStatus getCoordinatingCenterStudyStatus() {
        return coordinatingCenterStudyStatus;
    }

    /**
     * Sets the coordinating center study status.
     * 
     * @param coordinatingCenterStudyStatus the new coordinating center study status
     */
    public void setCoordinatingCenterStudyStatus(
                    CoordinatingCenterStudyStatus coordinatingCenterStudyStatus) {
        this.coordinatingCenterStudyStatus = coordinatingCenterStudyStatus;
    }
    
    /**
     * Gets the companion study association.
     * 
     * @return the companion study association
     */
    @ManyToOne
	@Cascade( { CascadeType.LOCK})
	@JoinColumn(name = "comp_assoc_id" , insertable=false, updatable=false)
	public CompanionStudyAssociation getCompanionStudyAssociation() {
		return companionStudyAssociation;
	}

	/**
	 * Sets the companion study association.
	 * 
	 * @param companionStudyAssociation the new companion study association
	 */
	public void setCompanionStudyAssociation(
			CompanionStudyAssociation companionStudyAssociation) {
		this.companionStudyAssociation = companionStudyAssociation;
	}
}
