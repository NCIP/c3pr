package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DataAccessException;

import com.semanticbits.coppa.infrastructure.RemoteEntitiesUtils;
import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.emory.mathcs.backport.java.util.Collections;
import edu.nwu.bioinformatics.commons.CollectionUtils;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.authorization.domainobjects.Application;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.ProtectionElement;
import gov.nih.nci.security.authorization.domainobjects.ProtectionGroup;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;

/**
 * The Class HealthcareSiteDao.
 * 
 * @author Ramakrishna
 * @author kherm
 * @version 1.0
 */
public class HealthcareSiteDao extends OrganizationDao {

	/** The SUBSTRING_ match_ properties. */
	private List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name",
			"nciInstituteCode");

	/** The EXACT_ match_ properties. */
	private List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

	private RemoteSession remoteSession;
	
	private UserProvisioningManager userProvisioningManager;
	
	 private Logger log = Logger.getLogger(HealthcareSiteDao.class);
	
	public UserProvisioningManager getUserProvisioningManager() {
		return userProvisioningManager;
	}

	public void setUserProvisioningManager(
			UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}

	private String csmApplicationContextName;

    private String siteProtectionGroupId;

    private String siteAccessRoleId;

    private CSMObjectIdGenerator siteObjectIdGenerator;


	public String getCsmApplicationContextName() {
		return csmApplicationContextName;
	}

	public void setCsmApplicationContextName(String csmApplicationContextName) {
		this.csmApplicationContextName = csmApplicationContextName;
	}

	public String getSiteProtectionGroupId() {
		return siteProtectionGroupId;
	}

	public void setSiteProtectionGroupId(String siteProtectionGroupId) {
		this.siteProtectionGroupId = siteProtectionGroupId;
	}

	public String getSiteAccessRoleId() {
		return siteAccessRoleId;
	}

	public void setSiteAccessRoleId(String siteAccessRoleId) {
		this.siteAccessRoleId = siteAccessRoleId;
	}

	public CSMObjectIdGenerator getSiteObjectIdGenerator() {
		return siteObjectIdGenerator;
	}

	public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
		this.siteObjectIdGenerator = siteObjectIdGenerator;
	}

	public RemoteSession getRemoteSession() {
		return remoteSession;
	}

	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
	 */
	public Class<HealthcareSite> domainClass() {
		return HealthcareSite.class;
	}

	/*
	 * Returns all HealthcarSite objects (non-Javadoc)
	 * 
	 * @see edu.duke.cabig.c3pr.dao.HealthcareSiteDao#getAll()
	 */
	/**
	 * Gets the all.
	 * 
	 * @return HealthcareSite
	 */
	public List<HealthcareSite> getAll() {
		
		List<HealthcareSite> dataBaseHealthcareSites = new ArrayList<HealthcareSite>();
		dataBaseHealthcareSites = getHibernateTemplate().find("from HealthcareSite");
		
		List<HealthcareSite> remoteHealthcareSites = new ArrayList<HealthcareSite>();
		remoteHealthcareSites = getFromResolver(new RemoteHealthcareSite());
		
		return mergeRemoteWithLocalLists(remoteHealthcareSites, dataBaseHealthcareSites);
	}

	/**
	 * Clear.
	 */
	public void clear() {
		getHibernateTemplate().clear();
	}

	/**
	 * Gets by subnames.
	 * 
	 * @param subnames
	 *            the subnames
	 * 
	 * @return the subnames
	 * @throws C3PRBaseException 
	 * @throws C3PRBaseRuntimeException 
	 */
	public List<HealthcareSite> getBySubnames(String[] subnames) throws C3PRBaseRuntimeException, C3PRBaseException {
		
		List<HealthcareSite> remoteHealthcareSites = new ArrayList<HealthcareSite>();

		remoteHealthcareSites
				.addAll(getFromResolver(new RemoteHealthcareSite()));

		updateDatabaseWithRemoteContent(remoteHealthcareSites);

		return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES,
				EXACT_MATCH_PROPERTIES);

	}

	/**
	 * Gets by nci institute code. DOES NOT fetch from COPPA
	 * 
	 * @param nciInstituteCode
	 *            the nci institute code
	 * 
	 * @return the HealthcareSite
	 * @throws C3PRBaseException 
	 * @throws C3PRBaseRuntimeException 
	 */
	public HealthcareSite getByNciInstituteCodeFromDatabaseOnly(String nciInstituteCode) {
		
		return CollectionUtils
				.firstElement((List<HealthcareSite>) getHibernateTemplate()
						.find(
								"from HealthcareSite h where h.nciInstituteCode = ?",
								nciInstituteCode));
	}
	
	/**
	 * Gets by nci institute code.
	 * 
	 * @param nciInstituteCode
	 *            the nci institute code
	 * 
	 * @return the HealthcareSite
	 * @throws C3PRBaseException 
	 * @throws C3PRBaseRuntimeException 
	 */
	public HealthcareSite getByNciInstituteCode(String nciInstituteCode) {
		
		List<HealthcareSite> remoteHealthcareSites = new ArrayList<HealthcareSite>();
		remoteHealthcareSites
		.addAll(getFromResolver(new RemoteHealthcareSite()));

		updateDatabaseWithRemoteContent(remoteHealthcareSites);
		
		return CollectionUtils
				.firstElement((List<HealthcareSite>) getHibernateTemplate()
						.find(
								"from HealthcareSite h where h.nciInstituteCode = ?",
								nciInstituteCode));
	}

	/**
	 * Gets the organizations from the resolver.
	 * 
	 * @param healthcareSite
	 *            the healthcare site
	 * 
	 * @return the healthcare sites
	 */
	public List<HealthcareSite> getFromResolver(HealthcareSite healthcareSite) {

		HealthcareSite remoteHealthcareSite = new RemoteHealthcareSite();

		List<Object> objectList = remoteSession.find(remoteHealthcareSite);
		List<HealthcareSite> healthcareSiteList = new ArrayList<HealthcareSite>();

		HealthcareSite tempRemoteHealthcareSite;
		for (Object object : objectList) {
			tempRemoteHealthcareSite = (HealthcareSite) object;
			healthcareSiteList.add(tempRemoteHealthcareSite);
		}
		
		return healthcareSiteList;
	}

	/**
	 * Update database with remote content.
	 * 
	 * @param remoteHealthcareSiteList
	 *            the health care site list
	 * @throws C3PRBaseException 
	 * @throws C3PRBaseRuntimeException 
	 */
	public void updateDatabaseWithRemoteContent(
			List<HealthcareSite> remoteHealthcareSiteList) {

		try {
			for (HealthcareSite remoteHealthcareSite : remoteHealthcareSiteList) {
				RemoteHealthcareSite remoteHealthcareSiteTemp = (RemoteHealthcareSite)remoteHealthcareSite;
				HealthcareSite healthcareSiteFromDatabase = getByUniqueIdentifier(remoteHealthcareSiteTemp
						.getNciInstituteCode());
				if (healthcareSiteFromDatabase != null) {
					// this guy exists....copy latest remote data into the existing
					// object...which is done by the interceptor
					copyRemotePropertiesFromSourceToTarget(healthcareSiteFromDatabase, remoteHealthcareSiteTemp);
				} else {
					// this guy doesnt exist
					createGroupForOrganization(remoteHealthcareSiteTemp);
					getHibernateTemplate().save(remoteHealthcareSiteTemp);
				}
				getHibernateTemplate().flush();
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
		} catch (C3PRBaseException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Gets by nci institute code.
	 * 
	 * @param nciInstituteCode
	 *            the nci institute code
	 * 
	 * @return the HealthcareSite
	 * @throws C3PRBaseException 
	 * @throws C3PRBaseRuntimeException 
	 */
	public HealthcareSite getByUniqueIdentifier(String nciInstituteCode) {
		return CollectionUtils
		.firstElement((List<HealthcareSite>) getHibernateTemplate()
				.find(
						"from HealthcareSite h where h.nciInstituteCode = ?",
						nciInstituteCode));
	}

	public Group createGroupForOrganization(HealthcareSite organization)
			throws C3PRBaseException, C3PRBaseRuntimeException {
		Group org = new Group();
		try {
			String siteId = siteObjectIdGenerator.generateId(organization);

			Application app = userProvisioningManager
					.getApplication(csmApplicationContextName);
			org.setApplication(app);
			org.setGroupDesc(organization.getDescriptionText());
			org.setGroupName(siteId);
			org.setUpdateDate(new Date());
			log.debug("Creating group for new organization:" + siteId);
			userProvisioningManager.createGroup(org);

			ProtectionGroup pg = new ProtectionGroup();
			pg.setApplication(userProvisioningManager
					.getApplication(csmApplicationContextName));
			pg.setParentProtectionGroup(userProvisioningManager
					.getProtectionGroupById(siteProtectionGroupId));
			pg.setProtectionGroupName(siteId);
			log.debug("Creating protection group for new organization:"
					+ siteId);
			userProvisioningManager.createProtectionGroup(pg);

			log.debug("Creating Protection Element for new organization:"
					+ siteId);
			ProtectionElement pe = new ProtectionElement();
			pe.setApplication(userProvisioningManager
					.getApplication(csmApplicationContextName));
			pe.setObjectId(siteId);
			pe.setProtectionElementName(siteId);
			pe.setProtectionElementDescription("Site Protection Element");
			Set pgs = new HashSet();
			pgs.add(pg);
			pe.setProtectionGroups(pgs);
			userProvisioningManager.createProtectionElement(pe);

			userProvisioningManager.assignGroupRoleToProtectionGroup(pg
					.getProtectionGroupId().toString(), org.getGroupId()
					.toString(), new String[] { siteAccessRoleId });

		} catch (CSObjectNotFoundException e) {
			log
					.error("###Error getting info for"
							+ csmApplicationContextName
							+ " application from CSM. Application configuration exception###");
			throw new C3PRBaseRuntimeException(
					"Application configuration problem. Cannot find application '"
							+ csmApplicationContextName + "' in CSM", e);
		} catch (CSTransactionException e) {
			log.warn("Could not create group for organization:"
					+ organization.getNciInstituteCode());
			throw new C3PRBaseException(
					"Cannot create group for organization.", e);
		}
		return org;
	}
	
	  /**
     * Search by example.
     * 
     * @param hcs the hcs
     * @param isWildCard the is wild card
     * 
     * @return the list< healthcare site>
	 * @throws C3PRBaseException 
	 * @throws C3PRBaseRuntimeException 
     */
	
    public List<HealthcareSite> searchByExample(HealthcareSite hcs, boolean isWildCard){
    	
    	List<HealthcareSite> remoteHealthcareSites = new ArrayList<HealthcareSite>();
		remoteHealthcareSites
		.addAll(getFromResolver(hcs));

		updateDatabaseWithRemoteContent(remoteHealthcareSites);
        List<HealthcareSite> result = new ArrayList<HealthcareSite>();
        Example example = Example.create(hcs).excludeZeroes().ignoreCase();
        try {
            Criteria orgCriteria = getSession().createCriteria(Organization.class);
            orgCriteria.addOrder(Order.asc("name"));  
            orgCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            orgCriteria.setMaxResults(50);
            orgCriteria.add(Restrictions.ilike("nciInstituteCode", "%" + hcs.getNciInstituteCode() + "%"));
            if (isWildCard) {
                example.enableLike(MatchMode.ANYWHERE);
            }
            example.excludeProperty("studyEndPointProperty");
            example.excludeProperty("registrationEndPointProperty");
            result = orgCriteria.add(example).list();
        }
        catch (Exception e) {
        	log.error(e.getMessage());
        }
        return result;
    }

	
	public List<HealthcareSite> mergeRemoteWithLocalLists(List<HealthcareSite> remoteHealthcareSiteList,List<HealthcareSite> localHealthcareSiteList){
		List<HealthcareSite> mergedHealthcareSiteList = new ArrayList<HealthcareSite>();
		Set<HealthcareSite> uniqueHealthcareSitesList = new HashSet<HealthcareSite>();
		uniqueHealthcareSitesList.addAll(remoteHealthcareSiteList);
		uniqueHealthcareSitesList.addAll(localHealthcareSiteList);
		mergedHealthcareSiteList.addAll(uniqueHealthcareSitesList);
		
		return mergedHealthcareSiteList;
	}
	
	public void copyRemotePropertiesFromSourceToTarget(HealthcareSite targetHealthcareSite,HealthcareSite sourceHealthcareSite){
		List<String> remoteProperties = new ArrayList<String>();
		remoteProperties = RemoteEntitiesUtils.getRemoteProperties(sourceHealthcareSite.getClass());
		BeanWrapperImpl beanWrapperSource = new BeanWrapperImpl(sourceHealthcareSite);
		BeanWrapperImpl beanWrapperTarget = new BeanWrapperImpl(targetHealthcareSite);
		for (int i=0; i< remoteProperties.size();i++){
				// copy property value from remote to entity
				if(beanWrapperSource.isReadableProperty(remoteProperties.get(i))){
					beanWrapperTarget.setPropertyValue(remoteProperties.get(i), beanWrapperSource.getPropertyValue(remoteProperties.get(i)));
				}
			} 
	}
		

}
