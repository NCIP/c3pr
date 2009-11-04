README.TXT for ESB-CLIENT
==========================

TABLE OF CONTENTS
-----------------
    INSTALLATION
    USAGE STANDALONE
    USAGE SPRING
    EXAMPLE CONFIGURATION SNIPPETS

INSTALLATION:
------------
To Install ESB-CLIENT, run the build.xml default target  i.e 'all' which will compile the code
and generate a jar file called esb-client.jar and put it in the build/jars directory. Copy this
jar and other dependency jars from lib/ to your project classpath and its done.

USAGE STANDALONE:
----------------
Example code:
//create an instance of the service
MessageBroadcastServiceImpl esbClient=new MessageBroadcastServiceImpl();
//set connection factory
esbClient.setConnectionFactory(new ActiveMQConnectionFactory(<brokerUrl>));
//set the sending queue. This is optional in case the apllication only wants to recieve the jms
esbClient.setSendQueue(new ActiveMQQueue(sendQueue));
//set the recieve queue. This is optional in case the apllication only wants to send the jms
esbClient.setRecvQueue(new ActiveMQQueue(recvQueue));
//initialize the service
esbClient.initialize();

//broadcasting message
esbClient.broadcast(xml);

//recieving message. Note that the getBroadcastStatus() returns a vector which contains all the
//messages that are present on the queue. After returning the messages it will delete the
//mesages. Its the responsibility of the application thereafter to persisit the message
//if they want.
Vector result = esbClient.getBroadcastStatus();

USAGE SPRING:
------------
Application context snippet:
//create connection factory bean
<bean id="connectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
	<property name="brokerURL"
		value="tcp://<ip>:<port>" />
</bean>

//create SendQueueDestination
<bean id="sendQueueDestination"	class="org.apache.activemq.ActiveMQQueue">
	<constructor-arg>
		<value>Send Destination Name</value>
	<constructor-arg>				
</bean>

//create RecieveQueueDestination
<bean id="recvQueueDestination"	class="org.apache.activemq.ActiveMQQueue">
	<constructor-arg>
		<value>Recieve Destination Name</value>
	<constructor-arg>				
</bean>

//create the ESB Mesage Broadcaster
<bean id="MessageBroadcaster" class="edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl">
	<property name="connectionFactory">
        <ref bean="connectionFactory"/>
    </property>
	<property name="sendQueue">
        <ref bean="sendQueueDestination"/>
    </property>
	<property name="recvQueue">
        <ref bean="recvQueueDestination"/>
    </property>
</bean>

//inject the above contructed bean to your bean
<bean id="MyImpl"	class="my.package.ESBService">
	<property name="esbBroadcastService">
        <ref bean="MessageBroadcaster"/>
    </property>
</bean>

Now here's what your code should look like:
package my.package;
import edu.duke.cabig.c3pr.esb.MessageBroadcastService
	.
	.
	.
public class ESBService{
	MessageBroadcastService esbBroadcastService=null;
	getEsbBroadcastService(MessageBroadcastService esbBroadcastService){
		this.esbBroadcastService=esbBroadcastService;
	.
	.
	.
	.
	//calling the ESB Broadcast Service
	esbBroadcastService.broadcast("<Message>");
	.
	.
	.
}