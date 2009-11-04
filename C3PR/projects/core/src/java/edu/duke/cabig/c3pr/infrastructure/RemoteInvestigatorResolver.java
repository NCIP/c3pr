package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

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
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
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
			remoteInvestigator.setNciIdentifier(coppaPerson.getIdentifier().getExtension());
			remoteInvestigator.setExternalId(coppaPerson.getIdentifier().getExtension());
			
			if(!StringUtils.isEmpty(staffNciIdentifier)){
				remoteInvestigator.setNciIdentifier(staffNciIdentifier);
			} else {
				II ii = CoppaObjectFactory.getIISearchCriteriaForPerson(coppaPerson.getIdentifier().getExtension());
				IdentifiedPerson identifiedPerson = personOrganizationResolverUtils.getIdentifiedPerson(ii);
				if(identifiedPerson  != null){
					remoteInvestigator.setNciIdentifier(identifiedPerson.getAssignedId().getExtension());
				} else {
					log.error("IdentifiedPerson is null for person with coppaId: "+coppaPerson.getIdentifier().getExtension());
				}
			}
			
			//Build HealthcareSite and HealthcareSiteInvestigator
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
					
					HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
					healthcareSiteInvestigator.setHealthcareSite(healthcareSite);
					healthcareSiteInvestigator.setInvestigator(remoteInvestigator);
					healthcareSiteInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);
					
					remoteInvestigator.getHealthcareSiteInvestigators().add(healthcareSiteInvestigator);
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
	
			remoteInvestigator.setNciIdentifier(coppaPerson.getIdentifier().getExtension());
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
		RemoteInvestigator remoteInvestigator = null;
		List<Object> remoteInvestigatorList = null;
		
		if(example instanceof RemoteInvestigator){
			remoteInvestigator = (RemoteInvestigator) example;
			
			if(!StringUtils.isEmpty(remoteInvestigator.getNciIdentifier())){
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
		return remoteInvestigatorList;
	}
	
	private List<Object> searchInvestigatorBasedOnOrganization(RemoteInvestigator remoteInvestigatorExample){
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		RemoteInvestigator tempRemoteInvestigator = null;
        
        //get IdentifiedOrganization by ctepId(nciId)
        IdentifiedOrganization identifiedOrganizationSearchCriteria = CoppaObjectFactory.getCoppaIdentfiedOrganizationSearchCriteriaOnCTEPId
        			(remoteInvestigatorExample.getHealthcareSiteInvestigators().get(0).getHealthcareSite().getPrimaryIdentifier());
        String payload = CoppaObjectFactory.getCoppaIdentfiedOrganization(identifiedOrganizationSearchCriteria);
        String results = "";
		try {
			results = personOrganizationResolverUtils.broadcastIdentifiedOrganizationSearch(payload);
		} catch (C3PRCodedException e) {
			log.error(e.getMessage());
		}

        List<String> resultObjects = XMLUtils.getObjectsFromCoppaResponse(results);
        for (String resultObj:resultObjects) {
            IdentifiedOrganization coppaIdOrganization = CoppaObjectFactory.getCoppaIdentfiedOrganization(resultObj);
            II organizationIdentifier = coppaIdOrganization.getPlayerIdentifier();
            String iiXml = CoppaObjectFactory.getCoppaIIXml(organizationIdentifier);
            //Get Organizations based on player id of above IdentifiedOrganizations.
            String organizationResults = "";
			try {
				organizationResults = personOrganizationResolverUtils.broadcastOrganizationGetById(iiXml);
			} catch (C3PRCodedException e) {
				log.error(e);
			}
            
            List<String> organizationResultObjects = XMLUtils.getObjectsFromCoppaResponse(organizationResults);
            //should contain only one but looping anyway(fix later)
            for (String organizationResultObject:organizationResultObjects) {
                gov.nih.nci.coppa.po.Organization coppaOrganizationResult = CoppaObjectFactory.getCoppaOrganization(organizationResultObject);
                II ii = coppaOrganizationResult.getIdentifier();
                //Organization ii is the scoper for healthCareProvider...which returns hcp with staff as playerId
                HealthCareProvider healthCareProvider = CoppaObjectFactory.getCoppaHealthCareProviderWithScoperIdAsSearchCriteria(ii);
                String coppaHealthCareProviderXml = CoppaObjectFactory.getCoppaHealthCareProviderXml(healthCareProvider);
                String sRolesXml = "";
                try {
                    sRolesXml = personOrganizationResolverUtils.broadcastHealthcareProviderSearch(coppaHealthCareProviderXml);
                } catch (C3PRCodedException e) {
                	log.error(e);
                }

                List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
                for(String sRole: sRoles){
                    HealthCareProvider hcp = CoppaObjectFactory.getCoppaHealthCareProvider(sRole);
                    II pid = hcp.getPlayerIdentifier();    
                    String idXml = CoppaObjectFactory.getCoppaIIXml(pid);
                    //above player id is the Id of a Person ... now get  the Person by Id
                    String personResultXml = "";
                    try {
                        personResultXml = personOrganizationResolverUtils.broadcastPersonGetById(idXml);
                        List<String> persons = XMLUtils.getObjectsFromCoppaResponse(personResultXml);  
                        for(String personXml: persons){
                            Person person = CoppaObjectFactory.getCoppaPerson(personXml);
                            IdentifiedPerson identifiedPerson = personOrganizationResolverUtils.getIdentifiedPerson(person.getIdentifier());
                            String nciIdentifier = null;
                            if (identifiedPerson != null) {
                                nciIdentifier = identifiedPerson.getAssignedId().getExtension();
                            }
                            tempRemoteInvestigator = populateRemoteInvestigator(person, nciIdentifier, coppaIdOrganization);
                            remoteInvestigatorList.add(tempRemoteInvestigator);
                        }
                    } catch (C3PRCodedException e) {
                            log.error(e);
                    }
                }
            }
        }
        return remoteInvestigatorList;
	}

	
	private List<Object> searchInvestigatorBasedOnNciId(RemoteInvestigator remoteInvestigatorExample) {
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		RemoteInvestigator tempRemoteInvestigator = null; 
		
		if (remoteInvestigatorExample.getNciIdentifier() != null) {
             //get Identified Organization ...
             IdentifiedPerson identifiedPersonToSearch = CoppaObjectFactory.getCoppaIdentfiedPersonSearchCriteriaOnCTEPId(remoteInvestigatorExample.getNciIdentifier());
             IdentifiedPerson identifiedPerson = personOrganizationResolverUtils.getIdentifiedPerson(identifiedPersonToSearch);
             if (identifiedPerson == null) {
                 return remoteInvestigatorList;
             }
             II ii = identifiedPerson.getPlayerIdentifier();
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


	private List<Object> searchInvestigatorBasedOnName(RemoteInvestigator remoteInvestigator) {
		List<Object> remoteInvestigatorList = new ArrayList<Object>();
		//Serialize the remoteInv(used for searches based on Name(first, middle, last))
		String personXml = CoppaObjectFactory.getCoppaPersonXml(
				CoppaObjectFactory.getCoppaPerson(remoteInvestigator.getFirstName(), remoteInvestigator.getMiddleName(), remoteInvestigator.getLastName()));
		String resultXml = "";
		try {
			//Calling Coppa for person search
			resultXml = personOrganizationResolverUtils.broadcastPersonSearch(personXml);
		} catch (Exception e) {
			System.out.print(e);
		}
		
		List<String> coppaPersons = XMLUtils.getObjectsFromCoppaResponse(resultXml);
		RemoteInvestigator tempRemoteInvestigator = null;
		Person coppaPerson = null;
		if (coppaPersons != null){
			for(String coppaPersonXml: coppaPersons){
				//deserializing the person returned
				coppaPerson = CoppaObjectFactory.getCoppaPerson(coppaPersonXml);
				//calling Coppa for organization search
				List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = getOrganizationsForPerson(coppaPerson);
				//if the person is not a staff then he/she wont have a role and hence wont have a org
				if(coppaOrganizationList.size() > 0){
					tempRemoteInvestigator = populateRemoteInvestigator(coppaPerson, null, coppaOrganizationList);
					remoteInvestigatorList.add(tempRemoteInvestigator);
				}
			}
		}
		return remoteInvestigatorList;
	}


	/**
	 * Gets the organizations for person.
	 * Gets the HealthcareProvider for a person. This is a Structural Role.
	 * This role has the person as the player and the Organization as the scoper.
	 * So get the scoper id from the role and use it to search all orgs. This will get us
	 * all the related orgs.
	 * 
	 * @param coppaPerson the coppa person
	 * @return the organizations for person
	 */
	private List<gov.nih.nci.coppa.po.Organization> getOrganizationsForPerson(Person coppaPerson) {
		List<gov.nih.nci.coppa.po.Organization>  coppaOrganizationList = new ArrayList<gov.nih.nci.coppa.po.Organization>();
		HealthCareProvider healthCareProvider = CoppaObjectFactory.getCoppaHealthCareProvider(coppaPerson.getIdentifier());
		String coppaHealthCareProviderXml = CoppaObjectFactory.getCoppaHealthCareProviderXml(healthCareProvider);
		String sRolesXml = "";
		try {
			sRolesXml = personOrganizationResolverUtils.broadcastHealthcareProviderSearch(coppaHealthCareProviderXml);
		} catch (C3PRCodedException e) {
			System.out.print(e);
		}
		List<String> sRoles = XMLUtils.getObjectsFromCoppaResponse(sRolesXml);
		//Only if the person has a healthcare provider role do we process further.
		if(sRoles != null && sRoles.size() > 0){
			for(String sRole: sRoles){
				String orgResultXml = "";
				HealthCareProvider hcp = CoppaObjectFactory.getCoppaHealthCareProvider(sRole);
				String orgIiXml = CoppaObjectFactory.getCoppaIIXml(hcp.getScoperIdentifier());
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
	 * Load investigator for person result. This is also used from StudyResolver; hence the public scope.
	 * 
	 * @param personResultXml the person result xml
	 * 
	 * @return the remote investigator
	 */
	public RemoteInvestigator loadInvestigatorForPersonResult(String personResultXml) {
        List<String> results = XMLUtils.getObjectsFromCoppaResponse(personResultXml);
        List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList = null;
        RemoteInvestigator remoteInvestigator = null;
        Person coppaPerson = null;
        String nciIdentifier = null;
        if (results.size() > 0) {
            coppaPerson = CoppaObjectFactory.getCoppaPerson(results.get(0));
            coppaOrganizationList = getOrganizationsForPerson(coppaPerson);
            IdentifiedPerson identifiedPerson = personOrganizationResolverUtils.getIdentifiedPerson(coppaPerson.getIdentifier());
            
            if (identifiedPerson != null ) {
                    nciIdentifier = identifiedPerson.getAssignedId().getExtension();
            }
            remoteInvestigator =  this.populateRemoteInvestigator(coppaPerson, nciIdentifier, coppaOrganizationList);
        }

        return remoteInvestigator;            
	}
	

	public Object getRemoteEntityByUniqueId(String externalId) {
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
		
		RemoteInvestigator remoteInvestigator = populateRemoteInvestigator(coppaPerson, null, coppaOrganizationList);
		return remoteInvestigator;
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