package edu.duke.cabig.c3pr.esb.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Vector;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl;

public class TestEsbClient {
	public static String brokerUrl="tcp://localhost:61616";
	public static String sendQueue="bridge.output.async";
	public static String recvQueue="esb.output";
	public static long sleep=1000;
	public static void main(String args[]){
		MessageBroadcastServiceImpl esbClient=new MessageBroadcastServiceImpl();
		try {
			esbClient.setConnectionFactory(new ActiveMQConnectionFactory(brokerUrl));
//			esbClient.setSendQueue(new ActiveMQQueue(sendQueue));
			esbClient.setRecvQueue(new ActiveMQQueue(recvQueue));
			esbClient.initialize();
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
     		System.out.println("Sleeping for"+sleep);
     		Thread.sleep(sleep);
     		System.out.println("out of sleep..");
			Vector result = esbClient.getBroadcastStatus();
			if(result!=null){
				for(int i=0 ; i<result.size();i++){
					String msg=(String)result.get(i);
					System.out.println((i+1)+".");
					System.out.println(msg);
					System.out.println("--------");
				}
			}else{
				System.out.println("no avalbale result..");
			}
				
		} catch (BroadcastException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				esbClient.close();
			} catch (BroadcastException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
