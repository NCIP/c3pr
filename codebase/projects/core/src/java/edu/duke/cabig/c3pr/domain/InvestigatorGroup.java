package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.collections.LazyListHelper;

import java.util.ArrayList;
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

@Entity
@Table(name="investigator_groups")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="investigator_groups_id_seq")
    }
)
public class InvestigatorGroup extends AbstractMutableDeletableDomainObject {

	private String name;
	private String descriptionText;
	private Date startDate;
	private Date endDate;
	private HealthcareSite healthcareSite;
	private LazyListHelper lazyListHelper;
	
	public InvestigatorGroup(){
		lazyListHelper = new LazyListHelper();
		lazyListHelper.add(SiteInvestigatorGroupAffiliation.class, new BiDirectionalInstantiateFactory<SiteInvestigatorGroupAffiliation>(SiteInvestigatorGroupAffiliation.class,this));
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@ManyToOne
	@JoinColumn(name="hcs_id")
	public HealthcareSite getHealthcareSite() {
		return healthcareSite;
	}
	
	public void setHealthcareSite(HealthcareSite healthcareSite) {
		this.healthcareSite = healthcareSite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@OneToMany(mappedBy="investigatorGroup",fetch = FetchType.LAZY)
	@Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<SiteInvestigatorGroupAffiliation> getSiteInvestigatorGroupAffiliationsInternal() {
		return lazyListHelper.getInternalList(SiteInvestigatorGroupAffiliation.class);
	}

	public void setSiteInvestigatorGroupAffiliationsInternal(
			List<SiteInvestigatorGroupAffiliation> siteInvestigatorGroupAffiliations) {
		lazyListHelper.setInternalList(SiteInvestigatorGroupAffiliation.class, siteInvestigatorGroupAffiliations);
	}
	
	@Transient
	public List<SiteInvestigatorGroupAffiliation> getSiteInvestigatorGroupAffiliations() {
		return lazyListHelper.getLazyList(SiteInvestigatorGroupAffiliation.class);
	}
	
	public void setSiteInvestigatorGroupAffiliations(
			List<SiteInvestigatorGroupAffiliation> siteInvestigatorGroupAffiliations) {
	}

}
