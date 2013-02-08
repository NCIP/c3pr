/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.BaseInvestigatorDataContainer;
import edu.duke.cabig.c3pr.domain.BaseResearchStaffDataContainer;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.RecipientScheduledNotification;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.RemotePersonUser;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import gov.nih.nci.security.authorization.domainobjects.User;

/**
 * Service to handle C3PR Users management <p/> Created by IntelliJ IDEA. User: kherm Date: Aug 24,
 * 2007 Time: 9:41:56 AM To change this template use File | Settings | File Templates.
 */
@Transactional(readOnly = false, rollbackFor = C3PRBaseException.class, noRollbackFor = C3PRBaseRuntimeException.class)
public interface PersonnelService {

    public void save(Investigator user) throws C3PRBaseException;

    /**
     * Will save Research Staff without CSM
     * 
     * @param staff
     * @throws C3PRBaseException
     * @throws C3PRBaseRuntimeException
     */
    public void save(PersonUser staff) throws C3PRBaseException;
    
    /**
     * Will save Research Staff and add appropriate data into CSM
     * 
     * @param staff
     * @throws C3PRBaseException
     * @throws C3PRBaseRuntimeException
     */
    public void saveUser(PersonUser staff) throws C3PRBaseException;

    /**
     * Used to update Investigator domain object
     * 
     * @param user
     * @throws C3PRBaseException
     */
    public Investigator merge(Investigator user) throws C3PRBaseException;

    /**
     * Get a list of csm groups for the user
     * 
     * @param user
     * @return
     * @throws C3PRBaseException
     */
    public List<C3PRUserGroupType> getGroups(C3PRUser user) throws C3PRBaseException;
    
    /**
     * Get a list of csm groups for the emailId
     * Used by the dashboardController
     * @param emailId
     * @return
     * @throws C3PRBaseException
     */
    public List<C3PRUserGroupType> getGroups(String emailId) throws C3PRBaseException;
    
    /*
     * used exclusively for the dashboard and inbox view of notifications
     */
    public List<RecipientScheduledNotification> getRecentNotifications(HttpServletRequest request);
    
    /*
     * Used while saving the subject to add the creating org.
     */
    public List<HealthcareSite> getUserOrganizations(User user);

    public User getCSMUserByUsername(String userName);
    
    public BaseResearchStaffDataContainer convertLocalPersonUserToRemotePersonUser(LocalPersonUser localPersonUser, RemotePersonUser remotePersonUser);
    
    public BaseInvestigatorDataContainer convertLocalInvestigatorToRemoteInvestigator(LocalInvestigator localInvestigator,RemoteInvestigator remoteInvestigator);

}
