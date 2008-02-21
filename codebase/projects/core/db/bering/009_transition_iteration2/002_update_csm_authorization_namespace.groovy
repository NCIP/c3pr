class UpdateCsmAuthorizationNamespace extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
	if (databaseMatches('postgres')) {
			execute("update csm_protection_element set protection_element_name='edu.duke.cabig.c3pr.domain.HealthcareSite' || '.' || split_part(protection_element_name,'.',8) where protection_element_id <= -5000");
			execute("update csm_protection_element set object_id='edu.duke.cabig.c3pr.domain.HealthcareSite' || '.' || split_part(object_id,'.',8) where protection_element_id <= -5000");
			execute("update csm_group set group_name='edu.duke.cabig.c3pr.domain.HealthcareSite' || '.' || split_part(group_name,'.',8) where group_id <= -5000");
			execute("update csm_protection_group set  protection_group_name='edu.duke.cabig.c3pr.domain.HealthcareSite' || '.' || split_part(protection_group_name,'.',8) where protection_group_id <= -5000");
		 }
	 
	 if (databaseMatches('oracle')) {
			execute("update csm_protection_element set protection_element_name = replace(protection_element_name,'gov.nih.nci.cabig.caaers.domain.Organization','edu.duke.cabig.c3pr.domain.HealthcareSite') where protection_element_id <= -5000");
			execute("update csm_protection_element set object_id = replace(object_id,'gov.nih.nci.cabig.caaers.domain.Organization','edu.duke.cabig.c3pr.domain.HealthcareSite') where protection_element_id <= -5000");
			execute("update csm_group set group_name = replace(group_name,'gov.nih.nci.cabig.caaers.domain.Organization','edu.duke.cabig.c3pr.domain.HealthcareSite')where group_id <= -5000");
			execute("update csm_protection_group set protection_group_name = replace(protection_group_name,'gov.nih.nci.cabig.caaers.domain.Organization','edu.duke.cabig.c3pr.domain.HealthcareSite') where protection_group_id <= -5000");
	 	}
		 
	}

	void down(){

	}
}