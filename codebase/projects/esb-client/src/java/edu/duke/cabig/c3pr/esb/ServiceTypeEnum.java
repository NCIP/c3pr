package edu.duke.cabig.c3pr.esb;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum ServiceTypeEnum implements CodedEnum<String> {
	PERSON("Person"), ORGANIZATION("Organization"), 
	CLINICAL_RESEARCH_STAFF_CORRELATION("Clinical Research Staff Corelation"),
	CLINICAL_RESEARCH_STAFF("Clinical Research Staff"),
	HEALTH_CARE_FACILITY("Healthcare Facility"),
	HEALTH_CARE_PROVIDER("Healthcare Provider"),
	IDENTIFIED_ORGANIZATION("Identified Organization"),
	HEALTH_CARE_PROVIDER_CORRELATION("Healthcare Provider Correlation"),
	PERSON_BUSINESS_SERVICE("Person Business Service");

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
