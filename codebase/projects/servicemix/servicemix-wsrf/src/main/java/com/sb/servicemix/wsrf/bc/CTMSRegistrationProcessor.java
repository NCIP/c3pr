package com.sb.servicemix.wsrf.bc;



import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.NormalizedMessage;

import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.cabig.ctms.client.RegistrationConsumerClient;

import java.io.StringReader;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;

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

        gov.nih.nci.cabig.ctms.grid.RegistrationType registration = (gov.nih.nci.cabig.ctms.grid.RegistrationType)
                Utils.deserializeObject(new StringReader(new SourceTransformer().contentToString(in)),gov.nih.nci.cabig.ctms.grid.RegistrationType.class);

        System.out.println("Registration received with Grid ID " + registration.getStudyGridId());

        RegistrationConsumerClient client = new RegistrationConsumerClient(epr);
        client.register(registration);
        
        NormalizedMessage confirmation = exchange.createMessage();
        confirmation.setContent(new StringSource("<xml>delivered</xml>"));
        exchange.setMessage(confirmation, "out");
        channel.send(exchange);

    }

}
