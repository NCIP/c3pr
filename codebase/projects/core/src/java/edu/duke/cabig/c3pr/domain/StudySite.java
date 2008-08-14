package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.DateUtil;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * @author Ram Chilukuri, Priyatam
 * @author kherm
 * 
 */
@Entity
@DiscriminatorValue(value = "SST")
public class StudySite extends StudyOrganization implements
		Comparable<StudySite> {
	private Date irbApprovalDate = Calendar.getInstance().getTime();

	private String roleCode;

	public StudySite() {

		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
	}

	private Integer targetAccrualNumber;

	private SiteStudyStatus siteStudyStatus = SiteStudyStatus.PENDING;

	private Date startDate = Calendar.getInstance().getTime();

	private Date endDate;

	private String irbApprovalDateStr;

	private String startDateStr;

	private List<StudySubject> studySubjects = new ArrayList<StudySubject>();

	private LazyListHelper lazyListHelper;
	
	private C3PRExceptionHelper c3PRExceptionHelper;
	
	private MessageSource c3prErrorMessages;

	public void addStudySubject(StudySubject spAssignments) {
		studySubjects.add(spAssignments);
		studySubjects.size();
	}

	public void removeStudySubject(StudySubject studySubject) {
		studySubjects.remove(studySubject);
	}

	/** Are there any assignments using this relationship? */
	@Transient
	public boolean isUsed() {
		return getStudySubjects().size() > 0;
	}

	// / BEAN PROPERTIES

	public void setStudySubjects(List<StudySubject> studySubjects) {
		this.studySubjects = studySubjects;
	}

	@OneToMany(mappedBy = "studySite", fetch = FetchType.LAZY)
	@Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudySubject> getStudySubjects() {
		return studySubjects;
	}

	public void setIrbApprovalDate(Date irbApprovalDate) {
		this.irbApprovalDate = irbApprovalDate;
	}

	public Date getIrbApprovalDate() {
		return irbApprovalDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int compareTo(StudySite o) {
		if (this.equals(o))
			return 0;
		else
			return 1;
	}

	@Transient
	public String getIrbApprovalDateStr() {
		try {
			return DateUtil.formatDate(irbApprovalDate, "MM/dd/yyyy");
		} catch (Exception e) {
			// do nothing
		}
		return null;
	}

	@Transient
	public String getStartDateStr() {
		try {
			return DateUtil.formatDate(startDate, "MM/dd/yyyy");
		} catch (Exception e) {
			// do nothing
		}
		return "";
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final StudySite other = (StudySite) obj;
		return true;
	}

	@Enumerated(EnumType.STRING)
	public SiteStudyStatus getSiteStudyStatus() {
		return siteStudyStatus;
	}

	public Integer getTargetAccrualNumber() {
		return targetAccrualNumber;
	}

	public void setTargetAccrualNumber(Integer targetAccrualNumber) {
		this.targetAccrualNumber = targetAccrualNumber;
	}

	@Transient
	public int getCurrentAccrualCount() {
		int count = 0;
		for (StudySubject s : this.getStudySubjects()) {
			if (s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED
					|| s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED)
				count++;
		}
		return count;
	}

	public SiteStudyStatus evaluateSiteStudyStatus()
			throws C3PRCodedException {

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

		if ((this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.AMENDMENT_PENDING)
				&& (this.getSiteStudyStatus() == SiteStudyStatus.ACTIVE)) {
			return SiteStudyStatus.AMENDMENT_PENDING;
		}
		if (this.getStudy().getCoordinatingCenterStudyStatus() == CoordinatingCenterStudyStatus.ACTIVE) {

			Date currentDate = new Date();
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(currentDate);
			calendar.add(calendar.YEAR, -1);
			String allowedOldDate = "" ;
			String todayDate = "" ;
			
				try {
					allowedOldDate  = DateUtil.formatDate(calendar.getTime(), "MM/dd/yyyy");
					todayDate  = DateUtil.formatDate(currentDate, "MM/dd/yyyy");
				} catch (ParseException e) {
					throw getC3PRExceptionHelper().getException(getCode("C3PR.EXCEPTION.STUDYSITE.PARSING.DATE.CODE"));
				}
				
			if (this.getIrbApprovalDate() == null ) {
				if( this.getId() != null ){
					throw getC3PRExceptionHelper().getException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.IRB_APPROVAL_DATE.CODE"),
							new String[] { this.getHealthcareSite().getName() });
				}
				return SiteStudyStatus.PENDING;
			}
			if ( this.getIrbApprovalDate().after(currentDate)){
				if ((this.getId() != null)) {
					throw getC3PRExceptionHelper().getException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.INVALID.IRB_APPROVAL_DATE.CODE"),
									new String[] { this.getHealthcareSite().getName(), todayDate});
				}
				return SiteStudyStatus.PENDING;
			}
			if (this.getIrbApprovalDate().before(calendar.getTime())){
				if ((this.getId() != null)) {
					throw getC3PRExceptionHelper().getException(getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.EXPIRED.IRB_APPROVAL_DATE.CODE"),
									new String[] { this.getHealthcareSite().getName(), allowedOldDate });
				}
				return SiteStudyStatus.PENDING;
			}
			if ((this.getStartDate() == null)
					|| (this.getStartDate().after(currentDate))) {
				if ((this.getId() != null)) {
					throw getC3PRExceptionHelper()
							.getException(
									getCode("C3PR.EXCEPTION.STUDY.STUDYSITE.MISSING.INVALID.START_DATE.CODE"),
									new String[] { this
											.getHealthcareSite().getName() });
				}
				return SiteStudyStatus.PENDING;
			}
			return SiteStudyStatus.ACTIVE;
		}

		return SiteStudyStatus.PENDING;
	}

	public Study setWorkFlowSiteStudyStatus(SiteStudyStatus status) throws C3PRCodedException, ParseException {
		SiteStudyStatus currentSiteStatus = this.getSiteStudyStatus();
		if ((status == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL)
				|| (status == SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
			if (((currentSiteStatus) == (SiteStudyStatus.ACTIVE))
					|| ((currentSiteStatus) == (SiteStudyStatus.TEMPORARILY_CLOSED_TO_ACCRUAL))) {
				this.setSiteStudyStatus(status);
			}
		} else if ((status == SiteStudyStatus.CLOSED_TO_ACCRUAL)
				|| (status == SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT)) {
			if (((currentSiteStatus) == (SiteStudyStatus.PENDING))
					|| ((currentSiteStatus) == (SiteStudyStatus.AMENDMENT_PENDING))
					|| ((currentSiteStatus) == (SiteStudyStatus.CLOSED_TO_ACCRUAL_AND_TREATMENT))) {
				if ((this.getId() != null)) {
					throw getC3PRExceptionHelper()
							.getException(
									getCode("C3PR.EXCEPTION.SITE.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
									new String[] { status.getDisplayName() });
				}
				return getStudy();
			} else
				this.setSiteStudyStatus(status);
		} else {
			if (status == evaluateSiteStudyStatus()) {
				this.setSiteStudyStatus(status);
			} else {
				if ((getStudy().getId() != null)) {
					throw getC3PRExceptionHelper()
							.getException(
									getCode("C3PR.EXCEPTION.STUDY.STATUS_NEEDS_TO_BE_ACTIVE_FIRST.CODE"),
									new String[] {
											status.getDisplayName(),
											getStudy()
													.getCoordinatingCenterStudyStatus()
													.getDisplayName() });
				}
			}
		}
		return getStudy();
	}
	
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
	
	 public void setSiteStudyStatus(SiteStudyStatus siteStudyStatus) {
	        this.siteStudyStatus = siteStudyStatus;
	 }
}