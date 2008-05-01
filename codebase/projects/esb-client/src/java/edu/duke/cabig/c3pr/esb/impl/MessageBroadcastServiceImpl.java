package edu.duke.cabig.c3pr.esb.impl;

import org.apache.log4j.Logger;

import java.util.Vector;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.JmsService;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;
import edu.duke.cabig.c3pr.esb.MessageResponseRetreiverService;


public class MessageBroadcastServiceImpl extends JmsService implements MessageBroadcastService, MessageResponseRetreiverService {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(MessageBroadcastServiceImpl.class);

    public void broadcast(String message) throws BroadcastException {
        if (!isProvider()) {
            if (logger.isDebugEnabled()) {
                logger.debug("broadcast(String) - no send queue provided ");
            }
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("broadcast(String) - calling sendJms method...");
            }
            sendJms(message);
        }
    }


    public void broadcast(String message, String externalId) throws BroadcastException {
        broadcast(message, DUMMY_ID);
    }

    public Vector getResponses() {
        if (!isConsumer()) {
            if (logger.isDebugEnabled()) {
                logger.debug("getBroadcastStatus() - no recieve queue provided ");
            }
        }
        return messages;
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
