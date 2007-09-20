package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum SiteStudyStatus  implements CodedEnum<String> {
	ACTIVE("Active"), PENDING("Pending"), AMENDMENT_PENDING("Amendment Pending"), 
	CLOSED_TO_ACCRUAL_AND_TREATMENT("Closed To Accrual And Treatment"),CLOSED_TO_ACCRUAL("Closed To Accrual"),
	TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT("Temporarily Closed To Accrual And Treatment"),
	TEMPORARILY_CLOSED_TO_ACCRUAL("Temporarily Closed To Accrual") ;

	private String code;

	private SiteStudyStatus(String code) {
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

	public static SiteStudyStatus getByCode(String code) {
		return getByClassAndCode(SiteStudyStatus.class, code);
	}
}
