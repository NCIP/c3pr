package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.springframework.dao.DataAccessException;

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
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

	/** The remote session. */
	private RemoteSession remoteSession;
	
	/** The user provisioning manager. */
	private UserProvisioningManager userProvisioningManager;
	
	 /** The log. */
 	private Logger log = Logger.getLogger(HealthcareSiteDao.class);
	
	/**
	 * Sets the user provisioning manager.
	 * 
	 * @param userProvisioningManager the new user provisioning manager
	 */
	public void setUserProvisioningManager(
			UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}

	/** The csm application context name. */
	private String csmApplicationContextName;

    /** The site protection group id. */
    private String siteProtectionGroupId;

    /** The site access role id. */
    private String siteAccessRoleId;

    /** The site object id generator. */
    private CSMObjectIdGenerator siteObjectIdGenerator;

	/**
	 * Sets the csm application context name.
	 * @param csmApplicationContextName the new csm application context name
	 */
	public void setCsmApplicationContextName(String csmApplicationContextName) {
		this.csmApplicationContextName = csmApplicationContextName;
	}

	/**
	 * Sets the site protection group id.
	 * @param siteProtectionGroupId the new site protection group id
	 */
	public void setSiteProtectionGroupId(String siteProtectionGroupId) {
		this.siteProtectionGroupId = siteProtectionGroupId;
	}

	/**
	 * Sets the site access role id.
	 * @param siteAccessRoleId the new site access role id
	 */
	public void setSiteAccessRoleId(String siteAccessRoleId) {
		this.siteAccessRoleId = siteAccessRoleId;
	}

	/**
	 * Sets the site object id generator.
	 * @param siteObjectIdGenerator the new site object id generator
	 */
	public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
		this.siteObjectIdGenerator = siteObjectIdGenerator;
	}
	
	/**
	 * Sets the remote session.
	 * @param remoteSession the new remote session
	 */
	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}

	/*
	 * (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
	 */
	public Class<HealthcareSite> domainClass() {
		return HealthcareSite.class;
	}


	/**
	 * Gets all HealthcarSite objects.
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
	 * @param subnames the subnames
	 * @return the subnames
	 * @throws C3PRBaseException the c3 pr base exception
	 * @throws C3PRBaseRuntimeException the c3 pr base runtime exception
	 */
	public List<HealthcareSite> getBySubnames(String[] subnames) throws C3PRBaseRuntimeException, C3PRBaseException {
		
		List<HealthcareSite> remoteHealthcareSites = new ArrayList<HealthcareSite>();

		//get all by name first
		RemoteHealthcareSite remoteHealthcareSiteName = new RemoteHealthcareSite();
		remoteHealthcareSiteName.setName(subnames[0]);
		remoteHealthcareSites
				.addAll(getFromResolver(remoteHealthcareSiteName));

		//get all by nciId next
		RemoteHealthcareSite remoteHealthcareSiteNciID = new RemoteHealthcareSite();
		remoteHealthcareSiteNciID.setNciInstituteCode(subnames[0]);
		remoteHealthcareSites
				.addAll(getFromResolver(remoteHealthcareSiteNciID));
		
		//save both sets to the db
		updateDatabaseWithRemoteContent(remoteHealthcareSites);

		return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES,
				EXACT_MATCH_PROPERTIES);
	}

	/**
	 * Gets by nci institute code. If we find a match in local db dont go to COPPA.
	 * Goto Copa if no match is fouind in local db.
	 * We always defer to local db in cases of queries where only one result is expected.
	 * 
	 * @param nciInstituteCode the nci institute code
	 * @return the HealthcareSite
	 * @throws C3PRBaseException 	 * @throws C3PRBaseRuntimeException 	 */
	public HealthcareSite getByNciInstituteCode(String nciInstituteCode) {
		
		HealthcareSite healthcareSite = getByNciInstituteCodeFromLocal(nciInstituteCode);
		if(healthcareSite == null){
			List<HealthcareSite> remoteHealthcareSites = new ArrayList<HealthcareSite>();
			remoteHealthcareSites.addAll(getFromResolver(new RemoteHealthcareSite()));
			updateDatabaseWithRemoteContent(remoteHealthcareSites);
			
			return CollectionUtils
					.firstElement((List<HealthcareSite>) getHibernateTemplate()
							.find(
									"from HealthcareSite h where h.nciInstituteCode = ?",
									nciInstituteCode));
		}
		return healthcareSite;
	}
	
	/**
	 * Gets by nci institute code from local.
	 * 
	 * @param nciInstituteCode the nci institute code
	 * @return the HealthcareSite
	 * @throws C3PRBaseException 	 * @throws C3PRBaseRuntimeException 	 */
	public HealthcareSite getByNciInstituteCodeFromLocal(String nciInstituteCode) {
		
		return CollectionUtils
				.firstElement((List<HealthcareSite>) getHibernateTemplate()
					.find("from HealthcareSite h where h.nciInstituteCode = ?",nciInstituteCode));
	}


	/**
	 * Gets the organizations from the resolver.
	 * 
	 * @param healthcareSite the healthcare site
	 * @return the healthcare sites
	 */
	public List<HealthcareSite> getFromResolver(HealthcareSite healthcareSite) {

		RemoteHealthcareSite remoteHealthcareSite = new RemoteHealthcareSite();
		if(healthcareSite.getName() == null){
			remoteHealthcareSite.setName("");
		}else{
			remoteHealthcareSite.setName(healthcareSite.getName());
		}
		if(healthcareSite.getNciInstituteCode() == null){
			remoteHealthcareSite.setNciInstituteCode(null);
		}else{
			remoteHealthcareSite.setNciInstituteCode(healthcareSite.getNciInstituteCode());
		}
		
		remoteHealthcareSite.setAddress(healthcareSite.getAddress());
		if(healthcareSite.getAddress().getCity() == null){
			remoteHealthcareSite.getAddress().setCity("");
		}
		if(healthcareSite.getAddress().getCountryCode() == null){
			remoteHealthcareSite.getAddress().setCountryCode("");
		}
		
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
	 * @param remoteHealthcareSiteList the health care site list
	 * @throws C3PRBaseException 	 * @throws C3PRBaseRuntimeException 	 */
	public void updateDatabaseWithRemoteContent(List<HealthcareSite> remoteHealthcareSiteList) {

		try {
			for (HealthcareSite remoteHealthcareSite : remoteHealthcareSiteList) {
				if(remoteHealthcareSite != null){
					RemoteHealthcareSite remoteHealthcareSiteTemp = (RemoteHealthcareSite)remoteHealthcareSite;
					HealthcareSite healthcareSiteFromDatabase = getByUniqueIdentifier(remoteHealthcareSiteTemp
							.getExternalId());
					if (healthcareSiteFromDatabase != null) {
						//this healthcare site already exists....make sure it's NCI institute code is not currently in db and update the database
						//Not doing anything for now....this pre-existing site should be up to date.
					} else {
						HealthcareSite healthcareSiteWithSameNCICode = null;
						// check if a healthcare site with this NCI code already exists in DB
						healthcareSiteWithSameNCICode = getByNciInstituteCodeFromLocal(remoteHealthcareSiteTemp.getNciInstituteCode());
						if(healthcareSiteWithSameNCICode == null){
							// this site doesn't exist
							createGroupForOrganization(remoteHealthcareSiteTemp);
							getHibernateTemplate().save(remoteHealthcareSiteTemp);
						} else{
							log.error("Healthcare site with NCI Institute Code:" + remoteHealthcareSiteTemp.getNciInstituteCode() + "already exists in database");
						}
					}
					getHibernateTemplate().flush();
				} else {
					log.error("Null remoteHealthcareSite in the list in updateDatabaseWithRemoteContent");
				}
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (C3PRBaseRuntimeException e) {
			e.printStackTrace();
		} catch (C3PRBaseException e) {
			e.printStackTrace();
		} 
	}

	/*
	 * Gets by nci institute code.
	 * 
	 * @param nciInstituteCode the nci institute code
	 * @return the HealthcareSite
	 * @throws C3PRBaseException 	 * @throws C3PRBaseRuntimeException 	 */
	public HealthcareSite getByUniqueIdentifier(String externalId) {
		return CollectionUtils
		.firstElement((List<HealthcareSite>) getHibernateTemplate()
			.find("from RemoteHealthcareSite h where h.externalId = ?", externalId));
	}

	/**
	 * Creates the group for organization.
	 * 
	 * @param organization the organization
	 * 
	 * @return the group
	 * 
	 * @throws C3PRBaseException the c3 pr base exception
	 * @throws C3PRBaseRuntimeException the c3 pr base runtime exception
	 */
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
  	 * @return the list< healthcare site>
  	 * @throws C3PRBaseException   	 * @throws C3PRBaseRuntimeException   	 */
	
    public List<HealthcareSite> searchByExample(HealthcareSite hcs, boolean isWildCard){
    	
    	List<HealthcareSite> remoteHealthcareSites = new ArrayList<HealthcareSite>();
		remoteHealthcareSites.addAll(getFromResolver(hcs));
		updateDatabaseWithRemoteContent(remoteHealthcareSites);

		List<HealthcareSite> result = new ArrayList<HealthcareSite>();
        Example example = Example.create(hcs).excludeZeroes().ignoreCase();
        try {
            Criteria orgCriteria = getHibernateTemplate().getSessionFactory()
            .getCurrentSession().createCriteria(HealthcareSite.class);
           
            if(hcs.getNciInstituteCode() != null && hcs.getNciInstituteCode() != ""){
            	orgCriteria.add(Expression.ilike("nciInstituteCode", "%" + hcs.getNciInstituteCode() + "%"));
            }
            if(hcs.getName() != null && hcs.getName() != ""){
            	orgCriteria.add(Expression.ilike("name", "%" + hcs.getName() + "%"));
            }
            
            if (isWildCard) {
                example.enableLike(MatchMode.ANYWHERE);
            }
            example.excludeProperty("studyEndPointProperty");
            example.excludeProperty("registrationEndPointProperty");
            
            orgCriteria.addOrder(Order.asc("name"));  
            orgCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            orgCriteria.setMaxResults(50);
            result = orgCriteria.add(example).list();
        }
        catch (Exception e) {
        	log.error(e.getMessage());
        }
        return result;
    }

	
	/**
	 * Merge remote with local lists.
	 * 
	 * @param remoteHealthcareSiteList the remote healthcare site list
	 * @param localHealthcareSiteList the local healthcare site list
	 * 
	 * @return the list< healthcare site>
	 */
	public List<HealthcareSite> mergeRemoteWithLocalLists(List<HealthcareSite> remoteHealthcareSiteList,List<HealthcareSite> localHealthcareSiteList){
		List<HealthcareSite> mergedHealthcareSiteList = new ArrayList<HealthcareSite>();
		Set<HealthcareSite> uniqueHealthcareSitesList = new HashSet<HealthcareSite>();
		uniqueHealthcareSitesList.addAll(remoteHealthcareSiteList);
		uniqueHealthcareSitesList.addAll(localHealthcareSiteList);
		mergedHealthcareSiteList.addAll(uniqueHealthcareSitesList);
		
		return mergedHealthcareSiteList;
	}
	
	
	  /**
  	 * Gets the remote organizations.
  	 * 
  	 * @param organization the organization
  	 * @return the remote organizations
  	 */
  	@SuppressWarnings("unchecked")
		public List<HealthcareSite> getRemoteOrganizations(HealthcareSite organization){
	  	HealthcareSite searchCriteria = new RemoteHealthcareSite();
	  	searchCriteria.setNciInstituteCode(organization.getNciInstituteCode());
	  	return (List)remoteSession.find(searchCriteria);
	  }
	  
}
