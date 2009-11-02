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
             IdentifiedPerson identifiedPerson = personOrganizationResolverUtils.getIdentifiedPerson(identifiedPersonToSearch);
             if (identifiedPerson == null) {
                 return remoteResearchStaffList;
             }
             II ii = identifiedPerson.getPlayerIdentifier();
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
                        for(String personXml: persons){
                            Person person = CoppaObjectFactory.getCoppaPerson(personXml);
                            IdentifiedPerson identifiedPerson = personOrganizationResolverUtils.getIdentifiedPerson(person.getIdentifier());
                            String nciIdentifier = null;
                            if (identifiedPerson != null) {
                                nciIdentifier = identifiedPerson.getAssignedId().getExtension();
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
			resultXml = personOrganizationResolverUtils.broadcastPersonSearch(personXml);
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
	    	Map<String, IdentifiedPerson> nciIdsMap = personOrganizationResolverUtils.getIdentifiedPersonsForPersonList(personList);
	    	String nciId;
			RemoteResearchStaff tempRemoteResearchStaff = null;
			for(Person coppaPerson: personList){
        		IdentifiedPerson identifiedPerson = nciIdsMap.get(coppaPerson.getIdentifier().getExtension());
        		nciId = "";
        		if(identifiedPerson != null){
        			nciId = identifiedPerson.getAssignedId().getExtension();
        		}            	
            	List<gov.nih.nci.coppa.po.Organization> organizationsList = organizationsMap.get(coppaPerson.getIdentifier().getExtension());
            	tempRemoteResearchStaff = populateRemoteResearchStaff(coppaPerson, nciId, organizationsList);
            	if(tempRemoteResearchStaff != null){
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
			
			//Build a map with personId as key and sRole as value
			Map<String, ClinicalResearchStaff> sRoleMap = new HashMap<String, ClinicalResearchStaff>();
			if(sRoles != null && sRoles.size() > 0){
				ClinicalResearchStaff crs = null;
				for(String sRole: sRoles){
					crs = CoppaObjectFactory.getCoppaClinicalResearchStaff(sRole);
					if(crs != null){
						sRoleMap.put(crs.getPlayerIdentifier().getExtension(), crs);
					}
				}
			}
			
			//Iterate over the person list and build the investigators; Get Organizations only if they have roles in the sRolesmap
			Person coppaPerson = null;
			List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList;
			for(int index = 0; index < coppaPersonsList.size(); index++){
				coppaOrganizationList = null;
				coppaPerson = coppaPersonsList.get(index);
				//Only if the person has a HealthcareProvider role do we fetch the associated Organization.
				if(sRoleMap.containsKey(coppaPerson.getIdentifier().getExtension())){
					ClinicalResearchStaff crs = sRoleMap.get(coppaPerson.getIdentifier().getExtension());
					String orgIiXml = CoppaObjectFactory.getCoppaIIXml(crs.getScoperIdentifier());
	
					//Coppa-call for Organization search
					String orgResultXml = personOrganizationResolverUtils.broadcastOrganizationGetById(orgIiXml);
					List<String> orgResults = XMLUtils.getObjectsFromCoppaResponse(orgResultXml);
					if (orgResults.size() > 0) {
						coppaOrganizationList = new ArrayList<gov.nih.nci.coppa.po.Organization>();
						coppaOrganizationList.add(CoppaObjectFactory.getCoppaOrganization(orgResults.get(0)));
					}
				} 
				organizationsMap.put(coppaPerson.getIdentifier().getExtension(), coppaOrganizationList);
			}
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}
		return organizationsMap;
	}
	
	
	public Object getRemoteEntityByUniqueId(String externalId) {
		log.debug("Entering getRemoteEntityByUniqueId() for:" + this.getClass());
		II ii = CoppaObjectFactory.getIISearchCriteriaForPerson(externalId);
		
		String iiXml = CoppaObjectFactory.getCoppaIIXml(ii);
		String resultXml = "";
		try {
			resultXml = personOrganizationResolverUtils.broadcastPersonGetById(iiXml);
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}
		
		List<String> results = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList = null;
		Person coppaPerson = null;
		if (results.size() > 0) {
			coppaPerson = CoppaObjectFactory.getCoppaPerson(results.get(0));
			coppaOrganizationList = getOrganizationsForPerson(coppaPerson);
		}
		
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
            IdentifiedPerson identifiedPerson = personOrganizationResolverUtils.getIdentifiedPerson(coppaPerson.getIdentifier());
            
            if (identifiedPerson != null ) {
                    nciIdentifier = identifiedPerson.getAssignedId().getExtension();
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
				IdentifiedPerson identifiedPerson = personOrganizationResolverUtils.getIdentifiedPerson(ii);
				if(identifiedPerson  != null){
					remoteResearchStaff.setNciIdentifier(identifiedPerson.getAssignedId().getExtension());
				} else {
					log.error("IdentifiedPerson is null for person with coppaId: "+coppaPerson.getIdentifier().getExtension());
				}
			}
			
			//Build HealthcareSite 
			RemoteHealthcareSite healthcareSite = null;
			if(coppaOrganizationList != null && coppaOrganizationList.size()>0){
				for(gov.nih.nci.coppa.po.Organization coppaOrganization: coppaOrganizationList){
					IdentifiedOrganization identifiedOrganization = personOrganizationResolverUtils.getIdentifiedOrganization(coppaOrganization);
					
					healthcareSite = new RemoteHealthcareSite();
					personOrganizationResolverUtils.setCtepCodeFromExtension(healthcareSite, identifiedOrganization.getAssignedId().getExtension());
					healthcareSite.setName(CoppaObjectFactory.getName(coppaOrganization.getName()));
					
					healthcareSite.setExternalId(coppaOrganization.getIdentifier().getExtension());
					Address address = personOrganizationResolverUtils.getAddressFromCoppaOrganization(coppaOrganization);
					healthcareSite.setAddress(address);
					
					remoteResearchStaff.setHealthcareSite(healthcareSite);
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