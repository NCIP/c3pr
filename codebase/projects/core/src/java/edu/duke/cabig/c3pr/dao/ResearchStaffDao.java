package edu.duke.cabig.c3pr.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.mail.MailException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.semanticbits.coppa.infrastructure.RemoteSession;

import edu.duke.cabig.c3pr.dao.query.ResearchStaffQuery;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.domain.ContactMechanism;
import edu.duke.cabig.c3pr.domain.ContactMechanismType;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.dao.GroupSearchCriteria;
import gov.nih.nci.security.exceptions.CSObjectNotFoundException;
import gov.nih.nci.security.exceptions.CSTransactionException;

/**
 * Hibernate implementation of ResearchStaffDao.
 * 
 * @see edu.duke.cabig.c3pr.dao.ResearchStaffDao
 * @author Priyatam
 */
public class ResearchStaffDao extends GridIdentifiableDao<ResearchStaff> {

    /** The log. */
    private static Log log = LogFactory.getLog(InvestigatorDao.class);

    /** The Constant SUBSTRING_MATCH_PROPERTIES. */
    private static final List<String> SUBSTRING_MATCH_PROPERTIES = Arrays.asList("firstName","lastName");
    
    /** The Constant SUBNAME_SUBEMAIL_MATCH_PROPERTIES. */
    private static final List<String> SUBNAME_SUBEMAIL_MATCH_PROPERTIES = Arrays.asList("firstName","lastName","contactMechanisms.value");

    /** The Constant EXACT_MATCH_PROPERTIES. */
    private static final List<String> EXACT_MATCH_PROPERTIES = Collections.emptyList();

    /** The Constant EXTRA_PARAMS. */
    private static final List<Object> EXTRA_PARAMS = Collections.emptyList();
    
    private RemoteSession remoteSession;
    
    private UserProvisioningManager userProvisioningManager;
    
    private CSMObjectIdGenerator siteObjectIdGenerator;
    
    private HealthcareSiteDao healthcareSiteDao;

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.dao.C3PRBaseDao#domainClass()
     */
    @Override
    public Class<ResearchStaff> domainClass() {
        return ResearchStaff.class;
    }

    /**
     * Gets  all ResearchStaff.
     * 
     * @return all ResearchStaff
     */
    public List<ResearchStaff> getAll() {
        return getHibernateTemplate().find("from ResearchStaff");
    }

    /**
     * Gets the by subnames.
     * 
     * @param subnames the subnames
     * @param healthcareSite the healthcare site
     * 
     * @return the by subnames
     */
    public List<ResearchStaff> getBySubnames(String[] subnames, int healthcareSite) {
        return findBySubname(subnames, "o.healthcareSite.id = '" + healthcareSite + "'",
                        EXTRA_PARAMS, SUBSTRING_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }
    
    /**
     * Gets the by sub name and sub email.
     * 
     * @param subnames the subnames
     * @param nciInstituteCode the nci institute code
     * 
     * @return the by sub name and sub email
     */
    public List<ResearchStaff> getBySubNameAndSubEmail(String[] subnames, String nciInstituteCode) {
        return findBySubname(subnames, "o.healthcareSite.nciInstituteCode = '" + nciInstituteCode + "'",
                        EXTRA_PARAMS, SUBNAME_SUBEMAIL_MATCH_PROPERTIES, EXACT_MATCH_PROPERTIES);
    }

    /**
     * Search by example.
     * 
     * @param staff the staff
     * @param isWildCard the is wild card
     * 
     * @return the list< research staff>
     */
    public List<ResearchStaff> searchByExample(ResearchStaff staff, boolean isWildCard) {
    	
    	//get the remote staff and update the database first
        RemoteResearchStaff remoteResearchStaff = convertToRemoteResearchStaff(staff);
        getAndUpdateRemoteResearchStaff(remoteResearchStaff);
        
        List<ResearchStaff> result = new ArrayList<ResearchStaff>();

        Example example = Example.create(staff).excludeZeroes().ignoreCase();
        example.excludeProperty("salt");
        example.excludeProperty("passwordLastSet");
        try {
            Criteria criteria = getSession().createCriteria(ResearchStaff.class);
            criteria.addOrder(Order.asc("nciIdentifier"));
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

            if (isWildCard) {
                example.enableLike(MatchMode.ANYWHERE);
                criteria.add(example);
                if (staff.getHealthcareSite() != null) {
                    criteria.createCriteria("healthcareSite").add(
                               Restrictions.ilike("name", "%" + staff.getHealthcareSite().getName() + "%"));
                }
                result = criteria.list();
            }
            else {
                result = criteria.add(example).list();
            }
        }
        catch (Exception e) {
        	e.printStackTrace();
            log.error(e.getMessage());
        }
        return result;
    }

    
    /**
     * Search research staff.
     * 
     * @param query the query
     * 
     * @return the list< research staff>
     */
    @SuppressWarnings( { "unchecked" })
    public List<ResearchStaff> searchResearchStaff(final ResearchStaffQuery query) {
        String queryString = query.getQueryString();
        log.debug("::: " + queryString.toString());
        return (List<ResearchStaff>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) throws HibernateException,
                            SQLException {
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
     * Gets the by nci identifier. Looks for local and remote
     * 
     * @param nciIdentifier the nci identifier
     * 
     * @return the by nci identifier
     */
    public ResearchStaff getByNciIdentifier(String nciIdentifier) {
    	//get the remote staff and update the database first
        RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
        remoteResearchStaff.setNciIdentifier(nciIdentifier);
        
        getAndUpdateRemoteResearchStaff(remoteResearchStaff);
        
        ResearchStaff result = null;
        try {
            result = (ResearchStaff)(getHibernateTemplate().find("from ResearchStaff rs where rs.nciIdentifier = '" +nciIdentifier+ "'").get(0));
        }
        catch (Exception e) {
            log.debug("User with nciIdentifier " + nciIdentifier + " does not exist. Returning null");
        }
        return result;
    }

    
    /**
     * Gets the by email address. Created for the notifications use case.
     * 
     * @param emailAddress the email address
     * 
     * @return the ResearchStaff List
     */
    public List<ResearchStaff> getByEmailAddress(String emailAddress) {
    	//get the remote staff and update the database first
        RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
        remoteResearchStaff.setUniqueIdentifier(emailAddress);
        
        getAndUpdateRemoteResearchStaff(remoteResearchStaff);
        
        return getHibernateTemplate().find("from ResearchStaff rs where rs.contactMechanisms.value = '" +emailAddress+ "'");
    }
    
    /**
     * Gets the by unique identifier. Created for the remote research staff use case.
     * 
     * @param emailAddress the email address
     * 
     * @return the ResearchStaff List
     */
    public List<ResearchStaff> getByUniqueIdentifier(String emailAddress) {
    	//get the remote staff and update the database first
//        RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
//        remoteResearchStaff.setUniqueIdentifier(emailAddress);
//        getAndUpdateRemoteResearchStaff(remoteResearchStaff);
        
    	List<ResearchStaff> researchStaffList = new ArrayList<ResearchStaff>();
    	researchStaffList.addAll(getHibernateTemplate().find("from LocalResearchStaff rs where rs.contactMechanisms.value = '" +emailAddress+ "'"));
    	researchStaffList.addAll(getHibernateTemplate().find("from RemoteResearchStaff rs where rs.uniqueIdentifier = '" +emailAddress+ "'"));
        return researchStaffList;
    }
    
    
    /**
     * Convert to remote research staff. Only include the properties that COPPA understands
     * 
     * @param researchStaff the research staff
     * 
     * @return the remote research staff
     */
    private RemoteResearchStaff convertToRemoteResearchStaff(ResearchStaff researchStaff){
    	if(researchStaff instanceof RemoteResearchStaff){
    		return (RemoteResearchStaff)researchStaff;
    	}
    	
    	RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
    	remoteResearchStaff.setAddress(researchStaff.getAddress());
    	remoteResearchStaff.setFirstName(researchStaff.getFirstName());
    	remoteResearchStaff.setLastName(researchStaff.getLastName());
    	remoteResearchStaff.setHealthcareSite(researchStaff.getHealthcareSite());
    	remoteResearchStaff.setNciIdentifier(researchStaff.getNciIdentifier());
    	return remoteResearchStaff;
    }
    
    /**
     * First gets the staff from COPPA based on the hcs.nciCode and saves it to the db.
     * Since hcs is not transient it is saved along with the staff.
     * Then gets btoh the local and remote research staff by organization nci institute code from the database.
     * 
     * @param nciInstituteCode the nci institute code
     * 
     * @return the research staff by organization nci institute code
     */
    public List<ResearchStaff> getResearchStaffByOrganizationNCIInstituteCode(HealthcareSite healthcareSite) {
    	RemoteResearchStaff remoteResearchStaff = new RemoteResearchStaff();
    	remoteResearchStaff.setHealthcareSite(healthcareSite);
    	getAndUpdateRemoteResearchStaff(remoteResearchStaff);
    	
    	//run a query against the updated database to get all research staff
    	List<ResearchStaff> completeResearchStaffListFromDatabase =  
    		getHibernateTemplate().find("from ResearchStaff rs where rs.healthcareSite.nciInstituteCode = '" +healthcareSite.getNciInstituteCode()+ "'");
    	return completeResearchStaffListFromDatabase;
    }
    
    
    private void getAndUpdateRemoteResearchStaff(RemoteResearchStaff remoteResearchStaff){
    	//First get the remote content
    	List<RemoteResearchStaff> remoteResearchStaffList =  getRemoteResearchStaffFromResolverByExample(remoteResearchStaff);
    	//update the database with the remote content
    	updateDatabaseWithRemoteContent(remoteResearchStaffList);
    }
    
    /**
     * Gets the research staff by organization nci institute code from the resolver.
     * 
     * @param nciInstituteCode the nci institute code
     * 
     * @return the research staff by organization nci institute code
     */
    public List<RemoteResearchStaff> getRemoteResearchStaffFromResolverByExample(RemoteResearchStaff remoteResearchStaff){
    	List<Object> objectList = remoteSession.find(remoteResearchStaff);
    	List<RemoteResearchStaff> researchStaffList = new ArrayList<RemoteResearchStaff>();
    	
    	RemoteResearchStaff tempRemoteResearchStaff;
    	for(Object object: objectList){
    		tempRemoteResearchStaff = (RemoteResearchStaff)object;
    		if(tempRemoteResearchStaff.getHealthcareSite() == null && remoteResearchStaff.getHealthcareSite() != null){
    			//if the resolver hasnt set the hcs then set it if it has been passed in
    			tempRemoteResearchStaff.setHealthcareSite(remoteResearchStaff.getHealthcareSite());
    		} else {
    			//if the resolver hasnt set the hcs and if it hasn't been passed in then..
    			//get the corresponding hcs from the dto object and save that organization and then save this staff
    			//for now defaulting to duke
    			tempRemoteResearchStaff.setHealthcareSite(healthcareSiteDao.getByNciInstituteCode("NC010"));
    		}
    		researchStaffList.add(tempRemoteResearchStaff);
    	}
    	return researchStaffList;
    }
    

    /**
     * Update database with remote content.
     * 
     * @param remoteResearchStaffList the remote research staff list
     */
    public void updateDatabaseWithRemoteContent(List<RemoteResearchStaff> remoteResearchStaffList){
    	
    	for (RemoteResearchStaff remoteResearchStaff: remoteResearchStaffList) {
    		List<ResearchStaff> researchStaffFromDatabase = getByUniqueIdentifier(remoteResearchStaff.getUniqueIdentifier());
   			if(researchStaffFromDatabase.size() > 0){
   				//this guy already exists....call mergeResearchStaff
   				try {
					merge((ResearchStaff)remoteResearchStaff);
					getHibernateTemplate().flush();
				} catch (C3PRBaseException e) {
					log.error(e.getMessage());
				}
   			} else{
   				//this guy doesnt exist
   				try{
   					//This if condition is temporary. once we have the logic to fetch the org of the retrieved staff then we can remove this.
   					if(remoteResearchStaff.getHealthcareSite() != null){
   						saveResearchStaff(remoteResearchStaff);
   						getHibernateTemplate().flush();
   					}
   				} catch (C3PRBaseException cbe){
   					log.error(cbe.getMessage());
   				}
   			}
    	}
    }

    
    /*
	 * Moved save code here from personnelServiceImpl for coppa integration
	 */
    public void saveResearchStaff(ResearchStaff staff) throws C3PRBaseException {
        save(staff, null);

        try {
            User csmUser = getCSMUser(staff);
            csmUser.setOrganization(staff.getHealthcareSite().getNciInstituteCode());
            assignUserToGroup(csmUser, siteObjectIdGenerator.generateId(staff.getHealthcareSite()));
            log.debug("Successfully assigned user to organization group"
                            + siteObjectIdGenerator.generateId(staff.getHealthcareSite()));
        }
        catch (CSObjectNotFoundException e) {
            new C3PRBaseException("Could not assign user to organization group.");
        }
    }

	
    private User getCSMUser(C3PRUser user) throws CSObjectNotFoundException {
        return userProvisioningManager.getUserById(user.getLoginId());
    }

    
	private void save(C3PRUser c3prUser,
            gov.nih.nci.security.authorization.domainobjects.User csmUser)
            throws C3PRBaseException, MailException {
		try {
		    if (csmUser == null) {
		        csmUser = new gov.nih.nci.security.authorization.domainobjects.User();
		        populateCSMUser(c3prUser, csmUser);
		        userProvisioningManager.createUser(csmUser);
		    }
		    else {
		        populateCSMUser(c3prUser, csmUser);
		        userProvisioningManager.modifyUser(csmUser);
		    }
		
		    log.debug("Saving c3pr user");
		    this.save(c3prUser);
		    c3prUser.setLoginId(csmUser.getUserId().toString());
		
		    assignUsersToGroup(csmUser, c3prUser.getGroups());
		}
		catch (CSTransactionException e) {
		    throw new C3PRBaseException("Could not create user", e);
		}
	}
	
	
	private void populateCSMUser(C3PRUser c3prUser,
            gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		csmUser.setFirstName(c3prUser.getFirstName());
		csmUser.setLastName(c3prUser.getLastName());
		csmUser.setPassword(c3prUser.getLastName());
		
		for (ContactMechanism cm : c3prUser.getContactMechanisms()) {
		    if (cm.getType().equals(ContactMechanismType.EMAIL)) {
		        csmUser.setLoginName(cm.getValue().toLowerCase());
		        csmUser.setEmailId(cm.getValue());
		    }
		}
	}

	public UserProvisioningManager getUserProvisioningManager() {
		return userProvisioningManager;
	}

	public void setUserProvisioningManager(
			UserProvisioningManager userProvisioningManager) {
		this.userProvisioningManager = userProvisioningManager;
	}
	
	/*
     * Takes the whole list of groups instead of one ata time .Thsi was crated so the unchecked
     * groups could be deleted.
     */
    private void assignUsersToGroup(User csmUser, List<C3PRUserGroupType> groupList)
                    throws C3PRBaseException {
        Set<String> groups = new HashSet<String>();
        try {
            for (C3PRUserGroupType group : groupList) {
                groups.add(getGroupIdByName(group.getCode()));
            }

            userProvisioningManager.assignGroupsToUser(csmUser.getUserId().toString(), groups
                            .toArray(new String[groups.size()]));
        }
        catch (Exception e) {
            throw new C3PRBaseException("Could not add user to group", e);
        }
    }
    
    private void assignUserToGroup(User csmUser, String groupName) throws C3PRBaseException {
        Set<String> groups = new HashSet<String>();
        try {
            Set<Group> existingSet = userProvisioningManager.getGroups(csmUser.getUserId()
                            .toString());
            for (Group existingGroup : existingSet) {
                groups.add(existingGroup.getGroupId().toString());
            }
            groups.add(getGroupIdByName(groupName));
            userProvisioningManager.assignGroupsToUser(csmUser.getUserId().toString(), groups
                            .toArray(new String[groups.size()]));
        }
        catch (Exception e) {
            throw new C3PRBaseException("Could not add user to group", e);
        }
    }
    
    
    private String getGroupIdByName(String groupName) {
        Group search = new Group();
        search.setGroupName(groupName);
        GroupSearchCriteria sc = new GroupSearchCriteria(search);
        Group returnGroup = (Group) userProvisioningManager.getObjects(sc).get(0);
        return returnGroup.getGroupId().toString();
    }
    /*
	 * Moved save code here from personnelServiceImpl for coppa integration
	 */
    

    /*
	 * Moved merge code here from personnelServiceImpl for coppa integration
	 */
    public void merge(ResearchStaff staff) throws C3PRBaseException {
        try {
            User csmUser = getCSMUser(staff);
            save(staff, csmUser);
        }
        catch (CSObjectNotFoundException e) {
            new C3PRBaseException("Could not save Research staff" + e.getMessage());
        }
        try {
            User csmUser = getCSMUser(staff);
            csmUser.setOrganization(staff.getHealthcareSite().getNciInstituteCode());
            assignUserToGroup(csmUser, siteObjectIdGenerator.generateId(staff.getHealthcareSite()));
            log.debug("Successfully assigned user to organization group"
                            + siteObjectIdGenerator.generateId(staff.getHealthcareSite()));
        }
        catch (CSObjectNotFoundException e) {
            new C3PRBaseException("Could not assign user to organization group.");
        }

    }
    /*
	 * Moved merge code here from personnelServiceImpl for coppa integration
	 */
    
	public void setRemoteSession(RemoteSession remoteSession) {
		this.remoteSession = remoteSession;
	}

	public CSMObjectIdGenerator getSiteObjectIdGenerator() {
		return siteObjectIdGenerator;
	}

	public void setSiteObjectIdGenerator(CSMObjectIdGenerator siteObjectIdGenerator) {
		this.siteObjectIdGenerator = siteObjectIdGenerator;
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

}
