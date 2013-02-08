/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

public enum NCIRecognizedProgramName implements CodedEnum<String> {
	Cancer_Biology("Cancer Biology"), Molecular_Carcinogenesis("Molecular Carcinogenesis"),Nuclear_Receptors("Nuclear Receptors"),Breast_Cancer("Breast Cancer"),
	Cell_and_Gene_Therapy("Cell and Gene Therapy"),Prostate_Cancer("Prostate Cancer"),Cancer_Prevention("Cancer Prevention"),Pediatric_Cancer("Pediatric Cancer"),
	Unaligned("Unaligned");

    private String code;

    private NCIRecognizedProgramName(String code) {
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

    public static StatusType getByCode(String code) {
        return getByClassAndCode(StatusType.class, code);
    }
}
