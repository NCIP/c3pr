package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

// TODO: Auto-generated Javadoc
/**
 * The Class HealthcareSite.
 * 
 * @author Priyatam
 * @author Kulasekaran
 * 
 * Currently points to the newly renamed organizations table instead of the healthcareSite table.
 */
@Entity
@Table(name = "organizations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "organizations_id_seq") })
@Where(clause = "retired_indicator  = 'false'")
public abstract class HealthcareSite extends Organization implements Comparable<HealthcareSite> {

    /** The nci institute code. */
    private String nciInstituteCode;

    /** The healthcare site investigators. */
    private List<HealthcareSiteInvestigator> healthcareSiteInvestigators = new ArrayList<HealthcareSiteInvestigator>();

    /** The research staffs. */
    private List<ResearchStaff> researchStaffs = new ArrayList<ResearchStaff>();
    
    /** The participants. */
    private List<Participant> participants = new ArrayList<Participant>();
    
    /** The external organizations. */
    protected List<HealthcareSite> externalOrganizations = new ArrayList<HealthcareSite>();

    /**
     * Sets the external organizations.
     * 
     * @param externalOrganizations the new external organizations
     */
    public void setExternalOrganizations(List<HealthcareSite> externalOrganizations) {
		this.externalOrganizations = externalOrganizations;
	}

	/** The lazy list helper. */
	private LazyListHelper lazyListHelper;
    
    /**
     * Instantiates a new healthcare site.
     */
    public HealthcareSite() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(InvestigatorGroup.class,
                        new BiDirectionalInstantiateFactory<InvestigatorGroup>(
                                        InvestigatorGroup.class, this, "HealthcareSite", new Class[] { HealthcareSite.class }));
    }

    /**
     * Adds the healthcare site investigator.
     * 
     * @param hcsi the hcsi
     */
    public void addHealthcareSiteInvestigator(HealthcareSiteInvestigator hcsi) {
        healthcareSiteInvestigators.add(hcsi);
        hcsi.setHealthcareSite(this);
    }

    /**
     * Adds the investigator group.
     * 
     * @param invGroup the inv group
     */
    public void addInvestigatorGroup(InvestigatorGroup invGroup) {
        this.getInvestigatorGroups().add(invGroup);
    }

    /**
     * Removes the healthcare site investigator.
     * 
     * @param hcsi the hcsi
     */
    public void removeHealthcareSiteInvestigator(HealthcareSiteInvestigator hcsi) {
        healthcareSiteInvestigators.remove(hcsi);
    }

    /**
     * Gets the healthcare site investigators.
     * 
     * @return the healthcare site investigators
     */
    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<HealthcareSiteInvestigator> getHealthcareSiteInvestigators() {
        return healthcareSiteInvestigators;
    }

    /**
     * Sets the healthcare site investigators.
     * 
     * @param healthcareSiteInvestigators the new healthcare site investigators
     */
    public void setHealthcareSiteInvestigators(
                    List<HealthcareSiteInvestigator> healthcareSiteInvestigators) {
        this.healthcareSiteInvestigators = healthcareSiteInvestigators;
    }

    /**
     * Gets the research staffs.
     * 
     * @return the research staffs
     */
    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<ResearchStaff> getResearchStaffs() {
        return researchStaffs;
    }

    /**
     * Sets the research staffs.
     * 
     * @param researchStaffs the new research staffs
     */
    public void setResearchStaffs(List<ResearchStaff> researchStaffs) {
        this.researchStaffs = researchStaffs;
    }

    /**
     * Adds the research staff.
     * 
     * @param rs the rs
     */
    public void addResearchStaff(ResearchStaff rs) {
        researchStaffs.add(rs);
    }

    /**
     * Removes the research staff.
     * 
     * @param rs the rs
     */
    public void removeResearchStaff(ResearchStaff rs) {
        researchStaffs.remove(rs);
    }
    
    /**
     * Gets the nci institute code.
     * 
     * @return the nci institute code
     */
    @Transient
    public String getNciInstituteCode() {
        return nciInstituteCode;
    }

    /**
     * Sets the nci institute code.
     * 
     * @param nciInstituteCode the new nci institute code
     */
    public void setNciInstituteCode(String nciInstituteCode) {
        this.nciInstituteCode = nciInstituteCode;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(HealthcareSite o) {
        if (this.equals((HealthcareSite) o)) return 0;
        else return 1;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Organization#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((nciInstituteCode == null) ? 0 : nciInstituteCode.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.Organization#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        final HealthcareSite other = (HealthcareSite) obj;
        if (nciInstituteCode == null) {
            if (other.nciInstituteCode != null) return false;
        }
        else if (!nciInstituteCode.equals(other.nciInstituteCode)) return false;
        return true;
    }

    /**
     * Gets the investigator groups internal.
     * 
     * @return the investigator groups internal
     */
    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<InvestigatorGroup> getInvestigatorGroupsInternal() {
        return lazyListHelper.getInternalList(InvestigatorGroup.class);
    }

    /**
     * Sets the investigator groups internal.
     * 
     * @param investigatorGroups the new investigator groups internal
     */
    public void setInvestigatorGroupsInternal(List<InvestigatorGroup> investigatorGroups) {
        this.lazyListHelper.setInternalList(InvestigatorGroup.class, investigatorGroups);
    }

    /**
     * Gets the investigator groups.
     * 
     * @return the investigator groups
     */
    @Transient
    public List<InvestigatorGroup> getInvestigatorGroups() {
        return lazyListHelper.getLazyList(InvestigatorGroup.class);
    }

    /**
     * Sets the investigator groups.
     * 
     * @param investigatorGroups the new investigator groups
     */
    public void setInvestigatorGroups(List<InvestigatorGroup> investigatorGroups) {
    }
    
    /**
     * Gets the participants.
     * 
     * @return the participants
     */
    @ManyToMany(mappedBy = "healthcareSites" )
    @Cascade(value = { CascadeType.LOCK})
    public List<Participant> getParticipants() {
        return participants;
    }

    /**
     * Sets the participants.
     * 
     * @param participants the new participants
     */
    public void setParticipants(List<Participant> participants) {
    	this.participants = participants;
    }
    
    /**
     * Gets the external organizations.
     * 
     * @return the external organizations
     */
    @Transient
	public List<HealthcareSite> getExternalOrganizations() {
		return externalOrganizations;
	}
    
    /**
     * Adds the external organization.
     * 
     * @param externalHealthcareSite the external healthcare site
     */
    public void addExternalOrganization(HealthcareSite externalHealthcareSite){
    	this.getExternalOrganizations().add(externalHealthcareSite);
    }


}