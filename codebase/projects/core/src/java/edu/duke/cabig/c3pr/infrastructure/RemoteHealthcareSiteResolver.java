package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.iso._21090.II;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.PersonResolverUtils;
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
    
	private PersonResolverUtils personResolverUtils = null;
	
	/**
	 * Convert to organization.
	 * 
	 * @param organizationDto the organization dto
	 * @return the list< object>
	 */
	public RemoteHealthcareSite populateRemoteHealthcareSite(gov.nih.nci.coppa.po.Organization coppaOrganization){

		RemoteHealthcareSite remoteHealthcareSite= null;
		if(coppaOrganization != null){
			//using coppa organization identier and previously obtained id of CTEP (hard coded in CoppaObjectFactory.getIIOfCTEP) get Identified organization 
			IdentifiedOrganization identifiedOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaForCorrelation(coppaOrganization.getIdentifier());
			
			String identifiedOrganizationXml = CoppaObjectFactory.getCoppaIdentfiedOrganization(identifiedOrganization);		
			String resultXml = "";
			try {
				resultXml = personResolverUtils.broadcastIdentifiedOrganizationSearch(identifiedOrganizationXml);
			} catch (C3PRCodedException e) {
				log.error(e.getMessage());
			}
			
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
			if (results.size() > 0) {
				identifiedOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(results.get(0));
			}	
			
			if (identifiedOrganization.getAssignedId() != null ) {
				remoteHealthcareSite = new RemoteHealthcareSite();
				// CTEP ID 
				remoteHealthcareSite.setNciInstituteCode(identifiedOrganization.getAssignedId().getExtension());	
				remoteHealthcareSite.setName(CoppaObjectFactory.getName(coppaOrganization.getName()));
				remoteHealthcareSite.setExternalId(coppaOrganization.getIdentifier().getExtension());
				
				Address address = new Address();
				address.setCity(CoppaObjectFactory.getCity(coppaOrganization.getPostalAddress()));
				address.setCountryCode(CoppaObjectFactory.getCountry(coppaOrganization.getPostalAddress()));
				
				remoteHealthcareSite.setAddress(address);
			} else {
				return null;
			}
		}
		
		return remoteHealthcareSite;
	}
	
	
	/**
	 * Populate remote organization including the nciCode.
	 * 
	 * @param coppaOrganization the coppa organization
	 * @param identifiedOrganization the identified organization
	 * 
	 * @return the organization
	 */
	public Organization populateRemoteHealthcareSite(gov.nih.nci.coppa.po.Organization coppaOrganization,IdentifiedOrganization identifiedOrganization){
		
		RemoteHealthcareSite remoteOrganization = new RemoteHealthcareSite();
		remoteOrganization.setNciInstituteCode(identifiedOrganization.getAssignedId().getExtension());	
		remoteOrganization.setName(CoppaObjectFactory.getName(coppaOrganization.getName()));

		Address address = new Address();
		remoteOrganization.setAddress(address);
		
		address.setCity(CoppaObjectFactory.getCity(coppaOrganization.getPostalAddress()));
		address.setCountryCode(CoppaObjectFactory.getCountry(coppaOrganization.getPostalAddress()));
		
		remoteOrganization.setExternalId(coppaOrganization.getIdentifier().getExtension());
		return remoteOrganization;
	}

	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String externalId) {
		gov.nih.nci.coppa.po.Organization coppaOrganization = null;
		// using external id (coppa id ) 
		II ii = CoppaObjectFactory.getIISearchCriteria(externalId);

		try {
			String iiXml = CoppaObjectFactory.getCoppaIIXml(ii);
			String resultXml = personResolverUtils.broadcastOrganizationGetById(iiXml);
			
			//get coppa payload from caXchange ResponseMessage
			List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
			if (results.size() > 0) {
				coppaOrganization = CoppaObjectFactory.getCoppaOrganization(results.get(0));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return populateRemoteHealthcareSite(coppaOrganization);
	}
	
	/**
	 * Gets the remote organization by nci identifier.
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
			idOrgResult = personResolverUtils.broadcastIdentifiedOrganizationSearch(idOrgPayLoad);
		} catch (C3PRCodedException e) {
			log.error(e);
		}
		List<String> idOrgResults = XMLUtils.getObjectsFromCoppaResponse(idOrgResult);
		if (idOrgResults.size() >0) {
			IdentifiedOrganization coppaIdOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(idOrgResults.get(0));
			II orgId = coppaIdOrganization.getPlayerIdentifier();
			String iiXml = CoppaObjectFactory.getCoppaIIXml(orgId);
			//Get Organization based on player id of above.
			String resultOrg  = "";
			try {
				resultOrg = personResolverUtils.broadcastOrganizationGetById(iiXml);
			} catch (Exception e) {
				log.error(e);
			}
			 
			List<String> orgResult = XMLUtils.getObjectsFromCoppaResponse(resultOrg);
			if (orgResult.size() > 0) {
				gov.nih.nci.coppa.po.Organization coppaOrganizationResult = CoppaObjectFactory.getCoppaOrganization(orgResult.get(0));
				remoteOrganizationPopulated = populateRemoteHealthcareSite(coppaOrganizationResult,coppaIdOrganization);
			}
		}
		return remoteOrganizationPopulated;
	}
	
	/**
	 * Searches Coppa database for orgs simliar to the example RemoteHelathcareSite that is passed in
	 * 
	 * @param Object the remote HealthcareSite
	 * @return the object list; list of remoteHealthcareSites
	 */
	@SuppressWarnings("unchecked")
	public List<Object> find(Object example) {	
		RemoteHealthcareSite remoteOrgExample = (RemoteHealthcareSite)example;
		
		//get by nci-id
		if (remoteOrgExample.getNciInstituteCode() != null) {
			List<Object> remoteOrganizations = new ArrayList<Object>();
			Organization remoteOrganizationPopulated = getRemoteOrganizationByNciIdentifier(remoteOrgExample.getNciInstituteCode());
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
			resultXml  = personResolverUtils.broadcastOrganizationSearch(payLoad);
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
			RemoteHealthcareSite remoteHealthcareSite = populateRemoteHealthcareSite(corg);
			if (remoteHealthcareSite != null) {
				remoteHealthcareSites.add(remoteHealthcareSite);
			}
		}	
		
		return remoteHealthcareSites;
	}
	
	
    public PersonResolverUtils getPersonResolverUtils() {
		return personResolverUtils;
	}


	public void setPersonResolverUtils(PersonResolverUtils personResolverUtils) {
		this.personResolverUtils = personResolverUtils;
	}
	
}



