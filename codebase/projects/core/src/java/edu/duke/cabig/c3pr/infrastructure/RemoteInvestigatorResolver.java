package edu.duke.cabig.c3pr.infrastructure;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;

import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.coppa.iso.Util.IiConverter;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

public class RemoteInvestigatorResolver implements RemoteResolver{
	
	private static Logger logger = Logger.getLogger(RemoteInvestigatorResolver.class);

	private IdentifiedOrganizationCorrelationServiceRemote  identifiedOrganizationCorrelationServiceRemote;
	private HealthCareProviderCorrelationServiceRemote healthCareProviderCorrelationServiceRemote;
	private PersonEntityServiceRemote personEntityServiceRemote;
	private IdentifiedPersonCorrelationServiceRemote identifiedPersonCorrelationServiceRemote;

	
	public Investigator populateRemoteInvestigator(PersonDTO personDTO,String orgCtepId,String coppaOrgId){
		RemoteInvestigator remoteInvestigator = new RemoteInvestigator();
		Iterator<Enxp> enxpItr = personDTO.getName().getPart().iterator();
        Enxp next = enxpItr.next();
        String firstName = next.getValue();
        next = enxpItr.next();
        String lastName = next.getValue();
        
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
			logger.error(e.getMessage());
		}
		remoteInvestigator.setFirstName(firstName);
		remoteInvestigator.setLastName(lastName);
		
		// DO I NEED NCI ID ? 
		Ii ii = new Ii();
		ii.setExtension(externalId);
		try {
			IdentifiedPersonDTO identifiedPersonDTO = identifiedPersonCorrelationServiceRemote.getCorrelation(ii);
			remoteInvestigator.setNciIdentifier(identifiedPersonDTO.getAssignedId().getExtension());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	
		ContactMechanism contactMechanism = new ContactMechanism();
		contactMechanism.setType(ContactMechanismType.EMAIL);
		contactMechanism.setValue(emailStr);
		remoteInvestigator.addContactMechanism(contactMechanism);
		
		remoteInvestigator.setUniqueIdentifier(externalId);
		//build org
		HealthcareSite healthcareSite = new RemoteHealthcareSite();
		healthcareSite.setNciInstituteCode(orgCtepId);
		// XXXXX TO DO ... SITE INV ...
		//remoteInvestigator.a.setOrganization(organization)	;
		return remoteInvestigator;
	}
	/**
	 * Find By Organization
	 */
	public List<Object> find(Object example) {
		List<Object> remoteInvestigators = new ArrayList<Object>();
		
		Investigator remoteInvestigatorExample = (RemoteInvestigator)example;
		//organization ctep-id
		HealthcareSite healthcareSite = null;
		if(remoteInvestigatorExample.getHealthcareSiteInvestigators().size() > 0){
			healthcareSite = remoteInvestigatorExample.getHealthcareSiteInvestigators().get(0).getHealthcareSite();
		}
		//if organization is null get all research staffs
		if (healthcareSite == null) {
			PersonDTO personDTO = new PersonDTO();
			Ii ii = new Ii();
			ii.setRoot(IiConverter.HEALTH_CARE_PROVIDER_ROOT);
			personDTO.setIdentifier(ii);
			
			List<PersonDTO> allPersons = personEntityServiceRemote.search(personDTO);
			//need to get organization for each person . 
			
			for (PersonDTO person:allPersons) {
				String ctepOrgId = "";
				String coppaOrgId = "";
				try {
					HealthCareProviderDTO ctDto = healthCareProviderCorrelationServiceRemote.getCorrelation(person.getIdentifier()); //player id
					Ii scoperId = ctDto.getScoperIdentifier();
					IdentifiedOrganizationDTO organizationDto = identifiedOrganizationCorrelationServiceRemote.getCorrelation(scoperId);
					ctepOrgId = organizationDto.getAssignedId().getExtension();		
					coppaOrgId = organizationDto.getPlayerIdentifier().getExtension();
					
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
				if(person != null){
					remoteInvestigators.add(this.populateRemoteInvestigator(person,ctepOrgId,coppaOrgId));
				}
			}
			return remoteInvestigators;
		}
		String ctepOrgId = healthcareSite.getNciInstituteCode();
		//build IdentifiedOrganizationDTO
		IdentifiedOrganizationDTO idOrgDto = new IdentifiedOrganizationDTO();
		Ii ii = new Ii();
		ii.setExtension(ctepOrgId);
		ii.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
		idOrgDto.setAssignedId(ii);
		// get List of Organizations , in this case it should be only one ...
		List <IdentifiedOrganizationDTO> idOrgDtos = identifiedOrganizationCorrelationServiceRemote.search(idOrgDto);
		IdentifiedOrganizationDTO identifiedOrganizationDTO = idOrgDtos.get(0);
		// grab coppa-id of organization from above object
		Ii playerId = identifiedOrganizationDTO.getPlayerIdentifier();
		
		// using above id get investigators
		HealthCareProviderDTO crDto = new HealthCareProviderDTO();
		crDto.setScoperIdentifier(playerId);
		List<HealthCareProviderDTO> investigators = healthCareProviderCorrelationServiceRemote.search(crDto);
		
		//now we need to get the details of each person from person service .
		
		for (HealthCareProviderDTO investigator:investigators) {
			Ii crPlayerId = investigator.getPlayerIdentifier();
			try {
				PersonDTO personDTO = personEntityServiceRemote.getPerson(crPlayerId);
				Investigator remoteResearchStaff = populateRemoteInvestigator(personDTO,ctepOrgId,playerId.getExtension());
				remoteInvestigators.add(remoteResearchStaff);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		/*
		List<ResearchStaffDTO> ResearchStaffDTOs = 
			researchStaffRemoteService.searchClinicalResearchStaffPerson(remoteResearchStaffExample.getEmailAddress());
		for (ResearchStaffDTO researchStaffDTO:ResearchStaffDTOs) {
			ResearchStaff remoteResearchStaff = populateRemoteResearchStaff(researchStaffDTO);
			remoteResearchStaffs.add(remoteResearchStaff);
		}*/
		return remoteInvestigators;
	}

	public Object getRemoteEntityByUniqueId(String externalId) {
		Ii ii = new Ii();
		ii.setExtension(externalId);
		PersonDTO person = null;
		String ctepOrgId = "";
		String coppaOrgId = "";
		try {
			person = personEntityServiceRemote.getPerson(ii);
			HealthCareProviderDTO ctDto = healthCareProviderCorrelationServiceRemote.getCorrelation(person.getIdentifier()); //player id
			Ii scoperId = ctDto.getScoperIdentifier();
			IdentifiedOrganizationDTO organizationDto = identifiedOrganizationCorrelationServiceRemote.getCorrelation(scoperId);
			ctepOrgId = organizationDto.getAssignedId().getExtension();	
			coppaOrgId = organizationDto.getPlayerIdentifier().getExtension();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(person != null){
			return this.populateRemoteInvestigator(person,ctepOrgId,coppaOrgId);
		} else{
			return null;
		}
		
	}



	public void setIdentifiedOrganizationCorrelationServiceRemote(
			IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote) {
		this.identifiedOrganizationCorrelationServiceRemote = identifiedOrganizationCorrelationServiceRemote;
	}
	public void setPersonEntityServiceRemote(
			PersonEntityServiceRemote personEntityServiceRemote) {
		this.personEntityServiceRemote = personEntityServiceRemote;
	}
	public void setIdentifiedPersonCorrelationServiceRemote(
			IdentifiedPersonCorrelationServiceRemote identifiedPersonCorrelationServiceRemote) {
		this.identifiedPersonCorrelationServiceRemote = identifiedPersonCorrelationServiceRemote;
	}
	public void setHealthCareProviderCorrelationServiceRemote(
			HealthCareProviderCorrelationServiceRemote healthCareProviderCorrelationServiceRemote) {
		this.healthCareProviderCorrelationServiceRemote = healthCareProviderCorrelationServiceRemote;
	}

}