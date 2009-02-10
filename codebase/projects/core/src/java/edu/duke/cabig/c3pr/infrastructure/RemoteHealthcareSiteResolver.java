package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.domain.OrganizationDTO;
import com.semanticbits.coppasimulator.domain.ResearchStaffDTO;
import com.semanticbits.coppasimulator.service.OrganizationService;
import com.semanticbits.coppasimulator.service.ResearchStaffService;

import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;

/**
 * The Class RemoteResearchStaffResolver.
 */
public class RemoteHealthcareSiteResolver implements RemoteResolver{

//	ResearchStaffService researchStaffService;
	
	OrganizationService organizationService;
	
	public OrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	public List<Object> find(Object arg0) {
		RemoteHealthcareSite remoteHealthcareSite = (RemoteHealthcareSite) arg0;
		
		List<OrganizationDTO> organizationDTOList  = new ArrayList<OrganizationDTO>();
		
		try {
			organizationDTOList = organizationService.search(new OrganizationDTO());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Object> organizationList = convertToOrganization(organizationDTOList);
		
		return organizationList;
	}
	

	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private List<Object> convertToOrganization(List<OrganizationDTO> organizationDTOList){
		List<Object> remoteOrganizations = new ArrayList<Object>();
		RemoteHealthcareSite remoteOrganization;
		
		for(OrganizationDTO organizationDTO: organizationDTOList){
			remoteOrganization = new RemoteHealthcareSite();
			remoteOrganization.setName(organizationDTO.getName());
			remoteOrganization.setNciInstituteCode(organizationDTO.getNciIdentifier());
			remoteOrganizations.add(remoteOrganization);
		}
		
		return remoteOrganizations;
	}
	

}



