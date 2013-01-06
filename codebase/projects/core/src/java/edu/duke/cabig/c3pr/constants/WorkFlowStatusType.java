package edu.duke.cabig.c3pr.constants;

import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.getByClassAndCode;
import static gov.nih.nci.cabig.ctms.domain.CodedEnumHelper.register;
import static gov.nih.nci.cabig.ctms.domain.EnumHelper.sentenceCasedName;
import gov.nih.nci.cabig.ctms.domain.CodedEnum;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 19, 2007 Time: 2:35:15 PM To change this template
 * use File | Settings | File Templates.
 */
public enum WorkFlowStatusType implements CodedEnum<String> {

    MESSAGE_SEND("Message sent"), MESSAGE_SEND_CONFIRMED("Message successfully sent"), MESSAGE_SEND_FAILED(
                    "Message send failed"), MESSAGE_RECIEVED("Message has been receved"),
                    MESSAGE_REPLY_CONFIRMED("Message reply confirmed"), MESSAGE_REPLY_FAILED("Message failed while replying to the registration request"),
                    MESSAGE_RESPONSE_RECIEVED("Message response recieved"), MESSAGE_ACK_FAILED("Message acknowledgment failed");

    private String code;

    WorkFlowStatusType(String code) {
        this.code = code;
        register(this);

    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return sentenceCasedName(this);
    }

    public static WorkFlowStatusType getByCode(String code) {
        return getByClassAndCode(WorkFlowStatusType.class, code);
    }

}
