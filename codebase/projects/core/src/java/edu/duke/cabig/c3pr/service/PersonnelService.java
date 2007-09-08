package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service to handle C3PR Users management
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 24, 2007
 * Time: 9:41:56 AM
 * To change this template use File | Settings | File Templates.
 */
//ToDo refactor to different services for ResearchStaff and Investigator
@Transactional(readOnly = false, rollbackFor = C3PRBaseException.class,
        noRollbackFor = C3PRBaseRuntimeException.class)
public interface PersonnelService {


    public void save(Investigator user) throws C3PRBaseException, C3PRBaseRuntimeException;

    /**
     * Will save Research Staff and add appropriate data into CSM
     *
     * @param user
     * @throws C3PRBaseException
     * @throws C3PRBaseRuntimeException
     */
    public void save(ResearchStaff user) throws C3PRBaseException, C3PRBaseRuntimeException;

}
