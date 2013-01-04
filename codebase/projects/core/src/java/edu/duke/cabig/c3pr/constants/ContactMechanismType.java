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

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 27, 2007 Time: 12:23:36 PM To change this
 * template use File | Settings | File Templates.
 */
public enum ContactMechanismType implements CodedEnum<String> {

    PHONE("Phone"), EMAIL("Email"), Fax("Fax"), OTHER("Other");

    private String code;

    ContactMechanismType(String code) {
        this.code = code;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public static ContactMechanismType getByCode(String code) {
        return getByClassAndCode(ContactMechanismType.class, code);
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

}
