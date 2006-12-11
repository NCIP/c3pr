package edu.duke.cabig.c3pr.esb.impl;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import edu.duke.cabig.c3pr.esb.AbstractJmsService;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;
import edu.duke.cabig.c3pr.esb.BroadcastException;


public class MessageBroadcastServiceImpl extends AbstractJmsService implements MessageBroadcastService {

	public void broadcast(String message) throws BroadcastException {
		// TODO Auto-generated method stub
		System.out.println("calling sendJms method...");
        try {
            sendJms(message);
        } catch (JMSException e) {
            throw new BroadcastException(e);
        }
    }

	public void getBroadcastStatus() {
		// TODO Auto-generated method stub
		
	}

	public void sendJms(String xml) throws JMSException{
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
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (JMSException e) {
                    //exception in finally block will not be passed to client
                }
            }
        }
	}
}
