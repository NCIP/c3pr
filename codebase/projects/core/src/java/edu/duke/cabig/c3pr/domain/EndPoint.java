package edu.duke.cabig.c3pr.domain;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

@Entity
@Table(name = "ENDPOINTS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "ENDPOINTS_ID_SEQ") })
public abstract class EndPoint extends AbstractMutableDeletableDomainObject{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(EndPoint.class);
    
    protected EndPointConnectionProperty endPointProperty;
    protected ServiceName serviceName;
    protected APIName apiName;
    protected WorkFlowStatusType status;
    protected List<Error> errors= new ArrayList<Error>();

    public EndPoint(){
        
    }
    
    public EndPoint(EndPointConnectionProperty endPointProperty, ServiceName serviceName,
                    APIName aPIName) {
        super();
        this.endPointProperty = endPointProperty;
        this.serviceName = serviceName;
        this.apiName = aPIName;
    }

    @Transient
    public abstract Object getService();
    
    @Transient
    public abstract Method getAPI();
    
    @Transient
    public abstract Object[] getArguments(Object argument);
    
    public void invoke(Object argument) throws InvocationTargetException{
        Object service=getService(); 
        Method method=getAPI();
        Error error=new Error();
        error.setErrorDate(new Date());
        try {
            method.invoke(service, getArguments(argument));
            this.setStatus(WorkFlowStatusType.MESSAGE_SEND_CONFIRMED);
        }
        catch (IllegalArgumentException e) {
            error.setErrorCode("-1");
            error.setErrorMessage(e.getMessage());
            this.errors.add(error);
            this.setStatus(WorkFlowStatusType.MESSAGE_SEND_FAILED);
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            error.setErrorCode("-1");
            error.setErrorMessage(e.getMessage());
            this.errors.add(error);
            this.setStatus(WorkFlowStatusType.MESSAGE_SEND_FAILED);
            throw new RuntimeException(e);
        }
        catch (InvocationTargetException e) {
            error.setErrorCode("500");
            error.setErrorMessage("Error invoking "+this.apiName.getCode()+":"+e.getMessage());
            this.errors.add(error);
            this.setStatus(WorkFlowStatusType.MESSAGE_SEND_FAILED);
            throw e;
        }
    }
    
    @Transient
    protected XMLUtils getXMLUtils(){
        XMLUtils xmlUtils=null;
        if(serviceName==ServiceName.STUDY)
            xmlUtils= new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        else if(serviceName==ServiceName.REGISTRATION)
            xmlUtils= new XMLUtils(new XmlMarshaller("c3pr-registration-xml-castor-mapping.xml"));
        return xmlUtils;
    }

    @ManyToOne
    @JoinColumn(name="ENDPOINT_PROP_ID")
    @Cascade(value = { CascadeType.LOCK })
    public EndPointConnectionProperty getEndPointProperty() {
        return endPointProperty;
    }

    public void setEndPointProperty(EndPointConnectionProperty endPointProperty) {
        this.endPointProperty = endPointProperty;
    }

    @OneToMany
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "endpoint_id")
    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    @Enumerated(EnumType.STRING)
    public ServiceName getServiceName() {
        return serviceName;
    }

    public void setServiceName(ServiceName serviceName) {
        this.serviceName = serviceName;
    }

    @Enumerated(EnumType.STRING)
    public APIName getApiName() {
        return apiName;
    }

    public void setApiName(APIName apiName) {
        this.apiName = apiName;
    }

    @Enumerated(EnumType.STRING)
    public WorkFlowStatusType getStatus() {
        return status;
    }

    public void setStatus(WorkFlowStatusType status) {
        this.status = status;
    }

}
