package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum APIName implements CodedEnum<String> {
    CREATE_STUDY("createStudy", "Create Study Defination"), OPEN_STUDY("openStudy", "Open Study"), APPROVE_STUDY_SITE_FOR_ACTIVATION("approveStudySiteForActivation", "Approve Study Site for Activation"),
    ACTIVATE_STUDY_SITE("activateStudySite", "Activate Study Site"), AMEND_STUDY("amendStudy", "Amend Study"), UDATE_STUDY_SITE_PROTOCOL_VERSION("updateStudySiteProtocolVersion", "Update Study Site Protocol Version"),
    CLOSE_STUDY("closeStudy", "Close Study"), UDATE_STUDY_STATUS("updateStudyStatus", "Update Study Status"), CLOSE_STUDY_SITE("closeStudySite", "Close Study Site"),
    CLOSE_STUDY_SITES("closeStudySites", "Close Study Sites"), ENROLL_SUBJECT("enroll","Enroll subject"), PUT_SUBJECT_OFF_STUDY("offStudy","Put subject off study"),
    CHANGE_EPOCH("transfer", "Change subject's epoch");

    private String code;
    private String displayName;

    private APIName(String code, String displayName) {
        this.code = code;
        this.displayName=displayName;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getName() {
        return name();
    }

    public static APIName getByCode(String code) {
        return getByClassAndCode(APIName.class, code);
    }
}
