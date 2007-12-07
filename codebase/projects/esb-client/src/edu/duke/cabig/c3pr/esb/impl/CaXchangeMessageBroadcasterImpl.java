package edu.duke.cabig.c3pr.esb.impl;

import edu.duke.cabig.c3pr.esb.*;
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
public class CaXchangeMessageBroadcasterImpl implements CCTSMessageBroadcaster, CaXchangeMessageResponseNotifier {

    private String caXchangeURL;
    //default valut. Should not change
    private Map messageTypesMapping;

    private CaXchangeMessageResponseHandlerSet messageResponseHandlers = new CaXchangeMessageResponseHandlerSet();

    private Logger log = Logger.getLogger(CaXchangeMessageBroadcasterImpl.class);
    private MessageWorkflowCallback messageWorkflowCallback;


    /**
     * Will just use a dummy id to broadcast message
     *
     * @param message
     * @throws BroadcastException
     */
    public void broadcast(String message) throws BroadcastException {
        broadcast(message, DUMMY_ID);
    }

    /**
     * will broadcast the domain object to caXchange
     *
     * @param cctsDomainObjectXML
     * @throws BroadcastException
     */
    public void broadcast(String cctsDomainObjectXML, String externalId) throws BroadcastException {

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
            mData.setExternalIdentifier(externalId);

            //will be removed. temp
            Credentials creds = new Credentials();
            creds.setUserName("hmarwaha");
            creds.setPassword("password");
            mData.setCredentials(creds);

            xchangeMessage.setMetadata(mData);
            log.debug("Sending message to caXchange");
            responseRef = caXchangeClient.processRequestAsynchronously(xchangeMessage);
            if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendSuccessful(externalId);
            }
        } catch (RemoteException e) {
            log.error("caXchange could not process request", e);
            if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendFailed(externalId);
            }
            throw new BroadcastException("caXchange could not process message", e);
        }

        //check on the response asynchronously
        //only if someone is interested
        if (messageWorkflowCallback != null || messageResponseHandlers.size() > 0) {
            log.debug("Will track response from caXchange");
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
    }


    public void addResponseHandler(CaXchangeMessageResponseHandler handler) {
        messageResponseHandlers.add(handler);
    }


    public CaXchangeMessageResponseHandlerSet getMessageResponseHandlers() {
        return messageResponseHandlers;
    }

    public void setMessageResponseHandlers(CaXchangeMessageResponseHandlerSet messageResponseHandlers) {
        this.messageResponseHandlers = messageResponseHandlers;
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
                log.debug("Received response from caXchange");
                String objectId = response.getResponseMetadata().getExternalIdentifier();
                log.debug("Received response from caXchange for externalId" + objectId);

                if (response.getResponse().getResponseStatus().equals(Statuses.SUCCESS)) {
                    log.debug("Received delivery confirmation from caXchange");

                    messageWorkflowCallback.messageSendConfirmed(objectId);

                    // notify response handlers
                    log.debug("Notifying " + messageResponseHandlers.size() + " handlers");
                    messageResponseHandlers.notifyAll(objectId, response.getResponse());

                }
                if (response.getResponse().getResponseStatus().equals(Statuses.FAILURE)) {
                    log.debug("Received failure from caXchange");
                    messageWorkflowCallback.messageSendFailed(objectId);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                log.warn(e);
            } catch (ExecutionException e) {
                log.warn(e);
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
                log.debug("Checking caXchange for response");
                return responseService.getResponse();
            } catch (RemoteException e) {
                log.debug("Will check caXchange for response again");
                return call();
            }
        }


        protected void finalize() throws Throwable {
            log.debug("Killing listening thread");
            super.finalize();    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

}
