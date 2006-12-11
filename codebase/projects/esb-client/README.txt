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
//get connection factory
ConnectionFactory connectionFactory= new ActiveMQConnectionFactory("tcp://<ip>:<port>");
//get queue or topic destination
Destination destination = new ActiveMQQueue("<queue/topic name>");
//create connection
Connection connection = connectionFactory.createConnection();
//create session
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//create producer
Producer producer = session.createProducer(destination);
//create text message
TextMessage message = session.createTextMessage();
//set message string
message.setText(<String Data>);
//send jms
producer.send(message);

USAGE SPRING:
------------
Application context snippet:
//create connection factory bean
<bean id="connectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
	<property name="brokerURL"
		value="tcp://<ip>:<port>" />
</bean>

//create QueueDestination
<bean id="QueueDestination"	class="org.apache.activemq.ActiveMQQueue">
	<constructor-arg>
		<value>Destination Name</value>
	<constructor-arg>				
</bean>

//create the ESB Mesage Broadcaster
<bean id="MessageBroadcaster" class="edu.duke.cabig.c3pr.esb.impl.MessageBroadcastServiceImpl">
	<property name="connectionFactory">
        <ref bean="connectionFactory"/>
    </property>
	<property name="destination">
        <ref bean="QueueDestination"/>
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