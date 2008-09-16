package edu.duke.cabig.c3pr.domain;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 19, 2007 Time: 2:35:15 PM To change this template
 * use File | Settings | File Templates.
 */
public enum CCTSWorkflowStatusType implements CodedEnum<String> {

    MESSAGE_SEND("Message sent"), MESSAGE_SEND_CONFIRMED("Message send confirmed"), MESSAGE_SEND_FAILED(
                    "Message send failed"), MESSAGE_RECIEVED("Message has been receved"),
                    MESSAGE_REPLY_CONFIRMED("Message reply confirmed"), MESSAGE_REPLY_FAILED("Message failed while replying to the registration request"),
                    MESSAGE_RESPONSE_RECIEVED("Message response recieved");

    private String code;

    CCTSWorkflowStatusType(String code) {
        this.code = code;
        register(this);

    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    public static CCTSWorkflowStatusType getByCode(String code) {
        return getByClassAndCode(CCTSWorkflowStatusType.class, code);
    }

}
