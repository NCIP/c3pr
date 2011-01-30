package edu.duke.cabig.c3pr.web.admin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.AccessDeniedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.utils.ConfigurationProperty;
import edu.duke.cabig.c3pr.utils.Lov;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.ajax.SearchResearchStaffAjaxFacade;

/**
 * Runs a search for all staff and users and merges the two sets and returns the combined results.
 * Invokes the SearchResearchStaffAjaxFacade to generate the results table.
 * 
 * @author Vinay Gangoli
 *
 */
public class SearchPersonOrUserController extends SimpleFormController {

    private static Log log = LogFactory.getLog(SearchPersonOrUserController.class);

    private HealthcareSiteDao healthcareSiteDao;
    
    private PersonUserDao personUserDao;
    
    private ConfigurationProperty configurationProperty;

    private SearchResearchStaffAjaxFacade searchResearchStaffAjaxFacade;
    
    private CSMUserRepository csmUserRepository;
    
    @Override
    protected boolean isFormSubmission(HttpServletRequest request) {
        return super.isFormSubmission(request);
    }

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response,
                                    Object oCommand, BindException errors) throws Exception {
    	
    	SearchPersonOrUserWrapper searchPersonOrUserWrapper = (SearchPersonOrUserWrapper) oCommand;
    	
        String firstName = searchPersonOrUserWrapper.getFirstName();
        String lastName = searchPersonOrUserWrapper.getLastName();
        String emailAddress = searchPersonOrUserWrapper.getEmailAddress();

        //Staff criteria
        String assignedIdentifier = searchPersonOrUserWrapper.getAssignedIdentifier();
        String organizationId = searchPersonOrUserWrapper.getHealthcareSite();
        
        //User criteria
        String loginName = searchPersonOrUserWrapper.getLoginName();
    	
    	boolean searchStaff = false;
    	boolean searchUser = false;
        searchStaff = isStaffSearch(firstName, lastName, emailAddress, assignedIdentifier, organizationId, loginName);
        searchUser = isUserSearch(firstName, lastName, emailAddress, assignedIdentifier, organizationId, loginName);
        
        Set<PersonUser> rStaffResults = new HashSet<PersonUser>();
        
        //Search for staff
        if(searchStaff){
        	LocalPersonUser localPersonUser = new LocalPersonUser();
            if (!StringUtils.isBlank(firstName)) {
                localPersonUser.setFirstName(firstName);
            }
            if (!StringUtils.isBlank(lastName)) {
                localPersonUser.setLastName(lastName);
            }
            if (!StringUtils.isBlank(assignedIdentifier)) {
                localPersonUser.setAssignedIdentifier(assignedIdentifier);
            }
            if (!StringUtils.isBlank(organizationId)) {
                HealthcareSite healthcareSite = healthcareSiteDao.getById(Integer.parseInt(organizationId));
                localPersonUser.addHealthcareSite(healthcareSite);
            }
            
            rStaffResults = new HashSet(personUserDao.searchByExample(localPersonUser, true, emailAddress));
            log.debug("Staff results size " + rStaffResults.size());
        } else {
        	log.debug("Not running Staff search");
        }
    	
        //Search for users
        if(searchUser){
        	//if nothing is specified then get all users. set the % as CSM returns nothing on blank searches.
        	//Note also that CSM searches are exact match searches and not sub-string searches
            if(StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName) && StringUtils.isEmpty(emailAddress) && StringUtils.isEmpty(loginName) 
            		&& StringUtils.isEmpty(assignedIdentifier) && StringUtils.isEmpty(organizationId)){
            	firstName = "%";
            } else if(!StringUtils.isEmpty(loginName)){
            	//add %'s to make the login/username searches "like" searches if login is one of the search criteria.
            	loginName = "%" + loginName + "%";
            }
            
            List<gov.nih.nci.security.authorization.domainobjects.User> csmUsersList = 
            	csmUserRepository.searchCSMUsers(firstName, lastName, emailAddress, loginName);
            log.debug("User results size " + csmUsersList.size());
            
            //get corresponding staff for fetched users so searchStaffFacade can display it
            PersonUser personUser;
            for(gov.nih.nci.security.authorization.domainobjects.User user: csmUsersList){
            	try{
                	personUser = personUserDao.getByLoginId(user.getUserId().toString());
                	if(personUser != null){
                		rStaffResults.add(personUser);
                	}
            	} catch(AccessDeniedException ade){
            		log.warn("filtering out CSM-user with id : "+user.getUserId());
            	}
            }
        } else {
        	log.debug("Not searching for Users.");
        }
        
        Map<String, Object> map = errors.getModel();
        map.put("personOrUserResults", rStaffResults);
        String innerHtml = "";
        try {
            innerHtml = searchResearchStaffAjaxFacade.getPersonOrUserTable(map, request);
        }
        catch (Exception e) {
            log.error("Exception caught in SearchResearchStaffAjaxFacade", e);
        }
        
        response.getWriter().print(innerHtml);
        return null;
    }


	/** Determines whether to run staff search or not
	 * 
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param assignedIdentifier
	 * @param organizationId
	 * @param loginName
	 * @return
	 */
	private boolean isStaffSearch(String firstName, String lastName,
			String emailAddress, String assignedIdentifier,
			String organizationId, String loginName) {
		//run staff search if nothing is specified
		if(StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName) && StringUtils.isEmpty(emailAddress) && StringUtils.isEmpty(loginName) 
        		&& StringUtils.isEmpty(assignedIdentifier) && StringUtils.isEmpty(organizationId)){
			return true;
		}
		//run staff search if either basic or staff criteria is specified
		if(!StringUtils.isEmpty(firstName) || !StringUtils.isEmpty(lastName) || !StringUtils.isEmpty(emailAddress) 
        		|| !StringUtils.isEmpty(assignedIdentifier) || !StringUtils.isEmpty(organizationId)){
			return true;
		}
		return false;
	}
	

	/** Determines whether to run user search or not
	 * 
	 * @param firstName
	 * @param lastName
	 * @param emailAddress
	 * @param assignedIdentifier
	 * @param organizationId
	 * @param loginName
	 * @return
	 */
	private boolean isUserSearch(String firstName, String lastName,
			String emailAddress, String assignedIdentifier,
			String organizationId, String loginName) {
		//run user search if nothing is specified
		if(StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName) && StringUtils.isEmpty(emailAddress) && StringUtils.isEmpty(loginName) 
        		&& StringUtils.isEmpty(assignedIdentifier) && StringUtils.isEmpty(organizationId)){
			return true;
		}
		//run user search if either basic or user criteria is specified
		if(!StringUtils.isEmpty(firstName) || !StringUtils.isEmpty(lastName) || !StringUtils.isEmpty(emailAddress) 
        		|| !StringUtils.isEmpty(loginName)){
			return true;
		}
		return false;
	}

	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
        Map<String, Object> refdata = new HashMap<String, Object>();
        Map<String, List<Lov>> configMap = configurationProperty.getMap();
        refdata.put("searchTypeRefData", configMap.get("studySearchType"));
        return refdata;
    }

    public ConfigurationProperty getConfigurationProperty() {
        return configurationProperty;
    }

    public void setConfigurationProperty(ConfigurationProperty configurationProperty) {
        this.configurationProperty = configurationProperty;
    }

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public PersonUserDao getPersonUserDao() {
		return personUserDao;
	}

	public void setPersonUserDao(PersonUserDao personUserDao) {
		this.personUserDao = personUserDao;
	}

	public SearchResearchStaffAjaxFacade getSearchResearchStaffAjaxFacade() {
		return searchResearchStaffAjaxFacade;
	}

	public void setSearchResearchStaffAjaxFacade(
			SearchResearchStaffAjaxFacade searchResearchStaffAjaxFacade) {
		this.searchResearchStaffAjaxFacade = searchResearchStaffAjaxFacade;
	}

	public CSMUserRepository getCsmUserRepository() {
		return csmUserRepository;
	}

	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}

	
}
