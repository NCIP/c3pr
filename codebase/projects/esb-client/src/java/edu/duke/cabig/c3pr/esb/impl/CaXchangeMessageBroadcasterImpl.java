package edu.duke.cabig.c3pr.esb.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.log4j.Logger;
import org.globus.gsi.GlobusCredential;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.CCTSApplicationNames;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageHelper;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseHandler;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseHandlerSet;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseNotifier;
import edu.duke.cabig.c3pr.esb.DelegatedCredential;
import edu.duke.cabig.c3pr.esb.DelegatedCredentialProvider;
import edu.duke.cabig.c3pr.esb.MessageWorkflowCallback;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ResponseErrors;
import gov.nih.nci.cagrid.caxchange.client.CaXchangeRequestProcessorClient;
import gov.nih.nci.cagrid.caxchange.context.client.CaXchangeResponseServiceClient;
import gov.nih.nci.cagrid.caxchange.context.stubs.types.CaXchangeResponseServiceReference;
import gov.nih.nci.cagrid.common.Utils;
import gov.nih.nci.caxchange.Credentials;
import gov.nih.nci.caxchange.Message;
import gov.nih.nci.caxchange.MessagePayload;
import gov.nih.nci.caxchange.Metadata;
import gov.nih.nci.caxchange.Request;
import gov.nih.nci.caxchange.ResponseMessage;
import gov.nih.nci.caxchange.Statuses;
import gov.nih.nci.caxchange.TargetResponseMessage;

/**
 * Sends messages to caXchange. Also, will notify of the message status
 * by impelementing MessageWorkflowNotifier
 * <p/>
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm, Vinay Gangoli
 * Date: Nov 13, 2007
 * Time: 3:40:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class CaXchangeMessageBroadcasterImpl implements CCTSMessageBroadcaster, CaXchangeMessageResponseNotifier {

    private String caXchangeURL;
    //default valut. Should not change
    private Map messageTypesMapping;

    private CaXchangeMessageResponseHandlerSet messageResponseHandlers = new CaXchangeMessageResponseHandlerSet();
    private DelegatedCredentialProvider delegatedCredentialProvider;

    private Logger log = Logger.getLogger(CaXchangeMessageBroadcasterImpl.class);
    private MessageWorkflowCallback messageWorkflowCallback;

    private int timeout;
    public static final String namespaceURI = "http://caXchange.nci.nih.gov/messaging";
    public static final String localPart = "caXchangeResponseMessage";
    
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    /**
     * Will just use a dummy id to broadcast message
     *
     * @param message
     * @throws BroadcastException
     */
    public void broadcast(String message) throws BroadcastException {
        broadcast(message, getLocalMetadataWithDummyId());
    }

    private edu.duke.cabig.c3pr.esb.Metadata getLocalMetadataWithDummyId() {
		return new edu.duke.cabig.c3pr.esb.Metadata(OperationNameEnum.NA.name(), "DUMMY_ID"); 
	}

	/**
     * * Broadcasts the domain object to caXchange
     *
     * @param cctsDomainObjectXML xml message
     * @param edu.duke.cabig.c3pr.esb.Metadata localMetadata   
     * 			localMetadata includes attributes like externalId(business id of the message. You can track messages by this id)
     * 			and operationType (e.g. NA, PERSON, ORGANIZATION etc)
     * @throws BroadcastException
     */
    public void broadcast(String cctsDomainObjectXML, edu.duke.cabig.c3pr.esb.Metadata localMetadata) throws BroadcastException {

        GlobusCredential proxy = getProxy();
        Credentials credentials = getCredentials();
        String externalId = localMetadata.getExternalIdentifier();

        //marshall the bean
        Document messageDOM = marshallBean(cctsDomainObjectXML);
        
        CaXchangeRequestProcessorClient caXchangeClient = null;
        CaXchangeResponseServiceReference responseRef = null;
        try {
        	caXchangeClient = new CaXchangeRequestProcessorClient(caXchangeURL, proxy);
        	
            Message xchangeMessage = CaXchangeMessageHelper.createXchangeMessage(messageDOM);
            Metadata mData = buildMetadata(localMetadata, messageDOM, credentials);
            xchangeMessage.setMetadata(mData);
            
            log.debug("Sending message to caXchange");
            responseRef = caXchangeClient.processRequestAsynchronously(xchangeMessage);
            if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendSuccessful(externalId);
            }
        } catch (RemoteException e) {
        	if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendFailed(externalId);
            }
            throw new BroadcastException(e);
        }catch (MalformedURIException e) {
        	if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendFailed(externalId);
            }
			throw new BroadcastException(e);
		}

        //logging epr info
        logEPR(responseRef.getEndpointReference());
        //check on the response asynchronously only if someone is interested
        checkResponseAsynchronously(responseRef, externalId, proxy);
    }


	/**
     * * Broadcasts the COPPA content to caXchange
     *
     * @param cctsDomainObjectXML xml message
     * @param edu.duke.cabig.c3pr.esb.Metadata localMetadata   
     * 			localMetadata includes attributes like externalId(business id of the message. You can track messages by this id)
     * 			and operationType (e.g. NA, PERSON, ORGANIZATION etc)
     * @throws BroadcastException
     * @return responseXML as string
     */
    public String broadcastCoppaMessage(String cctsDomainObjectXML, edu.duke.cabig.c3pr.esb.Metadata localMetadata) throws BroadcastException {
    	String serviceResponsePayload = null;
        Credentials credentials = getCredentials();

        //marshall the bean
        Document messageDOM = marshallBean(cctsDomainObjectXML);
        
        try {
            Message xchangeMessage = new Message();

            Metadata mData = buildMetadataForCoppa(localMetadata, messageDOM, credentials);
            xchangeMessage.setMetadata(mData);
            
            MessageElement messageElement = new MessageElement(messageDOM.getDocumentElement());
            MessagePayload messagePayload = new MessagePayload();
            messagePayload.setXmlSchemaDefinition(new URI("http://po.coppa.nci.nih.gov"));
            messagePayload.set_any(new MessageElement[]{messageElement});
            Request request = new Request();
            xchangeMessage.setRequest(request);
            xchangeMessage.getRequest().setBusinessMessagePayload(messagePayload);

            serviceResponsePayload = broadcastCoppaMessage(xchangeMessage);
        } catch (MalformedURIException e) {
			log.error("Could not instantiate CaXchangeRequestProcessorClient");
			log.error(e.getMessage());
		} 
		return serviceResponsePayload;
    }
    
    /**
     * * Broadcasts the COPPA content to caXchange. This is used by the PA searches which return more than one result 
     *   and needs an offset to be specified.
     *
     * @param cctsDomainObjectXMLList list of xml messages 
     * @param edu.duke.cabig.c3pr.esb.Metadata localMetadata   
     * 			localMetadata includes attributes like externalId(business id of the message. You can track messages by this id)
     * 			and operationType (e.g. NA, PERSON, ORGANIZATION etc)
     * @throws BroadcastException
     * @return responseXML as string
     */
    public String broadcastCoppaMessage(List<String> cctsDomainObjectXMLList, edu.duke.cabig.c3pr.esb.Metadata localMetadata) throws BroadcastException  {        
    	String serviceResponsePayload = null;
    	Credentials credentials = getCredentials(); 
    	
    	MessageElement[] messageElements = new MessageElement[cctsDomainObjectXMLList.size()];        
    	Document messageDOM = null;        
    	MessageElement messageElement = null;                
    	for(int i=0;i < cctsDomainObjectXMLList.size() ; i++){            
	    	//marshall the bean            
	    	messageDOM = marshallBean(cctsDomainObjectXMLList.get(i));            
	    	messageElement = new MessageElement(messageDOM.getDocumentElement());            
	    	messageElements[i] = messageElement;        
    	}
    	
    	try {            
    		Message xchangeMessage = new Message();   
    		
    		Metadata mData = buildMetadataForCoppa(localMetadata, messageDOM, credentials);            
    		xchangeMessage.setMetadata(mData);   
    		
    		MessagePayload messagePayload = new MessagePayload();            
    		messagePayload.setXmlSchemaDefinition(new URI("http://pa.services.coppa.nci.nih.gov"));
    		messagePayload.set_any(messageElements);            
    		Request request = new Request();           
    		xchangeMessage.setRequest(request);
    		xchangeMessage.getRequest().setBusinessMessagePayload(messagePayload); 

    		serviceResponsePayload = broadcastCoppaMessage(xchangeMessage);
    	} catch (MalformedURIException e) {            
    		log.error("Could not instantiate CaXchangeRequestProcessorClient");      
    		log.error(e.getMessage());
    	}  
    	return serviceResponsePayload;
    }
    
    
    /** private method used by the broadcastCoppa overloaded methods
     * 
     * @param xchangeMessage
     * @return
     * @throws BroadcastException
     */
    private String broadcastCoppaMessage(Message xchangeMessage) throws BroadcastException{
    	String serviceResponsePayload = null;   
    	CaXchangeRequestProcessorClient caXchangeClient = null;        
    	GlobusCredential proxy = getProxy();        
    	
    	ResponseMessage responseMessage = null;        
    	try {            
    		caXchangeClient = new CaXchangeRequestProcessorClient(caXchangeURL, proxy);            

    		log.debug("Sending message to caXchange ");                        
    		responseMessage = caXchangeClient.processRequestSynchronously(xchangeMessage);  
    		InputStream serializeStream = CaXchangeRequestProcessorClient.class.getResourceAsStream("client-config.wsdd");            
    		StringWriter writer = new StringWriter();            
    		Utils.serializeObject(responseMessage, new QName(namespaceURI,localPart),writer, serializeStream);            
    		serviceResponsePayload =  writer.getBuffer().toString();        
    	} catch (RemoteException e) {           
    		log.error("caXchange could not process request", e);            
    		throw new BroadcastException("caXchange could not process message", e);        
    	} catch (Exception e) {            
    		log.error("Could not serialize ");
    		log.error(e.getMessage());
    	}       
    	return serviceResponsePayload;
    }
    
    
    /**
     * Gets the proxy
     * 
     * @param caXchangeClient
     * @param proxy
     * @return credentials
     * @throws BroadcastException
     */
    private GlobusCredential getProxy() throws BroadcastException {
    	GlobusCredential proxy = null;
        try {
            if (delegatedCredentialProvider != null) {
                log.debug("Using delegated crential provider to set credentials");
                DelegatedCredential cred = delegatedCredentialProvider.provideDelegatedCredentials();
                if (cred != null) {
                    proxy = cred.getCredential();
                    log.debug("Found valid proxy. Using it for esb communication");
                }
            }
        } catch (Exception e) {
            throw new BroadcastException("caXchange could not initialize caExchange client. Using URL " + caXchangeURL, e);
        }
        return proxy;
    }
    
    
    /**
     * Gets the credentials.
     * 
     * @return the credentials
     * 
     * @throws BroadcastException the broadcast exception
     */
    private Credentials getCredentials() throws BroadcastException{
    	Credentials creds = new Credentials();
    	creds.setUserName("hmarwaha");
        creds.setPassword("password");
        try {
            // if a provider is registered then use it to get credentials
            if (delegatedCredentialProvider != null) {
                log.debug("Using delegated crential provider to set credentials");
                DelegatedCredential cred = delegatedCredentialProvider.provideDelegatedCredentials();
                if (cred != null) {
                    //set the delegated epr.
                    creds.setDelegatedCredentialReference(cred.getDelegatedEPR());
                }
            }
        } catch (Exception e) {
            throw new BroadcastException("caXchange could not initialize caExchange client. Using URL " + caXchangeURL, e);
        }
        return creds;
    }
    
    
    /**
     * Marshall bean.
     * 
     * @param cctsDomainObjectXML the ccts domain object xml
     * 
     * @return the document
     * 
     * @throws BroadcastException the broadcast exception
     */
    private Document marshallBean(String cctsDomainObjectXML)  throws BroadcastException {
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();        
    	dbf.setNamespaceAware(true);        
    	Document messageDOM;        
    	try {            
    		DocumentBuilder db =dbf.newDocumentBuilder();            
    		messageDOM = db.parse(new InputSource(new StringReader(cctsDomainObjectXML)));
    	} catch (SAXException e) {
            throw new BroadcastException("caXchange could not serialize domain object", e);
        } catch (IOException e) {
            throw new BroadcastException("caXchange could not serialize domain object", e);
        } catch (ParserConfigurationException e) {
            throw new BroadcastException("caXchange could not serialize domain object", e);
        }
        return messageDOM;
    }

    
    /**
     * Builds the metadata for COPPa scenarios.
     * 
     * @param localMetadata the local metadata
     * @param messageDOM the message dom
     * @param creds the creds
     * 
     * @return the metadata
     */
    private Metadata buildMetadataForCoppa(edu.duke.cabig.c3pr.esb.Metadata localMetadata, Document messageDOM, Credentials creds) {
    	Metadata mData = new Metadata();
    	
        mData.setOperationName(localMetadata.getOperationName());
        mData.setExternalIdentifier(localMetadata.getExternalIdentifier());
        mData.setServiceType(localMetadata.getServiceType());
        //will be removed. temp
        mData.setCredentials(creds);
        return mData;
    }
    
    /**
     * Builds the metadata For interoperability.
     * 
     * @param localMetadata the local metadata
     * @param messageDOM the message dom
     * @param creds the creds
     * 
     * @return the metadata
     */
    private Metadata buildMetadata(edu.duke.cabig.c3pr.esb.Metadata localMetadata, Document messageDOM, Credentials creds) {
    	Metadata mData = new Metadata();
        //mData.setOperationName(OperationNameEnum.PROCESS.getName());
        mData.setServiceType((String) messageTypesMapping.get(messageDOM.getDocumentElement().getNodeName()));
        mData.setExternalIdentifier(localMetadata.getExternalIdentifier());

        //will be removed. temp
        mData.setCredentials(creds);
        return mData;
    }

    
    /**
     * Check response asynchronously.
     * 
     * @param responseRef the response ref
     * @param externalId the external id
     * @param proxy the proxy
     */
    private void checkResponseAsynchronously(CaXchangeResponseServiceReference responseRef, String externalId, GlobusCredential proxy){
    	if (messageWorkflowCallback != null || messageResponseHandlers.size() > 0) {
            log.debug("Will track response from caXchange");
            try {
                FutureTask asyncTask = new AsynchronousResponseRetreiver(new SynchronousResponseProcessor(responseRef,messageWorkflowCallback, externalId, proxy, timeout),SecurityContextHolder.getContext().getAuthentication());
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
    

    public void setDelegatedCredentialProvider(DelegatedCredentialProvider delegatedCredentialProvider) {
        this.delegatedCredentialProvider = delegatedCredentialProvider;
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

        Authentication auth;

        public AsynchronousResponseRetreiver(Callable callable, Authentication auth) {
            super(callable);
            this.auth = auth;
        }


        protected void done() {
            try {
                ResponseMessage response = (ResponseMessage) get();
                if (response != null) {

                    log.debug("Received response from caXchange");
                    log.debug("caXchange Response follows.....\n");
                    log.debug(response.getResponse().toString());

                    String objectId = response.getResponseMetadata().getExternalIdentifier();
                    log.debug("Received response from caXchange for externalId" + objectId);

                    log.debug("Setting authentication object in SecurityContext.");
                    SecurityContext ctx = new SecurityContextImpl();
                    ctx.setAuthentication(this.auth);
                    SecurityContextHolder.setContext(ctx);

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
                        ResponseErrors<CCTSApplicationNames> errors=new ResponseErrors<CCTSApplicationNames>();
                        log.debug("looking at caXchange error..");
                        if(response.getResponse().getCaXchangeError()!=null && response.getResponse().getCaXchangeError().getErrorDescription()!=null){
                        	log.error("Found caXchange error : " + response.getResponse().getCaXchangeError().getErrorDescription());
                            errors.addError(CCTSApplicationNames.CAXCHANGE, response.getResponse().getCaXchangeError().getErrorDescription());
                        }else
                            log.debug("caXchange Error is null....");
                        log.debug("looking at aplication level error..");
                        CCTSApplicationNames cApplicationName=null;
                        for (TargetResponseMessage tResponse : response.getResponse().getTargetResponse()){
                            log.debug("looking at aplication "+tResponse.getTargetServiceIdentifier()+"..");
                            if (tResponse.getTargetServiceIdentifier().indexOf("C3D") > -1) {
                                log.debug("Found c3d response. Processing...");
                                cApplicationName=CCTSApplicationNames.C3D;
                            }else if (tResponse.getTargetServiceIdentifier().indexOf("caAERS") > -1) {
                                log.debug("Found caAERS response. Processing...");
                                cApplicationName=CCTSApplicationNames.CAAERS;
                            }else if (tResponse.getTargetServiceIdentifier().indexOf("LabViewer") > -1) {
                                log.debug("Found CTODS response. Processing...");
                                cApplicationName=CCTSApplicationNames.CTODS;
                            }else if (tResponse.getTargetServiceIdentifier().indexOf("psc") > -1) {
                                log.debug("Found PSC response. Processing...");
                                cApplicationName=CCTSApplicationNames.PSC;
                            }
                            log.debug("App:"+cApplicationName);
                            if(tResponse.getTargetError()!=null && tResponse.getTargetError().getErrorDescription()!=null){
                                log.debug("Error: "+tResponse.getTargetError().getErrorDescription());
                                if(cApplicationName!=null){
                                    String errorString=tResponse.getTargetError().getErrorDescription();
                                    String errorCode=tResponse.getTargetError().getErrorCode();
                                    errorString=errorCode!=null || !errorCode.equalsIgnoreCase("")?errorCode+" : "+errorString:errorString;
                                    log.debug("Found error in response : "+errorString);
                                    errors.addError(cApplicationName, errorString);
                                }
                            }else{
                                log.debug("Error is null");
                            }
                        }
                        messageWorkflowCallback.recordError(objectId, errors);
                    }
                }


            } catch (InterruptedException e) {
                log.warn(e);
            } catch (ExecutionException e) {
                log.warn(e);
            }
            //call handlers for the result
        }
    }

    class SynchronousResponseProcessor implements Callable {

        CaXchangeResponseServiceClient responseService;
        String objectId;
        private MessageWorkflowCallback messageWorkflowCallback;
        private long startTime;
        private int timeout;

        public SynchronousResponseProcessor(CaXchangeResponseServiceReference responseRef, MessageWorkflowCallback messageWorkflowCallback, String objectId, GlobusCredential proxy, int timeout) throws org.apache.axis.types.URI.MalformedURIException, RemoteException {
            responseService = new CaXchangeResponseServiceClient(responseRef.getEndpointReference(),proxy);
            this.messageWorkflowCallback=messageWorkflowCallback;
            this.objectId=objectId;
            this.timeout=timeout;
        }


        public ResponseMessage call() throws Exception {
            //only run this for 60 seconds
            if (startTime == 0l) {
                startTime = System.currentTimeMillis();
            }
            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            log.debug("Elapsed time : " + elapsedTime + " seconds");
            if (elapsedTime > timeout) {
                log.debug("Giving up. caXchange never returned a response for more than 60 seconds. Recording Error.");
                messageWorkflowCallback.messageAcknowledgmentFailed(objectId);
                return null;
            }

            try {
                log.debug("Checking caXchange for response");
                return responseService.getResponse();
            } catch (RemoteException e) {
            	//sleep for 3 seconds and check again
                log.info("Response not yet ready. Waiting...");
                Thread.sleep(3000);
                return call();
            }
        }


        protected void finalize() throws Throwable {
            log.debug("Killing listening thread");
            super.finalize();    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    private void logEPR(EndpointReferenceType endpointReference){
      //logging epr info
        log.debug("logging EPR info..");
        log.debug(endpointReference.getAddress());
        log.debug(endpointReference.getAddress().toString());
        log.debug(endpointReference.getAddress().getHost());
        log.debug(endpointReference.getAddress().getPath());
        log.debug(endpointReference.getAddress().getFragment());
        log.debug(endpointReference.getAddress().getQueryString());
        log.debug(endpointReference.getAddress().getRegBasedAuthority());
        log.debug(endpointReference.getAddress().getScheme());
        log.debug(endpointReference.getAddress().getSchemeSpecificPart());
        log.debug(endpointReference.getAddress().getUserinfo());
        log.debug(endpointReference.getAddress().getPort());
        
    }

	public DelegatedCredentialProvider getDelegatedCredentialProvider() {
		return delegatedCredentialProvider;
	}
}
