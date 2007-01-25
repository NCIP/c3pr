package com.sb.servicemix.wsrf.bc;

import gov.nih.nci.cagrid.common.Utils;

import gov.nih.nci.cabig.ctms.createcandidateadverseeventservice.client.CreateCandidateAdverseEventServiceClient;

import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.NormalizedMessage;
import java.io.StringReader;

import org.apache.servicemix.jbi.jaxp.SourceTransformer;
import org.apache.servicemix.jbi.jaxp.StringSource;

import gov.nih.nci.caaers.grid.beans.AdverseEvent;


/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jan 25, 2007
 * Time: 4:14:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class CTMSCreateCandidateAdverseEventProcessor implements MessageExchangeProcessor {


    public void process(MessageExchange exchange, DeliveryChannel channel, String epr) throws Exception {

        // If the MEP is an InOnly, RobustInOnly, you have to set the exchange to DONE status
        // else, you have to create an Out message and populate it

        NormalizedMessage in = exchange.getMessage("in");

        AdverseEvent adverseEventMessage = (AdverseEvent)
                Utils.deserializeObject(new StringReader(new SourceTransformer().contentToString(in)),AdverseEvent.class);

        System.out.println("Adverse Event received for Study with ID " + adverseEventMessage.getStudySubjectAssignment().getStudy().getIdentifier());

        String deliveryStatus=null;
        try {
            CreateCandidateAdverseEventServiceClient client = new CreateCandidateAdverseEventServiceClient(epr);
            client.processMessage(adverseEventMessage);
            deliveryStatus = "delivered";
        } catch (Exception e) {
             deliveryStatus = "failed: " + e.getMessage();

          }

        NormalizedMessage confirmation = exchange.createMessage();
        confirmation.setContent(new StringSource("<status>" + deliveryStatus + " at "+epr +"<studyID>"+ adverseEventMessage.getStudySubjectAssignment().getStudy().getIdentifier()+"</studyID></status>"));
        exchange.setMessage(confirmation, "out");
    }
}
