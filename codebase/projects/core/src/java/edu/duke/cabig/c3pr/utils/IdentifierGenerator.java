package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.jackrabbit.uuid.UUID;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class IdentifierGenerator {
	
	private StudyDao studyDao ;
	
	public OrganizationAssignedIdentifier generateOrganizationAssignedIdentifier(StudySubject studySubject){
		OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
		orgIdentifier.setHealthcareSite(studySubject.getStudySite().getStudy().getCoordinatingCenterAssignedIdentifier().getHealthcareSite());
		orgIdentifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER);
		List<StudySubject> studySubjects = new ArrayList<StudySubject>();
		if(studySubject.getParentStudySubject() == null ){
			studySubjects = studyDao.getStudySubjectsForStudy(studySubject.getStudySite().getStudy().getId());
		}else{
			studySubjects = studyDao.getStudySubjectsForCompanionStudy(studySubject.getStudySite().getStudy().getId());
		}
		
		int count = 1 ;
		for(StudySubject s : studySubjects){
			if (s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.ENROLLED) {
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
	
	public SystemAssignedIdentifier generateSystemAssignedIdentifier(Participant participant){
		SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
		sysIdentifier.setSystemName("C3PR");
		sysIdentifier.setType("Participant Identifier");
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
