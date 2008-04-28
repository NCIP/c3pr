package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum RaceCode implements CodedEnum<String> {
	
	Asian("Asian"), 
	White("White"),
	Black_or_African_American("Black or African American"),
	American_Indian_or_Alaska_Native("American Indian or Alaska Native"),
	Native_Hawaiian_or_Pacific_Islander("Native Hawaiian or Pacific Islander"),
	Not_Reported("Not Reported"),
	Unknown("Unknown");
	
	private String raceCode;

    private RaceCode(String code) {
        this.raceCode = code;
        register(this);
    }

    public String getCode() {
        return raceCode;
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    public String getName() {
        return name();
    }

    public static RaceCode getByCode(String code) {
        return getByClassAndCode(RaceCode.class, code);
    }

}
