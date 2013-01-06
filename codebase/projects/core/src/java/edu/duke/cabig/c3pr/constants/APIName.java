package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum APIName implements CodedEnum<String> {
    ACTIVATE_STUDY_SITE("activateStudySite", "Activate Study Site"), 
    CLOSE_STUDY_SITE_TO_ACCRUAL("closeStudySiteToAccrual", "Close Study Site To Accrual"),
    CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT("closeStudySiteToAccrualAndTreatment", "Close Study Site To Accrual And Treatement"),
    TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL("temporarilyCloseStudySiteToAccrual", "Temporarily Close Study Site To Accrual And Treatment"),
    TEMPORARILY_CLOSE_STUDY_SITE_TO_ACCRUAL_AND_TREATMENT("temporarilyCloseStudySiteToAccrualAndTreatment", "Temporarily Close Study Site To Accrual And Treatement"),
    ENROLL_SUBJECT("enroll","Enroll subject"), 
    PUT_SUBJECT_OFF_STUDY("offStudy","Put subject off study"),
    GET_REGISTRATIONS("getRegistrations", "Fetch Registrations"),
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
