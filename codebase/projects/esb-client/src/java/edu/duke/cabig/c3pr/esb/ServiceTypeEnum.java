package edu.duke.cabig.c3pr.esb;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum ServiceTypeEnum implements CodedEnum<String> {
	PERSON("Person"), 
	ORGANIZATION("Organization"), 
	CLINICAL_RESEARCH_STAFF_CORRELATION("Clinical Research Staff Corelation"),
	CLINICAL_RESEARCH_STAFF("Clinical Research Staff"),
	HEALTH_CARE_FACILITY("Healthcare Facility"),
	RESEARCH_ORGANIZATION("Research Organization"),
	HEALTH_CARE_PROVIDER("Healthcare Provider"),
	IDENTIFIED_ORGANIZATION("Identified Organization"),
	IDENTIFIED_PERSON("Identified Person"),
	HEALTH_CARE_PROVIDER_CORRELATION("Healthcare Provider Correlation"),
	PERSON_BUSINESS_SERVICE("Person Business Service"),
	//PA
	ARM("Arm"),
	PLANNED_ACTIVITY("Planned Activity"),
	STUDY_CONTACT("Study Contact"),
	DOCUMENT("Document"),
	DOCUMENT_WORKFLOW_STATUS("Document Workflow Status"),
	STUDY_DISEASE("Study Disease"),
	STUDY_IND_IDE("Study Ind Ide"),
	STUDY_ONHOLD("Study Onhold"),
	STUDY_OUTCOME_MEASURE("Study Outcome Measure"),
	STUDY_OVERALL_STATUS("Study Overall Status"),
	STUDY_PARTICIPATION_CONTACT("Study Participation Contact"),
	STUDY_PARTICIPATION("Study Participation"),
	STUDY_PROTOCOL("Study Protocol"),	
	STUDY_RECRUITMENT_STATUS("Study Recruitment Status"),
	STUDY_REGULATORY_AUTHORITY("Study Regulatory Authority"),
	STUDY_RELATIONSHIP("Study Relationship"),
	STUDY_RESOURCING("Study Resourcing"),
	STUDY_SITE_ACCRUAL_STATUS("Study Site Accrual Status");

    private String code;

    private ServiceTypeEnum(String code) {
        this.code = code;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    public String getName() {
        return name();
    }

    public static ServiceTypeEnum getByCode(String code) {
        return getByClassAndCode(ServiceTypeEnum.class, code);
    }
}
