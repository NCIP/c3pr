package edu.duke.cabig.c3pr.domain;

import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

@Entity
@Table(name = "ENDPOINTS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@GenericGenerator(name = "id-generator", strategy = "native", parameters = { @Parameter(name = "sequence", value = "ENDPOINTS_ID_SEQ") })
public abstract class EndPoint extends AbstractMutableDeletableDomainObject implements Comparable<EndPoint>{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(EndPoint.class);
    
    protected EndPointConnectionProperty endPointProperty;
    protected ServiceName serviceName;
    protected APIName apiName;
    protected WorkFlowStatusType status;
    //private Timestamp attemptDate;
    private Date attemptDate;
    protected List<Error> errors= new ArrayList<Error>();
    protected Object returnValue;
    protected StudyOrganization studyOrganization;

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
    
    @Transient
    public abstract Object processReturnType(Object returnValue);
    
    public void invoke(Object argument) throws InvocationTargetException{
        try {
            Object service=getService(); 
            Method method=getAPI();
            returnValue=method.invoke(service, getArguments(argument));
            returnValue=processReturnType(returnValue);
            this.setStatus(WorkFlowStatusType.MESSAGE_SEND_CONFIRMED);
        }
        catch (InvocationTargetException e) {
        	e.getTargetException().printStackTrace();
            String errorMsg=e.getTargetException().getMessage();
            String code="500";
            Pattern p=Pattern.compile(".*([0-9][0-9][0-9]):(.*)");
            Matcher matcher=p.matcher(errorMsg);
            if(matcher.find()){
                code=matcher.group(1);
                errorMsg=matcher.group(2);
            }else{
                System.out.println("code not found.");
            }
            this.errors.add(new Error(this.toString(),code,"Error invoking "+this.apiName.getCode()+":"+errorMsg));
            this.setStatus(WorkFlowStatusType.MESSAGE_SEND_FAILED);
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
    
    @Transient
    protected XMLUtils getXMLUtils(){
        XMLUtils xmlUtils=null;
        if(serviceName==ServiceName.STUDY)
            xmlUtils= new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        else if(serviceName==ServiceName.REGISTRATION)
            xmlUtils= new XMLUtils(new XmlMarshaller("c3pr-registration-xml-castor-mapping.xml"));
        return xmlUtils;
    }

    @Transient
    protected XmlMarshaller getXMLMarshaller(){
        if(serviceName==ServiceName.STUDY)
            return new XmlMarshaller("c3pr-study-xml-castor-mapping.xml");
        else if(serviceName==ServiceName.REGISTRATION)
            return new XmlMarshaller("c3pr-registration-xml-castor-mapping.xml");
        return null;
    }
    
    public int compareTo(EndPoint endPoint) {
        return this.attemptDate.compareTo(endPoint.getAttemptDate());
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

    @OneToMany(fetch=FetchType.EAGER)
    @Cascade(value = { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
    @JoinColumn(name = "endpoint_id", nullable=false)
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
    
    @Override
    public String toString() {
        return "Endpoint("+this.endPointProperty.getUrl()+"of type"+this.endPointProperty.getEndPointType().getCode()+". Service:"+this.serviceName.getCode()+" -API:"+this.apiName.getCode()+")";
    }

//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name="attempt_name")
//    public Timestamp getAttemptDateInternal() {
//        return attemptDate;
//    }
//
//    public void setAttemptDateInternal(Timestamp attemptDate) {
//        this.attemptDate = attemptDate;
//    }
    
//    @Transient
//    public Date getAttemptDate() {
//        return attemptDate;
//    }
//
//    public void setAttemptDate(Date attemptDate) {
//        this.attemptDate = new Timestamp(attemptDate.getTime());
//    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getAttemptDate() {
        return attemptDate;
    }

    public void setAttemptDate(Date attemptDate) {
        this.attemptDate = attemptDate;
    }

    @Transient
    public Error getLastAttemptError(){
        List<Error> tempList = new ArrayList<Error>();
        tempList.addAll(getErrors());
        Collections.sort(tempList);
        if (tempList.size() == 0) return null;
        return tempList.get(tempList.size() - 1);
    }

    @Transient
    public Object getReturnValue() {
        return returnValue;
    }

    @ManyToOne
    //@Cascade(value = { CascadeType.LOCK})
    @JoinColumn(name = "sto_id", nullable = false)
	public StudyOrganization getStudyOrganization() {
		return studyOrganization;
	}

	public void setStudyOrganization(StudyOrganization studyOrganization) {
		this.studyOrganization = studyOrganization;
	}
}
