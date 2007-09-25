package com.sb.servicemix.wsrf.bc;



import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.NormalizedMessage;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.ccts.grid.client.RegistrationConsumerClient;
import gov.nih.nci.ccts.grid.Registration;

import java.io.StringReader;
import java.rmi.RemoteException;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;
import org.apache.axis.types.URI;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jan 22, 2007
 * Time: 12:22:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class CTMSRegistrationProcessor implements MessageExchangeProcessor {


    public void process(MessageExchange exchange, DeliveryChannel channel,String epr) throws Exception{

        // If the MEP is an InOnly, RobustInOnly, you have to set the exchange to DONE status
        // else, you have to create an Out message and populate it


        NormalizedMessage in = exchange.getMessage("in");

        Registration registration =
                (Registration)Utils.deserializeObject(new StringReader(new SourceTransformer().contentToString(in)),Registration.class);

        System.out.println("Registration received with Grid ID " + registration.getGridId());

        String deliveryStatus=null;
        try {
            RegistrationConsumerClient client = new RegistrationConsumerClient(epr);
            client.register(registration);
            deliveryStatus = "delivered";
        } catch (Exception e) {
            deliveryStatus = "failed: " + e.getMessage();

          }

        NormalizedMessage confirmation = exchange.createMessage();
        confirmation.setContent(new StringSource("<status>" + deliveryStatus + " at "+epr +"<registrationID>"+ registration.getGridId()+"</registrationID></status>"));
        exchange.setMessage(confirmation, "out");
////        exchange.setStatus(javax.jbi.messaging.ExchangeStatus.DONE);
//        channel.send(exchange);
    }
}
