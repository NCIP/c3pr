package edu.duke.cabig.c3pr.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 13, 2007 Time: 3:25:53 PM To change this template
 * use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class InteroperableAbstractMutableDeletableDomainObject extends AbstractMutableDeletableDomainObject {

    private WorkFlowStatusType cctsWorkflowStatus;

    private WorkFlowStatusType multisiteWorkflowStatus;
    
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

    @Enumerated(EnumType.STRING)
    public WorkFlowStatusType getMultisiteWorkflowStatus() {
        return multisiteWorkflowStatus;
    }

    public void setMultisiteWorkflowStatus(WorkFlowStatusType multisiteWorkflowStatus) {
        this.multisiteWorkflowStatus = multisiteWorkflowStatus;
    }

    @Transient
    public abstract List<EndPoint> getEndpoints();

    public void setEndpoints(List<EndPoint> endpoints) {
        this.endpoints = endpoints;
    }
}
