/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.DateUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class SiteInvestigatorGroupAffiliation.
 */
@Entity
@Table(name = "org_inv_gr_affiliations")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "org_inv_gr_affiliations_id_seq") })
public class SiteInvestigatorGroupAffiliation extends AbstractMutableDeletableDomainObject
                implements Comparable<SiteInvestigatorGroupAffiliation> {
    
    /** The start date. */
    private Date startDate;

    /** The end date. */
    private Date endDate;

    /** The healthcare site investigator. */
    private HealthcareSiteInvestigator healthcareSiteInvestigator;

    /** The investigator group. */
    private InvestigatorGroup investigatorGroup;

    /**
     * Gets the investigator group.
     * 
     * @return the investigator group
     */
    @ManyToOne
    @JoinColumn(name = "ing_id")
    public InvestigatorGroup getInvestigatorGroup() {
        return investigatorGroup;
    }

    /**
     * Sets the investigator group.
     * 
     * @param investigatorGroup the new investigator group
     */
    public void setInvestigatorGroup(InvestigatorGroup investigatorGroup) {
        this.investigatorGroup = investigatorGroup;
    }

    /**
     * Gets the healthcare site investigator.
     * 
     * @return the healthcare site investigator
     */
    @ManyToOne
    @JoinColumn(name = "hsi_id")
    public HealthcareSiteInvestigator getHealthcareSiteInvestigator() {
        return healthcareSiteInvestigator;
    }

    /**
     * Sets the healthcare site investigator.
     * 
     * @param healthcareSiteInvestigator the new healthcare site investigator
     */
    public void setHealthcareSiteInvestigator(HealthcareSiteInvestigator healthcareSiteInvestigator) {
        this.healthcareSiteInvestigator = healthcareSiteInvestigator;
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
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Gets the end date str.
     * 
     * @return the end date str
     */
    @Transient
    public String getEndDateStr() {
        try {
            return DateUtil.formatDate(endDate, "MM/dd/yyyy");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME
                        * result
                        + ((healthcareSiteInvestigator == null) ? 0 : healthcareSiteInvestigator
                                        .hashCode());
        result = PRIME * result + ((investigatorGroup == null) ? 0 : investigatorGroup.hashCode());
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
        final SiteInvestigatorGroupAffiliation other = (SiteInvestigatorGroupAffiliation) obj;
        if (healthcareSiteInvestigator == null) {
            if (other.healthcareSiteInvestigator != null) return false;
        }
        else if (!healthcareSiteInvestigator.equals(other.healthcareSiteInvestigator)) return false;
        if (investigatorGroup == null) {
            if (other.investigatorGroup != null) return false;
        }
        else if (!investigatorGroup.equals(other.investigatorGroup)) return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(SiteInvestigatorGroupAffiliation o) {
        if (this.equals(o)) return 0;
        return 1;
    }

}
