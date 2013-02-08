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

public enum CoordinatingCenterStudyStatus implements CodedEnum<String> {
    READY_TO_OPEN("Ready to open"), OPEN("Open"), PENDING("Pending"), CLOSED_TO_ACCRUAL_AND_TREATMENT(
                    "Closed To Accrual And Treatment"), CLOSED_TO_ACCRUAL("Closed To Accrual"), TEMPORARILY_CLOSED_TO_ACCRUAL_AND_TREATMENT(
                    "Temporarily Closed To Accrual And Treatment"), TEMPORARILY_CLOSED_TO_ACCRUAL(
                    "Temporarily Closed To Accrual");

    private String code;

    private CoordinatingCenterStudyStatus(String code) {
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

    public static CoordinatingCenterStudyStatus getByCode(String code) {
        return getByClassAndCode(CoordinatingCenterStudyStatus.class, code);
    }
}
