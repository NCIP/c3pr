/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.coppa.po.CorrelationNode;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.Person;

/**
 * The Class RemoteInvestigatorResolver.
 */
public class RemoteInvestigatorResolver extends BaseResolver implements RemoteResolver{
	
	/** The log. */
    private static Log log = LogFactory.getLog(RemoteInvestigatorResolver.class);

	
	/* 
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String externalId) {
		log.debug("Entering getRemoteEntityByUniqueId() for:" + this.getClass() + " - ExtId: " +externalId);
		
		Person person = CoppaObjectFactory.getCoppaPersonForExtension(externalId);
		
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new HealthCareProvider(), person, null);
		List<CorrelationNode> correlationNodeList = personOrganizationResolverUtils.getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		RemoteInvestigator populatedRemoteInvestigator = null;
		Person coppaPerson = null;
		for(CorrelationNode cNode: correlationNodeList){
			coppaPerson = personOrganizationResolverUtils.getCoppaPersonFromPlayerInCorrelationNode(cNode);
			populatedRemoteInvestigator = (RemoteInvestigator) populateRole(coppaPerson, "", null);	
	    	if(populatedRemoteInvestigator != null){
	    		remoteInvestigatorList.add(populatedRemoteInvestigator);
	    	}
		}
		log.debug("Exiting getRemoteEntityByUniqueId() for:" + this.getClass());
		if(remoteInvestigatorList.size() > 0){
			return remoteInvestigatorList.get(0);
		}
		return null;
	}
	
	
	/**
	 * Find By example remoteInvestigator
	 */
	public List<Object> find(Object example) {
		log.debug("Entering find() for:" + this.getClass());
		RemoteInvestigator remoteInvestigator = null;
		List<Object> remoteInvestigatorList = new ArrayList<Object>();;
		try{
			if(example instanceof RemoteInvestigator){
				remoteInvestigator = (RemoteInvestigator) example;
				
				if(!StringUtils.isEmpty(remoteInvestigator.getAssignedIdentifier())){
					log.debug("Searching based on NciId");
					remoteInvestigatorList = searchRoleBasedOnNciId(remoteInvestigator.getAssignedIdentifier(), new HealthCareProvider());
				} else if(remoteInvestigator.getHealthcareSiteInvestigators().size() > 0 &&
							remoteInvestigator.getHealthcareSiteInvestigators().get(0).getHealthcareSite() != null && 
							remoteInvestigator.getHealthcareSiteInvestigators().get(0).getHealthcareSite().getPrimaryIdentifier() != null){
					log.debug("Searching based on Organization");
					remoteInvestigatorList = searchRoleBasedOnOrganization(
							remoteInvestigator.getHealthcareSiteInvestigators().get(0).getHealthcareSite().getPrimaryIdentifier(), new HealthCareProvider());
				} else {
					log.debug("Searching based on Name");
					remoteInvestigatorList = searchRoleBasedOnName(remoteInvestigator.getFirstName(), 
							remoteInvestigator.getMiddleName(), remoteInvestigator.getLastName(), new HealthCareProvider());
				}
			}
		} catch(Exception e){
			log.error(e.getMessage());
		}
		log.debug("Exiting find() for:" + this.getClass());
		return remoteInvestigatorList;
	}

	
	/**
	 * Populate remote investigator.
	 * 
	 * @param coppaPerson the coppa person
	 * @param staffAssignedIdentifier the staff assigned identifier
	 * @param coppaOrganizationList the coppa organization list
	 * @param organizationIdToIdentifiedOrganizationsMap the organization id to identified organizations map
	 * @return the remote investigator
	 */
	public Object populateRole(Person coppaPerson, String staffAssignedIdentifier, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList,
																			Map<String, IdentifiedOrganization>	organizationIdToIdentifiedOrganizationsMap){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteInvestigator());
		if(object == null){
			return null;
		} else {
			RemoteInvestigator remoteInvestigator = (RemoteInvestigator) object;
			remoteInvestigator.setExternalId(coppaPerson.getIdentifier().getExtension());
			remoteInvestigator.setAssignedIdentifier(staffAssignedIdentifier);
			
			//Build HealthcareSite and HealthcareSiteInvestigator
			RemoteHealthcareSite healthcareSite = null;
			if(coppaOrganizationList != null && coppaOrganizationList.size()>0){
				IdentifiedOrganization identifiedOrganization = null;
				for(gov.nih.nci.coppa.po.Organization coppaOrganization: coppaOrganizationList){
					identifiedOrganization = organizationIdToIdentifiedOrganizationsMap.get(coppaOrganization.getIdentifier().getExtension());
					if(identifiedOrganization != null){
						healthcareSite = new RemoteHealthcareSite();
						personOrganizationResolverUtils.setCtepCodeFromExtension(healthcareSite, identifiedOrganization.getAssignedId().getExtension());
						healthcareSite.setName(CoppaObjectFactory.getName(coppaOrganization.getName()));
						healthcareSite.setExternalId(coppaOrganization.getIdentifier().getExtension());
						Address address = personOrganizationResolverUtils.getAddressFromCoppaOrganization(coppaOrganization);
						healthcareSite.setAddress(address);
						
						HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
						healthcareSiteInvestigator.setHealthcareSite(healthcareSite);
						healthcareSiteInvestigator.setInvestigator(remoteInvestigator);
						healthcareSiteInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
						
						remoteInvestigator.getHealthcareSiteInvestigators().add(healthcareSiteInvestigator);
					} else {
						log.error("IdentifiedOrganization is null for Organization with coppaId: "+coppaOrganization.getIdentifier().getExtension());
					}
				}
			}
			return remoteInvestigator;
		}
	}
	
	
	/**
	 * Populate remote investigator. Populate remote research staff. Called from searchStaffByrganization() 
	 * Wont load HCSI if identifiedOrganization is passed in as null (for getRemoteEntityByUniqueId()).
	 * 
	 * @param coppaPerson the coppa person
	 * @param staffAssignedIdentifier the staff assigned identifier
	 * @param identifiedOrganization the identified organization
	 * @return the remote investigator
	 */
	public Object populateRole(Person coppaPerson, String staffAssignedIdentifier, IdentifiedOrganization identifiedOrganization){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteInvestigator());
		if(object == null){
			return null;
		} else {
			RemoteInvestigator remoteInvestigator = (RemoteInvestigator)object;
			remoteInvestigator.setAssignedIdentifier(staffAssignedIdentifier);
			remoteInvestigator.setExternalId(coppaPerson.getIdentifier().getExtension());
			
			//Build HealthcareSite
			if(identifiedOrganization != null){
				HealthcareSite healthcareSite = new RemoteHealthcareSite();
				personOrganizationResolverUtils.setCtepCodeFromExtension(healthcareSite, identifiedOrganization.getAssignedId().getExtension());
				
				HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();
				hcsi.setHealthcareSite(healthcareSite);
				hcsi.setInvestigator(remoteInvestigator);
				remoteInvestigator.getHealthcareSiteInvestigators().add(hcsi);
			}
			return remoteInvestigator;
		}
	}
	

	public Object saveOrUpdate(Object example) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
