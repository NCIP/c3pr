package edu.duke.cabig.c3pr.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.dao.EndpointDao;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.factory.EndPointFactory;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class MultiSiteHandlerService {

    private EndPointFactory endPointFactory;
    
    private EndpointDao endpointDao;
    
    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;
    
    public void setEndpointDao(EndpointDao endpointDao) {
        this.endpointDao = endpointDao;
    }

    public <T extends AbstractMutableDomainObject> void  handle(ServiceName multisiteServiceName, APIName multisiteAPIName, EndPointConnectionProperty endPointProperty, List<T> domainObjects) throws C3PRCodedException{
        EndPoint endPoint=endPointFactory.newInstance(multisiteServiceName, multisiteAPIName, endPointProperty);
        try {
            endPoint.invoke(domainObjects);
        }
        catch (InvocationTargetException e) {
            throw this.exceptionHelper.getException(getCode(""),new String[]{endPoint.getApiName().getCode(), endPoint.getServiceName().getCode()}, e);
        }catch (Exception e) {
            throw this.exceptionHelper.getException(-1, e);
        }
        finally{
            endpointDao.save(endPoint);
        }
    }
    
    public int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
    
    public void setEndPointFactory(EndPointFactory endPointFactory) {
        this.endPointFactory = endPointFactory;
    }

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

}
