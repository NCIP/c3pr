package edu.duke.cabig.c3pr.esb;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum OperationNameEnum implements CodedEnum<String> {
	NA("NA"), 
	getPerson("getPerson"), 
	getById("getById"),
	createPerson("createPerson"),
	validate("validate"),
	search("search"),
	updatePerson("updatePerson"),
	updatePersonStatus("updatePersonStatus"),
	getOrganization("getOrganization"),
	createOrganization("createOrganization"),
	updateOrganization("updateOrganization"),
	updateOrganizationStatus("updateOrganizationStatus"),
	getCorrelation("getCorrelation"),
	getCorrelations("getCorrelations"),
	createCorrelation("createCorrelation"),
	updateCorrelation("updateCorrelation"),
	updateCorrelationStatus("updateCorrelationStatus");

    private String code;

    private OperationNameEnum(String code) {
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

    public static OperationNameEnum getByCode(String code) {
        return getByClassAndCode(OperationNameEnum.class, code);
    }
}
