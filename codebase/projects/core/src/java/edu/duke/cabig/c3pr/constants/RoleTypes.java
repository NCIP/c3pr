/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum RoleTypes implements CodedEnum<String>{

	SYSTEM_ADMINISTRATOR("ROLE_system_administrator", "System Admin."),
    BUSINESS_ADMINISTRATOR("ROLE_business_administrator", "Business Admin."),
    PERSON_AND_ORGANIZATION_INFORMATION_MANAGER("ROLE_person_and_organization_information_manager", "P&O Info. Manager"),
    DATA_IMPORTER("ROLE_data_importer", "Data Importer"),
    USER_ADMINISTRATOR("ROLE_user_administrator","User Administrator"),
	STUDY_QA_MANAGER("ROLE_study_qa_manager", "Study QA Manager"),
	STUDY_CREATOR("ROLE_study_creator", "Study Creator"),
	SUPPLEMENTAL_STUDY_INFORMATION_MANAGER("ROLE_supplemental_study_information_manager", "Supplemental Study Info. Manager"),
	STUDY_TEAM_ADMINISTRATOR("ROLE_study_team_administrator","Study Team Admin."),
	STUDY_SITE_PARTICIPATION_ADMINISTRATOR("ROLE_study_site_participation_administrator", "Study Site Participation Admin."),
	REGISTRATION_QA_MANAGER("ROLE_registration_qa_manager", "Registration QA Manager"),
	SUBJECT_MANAGER("ROLE_subject_manager", "Subject Manager"),
	REGISTRAR("ROLE_registrar", "Registrar"),
	DATA_READER("ROLE_data_reader", "Data Reader"),
	DATA_ANALYST("ROLE_data_analyst", "Data Analyst"),
	AE_RULE_AND_REPORT_MANAGER("ROLE_ae_rule_and_report_manager", "AE Rule and Report Manager"),
    STUDY_CALENDAR_TEMPLATE_BUILDER("ROLE_study_calendar_template_builder", "Study Calendar Template Builder"),
    STUDY_SUBJECT_CALENDAR_MANAGER("ROLE_study_subject_calendar_manager", "Study Subject Calendar Manager"),
    AE_REPORTER("ROLE_ae_reporter", "AE Reporter"),
    AE_EXPEDITED_REPORT_REVIEWER("ROLE_ae_expedited_report_reviewer", "AE Expedited Report Reviewer"),
    AE_STUDY_DATA_REVIEWER("ROLE_ae_study_data_reviewer", "AE Study Data Reviewer"),
    LAB_IMPACT_CALENDAR_NOTIFIER("ROLE_lab_impact_calendar_notifier", "Lab Impact Calendar Notifier"),
    LAB_DATA_USER("ROLE_lab_data_user", "Lab Data User")
	;

	private String code;
    private String displayName;

    private RoleTypes(String code, String displayName) {
        this.code = code;
        this.displayName=displayName;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getName() {
        return name();
    }

    public static RoleTypes getByCode(String code) {
        return getByClassAndCode(RoleTypes.class, code);
    }
}
