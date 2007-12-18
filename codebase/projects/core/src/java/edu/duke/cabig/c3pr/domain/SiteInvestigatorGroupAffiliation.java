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

@Entity
@Table(name="org_inv_gr_affiliations")
@GenericGenerator(name="id-generator", strategy = "native",
 parameters = {
        @Parameter(name="sequence", value="org_inv_gr_affiliations_id_seq")
    }
)
public class SiteInvestigatorGroupAffiliation extends AbstractMutableDeletableDomainObject{
	private Date startDate;
	private Date endDate;
	private HealthcareSiteInvestigator healthcareSiteInvestigator;
	private InvestigatorGroup investigatorGroup;
	
	@ManyToOne
	@JoinColumn(name = "ing_id")
	public InvestigatorGroup getInvestigatorGroup() {
		return investigatorGroup;
	}
	public void setInvestigatorGroup(InvestigatorGroup investigatorGroup) {
		this.investigatorGroup = investigatorGroup;
	}
	
	@ManyToOne
	@JoinColumn(name = "hsi_id")
	public HealthcareSiteInvestigator getHealthcareSiteInvestigator() {
		return healthcareSiteInvestigator;
	}
	public void setHealthcareSiteInvestigator(
			HealthcareSiteInvestigator healthcareSiteInvestigator) {
//		healthcareSiteInvestigator.getSiteInvestigatorGroupAffiliations().add(this);
		this.healthcareSiteInvestigator = healthcareSiteInvestigator;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
