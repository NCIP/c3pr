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
    
    public static final String CTEP_PERSON = "Cancer Therapy Evaluation Program Person Identifier";
    
	/**
	 * Populate remote investigator.
	 * 
	 * @param coppaPerson the coppa person
	 * @param coppaOrganizationList the coppa organization list
	 * 
	 * @return the remote investigator
	 */
	private RemoteInvestigator populateRemoteInvestigator(Person coppaPerson, String staffAssignedIdentifier, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList,
																								Map<String, IdentifiedOrganization>	organizationIdToIdentifiedOrganizationsMap){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteInvestigator());
		if(object == null){
			return null;
		} else {
			RemoteInvestigator remoteInvestigator = (RemoteInvestigator) object;
			if(remoteInvestigator.getVersion() == null){
				remoteInvestigator.setVersion(0);
			}
			remoteInvestigator.setExternalId(coppaPerson.getIdentifier().getExtension());
			
			if(!StringUtils.isEmpty(staffAssignedIdentifier)){
				remoteInvestigator.setAssignedIdentifier(staffAssignedIdentifier);
			}
			
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
	 * Populate remote research staff. Called from searchStaffByrganization() 
	 * Wont load HCSI if  identifiedOrganization is passed in as null. (for getRemoteEntityByUniqueId())
	 * 
	 * @param personDTO the person dto
	 * @param orgCtepId the org ctep id
	 * @param coppaOrgId the coppa org id
	 * 
	 * @return the research staff
	 */
	private RemoteInvestigator populateRemoteInvestigator(Person coppaPerson, String staffAssignedIdentifier, IdentifiedOrganization identifiedOrganization){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteInvestigator());
		if(object == null){
			return null;
		} else {
			RemoteInvestigator remoteInvestigator = (RemoteInvestigator)object;
			if(remoteInvestigator.getVersion() == null){
				remoteInvestigator.setVersion(0);
			}
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
	

	
	/* 
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String externalId) {
		Person person = CoppaObjectFactory.getCoppaPerson(null, null, null);
		person.getIdentifier().setExtension(externalId);
		
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new HealthCareProvider(), person, null);
		List<CorrelationNode> correlationNodeList = getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		RemoteInvestigator populatedRemoteInvestigator = null;
		for(CorrelationNode cNode: correlationNodeList){
			Person coppaPerson = getCoppaPersonFromCorrelationNode(cNode);
			
			populatedRemoteInvestigator = populateRemoteInvestigator(coppaPerson, "", null);	
	    	if(populatedRemoteInvestigator != null){
	    		remoteInvestigatorList.add(populatedRemoteInvestigator);
	    	}
		}
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
    	//Get IdentifiedOrganization by ctepId(nciId)
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
        			"assumes the ctep code serach to always yield one exact match.");
        }
        
        IdentifiedOrganization coppaIdOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(resultObjects.get(0));
        II organizationIdentifier = coppaIdOrganization.getPlayerIdentifier();
        
        Organization organization = CoppaObjectFactory.getCoppaOrganizationFromII(organizationIdentifier);
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new HealthCareProvider(), null, organization);
		
		List<CorrelationNode> correlationNodeList = getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		List<Person> listOfAllPersons = new ArrayList<Person>();
		Person person = null;
		for(CorrelationNode cNode: correlationNodeList){
			person = getCoppaPersonFromCorrelationNode(cNode);
			if(person != null){
				listOfAllPersons.add(person);
			}
		}
		
		Map<String, List<IdentifiedPerson>> personIdToIdentifiedPersonMap = 
							personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(listOfAllPersons);
		
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		RemoteInvestigator populatedRemoteInvestigator = null;
		Person coppaPerson = null;
		String assignedIdentifier;
		for(CorrelationNode cNode: correlationNodeList){
			assignedIdentifier = null;
			coppaPerson = getCoppaPersonFromCorrelationNode(cNode);
			assignedIdentifier = getAssignedIdentifierFromCorrelationNode(coppaPerson, personIdToIdentifiedPersonMap);
			if(assignedIdentifier == null){
				assignedIdentifier = coppaPerson.getIdentifier().getExtension();
			}
			
			populatedRemoteInvestigator = populateRemoteInvestigator(coppaPerson, 
																	assignedIdentifier, coppaIdOrganization);	
	    	if(populatedRemoteInvestigator != null){
	    		remoteInvestigatorList.add(populatedRemoteInvestigator);
	    	}
		}
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
             //Build a search Person Xml using the player Id from ANY IdentifiedPerson in the IdentifiedPersonsList.
		}
		String personIiExtension = null;
		Person person = null;
		String correlationNodeXmlPayload = null;
		List<CorrelationNode> correlationNodeList = null;
		for(int i=0; i<identifiedPersonsList.size(); i++ ){
			personIiExtension = identifiedPersonsList.get(i).getPlayerIdentifier().getExtension();
			if(personIiExtension == null){
				return remoteInvestigatorList;
			}
			person = CoppaObjectFactory.getCoppaPersonForExtension(personIiExtension);
			correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new HealthCareProvider(), person, null);
			
			correlationNodeList = getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
			remoteInvestigatorList.addAll(getRemoteInvestigatorsFromCorrelationNodesList(correlationNodeList));
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
		
		List<CorrelationNode> correlationNodeList = getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		List<Object> remoteInvestigatorList = getRemoteInvestigatorsFromCorrelationNodesList(correlationNodeList);
		return remoteInvestigatorList;
	}

	
	private List<CorrelationNode> getCorrelationNodesFromPayloadXml(String correlationNodeXmlPayload) {
		String correlationNodeArrayXml = "";
		try{
			correlationNodeArrayXml = personOrganizationResolverUtils.broadcastSearchCorrelationsWithEntities(correlationNodeXmlPayload, true, true);
		} catch(C3PRCodedException e){
			log.error(e.getStackTrace());
		}
		List<String> correlationNodes = XMLUtils.getObjectsFromCoppaResponse(correlationNodeArrayXml);
		List<CorrelationNode> correlationNodeList = new ArrayList<CorrelationNode>();
		//creating a list of correlationNodes
		for(String correlationNode: correlationNodes){
			correlationNodeList.add(CoppaObjectFactory.getCorrelationNodeObjectFromXml(correlationNode));
		}
		return correlationNodeList;
	}
	
	private List<Object> getRemoteInvestigatorsFromCorrelationNodesList(
									List<CorrelationNode> correlationNodeList) {
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		HashMap<String, List<Organization>> personIdToCoppaOrganizationsHashMap = new HashMap<String, List<Organization>>();
		List<Organization> listOfAllOrganizations = new ArrayList<Organization>();
		List<Person> listOfAllPersons = new ArrayList<Person>();
		Person tempPerson = null;
		Organization tempOrganization  = null;
		for(CorrelationNode cNode: correlationNodeList){
			tempPerson = getCoppaPersonFromCorrelationNode(cNode);
			tempOrganization = getCoppaOrganizationAssociatedToInvestigatorFromCorrelationNode(cNode);
			
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
		
		Map<String, IdentifiedOrganization> organizationIdToIdentifiedOrganizationsMap = 
									personOrganizationResolverUtils.getIdentifiedOrganizationsForOrganizationsList(listOfAllOrganizations);
		Map<String, List<IdentifiedPerson>> personIdToIdentifiedPersonMap = 
									personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(listOfAllPersons);
		
		RemoteInvestigator populatedRemoteInvestigator  = null;
		for(CorrelationNode cNode: correlationNodeList){
			Person coppaPerson = getCoppaPersonFromCorrelationNode(cNode);
			String assignedIdentifier = getAssignedIdentifierFromCorrelationNode(coppaPerson, personIdToIdentifiedPersonMap);
			if(assignedIdentifier == null){
				assignedIdentifier = coppaPerson.getIdentifier().getExtension();
			}
			
			populatedRemoteInvestigator = populateRemoteInvestigator(coppaPerson, assignedIdentifier, 
										personIdToCoppaOrganizationsHashMap.get(coppaPerson.getIdentifier().getExtension()), organizationIdToIdentifiedOrganizationsMap);	
	    	if(populatedRemoteInvestigator != null){
	    		remoteInvestigatorList.add(populatedRemoteInvestigator);
	    	}
		}
		return remoteInvestigatorList;
	}

	private Organization getCoppaOrganizationAssociatedToInvestigatorFromCorrelationNode(CorrelationNode cNode) {
		Organization coppaOrganization = null;
		for(int i = 0; i < cNode.getScoper().getContent().size(); i++){
			Object object = cNode.getScoper().getContent().get(i);
			if(object instanceof Organization){
				coppaOrganization = (Organization)object;
				break;
			}
		}
		return coppaOrganization;
	}
	
	private String getAssignedIdentifierFromCorrelationNode(Person coppaPerson, Map<String, List<IdentifiedPerson>> personIdToIdentifiedPersonMap) {
		String assignedIdentifier = null;
		if(personIdToIdentifiedPersonMap.containsKey(coppaPerson.getIdentifier().getExtension())){
			List<IdentifiedPerson> identifiedPersonList = personIdToIdentifiedPersonMap.get(coppaPerson.getIdentifier().getExtension());
    		for(IdentifiedPerson identifiedPerson: identifiedPersonList){
    			if(identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)){
    				assignedIdentifier = identifiedPerson.getAssignedId().getExtension();
        		}
    		}
		}
		return assignedIdentifier;
	}

	private Person getCoppaPersonFromCorrelationNode(CorrelationNode cNode) {
		Person person = null;
		for(int i = 0; i < cNode.getPlayer().getContent().size(); i++){
			Object object = cNode.getPlayer().getContent().get(i);
			if(object instanceof Person){
				person = (Person) object;
				break;
			}
		}
		return person;
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