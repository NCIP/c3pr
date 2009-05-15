package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

// TODO: Auto-generated Javadoc
/**
 * The Class StudyPersonnel.
 * 
 * @author Priyatam
 */
@Entity
@Table(name = "study_personnel")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "study_personnel_id_seq") })
public class StudyPersonnel extends AbstractMutableDeletableDomainObject implements
                Comparable<StudyPersonnel> {

    /** The research staff. */
    private ResearchStaff researchStaff;

    /** The study organization. */
    private StudyOrganization studyOrganization;

    /** The role code. */
    private String roleCode;

    /** The status code. */
    private String statusCode;

    /** The start date. */
    private Date startDate;

    /** The end date. */
    private Date endDate;

    /**
     * Gets the research staff.
     * 
     * @return the research staff
     */
    @ManyToOne
    @JoinColumn(name = "research_staff_id")
    @Cascade(value= {CascadeType.SAVE_UPDATE})
    public ResearchStaff getResearchStaff() {
        return researchStaff;
    }

    /**
     * Sets the research staff.
     * 
     * @param researchStaff the new research staff
     */
    public void setResearchStaff(ResearchStaff researchStaff) {
        this.researchStaff = researchStaff;
    }

    /**
     * Gets the study organization.
     * 
     * @return the study organization
     */
    @ManyToOne
    @JoinColumn(name = "sto_id")
    public StudyOrganization getStudyOrganization() {
        return studyOrganization;
    }

    /**
     * Sets the study organization.
     * 
     * @param studyOrganization the new study organization
     */
    public void setStudyOrganization(StudyOrganization studyOrganization) {
        this.studyOrganization = studyOrganization;
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
     * Gets the status code.
     * 
     * @return the status code
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the status code.
     * 
     * @param statusCode the new status code
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(StudyPersonnel o) {
        if (this.equals(o)) return 0;
        return 1;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((researchStaff == null) ? 0 : researchStaff.hashCode());
        result = PRIME * result + ((roleCode == null) ? 0 : roleCode.hashCode());
        result = PRIME * result + ((studyOrganization == null) ? 0 : studyOrganization.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        final StudyPersonnel other = (StudyPersonnel) obj;
        if (researchStaff == null) {
            if (other.researchStaff != null) return false;
        }
        else if (!researchStaff.equals(other.researchStaff)) return false;
        if (roleCode == null) {
            if (other.roleCode != null) return false;
        }
        else if (!roleCode.equals(other.roleCode)) return false;
        if (studyOrganization == null) {
            if (other.studyOrganization != null) return false;
        }
        else if (!studyOrganization.equals(other.studyOrganization)) return false;
        return true;
    }
}