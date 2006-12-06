/*
 * Created Thu Apr 20 17:45:39 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.duke.cabig.c3pr.constants.ReferenceDataConstants;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * A class that represents a row in the 'PROTOCOL' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 * TODO comment code.
 */
public class Protocol extends AbstractDomainObject implements Comparable<Protocol>, Serializable
{
	private Integer id;
	private String multiInstitutionalFlag;
	private String createdBy;
	private Date creationDate;
	private String modificationBy;
	private String modificationType;
	private Date modificationDate;
	private String systemIdentifier;
	
	private Collection diseaseCollection;
	private Collection diseaseSiteCollection;
	private Collection protocolParticipantRoleCollection;
	private ProtocolType protocolType;
	private ProtocolPhase protocolPhase;
	private Collection amendmentCollection;
	private Collection<ProtocolStatus> protocolStatusCollection;
	private Collection protocolArmCollection;
	private Collection<ProtocolInstitution> protocolInstitutionCollection;
	private ClinicalStudy clinicalStudy;
	/**
     * Simple constructor of Protocol instances.
     */
    public Protocol()
    {
    }

    public int compareTo(Protocol o) {
        // by type first
       // int typeDiff = getType().compareTo(o.getType());
       // if (typeDiff != 0) return typeDiff;
        // then by name
       // return ComparisonUtils.nullSafeCompare(toLower(getName()), toLower(o.getName()));
        return 1;
    }
    
	public ClinicalStudy getClinicalStudy() {
		return clinicalStudy;
	}

	public void setClinicalStudy(ClinicalStudy clinicalStudy) {
		this.clinicalStudy = clinicalStudy;
	}

	public Collection getAmendmentCollection() {
		return amendmentCollection;
	}

	public void setAmendmentCollection(Collection amendmentCollection) {
		this.amendmentCollection = amendmentCollection;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getMultiInstitutionalFlag() {
		return multiInstitutionalFlag;
	}

	public void setMultiInstitutionalFlag(String multiInstitutionalFlag) {
		this.multiInstitutionalFlag = multiInstitutionalFlag;
	}

	public Collection getProtocolArmCollection() {
		return protocolArmCollection;
	}

	public void setProtocolArmCollection(Collection protocolArmCollection) {
		this.protocolArmCollection = protocolArmCollection;
	}

	public Collection getProtocolInstitutionCollection() {
		return protocolInstitutionCollection;
	}

	public void setProtocolInstitutionCollection(
			Collection protocolInstitutionCollection) {
		this.protocolInstitutionCollection = protocolInstitutionCollection;
	}

	public Collection getProtocolParticipantRoleCollection() {
		return protocolParticipantRoleCollection;
	}

	public void setProtocolParticipantRoleCollection(
			Collection protocolParticipantRoleCollection) {
		this.protocolParticipantRoleCollection = protocolParticipantRoleCollection;
	}

	public ProtocolPhase getProtocolPhase() {
		return protocolPhase;
	}

	public void setProtocolPhase(ProtocolPhase protocolPhase) {
		this.protocolPhase = protocolPhase;
	}

	public Collection getProtocolStatusCollection() {
		return protocolStatusCollection;
	}

	public void setProtocolStatusCollection(Collection protocolStatusCollection) {
		this.protocolStatusCollection = protocolStatusCollection;
	}

	public ProtocolType getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(ProtocolType protocolType) {
		this.protocolType = protocolType;
	}

	public Collection getDiseaseCollection() {
		return diseaseCollection;
	}

	public void setDiseaseCollection(Collection diseaseCollection) {
		this.diseaseCollection = diseaseCollection;
	}

	public Collection getDiseaseSiteCollection() {
		return diseaseSiteCollection;
	}

	public void setDiseaseSiteCollection(Collection diseaseSiteCollection) {
		this.diseaseSiteCollection = diseaseSiteCollection;
	}

    /**
     * Convenience method to return the Protocol Identifier that is designated as
     * primary for the Protocol.
     * 
     * @return
     */
    public ProtocolIdentifier getPrimaryProtocolIdentifier() {
		
		for(ProtocolInstitution pi:protocolInstitutionCollection)
		{
			if(ReferenceDataConstants.PROTOCOL_OWNER.equals(pi.getProtocolInstitutionRole().getCode()))
				return pi.getProtocolIdentifier();
		}
		return null;
    }

	/**
	 * Returns the primary protocol identifier code as string. If there is no primary protocol
	 * identifier associated then it returns empty string 
	 * 
	 * @return
	 */
	public String getPrimaryProtocolIdentifierAsString()
	{	
		ProtocolIdentifier pid = getPrimaryProtocolIdentifier();
		if(pid!=null)
			return pid.getProtocolIdentifierCode();
		else
			return "";
	}
	/**
     * Returns description of associated ProtocolType. If ProtocolType is not present it returns empty string.
	 * 
	 * @return
	 */
	public String getProtocolTypeAsString()
	{
		if(protocolType == null) return "";
	
		return protocolType.getDescription();
	}
	
	/**
     * Returns description of associated ProtocolPhase. If ProtocolPhase is not present it returns empty string.
     * 
	 * @return
	 */
	public String getProtocolPhaseAsString()
	{
		if(protocolPhase == null) return "";
	
		return protocolPhase.getDescription();
	}

	/**
     * Returns true if the protocol has associated clinical study. If not then return false.
	 * 
	 * @return
	 */
	public boolean getHasClinicalStudy()
	{
		if(clinicalStudy == null) return false;
		if("Y".equals(clinicalStudy.getIsC3dProtocol())) return true;
		return false;
	}
    
    /**
     * Business convenience method to return the current Amendment for the Protocol
     * 
     * @return The current amendment for the protocol
     */
    public Amendment getCurrentAmendment() {
    	
    	if (amendmentCollection == null) return null;
    	
    	Collection <Amendment> col= amendmentCollection;
    	Calendar latestDate = new GregorianCalendar(1800, Calendar.DECEMBER, 25); //Set the initial compare date to some very old date
    	Calendar tempCal = new GregorianCalendar();
    	
    	Amendment currentAmendment = null;
    	for (Amendment a : col){
    		tempCal.setTime(a.getDate());
    		if (tempCal.after(latestDate)){
    			currentAmendment = a;
    			latestDate = tempCal;
    			tempCal = new GregorianCalendar();
    		}
    	}
    	
    	return currentAmendment;
    }
 

	/**
	 * Returns the date corresponding to the particular status   
	 * @return
	 */ 
    public Date getProtocolStatusDate(String protocolStatus){
    	if(protocolStatus!=null){
			if(protocolStatusCollection != null){
				Date latestDate = new Date(0);
				for(ProtocolStatus p: protocolStatusCollection){
					if (protocolStatus.equalsIgnoreCase(p.getProtocolStatusCode().getCode())){
						return p.getEffectiveDate();
					}
				}
			}
    	}
		return null;
	}
    
    /**
     * @return
     */
    public String getOpenDateAsString(){
    	Date d = getProtocolStatusDate(ReferenceDataConstants.PROT_STATUS_OPEN);
    	if(d==null) return "";
    	
    	return StringUtils.initString(DateUtil.toString(d,DateUtil.DISPLAY_DATE_FORMAT));
    }
    /**
     * @return
     */
    public String getClosedDateAsString(){
    	Date d = getProtocolStatusDate(ReferenceDataConstants.PROT_STATUS_CLOSED);
    	if(d==null) return "";
    	
    	return StringUtils.initString(DateUtil.toString(d,DateUtil.DISPLAY_DATE_FORMAT));
    }
    /**
     * @return
     */
    public String getTerminatedDateAsString(){
    	Date d = getProtocolStatusDate(ReferenceDataConstants.PROT_STATUS_TERMINATED);
    	if(d==null) return "";
    	
    	return StringUtils.initString(DateUtil.toString(d,DateUtil.DISPLAY_DATE_FORMAT));
    }
    /**
     * @return
     */
    public String getSuspendedDateAsString(){
    	Date d = getProtocolStatusDate(ReferenceDataConstants.PROT_STATUS_SUSPENDED);
    	if(d==null) return "";
    	
    	return StringUtils.initString(DateUtil.toString(d,DateUtil.DISPLAY_DATE_FORMAT));
    }
    /**
     * @return
     */
    public String getIRBApprovedDateAsString(){
    	Date d = getProtocolStatusDate(ReferenceDataConstants.PROT_STATUS_IRB_APPROVED);
    	if(d==null)	return "";
    	
    	return StringUtils.initString(DateUtil.toString(d,DateUtil.DISPLAY_DATE_FORMAT));
    }
    
    /**
     * @return Protocol Status as string.
     */
    public String getCurrentProtocolStatusAsString()
	{
		String status = null;
		ProtocolStatus protocolStatus = getCurrentProtocolStatus();
		if(protocolStatus!=null) return protocolStatus.getProtocolStatusCode().getDescription();
		return StringUtils.initString(status);
	}	
    

    
    /**
     * @return Protocol Phase as string
     */
    public String getCurrentProtocolPhaseAsString(){
    	String phase = null;
    	if(protocolPhase!=null) return protocolPhase.getDescription();
    	return phase;
    }
   

    /**
     * Business convenience method to return the current Protocol Status for the Protocol
     * 
     * @return the current protocol status
     */
    public ProtocolStatus getCurrentProtocolStatus() {
    	
    	if (protocolStatusCollection == null) return null;
    	
    	Collection <ProtocolStatus> col= protocolStatusCollection;
    	Calendar latestDate = new GregorianCalendar(1800, Calendar.DECEMBER, 25); //Set the initial compare date to some very old date
    	Calendar tempCal = new GregorianCalendar();
    	
    	ProtocolStatus currentProtocolStatus = null;
    	for (ProtocolStatus s : col){
    		tempCal.setTime(s.getEffectiveDate());
    		if (tempCal.after(latestDate)){
    			currentProtocolStatus = s;
    			latestDate = tempCal;
    			tempCal = new GregorianCalendar();
    		}
    	}
    	
    	return currentProtocolStatus;
    	
    	
    }

    public String getProtocolSponsorProtocolIdAsString(){
    	
    	String str = "";
    	ProtocolInstitution pi = getProtocolSponsor();
    	
    	if(pi == null) return str;
    	try {
    		return pi.getProtocolIdentifier().getProtocolIdentifierCode();
    	} catch (NullPointerException ex) {
    		//do nothing just return an empty String
    	}
    	return str;
    	
    }

    
    
    public String getProtocolSponsorNameAsString() {
    	String str = "";
    	try {
    	if (getProtocolSponsor() != null) {
    		str = getProtocolSponsor().getInstitution().getName();
    		
    	}
    	} catch (NullPointerException ex){
    		//do nothing just return an empty string
    	}
    	
    	return str;
	}

    
    public ProtocolInstitution getProtocolSponsor() {
    	
    	if (protocolInstitutionCollection == null) return null;
    	
    	Collection <ProtocolInstitution> col = protocolInstitutionCollection;
    	for(ProtocolInstitution p : protocolInstitutionCollection) {
    		if (p.getProtocolInstitutionRole().getCode().equalsIgnoreCase(ReferenceDataConstants.PROTOCOL_SPONSOR)) {
    			return p;
    		}
    			
    	}
    	return null;
    }
    
    
    public String getProtocolMonitorProtocolIdAsString(){
    	
    	String str = "";
    	ProtocolInstitution pi = getProtocolMonitor();
    	
    	if(pi == null) return str;
    	try {
    		return pi.getProtocolIdentifier().getProtocolIdentifierCode();
    	} catch (NullPointerException ex) {
    		//do nothing just return an empty String
    	}
    	return str;
    	
    }

    
    public String getProtocolNavyProtocolIdAsString(){
    	
    	String str = "";
    	ProtocolInstitution pi = getNavyInst();
    	
    	if(pi == null) return str;
    	try {
    		return pi.getProtocolIdentifier().getProtocolIdentifierCode();
    	} catch (NullPointerException ex) {
    		//do nothing just return an empty String
    	}
    	return str;
    	
    }

    
    public String getProtocolMonitorNameAsString() {
    	String str = "";
    	ProtocolInstitution pi = getProtocolMonitor();
    	
    	if(pi == null) return str;
    	try {
    		return pi.getInstitution().getName();
    	} catch (NullPointerException ex) {
    		//do nothing just return an empty String
    	}
    	return str;
    }
 
    public String getProtocolBranchNameAsString() {
    	String str = "";
    	try {
    	if (getProtocolBranch() != null) {
    		str = getProtocolBranch().getInstitution().getName();
    		
    	}
    	} catch (NullPointerException ex){
    		//do nothing just return an empty string
    	}
    	
    	return str;
	}
    

    public ProtocolInstitution getProtocolMonitor() {
    	
    	if (protocolInstitutionCollection == null) return null;
    	
    	Collection <ProtocolInstitution> col = protocolInstitutionCollection;
    	for(ProtocolInstitution p : protocolInstitutionCollection) {
    		if (p.getProtocolInstitutionRole().getCode().equalsIgnoreCase(ReferenceDataConstants.PROTOCOL_MONITOR)) {
    			return p;
    		}
    			
    	}
    		
    	
    	return null;
    }

    public ProtocolInstitution getNavyInst() {
    	
    	if (protocolInstitutionCollection == null) return null;
    	
    	Collection <ProtocolInstitution> col = protocolInstitutionCollection;
    	for(ProtocolInstitution p : protocolInstitutionCollection) {
    		if (p.getProtocolInstitutionRole().getCode().equalsIgnoreCase(ReferenceDataConstants.PROTOCOL_NAVY_INST)) {
    			return p;
    		}
    			
    	}
    		
    	
    	return null;
    }
    
    
    public ProtocolInstitution getProtocolBranch() {
    	
    	if (protocolInstitutionCollection == null) return null;
    	
    	Collection <ProtocolInstitution> col = protocolInstitutionCollection;
    	for(ProtocolInstitution p : protocolInstitutionCollection) {
    		if (p.getInstitution().getOrgTypeCd().equalsIgnoreCase(ReferenceDataConstants.BRANCH)) {
    			return p;
    		}
    			
    	}
    		
    	
    	return null;
    }
    
    public String getProtocolSponsorAsString() {
    	ProtocolInstitution protocolInstitution = getProtocolSponsor();
    	if(protocolInstitution!=null){
    		return protocolInstitution.getProtocolInstitutionRole().getDescription();
    	}else{
    		return "";
    	}
    }
    
    public String getProtocolSponsorIdAsString() {
    	ProtocolInstitution protocolInstitution = getProtocolSponsor();
    	if(protocolInstitution!=null){
    		return protocolInstitution.getProtocolInstitutionRole().getId();
    	}else{
    		return "";
    	}
    }
    
  
    
    @SuppressWarnings("unchecked")
	public Collection<Participant> getPrimaryInvestigators() {
    	
    	if(protocolParticipantRoleCollection == null)return null;
    	
    	Collection<Participant> piColl = new ArrayList();
    	Collection<ProtocolParticipantRole> pprColl = protocolParticipantRoleCollection;
    	for(ProtocolParticipantRole ppr : pprColl){
    		if(ppr.getProtocolRole().getCode().equalsIgnoreCase(ReferenceDataConstants.PRINCIPAL_INVESTIGATOR))
    				piColl.add(ppr.getParticipant());	
    		
    	}
    	return piColl;
    }
    
    /**
     * Convenience method to return all of the names of the PIs for a Protocol seperated by commas
     * @return
     */
    public String getPrimaryInvestigatorNamesAsString(){
    	Collection<Participant> piCol = getPrimaryInvestigators();
    	StringBuffer piNames = new StringBuffer(); 
    	if(piCol!=null){    		
   
	    	for(Participant p : piCol){
	    		if (piNames.length() > 0 )
	    			piNames.append(", ");
	    		piNames.append(StringUtils.initString(p.getFirstName())+" "+StringUtils.initString(p.getLastName()));
	    	}
    	}
    	return String.valueOf(piNames);
    }
    
    /**
     * Convenience method to return all of the names of the PIs for a Protocol seperated by commas
     * @return
     */
    public String getPrimaryInvestigatorNamesWithMultiRecordIndicator(){
    	Collection<Participant> piCol = getPrimaryInvestigators();
    	StringBuffer piNames = new StringBuffer(); 
    	if(piCol!=null ){    		
    		if(piCol.size()>0){
	    		Participant p = piCol.iterator().next();
		    	if(p!=null){
		    		piNames.append(StringUtils.initString(p.getFirstName())+" "+StringUtils.initString(p.getLastName()));
			    	if (piCol.size()> 1 ){
		    			piNames.append(" (+)");
			    	}
		    	}
    		}
    	}

    	return String.valueOf(piNames);
    }
    
    
    public String getEligibilityCriteriaStatusCode(){
    	EligibilityDefinition eligibilityDefinition = getCurrentAmendment().getEligibilityDefinition();
    	if(eligibilityDefinition!=null){
    		EligibilityStatus ecStatus = eligibilityDefinition.getStatus();
    		if(ecStatus!=null){
    			return ecStatus.getCode();
    		}
    	}
    	return "";
    }
 
   
    public String getEligibilityCheckistId(){
    	EligibilityDefinition eligibilityDefinition = getCurrentAmendment().getEligibilityDefinition();
    	if(eligibilityDefinition!=null){
    		return String.valueOf(eligibilityDefinition.getId().intValue());
    	}
    	return "";
    }
    
    
    public String getEligibilityCheckistCrfId(){
    	EligibilityDefinition eligibilityDefinition = getCurrentAmendment().getEligibilityDefinition();
    	if(eligibilityDefinition!=null){
    		return eligibilityDefinition.getCrfId();
    	}
    	return "";
    }



	public String getSystemIdentifier() {
		return systemIdentifier;
	}



	public void setSystemIdentifier(String systemIdentifier) {
		this.systemIdentifier = systemIdentifier;
	}

	public String getLOVDescription(Object param) {
		String description = new String();
		if(this.getCurrentAmendment() != null && this.getCurrentAmendment().getShortTitle() != null){
			description +=this.getCurrentAmendment().getShortTitle(); 
		}
		if(this.getPrimaryProtocolIdentifier() != null && this.getPrimaryProtocolIdentifier().getProtocolIdentifierCode() != null){
			description += "/"+this.getPrimaryProtocolIdentifier().getProtocolIdentifierCode(); 
		}
		return description;
	}

	public String getLOVId() {
		return this.getId().toString();
	}

	public void setLOVFilter(Object param, Object filter) {
	}


}
