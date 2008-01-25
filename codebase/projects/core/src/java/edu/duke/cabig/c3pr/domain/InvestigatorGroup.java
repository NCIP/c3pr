package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StringUtils;
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
public class InvestigatorGroup extends AbstractMutableDeletableDomainObject implements Comparable<InvestigatorGroup> {

	private String name;
	private String descriptionText;
	private Date startDate;
	private Date endDate;
	private HealthcareSite healthcareSite;
	private LazyListHelper lazyListHelper;
	private String startDateStr;
	private String endDateStr;
	
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
	 @Transient
	    public String getStartDateStr() {
	        try {
	            return DateUtil.formatDate(startDate, "MM/dd/yyyy");
	        }
	        catch(Exception e){
	            //do nothing
	        }
	        return "";
	    }
	 
	 @Transient
	    public String getEndDateStr() {
	        try {
	            return DateUtil.formatDate(endDate, "MM/dd/yyyy");
	        }
	        catch(Exception e){
	            //do nothing
	        }
	        return "";
	    }

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final InvestigatorGroup other = (InvestigatorGroup) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public int compareTo(InvestigatorGroup o) {
		if (this.equals(o)) return 0;
		return 1;
	}

}
