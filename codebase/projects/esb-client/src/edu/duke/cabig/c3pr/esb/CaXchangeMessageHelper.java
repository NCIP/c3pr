package edu.duke.cabig.c3pr.esb;

import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Request;
import org.apache.axis.message.MessageElement;
import org.w3c.dom.Document;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 15, 2007
 * Time: 11:32:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class CaXchangeMessageHelper {

    public static Message createXchangeMessage(Document dom) {
        //formulate caXchange message
        MessageElement messageElement = new MessageElement(dom.getDocumentElement());
        MessagePayload payload = new MessagePayload();
        payload.set_any(new MessageElement[]{messageElement});
        Request request = new Request();
        request.setBusinessMessagePayload(payload);
        Message message = new Message();
        message.setRequest(request);

        return message;
    }
}
