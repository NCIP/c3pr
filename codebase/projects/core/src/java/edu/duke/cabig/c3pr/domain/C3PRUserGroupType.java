package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.CodedEnum;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 30, 2007
 * Time: 2:21:20 PM
 * To change this template use File | Settings | File Templates.
 */
public enum C3PRUserGroupType implements CodedEnum<String> {

    C3PR_ADMIN("c3pr_admin"),
    STUDY_COORDINATOR("study_coordinator"),
    REGISTRAR("registrar"),
    SITE_COORDINATOR("site_coordinator");

    private String code;


    private C3PRUserGroupType(String code) {
        this.code = code;
        register(this);
    }


    public String getCode() {
        return code;
    }

    public static C3PRUserGroupType getByCode(String code) {
        return getByClassAndCode(C3PRUserGroupType.class, code);
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }
}
