package edu.duke.cabig.c3pr.esb;

import javax.jms.*;

public abstract class AbstractJmsService {
    public ConnectionFactory connectionFactory = null;
    public Destination destination = null;
    
    public void sendJms(String xml) throws JMSException {

    }
}
