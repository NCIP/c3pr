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
import org.springframework.transaction.annotation.Transactional;

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.utils.StringUtils;
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
 * @author Ramakrishna, Vinay Gangoli, kherm
 * @version 1.0
 */
public class HealthcareSiteDao extends OrganizationDao {

	/** The SUBSTRING_ match_ properties. */
	private List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name",
			"identifiersAssignedToOrganization.value");

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

    @Override
    public HealthcareSite getById(int arg0) {
    	HealthcareSite healthcareSite = super.getById(arg0);
    	initialize(healthcareSite);
    	return healthcareSite;
    }

	public void initialize(HealthcareSite healthcareSite){
        getHibernateTemplate().initialize(healthcareSite.getHealthcareSiteInvestigators());
        getHibernateTemplate().initialize(healthcareSite.getIdentifiersAssignedToOrganization());
        for(OrganizationAssignedIdentifier organizationAssignedIdentifier: healthcareSite.getOrganizationAssignedIdentifiers()){
        	if(organizationAssignedIdentifier.getHealthcareSite() != null && organizationAssignedIdentifier.getHealthcareSite() != healthcareSite){
        		this.initialize(organizationAssignedIdentifier.getHealthcareSite());
        	}
        }
	}
	
	/* Saves a domain object
     * @see gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao#save(gov.nih.nci.cabig.ctms.domain.MutableDomainObject)
     */
    @Transactional(readOnly = false)
    public void save(HealthcareSite healthcareSite) {
    	//uncomment this when CREATE PO for COPPA is available.
//    	if(healthcareSite instanceof RemoteHealthcareSite){
//    		Object healthcareSiteObject = remoteSession.saveOrUpdate(healthcareSite);
//    		if(healthcareSiteObject != null && healthcareSiteObject instanceof HealthcareSite){
//    			getHibernateTemplate().saveOrUpdate((HealthcareSite)healthcareSite);
//    		}
//    	} else {
    		getHibernateTemplate().saveOrUpdate(healthcareSite);
//    	}
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
				.addAll(getExternalOrganizationsByExampleFromResolver(remoteHealthcareSiteName));

		//get all by nciId next
		RemoteHealthcareSite remoteHealthcareSiteNciID = new RemoteHealthcareSite();
		remoteHealthcareSiteNciID.setCtepCode(subnames[0]);
		remoteHealthcareSites
				.addAll(getExternalOrganizationsByExampleFromResolver(remoteHealthcareSiteNciID));
		
		//save both sets to the db
		updateDatabaseWithRemoteHealthcareSites(remoteHealthcareSites);

		return findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES,
				EXACT_MATCH_PROPERTIES);
	}

	/**
	 * Gets by nci institute code. If we find a match in local db dont go to COPPA.
	 * Goto Copa if no match is fouind in local db.
	 * We always defer to local db in cases of queries where only one result is expected.
	 * 
	 * @param primaryIdentifierCode the nci institute code
	 * @return the HealthcareSite
	 **/
	public HealthcareSite getByPrimaryIdentifier(String primaryIdentifierCode) {
		
		HealthcareSite healthcareSite = getByPrimaryIdentifierFromLocal(primaryIdentifierCode);
		if(healthcareSite == null){
			List<HealthcareSite> remoteHealthcareSites = new ArrayList<HealthcareSite>();
			
			//create a example remoteHealthcareSite with the primaryId set in it.
			RemoteHealthcareSite exampleRemoteHealthcareSite = new RemoteHealthcareSite();
			OrganizationAssignedIdentifier oai = new OrganizationAssignedIdentifier();
			oai.setPrimaryIndicator(Boolean.TRUE);
			oai.setValue(primaryIdentifierCode);			
			exampleRemoteHealthcareSite.getIdentifiers().add(oai);
			
			remoteHealthcareSites.addAll(getExternalOrganizationsByExampleFromResolver(exampleRemoteHealthcareSite));
			updateDatabaseWithRemoteHealthcareSites(remoteHealthcareSites);
			
			return getByPrimaryIdentifierFromLocal(primaryIdentifierCode);
		}
		return healthcareSite;
	}
	
	/**
	 * Gets by primary IDentifier code from local. 
	 * 
	 * @param primaryIdentifierCode the nci institute code
	 * @return the HealthcareSite (Null if not match is found)
	 * @throws C3PRBaseException 	
	 * @throws C3PRBaseRuntimeException 	 */
	public HealthcareSite getByPrimaryIdentifierFromLocal(String primaryIdentifierCode) {
		if(StringUtils.isEmpty(primaryIdentifierCode)){
			return null;
		}
		
		Criteria orgCriteria = getHibernateTemplate().getSessionFactory()
        				.getCurrentSession().createCriteria(HealthcareSite.class);
        Criteria identifiersAssignedToOrganizationCriteria = orgCriteria.createCriteria("identifiersAssignedToOrganization");
       
    	identifiersAssignedToOrganizationCriteria.add(Expression.eq("value", primaryIdentifierCode));
    	identifiersAssignedToOrganizationCriteria.add(Expression.eq("primaryIndicator", Boolean.TRUE));
    	
    	if(orgCriteria.list().size() > 0){
    		return (HealthcareSite)orgCriteria.list().get(0);
    	} else {
    		return null;
    	}        
	}

	/**
	 * Gets NCI Organization from local.
	 * 
	 * @return the HealthcareSite
	 */
	public HealthcareSite getNCIOrganization() {
		return CollectionUtils.firstElement((List<HealthcareSite>) getHibernateTemplate()
				.find("select H from HealthcareSite H where H.identifiersAssignedToOrganization.value=?", 
						new Object[] {OrganizationIdentifierTypeEnum.NCI.getName()}));
	}
	
	/**
	 * Gets CTEP Organization from local.
	 * 
	 * @return the HealthcareSite
	 */
	public HealthcareSite getCTEPOrganization() {
		return CollectionUtils.firstElement((List<HealthcareSite>) getHibernateTemplate()
				.find("select H from HealthcareSite H where H.identifiersAssignedToOrganization.value=?", 
						new Object[] {OrganizationIdentifierTypeEnum.CTEP.getName()}));
	}
	
	/**
	 * Gets by ctep code from local.
	 * 
	 * @param primaryIdentifierCode the nci institute code
	 * @return the HealthcareSite
	 * @throws C3PRBaseException 	 * @throws C3PRBaseRuntimeException 	 */
	public HealthcareSite getByNciCodeFromLocal(String nciCode) {
		if(StringUtils.isEmpty(nciCode)){
			return null;
		}
		return CollectionUtils.firstElement((List<HealthcareSite>) getHibernateTemplate()
				.find("select H from HealthcareSite H where H.identifiersAssignedToOrganization.typeInternal=? and " +
					  "H.identifiersAssignedToOrganization.value=?", 
						new Object[] {OrganizationIdentifierTypeEnum.NCI.getName(), nciCode}));
	}
	
	/**
	 * Gets by ctep code from local.
	 * 
	 * @param primaryIdentifierCode the nci institute code
	 * @return the HealthcareSite
	 * @throws C3PRBaseException 	 * @throws C3PRBaseRuntimeException 	 */
	public HealthcareSite getByCtepCodeFromLocal(String ctepCode) {
		if(StringUtils.isEmpty(ctepCode)){
			return null;
		}
		return CollectionUtils.firstElement((List<HealthcareSite>) getHibernateTemplate()
				.find("select H from HealthcareSite H where H.identifiersAssignedToOrganization.typeInternal=? and " +
					  "H.identifiersAssignedToOrganization.value=?", 
						new Object[] {OrganizationIdentifierTypeEnum.CTEP.getName(), ctepCode}));
	}
	/**
	 * Gets the organizations from the resolver.
	 * 
	 * @param healthcareSite the healthcare site
	 * @return the healthcare sites
	 */
	public List<HealthcareSite> getExternalOrganizationsByExampleFromResolver(HealthcareSite healthcareSite) {

		RemoteHealthcareSite remoteHealthcareSite = new RemoteHealthcareSite();
		if(healthcareSite.getName() == null){
			remoteHealthcareSite.setName("");
		}else{
			remoteHealthcareSite.setName(healthcareSite.getName());
		}
		remoteHealthcareSite.setCtepCode(healthcareSite.getPrimaryIdentifier());
		
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
	public void updateDatabaseWithRemoteHealthcareSites(List<HealthcareSite> remoteHealthcareSiteList) {

		HealthcareSite remoteHealthcareSite = null;
		try {
			for (int i = 0; i< remoteHealthcareSiteList.size(); i++) {
				remoteHealthcareSite = remoteHealthcareSiteList.get(i);
				if(remoteHealthcareSite != null){
					RemoteHealthcareSite remoteHealthcareSiteTemp = (RemoteHealthcareSite)remoteHealthcareSite;
					HealthcareSite healthcareSiteFromDatabase = 
									getByUniqueIdentifier(remoteHealthcareSiteTemp.getExternalId());
					//If healthcareSiteFromDatabase is not null then it already exists as a remoteOrg
					//this pre-existing site should be up to date.
					
					if (healthcareSiteFromDatabase == null) {
						HealthcareSite healthcareSiteWithSameCtepCode = null;
						HealthcareSite healthcareSiteWithSameNciCode = null;
						//If healthcareSiteFromDatabase is null then it doesnt exists as a remoteOrg
						//check to see if it exists as local Organization

						// check by ctepCode
						healthcareSiteWithSameCtepCode = getByCtepCodeFromLocal(remoteHealthcareSiteTemp.getCtepCode());
						healthcareSiteWithSameNciCode = getByNciCodeFromLocal(remoteHealthcareSiteTemp.getNciIdentifierAsString());
						if(healthcareSiteWithSameCtepCode != null){
							//make the remoteHcs refer to the existing one so other associations can be saved succesfully.
							remoteHealthcareSiteList.remove(i);
							remoteHealthcareSiteList.add(i, healthcareSiteWithSameCtepCode);
							log.error("Healthcare site with CTEP: " + remoteHealthcareSiteTemp.getCtepCode() + " already exists in database");
						} else if(healthcareSiteWithSameNciCode != null){
							//make the remoteHcs refer to the existing one so other associations can be saved succesfully.
							remoteHealthcareSiteList.remove(i);
							remoteHealthcareSiteList.add(i, healthcareSiteWithSameNciCode);
							log.error("Healthcare site with NCI: " + remoteHealthcareSiteTemp.getNciIdentifierAsString() + " already exists in database");
						} else {
							// this site doesn't exist
							createGroupForOrganization(remoteHealthcareSiteTemp);
							getHibernateTemplate().save(remoteHealthcareSiteTemp);
						}
					} else {
						//make the study Org list refer to the existing org so other associations can be saved succesfully.
						remoteHealthcareSiteList.remove(i);
						remoteHealthcareSiteList.add(i, healthcareSiteFromDatabase);
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


	/**Gets by the unique Identifier
	 * @param externalId
	 * @return
	 */
	public HealthcareSite getByUniqueIdentifier(String externalId) {
		if(StringUtils.isEmpty(externalId)){
			return null;
		}
		return CollectionUtils.firstElement((List<HealthcareSite>) getHibernateTemplate()
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
			log.error("###Error getting info for"
							+ csmApplicationContextName
							+ " application from CSM. Application configuration exception###");
			throw new C3PRBaseRuntimeException(
					"Application configuration problem. Cannot find application '"
							+ csmApplicationContextName + "' in CSM", e);
		} catch (CSTransactionException e) {
			log.warn("Could not create group for organization:"
					+ organization.getPrimaryIdentifier());
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
  	 */
	
    public List<HealthcareSite> searchByExample(HealthcareSite hcs, boolean isWildCard, int maxResults){
    	
    	List<HealthcareSite> remoteHealthcareSites = new ArrayList<HealthcareSite>();
		remoteHealthcareSites.addAll(getExternalOrganizationsByExampleFromResolver(hcs));
		updateDatabaseWithRemoteHealthcareSites(remoteHealthcareSites);

		List<HealthcareSite> result = new ArrayList<HealthcareSite>();
        Example example = Example.create(hcs).excludeZeroes().ignoreCase();
        try {
            Criteria orgCriteria = getHibernateTemplate().getSessionFactory()
            .getCurrentSession().createCriteria(HealthcareSite.class);
            Criteria identifiersAssignedToOrganizationCriteria = orgCriteria.createCriteria("identifiersAssignedToOrganization");
           
            if(hcs.getPrimaryIdentifier() != null && hcs.getPrimaryIdentifier() != ""){
            	identifiersAssignedToOrganizationCriteria.add(Expression.ilike("value", "%" + hcs.getPrimaryIdentifier() + "%"));
            	identifiersAssignedToOrganizationCriteria.add(Expression.eq("typeInternal", OrganizationIdentifierTypeEnum.CTEP.getName()));
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
            if(maxResults > 0) orgCriteria.setMaxResults(maxResults);
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
	  	searchCriteria.setCtepCode(organization.getPrimaryIdentifier());
	  	return (List)remoteSession.find(searchCriteria);
	  }
	  
}
