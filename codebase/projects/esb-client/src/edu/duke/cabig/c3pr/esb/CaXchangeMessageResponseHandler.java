package edu.duke.cabig.c3pr.esb;

import gov.nih.nci.caxchange.Response;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 26, 2007
 * Time: 10:27:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface CaXchangeMessageResponseHandler {

    public void handleMessageResponse(String objectId, Response response);
}
