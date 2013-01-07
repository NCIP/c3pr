package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum FamilialRelationshipName implements CodedEnum<String> {
	FATHER("Father"),MOTHER("Mother"),SIBLING("Sibling");

    private String code;

    private FamilialRelationshipName(String code) {
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

    public static FamilialRelationshipName getByCode(String code) {
        return getByClassAndCode(FamilialRelationshipName.class, code);
    }
}