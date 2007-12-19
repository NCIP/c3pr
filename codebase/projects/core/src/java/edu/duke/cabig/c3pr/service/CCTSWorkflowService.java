package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.CCTSAbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.CCTSWorkflowStatusType;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 6, 2007
 * Time: 4:15:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CCTSWorkflowService<C extends CCTSAbstractMutableDeletableDomainObject> {

    public CCTSWorkflowStatusType getCCTSWofkflowStatus(C cctsObject);

    public void broadcastMessage(C cctsDomainObject) throws C3PRCodedException;


}
