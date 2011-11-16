package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum ConsentRequired implements CodedEnum<String>{
	ONE("Atleast One"), ALL("All"), NONE("None"), AS_MARKED_BELOW("As Marked Below");
 
    private String code;

    ConsentRequired(String code) {
        this.code = code;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public static ConsentRequired getByCode(String code) {
        return getByClassAndCode(ConsentRequired.class, code);
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

	
}
