package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum ConsentingMethod implements CodedEnum<String>{
	WRITTEN("Written"), VERBAL("Verbal");

	private String code;

	private ConsentingMethod(String code) {
		this.code = code;
		register(this);
	}

	public String getCode() {
		return code;
	}

	public static ConsentingMethod getByCode(String code) {
		return getByClassAndCode(ConsentingMethod.class, code);
	}
	
	public String getName() {
	        return name();
	    }

	public String getDisplayName() {
		return sentenceCasedName(this);
	}
}
