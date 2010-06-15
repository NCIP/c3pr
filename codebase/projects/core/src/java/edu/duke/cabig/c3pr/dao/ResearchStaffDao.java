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
import org.springframework.mail.MailException;
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
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSessionFactory;
import gov.nih.nci.cabig.ctms.suite.authorization.ScopeType;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRole;
import gov.nih.nci.cabig.ctms.suite.authorization.SuiteRoleMembership;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.authorization.domainobjects.User;
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
	
	/** The site object id generator. */
	private CSMObjectIdGenerator siteObjectIdGenerator;

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

	public void initialize(ResearchStaff researchStaff){
		if(researchStaff.getHealthcareSite() != null){
			getHibernateTemplate().initialize(researchStaff.getHealthcareSite().getIdentifiersAssignedToOrganization());
			getHibernateTemplate().initialize(researchStaff.getContactMechanisms());
		}
	}
	
	@Transactional(readOnly = false)
    public void flush() {
		getHibernateTemplate().flush();
	}
	
	/**
	 * Gets the by subnames.
	 *
	 * @param subnames
	 *            the subnames
	 * @param healthcareSite
	 *            the healthcare site
	 *
	 * @return the by subnames
	 */
	public List<ResearchStaff> getBySubnames(String[] subnames,
			int healthcareSite) {
		return findBySubname(subnames, "o.healthcareSite.id = '"
				+ healthcareSite + "'", EXTRA_PARAMS,
				SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
	}

	/**
	 * Gets the by sub name and sub email.
	 *
	 * @param subnames
	 *            the subnames
	 * @param ctepCode
	 *            the nci institute code
	 *
	 * @return the by sub name and sub email
	 */
	public List<ResearchStaff> getBySubNameAndSubEmail(String[] subnames, String ctepCode) {
		return findBySubname(subnames,
				"o.healthcareSite.identifiersAssignedToOrganization.value = '"+ ctepCode + "'" +
        		" and o.healthcareSite.identifiersAssignedToOrganization.primaryIndicator = '1'",
				EXTRA_PARAMS, SUBNAME_SUBEMAIL_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
	}

	/**
	 * Search by example.
	 *
	 * @param staff
	 *            the staff
	 * @param isWildCard
	 *            the is wild card
	 *
	 * @return the list< research staff>
	 */
	public List<ResearchStaff> searchByExample(ResearchStaff staff,
			boolean isWildCard) {

		// get the remote staff and update the database first
		RemoteResearchStaff remoteResearchStaff = convertToRemoteResearchStaff(staff);
		getRemoteResearchStaffFromResolverByExample(remoteResearchStaff);

		List<ResearchStaff> result = new ArrayList<ResearchStaff>();

		Example example = Example.create(staff).excludeZeroes().ignoreCase();
		example.excludeProperty("salt");
		example.excludeProperty("passwordLastSet");
		try {
			Criteria criteria = getSession()
					.createCriteria(ResearchStaff.class);
			criteria.addOrder(Order.asc("assignedIdentifier"));
			criteria
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			if (isWildCard) {
				example.enableLike(MatchMode.ANYWHERE);
				criteria.add(example);
				if (staff.getHealthcareSite() != null) {
					criteria.createCriteria("healthcareSite").add(
							Restrictions
									.ilike("name", "%"
											+ staff.getHealthcareSite()
													.getName() + "%"));
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
	 * @param query
	 *            the query
	 *
	 * @return the list< research staff>
	 */
	@SuppressWarnings( { "unchecked" })
	public List<ResearchStaff> searchResearchStaff(
			final ResearchStaffQuery query) {
		String queryString = query.getQueryString();
		log.debug("::: " + queryString.toString());
		return (List<ResearchStaff>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(final Session session)
							throws HibernateException, SQLException {
						org.hibernate.Query hiberanteQuery = session
								.createQuery(query.getQueryString());
						Map<String, Object> queryParameterMap = query
								.getParameterMap();
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
			result = (ResearchStaff) (getHibernateTemplate().find(
					"from ResearchStaff rs where rs.assignedIdentifier = '"
							+ assignedIdentifier + "'").get(0));
		} catch (Exception e) {
			log.debug("User with assignedIdentifier " + assignedIdentifier
					+ " does not exist. Returning null");
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
	 * @param emailAddress
	 *            the email address
	 *
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
		remoteResearchStaff
				.setHealthcareSite(researchStaff.getHealthcareSite());
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
	 * @param nciInstituteCode
	 *            the nci institute code
	 * @return the research staff by organization nci institute code
	 */
	public List<ResearchStaff> getResearchStaffByOrganizationNCIInstituteCode(
			HealthcareSite healthcareSite) {
		RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
		remoteResearchStaff.setHealthcareSite(healthcareSite);
		getRemoteResearchStaffFromResolverByExample(remoteResearchStaff);

		//run a query against the updated database to get all research staff
		return getResearchStaffByOrganizationCtepCodeFromLocal(healthcareSite);
	}

	/**
	 *
	 * @param healthcareSite
	 * @return
	 */
	public List<ResearchStaff> getResearchStaffByOrganizationCtepCodeFromLocal(
			HealthcareSite healthcareSite) {
		//run a query against the updated database to get all research staff
		Criteria researchStaffCriteria = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(ResearchStaff.class);
		Criteria healthcareSiteCriteria = researchStaffCriteria.createCriteria("healthcareSite");
		Criteria identifiersAssignedToOrganizationCriteria = healthcareSiteCriteria.createCriteria("identifiersAssignedToOrganization");
		
		identifiersAssignedToOrganizationCriteria.add(Expression.eq("value", healthcareSite.getPrimaryIdentifier()));
		identifiersAssignedToOrganizationCriteria.add(Expression.eq("primaryIndicator", Boolean.TRUE));
		
    	return researchStaffCriteria.list();
	}


	/**
	 * Gets the remote research staff by organization nci institute code from
	 * the resolver and updates the db.
	 *
	 * @param nciInstituteCode
	 *            the nci institute code
	 * @return the research staff by organization nci institute code
	 */
	public List<RemoteResearchStaff> getRemoteResearchStaffFromResolverByExample(
			RemoteResearchStaff remoteResearchStaff) {
		List<Object> objectList = remoteSession.find(remoteResearchStaff);
		List<RemoteResearchStaff> researchStaffList = new ArrayList<RemoteResearchStaff>();

		RemoteResearchStaff retrievedRemoteResearchStaff;
		for (Object object : objectList) {
			retrievedRemoteResearchStaff = (RemoteResearchStaff) object;
			if (retrievedRemoteResearchStaff.getHealthcareSite() != null) {
				// If the organization attached to the staff is in the db use it. Else create it.
				HealthcareSite matchingHealthcareSiteFromDb = healthcareSiteDao
						.getByPrimaryIdentifierFromLocal(retrievedRemoteResearchStaff
								.getHealthcareSite().getPrimaryIdentifier());
				if (matchingHealthcareSiteFromDb == null) {
					log.error("No Organization exists for the CTEP Code:"
							+ retrievedRemoteResearchStaff.getHealthcareSite().getPrimaryIdentifier());
					try {
						healthcareSiteDao.createGroupForOrganization(retrievedRemoteResearchStaff.getHealthcareSite());
						healthcareSiteDao.save(retrievedRemoteResearchStaff.getHealthcareSite());
					} catch (C3PRBaseRuntimeException e) {
						log.error(e.getMessage());
					} catch (C3PRBaseException e) {
						log.error(e.getMessage());
					}
				} else {
					// we have the retrieved staff's Org in our db...link up with the same
					retrievedRemoteResearchStaff
							.setHealthcareSite(matchingHealthcareSiteFromDb);
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
					if (remoteResearchStaff.getHealthcareSite() != null) {
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
							saveOrUpdateStaffAndCSMUser(remoteResearchStaff, null);
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
	 * Merge research staff.
	 * 
	 * @param staff the staff
	 */
	public void mergeResearchStaff(ResearchStaff staff) {
		getHibernateTemplate().merge(staff);
	}

	
	/**
	 * Moved csm related save/save code here from personnelServiceImpl for coppa
	 * integration
	 * 
	 * @param staff
	 * @throws C3PRBaseException
	 */
	@Transactional
	public void saveResearchStaff(ResearchStaff staff) throws C3PRBaseException {
		save(staff, null);
	}

	/**
	 * @param user
	 * @return
	 * @throws CSObjectNotFoundException
	 */
	private User getCSMUser(C3PRUser user) throws CSObjectNotFoundException {
		return userProvisioningManager.getUserById(user.getLoginId());
	}

	/**
	 * Need to merge this with createCSMUser. currently used by coppa.
	 * @param c3prUser
	 * @param csmUser
	 * @throws C3PRBaseException
	 * @throws MailException
	 */
	@Transactional
	private void save(ResearchStaff staff,
			gov.nih.nci.security.authorization.domainobjects.User csmUser)
			throws C3PRBaseException, MailException {
		
		C3PRUser c3prUser = (C3PRUser)staff;
		try {
			if (csmUser == null) {
				csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
				populateCSMUser(c3prUser, csmUser);
				csmUser.setLoginName(c3prUser.getLoginId().toLowerCase());
				csmUser.setPassword(((edu.duke.cabig.c3pr.domain.User)c3prUser).generatePassword());
				userProvisioningManager.createUser(csmUser);
				UserDao.addUserToken((edu.duke.cabig.c3pr.domain.User) c3prUser);
			} else {
				populateCSMUser(c3prUser, csmUser);
				userProvisioningManager.modifyUser(csmUser);
			}

			log.debug("Saving c3pr user");
			if (c3prUser.getId() != null) {
				this.merge(c3prUser);
			} else {
				this.save(c3prUser);
			}
			c3prUser.setLoginId(csmUser.getUserId().toString());

			assignUsersToGroup(csmUser, c3prUser.getGroups(), staff.getHealthcareSite());
		} catch (CSTransactionException e) {
			throw new C3PRBaseException("Could not create user", e);
		}
	}
	
	/**Does not create CSM user if username is blank or null. 
	 * Else If staff doesn't have a login id then it creates the CSM User and sets the login id in the staff from the created csm user.
	 * If staff has a login id then it means the csm user exists, so this method will just update it instead.
	 * 
	 * @param staff
	 * @param username
	 * @return gov.nih.nci.security.authorization.domainobjects.User
	 * @throws C3PRBaseException, MailException, CSTransactionException
	 */
	public gov.nih.nci.security.authorization.domainobjects.User saveOrUpdateStaffAndCSMUser(ResearchStaff staff, String username)
			throws C3PRBaseException, MailException {
		
		C3PRUser c3prUser = (C3PRUser)staff;
		gov.nih.nci.security.authorization.domainobjects.User csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
		try {
			if(StringUtils.isEmpty(username)){
				if (StringUtils.isEmpty(staff.getLoginId())) {
					populateCSMUser(c3prUser, csmUser);
					csmUser.setLoginName(username.toLowerCase());
					csmUser.setPassword(((edu.duke.cabig.c3pr.domain.User)c3prUser).generatePassword());
					userProvisioningManager.createUser(csmUser);
					c3prUser.setLoginId(csmUser.getUserId().toString());
					UserDao.addUserToken((edu.duke.cabig.c3pr.domain.User) c3prUser);
				} else {
					populateCSMUser(c3prUser, csmUser);
					userProvisioningManager.modifyUser(csmUser);
				}
			} else {
				log.debug("Not creating csm user as staff's username is not specified.");
			}
			
			log.debug("Saving Research Staff");
			if (c3prUser.getId() != null) {
				this.merge(c3prUser);
			} else {
				this.save(c3prUser);
			}
		} catch (CSTransactionException e) {
			throw new C3PRBaseException("Could not create user", e);
		}
		return csmUser;
	}
	
	/**
	 * Creates the research staff with groups.
	 *
	 * @param staff the staff
	 * @param map the map
	 * @param username the username
	 * @return the research staff
	 */
	public ResearchStaff saveOrUpdateStaffAndCSMUserWithGroups(ResearchStaff staff,
			Map<HealthcareSite, List<C3PRUserGroupType>> map, String username) {
		
		try {
			gov.nih.nci.security.authorization.domainobjects.User csmUser = saveOrUpdateStaffAndCSMUser(staff, username);
			Iterator<HealthcareSite> iter = map.keySet().iterator();
			HealthcareSite healthcareSite;
			while(iter.hasNext()){
				healthcareSite = iter.next();
				assignUsersToGroup(csmUser, map.get(healthcareSite), healthcareSite);
			}
			staff.setLoginId(csmUser.getUserId().toString());	
			
			log.debug("Saving c3pr user");
			if (staff.getId() != null) {
				this.merge(staff);
			} else {
				this.save(staff);
			}
		} catch (MailException e) {
			log.error(e.getMessage());
		} catch (C3PRBaseException e) {
			log.error(e.getMessage());
		}
		return staff;
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
	 * Assign users to group.
	 * Takes the whole list of groups instead of one at a time.This was created so the unchecked groups could be deleted.
	 * Since CCTS2.2: Uses ProvisioningSession to create the unified roles and attach them to the user.
	 * This only takes the unified groups in the groupList parameter. So it shouldn't contain C3PR specific roles like C3PR_admin.
	 *
	 * @param csmUser the csm user
	 * @param groupList the group list
	 * @param healthcareSite the healthcare site
	 * @throws C3PRBaseException the c3pr base exception
	 */
	public void assignUsersToGroup(User csmUser,
			List<C3PRUserGroupType> groupList, HealthcareSite healthcareSite) throws C3PRBaseException {
		//provisioning as per the new suite authorization -start
		ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
		for(C3PRUserGroupType groupType: groupList){
			if(C3PRUserGroupType.getUnifiedSuiteRole(groupType) == null){
				log.error("Ignoring the invalid role that was passed to the Dao: " + groupType.getDisplayName());
			} else {
				SuiteRoleMembership suiteRoleMembership = new SuiteRoleMembership(C3PRUserGroupType.getUnifiedSuiteRole(groupType), null, null);
				setSitesAndStudies(suiteRoleMembership, healthcareSite);
				provisioningSession.replaceRole(suiteRoleMembership);	
			}
		}
		//provisioning as per the new suite authorization - end
	}


	/**
	 * Sets the sites and studies.
	 *
	 * @param suiteRoleMembership the suite role membership
	 * @param healthcareSite the healthcare site
	 */
	private void setSitesAndStudies(SuiteRoleMembership suiteRoleMembership,
			HealthcareSite healthcareSite) {
		
		Set<ScopeType> scopeTypes = suiteRoleMembership.getRole().getScopes();
		if(scopeTypes.contains(ScopeType.SITE)){
			suiteRoleMembership.addSite(healthcareSite.getPrimaryIdentifier());
		} else {
			suiteRoleMembership.forAllSites();
		}
	}
	
	/**
	 * Merge research staff and csm data.
	 *
	 * @param staff the staff
	 * @throws C3PRBaseException the c3 pr base exception
	 */
	public void mergeResearchStaffAndCsmData(ResearchStaff staff)
			throws C3PRBaseException {
		try {
			User csmUser = getCSMUser(staff);
			save(staff, csmUser);
		} catch (CSObjectNotFoundException e) {
			new C3PRBaseException("Could not save Research staff"
					+ e.getMessage());
		}
	}

	/*
	 * Moved csm related save/merge code here from personnelServiceImpl for coppa integration
	 */
	public void setUserProvisioningManager(
			UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}

	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}

	public void setSiteObjectIdGenerator(
			CSMObjectIdGenerator siteObjectIdGenerator) {
		this.siteObjectIdGenerator = siteObjectIdGenerator;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	/**
	 * This method queries the external system to fetch all the matching
	 * ResearchStaff
	 *
	 * @param researchStaff
	 * @return
	 */
	public List<ResearchStaff> getRemoteResearchStaff(
			final ResearchStaff researchStaff) {
		ResearchStaff searchCriteria = new RemoteResearchStaff();
		searchCriteria.setAssignedIdentifier(researchStaff.getAssignedIdentifier());
		List<ResearchStaff> remoteResearchStaffs = (List)remoteSession.find(searchCriteria);
		return remoteResearchStaffs;
	}

	public ProvisioningSessionFactory getProvisioningSessionFactory() {
		return provisioningSessionFactory;
	}

	public void setProvisioningSessionFactory(
			ProvisioningSessionFactory provisioningSessionFactory) {
		this.provisioningSessionFactory = provisioningSessionFactory;
	}

}
