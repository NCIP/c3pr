package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import gov.nih.nci.security.authorization.domainobjects.Group;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 7, 2007
 * Time: 1:31:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface OrganizationService {

    public void save(HealthcareSite organization) throws C3PRBaseException, C3PRBaseRuntimeException;
    
    public void merge(HealthcareSite organization) throws C3PRBaseException, C3PRBaseRuntimeException;
    
    public Group createGroupForOrganization(HealthcareSite organization) throws C3PRBaseException, C3PRBaseRuntimeException;
    
    public OrganizationDao getOrganizationDao();

}
