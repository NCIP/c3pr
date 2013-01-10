/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.esb.impl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContextImpl;
import org.apache.axis.types.URI;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.esb.BroadcastException;
import edu.duke.cabig.c3pr.esb.CCTSApplicationNames;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseHandler;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseHandlerSet;
import edu.duke.cabig.c3pr.esb.CaXchangeMessageResponseNotifier;
import edu.duke.cabig.c3pr.esb.DelegatedCredentialProvider;
import edu.duke.cabig.c3pr.esb.MessageWorkflowCallback;
import edu.duke.cabig.c3pr.esb.OperationNameEnum;
import edu.duke.cabig.c3pr.esb.ResponseErrors;
import gov.nih.nci.caxchange.Message;

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
public class MockCaXchangeMessageBroadcaster implements CCTSMessageBroadcaster, CaXchangeMessageResponseNotifier {

    private String action="";
    
    private Integer responseTime;
    
    private List<String> responseErrorApplications;

    private CaXchangeMessageResponseHandlerSet messageResponseHandlers = new CaXchangeMessageResponseHandlerSet();

    private Logger log = Logger.getLogger(MockCaXchangeMessageBroadcaster.class);
    private MessageWorkflowCallback messageWorkflowCallback;

    private int timeout;
    
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

        String externalId = localMetadata.getExternalIdentifier();

        try {
        	if(action.equals("THROW_RemoteException")){
        		throw new RemoteException("Deliberate Remote Exception");
        	}else if(action.equals("THROW_MalformedURIException")){
        		throw new MalformedURIException("Deliberate MalformedURIException Exception");
        	}
            log.debug("Sending message to caXchange");
            if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendSuccessful(externalId);
            }
        } catch (RemoteException e) {
            //log.error("caXchange could not process request", e);
            if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendFailed(externalId);
            }
            throw new BroadcastException(e);
        }catch (MalformedURIException e) {
			//e.printStackTrace();
			//log.error("Could not instantiate CaXchangeRequestProcessorClient");
        	if (messageWorkflowCallback != null) {
                messageWorkflowCallback.messageSendFailed(externalId);
            }
			throw new BroadcastException(e);
		}

        //check on the response asynchronously only if someone is interested
        checkResponseAsynchronously(externalId);
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
    	return "";
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
    	return "";
    }
    
    
    /** private method used by the broadcastCoppa overloaded methods
     * 
     * @param xchangeMessage
     * @return
     * @throws BroadcastException
     */
    private String broadcastCoppaMessage(Message xchangeMessage) throws BroadcastException{
    	return "";
    }
    
    
    
    /**
     * Check response asynchronously.
     * 
     * @param responseRef the response ref
     * @param externalId the external id
     * @param proxy the proxy
     */
    private void checkResponseAsynchronously(String externalId){
    	if (messageWorkflowCallback != null || messageResponseHandlers.size() > 0) {
            log.debug("Will track response from caXchange");
            try {
                FutureTask asyncTask = new AsynchronousResponseRetreiver(new SynchronousResponseProcessor(messageWorkflowCallback, externalId, timeout),SecurityContextHolder.getContext().getAuthentication());
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

    public void setNotificationHandler(MessageWorkflowCallback handler) {
        this.messageWorkflowCallback = handler;
    }


    class AsynchronousResponseRetreiver extends FutureTask {

        Authentication auth;

        public AsynchronousResponseRetreiver(Callable callable, Authentication auth) {
            super(callable);
            this.auth = auth;
        }


        protected void done() {
            try {
                String response = (String)get();
                if (response != null) {

                    log.debug("Received response from caXchange");
                    log.debug("caXchange Response follows.....\n");

                    String objectId = response;
                    log.debug("Received response from caXchange for externalId" + objectId);

                    log.debug("Setting authentication object in SecurityContext.");
                    SecurityContext ctx = new SecurityContextImpl();
                    ctx.setAuthentication(this.auth);
                    SecurityContextHolder.setContext(ctx);

                    if (action.equals("SUCCESS")) {
                        log.debug("Received delivery confirmation from caXchange");

                        messageWorkflowCallback.messageSendConfirmed(objectId);

                        // notify response handlers
                        log.debug("Notifying " + messageResponseHandlers.size() + " handlers");
                        messageResponseHandlers.notifyAll(objectId, null);

                    }
                    if (action.equals("FAILURE")) {
                        log.debug("Received failure from caXchange");
                        messageWorkflowCallback.messageSendFailed(objectId);
                        ResponseErrors<CCTSApplicationNames> errors=new ResponseErrors<CCTSApplicationNames>();
                        log.debug("looking at caXchange error..");
                        if(responseErrorApplications.contains("CAXCHANGE"))
                            errors.addError(CCTSApplicationNames.CAXCHANGE, "Some caXchange Error");
                        else
                            log.debug("caXchange Error is null....");
                        log.debug("looking at aplication level error..");
                        CCTSApplicationNames cApplicationName=null;
                        if(responseErrorApplications.contains("C3D"))
                            errors.addError(CCTSApplicationNames.C3D, "Some C3D Error");
                        if(responseErrorApplications.contains("CAAERS"))
                            errors.addError(CCTSApplicationNames.CAAERS, "Some CAAERS Error");
                        if(responseErrorApplications.contains("CTODS"))
                            errors.addError(CCTSApplicationNames.CTODS, "Some CTODS Error");
                        if(responseErrorApplications.contains("PSC"))
                            errors.addError(CCTSApplicationNames.PSC, "Some PSC Error");
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

        String objectId;
        private MessageWorkflowCallback messageWorkflowCallback;
        private long startTime;
        private int timeout;

        public SynchronousResponseProcessor(MessageWorkflowCallback messageWorkflowCallback, String objectId, int timeout) throws org.apache.axis.types.URI.MalformedURIException, RemoteException {
            this.messageWorkflowCallback=messageWorkflowCallback;
            this.objectId=objectId;
            this.timeout=timeout;
        }


        public String call() throws Exception {
            //only run this for 60 seconds
            if (startTime == 0l) {
                startTime = System.currentTimeMillis();
            }
            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
            log.debug("Elapsed time : " + elapsedTime + " seconds");
            if (elapsedTime > timeout) {
                log.debug("Giving up. caXchange never returned a response for more than"+timeout+"  seconds. Recording Error.");
                messageWorkflowCallback.messageAcknowledgmentFailed(objectId);
                return null;
            }

            try {
                log.debug("Checking caXchange for response");
                if(action.equals("SHOULD_Timeout") || elapsedTime < responseTime){
                	throw new RemoteException("Deliberate EPR Exception"); 
                }
                return objectId;
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

	public DelegatedCredentialProvider getDelegatedCredentialProvider() {
		return null;
	}

	public void setDelegatedCredentialProvider(
			DelegatedCredentialProvider delegatedCredentialProvider) {
		// TODO Auto-generated method stub
		
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setResponseTime(Integer responseTime) {
		this.responseTime = responseTime;
	}

	public void setResponseErrorApplications(List<String> responseErrorApplications) {
		this.responseErrorApplications = responseErrorApplications;
	}
}
