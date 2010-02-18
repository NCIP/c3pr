package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iso._21090.II;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.PersonOrganizationResolverUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.po.CorrelationNode;
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;

/**
 * The Class RemoteInvestigatorResolver.
 */
public class RemoteInvestigatorResolver implements RemoteResolver{
	
	/** The log. */
    private static Log log = LogFactory.getLog(RemoteInvestigatorResolver.class);

    /** The person resolver utils. */
    private PersonOrganizationResolverUtils personOrganizationResolverUtils;
    
	
	/* 
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String externalId) {
		log.debug("Entering getRemoteEntityByUniqueId() for:" + this.getClass() + " - ExtId: " +externalId);
		
		Person person = CoppaObjectFactory.getCoppaPerson(null, null, null);
		person.getIdentifier().setExtension(externalId);
		
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new HealthCareProvider(), person, null);
		List<CorrelationNode> correlationNodeList = personOrganizationResolverUtils.getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		RemoteInvestigator populatedRemoteInvestigator = null;
		for(CorrelationNode cNode: correlationNodeList){
			Person coppaPerson = personOrganizationResolverUtils.getCoppaPersonFromPlayerInCorrelationNode(cNode);
			
			populatedRemoteInvestigator = populateRemoteInvestigator(coppaPerson, "", null);	
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
					//search based on nci id of person
					log.debug("Searching based on NciId");
					remoteInvestigatorList = searchInvestigatorBasedOnNciId(remoteInvestigator);
				} else if(remoteInvestigator.getHealthcareSiteInvestigators().size() > 0 &&
							remoteInvestigator.getHealthcareSiteInvestigators().get(0).getHealthcareSite() != null && 
							remoteInvestigator.getHealthcareSiteInvestigators().get(0).getHealthcareSite().getPrimaryIdentifier() != null){
					//search based on Organization
					log.debug("Searching based on Organization");
					remoteInvestigatorList = searchInvestigatorBasedOnOrganization(remoteInvestigator);
				} else {
					//search based on name
					log.debug("Searching based on Name");
					remoteInvestigatorList = searchInvestigatorBasedOnName(remoteInvestigator);
				}
			}
		} catch(Exception e){
			log.error(e.getMessage());
		}
		log.debug("Exiting find() for:" + this.getClass());
		return remoteInvestigatorList;
	}
	
	/**
	 * Search investigator based on organization.
	 * 
	 * @param remoteInvestigatorExample the remote investigator example
	 * @return the list< object>
	 */
	private List<Object> searchInvestigatorBasedOnOrganization(RemoteInvestigator remoteInvestigatorExample){
    	//Get IdentifiedOrganization by ctepId
        IdentifiedOrganization identifiedOrganizationSearchCriteria = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaOnCTEPId
        			(remoteInvestigatorExample.getHealthcareSiteInvestigators().get(0).getHealthcareSite().getPrimaryIdentifier());
        String payload = CoppaObjectFactory.getCoppaIdentfiedOrganization(identifiedOrganizationSearchCriteria);
        String results = null;
		try {
			results = personOrganizationResolverUtils.broadcastIdentifiedOrganizationSearch(payload);
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}

		//Assuming here that the ctepCode search yields exactly one organization
        List<String> resultObjects = XMLUtils.getObjectsFromCoppaResponse(results);
        if(resultObjects.size() == 0){
        	return new ArrayList<Object>();
        }
        if(resultObjects.size() > 1){
        	log.error("searchInvestigatorBasedOnOrganization: The ctep code matches more than one organization. The current implementation uses only the first match as it" +
        			"assumes the ctep code search to always yield one exact match.");
        }
        
        IdentifiedOrganization coppaIdOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(resultObjects.get(0));
        II organizationIdentifier = coppaIdOrganization.getPlayerIdentifier();
        Organization organization = CoppaObjectFactory.getCoppaOrganizationFromII(organizationIdentifier);
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new HealthCareProvider(), null, organization);
		
		List<CorrelationNode> correlationNodeList = personOrganizationResolverUtils.getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		List<Object> remoteInvestigatorList = getRemoteInvestigatorsFromCorrelationNodesList(correlationNodeList, coppaIdOrganization, null);
		
		return remoteInvestigatorList;
	}


	/**
	 * Search investigator based on assigned id.
	 * 
	 * @param remoteInvestigatorExample the remote investigator example
	 * @return the list< object>
	 */
	private List<Object> searchInvestigatorBasedOnNciId(RemoteInvestigator remoteInvestigatorExample) {
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		List<IdentifiedPerson> identifiedPersonsList = null;
		if (remoteInvestigatorExample.getAssignedIdentifier() != null) {
             //get Identified Organization using the Identifier provided
             IdentifiedPerson identifiedPersonToSearch = CoppaObjectFactory.getCoppaIdentfiedPersonSearchCriteriaOnCTEPId(remoteInvestigatorExample.getAssignedIdentifier());
             identifiedPersonsList = personOrganizationResolverUtils.getIdentifiedPerson(identifiedPersonToSearch);
             if (identifiedPersonsList.size() == 0) {
                 return remoteInvestigatorList;
             }
		}
		String personIiExtension = null;
		Person person = null;
		String correlationNodeXmlPayload = null;
		List<CorrelationNode> correlationNodeList = null;

		//The identifiedPerson search by CTEP code is a like match & not exact match and returns more than one result.
		//So Iterate thru the IdentifiedPersons fetched and isolate the one whose extension exactly matches the search criteria. 
		IdentifiedPerson identifiedPersonWithExactCtepIdMatch = null;
		for(IdentifiedPerson identifiedPerson: identifiedPersonsList){
			if(identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(PersonOrganizationResolverUtils.CTEP_PERSON)){
				if(identifiedPerson.getAssignedId().getExtension().equalsIgnoreCase(remoteInvestigatorExample.getAssignedIdentifier())){
					identifiedPersonWithExactCtepIdMatch = identifiedPerson;
					break;
				}
			}
		}
		
		if(identifiedPersonWithExactCtepIdMatch != null){
			personIiExtension = identifiedPersonWithExactCtepIdMatch.getPlayerIdentifier().getExtension();
			if(personIiExtension == null){
				return remoteInvestigatorList;
			}
			person = CoppaObjectFactory.getCoppaPersonForExtension(personIiExtension);
			correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new HealthCareProvider(), person, null);
			
			correlationNodeList = personOrganizationResolverUtils.getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
			remoteInvestigatorList.addAll(getRemoteInvestigatorsFromCorrelationNodesList(correlationNodeList, null, remoteInvestigatorExample.getAssignedIdentifier()));
		}
		return remoteInvestigatorList;
	}


	/**
	 * Search investigator based on name.
	 * 
	 * @param remoteInvestigator the remote investigator
	 * @return the list< object>
	 */
	private List<Object> searchInvestigatorBasedOnName(RemoteInvestigator remoteInvestigator) {
		Person person = CoppaObjectFactory.getCoppaPerson(remoteInvestigator.getFirstName(), remoteInvestigator.getMiddleName(), remoteInvestigator.getLastName());
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new HealthCareProvider(), person, null);
		
		List<CorrelationNode> correlationNodeList = personOrganizationResolverUtils.getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		List<Object> remoteInvestigatorList = getRemoteInvestigatorsFromCorrelationNodesList(correlationNodeList, null, null);
		return remoteInvestigatorList;
	}

	
	/**
	 * Gets the remote investigators from correlation nodes list.
	 * 
	 * @param correlationNodeList the correlation node list
	 * @param organizationCtepCode the organizations CTEP Code. null value indicates that this method needs to call identified_organization
	 * @param investigatorAssignedIdentifier the investigators assigned identifier. null value indicates that this method needs to call identified_person
	 * @return the remote investigators from correlation nodes list
	 */
	private List<Object> getRemoteInvestigatorsFromCorrelationNodesList(
									List<CorrelationNode> correlationNodeList, IdentifiedOrganization identifiedOrganization, String investigatorAssignedIdentifier) {
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		HashMap<String, List<Organization>> personIdToCoppaOrganizationsHashMap = new HashMap<String, List<Organization>>();
		List<Organization> listOfAllOrganizations = new ArrayList<Organization>();
		List<Person> listOfAllPersons = new ArrayList<Person>();
		Person tempPerson = null;
		Organization tempOrganization  = null;
		for(CorrelationNode cNode: correlationNodeList){
			tempPerson = personOrganizationResolverUtils.getCoppaPersonFromPlayerInCorrelationNode(cNode);
			tempOrganization = personOrganizationResolverUtils.getCoppaOrganizationFromScoperInCorrelationNode(cNode);
			
			//building a list of all organizations
			listOfAllOrganizations.add(tempOrganization);

			List<Organization> organizationList = null;
			if(personIdToCoppaOrganizationsHashMap.containsKey(tempPerson.getIdentifier().getExtension())){
				organizationList = personIdToCoppaOrganizationsHashMap.get(tempPerson.getIdentifier().getExtension());
				organizationList.add(tempOrganization);
			} else {
				organizationList = new ArrayList<Organization>();
				organizationList.add(tempOrganization);
				personIdToCoppaOrganizationsHashMap.put(tempPerson.getIdentifier().getExtension(), organizationList);
				//building a list of all persons. This is in the else loop because different correlationNodes can have the same person.
				//So we only add when the personIdToCoppaOrganizationsHashMap does not contain the personId as the key.
				listOfAllPersons.add(tempPerson);
			}
		}
		
		Map<String, IdentifiedOrganization> organizationIdToIdentifiedOrganizationsMap = null;
		Map<String, List<IdentifiedPerson>> personIdToIdentifiedPersonMap = null;
		if(identifiedOrganization == null){
			organizationIdToIdentifiedOrganizationsMap = 
				personOrganizationResolverUtils.getIdentifiedOrganizationsForOrganizationsList(listOfAllOrganizations);
		}
		if(investigatorAssignedIdentifier == null){
			personIdToIdentifiedPersonMap = personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(listOfAllPersons);
		}
		
		RemoteInvestigator populatedRemoteInvestigator  = null;
		Person coppaPerson = null;
		String assignedIdentifier = null;
		for(CorrelationNode cNode: correlationNodeList){
			coppaPerson = personOrganizationResolverUtils.getCoppaPersonFromPlayerInCorrelationNode(cNode);
			//Only get the AssignedIdentifier if its passed in as null
			if(investigatorAssignedIdentifier == null){
				assignedIdentifier = personOrganizationResolverUtils.getAssignedIdentifierFromPersonIdToIdentifiedPersonMap(coppaPerson, personIdToIdentifiedPersonMap);
				if(assignedIdentifier == null){
					assignedIdentifier = coppaPerson.getIdentifier().getExtension();
				}
			} else {
				assignedIdentifier = investigatorAssignedIdentifier;
			}
			//Call the right populate method based on whether identifiedOrganization is already available or not
			if(identifiedOrganization == null){
				populatedRemoteInvestigator = populateRemoteInvestigator(coppaPerson, assignedIdentifier, 
						personIdToCoppaOrganizationsHashMap.get(coppaPerson.getIdentifier().getExtension()), organizationIdToIdentifiedOrganizationsMap);	
			} else {
				populatedRemoteInvestigator = populateRemoteInvestigator(coppaPerson, assignedIdentifier, identifiedOrganization);
			}

			if(populatedRemoteInvestigator != null){
	    		remoteInvestigatorList.add(populatedRemoteInvestigator);
	    	}
		}
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
	private RemoteInvestigator populateRemoteInvestigator(Person coppaPerson, String staffAssignedIdentifier, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList,
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
	private RemoteInvestigator populateRemoteInvestigator(Person coppaPerson, String staffAssignedIdentifier, IdentifiedOrganization identifiedOrganization){
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
	

	
	public PersonOrganizationResolverUtils getPersonOrganizationResolverUtils() {
		return personOrganizationResolverUtils;
	}

	public void setPersonOrganizationResolverUtils(PersonOrganizationResolverUtils personResolverUtils) {
		this.personOrganizationResolverUtils = personResolverUtils;
	}

	public Object saveOrUpdate(Object example) {
		// TODO Auto-generated method stub
		return null;
	}
	
}