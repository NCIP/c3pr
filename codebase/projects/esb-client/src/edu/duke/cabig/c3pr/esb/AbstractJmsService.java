package edu.duke.cabig.c3pr.esb;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;

public abstract class AbstractJmsService {
    public ConnectionFactory connectionFactory = null;
    public Destination destination = null;
    
    public void sendJms(String xml){

    }
}
