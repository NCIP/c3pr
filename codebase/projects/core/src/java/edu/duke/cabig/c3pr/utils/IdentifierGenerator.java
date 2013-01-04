/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.List;
import java.util.UUID;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;

public class IdentifierGenerator {
	public static final String ASSIGNED_BY_PARAM_NAME="assignedBy";
	public static final String ASSIGNED_BY_ORG_VALUE="organization";
	public static final String ASSIGNED_BY_SYS_VALUE="system";
	public static final String SYSYEM_NAME_PARAM_NAME="systemName";
	public static final String ORG_NCI_PARAM_NAME="organizationNciId";
	public static final String IDENTIFIER_VALUE_PARAM_NAME="identifier";
	public static final String IDENTIFIER_TYPE_PARAM_NAME="identifierType";
	
	private StudyDao studyDao ;
	
	public OrganizationAssignedIdentifier generateOrganizationAssignedIdentifier(StudySubject studySubject){
		OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
		orgIdentifier.setHealthcareSite(studySubject.getStudySite().getStudy().getCoordinatingCenterAssignedIdentifier().getHealthcareSite());
		orgIdentifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_ASSIGNED_STUDY_SUBJECT_IDENTIFIER);
		List<StudySubject> studySubjects = studyDao.getStudySubjectsForStudy(studySubject.getStudySite().getStudy().getId());
		int count = 1 ;
		for(StudySubject s : studySubjects){
			if (s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.ON_STUDY ||
					s.getRegWorkflowStatus() == RegistrationWorkFlowStatus.OFF_STUDY) {
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
		sysIdentifier.setType(OrganizationIdentifierTypeEnum.SUBJECT_IDENTIFIER.toString());
		sysIdentifier.setValue(UUID.randomUUID().toString());
		return sysIdentifier;
	}
	
	 public static String createParameterString(Identifier identifier){
	    	if (identifier instanceof OrganizationAssignedIdentifier) {
				OrganizationAssignedIdentifier organizationAssignedIdentifier = (OrganizationAssignedIdentifier) identifier;
				 return ASSIGNED_BY_PARAM_NAME+"="+ASSIGNED_BY_ORG_VALUE+"&"+ORG_NCI_PARAM_NAME+"="+organizationAssignedIdentifier.getHealthcareSite().getPrimaryIdentifier()
						+"&"+IDENTIFIER_TYPE_PARAM_NAME+"="+identifier.getTypeInternal()+"&"+IDENTIFIER_VALUE_PARAM_NAME+"="+identifier.getValue();
			}
	    	SystemAssignedIdentifier systemAssignedIdentifier=(SystemAssignedIdentifier)identifier;
	    	return ASSIGNED_BY_PARAM_NAME+"="+ASSIGNED_BY_SYS_VALUE+"&"+SYSYEM_NAME_PARAM_NAME+"="+systemAssignedIdentifier.getSystemName()
			+"&"+IDENTIFIER_TYPE_PARAM_NAME+"="+identifier.getTypeInternal()+"&"+IDENTIFIER_VALUE_PARAM_NAME+"="+identifier.getValue();
	    }

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

}
