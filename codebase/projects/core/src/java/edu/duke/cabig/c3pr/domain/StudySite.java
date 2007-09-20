package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import edu.duke.cabig.c3pr.utils.DateUtil;
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * @author Ram Chilukuri, Priyatam
 * @author kherm
 *
 */
@Entity
@DiscriminatorValue(value = "SST")
// @Table (name = "study_sites")
//@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "STUDY_SITES_ID_SEQ") })
public class StudySite extends StudyOrganization implements
		Comparable<StudySite> {
//    private HealthcareSite site;
//    private Study study;
    private Date irbApprovalDate = Calendar.getInstance().getTime();
    private String roleCode;
 // private String statusCode;
    private SiteStudyStatus siteStudyStatus;
    private Date startDate = Calendar.getInstance().getTime();
    private Date endDate;
    private String irbApprovalDateStr;
    private String startDateStr;

    private List<StudySubject> studySubjects = new ArrayList<StudySubject>();

    private LazyListHelper lazyListHelper;


    public StudySite() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(StudyInvestigator.class, new BiDirectionalInstantiateFactory<StudyInvestigator>(StudyInvestigator.class,this));
        lazyListHelper.add(StudyPersonnel.class, new BiDirectionalInstantiateFactory<StudyPersonnel>(StudyPersonnel.class,this));

    }

	public void addStudyPersonnel(StudyPersonnel studyPersonnel) {
		getStudyPersonnel().add(studyPersonnel);
		studyPersonnel.setStudySite(this);
	}

	public void addStudyInvestigator(StudyInvestigator studyInvestigator) {
		getStudyInvestigators().add(studyInvestigator);
		studyInvestigator.setStudySite(this);
	}

    public void addStudySubject(StudySubject spAssignments)
    {
        studySubjects.add(spAssignments);
        studySubjects.size();
    }

    public void removeStudySubject(StudySubject studySubject){
        studySubjects.remove(studySubject);
    }


    /** Are there any assignments using this relationship? */
    @Transient
    public boolean isUsed() {
        return getStudySubjects().size() > 0;
    }

    /// BEAN PROPERTIES

  /*  public void setSite(HealthcareSite site) {
        this.site = site;
    }*/

  /*  @ManyToOne
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "hcs_id", nullable=false)
    public HealthcareSite getSite() {
        return site;
    }*/

   /* public void setStudy(Study study) {
        this.study = study;
    }
*/
   /* @ManyToOne
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "study_id", nullable=false)
    public Study getStudy() {
        return study;
    }*/

    public void setStudySubjects(List<StudySubject> studySubjects) {
        this.studySubjects = studySubjects;
    }

    @OneToMany (mappedBy = "studySite", fetch=FetchType.LAZY)
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudySubject> getStudySubjects() {
        return studySubjects;
    }

    @OneToMany (mappedBy = "studySite")
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudyInvestigator> getStudyInvestigatorsInternal(){
        return lazyListHelper.getInternalList(StudyInvestigator.class);
    }

    public void setStudyInvestigatorsInternal(List<StudyInvestigator> studyInvestigators){
        lazyListHelper.setInternalList(StudyInvestigator.class, studyInvestigators);
    }


    @Transient
    public List<StudyInvestigator> getStudyInvestigators() {
        return lazyListHelper.getLazyList(StudyInvestigator.class);
    }


    public void setStudyInvestigators(List<StudyInvestigator> studyInvestigators) {
        lazyListHelper.setInternalList(StudyInvestigator.class,studyInvestigators);
    }

    @OneToMany (mappedBy = "studySite")
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudyPersonnel> getStudyPersonnelInternal() {
        return lazyListHelper.getInternalList(StudyPersonnel.class);
    }

    public void setStudyPersonnelInternal(List<StudyPersonnel> studyPersonnel) {
        lazyListHelper.setInternalList(StudyPersonnel.class, studyPersonnel);
    }

    @Transient
    public List<StudyPersonnel> getStudyPersonnel() {
        return lazyListHelper.getLazyList(StudyPersonnel.class);
    }

    public void setStudyPersonnel(List<StudyPersonnel> studyPersonnel) {
       lazyListHelper.setInternalList(StudyPersonnel.class,studyPersonnel);
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

   /* public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }*/

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
        // TODO Auto-generated method stub
        return 0;
    }

    @Transient
    public String getIrbApprovalDateStr() {
        try {
            return DateUtil.formatDate(irbApprovalDate, "MM/dd/yyyy");
        }
        catch(Exception e){
            //do nothing
        }
        return null;
    }

    @Transient
    public String getStartDateStr() {
        try {
            return DateUtil.formatDate(startDate, "MM/dd/yyyy");
        }
        catch(Exception e){
            //do nothing
        }
        return "";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StudySite)) return false;
        final StudySite studySite = (StudySite) obj;
        Study study = studySite.getStudy();
        HealthcareSite site = studySite.getHealthcareSite();
        if (!getHealthcareSite().equals(site)) return false;
        return true;
    }

    public int hashCode() {
        int result;
        result = (getHealthcareSite() != null ? getHealthcareSite().hashCode() : 0);
        result = 29 * result + (studySubjects != null ? studySubjects.hashCode() : 0);
        return result;
    }
    
    @Enumerated(EnumType.STRING)
	public SiteStudyStatus getSiteStudyStatus() {
		return siteStudyStatus;
	}

	public void setSiteStudyStatus(SiteStudyStatus siteStudyStatus) {
		this.siteStudyStatus = siteStudyStatus;
	}

}