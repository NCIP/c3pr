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

/**
 * Created by IntelliJ IDEA. User: kherm Date: Aug 27, 2007 Time: 12:23:36 PM To change this
 * template use File | Settings | File Templates.
 */
public enum AddressUse implements CodedEnum<String> {

    H("Home"), HP("Primary Home"), HV("Vacation Home"), WP("Work Place"), DIR("Direct"), PUB("Public"), BAD("Bad Adress"), TMP("Temporary Address"), 
    ABC("Alphabetic"), IDE("Ideographic"), SYL("Syllabic"), PHYS("Physical Visit Address"), PST("Postal Address"), SNDX("Soundex"), PHON("Phonetic");

    private String code;

    AddressUse(String code) {
        this.code = code;
        register(this);
    }

    public String getCode() {
        return code;
    }

    public static AddressUse getByCode(String code) {
        return getByClassAndCode(AddressUse.class, code);
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

}
