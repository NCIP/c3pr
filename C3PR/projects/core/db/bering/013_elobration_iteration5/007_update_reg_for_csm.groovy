class UpdateRegForCsm extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("update CSM_PROTECTION_GROUP set PROTECTION_GROUP_NAME='edu.duke.cabig.c3pr.domain.StudySubject' where PROTECTION_GROUP_ID='3'")
	
			execute("update csm_protection_element set protection_element_name='edu.duke.cabig.c3pr.domain.StudySubject' where protection_element_id='5'")
			execute("update csm_protection_element set object_id='edu.duke.cabig.c3pr.domain.StudySubject' where protection_element_id='5'")
			
			execute("update CSM_ROLE set role_name='edu.duke.cabig.c3pr.domain.StudySubject.CREATE' where role_id='7'")
			execute("update CSM_ROLE set role_name='edu.duke.cabig.c3pr.domain.StudySubject.UPDATE' where role_id='8'")
			execute("update CSM_ROLE set role_name='edu.duke.cabig.c3pr.domain.StudySubject.READ' where role_id='9'")
	}

	void down(){
	
	}
}
