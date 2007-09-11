package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.OrganizationService;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 7, 2007
 * Time: 1:32:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class OrganizationServiceImpl implements OrganizationService {

    private UserProvisioningManager userProvisioningManager;
    private OrganizationDao organizationDao;
    private String csmApplicationContextName;

    public void save(HealthcareSite site) throws C3PRBaseException, C3PRBaseRuntimeException {

        createGroupForOrganization(site);
        organizationDao.save(site);
    }

    /*
     * merge also calls save on the dao because he dao's save calls a saveOrUpdate which works just fine.
     * thsi methiod was created so that we caould avoid calling createGroupForOrganization
     * @see edu.duke.cabig.c3pr.service.OrganizationService#merge(edu.duke.cabig.c3pr.domain.HealthcareSite)
     */
    public void merge(HealthcareSite site) throws C3PRBaseException, C3PRBaseRuntimeException{
    	organizationDao.save(site);
    }

    public Group createGroupForOrganization(HealthcareSite organization) throws C3PRBaseException, C3PRBaseRuntimeException {
        Group org = new Group();
        try {
            org.setApplication(userProvisioningManager.getApplication(csmApplicationContextName));
        } catch (CSObjectNotFoundException e) {
            throw new C3PRBaseRuntimeException("Application configuration problem. Cannot find application '" + csmApplicationContextName + "' in CSM", e);
        }

        org.setGroupDesc(organization.getDescriptionText());
        org.setGroupName(organization.getNciInstituteCode());
        org.setUpdateDate(new Date());

        try {
            userProvisioningManager.createGroup(org);
        } catch (CSTransactionException e) {
            throw new C3PRBaseException("Cannot create group for organization.", e);
        }
        return org;
    }


    public UserProvisioningManager getUserProvisioningManager() {
        return userProvisioningManager;
    }

    public void setUserProvisioningManager(UserProvisioningManager userProvisioningManager) {
        this.userProvisioningManager = userProvisioningManager;
    }

    public OrganizationDao getOrganizationDao() {
        return organizationDao;
    }

    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }


    public String getCsmApplicationContextName() {
        return csmApplicationContextName;
    }

    public void setCsmApplicationContextName(String csmApplicationContextName) {
        this.csmApplicationContextName = csmApplicationContextName;
    }
}
