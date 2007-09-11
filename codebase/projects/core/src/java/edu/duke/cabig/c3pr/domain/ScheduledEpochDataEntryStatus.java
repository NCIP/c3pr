package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum ScheduledEpochDataEntryStatus  implements CodedEnum<String> {
	INCOMPLETE("Incomplete"), COMPLETE("Complete");

	private String code;

	private ScheduledEpochDataEntryStatus(String code) {
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

	public static ScheduledEpochDataEntryStatus getByCode(String code) {
		return getByClassAndCode(ScheduledEpochDataEntryStatus.class, code);
	}
}
