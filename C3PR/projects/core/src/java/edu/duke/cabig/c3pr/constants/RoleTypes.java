package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum RoleTypes implements CodedEnum<String>{
	C3PR_ADMIN("ROLE_c3pr_admin"), STUDY_COORDINATOR("ROLE_study_coordinator"), REGISTRAR(
			"ROLE_registrar"), SITE_COORDINATOR("ROLE_site_coordinator");

	private String code;

	private RoleTypes(String code) {
		this.code = code;
		register(this);
	}

	public String getCode() {
		return code;
	}

	public static RoleTypes getByCode(String code) {
		return getByClassAndCode(RoleTypes.class, code);
	}

	public String getDisplayName() {
		return sentenceCasedName(this);
	}
}