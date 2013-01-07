package edu.duke.cabig.c3pr.domain;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.axis.AxisFault;
import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

// TODO: Auto-generated Javadoc
/**
 * The Class EndPoint.
 */
@Entity
@Table(name = "ENDPOINTS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "ENDPOINTS_ID_SEQ") })
public abstract class EndPoint extends AbstractMutableDeletableDomainObject implements Comparable<EndPoint>{
    
    /** Logger for this class. */
    private static final Logger logger = Logger.getLogger(EndPoint.class);
    
    /** The end point property. */
    protected EndPointConnectionProperty endPointProperty;
    
    /** The service name. */
    protected ServiceName serviceName;
    
    /** The api name. */
    protected APIName apiName;
    
    /** The status. */
    protected WorkFlowStatusType status;
    /** The attempt date. */
    private Date attemptDate;
    
    /** The errors. */
    protected List<Error> errors= new ArrayList<Error>();
    
    /** The return value. */
    protected Object returnValue;
    
    /** The study organization. */
    protected StudyOrganization studyOrganization;
    
	/** The c3 pr exception helper. */
	private C3PRExceptionHelper c3PRExceptionHelper;

	/** The c3pr error messages. */
	private MessageSource c3prErrorMessages;
    
    /** The Constant STUDY_XML_CASTOR_MAPPING_FILE_NAME. */
    public static final String STUDY_XML_CASTOR_MAPPING_FILE_NAME="c3pr-study-xml-castor-mapping.xml";
    
    /** The Constant REGISTRATION_XML_CASTOR_MAPPING_FILE_NAME. */
    public static final String REGISTRATION_XML_CASTOR_MAPPING_FILE_NAME="c3pr-registration-xml-castor-mapping.xml";

	/**
	 * Instantiates a new end point.
	 */
	public EndPoint(){
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("error_messages_multisite");
		ResourceBundleMessageSource resourceBundleMessageSource1 = new ResourceBundleMessageSource();
		resourceBundleMessageSource1.setBasename("error_messages_c3pr");
		resourceBundleMessageSource1.setParentMessageSource(resourceBundleMessageSource);
		this.c3prErrorMessages = resourceBundleMessageSource1;
		this.c3PRExceptionHelper = new C3PRExceptionHelper(c3prErrorMessages);
    }
    
    /**
     * Instantiates a new end point.
     * 
     * @param endPointProperty the end point property
     * @param serviceName the service name
     * @param aPIName the a pi name
     */
    public EndPoint(EndPointConnectionProperty endPointProperty, ServiceName serviceName,
                    APIName aPIName) {
        super();
        this.endPointProperty = endPointProperty;
        this.serviceName = serviceName;
        this.apiName = aPIName;
    }

    /**
     * Gets the service.
     * 
     * @return the service
     */
    @Transient
    public abstract Object getService();
    
    /**
     * Gets the aPI.
     * 
     * @return the aPI
     */
    @Transient
    public abstract Method getAPI();
    
    /**
     * Gets the arguments.
     * 
     * @param argument the argument
     * 
     * @return the arguments
     */
    @Transient
    public abstract Object[] getArguments(Object argument);
    
    /**
     * Process return type.
     * 
     * @param returnValue the return value
     * 
     * @return the object
     */
    @Transient
    public abstract Object processReturnType(Object returnValue);
    
    /**
     * Invoke.
     * 
     * @param argument the argument
     * 
     * @throws InvocationTargetException the invocation target exception
     */
    public void invoke(Object argument) throws InvocationTargetException, C3PRCodedRuntimeException{
        try {
            Object service=getService(); 
            Method method=getAPI();
            returnValue=method.invoke(service, getArguments(argument));
            returnValue=processReturnType(returnValue);
            this.setStatus(WorkFlowStatusType.MESSAGE_SEND_CONFIRMED);
        }
        catch (InvocationTargetException e) {
        	this.setStatus(WorkFlowStatusType.MESSAGE_SEND_FAILED);
        	String errorMsg=e.getTargetException().getMessage();
        	Integer code=500;
        	if (e.getTargetException() instanceof AxisFault) {
				AxisFault fault = (AxisFault) e.getTargetException();
				if(fault.detail instanceof ConnectException){
					code= getCode("C3PR.EXCEPTION.ENDPOINT.CONNECTION_EXCEPTION.CODE");
					C3PRCodedRuntimeException codedRuntimeException= c3PRExceptionHelper.getRuntimeException(code);
					this.errors.add(new Error(this.toString(),code.toString(),codedRuntimeException.getCodedExceptionMesssage()));
					throw codedRuntimeException;
				}
			}
        	if(errorMsg.contains("AuthorizationException")){
        		Pattern p=Pattern.compile(".*/CN=(.*)\".*");
                Matcher matcher=p.matcher(errorMsg);
                if(matcher.find()){
                	code= getCode("C3PR.EXCEPTION.MULTISITE.CODE");
					C3PRCodedRuntimeException codedRuntimeException= c3PRExceptionHelper.getRuntimeException(getCode("C3PR.EXCEPTION.ENDPOINT.AUTHORIZATION_EXCEPTION.CODE"),new String[] { matcher.group(1), this.apiName.getDisplayName(), this.studyOrganization.getHealthcareSite().getName() });
					this.errors.add(new Error(this.toString(),code.toString(),codedRuntimeException.getCodedExceptionMesssage()));
					throw codedRuntimeException;
                }
        	}
            Pattern p=Pattern.compile(".*([0-9][0-9][0-9]):(.*)");
            Matcher matcher=p.matcher(errorMsg);
            if(matcher.find()){
            	code= getCode("C3PR.EXCEPTION.MULTISITE.BUSINESS_ERROR.CODE");
            	errorMsg=matcher.group(2);
				C3PRCodedRuntimeException codedRuntimeException= c3PRExceptionHelper.getRuntimeException(code,new String[] {this.studyOrganization.getHealthcareSite().getName(), errorMsg });
				this.errors.add(new Error(this.toString(), code.toString(), codedRuntimeException.getCodedExceptionMesssage()));
				throw codedRuntimeException;
            }
            System.out.println("code not found.");
            this.errors.add(new Error(this.toString(),code.toString(),"Error invoking "+(this.apiName==null?"":this.apiName.getDisplayName())+":"+errorMsg));
            throw e;
        }
        catch (Exception e) {
            this.errors.add(new Error(this.toString(),"-1",e.getMessage()));
            this.setStatus(WorkFlowStatusType.MESSAGE_SEND_FAILED);
            throw new RuntimeException(e);
        }finally{
            setAttemptDate(new Date());
        }
    }
    
    /**
     * Gets the xML utils.
     * 
     * @return the xML utils
     */
    @Transient
    public XMLUtils getXMLUtils(){
        XMLUtils xmlUtils=null;
        if(serviceName==ServiceName.REGISTRATION)
            xmlUtils= new XMLUtils(new XmlMarshaller(REGISTRATION_XML_CASTOR_MAPPING_FILE_NAME));
        return xmlUtils;
    }

    /**
     * Gets the xML marshaller.
     * 
     * @return the xML marshaller
     */
    @Transient
    protected XmlMarshaller getXMLMarshaller(){
        if(serviceName==ServiceName.REGISTRATION)
            return new XmlMarshaller(REGISTRATION_XML_CASTOR_MAPPING_FILE_NAME);
        return null;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(EndPoint endPoint) {
        return this.attemptDate.compareTo(endPoint.getAttemptDate());
    }
    
    /**
     * Gets the end point property.
     * 
     * @return the end point property
     */
    @ManyToOne
    @JoinColumn(name="ENDPOINT_PROP_ID")
    @Cascade(value = { CascadeType.LOCK })
    public EndPointConnectionProperty getEndPointProperty() {
        return endPointProperty;
    }

    /**
     * Sets the end point property.
     * 
     * @param endPointProperty the new end point property
     */
    public void setEndPointProperty(EndPointConnectionProperty endPointProperty) {
        this.endPointProperty = endPointProperty;
    }

    /**
     * Gets the errors.
     * 
     * @return the errors
     */
    @OneToMany(fetch=FetchType.EAGER)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "endpoint_id", nullable=false)
    public List<Error> getErrors() {
        return errors;
    }

    /**
     * Sets the errors.
     * 
     * @param errors the new errors
     */
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    /**
     * Gets the service name.
     * 
     * @return the service name
     */
    @Enumerated(EnumType.STRING)
    public ServiceName getServiceName() {
        return serviceName;
    }

    /**
     * Sets the service name.
     * 
     * @param serviceName the new service name
     */
    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Gets the api name.
     * 
     * @return the api name
     */
    @Enumerated(EnumType.STRING)
    public APIName getApiName() {
        return apiName;
    }

    /**
     * Sets the api name.
     * 
     * @param apiName the new api name
     */
    public void setApiName(APIName apiName) {
        this.apiName = apiName;
    }

    /**
     * Gets the status.
     * 
     * @return the status
     */
    @Enumerated(EnumType.STRING)
    public WorkFlowStatusType getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * 
     * @param status the new status
     */
    public void setStatus(WorkFlowStatusType status) {
        this.status = status;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	if(this.endPointProperty==null){
    		return "Incomplete Endpoint Object";
    	}else if(this.endPointProperty.getEndPointType()==null){
    		return "Incomplete Endpoint Object";
    	}else if(this.serviceName==null){
    		return "Incomplete Endpoint Object";
    	}else if(this.apiName==null){
    		return "Incomplete Endpoint Object";
    	}
        return "Endpoint("
        +(this.endPointProperty==null?"":StringUtils.getBlankIfNull(this.endPointProperty.getUrl()))
        +"of type"
        +(this.endPointProperty.getEndPointType()==null?"":StringUtils.getBlankIfNull(this.endPointProperty.getEndPointType().getCode()))
        +". Service:"
        +(this.serviceName==null?"":StringUtils.getBlankIfNull(this.serviceName.getCode()))
        +" -API:"
        +(this.apiName==null?"":StringUtils.getBlankIfNull(this.apiName.getCode()))
        +")";
    }

    /**
     * Gets the attempt date.
     * 
     * @return the attempt date
     */
    @Temporal(TemporalType.TIMESTAMP)
    public Date getAttemptDate() {
        return attemptDate;
    }

    /**
     * Sets the attempt date.
     * 
     * @param attemptDate the new attempt date
     */
    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }

    /**
     * Gets the last attempt error.
     * 
     * @return the last attempt error
     */
    @Transient
    public Error getLastAttemptError(){
        List<Error> tempList = new ArrayList<Error>();
        tempList.addAll(getErrors());
        Collections.sort(tempList);
        if (tempList.size() == 0) return null;
        return tempList.get(tempList.size() - 1);
    }

    /**
     * Gets the return value.
     * 
     * @return the return value
     */
    @Transient
    public Object getReturnValue() {
        return returnValue;
    }

    /**
     * Gets the study organization.
     * 
     * @return the study organization
     */
    @ManyToOne
    @JoinColumn(name = "sto_id", nullable = false)
	public StudyOrganization getStudyOrganization() {
		return studyOrganization;
	}

	/**
	 * Sets the study organization.
	 * 
	 * @param studyOrganization the new study organization
	 */
	public void setStudyOrganization(StudyOrganization studyOrganization) {
		this.studyOrganization = studyOrganization;
	}
	
	/**
	 * Gets the c3 pr exception helper.
	 * 
	 * @return the c3 pr exception helper
	 */
	@Transient
	public C3PRExceptionHelper getC3PRExceptionHelper() {
		return c3PRExceptionHelper;
	}

	/**
	 * Gets the code.
	 * 
	 * @param errortypeString the errortype string
	 * 
	 * @return the code
	 */
	@Transient
	public int getCode(String errortypeString) {
		return Integer.parseInt(this.c3prErrorMessages.getMessage(
				errortypeString, null, null));
	}

	/**
	 * Gets the c3pr error messages.
	 * 
	 * @return the c3pr error messages
	 */
	@Transient
	public MessageSource getC3prErrorMessages() {
		return c3prErrorMessages;
	}
}
