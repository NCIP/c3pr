package edu.duke.cabig.c3pr.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * @author Priyatam
 */
@Entity
@Table (name="study_investigators")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="study_investigators_id_seq")
    }
)
public class StudyInvestigator extends AbstractDomainObject {
	
	//private String signatureText;
	private HealthcareSiteInvestigator healthcareSiteInvestigator;
	private StudySite studySite;
	
//	public String getSignatureText() {
//		return signatureText;
//	}
//	
//	public void setSignatureText(String signatureText) {
//		this.signatureText = signatureText;
//	}
		
	@ManyToOne
    @JoinColumn(name = "hsi_id")
	public HealthcareSiteInvestigator getHealthcareSiteInvestigator() {
		return healthcareSiteInvestigator;
	}
	
	public void setHealthcareSiteInvestigator(
			HealthcareSiteInvestigator healthcareSiteInvestigator) {
		this.healthcareSiteInvestigator = healthcareSiteInvestigator;
	}
	
	public void setSiteInvestigator(HealthcareSiteInvestigator healthcareSiteInvestigator) {
		this.healthcareSiteInvestigator = healthcareSiteInvestigator;
	}
		
	@ManyToOne
    @JoinColumn(name = "sts_id")    	
	public StudySite getStudySite() {
		return studySite;
	}

	public void setStudySite(StudySite studySite) {
		this.studySite = studySite;
	}
	
}
