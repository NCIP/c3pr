package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum ServiceName implements CodedEnum<String> {
    STUDY("edu.duke.cabig.c3pr.grid.client.StudyServiceClient"), REGISTRATION("edu.duke.cabig.c3pr.grid.client.RegistrationServiceClient");

    private String code;

    private ServiceName(String code) {
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

    public static ServiceName getByCode(String code) {
        return getByClassAndCode(ServiceName.class, code);
    }
}
