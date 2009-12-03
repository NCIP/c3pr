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
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.IdentifiedPerson;
import gov.nih.nci.coppa.po.Person;

/**
 * @author Vinay Gangoli
 * The Class RemoteResearchStaffResolver.
 */
public class RemoteResearchStaffResolver implements RemoteResolver{

	/** The log. */
    private static Log log = LogFactory.getLog(RemoteResearchStaffResolver.class);
	
    private PersonOrganizationResolverUtils personOrganizationResolverUtils;
    
    public static final String CTEP_PERSON = "Cancer Therapy Evaluation Program Person Identifier";
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	public List<Object> find(Object example) {
		log.debug("Entering find() for:" + this.getClass());
		RemoteResearchStaff remoteResearchStaff = null;
		List<Object> remoteResearchStaffList = null;
		
		if(example instanceof RemoteResearchStaff){
			remoteResearchStaff = (RemoteResearchStaff) example;
			
			if(!StringUtils.isEmpty(remoteResearchStaff.getNciIdentifier())){
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
		log.debug("Exiting find() for:" + this.getClass());
		return remoteResearchStaffList;
	}

	
	private List<Object> searchStaffBasedOnNciId(RemoteResearchStaff remoteResearchStaffExample) {
		List<Object> remoteResearchStaffList = new ArrayList<Object>();
        RemoteResearchStaff tempRemoteResearchStaff = null; 
		
		if (remoteResearchStaffExample.getNciIdentifier() != null) {
             //get Identified Organization ...
             IdentifiedPerson identifiedPersonToSearch = CoppaObjectFactory.getCoppaIdentfiedPersonSearchCriteriaOnCTEPId(remoteResearchStaffExample.getNciIdentifier());
             List<IdentifiedPerson> identifiedPersonsList = personOrganizationResolverUtils.getIdentifiedPerson(identifiedPersonToSearch);
             if (identifiedPersonsList.size() == 0) {
                 return remoteResearchStaffList;
             }
             II ii = identifiedPersonsList.get(0).getPlayerIdentifier();
             String iiXml = CoppaObjectFactory.getCoppaIIXml(ii);
             try {
                 String resultXml = personOrganizationResolverUtils.broadcastPersonGetById(iiXml);
                 tempRemoteResearchStaff = loadResearchStaffForPersonResult(resultXml);
                 remoteResearchStaffList.add(tempRemoteResearchStaff);
             } catch (Exception e) {
                log.error(e.getMessage());
             }
		}
		return remoteResearchStaffList;
	}


	private List<Object> searchStaffBasedOnOrganization(RemoteResearchStaff remoteResearchStaffExample) {
		List<Object> remoteResearchStaffList = new ArrayList<Object>();
        RemoteResearchStaff tempRemoteResearchStaff = null;
        try{
        	//Get IdentifiedOrganization by ctepId(nciId)
            IdentifiedOrganization identifiedOrganizationSearchCriteria = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaOnCTEPId
            			(remoteResearchStaffExample.getHealthcareSite().getPrimaryIdentifier());
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
                    //Organization ii is the scoper for clinical research staff...which returns crs with staff as playerId
                    ClinicalResearchStaff clinicalResearchStaff = CoppaObjectFactory.getCoppaClinicalResearchStaffWithScoperIdAsSearchCriteria(ii);
                    String coppaClinicalResearchStaffXml = CoppaObjectFactory.getClinicalResearchStaffXml(clinicalResearchStaff);
                    String sRolesXml = personOrganizationResolverUtils.broadcastClinicalResearchStaffSearch(coppaClinicalResearchStaffXml);

                    List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
                    for(String sRole: sRoles){
                        ClinicalResearchStaff crs = CoppaObjectFactory.getCoppaClinicalResearchStaff(sRole);
                        II pid = crs.getPlayerIdentifier();    
                        String idXml = CoppaObjectFactory.getCoppaIIXml(pid);
                        //above player id is the Id of a Person ... now get  the Person by Id
                        String personResultXml = personOrganizationResolverUtils.broadcastPersonGetById(idXml);
                        List<String> persons = XMLUtils.getObjectsFromCoppaResponse(personResultXml);  
                        if(persons.size() > 0){
                            Person person = CoppaObjectFactory.getCoppaPerson(persons.get(0));
                            List<Person> personList = new ArrayList<Person>();
                            personList.add(person);
                            
                            Map<String, List<IdentifiedPerson>> nciIdsMap = personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(personList); 
                            List<IdentifiedPerson> identifiedPersonList = nciIdsMap.get(person.getIdentifier().getExtension());
                            
                            String nciIdentifier = null;
                            for(IdentifiedPerson identifiedPerson:identifiedPersonList){
            					if(identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)){
            						nciIdentifier = identifiedPerson.getAssignedId().getExtension();
            						break;
            					}
                            }
                            tempRemoteResearchStaff = populateRemoteResearchStaff(person, nciIdentifier, coppaIdOrganization);
                            if(tempRemoteResearchStaff != null){
                            	remoteResearchStaffList.add(tempRemoteResearchStaff);
                            }
                        }
                    }
                }
            }
        } catch(C3PRCodedException cce){
        	log.error(cce.getMessage());
        } catch(Exception e){
        	log.error(e.getMessage());
        }
        return remoteResearchStaffList;
	}


	private List<Object> searchStaffBasedOnName(RemoteResearchStaff remoteResearchStaff) {
		List<Object> remoteResearchStaffList = new ArrayList<Object>();
		String personXml = CoppaObjectFactory.getCoppaPersonXml(
				CoppaObjectFactory.getCoppaPerson(remoteResearchStaff.getFirstName(), remoteResearchStaff.getMiddleName(), remoteResearchStaff.getLastName()));
		String resultXml = "";
		try {
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
			
			Map<String, List<gov.nih.nci.coppa.po.Organization>> organizationsMap = getOrganizationsForPersonsList(personList);
	    	Map<String, List<IdentifiedPerson>> nciIdsMap = personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(personList);
			RemoteResearchStaff tempRemoteResearchStaff = null;
			for(Person coppaPerson: personList){
				String nciId = null;
				if(nciIdsMap.containsKey(coppaPerson.getIdentifier().getExtension())){
					List<IdentifiedPerson> identifiedPersonList = nciIdsMap.get(coppaPerson.getIdentifier().getExtension());
	        		for(IdentifiedPerson identifiedPerson: identifiedPersonList){
	        			if(identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)){
	            			nciId = identifiedPerson.getAssignedId().getExtension();
	            		}
	        		}
				}
        		
        		List<gov.nih.nci.coppa.po.Organization> organizationsList = organizationsMap.get(coppaPerson.getIdentifier().getExtension());
            	tempRemoteResearchStaff = populateRemoteResearchStaff(coppaPerson, nciId, organizationsList);
            	if(tempRemoteResearchStaff != null && tempRemoteResearchStaff.getHealthcareSite() != null){
					remoteResearchStaffList.add(tempRemoteResearchStaff);
				}
			}
		}
		return remoteResearchStaffList;
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
			
			//Coppa-call for Structural Role(ClinicalResearchStaff) getByIds
			String sRolesXml = personOrganizationResolverUtils.broadcastClinicalResearchStaffGetByPlayerIds(personIdXmlList);
			List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
			
			//Build a map with personId as key and List of sRole as value
			Map<String, List<ClinicalResearchStaff>> sRoleMap = new HashMap<String, List<ClinicalResearchStaff>>();
			if(sRoles != null && sRoles.size() > 0){
				ClinicalResearchStaff crs = null;
				for(String sRole: sRoles){
					crs = CoppaObjectFactory.getCoppaClinicalResearchStaff(sRole);
					if(crs != null){
						List<ClinicalResearchStaff> crsList = null;
						if(sRoleMap.containsKey(crs.getPlayerIdentifier().getExtension())){
							crsList  = sRoleMap.get(crs.getPlayerIdentifier().getExtension());
							crsList.add(crs);
						} else {
							crsList = new ArrayList<ClinicalResearchStaff>();
							crsList.add(crs);
							sRoleMap.put(crs.getPlayerIdentifier().getExtension(), crsList);
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
					List<ClinicalResearchStaff> crsList = sRoleMap.get(coppaPerson.getIdentifier().getExtension());
					for(ClinicalResearchStaff crs : crsList){
						String orgIiXml = CoppaObjectFactory.getCoppaIIXml(crs.getScoperIdentifier());
						
						//Coppa-call for Organization getById
						String orgResultXml = personOrganizationResolverUtils.broadcastOrganizationGetById(orgIiXml);
						List<String> orgResults = XMLUtils.getObjectsFromCoppaResponse(orgResultXml);
						if (orgResults.size() > 0) {
							coppaOrganizationList.add(CoppaObjectFactory.getCoppaOrganization(orgResults.get(0)));
						}
					}
					organizationsMap.put(coppaPerson.getIdentifier().getExtension(), coppaOrganizationList);
				} else {
					//Remove non-staff from persons list.
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
		if (results.size() > 0) {
			coppaPerson = CoppaObjectFactory.getCoppaPerson(results.get(0));
		}
		
		List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList = null;
		ResearchStaff researchStaff = populateRemoteResearchStaff(coppaPerson, null, coppaOrganizationList);
		log.debug("Exiting getRemoteEntityByUniqueId() for:" + this.getClass());
		return researchStaff;
	}

	
	private RemoteResearchStaff loadResearchStaffForPersonResult(String personResultXml) {
        List<String> results = XMLUtils.getObjectsFromCoppaResponse(personResultXml);
        List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList = null;
        RemoteResearchStaff remoteResearchStaff = null;
        Person coppaPerson = null;
        String nciIdentifier = null;
        if (results.size() > 0) {
            coppaPerson = CoppaObjectFactory.getCoppaPerson(results.get(0));
            coppaOrganizationList = getOrganizationsForPerson(coppaPerson);
            List<IdentifiedPerson> identifiedPersonsList = personOrganizationResolverUtils.getIdentifiedPerson(coppaPerson.getIdentifier());
            for(IdentifiedPerson identifiedPerson: identifiedPersonsList){
            	if (identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)) {
                    nciIdentifier = identifiedPerson.getAssignedId().getExtension();
                    break;
            	}
            }
            remoteResearchStaff =  this.populateRemoteResearchStaff(coppaPerson, nciIdentifier, coppaOrganizationList);
        }
        return remoteResearchStaff;            
	}
	
	
	/**
	 * Gets the organizations for person. 
	 * 
	 * Gets the ClinicalResearchStaff for a person. This is a Structural Role.
	 * This role has the person as the player and the Organization as the scoper.
	 * So get the scoper id from the role and use it to search all orgs. This will get us
	 * all the related orgs.
	 * 
	 * @param coppaPerson the coppa person
	 * @return the organizations for person
	 */
	private List<gov.nih.nci.coppa.po.Organization> getOrganizationsForPerson(Person coppaPerson) {
		List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = new ArrayList<gov.nih.nci.coppa.po.Organization>();
		ClinicalResearchStaff clinicalResearchStaff = CoppaObjectFactory.getCoppaClinicalResearchStaff(coppaPerson.getIdentifier());
		String coppaClinicalResearchStaffXml = CoppaObjectFactory.getCoppaClinicalResearchStaffXml(clinicalResearchStaff);
		String sRolesXml = "";
		try {
			sRolesXml = personOrganizationResolverUtils.broadcastClinicalResearchStaffSearch(coppaClinicalResearchStaffXml);
		} catch (C3PRCodedException e) {
			System.out.print(e);
		}
		List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
		//Only if the person has a clinicalResearchStaff role do we process further.
		if(sRoles != null && sRoles.size() > 0){
			for(String sRole: sRoles){
				String orgResultXml = "";
				ClinicalResearchStaff crs = CoppaObjectFactory.getCoppaClinicalResearchStaff(sRole);
				String orgIiXml = CoppaObjectFactory.getCoppaIIXml(crs.getScoperIdentifier());
				try {
					orgResultXml = personOrganizationResolverUtils.broadcastOrganizationGetById(orgIiXml);
				} catch (Exception e) {
					System.out.print(e);
				}
				List<String> orgResults = XMLUtils.getObjectsFromCoppaResponse(orgResultXml);
				if (orgResults.size() > 0) {
					coppaOrganizationList.add(CoppaObjectFactory.getCoppaOrganization(orgResults.get(0)));
				}
			}
		}
		return coppaOrganizationList;
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
	public RemoteResearchStaff populateRemoteResearchStaff(Person coppaPerson, String staffNciIdentifier, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteResearchStaff());
		if(object == null){
			return null;
		} else {
			RemoteResearchStaff remoteResearchStaff = (RemoteResearchStaff) object;
			remoteResearchStaff.setExternalId(coppaPerson.getIdentifier().getExtension());
			
			if(!StringUtils.isEmpty(staffNciIdentifier)){
				remoteResearchStaff.setNciIdentifier(staffNciIdentifier);
			} else {
				II ii = CoppaObjectFactory.getIISearchCriteriaForPerson(coppaPerson.getIdentifier().getExtension());
				List<IdentifiedPerson> identifiedPersonsList = personOrganizationResolverUtils.getIdentifiedPerson(ii);
				for(IdentifiedPerson identifiedPerson: identifiedPersonsList){
					if(identifiedPerson != null && identifiedPerson.getAssignedId().getRoot().equalsIgnoreCase(CTEP_PERSON)){
						remoteResearchStaff.setNciIdentifier(identifiedPerson.getAssignedId().getExtension());
						break;
					} else {
						log.error("IdentifiedPerson is null for person with coppaId: "+coppaPerson.getIdentifier().getExtension());
					}
				}
			}
			
			//Build HealthcareSite
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
	public RemoteResearchStaff populateRemoteResearchStaff(Person coppaPerson, String staffNciIdentifier, IdentifiedOrganization identifiedOrganization){

		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemoteResearchStaff());
		if(object == null){
			return null;
		} else {
			RemoteResearchStaff remoteResearchStaff = (RemoteResearchStaff) object;
			remoteResearchStaff.setNciIdentifier(staffNciIdentifier);
			remoteResearchStaff.setExternalId(coppaPerson.getIdentifier().getExtension());
			
			//Build HealthcareSite
			HealthcareSite healthcareSite = new RemoteHealthcareSite();
			personOrganizationResolverUtils.setCtepCodeFromExtension(healthcareSite,identifiedOrganization.getAssignedId().getExtension());
			remoteResearchStaff.setHealthcareSite(healthcareSite);
			return remoteResearchStaff;
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