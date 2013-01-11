/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum StudySubjectDisplayPageEnum implements CodedEnum<String>{
	
	STUDY_DETAIL("Study Details"), STUDY_DESIGN("Study Design"), STUDY_ELIGIBILITY("Eligibility"), STUDY_STRATIFICATION("Stratification"),
	STUDY_RANDOMIZATION("Randomization"), STUDY_DISEASES("Diseases"),STUDY_SITES("Study Sites");
	
	private String code;

    private StudySubjectDisplayPageEnum(String code) {
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

    public static StudySubjectDisplayPageEnum getByCode(String code) {
        return getByClassAndCode(StudySubjectDisplayPageEnum.class, code);
    }
}
