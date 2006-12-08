package jmslogger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.jbi.messaging.ExchangeStatus;
import javax.jbi.messaging.InOut;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessagingException;
import javax.jbi.messaging.NormalizedMessage;
import javax.jbi.messaging.MessageExchange.Role;
import javax.jbi.servicedesc.ServiceEndpoint;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;

import org.apache.servicemix.MessageExchangeListener;
import org.apache.servicemix.components.util.ComponentSupport;
import org.apache.servicemix.jbi.jaxp.SourceTransformer;


public class Logger extends ComponentSupport implements MessageExchangeListener{
	String ns="http://servicemix.apache.org/samples/bridge";
//	String pip="pipeline";
	String pip="route-pipeline";
	String jms="jms-logger";
	Map requests=new ConcurrentHashMap();
	
	public Logger() {
        super(new QName("http://servicemix.apache.org/samples/bridge","jms-logger"), "input");
        System.out.println("Calling Cons.......");
	}

	public void onMessageExchange(MessageExchange exchange) throws MessagingException {
		System.out.println("Recieved Message.......");
		if (exchange.getStatus() == ExchangeStatus.DONE) {
			return;
        // Consumer role
        }
		if (exchange.getRole() == Role.PROVIDER) {
			processInputRequest(exchange);
        // Consumer role
        } else {
        	System.out.println("Recieved Response.......");
        	System.out.println("constructor was called.......");
            ServiceEndpoint ep = exchange.getEndpoint();
            // Credit agency response
            if (ep.getServiceName().getLocalPart().equals(pip)) {
            	processResponse(exchange);
            	System.out.println("Returned JMS with done.......");
            }
        }
		// TODO Auto-generated method stub
	}
	
    private void processInputRequest(MessageExchange exchange) throws MessagingException {
		System.out.println("Recieved JMS Message.......");
		String id=exchange.getExchangeId();
		requests.put(id, exchange);
		System.out.println("exchange id of request is : "+id);
//		InOnly inout = createInOnlyExchange(new QName(ns, pip), null, null);
		InOut inout = createInOutExchange(new QName(ns, pip), null, null);
		inout.setProperty("test", id);
        Source source=(exchange.getMessage("in").getContent());
        String xml="";
		try {
			xml=new SourceTransformer().toString(source);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("XML Payload....");
		System.out.println(xml);
		NormalizedMessage msg = inout.createMessage();
		msg=exchange.getMessage("in");
		inout.setInMessage(msg);
		send(inout);
		disp(exchange);
		System.out.println("Message Sent to b:pipeline....");
    }
    
    private void processResponse(MessageExchange exchange) throws MessagingException {
    	System.out.println("Recieved Response from b:pipeline.......");
    	System.out.println("exchange id of response is : "+exchange.getExchangeId());
//        Source inSource=(exchange.getMessage("in").getContent());
//		String resInXml="";
//		try {
//			resInXml=new SourceTransformer().toString(inSource);
//		} catch (TransformerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("In XML..");
//		System.out.println(resInXml);
//        Source outSource=(exchange.getMessage("out").getContent());
//		String resOutXml="";
//		try {
//			resOutXml=new SourceTransformer().toString(outSource);
//		} catch (TransformerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("Out XML..");
//		System.out.println(resOutXml);
		String reqId=(String)exchange.getProperty("test");
		System.out.println("property id value : "+reqId);
		MessageExchange request=(MessageExchange)requests.get(reqId);
		disp(request);
	   	NormalizedMessage response = request.createMessage();
    	response.setContent(exchange.getMessage("out").getContent());
		done(exchange);
		System.out.println("done with exchange...");
//        request.setMessage(response, "out");
//        send(request);
//        response.setProperty("a", "b");
//        response.setProperty("c","d");
        request.setMessage(response, "out");
        send(request);
        requests.remove(reqId);
//        done(request);
    } 
    
    private void disp(MessageExchange request){
		if(request!=null){
			System.out.println("request is not null");
		}else{
			System.out.println("request is null");
		}
//		request.setStatus(ExchangeStatus.ACTIVE);
		System.out.println("request property exchange id : "+request.getExchangeId());
		System.out.println("property status value : "+request.getStatus().toString());
		String role=request.getRole()==Role.PROVIDER?"provider":"consumer";
		System.out.println("property role value : "+role);
		System.out.println("property service localpart value : "+request.getService().getLocalPart());
		try {
			System.out.println(new SourceTransformer().toString(request.getMessage("in").getContent()));
			System.out.println("---------------");
//			System.out.println(new SourceTransformer().toString(request.getMessage("out").getContent()));
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
