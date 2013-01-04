/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;


public enum StudyPart implements CodedEnum<String>{
	DETAIL("Detail"),CONSENT("Consent"), DESIGN("Design"), STRATIFICATION("Stratification"), ELIGIBILITY("Eligibility"), DISEASE("Disease"),
	RANDOMIZATION("Randomization"),COMPANION("Companion");

    private String code;

    StudyPart(String code) {
        this.code = code;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public static StudyPart getByCode(String code) {
        return getByClassAndCode(StudyPart.class, code);
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    public String getName() {
        return name();
    }

}
