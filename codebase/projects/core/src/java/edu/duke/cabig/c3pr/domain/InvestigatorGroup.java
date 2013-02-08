/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.domain;

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
import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class InvestigatorGroup.
 */
@Entity
@Table(name = "investigator_groups")
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "investigator_groups_id_seq") })
public class InvestigatorGroup extends AbstractMutableDeletableDomainObject implements
                Comparable<InvestigatorGroup> {

    /** The name. */
    private String name;

    /** The description text. */
    private String descriptionText;

    /** The start date. */
    private Date startDate;

    /** The end date. */
    private Date endDate;

    /** The healthcare site. */
    private HealthcareSite healthcareSite;

    /** The lazy list helper. */
    private LazyListHelper lazyListHelper;

    /**
     * Instantiates a new investigator group.
     */
    public InvestigatorGroup() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(SiteInvestigatorGroupAffiliation.class,
                        new BiDirectionalInstantiateFactory<SiteInvestigatorGroupAffiliation>(
                                        SiteInvestigatorGroupAffiliation.class, this));
    }

    /**
     * Gets the description text.
     * 
     * @return the description text
     */
    public String getDescriptionText() {
        return descriptionText;
    }

    /**
     * Sets the description text.
     * 
     * @param descriptionText the new description text
     */
    public void setDescriptionText(String descriptionText) {
        this.descriptionText = descriptionText;
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
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
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
     * Gets the site investigator group affiliations internal.
     * 
     * @return the site investigator group affiliations internal
     */
    @OneToMany(mappedBy = "investigatorGroup", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<SiteInvestigatorGroupAffiliation> getSiteInvestigatorGroupAffiliationsInternal() {
        return lazyListHelper.getInternalList(SiteInvestigatorGroupAffiliation.class);
    }

    /**
     * Sets the site investigator group affiliations internal.
     * 
     * @param siteInvestigatorGroupAffiliations the new site investigator group affiliations internal
     */
    public void setSiteInvestigatorGroupAffiliationsInternal(
                    List<SiteInvestigatorGroupAffiliation> siteInvestigatorGroupAffiliations) {
        lazyListHelper.setInternalList(SiteInvestigatorGroupAffiliation.class,
                        siteInvestigatorGroupAffiliations);
    }

    /**
     * Gets the site investigator group affiliations.
     * 
     * @return the site investigator group affiliations
     */
    @Transient
    public List<SiteInvestigatorGroupAffiliation> getSiteInvestigatorGroupAffiliations() {
        return lazyListHelper.getLazyList(SiteInvestigatorGroupAffiliation.class);
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
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
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
        final InvestigatorGroup other = (InvestigatorGroup) obj;
        if (name == null) {
            if (other.name != null) return false;
        }
        else if (!name.equals(other.name)) return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(InvestigatorGroup o) {
        if (this.equals(o)) return 0;
        return 1;
    }

}
