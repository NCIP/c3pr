package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iso._21090.DSETII;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.II;
import org.iso._21090.TEL;
import org.springframework.context.MessageSource;

import com.semanticbits.coppasimulator.util.CoppaObjectFactory;
import com.semanticbits.coppasimulator.util.CoppaPAObjectFactory;

import edu.duke.cabig.c3pr.constants.RemoteSystemStatusCodeEnum;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.Person;


public class PersonOrganizationResolverUtils {
	
	/** The message broadcaster. */
	private CCTSMessageBroadcaster coppaMessageBroadcaster;
	
	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;
	
	/** The log. */
    private static Log log = LogFactory.getLog(PersonOrganizationResolverUtils.class);
	
	public static final String CTEP_ROOT = "2.16.840.1.113883.3.26.6.2";
	public static final String CTEP_ID = "CTEP ID";
	public static final String NCI_ID = "NCI Research Organization identifier";
	public static final String NCI_ROOT = "2.16.840.1.113883.3.26.4.4.5";
	
    
	public List<IdentifiedPerson> getIdentifiedPerson(II personIdentifier) {
		List<IdentifiedPerson> identifiedPersonsList = new ArrayList<IdentifiedPerson>();
        IdentifiedPerson ip = CoppaObjectFactory.getCoppaIdentfiedPersonSearchCriteriaForCorrelation(personIdentifier);
        String ipPayload = CoppaObjectFactory.getCoppaIdentfiedPersonXml(ip);                
        
        String result = "";
		try {
			result = broadcastIdentifiedPersonSearch(ipPayload);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
        List<String> identifiedPersons = XMLUtils.getObjectsFromCoppaResponse(result);        
        IdentifiedPerson identifiedPerson = null;
        for(String identifiedPersonXml: identifiedPersons){
                identifiedPerson = CoppaObjectFactory.getCoppaIdentfiedPerson(identifiedPersonXml);
                identifiedPersonsList.add(identifiedPerson);
        }
        return identifiedPersonsList;
	}
	
	/**
	 * Gets the identified person.
	 * Returns a list of IdentifiedPersons.
	 * 
	 * @param ip the ip
	 * 
	 * @return the identified person
	 */
	public List<IdentifiedPerson> getIdentifiedPerson(IdentifiedPerson ip) {
		List<IdentifiedPerson> identifiedPersonsList = new ArrayList<IdentifiedPerson>();
        String ipPayload = CoppaObjectFactory.getCoppaIdentfiedPersonXml(ip);              
        String result = "";
		try {
			result = broadcastIdentifiedPersonSearch(ipPayload);
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}

        List<String> identifiedPersons = XMLUtils.getObjectsFromCoppaResponse(result);    
        IdentifiedPerson identifiedPerson = null;
        for(String identifiedPersonXml: identifiedPersons){
                identifiedPerson = CoppaObjectFactory.getCoppaIdentfiedPerson(identifiedPersonXml);
                identifiedPersonsList.add(identifiedPerson);
        }
        return identifiedPersonsList;
	}
    
	/**
	 * Gets the identifier organizations for organizations list.
	 * 
	 * @param coppaOrganizationsList the coppa organizations list
	 * 
	 * @return the identifier organizations for organizations list
	 */
	public Map<String, IdentifiedOrganization> getIdentifiedOrganizationsForOrganizationsList(List<gov.nih.nci.coppa.po.Organization> coppaOrganizationsList) {
		Map<String, IdentifiedOrganization> identifiedOrganizationsMap = new HashMap<String, IdentifiedOrganization>();
		
		try {
			//Build a list of orgId Xml
			List<String> organizationIdXmlList = new ArrayList<String>();
			DSETII dsetii = null;
			for(gov.nih.nci.coppa.po.Organization coppaOrganization : coppaOrganizationsList){
				dsetii = CoppaObjectFactory.getDSETIISearchCriteria(coppaOrganization.getIdentifier().getExtension());
				organizationIdXmlList.add(CoppaObjectFactory.getCoppaIIXml(dsetii));
			}
			
			//Coppa-call for Identifier Organizations getByIds
			String identifiedOrganizationsXml = broadcastIdentifiedOrganizationGetByPlayerIds(organizationIdXmlList);
			List<String> identifiedOrganizations = XMLUtils.getObjectsFromCoppaResponse(identifiedOrganizationsXml);
			
			//Build a map with orgId as key and identifiedOrganization as value. Only get IdOrgs that have CTEP ID
			if(identifiedOrganizations != null && identifiedOrganizations.size() > 0){
				IdentifiedOrganization identifiedOrganization = null;
				for(String identifiedOrganizationString : identifiedOrganizations){
					identifiedOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(identifiedOrganizationString);
					if(identifiedOrganization != null && identifiedOrganization.getAssignedId().getIdentifierName().equals(CTEP_ID)){
						identifiedOrganizationsMap.put(identifiedOrganization.getPlayerIdentifier().getExtension(), identifiedOrganization);
					}
				}
			}
    	} catch(Exception e){
    		log.error(e.getMessage());
    	}
    	return identifiedOrganizationsMap;
	}
	
	
	/**
	 * Sets the c3pr user details. Returns null if person doesnt have legal email address or \
	 * if there is some exception while processing the person.
	 * 
	 * @param coppaPerson the coppa person
	 * @param c3prUser the c3pr user
	 * 
	 * @return the c3 pr user
	 */
	public C3PRUser setC3prUserDetails(Person coppaPerson, C3PRUser c3prUser) {
		try{	
			Iterator<ENXP> enxpItr = coppaPerson.getName().getPart().iterator();
			String firstName = null;
			String middleName = "";
			String lastName = "";
			
			while(enxpItr.hasNext()){
				ENXP enxp = enxpItr.next();
				if(enxp.getType().equals(EntityNamePartType.GIV)){
					if(firstName == null){
						firstName = enxp.getValue();
					} else {
						middleName += enxp.getValue() + " ";
					}
				}
				if(enxp.getType().equals(EntityNamePartType.FAM)){
					lastName = enxp.getValue();
				}
			}
	        
			List<TEL> tel = coppaPerson.getTelecomAddress().getItem();
	        Iterator<TEL> telItr = tel.iterator();
	        
	        String emailStr = "";
			String phoneNumber = "";
			String faxNumber = "";
			while(telItr.hasNext()){
				String  nextVal = telItr.next().getValue();
				//remove mailto: string from email 
				if (nextVal.startsWith("mailto:")) {
					emailStr = nextVal.substring("mailto:".length(), nextVal.length());
				}
				if (nextVal.startsWith("tel:")) {
					phoneNumber = nextVal.substring("tel:".length(), nextVal.length());
				}
				if (nextVal.startsWith("x-text-fax:")) {
					faxNumber = nextVal.substring("x-text-fax:".length(), nextVal.length());
				}
			}
			c3prUser.setFirstName(firstName);
			c3prUser.setLastName(lastName);
			c3prUser.setMiddleName(middleName);
			c3prUser.setRemoteEmail(emailStr);
			c3prUser.setRemotePhone(phoneNumber);
			c3prUser.setRemoteFax(faxNumber);
			
			if(emailStr == null){
				//return null if person doesnt have a valid email id.
				c3prUser = null;
			} 
		} catch(IllegalArgumentException iae){
			log.error("Person has invalid contact information. Proceeding with out it." + iae.getMessage());
		} catch(Exception e){
			log.error("Error while processing Person. Proceeding with out it." + e.getMessage());
		} finally {
			return c3prUser;
		}
	}
	
	/**
	 * Sets a primaryOAI as the ctep from given extension
	 * @param remoteOrganization
	 * @param extension
	 */
	public void setCtepCodeFromExtension(HealthcareSite remoteOrganization, String extension) {
		remoteOrganization.setCtepCode(extension);
	}
	
	/**
	 * Sets a primaryOAI as the NCI from given extension
	 * @param remoteOrganization
	 * @param extension
	 */
	public void setNciCodeFromExtension(HealthcareSite remoteOrganization, String extension) {
		remoteOrganization.setNCICode(extension, true);
	}
	
	/** Populate Remote Organization , given the Coppa Organization.
	 *  Populate the ctepCode   from  IdentifiedOrganization.assignedId.extension by calling the IdentifiedOrganization search.
	 *  Get ctep only if the "getCtepFromIdentifiedOrganization" boolean is true. Else assume CTEP code is already available.
	 *  Populate the externalId from  IdentifiedOrganization.identifier.extension by calling the IdentifiedOrganization search.
	 * 
	 * @param coppaOrganization
	 * @return RemoteHealthcareSite
	 */
	public RemoteHealthcareSite getRemoteHealthcareSiteFromCoppaOrganization(gov.nih.nci.coppa.po.Organization coppaOrganization){
		return getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization, true);
	}
	
	
	/**
	 * Gets the nci ids for person list.
	 * Returns a map with personID as key and associated NciId as value.
	 * 
	 * @param coppaPersonsList the coppa persons list
	 * 
	 * @return the nci ids for person list
	 */
	public Map<String, List<IdentifiedPerson>> getIdentifiedPersonsForPersonList(List<Person> coppaPersonsList){
    	Map<String, List<IdentifiedPerson>> identifiedPersonMap = new HashMap<String, List<IdentifiedPerson>>();
		
    	try {
			//Build a list of personId Xml
			List<String> personIdXmlList = new ArrayList<String>();
			for(Person coppaPerson:coppaPersonsList){
				personIdXmlList.add(CoppaObjectFactory.getCoppaPersonIdXML(coppaPerson.getIdentifier().getExtension()));
			}
			//Coppa-call for Identifier Persons getByIds
			String identifiedPersonsXml = broadcastIdentifiedPersonGetByPlayerIds(personIdXmlList);
			List<String> identifiedPersons = XMLUtils.getObjectsFromCoppaResponse(identifiedPersonsXml);
			
			//Build a map with personId as key and sRole as value
			if(identifiedPersons != null && identifiedPersons.size() > 0){
				IdentifiedPerson identifiedPerson = null;
				for(String identifiedPersonString : identifiedPersons){
					identifiedPerson = CoppaObjectFactory.getCoppaIdentfiedPerson(identifiedPersonString);
					if(identifiedPerson != null){
						//identifiedPersonMap.put(identifiedPerson.getPlayerIdentifier().getExtension(), identifiedPerson);
						List<IdentifiedPerson> ipList = null;
						if(identifiedPersonMap.containsKey(identifiedPerson.getPlayerIdentifier().getExtension())){
							ipList  = identifiedPersonMap.get(identifiedPerson.getPlayerIdentifier().getExtension());
							ipList.add(identifiedPerson);
						} else {
							ipList = new ArrayList<IdentifiedPerson>();
							ipList.add(identifiedPerson);
							identifiedPersonMap.put(identifiedPerson.getPlayerIdentifier().getExtension(), ipList);
						}
					}
				}
			}
    	} catch(Exception e){
    		log.error(e.getMessage());
    	}
    	return identifiedPersonMap;
    }
	
	/**
	 * Gets the remote healthcare site from coppa organization.
	 * 
	 * @param coppaOrganization the coppa organization
	 * @param getCtepFromIdentifiedOrganization the get ctep from identified organization
	 * 
	 * @return the remote healthcare site from coppa organization
	 */
	public RemoteHealthcareSite getRemoteHealthcareSiteFromCoppaOrganization(gov.nih.nci.coppa.po.Organization coppaOrganization, boolean getCtepFromIdentifiedOrganization){
		RemoteHealthcareSite remoteHealthcareSite = null;
		if(coppaOrganization != null){
			remoteHealthcareSite = new RemoteHealthcareSite();
			//using coppa organization identier and previously obtained id of CTEP (hard coded in CoppaObjectFactory.getIIOfCTEP) get Identified organization
			if(getCtepFromIdentifiedOrganization){
				IdentifiedOrganization identifiedOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaForCorrelation(coppaOrganization.getIdentifier());
				String identifiedOrganizationXml = CoppaObjectFactory.getCoppaIdentfiedOrganization(identifiedOrganization);		
				String resultXml = "";
				try {
					resultXml = broadcastIdentifiedOrganizationSearch(identifiedOrganizationXml);
				} catch (C3PRCodedException e) {
					//throwing a runtimeException here as this is non-recoverable exception
					throw new RuntimeException();
				}
				
				List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
				if (results.size() > 0) {
					identifiedOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(results.get(0));
					if (identifiedOrganization.getAssignedId() != null ) {
						//Setting the CTEP ID 
						setCtepCodeFromExtension(remoteHealthcareSite, identifiedOrganization.getAssignedId().getExtension());
					}
				}
			}
			
			//set values from CoppaOrganization
			remoteHealthcareSite.setName(CoppaObjectFactory.getName(coppaOrganization.getName()));
			remoteHealthcareSite.setExternalId(coppaOrganization.getIdentifier().getExtension());
			remoteHealthcareSite.setRemoteSystemStatusCode(RemoteSystemStatusCodeEnum.getByCode(coppaOrganization.getStatusCode().getCode()));
			
			Address address = getAddressFromCoppaOrganization(coppaOrganization);
			remoteHealthcareSite.setAddress(address);
		}
		return remoteHealthcareSite;
	}
	
	public void setCtepCodeFromStructuralRoleIIList(RemoteHealthcareSite remoteHealthcareSite, List<II> iiList){
		for(II ii: iiList){
			if(ii.getRoot().equalsIgnoreCase(CTEP_ROOT) || ii.getIdentifierName().equals(CTEP_ID) ){
				setCtepCodeFromExtension(remoteHealthcareSite, ii.getExtension());
			}
			if(ii.getRoot().equalsIgnoreCase(NCI_ROOT) || ii.getIdentifierName().equals(NCI_ID) ){
				setNciCodeFromExtension(remoteHealthcareSite, ii.getExtension());
			}
		}
	}

	/** Populate the Address object from the coppaOrganization which is passed into it.
	 * 
	 * @param coppaOrganization
	 * @return Address
	 */
	public Address getAddressFromCoppaOrganization(gov.nih.nci.coppa.po.Organization coppaOrganization) {
		Address address  = new Address();
		
		address.setCity(CoppaObjectFactory.getCity(coppaOrganization.getPostalAddress()));
		address.setCountryCode(CoppaObjectFactory.getCountry(coppaOrganization.getPostalAddress()));
		address.setStateCode(CoppaObjectFactory.getState(coppaOrganization.getPostalAddress()));
		address.setPostalCode(CoppaObjectFactory.getZip(coppaOrganization.getPostalAddress()));
		address.setStreetAddress(CoppaObjectFactory.getStreet(coppaOrganization.getPostalAddress()));
		
		return address;
	}
	
	/**
	 * Broadcast organization search.
	 * 
	 * @param gov.nih.nci.coppa.po.Organization coppa organization example to search by.
	 * @return the List<Object> list of coppa organizations
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	public String broadcastOrganizationSearch(String healthcareSiteXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.search.getName() + "   Service -->" +ServiceTypeEnum.ORGANIZATION.getName());
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.ORGANIZATION.getName());
        return broadcastCoppaMessage(healthcareSiteXml, mData);
	}	
	
	public String broadcastIdentifiedPersonSearch(String ipXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.search.getName() + "   Service -->" +ServiceTypeEnum.IDENTIFIED_PERSON.getName());
		Metadata mData = new Metadata(OperationNameEnum.search.getName(),  "externalId", ServiceTypeEnum.IDENTIFIED_PERSON.getName());
		return broadcastCoppaMessage(ipXml, mData);
	}
	

	public String broadcastIdentifiedPersonGetByPlayerIds(List<String> personIdXmlList) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getByPlayerIds.getName() + "   Service -->" +ServiceTypeEnum.IDENTIFIED_PERSON.getName());
        Metadata mData = new Metadata(OperationNameEnum.getByPlayerIds.getName(),  "extId", ServiceTypeEnum.IDENTIFIED_PERSON.getName());
		return broadcastCoppaMessage(personIdXmlList, mData);
	}
	
	public String broadcastOrganizationGetById(String iiXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getById.getName() + "   Service -->" +ServiceTypeEnum.ORGANIZATION.getName());
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(),  "extId", ServiceTypeEnum.ORGANIZATION.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}
	
	public String broadcastIdentifiedOrganizationSearch(String healthcareSiteXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.search.getName() + "   Service -->" +ServiceTypeEnum.IDENTIFIED_ORGANIZATION.getName());
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "externalId", ServiceTypeEnum.IDENTIFIED_ORGANIZATION.getName());
        return broadcastCoppaMessage(healthcareSiteXml, mData);
	}
	

	public String broadcastIdentifiedOrganizationGetByPlayerIds(List<String> organizationIdXmlList) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getByPlayerIds.getName() + "   Service -->" +ServiceTypeEnum.IDENTIFIED_ORGANIZATION.getName());
        Metadata mData = new Metadata(OperationNameEnum.getByPlayerIds.getName(), "externalId", ServiceTypeEnum.IDENTIFIED_ORGANIZATION.getName());
		return broadcastCoppaMessage(organizationIdXmlList, mData);
	}
	
	public String broadcastClinicalResearchStaffSearch(String personXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.search.getName() + "   Service -->" +ServiceTypeEnum.CLINICAL_RESEARCH_STAFF.getName());
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "externalId", ServiceTypeEnum.CLINICAL_RESEARCH_STAFF.getName());
		return broadcastCoppaMessage(personXml, mData);
	}
	

	public String broadcastClinicalResearchStaffGetByPlayerIds(List<String> personIdXmlList) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getByPlayerIds.getName() + "   Service -->" +ServiceTypeEnum.CLINICAL_RESEARCH_STAFF.getName());
        Metadata mData = new Metadata(OperationNameEnum.getByPlayerIds.getName(), "externalId", ServiceTypeEnum.CLINICAL_RESEARCH_STAFF.getName());
		return broadcastCoppaMessage(personIdXmlList, mData);
	}
	
	
	public String broadcastResearchOrganizationGetById(String roXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getById.getName() + "   Service -->" +ServiceTypeEnum.RESEARCH_ORGANIZATION.getName());
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "externalId", ServiceTypeEnum.RESEARCH_ORGANIZATION.getName());
		return broadcastCoppaMessage(roXml, mData);
	}
	
	public String broadcastHealthcareFacilityGetById(String roXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getById.getName() + "   Service -->" +ServiceTypeEnum.HEALTH_CARE_FACILITY.getName());
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "externalId", ServiceTypeEnum.HEALTH_CARE_FACILITY.getName());
		return broadcastCoppaMessage(roXml, mData);
	}

	public String broadcastPersonGetById(String iiXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getById.getName() + "   Service -->" +ServiceTypeEnum.PERSON.getName());
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "externalId", ServiceTypeEnum.PERSON.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}
	
	public String broadcastPersonSearch(String iiXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.search.getName() + "   Service -->" +ServiceTypeEnum.PERSON.getName());
        Metadata mData = new Metadata(OperationNameEnum.search.getName(),  "externalId", ServiceTypeEnum.PERSON.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}
	
	public String broadcastHealthcareProviderSearch(String personXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.search.getName() + "   Service -->" +ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "externalId", ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
		return broadcastCoppaMessage(personXml, mData);
	}
	
	public String broadcastHealthcareProviderGetByPlayerIds(List<String> personXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getByPlayerIds.getName() + "   Service -->" +ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
        Metadata mData = new Metadata(OperationNameEnum.getByPlayerIds.getName(), "externalId", ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
		return broadcastCoppaMessage(personXml, mData);
	}
	
	public String broadcastHealthcareProviderGetById(String personXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.getById.getName() + "   Service -->" +ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "externalId", ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
		return broadcastCoppaMessage(personXml, mData);
	}
	
	public String broadcastOrganizationCreate(String healhtcareSiteXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		log.debug("Broadcasting : Operation --> "+ OperationNameEnum.create.getName() + "   Service -->" +ServiceTypeEnum.ORGANIZATION.getName());
        Metadata mData = new Metadata(OperationNameEnum.create.getName(), "externalId", ServiceTypeEnum.ORGANIZATION.getName());
		return broadcastCoppaMessage(healhtcareSiteXml, mData);
	}
	
	/**
	 * Broadcast coppa message. The actual call to the esb-client.
	 * 
	 * @param healthcareSiteXml the healthcare site xml
	 * @param mData the m data
	 * @return the string
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	private String broadcastCoppaMessage(String healthcareSiteXml, Metadata mData) throws C3PRCodedException {

		String caXchangeResponseXml = null;
		try {
            caXchangeResponseXml = getCoppaMessageBroadcaster().broadcastCoppaMessage(healthcareSiteXml, mData);
        }
        catch (Exception e) {
            log.error(e);
            throw this.exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.ORGANIZATION.SEARCH.BROADCAST.SEND_ERROR"), e);
        }
		return caXchangeResponseXml;
	}	
	
	/**
	 * Broadcast coppa message. The actual call to the esb-client which takes a List of Strings.
	 * NOTE: Users of this method do not need to specify the offset, a default value is added by this method.
	 * It is important to note that the pauload needs to be the first element in the list and the offset needs to be
	 * the second element, otherwise it results in a parse exception.
	 * 
	 * @param healthcareSiteXml the healthcare site xml
	 * @param mData the m data
	 * @return the string
	 * @throws C3PRCodedException the c3pr coded exception
	 */
	private String broadcastCoppaMessage(List<String> cctsDomainObjectXMLList, Metadata mData) throws C3PRCodedException {
		String caXchangeResponseXml = null;
		//adding a default limit-offset setting incase its not already specified
		if(cctsDomainObjectXMLList.size() == 1){
			cctsDomainObjectXMLList.add(CoppaPAObjectFactory.getLimitOffsetXML(5, 0));
		}
		
		try {
            caXchangeResponseXml = getCoppaMessageBroadcaster().broadcastCoppaMessage(cctsDomainObjectXMLList, mData);
        }
        catch (Exception e) {
            log.error(e);
            throw this.exceptionHelper.getException(
                    getCode("C3PR.EXCEPTION.ORGANIZATION.SEARCH.BROADCAST.SEND_ERROR"), e);
        }
		return caXchangeResponseXml;
	}
	
	
	/**
     * Gets the error code which is used to retrieve the exception message.
     * 
     * @param errortypeString the errortype string
     * @return the code
     */
    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
    
	public C3PRExceptionHelper getExceptionHelper() {
		return exceptionHelper;
	}

	public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}

	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}

	public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}

	public CCTSMessageBroadcaster getCoppaMessageBroadcaster() {
		return coppaMessageBroadcaster;
	}

	public void setCoppaMessageBroadcaster(
			CCTSMessageBroadcaster coppaMessageBroadcaster) {
		this.coppaMessageBroadcaster = coppaMessageBroadcaster;
	}

	
}
