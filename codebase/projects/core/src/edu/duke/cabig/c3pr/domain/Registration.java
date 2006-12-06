/*
 * Created Thu Apr 20 17:45:41 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.constants.ReferenceDataConstants;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * A class that represents a row in the 'REGISTRATION' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Registration implements Serializable
{
	private Integer id;
	
    private String comments;
    private Date consentDate;
    private String consentVersionSigned;
    private String consentSignedForCurrentAmendmentFlag;
    private String eligibility;
    private String patientPostionId;
    private Institution registrarBranch;
    private Institution registeringInstitution;
    private String sequenceNumber;

    private String modificationBy;
    private String modificationType;
    private Date modificationDate;
    private String createdBy;
    private Date creationDate;
	
	private Amendment screenedAmendment;
	private ProtocolArm protocolArm;
	private Amendment amendment;
	private Institution institution;
	private FundingSource fundingSource;
	private NotCountTowardsAccrualIndicator notCountTowardsAccrualIndicator;
	private PatientRegistrationInformation patientRegistrationInformation; 
	
	private Collection<DiseaseSite> diseaseSiteCollection;
	private Collection<Disease> diseaseCollection;
	private Collection<ParticipantRegistrationStatus> participantRegistrationStatusCollection;
	private Collection<RegistrationParticipantRole> registrationParticipantRoleCollection;
	private Collection<EligibilityAnswer> eligibilityAnswerCollection;
	/**
     * Simple constructor of Registration instances.
     */
    public Registration()
    {
    }
	public Amendment getAmendment() {
		return amendment;
	}
	public void setAmendment(Amendment amendment) {
		this.amendment = amendment;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getConsentDate() {
		return consentDate;
	}
	public void setConsentDate(Date consentDate) {
		this.consentDate = consentDate;
	}
	public String getConsentSignedForCurrentAmendmentFlag() {
		return consentSignedForCurrentAmendmentFlag;
	}
	public void setConsentSignedForCurrentAmendmentFlag(
			String consentSignedForCurrentAmendmentFlag) {
		this.consentSignedForCurrentAmendmentFlag = consentSignedForCurrentAmendmentFlag;
	}
	public String getConsentVersionSigned() {
		return consentVersionSigned;
	}
	public void setConsentVersionSigned(String consentVersionSigned) {
		this.consentVersionSigned = consentVersionSigned;
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
	public Collection getDiseaseCollection() {
		return diseaseCollection;
	}
	protected void setDiseaseCollection(Collection diseaseCollection) {
		this.diseaseCollection = diseaseCollection;
	}
	public Collection getDiseaseSiteCollection() {
		return diseaseSiteCollection;
	}
	protected void setDiseaseSiteCollection(Collection diseaseSiteCollection) {
		this.diseaseSiteCollection = diseaseSiteCollection;
	}
	public String getEligibility() {
		return eligibility;
	}
	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}
	public FundingSource getFundingSource() {
		return fundingSource;
	}
	public void setFundingSource(FundingSource fundingSource) {
		this.fundingSource = fundingSource;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Institution getInstitution() {
		return institution;
	}
	public void setInstitution(Institution institution) {
		this.institution = institution;
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
	public NotCountTowardsAccrualIndicator getNotCountTowardsAccrualIndicator() {
		return notCountTowardsAccrualIndicator;
	}
	public void setNotCountTowardsAccrualIndicator(
			NotCountTowardsAccrualIndicator notCountTowardsAccrualIndicator) {
		if(notCountTowardsAccrualIndicator!=null)
			notCountTowardsAccrualIndicator.setNotCountTowardsAccrualFlag(true);
		this.notCountTowardsAccrualIndicator = notCountTowardsAccrualIndicator;
	}
	public Collection<ParticipantRegistrationStatus> getParticipantRegistrationStatusCollection() {
		return participantRegistrationStatusCollection;
	}
	protected void setParticipantRegistrationStatusCollection(
			Collection participantRegistrationStatusCollection) {
		this.participantRegistrationStatusCollection = participantRegistrationStatusCollection;
	}
	public String getPatientPostionId() {
		return patientPostionId;
	}
	public void setPatientPostionId(String patientPostionId) {
		this.patientPostionId = patientPostionId;
	}
	public PatientRegistrationInformation getPatientRegistrationInformation() {
		return patientRegistrationInformation;
	}
	public void setPatientRegistrationInformation(
			PatientRegistrationInformation patientRegistrationInformation) {
		this.patientRegistrationInformation = patientRegistrationInformation;
	}
	public ProtocolArm getProtocolArm() {
		return protocolArm;
	}
	public void setProtocolArm(ProtocolArm protocolArm) {
		this.protocolArm = protocolArm;
	}
	public Institution getRegistrarBranch() {
		return registrarBranch;
	}
	public void setRegistrarBranch(Institution registrarBranch) {
		this.registrarBranch = registrarBranch;
	}
	public Collection getRegistrationParticipantRoleCollection() {
		return registrationParticipantRoleCollection;
	}
	protected void setRegistrationParticipantRoleCollection(
			Collection registrationParticipantRoleCollection) {
		this.registrationParticipantRoleCollection = registrationParticipantRoleCollection;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
    public Amendment getScreenedAmendment(){
		return screenedAmendment;
	}
	public void setScreenedAmendment(Amendment screenedAmendment){
		this.screenedAmendment = screenedAmendment;
	}
	public Collection<EligibilityAnswer> getEligibilityAnswerCollection()
	{
		return eligibilityAnswerCollection;
	}
	protected void setEligibilityAnswerCollection(Collection<EligibilityAnswer> eligibilityAnswerCollection)
	{
		this.eligibilityAnswerCollection = eligibilityAnswerCollection;
	}
	public Institution getRegisteringInstitution()
	{
		return registeringInstitution;
	}
	public void setRegisteringInstitution(Institution registeringInstitution)
	{
		this.registeringInstitution = registeringInstitution;
	}
	
	
	public void addEligibilityAnswer(EligibilityAnswer eligibilityAnswer)
	{
    	if(eligibilityAnswerCollection ==  null)
    		eligibilityAnswerCollection = new HashSet<EligibilityAnswer>();
    	
    	Collection<EligibilityAnswer> answersToRemove = new HashSet<EligibilityAnswer>();
    	for(EligibilityAnswer answer:eligibilityAnswerCollection)
    	{
    		if(answer.getQuestionId().equals(eligibilityAnswer.getQuestionId()))
    			answersToRemove.add(answer);
    	}
    	eligibilityAnswerCollection.removeAll(answersToRemove);
    	eligibilityAnswerCollection.add(eligibilityAnswer);
	}
	
	public void addDiseaseSite(DiseaseSite diseaseSite)
    {
    	if(diseaseSiteCollection ==  null)
    		diseaseSiteCollection = new HashSet<DiseaseSite>();
    	
    	diseaseSiteCollection.add(diseaseSite);
    }

    public void addDisease(Disease disease)
    {
    	if(diseaseCollection ==  null)
    		diseaseCollection = new HashSet<Disease>();
    	
    	diseaseCollection.add(disease);
    }

    public void addParticipantRegistrationStatus(ParticipantRegistrationStatus participantRegistrationStatus)
    {
    	if(participantRegistrationStatusCollection ==  null)
    		participantRegistrationStatusCollection = new HashSet<ParticipantRegistrationStatus>();
    	
    	participantRegistrationStatusCollection.add(participantRegistrationStatus);    	
    }
    
    public void addRegistrationParticipantRole(RegistrationParticipantRole registrationParticipantRole)
    {
    	RegistrationParticipantRole existingRole = null;
    	if(registrationParticipantRoleCollection ==  null)
    		registrationParticipantRoleCollection = new HashSet<RegistrationParticipantRole>();
    	else existingRole = searchRegistrationParticipantRole(registrationParticipantRole);
    	
    	if(existingRole == null)
    	{
    		registrationParticipantRole.setRegistration(this);
    		registrationParticipantRoleCollection.add(registrationParticipantRole);
    	}
    }

    public void removeRegistrationParticipantRole(RegistrationParticipantRole registrationParticipantRole)
    {
    	RegistrationParticipantRole existingRole = null;
    	if(registrationParticipantRoleCollection ==  null)
    		registrationParticipantRoleCollection = new HashSet<RegistrationParticipantRole>();
    	else existingRole = searchRegistrationParticipantRole(registrationParticipantRole);
    	
    	if(existingRole != null)
    		registrationParticipantRoleCollection.remove(existingRole);
    }

    private RegistrationParticipantRole searchRegistrationParticipantRole(RegistrationParticipantRole registrationParticipantRole)
	{
		RegistrationParticipantRole existingRole = null;
		if(registrationParticipantRoleCollection == null) return null;
		for(RegistrationParticipantRole role: registrationParticipantRoleCollection)
		{
			if(role.getParticipant().getId().equals(registrationParticipantRole.getParticipant().getId()))
				if(role.getRegistrationRole().getId().equals(registrationParticipantRole.getRegistrationRole().getId()))
					return role;
		}
		return null;
	}
	/**
	 * Returns the Date that the current status of the Registration became effective.
	 * 
	 * @return
	 */
	/*public Date getCurrentRegistrationStatusDate()
	{
		RegistrationStatus status = null;
		Date latestDate = null;
		if(participantRegistrationStatusCollection != null)
		{
			latestDate = new Date(0);
			for(ParticipantRegistrationStatus s:participantRegistrationStatusCollection)
			{
				if ((s.getEffectiveDate()!=null) && (latestDate.before(s.getEffectiveDate())))
				{
					latestDate = s.getEffectiveDate();
					status = s.getRegistrationStatus();
				}
			}
		}
		return latestDate;
	}*/

	/**
	 * 
	 * Returns the code of the RegistrationStatus corresponding to the latest effectiveDate. 
	 * If the effectiveDate is null for the status then the date is discarded.
	 *   
	 * @return
	 */
	/*public RegistrationStatus getCurrentRegistrationStatus()
	{
		RegistrationStatus status = null;
		if(participantRegistrationStatusCollection != null)
		{
			Date latestDate = new Date(0);
			for(ParticipantRegistrationStatus s:participantRegistrationStatusCollection)
			{
				if ((s.getEffectiveDate()!=null) && (latestDate.before(s.getEffectiveDate())))
				{
					latestDate = s.getEffectiveDate();
					status = s.getRegistrationStatus();
				}
			}
		}
		return status;
	}*/
	
	
	/**
	 * Convenience method to get the Data that a Registration went into a particular status.
	 * 
	 * @param status
	 * @return  
	 */
	/*public String getRegistrationStatusEffectiveDateAsString(String status){
		
		Date dte = getRegistrationStatusEffectiveDate(status);
		return StringUtils.initString(DateUtil.toString(dte,DateUtil.DISPLAY_DATE_FORMAT));
		
		
	}*/
	
	/**
	 * Convenience method to get the Date that a Registration was in a particular status
	 * 
	 * @param status
	 * @return Date - The effective date of the status passed in the interface
	 */
	/*public Date getRegistrationStatusEffectiveDate(String status){
		
		if(participantRegistrationStatusCollection == null || status == null ||status.length() == 0)
			return null;
		
		for(ParticipantRegistrationStatus s:participantRegistrationStatusCollection)
		{
			if (s.getEffectiveDate() != null && status.equalsIgnoreCase(s.getRegistrationStatus().getCode()))
					return s.getEffectiveDate();
		}
		
		return null; //if requested status not found
	
	}*/

	/**
	 * Convenience method to get the code of the reason a Registration went into a particular status
	 * 
	 * @param status
	 * @return String 
	 */
	/*public String getRegistrationStatusReasonCode(String status){
		
		if(participantRegistrationStatusCollection == null || status == null ||status.length() == 0)
			return null;
		
		for(ParticipantRegistrationStatus s:participantRegistrationStatusCollection)
		{
			if (s.getEffectiveDate() != null && s.getRegistrationStatus()!= null && status.equalsIgnoreCase(s.getRegistrationStatus().getCode()))
					if (s.getReason() != null)		
						return s.getReason().getCode();
					 else 
						 return "";
		}
		
		return null; //if requested status not found
	
	}*/

	/**
	 * Convenience method to get the description of the reason a Registration went into a particular status
	 * 
	 * @param status
	 * @return String 
	 */
	/*public String getRegistrationStatusReasonAsString(String status){
		
		if(participantRegistrationStatusCollection == null || status == null ||status.length() == 0)
			return null;
		
		for(ParticipantRegistrationStatus s:participantRegistrationStatusCollection)
		{
			if (s.getEffectiveDate() != null && s.getRegistrationStatus()!= null && status.equalsIgnoreCase(s.getRegistrationStatus().getCode()))
					if (s.getReason() != null)		
						return s.getReason().getDescription();
					 else 
						 return "";
		}
		
		return null; //if requested status not found
	
	}*/
	
	/**
	 * Returns current ParticipantRegistrationStatus. Current status is determined by the status 
	 * which has the most recent effective date.
	 * 
	 * @return
	 */
	/*public ParticipantRegistrationStatus getCurrentParticipantRegistrationStatus()
	{
		ParticipantRegistrationStatus status = null;
		if(participantRegistrationStatusCollection != null)
		{
			Date latestDate = new Date(0);
			for(ParticipantRegistrationStatus s:participantRegistrationStatusCollection)
			{
				if ((s.getEffectiveDate()!=null) && (latestDate.before(s.getEffectiveDate())))
				{
					latestDate = s.getEffectiveDate();
					status = s;
				}
			}
		}
		return status;
	}*/
		

	/*public String getCurrentRegistrationStatusAsString()
	{
		String status = null;
		RegistrationStatus rStatus = getCurrentRegistrationStatus();
		if(rStatus!=null) return rStatus.getDescription();
		return status;
	}*/


	/**
	 * Returns current ParticipantRegistrationStatus. Current status is determined by the status Hierarchy. 
	 * The Hierarchy is as follows Pending < Registered < Treatment < Off Treatment < Off Study < Closed Not Counted
	 * 
	 * @return ParticipantRegistrationStatus
	 */	
	public ParticipantRegistrationStatus getCurrentParticipantRegistrationStatus(){
		String currentRegistrationStatusCode = null;
		if(getParticipantRegistrationStatusCollection() != null){
			String registrationStatusCode = null;
			int registrationStatusLevel = 0;
			int currentRegsistrationStatusLevel = 0;
			//Finding the current registration status level
			for(ParticipantRegistrationStatus s: getParticipantRegistrationStatusCollection()){
				registrationStatusCode = s.getRegistrationStatus().getCode();
				if(ReferenceDataConstants.REG_STATUS_PENDING.equals(registrationStatusCode)){
					registrationStatusLevel = 1;
				}else if(ReferenceDataConstants.REG_STATUS_REGISTERED.equals(registrationStatusCode)){
					registrationStatusLevel = 2;
				}else if(ReferenceDataConstants.REG_STATUS_TREATMENT.equals(registrationStatusCode)){
					registrationStatusLevel = 3;
				}else if(ReferenceDataConstants.REG_STATUS_OFF_TREATMENT.equals(registrationStatusCode)){
					registrationStatusLevel = 4;
				}else if(ReferenceDataConstants.REG_STATUS_OFF_STUDY.equals(registrationStatusCode)){
					registrationStatusLevel = 5;
				}else if(ReferenceDataConstants.REG_STATUS_CLOSED_NOT_COUNTED.equals(registrationStatusCode)){
					registrationStatusLevel = 6;
				}
				
				if(currentRegsistrationStatusLevel < registrationStatusLevel){
					currentRegsistrationStatusLevel = registrationStatusLevel;
				}
			}
			//Finding the current registration status code
			switch(currentRegsistrationStatusLevel){
				case 1:
					currentRegistrationStatusCode = ReferenceDataConstants.REG_STATUS_PENDING;
					break;
				case 2:
					currentRegistrationStatusCode = ReferenceDataConstants.REG_STATUS_REGISTERED;
					break;
				case 3:
					currentRegistrationStatusCode = ReferenceDataConstants.REG_STATUS_TREATMENT;
					break;
				case 4:
					currentRegistrationStatusCode = ReferenceDataConstants.REG_STATUS_OFF_TREATMENT;
					break;
				case 5:
					currentRegistrationStatusCode = ReferenceDataConstants.REG_STATUS_OFF_STUDY;
					break;
				case 6:
					currentRegistrationStatusCode = ReferenceDataConstants.REG_STATUS_CLOSED_NOT_COUNTED;
					break;
			}


		}
		return getParticipantRegistrationStatus(currentRegistrationStatusCode);
	}
	
	
	/**
	 * Returns the Date that the current status of the Registration became effective.
	 */
	public Date getCurrentRegistrationStatusDate(){		
		Date latestDate = getCurrentParticipantRegistrationStatus().getEffectiveDate();
		return latestDate;
	}

	/**
	 * Returns the Current RegistrationStatus.
	 */
	public RegistrationStatus getCurrentRegistrationStatus(){
		return getCurrentParticipantRegistrationStatus().getRegistrationStatus();
	}

	/**
	 * Returns the registration status effective date as String for the given Status Code, 
	 * If the registration status is not found for the given Status Code then returns the null.
	 */
	public String getRegistrationStatusEffectiveDateAsString(String statusCode){
		ParticipantRegistrationStatus participantRegistrationStatus = getParticipantRegistrationStatus(statusCode);
		if(participantRegistrationStatus != null)
			return StringUtils.initString(DateUtil.toString(participantRegistrationStatus.getEffectiveDate(),DateUtil.DISPLAY_DATE_FORMAT));
		else
			return null;
	}

	/**
	 * Returns the registration status effective date for the given Status Code, 
	 * If the registration status is not found for the given Status Code then returns the null.
	 */
	public Date getRegistrationStatusEffectiveDate(String statusCode){
		ParticipantRegistrationStatus participantRegistrationStatus = getParticipantRegistrationStatus(statusCode);
		if(participantRegistrationStatus != null)
			return participantRegistrationStatus.getEffectiveDate();
		else
			return null;			
	}

	/**
	 * Returns the registration status reason code for the given Status Code, 
	 * If the registration status is not found for the given Status Code then returns the null.
	 */
	public String getRegistrationStatusReasonCode(String statusCode){		
		ParticipantRegistrationStatus participantRegistrationStatus = getParticipantRegistrationStatus(statusCode);
		if(participantRegistrationStatus != null)
			return participantRegistrationStatus.getReason().getCode();
		else
			return null;	
	}

	/**
	 * Returns the registration status reason as String for the given Status Code, 
	 * If the registration status is not found for the given Status Code then returns the null.
	 */
	public String getRegistrationStatusReasonAsString(String statusCode){
		ParticipantRegistrationStatus participantRegistrationStatus = getParticipantRegistrationStatus(statusCode);
		if(participantRegistrationStatus != null)
			return participantRegistrationStatus.getReason().getDescription();
		else
			return null;
	}
	
	/**
	 * Returns the current registration status as String
	 */
	public String getCurrentRegistrationStatusAsString(){
		return getCurrentRegistrationStatus().getDescription();
	}

	/**
	 * Returns the current registration status code as String
	 */
	public String getCurrentRegistrationStatusCodeAsString(){
		return getCurrentRegistrationStatus().getCode();
	}
	
	/**
	 * Returns the current ParticipantRegisrationStatus
	 */
	public ParticipantRegistrationStatus getParticipantRegistrationStatus(String statusCode){
		ParticipantRegistrationStatus currentParticipantRegistrationStatus = null;
		for(ParticipantRegistrationStatus s: getParticipantRegistrationStatusCollection()){
			if(statusCode.equals(s.getRegistrationStatus().getCode())){
				currentParticipantRegistrationStatus = s;
				break;
			}
		}
		return currentParticipantRegistrationStatus;
	}
	
	/**
	 * Returns primary ProtocolIdentifier object by invoking a same method on the protocol object. 
	 * If there are no protocol associated with the registration then it returns null.
	 * 
	 * @return
	 */
	public ProtocolIdentifier getPrimaryProtocolIdentifier()
	{	
		if(amendment!=null && amendment.getProtocol()!=null)
			return amendment.getProtocol().getPrimaryProtocolIdentifier();
		else
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
	 * If the registration has associated Arm, it returns the code of the ProtocolArm
	 * 
	 * @return
	 */
	public String getProtocolArmIdentifier()
	{	
		if(protocolArm!=null)
			return protocolArm.getCode();
		else
			return "";
	}

	/**
	 * If the registration has associated Protocol Amendment then it returns amendment attribute
	 * of the Protocol Amendmement object
	 * 
	 * @return
	 */
	public String getProtocolAmendmentIdentifier()
	{	
		if(amendment!=null)
			return amendment.getAmendment();
		else
			return "";
	}
	
	/**
	 * @return
	 */
	public boolean getHasClinicalStudy()
	{
		if(amendment==null || amendment.getProtocol()==null)
			return false;
		return amendment.getProtocol().getHasClinicalStudy();
	}
	
	public Participant getAssociatedPatient()
	{
		if(registrationParticipantRoleCollection == null) return null;
		
		for(RegistrationParticipantRole r:registrationParticipantRoleCollection)
		{
			if(ReferenceDataConstants.REG_ROLE_PATIENT.equals(r.getRegistrationRole().getCode()))
				return r.getParticipant();
		}
		return null;
	}

	/**
	 * Returns the Primary Protocol Identifier Code and Short Title of the Screening Protocol.
	 * 
	 * If there are no Screening Protocols associated then it returns empty string
	 * 
	 * @return
	 */
	public String getScreenedProtocolAsString() {
		if(screenedAmendment == null || screenedAmendment.getProtocol() == null) return "";
		
		return screenedAmendment.getProtocol().getProtocolTypeAsString()+" / "+screenedAmendment.getProtocol().getPrimaryProtocolIdentifierAsString();
	}

	/**
	 * Returns the Primary Id Code of the first associated Participant in PI role. If there are 
	 * more than one such Participants associated, it attaches the "(+)" text to the end of the first
	 * Primary Identifier Code and returns it.
	 * 
	 * If there are no associated Participants in PI role it returns empty string
	 * @return
	 */
	public String getAssociatedPiAsString()
	{
		String result="";
		if(registrationParticipantRoleCollection == null) return result;
		
		
		for(RegistrationParticipantRole r:registrationParticipantRoleCollection)
		{
			if(ReferenceDataConstants.PRINCIPAL_INVESTIGATOR.equals(r.getRegistrationRole().getCode()))
			{
				if("".equals(result))
					result =  r.getParticipant().getFullName()+" ";
				else
					return result+" (+)";
			}
		}
		return result;
	}

	/**
	 * Returns the Name of the first associated Participant in treating physician role. If there are 
	 * more than one such Participants associated, it attaches the "(+)" text to the end of the first
	 * Primary Identifier Code and returns it.
	 * 
	 * If there are no associated Participants in treating physician role it returns empty string
	 * @return
	 */
	public String getAssociatedTreatingPhysicianAsString()
	{
		String result="";
		if(registrationParticipantRoleCollection == null) return result;
		
		
		for(RegistrationParticipantRole r:registrationParticipantRoleCollection)
		{
			if(ReferenceDataConstants.TREATING_PHYSICIAN.equals(r.getRegistrationRole().getCode()))
			{
				if("".equals(result))
					result =  r.getParticipant().getFullName()+" ";
				else
					return result+" (+)";
			}
		}
		return result;
	}
	
	/**
	 * Returns the name code and description of the first associated disease. If there are 
	 * more than one diseases associated, it attaches the "(+)" text to the end of the first
	 * disease's description and returns it.
	 * 
	 * If there are no associated disease it returns empty string
	 * 	 * @return
	 */
	public String getDiseaseAsString()
	{
		String result="";
		if(diseaseCollection == null) return result;
		
		for(Disease d:diseaseCollection)
		{
			if("".equals(result))
				result =  d.getDescription()+" / "+d.getCode();
			else
				return result+" (+)";
		}
		return result;
	}

	/**
	 * Returns the code of the first associated disease. 
	 * If there are no associated disease it returns empty string
	 * 	 * @return
	 */
	public String getDiseaseCodeAsString(){
		String result="";
		if(diseaseCollection == null) return result;
		
		for(Disease d:diseaseCollection){
			if("".equals(result))
				result =  d.getCode();
		}
		return result;
	}
	
	/**
	 * Returns the name code and description of the first associated disease site. If there are 
	 * more than one disease sites associated, it attaches the "(+)" text to the end of the first
	 * disease site's description and returns it.
	 * 
	 * If there are no associated disease sites it returns empty string
	 * 
	 * @return
	 */
	public String getDiseaseSiteAsString()
	{
		String result="";
		if(diseaseSiteCollection == null) return result;
		
		for(DiseaseSite d:diseaseSiteCollection)
		{
			if("".equals(result))
				result =  d.getDescription();
			else
				return result+" (+)";
		}
		return result;
	}

	/**
	 * Returns the code of the first associated disease site. 
	 * If there are no associated disease sites it returns empty string
	 * 
	 * @return
	 */
	public String getDiseaseSiteCodeAsString()
	{
		String result="";
		if(diseaseSiteCollection == null) return result;
		
		for(DiseaseSite d:diseaseSiteCollection){
			if("".equals(result))
				result =  d.getCode();
		}
		return result;
	}	
	
	public void addRegistrationParticipantIdentifier(
			RegistrationParticipantIdentifier registrationParticipantIdentifier) {
		registrationParticipantIdentifier.setRegistration(this);
		patientRegistrationInformation.addRegistrationParticipantIdentifier(registrationParticipantIdentifier);
	}	
	
	
	public Protocol getProtocol()
	{
		if(amendment == null) return null;
		return amendment.getProtocol();
	}

	public Protocol getScreenedProtocol()
	{
		if(screenedAmendment == null) return null;
		return screenedAmendment.getProtocol();
	}

	
	public Collection getAssociatedPICollection()
	{
		if(registrationParticipantRoleCollection == null || registrationParticipantRoleCollection.size()==0)
			return (Collection) new HashSet();
		Collection<RegistrationParticipantRole> result = new HashSet<RegistrationParticipantRole>();
		for(RegistrationParticipantRole role :registrationParticipantRoleCollection)
		{	
			if(ReferenceDataConstants.PRINCIPAL_INVESTIGATOR.equals(role.getRegistrationRole().getCode()))
				result.add(role);
		}
		return result;
	}

	public Collection getAssociatedTreatingPhysicianCollection()
	{
		if(registrationParticipantRoleCollection == null || registrationParticipantRoleCollection.size()==0)
			return (Collection) new HashSet();
		Collection<RegistrationParticipantRole> result = new HashSet<RegistrationParticipantRole>();
		for(RegistrationParticipantRole role :registrationParticipantRoleCollection)
		{	
			if(ReferenceDataConstants.TREATING_PHYSICIAN.equals(role.getRegistrationRole().getCode()))
				result.add(role);
		}
		return result;
	}
	
	public void addTreatmentDate(Date date)
	{
		ParticipantRegistrationStatus oldStatus = getParticipantRegistrationStatus(ReferenceDataConstants.REG_STATUS_TREATMENT);
		if(oldStatus == null)
		{
			ParticipantRegistrationStatus status = new ParticipantRegistrationStatus();
			RegistrationStatus regStatus = new RegistrationStatus(ReferenceDataConstants.REG_STATUS_TREATMENT);
			status.setRegistrationStatus(regStatus);
			status.setEffectiveDate(date);
			addParticipantRegistrationStatus(status);
		}
		else
		{
			oldStatus.setEffectiveDate(date);
		}
	}
	
	public void removeDiseaseCollection()
	{
		if(diseaseCollection == null) return;
		diseaseCollection.clear();
	}
	public void removeDiseaseSiteCollection()
	{
		if(diseaseSiteCollection == null) return;
		diseaseSiteCollection.clear();
	}
	
	public String getProtocolSystemIdentifier(){
		if(amendment==null) return null;
		if(amendment.getProtocol()==null) return null;
		return amendment.getProtocol().getSystemIdentifier();
	}
}