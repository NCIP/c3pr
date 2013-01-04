/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iso._21090.II;

import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.PersonOrganizationResolverUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.po.CorrelationNode;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.Organization;
import gov.nih.nci.coppa.po.Person;

public abstract class BaseResolver {
	
	private static Log log = LogFactory.getLog(BaseResolver.class);

	protected PersonOrganizationResolverUtils personOrganizationResolverUtils;
    
	
	/**
	 * Search role based on organization.
	 * 
	 * @param organizationNciIdentifier the organization nci identifier
	 * @param role the role
	 * @return the list
	 * @return
	 */
    protected List<Object> searchRoleBasedOnOrganization(String organizationNciIdentifier,gov.nih.nci.coppa.po.Correlation role){
    	//Get IdentifiedOrganization by ctepId
        IdentifiedOrganization identifiedOrganizationSearchCriteria = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaOnCTEPId
        													(organizationNciIdentifier);
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
		String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(role, null, organization);
		
		List<CorrelationNode> correlationNodeList = personOrganizationResolverUtils.getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
		List<Object> remoteStaffList = getRemoteRolesFromCorrelationNodesList(correlationNodeList, coppaIdOrganization, null);
		
		return remoteStaffList;
    }
    
    /**
     * Search role based on nci id.
     * 
     * @param assignedIdentifier the assigned identifier
     * @param role the role
     * @return the list
     * @return
     */
    protected List<Object> searchRoleBasedOnNciId(String assignedIdentifier, gov.nih.nci.coppa.po.Correlation role) {
        List<Object> remoteRoleList = new ArrayList<Object>();
        IdentifiedPerson identifiedPersonWithExactCtepIdMatch = null;
        if (assignedIdentifier != null) {
             //get Identified Organization using the Identifier provided
             IdentifiedPerson identifiedPersonToSearch = CoppaObjectFactory.getCoppaIdentfiedPersonSearchCriteriaOnCTEPId(assignedIdentifier);
             List<IdentifiedPerson> identifiedPersonsList = personOrganizationResolverUtils.getIdentifiedPerson(identifiedPersonToSearch);
             if (identifiedPersonsList.size() == 0) {
                 return remoteRoleList;
             }
    		
    		//The identifiedPerson search by CTEP code is a like match & not exact match and returns more than one result.
    		//So Iterate thru the IdentifiedPersons fetched and isolate the one whose extension exactly matches the search criteria. 
    		for(IdentifiedPerson identifiedPerson: identifiedPersonsList){
    			if(identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(PersonOrganizationResolverUtils.CTEP_PERSON)){
    				if(identifiedPerson.getAssignedId().getExtension().equalsIgnoreCase(assignedIdentifier)){
    					identifiedPersonWithExactCtepIdMatch = identifiedPerson;
    					break;
    				}
    			}
    		}
        }
        String personIiExtension = null;
        Person person = null;
        String correlationNodeXmlPayload = null;
        List<CorrelationNode> correlationNodeList = null;
        //Get the Role corresponding to every Identified Person fetched. Because the identifiedPerson search by CTEP code is a 
        //like match not exact match and can return more than one result.
        if(identifiedPersonWithExactCtepIdMatch != null){
            personIiExtension = identifiedPersonWithExactCtepIdMatch.getPlayerIdentifier().getExtension();
            if(personIiExtension == null){
                return remoteRoleList;
            }
            person = CoppaObjectFactory.getCoppaPersonForExtension(personIiExtension);
            correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(role, person, null);
            
            correlationNodeList = getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
            remoteRoleList.addAll(getRemoteRolesFromCorrelationNodesList(correlationNodeList, null, assignedIdentifier));
        }
        return remoteRoleList;
    }
    
	/**
	 * Search role based on name.
	 * 
	 * @param firstName the first name
	 * @param middleName the middle name
	 * @param lastName the last name
	 * @param role (instance of HealthCareProvide or  ClinicalResearchStaff)
	 * @return the list
	 * @return
	 */
    protected List<Object> searchRoleBasedOnName(String firstName,String middleName, String lastName, gov.nih.nci.coppa.po.Correlation role) {
    	if(isDataValid(firstName, middleName, lastName)){
    		Person person = CoppaObjectFactory.getCoppaPerson(firstName, middleName, lastName);
            String correlationNodeXmlPayload = CoppaObjectFactory.getCorrelationNodePayload(role, person, null);

            List<CorrelationNode> correlationNodeList = getCorrelationNodesFromPayloadXml(correlationNodeXmlPayload);
            List<Object> remoteRoleList = getRemoteRolesFromCorrelationNodesList(correlationNodeList, null, null);
            return remoteRoleList;
    	}
        return new ArrayList<Object>();
    }
    
    /** returns false if input values are %'s or blanks
     * @param firstName
     * @param middleName
     * @param lastName
     * @return boolean
     */
    private boolean isDataValid(String firstName,String middleName, String lastName) {
    	if((StringUtils.isBlank(firstName) || firstName.matches("%+")) &&
    		(StringUtils.isBlank(middleName) || middleName.matches("%+")) &&
    			(StringUtils.isBlank(lastName) || lastName.matches("%+"))){
    		return false;
    	}
		return true;
	}

	/**
     * Gets the remote investigators/rs from correlation nodes list.
     * 
     * @param correlationNodeList the correlation node list
     * @return the remote investigators/rs from correlation nodes list
     */
    protected List<Object> getRemoteRolesFromCorrelationNodesList( List<CorrelationNode> correlationNodeList, 
    								IdentifiedOrganization identifiedOrganization, String staffAssignedIdentifier) {
    	List<Object> remoteRoleList = new ArrayList<Object>();
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
		
		Object populatedRole  = null;
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
				populatedRole = populateRole(coppaPerson, assignedIdentifier, 
						personIdToCoppaOrganizationsHashMap.get(coppaPerson.getIdentifier().getExtension()), organizationIdToIdentifiedOrganizationsMap);	
			} else {
				populatedRole = populateRole(coppaPerson, assignedIdentifier, identifiedOrganization);
			}
			
	    	if(populatedRole != null){
	    		remoteRoleList.add(populatedRole);
	    	}
		}
		return remoteRoleList;
    }
    
    /**
     * Populate role.
     * 
     * @param coppaPerson the coppa person
     * @param staffAssignedIdentifier the staff assigned identifier
     * @param coppaOrganizationList the coppa organization list
     * @param organizationIdToIdentifiedOrganizationsMap the organization id to identified organizations map
     * @return the object
     * @return
     */
    public abstract Object populateRole(Person coppaPerson, String staffAssignedIdentifier, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList,
            Map<String, IdentifiedOrganization> organizationIdToIdentifiedOrganizationsMap);
    
    /**
     * Populate role.
     * 
     * @param coppaPerson the coppa person
     * @param staffAssignedIdentifier the staff assigned identifier
     * @param identifiedOrganization the identified organization
     * @return the object
     * @return
     */
    public abstract Object populateRole(Person coppaPerson, String staffAssignedIdentifier, IdentifiedOrganization identifiedOrganization);
   

	/**
	 * Gets the correlation nodes from payload xml.
	 * 
	 * @param correlationNodeXmlPayload the correlation node xml payload
	 * @return the correlation nodes from payload xml
	 */
	public List<CorrelationNode> getCorrelationNodesFromPayloadXml(String correlationNodeXmlPayload) {
		String correlationNodeArrayXml = "";
		try{
			correlationNodeArrayXml = personOrganizationResolverUtils.broadcastSearchCorrelationsWithEntities(correlationNodeXmlPayload, true, true);
		} catch(Exception e){
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
	
	
	/**
	 * Sets the person organization resolver utils.
	 * 
	 * @param personResolverUtils the new person organization resolver utils
	 */
	public void setPersonOrganizationResolverUtils(PersonOrganizationResolverUtils personResolverUtils) {
		this.personOrganizationResolverUtils = personResolverUtils;
	}

}
