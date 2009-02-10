package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.domain.ResearchStaffDTO;
import com.semanticbits.coppasimulator.service.ResearchStaffService;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;

/**
 * The Class RemoteResearchStaffResolver.
 */
public class RemoteResearchStaffResolver implements RemoteResolver{

	ResearchStaffService researchStaffService;
	
	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#find(java.lang.Object)
	 */
	public List<Object> find(Object arg0) {
		RemoteResearchStaff remoteResearchStaff = (RemoteResearchStaff) arg0;
		
		List<ResearchStaffDTO> researchStaffDTOList  = new ArrayList<ResearchStaffDTO>();
		researchStaffDTOList.add(researchStaffService.getClinicalResearchStaffPerson("SBine@nci.org"));
		researchStaffDTOList.add(researchStaffService.getClinicalResearchStaffPerson("DTrump@nci.org"));
		
		//pass the healthcareSite tht was set in the exmaple object that was sent in....otherwise null
		return convertToResearchStaff(researchStaffDTOList, remoteResearchStaff.getHealthcareSite());
	}

	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String arg0) {
		Object object = researchStaffService.getClinicalResearchStaffPerson(arg0);
		List<ResearchStaffDTO> researchStaffDTOList = new ArrayList<ResearchStaffDTO>();
		researchStaffDTOList.add((ResearchStaffDTO)object);
		return convertToResearchStaff(researchStaffDTOList, null).get(0);
	}

	
	private List<Object> convertToResearchStaff(List<ResearchStaffDTO> researchStaffDTOList, HealthcareSite healthcareSite){
		List<Object> researchStaffList = new ArrayList<Object>();
		RemoteResearchStaff remoteResearchStaff;
		for(ResearchStaffDTO researchStaffDTO: researchStaffDTOList){
			remoteResearchStaff = new RemoteResearchStaff();
			remoteResearchStaff.setFirstName(researchStaffDTO.getFirstName());
			remoteResearchStaff.setLastName(researchStaffDTO.getLastName());
			remoteResearchStaff.setNciIdentifier(researchStaffDTO.getNciIdentifier());
			//set the healthcare site that was passed in
			remoteResearchStaff.setHealthcareSite(healthcareSite);
			
			remoteResearchStaff.setUniqueIdentifier(researchStaffDTO.getEmailAddress());
			researchStaffList.add(remoteResearchStaff);
		}
		
		return researchStaffList;
	}
	
	public ResearchStaffService getResearchStaffService() {
		return researchStaffService;
	}

	public void setResearchStaffService(ResearchStaffService researchStaffService) {
		this.researchStaffService = researchStaffService;
	}


}



