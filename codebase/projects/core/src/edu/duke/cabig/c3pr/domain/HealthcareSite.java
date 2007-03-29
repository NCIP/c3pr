package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * @author Priyatam
 * @author Kulasekaran
 */
 @Entity
 @Table (name = "healthcare_sites")
 @GenericGenerator(name="id-generator", strategy = "native",
     parameters = {
         @Parameter(name="sequence", value="healthcare_sites_id_seq")
     }
 )
public class HealthcareSite extends Organization implements Comparable<HealthcareSite> {
		 
	private String nciInstituteCode;	
	private List<HealthcareSiteInvestigator> healthcareSiteInvestigators
		= new ArrayList<HealthcareSiteInvestigator>();
	
	public void addHealthcareSiteInvestigator(HealthcareSiteInvestigator hcsi)
	{
		healthcareSiteInvestigators.add(hcsi);
		hcsi.setHealthcareSite(this);
	}
	
	public void removeHealthcareSiteInvestigator(HealthcareSiteInvestigator hcsi)
	{
		healthcareSiteInvestigators.remove(hcsi);
	}
	
	@OneToMany (mappedBy="healthcareSite", fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	
	public List<HealthcareSiteInvestigator> getHealthcareSiteInvestigators() {
		return healthcareSiteInvestigators;
	}

	public void setHealthcareSiteInvestigators(
			List<HealthcareSiteInvestigator> healthcareSiteInvestigators) {
		this.healthcareSiteInvestigators = healthcareSiteInvestigators;
	}

	public String getNciInstituteCode() {
		return nciInstituteCode;
	}

	public void setNciInstituteCode(String nciInstituteCode) {
		this.nciInstituteCode = nciInstituteCode;
	}  
	
	public int compareTo(HealthcareSite o) {
		// TODO Auto-generated method stub
		return 0;
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
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final HealthcareSite other = (HealthcareSite) obj;
		if (nciInstituteCode == null) {
			if (other.nciInstituteCode != null)
				return false;
		} else if (!nciInstituteCode.equals(other.nciInstituteCode))
			return false;
		return true;
	}	
}