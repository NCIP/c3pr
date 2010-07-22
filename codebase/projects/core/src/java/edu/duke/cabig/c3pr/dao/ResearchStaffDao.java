package edu.duke.cabig.c3pr.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.ContactMechanismType;
import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSessionFactory;
import gov.nih.nci.cabig.ctms.suite.authorization.ScopeType;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;

/**
 * Hibernate implementation of ResearchStaffDao.
 *
 * @see edu.duke.cabig.c3pr.dao.ResearchStaffDao
 * @author Vinay Gangoli, Priyatam
 */
public class ResearchStaffDao extends GridIdentifiableDao<ResearchStaff> {

	/** The log. */
	private static Log log = LogFactory.getLog(ResearchStaffDao.class);

	/** The Constant SUBSTRING_MATCH_PROPERTIES. */
	private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays
			.asList("firstName", "lastName");

	/** The Constant SUBNAME_SUBEMAIL_MATCH_PROPERTIES. */
	private static final List<String> SUBNAME_SUBEMAIL_MATCH_PROPERTIES = Arrays
			.asList("firstName", "lastName", "contactMechanisms.value");

	/** The Constant EXACT_MATCH_PROPERTIES. */
	private static final List<String> EXACT_MATCH_PROPERTIES = Collections
			.emptyList();

	/** The Constant EXTRA_PARAMS. */
	private static final List<Object> EXTRA_PARAMS = Collections.emptyList();

	/** The remote session. */
	private RemoteSession remoteSession;

	/** The user provisioning manager. */
	private UserProvisioningManager userProvisioningManager;

	/** The provisioning session factory. This is from Suite Authorization Project for the unified roles*/
	private ProvisioningSessionFactory provisioningSessionFactory;
	
	/** The healthcare site dao. */
	private HealthcareSiteDao healthcareSiteDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
	 */
	@Override
	public Class<ResearchStaff> domainClass() {
		return ResearchStaff.class;
	}

	/**
	 * Initialize.
	 *
	 * @param researchStaff the research staff
	 */
	public void initialize(ResearchStaff researchStaff){
		for(HealthcareSite healthcareSite : researchStaff.getHealthcareSites()){
			getHibernateTemplate().initialize(healthcareSite.getIdentifiersAssignedToOrganization());
		}
		getHibernateTemplate().initialize(researchStaff.getContactMechanisms());
	}
	
	/**
	 * Flush.
	 */
	@Transactional(readOnly = false)
    public void flush() {
		getHibernateTemplate().flush();
	}
	
	/**
	 * Gets the by sub name and sub email.
	 *
	 * @param subnames the subnames
	 * @return the by sub name and sub email
	 */
	public List<ResearchStaff> getBySubNameAndSubEmail(String[] subnames) {
		return findBySubname(subnames,null ,
				EXTRA_PARAMS, SUBNAME_SUBEMAIL_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
	}

	/**
	 * Search by example.
	 *
	 * @param staff the staff
	 * @param isWildCard the is wild card
	 * @return the list
	 */
	public List<ResearchStaff> searchByExample(ResearchStaff staff, boolean isWildCard) {

		// get the remote staff and update the database first
		RemoteResearchStaff remoteResearchStaff = convertToRemoteResearchStaff(staff);
		getRemoteResearchStaffFromResolverByExample(remoteResearchStaff);

		List<ResearchStaff> result = new ArrayList<ResearchStaff>();

		Example example = Example.create(staff).excludeZeroes().ignoreCase();
		example.excludeProperty("salt");
		example.excludeProperty("passwordLastSet");
		try {
			Criteria criteria = getSession().createCriteria(ResearchStaff.class);
			criteria.addOrder(Order.asc("assignedIdentifier"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			if (isWildCard) {
				example.enableLike(MatchMode.ANYWHERE);
				criteria.add(example);
				if (staff.getHealthcareSites().size() > 0) {
					criteria.createCriteria("healthcareSites").add(Restrictions.ilike("name", "%" + staff.getHealthcareSites().get(0).getName() + "%")); 
					// As per discussion in search by example staff will have only one healthcare site
				}
				result = criteria.list();
			} else {
				result = criteria.add(example).list();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}

	/**
	 * Search research staff.
	 *
	 * @param query the query
	 * @return the list
	 */
	@SuppressWarnings( { "unchecked" })
	public List<ResearchStaff> searchResearchStaff(final ResearchStaffQuery query) {
		String queryString = query.getQueryString();
		log.debug("::: " + queryString.toString());
		return (List<ResearchStaff>) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(final Session session) throws HibernateException, SQLException {
						org.hibernate.Query hiberanteQuery = session.createQuery(query.getQueryString());
						Map<String, Object> queryParameterMap = query.getParameterMap();
						for (String key : queryParameterMap.keySet()) {
							Object value = queryParameterMap.get(key);
							hiberanteQuery.setParameter(key, value);
						}
						return hiberanteQuery.list();
					}
				});
	}

	/**
	 * Gets the by assigned identifier. Looks for local and remote
	 *
	 * @param assignedIdentifier
	 *            the assigned identifier
	 *
	 * @return the research staff
	 */
	public ResearchStaff getByAssignedIdentifierFromLocal(String assignedIdentifier) {
		ResearchStaff result = null;
		try {
			result = (ResearchStaff) (getHibernateTemplate().find("from ResearchStaff rs where rs.assignedIdentifier = '" + assignedIdentifier + "'").get(0));
		} catch (Exception e) {
			log.debug("User with assignedIdentifier " + assignedIdentifier + " does not exist. Returning null");
		}
		return result;
	}

	/**
	 * Gets the by assigned identifier. If we find a match in local db dont go to COPPA.
	 * Goto Coppa if no match is found in local db.
	 * We always defer to local db in cases of queries where only one result is expected.
	 *
	 * @param assignedIdentifier - the assigned identifier
	 * @return the research staff
	 */
	public ResearchStaff getByAssignedIdentifier(String assignedIdentifier) {
		ResearchStaff researchStaff = getByAssignedIdentifierFromLocal(assignedIdentifier);
		if(researchStaff == null){
			//get the remote staff and update the database
			RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
			remoteResearchStaff.setAssignedIdentifier(assignedIdentifier);

			getRemoteResearchStaffFromResolverByExample(remoteResearchStaff);
			//now run the query against the db after saving the retrieved data
			ResearchStaff result = null;
			try {
				result = (ResearchStaff) (getHibernateTemplate().find(
						"from ResearchStaff rs where rs.assignedIdentifier = '"
								+ assignedIdentifier + "'").get(0));
			} catch (Exception e) {
				log.debug("User with assignedIdentifier " + assignedIdentifier
						+ " does not exist. Returning null");
			}
			return result;
		}
		return researchStaff;
	}

	/**
	 * Gets the by external identifier. Created for the remote research staff
	 * use case.
	 *
	 * @param externalIdentifier the external identifier
	 * @return the ResearchStaff List
	 */
	public List<ResearchStaff> getByExternalIdentifierFromLocal(
			String externalIdentifier) {
		List<ResearchStaff> researchStaffList = new ArrayList<ResearchStaff>();
		researchStaffList.addAll(getHibernateTemplate().find(
				"from RemoteResearchStaff rs where rs.externalId = '"
						+ externalIdentifier + "' "));
		return researchStaffList;
	}

	/**
	 * Convert to remote research staff. Only include the properties that COPPA
	 * understands
	 *
	 * @param researchStaff
	 *            the research staff
	 *
	 * @return the remote research staff
	 */
	private RemoteResearchStaff convertToRemoteResearchStaff(
			ResearchStaff researchStaff) {
		if (researchStaff instanceof RemoteResearchStaff) {
			return (RemoteResearchStaff) researchStaff;
		}

		RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
		remoteResearchStaff.setAddress(researchStaff.getAddress());
		remoteResearchStaff.setFirstName(researchStaff.getFirstName());
		remoteResearchStaff.setLastName(researchStaff.getLastName());
		for(HealthcareSite hcSite : researchStaff.getHealthcareSites()){
			remoteResearchStaff.addHealthcareSite(hcSite);
		}
		remoteResearchStaff.setAssignedIdentifier(researchStaff.getAssignedIdentifier());
		return remoteResearchStaff;
	}

	/**
	 * First gets the staff from COPPA based on the hcs.nciCode and saves it to
	 * the db. Since hcs is not transient it is saved along with the staff. If
	 * hcs is null..then gets all the new staff from COPPA and saves them to the
	 * database Then gets btoh the local and remote research staff by
	 * organization nci institute code from the database.
	 *
	 * @param healthcareSite the healthcare site
	 * @return the research staff by organization nci institute code
	 */
	public List<ResearchStaff> getResearchStaffByOrganizationNCIInstituteCode(
			HealthcareSite healthcareSite) {
		RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
		remoteResearchStaff.addHealthcareSite(healthcareSite);
		getRemoteResearchStaffFromResolverByExample(remoteResearchStaff);

		//run a query against the updated database to get all research staff
		return getResearchStaffByOrganizationCtepCodeFromLocal(healthcareSite);
	}

	/**
	 * Gets the research staff by organization ctep code from local.
	 *
	 * @param healthcareSite the healthcare site
	 * @return the research staff by organization ctep code from local
	 */
	public List<ResearchStaff> getResearchStaffByOrganizationCtepCodeFromLocal(
			HealthcareSite healthcareSite) {
		//run a query against the updated database to get all research staff
		Criteria researchStaffCriteria = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(ResearchStaff.class);
		Criteria healthcareSiteCriteria = researchStaffCriteria.createCriteria("healthcareSites");
		Criteria identifiersAssignedToOrganizationCriteria = healthcareSiteCriteria.createCriteria("identifiersAssignedToOrganization");
		
		identifiersAssignedToOrganizationCriteria.add(Expression.eq("value", healthcareSite.getPrimaryIdentifier()));
		identifiersAssignedToOrganizationCriteria.add(Expression.eq("primaryIndicator", Boolean.TRUE));
		
    	return researchStaffCriteria.list();
	}


	/**
	 * Gets the remote research staff by organization nci institute code from
	 * the resolver and updates the db.
	 *
	 * @param remoteResearchStaff the remote research staff
	 * @return the research staff by organization nci institute code
	 */
	public List<RemoteResearchStaff> getRemoteResearchStaffFromResolverByExample(
			RemoteResearchStaff remoteResearchStaff) {
		List<Object> objectList = remoteSession.find(remoteResearchStaff);
		List<RemoteResearchStaff> researchStaffList = new ArrayList<RemoteResearchStaff>();

		RemoteResearchStaff retrievedRemoteResearchStaff;
		for (Object object : objectList) {
			retrievedRemoteResearchStaff = (RemoteResearchStaff) object;
			List<HealthcareSite> healthcareSites = new ArrayList<HealthcareSite>();
			healthcareSites.addAll(retrievedRemoteResearchStaff.getHealthcareSites());
			if (healthcareSites.size() > 0) {
				// If the organization attached to the staff is in the db use it. Else create it.
				for(HealthcareSite hcs : healthcareSites ){
					HealthcareSite matchingHealthcareSiteFromDb = healthcareSiteDao.getByPrimaryIdentifierFromLocal(hcs.getPrimaryIdentifier());
					if (matchingHealthcareSiteFromDb == null) {
						log.error("No Organization exists for the CTEP Code:" + hcs.getPrimaryIdentifier());
						try {
							healthcareSiteDao.createGroupForOrganization(hcs);
							healthcareSiteDao.save(hcs);
						} catch (C3PRBaseRuntimeException e) {
							log.error(e.getMessage());
						} catch (C3PRBaseException e) {
							log.error(e.getMessage());
						}
					} else {
						// we have the retrieved staff's Org in our db...link up with the same
						retrievedRemoteResearchStaff.removeHealthcareSite(hcs);
						retrievedRemoteResearchStaff.addHealthcareSite(matchingHealthcareSiteFromDb);
					}
				}
			} else {
				//If the resolver hasn't set the hcs, it can't be saved. We don't save staff without organization.
				log.error("RemoteResearchStaffResolver returned staff without organization!");
			}
			researchStaffList.add(retrievedRemoteResearchStaff);
		}
		updateDatabaseWithRemoteContent(researchStaffList);
		return researchStaffList;
	}

	/**
	 * Update database with remote content.
	 *
	 * @param remoteResearchStaffList
	 *            the remote research staff list
	 */
	private void updateDatabaseWithRemoteContent(
			List<RemoteResearchStaff> remoteResearchStaffList) {

		try {
			for (RemoteResearchStaff remoteResearchStaff : remoteResearchStaffList) {
				List<ResearchStaff> researchStaffFromDatabase = getByExternalIdentifierFromLocal(remoteResearchStaff
						.getExternalId());
				if (researchStaffFromDatabase.size() > 0) {
					// this guy already exists as remote staff...and should be up to date.
					updateContactMechanisms(researchStaffFromDatabase.get(0), remoteResearchStaff);
				} else {
					// Ensure the staff has an organization and that its assignedId is unique.
					if (remoteResearchStaff.getHealthcareSites().size() > 0) {
						ResearchStaff researchStaffWithMatchingAssignedIdentifier = 
								getByAssignedIdentifierFromLocal(remoteResearchStaff.getAssignedIdentifier());
						if (researchStaffWithMatchingAssignedIdentifier == null) {
							//First set up the login id for this new user before saving
							if(userProvisioningManager.getUser(remoteResearchStaff.getEmail()) != null){
								//this email is already being used as login so use the externalId
								remoteResearchStaff.setLoginId(remoteResearchStaff.getExternalId());
							} else {
								//this email is not being used as login so use it as the loginId
								remoteResearchStaff.setLoginId(remoteResearchStaff.getEmail());
							}
							createResearchStaff(remoteResearchStaff);
						} else {
							log.error("Unable to save Remote Staff: "	+ remoteResearchStaff.getFullName()
									+ " as it's NCI Identifier: "+ remoteResearchStaff.getAssignedIdentifier() + " is already in the database.");
						}
					} else {
						log.error("Unable to save this Remote Staff as it doesn't have a healthcareSite associated with it." + remoteResearchStaff.getFullName());
					}
				}
			}
			getHibernateTemplate().flush();
		} catch (DataAccessException e) {
			log.error(e.getMessage());
		} catch (C3PRBaseException e) {
			log.error(e.getMessage());
		}
	}

	 /**
 	 * Update contact mechanisms.
 	 * 
 	 * @param staffToBeUpdated the staff to be updated
 	 * @param staffToBeDiscarded the staff to be discarded
 	 */
 	private void updateContactMechanisms(ResearchStaff staffToBeUpdated, ResearchStaff staffToBeDiscarded){
    	for(ContactMechanism cm: staffToBeDiscarded.getContactMechanisms()){
    		if(cm.getType().equals(ContactMechanismType.EMAIL)){
    			staffToBeUpdated.setEmail(cm.getValue());
    		}
    		if(cm.getType().equals(ContactMechanismType.Fax)){
    			staffToBeUpdated.setFax(cm.getValue());
    		}
    		if(cm.getType().equals(ContactMechanismType.PHONE)){
    			staffToBeUpdated.setPhone(cm.getValue());
    		}
    	}
    }
 	
 	/**
	 * Creates only the research staff.
	 *
	 * @param researchStaff the research staff
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public ResearchStaff createResearchStaff(ResearchStaff researchStaff) throws C3PRBaseException {
		return createOrModifyResearchStaff(researchStaff, false, null, null , false);
	}


	/**
	 * Gets the cSM user.
	 *
	 * @param user the user
	 * @return the cSM user
	 * @throws CSObjectNotFoundException the cS object not found exception
	 */
	public User getCSMUser(C3PRUser user) throws CSObjectNotFoundException {
		return userProvisioningManager.getUserById(user.getLoginId());
	}


	/**
	 * Populate csm user.
	 *
	 * @param c3prUser the c3pr user
	 * @param csmUser the csm user
	 */
	private void populateCSMUser(C3PRUser c3prUser,
			gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		csmUser.setFirstName(c3prUser.getFirstName());
		csmUser.setLastName(c3prUser.getLastName());
		csmUser.setEmailId(c3prUser.getEmail());
	}


	/**
	 * Sets the sites and studies.
	 *
	 * @param suiteRoleMembership the suite role membership
	 * @param healthcareSite the healthcare site
	 * @param hasAccessToAllSites the has access to all sites
	 */
	private void setSitesAndStudies(User csmUser, ProvisioningSession provisioningSession, List<C3PRUserGroupType> groupList,
			HealthcareSite healthcareSite, boolean hasAccessToAllSites) {
		
		SuiteRole suiteRole;
		SuiteRoleMembership suiteRoleMembership;
		//iterate over the newly assigned c3prGroup list and add/edit the corresponding
		//suiteRoleMemberships in the user's provisioningSession
		for(int i=0; i< groupList.size(); i++){
			suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(groupList.get(i));
			suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
			if(suiteRole != null && suiteRole.getScopes().contains(ScopeType.SITE)){
				//Get the suiteRoleMembership and edit it with the new changes
				if(hasAccessToAllSites || suiteRole.equals(SecurityUtils.GLOBAL_ROLE)){
					suiteRoleMembership.forAllSites();
					provisioningSession.replaceRole(suiteRoleMembership);
				} else {
					//Get all existing sites(if any) add the new site and save it thru a new SRM. 
					//This ensures the SRM doesn't have all-site access since the all site access chkbox was unchecked.
					SuiteRoleMembership newSuiteRoleMembership= new SuiteRoleMembership(suiteRole, null, null);
					if(!suiteRoleMembership.isAllSites()){
						List<String> allSiteIds = suiteRoleMembership.getStudyIdentifiers();
						for(String siteId: allSiteIds){
							newSuiteRoleMembership.addSite(siteId);
						}
					}
					if(suiteRole.getScopes().contains(ScopeType.STUDY) && !suiteRoleMembership.isAllStudies()){
						List<String> allStudyIds = suiteRoleMembership.getStudyIdentifiers();
						for(String studyId: allStudyIds){
							newSuiteRoleMembership.addSite(studyId);
						}
					}
					newSuiteRoleMembership.addSite(healthcareSite.getPrimaryIdentifier());	
					provisioningSession.replaceRole(newSuiteRoleMembership);
				}
			} else {
				//provisioning or global non site scoped role with all site access
				provisioningSession.replaceRole(suiteRoleMembership);
			}
		}
		
		//Iterate over the user's (pre-existing) suiteRole list and delete the unchecked roles. 
		//Can only delete site scoped roles here as this method deals with one site at a time.
		//Global roles will have to be deleted in assignRolesToOrganization
		Set<Group> groups;
		if(csmUser.getGroups() != null){
			try {
				groups = userProvisioningManager.getGroups(csmUser.getUserId().toString());
				Iterator<Group> iter = groups.iterator();
				while(iter.hasNext()){
					suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(C3PRUserGroupType.getByCode(iter.next().getGroupName()));
					suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
					if(!suiteRoleMembership.isAllSites() && suiteRoleMembership.getSiteIdentifiers().contains(healthcareSite.getPrimaryIdentifier()) &&
							!groupList.contains(C3PRUserGroupType.getByCode(suiteRole.getCsmName()))){
						suiteRoleMembership.removeSite(healthcareSite.getPrimaryIdentifier());
						//remove the site for which the role was unchecked
						provisioningSession.replaceRole(suiteRoleMembership);
						//remove the role if it has no sites remaining
						if(suiteRoleMembership.getSiteIdentifiers().size() == 0){
							provisioningSession.deleteRole(suiteRole);
						}
					}
				}
			} catch (CSObjectNotFoundException e) {
				log.error(e.getMessage());
			}
		}
	}
	

	/**
	 * This method queries the external system to fetch all the matching
	 * ResearchStaff.
	 *
	 * @param researchStaff the research staff
	 * @return the remote research staff
	 */
	public List<ResearchStaff> getRemoteResearchStaff( final ResearchStaff researchStaff) {
		ResearchStaff searchCriteria = new RemoteResearchStaff();
		searchCriteria.setAssignedIdentifier(researchStaff.getAssignedIdentifier());
		List<ResearchStaff> remoteResearchStaffs = (List)remoteSession.find(searchCriteria);
		return remoteResearchStaffs;
	}
	
	/**
	 * Creates the or modify csmUser.
	 * assign groups and save/update Staff
	 * 
	 *
	 * @param staff the staff
	 * @param createCsmUser the create csm user
	 * @param username the username
	 * @param associationMap the association map
	 * @param hasAccessToAllSites the has access to all sites
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	@Transactional
	public ResearchStaff createOrModifyResearchStaff(ResearchStaff staff, boolean createCsmUser, String username, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap, boolean hasAccessToAllSites) throws C3PRBaseException {
		C3PRUser c3prUser = (C3PRUser)staff;
		gov.nih.nci.security.authorization.domainobjects.User csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
		if(createCsmUser && StringUtils.isNotBlank(username)){
			try {
				populateCSMUser(c3prUser, csmUser);
				csmUser.setLoginName(username.toLowerCase());
				csmUser.setPassword(((edu.duke.cabig.c3pr.domain.User)c3prUser).generatePassword());
				userProvisioningManager.createUser(csmUser);
				c3prUser.setLoginId(csmUser.getUserId().toString());
				UserDao.addUserToken((edu.duke.cabig.c3pr.domain.User) c3prUser);
			} catch (CSTransactionException e) {
				throw new C3PRBaseException("Could not create user", e);
			}
		}else{
			log.debug("Not creating csm user as createCsmUser flag is set to false.");
		}
		
		boolean csmUserExists = false ;
		try {
			csmUser = getCSMUser(c3prUser);
			if(csmUser != null){
				csmUserExists = true ;
			}
		} catch (CSObjectNotFoundException e) {
			csmUserExists = false ;
		}
		
		if(!createCsmUser && csmUserExists){
			log.debug("Updating csm user to make sure csmUser is refelecting same information as research staff.");
			// We have to make sure we update csm user everytime because we dont know if firstname, last name or email address got updated.
			try {
				populateCSMUser(c3prUser, csmUser);
				userProvisioningManager.modifyUser(csmUser);
			} catch (CSTransactionException e) {
				throw new C3PRBaseException("not able to update csm user", e);
			} 
		}
		
		log.debug("Saving Research Staff");
		if(associationMap != null && !associationMap.isEmpty() && csmUserExists){
			c3prUser = assignRolesToOrganization(c3prUser, csmUser, associationMap, hasAccessToAllSites);
		}
		c3prUser = (C3PRUser) merge((ResearchStaff)c3prUser);
		return (ResearchStaff)c3prUser ;
	}
	
	
	
	/**
	 * Assign roles to organization.
	 *
	 * @param c3prUser the c3pr user
	 * @param csmUser the csm user
	 * @param associationMap the association map
	 * @param hasAccessToAllSites the has access to all sites
	 * @return the C3PR user
	 */
	private C3PRUser assignRolesToOrganization(C3PRUser c3prUser, User csmUser, Map<HealthcareSite, List<C3PRUserGroupType>> associationMap, boolean hasAccessToAllSites) {
		Iterator<HealthcareSite> iter = associationMap.keySet().iterator();
		ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
		HealthcareSite healthcareSite;
		List<C3PRUserGroupType> groupList;
		C3PRUserGroupType groupArray[] = C3PRUserGroupType.values();
		while(iter.hasNext()){
			healthcareSite = iter.next();
			groupList = associationMap.get(healthcareSite);
			setSitesAndStudies(csmUser, provisioningSession, groupList, healthcareSite, hasAccessToAllSites);
			//if any of the global roles are unchecked for every site then delete them.
			for(int i=0;i<groupArray.length;i++){
				if(groupArray[i] != null && groupList.contains(groupArray[i])){
					groupArray[i] = null;
				}
			}
		}
		deleteRole(provisioningSession, groupArray);
		return c3prUser;
	}
	

    /**
     * Delete roles specified in the array. This comes into play when the allSiteAccess chk box is checked.
     * When the allSite chkbox is checked we cannot delete individual site-roles.  We only delete those roles
     * which are unchecked for all sites.
     *
     * @param provisioningSession the provisioning session
     * @param groupArray the group array
     */
    private void deleteRole(ProvisioningSession provisioningSession,
			C3PRUserGroupType[] groupArray) {
    	SuiteRole suiteRole;
    	for(int i=0;i<groupArray.length;i++){
    		if(groupArray[i] != null){
    			suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(C3PRUserGroupType.getByCode(groupArray[i].getCode()));
    			provisioningSession.deleteRole(suiteRole);
    		}
    	}
	}

	/**
     * Gets the user groups for organization.
     *
     * @param csmUser the csm user
     * @param healthcareSite the healthcare site
     * @return the user groups for organization
     */
    public List<C3PRUserGroupType> getUserGroupsForOrganization(User csmUser, HealthcareSite healthcareSite){

    	ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
    	List<C3PRUserGroupType> groupList = new ArrayList<C3PRUserGroupType>();
    	
        SuiteRoleMembership suiteRoleMembership;
        SuiteRole suiteRole;
    	Set<Group> groups;
		try {
			groups = userProvisioningManager.getGroups(csmUser.getUserId().toString());
	    	Iterator<Group> iter = groups.iterator();
	    	String groupName;
	    	while(iter.hasNext()){
	    		groupName = ((Group)iter.next()).getGroupName();
	    		suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(C3PRUserGroupType.getByCode(groupName));
            	suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
                //include roles that are scoped by site and have access to the site in question.
                if(suiteRoleMembership.isAllSites() ||
                		suiteRoleMembership.getSiteIdentifiers().contains(healthcareSite.getPrimaryIdentifier())){
                    groupList.add(C3PRUserGroupType.getByCode(suiteRole.getCsmName()));
                }
                if(!suiteRole.isScoped()){
                    groupList.add(C3PRUserGroupType.getByCode(suiteRole.getCsmName()));
                }
	    	}
		} catch (CSObjectNotFoundException e) {
			log.error(e.getMessage());
		}
        return groupList;
    }
    
    /**
     * Checks for all site access. Returns true if user is not scoped by site at all.
     *
     * @return true, if successful
     */
    public boolean getHasAccessToAllSites(User csmUser){
    	ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
    	SuiteRoleMembership suiteRoleMembership;
        SuiteRole suiteRole;
		try {
			Set<Group> groups = userProvisioningManager.getGroups(csmUser.getUserId().toString());
	    	Iterator<Group> iter = groups.iterator();
	    	String groupName;
	    	//global roles are not considered for the all sites access checkbox value.
	    	while(iter.hasNext()){
	    		groupName = iter.next().getGroupName();
	    		suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(C3PRUserGroupType.getByCode(groupName));
	    		//exclude PO_MGR as he is hard-coded to have all site access and hence doesnt drive the all-site access chkbox
	    		if(suiteRole.getScopes().contains(ScopeType.SITE)  && !suiteRole.equals(SecurityUtils.GLOBAL_ROLE)){
	            	suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
	                //include roles that are scoped by site and have access to all sites or the site in question
	                if(suiteRoleMembership.isAllSites()){
	                    return true;
	                }
	            }
	    	}
		} catch (CSObjectNotFoundException e) {
			log.error(e.getMessage());
		}
        return false;
    }
    
    
    /**
    * Gets the staff scoped by study. 
    * Used mainly to display study scoped personnel(e.g reg and data readers) on study_personnel page.
    *
    * @param staffList the staff list
    * @return the staff scoped by study
    */
	public List<ResearchStaff> getStaffScopedByStudy(List<ResearchStaff> staffList, HealthcareSite healthcareSite) {
		List<ResearchStaff> reducedHcsRsList = new ArrayList<ResearchStaff>();
		User user = null;
		for (ResearchStaff researchStaff : staffList) {
			try {
				user = getCSMUser(researchStaff);
			} catch (CSObjectNotFoundException e) {
				logger.error("Failed to load user for :"+ researchStaff.getFirstName());
				logger.error(e.getMessage());
			}
			if(user == null){
				log.warn("No csm user exists for staff with first Name: "+researchStaff.getFirstName());
				continue;
			}
			if (checkUserAccessForSite(user, healthcareSite, C3PRUserGroupType.REGISTRAR.getCode())) {
				reducedHcsRsList.add(researchStaff);
			}
		}
		return reducedHcsRsList;
	}



	/**
	 * Check user access for site for the role that is passed in.
	 *
	 * @param csmUser the csm user
	 * @param healthcareSite the healthcare site
	 * @param role the role
	 * @return true, if successful
	 */
	public boolean checkUserAccessForSite(User csmUser, HealthcareSite healthcareSite, String role) {
		try {
			return userProvisioningManager.checkPermission(csmUser.getLoginName(), 
							"HealthcareSite."+healthcareSite.getPrimaryIdentifier(), role);
		} catch (CSObjectNotFoundException e) {
			log.error(e.getMessage());
		} catch (CSException e) {
			log.error(e.getMessage());
		}
		return false;
	}

	/**
	 * Gets the provisioning session factory.
	 *
	 * @return the provisioning session factory
	 */
	public ProvisioningSessionFactory getProvisioningSessionFactory() {
		return provisioningSessionFactory;
	}

	/**
	 * Sets the provisioning session factory.
	 *
	 * @param provisioningSessionFactory the new provisioning session factory
	 */
	public void setProvisioningSessionFactory(ProvisioningSessionFactory provisioningSessionFactory) {
		this.provisioningSessionFactory = provisioningSessionFactory;
	}

	/**
	 * Merge research staff and csm data.
	 *
	 * @param staff the staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	public void mergeResearchStaffAndCsmData(ResearchStaff staff)
			throws C3PRBaseException {
		try {
			User csmUser = getCSMUser(staff);
			createOrModifyResearchStaff(staff, false, csmUser.getLoginName(), null, false);
		} catch (CSObjectNotFoundException e) {
			new C3PRBaseException("Could not save Research staff" + e.getMessage());
		}
	}

	/*
	 * Moved csm related save/merge code here from personnelServiceImpl for coppa integration
	 */
	/**
	 * Sets the user provisioning manager.
	 *
	 * @param userProvisioningManager the new user provisioning manager
	 */
	public void setUserProvisioningManager(UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}

	/**
	 * Sets the remote session.
	 *
	 * @param remoteSession the new remote session
	 */
	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}


	/**
	 * Sets the healthcare site dao.
	 *
	 * @param healthcareSiteDao the new healthcare site dao
	 */
	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}
}
	