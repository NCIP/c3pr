package edu.duke.cabig.c3pr.service;

import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 7, 2007 Time: 1:31:17 PM To change this template
 * use File | Settings | File Templates.
 */
@Transactional(readOnly = false, rollbackFor = C3PRBaseException.class, noRollbackFor = C3PRBaseRuntimeException.class)
public interface OrganizationService {

    public void save(HealthcareSite organization) throws C3PRBaseException,
                    C3PRBaseRuntimeException;

    /*
     * merge also calls save on the dao because he dao's save calls a saveOrUpdate which works just
     * fine. thsi methiod was created so that we caould avoid calling createGroupForOrganization
     * 
     * @see edu.duke.cabig.c3pr.service.OrganizationService#merge(edu.duke.cabig.c3pr.domain.HealthcareSite)
     */
    public void merge(HealthcareSite organization) throws C3PRBaseException,
                    C3PRBaseRuntimeException;

    public OrganizationDao getOrganizationDao();

    public String getSiteNameByNciIdentifier(String nciId);
    
    public void saveNotification(Organization organization) throws C3PRBaseException, C3PRBaseRuntimeException;
    
    public void mergeNotification(Organization organization) throws C3PRBaseException, C3PRBaseRuntimeException;

}
