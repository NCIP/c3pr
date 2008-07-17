package edu.duke.cabig.c3pr.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import gov.nih.nci.security.authorization.domainobjects.*;
import gov.nih.nci.security.authorization.domainobjects.User;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.domain.*;

/**
 * Service to handle C3PR Users management <p/> Created by IntelliJ IDEA. User: kherm Date: Aug 24,
 * 2007 Time: 9:41:56 AM To change this template use File | Settings | File Templates.
 */
@Transactional(readOnly = false, rollbackFor = C3PRBaseException.class, noRollbackFor = C3PRBaseRuntimeException.class)
public interface PersonnelService {

    public void save(Investigator user) throws C3PRBaseException;

    /**
     * Will save Research Staff and add appropriate data into CSM
     * 
     * @param staff
     * @throws C3PRBaseException
     * @throws C3PRBaseRuntimeException
     */
    public void save(ResearchStaff staff) throws C3PRBaseException;

    /**
     * Used to update Investigator domain object
     * 
     * @param user
     * @throws C3PRBaseException
     */
    public Investigator merge(Investigator user) throws C3PRBaseException;

    /**
     * Used to update ResearchStaff domain object
     * 
     * @param staff
     * @throws C3PRBaseException
     */
    public void merge(ResearchStaff staff) throws C3PRBaseException;

    /**
     * Get a list of csm groups for the user
     * 
     * @param user
     * @return
     * @throws C3PRBaseException
     */
    public List<C3PRUserGroupType> getGroups(C3PRUser user) throws C3PRBaseException;

    public User getCSMUserByUsername(String userName);

}
