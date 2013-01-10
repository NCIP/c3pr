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

public enum ICD9DiseaseSiteCodeDepth implements CodedEnum<String> {
    LEVEL1("level 1"), LEVEL2("level 2"),LEVEL3("level 3"), LEVEL4("level 4");

    private String code;

    private ICD9DiseaseSiteCodeDepth(String code) {
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

    public static ICD9DiseaseSiteCodeDepth getByCode(String code) {
        return getByClassAndCode(ICD9DiseaseSiteCodeDepth.class, code);
    }
}
