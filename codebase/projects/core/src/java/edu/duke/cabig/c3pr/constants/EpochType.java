/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum EpochType implements CodedEnum<String>{
	RESERVING("Reserving"), SCREENING("Screening"), TREATMENT("Treatment"), FOLLOWUP("Follow-up"),
	OBSERVATION("Observation"), OTHER("Other");

	private String code;

	private EpochType(String code) {
		this.code = code;
		register(this);
	}

	public String getCode() {
		return code;
	}

	public static EpochType getByCode(String code) {
		return getByClassAndCode(EpochType.class, code);
	}

	public String getDisplayName() {
		return sentenceCasedName(this);
	}
}
