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

import org.apache.jackrabbit.uuid.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.RemotePersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
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

/**
 * @author Ramakrishna
 */
public class CreateResearchStaffController extends SimpleFormController{

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

    private Logger log = Logger.getLogger(CreateResearchStaffController.class);
    
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
    	
    	ResearchStaffWrapper wrapper = new ResearchStaffWrapper();
        PersonUser researchStaff;
        String assignedIdentifier = request.getParameter("assignedIdentifier") ;
        
        if (StringUtils.isNotBlank(assignedIdentifier)) {
            researchStaff = personUserRepository.getByAssignedIdentifier(assignedIdentifier);
            personUserRepository.initialize(researchStaff);
            for(HealthcareSite hcSite : researchStaff.getHealthcareSites()){
        		HealthcareSiteRolesHolder object = new HealthcareSiteRolesHolder();
        		object.setHealthcareSite(hcSite);
        		wrapper.addHealthcareSiteRolesHolder(object);
        	}
            gov.nih.nci.security.authorization.domainobjects.User csmUser = personUserRepository.getCSMUser(researchStaff);
            if(csmUser != null){
            	wrapper.setUserName(csmUser.getLoginName());
            	wrapper.setHasAccessToAllSites(personUserRepository.getHasAccessToAllSites(csmUser));
            	for(HealthcareSiteRolesHolder rolesHolder : wrapper.getHealthcareSiteRolesHolderList()){
            		rolesHolder.setGroups(personUserRepository.getGroups(csmUser, rolesHolder.getHealthcareSite()));
            	}
            }
            
            request.getSession().setAttribute(FLOW, EDIT_FLOW);
        }else {
            researchStaff = new LocalPersonUser();
            researchStaff.setVersion(Integer.parseInt("1"));
            request.getSession().setAttribute(FLOW, SAVE_FLOW);
            HealthcareSiteRolesHolder rolesHolder = new HealthcareSiteRolesHolder();
    		if(wrapper.getHealthcareSiteRolesHolderList().size() == 0){
    			wrapper.getHealthcareSiteRolesHolderList().add(rolesHolder);
    		}
        }
        wrapper.setResearchStaff(researchStaff);
        return wrapper;
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    	ResearchStaffWrapper wrapper = (ResearchStaffWrapper)command;
    	PersonUser researchStaff = wrapper.getResearchStaff();
    	
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
    	ResearchStaffWrapper wrapper = (ResearchStaffWrapper) command ;
		PersonUser researchStaff = wrapper.getResearchStaff();
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
		PersonUser researchStaff = new LocalPersonUser();
		researchStaff.setFirstName(csmUser.getFirstName());
		researchStaff.setLastName(csmUser.getLastName());
		researchStaff.setLoginId(csmUser.getUserId().toString());
		researchStaff.setEmail(csmUser.getEmailId());
//		researchStaff.setPhone(csmUser.getPhoneNumber());
		researchStaff.setAssignedIdentifier(UUID.randomUUID().toString());
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
	  ModelAndView mv = new ModelAndView(getSuccessView(), null);
	  return mv;
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