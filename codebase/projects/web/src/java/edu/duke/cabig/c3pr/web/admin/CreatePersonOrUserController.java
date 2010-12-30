package edu.duke.cabig.c3pr.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.constants.PersonUserType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.RemotePersonUser;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.domain.repository.PersonUserRepository;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl.C3PRNoSuchUserException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.OrganizationService;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;

/**This class is used to create the Person or a User or both.
 * 
 * @author Vinay Gangoli
 */
public class CreatePersonOrUserController extends SimpleFormController{

    protected PersonUserDao personUserDao;
    private HealthcareSiteDao healthcareSiteDao ;
    private OrganizationService organizationService;
    private UserDao userDao;
    
    public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	private PersonnelService personnelService ;

	public PersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}
	private PersonUserRepository personUserRepository;
	
    private CSMUserRepository csmUserRepository;
    
    public CSMUserRepository getCsmUserRepository() {
		return csmUserRepository;
	}

	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}
	private Configuration configuration;

    private String EDIT_FLOW = "EDIT_FLOW";

    private String SAVE_FLOW = "SAVE_FLOW";
    
    protected String SETUP_FLOW = "SETUP_FLOW";
    private final String TRUE = "true" ;

    protected String FLOW = "FLOW";
    
    private final String CREATE_STAFF = "CREATE_RESEARCH_STAFF" ;
    private final String CREATE_USER = "_createUser" ;

    private Logger log = Logger.getLogger(CreatePersonOrUserController.class);
    
    /**
     * Create a nested object graph that Create Research Staff needs
     * 
     * @param request -  HttpServletRequest
     * @throws ServletException
     */
    
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)  throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(true));
	   binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
	   binder.registerCustomEditor(C3PRUserGroupType.class, new EnumByNameEditor( C3PRUserGroupType.class));
	}
    
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	
    	PersonOrUserWrapper wrapper = new PersonOrUserWrapper();
        PersonUser researchStaff;
        String assignedIdentifier = request.getParameter("assignedIdentifier") ;
        String loginId = request.getParameter("loginId") ;
        
        if (StringUtils.isNotBlank(assignedIdentifier) || StringUtils.isNotBlank(loginId)) {
        	if(StringUtils.isNotBlank(assignedIdentifier) ){
        		wrapper.setCreateAsStaff(true);
        		researchStaff = personUserRepository.getByAssignedIdentifier(assignedIdentifier);
        	} else {
        		wrapper.setCreateAsStaff(false);
        		researchStaff = personUserDao.getByLoginId(loginId);
        	}
            
            personUserRepository.initialize(researchStaff);
            for(HealthcareSite hcSite : researchStaff.getHealthcareSites()){
        		HealthcareSiteRolesHolder object = new HealthcareSiteRolesHolder();
        		object.setHealthcareSite(hcSite);
        		wrapper.addHealthcareSiteRolesHolder(object);
        	}
            gov.nih.nci.security.authorization.domainobjects.User csmUser = personUserRepository.getCSMUser(researchStaff);
            if(csmUser != null){
            	wrapper.setCreateAsUser(true);
            	wrapper.setUserName(csmUser.getLoginName());
            	wrapper.setHasAccessToAllSites(personUserRepository.getHasAccessToAllSites(csmUser));
            	for(HealthcareSiteRolesHolder rolesHolder : wrapper.getHealthcareSiteRolesHolderList()){
            		rolesHolder.setGroups(personUserRepository.getGroups(csmUser, rolesHolder.getHealthcareSite()));
            	}
            } else {
            	wrapper.setCreateAsUser(false);
            }
            request.getSession().setAttribute(FLOW, EDIT_FLOW);
        } else {
            researchStaff = new LocalPersonUser();
            researchStaff.setVersion(Integer.parseInt("1"));
            request.getSession().setAttribute(FLOW, SAVE_FLOW);
            HealthcareSiteRolesHolder rolesHolder = new HealthcareSiteRolesHolder();
    		if(wrapper.getHealthcareSiteRolesHolderList().size() == 0){
    			wrapper.getHealthcareSiteRolesHolderList().add(rolesHolder);
    		}
        }
        wrapper.setPersonUser(researchStaff);
        return wrapper;
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    	PersonOrUserWrapper wrapper = (PersonOrUserWrapper)command;
    	PersonUser researchStaff = wrapper.getPersonUser();
    	
    	Map<String, Object> model = new HashMap<String, Object>();
    	List<C3PRUserGroupType> roles = new ArrayList<C3PRUserGroupType>();
    	List<C3PRUserGroupType> globalRoles = new ArrayList<C3PRUserGroupType>();
    	for(C3PRUserGroupType role : C3PRUserGroupType.values()){
    		if(SecurityUtils.isGlobalRole(role)){
    			globalRoles.add(role);
    		}else{
    			roles.add(role);
    		}
    	}
        model.put("roles", roles);
        model.put("globalRoles", globalRoles);
        model.put("isLoggedInUser", personUserRepository.isLoggedInUser(researchStaff));
        model.put("coppaEnable", configuration.get(Configuration.COPPA_ENABLE));
        return model;
    }
    
    @Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
    	super.onBindAndValidate(request, command, errors);
    	PersonOrUserWrapper wrapper = (PersonOrUserWrapper) command ;
		PersonUser researchStaff = wrapper.getPersonUser();
		List<HealthcareSiteRolesHolder> listAssociation = wrapper.getHealthcareSiteRolesHolderList();
		if(listAssociation.size() == 0){
			errors.reject("organization.not.present.error");
		}
		
		if(wrapper.getHasAccessToAllSites()){
			boolean onlyGlobalRolesSelected = true ;
			for(HealthcareSiteRolesHolder roleHolder : listAssociation){
				if(roleHolder != null){
					for(C3PRUserGroupType group : roleHolder.getGroups()){
						if(!SecurityUtils.isGlobalRole(group)){
							onlyGlobalRolesSelected = false ;
							break ;
						}
					}
					if(!onlyGlobalRolesSelected){
						break ;
					}
				}
			}
			if(onlyGlobalRolesSelected){
				errors.reject("researchstaff.hasAllSiteAccess.noSiteStudySpecificRoleSelected");
			}
		}

		boolean noDuplicateOrg = true ;
		Set<HealthcareSite> hcSites = new HashSet<HealthcareSite> ();
		for(HealthcareSiteRolesHolder roleHolder : listAssociation){
			if(roleHolder != null){
				noDuplicateOrg = hcSites.add(roleHolder.getHealthcareSite());
				if(!noDuplicateOrg){
					break ;
				}
			}
		}
		if(!noDuplicateOrg){
			errors.reject("organization.already.present.error");	
		}
		
    	String actionParam = request.getParameter("_action");
		String flowVar = request.getSession().getAttribute(FLOW).toString();
		
		String createUser = request.getParameter(CREATE_USER);
		if(StringUtils.isNotBlank(createUser)  && StringUtils.equals(createUser, TRUE)){
			String username = wrapper.getUserName();
			if(StringUtils.isNotBlank(username) ){
				try{
					gov.nih.nci.security.authorization.domainobjects.User user = csmUserRepository.getCSMUserByName(username);
					if(user != null){
						errors.reject("duplicate.username.error");
						request.setAttribute("duplicateUser", "true");
						wrapper.setPreExistingUsersAssignedId(getResearchStaffsAssignedIdentifier(user));
					}
				}catch(C3PRNoSuchUserException e){
				}
			}
		}

		if(!StringUtils.equals(actionParam, "saveRemoteRStaff") && StringUtils.equals(flowVar ,EDIT_FLOW)){
			if (!StringUtils.equals(actionParam ,"syncResearchStaff")) {
				PersonUser rStaffFromDB = personUserRepository.getByAssignedIdentifierFromLocal(researchStaff.getAssignedIdentifier());
				if (rStaffFromDB != null) {
					// This check is already being done in the UsernameDuplicateValidator.
					//FIXME : Ramakrishna Gundala - Not sure why we have this if condition here, please verify and put appropriate comments
					return;
				}
			}
			boolean matchingExternalResearchStaffPresent = externalResearchStaffExists(researchStaff);
    		if(matchingExternalResearchStaffPresent){
    			errors.reject("REMOTE_RSTAFF_EXISTS","Research Staff with assigned identifier " +researchStaff.getAssignedIdentifier()+ " exists in external system");
    		}
		}
	}

	/**
	 * Gets the research staff. 
	 * Creates one if the staff does not exist. This is for the suite use case of dynamic provisioning.
	 * NOTE: Throws a runtime exception if unable to provision.
	 * Since dynamic user provisioning failed, we show the error on the ui instead of working around it.
	 * @param userId the user id
	 * @return the research staff
	 */
	private String getResearchStaffsAssignedIdentifier(gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		edu.duke.cabig.c3pr.domain.C3PRUser c3prUser = userDao.getByLoginId(csmUser.getUserId());
		if(c3prUser == null){
			PersonUser researchStaff = populateResearchStaff(csmUser);
			try {
				logger.debug("Attempting to dynamically provision the CSM user with user id: "+ csmUser.getLoginName() +" in C3PR as staff.");
				personUserDao.createResearchStaff(researchStaff);
			} catch(Exception e){
				logger.error("Dynamic provisioning failed. Check user details in csm_user for invalid data.");
				logger.error(e.getMessage());
				throw new RuntimeException();
			}
			return researchStaff.getAssignedIdentifier();
		}
		return ((PersonUser)c3prUser).getAssignedIdentifier();
	}

	/**
	 * Populate research staff.
	 *
	 * @param csmUser the csm user
	 * @return the research staff
	 */
	private PersonUser populateResearchStaff(gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		PersonUser researchStaff = new LocalPersonUser(PersonUserType.USER);
		researchStaff.setFirstName(csmUser.getFirstName());
		researchStaff.setLastName(csmUser.getLastName());
		researchStaff.setLoginId(csmUser.getUserId().toString());
		researchStaff.setEmail(csmUser.getEmailId());
//		researchStaff.setPhone(csmUser.getPhoneNumber());
//		researchStaff.setAssignedIdentifier(UUID.randomUUID().toString());
		return researchStaff;
	}
	
	private boolean externalResearchStaffExists(PersonUser researchStaff) {
		List<PersonUser> remoteResearchStaff = personUserRepository.getRemoteResearchStaff(researchStaff);
		boolean matchingExternalResearchStaffPresent = false;
		for(PersonUser remoteRStaff : remoteResearchStaff){
			if(remoteRStaff.getAssignedIdentifier().equals(researchStaff.getAssignedIdentifier())){
				researchStaff.addExternalResearchStaff(remoteRStaff);
				matchingExternalResearchStaffPresent = true;
			}
		}
		return matchingExternalResearchStaffPresent;
	}

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit (HttpServletRequest
     *      request, HttpServletResponse response, Object command, BindException errors)
     */
    @Override
	protected boolean suppressValidation(HttpServletRequest request, Object command) {
    	String actionParam = request.getParameter("_action");
		return (StringUtils.equals(actionParam, "saveRemoteRStaff") || StringUtils.equals(actionParam, "syncResearchStaff"));
	}
    
	@Override
	 protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		PersonOrUserWrapper wrapper = (PersonOrUserWrapper) command;
        PersonUser researchStaff = wrapper.getPersonUser();
        
        String actionParam = request.getParameter("_action");
		String selectedParam = request.getParameter("_selected");
		String username = wrapper.getUserName();
		String flowVar = request.getSession().getAttribute(FLOW).toString();
		//String createUser = request.getParameter(CREATE_USER);
		
		boolean createAsUser = wrapper.getCreateAsUser();
		boolean createAsStaff = wrapper.getCreateAsStaff();
		
        Map map = errors.getModel();
        String studyflow = request.getParameter("studyflow") ; 
        if(StringUtils.isNotBlank(studyflow)){
        	map.put("studyflow", studyflow);
        }
        
        RemotePersonUser remoteRStaffSelected = null;
        boolean saveExternalResearchStaff = false;

        try {
        	//If saving a remote Research Staff
			if (StringUtils.equals(EDIT_FLOW, flowVar) && StringUtils.equals("saveRemoteRStaff", actionParam)) {
				if(researchStaff.getExternalResearchStaff()!= null && researchStaff.getExternalResearchStaff().size() > 0){
					saveExternalResearchStaff = true;
					remoteRStaffSelected = (RemotePersonUser) researchStaff.getExternalResearchStaff().get(Integer.parseInt(selectedParam));
					if(remoteRStaffSelected.getHealthcareSites().size() > 0){
	        			for(HealthcareSite hcSite : remoteRStaffSelected.getHealthcareSites()){
	    				//	get the corresponding hcs from the dto object and save that organization and then save this staff
	        				HealthcareSite matchingHealthcareSiteFromDb = getHealthcareSiteDao().getByPrimaryIdentifier(hcSite.getPrimaryIdentifier());
	        				if(matchingHealthcareSiteFromDb == null){
	        					organizationService.save(hcSite);
	        				} else{
	    					//	we have the retrieved staff's Org in our db...link up with the same and persist
	        					remoteRStaffSelected.removeHealthcareSite(hcSite);
	        					remoteRStaffSelected.addHealthcareSite(matchingHealthcareSiteFromDb);
	        				}
	        			}
	        			personUserDao.evict(researchStaff);
						personnelService.convertLocalResearchStaffToRemoteResearchStaff((LocalPersonUser)researchStaff, remoteRStaffSelected);
						// add organizations of selected remote research staff  to the remote research staff in Db( which is just converted from local)
						//first load the remote research staff just converted
						RemotePersonUser remoteResearchStaffFromDb = (RemotePersonUser) personUserDao.getByAssignedIdentifierFromLocal
							(remoteRStaffSelected.getAssignedIdentifier());
						
						// add organizations from selected remote research staff that the converted research staff doesn't have
						for(HealthcareSite hcs: remoteRStaffSelected.getHealthcareSites()){
							if(!remoteResearchStaffFromDb.getHealthcareSites().contains(hcs)){
								remoteResearchStaffFromDb.addHealthcareSite(hcs);
							}
						}
		      		} else {
		      			errors.reject("REMOTE_RS_ORG_NULL","There is no Organization associated with the external Research Staff");
		      		}
				}
			} else {
				//For Non-Remote Staff cases
				boolean hasAccessToAllSites = wrapper.getHasAccessToAllSites() ;

				List<HealthcareSiteRolesHolder> listAssociation = wrapper.getHealthcareSiteRolesHolderList();
				Map<HealthcareSite, List<C3PRUserGroupType>> associationMap = new HashMap<HealthcareSite, List<C3PRUserGroupType>>();
				for(HealthcareSiteRolesHolder associationObject : listAssociation){
					if(associationObject != null){
						List<C3PRUserGroupType> groups = associationObject.getGroups();
						if(groups == null) {
							groups = new ArrayList<C3PRUserGroupType>();
						}
						associationMap.put(associationObject.getHealthcareSite(), groups);
						if(!researchStaff.getHealthcareSites().contains(associationObject.getHealthcareSite())){
							researchStaff.addHealthcareSite(associationObject.getHealthcareSite());
						}
					}
				}
				
				if(StringUtils.equals(flowVar, SAVE_FLOW)){
		        	if(createAsUser && createAsStaff){
	        			researchStaff.setPersonUserType(PersonUserType.STAFF_USER);
	        			researchStaff = personUserRepository.createOrModifyResearchStaffWithUserAndAssignRoles(researchStaff, username, associationMap, hasAccessToAllSites);
	        		} else if(createAsUser) {
	        			researchStaff.setPersonUserType(PersonUserType.USER);
	        			researchStaff = personUserRepository.createOrModifyUserWithoutResearchStaffAndAssignRoles(researchStaff, username, associationMap, hasAccessToAllSites);
		        	} else if(createAsStaff) {
		        		//if create as research staff and not as user.
		        		researchStaff.setPersonUserType(PersonUserType.STAFF);
	        			researchStaff = personUserRepository.createOrModifyResearchStaffWithoutUser(researchStaff, associationMap, hasAccessToAllSites);
		        	}
		        } else if (StringUtils.equals(flowVar, SETUP_FLOW)){
		        	// create research staff, csm user and assign org and provide access to all sites
		        	researchStaff.setPersonUserType(PersonUserType.STAFF_USER);
		        	researchStaff = personUserRepository.createSuperUser(researchStaff, username, associationMap);
		        	
		        } else if(StringUtils.equals(flowVar, EDIT_FLOW)){
		        	if(createAsUser && createAsStaff){
		        		// create research staff and csm user and assign roles if provided
		        		researchStaff.setPersonUserType(PersonUserType.STAFF_USER);
	        			personUserRepository.createOrModifyResearchStaffWithUserAndAssignRoles(researchStaff, username, associationMap, hasAccessToAllSites);
		        	} else if(createAsUser) {
		        		//update the user without touching the staff(assigned id needs to be empty).
	        			//no need to set PersonType as it should already be set
	        			personUserRepository.createOrModifyUserWithoutResearchStaffAndAssignRoles(researchStaff, username, associationMap, hasAccessToAllSites);
		        	} else if(createAsStaff) {
		        		//update the staff without touching the user.
		        		//no need to set PersonType as it should already be set
		        		researchStaff = personUserRepository.createOrModifyResearchStaffWithoutUser(researchStaff, associationMap, hasAccessToAllSites);
		        	}
		        }
			}
        } catch (C3PRBaseRuntimeException e) {
	        if (e.getRootCause().getMessage().contains("MailException")) {
	            log.info("Error saving Research staff.Probably failed to send email", e);
	        } else {
	        	errors.reject(e.getMessage());
	        }
      }
	  if(saveExternalResearchStaff){
		  wrapper.setPersonUser(remoteRStaffSelected);
	  } else {
		  wrapper.setPersonUser(researchStaff);  
	  }
	  
	  if (!errors.hasErrors()) {
		  map.put("command", wrapper);
		  ModelAndView mv = new ModelAndView(getSuccessView(), map);
		  return mv;
	  } else {
		  return super.processFormSubmission(request, response, command, errors);
	  }
	  
    }

	@Required
    public void setPersonUserDao(PersonUserDao personUserDao) {
        this.personUserDao = personUserDao;
    }

    @Required
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
    
    @Required
	public void setPersonUserRepository(PersonUserRepository personUserRepository) {
		this.personUserRepository = personUserRepository;
	}

	public PersonUserRepository getPersonUserRepository() {
		return personUserRepository;
	}
	
	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}
	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}