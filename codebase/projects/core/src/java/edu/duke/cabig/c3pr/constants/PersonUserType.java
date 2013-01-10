/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

/**
 * The Enum PersonUserType. Determines whether the PersonUser is a staff, user etc
 * 
 * @author Vinay G
 */
public enum PersonUserType implements CodedEnum<String>{
	STAFF("Staff"), USER("User"), STAFF_USER("Staff_User");

	private String code;

	private PersonUserType(String code) {
		this.code = code;
		register(this);
	}

	public String getCode() {
		return code;
	}

	public static PersonUserType getByCode(String code) {
		return getByClassAndCode(PersonUserType.class, code);
	}

	public String getDisplayName() {
		return sentenceCasedName(this);
	}
}
