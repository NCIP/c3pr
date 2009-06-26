package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

/**
 * Enum class for the various COPPA status codes.
 * 
 * @author Vinay Gangoli
 */
public enum CoppaStatusCodeEnum implements CodedEnum<String> {
    
    /** The PENDING. */
    PENDING("Pending"), 
    
    /** The ACTIVE. */
    ACTIVE("Active"), 
    
    /** The INACTIVE. */
    INACTIVE("Inactive"),
    
    /** The NULLIFIED. */
    NULLIFIED("Nullified");

    /** The code. */
    private String code;

    /**
     * Instantiates a new coppa status code enum.
     * 
     * @param code the code
     */
    private CoppaStatusCodeEnum(String code) {
        this.code = code;
        register(this);
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.domain.CodedEnum#getCode()
     */
    public String getCode() {
        return code;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.cabig.ctms.domain.CodedEnum#getDisplayName()
     */
    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    /**
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name();
    }

    /**
     * Gets the by code.
     * 
     * @param code the code
     * 
     * @return the by code
     */
    public static CoppaStatusCodeEnum getByCode(String code) {
        return getByClassAndCode(CoppaStatusCodeEnum.class, code);
    }
}