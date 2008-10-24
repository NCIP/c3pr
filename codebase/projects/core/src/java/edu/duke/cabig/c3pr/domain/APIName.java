package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum APIName implements CodedEnum<String> {
    CREATE_STUDY("createStudy"), OPEN_STUDY("openStudy"), APPROVE_STUDY_SITE_FOR_ACTIVATION("approveStudySiteForActivation"),
    ACTIVATE_STUDY_SITE("activateStudySite"), AMEND_STUDY("amendStudy"), UDATE_STUDY_SITE_PROTOCOL_VERSION("updateStudySiteProtocolVersion"),
    CLOSE_STUDY("closeStudy"), UDATE_STUDY_STATUS("updateStudyStatus"), CLOSE_STUDY_SITE("closeStudySite"),
    CLOSE_STUDY_SITES("closeStudySites");

    private String code;

    private APIName(String code) {
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

    public static APIName getByCode(String code) {
        return getByClassAndCode(APIName.class, code);
    }
}
