package edu.duke.cabig.c3pr.domain;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

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


    @Enumerated(EnumType.STRING)
    public CCTSWorkflowStatusType getCctsWorkflowStatus() {
        return cctsWorkflowStatus;
    }

    public void setCctsWorkflowStatus(CCTSWorkflowStatusType cctsWorkflowStatus) {
        this.cctsWorkflowStatus = cctsWorkflowStatus;
    }
}
