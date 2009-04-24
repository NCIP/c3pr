package edu.duke.cabig.c3pr.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.axis.message.MessageElement;
import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.utils.XMLUtils;

import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

@Entity
@DiscriminatorValue("GRID")
public class GridEndPoint extends EndPoint {

    private GlobusCredential globusCredential;
    
    public GridEndPoint(){}
    
    public GridEndPoint(EndPointConnectionProperty endPointProperty,
                    ServiceName multisiteServiceName, APIName multisiteAPIName, GlobusCredential globusCredential) {
        super(endPointProperty, multisiteServiceName, multisiteAPIName);
        this.globusCredential = globusCredential;
    }

    @Override
    @Transient
    public Method getAPI() {

        Method method = null;
        try {
                method = getService().getClass().getMethod(apiName.getCode(),
                                new Class[] { Message.class });
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("Error invoking the client api",e);
        }
        catch (SecurityException e) {
            throw new RuntimeException("Error invoking the client api",e);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Error invoking the client api",e);
        }
        return method;
    }

    @Override
    @Transient
    public Object getService() {
        Object service = null;
        try {
            Class kClass = Class.forName(serviceName.getCode());
            if (endPointProperty.getIsAuthenticationRequired()) {
            	System.out.println("authentication required. creating service with credential");
                Constructor constructor = kClass.getConstructor(String.class,
                                GlobusCredential.class);
                service = constructor.newInstance(new Object[] { endPointProperty.getUrl(),
                        globusCredential });
            }
            else {
            	System.out.println("authentication not required. creating service without credential");
                Constructor constructor = kClass.getConstructor(String.class);
                service = constructor.newInstance(endPointProperty.getUrl());
            }
        }
        catch (IllegalArgumentException e) {
            throw new RuntimeException("Error creating the service instance",e);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException("Error creating the service instance",e);
        }
        catch (SecurityException e) {
            throw new RuntimeException("Error creating the service instance",e);
        }
        catch (NoSuchMethodException e) {
            throw new RuntimeException("Error creating the service instance",e);
        }
        catch (InstantiationException e) {
            throw new RuntimeException("Error creating the service instance",e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Error creating the service instance",e);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Error creating the service instance",e);
        }
        return service;
    }

    @Override
    @Transient
    public Object[] getArguments(Object argument) {
        if(argument==null){
            return new Object[]{new Message()};
        }
        Message message=getXMLUtils().buildMessageFromDomainObjects((List<AbstractMutableDomainObject>)argument);
        return new Object[]{message};
    }
    
    public void setGlobusCredential(GlobusCredential globusCredential) {
        this.globusCredential = globusCredential;
    }

    @Override
    public Object processReturnType(Object returnType) {
        if(returnType==null) return null;
        if (returnType instanceof Message) {
            Message message = (Message) returnType;
            try {
                List arguments = getXMLUtils().getArguments(message);
                return arguments;
            }
            catch (RemoteException e) {
                this.getErrors().add(new Error(this.toString(),"-1","error deserializing the returned value"));
                return null;
            }
        }
        
        return null;
    }
}
