package edu.duke.cabig.c3pr.esb;

import org.apache.log4j.Logger;

import java.util.Vector;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class JmsService implements MessageListener {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(JmsService.class);

    public ConnectionFactory connectionFactory = null;

    public Destination sendQueue = null;

    public Destination recvQueue = null;

    private Connection connection = null;

    private Session session = null;

    public Vector messages = new Vector();

    private MessageConsumer consumer = null;

    private MessageProducer producer = null;

    private MessageResponseHandler messageResponseHandler;

    private boolean enableJMS = true;

    public void setEnableJMS(boolean enableJMS) {
        this.enableJMS = enableJMS;
    }

    public void setMessageResponseHandler(MessageResponseHandler messageResponseHandler) {
        this.messageResponseHandler = messageResponseHandler;
    }

    public void sendJms(String xml) throws BroadcastException {
        /*
         * Create sender and text message.
         */
        try {
            TextMessage message = session.createTextMessage();
            if (logger.isDebugEnabled()) {
                logger.debug("sendJms(String) - XML Payload....");
            }
            message.setText(xml);
            /*
             * Send a non-text control message indicating end of messages.
             */
            if (logger.isDebugEnabled()) {
                logger.debug("sendJms(String) - sending jms....");
            }
            producer.send(message);
            if (logger.isDebugEnabled()) {
                logger.debug("sendJms(String) - jms sent....");
            }
        }
        catch (Exception e) {
            throw new BroadcastException(e.getMessage(), e);
        }
    }

    public void close() throws BroadcastException {
        try {
            connection.close();
        }
        catch (JMSException e) {
            throw new BroadcastException(e.getMessage(), e);
        }
    }

    public void onMessage(Message msg) {
        if (!enableJMS)

        if (logger.isDebugEnabled()) {
            logger.debug("onMessage(Message) - jms recieved..");
        }
        TextMessage message = null;
        if (msg instanceof TextMessage) {
            message = (TextMessage) msg;
            if (logger.isDebugEnabled()) {
                logger.debug("onMessage(Message) - XML Payload....");
            }
            try {
                if (logger.isDebugEnabled()) {
                    logger.debug("onMessage(Message) - " + message.getText());
                }
                messages.add(message.getText());
                messageResponseHandler.processMessage(message.getText());
                messages.remove(message.getText());
            }
            catch (JMSException e) {
                logger.error("onMessage(Message)", e);
            }
        }
        else {
            if (logger.isDebugEnabled()) {
                logger.debug("onMessage(Message) - not a kind of text message..");
            }
        }
    }

    public void initialize() throws BroadcastException {
        if(!enableJMS){
            if (logger.isDebugEnabled()) {
                logger.debug("initialize() - jms is disabled. not initializing the servcie.");
            }
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("initialize() - initializing esb jms client....");
        }
        if (connectionFactory == null) {
            throw new BroadcastException("JMS Connection Factory not provided..");
        }
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("initialize() - creating connection and session....");
            }
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            if (sendQueue != null) {
                producer = session.createProducer(sendQueue);
            }
            else {
                if (logger.isDebugEnabled()) {
                    logger.debug("initialize() - no send queue provided....");
                }
            }
            if (recvQueue != null) {
                consumer = session.createConsumer(recvQueue);
                consumer.setMessageListener(this);
                if (logger.isDebugEnabled()) {
                    logger.debug("initialize() - starting connection....");
                }
                connection.start();
                if (logger.isDebugEnabled()) {
                    logger.debug("initialize() - connection started and subscriber registered....");
                }
            }
            else {
                if (logger.isDebugEnabled()) {
                    logger.debug("initialize() - no recieve queue provided....");
                }
            }
        }
        catch (JMSException e) {
            throw new BroadcastException("Exception occurred while registering: ", e);
        }
        catch (Exception e) {
            throw new BroadcastException("Exception occurred while registering: ", e);
        }
    }

    public boolean isConsumer() {
        if (consumer == null) {
            return false;
        }
        return true;
    }

    public boolean isProvider() {
        if (producer == null) {
            return false;
        }
        return true;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setRecvQueue(Destination recvQueue) {
        this.recvQueue = recvQueue;
    }

    public void setSendQueue(Destination sendQueue) {
        this.sendQueue = sendQueue;
    }
}
