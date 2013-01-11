/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
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
public enum ContactMechanismUse implements CodedEnum<String> {

    H("Home"), HP("Primary Home"), HV("Vacation Home"), WP("Work Place"), DIR("Direct"), PUB("Public"), BAD("Bad Adress"), TMP("Temporary Address"), 
    AS("Answering Service"), EC("Emergency Contact"), MC("Mobile Contact"), PG("Pager");

    private String code;

    ContactMechanismUse(String code) {
        this.code = code;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public static ContactMechanismUse getByCode(String code) {
        return getByClassAndCode(ContactMechanismUse.class, code);
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

}
