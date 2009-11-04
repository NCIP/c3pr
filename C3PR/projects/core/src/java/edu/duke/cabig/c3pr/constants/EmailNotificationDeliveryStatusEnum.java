package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum EmailNotificationDeliveryStatusEnum implements CodedEnum<String> {
    COMPLETE("Complete"), PENDING("Pending"), RETRY("Retry"), ERROR ("Error");

    private String code;

    private EmailNotificationDeliveryStatusEnum(String code) {
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

    public static EmailNotificationDeliveryStatusEnum getByCode(String code) {
        return getByClassAndCode(EmailNotificationDeliveryStatusEnum.class, code);
    }
}