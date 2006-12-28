package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.Parameter;

/**
 * A systematic evaluation of an observation or an
 * intervention (for example, treatment, drug, device, procedure or system) in one
 * or more subjects. Frequently this is a test of a particular hypothesis about
 * the treatment, drug, device, procedure or system. [CDAM]  A study can be either
 * primary or correlative. A study is considered a primary study if it has one or
 * more correlative studies. A correlative study extends the objectives or
 * observations of a primary study, enrolling the same, or a subset of the same,
 * subjects as the primary study. A Clinical Trial is a Study with type=
 * "intervention" with subjects of type="human". 
 * 
 * @author priyatam
 */

@Entity
@Table (name = "STUDIES")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="STUDIES_ID_SEQ")
    }
)
public class Study extends AbstractDomainObject implements Comparable<Study>, Serializable{
	
	 
	private String blindedIndicator;
	
	@Column(name = "DESCRIPTION_TEXT", length = 50, nullable = false)	   
	private String descriptionText;
	
	@Column(name = "DISEASE_CODE", length = 20, nullable = false)	   
	private String diseaseCode;	

	@Column(name = "DISEASE_CODE_LONG_TITLE_TEXT", length = 50, nullable = false)	   
	private String longTitleText;	
	
	@Column(name = "MONITOR_CODE", length = 20, nullable = false)	   
	private String monitorCode;
	
	@Column(name = "MULTI_INSTITUTION_INDICATOR", length = 4, nullable = false)	   
	private String multiInstitutionIndicator;
	
	@Column(name = "NCI_IDENTIFIER", length = 4, nullable = false)	   
	private String nciIdentifier;
	
	@Column(name = "PHASE_CODE", length = 20, nullable = false)	   	
	private String phaseCode;
	
	@Column(name = "PRECIS_TEXT", length = 30, nullable = false)	   			
	private String precisText;
	
	@Column(name = "RANDOMIZED_INDICATOR", length = 20, nullable = false)	   	
	private String randomizedIndicator;	
	
	// A name or abbreviated title by which the document is known
	@Column(name = "SHORT_TITLE_TEXT", length = 30, nullable = false)	   	
	private String shortTitleText;
	
	@Column(name = "SPONSOR_CODE", length = 20, nullable = false)			
	private String sponsorCode;
	
	@Column(name = "STATUS_CODE", length = 20, nullable = false)	   		
	private String status;
	
	@Column(name = "TARGET_ACCRUAL_NUMBER", length = 10, nullable = false)	   		
	private int targetAccrualNumber;
	
	@Column(name = "TYPE_CODE", length = 20, nullable = false)	   		
	private String type;
	
	//private List<EligibilityCriteria> eligibilityCriteria;
	private List<Epoch> epochs;
	private List<Amendment> amendments;
	private List<StudySite> studySites;
	private List<Study> studies;
	
	/**
	 * @return the amendments
	 */
	@OneToMany (mappedBy = "study", fetch=FetchType.EAGER )
	@Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })	
	public List<Amendment> getAmendments() {
		return amendments;
	}

	/**
	 * @param amendments the amendments to set
	 */
	public void setAmendments(List<Amendment> amendments) {
		this.amendments = amendments;
	}

	/**
	 * @return the epochs
	 */
	@OneToMany
    @JoinColumn(name="study_id", nullable=false)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	   
	public List<Epoch> getEpochs() {
		return epochs;
	}

	/**
	 * @param epochs the epochs to set
	 */
	public void setEpochs(List<Epoch> epochs) {
		this.epochs = epochs;
	}

	/**
	 * @return the studies
	 */
	@OneToMany
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	   
	public List<Study> getStudies() {
		return studies;
	}

	/**
	 * @param studies the studies to set
	 */
	public void setStudies(List<Study> studies) {
		this.studies = studies;
	}

	/**
	 * @return the studySites
	 */
	@OneToMany
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	   	
	public List<StudySite> getStudySites() {
		return studySites;
	}

	/**
	 * @param studySites the studySites to set
	 */
	public void setStudySites(List<StudySite> studySites) {
		this.studySites = studySites;
	}

	public int compareTo(Study o) {
     //TODO
    	return 1;
    }

	@Column(name = "BLINDED_INDICATOR")	 
	public String getBlindedIndicator() {
		return blindedIndicator;
	}

	public void setBlindedIndicator(String blindedIndicator) {
		this.blindedIndicator = blindedIndicator;
	}

	public String getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(String descriptionText) {
		this.descriptionText = descriptionText;
	}

	public String getDiseaseCode() {
		return diseaseCode;
	}

	public void setDiseaseCode(String diseaseCode) {
		this.diseaseCode = diseaseCode;
	}

	public String getLongTitleText() {
		return longTitleText;
	}

	public void setLongTitleText(String longTitleText) {
		this.longTitleText = longTitleText;
	}

	public String getMonitorCode() {
		return monitorCode;
	}

	public void setMonitorCode(String monitorCode) {
		this.monitorCode = monitorCode;
	}

	public String getMultiInstitutionIndicator() {
		return multiInstitutionIndicator;
	}

	public void setMultiInstitutionIndicator(String multiInstitutionIndicator) {
		this.multiInstitutionIndicator = multiInstitutionIndicator;
	}

	public String getNciIdentifier() {
		return nciIdentifier;
	}

	public void setNciIdentifier(String nciIdentifier) {
		this.nciIdentifier = nciIdentifier;
	}

	public String getPhaseCode() {
		return phaseCode;
	}

	public void setPhaseCode(String phaseCode) {
		this.phaseCode = phaseCode;
	}

	public String getPrecisText() {
		return precisText;
	}

	public void setPrecisText(String precisText) {
		this.precisText = precisText;
	}

	public String getRandomizedIndicator() {
		return randomizedIndicator;
	}

	public void setRandomizedIndicator(String randomizedIndicator) {
		this.randomizedIndicator = randomizedIndicator;
	}

	public String getShortTitleText() {
		return shortTitleText;
	}

	public void setShortTitleText(String shortTitleText) {
		this.shortTitleText = shortTitleText;
	}

	public String getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getTargetAccrualNumber() {
		return targetAccrualNumber;
	}

	public void setTargetAccrualNumber(int targetAccrualNumber) {
		this.targetAccrualNumber = targetAccrualNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((amendments == null) ? 0 : amendments.hashCode());
		result = PRIME * result + ((blindedIndicator == null) ? 0 : blindedIndicator.hashCode());
		result = PRIME * result + ((descriptionText == null) ? 0 : descriptionText.hashCode());
		result = PRIME * result + ((diseaseCode == null) ? 0 : diseaseCode.hashCode());
		result = PRIME * result + ((epochs == null) ? 0 : epochs.hashCode());
		result = PRIME * result + ((longTitleText == null) ? 0 : longTitleText.hashCode());
		result = PRIME * result + ((monitorCode == null) ? 0 : monitorCode.hashCode());
		result = PRIME * result + ((multiInstitutionIndicator == null) ? 0 : multiInstitutionIndicator.hashCode());
		result = PRIME * result + ((nciIdentifier == null) ? 0 : nciIdentifier.hashCode());
		result = PRIME * result + ((phaseCode == null) ? 0 : phaseCode.hashCode());
		result = PRIME * result + ((precisText == null) ? 0 : precisText.hashCode());
		result = PRIME * result + ((randomizedIndicator == null) ? 0 : randomizedIndicator.hashCode());
		result = PRIME * result + ((shortTitleText == null) ? 0 : shortTitleText.hashCode());
		result = PRIME * result + ((sponsorCode == null) ? 0 : sponsorCode.hashCode());
		result = PRIME * result + ((status == null) ? 0 : status.hashCode());
		result = PRIME * result + ((studies == null) ? 0 : studies.hashCode());
		result = PRIME * result + ((studySites == null) ? 0 : studySites.hashCode());
		result = PRIME * result + targetAccrualNumber;
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Study other = (Study) obj;
		if (amendments == null) {
			if (other.amendments != null)
				return false;
		} else if (!amendments.equals(other.amendments))
			return false;
		if (blindedIndicator == null) {
			if (other.blindedIndicator != null)
				return false;
		} else if (!blindedIndicator.equals(other.blindedIndicator))
			return false;
		if (descriptionText == null) {
			if (other.descriptionText != null)
				return false;
		} else if (!descriptionText.equals(other.descriptionText))
			return false;
		if (diseaseCode == null) {
			if (other.diseaseCode != null)
				return false;
		} else if (!diseaseCode.equals(other.diseaseCode))
			return false;
		if (epochs == null) {
			if (other.epochs != null)
				return false;
		} else if (!epochs.equals(other.epochs))
			return false;
		if (longTitleText == null) {
			if (other.longTitleText != null)
				return false;
		} else if (!longTitleText.equals(other.longTitleText))
			return false;
		if (monitorCode == null) {
			if (other.monitorCode != null)
				return false;
		} else if (!monitorCode.equals(other.monitorCode))
			return false;
		if (multiInstitutionIndicator == null) {
			if (other.multiInstitutionIndicator != null)
				return false;
		} else if (!multiInstitutionIndicator.equals(other.multiInstitutionIndicator))
			return false;
		if (nciIdentifier == null) {
			if (other.nciIdentifier != null)
				return false;
		} else if (!nciIdentifier.equals(other.nciIdentifier))
			return false;
		if (phaseCode == null) {
			if (other.phaseCode != null)
				return false;
		} else if (!phaseCode.equals(other.phaseCode))
			return false;
		if (precisText == null) {
			if (other.precisText != null)
				return false;
		} else if (!precisText.equals(other.precisText))
			return false;
		if (randomizedIndicator == null) {
			if (other.randomizedIndicator != null)
				return false;
		} else if (!randomizedIndicator.equals(other.randomizedIndicator))
			return false;
		if (shortTitleText == null) {
			if (other.shortTitleText != null)
				return false;
		} else if (!shortTitleText.equals(other.shortTitleText))
			return false;
		if (sponsorCode == null) {
			if (other.sponsorCode != null)
				return false;
		} else if (!sponsorCode.equals(other.sponsorCode))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (studies == null) {
			if (other.studies != null)
				return false;
		} else if (!studies.equals(other.studies))
			return false;
		if (studySites == null) {
			if (other.studySites != null)
				return false;
		} else if (!studySites.equals(other.studySites))
			return false;
		if (targetAccrualNumber != other.targetAccrualNumber)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	// logic
	
	/**
	 * 
	 */
	public void addStudy(Study study){
		getStudies().add(study);	       
	}
}