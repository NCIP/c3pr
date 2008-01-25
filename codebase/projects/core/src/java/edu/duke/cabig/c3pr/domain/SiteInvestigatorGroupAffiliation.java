package edu.duke.cabig.c3pr.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StringUtils;

@Entity
@Table(name="org_inv_gr_affiliations")
@GenericGenerator(name="id-generator", strategy = "native",
 parameters = {
        @Parameter(name="sequence", value="org_inv_gr_affiliations_id_seq")
    }
)
public class SiteInvestigatorGroupAffiliation extends AbstractMutableDeletableDomainObject implements Comparable<SiteInvestigatorGroupAffiliation>{
	private Date startDate;
	private Date endDate;
	private HealthcareSiteInvestigator healthcareSiteInvestigator;
	private InvestigatorGroup investigatorGroup;
	private String startDateStr;
	private String endDateStr;
	
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
		result = PRIME * result + ((healthcareSiteInvestigator == null) ? 0 : healthcareSiteInvestigator.hashCode());
		result = PRIME * result + ((investigatorGroup == null) ? 0 : investigatorGroup.hashCode());
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
		final SiteInvestigatorGroupAffiliation other = (SiteInvestigatorGroupAffiliation) obj;
		if (healthcareSiteInvestigator == null) {
			if (other.healthcareSiteInvestigator != null)
				return false;
		} else if (!healthcareSiteInvestigator.equals(other.healthcareSiteInvestigator))
			return false;
		if (investigatorGroup == null) {
			if (other.investigatorGroup != null)
				return false;
		} else if (!investigatorGroup.equals(other.investigatorGroup))
			return false;
		return true;
	}
	public int compareTo(SiteInvestigatorGroupAffiliation o) {
		if (this.equals(o)) return 0;
		return 1;
	}

}
