/*
 * Created Thu Apr 20 17:45:39 EDT 2006 by MyEclipse Hibernate Tool.
 */ 
package edu.duke.cabig.c3pr.domain;

import edu.duke.cabig.c3pr.utils.Constants;
import edu.duke.cabig.c3pr.utils.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * A class that represents a row in the 'PERSON' table. 
 * This class may be customized as it is never re-generated 
 * after being created.
 */
public class Participant  implements Serializable
{
    private Integer id;
    private Title title;
    private String firstName;
    private String lastName;
    private String middleName;
    private Suffix suffix;
    private Date birthDate;
    private Gender gender;
    private Ethnicity ethnicity;
    private Date deathDate;
    private String ssn;
    private Date dateFirstSeen;
    private String status;
    private String createdBy;
    private Date creationDate;
    private String modificationType;
    private Date modificationDate;
    private String modificationBy;
    private String activeInactiveFlag;

    private Collection<ParticipantIdentifier> participantIdentifierCollection;
    private Collection<RegistrationParticipantRole> registrationParticipantRoleCollection;
    private Collection<ParticipantAddress> participantAddressCollection;
    private Collection<ParticipantContact> participantContactCollection;
    private Collection<DiseaseSite> diseaseSiteCollection;
    private Collection<Disease> diseaseCollection;
    private Collection<Specialty> specialtyCollection;
    private Collection<Race> raceCollection;
    private Collection<ParticipantRole> participantRoleCollection;
    private Collection<RelatedParticipant> relatedParticipantCollection;
    
    /**
	 * @param id
	 * @param title
	 * @param firstName
	 * @param lastName
	 * @param middleName
	 * @param suffix
	 * @param birthDate
	 * @param gender
	 * @param ethnicity
	 * @param deathDate
	 * @param ssn
	 * @param dateFirstSeen
	 * @param status
	 * @param activeInactiveFlag
	 */
	public Participant(Integer id, Title title, String firstName, String lastName, String middleName, Suffix suffix, Date birthDate, Gender gender, Ethnicity ethnicity, Date deathDate, String ssn, Date dateFirstSeen, String status, String activeInactiveFlag) {
		super();
		// TODO Auto-generated constructor stub
		this.id = id;
		this.title = title;
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.suffix = suffix;
		this.birthDate = birthDate;
		this.gender = gender;
		this.ethnicity = ethnicity;
		this.deathDate = deathDate;
		this.ssn = ssn;
		this.dateFirstSeen = dateFirstSeen;
		this.status = status;
		this.activeInactiveFlag = activeInactiveFlag;
	
	}



	/**
     * Simple constructor of Participant instances.
     */
    public Participant()
    {
    }


    
    public Ethnicity getEthnicity() {
		return ethnicity;
	}

    public void setEthnicity(Ethnicity ethnicity) {
		this.ethnicity = ethnicity;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Suffix getSuffix() {
		return suffix;
	}

	public void setSuffix(Suffix suffix) {
		this.suffix = suffix;
	}

	public Collection<ParticipantAddress> getParticipantAddressCollection() {
		return participantAddressCollection;
	}

	protected void setParticipantAddressCollection(Collection participantAddressCollection) {
		this.participantAddressCollection = participantAddressCollection;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Collection<ParticipantContact> getParticipantContactCollection() {
		return participantContactCollection;
	}

	protected void setParticipantContactCollection(Collection participantContactCollection) {
		this.participantContactCollection = participantContactCollection;
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

	public Date getDateFirstSeen() {
		return dateFirstSeen;
	}

	public void setDateFirstSeen(Date dateFirstSeen) {
		this.dateFirstSeen = dateFirstSeen;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
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

	public String getActiveInactiveFlag() {
		return activeInactiveFlag;
	}

	public void setactiveInactiveFlag(String activeInactiveFlag) {
		this.activeInactiveFlag = activeInactiveFlag;
	}
	
	public Collection<ParticipantIdentifier> getParticipantIdentifierCollection() {
		return participantIdentifierCollection;
	}
	


	
	/**
	 * 
	 * Convenience method to return the PrimaryIdentifier of the Participant
	 * @return ParticipantIdentifier
	 */
	public ParticipantIdentifier getPrimaryIdentifier() {
		if (participantIdentifierCollection == null || participantIdentifierCollection.size() == 0)
			return null;
		
		for (ParticipantIdentifier pi : participantIdentifierCollection) {
			if(pi.isPrimary())
				return pi;
		}
		
		return null;
	}

	public void setParticipantIdentifierCollection(
			Collection participantIdentifierCollection) {
		this.participantIdentifierCollection = participantIdentifierCollection;
	}

	public Collection<ParticipantRole> getParticipantRoleCollection() {
		return participantRoleCollection;
	}

	public void setParticipantRoleCollection(Collection participantRoleCollection) {
		this.participantRoleCollection = participantRoleCollection;
	}

	/**
	 * Convenience method add to add a ParticipantRole to the ParticipantRoleCollection.
	 * If ther ParticipantRoleCollection is null, it will be instantiated and added to.
	 * 
	 * @param participantRole
	 */
	public void addParticipantRole(ParticipantRole participantRole) {
		
		if (participantRole == null)
			return;
		
		if (participantRoleCollection == null)
			participantRoleCollection = new HashSet();
				
		participantRoleCollection.add(participantRole);
	}

	

	public Collection<Race> getRaceCollection() {
		return raceCollection;
	}

	protected void setRaceCollection(Collection raceCollection) {
		this.raceCollection = raceCollection;
	}

	public Collection getRegistrationParticipantRoleCollection() {
		return registrationParticipantRoleCollection;
	}

	protected void setRegistrationParticipantRoleCollection(
			Collection registrationParticipantRoleCollection) {
		this.registrationParticipantRoleCollection = registrationParticipantRoleCollection;
	}

	public Collection<RelatedParticipant> getRelatedParticipantCollection() {
		return relatedParticipantCollection;
	}

	public void setRelatedParticipantCollection(
			Collection relatedParticipantCollection) {
		this.relatedParticipantCollection = relatedParticipantCollection;
	}

	public Collection getSpecialtyCollection() {
		return specialtyCollection;
	}

	public void setSpecialtyCollection(Collection specialityCollection) {
		this.specialtyCollection = specialityCollection;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}
	
	
	public boolean isParticipateInRole(String role)
	{
		if(role == null || participantRoleCollection == null) return false;
		
		for(ParticipantRole r:participantRoleCollection)
		{
			if(role.equals(r.getCode()))
				return true;
		}
		return false;
	}


	private void addParticipantIdentifier(ParticipantIdentifier participantIdentifier)
	{
		if(participantIdentifier == null) return;
		
		if(participantIdentifierCollection == null)
			participantIdentifierCollection = new HashSet<ParticipantIdentifier>();
		
		
		participantIdentifierCollection.add(participantIdentifier);
	}
	
	public void addRegistrationParticipantRole(RegistrationParticipantRole registrationParticipantRole)
	{
		if(registrationParticipantRole == null) return;
		
		if(registrationParticipantRoleCollection == null)
			registrationParticipantRoleCollection = new HashSet<RegistrationParticipantRole>();
		
		registrationParticipantRole.setParticipant(this);
		registrationParticipantRoleCollection.add(registrationParticipantRole);
	}
	
	public void addParticipantAddress(ParticipantAddress participantAddress)
	{
		if(participantAddress == null) return;
		
		if(participantAddressCollection == null)
			participantAddressCollection = new HashSet<ParticipantAddress>();
		
		participantAddress.setParticipant(this);
		participantAddressCollection.add(participantAddress);
	}

	public void addParticipantContact(ParticipantContact participantContact)
	{
		if(participantContact == null) return;
		
		if(participantContactCollection == null)
			participantContactCollection = new HashSet<ParticipantContact>();
		
		participantContact.setParticipant(this);
		participantContactCollection.add(participantContact);
	}

	public void addDiseaseSite(DiseaseSite diseaseSite)
	{
		if(diseaseSite == null) return;
		
		if(diseaseSiteCollection == null)
			diseaseSiteCollection = new HashSet<DiseaseSite>();
		
		diseaseSiteCollection.add(diseaseSite);
	}
	
	public void addDisease(Disease disease)
	{
		if(disease == null) return;
		
		if(diseaseCollection == null)
			diseaseCollection = new HashSet<Disease>();
		
		diseaseCollection.add(disease);
	}
	
	public void addSpecialty(Specialty specialty)
	{
		if(specialty == null) return;
		
		if(specialtyCollection == null)
			specialtyCollection = new HashSet<Specialty>();
		
		specialtyCollection.add(specialty);
	}

	public void addRace(Race race)
	{
		if(race == null) return;
		
		if(raceCollection == null)
			raceCollection = new HashSet<Race>();
		
		raceCollection.add(race);
	}

    public void addRelatedParticipant(RelatedParticipant relatedParticipant)
    {
		if(relatedParticipant == null) return;
    	
		if(relatedParticipantCollection == null)
			relatedParticipantCollection = new HashSet<RelatedParticipant>();
		
		relatedParticipant.setParticipant(this);
		relatedParticipantCollection.add(relatedParticipant);
    }
    
    public void addPrimaryIdentifier(ParticipantIdentifier participantIdentifier){
    	if(participantIdentifier == null) return;
    	
    	participantIdentifier.setPrimaryIDFlag(Constants.TRUE);
    	
    	addParticipantIdentifier(participantIdentifier);
    }
    
    public void addOtherIdentifier(ParticipantIdentifier participantIdentifier){
    	if(participantIdentifier == null) return;
    	
    	participantIdentifier.setPrimaryIDFlag(Constants.FALSE);
    	
    	addParticipantIdentifier(participantIdentifier);
    }    

    
    /**
     * Convinience method that returns all the associated races' description seperated
     * by comma (,) 
     * 
     * If no race is associated then it returns the empty string 
     *  
     * @return
     */
    public String getRaceCollectionAsString()
    {
    	String races = "";
    	if(raceCollection == null) return races;
    	for(Race r: raceCollection)
    	{
    		String seperator = ", ";
    		if("".equals(races)) seperator = "";
    		races = races +seperator+ r.getDescription();
    	}
    	return races;
    }

    /**
     * Convinience method that returns all the associated specialty's description seperated
     * by comma (,) 
     * 
     * If no specialty is assocuiated then it returns empty string
     * 
     * @return
     */
    public String getSpecialtyCollectionAsString()
    {
    	String specialties = "";
    	if(specialtyCollection == null) return specialties;
    	for(Specialty s: specialtyCollection)
    	{
    		String seperator = ", ";
    		if("".equals(specialties)) seperator = "";
    		specialties = specialties +seperator+ s.getDescription();
    	}
    	return specialties;
    }
    
    /**
     * Returns description of associated gender. If gender is not present it returns empty string.
     * 
     * @return
     */
    public String getGenderAsString()
    {
    	if(gender == null) return "";
    	
    	return gender.getDescription();
    }

    /**
     * Returns description of associated ethnicity. If ethnicity is not present it returns empty string.
     * 
     * @return
     */
    public String getEthnicityAsString()
    {
    	if(ethnicity == null) return "";
    	
    	return ethnicity.getDescription();
    }

    /**
     * Returns description of associated suffix. If suffix is not present it returns empty string.
     * 
     * @return
     */
    public String getSuffixAsString()
    {
    	if(suffix == null) return "";
    	
    	return suffix.getDescription();
    }
    
    public Address getPreferredAddress()
    {
    	Address address = null;
    	
    	for(ParticipantAddress pAddress:participantAddressCollection)
    	{
    		if("Y".equals(pAddress.getPreferredFlag()))
    				return pAddress.getAddress();
    	}
    	
    	return address;
    }
    
	/**
	 * 
	 * Convenience method to return the Collection of the OtherIdentifier for the Participant
	 * @return Collection
	 */
	public Collection<ParticipantIdentifier> getOtherIdentifiers() {
		Collection<ParticipantIdentifier> otherIdentifiersCol = new HashSet<ParticipantIdentifier>();
		if (participantIdentifierCollection != null){
			for (ParticipantIdentifier pi : participantIdentifierCollection) {
				if(!pi.isPrimary())
					otherIdentifiersCol.add(pi);
			}
		}		
		return otherIdentifiersCol;
	}

    /**
     * Returns Full name of the participant.
     * 
     * @return String, concordination of the Title Lastname Firstname Middlename Suffix.
     */    
    public String getFullName(){
    	String fullName = "";
    	
    	if(getTitle() != null && !getTitle().equals("")){
    		Title title = getTitle();
    		fullName = title.getCode();
    	}
    	
    	if(getFirstName() != null && !getFirstName().equals("")){
    		if(fullName.equals("")){
    			fullName = getFirstName();
    		}else{
    			fullName = fullName + " " + getFirstName();
    		}
    	}    	

    	if(getMiddleName() != null && !getMiddleName().equals("")){
    		if(fullName.equals("")){
    			fullName = getMiddleName();
    		}else{
    			fullName = fullName + " " + getMiddleName();
    		}
    	}
    	
    	if(getLastName() != null && !getLastName().equals("")){
    		if(fullName.equals("")){
    			fullName = getLastName();
    		}else{
    			fullName = fullName + " " + getLastName();
    		}
    	}
    	
    	if(getSuffix() != null && !getSuffix().equals("")){
    		if(fullName.equals("")){
    			fullName = getSuffixAsString();
    		}else{
    			fullName = fullName + " " + getSuffixAsString();
    		}
    	}
    	
    	return fullName;
    } 
    
	public String getIdAsString() {
		if(id==null)
			return "";
		
		return id.toString();
	} 
	
	public String getPrimaryIdAsString(){
		ParticipantIdentifier personIdentifier = getPrimaryIdentifier();
		if(personIdentifier !=null)
			return personIdentifier.getIdentifierCode();
		
		return "";		
	}
	
	public String getPrimaryIdInstitutionIdAsString(){
		ParticipantIdentifier personIdentifier = getPrimaryIdentifier();
		if(personIdentifier != null){
			Institution institution = personIdentifier.getInstitution();
			if(institution != null)
				return institution.getId().toString();
		}
		
		return "";		
	}	

	public String getPrimaryIdInstitutionCodeAsString(){
		ParticipantIdentifier personIdentifier = getPrimaryIdentifier();
		if(personIdentifier != null){
			Institution institution = personIdentifier.getInstitution();
			if(institution != null)
				return institution.getCode();
		}
		
		return "";		
	}
	
	public String getPrimaryIdInstitutionDescriptionAsString(){
		ParticipantIdentifier personIdentifier = getPrimaryIdentifier();
		if(personIdentifier != null){
			Institution institution = personIdentifier.getInstitution();
			if(institution != null)
				return institution.getDescription();
		}
		
		return "";		
	}
	
	/**
	 * This method returns the Collection of Strings with 
	 * OtherIdentifierCode and OtherIdentifierInstitutionDescription with : separated.
	 */
	public Collection<String> getOtherIdsAndInstitutionDescriptionAsCollection(){
		Collection<String> otherIdsAndInstitutionDescCol = new ArrayList<String>();
		Collection<ParticipantIdentifier> otherIdentifiersCol = getOtherIdentifiers();
		String otherIdAndInstDescStr = "";
		for(ParticipantIdentifier pi : otherIdentifiersCol){			
			if(pi.getInstitution() != null){
				if(pi.getIdentifierCode() == null){
					otherIdAndInstDescStr = " :"+pi.getInstitution().getDescription();
				}else{
					otherIdAndInstDescStr = pi.getIdentifierCode()+":"+pi.getInstitution().getDescription();
				}
				otherIdsAndInstitutionDescCol.add(otherIdAndInstDescStr);
			}			
		}
		return otherIdsAndInstitutionDescCol;
	}
	
    /**
     * Returns code of associated title. If title is not present it returns empty string.
     * 
     * @return
     */
    public String getTitleAsString()
    {
    	if(title == null) return "";
    	
    	return title.getCode();
    }	
    
    /**
     * Returns the descriptions of all the roles associated with this participant by comma sperator. 
     */
    public String getRolesDescriptionAsString(){
    	String rolesDescriptionStr = "";
    	if(participantRoleCollection == null) return rolesDescriptionStr;
    	for(ParticipantRole pr : getParticipantRoleCollection()){
    		if("".equals(rolesDescriptionStr)){
    			rolesDescriptionStr = StringUtils.camelCase(pr.getDescription());
    		}else{
    			rolesDescriptionStr = rolesDescriptionStr +", "+ StringUtils.camelCase(pr.getDescription());
    		}
    	}    	
    	return rolesDescriptionStr;
    }
    
    /**
     * Returns the preferred address as String
     */
    public String getPreferredAddressAsString(){
    	String preferredAddressStr = "";
    	Address address = null;
    	for(ParticipantAddress pa : getParticipantAddressCollection()){
    		if(Constants.YES.equals(pa.getPreferredFlag())){
    			address = pa.getAddress();    			
    			if(address.getAddressLine1() !=null && "".equals(address.getAddressLine1())){
    				preferredAddressStr = address.getAddressLine1();	
    			}
    			if(address.getAddressLine2() !=null ){
    	    		if("".equals(preferredAddressStr))preferredAddressStr = address.getAddressLine2(); 
    	    		else preferredAddressStr = preferredAddressStr +", "+ address.getAddressLine2();    				
    			}
    			if(address.getCity() !=null ){
    	    		if("".equals(preferredAddressStr))preferredAddressStr = address.getCity(); 
    	    		else preferredAddressStr = preferredAddressStr +", "+ address.getCity();    				
    			}
    			if(address.getStateProvince() !=null){
    	    		if("".equals(preferredAddressStr))preferredAddressStr = address.getStateProvince(); 
    	    		else preferredAddressStr = preferredAddressStr +", "+ address.getStateProvince();    				
    			} 
    			if(address.getZipPostalCode() !=null){
    	    		if("".equals(preferredAddressStr))preferredAddressStr = address.getZipPostalCode(); 
    	    		else preferredAddressStr = preferredAddressStr +", "+ address.getZipPostalCode();    				
    			}     			
    			if(address.getCountryCode() !=null){
    	    		if("".equals(preferredAddressStr))preferredAddressStr = address.getCountryCode(); 
    	    		else preferredAddressStr = preferredAddressStr +", "+ address.getCountryCode();    				
    			}
    		}    			
    	}
    	return preferredAddressStr;
    }
    
    /**
     * Returns the preferred address or the only available address 
     */    
    public Address getPreferredOrLatestAddress()
    {
    	Address address = null;
    	Collection<ParticipantAddress> participantAddressCollection= getParticipantAddressCollection();
    	
    	if(participantAddressCollection!=null){
    		
    		for(ParticipantAddress pa : participantAddressCollection){	
	    		if(Constants.YES.equals(pa.getPreferredFlag())){
	    			address = pa.getAddress();
	    		}
    		}
    		if(address==null){
    			if(participantAddressCollection.size()==0) return address;
    			
    			ParticipantAddress finalPA = participantAddressCollection.iterator().next();
    			for(ParticipantAddress pa : participantAddressCollection){	
    	    		if(finalPA.getAddress().getId()< pa.getAddress().getId()){
    	    			finalPA = pa ;
    	    		}
        		}
    			address = finalPA.getAddress();
    		}
    	}
    	
    	
    	return address;
    }
    
   
    /**
     * Returns the preferred address or the only available address as String
     */
    public String getPreferredOrLatestAddressAsString(){
    	String preferredAddressStr = "";
    	Address address = null;
    	Collection<ParticipantAddress> participantAddressCollection= getParticipantAddressCollection();
    	
    	if(participantAddressCollection!=null){
    		
    		for(ParticipantAddress pa : participantAddressCollection){	
	    		if(Constants.YES.equals(pa.getPreferredFlag())){
	    			address = pa.getAddress();
	    		}
    		}
    		if(address==null){
    			if(participantAddressCollection == null || participantAddressCollection.size()==0) return preferredAddressStr;
    			
    			ParticipantAddress finalPA = participantAddressCollection.iterator().next();
    			

    			for(ParticipantAddress pa : participantAddressCollection){	
    	    		if( finalPA !=null && finalPA.getAddress().getId() != null 
    	    			&& pa.getAddress().getId() !=null &&
    	    			finalPA.getAddress().getId()< pa.getAddress().getId()){
    	    			finalPA = pa ;
    	    		}
        		}
    			
    			
    			address = finalPA.getAddress();
    		}
    		
    		
    		    			
    		if(address.getAddressLine1() !=null ){
    			preferredAddressStr = address.getAddressLine1().trim();	
    		}
    		if(address.getAddressLine2() !=null ){
    	    	if("".equals(preferredAddressStr))preferredAddressStr = address.getAddressLine2(); 
    	    	else preferredAddressStr = preferredAddressStr +", "+ address.getAddressLine2();    				
    		}
    		if(address.getCity() !=null ){
    	    	if("".equals(preferredAddressStr))preferredAddressStr = address.getCity(); 
    	    	else preferredAddressStr = preferredAddressStr +", "+ address.getCity();    				
    		}
    		if(address.getStateProvince() !=null ){
    	    	if("".equals(preferredAddressStr))preferredAddressStr = address.getStateProvince(); 
    	    	else preferredAddressStr = preferredAddressStr +", "+ address.getStateProvince();    				
    		} 
    		if(address.getZipPostalCode() !=null ){
    	    	if("".equals(preferredAddressStr))preferredAddressStr = address.getZipPostalCode(); 
    	    	else preferredAddressStr = preferredAddressStr +", "+ address.getZipPostalCode();    				
    		}     			
    		if(address.getCountryCode() !=null ){
    	    	if("".equals(preferredAddressStr))preferredAddressStr = address.getCountryCode(); 
    	    	else preferredAddressStr = preferredAddressStr +", "+ address.getCountryCode();    				
    		}
    	}
    		    	
    	return preferredAddressStr;
    }

    
    /**
     * If the participant is associated with the registration then the value returned is TRUE otherwise FALSE. 
     */
    public String isAssociatedWithRegistration(){
    	if(getRegistrationParticipantRoleCollection() != null){
    		if(getRegistrationParticipantRoleCollection().size()>0){
    			return Constants.TRUE;
    		}
    	}
    	return Constants.FALSE;
    }
    
    /**
     * Removes the ParticipantAddress object of the given id from the collection of ParticipantAddress objects.
     */
    public void removeParticipantAddress(String id){
    	if(id == null)
    		return;
    	
    	ParticipantAddress pa = getParticipantAddress(id);
    	if(pa!=null){
    		getParticipantAddressCollection().remove(pa);
    	}
    }

    /**
     * Returns the ParticipantAddress object of the given id from the collection of ParticipantAddress objects.
     */
    public ParticipantAddress getParticipantAddress(String id){
    	if(id == null)
    		return null;    	
    	ParticipantAddress participantAddress = null;
    	for(ParticipantAddress pa : getParticipantAddressCollection()){
    		if(id.equals(pa.getIdAsString())){
    			participantAddress = pa;
    			break;
    		}
    	}
    	return participantAddress;
    }  
    
    /**
     * Returns the ParticipantContact object of the given id from the collection of ParticipantContact objects.
     */
    public ParticipantContact getParticipantContact(String id){
    	if(id == null)
    		return null;    	
    	ParticipantContact participantContact = null;
    	for(ParticipantContact pc : getParticipantContactCollection()){
    		if(id.equals(pc.getIdAsString())){
    			participantContact = pc;
    			break;
    		}
    	}
    	return participantContact;
    }  

    /**
     * Removes the ParticipantContact object of the given id from the collection of ParticipantContact objects.
     */
    public void removeParticipantContact(String id){
    	if(id == null)
    		return;
    	
    	ParticipantContact pc = getParticipantContact(id);
    	if(pc!=null){
    		getParticipantContactCollection().remove(pc);
    	}
    }    
    
    /**
     * Returns the Full name and Specialty of the participant.
     * 
     * @return String, concordination of the Title Lastname Firstname Middlename Suffix and Specialty.
     */     
    public String getFullNameAndSpecialty(){

    	String fulNameAndSpecialty = getFullName();
    	
    	if(!"".equals(getSpecialtyCollectionAsString())){
    		if("".equals(fulNameAndSpecialty)){
    			fulNameAndSpecialty = getSpecialtyCollectionAsString();
    		}else{
    			fulNameAndSpecialty = fulNameAndSpecialty + ", " + getSpecialtyCollectionAsString();
    		}
    	}
    	return fulNameAndSpecialty;
    }

    /**
     * Returns the RelatedParticipant object of the given id from the collection of RelatedParticipant objects.
     */
    public RelatedParticipant getRelatedParticipant(String id){
    	if(id == null)
    		return null;    	
    	RelatedParticipant relatedParticipant = null;
    	for(RelatedParticipant rp : getRelatedParticipantCollection()){
    		if(id.equals(rp.getIdAsString())){
    			relatedParticipant = rp;
    			break;
    		}
    	}
    	return relatedParticipant;
    }  

    /**
     * Removes the RelatedParticipant object of the given id from the collection of RelatedParticipant objects.
     */
    public void removeRelatedParticipant(String id){
    	if(id == null)
    		return;
    	
    	RelatedParticipant rp = getRelatedParticipant(id);
    	if(rp!=null){
    		getRelatedParticipantCollection().remove(rp);
    	}
    }  
    
    /**
     * Removes the RelatedParticipant object of the given id from the collection of RelatedParticipant objects.
     */
    public boolean isRelatedParticipant(String personId){
    	boolean isExists = false;
    	if(personId != null){
    		Participant participant = null;    	
    		for(RelatedParticipant rpa : getRelatedParticipantCollection()){
    			participant = rpa.getRelatedParticipant();
    			if(personId.equals(participant.getIdAsString())){
    				isExists = true;
    			}
    		}
    	}
    	return isExists;
    }  

    /**
     * Returns the Ethnicity code as String.
     */
    public String getEthnicityCodeAsString(){
    	if(ethnicity == null) return "";
    	
    	return ethnicity.getCode();
    }
    
    /**
     * Returns the Gender code as String.
     */
    public String getGenderCodeAsString(){
    	if(gender == null) return "";
    	
    	return gender.getCode();
    }
    
    /**
     * Returns the ParticipantRole codes as String array.
     */
    public String[] getParticipantRoleCodesAsStringArray(){    
		String[] participationTypeCheckBox = new String[getParticipantRoleCollection().size()];
		ParticipantRole pr = null;
		Iterator participantRoleItr = getParticipantRoleCollection().iterator();
		int i = 0;
		while(participantRoleItr.hasNext()){
			pr = (ParticipantRole)participantRoleItr.next();
			participationTypeCheckBox[i] = pr.getCode();
			i=++i;
		}
		return participationTypeCheckBox; 
    }
    
    /**
     * Returns the Race codes as String array.
     */
    public String[] getRaceCodesAsStringArray(){    
		String[] raceCodes = new String[getRaceCollection().size()];
		Race race = null;
		Iterator raceItr = getRaceCollection().iterator();
		int i = 0;
		while(raceItr.hasNext()){
			race = (Race)raceItr.next();
			raceCodes[i] = race.getCode();
			i=++i;
		}
		return raceCodes; 
    }
    
    /**
     * Returns the Speciality codes as String array.
     */
    public String[] getSpecialtyCodesAsStringArray(){    
		String[] specialtyCodes = new String[getSpecialtyCollection().size()];
		Specialty specialty = null;
		Iterator specialtyItr = getSpecialtyCollection().iterator();
		int i = 0;
		while(specialtyItr.hasNext()){
			specialty = (Specialty)specialtyItr.next();
			specialtyCodes[i] = specialty.getCode();
			i=++i;
		}
		return specialtyCodes; 
    } 
    
    /**
     * Returns the Suffix code as String.
     */
    public String getSuffixCodeAsString(){
    	if(getSuffix() == null) return "";
    	
    	return getSuffix().getCode();
    }    
    
    /**
     * Returns the Title code as String.
     */
    public String getTitleCodeAsString(){
    	if(getTitle() == null) return "";
    	
    	return getTitle().getCode();
    } 


    public void removeParticipantRoleCollection(){
		if (getParticipantRoleCollection() == null)
			return;
		else
			setParticipantRoleCollection(null);	
    }
    
    public void removeRaceCollection(){
		if (getRaceCollection() == null)
			return;
		else
			setRaceCollection(null);	
    }
    
    public void removeSpecialtyCollection(){
		if (getSpecialtyCollection() == null)
			return;
		else
			setSpecialtyCollection(null);	
    }
    
    public String getParticipantIdentifierAndSourceAsString()
    {
    	if(participantIdentifierCollection == null || participantIdentifierCollection.size()==0)
    		return "";
    	
    	ParticipantIdentifier id = getPrimaryIdentifier();
    	
    	if(id!=null)
    		return (id.getIdentifierCode()+" / "+id.getInstitution().getName());
    	
    	id = participantIdentifierCollection.iterator().next();
    	String result = (id.getIdentifierCode()+" / "+id.getInstitution().getName());
    	
    	if(participantIdentifierCollection.size() >1)
    		result += " (+)";
    	
    	return result;
    }

    public String getSpecialtyAsString()
    {
    	String specialties = "";
    	if(specialtyCollection == null || specialtyCollection.size()==0) return specialties;
    	
    	specialties = ((Specialty)specialtyCollection.iterator().next()).getDescription();
    	
    	if(specialtyCollection.size()>1)
    		specialties += " (+)";
    	
    	return specialties;
    }

    
    public void removeParticipantIdentifierCollection(){
		if (getParticipantIdentifierCollection() == null)
			return;
		else
			getParticipantIdentifierCollection().clear();	
    }
    
}

