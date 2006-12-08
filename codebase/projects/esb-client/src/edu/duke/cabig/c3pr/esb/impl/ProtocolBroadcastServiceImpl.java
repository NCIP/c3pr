package edu.duke.cabig.c3pr.esb.impl;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.esb.AbstractJmsService;
import edu.duke.cabig.c3pr.esb.ProtocolBroadcastService;
import edu.duke.cabig.c3pr.util.XMLBuilder;

public class ProtocolBroadcastServiceImpl extends AbstractJmsService implements ProtocolBroadcastService{

	public void broadcast(Study study) {
		// TODO Auto-generated method stub
		System.out.println("calling xml builder util...");
		String xml=new XMLBuilder().buildCreateProtocolXML(study);
		System.out.println("calling sendJms method...");		
		sendJms(xml);
	}

	public void getBroadcastStatus() {
		// TODO Auto-generated method stub
		
	}

	public void sendJms(String xml) {
		Connection connection = null;
        Session session = null;
        MessageProducer producer = null;


        /*
         * Create connection.
         * Create session from connection; false means session is not transacted.
         * Create sender and text message.
         * Send messages, varying text slightly.
         * Send end-of-messages message.
         * Finally, close connection.
         */
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage();
            System.out.println("XML Payload....");
			message.setText(xml);
            /*
             * Send a non-text control message indicating end of messages.
             */
			System.out.println("sending jms....");
            producer.send(message);
            System.out.println("jms sent....");
        }
        catch (JMSException e) {
            System.out.println("Exception occurred: " + e);
        }catch (Exception e) {
            System.out.println("Exception occurred: " + e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (JMSException e) {
                }
            }
        }
	}
}
