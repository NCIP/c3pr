/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.RemotePersonUser;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.utils.RoleBasedHealthcareSitesAndStudiesDTO;
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
 * Hibernate implementation of PersonUserDao.
 *
 * @see edu.duke.cabig.c3pr.dao.PersonUserDao
 * @author Vinay Gangoli, Priyatam
 */
public class PersonUserDao extends GridIdentifiableDao<PersonUser> {

	/** The log. */
	private static Log log = LogFactory.getLog(PersonUserDao.class);

	/** The Constant SUBSTRING_MATCH_PROPERTIES. */
	private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays
			.asList("firstName", "lastName");

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
	public Class<PersonUser> domainClass() {
		return PersonUser.class;
	}

	/**
	 * Initialize.
	 *
	 * @param personUser the research staff
	 */
	public void initialize(PersonUser personUser){
		for(HealthcareSite healthcareSite : personUser.getHealthcareSites()){
			getHibernateTemplate().initialize(healthcareSite.getIdentifiersAssignedToOrganization());
		}
		getHibernateTemplate().initialize(personUser.getContactMechanisms());
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
	public List<PersonUser> getBySubNameAndSubEmail(String[] subnames) {
		String queryTemplate = "LOWER(o.firstName) LIKE ? or LOWER(o.lastName) LIKE ? or LOWER(cm.value) LIKE ?";
		return getHibernateTemplate().find("select distinct o from edu.duke.cabig.c3pr.domain.PersonUser o , ContactMechanism cm " +
				"where cm = any elements(o.contactMechanisms) and " + buildSubNameQuery(queryTemplate, subnames.length), buildSubNames(subnames, 3));
	}

	/**
	 * Search by example.
	 *
	 * @param personUser the staff
	 * @param isWildCard the is wild card
	 * @return the list
	 */
	public List<PersonUser> searchByExample(PersonUser personUser, boolean isWildCard, String emailAddress) {

		// get the remote staff and update the database first
		RemotePersonUser remotePersonUser = convertToRemotePersonUser(personUser);
		getRemoteResearchStaffFromResolverByExample(remotePersonUser);

		List<PersonUser> result = new ArrayList<PersonUser>();

		Example example = Example.create(personUser).excludeZeroes().ignoreCase();
		example.excludeProperty("salt");
		example.excludeProperty("passwordLastSet");
		try {
			Criteria criteria = getSession().createCriteria(PersonUser.class);
			criteria.addOrder(Order.asc("assignedIdentifier"));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			if (isWildCard) {
				example.enableLike(MatchMode.ANYWHERE);
				criteria.add(example);
				if (personUser.getHealthcareSites().size() > 0) {
					criteria.createCriteria("healthcareSites").add(Restrictions.ilike("name", "%" + personUser.getHealthcareSites().get(0).getName() + "%")); 
					// As per discussion in search by example staff will have only one healthcare site
				}
				if (!StringUtils.isBlank(emailAddress)) {
					Criteria emailCriteria = criteria.createCriteria("contactMechanisms");
					emailCriteria.add(Restrictions.ilike("value", "%" + emailAddress + "%")); 
					//emailCriteria.add(Restrictions.ilike("type", ContactMechanismType.EMAIL)); 
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
	public List<PersonUser> searchResearchStaff(final ResearchStaffQuery query) {
		String queryString = query.getQueryString();
		log.debug("::: " + queryString.toString());
		return (List<PersonUser>) getHibernateTemplate().execute(
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
	public PersonUser getByAssignedIdentifierFromLocal(String assignedIdentifier) {
		PersonUser result = null;
		try {
			result = (PersonUser) (getHibernateTemplate().find("from PersonUser rs where rs.assignedIdentifier = '" + assignedIdentifier + "'").get(0));
		} catch (Exception e) {
			log.debug("User with assignedIdentifier " + assignedIdentifier + " does not exist. Returning null");
		}
		return result;
	}
	
	
	/**
	 * Gets the by login id.
	 *
	 * @param loginId the login id
	 * @return the by login id
	 */
	public PersonUser getByLoginId(String loginId) {
		PersonUser result = null;
		try {
			result = (PersonUser) (getHibernateTemplate().find("from PersonUser rs where rs.loginId = '" + loginId + "'").get(0));
		} catch (Exception e) {
			log.debug("User with loginId " + loginId + " does not exist. Returning null");
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
	public PersonUser getByAssignedIdentifier(String assignedIdentifier) {
		PersonUser researchStaff = getByAssignedIdentifierFromLocal(assignedIdentifier);
		if(researchStaff == null){
			//get the remote staff and update the database
			RemotePersonUser remoteResearchStaff = new RemotePersonUser();
			remoteResearchStaff.setAssignedIdentifier(assignedIdentifier);

			getRemoteResearchStaffFromResolverByExample(remoteResearchStaff);
			//now run the query against the db after saving the retrieved data
			PersonUser result = null;
			try {
				result = (PersonUser) (getHibernateTemplate().find(
						"from PersonUser rs where rs.assignedIdentifier = '"
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
	 * @return the PersonUser List
	 */
	public List<PersonUser> getByExternalIdentifierFromLocal(
			String externalIdentifier) {
		List<PersonUser> researchStaffList = new ArrayList<PersonUser>();
		researchStaffList.addAll(getHibernateTemplate().find(
				"from RemotePersonUser rs where rs.externalId = '"
						+ externalIdentifier + "' "));
		return researchStaffList;
	}

	/**
	 * Convert to remote research staff. Only include the properties that COPPA
	 * understands
	 *
	 * @param personUser
	 *            the research staff
	 *
	 * @return the remote research staff
	 */
	private RemotePersonUser convertToRemotePersonUser(
			PersonUser personUser) {
		if (personUser instanceof RemotePersonUser) {
			return (RemotePersonUser) personUser;
		}

		RemotePersonUser remoteResearchStaff = new RemotePersonUser();
		remoteResearchStaff.setAddress(personUser.getAddress());
		remoteResearchStaff.setFirstName(personUser.getFirstName());
		remoteResearchStaff.setLastName(personUser.getLastName());
		for(HealthcareSite hcSite : personUser.getHealthcareSites()){
			remoteResearchStaff.addHealthcareSite(hcSite);
		}
		remoteResearchStaff.setAssignedIdentifier(personUser.getAssignedIdentifier());
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
	public List<PersonUser> getPersonUsersByOrganizationNCIInstituteCode(
			HealthcareSite healthcareSite) {
		RemotePersonUser remoteResearchStaff = new RemotePersonUser();
		remoteResearchStaff.addHealthcareSite(healthcareSite);
		getRemoteResearchStaffFromResolverByExample(remoteResearchStaff);

		//run a query against the updated database to get all research staff
		return getResearchStaffByOrganizationCtepCodeFromLocal(healthcareSite, false);
	}

	/**
	 * Gets the research staff by organization ctep code from local.
	 *
	 * @param healthcareSite the healthcare site
	 * @param isUser returns staff who are users if passed as true else returns all staff
	 * @return the research staff by organization ctep code from local
	 */
	public List<PersonUser> getResearchStaffByOrganizationCtepCodeFromLocal(
			HealthcareSite healthcareSite, boolean isUser) {
		//run a query against the updated database to get all research staff
		Criteria researchStaffCriteria = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createCriteria(PersonUser.class);
		if(isUser){
			researchStaffCriteria.add(Expression.isNotNull("loginId"));
		}
		
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
	public List<RemotePersonUser> getRemoteResearchStaffFromResolverByExample(
			RemotePersonUser remoteResearchStaff) {
		List<Object> objectList = remoteSession.find(remoteResearchStaff);
		List<RemotePersonUser> researchStaffList = new ArrayList<RemotePersonUser>();

		RemotePersonUser retrievedRemoteResearchStaff;
		for (Object object : objectList) {
			retrievedRemoteResearchStaff = (RemotePersonUser) object;
			List<HealthcareSite> healthcareSites = new ArrayList<HealthcareSite>();
			healthcareSites.addAll(retrievedRemoteResearchStaff.getHealthcareSites());
			if (healthcareSites.size() > 0) {
				// If the organization attached to the staff is in the db use it. Else create it.
				for(HealthcareSite hcs : healthcareSites ){
					HealthcareSite matchingHealthcareSiteFromDb = healthcareSiteDao.getByPrimaryIdentifierFromLocal(hcs.getPrimaryIdentifier());
					if (matchingHealthcareSiteFromDb == null) {
						log.error("No Organization exists for the CTEP Code:" + hcs.getPrimaryIdentifier());
						try {
							healthcareSiteDao.save(hcs);
						} catch (C3PRBaseRuntimeException e) {
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
			List<RemotePersonUser> remoteResearchStaffList) {

		try {
			for (RemotePersonUser remoteResearchStaff : remoteResearchStaffList) {
				List<PersonUser> researchStaffFromDatabase = getByExternalIdentifierFromLocal(remoteResearchStaff
						.getExternalId());
				PersonUser preExistingStaff = null;
				if (researchStaffFromDatabase.size() > 0) {
					// this guy already exists as remote staff...simply update the collections . i.e contact mech and orgs.
					preExistingStaff = researchStaffFromDatabase.get(0);
					updateContactMechanisms(preExistingStaff, remoteResearchStaff);
					for(HealthcareSite healthcareSite: remoteResearchStaff.getHealthcareSites()){
						if(!preExistingStaff.getHealthcareSites().contains(healthcareSite)){
							preExistingStaff.addHealthcareSite(healthcareSite);
						}
					}
					save(preExistingStaff);
				} else {
					// Ensure the staff has an organization and that its assignedId is unique.
					if (remoteResearchStaff.getHealthcareSites().size() > 0) {
						PersonUser researchStaffWithMatchingAssignedIdentifier = 
								getByAssignedIdentifierFromLocal(remoteResearchStaff.getAssignedIdentifier());
						if (researchStaffWithMatchingAssignedIdentifier == null) {
							createResearchStaff(remoteResearchStaff);
						} else {
							log.error("Unable to save Remote Staff : "	+ remoteResearchStaff.getFullName()
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
 	private void updateContactMechanisms(PersonUser staffToBeUpdated, PersonUser staffToBeDiscarded){
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
	public PersonUser createResearchStaff(PersonUser researchStaff) throws C3PRBaseException {
		return createOrModifyPersonUser(researchStaff, false, null, null);
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
		if(StringUtils.isNotBlank(c3prUser.getPhone())){
			csmUser.setPhoneNumber(c3prUser.getPhone());
		}
	}


	/**
	 * This method queries the external system to fetch all the matching
	 * ResearchStaff.
	 *
	 * @param researchStaff the research staff
	 * @return the remote research staff
	 */
	public List<PersonUser> getRemoteResearchStaff( final PersonUser researchStaff) {
		PersonUser searchCriteria = new RemotePersonUser();
		searchCriteria.setAssignedIdentifier(researchStaff.getAssignedIdentifier());
		List<PersonUser> remoteResearchStaffs = (List)remoteSession.find(searchCriteria);
		return remoteResearchStaffs;
	}
	
	/**
	 * Creates or modifies staff and csmUser; assign groups and save/update Staff
	 * 1. To create/merge Staff only: createCsmUser=false and username should be blank or null. personUser must have assigned id.
	 * 2. To create/merge CSM user only: pass in personUser without assigned id. PersonUser will be saved but wont have an assignedId. provide username.
	 * 3. To create/merge Staff and CSM User both:personUser must have assigned id. provide username.
	 * Call via PersonUserRepositoryImpl and never directly.
	 *
	 * @param personUser the staff
	 * @param createCsmUser the create csm user
	 * @param username the username
	 * @param associationMap the association map. only used if creating user.
	 * @param hasAccessToAllSites the has access to all sites. only used if creating user.
	 * @return the research staff
	 * @throws C3PRBaseException the C3PR base exception
	 */
	@Transactional
	public PersonUser createOrModifyPersonUser(PersonUser personUser, boolean createCsmUser, String username, 
			List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) throws C3PRBaseException {
		if(createCsmUser || StringUtils.isNotBlank(username)){
			saveOrUpdateCSMUser(personUser, username, listAssociation);
		} else {
			log.debug("Not creating csm user as createCsmUser flag is set to false.");
		}
		
		log.debug("Saving Research Staff");
		personUser = saveOrUpdatePersonUser(personUser);
			
		return personUser;
	}
	
	
	/**
	 * Save or update the PersonUser. Does not save CSM User. Doesnt save if
	 * staff's assignedID is not null and already in the system
	 *
	 * @param personUser the person user
	 * @return the person user
	 */
	public PersonUser saveOrUpdatePersonUser(PersonUser personUser){
		if(personUser.getId() == null){
			if(personUser.getAssignedIdentifier() != null && 
					getByAssignedIdentifierFromLocal(personUser.getAssignedIdentifier()) != null){
				log.error("Save aborted as Research Staff with AssignedIdentifier " +personUser.getAssignedIdentifier() 
							+" already exists in the system.");
				return personUser;
			}
			save(personUser);
		} else {
			personUser = (PersonUser) merge(personUser);
		}
		return personUser;
	}
	

	/**
	 * Creates or modifies the csm user. Also assigns the studies and sites from the association map provided.
	 *
	 * @param personUser the person user
	 * @param username the CSM username
	 * @param associationMap the association map
	 * @param hasAccessToAllSites the has access to all sites
	 * @return the user
	 * @throws C3PRBaseException the c3pr base exception
	 */
	private User saveOrUpdateCSMUser(PersonUser personUser, String username, 
			List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation)
			throws C3PRBaseException {
		
		gov.nih.nci.security.authorization.domainobjects.User csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
		boolean csmUserExists = false;
		try {
			csmUser = getCSMUser(personUser);
			if(csmUser != null){
				csmUserExists = true ;
			}
		} catch (CSObjectNotFoundException e) {
			log.debug("CSM user does not exist.");
		}
		
		//create a CSM user if he doesn't exist and a username has been provided
		if(!csmUserExists && StringUtils.isNotBlank(username)){
			try {
				log.debug("Creating CSM user.");
				populateCSMUser(personUser, csmUser);
				csmUser.setLoginName(username.toLowerCase());
				csmUser.setStartDate(Calendar.getInstance().getTime());
				csmUser.setPassword(((edu.duke.cabig.c3pr.domain.User)personUser).generatePassword());
				userProvisioningManager.createUser(csmUser);
				personUser.setLoginId(csmUser.getUserId().toString());
				UserDao.addUserToken((edu.duke.cabig.c3pr.domain.User) personUser);
			} catch (CSTransactionException e) {
				throw new C3PRBaseException("Could not create user", e);
			}
		} else {
			//modify the existing csmUser
			log.debug("Updating existing CSM user.");
			try {
				populateCSMUser(personUser, csmUser);
				//update endDate (for user status) if necessary
				updateEndDate(personUser, csmUser);
				userProvisioningManager.modifyUser(csmUser);
			} catch (CSTransactionException e) {
				throw new C3PRBaseException("not able to update CSM user", e);
			} 
		}
		
		if(listAssociation != null){
			assignRolesStudiesAndOrganizationsToUser(csmUser, listAssociation);
		}
		return csmUser;
	}
	
	
	/**
	 * Update end date. Set it to null to activate user and set it to today to de-activate user.
	 *
	 * @param personUser the person user
	 * @param csmUser the csm user
	 */
	private void updateEndDate(PersonUser personUser, User csmUser) {
		boolean isDeactivated = SecurityUtils.isUserDeactivated(csmUser.getEndDate());
		if(isDeactivated==true && personUser.getUserStatus().equals(StatusType.AC.getName()) ){
			//activate user
			csmUser.setEndDate(null);
		}
		if(isDeactivated==false && personUser.getUserStatus().equals(StatusType.IN.getName()) ){
			//de-activate user
			csmUser.setEndDate(Calendar.getInstance().getTime());
		}
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
	private void assignRolesStudiesAndOrganizationsToUser(User csmUser,
			List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) {
		
		ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
		C3PRUserGroupType groupArray[] = C3PRUserGroupType.values();
		for(RoleBasedHealthcareSitesAndStudiesDTO dataHolder : listAssociation){
			setSitesAndStudies(csmUser, provisioningSession, dataHolder);
			//if any of the global roles are unchecked for every site then delete them.
			for(int i=0;i<groupArray.length;i++){
				if(groupArray[i] != null && groupArray[i].equals(dataHolder.getGroup())){
					groupArray[i] = null;
				}
			}
		}
		deleteRole(provisioningSession, groupArray);
		return;
	}
	
	
	/**
	 * Sets the sites and studies.
	 *
	 * @param suiteRoleMembership the suite role membership
	 * @param healthcareSite the healthcare site
	 * @param hasAccessToAllSites the has access to all sites
	 */
	private void setSitesAndStudies(User csmUser, ProvisioningSession provisioningSession, RoleBasedHealthcareSitesAndStudiesDTO roleBasedHealthcareSitesAndStudiesHolder) {
		
		//iterate over the newly assigned c3prGroup list and add/edit the corresponding
		//suiteRoleMemberships in the user's provisioningSession
		boolean hasAccessToAllSites = roleBasedHealthcareSitesAndStudiesHolder.getHasAllSiteAccess();
		boolean hasAccessToAllStudies = roleBasedHealthcareSitesAndStudiesHolder.getHasAllStudyAccess();
		
		SuiteRole suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(roleBasedHealthcareSitesAndStudiesHolder.getGroup());
		SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
		if(suiteRole != null){
			if(!suiteRole.getScopes().contains(ScopeType.STUDY) && !suiteRole.getScopes().contains(ScopeType.SITE)){
				//provisioning for global non site scoped role with all site access
				provisioningSession.replaceRole(suiteRoleMembership);
			} else {
				SuiteRoleMembership newSuiteRoleMembership= new SuiteRoleMembership(suiteRole, null, null);
				if(suiteRole.getScopes().contains(ScopeType.SITE)){
					//Get the suiteRoleMembership and edit it with the new changes
					//global_role is po_mgr: giving him all site access as c3pr requirement.
					if(hasAccessToAllSites || suiteRole.equals(SecurityUtils.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER)){
						newSuiteRoleMembership.forAllSites();
					} else {
						//This ensures the SRM doesn't have all-site access since the all site access chkbox was unchecked.
						//Expects the String to be in the following format "NC010".
						for(String hcs: roleBasedHealthcareSitesAndStudiesHolder.getSites()){
							newSuiteRoleMembership.addSite(hcs);
						}
					}
				}
				if(suiteRole.getScopes().contains(ScopeType.STUDY)){	
					if(hasAccessToAllStudies){
						newSuiteRoleMembership.forAllStudies();
					} else {
						//This ensures the SRM doesn't have all-study access since the all study access chkbox was unchecked.
						//Expects the String to be in the following format "CCAI-123".
						for(String studyId: roleBasedHealthcareSitesAndStudiesHolder.getStudies()){
							newSuiteRoleMembership.addStudy(studyId);
						}
					}
				} 
				provisioningSession.replaceRole(newSuiteRoleMembership);
			}
		}	
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
     * Gets the groups for user.
     *
     * @return the groups for user
     */
    public List<C3PRUserGroupType> getGroupsForUser(User csmUser){
    	
    	List<C3PRUserGroupType> groupList = new ArrayList<C3PRUserGroupType>();
    	try {
    		Set<Group> groups = userProvisioningManager.getGroups(csmUser.getUserId().toString());
    		Iterator<Group> iter = groups.iterator();
	    	String groupName;
	    	while(iter.hasNext()){
	    		groupName = ((Group)iter.next()).getGroupName();
	    		groupList.add(C3PRUserGroupType.getByCode(groupName));
	    	}
		} catch (CSObjectNotFoundException e) {
			log.error(e.getMessage());
		}
		return groupList;
    }
    
    
    /**
     * Gets a list of organizations on which the user has the passed in role.
     * The associated orgs are generally fetched from the staff but this
     * is Used to fetch the orgs associated to a user.
     *
     * @param csmUser the csm user
     * @param c3prUserGroupType the c3pr user group type
     * @return the organization ids for user
     */
    public List<String> getOrganizationIdsForUser(User csmUser, C3PRUserGroupType c3prUserGroupType){
    	Set<String> organizationIdSet = new HashSet<String>();
    	if(!SecurityUtils.isGlobalRole(c3prUserGroupType)){
    		ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
    		return getOrganizationIdsForUser(provisioningSession, c3prUserGroupType);
    	}
        return new ArrayList<String>(organizationIdSet);
    }
    
    /**
     * Gets the organization ids for user.
     *
     * @param provisioningSession the provisioning session
     * @param c3prUserGroupType the c3pr user group type
     * @return the organization ids for user
     */
    public List<String> getOrganizationIdsForUser(ProvisioningSession provisioningSession, C3PRUserGroupType c3prUserGroupType){
    	Set<String> organizationIdSet = new HashSet<String>();
    	if(!SecurityUtils.isGlobalRole(c3prUserGroupType)){
            SuiteRole suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(c3prUserGroupType);
            SuiteRoleMembership suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
    	    //add all site identifiers to a set
    		if(suiteRoleMembership.hasSiteScope() && !suiteRoleMembership.isAllSites()){
    			organizationIdSet.addAll(suiteRoleMembership.getSiteIdentifiers());
    		}
    	}
        return new ArrayList<String>(organizationIdSet);
    }
    
    /**
     * Gets a list of studies on which the user has the passed in role.
     *
     * @param csmUser the csm user
     * @param c3prUserGroupType the c3pr user group type
     * @return the study ids for user
     */
    public List<String> getStudyIdsForUser(User csmUser, C3PRUserGroupType c3prUserGroupType){
    	Set<String> studyIdSet = new HashSet<String>();
    	if(!SecurityUtils.isGlobalRole(c3prUserGroupType)){
        	ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
        	return getStudyIdsForUser(provisioningSession, c3prUserGroupType);
    	}
    	return new ArrayList<String>(studyIdSet);
    }
    
    /**
     * Gets the study ids for user.
     *
     * @param provisioningSession the provisioning session
     * @param c3prUserGroupType the c3pr user group type
     * @return the study ids for user
     */
    public List<String> getStudyIdsForUser(ProvisioningSession provisioningSession, C3PRUserGroupType c3prUserGroupType){
    	Set<String> studyIdSet = new HashSet<String>();
    	
        SuiteRoleMembership suiteRoleMembership;
        SuiteRole suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(c3prUserGroupType);
		suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
	    //add all site identifiers to a set
		if(suiteRoleMembership.hasStudyScope() && !suiteRoleMembership.isAllStudies()){
			studyIdSet.addAll(suiteRoleMembership.getStudyIdentifiers());
		}
        return new ArrayList<String>(studyIdSet);
    }
    
    /**
     * Checks for all site access. Returns false if user is not scoped by site at all.
     *
     * @return true, if successful
     */
    public boolean getHasAccessToAllSites(User csmUser, C3PRUserGroupType group){
    	ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
    	return getHasAccessToAllSites(provisioningSession, group);
    }
    
    /**
     * Gets the checks for access to all sites.
     *
     * @param provisioningSession the provisioning session
     * @param group the group
     * @return the checks for access to all sites
     */
    public boolean getHasAccessToAllSites(ProvisioningSession provisioningSession, C3PRUserGroupType group){
    	SuiteRoleMembership suiteRoleMembership;
        SuiteRole suiteRole;
		//global roles are not considered for the all sites access checkbox value.
		suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(group);
		if(suiteRole.getScopes().contains(ScopeType.SITE)){
			suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
		    //include roles that are scoped by site and have access to all sites or the site in question
		    if(suiteRoleMembership.isAllSites()){
		        return true;
		    }
		}
        return false;
    }
    

    /**
     * Gets the checks for access to all studies. Returns false if user is not scoped by study at all.
     *
     * @param csmUser the csm user
     * @param group the group
     * @return the checks for access to all studies
     */
    public boolean getHasAccessToAllStudies(User csmUser, C3PRUserGroupType group){
    	ProvisioningSession provisioningSession = provisioningSessionFactory.createSession(csmUser.getUserId());
    	return getHasAccessToAllStudies(provisioningSession, group);
    }
    
    /**
     * Gets the provisioning session.
     *
     * @param csmUser the csm user
     * @return the provisioning session
     */
    public ProvisioningSession getProvisioningSession(User csmUser){
    	return provisioningSessionFactory.createSession(csmUser.getUserId());
    }
    
    /**
     * Gets the checks for access to all studies.
     *
     * @param provisioningSession the provisioning session
     * @param group the group
     * @return the checks for access to all studies
     */
    public boolean getHasAccessToAllStudies(ProvisioningSession provisioningSession, C3PRUserGroupType group){
    	SuiteRoleMembership suiteRoleMembership;
        SuiteRole suiteRole;
		//global roles are not considered for the all studies access checkbox value.
		suiteRole = C3PRUserGroupType.getUnifiedSuiteRole(group);
		if(suiteRole.getScopes().contains(ScopeType.STUDY)){
			suiteRoleMembership = provisioningSession.getProvisionableRoleMembership(suiteRole);
		    //include roles that are scoped by site and have access to all sites or the site in question
		    if(suiteRoleMembership.isAllStudies()){
		        return true;
		    }
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
	public HashMap<PersonUser, List<String>> getStaffScopedByStudy(List<PersonUser> staffList, HealthcareSite healthcareSite) {
		HashMap<PersonUser, List<String>> reducedHcsRsMap = new HashMap<PersonUser, List<String>>();
		User user = null;
		for (PersonUser researchStaff : staffList) {
			try {
				user = getCSMUser(researchStaff);
			} catch (CSObjectNotFoundException e) {
				logger.error("Failed to load user for :"+ researchStaff.getFirstName());
				logger.error(e.getMessage());
				continue;
			}
			if(user != null){
				for (C3PRUserGroupType role : C3PRUserGroupType.getStudyScopedRoles()) {
					try {
						if (userProvisioningManager.checkPermission(user.getLoginName(), "HealthcareSite." + healthcareSite.getPrimaryIdentifier(), role.getCode()) ||
								(userProvisioningManager.checkPermission(user.getLoginName(), "HealthcareSite", role.getCode()))) {
							//add this rs with corresponding studyScoped role to the hashmap of staff vs roles
							if(reducedHcsRsMap.containsKey(researchStaff)){
								((ArrayList<String>) reducedHcsRsMap.get(researchStaff)).add(role.getCode());
								
							} else {
								ArrayList<String> roleList = new ArrayList<String>();
								roleList.add(role.getCode());
								reducedHcsRsMap.put(researchStaff, roleList);
							}
						}
					} catch (CSObjectNotFoundException e) {
						log.error(e.getMessage());
					} catch (CSException e) {
						log.error(e.getMessage());
					}
				}
			} else {
				log.warn("No csm user exists for staff with first Name: "+researchStaff.getFirstName());
			}
		}
		return reducedHcsRsMap;
	}
	
	public List<PersonUser> getAllStudyScopedStaff(List<PersonUser> staffList, HealthcareSite healthcareSite) {
		Set<PersonUser> researchStaffSet = new HashSet<PersonUser>();
		User user = null;
		for (PersonUser researchStaff : staffList) {
			try {
				user = getCSMUser(researchStaff);
			} catch (CSObjectNotFoundException e) {
				logger.error("Failed to load user for :"+ researchStaff.getFirstName());
				logger.error(e.getMessage());
				continue;
			}
			if(user != null){
				for (C3PRUserGroupType role : C3PRUserGroupType.getStudyScopedRoles()) {
					try {
						if (userProvisioningManager.checkPermission(user.getLoginName(), "Study", role.getCode())) {
							researchStaffSet.add(researchStaff);
						}
					} catch (CSObjectNotFoundException e) {
						log.error(e.getMessage());
					} catch (CSException e) {
						log.error(e.getMessage());
					}
				}
			} else {
				log.warn("No csm user exists for staff with first Name: "+researchStaff.getFirstName());
			}
		}
		
		return new ArrayList<PersonUser>(researchStaffSet);
	}

	


	/**
	 * Check user access for site for the role that is passed in.
	 *
	 * @param csmUser the csm user
	 * @param healthcareSite the healthcare site
	 * @param role the role
	 * @return true, if successful
	 */
	public boolean checkUserAccessForSite(User csmUser,
			HealthcareSite healthcareSite, List<C3PRUserGroupType> roles) {
		for (C3PRUserGroupType role : roles) {
			try {
				if (userProvisioningManager.checkPermission(csmUser.getLoginName(), "HealthcareSite." + healthcareSite.getPrimaryIdentifier(), role.getCode()) ||
						(userProvisioningManager.checkPermission(csmUser.getLoginName(), "HealthcareSite", role.getCode()))) {
					return true;
				}
			} catch (CSObjectNotFoundException e) {
				log.error(e.getMessage());
			} catch (CSException e) {
				log.error(e.getMessage());
			}
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
	
