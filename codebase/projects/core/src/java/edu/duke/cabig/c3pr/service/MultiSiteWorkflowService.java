/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.service;

import java.util.List;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.InteroperableAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
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
