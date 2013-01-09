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

public enum RegistrationWorkFlowStatus implements CodedEnum<String> {
   PENDING("Pending"),  RESERVED(
                    "Reserved"),OFF_STUDY("Off-Study"),  
                    PENDING_ON_STUDY("Pending On-Study"),ON_STUDY("On-Study") ,INVALID("Invalid"),
                    NOT_REGISTERED("Not Registered"),INELIGIBLE("Ineligible");
   
   // TODO
   // following to be removed with the appropriate data migration script
//  , UNREGISTERED("Unregistered"), DISAPPROVED("Disapproved"),REGISTERED("Registered"),
//   ,READY_FOR_REGISTRATION("Ready for registration")

    private String code;

    private RegistrationWorkFlowStatus(String code) {
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

    public static RegistrationWorkFlowStatus getByCode(String code) {
        return getByClassAndCode(RegistrationWorkFlowStatus.class, code);
    }
}
