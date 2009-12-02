package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iso._21090.DSETII;
import org.iso._21090.II;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.PersonOrganizationResolverUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.po.IdentifiedOrganization;

/**
 * The Class RemoteHealthcareSiteResolver.
 * @author Vinay Gangoli, Ramakrishna Gundala
 */
public class RemoteHealthcareSiteResolver implements RemoteResolver{

	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	/** The log. */
	private static Log log = LogFactory.getLog(RemoteHealthcareSiteResolver.class);
    
	private PersonOrganizationResolverUtils personOrganizationResolverUtils = null;
	
	public static final String CTEP_ID = "CTEP ID";
	/**
	 * Populate remote organization including the ctepCode given the IdentifiedOrganization.
	 * No calls need to be invoked as the IdentifiedOrg is already provided
	 * 
	 * @param coppaOrganization the coppa organization
	 * @param identifiedOrganization the identified organization
	 * 
	 * @return the organization
	 */
	private Organization getRemoteHealthcareSite(gov.nih.nci.coppa.po.Organization coppaOrganization,IdentifiedOrganization identifiedOrganization){
		
		RemoteHealthcareSite remoteHealthcareSite = null;
		if (identifiedOrganization.getAssignedId() != null && identifiedOrganization.getAssignedId().getIdentifierName().equals(CTEP_ID)) {
			remoteHealthcareSite = new RemoteHealthcareSite();
			//Setting the CTEP ID 
			personOrganizationResolverUtils.setCtepCodeFromExtension(remoteHealthcareSite, identifiedOrganization.getAssignedId().getExtension());
			
			Address address = personOrganizationResolverUtils.getAddressFromCoppaOrganization(coppaOrganization);
			remoteHealthcareSite.setAddress(address);
			remoteHealthcareSite.setName(CoppaObjectFactory.getName(coppaOrganization.getName()));
			remoteHealthcareSite.setExternalId(coppaOrganization.getIdentifier().getExtension());
		} else {
			log.error(CoppaObjectFactory.getName(coppaOrganization.getName()) +" does not have valid CTEP ID. Skipping it.");
		}
		return remoteHealthcareSite;
	}


	/**This is called by the interceptor when the object is loaded. 
	 * This is not used by the searches. It accpets the externalId as a parameter and 
	 * calls the getById on the Organization.
	 * 
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 * @return RemoteHealthcareSite
	 */
	public RemoteHealthcareSite getRemoteEntityByUniqueId(String externalId) {
		log.debug("Entering getRemoteEntityByUniqueId() for:" + this.getClass() + " - ExtId: " +externalId);
		gov.nih.nci.coppa.po.Organization coppaOrganization = null;
		DSETII dsetii = CoppaObjectFactory.getDSETIISearchCriteria(externalId);

		try {
			String iiXml = CoppaObjectFactory.getCoppaIIXml(dsetii);
			String resultXml = personOrganizationResolverUtils.broadcastOrganizationGetById(iiXml);
			
			//get coppa payload from caXchange ResponseMessage
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
			if (results.size() > 0) {
				coppaOrganization = CoppaObjectFactory.getCoppaOrganization(results.get(0));
			}
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}
		log.debug("Exiting getRemoteEntityByUniqueId() for:" + this.getClass());
		return personOrganizationResolverUtils.getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization);
	}
	
	/**
	 * Gets the remote organization by nci identifier.
	 * 
	 * First calls the IdentifiedOrganization.search with the nciId set in the payload.
	 * Then calls the Organization.getById for every organizationId returned by the previous call.
	 * 
	 * @param nciInstituteCode the nci institute code
	 * @return the remote organization by nci identifier
	 */
	private List<Organization> getRemoteOrganizationsByNciIdentifier(String nciInstituteCode){
		// get by nci-id
		List <Organization> remoteOrganizationList = new ArrayList<Organization>();
		IdentifiedOrganization idOrg = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaOnCTEPId(nciInstituteCode);
		String idOrgPayLoad = CoppaObjectFactory.getCoppaIdentfiedOrganization(idOrg);
		String idOrgResult = "";
		try {
			idOrgResult = personOrganizationResolverUtils.broadcastIdentifiedOrganizationSearch(idOrgPayLoad);
		} catch (C3PRCodedException e) {
			throw new RuntimeException();
		}
		List<String> idOrgResults = XMLUtils.getObjectsFromCoppaResponse(idOrgResult);
		for(int i = 0; i < idOrgResults.size(); i++){
			IdentifiedOrganization coppaIdOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(idOrgResults.get(i));
			II orgId = coppaIdOrganization.getPlayerIdentifier();
			String iiXml = CoppaObjectFactory.getCoppaIIXml(orgId);
			//Get Organization based on player id of above.
			String resultOrg  = "";
			try {
				resultOrg = personOrganizationResolverUtils.broadcastOrganizationGetById(iiXml);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			 
			List<String> orgResult = XMLUtils.getObjectsFromCoppaResponse(resultOrg);
			if (orgResult.size() > 0) {
				gov.nih.nci.coppa.po.Organization coppaOrganizationResult = CoppaObjectFactory.getCoppaOrganization(orgResult.get(0));
				Organization remoteOrganization = getRemoteHealthcareSite(coppaOrganizationResult,coppaIdOrganization); 
				if(remoteOrganization != null){
					remoteOrganizationList.add(remoteOrganization);
				}
			}
		}
		return remoteOrganizationList;
	}
	
	/**
	 * Searches Coppa database for orgs simliar to the example RemoteHelathcareSite that is passed into it.
	 * 
	 * @param Object the remote HealthcareSite
	 * @return the object list; list of remoteHealthcareSites
	 */
	@SuppressWarnings("unchecked")
	public List<Object> find(Object example) {	
		log.debug("Entering find() for:" + this.getClass());
		RemoteHealthcareSite remoteOrgExample = (RemoteHealthcareSite)example;
		
		//get by nci-id
		if (remoteOrgExample.getPrimaryIdentifier() != null) {
			List<Object> remoteOrganizations = new ArrayList<Object>();
			List<Organization> remoteOrganizationsList = getRemoteOrganizationsByNciIdentifier(remoteOrgExample.getPrimaryIdentifier());
			if (remoteOrganizationsList != null) {
				remoteOrganizations.addAll(remoteOrganizationsList);
			}
			return remoteOrganizations;
		}
		
		//get by all other criteria
		String payLoad = CoppaObjectFactory.getCoppaOrganizationXml(remoteOrgExample.getName(), null, 
				remoteOrgExample.getAddress().getCity(), null, null, remoteOrgExample.getAddress().getCountryCode());
		String resultXml  = "";
		try {
			resultXml  = personOrganizationResolverUtils.broadcastOrganizationSearchWithLimit(payLoad);
		} catch (C3PRCodedException e) {
			log.error(e);
		}
		
		List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		List<gov.nih.nci.coppa.po.Organization> coppaOrganizations = new ArrayList<gov.nih.nci.coppa.po.Organization>();
		for (String result:results) {
			gov.nih.nci.coppa.po.Organization coppaOrganization = CoppaObjectFactory.getCoppaOrganization(result);
			coppaOrganizations.add(coppaOrganization);
		}

		//get the map of identifiedOrgs for every org.
		Map<String, IdentifiedOrganization> identifierOrganizationsMap = 
							personOrganizationResolverUtils.getIdentifiedOrganizationsForOrganizationsList(coppaOrganizations);
		
		RemoteHealthcareSite remoteHealthcareSite;
		List<Object> remoteHealthcareSites = new ArrayList<Object>();
		IdentifiedOrganization identifiedOrganization;
		for (gov.nih.nci.coppa.po.Organization coppaOrganization:coppaOrganizations) {
			remoteHealthcareSite = null;
			if(identifierOrganizationsMap.get(coppaOrganization.getIdentifier().getExtension()) != null){
				remoteHealthcareSite = personOrganizationResolverUtils.getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization, false);
				if (remoteHealthcareSite != null) {
					identifiedOrganization = identifierOrganizationsMap.get(coppaOrganization.getIdentifier().getExtension());
					personOrganizationResolverUtils.setCtepCodeFromExtension(remoteHealthcareSite, identifiedOrganization.getAssignedId().getExtension());
					remoteHealthcareSites.add(remoteHealthcareSite);
				}
			}
		}
		log.debug("Exiting find() for:" + this.getClass());
		return remoteHealthcareSites;
	}
	


	/**
	 * Saves orUpdates the remoteOrganization to Coppa.
	 * This is utilized by the write/pdate flow and is currently not in use.
	 * 
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#saveOrUpdate(java.lang.Object)
	 */
	public Object saveOrUpdate(Object remoteOrg) {
		RemoteHealthcareSite remoteHealthcareSite = (RemoteHealthcareSite)remoteOrg;
		String payLoad = 
			CoppaObjectFactory.getCoppaOrganizationXml(remoteHealthcareSite.getName(), remoteHealthcareSite.getAddress().getStreetAddress(), remoteHealthcareSite.getAddress().getCity(),
					remoteHealthcareSite.getAddress().getStateCode(), remoteHealthcareSite.getAddress().getPostalCode(), remoteHealthcareSite.getAddress().getCountryCode(), remoteHealthcareSite.getEmailAsString());
		
		String resultXml  = "";
		try {
			resultXml  = personOrganizationResolverUtils.broadcastOrganizationCreate(payLoad);
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}
		
		Object savedRemoteHealthcareSite = null;
		//If save was succesfull then get the saved entity by externalID and return it to the calling method.
		if(resultXml.contains("SUCCESS")){
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
			
			gov.nih.nci.coppa.po.Id coppaId = CoppaObjectFactory.getCoppaId(results.get(0));
			savedRemoteHealthcareSite = getRemoteEntityByUniqueId(coppaId.getExtension());
			
			return savedRemoteHealthcareSite;
		} else {
			return remoteHealthcareSite;
		}
    }
	
	
    public PersonOrganizationResolverUtils getPersonOrganizationResolverUtils() {
		return personOrganizationResolverUtils;
	}


	public void setPersonOrganizationResolverUtils(PersonOrganizationResolverUtils personResolverUtils) {
		this.personOrganizationResolverUtils = personResolverUtils;
	}
	
}



