package edu.duke.cabig.c3pr.utils;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import edu.duke.cabig.c3pr.domain.CCTSAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.esb.MessageResponseHandler;

public abstract class AbstractRequestHandler<C extends CCTSAbstractMutableDeletableDomainObject> implements MessageResponseHandler{
    
    private OpenSessionInViewInterceptor openSessionInViewInterceptor;
    
    public void setOpenSessionInViewInterceptor(
                    OpenSessionInViewInterceptor openSessionInViewInterceptor) {
        this.openSessionInViewInterceptor = openSessionInViewInterceptor;
    }

    private WebRequest preProcess() {
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        openSessionInViewInterceptor.preHandle(webRequest);
        return webRequest;
    }

    private void postProcess(WebRequest webRequest) {
        openSessionInViewInterceptor.afterCompletion(webRequest, null);
    }
    
    public void processMessage(String registrationXML) {
        WebRequest webRequest=preProcess();
        try{
        C messageObject=messageAsObject(registrationXML);
        messageObject.setMultisiteWorkflowStatus(multisiteWorkflowStatus());
        handlerRequest(messageObject);
        }catch(RuntimeException e){
            throw e;
        }finally{
            postProcess(webRequest);
        }
    }
    
    public abstract CCTSWorkflowStatusType multisiteWorkflowStatus();
    
    public abstract C messageAsObject(String message);
    
    public abstract void handlerRequest(C messageObject);
}
