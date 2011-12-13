package edu.duke.cabig.c3pr.dao;

import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.nwu.bioinformatics.commons.CollectionUtils;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.UserProvisioningManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.semanticbits.coppa.infrastructure.RemoteSession;

/**
 * The Class HealthcareSiteDao.
 * 
 * @author Ramakrishna, Vinay Gangoli, kherm
 * @version 1.0
 */
public class HealthcareSiteDao extends OrganizationDao {

	/** The SUBSTRING_ match_ properties. */
	private List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("name");

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
	 * Gets by subnames. Match the strings against the name of organization and 
	 * value of identifierAssignedToOrganization
	 * 
	 * @param subnames the subnames
	 * @return the subnames
	 * @throws C3PRBaseException the c3 pr base exception
	 * @throws C3PRBaseRuntimeException the c3 pr base runtime exception
	 */
	public List<HealthcareSite> getBySubnames(String[] subnames) throws C3PRBaseRuntimeException, C3PRBaseException {
		
		//get all orgs having a matching identifier if subnames length is 1. So more than one words will not invoke this.
		List<HealthcareSite> healthcareSitesByIdentifier = new ArrayList<HealthcareSite>();
		if(subnames.length == 1){
			healthcareSitesByIdentifier= getBySubIdentifier(subnames);
		}
		
		//get all orgs having matching name
		List<HealthcareSite> healthcareSitesByName =  findBySubname(subnames, SUBSTRING_MATCH_PROPERTIES,
				EXACT_MATCH_PROPERTIES);
		//merge both into a set
		Set<HealthcareSite> siteSet = new HashSet<HealthcareSite>();
		siteSet.addAll(healthcareSitesByIdentifier);
		siteSet.addAll(healthcareSitesByName);
		//limit the size based on the maxLimit defined in baseDao. This max limit is also enforced by the findBySubname(), but we re-enforce
		//it after merging with healthcareSitesByIdentifier. Need a cleaner way to do this.
		if(siteSet.size() > getMaxSearchResultsForAutocompleter()){
			List<HealthcareSite> finalList =  new ArrayList<HealthcareSite>(siteSet);
			finalList.subList(getMaxSearchResultsForAutocompleter(), siteSet.size()).clear();
			return finalList;
		} else {
			return new ArrayList<HealthcareSite>(siteSet);
		}
		
	}

    /**Runs a like search against the identifier value for all the strings in the array that is passed in.
     * Used by auto-completer. Can be changed to use hql.
     * 
     * @param subnames
     * @return List<HealthcareSite> which have an identifier matching to one of the strings in subnames array
     */
	private List<HealthcareSite> getBySubIdentifier(String[] subnames) {
		List<HealthcareSite> healthcareSitesByIdentifier = new ArrayList<HealthcareSite>();
		for(String subname: subnames){
			if(!StringUtils.isEmpty(subname)){
				Criteria orgCriteria = getHibernateTemplate().getSessionFactory()
												.getCurrentSession().createCriteria(HealthcareSite.class);
				Criteria identifiersAssignedToOrganizationCriteria = orgCriteria.createCriteria("identifiersAssignedToOrganization");
				identifiersAssignedToOrganizationCriteria.add(Expression.ilike("value", "%"+subname+"%"));
				
				if(orgCriteria.list().size() > 0){
					healthcareSitesByIdentifier.addAll(orgCriteria.list());
				}    
			}
		}
		return healthcareSitesByIdentifier;
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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
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
	@SuppressWarnings("unchecked")
	public HealthcareSite getByNciCodeFromLocal(String nciCode) {
		return getByTypeAndCodeFromLocal(OrganizationIdentifierTypeEnum.NCI.getName(), nciCode);
	}
	
	@SuppressWarnings("unchecked")
	public HealthcareSite getByTypeAndCodeFromLocal(String typeName, String code) {
		return getByTypeAndCodeFromLocal(typeName, code, null);
	}
	
	@SuppressWarnings("unchecked")
	public HealthcareSite getByTypeAndCodeFromLocal(String typeName,
			String code, Boolean isPrimary) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}

		Criteria orgCriteria = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(HealthcareSite.class);
		Criteria identifiersAssignedToOrganizationCriteria = orgCriteria
				.createCriteria("identifiersAssignedToOrganization");
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Expression.eq(
				"typeInternal", typeName));
		conjunction.add(Expression.eq("value",
				code));
		if (isPrimary!=null) {
			conjunction.add(Expression.eq(
					"primaryIndicator", isPrimary));
		}
		identifiersAssignedToOrganizationCriteria.add(conjunction);
		return CollectionUtils.firstElement((List<HealthcareSite>) orgCriteria
				.list());
	}
	
	/**
	 * Gets by ctep code from local.
	 * 
	 * @param primaryIdentifierCode the nci institute code
	 * @return the HealthcareSite
	 * @throws C3PRBaseException 	 * @throws C3PRBaseRuntimeException 	 */
	@SuppressWarnings("unchecked")
	public HealthcareSite getByCtepCodeFromLocal(String ctepCode) {
		return getByTypeAndCodeFromLocal(OrganizationIdentifierTypeEnum.CTEP.getName(), ctepCode);
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
						healthcareSiteWithSameNciCode = getByNciCodeFromLocal(remoteHealthcareSiteTemp.getNCICode());
						if(healthcareSiteWithSameCtepCode != null){
							//make the remoteHcs refer to the existing one so other associations can be saved successfully.
							remoteHealthcareSiteList.remove(i);
							remoteHealthcareSiteList.add(i, healthcareSiteWithSameCtepCode);
							log.warn("Healthcare site with CTEP: " + remoteHealthcareSiteTemp.getCtepCode() + " already exists in database");
						} else if(healthcareSiteWithSameNciCode != null){
							//make the remoteHcs refer to the existing one so other associations can be saved successfully.
							remoteHealthcareSiteList.remove(i);
							remoteHealthcareSiteList.add(i, healthcareSiteWithSameNciCode);
							log.warn("Healthcare site with NCI: " + remoteHealthcareSiteTemp.getNCICode() + " already exists in database");
						} else {
							// this site doesn't exist
//							createGroupForOrganization(remoteHealthcareSiteTemp);
							getHibernateTemplate().save(remoteHealthcareSiteTemp);
						}
					} else {
						//make the study Org list refer to the existing org so other associations can be saved successfully.
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
           
            if(StringUtils.isNotBlank(hcs.getPrimaryIdentifier())){
            	identifiersAssignedToOrganizationCriteria.add(Expression.ilike("value", "%" + hcs.getPrimaryIdentifier() + "%"));
            	identifiersAssignedToOrganizationCriteria.add(Expression.eq("typeInternal", OrganizationIdentifierTypeEnum.CTEP.getName()));
            }
            if(StringUtils.isNotBlank(hcs.getName())){
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
