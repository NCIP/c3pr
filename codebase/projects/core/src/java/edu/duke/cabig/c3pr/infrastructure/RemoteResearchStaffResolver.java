package edu.duke.cabig.c3pr.infrastructure;

import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import gov.nih.nci.coppa.iso.EntityNamePartType;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.Util.IiConverter;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;

/**
 * @author Vinay Gangoli
 * The Class RemoteResearchStaffResolver.
 */
public class RemoteResearchStaffResolver implements RemoteResolver{

	/** The identified organization correlation service remote. */
	private IdentifiedOrganizationCorrelationServiceRemote  identifiedOrganizationCorrelationServiceRemote;
	
	/** The clinical research staff correlation service remote. */
	private ClinicalResearchStaffCorrelationServiceRemote clinicalResearchStaffCorrelationServiceRemote;
	
	/** The person entity service remote. */
	private PersonEntityServiceRemote personEntityServiceRemote;
	
	/** The identified person correlation service remote. */
	private IdentifiedPersonCorrelationServiceRemote identifiedPersonCorrelationServiceRemote;
	
	/** The log. */
    private static Log log = LogFactory.getLog(InvestigatorDao.class);
	
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	public List<Object> find(Object example) {
		List<Object> remoteResearchStaffs = new ArrayList<Object>();
		
		ResearchStaff remoteResearchStaffExample = (RemoteResearchStaff)example;
		//organization ctep-id
		HealthcareSite healthcareSite = remoteResearchStaffExample.getHealthcareSite();
		//if organization is null get all research staffs
		if (healthcareSite == null) {
			PersonDTO personDTO = new PersonDTO();
			List<PersonDTO> allPersons = personEntityServiceRemote.search(personDTO);
			//need to get organization for each person . 
			
			for (PersonDTO person:allPersons) {
				String ctepOrgId = "";
				String coppaOrgId = "";
				try {
					ClinicalResearchStaffDTO ctDto = clinicalResearchStaffCorrelationServiceRemote.getCorrelation(person.getIdentifier()); //player id
					Ii scoperId = ctDto.getScoperIdentifier();
					IdentifiedOrganizationDTO organizationDto = identifiedOrganizationCorrelationServiceRemote.getCorrelation(scoperId);
					ctepOrgId = organizationDto.getAssignedId().getExtension();		
					coppaOrgId = organizationDto.getPlayerIdentifier().getExtension();
				} catch (Exception e) {
					log.error(e.getMessage());
				}
				remoteResearchStaffs.add(this.populateRemoteResearchStaff(person,ctepOrgId,coppaOrgId));
			}
			return remoteResearchStaffs;
		} else {
			String ctepOrgId = healthcareSite.getNciInstituteCode();
			//build IdentifiedOrganizationDTO
			IdentifiedOrganizationDTO idOrgDto = new IdentifiedOrganizationDTO();
			Ii ii = new Ii();
			ii.setExtension(ctepOrgId);
		//	ii.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
			ii.setRoot(IiConverter.CLINICAL_RESEARCH_STAFF_ROOT);
			idOrgDto.setAssignedId(ii);
			// get List of Organizations , in this case it should be only one ...
			List <IdentifiedOrganizationDTO> idOrgDtos = identifiedOrganizationCorrelationServiceRemote.search(idOrgDto);
			if(idOrgDtos != null && idOrgDtos.size() > 0){
				IdentifiedOrganizationDTO identifiedOrganizationDTO = idOrgDtos.get(0);
				// grab coppa-id of organization from above object
				Ii playerId = identifiedOrganizationDTO.getPlayerIdentifier();
				
				// using above id get research staffs
				ClinicalResearchStaffDTO crDto = new ClinicalResearchStaffDTO();
				crDto.setScoperIdentifier(playerId);
				List<ClinicalResearchStaffDTO> clinicalResearchStaffs = clinicalResearchStaffCorrelationServiceRemote.search(crDto);
				
				//now we need to get the details of each person from person service .
				for (ClinicalResearchStaffDTO clinicalResearchStaff:clinicalResearchStaffs) {
					Ii crPlayerId = clinicalResearchStaff.getPlayerIdentifier();
					try {
						PersonDTO personDTO = personEntityServiceRemote.getPerson(crPlayerId);
						ResearchStaff remoteResearchStaff = populateRemoteResearchStaff(personDTO,ctepOrgId,playerId.getExtension());
						remoteResearchStaffs.add(remoteResearchStaff);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}
	
			return remoteResearchStaffs;
		}
	}

	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String externalId) {
		Ii ii = new Ii();
		ii.setExtension(externalId);
		PersonDTO person = null;
		String ctepOrgId = "";
		String coppaOrgId = "";
		try {
			person = personEntityServiceRemote.getPerson(ii);
			if(person != null){
				ClinicalResearchStaffDTO ctDto = clinicalResearchStaffCorrelationServiceRemote.getCorrelation(person.getIdentifier()); //player id
				Ii scoperId = ctDto.getScoperIdentifier();
				IdentifiedOrganizationDTO organizationDto = identifiedOrganizationCorrelationServiceRemote.getCorrelation(scoperId);
				ctepOrgId = organizationDto.getAssignedId().getExtension();	
				coppaOrgId = organizationDto.getPlayerIdentifier().getExtension();
				return populateRemoteResearchStaff(person,ctepOrgId,coppaOrgId);
			} else {
				log.error("externalId did not match with any remote staff!");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
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
	private ResearchStaff populateRemoteResearchStaff(PersonDTO personDTO,String orgCtepId,String coppaOrgId){
		RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
		Iterator<Enxp> enxpItr = personDTO.getName().getPart().iterator();
		Enxp enxp;
		String firstName = "";
		String lastName = "";
		while(enxpItr.hasNext()){
			enxp = enxpItr.next();
			if(enxp.getType().equals(EntityNamePartType.GIV)){
				firstName = enxp.getValue();
			}
			if(enxp.getType().equals(EntityNamePartType.FAM)){
				lastName = enxp.getValue();
			}
		}
        
        String externalId = personDTO.getIdentifier().getExtension();
        Set<Tel> email = personDTO.getTelecomAddress().getItem();
        Iterator<Tel> emailItr = email.iterator();
        Tel nextTel = emailItr.next();
        String emailStr = "";
		try {
			emailStr = nextTel.getValue().toURL().toString();
			//remove mailto: string from email 
			if (emailStr.startsWith("mailto:")) {
				emailStr = emailStr.substring("mailto:".length(), emailStr.length());
			}
		} catch (MalformedURLException e) {
			log.error(e.getMessage());
		}
		remoteResearchStaff.setFirstName(firstName);
		remoteResearchStaff.setLastName(lastName);
		
		// DO I NEED NCI ID ? 
		Ii ii = new Ii();
		ii.setExtension(externalId);
		try {
			IdentifiedPersonDTO identifiedPersonDTO = identifiedPersonCorrelationServiceRemote.getCorrelation(ii);
			remoteResearchStaff.setNciIdentifier(identifiedPersonDTO.getAssignedId().getExtension());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	
		ContactMechanism contactMechanismEmail = new ContactMechanism();
		contactMechanismEmail.setType(ContactMechanismType.EMAIL);
		contactMechanismEmail.setValue(emailStr);
		remoteResearchStaff.addContactMechanism(contactMechanismEmail);
		remoteResearchStaff.setUniqueIdentifier(externalId);
		
		//build org...or retrieve if pre-existing from database
		LocalHealthcareSite localHealthcareSite= new LocalHealthcareSite();
		localHealthcareSite.setNciInstituteCode(orgCtepId);
		localHealthcareSite.setName(orgCtepId);
		
		remoteResearchStaff.setHealthcareSite(localHealthcareSite)	;
		return remoteResearchStaff;
	}


	public void setClinicalResearchStaffCorrelationServiceRemote(
			ClinicalResearchStaffCorrelationServiceRemote clinicalResearchStaffCorrelationServiceRemote) {
		this.clinicalResearchStaffCorrelationServiceRemote = clinicalResearchStaffCorrelationServiceRemote;
	}

	public void setIdentifiedOrganizationCorrelationServiceRemote(
			IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote) {
		this.identifiedOrganizationCorrelationServiceRemote = identifiedOrganizationCorrelationServiceRemote;
	}

	public void setIdentifiedPersonCorrelationServiceRemote(
			IdentifiedPersonCorrelationServiceRemote identifiedPersonCorrelationServiceRemote) {
		this.identifiedPersonCorrelationServiceRemote = identifiedPersonCorrelationServiceRemote;
	}

	public void setPersonEntityServiceRemote(
			PersonEntityServiceRemote personEntityServiceRemote) {
		this.personEntityServiceRemote = personEntityServiceRemote;
	}

}