package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 13, 2007 Time: 3:25:53 PM To change this template
 * use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class InteroperableAbstractMutableDeletableDomainObject extends AbstractMutableDeletableDomainObject {

    private WorkFlowStatusType cctsWorkflowStatus;

    private boolean importErrorFlag;

    private String importErrorString;
    
    private String cctsErrorString;
    
    protected List<EndPoint> endpoints=new ArrayList<EndPoint>();

    public String getCctsErrorString() {
        return cctsErrorString;
    }

    public void setCctsErrorString(String cctsErrorString) {
        this.cctsErrorString = cctsErrorString;
    }

    @Enumerated(EnumType.STRING)
    public WorkFlowStatusType getCctsWorkflowStatus() {
        return cctsWorkflowStatus;
    }

    public void setCctsWorkflowStatus(WorkFlowStatusType cctsWorkflowStatus) {
        this.cctsWorkflowStatus = cctsWorkflowStatus;
    }

    /**
     * Flag is set to True if there is any error duing import
     * 
     * @return
     */
    @Transient
    public boolean isImportErrorFlag() {
        return importErrorFlag;
    }

    public void setImportErrorFlag(boolean importErrorFlag) {
        this.importErrorFlag = importErrorFlag;
    }

    /**
     * Used to manage import process and passing error messages. Will only return the first 100
     * characters of the error message
     * 
     * @return
     */
    @Transient
    public String getImportErrorString() {
        if ((importErrorString != null) && (importErrorString.length() > 100)) {
            importErrorString = importErrorString.substring(0, 100);
        }

        return importErrorString;
    }

    public void setImportErrorString(String importErrorString) {
        setImportErrorFlag(true);
        this.importErrorString = importErrorString;
    }

    @Transient
    public WorkFlowStatusType getMultisiteWorkflowStatus() {
        return getLastAttemptedEndpoint()!=null?getLastAttemptedEndpoint().getStatus():null;
    }

    @Transient
    public abstract List<EndPoint> getEndpoints();

    public void setEndpoints(List<EndPoint> endpoints) {
        this.endpoints = endpoints;
    }
    
    public void addEndPoint(EndPoint endPoint){
        this.getEndpoints().add(endPoint);
    }
    
    public EndPoint getEndPoint(ServiceName serviceName, APIName apiName){
        for(EndPoint endPoint: this.endpoints){
            if(endPoint.getServiceName()==serviceName && endPoint.getApiName()==apiName)
                return endPoint;
        }
        return null;
    }
    
    @Transient
    public EndPoint getLastAttemptedEndpoint(){
        List<EndPoint> tempList = new ArrayList<EndPoint>();
        tempList.addAll(getEndpoints());
        Collections.sort(tempList);
        if (tempList.size() == 0) return null;
        return tempList.get(tempList.size() - 1);
    }
    
    @Transient
    public List<Error> getRecentErrors(){
        List<Error> tempList = new ArrayList<Error>();
        for(EndPoint endPoint:getEndpoints())
            tempList.addAll(endPoint.getErrors());
        Collections.sort(tempList);
        return tempList;
    }
}
