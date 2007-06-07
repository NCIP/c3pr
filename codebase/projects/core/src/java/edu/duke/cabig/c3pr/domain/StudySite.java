package edu.duke.cabig.c3pr.domain;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.DateUtil;

/**
 * @author Ram Chilukuri, Priyatam
 * 
 */
@Entity
@Table (name = "study_sites")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="STUDY_SITES_ID_SEQ")
    }
)
public class StudySite extends AbstractGridIdentifiableDomainObject implements Comparable<StudySite>{
    private HealthcareSite site;
    private Study study;
    private Date irbApprovalDate = Calendar.getInstance().getTime();
    private String roleCode;
    private String statusCode;
    private Date startDate = Calendar.getInstance().getTime();
    private Date endDate;   
    private String irbApprovalDateStr;
    private String startDateStr;
    
    private List<StudyParticipantAssignment> studyParticipantAssignments 
    	= new ArrayList<StudyParticipantAssignment>();    
    private List<StudyInvestigator> studyInvestigators = new ArrayList<StudyInvestigator>();
    private List<StudyPersonnel> studyPersonnels = new ArrayList<StudyPersonnel>();
  

    /// LOGIC

    public void addStudyPersonnel(StudyPersonnel studyPersonnel) {
        studyPersonnels.add(studyPersonnel);
        studyPersonnel.setStudySite(this);
    }
    
    public void addStudyInvestigator(StudyInvestigator studyInvestigator) {
        getStudyInvestigators().add(studyInvestigator);
        studyInvestigator.setStudySite(this);
    }
    
    public void addstudyParticipantAssignment(StudyParticipantAssignment spAssignments)
    {
    	studyParticipantAssignments.add(spAssignments);
    	spAssignments.setStudySite(this);
    }
	
    public void removeStudyParticipantAssignment(StudyParticipantAssignment studyParticipantAssignment){
    	studyParticipantAssignments.remove(studyParticipantAssignment);
    }

    
	/** Are there any assignments using this relationship? */
    @Transient
    public boolean isUsed() {
        return getStudyParticipantAssignments().size() > 0;
    }

    /// BEAN PROPERTIES

    public void setSite(HealthcareSite site) {
        this.site = site;
    }

    @ManyToOne 
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })    
    @JoinColumn(name = "hcs_id", nullable=false)    
    public HealthcareSite getSite() {
        return site;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    @ManyToOne
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })        
    @JoinColumn(name = "study_id", nullable=false)    
    public Study getStudy() {
        return study;
    }

    public void setStudyParticipantAssignments(List<StudyParticipantAssignment> studyParticipantAssignments) {
        this.studyParticipantAssignments = studyParticipantAssignments;
    }

    @OneToMany (mappedBy = "studySite", fetch=FetchType.LAZY)
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudyParticipantAssignment> getStudyParticipantAssignments() {
        return studyParticipantAssignments;
    }
    
    @OneToMany (mappedBy = "studySite")
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<StudyInvestigator> getStudyInvestigators() {
		return studyInvestigators;
	}

	public void setStudyInvestigators(List<StudyInvestigator> studyInvestigators) {
		this.studyInvestigators = studyInvestigators;
	}	
	
	@OneToMany (mappedBy = "studySite")
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })    
	public List<StudyPersonnel> getStudyPersonnels() {
		return studyPersonnels;
	}

	public void setStudyPersonnels(List<StudyPersonnel> studyPersonnels) {
		this.studyPersonnels = studyPersonnels;
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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Transient
	public String getIrbApprovalDateStr() {
		try {
			return DateUtil.formatDate(irbApprovalDate, "MM/dd/yyyy");
		}
		catch(ParseException e){
			//do nothing
		}
		return null;
	}
	
	@Transient
	public String getStartDateStr() {
		try {
			return DateUtil.formatDate(startDate, "MM/dd/yyyy");
		}
		catch(ParseException e){
			//do nothing
		}
		return "";
	}
	
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof StudySite)) return false;
        final StudySite studySite = (StudySite) obj;
        Study study = studySite.getStudy();
        HealthcareSite site = studySite.getSite();
        if (!getSite().equals(site)) return false;
        return true;
    }

    public int hashCode() {
        int result;
        result = (site != null ? site.hashCode() : 0);
        result = 29 * result + (studyParticipantAssignments != null ? studyParticipantAssignments.hashCode() : 0);
        return result;
    }
    
}