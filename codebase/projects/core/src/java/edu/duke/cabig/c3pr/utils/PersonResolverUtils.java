package edu.duke.cabig.c3pr.utils;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.II;
import org.iso._21090.TEL;
import org.springframework.context.MessageSource;

import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.Person;


public class PersonResolverUtils {
	
	/** The message broadcaster. */
	private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster coppaMessageBroadcaster;
	
	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;
	
	/** The log. */
    private static Log log = LogFactory.getLog(PersonResolverUtils.class);
	
    
    
	public IdentifiedPerson getIdentifiedPerson(II personIdentifier) {
        
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
        }
        return identifiedPerson;
	}
    
	public IdentifiedPerson getIdentifiedPerson(IdentifiedPerson ip) {
        
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
        }
        return identifiedPerson;
	}
    


	
	public IdentifiedOrganization getIdentifiedOrganization(gov.nih.nci.coppa.po.Organization coppaOrganization){
		if(coppaOrganization != null){
			//using coppa organization identier and previously obtained id of CTEP (hard coded in CoppaObjectFactory.getIIOfCTEP) get Identified organization 
			IdentifiedOrganization identifiedOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaForCorrelation(coppaOrganization.getIdentifier());
			String identifiedOrganizationXml = CoppaObjectFactory.getCoppaIdentfiedOrganization(identifiedOrganization);		
			String resultXml = "";
			try {
				resultXml = broadcastIdentifiedOrganizationSearch(identifiedOrganizationXml);
			} catch (C3PRCodedException e) {
				log.error(e.getMessage());
			}
			
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
			if (results.size() > 0) {
				identifiedOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(results.get(0));
			}
			return identifiedOrganization;
		}
		return null;
	}
	
	
	public C3PRUser setC3prUserDetails(Person coppaPerson, C3PRUser c3prUser) {
		
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
		c3prUser.setEmail(emailStr);
		c3prUser.setPhone(phoneNumber);
		c3prUser.setFax(faxNumber);
		return c3prUser;
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
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "extId", ServiceTypeEnum.ORGANIZATION.getName());
        return broadcastCoppaMessage(healthcareSiteXml, mData);
	}	
	
	public String broadcastIdentifiedPersonSearch(String ipXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
		Metadata mData = new Metadata(OperationNameEnum.search.getName(),  "externalId", ServiceTypeEnum.IDENTIFIED_PERSON.getName());
		return broadcastCoppaMessage(ipXml, mData);
	}
	
	public String broadcastOrganizationGetById(String iiXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(),  "extId", ServiceTypeEnum.ORGANIZATION.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}
	
	public String broadcastIdentifiedOrganizationSearch(String healthcareSiteXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "externalId", ServiceTypeEnum.IDENTIFIED_ORGANIZATION.getName());
        return broadcastCoppaMessage(healthcareSiteXml, mData);
	}
	
	public String broadcastClinicalResearchStaffSearch(String personXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "externalId", ServiceTypeEnum.CLINICAL_RESEARCH_STAFF.getName());
		return broadcastCoppaMessage(personXml, mData);
	}

	public String broadcastPersonGetById(String iiXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "externalId", ServiceTypeEnum.PERSON.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}
	
	public String broadcastPersonSearch(String iiXml) throws C3PRCodedException{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(),  "externalId", ServiceTypeEnum.PERSON.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}
	
	public String broadcastHealthcareProviderSearch(String personXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "externalId", ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
		return broadcastCoppaMessage(personXml, mData);
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
            caXchangeResponseXml = coppaMessageBroadcaster.broadcastCoppaMessage(healthcareSiteXml, mData);
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

	public edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster getCoppaMessageBroadcaster() {
		return coppaMessageBroadcaster;
	}

	public void setCoppaMessageBroadcaster(
			edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster coppaMessageBroadcaster) {
		this.coppaMessageBroadcaster = coppaMessageBroadcaster;
	}
	
	
}
