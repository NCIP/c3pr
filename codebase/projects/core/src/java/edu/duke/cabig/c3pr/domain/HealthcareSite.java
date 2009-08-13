package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Iterator;
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

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.utils.StringUtils;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

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
     * Gets the ctep code.
     *
     * @return the ctep code
     */
    @Transient
    public String getCtepCode() {
    	if(getOrganizationAssignedIdentifiers().size() > 0){
    		Iterator iter = getOrganizationAssignedIdentifiers().iterator();
    		OrganizationAssignedIdentifier identifier = null;
    		while(iter.hasNext()){
    			identifier = (OrganizationAssignedIdentifier)iter.next();
    			if(identifier.getType().equals(OrganizationIdentifierTypeEnum.CTEP)){
    				return identifier.getValue();
    			}
    		}
    	}
		return "";
    }

    /**
     * Gets the Primary code.
     *
     * @return the Primary code
     */
    @Transient
    public String getPrimaryIdentifier() {
    	if(getOrganizationAssignedIdentifiers().size() > 0 ||
    			getSystemAssignedIdentifiers().size() > 0){
    		Iterator iter = getIdentifiersAssignedToOrganization().iterator();
    		Identifier identifier = null;
    		while(iter.hasNext()){
    			identifier = (Identifier)iter.next();
    			if(identifier.isPrimary()){
    				return identifier.getValue();
    			}
    		}
    	}
		return null;
    }

    /**
     * Gets the nci code.
     *
     * @return the nci code
     */
    @Transient
    public String getNciIdentifierAsString() {
    	if(getOrganizationAssignedIdentifiers().size() > 0){
    		Iterator iter = getOrganizationAssignedIdentifiers().iterator();
    		OrganizationAssignedIdentifier identifier = null;
    		while(iter.hasNext()){
    			identifier = (OrganizationAssignedIdentifier)iter.next();
    			if(identifier.getType().equals(OrganizationIdentifierTypeEnum.NCI)){
    				return identifier.getValue();
    			}
    		}
    	}
		return "";
    }


    /**
     * Sets the Ctep code in the IdentifiersAssignedToOrganization.
     *
     * @param nciInstituteCode the new nci institute code
     */
    public void setCtepCode(String ctepCode) {
    	setCtepCode(ctepCode, true);
    }

    public void setCtepCode(String ctepCode, Boolean primaryIndicator) {
    	if(!StringUtils.isEmpty(ctepCode)){
    		OrganizationAssignedIdentifier identifier = new OrganizationAssignedIdentifier();
    		identifier.setType(OrganizationIdentifierTypeEnum.CTEP);
    		identifier.setValue(ctepCode);
    		identifier.setPrimaryIndicator(primaryIndicator);
    		getIdentifiersAssignedToOrganization().add(identifier);
    	}
    }

    public void setNCICode(String nciCode) {
    	if(!StringUtils.isEmpty(nciCode)){
    		OrganizationAssignedIdentifier identifier = new OrganizationAssignedIdentifier();
    		identifier.setType(OrganizationIdentifierTypeEnum.NCI);
    		identifier.setValue(nciCode);
    		identifier.setPrimaryIndicator(false);
    		getIdentifiersAssignedToOrganization().add(identifier);
    	}
    }

    /**
     * Sets the Ctep Identifier in the IdentifiersAssignedToOrganization.
     * Overloaded to accept an identifier instead of string
     */
    public void setCtepCode(OrganizationAssignedIdentifier ctepIdentifier) {
    	if(ctepIdentifier != null){
    		getIdentifiersAssignedToOrganization().add(ctepIdentifier);
    	}
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
        result = PRIME * result + ((getPrimaryIdentifier() == null) ? 0 : getPrimaryIdentifier().hashCode());
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
        if (getPrimaryIdentifier() == null) {
            if (other.getPrimaryIdentifier() != null) return false;
        }
        else if (!getPrimaryIdentifier().equals(other.getPrimaryIdentifier())) return false;
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