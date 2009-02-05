package edu.duke.cabig.c3pr.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.semanticbits.coppa.infrastructure.service.RemoteResolver;
import com.semanticbits.coppasimulator.domain.ResearchStaffDTO;
import com.semanticbits.coppasimulator.service.ResearchStaffService;

import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;

// TODO: Auto-generated Javadoc
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
		
		ResearchStaffDTO researchStaffDTO = researchStaffService.getClinicalResearchStaffPerson("LPage@nci.org");
		List<ResearchStaffDTO> researchStaffDTOList  = new ArrayList<ResearchStaffDTO>();
		researchStaffDTOList.add(researchStaffDTO);
		
		List<Object> researchStaffList = convertToResearchStaff(researchStaffDTOList);
		
		return researchStaffList;
	}

	/* (non-Javadoc)
	 * @see com.semanticbits.coppa.infrastructure.service.RemoteResolver#getRemoteEntityByUniqueId(java.lang.String)
	 */
	public Object getRemoteEntityByUniqueId(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	
	private List<Object> convertToResearchStaff(List<ResearchStaffDTO> researchStaffDTOList){
		List<Object> researchStaffList = new ArrayList<Object>();
		RemoteResearchStaff remoteResearchStaff;
		for(ResearchStaffDTO researchStaffDTO: researchStaffDTOList){
			remoteResearchStaff = new RemoteResearchStaff();
			remoteResearchStaff.setFirstName(researchStaffDTO.getFirstName());
			remoteResearchStaff.setLastName(researchStaffDTO.getLastName());
			remoteResearchStaff.setNciIdentifier(researchStaffDTO.getNciIdentifier());
			
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



