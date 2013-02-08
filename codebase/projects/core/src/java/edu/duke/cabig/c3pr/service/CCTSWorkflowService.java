/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.constants.WorkFlowStatusType;
import edu.duke.cabig.c3pr.domain.InteroperableAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Dec 6, 2007 Time: 4:15:53 PM To change this template
 * use File | Settings | File Templates.
 */
public interface CCTSWorkflowService<C extends InteroperableAbstractMutableDeletableDomainObject> {

    public WorkFlowStatusType getCCTSWofkflowStatus(C cctsObject);

    public void broadcastMessage(C cctsDomainObject) throws C3PRCodedException;

}
