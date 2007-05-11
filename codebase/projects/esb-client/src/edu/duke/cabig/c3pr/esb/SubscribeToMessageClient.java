package edu.duke.cabig.c3pr.esb;

import javax.management.remote.JMXServiceURL;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 21, 2007
 * Time: 3:24:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class SubscribeToMessageClient {

    private MBeanServerConnection mbsc = null;
    //properties
    private String username = "smx";
    private String password = "smx";


    public static void main(String[] args) throws Exception{
        SubscribeToMessageClient client = new SubscribeToMessageClient();

        client.connectToESB("localhost","1098");

        System.out.println("Sending Subscription for Messages to ESB Registry");
        ObjectName esbRegistry = client.createBean();

        client.registerSubscription(esbRegistry,"RegistrationType","grid","http://127.0.0.1:9080/wsrf/services/cagrid/RegistrationConsumer");



         }

    private void connectToESB(String hostname,String port) throws Exception
    {

        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+hostname+":"+port+"/jmxrmi");

        Hashtable h  = new Hashtable();

        //crendetial
        String[] credentials = new String[] {username ,password };
        h.put("jmx.remote.credentials", credentials);


        //Establish the JMX connection.
        JMXConnector jmxc = JMXConnectorFactory.connect(url, h);

        //Get the MBean server connection instance.
        mbsc = jmxc.getMBeanServerConnection();

    }

    private ObjectName createBean() throws Exception{
        return new ObjectName("edu.duke.cabig.ctms.ESBRegistry:name=registry");
    }


    private void registerSubscription(ObjectName esbRegistry,String messageType, String consumingServiceType, String url) throws Exception{
        mbsc.invoke(esbRegistry,"createOrUpdateSubscription",
                new Object[]{
                        messageType,consumingServiceType,url
                },
                new String[]{
                        "java.lang.String","java.lang.String","java.lang.String"
                });
    }



  
}


