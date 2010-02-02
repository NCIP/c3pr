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
import gov.nih.nci.coppa.po.HealthCareProvider;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.IdentifiedPerson;
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
	public RemoteInvestigator populateRemoteInvestigator(Person coppaPerson, String staffNciIdentifier, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteInvestigator());
		if(object == null){
			return null;
		} else {
			RemoteInvestigator remoteInvestigator = (RemoteInvestigator) object;
			remoteInvestigator.setExternalId(coppaPerson.getIdentifier().getExtension());
			
			if(!StringUtils.isEmpty(staffNciIdentifier)){
				remoteInvestigator.setAssignedIdentifier(staffNciIdentifier);
			}
			
			//Build HealthcareSite and HealthcareSiteInvestigator
			RemoteHealthcareSite healthcareSite = null;
			if(coppaOrganizationList != null && coppaOrganizationList.size()>0){
				Map<String, IdentifiedOrganization> identifierOrganizationsMap = 
					personOrganizationResolverUtils.getIdentifiedOrganizationsForOrganizationsList(coppaOrganizationList);
	
				IdentifiedOrganization identifiedOrganization = null;
				for(gov.nih.nci.coppa.po.Organization coppaOrganization: coppaOrganizationList){
					identifiedOrganization = identifierOrganizationsMap.get(coppaOrganization.getIdentifier().getExtension());
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
	 * 
	 * @param personDTO the person dto
	 * @param orgCtepId the org ctep id
	 * @param coppaOrgId the coppa org id
	 * 
	 * @return the research staff
	 */
	public RemoteInvestigator populateRemoteInvestigator(Person coppaPerson, String staffNciIdentifier, IdentifiedOrganization identifiedOrganization){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteInvestigator());
		if(object == null){
			return null;
		} else {
			RemoteInvestigator remoteInvestigator = (RemoteInvestigator)object;
			remoteInvestigator.setAssignedIdentifier(staffNciIdentifier);
			remoteInvestigator.setExternalId(coppaPerson.getIdentifier().getExtension());
			
			//Build HealthcareSite
			HealthcareSite healthcareSite = new RemoteHealthcareSite();
			personOrganizationResolverUtils.setCtepCodeFromExtension(healthcareSite, identifiedOrganization.getAssignedId().getExtension());
			
			HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();
			hcsi.setHealthcareSite(healthcareSite);
			hcsi.setInvestigator(remoteInvestigator);
			remoteInvestigator.getHealthcareSiteInvestigators().add(hcsi);
			return remoteInvestigator;
		}
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
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		RemoteInvestigator tempRemoteInvestigator = null;
        try{
        	//Get IdentifiedOrganization by ctepId(nciId)
            IdentifiedOrganization identifiedOrganizationSearchCriteria = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaOnCTEPId
            			(remoteInvestigatorExample.getHealthcareSiteInvestigators().get(0).getHealthcareSite().getPrimaryIdentifier());
            String payload = CoppaObjectFactory.getCoppaIdentfiedOrganization(identifiedOrganizationSearchCriteria);
            String results = personOrganizationResolverUtils.broadcastIdentifiedOrganizationSearch(payload);

            List<String> resultObjects = XMLUtils.getObjectsFromCoppaResponse(results);
            for (String resultObj:resultObjects) {
                IdentifiedOrganization coppaIdOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(resultObj);
                II organizationIdentifier = coppaIdOrganization.getPlayerIdentifier();
                String iiXml = CoppaObjectFactory.getCoppaIIXml(organizationIdentifier);
                //Get Organizations based on player id of above IdentifiedOrganizations.
                String organizationResults = personOrganizationResolverUtils.broadcastOrganizationGetById(iiXml);
                
                List<String> organizationResultObjects = XMLUtils.getObjectsFromCoppaResponse(organizationResults);
                //should contain only one but looping anyway(fix later)
                for (String organizationResultObject:organizationResultObjects) {
                    gov.nih.nci.coppa.po.Organization coppaOrganizationResult = CoppaObjectFactory.getCoppaOrganization(organizationResultObject);
                    II ii = coppaOrganizationResult.getIdentifier();
                    //Organization ii is the scoper for healthCareProvider...which returns hcp with staff as playerId
                    HealthCareProvider healthCareProvider = CoppaObjectFactory.getCoppaHealthCareProviderWithScoperIdAsSearchCriteria(ii);
                    String coppaHealthCareProviderXml = CoppaObjectFactory.getCoppaHealthCareProviderXml(healthCareProvider);
                    String sRolesXml = personOrganizationResolverUtils.broadcastHealthcareProviderSearch(coppaHealthCareProviderXml);

                    List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
                    for(String sRole: sRoles){
                        HealthCareProvider hcp = CoppaObjectFactory.getCoppaHealthCareProvider(sRole);
                        II pid = hcp.getPlayerIdentifier();    
                        String idXml = CoppaObjectFactory.getCoppaIIXml(pid);
                        //above player id is the Id of a Person ... now get the Person by Id
                        String personResultXml = personOrganizationResolverUtils.broadcastPersonGetById(idXml);
                        List<String> persons = XMLUtils.getObjectsFromCoppaResponse(personResultXml);  
                        if(persons.size() > 0){
                            Person person = CoppaObjectFactory.getCoppaPerson(persons.get(0));
                            List<IdentifiedPerson> identifiedPersonsList = personOrganizationResolverUtils.getIdentifiedPerson(person.getIdentifier());
                            String nciIdentifier = null;
                            for(IdentifiedPerson identifiedPerson: identifiedPersonsList){
                            	if (identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)) {
                                    nciIdentifier = identifiedPerson.getAssignedId().getExtension();
                                    break;
                                }
                            }
                            tempRemoteInvestigator = populateRemoteInvestigator(person, nciIdentifier, coppaIdOrganization);
                            remoteInvestigatorList.add(tempRemoteInvestigator);
                        }
                    }
                }
            }
        } catch(C3PRCodedException ce){
        	log.error(ce.getMessage());
        } catch(Exception e){
        	log.error(e.getMessage());
        }
        
        return remoteInvestigatorList;
	}

	
	/**
	 * Search investigator based on nci id.
	 * 
	 * @param remoteInvestigatorExample the remote investigator example
	 * @return the list< object>
	 */
	private List<Object> searchInvestigatorBasedOnNciId(RemoteInvestigator remoteInvestigatorExample) {
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		RemoteInvestigator tempRemoteInvestigator = null; 
		
		if (remoteInvestigatorExample.getAssignedIdentifier() != null) {
             //get Identified Organization using the Identifier provided
             IdentifiedPerson identifiedPersonToSearch = CoppaObjectFactory.getCoppaIdentfiedPersonSearchCriteriaOnCTEPId(remoteInvestigatorExample.getAssignedIdentifier());
             List<IdentifiedPerson> identifiedPersonsList = personOrganizationResolverUtils.getIdentifiedPerson(identifiedPersonToSearch);
             if (identifiedPersonsList.size() == 0) {
                 return remoteInvestigatorList;
             }
             //Build a search Person Xml using the player Id from ANY IdentifiedPerson in the IdentifiedPersonsList.
             II ii = identifiedPersonsList.get(0).getPlayerIdentifier();
             String iiXml = CoppaObjectFactory.getCoppaIIXml(ii);
             try {
                 String resultXml = personOrganizationResolverUtils.broadcastPersonGetById(iiXml);
                 tempRemoteInvestigator = loadInvestigatorForPersonResult(resultXml);
                 remoteInvestigatorList.add(tempRemoteInvestigator);
             } catch (Exception e) {
                log.error(e.getMessage());
             }
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
		List<Object> remoteInvestigatorList = null;
		//Serialize the remoteInv(used for searches based on Name(first, middle, last))
		String personXml = CoppaObjectFactory.getCoppaPersonXml(
				CoppaObjectFactory.getCoppaPerson(remoteInvestigator.getFirstName(), remoteInvestigator.getMiddleName(), remoteInvestigator.getLastName()));
		String resultXml = "";
		try {
			//Coppa-call for person search
			resultXml = personOrganizationResolverUtils.broadcastPersonSearchWithLimit(personXml);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		List<String> coppaPersons = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		List<Person> personList = new ArrayList<Person>();
		if (coppaPersons != null){
			//creating a list of persons
			for(String coppaPersonXml: coppaPersons){
				personList.add(CoppaObjectFactory.getCoppaPerson(coppaPersonXml));
			}
			//Fetch the associated Organizations
			Map<String, List<gov.nih.nci.coppa.po.Organization>> organizationsMap = getOrganizationsForPersonsList(personList);
        	Map<String, List<IdentifiedPerson>> nciIdsMap = personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(personList);
        	remoteInvestigatorList = new ArrayList<Object>();
        	for(Person coppaPerson : personList){
        		String nciId = null;
        		if(nciIdsMap.containsKey(coppaPerson.getIdentifier().getExtension())){
        			List<IdentifiedPerson> identifiedPersonList = nciIdsMap.get(coppaPerson.getIdentifier().getExtension());
            		for(IdentifiedPerson identifiedPerson:identifiedPersonList){
            			if(identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)){
                			nciId = identifiedPerson.getAssignedId().getExtension();
                		}
            		}
        		}
        		
    			List<gov.nih.nci.coppa.po.Organization> organizationsList = organizationsMap.get(coppaPerson.getIdentifier().getExtension());
            	remoteInvestigator = populateRemoteInvestigator(coppaPerson, nciId, organizationsList);	
            	if(remoteInvestigator != null){
            		remoteInvestigatorList.add(remoteInvestigator);
            	}
        	}
		}
		return remoteInvestigatorList;
	}


	/**
	 * Returns a map with personID as key and associated OrganizationsList as value.
	 * If no Organizations are associated with a person then the value for that key will be null.
	 * 
	 * Does so by getting the HealthcareProvider for a person. This is a Structural Role.
	 * This role has the person as the player and the Organization as the scoper.
	 * So get the scoper id from the role and use it to search all orgs. This gets us
	 * all the related orgs.
	 * 
	 * @param coppaPersons the coppa person List
	 * @return the organizations for person
	 */
	private Map<String, List<gov.nih.nci.coppa.po.Organization>> getOrganizationsForPersonsList(List<Person> coppaPersonsList) {
		Map<String, List<gov.nih.nci.coppa.po.Organization>> organizationsMap = new HashMap<String, List<gov.nih.nci.coppa.po.Organization>>();
		try {
			//Build a list of personId Xml
			List<String> personIdXmlList = new ArrayList<String>();
			for(Person coppaPerson:coppaPersonsList){
				personIdXmlList.add(CoppaObjectFactory.getCoppaPersonIdXML(coppaPerson.getIdentifier().getExtension()));
			}
			
			//Coppa-call for Structural Role(HealthcareProvider) getByIds
			String sRolesXml = personOrganizationResolverUtils.broadcastHealthcareProviderGetByPlayerIds(personIdXmlList);
			List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
			
			//Build a map with personId as key and sRole as value
			Map<String, List<HealthCareProvider>> sRoleMap = new HashMap<String, List<HealthCareProvider>>();
			if(sRoles != null && sRoles.size() > 0){
				HealthCareProvider hcp = null;
				for(String sRole: sRoles){
					hcp = CoppaObjectFactory.getCoppaHealthCareProvider(sRole);
					if(hcp != null){
						List<HealthCareProvider> hcpList = null;
						if(sRoleMap.containsKey(hcp.getPlayerIdentifier().getExtension())){
							hcpList  = sRoleMap.get(hcp.getPlayerIdentifier().getExtension());
							hcpList.add(hcp);
						} else {
							hcpList = new ArrayList<HealthCareProvider>();
							hcpList.add(hcp);
							sRoleMap.put(hcp.getPlayerIdentifier().getExtension(), hcpList);
						}
					}
				}
			}
			
			//Iterate over the person list and build the investigators; Get Organizations only if they have roles in the sRolesmap
			Person coppaPerson = null;
			List<Integer> personsToBeDeletedList = new ArrayList<Integer>();
			List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList;
			for(int index = 0; index < coppaPersonsList.size(); index++){
				coppaOrganizationList = new ArrayList<gov.nih.nci.coppa.po.Organization>();
				coppaPerson = coppaPersonsList.get(index);
				//Only if the person has a HealthcareProvider role do we fetch the associated Organization.
				if(sRoleMap.containsKey(coppaPerson.getIdentifier().getExtension())){
					List<HealthCareProvider> hcpList = sRoleMap.get(coppaPerson.getIdentifier().getExtension());
					for(HealthCareProvider hcp : hcpList){
						String orgIiXml = CoppaObjectFactory.getCoppaIIXml(hcp.getScoperIdentifier());
						
						//Coppa-call for Organization search
						String orgResultXml = personOrganizationResolverUtils.broadcastOrganizationGetById(orgIiXml);
						List<String> orgResults = XMLUtils.getObjectsFromCoppaResponse(orgResultXml);
						if (orgResults.size() > 0) {
							coppaOrganizationList.add(CoppaObjectFactory.getCoppaOrganization(orgResults.get(0)));
						}
					}
					organizationsMap.put(coppaPerson.getIdentifier().getExtension(), coppaOrganizationList);
				} else {
					//Remove non-investigator from persons list.
					personsToBeDeletedList.add(index);
				}
			}
			//Create a duplicatePerson list so that we can remove the persons who dont have structuralRoles from 
			//the orignal list that was passed in.
			List<Person> duplicateCoppaPersonsList = new ArrayList<Person>();
			for(int index = 0; index < coppaPersonsList.size(); index++){
				duplicateCoppaPersonsList.add(coppaPersonsList.get(index));
			}
			coppaPersonsList.clear();
			for(int i = 0; i < duplicateCoppaPersonsList.size(); i++){
				if(!personsToBeDeletedList.contains(new Integer(i))){
					coppaPersonsList.add(duplicateCoppaPersonsList.get(i));
				}
			}
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}
		return organizationsMap;
	}
	
	
	/* 
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
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
		
		RemoteInvestigator remoteInvestigator = loadInvestigatorAttributesOnlyForPersonResult(resultXml);
		log.debug("Exiting getRemoteEntityByUniqueId() for:" + this.getClass());
		return remoteInvestigator;
	}

	/**
	 * Load investigator for person result. This is a condensed version of loadInvestigatorForPersonResult.
	 * Used by getRemoteEntityByUniqueId
	 * 
	 * @param personResultXml the person result xml
	 * @return the remote investigator
	 */
	public RemoteInvestigator loadInvestigatorAttributesOnlyForPersonResult(String personResultXml) {
        List<String> results = XMLUtils.getObjectsFromCoppaResponse(personResultXml);
        List<Person> coppaPersonsList = new ArrayList<Person>();
        RemoteInvestigator remoteInvestigator = null;
        if (results.size() > 0) {
        	coppaPersonsList.add(CoppaObjectFactory.getCoppaPerson(results.get(0)));
        	
        	Map<String, List<IdentifiedPerson>> nciIdsMap = personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(coppaPersonsList);
        	List<IdentifiedPerson> identifiedPersonList = nciIdsMap.get(coppaPersonsList.get(0).getIdentifier().getExtension());
        	String nciId = "";
        	for(IdentifiedPerson identifiedPerson: identifiedPersonList){
        		if(identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)){
            		nciId = identifiedPerson.getAssignedId().getExtension();
            	}
        	}
        	
        	List<gov.nih.nci.coppa.po.Organization> organizationList = null;
        	remoteInvestigator = populateRemoteInvestigator(coppaPersonsList.get(0), nciId, organizationList);
            return remoteInvestigator;
        }
        return null;            
	}
	
	
	/**
	 * Load investigator for person result. This is also used from StudyResolver; hence the public scope.
	 * This is for Individual persons results only, like the getRemoteEntityByUniqueId().
	 * 
	 * @param personResultXml the person result xml
	 * @return the remote investigator
	 */
	public RemoteInvestigator loadInvestigatorForPersonResult(String personResultXml) {
        List<String> results = XMLUtils.getObjectsFromCoppaResponse(personResultXml);
        List<Person> coppaPersonsList = new ArrayList<Person>();
        RemoteInvestigator remoteInvestigator = null;
        if (results.size() > 0) {
        	coppaPersonsList.add(CoppaObjectFactory.getCoppaPerson(results.get(0)));
        	
        	Map<String, List<gov.nih.nci.coppa.po.Organization>> organizationsMap = getOrganizationsForPersonsList(coppaPersonsList);
        	Map<String, List<IdentifiedPerson>> nciIdsMap = personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(coppaPersonsList);
        	
        	List<IdentifiedPerson> identifiedPersonList = nciIdsMap.get(coppaPersonsList.get(0).getIdentifier().getExtension());
        	String nciId = "";
        	for(IdentifiedPerson identifiedPerson:identifiedPersonList){
        		if(identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)){
            		nciId = identifiedPerson.getAssignedId().getExtension();
            		break;
            	}
        	}
        	
        	remoteInvestigator = populateRemoteInvestigator(coppaPersonsList.get(0), nciId, organizationsMap.get(coppaPersonsList.get(0).getIdentifier().getExtension()));
            return remoteInvestigator;
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