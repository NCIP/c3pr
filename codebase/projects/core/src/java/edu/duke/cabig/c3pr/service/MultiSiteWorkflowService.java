package edu.duke.cabig.c3pr.service;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.InteroperableAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySubject;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Dec 6, 2007 Time: 4:15:53 PM To change this template
 * use File | Settings | File Templates.
 */
public interface MultiSiteWorkflowService<C extends InteroperableAbstractMutableDeletableDomainObject> {
    
    public boolean canMultisiteBroadcast(StudyOrganization studyOrganization);
    
    public boolean isMultisiteEnable();
    
    public <T extends AbstractMutableDomainObject> EndPoint handleMultiSiteBroadcast(StudyOrganization studyOrganization, ServiceName multisiteServiceName, APIName multisiteAPIName, List<T> domainObjects);

}
