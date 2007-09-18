package edu.duke.cabig.c3pr.service.impl;

import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.OrganizationService;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.authorization.domainobjects.Application;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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
    private String siteProtectionGroupId;
    private String siteAccessRoleId;

    private CSMObjectIdGenerator siteObjectIdGenerator;

    private Logger log  = Logger.getLogger(OrganizationService.class);

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

    private Group createGroupForOrganization(HealthcareSite organization) throws C3PRBaseException, C3PRBaseRuntimeException {
        Group org = new Group();
        try {
            String siteId = siteObjectIdGenerator.generateId(organization);

            Application app = userProvisioningManager.getApplication(csmApplicationContextName);
            org.setApplication(app);
            org.setGroupDesc(organization.getDescriptionText());
            org.setGroupName(siteId);
            org.setUpdateDate(new Date());
            log.debug("Creating group for new organization:" + siteId);
            userProvisioningManager.createGroup(org);

            ProtectionGroup pg = new ProtectionGroup();
            pg.setApplication(userProvisioningManager.getApplication(csmApplicationContextName));
            pg.setParentProtectionGroup(userProvisioningManager.getProtectionGroupById(siteProtectionGroupId));
            pg.setProtectionGroupName(siteId);
            log.debug("Creating protection group for new organization:" + siteId);
            userProvisioningManager.createProtectionGroup(pg);

            log.debug("Creating Protection Element for new organization:" + siteId);
            ProtectionElement pe = new ProtectionElement();
            pe.setApplication(userProvisioningManager.getApplication(csmApplicationContextName));
            pe.setObjectId(siteId);
            pe.setProtectionElementName(siteId);
            pe.setProtectionElementDescription("Site Protection Element");
            Set pgs = new HashSet();
            pgs.add(pg);
            pe.setProtectionGroups(pgs);
            userProvisioningManager.createProtectionElement(pe);

            userProvisioningManager.assignGroupRoleToProtectionGroup(pg.getProtectionGroupId().toString(),org.getGroupId().toString(),new String[]{siteAccessRoleId});


        } catch (CSObjectNotFoundException e) {
            log.error("###Error getting info for"+ csmApplicationContextName + " application from CSM. Application configuration exception###");
            throw new C3PRBaseRuntimeException("Application configuration problem. Cannot find application '" + csmApplicationContextName + "' in CSM", e);
        }
        catch (CSTransactionException e) {
            log.warn("Could not create group for organization:" + organization.getNciInstituteCode());
            throw new C3PRBaseException("Cannot create group for organization.", e);
        }
        return org;
    }


    public CSMObjectIdGenerator getSiteObjectIdGenerator() {
        return siteObjectIdGenerator;
    }

    public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
        this.siteObjectIdGenerator = siteObjectIdGenerator;
    }


    public String getSiteAccessRoleId() {
        return siteAccessRoleId;
    }

    public void setSiteAccessRoleId(String siteAccessRoleId) {
        this.siteAccessRoleId = siteAccessRoleId;
    }

    public String getSiteProtectionGroupId() {
        return siteProtectionGroupId;
    }

    public void setSiteProtectionGroupId(String siteProtectionGroupId) {
        this.siteProtectionGroupId = siteProtectionGroupId;
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
