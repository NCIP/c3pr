package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 30, 2007 Time: 2:21:20 PM To change this template
 * use File | Settings | File Templates.
 */
public enum C3PRUserGroupType implements CodedEnum<String> {

//    C3PR_ADMIN("c3pr_admin"), 
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
	DATA_ANALYST("data_analyst"),
    AE_RULE_AND_REPORT_MANAGER("ae_rule_and_report_manager"),
    STUDY_CALENDAR_TEMPLATE_BUILDER("study_calendar_template_builder"),
    STUDY_SUBJECT_CALENDAR_MANAGER("study_subject_calendar_manager"),
    AE_REPORTER("ae_reporter"),
    AE_EXPEDITED_REPORT_REVIEWER("ae_expedited_report_reviewer"),
    AE_STUDY_DATA_REVIEWER("ae_study_data_reviewer"),
    LAB_IMPACT_CALENDAR_NOTIFIER("lab_impact_calendar_notifier"),
    LAB_DATA_USER("lab_data_user")
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
    
    public String getName() {
        return name();
    }
    
	public static SuiteRole getUnifiedSuiteRole(C3PRUserGroupType groupType) {
		
		switch(groupType){
			case SYSTEM_ADMINISTRATOR: return SuiteRole.SYSTEM_ADMINISTRATOR;
			case BUSINESS_ADMINISTRATOR: return SuiteRole.BUSINESS_ADMINISTRATOR;
			case PERSON_AND_ORGANIZATION_INFORMATION_MANAGER: return SuiteRole.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER;
			case DATA_IMPORTER: return SuiteRole.DATA_IMPORTER;
			case USER_ADMINISTRATOR: return SuiteRole.USER_ADMINISTRATOR;
			case STUDY_QA_MANAGER: return SuiteRole.STUDY_QA_MANAGER;
			case STUDY_CREATOR: return SuiteRole.STUDY_CREATOR;
			case SUPPLEMENTAL_STUDY_INFORMATION_MANAGER: return SuiteRole.SUPPLEMENTAL_STUDY_INFORMATION_MANAGER;
			case STUDY_TEAM_ADMINISTRATOR: return SuiteRole.STUDY_TEAM_ADMINISTRATOR;
			case STUDY_SITE_PARTICIPATION_ADMINISTRATOR: return SuiteRole.STUDY_SITE_PARTICIPATION_ADMINISTRATOR;
			case REGISTRATION_QA_MANAGER: return SuiteRole.REGISTRATION_QA_MANAGER;
			case SUBJECT_MANAGER: return SuiteRole.SUBJECT_MANAGER;
			case REGISTRAR: return SuiteRole.REGISTRAR;
			case DATA_READER: return SuiteRole.DATA_READER;
			case DATA_ANALYST: return SuiteRole.DATA_ANALYST;
			case AE_RULE_AND_REPORT_MANAGER: return SuiteRole.AE_RULE_AND_REPORT_MANAGER;
			case STUDY_CALENDAR_TEMPLATE_BUILDER: return SuiteRole.STUDY_CALENDAR_TEMPLATE_BUILDER;
			case STUDY_SUBJECT_CALENDAR_MANAGER: return SuiteRole.STUDY_SUBJECT_CALENDAR_MANAGER;
			case AE_REPORTER: return SuiteRole.AE_REPORTER;
			case AE_EXPEDITED_REPORT_REVIEWER: return SuiteRole.AE_EXPEDITED_REPORT_REVIEWER;
			case AE_STUDY_DATA_REVIEWER: return SuiteRole.AE_STUDY_DATA_REVIEWER;
			case LAB_IMPACT_CALENDAR_NOTIFIER: return SuiteRole.LAB_IMPACT_CALENDAR_NOTIFIER;
			case LAB_DATA_USER: return SuiteRole.LAB_DATA_USER;
			default: return null; 
		}
		
	}

}
