package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum RoleTypes implements CodedEnum<String>{
	C3PR_ADMIN("ROLE_c3pr_admin"), 
	STUDY_COORDINATOR("ROLE_study_coordinator"), 
	SITE_COORDINATOR("ROLE_site_coordinator"),
	//INTRODUCED AS A PART OF UNIFIED SECURITY - SUITE2.2
    SYSTEM_ADMINISTRATOR("ROLE_system_administrator"),
    BUSINESS_ADMINISTRATOR("ROLE_business_administrator"),
    PERSON_AND_ORGANIZATION_INFORMATION_MANAGER("ROLE_person_and_organization_information_manager"),
    DATA_IMPORTER("ROLE_data_importer"),
    USER_ADMINISTRATOR("ROLE_user_administrator"),
	STUDY_QA_MANAGER("ROLE_study_qa_manager"),
	STUDY_CREATOR("ROLE_study_creator"),
	SUPPLEMENTAL_STUDY_INFORMATION_MANAGER("ROLE_supplemental_study_information_manager"),
	STUDY_TEAM_ADMINISTRATOR("ROLE_study_team_administrator"),
	STUDY_SITE_PARTICIPATION_ADMINISTRATOR("ROLE_study_site_participation_administrator"),
	REGISTRATION_QA_MANAGER("ROLE_registration_qa_manager"),
	SUBJECT_MANAGER("ROLE_subject_manager"),
	REGISTRAR("ROLE_registrar"),
	DATA_READER("ROLE_data_reader"),
	DATA_ANALYST("ROLE_data_analyst");

	private String code;

	private RoleTypes(String code) {
		this.code = code;
		register(this);
	}

	public String getCode() {
		return code;
	}

	public static RoleTypes getByCode(String code) {
		return getByClassAndCode(RoleTypes.class, code);
	}

	public String getDisplayName() {
		return sentenceCasedName(this);
	}
}
