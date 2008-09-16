package edu.duke.cabig.c3pr.utils;

import java.util.Date;

import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseHandler;
import gov.nih.nci.caxchange.Response;

public abstract class CaXchangeMessageResponseHandlerImpl implements CaXchangeMessageResponseHandler {

    public void handleMessageResponse(String objectId, Response response) {
        gov.nih.nci.cabig.ctms.audit.DataAuditInfo.setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
                        "C3PR Admin", "CCTS Callback", new Date(), "CCTS Callback"));
        processResponse(objectId, response);
    }

    public abstract void processResponse(String objectId, Response response);
}
