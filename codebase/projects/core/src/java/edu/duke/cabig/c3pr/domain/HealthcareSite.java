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

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

/**
 * @author Priyatam
 * @author Kulasekaran
 * 
 * Currently points to the newly renamed organizations table instead of the healthcareSite table.
 */
@Entity
@Table(name = "organizations")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "organizations_id_seq") })
public abstract class HealthcareSite extends Organization implements Comparable<HealthcareSite> {

    private String nciInstituteCode;

    private List<HealthcareSiteInvestigator> healthcareSiteInvestigators = new ArrayList<HealthcareSiteInvestigator>();

    private List<ResearchStaff> researchStaffs = new ArrayList<ResearchStaff>();
    
    private List<Participant> participants = new ArrayList<Participant>();
    
    protected List<HealthcareSite> externalOrganizations = new ArrayList<HealthcareSite>();

    public void setExternalOrganizations(List<HealthcareSite> externalOrganizations) {
		this.externalOrganizations = externalOrganizations;
	}

	private LazyListHelper lazyListHelper;
    
    public HealthcareSite() {
        lazyListHelper = new LazyListHelper();
        lazyListHelper.add(InvestigatorGroup.class,
                        new BiDirectionalInstantiateFactory<InvestigatorGroup>(
                                        InvestigatorGroup.class, this, "HealthcareSite", new Class[] { HealthcareSite.class }));
    }

    public void addHealthcareSiteInvestigator(HealthcareSiteInvestigator hcsi) {
        healthcareSiteInvestigators.add(hcsi);
        hcsi.setHealthcareSite(this);
    }

    public void addInvestigatorGroup(InvestigatorGroup invGroup) {
        this.getInvestigatorGroups().add(invGroup);
    }

    public void removeHealthcareSiteInvestigator(HealthcareSiteInvestigator hcsi) {
        healthcareSiteInvestigators.remove(hcsi);
    }

    @Transient
    public int getUnsavedInvestigatorGroupsLastIndex() {
        if ((this.getInvestigatorGroups().size() > 0)
                        && (this.getInvestigatorGroups().get(
                                        this.getInvestigatorGroups().size() - 1).getName()) == null) {
            return this.getInvestigatorGroups().size() - 1;
        }
        return -1;
    }

    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<HealthcareSiteInvestigator> getHealthcareSiteInvestigators() {
        return healthcareSiteInvestigators;
    }

    public void setHealthcareSiteInvestigators(
                    List<HealthcareSiteInvestigator> healthcareSiteInvestigators) {
        this.healthcareSiteInvestigators = healthcareSiteInvestigators;
    }

    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<ResearchStaff> getResearchStaffs() {
        return researchStaffs;
    }

    public void setResearchStaffs(List<ResearchStaff> researchStaffs) {
        this.researchStaffs = researchStaffs;
    }

    public void addResearchStaff(ResearchStaff rs) {
        researchStaffs.add(rs);
    }

    public void removeResearchStaff(ResearchStaff rs) {
        researchStaffs.remove(rs);
    }
    
    @Transient
    public String getNciInstituteCode() {
        return nciInstituteCode;
    }

    public void setNciInstituteCode(String nciInstituteCode) {
        this.nciInstituteCode = nciInstituteCode;
    }

    @Override
    public int compareTo(HealthcareSite o) {
        if (this.equals((HealthcareSite) o)) return 0;
        else return 1;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = PRIME * result + ((nciInstituteCode == null) ? 0 : nciInstituteCode.hashCode());
        return result;
    }

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

    @OneToMany(mappedBy = "healthcareSite", fetch = FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    public List<InvestigatorGroup> getInvestigatorGroupsInternal() {
        return lazyListHelper.getInternalList(InvestigatorGroup.class);
    }

    public void setInvestigatorGroupsInternal(List<InvestigatorGroup> investigatorGroups) {
        this.lazyListHelper.setInternalList(InvestigatorGroup.class, investigatorGroups);
    }

    @Transient
    public List<InvestigatorGroup> getInvestigatorGroups() {
        return lazyListHelper.getLazyList(InvestigatorGroup.class);
    }

    public void setInvestigatorGroups(List<InvestigatorGroup> investigatorGroups) {
    }
    
    @ManyToMany(mappedBy = "healthcareSites" )
    @Cascade(value = { CascadeType.LOCK})
    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
    	this.participants = participants;
    }
    
    @Transient
	public List<HealthcareSite> getExternalOrganizations() {
		return externalOrganizations;
	}
    
    public void addExternalOganization(RemoteHealthcareSite healthcareSite){
    	this.getExternalOrganizations().add(healthcareSite);
    }


}