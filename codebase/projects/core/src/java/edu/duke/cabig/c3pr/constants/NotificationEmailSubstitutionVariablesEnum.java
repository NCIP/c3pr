package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum NotificationEmailSubstitutionVariablesEnum implements CodedEnum<String> {
	//Study and studySite Status related
	COORDINATING_CENTER_STUDY_STATUS("Coordinating Center Study Status"),
	STUDY_SITE_STATUS("Study Site Status"),
	STUDY_ID("Primary Study Identifier"), 
	STUDY_SHORT_TITLE("Study Short Title"), 
	//Registration related
	REGISTRATION_WORKFLOW_STATUS("Registration Workflow Status"),
	PARTICIPANT_MRN("Participant MRN"),
	REGISTRATION_STATUS ("Registration Status");

    private String code;

    private NotificationEmailSubstitutionVariablesEnum(String code) {
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

    public static NotificationEmailSubstitutionVariablesEnum getByCode(String code) {
        return getByClassAndCode(NotificationEmailSubstitutionVariablesEnum.class, code);
    }
}