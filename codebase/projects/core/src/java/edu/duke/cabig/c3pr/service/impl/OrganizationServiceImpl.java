/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service.impl;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.OrganizationService;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Sep 7, 2007 Time: 1:32:14 PM To change this template
 * use File | Settings | File Templates.
 */
public class OrganizationServiceImpl implements OrganizationService {

    private UserProvisioningManager userProvisioningManager;

    private HealthcareSiteDao healthcareSiteDao;

    private String csmApplicationContextName;

    private String siteProtectionGroupId;

    private String siteAccessRoleId;

    private Logger log = Logger.getLogger(OrganizationService.class);
    
    
    public void saveNotification(Organization organization) throws C3PRBaseException, C3PRBaseRuntimeException {
    	healthcareSiteDao.save(organization);
    }
    
    public Organization merge(Organization organization) throws C3PRBaseException, C3PRBaseRuntimeException {
    	return healthcareSiteDao.merge(organization);
    }
    
    public void save(HealthcareSite site) throws C3PRBaseException, C3PRBaseRuntimeException {
    	Organization existingOrg = healthcareSiteDao.getByPrimaryIdentifierFromLocal(site.getPrimaryIdentifier());
		if(existingOrg != null){
			throw new C3PRBaseException("Duplicate Primary identifier detected.");
		}
    	healthcareSiteDao.save(site);
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


    public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public String getCsmApplicationContextName() {
        return csmApplicationContextName;
    }

    public void setCsmApplicationContextName(String csmApplicationContextName) {
        this.csmApplicationContextName = csmApplicationContextName;
    }

    public String getSiteNameByNciIdentifier(String ctepId) {
        if (ctepId == null || ctepId.trim().equals("")) return "";
        
        try {
            return healthcareSiteDao.getByPrimaryIdentifier(ctepId).getName();
        } catch (Exception e) {
            log.warn("The site name could not be retrieved by NCI ID Code.");
            return "";
        }
    }
}
