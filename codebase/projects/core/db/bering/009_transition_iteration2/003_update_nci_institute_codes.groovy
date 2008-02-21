class UpdateNciInstituteCodes extends edu.northwestern.bioinformatics.bering.Migration {
	void up() {
			execute("update organizations set nci_institute_code='DUKE' where nci_institute_code='Duke'");
			execute("update csm_group set group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.DUKE' where group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.Duke'");
			execute("update csm_protection_element set protection_element_name='edu.duke.cabig.c3pr.domain.HealthcareSite.DUKE',object_id='edu.duke.cabig.c3pr.domain.HealthcareSite.DUKE' where protection_element_name='edu.duke.cabig.c3pr.domain.HealthcareSite.Duke'");
			execute("update csm_protection_group set  protection_group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.DUKE' where protection_group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.Duke'");
			
			execute("update organizations set nci_institute_code='WAKE' where nci_institute_code='Wake'");
			execute("update csm_group set group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.WAKE' where group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.Wake'");
			execute("update csm_protection_element set protection_element_name='edu.duke.cabig.c3pr.domain.HealthcareSite.WAKE',object_id='edu.duke.cabig.c3pr.domain.HealthcareSite.WAKE' where protection_element_name='edu.duke.cabig.c3pr.domain.HealthcareSite.Wake'");
			execute("update csm_protection_group set  protection_group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.WAKE' where protection_group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.Wake'");
			
			execute("update organizations set nci_institute_code='unkown or does not exist' where nci_institute_code='Warren'");
			execute("update csm_group set group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.unkown or does not exist' where group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.Warren'");
			execute("update csm_protection_element set protection_element_name='edu.duke.cabig.c3pr.domain.HealthcareSite.unkown or does not exist',object_id='edu.duke.cabig.c3pr.domain.HealthcareSite.unkown or does not exist' where protection_element_name='edu.duke.cabig.c3pr.domain.HealthcareSite.Warren'");
			execute("update csm_protection_group set  protection_group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.unkown or does not exist' where protection_group_name='edu.duke.cabig.c3pr.domain.HealthcareSite.Warren'");
	}

	void down(){

	}
}