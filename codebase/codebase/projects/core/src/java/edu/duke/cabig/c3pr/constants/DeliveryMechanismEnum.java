package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum DeliveryMechanismEnum implements CodedEnum<String> {
    EMAIL("Email"), FAX("Fax"), PAGER("Pager");

    private String code;

    private DeliveryMechanismEnum(String code) {
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

    public static DeliveryMechanismEnum getByCode(String code) {
        return getByClassAndCode(DeliveryMechanismEnum.class, code);
    }
}