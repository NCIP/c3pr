/**
 * 
 */
package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

/**
 * @author himanshu
 *
 */
public enum ConsentRequired implements CodedEnum<String> {
	ONE("One"), ALL("All"), NONE("None");

	  private String code;

	    private ConsentRequired(String code) {
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

	    public static ConsentRequired getByCode(String code) {
	        return getByClassAndCode(ConsentRequired.class, code);
	    }

}