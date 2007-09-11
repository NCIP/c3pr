package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum RegistrationWorkFlowStatus  implements CodedEnum<String> {
	UNREGISTERED("Unregistered"), PENDING("Pending"), DISAPPROVED("Disapproved"), RESERVED("Reserved"), REGISTERED("Registered"), OFF_STUDY("Off-Study"), INVALID("Invalid") ;

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
