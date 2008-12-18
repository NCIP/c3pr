package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum RegistrationWorkFlowStatus implements CodedEnum<String> {
   PENDING("Pending"),  RESERVED(
                    "Reserved"),  OFF_STUDY("Off-Study"),  
                    REGISTERED_BUT_NOT_ENROLLED("Registered but not enrolled"),ENROLLED("enrolled") 
                    // TODO
                    // following to be removed with the appropriate data migration script
//                   , UNREGISTERED("Unregistered"), DISAPPROVED("Disapproved"),REGISTERED("Registered"),INVALID(
//                    "Invalid"),READY_FOR_REGISTRATION("Ready for registration")
                    ;

    private String code;

    private RegistrationWorkFlowStatus(String code) {
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

    public static RegistrationWorkFlowStatus getByCode(String code) {
        return getByClassAndCode(RegistrationWorkFlowStatus.class, code);
    }
}
