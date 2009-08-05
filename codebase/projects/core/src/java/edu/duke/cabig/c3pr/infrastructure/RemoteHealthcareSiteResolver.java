package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.iso._21090.II;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.constants.CoppaStatusCodeEnum;
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
	private Logger log = Logger.getLogger(RemoteHealthcareSiteResolver.class);
    
	private PersonOrganizationResolverUtils personOrganizationResolverUtils = null;
	
	
	/** Populate Remote Organization , given the Coppa Organization.
	 *  Populate the ctepCode   from  IdentifiedOrganization.assignedId.extension by calling the IdentifiedOrganization search.
	 *  Populate the externalId from  IdentifiedOrganization.identifier.extension by calling the IdentifiedOrganization search.
	 * 
	 * @param coppaOrganization
	 * @return RemoteHealthcareSite
	 */
	private RemoteHealthcareSite getRemoteHealthcareSite(gov.nih.nci.coppa.po.Organization coppaOrganization){

		RemoteHealthcareSite remoteHealthcareSite = null;
		if(coppaOrganization != null){
			remoteHealthcareSite = new RemoteHealthcareSite();
			//using coppa organization identier and previously obtained id of CTEP (hard coded in CoppaObjectFactory.getIIOfCTEP) get Identified organization 
			IdentifiedOrganization identifiedOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaForCorrelation(coppaOrganization.getIdentifier());
			
			String identifiedOrganizationXml = CoppaObjectFactory.getCoppaIdentfiedOrganization(identifiedOrganization);		
			String resultXml = "";
			try {
				resultXml = personOrganizationResolverUtils.broadcastIdentifiedOrganizationSearch(identifiedOrganizationXml);
			} catch (C3PRCodedException e) {
				//throwing a runtimeException here as this is non-recoverable exception
				throw new RuntimeException();
			}
			
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
			if (results.size() > 0) {
				identifiedOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(results.get(0));
				if (identifiedOrganization.getAssignedId() != null ) {
					//Setting the CTEP ID 
					personOrganizationResolverUtils.setCtepCodeFromExtension(remoteHealthcareSite, identifiedOrganization.getAssignedId().getExtension());	
				} 
			}	
			
			//set values from CoppaOrganization
			remoteHealthcareSite.setName(CoppaObjectFactory.getName(coppaOrganization.getName()));
			remoteHealthcareSite.setExternalId(coppaOrganization.getIdentifier().getExtension());
			remoteHealthcareSite.setCoppaStatusCode(CoppaStatusCodeEnum.getByCode(coppaOrganization.getStatusCode().getCode()));
			
			Address address = getAddressFromCoppaOrganization(coppaOrganization);
			remoteHealthcareSite.setAddress(address);
		}
		
		return remoteHealthcareSite;
	}

	
	/**
	 * Populate remote organization including the nciCode given the IdentifiedOrganization.
	 * No calls need to be invoked as the IdentifiedOrg is already provided
	 * 
	 * @param coppaOrganization the coppa organization
	 * @param identifiedOrganization the identified organization
	 * 
	 * @return the organization
	 */
	private Organization getRemoteHealthcareSite(gov.nih.nci.coppa.po.Organization coppaOrganization,IdentifiedOrganization identifiedOrganization){
		
		RemoteHealthcareSite remoteHealthcareSite = new RemoteHealthcareSite();
		if (identifiedOrganization.getAssignedId() != null ) {
			//Setting the CTEP ID 
			personOrganizationResolverUtils.setCtepCodeFromExtension(remoteHealthcareSite, identifiedOrganization.getAssignedId().getExtension());
		} 	
		
		Address address = getAddressFromCoppaOrganization(coppaOrganization);
		remoteHealthcareSite.setAddress(address);
		
		remoteHealthcareSite.setName(CoppaObjectFactory.getName(coppaOrganization.getName()));
		remoteHealthcareSite.setExternalId(coppaOrganization.getIdentifier().getExtension());
		
		return remoteHealthcareSite;
	}


	/** Populate the Address object from the coppaOrganization which is passed into it.
	 * 
	 * @param coppaOrganization
	 * @return Address
	 */
	private Address getAddressFromCoppaOrganization(gov.nih.nci.coppa.po.Organization coppaOrganization) {
		Address address  = new Address();
		
		address.setCity(CoppaObjectFactory.getCity(coppaOrganization.getPostalAddress()));
		address.setCountryCode(CoppaObjectFactory.getCountry(coppaOrganization.getPostalAddress()));
		address.setStateCode(CoppaObjectFactory.getState(coppaOrganization.getPostalAddress()));
		address.setPostalCode(CoppaObjectFactory.getZip(coppaOrganization.getPostalAddress()));
		address.setStreetAddress(CoppaObjectFactory.getStreet(coppaOrganization.getPostalAddress()));
		
		return address;
	}



	/**This is called by the interceptor when the object is loaded. 
	 * This is not used by the searches. It accpets the externalId as a parameter and 
	 * calls the getById on the Organization.
	 * 
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 * @return RemoteHealthcareSite
	 */
	public RemoteHealthcareSite getRemoteEntityByUniqueId(String externalId) {
		gov.nih.nci.coppa.po.Organization coppaOrganization = null;
		// using external id (coppa id) 
		II ii = CoppaObjectFactory.getIISearchCriteria(externalId);

		try {
			String iiXml = CoppaObjectFactory.getCoppaIIXml(ii);
			String resultXml = personOrganizationResolverUtils.broadcastOrganizationGetById(iiXml);
			
			//get coppa payload from caXchange ResponseMessage
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
			if (results.size() > 0) {
				coppaOrganization = CoppaObjectFactory.getCoppaOrganization(results.get(0));
			}
		} catch (C3PRCodedException e) {
			e.printStackTrace();
		}
		return getRemoteHealthcareSite(coppaOrganization);
	}
	
	/**
	 * Gets the remote organization by nci identifier.
	 * 
	 * First calls the Organization.search with the nciId set in the payload.
	 * Then calls the IdentifiedOrganization.search for every organization returned by the previous call.
	 * 
	 * @param nciInstituteCode the nci institute code
	 * @return the remote organization by nci identifier
	 */
	private Organization getRemoteOrganizationByNciIdentifier(String nciInstituteCode){
		// get by nci-id
		Organization remoteOrganizationPopulated = null;
		IdentifiedOrganization idOrg = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaOnCTEPId(nciInstituteCode);
		String idOrgPayLoad = CoppaObjectFactory.getCoppaIdentfiedOrganization(idOrg);
		String idOrgResult = "";
		try {
			idOrgResult = personOrganizationResolverUtils.broadcastIdentifiedOrganizationSearch(idOrgPayLoad);
		} catch (C3PRCodedException e) {
			throw new RuntimeException();
		}
		List<String> idOrgResults = XMLUtils.getObjectsFromCoppaResponse(idOrgResult);
		if (idOrgResults.size() >0) {
			IdentifiedOrganization coppaIdOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(idOrgResults.get(0));
			II orgId = coppaIdOrganization.getPlayerIdentifier();
			String iiXml = CoppaObjectFactory.getCoppaIIXml(orgId);
			//Get Organization based on player id of above.
			String resultOrg  = "";
			try {
				resultOrg = personOrganizationResolverUtils.broadcastOrganizationGetById(iiXml);
			} catch (Exception e) {
				log.error(e);
			}
			 
			List<String> orgResult = XMLUtils.getObjectsFromCoppaResponse(resultOrg);
			if (orgResult.size() > 0) {
				gov.nih.nci.coppa.po.Organization coppaOrganizationResult = CoppaObjectFactory.getCoppaOrganization(orgResult.get(0));
				remoteOrganizationPopulated = getRemoteHealthcareSite(coppaOrganizationResult,coppaIdOrganization);
			}
		}
		return remoteOrganizationPopulated;
	}
	
	/**
	 * Searches Coppa database for orgs simliar to the example RemoteHelathcareSite that is passed into it.
	 * 
	 * @param Object the remote HealthcareSite
	 * @return the object list; list of remoteHealthcareSites
	 */
	@SuppressWarnings("unchecked")
	public List<Object> find(Object example) {	
		RemoteHealthcareSite remoteOrgExample = (RemoteHealthcareSite)example;
		
		//get by nci-id
		if (remoteOrgExample.getPrimaryIdentifier() != null) {
			List<Object> remoteOrganizations = new ArrayList<Object>();
			Organization remoteOrganizationPopulated = getRemoteOrganizationByNciIdentifier(remoteOrgExample.getPrimaryIdentifier());
			if (remoteOrganizationPopulated != null) {
				remoteOrganizations.add(remoteOrganizationPopulated);
			}
			return remoteOrganizations;
		}
		
		//get by all other criteria
		String payLoad = 
			CoppaObjectFactory.getCoppaOrganizationXml(remoteOrgExample.getName(), null, remoteOrgExample.getAddress().getCity(),
					null, null, remoteOrgExample.getAddress().getCountryCode());
		
		String resultXml  = "";
		try {
			resultXml  = personOrganizationResolverUtils.broadcastOrganizationSearch(payLoad);
		} catch (C3PRCodedException e) {
			log.error(e);
		}
		
		List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		List<gov.nih.nci.coppa.po.Organization> coppaOrganizations = new ArrayList<gov.nih.nci.coppa.po.Organization>();
		
		for (String result:results) {
			gov.nih.nci.coppa.po.Organization coppaOrganization = CoppaObjectFactory.getCoppaOrganization(result);
			coppaOrganizations.add(coppaOrganization);
		}

		List<Object> remoteHealthcareSites = new ArrayList<Object>();
		for (gov.nih.nci.coppa.po.Organization corg:coppaOrganizations) {
			RemoteHealthcareSite remoteHealthcareSite = getRemoteHealthcareSite(corg);
			if (remoteHealthcareSite != null) {
				remoteHealthcareSites.add(remoteHealthcareSite);
			}
		}
		
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
			log.error(e);
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



