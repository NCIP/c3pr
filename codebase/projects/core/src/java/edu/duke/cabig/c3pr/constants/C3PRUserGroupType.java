/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.cabig.ctms.domain.CodedEnum;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 30, 2007 Time: 2:21:20 PM To change this template
 * use File | Settings | File Templates.
 */
public enum C3PRUserGroupType implements CodedEnum<String> {

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
    	if(sentenceCasedName(this).contains("ae")){
    		return sentenceCasedName(this).replace("ae", "AE");
    	}else if(sentenceCasedName(this).contains("Ae")){
    		return sentenceCasedName(this).replace("Ae", "AE");
    	}else if(sentenceCasedName(this).contains("qa")){
    		return sentenceCasedName(this).replace("qa", "QA");
    	}else if(sentenceCasedName(this).contains("Qa")){
    		return sentenceCasedName(this).replace("Qa", "QA");
    	}else{
    		return sentenceCasedName(this);	
    	}
    }
    
    public String getName() {
        return name();
    }
    
    public SuiteRole getUnifiedSuiteRole() {
		return C3PRUserGroupType.getUnifiedSuiteRole(this);
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
	
	public static List<C3PRUserGroupType> getStudyScopedRoles(){
		List<C3PRUserGroupType> studyScopedRoles = new ArrayList<C3PRUserGroupType>();

		studyScopedRoles.add(C3PRUserGroupType.REGISTRAR);
		studyScopedRoles.add(C3PRUserGroupType.DATA_ANALYST);
		studyScopedRoles.add(C3PRUserGroupType.DATA_READER);
		
		studyScopedRoles.add(C3PRUserGroupType.AE_STUDY_DATA_REVIEWER);
		studyScopedRoles.add(C3PRUserGroupType.AE_EXPEDITED_REPORT_REVIEWER);
		studyScopedRoles.add(C3PRUserGroupType.AE_REPORTER);
		
		studyScopedRoles.add(C3PRUserGroupType.LAB_DATA_USER);
		studyScopedRoles.add(C3PRUserGroupType.LAB_IMPACT_CALENDAR_NOTIFIER);
		
		studyScopedRoles.add(C3PRUserGroupType.STUDY_CALENDAR_TEMPLATE_BUILDER);
		studyScopedRoles.add(C3PRUserGroupType.STUDY_SUBJECT_CALENDAR_MANAGER);
		
		return studyScopedRoles;
	}
	
	public String getRoleDescription() {
		switch(this){
			case SYSTEM_ADMINISTRATOR: return SuiteRole.SYSTEM_ADMINISTRATOR.getDescription();
			case BUSINESS_ADMINISTRATOR: return SuiteRole.BUSINESS_ADMINISTRATOR.getDescription();
			case PERSON_AND_ORGANIZATION_INFORMATION_MANAGER: return SuiteRole.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER.getDescription();
			case DATA_IMPORTER: return SuiteRole.DATA_IMPORTER.getDescription();
			case USER_ADMINISTRATOR: return SuiteRole.USER_ADMINISTRATOR.getDescription();
			case STUDY_QA_MANAGER: return SuiteRole.STUDY_QA_MANAGER.getDescription();
			case STUDY_CREATOR: return SuiteRole.STUDY_CREATOR.getDescription();
			case SUPPLEMENTAL_STUDY_INFORMATION_MANAGER: return SuiteRole.SUPPLEMENTAL_STUDY_INFORMATION_MANAGER.getDescription();
			case STUDY_TEAM_ADMINISTRATOR: return SuiteRole.STUDY_TEAM_ADMINISTRATOR.getDescription();
			case STUDY_SITE_PARTICIPATION_ADMINISTRATOR: return SuiteRole.STUDY_SITE_PARTICIPATION_ADMINISTRATOR.getDescription();
			case REGISTRATION_QA_MANAGER: return SuiteRole.REGISTRATION_QA_MANAGER.getDescription();
			case SUBJECT_MANAGER: return SuiteRole.SUBJECT_MANAGER.getDescription();
			case REGISTRAR: return SuiteRole.REGISTRAR.getDescription();
			case DATA_READER: return SuiteRole.DATA_READER.getDescription();
			case DATA_ANALYST: return SuiteRole.DATA_ANALYST.getDescription();
			case AE_RULE_AND_REPORT_MANAGER: return SuiteRole.AE_RULE_AND_REPORT_MANAGER.getDescription();
			case STUDY_CALENDAR_TEMPLATE_BUILDER: return SuiteRole.STUDY_CALENDAR_TEMPLATE_BUILDER.getDescription();
			case STUDY_SUBJECT_CALENDAR_MANAGER: return SuiteRole.STUDY_SUBJECT_CALENDAR_MANAGER.getDescription();
			case AE_REPORTER: return SuiteRole.AE_REPORTER.getDescription();
			case AE_EXPEDITED_REPORT_REVIEWER: return SuiteRole.AE_EXPEDITED_REPORT_REVIEWER.getDescription();
			case AE_STUDY_DATA_REVIEWER: return SuiteRole.AE_STUDY_DATA_REVIEWER.getDescription();
			case LAB_IMPACT_CALENDAR_NOTIFIER: return SuiteRole.LAB_IMPACT_CALENDAR_NOTIFIER.getDescription();
			case LAB_DATA_USER: return SuiteRole.LAB_DATA_USER.getDescription();
			default: return ""; 
		}
	}
	
	public boolean getIsScoped(){
		return this.getUnifiedSuiteRole().isScoped();
	}
	
	public boolean getIsSiteScoped(){
		return this.getUnifiedSuiteRole().isSiteScoped();
	}
	
	public boolean getIsStudyScoped(){
		return this.getUnifiedSuiteRole().isStudyScoped();
	}

}
