package com.sb.servicemix.wsrf.bc;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cabig.ctms.grid.ae.client.AdverseEventConsumerClient;
import gov.nih.nci.cabig.ctms.grid.ae.beans.AENotificationType;


import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.NormalizedMessage;
import java.io.StringReader;
import java.io.FileInputStream;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.globus.gsi.GlobusCredential;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jan 25, 2007
 * Time: 12:53:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class CTMSAdverseEventProcessor implements MessageExchangeProcessor {


    public void process(MessageExchange exchange, DeliveryChannel channel, String epr) throws Exception {
        // If the MEP is an InOnly, RobustInOnly, you have to set the exchange to DONE status
        // else, you have to create an Out message and populate it


        NormalizedMessage in = exchange.getMessage("in");

        AENotificationType ae = (AENotificationType)
                Utils.deserializeObject(new StringReader(new SourceTransformer().contentToString(in)),AENotificationType.class);

        System.out.println("Adverse event received for Registration Grid ID " + ae.getRegistrationGridId());

        String deliveryStatus=null;
        try {
           // String proxyFile = "~/.globus/certificates/proxy.txt";
            //GlobusCredential cred = new GlobusCredential(new FileInputStream(proxyFile));
            AdverseEventConsumerClient client = new AdverseEventConsumerClient(epr);
            client.register(ae);

            deliveryStatus = "delivered Adverse Event";
        } catch (Exception e) {
           deliveryStatus = "failed: " + e.getMessage();

        }

        NormalizedMessage confirmation = exchange.createMessage();
        confirmation.setContent(new StringSource("<status>" + deliveryStatus + " at "+epr +"<registrationID>"+ ae.getRegistrationGridId()+"</registrationID></status>"));
        exchange.setMessage(confirmation, "out");


    }
}
