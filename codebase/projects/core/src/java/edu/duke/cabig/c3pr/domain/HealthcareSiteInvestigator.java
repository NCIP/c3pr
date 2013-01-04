/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;

/**
 * The Class HealthcareSiteInvestigator.
 * 
 * @author Priyatam
 */

@Entity
@Table(name = "hc_site_investigators")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "hc_site_investigators_id_seq") })
public class HealthcareSiteInvestigator extends AbstractMutableDeletableDomainObject {

    /** The healthcare site. */
    private HealthcareSite healthcareSite;

    /** The investigator. */
    private Investigator investigator;

    /** The status code. */
    private InvestigatorStatusCodeEnum statusCode = InvestigatorStatusCodeEnum.AC;

    /** The status date. */
    private Date statusDate;

    /** The study investigators. */
    private List<StudyInvestigator> studyInvestigators = new ArrayList<StudyInvestigator>();

    /** The site investigator group affiliations. */
    private List<SiteInvestigatorGroupAffiliation> siteInvestigatorGroupAffiliations = new ArrayList<SiteInvestigatorGroupAffiliation>();

    /**
     * Adds the study investigator.
     * 
     * @param si the si
     */
    public void addStudyInvestigator(StudyInvestigator si) {
        studyInvestigators.add(si);
        si.setSiteInvestigator(this);
    }

    /**
     * Removes the study investigator.
     * 
     * @param si the si
     */
    public void removeStudyInvestigator(StudyInvestigator si) {
        studyInvestigators.remove(si);
    }

    /**
     * Gets the investigator.
     * 
     * @return the investigator
     */
    @ManyToOne
    @JoinColumn(name = "inv_id")
    public Investigator getInvestigator() {
        return investigator;
    }

    /**
     * Gets the study investigators.
     * 
     * @return the study investigators
     */
    @OneToMany(mappedBy = "healthcareSiteInvestigator", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.LOCK })
    public List<StudyInvestigator> getStudyInvestigators() {
        return studyInvestigators;
    }

    /**
     * Sets the study investigators.
     * 
     * @param studyInvestigators the new study investigators
     */
    public void setStudyInvestigators(List<StudyInvestigator> studyInvestigators) {
        this.studyInvestigators = studyInvestigators;
    }

    /**
     * Sets the investigator.
     * 
     * @param investigator the new investigator
     */
    public void setInvestigator(Investigator investigator) {
        this.investigator = investigator;
    }

    /**
     * Gets the healthcare site.
     * 
     * @return the healthcare site
     */
    @ManyToOne
    @JoinColumn(name = "hcs_id")
    public HealthcareSite getHealthcareSite() {
        return healthcareSite;
    }

    /**
     * Sets the healthcare site.
     * 
     * @param healthcareSite the new healthcare site
     */
    public void setHealthcareSite(HealthcareSite healthcareSite) {
        this.healthcareSite = healthcareSite;
    }

    /**
     * Gets the status code.
     * 
     * @return the status code
     */
    @Enumerated(EnumType.STRING)
    public InvestigatorStatusCodeEnum getStatusCode() {
		return statusCode;
	}

	/**
	 * Sets the status code.
	 * 
	 * @param statusCode the new status code
	 */
	public void setStatusCode(InvestigatorStatusCodeEnum statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Gets the status date.
	 * 
	 * @return the status date
	 */
	public Date getStatusDate() {
        return statusDate;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((healthcareSite == null) ? 0 : healthcareSite.hashCode());
        result = PRIME * result + ((investigator == null) ? 0 : investigator.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final HealthcareSiteInvestigator other = (HealthcareSiteInvestigator) obj;
        if (healthcareSite == null) {
            if (other.healthcareSite != null) return false;
        }
        else if (!healthcareSite.equals(other.healthcareSite)) return false;
        if (investigator == null) {
            if (other.investigator != null) return false;
        }
        else if (!investigator.equals(other.investigator)) return false;
        return true;
    }

    /**
     * Sets the status date.
     * 
     * @param statusDate the new status date
     */
    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return investigator.getFullName();
    }

    /**
     * Gets the site investigator group affiliations.
     * 
     * @return the site investigator group affiliations
     */
    @OneToMany(mappedBy = "healthcareSiteInvestigator", fetch = FetchType.LAZY, orphanRemoval=true)
    @Cascade(value = { CascadeType.ALL})
    public List<SiteInvestigatorGroupAffiliation> getSiteInvestigatorGroupAffiliations() {
        return siteInvestigatorGroupAffiliations;
    }

    /**
     * Sets the site investigator group affiliations.
     * 
     * @param siteInvestigatorGroupAffiliations the new site investigator group affiliations
     */
    public void setSiteInvestigatorGroupAffiliations(
                    List<SiteInvestigatorGroupAffiliation> siteInvestigatorGroupAffiliations) {
        this.siteInvestigatorGroupAffiliations = siteInvestigatorGroupAffiliations;
    }

}
