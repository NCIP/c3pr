/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;

/**
 * @author Priyatam
 */
@Entity
@Table(name = "study_investigators")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "study_investigators_id_seq") })
public class StudyInvestigator extends AbstractMutableDeletableDomainObject implements
                Comparable<StudyInvestigator> {

	/* Role Code value for PI */
	public static final String PRINCIPAL_INVESTIGATOR = "Principal Investigator";
	
	/* Role Code value for Site Inv */
	public static final String SITE_INVESTIGATOR = "Site Investigator";
	
    private HealthcareSiteInvestigator healthcareSiteInvestigator;

    private StudyOrganization studyOrganization;

    /** The role code.  Make this an enum eventually.*/
    private String roleCode;

    private InvestigatorStatusCodeEnum statusCode;

    private Date startDate;

    private Date endDate;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Enumerated(EnumType.STRING)
    public InvestigatorStatusCodeEnum getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(InvestigatorStatusCodeEnum statusCode) {
		this.statusCode = statusCode;
	}

	@ManyToOne
    @JoinColumn(name = "hsi_id")
    public HealthcareSiteInvestigator getHealthcareSiteInvestigator() {
        return healthcareSiteInvestigator;
    }

    public void setHealthcareSiteInvestigator(HealthcareSiteInvestigator healthcareSiteInvestigator) {
        this.healthcareSiteInvestigator = healthcareSiteInvestigator;
    }

    public void setSiteInvestigator(HealthcareSiteInvestigator healthcareSiteInvestigator) {
        this.healthcareSiteInvestigator = healthcareSiteInvestigator;
    }

    @ManyToOne
    @JoinColumn(name = "sto_id")
    public StudyOrganization getStudyOrganization() {
        return studyOrganization;
    }

    public void setStudyOrganization(StudyOrganization studyOrganization) {
        this.studyOrganization = studyOrganization;
    }

    public int compareTo(StudyInvestigator o) {
        if (this.equals(o)) return 0;
        return 1;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME
                        * result
                        + ((healthcareSiteInvestigator == null) ? 0 : healthcareSiteInvestigator
                                        .hashCode());
        result = PRIME * result + ((roleCode == null) ? 0 : roleCode.hashCode());
        result = PRIME * result + ((studyOrganization == null) ? 0 : studyOrganization.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final StudyInvestigator other = (StudyInvestigator) obj;
        if (healthcareSiteInvestigator == null) {
            if (other.healthcareSiteInvestigator != null) return false;
        }
        else if (!healthcareSiteInvestigator.equals(other.healthcareSiteInvestigator)) return false;
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
