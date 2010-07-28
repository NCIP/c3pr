package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

/**
 * Defines state codes for Subject as per Subject Management PSM.
 * 
 * @author dkrylov
 * 
 */
public enum SubjectStateCode implements CodedEnum<String> {

	ACTIVE("Active"), INACTIVE("Inactive");

	private String code;

	private SubjectStateCode(String code) {
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

	public static SubjectStateCode getByCode(String code) {
		return getByClassAndCode(SubjectStateCode.class, code);
	}

}
