package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */

@Entity
@Table (name = "hc_site_investigators")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="hc_site_investigators_id_seq")
    }
)
public class HealthcareSiteInvestigator extends AbstractDomainObject {

	private HealthcareSite healthcareSite;
    private Investigator investigator;    	
    private String statusCode;
    private Date statusDate;
    private List<StudyInvestigator> studyInvestigators = new ArrayList<StudyInvestigator>();
    
    public void addStudyInvestigator(StudyInvestigator si)
	{
    	studyInvestigators.add(si);
		si.setSiteInvestigator(this);
	}
	
    public void removeStudyInvestigator(StudyInvestigator si)
	{
    	studyInvestigators.remove(si);
	}
    
    @ManyToOne
    @JoinColumn(name = "inv_id")
	public Investigator getInvestigator() {
		return investigator;
	}
    
    @OneToMany (mappedBy = "healthcareSiteInvestigator", fetch = FetchType.LAZY)    
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
	public List<StudyInvestigator> getStudyInvestigators() {
		return studyInvestigators;
	}

	public void setStudyInvestigators(List<StudyInvestigator> studyInvestigators) {
		this.studyInvestigators = studyInvestigators;
	}

	public void setInvestigator(Investigator investigator) {
		this.investigator = investigator;
	}
	
	@ManyToOne
    @JoinColumn(name = "hcs_id")
	public HealthcareSite getHealthcareSite() {
		return healthcareSite;
	} 
	
	public void setHealthcareSite(HealthcareSite healthcareSite) {
		this.healthcareSite = healthcareSite;
	} 
	
	public String getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	public Date getStatusDate() {
		return statusDate;
	}
	
	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}        
	
	public String toString(){
		return investigator.getFullName();
	}
}