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
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum NotificationEventTypeEnum implements CodedEnum<String> {
	NEW_STUDY_SAVED_EVENT("NEW_STUDY_SAVED_EVENT"), 
	STUDY_STATUS_CHANGED_EVENT("STUDY_STATUS_CHANGED_EVENT"),
	NEW_STUDY_SITE_SAVED_EVENT("NEW_STUDY_SITE_SAVED_EVENT"), 
    STUDY_SITE_STATUS_CHANGED_EVENT("STUDY_SITE_STATUS_CHANGED_EVENT"), 
    NEW_REGISTRATION_EVENT("NEW_REGISTRATION_EVENT"),
    REGISTATION_STATUS_CHANGE("REGISTATION_STATUS_CHANGE"),
    SUBJECT_REMOVED_OFF_STUDY("SUBJECT_REMOVED_OFF_STUDY"),
    STUDY_ACCRUAL_EVENT("STUDY_ACCRUAL_EVENT"),
    STUDY_SITE_ACCRUAL_EVENT("STUDY_SITE_ACCRUAL_EVENT"),
    NEW_REGISTRATION_EVENT_REPORT("NEW_REGISTRATION_EVENT_REPORT"),
	MULTISITE_REGISTRATION_FAILURE("MULTISITE_REGISTRATION_FAILURE"),
    MASTER_SUBJECT_UPDATED_EVENT("MASTER_SUBJECT_UPDATED_EVENT"),
    CORRESPONDENCE_CREATED_OR_UPDATED_EVENT("CORRESPONDENCE_CREATED_OR_UPDATED_EVENT");
	
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
