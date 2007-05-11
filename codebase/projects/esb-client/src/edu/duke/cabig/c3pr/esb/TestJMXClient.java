package edu.duke.cabig.c3pr.esb;

import javax.management.remote.JMXServiceURL;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ObjectInstance;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 21, 2007
 * Time: 3:24:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestJMXClient{

    private MBeanServerConnection mbsc = null;
    //properties
    private String username = "smx";
    private String password = "smx";


    public static void main(String[] args) throws Exception{
        TestJMXClient client = new TestJMXClient();

        client.connectToESB("localhost","1098");

        System.out.println("Getting Remote Logger");
        ObjectName remoteLogger = client.createBean();
        System.out.println("Logger instantiated of type " + remoteLogger.getClass());

        System.out.println("Remote Logger level is " + client.getLogLevel(remoteLogger));

        String newLogLevel = "INFO";

        System.out.println("Setting logger level to " + newLogLevel);
        client.setLogLevel(remoteLogger,newLogLevel);

        System.out.println("Remote Logger level is" + client.getLogLevel(remoteLogger));

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
        return new ObjectName("java.util.logging:type=Logging");
    }


    private void setLogLevel(ObjectName remoteLogger,String level) throws Exception{
        mbsc.invoke(remoteLogger,"setLoggerLevel",
                new Object[]{
                        "global",level
                },
                new String[]{
                        "java.lang.String","java.lang.String"
                });
    }


    private String getLogLevel(ObjectName remoteLogger) throws Exception{
        return (String)mbsc.invoke(remoteLogger,"getLoggerLevel",
                new Object[]{
                        "global"
                },
                new String[]{
                        "java.lang.String"
                });

    }
}


