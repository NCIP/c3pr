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

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.PersonOrganizationResolverUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.CorrelationNode;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;

/**
 * @author Vinay Gangoli
 * The Class RemoteResearchStaffResolver.
 */
public class RemoteResearchStaffResolver implements RemoteResolver{

	/** The log. */
    private static Log log = LogFactory.getLog(RemoteResearchStaffResolver.class);
	
    private PersonOrganizationResolverUtils personOrganizationResolverUtils;
    
	
	public Object getRemoteEntityByUniqueId(String externalId) {
		log.debug("Entering getRemoteEntityByUniqueId() for:" + this.getClass() + " - ExtId: " +externalId);
		II ii = CoppaObjectFactory.getIISearchCriteriaForPerson(externalId);
		String iiXml = CoppaObjectFactory.getCoppaIIXml(ii);
		String resultXml = "";
		try {
			resultXml = personOrganizationResolverUtils.broadcastPersonGetById(iiXml);
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}
		
		List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		Person coppaPerson = null;
		ResearchStaff researchStaff = null;
		if (results.size() > 0) {
			coppaPerson = CoppaObjectFactory.getCoppaPerson(results.get(0));
			researchStaff = populateRemoteResearchStaff(coppaPerson, "", null);
		}
		
		log.debug("Exiting getRemoteEntityByUniqueId() for:" + this.getClass());
		return researchStaff;
	}
	
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	public List<Object> find(Object example) {
		log.debug("Entering find() for:" + this.getClass());
		RemoteResearchStaff remoteResearchStaff = null;
		List<Object> remoteResearchStaffList = new ArrayList<Object>();
		try{
			if(example instanceof RemoteResearchStaff){
				remoteResearchStaff = (RemoteResearchStaff) example;
				
				if(!StringUtils.isEmpty(remoteResearchStaff.getAssignedIdentifier())){
					//search based on nci id of person
					log.debug("Searching based on NciId");
					remoteResearchStaffList = searchStaffBasedOnNciId(remoteResearchStaff);
				} else if(remoteResearchStaff.getHealthcareSite() != null && remoteResearchStaff.getHealthcareSite().getPrimaryIdentifier() != null){
					//search based on Organization
					log.debug("Searching based on Organization");
					remoteResearchStaffList = searchStaffBasedOnOrganization(remoteResearchStaff);
				} else {
					//search based on name
					log.debug("Searching based on Name");
					remoteResearchStaffList = searchStaffBasedOnName(remoteResearchStaff);
				}
			}
		} catch (Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
		}
		log.debug("Exiting find() for:" + this.getClass());
		return remoteResearchStaffList;
	}

	
	/**
	 * Search staff based on nci id.
	 * 
	 * @param remoteResearchStaffExample the remote research staff example
	 * @return the list
	 */
	private List<Object> searchStaffBasedOnNciId(RemoteResearchStaff remoteResearchStaffExample) {
		List<Object> remoteResearchStaffList = new ArrayList<Object>();
		List<IdentifiedPerson> identifiedPersonsList = null;
		if (remoteResearchStaffExample.getAssignedIdentifier() != null) {
             //get Identified Organization using the Identifier provided
             IdentifiedPerson identifiedPersonToSearch = CoppaObjectFactory.getCoppaIdentfiedPersonSearchCriteriaOnCTEPId(remoteResearchStaffExample.getAssignedIdentifier());
             identifiedPersonsList = personOrganizationResolverUtils.getIdentifiedPerson(identifiedPersonToSearch);
             if (identifiedPersonsList.size() == 0) {
                 return remoteResearchStaffList;
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
				if(identifiedPerson.getAssignedId().getExtension().equalsIgnoreCase(remoteResearchStaffExample.getAssignedIdentifier())){
					identifiedPersonWithExactCtepIdMatch = identifiedPerson;
					break;
				}
			}
		}
		
		if(identifiedPersonWithExactCtepIdMatch != null){
			personIiExtension = identifiedPersonWithExactCtepIdMatch.getPlayerIdentifier().getExtension();
			if(personIiExtension == null){
				return remoteResearchStaffList;
			}
			person = CoppaObjectFactory.getCoppaPersonForExtension(personIiExtension);
			correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new ClinicalResearchStaff(), person, null);
			
			correlationNodeList = personOrganizationResolverUtils.getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
			remoteResearchStaffList.addAll(getRemoteStaffFromCorrelationNodesList(correlationNodeList, null, remoteResearchStaffExample.getAssignedIdentifier()));
		}
	
		return remoteResearchStaffList;
	}


	/**
	 * Search staff based on organization.
	 * 
	 * @param remoteResearchStaffExample the remote research staff example
	 * @return the list
	 */
	private List<Object> searchStaffBasedOnOrganization(RemoteResearchStaff remoteResearchStaffExample) {
    	//Get IdentifiedOrganization by ctepId
        IdentifiedOrganization identifiedOrganizationSearchCriteria = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaOnCTEPId
        													(remoteResearchStaffExample.getHealthcareSite().getPrimaryIdentifier());
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
        	log.error("searchStaffBasedOnOrganization: The ctep code matches more than one organization. The current implementation uses only the first match as it" +
        			"assumes the ctep code search to always yield one exact match.");
        }
        
        IdentifiedOrganization coppaIdOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(resultObjects.get(0));
        II organizationIdentifier = coppaIdOrganization.getPlayerIdentifier();
        Organization organization = CoppaObjectFactory.getCoppaOrganizationFromII(organizationIdentifier);
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new ClinicalResearchStaff(), null, organization);
		
		List<CorrelationNode> correlationNodeList = personOrganizationResolverUtils.getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		List<Object> remoteStaffList = getRemoteStaffFromCorrelationNodesList(correlationNodeList, coppaIdOrganization, null);
		
		return remoteStaffList;
	}


	/**
	 * Search staff based on name.
	 * 
	 * @param remoteResearchStaff the remote research staff
	 * @return the list
	 */
	private List<Object> searchStaffBasedOnName(RemoteResearchStaff remoteResearchStaff) {
		Person person = CoppaObjectFactory.getCoppaPerson(remoteResearchStaff.getFirstName(), remoteResearchStaff.getMiddleName(), remoteResearchStaff.getLastName());
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(new ClinicalResearchStaff(), person, null);
		
		List<CorrelationNode> correlationNodeList = personOrganizationResolverUtils.getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		List<Object> remoteResearchStaffList = getRemoteStaffFromCorrelationNodesList(correlationNodeList, null, null);
		return remoteResearchStaffList;
	}
	
	/**
	 * Gets the remote staff from correlation nodes list.
	 * 
	 * @param correlationNodeList the correlation node list
	 * @return the remote staff from correlation nodes list
	 */
	private List<Object> getRemoteStaffFromCorrelationNodesList(List<CorrelationNode> correlationNodeList, IdentifiedOrganization identifiedOrganization, String staffAssignedIdentifier) {
		List<Object> researchStaffList = new ArrayList<Object>();
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
		if(staffAssignedIdentifier == null){
			personIdToIdentifiedPersonMap = personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(listOfAllPersons);
		}
		
		RemoteResearchStaff populatedRemoteStaff  = null;
		Person coppaPerson = null;
		String assignedIdentifier = null;
		for(CorrelationNode cNode: correlationNodeList){
			coppaPerson = personOrganizationResolverUtils.getCoppaPersonFromPlayerInCorrelationNode(cNode);
			//Only get the AssignedIdentifier if its passed in as null
			if(staffAssignedIdentifier == null){
				assignedIdentifier = personOrganizationResolverUtils.getAssignedIdentifierFromPersonIdToIdentifiedPersonMap(coppaPerson, personIdToIdentifiedPersonMap);
				if(assignedIdentifier == null){
					assignedIdentifier = coppaPerson.getIdentifier().getExtension();
				}
			} else {
				assignedIdentifier = staffAssignedIdentifier;
			}
			
			//Call the right populate method based on whether identifiedOrganization is already available or not
			if(identifiedOrganization == null){
				populatedRemoteStaff = populateRemoteResearchStaff(coppaPerson, assignedIdentifier, 
						personIdToCoppaOrganizationsHashMap.get(coppaPerson.getIdentifier().getExtension()), organizationIdToIdentifiedOrganizationsMap);	
			} else {
				populateRemoteResearchStaff(coppaPerson, assignedIdentifier, identifiedOrganization);
			}
			
	    	if(populatedRemoteStaff != null){
	    		researchStaffList.add(populatedRemoteStaff);
	    	}
		}
		return researchStaffList;
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
	public RemoteResearchStaff populateRemoteResearchStaff(Person coppaPerson, String staffAssignedIdentifier, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList,
																				Map<String, IdentifiedOrganization>	organizationIdToIdentifiedOrganizationsMap){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteResearchStaff());
		if(object == null){
			return null;
		} else {
			RemoteResearchStaff remoteResearchStaff = (RemoteResearchStaff) object;
			remoteResearchStaff.setExternalId(coppaPerson.getIdentifier().getExtension());
			remoteResearchStaff.setAssignedIdentifier(staffAssignedIdentifier);
			
			//Build HealthcareSite
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
						
						remoteResearchStaff.setHealthcareSite(healthcareSite);
					} else {
						log.error("IdentifiedOrganization is null for Organization with coppaId: "+coppaOrganization.getIdentifier().getExtension());
					}
				}
			}
			return remoteResearchStaff;
		}
	}
	
	/**
	 * Populate remote research staff. Called from searchStaffByrganization()
	 * 
	 * @param personDTO the person dto
	 * @param orgCtepId the org ctep id
	 * @param coppaOrgId the coppa org id
	 * 
	 * @return the research staff
	 */
	public RemoteResearchStaff populateRemoteResearchStaff(Person coppaPerson, String staffAssignedIdentifier, IdentifiedOrganization identifiedOrganization){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteResearchStaff());
		if(object != null){
			RemoteResearchStaff remoteResearchStaff = (RemoteResearchStaff) object;
			remoteResearchStaff.setAssignedIdentifier(staffAssignedIdentifier);
			remoteResearchStaff.setExternalId(coppaPerson.getIdentifier().getExtension());
			
			if(identifiedOrganization != null){	
				//Build HealthcareSite
				HealthcareSite healthcareSite = new RemoteHealthcareSite();
				personOrganizationResolverUtils.setCtepCodeFromExtension(healthcareSite,identifiedOrganization.getAssignedId().getExtension());
				remoteResearchStaff.setHealthcareSite(healthcareSite);
			}
			return remoteResearchStaff;
		}
		return null;
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