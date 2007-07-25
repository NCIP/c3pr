package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.*;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum RandomizationType  implements CodedEnum<Integer> {
	PHONE_CALL(1), BOOK(2), CALL_OUT(3);

	private int code;

	private RandomizationType(int code) {
		this.code = code;
		register(this);
	}

	public Integer getCode() {
		return code;
	}

	public String getDisplayName() {
		return name();
	}

	public String getName() {
		return name();
	}

	public static RandomizationType getByCode(int code) {
		return getByClassAndCode(RandomizationType.class, code);
	}

}
