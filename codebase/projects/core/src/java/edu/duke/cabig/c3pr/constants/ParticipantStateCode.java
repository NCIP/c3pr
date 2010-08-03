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
public enum ParticipantStateCode implements CodedEnum<String> {

	ACTIVE("ACTIVE"), INACTIVE("INACTIVE");

	private String code;

	private ParticipantStateCode(String code) {
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

	public static ParticipantStateCode getByCode(String code) {
		return getByClassAndCode(ParticipantStateCode.class, code);
	}

}