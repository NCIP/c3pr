package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Where;

import edu.duke.cabig.c3pr.utils.StringUtils;

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
 * @author Priyatam
 */

@Entity
@Table (name = "STUDIES")
@GenericGenerator(name="id-generator", strategy = "native",
    parameters = {
        @Parameter(name="sequence", value="STUDIES_ID_SEQ")
    }
)
public class Study extends AbstractGridIdentifiableDomainObject implements Comparable<Study>{
		 
	private Boolean blindedIndicator;
	private Boolean multiInstitutionIndicator;
	private Boolean randomizedIndicator;	
	
	// A name or abbreviated title by which the document is known
	private String shortTitleText;
	private String longTitleText;	
	private String descriptionText;
	private String precisText;
	
	private String diseaseCode;		
	private String monitorCode;		
	private String phaseCode;					
	private String sponsorCode;	
	private String status;
	private String type;
	
	private int targetAccrualNumber;
		
	private List<Epoch> epochs = new ArrayList<Epoch>();	  	
	private List<StudySite> studySites = new ArrayList<StudySite>();;			  
	private List<Identifier> identifiers = new ArrayList<Identifier>();
	
	private String trimmedShortTitleText;
	private String primaryIdentifier;
	
	private List<InclusionEligibilityCriteria> incCriterias= new ArrayList<InclusionEligibilityCriteria>();
	private List<ExclusionEligibilityCriteria> excCriterias= new ArrayList<ExclusionEligibilityCriteria>();
	
	/// LOGIC

	public void addEpoch(Epoch epoch){
		epochs.add(epoch);
		epoch.setStudy(this);
	}
	
	public void removeEpoch(Epoch epoch){
		epochs.remove(epoch);
	}
	
	public void addStudySite(StudySite studySite)
	{
		studySites.add(studySite);
		studySite.setStudy(this);
	}
	
	public void removeStudySite(StudySite studySite)
	{
		studySites.remove(studySite);
	}
	
	public void addInclusionEligibilityCriteria(InclusionEligibilityCriteria inc)
	{
		incCriterias.add(inc);		
	}
	
	public void removeInclusionEligibilityCriteria(InclusionEligibilityCriteria inc)
	{
		incCriterias.remove(inc);		
	}
	
	public void addExclusionEligibilityCriteria(ExclusionEligibilityCriteria exc)
	{
		excCriterias.add(exc);		
	}
	
	public void removeExcclusionEligibilityCriteria(ExclusionEligibilityCriteria exc)
	{
		excCriterias.remove(exc);		
	}	
	
	public void addIdentifier(Identifier identifier)
	{
		identifiers.add(identifier);
	}
	
	public void removeIdentifier(Identifier identifier)
	{
		identifiers.remove(identifier);
	}
	
	/// BEAN PROPERTIES
	
	@OneToMany (mappedBy="study", fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})
	public List<Epoch> getEpochs() {
		return epochs;
	}

	public void setEpochs(List<Epoch> epochs) {
		this.epochs = epochs;
	}

	@OneToMany (mappedBy="study", fetch=FetchType.LAZY)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN})	
	public List<StudySite> getStudySites() {
		return studySites;
	}
	
	@OneToMany
    @Cascade({CascadeType.ALL,CascadeType.DELETE_ORPHAN})
    @JoinColumn(name = "STU_ID")	 
	public List<Identifier> getIdentifiers() {
		return identifiers;
	}

	public void setIdentifiers(List<Identifier> identifiers) {
		this.identifiers = identifiers;
	}

	public void setStudySites(List<StudySite> studySites) {
		this.studySites = studySites;
	}	
	
	public Boolean getBlindedIndicator() {
		return blindedIndicator;
	}

	public void setBlindedIndicator(Boolean blindedIndicator) {
		this.blindedIndicator = blindedIndicator;
	}
	
	public Boolean getMultiInstitutionIndicator() {
		return multiInstitutionIndicator;
	}

	public void setMultiInstitutionIndicator(Boolean multiInstitutionIndicator) {
		this.multiInstitutionIndicator = multiInstitutionIndicator;
	}
	
	public Boolean getRandomizedIndicator() {
		return randomizedIndicator;
	}

	public void setRandomizedIndicator(Boolean randomizedIndicator) {
		this.randomizedIndicator = randomizedIndicator;
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

	public int compareTo(Study o) {
     //TODO
    	return 1;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		result = PRIME * result + ((diseaseCode == null) ? 0 : diseaseCode.hashCode());
		result = PRIME * result + ((epochs == null) ? 0 : epochs.hashCode());
		result = PRIME * result + ((identifiers == null) ? 0 : identifiers.hashCode());
		result = PRIME * result + ((longTitleText == null) ? 0 : longTitleText.hashCode());
		result = PRIME * result + ((monitorCode == null) ? 0 : monitorCode.hashCode());
		result = PRIME * result + ((phaseCode == null) ? 0 : phaseCode.hashCode());
		result = PRIME * result + ((sponsorCode == null) ? 0 : sponsorCode.hashCode());
		result = PRIME * result + ((status == null) ? 0 : status.hashCode());
		result = PRIME * result + ((studySites == null) ? 0 : studySites.hashCode());
		result = PRIME * result + targetAccrualNumber;
		result = PRIME * result + ((type == null) ? 0 : type.hashCode());
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
		final Study other = (Study) obj;
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
		if (identifiers == null) {
			if (other.identifiers != null)
				return false;
		} else if (!identifiers.equals(other.identifiers))
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
		if (phaseCode == null) {
			if (other.phaseCode != null)
				return false;
		} else if (!phaseCode.equals(other.phaseCode))
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

	@Transient
	public String getTrimmedShortTitleText() {		
		return StringUtils.getTrimmedText(shortTitleText, 40);
	}
	
	@Transient
	public String getPrimaryIdentifier() {		
		for (Identifier identifier : identifiers) {
			if(identifier.getPrimaryIndicator().booleanValue() == true)
			{
				return identifier.getValue();
			}
		}
			
		return primaryIdentifier;		
	}

	@OneToMany
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })        
    @JoinColumn(name = "stu_id", nullable=false) 
    @Where(clause = "DTYPE = 'E'") // it is pretty lame that this is necessary    
	public List<ExclusionEligibilityCriteria> getExcCriterias() {
		return excCriterias;
	}

	public void setExcCriterias(List<ExclusionEligibilityCriteria> excCriterias) {
		this.excCriterias = excCriterias;
	}

	@OneToMany
    @Cascade (value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })        
    @JoinColumn(name = "stu_id", nullable=false)   
    @Where(clause = "DTYPE = 'I'") // it is pretty lame that this is necessary   		
	public List<InclusionEligibilityCriteria> getIncCriterias() {
		return incCriterias;
	}

	public void setIncCriterias(List<InclusionEligibilityCriteria> incCriterias) {
		this.incCriterias = incCriterias;
	}
}