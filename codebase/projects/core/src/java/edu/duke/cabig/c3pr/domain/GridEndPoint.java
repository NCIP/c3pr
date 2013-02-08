/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.ServiceName;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

// TODO: Auto-generated Javadoc
/**
 * The Class GridEndPoint.
 */
@Entity
@DiscriminatorValue("GRID")
public class GridEndPoint extends EndPoint {

    /** The globus credential. */
    private GlobusCredential globusCredential;
    
    /**
     * Instantiates a new grid end point.
     */
    public GridEndPoint(){}
    
    /**
     * Instantiates a new grid end point.
     * 
     * @param endPointProperty the end point property
     * @param multisiteServiceName the multisite service name
     * @param multisiteAPIName the multisite api name
     * @param globusCredential the globus credential
     */
    public GridEndPoint(EndPointConnectionProperty endPointProperty,
                    ServiceName multisiteServiceName, APIName multisiteAPIName, GlobusCredential globusCredential) {
        super(endPointProperty, multisiteServiceName, multisiteAPIName);
        this.globusCredential = globusCredential;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.EndPoint#getAPI()
     */
    @Override
    @Transient
    public Method getAPI() {

        Method method = null;
        try {
                method = getService().getClass().getMethod(apiName.getCode(),
                                new Class[] { Message.class });
        }
        catch (Exception e) {
            throw new RuntimeException("Error invoking the client api",e);
        }
        return method;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.EndPoint#getService()
     */
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
        catch (Exception e) {
            throw new RuntimeException("Error creating the service instance",e);
        }
        return service;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.EndPoint#getArguments(java.lang.Object)
     */
    @Override
    @Transient
    public Object[] getArguments(Object argument) {
        if(argument==null){
            return new Object[]{new Message()};
        }
        Message message=getXMLUtils().buildMessageFromDomainObjects((List<AbstractMutableDomainObject>)argument);
        return new Object[]{message};
    }
    
    /**
     * Sets the globus credential.
     * 
     * @param globusCredential the new globus credential
     */
    public void setGlobusCredential(GlobusCredential globusCredential) {
        this.globusCredential = globusCredential;
    }

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.domain.EndPoint#processReturnType(java.lang.Object)
     */
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
