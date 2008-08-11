package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum NotificationEventTypeEnum implements CodedEnum<String> {
    STUDY_STATUS_CHANGED_EVENT("STUDY_STATUS_CHANGED_EVENT"), 
    SUBJECT_REMOVED_OFF_STUDY("SUBJECT_REMOVED_OFF_STUDY"), 
    NEW_REGISTRATION("NEW_REGISTRATION");

    private String code;

    private NotificationEventTypeEnum(String code) {
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

    public static NotificationEventTypeEnum getByCode(String code) {
        return getByClassAndCode(NotificationEventTypeEnum.class, code);
    }
}