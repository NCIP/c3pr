package edu.duke.cabig.c3pr.esb.impl;

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

import edu.duke.cabig.c3pr.esb.JmsService;
import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService;


public class MessageBroadcastServiceImpl extends JmsService implements MessageBroadcastService{

	public void broadcast(String message) throws BroadcastException {
		// TODO Auto-generated method stub
		if(!isProvider()){
			System.out.println("no send queue provided ");
		}else{
			System.out.println("calling sendJms method...");
	        sendJms(message);
		}
    }
	
	public Vector getBroadcastStatus() {
		// TODO Auto-generated method stub
		if(!isConsumer()){
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
