package edu.duke.cabig.c3pr.esb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Vector;

import junit.framework.TestCase;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;

public class TestEsbClient extends TestCase{
    //	public static String brokerUrl="tcp://10.10.10.2:61616";
    public static String brokerUrl = "tcp://localhost:61616";
    public static String sendQueue = "registration-message.inputQueue";
    public static String recvQueue = "registration-message.outputQueue";
    public static long sleep = 5000;

    public static void usage() {
        System.out.println("Usage: ESBClient -file <sample-message> -url <esb-url>");
    }

    public static void main(String args[]) {
        MessageBroadcastServiceImpl esbClient = new MessageBroadcastServiceImpl();
        if (args.length > 1) {
            String fileName = args[1];

            if (args[2].indexOf("-url") > -1)
                brokerUrl = args[3];

            System.out.println("using broker at " + brokerUrl + " :queue:" + sendQueue);

            try {
                esbClient.setConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
                esbClient.setSendQueue(new ActiveMQQueue(sendQueue));
                esbClient.setRecvQueue(new ActiveMQQueue(recvQueue));
                esbClient.initialize();


                System.out.println("XML Payload....");
                String xml = "";
                File f = new File(fileName);
                System.out.println(f.getAbsolutePath());
                BufferedReader fr = new BufferedReader(new FileReader(f));
                String temp = "";
                while ((temp = fr.readLine()) != null) {
                    xml += temp;
                    System.out.println(temp);
                }
                esbClient.broadcast(xml);
                System.out.println("Sleeping for" + sleep);
                Thread.sleep(sleep);
                System.out.println("out of sleep..");
                Vector result = esbClient.getResponses();
                if (result != null) {
                    for (int i = 0; i < result.size(); i++) {
                        String msg = (String) result.get(i);
                        System.out.println((i + 1) + ".");
                        System.out.println(msg);
                        System.out.println("--------");
                    }
                } else {
                    System.out.println("no avalbale result..");
                }


            } catch (BroadcastException e) {
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    esbClient.close();
                } catch (BroadcastException e) {
                    e.printStackTrace();
                }
            }
        } else {
            usage();
        }
    }
    
    public void testDummy(){
        assertTrue(true);
    }
}
