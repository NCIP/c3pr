package edu.duke.cabig.c3pr.domain;

import org.apache.jackrabbit.uuid.UUID;

public class IdentifierGenerator {
	
	public static OrganizationAssignedIdentifier generateOrganizationAssignedIdentifier(StudySubject studySubject){
		OrganizationAssignedIdentifier orgIdentifier = new OrganizationAssignedIdentifier();
		orgIdentifier.setHealthcareSite(studySubject.getStudySite().getStudy().getCoordinatingCenterAssignedIdentifier().getHealthcareSite());
		orgIdentifier.setType("Coordinating Center Assigned Study Subject Identifier");
		orgIdentifier.setValue(UUID.randomUUID().toString());
		return orgIdentifier;
	}
	
	public static SystemAssignedIdentifier generateSystemAssignedIdentifier(StudySubject studySubject){
		SystemAssignedIdentifier sysIdentifier = new SystemAssignedIdentifier();
		sysIdentifier.setSystemName("C3PR");
		sysIdentifier.setType("Study Subject Identifier");
		sysIdentifier.setValue(UUID.randomUUID().toString());
		return sysIdentifier;
	}

}
