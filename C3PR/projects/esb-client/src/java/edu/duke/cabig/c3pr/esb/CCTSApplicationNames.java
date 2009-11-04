package edu.duke.cabig.c3pr.esb;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 19, 2007 Time: 2:35:15 PM To change this template
 * use File | Settings | File Templates.
 */
public enum CCTSApplicationNames implements CodedEnum<String> {

    CAXCHANGE("Clinical Trials Enterprise Service Bus"), CAAERS("Adverse Event Reporting System"), PSC(
                    "Patient Study Calendar"), C3D("Cancer Central Clinical Database"),
                    CTODS("Clinical Trials Object Data System"), C3PR("Cancer Central Clinical Participant Registry");

    private String code;

    CCTSApplicationNames(String code) {
        this.code = code;
        register(this);

    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    public static CCTSApplicationNames getByCode(String code) {
        return getByClassAndCode(CCTSApplicationNames.class, code);
    }

}
