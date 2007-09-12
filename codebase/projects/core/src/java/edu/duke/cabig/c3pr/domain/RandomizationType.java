package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum RandomizationType  implements CodedEnum<Integer> {
	PHONE_CALL(1), BOOK(2), CALL_OUT(3);

	private Integer code;

	private RandomizationType(Integer code) {
		this.code = code;
		register(this);
	}

	public Integer getCode() {
		return code;
	}

	public String getDisplayName() {
		String name = getName();
		if(name.equals("PHONE_CALL")){
			return "Phone Call";
		}else if(name.equals("CALL_OUT")){
			return "Call Out";
		}else {
			return "Book";
		}
	}

	public String getName() {
		return name();
	}

	public static RandomizationType getByCode(Integer code) {
		return getByClassAndCode(RandomizationType.class, code);
	}

}
