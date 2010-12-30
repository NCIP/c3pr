package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
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
import edu.duke.cabig.c3pr.domain.RemotePersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import gov.nih.nci.coppa.po.ClinicalResearchStaff;
import gov.nih.nci.coppa.po.IdentifiedOrganization;
import gov.nih.nci.coppa.po.Person;

/**
 * @author Vinay Gangoli
 * The Class RemoteResearchStaffResolver.
 */
public class RemoteResearchStaffResolver extends BaseResolver implements RemoteResolver{

	/** The log. */
    private static Log log = LogFactory.getLog(RemoteResearchStaffResolver.class);
	
    
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
		PersonUser researchStaff = null;
		if (results.size() > 0) {
			coppaPerson = CoppaObjectFactory.getCoppaPerson(results.get(0));
			researchStaff = (RemotePersonUser)populateRole(coppaPerson, "", null);
		}
		
		log.debug("Exiting getRemoteEntityByUniqueId() for:" + this.getClass());
		return researchStaff;
	}
	
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	public List<Object> find(Object example) {
		log.debug("Entering find() for:" + this.getClass());
		RemotePersonUser remoteResearchStaff = null;
		List<Object> remoteResearchStaffList = new ArrayList<Object>();
		try{
			if(example instanceof RemotePersonUser){
				remoteResearchStaff = (RemotePersonUser) example;
				
				if(!StringUtils.isEmpty(remoteResearchStaff.getAssignedIdentifier())){
					log.debug("Searching based on NciId");
					remoteResearchStaffList = searchRoleBasedOnNciId(remoteResearchStaff.getAssignedIdentifier(), new ClinicalResearchStaff());
				} else if(remoteResearchStaff.getHealthcareSites().size() > 0 
						&& remoteResearchStaff.getHealthcareSites().get(0).getPrimaryIdentifier() != null){ // It is search by example so only one org will be present in object
					log.debug("Searching based on Organization");
					remoteResearchStaffList = searchRoleBasedOnOrganization(remoteResearchStaff.getHealthcareSites().get(0).getPrimaryIdentifier(), new ClinicalResearchStaff());
				} else {
					log.debug("Searching based on Name");
					remoteResearchStaffList = searchRoleBasedOnName(remoteResearchStaff.getFirstName(), remoteResearchStaff.getMiddleName(), remoteResearchStaff.getLastName(), new ClinicalResearchStaff());
				}
			}
		} catch (Exception e){
			log.error(e.getMessage());
		}
		log.debug("Exiting find() for:" + this.getClass());
		return remoteResearchStaffList;
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
	public Object populateRole(Person coppaPerson, String staffAssignedIdentifier, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList,
																		Map<String, IdentifiedOrganization>	organizationIdToIdentifiedOrganizationsMap){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemotePersonUser());
		if(object == null){
			return null;
		} else {
			RemotePersonUser remoteResearchStaff = (RemotePersonUser) object;
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
						
						remoteResearchStaff.addHealthcareSite(healthcareSite);
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
	public Object populateRole(Person coppaPerson, String staffAssignedIdentifier, IdentifiedOrganization identifiedOrganization){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemotePersonUser());
		if(object != null){
			RemotePersonUser remoteResearchStaff = (RemotePersonUser) object;
			remoteResearchStaff.setAssignedIdentifier(staffAssignedIdentifier);
			remoteResearchStaff.setExternalId(coppaPerson.getIdentifier().getExtension());
			
			if(identifiedOrganization != null){	
				//Build HealthcareSite
				HealthcareSite healthcareSite = new RemoteHealthcareSite();
				personOrganizationResolverUtils.setCtepCodeFromExtension(healthcareSite,identifiedOrganization.getAssignedId().getExtension());
				remoteResearchStaff.addHealthcareSite(healthcareSite);
			}
			return remoteResearchStaff;
		}
		return null;
	}
	

	public Object saveOrUpdate(Object example) {
		// TODO Auto-generated method stub
		return null;
	}
	
}