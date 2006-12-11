package edu.duke.cabig.c3pr.esb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;

public class TestEsbClient {
	public static String brokerUrl="tcp://localhost:61616";
	public static String queue="bridge.output.async";
	public static void main(String args[]){
		MessageBroadcastServiceImpl esbClient=new MessageBroadcastServiceImpl();
		try {
			esbClient.setConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
			esbClient.setDestination(new ActiveMQQueue(queue));
            String fileName="resources/create-protocol.xml";
            System.out.println("XML Payload....");
            String xml="";
            File f= new File(fileName);
            System.out.println(f.getAbsolutePath());
        	BufferedReader fr=new BufferedReader(new FileReader(f));
			String temp="";
			while((temp=fr.readLine())!=null){
				xml+=temp;
				System.out.println(temp);
			}
     		esbClient.broadcast(xml);
		} catch (BroadcastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
