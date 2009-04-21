package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iso._21090.ENXP;
import org.iso._21090.II;
import org.iso._21090.TEL;
import org.springframework.context.MessageSource;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.esb.Metadata;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ServiceTypeEnum;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.Person;

/**
 * @author Vinay Gangoli
 * The Class RemoteResearchStaffResolver.
 */
public class RemoteResearchStaffResolver implements RemoteResolver{

	/** The message broadcaster. */
	private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster coppaMessageBroadcaster;
	
	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;
	
	/** The log. */
    private static Log log = LogFactory.getLog(RemoteResearchStaffResolver.class);
	
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	public List<Object> find(Object example) {

		RemoteResearchStaff remoteResearchStaff = null;
		if(example instanceof RemoteResearchStaff){
			remoteResearchStaff = (RemoteResearchStaff) example;
		} else {
			return null;
		}
		List<Object> remoteResearchStaffList = new ArrayList<Object>();
		String personXml = CoppaObjectFactory.getCoppaPersonXml(
				CoppaObjectFactory.getCoppaPerson(remoteResearchStaff.getFirstName(), remoteResearchStaff.getMiddleName(), remoteResearchStaff.getLastName()));
		String resultXml = "";
		try {
			resultXml = broadcastPersonSearch(personXml);
		} catch (Exception e) {
			System.out.print(e);
		}
		
		List<String> coppaPersons = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		RemoteResearchStaff tempRemoteResearchStaff = null;
		Person coppaPerson = null;
		if (coppaPersons != null){
			for(String coppaPersonXml: coppaPersons){
				coppaPerson = CoppaObjectFactory.getCoppaPerson(coppaPersonXml);
				
				List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = getOrganizationsForPerson(coppaPerson);
				
				tempRemoteResearchStaff = populateRemoteResearchStaff(coppaPerson, coppaOrganizationList);
				remoteResearchStaffList.add(tempRemoteResearchStaff);
			}
		}
		return remoteResearchStaffList;
	}

	
	public Object getRemoteEntityByUniqueId(String externalId) {
		II ii = CoppaObjectFactory.getIISearchCriteriaForPerson(externalId);
		
		String iiXml = CoppaObjectFactory.getCoppaIIXml(ii);
		String resultXml = "";
		try {
			resultXml = broadcastPersonGetById(iiXml);
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}
		
		List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList = null;
		Person coppaPerson = null;
		if (results.size() > 0) {
			coppaPerson = CoppaObjectFactory.getCoppaPerson(results.get(0));
			coppaOrganizationList = getOrganizationsForPerson(coppaPerson);
		}
		
		ResearchStaff researchStaff = populateRemoteResearchStaff(coppaPerson, coppaOrganizationList);
		return researchStaff;
	}

	
	/**
	 * Gets the organizations for person. 
	 * 
	 * Gets the ClinicalResearchStaff for a person. This is a Structural Role.
	 * This role has the person as the player and the Organization as the scoper.
	 * So get the scoper id from the role and use it to search all orgs. This will get us
	 * all the related orgs.
	 * 
	 * @param coppaPerson the coppa person
	 * @return the organizations for person
	 */
	private List<gov.nih.nci.coppa.po.Organization> getOrganizationsForPerson(Person coppaPerson) {
		List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = new ArrayList<gov.nih.nci.coppa.po.Organization>();
		ClinicalResearchStaff clinicalResearchStaff = CoppaObjectFactory.getCoppaClinicalResearchStaff(coppaPerson.getIdentifier());
		String coppaClinicalResearchStaffXml = CoppaObjectFactory.getCoppaClinicalResearchStaffXml(clinicalResearchStaff);
		String sRolesXml = "";
		try {
			sRolesXml = broadcastClinicalResearchStaffSearch(coppaClinicalResearchStaffXml);
		} catch (C3PRCodedException e) {
			System.out.print(e);
		}
		List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
		
		for(String sRole: sRoles){
			String orgResultXml = "";
			ClinicalResearchStaff crs = CoppaObjectFactory.getCoppaClinicalResearchStaff(sRole);
			String orgIiXml = CoppaObjectFactory.getCoppaIIXml(crs.getScoperIdentifier());
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
	
	
	/**
	 * Populate remote research staff.
	 * 
	 * @param personDTO the person dto
	 * @param orgCtepId the org ctep id
	 * @param coppaOrgId the coppa org id
	 * 
	 * @return the research staff
	 */
	public RemoteResearchStaff populateRemoteResearchStaff(Person coppaPerson, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList){
		RemoteResearchStaff remoteResearchStaff = setResearchStaffDetails(coppaPerson);
		
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
				remoteResearchStaff.setHealthcareSite(healthcareSite);
			}
		}
		return remoteResearchStaff;
	}
	
	private RemoteResearchStaff setResearchStaffDetails(Person coppaPerson) {
		RemoteResearchStaff researchStaff = new RemoteResearchStaff();
		Iterator<ENXP> enxpItr = coppaPerson.getName().getPart().iterator();
		String firstName = "";
		String lastName = "";
		while(enxpItr.hasNext()){
			ENXP enxp = enxpItr.next();
			if(enxp.getType().name().equals(EntityNamePartType.GIV.name())){
				firstName += enxp.getValue() + " ";
			}
			if(enxp.getType().name().equals(EntityNamePartType.FAM.name())){
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
		researchStaff.setFirstName(firstName);
		researchStaff.setLastName(lastName);
		researchStaff.setEmail(emailStr);
		researchStaff.setPhone(phoneNumber);
		researchStaff.setFax(faxNumber);
		
		researchStaff.setUniqueIdentifier(coppaPerson.getIdentifier().getExtension());
		return researchStaff;
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
	
	
	public String broadcastOrganizationGetById(String iiXml) throws Exception{
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
	
	public String broadcastPersonSearch(String iiXml) throws Exception{
		//build metadata with operation name and the external Id and pass it to the broadcast method.
        Metadata mData = new Metadata(OperationNameEnum.search.getName(),  "externalId", ServiceTypeEnum.PERSON.getName());
		return broadcastCoppaMessage(iiXml, mData);
	}
	
	/**
	 * Broadcast coppa message. The actual call to the esb-client.
	 * 
	 * @param healthcareSiteXml the healthcare site xml
	 * @param mData the m data
	 * @return the string
	 * @throws C3PRCodedException the c3 pr coded exception
	 */
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