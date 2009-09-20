package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.CommonUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class StudySiteStudyVersion.
 */
@Entity
@Table(name = "study_site_versions")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_SITE_VERSIONS_ID_SEQ") })
public class StudySiteStudyVersion extends AbstractMutableDeletableDomainObject implements Comparable<StudySiteStudyVersion>{

	private Date irbApprovalDate;
	private Date startDate;
	private Date endDate;
	private List<StudySubjectStudyVersion> studySubjectStudyVersions = new ArrayList<StudySubjectStudyVersion>();
	private C3PRExceptionHelper c3PRExceptionHelper;
    private MessageSource c3prErrorMessages;
    private StudySite studySite;
	private StudyVersion studyVersion ;

	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@ManyToOne
    @JoinColumn(name = "sto_id", nullable=false)
    @Cascade( { CascadeType.LOCK})
	public StudySite getStudySite() {
		return studySite;
	}

	public void setStudySite(StudySite studySite) {
		this.studySite = studySite;
	}

	@ManyToOne
    @JoinColumn(name = "stu_version_id")
    @Cascade( { CascadeType.LOCK})
	public StudyVersion getStudyVersion() {
		return studyVersion;
	}

	public void setStudyVersion(StudyVersion studyVersion) {
		this.studyVersion = studyVersion;
	}

	public Date getIrbApprovalDate() {
		return irbApprovalDate;
	}
	
	@Transient
	public String getIrbApprovalDateStr() {
		return CommonUtils.getDateString(irbApprovalDate);
	}

	public void setIrbApprovalDate(Date irbApprovalDate) {
		this.irbApprovalDate = irbApprovalDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@OneToMany(mappedBy = "studySiteStudyVersion")
	@Cascade(value = { CascadeType.LOCK, CascadeType.DELETE_ORPHAN })
	public List<StudySubjectStudyVersion> getStudySubjectStudyVersions() {
		return studySubjectStudyVersions;
	}

	public void setStudySubjectStudyVersions(List<StudySubjectStudyVersion> studySubjectStudyVersions) {
		this.studySubjectStudyVersions = studySubjectStudyVersions;
	}

	public void addStudySubjectStudyVersion(StudySubjectStudyVersion studySubjectStudyVersion) {
		this.getStudySubjectStudyVersions().add(studySubjectStudyVersion);
		studySubjectStudyVersion.setStudySiteStudyVersion(this);
	}

	/**
	 * Checks if the study version of the study site is valid for a given date.
	 * The study version is valid for a site between the start date and the end date
	 * of the site's IRB approval date for the version.
	 *
	 * @param date the date
	 *
	 * @return true, if is valid
	 */
	@Transient
	public boolean isValid(Date date){
		return (startDate == null ? false : !startDate.after(date)) && (endDate == null  ? true : !endDate.before(date));
	}

	public int compareTo(StudySiteStudyVersion studySiteStudyVersion) {
		if(this.irbApprovalDate == null && studySiteStudyVersion.getIrbApprovalDate() == null){
    		return 0;
    	}else if(this.irbApprovalDate == null && studySiteStudyVersion.getIrbApprovalDate() != null){
    		return 1;
    	}else if(this.irbApprovalDate != null && studySiteStudyVersion.getIrbApprovalDate() == null){
    		return -1;
    	}else{
    		return this.irbApprovalDate.compareTo(studySiteStudyVersion.getIrbApprovalDate());
    	}
	}
	
	private void validateIRBApprovalDate(){
		String allowedOldDate = "";
        String todayDate = "";
        
		Date currentDate = new Date();
		Date versionDate = studyVersion.getVersionDate();
		Date oldestAllowableIRBApprovalDate;

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(currentDate);
		calendar.add(calendar.YEAR, -1);
		Date oneYearOldDate = calendar.getTime();

		if (versionDate.before(oneYearOldDate)) {
			oldestAllowableIRBApprovalDate = oneYearOldDate;
		} else {
			oldestAllowableIRBApprovalDate = versionDate;
		}
		
        allowedOldDate = CommonUtils.getDateString(oldestAllowableIRBApprovalDate);
        todayDate = CommonUtils.getDateString(currentDate);
		
		if (this.getIrbApprovalDate() == null) {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.IRB_APPROVAL_DATE.CODE"), new String[] { studySite.getHealthcareSite().getName() });
	    }
	    
	    if (this.getIrbApprovalDate().after(currentDate)) {
	            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.INVALID.IRB_APPROVAL_DATE.CODE"),new String[] {studySite.getHealthcareSite().getName(),todayDate });
	    }
	    if (this.getIrbApprovalDate().before(oldestAllowableIRBApprovalDate)) {
	            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.EXPIRED.IRB_APPROVAL_DATE.CODE"),new String[] {studySite.getHealthcareSite().getName(),allowedOldDate });
	    }
	}
	
	private void validateStartDate(Date date) {
		Date allowedStartDate = studyVersion.getVersionDate();
		String allowedStartDateStr =  CommonUtils.getDateString(allowedStartDate);
        if (date == null) {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.START_DATE.CODE"), new String[] { studySite.getHealthcareSite().getName() });
        }
        if (date.before(allowedStartDate)) {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.INVALID.START_DATE.CODE"),new String[] {studySite.getHealthcareSite().getName(),allowedStartDateStr });
        }
        if (date.before(irbApprovalDate)) {
            throw getC3PRExceptionHelper().getRuntimeException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.START_DATE.BEFORE.IRB_APPROVAL_DATE.CODE"),new String[] {studySite.getHealthcareSite().getName(),getIrbApprovalDateStr() });
        }
	}

	public void apply(Date startDate) {
		validateIRBApprovalDate();
		validateStartDate(startDate);
        this.setStartDate(startDate);
	}
	
	@Transient
	public Date getAllowedOldDateForStartDate() {
		return studyVersion.getVersionDate();
	}
	
	@Transient
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}

	public void setC3PRExceptionHelper(C3PRExceptionHelper c3prExceptionHelper) {
		c3PRExceptionHelper = c3prExceptionHelper;
	}
	
	@Transient
	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource c3prErrorMessages) {
		this.c3prErrorMessages = c3prErrorMessages;
	}
	
	@Transient
    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }


}
