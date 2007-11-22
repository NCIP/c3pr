package edu.duke.cabig.c3pr.esb.impl;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageHelper;
import edu.duke.cabig.c3pr.esb.MessageWorkflowCallback;
import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.caxchange.context.client.CaXchangeResponseServiceClient;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference;
import gov.nih.nci.caxchange.*;
import org.apache.axis.types.URI;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Sends messages to caXchange. Also, will notify of the message status
 * by impelementing MessageWorkflowNotifier
 * <p/>
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 13, 2007
 * Time: 3:40:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class CaXchangeMessageBroadcasterImpl implements CCTSMessageBroadcaster {

    private String caXchangeURL;
    //default valut. Should not change
    private Map messageTypesMapping;

    private Logger log = Logger.getLogger(CaXchangeMessageBroadcasterImpl.class);
    private MessageWorkflowCallback messageWorkflowCallback;


    /**
     * will broadcast the domain object to caXchange
     *
     * @param cctsDomainObjectXML
     * @throws BroadcastException
     */
    public void broadcast(String cctsDomainObjectXML) throws BroadcastException {

        CaXchangeRequestProcessorClient caXchangeClient = null;
        try {
            caXchangeClient = new CaXchangeRequestProcessorClient(caXchangeURL);
        } catch (Exception e) {
            throw new BroadcastException("caXchange could not initialize caExchange client. Using URL " + caXchangeURL, e);
        }

        //marshall the bean
        Document messageDOM = null;

        try {
            messageDOM = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(cctsDomainObjectXML)));
        } catch (SAXException e) {
            throw new BroadcastException("caXchange could not serialize domain object", e);
        } catch (IOException e) {
            throw new BroadcastException("caXchange could not serialize domain object", e);
        } catch (ParserConfigurationException e) {
            throw new BroadcastException("caXchange could not serialize domain object", e);
        }


        CaXchangeResponseServiceReference responseRef = null;
        try {
            Message xchangeMessage = CaXchangeMessageHelper.createXchangeMessage(messageDOM);
            Metadata mData = new Metadata();
            mData.setOperation(Operations.PROCESS);
            mData.setMessageType(MessageTypes.fromString((String) messageTypesMapping.get(messageDOM.getDocumentElement().getNodeName())));

            xchangeMessage.setMetadata(mData);
            responseRef = caXchangeClient.processRequestAsynchronously(xchangeMessage);
            if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendSuccessful(cctsDomainObjectXML);
            }
        } catch (RemoteException e) {
            log.error("caXchange could not process request", e);
            if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendFailed(cctsDomainObjectXML);
            }
            throw new BroadcastException("caXchange could not process message", e);
        }

        //check on the response asynchronously

        try {
            FutureTask asyncTask = new AsynchronousResponseRetreiver(new SynchronousResponseProcessor(responseRef));
            //ToDo make this like a global service not single thread executor
            ExecutorService es = Executors.newSingleThreadExecutor();
            es.submit(asyncTask);
            es.shutdown();
            //these exceptions do not mean a message send failure
        } catch (URI.MalformedURIException e) {
            log.error(e);
        } catch (RemoteException e) {
            log.error(e);
        }
    }


    public String getCaXchangeURL() {
        return caXchangeURL;
    }

    public void setCaXchangeURL(String caXchangeURL) {
        this.caXchangeURL = caXchangeURL;
    }

    public void setNotificationHandler(MessageWorkflowCallback handler) {
        this.messageWorkflowCallback = handler;
    }


    public Map getMessageTypesMapping() {
        return messageTypesMapping;
    }

    public void setMessageTypesMapping(Map messageTypesMapping) {
        this.messageTypesMapping = messageTypesMapping;
    }

    class AsynchronousResponseRetreiver extends FutureTask {

        public AsynchronousResponseRetreiver(Callable callable) {
            super(callable);
        }


        protected void done() {
            try {
                ResponseMessage response = (ResponseMessage) get();
                if (response.getResponse().getResponseStatus().equals(Statuses._SUCCESS)) {
                    messageWorkflowCallback.messageSendSuccessful(response.getResponseMetadata().getExternalIdentifier());
                }

                //call some response handlers here
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ExecutionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            //call handlers for the result
        }
    }

    class SynchronousResponseProcessor implements Callable {

        CaXchangeResponseServiceClient responseService;

        public SynchronousResponseProcessor(CaXchangeResponseServiceReference responseRef) throws org.apache.axis.types.URI.MalformedURIException, RemoteException {
            responseService = new CaXchangeResponseServiceClient(responseRef.getEndpointReference());
        }


        public ResponseMessage call() throws Exception {
            try {
                return responseService.getResponse();
            } catch (RemoteException e) {
                return call();
            }
        }

    }
}
