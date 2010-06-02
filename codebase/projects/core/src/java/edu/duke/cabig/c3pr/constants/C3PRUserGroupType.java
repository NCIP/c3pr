package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 30, 2007 Time: 2:21:20 PM To change this template
 * use File | Settings | File Templates.
 */
public enum C3PRUserGroupType implements CodedEnum<String> {

    C3PR_ADMIN("c3pr_admin"), 
    STUDY_COORDINATOR("study_coordinator"), 
    SITE_COORDINATOR("site_coordinator"),
    //INTRODUCED AS A PART OF UNIFIED SECURITY - SUITE2.2
    SYSTEM_ADMINISTRATOR("system_administrator"),
    BUSINESS_ADMINISTRATOR("business_administrator"),
    PERSON_AND_ORGANIZATION_INFORMATION_MANAGER("person_and_organization_information_manager"),
    DATA_IMPORTER("data_importer"),
    USER_ADMINISTRATOR("user_administrator"),
	STUDY_QA_MANAGER("study_qa_manager"),
	STUDY_CREATOR("study_creator"),
	SUPPLEMENTAL_STUDY_INFORMATION_MANAGER("supplemental_study_information_manager"),
	STUDY_TEAM_ADMINISTRATOR("study_team_administrator"),
	STUDY_SITE_PARTICIPATION_ADMINISTRATOR("study_site_participation_administrator"),
	REGISTRATION_QA_MANAGER("registration_qa_manager"),
	SUBJECT_MANAGER("subject_manager"),
	REGISTRAR("registrar"),
	DATA_READER("data_reader"),
	DATA_ANALYST("data_analyst")
    ;

    private String code;

    private C3PRUserGroupType(String code) {
        this.code = code;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public static C3PRUserGroupType getByCode(String code) {
        return getByClassAndCode(C3PRUserGroupType.class, code);
    }

    public String getDisplayName() {
    	if (this.getCode().equals("c3pr_admin")){
    		return "C3PR admin";
    	}
        return sentenceCasedName(this);
    }
}
