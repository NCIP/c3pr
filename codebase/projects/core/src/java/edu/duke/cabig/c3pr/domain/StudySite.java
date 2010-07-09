package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import org.hibernate.annotations.Where;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.AmendmentType;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.NotificationEmailSubstitutionVariablesEnum;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.constants.SiteStudyStatus;
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.domain.factory.ParameterizedBiDirectionalInstantiateFactory;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.DateUtil;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * The Class StudySite.
 *
 *
 * @author Ram Chilukuri, Priyatam
 * @author kherm, himanshu
 */
@Entity
@DiscriminatorValue(value = "SST")
public class StudySite extends StudyOrganization implements Comparable<StudySite> {

    /** The companion study association. */
    private CompanionStudyAssociation companionStudyAssociation ;

    /** The target accrual number. */
    private Integer targetAccrualNumber;

    /** The c3pr exception helper. */
    private C3PRExceptionHelper c3PRExceptionHelper;

    /** The c3pr error messages. */
    private MessageSource c3prErrorMessages;

    /** The study site study version. */
    private StudySiteStudyVersion studySiteStudyVersion;

    /** The study site study versions. */
    private List<StudySiteStudyVersion> studySiteStudyVersions;

    /** The site study status. */
	private LazyListHelper lazyListHelper;


    /**
     * Instantiates a new study site.
     */
    public StudySite() {
    	lazyListHelper = new LazyListHelper();
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("error_messages_multisite");
        ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
        resourceBundleMessageSource1.setBasename("error_messages_c3pr");
        resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
        this.c3prErrorMessages = resourceBundleMessageSource1;
        this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
        studySiteStudyVersions= new ArrayList<StudySiteStudyVersion>();
        lazyListHelper.add(SiteStatusHistory.class,new ParameterizedBiDirectionalInstantiateFactory<SiteStatusHistory>(SiteStatusHistory.class, this));
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

    /**
     * Gets the study subjects.
     *
     * @return the study subjects
     */
    @Transient
    public List<StudySubject> getStudySubjects() {
    	Set<StudySubject> h = new HashSet<StudySubject>();
    	for (StudySiteStudyVersion studySiteStudyVersion: getStudySiteStudyVersions()){
    		for(StudySubjectStudyVersion studySubjectStudyVersion: studySiteStudyVersion.getStudySubjectStudyVersions()){
    			h.add(studySubjectStudyVersion.getStudySubject());
    		}
    	}
    	List<StudySubject> studySubjects= new ArrayList<StudySubject>();
    	studySubjects.addAll(h);
        return studySubjects;
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
     * Activate.
     */
    public void activate(Date effectiveDate) {
    	if (!(this.getSiteStudyStatus() == SiteStudyStatus.PENDING || this.getSiteStudyStatus() == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL || this.getSiteStudyStatus() == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)){
            	throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_CANNOT_SET_STATUS.CODE"),new String[] { this.getSiteStudyStatus().getDisplayName() });
        }
        if (this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.OPEN) {
        	 if(this.getSiteStudyStatus(effectiveDate) == SiteStudyStatus.PENDING) {
        		 StudySiteStudyVersion effectiveStudySiteStudyVersion = getStudySiteStudyVersion(effectiveDate);
        		 if(effectiveStudySiteStudyVersion != null){
        			 effectiveStudySiteStudyVersion.apply(effectiveDate);
        		 }else{
        			 throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.EFFECTIVE_DATE_NO_STUDY_SITE_STUDY_VERSION_FOUND.CODE"),
                             new String[] {CommonUtils.getDateString(effectiveDate) });
        		 }
        	 }
 			handleStudySiteStatusChange(effectiveDate, SiteStudyStatus.ACTIVE);
 			
//			 TODO companion study
//            Study study = this.getStudy();
//            if(!study.getCompanionIndicator()){
//            	for(CompanionStudyAssociation companionStudyAssociation : study.getStudyVersion().getCompanionStudyAssociations()){
//            		for(StudySite studySite : companionStudyAssociation.getStudySites()){
//            			if(studySite.getHealthcareSite().getPrimaryIdentifier() == this.getHealthcareSite().getPrimaryIdentifier()){
//            				if(studySite.getSiteStudyStatus() != SiteStudyStatus.ACTIVE){
//            					studySite.activate(effectiveDate);
//            				}
//            			}
//            		}
//            	}
//            }
        }
        else {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_CANNOT_BE_SET_WITH_CURRENT_COORDINATING_CENTER_STATUS.CODE"),
                                            new String[] {SiteStudyStatus.ACTIVE.getDisplayName(),this.getStudy().getCoordinatingCenterStudyStatus().getDisplayName() });
        }
    }
    
    public void applyStudyAmendment(String versionName, Date irbApprovalDate) {
    	StudySiteStudyVersion previousStudySiteStudyVersion = getLatestStudySiteStudyVersion();
    	if(previousStudySiteStudyVersion == null){
    		throw new RuntimeException();
    	}
    	StudySiteStudyVersion newStudySiteStudyVersion = new StudySiteStudyVersion();
    	StudyVersion studyVersion = getStudy().getStudyVersion(versionName);
    	newStudySiteStudyVersion.setStudyVersion(studyVersion);
    	newStudySiteStudyVersion.setIrbApprovalDate(irbApprovalDate);
    	this.addStudySiteStudyVersion(newStudySiteStudyVersion);
    	newStudySiteStudyVersion.apply(irbApprovalDate);
    	
		if(previousStudySiteStudyVersion.getEndDate() == null  || previousStudySiteStudyVersion.getEndDate().after(irbApprovalDate)){
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(irbApprovalDate);
			cal.add(Calendar.DATE, -1);
			previousStudySiteStudyVersion.setEndDate(cal.getTime());
		}
		
		
    }

	public void closeToAccrual(Date effectiveDate) throws C3PRCodedRuntimeException {
        if (this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL) {
        	throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_CLOSED_TO_ACCRUAL.CODE"),new String[] { SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT
                .getDisplayName() });
        }
        if (this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT){
        	throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.STUDY.ALREADY_CLOSED.CODE"),new String[] { SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT
                .getDisplayName() });
        }
        if (this.getSiteStudyStatus() == SiteStudyStatus.PENDING) {
        	throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),new String[] { SiteStudyStatus.CLOSED_TO_ACCRUAL.getDisplayName() });
        }
        handleStudySiteStatusChange(effectiveDate, SiteStudyStatus.CLOSED_TO_ACCRUAL) ;
    }
	
    public void closeToAccrualAndTreatment(Date effectiveDate) {
        if (this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT){
        	throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_CLOSED_TO_ACCRUAL_AND_TREATMENT.CODE"),new String[] { SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT
                .getDisplayName() });
        }
        if (this.getSiteStudyStatus() == SiteStudyStatus.PENDING ){
        	throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),new String[] { SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT
                                            .getDisplayName() });
        }
        handleStudySiteStatusChange(effectiveDate, SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) ;       
    }

    public void temporarilyCloseToAccrualAndTreatment(Date effectiveDate) {
    	if (this.getSiteStudyStatus() == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT) {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_TEMPORARY_CLOSED_TO_ACCRUAL_AND_TREATMENT.CODE"),new String[] { SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName() });
        }
        if (this.getSiteStudyStatus() == SiteStudyStatus.PENDING) {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),new String[] { SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName() });
        }
        if (this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL || this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
		    throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.STUDY.ALREADY_CLOSED.CODE"),new String[] { SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName() });
		}
        handleStudySiteStatusChange(effectiveDate, SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT) ;
    }

    public void temporarilyCloseToAccrual(Date effectiveDate) throws C3PRCodedRuntimeException {
    	if (this.getSiteStudyStatus() == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL) {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.STUDY.ALREADY.TEMPORARILY_CLOSED_TO_ACCRUAL.CODE"),new String[] { SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL.getDisplayName() });
        }
    	if (this.getSiteStudyStatus() == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT) {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDYSITE.STATUS_ALREADY_TEMPORARY_CLOSED_TO_ACCRUAL_AND_TREATMENT.CODE"),new String[] { SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName() });
        }
        if (this.getSiteStudyStatus() == SiteStudyStatus.PENDING) {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),new String[] { SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL.getDisplayName() });
        }
        if (this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL || this.getSiteStudyStatus() == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT) {
		    throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.SITE.STUDY.ALREADY_CLOSED.CODE"),new String[] { SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT.getDisplayName() });
		}
        handleStudySiteStatusChange(effectiveDate, SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL) ;
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
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    @Transient
    public C3PRExceptionHelper getC3PRExceptionHelper() {
        return c3PRExceptionHelper;
    }

    public void setExceptionHelper(C3PRExceptionHelper c3PRExceptionHelper) {
        this.c3PRExceptionHelper = c3PRExceptionHelper;
    }

    @Transient
    public MessageSource getC3prErrorMessages() {
        return c3prErrorMessages;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    @Transient
    /*
     * Used by the notifications use case to compose the email message by replacing the sub vars.
     */
    public Map<Object, Object> buildMapForNotification() {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_STATUS.toString(),getSiteStudyStatus().getDisplayName() == null ? "status": getSiteStudyStatus().getDisplayName());
        map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_ID.toString(),getHealthcareSite().getName() == null ? "site name": getHealthcareSite().getName().toString());
        map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SHORT_TITLE.toString(), getStudy().getShortTitleText() == null ? "Short Title" : getStudy().getShortTitleText().toString());
        map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_SITE_CURRENT_ACCRUAL.toString(),getStudy().getCurrentAccrualCount() == null ? "Study site current accrual" : getStudy().getCurrentAccrualCount().toString());
        map.put(NotificationEmailSubstitutionVariablesEnum.STUDY_ACCRUAL_THRESHOLD.toString(),getStudy().getTargetAccrualNumber() == null ? "Study site accrual threshold" : getStudy().getTargetAccrualNumber().toString());
        return map;
    }

    @Transient
    public List<APIName> getPossibleTransitions(){
        List<APIName> possibleActions=new ArrayList<APIName>();
        SiteStatusHistory siteHistory = getSiteStatusHistory(new Date());
        if(siteHistory != null && siteHistory.getEndDate() != null){
        	return possibleActions;
        }
        
        if(this.getStudy().getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.OPEN){
        	return possibleActions;
        }
        if(this.getSiteStudyStatus() == SiteStudyStatus.PENDING){
            possibleActions.add(APIName.ACTIVATE_STUDY_SITE);
            return possibleActions;
        }else if(this.getSiteStudyStatus()==SiteStudyStatus.ACTIVE){
            possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL);
            possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT);
            possibleActions.add(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL);
            possibleActions.add(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT);
            return possibleActions;
        }else if(this.getSiteStudyStatus()==SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL){
        	possibleActions.add(APIName.ACTIVATE_STUDY_SITE);
        	possibleActions.add(APIName.TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT);
        	possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL);
            possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT);
            return possibleActions;
        }else if(this.getSiteStudyStatus()==SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT){
        	possibleActions.add(APIName.ACTIVATE_STUDY_SITE);
        	possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL);
            possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT);
            return possibleActions;
        }else if(this.getSiteStudyStatus()==SiteStudyStatus.CLOSED_TO_ACCRUAL){
            possibleActions.add(APIName.CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT);
            return possibleActions;
        }
        return possibleActions;
    }

    @ManyToOne
	@Cascade( { CascadeType.LOCK})
	@JoinColumn(name = "comp_assoc_id" , insertable=false, updatable=false)
	public CompanionStudyAssociation getCompanionStudyAssociation() {
		return companionStudyAssociation;
	}

	public void setCompanionStudyAssociation(
			CompanionStudyAssociation companionStudyAssociation) {
		this.companionStudyAssociation = companionStudyAssociation;
	}

	@Transient
	public StudySiteStudyVersion getStudySiteStudyVersion(){
		if(studySiteStudyVersion == null){
			int size = getStudySiteStudyVersions().size();
			if(size == 0 ) {
				throw getC3PRExceptionHelper().getRuntimeException(
                        getCode("C3PR.EXCEPTION.STUDYSITE.CORRUPT.STATE.CODE"), new String[] { this.getHealthcareSite().getName(), this.getStudy().getCoordinatingCenterStudyStatus().getDisplayName()});
			}
			studySiteStudyVersion= getStudySiteStudyVersion(new Date());	
		}
		return studySiteStudyVersion;
	}
	
	public void setStudySiteStudyVersion(StudySiteStudyVersion studySiteStudyVersion) {
		this.studySiteStudyVersion = studySiteStudyVersion;
	}

	@Transient
	public StudySiteStudyVersion getLatestStudySiteStudyVersion(){
		TreeSet<StudySiteStudyVersion> studySiteStudyVersionSet = new TreeSet<StudySiteStudyVersion>();
		studySiteStudyVersionSet.addAll(getStudySiteStudyVersions());
		return studySiteStudyVersionSet.last();
	}

	/**
	 * Gets the study site study version for a given date.
	 * Due to amendments, participating sites can be on multiple version,
	 * however at a given date the site should only be using a single study version.
	 * Use this method to fetch the version applicable on a date.
	 *
	 * @param date the date
	 *
	 * @return the study site study version, null if no study version was active on the given date
	 */
	@Transient
	public StudySiteStudyVersion getStudySiteStudyVersion(Date date){
		for(StudySiteStudyVersion studySiteStudyVersion : getStudySiteStudyVersions()){
			if(studySiteStudyVersion.isValid(date)){
				return studySiteStudyVersion;
			}
		}
		return null;
	}
	
	public StudySiteStudyVersion getAccruingStudySiteStudyVersion(Date date){
		SiteStudyStatus status = getSiteStudyStatus(date);
		if(status == SiteStudyStatus.ACTIVE){
			return getStudySiteStudyVersion(date);
		}
		return null;
	}
	
	
	@Transient
	public StudySiteStudyVersion getLatestAccruingStudySiteStudyVersion(){
		return getAccruingStudySiteStudyVersion(new Date());
	}

	/**
	 * Gets the study study version for a given date.
	 * Due to amendments, participating sites can be on multiple version,
	 * however at a given date the site should only be using a single study version.
	 * Use this method to fetch the version applicable on a date.
	 *
	 * @param date the date
	 *
	 * @return the study version, null if no study version was active on the given date
	 */
	@Transient
	public StudyVersion getActiveStudyVersion(Date date){
		StudySiteStudyVersion studySiteStudyVersion = getStudySiteStudyVersion(date);
		if(studySiteStudyVersion != null){
			SiteStudyStatus status = getSiteStudyStatus(date);
			if(status == SiteStudyStatus.ACTIVE){
				return studySiteStudyVersion.getStudyVersion();
			}
		}
		return null;
	}

	/**
	 * Checks if study version setup is valid.
	 * This methods confirms if on a given date, the study version that the site is using
	 * is same as the one the study deems as latest.
	 * @param date the date
	 *
	 * throws RuntimeException:
	 * -if no study version is available for a given date
	 * -Code 347: if the study site does not have any study version while the study expects the
	 * 			  site to get the IRB approval for a study version
	 * -Code 348: if the study site study version does not match the study version of the study on the
	 * 			  given date. However the version does grant a grace period.
	 * -Code 349: if the study site study version does not match the study version of the study on the
	 * 			  given date. However the version is an optional amendment.
	 */
	public void isStudyVersionSetupValid(Date date){
		StudyVersion coCenterStudyVersion = getStudy().getStudyVersion(date);
		StudySiteStudyVersion studySiteStudyVersion = getStudySiteStudyVersion(date);
		if(coCenterStudyVersion == null){
			throw getC3PRExceptionHelper().getRuntimeException( getCode("C3PR.EXCEPTION.STUDYSITE.STUDYVERSION.NO_VERSION_FOUND.CODE"));
		}
		if(coCenterStudyVersion.getVersionStatus() == StatusType.IN){
			throw getC3PRExceptionHelper().getRuntimeException( getCode("C3PR.EXCEPTION.STUDYSITE.STUDYVERSION.PENDING.CODE"));
		}
		if(studySiteStudyVersion == null){
			throw getC3PRExceptionHelper().getRuntimeException( getCode("C3PR.EXCEPTION.STUDYSITE.STUDYVERSION.IMMEDIATE.CODE"));
		}
		if(coCenterStudyVersion == studySiteStudyVersion.getStudyVersion()){
			return;
		}
		if(coCenterStudyVersion.getAmendmentType() == AmendmentType.IMMEDIATE_AFTER_GRACE_PERIOD){
			long daysLeft = 0;
			if(studySiteStudyVersion.getEndDate() != null){
				daysLeft = (studySiteStudyVersion.getEndDate().getTime() - new Date().getTime()) / (1000*60*60*24);
			}
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDYSITE.STUDYVERSION.GRACE.CODE"),new String[]  {Long.toString(daysLeft)});
		}
		if(coCenterStudyVersion.getAmendmentType() == AmendmentType.OPTIONAL){
			throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDYSITE.STUDYVERSION.OPTIONAL.CODE"));
		}
	}

	/**
	 * Checks if the current study version setup is valid.
	 */
	public void isStudyVersionSetupValid(){
		if(getSiteStudyStatus() != SiteStudyStatus.PENDING){
			isStudyVersionSetupValid(new Date());
		}
	}

	/**
	 * Checks if study site can accrue a subject
	 * on a study version on a given date.
	 *
	 * @param studyVersion the study version
	 * @param date the date
	 *
	 * @return true, if is registerable
	 */
	public boolean canEnroll(StudyVersion studyVersion , Date date){
		StudySiteStudyVersion studySiteStudyVersion = getStudySiteStudyVersion(date);
		return studySiteStudyVersion == null ? false : (studySiteStudyVersion.getStudyVersion() == studyVersion);
	}

	@OneToMany(mappedBy = "studySite")
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudySiteStudyVersion> getStudySiteStudyVersions() {
		return studySiteStudyVersions;
	}
	
	@Transient
	public List<StudySiteStudyVersion> getSortedStudySiteStudyVersions() {
		List<StudySiteStudyVersion> sortedStudySiteStudyVersions =  new ArrayList<StudySiteStudyVersion>();
		sortedStudySiteStudyVersions.addAll(getStudySiteStudyVersions());
		Collections.sort(getStudySiteStudyVersions());
		return sortedStudySiteStudyVersions;
	}

	public void setStudySiteStudyVersions(List<StudySiteStudyVersion> studySiteStudyVersions) {
		this.studySiteStudyVersions = studySiteStudyVersions;
	}

	public void addStudySiteStudyVersion(StudySiteStudyVersion studySiteStudyVersion) {
		this.getStudySiteStudyVersions().add(studySiteStudyVersion);
		studySiteStudyVersion.setStudySite(this);
	}

	public void setup(Study study) {
		// this is the method where we will setup the study site for the  first time.
		super.setStudy(study);
		// 1. initially there is no study site study version, so we are creating one and associating it to study site.
		if(getStudySiteStudyVersions().size() == 0){
			studySiteStudyVersion = new StudySiteStudyVersion();
		     // 2. If we have active study version available , we are associating that study versin to study site study version otherwise we will associate latest available.
			StudyVersion studyVersion = study.getLatestActiveStudyVersion();
			if(studyVersion != null){
				studyVersion.addStudySiteStudyVersion(studySiteStudyVersion);
			}else{
				studyVersion = study.getStudyVersion();
				studyVersion.addStudySiteStudyVersion(studySiteStudyVersion);
			}
			//3. initializing startdate of study site study version to 100 years old so that for the first time, it is not invalid
	        studySiteStudyVersion.setStartDate(studyVersion.getVersionDate());
	        this.addStudySiteStudyVersion(studySiteStudyVersion);
		}
		if(getSiteStatusHistory().size() == 0){
			// 4. add default pending status to the study site
			createDefaultStudyStatusHistory();		
		}
	}
	
    public void setIrbApprovalDate(Date irbApprovalDate) {
        getStudySiteStudyVersion().setIrbApprovalDate(irbApprovalDate);
    }

    @Transient
    public Date getIrbApprovalDate() {
        return getStudySiteStudyVersion().getIrbApprovalDate();
    }

    public int compareTo(StudySite o) {
        if (this.equals(o)) return 0;
        else return 1;
    }

    @Transient
    public String getIrbApprovalDateStr() {
        return CommonUtils.getDateString(getIrbApprovalDate());
    }

	public void handleStudySiteStatusChange(Date effectiveDate, SiteStudyStatus status){
		SiteStatusHistory lastSiteStatusHistory = getLatestSiteStatusHistory();
		if( lastSiteStatusHistory != null){
        	if(lastSiteStatusHistory.getStartDate() == null){
        		throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.STATUS_HISTORY.NO.START_DATE.CODE"),new String[] {lastSiteStatusHistory.getSiteStudyStatus().getDisplayName()});
        	}else if(lastSiteStatusHistory.getStartDate() != null && !lastSiteStatusHistory.getStartDate().before(effectiveDate)){ 
        		throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.STATUS_HISTORY.INVALID.EFFECTIVE_DATE.CODE"),new String[] {CommonUtils.getDateString(lastSiteStatusHistory.getStartDate())});
        	}
        	if(lastSiteStatusHistory.getEndDate() != null){
        		// last history object should not have end date
        		throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.STATUS_HISTORY.END_DATE_PRESENT.CODE"),new String[] {lastSiteStatusHistory.getSiteStudyStatus().getDisplayName()});
        	}else{
        		 Date suggestedEndDate = effectiveDate;
                 GregorianCalendar calendar = new GregorianCalendar();
                 calendar.setTime(suggestedEndDate);
                 calendar.add(calendar.DATE, -1);
                 lastSiteStatusHistory.setEndDate(calendar.getTime());
        	}
        }
		createSiteStatusHistory(effectiveDate, status);
	}
	
	@Transient
    public SiteStudyStatus getSiteStudyStatus() {
       return getSiteStudyStatus(new Date());
    }
    
    @Transient
    public SiteStudyStatus getSiteStudyStatus(Date date) {
        SiteStatusHistory siteStatusHistory = getSiteStatusHistory(date);
        if(siteStatusHistory != null) {
        	return siteStatusHistory.getSiteStudyStatus();
        }else {
        	createDefaultStudyStatusHistory();
        	return SiteStudyStatus.PENDING;	
        }
    }
    
	@Transient
	public SiteStatusHistory getLatestSiteStatusHistory(){
		TreeSet<SiteStatusHistory> siteStatusHistorySet = new TreeSet<SiteStatusHistory>();
        siteStatusHistorySet.addAll(getSiteStatusHistory());
        if(siteStatusHistorySet.size() > 0){
        	return siteStatusHistorySet.last(); 
        }
        return null;
	}
	
	@Transient
	public SiteStatusHistory getSiteStatusHistory(Date date){
		Date newDate = DateUtil.getUtilDateFromString(DateUtil.formatDate(date, "MM/dd/yyyy"), "MM/dd/yyyy");
		List<SiteStatusHistory> siteStatusHistoryList =  new ArrayList<SiteStatusHistory>();
		siteStatusHistoryList.addAll(this.getSiteStatusHistory());
		Collections.sort(siteStatusHistoryList);
        for(SiteStatusHistory siteStatusHistory : siteStatusHistoryList){
        	Date startDate = siteStatusHistory.getStartDate();
        	Date endDate = siteStatusHistory.getEndDate();
        	if(!startDate.after(newDate) && (endDate == null ? true : !endDate.before(newDate))) {
        		return siteStatusHistory ;
        	}
        }
        return null ;
	}
	
	private void createDefaultStudyStatusHistory() {
    	Date currentDate = new Date();
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.add(calendar.YEAR, -100);
        
        SiteStatusHistory siteStatusHistory = new SiteStatusHistory();
    	siteStatusHistory.setStartDate(calendar.getTime());
    	siteStatusHistory.setSiteStudyStatus(SiteStudyStatus.PENDING);
    	this.addSiteStatusHistory(siteStatusHistory);
    }
	
	private void createSiteStatusHistory(Date startDate, SiteStudyStatus status) {
        SiteStatusHistory siteStatusHistory = new SiteStatusHistory();
    	siteStatusHistory.setStartDate(startDate);
    	siteStatusHistory.setSiteStudyStatus(status);
    	this.addSiteStatusHistory(siteStatusHistory);
	}
	
	@OneToMany(mappedBy = "studySite", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	@Where(clause = "retired_indicator  = 'false'")
	public List<SiteStatusHistory> getSiteStatusHistoryInternal() {
		return lazyListHelper.getInternalList(SiteStatusHistory.class);
	}

	public void setSiteStatusHistoryInternal(final List<SiteStatusHistory> siteStatusHistory) {
		lazyListHelper.setInternalList(SiteStatusHistory.class, siteStatusHistory);
	}

	@Transient
	public List<SiteStatusHistory> getSiteStatusHistory() {
		return lazyListHelper.getLazyList(SiteStatusHistory.class);
	}
	
	@Transient
	public List<SiteStatusHistory> getSortedSiteStatusHistory() {
		List<SiteStatusHistory> siteStatusHistoryList = new ArrayList<SiteStatusHistory>();
		siteStatusHistoryList.addAll(getSiteStatusHistory());
		Collections.sort(siteStatusHistoryList);
		return siteStatusHistoryList;
	}

	public void setSiteStatusHistory(List<SiteStatusHistory> siteStatusHistory) {
		setSiteStatusHistoryInternal(siteStatusHistory);
	}

	public void addSiteStatusHistory(SiteStatusHistory siteStatusHistory) {
		siteStatusHistory.setStudySite(this);
		getSiteStatusHistoryInternal().add(siteStatusHistory);
	}
	
	@Transient
	public SiteStatusHistory getNextPossibleSiteStatusHistory(){
		SiteStatusHistory siteHistory = getSiteStatusHistory(new Date());
		if(siteHistory.getEndDate() != null){
			List<SiteStatusHistory> listSiteStatusHistories = new ArrayList<SiteStatusHistory>();
			listSiteStatusHistories.addAll(getSiteStatusHistory());
			Collections.sort(listSiteStatusHistories);
			int index = listSiteStatusHistories.indexOf(siteHistory);
			if(listSiteStatusHistories.size() > index){
				return listSiteStatusHistories.get(index + 1);
			}
		}
		return null;
	}
	
	@Transient
	public StudySiteStudyVersion getCurrentStudySiteStudyVersion(){
		Date currentDate = new Date();
		StudySiteStudyVersion currentSiteStudyVersion= getStudySiteStudyVersion(currentDate);
		if(currentSiteStudyVersion == null){
			List<StudySiteStudyVersion> listStudySiteStudyVersion = getSortedStudySiteStudyVersions();
			return listStudySiteStudyVersion.get(listStudySiteStudyVersion.size() - 1);
		}
		return currentSiteStudyVersion;
	}
	
}