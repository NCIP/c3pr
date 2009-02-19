package edu.duke.cabig.c3pr.infrastructure;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import gov.nih.nci.coppa.iso.AddressPartType;
import gov.nih.nci.coppa.iso.Adxp;
import gov.nih.nci.coppa.iso.Enxp;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;

// TODO: Auto-generated Javadoc
/**
 * The Class RemoteHealthcareSiteResolver.
 */
public class RemoteHealthcareSiteResolver implements RemoteResolver{

	/** The organization entity service remote. */
	private OrganizationEntityServiceRemote organizationEntityServiceRemote;
	
	/** The identified organization correlation service remote. */
	private IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote;

	
	/**
	 * Convert to organization.
	 * 
	 * @param organizationDto the organization dto
	 * 
	 * @return the list< object>
	 */
	
	
	public RemoteHealthcareSite populateRemoteOrganization(OrganizationDTO organizationDto){
		IdentifiedOrganizationDTO identifiedOrganizationDTO = null;
		try {
			identifiedOrganizationDTO = identifiedOrganizationCorrelationServiceRemote.getCorrelation(organizationDto.getIdentifier());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RemoteHealthcareSite remoteHealthcareSite = new RemoteHealthcareSite();
		Iterator<Enxp> enxpItr = organizationDto.getName().getPart().iterator();
		Enxp next = enxpItr.next();
		List<Adxp> postalAddress = organizationDto.getPostalAddress().getPart();
		Iterator<Adxp> adxpItr = postalAddress.iterator();
		String city = "";
		String country = "";
		while(adxpItr.hasNext()){
			Adxp adXp = adxpItr.next();
			if(AddressPartType.CTY.equals(adXp.getType())){
				city = adXp.getValue();
			}

			if(AddressPartType.CNT.equals(adXp.getType())){
				country = adXp.getCode();
			}
		}
		
		Address remoteOrgAddress = new Address();

		remoteHealthcareSite.setName(next.getValue().toString());
		remoteOrgAddress.setCity(city);
		remoteOrgAddress.setCountryCode(country);	
		remoteHealthcareSite.setAddress(remoteOrgAddress);
		remoteHealthcareSite.setNciInstituteCode(identifiedOrganizationDTO.getAssignedId().getExtension());	
		remoteHealthcareSite.setExternalId(organizationDto.getIdentifier().getExtension());
		return remoteHealthcareSite;
	}
	
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String externalId) {
		OrganizationDTO organizationDto = null;
		Ii ii = new Ii();
		ii.setExtension(externalId);
		try {
			organizationDto = organizationEntityServiceRemote.getOrganization(ii);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return populateRemoteOrganization(organizationDto);
	}
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<Object> find(Object example) {	
		Organization remoteOrgExample = (RemoteHealthcareSite)example;
//		 build Organization DTO based on remoteOrgExample
		OrganizationDTO dtoExample = new OrganizationDTO();
		List<OrganizationDTO> organizationDTOs = organizationEntityServiceRemote.search(dtoExample);
		
		
		List<Object> remoteOrganizations = new ArrayList<Object>();
		for (OrganizationDTO organizationDTO:organizationDTOs) {
			Organization remoteOrganization = populateRemoteOrganization(organizationDTO);
			remoteOrganizations.add(remoteOrganization);
		}
		return remoteOrganizations;
	}
	
	/**
	 * Sets the identified organization correlation service remote.
	 * 
	 * @param identifiedOrganizationCorrelationServiceRemote the new identified organization correlation service remote
	 */
	public void setIdentifiedOrganizationCorrelationServiceRemote(
			IdentifiedOrganizationCorrelationServiceRemote identifiedOrganizationCorrelationServiceRemote) {
		this.identifiedOrganizationCorrelationServiceRemote = identifiedOrganizationCorrelationServiceRemote;
	}

	/**
	 * Sets the organization entity service remote.
	 * 
	 * @param organizationEntityServiceRemote the new organization entity service remote
	 */
	public void setOrganizationEntityServiceRemote(
			OrganizationEntityServiceRemote organizationEntityServiceRemote) {
		this.organizationEntityServiceRemote = organizationEntityServiceRemote;
	}

}



