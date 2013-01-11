/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.iso._21090.II;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.util.CoppaObjectFactory;

import edu.duke.cabig.c3pr.constants.PersonUserType;
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
		PersonUser personUser = null;
		if (results.size() > 0) {
			coppaPerson = CoppaObjectFactory.getCoppaPerson(results.get(0));
			personUser = (RemotePersonUser)populateRole(coppaPerson, "", null);
		}
		
		log.debug("Exiting getRemoteEntityByUniqueId() for:" + this.getClass());
		return personUser;
	}
	
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	public List<Object> find(Object example) {
		log.debug("Entering find() for:" + this.getClass());
		RemotePersonUser remotePersonUser = null;
		List<Object> remotePersonUserList = new ArrayList<Object>();
		try{
			if(example instanceof RemotePersonUser){
				remotePersonUser = (RemotePersonUser) example;
				
				if(!StringUtils.isEmpty(remotePersonUser.getAssignedIdentifier())){
					log.debug("Searching based on NciId");
					remotePersonUserList = searchRoleBasedOnNciId(remotePersonUser.getAssignedIdentifier(), new ClinicalResearchStaff());
				} else if(remotePersonUser.getHealthcareSites().size() > 0 
						&& remotePersonUser.getHealthcareSites().get(0).getPrimaryIdentifier() != null){ // It is search by example so only one org will be present in object
					log.debug("Searching based on Organization");
					remotePersonUserList = searchRoleBasedOnOrganization(remotePersonUser.getHealthcareSites().get(0).getPrimaryIdentifier(), new ClinicalResearchStaff());
				} else {
					log.debug("Searching based on Name");
					remotePersonUserList = searchRoleBasedOnName(remotePersonUser.getFirstName(), remotePersonUser.getMiddleName(), remotePersonUser.getLastName(), new ClinicalResearchStaff());
				}
			}
		} catch (Exception e){
			log.error(e.getMessage());
		}
		log.debug("Exiting find() for:" + this.getClass());
		return remotePersonUserList;
	}
	
	
	/**
	 * Populate remote PersonUser.
	 * 
	 * @param personDTO the person dto
	 * @param orgCtepId the org ctep id
	 * @param coppaOrgId the coppa org id
	 * 
	 * @return the personUser Object
	 */
	public Object populateRole(Person coppaPerson, String staffAssignedIdentifier, List<gov.nih.nci.coppa.po.Organization> coppaOrganizationList,
																		Map<String, IdentifiedOrganization>	organizationIdToIdentifiedOrganizationsMap){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemotePersonUser());
		if(object == null){
			return null;
		} else {
			RemotePersonUser remotePersonUser = (RemotePersonUser) object;
			remotePersonUser.setExternalId(coppaPerson.getIdentifier().getExtension());
			remotePersonUser.setAssignedIdentifier(staffAssignedIdentifier);
			remotePersonUser.setPersonUserType(PersonUserType.STAFF);
			
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
						
						remotePersonUser.addHealthcareSite(healthcareSite);
					} else {
						log.error("IdentifiedOrganization is null for Organization with coppaId: "+coppaOrganization.getIdentifier().getExtension());
					}
				}
			}
			return remotePersonUser;
		}
	}
	
	/**
	 * Populate remote PersonUser. Called from searchStaffByrganization()
	 * 
	 * @param personDTO the person dto
	 * @param orgCtepId the org ctep id
	 * @param coppaOrgId the coppa org id
	 * 
	 * @return the PersonUser Object
	 */
	public Object populateRole(Person coppaPerson, String staffAssignedIdentifier, IdentifiedOrganization identifiedOrganization){
		Object object = personOrganizationResolverUtils.setC3prUserDetails(coppaPerson, new RemotePersonUser());
		if(object != null){
			RemotePersonUser remotePersonUser = (RemotePersonUser) object;
			remotePersonUser.setAssignedIdentifier(staffAssignedIdentifier);
			remotePersonUser.setExternalId(coppaPerson.getIdentifier().getExtension());
			remotePersonUser.setPersonUserType(PersonUserType.STAFF);
			
			if(identifiedOrganization != null){	
				//Build HealthcareSite
				HealthcareSite healthcareSite = new RemoteHealthcareSite();
				personOrganizationResolverUtils.setCtepCodeFromExtension(healthcareSite,identifiedOrganization.getAssignedId().getExtension());
				remotePersonUser.addHealthcareSite(healthcareSite);
			}
			return remotePersonUser;
		}
		return null;
	}
	

	public Object saveOrUpdate(Object example) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
