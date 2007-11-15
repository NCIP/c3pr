package edu.duke.cabig.c3pr.esb.impl;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.JmsService;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;
import edu.duke.cabig.c3pr.esb.MessageResponseRetreiverService;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import java.util.Vector;


public class MessageBroadcastServiceImpl extends JmsService implements MessageBroadcastService, MessageResponseRetreiverService {

    public void broadcast(String message) throws BroadcastException {
        // TODO Auto-generated method stub
        if (!isProvider()) {
            System.out.println("no send queue provided ");
        } else {
            System.out.println("calling sendJms method...");
            sendJms(message);
        }
    }

    public Vector getBroadcastStatus() {
        // TODO Auto-generated method stub
        if (!isConsumer()) {
            System.out.println("no recieve queue provided ");
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
