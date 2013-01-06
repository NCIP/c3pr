package edu.duke.cabig.c3pr.domain.factory;

import org.apache.log4j.Logger;

import org.globus.gsi.GlobusCredential;
import org.springframework.context.MessageSource;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.EndPointType;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.esb.DelegatedCredentialProvider;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating EndPoint objects.
 */
public class EndPointFactory {
	
	/** Logger for this class. */
	private static final Logger logger = Logger
			.getLogger(EndPointFactory.class);

    /** The delegated credential provider. */
    private DelegatedCredentialProvider delegatedCredentialProvider;
    
    /** The exception helper. */
    private C3PRExceptionHelper exceptionHelper;
    
    /** The c3pr error messages. */
    private MessageSource c3prErrorMessages;
    
    /**
     * New instance.
     * 
     * @param multisiteServiceName the multisite service name
     * @param multisiteAPIName the multisite api name
     * @param endPointProperty the end point property
     * 
     * @return the end point
     */
    public EndPoint newInstance(ServiceName multisiteServiceName, APIName multisiteAPIName, EndPointConnectionProperty endPointProperty){
        EndPoint endPoint=null;
        if(endPointProperty.getEndPointType()==EndPointType.GRID){
            endPoint=new GridEndPoint(endPointProperty, multisiteServiceName, multisiteAPIName, getCredential());
        }
        
        return endPoint;
    }
    
    /**
     * Gets the end point.
     * 
     * @param multisiteServiceName the multisite service name
     * @param multisiteAPIName the multisite api name
     * @param studyOrganization the study organization
     * 
     * @return the end point
     */
    public EndPoint getEndPoint(ServiceName multisiteServiceName, APIName multisiteAPIName, StudyOrganization studyOrganization){
        EndPoint endPoint=studyOrganization.getEndPoint(multisiteServiceName, multisiteAPIName);
        if(endPoint==null){
        	EndPointConnectionProperty endPointProperty=null;
        	if(multisiteServiceName==ServiceName.REGISTRATION){
        		endPointProperty=studyOrganization.getHealthcareSite().getRegistrationEndPointProperty();
        	}
            endPoint=this.newInstance(multisiteServiceName, multisiteAPIName, endPointProperty);
            if(endPoint == null){
            	throw this.exceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.ENDPOINT_INCORRECT_CONFIGURATION.CODE"),new String[]{studyOrganization.getHealthcareSite().getName()});
            }
            studyOrganization.addEndPoint(endPoint);
            endPoint.setStudyOrganization(studyOrganization);
        }else{
            if(endPoint instanceof GridEndPoint){
                ((GridEndPoint)endPoint).setGlobusCredential(getCredential());
            }
        }
        return endPoint;
    }

    /**
     * Gets the credential.
     * 
     * @return the credential
     */
    private GlobusCredential getCredential(){
        if(delegatedCredentialProvider==null){
			logger.debug("getCredential() - delegatedCredentialProvider is null, return null credential");
            return null;
        }
        GlobusCredential credential=delegatedCredentialProvider.provideDelegatedCredentials().getCredential();
        if(credential==null){
			logger.debug("getCredential() - GlobusCredential is null, return null credential");
        }else{
			logger.debug("getCredential() - found delegatedCredentialProvider, returning credential");
        }
        return credential;
    }

    /**
     * Sets the delegated credential provider.
     * 
     * @param delegatedCredentialProvider the new delegated credential provider
     */
    public void setDelegatedCredentialProvider(DelegatedCredentialProvider delegatedCredentialProvider) {
        this.delegatedCredentialProvider = delegatedCredentialProvider;
    }
    
    /**
     * Sets the exception helper.
     * 
     * @param exceptionHelper the new exception helper
     */
    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
		this.exceptionHelper = exceptionHelper;
	}
    
    /**
     * Gets the code.
     * 
     * @param errortypeString the errortype string
     * 
     * @return the code
     */
    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }
    
    /**
     * Sets the c3pr error messages.
     * 
     * @param errorMessages the new c3pr error messages
     */
    public void setC3prErrorMessages(MessageSource errorMessages) {
		c3prErrorMessages = errorMessages;
	}
}
