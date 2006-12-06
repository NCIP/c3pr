/*
 * Created Thu Apr 20 17:45:40 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;


import java.io.Serializable;
import java.util.Date;

/**
 * A class that represents a row in the 'PROTOCOL_AMENDMENT' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Amendment implements Serializable
{
    private Integer id;
    private String amendment;
    private Date date;
    private Integer accrualCeiling;
    private String longTitle;
    private String shortTitle;
    private String blinded;
    private String nciCoordinated;
    private java.util.Date lastAudit;
    private String registrationTracked;
    private String description;
    private String precis;
    private String randomized;
    private String design;
    private String eligibilityChecklistRequiredFlag;

    private String modificationBy;
    private String modificationType;
    private Date modificationDate;
    private String createdBy;
    private Date creationDate;

    private Protocol protocol;
    private EligibilityDefinition eligibilityDefinition;
    
    /**
     * Simple constructor of Amendment instances.
     */
    public Amendment()
    {
    }


	public Integer getAccrualCeiling() {
		return accrualCeiling;
	}


	public void setAccrualCeiling(Integer accrualCeiling) {
		this.accrualCeiling = accrualCeiling;
	}


	public String getAmendment() {
		if(amendment == null) return "";
		return amendment;
	}


	public void setAmendment(String amendment) {
		this.amendment = amendment;
	}


	public String getBlinded() {
		return blinded;
	}


	public void setBlinded(String blinded) {
		this.blinded = blinded;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getDesign() {
		return design;
	}


	public void setDesign(String design) {
		this.design = design;
	}


	public String getEligibilityChecklistRequiredFlag() {
		return eligibilityChecklistRequiredFlag;
	}


	public void setEligibilityChecklistRequiredFlag(
			String eligibilityChecklistRequiredFlag) {
		this.eligibilityChecklistRequiredFlag = eligibilityChecklistRequiredFlag;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public java.util.Date getLastAudit() {
		return lastAudit;
	}


	public void setLastAudit(java.util.Date lastAudit) {
		this.lastAudit = lastAudit;
	}


	public String getLongTitle() {
		return longTitle;
	}


	public void setLongTitle(String longTitle) {
		this.longTitle = longTitle;
	}


	public String getModificationBy() {
		return modificationBy;
	}


	public void setModificationBy(String modificationBy) {
		this.modificationBy = modificationBy;
	}


	public Date getModificationDate() {
		return modificationDate;
	}


	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
	}


	public String getModificationType() {
		return modificationType;
	}


	public void setModificationType(String modificationType) {
		this.modificationType = modificationType;
	}


	public String getNciCoordinated() {
		return nciCoordinated;
	}


	public void setNciCoordinated(String nciCoordinated) {
		this.nciCoordinated = nciCoordinated;
	}


	public String getPrecis() {
		return precis;
	}


	public void setPrecis(String precis) {
		this.precis = precis;
	}


	public Protocol getProtocol() {
		return protocol;
	}


	public void setProtocol(Protocol protocol) {
		this.protocol = protocol;
	}


	public String getRandomized() {
		return randomized;
	}


	public void setRandomized(String randomized) {
		this.randomized = randomized;
	}


	public String getRegistrationTracked() {
		return registrationTracked;
	}


	public void setRegistrationTracked(String registrationTracked) {
		this.registrationTracked = registrationTracked;
	}


	public String getShortTitle() {
		return shortTitle;
	}


	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}


	public EligibilityDefinition getEligibilityDefinition() {
		return eligibilityDefinition;
	}


	public void setEligibilityDefinition(
			EligibilityDefinition eligibilityDefinition) {
		this.eligibilityDefinition = eligibilityDefinition;
	}

	
}
