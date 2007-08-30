package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;

/**
 * Service to handle C3PRUsers
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Aug 24, 2007
 * Time: 9:41:56 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PersonnelService {
    public void save(C3PRUser c3prUser) throws C3PRBaseException;

    public void assignUserToGroup(C3PRUser c3PRUser, C3PRUserGroupType groupName) throws C3PRBaseException;
}
