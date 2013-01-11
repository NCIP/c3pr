/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iso._21090.DSETII;
import org.iso._21090.II;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.PersonOrganizationResolverUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;

/**
 * The Class RemoteHealthcareSiteResolver.
 * @author Vinay Gangoli, Ramakrishna Gundala
 */
public class RemoteHealthcareSiteResolver implements RemoteResolver{

	/** The exception helper. */
	protected C3PRExceptionHelper exceptionHelper;

	/** The log. */
	private static Log log = LogFactory.getLog(RemoteHealthcareSiteResolver.class);
    
	private PersonOrganizationResolverUtils personOrganizationResolverUtils;
	
	
	/**This is called by the interceptor when the object is loaded. 
	 * This is not used by the searches. It accepts the externalId as a parameter and 
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
		return personOrganizationResolverUtils.getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization, false);
	}
	
	
	/**
	 * Searches Coppa database for orgs similar to the example RemoteHelathcareSite that is passed into it.
	 * 
	 * @param Object the remote HealthcareSite
	 * @return the object list; list of remoteHealthcareSites
	 */
	@SuppressWarnings("unchecked")
	public List<Object> find(Object example) {	
		log.debug("Entering find() for:" + this.getClass());
		List<Object> remoteOrganizations = getRemoteOrganizationsByExample((RemoteHealthcareSite)example);
		log.debug("Exiting find() for:" + this.getClass());
		return remoteOrganizations;
	}
	
	/**
	 * Gets the remote organization by nci identifier/name.
	 * 
	 * First calls the SearchCorrelationsWithEntities with IdentifiedOrganization as the correlationEntity.
	 * 
	 * @param RemoteHealthcareSite the example remoteHealthcareSite
	 * @return the remote organization
	 */
	private List<Object> getRemoteOrganizationsByExample(RemoteHealthcareSite remoteHealthcareSite){
		 List<Object> remoteOrganizations = new ArrayList<Object>();
		// get by name
		gov.nih.nci.coppa.po.Organization coppaOrganization = CoppaObjectFactory.getCoppaOrganization(remoteHealthcareSite.getName(), null, 
								remoteHealthcareSite.getAddress().getCity(), null, null, remoteHealthcareSite.getAddress().getCountryCode(), null);
		if (remoteHealthcareSite.getExternalId() != null) {
			II ii = new II();
			ii.setExtension(remoteHealthcareSite.getExternalId());
			ii.setRoot(PersonOrganizationResolverUtils.ORGANIZATION_ROOT);
			coppaOrganization.setIdentifier(ii);
		}
		gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization = new gov.nih.nci.coppa.po.IdentifiedOrganization();
		if (remoteHealthcareSite.getPrimaryIdentifier() != null) {
			II ii = new II();
			ii.setExtension(remoteHealthcareSite.getPrimaryIdentifier());
			identifiedOrganization.setAssignedId(ii);
		}
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(identifiedOrganization, coppaOrganization, null);

		String resultXml = null;
		try {
			resultXml = personOrganizationResolverUtils.broadcastSearchCorrelationsWithEntities(correlationNodeXmlPayload, true, false);
		} catch (C3PRCodedException e) {
			log.error(e.getStackTrace());
		}
		remoteOrganizations = buildOrganizationsFromResults(resultXml);
		return remoteOrganizations;
	}
	
	/**
	 * Builds the organizations from results.
	 * 
	 * @param resultXml the result xml
	 * @return the list
	 */
	private List<Object> buildOrganizationsFromResults(String resultXml) {
		List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		List<Object> remoteOrganizations = new ArrayList<Object>();

		Organization remoteOrganization = null;
		for (String result:results) {
			gov.nih.nci.coppa.po.CorrelationNode correlationNode = CoppaObjectFactory.getCorrelationNodeObjectFromXml(result);
			remoteOrganization = populateRemoteOrganization(correlationNode);
			remoteOrganizations.add(remoteOrganization);
		}
		return remoteOrganizations;
	}

	 /**
	* Populate c3pr organization with COPPA organization . 
	* @param correlationNode
	* @return
	*/
	private Organization populateRemoteOrganization(gov.nih.nci.coppa.po.CorrelationNode correlationNode){
		gov.nih.nci.coppa.po.Organization coppaOrganization = null;
		for(int i = 0; i < correlationNode.getPlayer().getContent().size(); i++){
			Object object = correlationNode.getPlayer().getContent().get(i);
			if(object instanceof gov.nih.nci.coppa.po.Organization){
				coppaOrganization = (gov.nih.nci.coppa.po.Organization) object;
				break;
			}
		}
		 
		gov.nih.nci.coppa.po.IdentifiedOrganization identifiedOrganization = null;
		for(int i = 0; i < correlationNode.getCorrelation().getContent().size(); i++){
			Object object = correlationNode.getCorrelation().getContent().get(i);
			if(object instanceof gov.nih.nci.coppa.po.IdentifiedOrganization){
				identifiedOrganization = (gov.nih.nci.coppa.po.IdentifiedOrganization) object;
				break;
			}
		}
		
		RemoteHealthcareSite remoteHealthcareSite = personOrganizationResolverUtils.getRemoteHealthcareSiteFromCoppaOrganization(coppaOrganization, false);
		//Setting the CTEP ID 
		if (identifiedOrganization != null && identifiedOrganization.getAssignedId() != null && 
						identifiedOrganization.getAssignedId().getIdentifierName().equals(PersonOrganizationResolverUtils.CTEP_ID)) {
			personOrganizationResolverUtils.setCtepCodeFromExtension(remoteHealthcareSite, identifiedOrganization.getAssignedId().getExtension());
		} else {
			return null;
		}
		return remoteHealthcareSite;
	}

	
	/**
	 * Saves orUpdates the remoteOrganization to Coppa.
	 * This is utilized by the write/update flow and is currently not in use.
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
		//If save was successful then get the saved entity by externalID and return it to the calling method.
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
