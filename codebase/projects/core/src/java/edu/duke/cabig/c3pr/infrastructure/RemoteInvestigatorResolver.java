package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.iso._21090.ENXP;
import org.iso._21090.EntityNamePartType;
import org.iso._21090.II;
import org.iso._21090.TEL;
import org.springframework.context.MessageSource;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.Person;

/**
 * The Class RemoteInvestigatorResolver.
 */
public class RemoteInvestigatorResolver implements RemoteResolver{
	
	private static Logger logger = Logger.getLogger(RemoteInvestigatorResolver.class);


	/** The message broadcaster. */
	private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster coppaMessageBroadcaster;
	
	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;
	
	/** The log. */
    private static Log log = LogFactory.getLog(RemoteResearchStaffResolver.class);

	
	/**
	 * Populate remote investigator.
	 * 
	 * @param coppaPerson the coppa person
	 * @param coppaOrganizationList the coppa organization list
	 * 
	 * @return the remote investigator
	 */
	public RemoteInvestigator populateRemoteInvestigator(Person coppaPerson, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList){
		
		RemoteInvestigator remoteInvestigator = setInvestigatorDetails(coppaPerson);
		
		List<gov.nih.nci.coppa.po.IdentifiedOrganization> identifiedCoppaOrganizationList = new ArrayList<IdentifiedOrganization>();
		if(coppaOrganizationList != null && coppaOrganizationList.size()>0){
			for(gov.nih.nci.coppa.po.Organization coppaOrganization: coppaOrganizationList){
				IdentifiedOrganization identifiedOrganization = getIdentifiedOrganization(coppaOrganization);
				identifiedCoppaOrganizationList.add(identifiedOrganization);
			}
		}
		
		//Build HealthcareSite and HealthcareSiteInvestigator
		HealthcareSite healthcareSite = null;
		if (identifiedCoppaOrganizationList != null && identifiedCoppaOrganizationList.size()>0){
			for(gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization: identifiedCoppaOrganizationList){
				healthcareSite = new RemoteHealthcareSite();
				healthcareSite.setNciInstituteCode(identifiedOrganization.getAssignedId().getExtension());
				
				HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
				healthcareSiteInvestigator.setHealthcareSite(healthcareSite);
				healthcareSiteInvestigator.setInvestigator(remoteInvestigator);
				
				remoteInvestigator.getHealthcareSiteInvestigators().add(healthcareSiteInvestigator);
			}
		}
		return remoteInvestigator;
	}
	
	
	private RemoteInvestigator setInvestigatorDetails(Person coppaPerson) {
		RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
		Iterator<ENXP> enxpItr = coppaPerson.getName().getPart().iterator();
		String firstName = "";
		String lastName = "";
		while(enxpItr.hasNext()){
			ENXP enxp = enxpItr.next();
			if(enxp.getType().equals(EntityNamePartType.GIV)){
				firstName += enxp.getValue() + " ";
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
		remoteInvestigator.setFirstName(firstName);
		remoteInvestigator.setLastName(lastName);
		remoteInvestigator.setEmail(emailStr);
		remoteInvestigator.setPhone(phoneNumber);
		remoteInvestigator.setFax(faxNumber);
        remoteInvestigator.setUniqueIdentifier(coppaPerson.getIdentifier().getExtension());
        
		return remoteInvestigator;
	}

	/**
	 * Find By example remoteInvestigator
	 */
	public List<Object> find(Object example) {
		RemoteInvestigator remoteInvestigator = null;
		if(example instanceof RemoteInvestigator){
			remoteInvestigator = (RemoteInvestigator) example;
		} else {
			return null;
		}
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		String personXml = CoppaObjectFactory.getCoppaPersonXml(
				CoppaObjectFactory.getCoppaPerson(remoteInvestigator.getFirstName(), remoteInvestigator.getMiddleName(), remoteInvestigator.getLastName()));
		String resultXml = "";
		try {
			resultXml = broadcastPersonSearch(personXml);
		} catch (Exception e) {
			System.out.print(e);
		}
		
		List<String> coppaPersons = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		RemoteInvestigator tempRemoteInvestigator = null;
		Person coppaPerson = null;
		if (coppaPersons != null){
			for(String coppaPersonXml: coppaPersons){
				coppaPerson = CoppaObjectFactory.getCoppaPerson(coppaPersonXml);
				
				List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = getOrganizationsForPerson(coppaPerson);
				
				tempRemoteInvestigator = populateRemoteInvestigator(coppaPerson, coppaOrganizationList);
				remoteInvestigatorList.add(tempRemoteInvestigator);
			}
		}
		return remoteInvestigatorList;
	}
	

	/**
	 * Gets the organizations for person.
	 * Gets the HealthcareProvider for a person. This is a Structural Role.
	 * This role has the person as the player and the Organization as the scoper.
	 * So get the scoper id from the role and use it to search all orgs. This will get us
	 * all the related orgs.
	 * 
	 * @param coppaPerson the coppa person
	 * @return the organizations for person
	 */
	private List<gov.nih.nci.coppa.po.Organization> getOrganizationsForPerson(Person coppaPerson) {
		List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = new ArrayList<gov.nih.nci.coppa.po.Organization>();
		HealthCareProvider healthCareProvider = CoppaObjectFactory.getCoppaHealthCareProvider(coppaPerson.getIdentifier());
		String coppaHealthCareProviderXml = CoppaObjectFactory.getCoppaHealthCareProviderXml(healthCareProvider);
		String sRolesXml = "";
		try {
			sRolesXml = broadcastHealthcareProviderSearch(coppaHealthCareProviderXml);
		} catch (C3PRCodedException e) {
			System.out.print(e);
		}
		List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
		
		for(String sRole: sRoles){
			String orgResultXml = "";
			HealthCareProvider hcp = CoppaObjectFactory.getCoppaHealthCareProvider(sRole);
			String orgIiXml = CoppaObjectFactory.getCoppaIIXml(hcp.getScoperIdentifier());
			try {
				orgResultXml = broadcastOrganizationGetById(orgIiXml);
			} catch (Exception e) {
				System.out.print(e);
			}
			List<String> orgResults = XMLUtils.getObjectsFromCoppaResponse(orgResultXml);
			if (orgResults.size() > 0) {
				coppaOrganizationList.add(CoppaObjectFactory.getCoppaOrganization(orgResults.get(0)));
			}
		}
		return coppaOrganizationList;
	}


	public Object getRemoteEntityByUniqueId(String externalId) {
		
		II ii = CoppaObjectFactory.getIISearchCriteriaForPerson(externalId);
		
		String iiXml = CoppaObjectFactory.getCoppaIIXml(ii);
		String resultXml = "";
		try {
			resultXml = broadcastPersonGetById(iiXml);
		} catch (C3PRCodedException e) {
			logger.error(e.getMessage());
		}
		
		List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList = null;
		Person coppaPerson = null;
		if (results.size() > 0) {
			coppaPerson = CoppaObjectFactory.getCoppaPerson(results.get(0));
			coppaOrganizationList = getOrganizationsForPerson(coppaPerson);
		}
		
		RemoteInvestigator remoteInvestigator = populateRemoteInvestigator(coppaPerson, coppaOrganizationList);
		return remoteInvestigator;
	}

	
	private IdentifiedOrganization getIdentifiedOrganization(gov.nih.nci.coppa.po.Organization coppaOrganization){
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
	
	/**
	 * Broadcast organization search.
	 * 
	 * @param gov.nih.nci.coppa.po.Organization coppa organization example to search by.
	 * @return the List<Object> list of coppa organizations
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
	public String broadcastIdentifiedOrganizationSearch(String healthcareSiteXml) throws C3PRCodedException {

		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "externalId", ServiceTypeEnum.IDENTIFIED_ORGANIZATION.getName());
        return broadcastCoppaMessage(healthcareSiteXml, mData);
	}
	
	public String broadcastHealthcareProviderSearch(String personXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(), "externalId", ServiceTypeEnum.HEALTH_CARE_PROVIDER.getName());
		return broadcastCoppaMessage(personXml, mData);
	}
	
	public String broadcastPersonGetById(String iiXml) throws C3PRCodedException {
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(), "externalId", ServiceTypeEnum.PERSON.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}
	
	
	public String broadcastPersonSearch(String iiXml) throws Exception{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(),  "externalId", ServiceTypeEnum.PERSON.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}

	
	/**
	 * Broadcast Org getById.
	 * 
	 * @param iiXml the ii xml
	 * @return the string
	 * @throws Exception the exception
	 */
	public String broadcastOrganizationGetById(String iiXml) throws Exception{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.getById.getName(),  "extId", ServiceTypeEnum.ORGANIZATION.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}
	
	public String broadcastCoppaMessage(String healthcareSiteXml, Metadata mData) throws C3PRCodedException {

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