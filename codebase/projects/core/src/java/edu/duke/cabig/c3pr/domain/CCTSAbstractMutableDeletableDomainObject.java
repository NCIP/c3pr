package edu.duke.cabig.c3pr.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Nov 13, 2007
 * Time: 3:25:53 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class CCTSAbstractMutableDeletableDomainObject extends AbstractMutableDeletableDomainObject {


    private CCTSWorkflowStatusType cctsWorkflowStatus;
    private boolean importErrorFlag;
    private String importErrorString;


    @Enumerated(EnumType.STRING)
    public CCTSWorkflowStatusType getCctsWorkflowStatus() {
        return cctsWorkflowStatus;
    }

    public void setCctsWorkflowStatus(CCTSWorkflowStatusType cctsWorkflowStatus) {
        this.cctsWorkflowStatus = cctsWorkflowStatus;
    }


    /**
     * Flag is set to True if there is any
     * error duing import
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
     * Used to manage import process and passing
     * error messages. Will only return the first
     * 100 characters of the error message
     *
     * @return
     */
    @Transient
    public String getImportErrorString() {
    		if ((importErrorString !=null)&& (importErrorString.length() > 100)){
    			importErrorString = importErrorString.substring(0, 100);
    		}

        return importErrorString;
    }

    public void setImportErrorString(String importErrorString) {
        setImportErrorFlag(true);
        this.importErrorString = importErrorString;
    }
}
