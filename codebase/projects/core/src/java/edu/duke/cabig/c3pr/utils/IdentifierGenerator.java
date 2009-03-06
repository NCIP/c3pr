package edu.duke.cabig.c3pr.utils;

import java.util.List;

import org.apache.jackrabbit.uuid.UUID;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class IdentifierGenerator {
	
	private StudyDao studyDao ;
	
	public OrganizationAssignedIdentifier generateOrganizationAssignedIdentifier(StudySubject studySubject){
		OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
		orgIdentifier.setHealthcareSite(studySubject.getStudySite().getStudy().getCoordinatingCenterAssignedIdentifier().getHealthcareSite());
		orgIdentifier.setType("Coordinating Center Assigned Study Subject Identifier");
		List<StudySubject> studySubjects = studyDao.getStudySubjectsForStudy(studySubject.getStudySite().getStudy().getId());
		int count = 1 ;
		for(StudySubject s : studySubjects){
			if (s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.ENROLLED || s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED
                    || s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED) {
				count++;
			}
		}
		orgIdentifier.setValue(Integer.toString(count));
		return orgIdentifier;
	}
	
	public SystemAssignedIdentifier generateSystemAssignedIdentifier(StudySubject studySubject){
		SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
		sysIdentifier.setSystemName("C3PR");
		sysIdentifier.setType("Study Subject Identifier");
		sysIdentifier.setValue(UUID.randomUUID().toString());
		return sysIdentifier;
	}

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

}
