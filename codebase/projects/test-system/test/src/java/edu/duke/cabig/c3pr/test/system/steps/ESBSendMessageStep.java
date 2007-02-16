package edu.duke.cabig.c3pr.test.system.steps;

import com.atomicobject.haste.framework.Step;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Vector;

import gov.nci.nih.cagrid.tests.core.util.FileUtils;

/**
 * Sends a message to the ESB
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Feb 15, 2007
 * Time: 11:47:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ESBSendMessageStep extends Step {
    private String brokerUrl;
    private String sendQueue;
    private String recvQueue;
    private File testMessage;

    public ESBSendMessageStep(String brokerUrl, String sendQueue, String recvQueue, File testMessage) {
        this.brokerUrl = brokerUrl;
        this.sendQueue = sendQueue;
        this.recvQueue = recvQueue;
        this.testMessage = testMessage;
    }


    public void runStep() throws Throwable {
        MessageBroadcastServiceImpl esbClient=new MessageBroadcastServiceImpl();

        esbClient.setConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
        esbClient.setSendQueue(new ActiveMQQueue(sendQueue));
        esbClient.setRecvQueue(new ActiveMQQueue(recvQueue));
        esbClient.initialize();

        String message = FileUtils.readText(testMessage);

        esbClient.broadcast(message);
        //long wait
        longpause();

        Vector result = esbClient.getBroadcastStatus();

        if(result!=null){
            for(int i=0 ; i<result.size();i++){
                String msg=(String)result.get(i);
                assertNotNull(msg);
                //make sure message was delivered
                assertTrue(msg.indexOf("delivered")>-1);
            }
        }else{
            fail();

        }
        longpause();
    }

    private synchronized void longpause(){
        try {
            this.wait(3000);
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
