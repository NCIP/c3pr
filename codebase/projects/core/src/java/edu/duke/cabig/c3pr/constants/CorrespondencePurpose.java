package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum CorrespondencePurpose implements CodedEnum<String> {
	PURPOSE_1("Purpose 1"), PURPOSE_2("Purpose 2"),PURPOSE_3("Purpose 3"),PURPOSE_4("Purpose 4"),OTHER("Other");

    private String code;

    private CorrespondencePurpose(String code) {
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

    public static CorrespondencePurpose getByCode(String code) {
        return getByClassAndCode(CorrespondencePurpose.class, code);
    }
}