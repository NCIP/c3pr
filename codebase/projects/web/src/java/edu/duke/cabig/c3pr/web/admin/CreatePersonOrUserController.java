/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
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
import edu.duke.cabig.c3pr.constants.StatusType;
import edu.duke.cabig.c3pr.constants.PersonUserType;
import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.PersonUserDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudyPersonnelDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.dao.UserDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalPersonUser;
import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.domain.RemotePersonUser;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyPersonnel;
import edu.duke.cabig.c3pr.domain.StudyPersonnelRole;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.domain.repository.PersonUserRepository;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl.C3PRNoSuchUserException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.OrganizationService;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.RoleBasedHealthcareSitesAndStudiesDTO;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import gov.nih.nci.cabig.ctms.suite.authorization.ProvisioningSession;

/**This class is used to create the Person or a User or both.
 * 
 * @author Vinay Gangoli
 */
public class CreatePersonOrUserController extends SimpleFormController{

    protected PersonUserDao personUserDao;
    private HealthcareSiteDao healthcareSiteDao ;
    private OrganizationService organizationService;
    private UserDao userDao;
    private StudyDao studyDao;
    private StudySiteDao studySiteDao;
    private StudyPersonnelDao studyPersonnelDao;
    
    
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
	    binder.registerCustomEditor(C3PRUserGroupType.class, new EnumByNameEditor(C3PRUserGroupType.class));
	}
    
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	
    	PersonOrUserWrapper wrapper = new PersonOrUserWrapper();
        PersonUser personUser;
        String assignedIdentifier = request.getParameter("assignedIdentifier") ;
        String loginId = request.getParameter("loginId") ;
        
        if (StringUtils.isNotBlank(assignedIdentifier) || StringUtils.isNotBlank(loginId)) {
        	if(StringUtils.isNotBlank(assignedIdentifier) ){
        		log.debug("loading personnel by the assigned Id");
        		wrapper.setCreateAsStaff(true);
        		personUser = personUserRepository.getByAssignedIdentifier(assignedIdentifier);
        	} else {
        		log.debug("loading personnel by login Id");
        		wrapper.setCreateAsStaff(false);
        		personUser = personUserDao.getByLoginId(loginId);
        	}
            
            personUserRepository.initialize(personUser);
            gov.nih.nci.security.authorization.domainobjects.User csmUser = personUserRepository.getCSMUser(personUser);
            if(csmUser != null){
            	wrapper.setCreateAsUser(true);
            	wrapper.setUserName(csmUser.getLoginName());
            	//setting code in wrapper for display purposes, but using name everywhere otherwise
            	if(SecurityUtils.isUserDeactivated(csmUser.getEndDate())){
            		wrapper.setUserStatus(StatusType.IN.getCode());
            	} else {
            		wrapper.setUserStatus(StatusType.AC.getCode());
            	}
            	
            	List<C3PRUserGroupType> preAssignedRolesList = personUserRepository.getGroupsForUser(csmUser);
            	//adding all the roles, assigned or not, for UI convenience.
            	RoleBasedHealthcareSitesAndStudiesDTO rolesHolder;
            	boolean hasAllSiteAccess = false;
            	boolean hasAllStudyAccess = false;
        		
            	log.debug("loading roles information for personnel");
            	ProvisioningSession provisioningSession = personUserDao.getProvisioningSession(csmUser);
            	for(C3PRUserGroupType group: C3PRUserGroupType.values()){
            		//adding the non assigned roles for UI convenience.
            		rolesHolder = new RoleBasedHealthcareSitesAndStudiesDTO(group);
            		
            		if(!preAssignedRolesList.contains(group)){
    	             	rolesHolder.setChecked(false);
    	             	//poMgr is always all-site in C3PR.
    	             	if(group.equals(C3PRUserGroupType.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER)){
                     		rolesHolder.setHasAllSiteAccess(true);
                     	}
            		} else {
            		//adding the non assigned roles for UI convenience.
            			log.debug("loading all-site and all-study info for :"+group.getDisplayName());
            			hasAllSiteAccess = personUserRepository.getHasAccessToAllSites(provisioningSession, group);
            			hasAllStudyAccess = personUserRepository.getHasAccessToAllStudies(provisioningSession, group);
            			
    	             	rolesHolder.setChecked(true);
    	             	rolesHolder.setHasAllSiteAccess(hasAllSiteAccess);
    	             	//populate csm organizations into the wrapper if user doesn't have all site access
    	             	if(!hasAllSiteAccess && SecurityUtils.getSiteScopedRoles().contains(group.getCode())){
                			log.debug("loading sites info for :"+group.getDisplayName());
    	             		List<String> hcsIds = personUserRepository.getOrganizationIdsForUser(provisioningSession, group);
                			
    	             		log.debug("No. of sites: "+hcsIds.size());
    	             		HealthcareSite healthcareSite = null;
    	             		for(String hcsId: hcsIds){
    	             			healthcareSite = healthcareSiteDao.getByPrimaryIdentifierFromLocal(hcsId);
    	             			if(healthcareSite != null){
    	             				rolesHolder.getSites().add("(" + hcsId + ") " + healthcareSite.getName());
    	             			}
    	             		}
    	             	}
    	             	
    	             	rolesHolder.setHasAllStudyAccess(hasAllStudyAccess);
    	             	//populate studies into the wrapper if user doesn't have all study access
    	             	if(!hasAllStudyAccess  && SecurityUtils.getStudyScopedRoles().contains(group.getCode())){
                			log.debug("loading studies info for :"+group.getDisplayName());
    	             		List<String> studyIds = personUserRepository.getStudyIdsForUser(provisioningSession, group);
    	             		
                			log.debug("No. of studies: "+studyIds.size());
    	             		Study study = null;
    	             		for(String studyId: studyIds){
    	             			study = studyDao.searchByCoordinatingCenterAssignedIdentifier(studyId);
    	             			if(study != null){
                     				rolesHolder.getStudies().add("(" + studyId + ") " + study.getShortTitleText());
    	             			}
    	             		}
    	             	}
            		}
            		wrapper.addHealthcareSiteRolesHolder(rolesHolder);
            	}
            	log.debug("FINISHED loading roles information for personnel");
            } else {
            	wrapper.setCreateAsUser(false);
                RoleBasedHealthcareSitesAndStudiesDTO rolesHolder;
            	for(C3PRUserGroupType group: C3PRUserGroupType.values()){
        			rolesHolder = new RoleBasedHealthcareSitesAndStudiesDTO(group);
        			rolesHolder.setGroup(group);
                 	rolesHolder.setChecked(false);
                 	//poMgr is always all-site in C3PR.
                 	if(group.equals(C3PRUserGroupType.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER)){
                 		rolesHolder.setHasAllSiteAccess(true);
                 	}
                 	wrapper.addHealthcareSiteRolesHolder(rolesHolder);
            	}
            }
            //we don't set the staff organizations in the wrapper as they cannot be deleted and hence are only displayed as labels.
            request.getSession().setAttribute(FLOW, EDIT_FLOW);
        } else {
            personUser = new LocalPersonUser();
            personUser.setVersion(Integer.parseInt("1"));
            request.getSession().setAttribute(FLOW, SAVE_FLOW);
            RoleBasedHealthcareSitesAndStudiesDTO rolesHolder;
            for(C3PRUserGroupType group: C3PRUserGroupType.values()){
    			rolesHolder = new RoleBasedHealthcareSitesAndStudiesDTO(group);
    			rolesHolder.setGroup(group);
             	rolesHolder.setChecked(false);
             	//poMgr is always all-site in C3PR.
             	if(group.equals(C3PRUserGroupType.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER)){
             		rolesHolder.setHasAllSiteAccess(true);
             	}
             	wrapper.addHealthcareSiteRolesHolder(rolesHolder);
        	}
        }
        if(SecurityUtils.getRoleTypes().contains(RoleTypes.USER_ADMINISTRATOR) &&
        		!SecurityUtils.getRoleTypes().contains(RoleTypes.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER)){
        	wrapper.setCreateAsStaff(false);
        	wrapper.setCreateAsUser(true);

        }
        if(SecurityUtils.getRoleTypes().contains(RoleTypes.PERSON_AND_ORGANIZATION_INFORMATION_MANAGER) &&
        		!SecurityUtils.getRoleTypes().contains(RoleTypes.USER_ADMINISTRATOR)){
        	wrapper.setCreateAsUser(false);
        	wrapper.setCreateAsStaff(true);

        }
        wrapper.setPersonUser(personUser);
        return wrapper;
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    	PersonOrUserWrapper wrapper = (PersonOrUserWrapper)command;
    	PersonUser personUser = wrapper.getPersonUser();
    	
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
        model.put("isLoggedInUser", personUserRepository.isLoggedInUser(personUser));
        model.put("coppaEnable", configuration.get(Configuration.COPPA_ENABLE));
        return model;
    }
    
    @Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
    	super.onBindAndValidate(request, command, errors);
    	PersonOrUserWrapper wrapper = (PersonOrUserWrapper) command ;
		PersonUser personUser = wrapper.getPersonUser();
		
		if(!StringUtils.isBlank(personUser.getAssignedIdentifier()) && 
				(wrapper.getStaffOrganizationPrimaryIdentifiers().isEmpty() && personUser.getHealthcareSites().isEmpty())){
			errors.reject("organization.not.present.error");
		}
		
		HealthcareSite healthcareSite;
		boolean noDuplicateOrg = true ;
		for(String primaryIdentifier : wrapper.getStaffOrganizationPrimaryIdentifiers()){
			//check if newly added sites have already been assigned to the personUser
 			if(!StringUtils.isBlank(primaryIdentifier)){
 				for(HealthcareSite hcs: wrapper.getPersonUser().getHealthcareSites()){
 					if(hcs.getPrimaryIdentifier().equalsIgnoreCase(primaryIdentifier)){
 						primaryIdentifier = null;
 						break;
 					}
 				}
 				//if pre-existing, its a duplicate.
 				if(primaryIdentifier == null){
 					noDuplicateOrg = false;
 				} else {
 					//else add non dupe to personUser
 					healthcareSite = healthcareSiteDao.getByPrimaryIdentifierFromLocal(primaryIdentifier);
					wrapper.getPersonUser().getHealthcareSites().add(healthcareSite);
 				}
 			}
		}

		//for dupe existing cases show rror message and clear the organizations from the StaffOrganizationPrimaryIdentifiers
		if(!noDuplicateOrg){
			wrapper.getStaffOrganizationPrimaryIdentifiers().clear();
			wrapper.setSelectedOrganizationForDisplay("");
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
						wrapper.setPreExistingUsersAssignedId(getPersonUsersAssignedIdentifier(user));
					}
				}catch(C3PRNoSuchUserException e){
					log.error(e.getMessage() + e.getStackTrace());
				}
			}
		}

		if(!StringUtils.equals(actionParam, "saveRemoteRStaff") && StringUtils.equals(flowVar ,EDIT_FLOW)){
			if (!StringUtils.equals(actionParam ,"syncResearchStaff")) {
				PersonUser rStaffFromDB = personUserRepository.getByAssignedIdentifierFromLocal(personUser.getAssignedIdentifier());
				if (rStaffFromDB != null) {
					// This check is already being done in the UsernameDuplicateValidator.
					//FIXME : Ramakrishna Gundala - Not sure why we have this if condition here, please verify and put appropriate comments
					return;
				}
			} else { //this will only run for sync flow.
				// clearing the current list so that the same external person is not added again.
				personUser.getExternalResearchStaff().clear();
				boolean matchingExternalResearchStaffPresent = externalResearchStaffExists(personUser);
	    		if(matchingExternalResearchStaffPresent){
	    			errors.reject("REMOTE_RSTAFF_EXISTS","Research Staff with assigned identifier " +personUser.getAssignedIdentifier()+ " exists in external system");
	    		} else{
	    			errors.reject("REMOTE_RSTAFF_EXISTS","Research Staff with assigned identifier " +personUser.getAssignedIdentifier()+ " does not exist in external system");
	    		}
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
	private String getPersonUsersAssignedIdentifier(gov.nih.nci.security.authorization.domainobjects.User csmUser) {
		edu.duke.cabig.c3pr.domain.C3PRUser c3prUser = userDao.getByLoginId(csmUser.getUserId());
		if(c3prUser == null){
			PersonUser personUser = populateResearchStaff(csmUser);
			try {
				logger.debug("Attempting to dynamically provision the CSM user with user id: "+ csmUser.getLoginName() +" in C3PR as staff.");
				personUserDao.createResearchStaff(personUser);
			} catch(Exception e){
				logger.error("Dynamic provisioning failed. Check user details in csm_user for invalid data.");
				logger.error(e.getMessage());
				throw new RuntimeException();
			}
			return personUser.getAssignedIdentifier();
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
		PersonUser personUser = new LocalPersonUser(PersonUserType.USER);
		personUser.setFirstName(csmUser.getFirstName());
		personUser.setLastName(csmUser.getLastName());
		personUser.setLoginId(csmUser.getUserId().toString());
		personUser.setEmail(csmUser.getEmailId());
//		researchStaff.setPhone(csmUser.getPhoneNumber());
		return personUser;
	}
	
	private boolean externalResearchStaffExists(PersonUser personUser) {
		List<PersonUser> remotePersonUser = personUserRepository.getRemoteResearchStaff(personUser);
		boolean matchingExternalResearchStaffPresent = false;
		for(PersonUser remoteRStaff : remotePersonUser){
			if(remoteRStaff.getAssignedIdentifier().equals(personUser.getAssignedIdentifier())){
				personUser.addExternalResearchStaff(remoteRStaff);
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
        PersonUser personUser = wrapper.getPersonUser();
        
        String actionParam = request.getParameter("_action");
		String selectedParam = request.getParameter("_selected");
		String _toggleStatus = request.getParameter("_toggleStatus");
		String username = wrapper.getUserName();
		String flowVar = request.getSession().getAttribute(FLOW).toString();
		
		boolean createAsUser = wrapper.getCreateAsUser();
		boolean createAsStaff = wrapper.getCreateAsStaff();
		
		//update the User status if toggleUserStatus has been clicked.
		if(_toggleStatus.equalsIgnoreCase("true")){
			if(wrapper.getUserStatus().equals(StatusType.AC.getCode())){
				personUser.setUserStatus(StatusType.IN.getName());
			} else {
				personUser.setUserStatus(StatusType.AC.getName());
			}
		} else {
			if(personUser.getUserStatus() == null && StringUtils.equals(flowVar, SAVE_FLOW)){
				//set the state as Active as it must be the SAVE_FLOW
				personUser.setUserStatus(StatusType.AC.getName());
			} else if(personUser.getUserStatus() == null){
				logger.error("User status was found to be null in non Create flow. Control shouldnt be here. ");
			}
		}
		
        Map map = errors.getModel();
        String studyflow = request.getParameter("studyflow") ; 
        if(StringUtils.isNotBlank(studyflow)){
        	map.put("studyflow", studyflow);
        }
        
        RemotePersonUser remotPersonUserSelected = null;
        boolean saveExternalPersonUser = false;

        try {
        	//If saving a remote Research Staff
			if (StringUtils.equals(EDIT_FLOW, flowVar) && StringUtils.equals("saveRemoteRStaff", actionParam)) {
				if(personUser.getExternalResearchStaff()!= null && personUser.getExternalResearchStaff().size() > 0){
					saveExternalPersonUser = true;
					remotPersonUserSelected = (RemotePersonUser) personUser.getExternalResearchStaff().get(Integer.parseInt(selectedParam));
					if(remotPersonUserSelected.getHealthcareSites().size() > 0){
	        			for(HealthcareSite hcSite : remotPersonUserSelected.getHealthcareSites()){
	    				//	get the corresponding hcs from the dto object and save that organization and then save this staff
	        				HealthcareSite matchingHealthcareSiteFromDb = getHealthcareSiteDao().getByPrimaryIdentifier(hcSite.getPrimaryIdentifier());
	        				if(matchingHealthcareSiteFromDb == null){
	        					organizationService.save(hcSite);
	        				} else{
	    					//	we have the retrieved staff's Org in our db...link up with the same and persist
	        					remotPersonUserSelected.removeHealthcareSite(hcSite);
	        					remotPersonUserSelected.addHealthcareSite(matchingHealthcareSiteFromDb);
	        				}
	        			}
	        			personUserDao.evict(personUser);
						personnelService.convertLocalPersonUserToRemotePersonUser((LocalPersonUser)personUser, remotPersonUserSelected);
						// add organizations of selected remote personUser  to the remote personUser in Db( which is just converted from local)
						//first load the remote personUser just converted
						RemotePersonUser remotePersonUserFromDb = (RemotePersonUser) personUserDao.getByAssignedIdentifierFromLocal
							(remotPersonUserSelected.getAssignedIdentifier());
						
						// add organizations from selected remote personUser that the converted personUser doesn't have
						for(HealthcareSite hcs: remotPersonUserSelected.getHealthcareSites()){
							if(!remotePersonUserFromDb.getHealthcareSites().contains(hcs)){
								remotePersonUserFromDb.addHealthcareSite(hcs);
							}
						}
		      		} else {
		      			errors.reject("REMOTE_RS_ORG_NULL","There is no Organization associated with the external Research Staff");
		      		}
				}
			} else {
				//For Non-Remote Staff cases
				List<RoleBasedHealthcareSitesAndStudiesDTO> origListAssociation = wrapper.getHealthcareSiteRolesHolderList();
				//remove the DTO's that are unchecked and create a new list of checked roles only
				RoleBasedHealthcareSitesAndStudiesDTO[] backupArray = (RoleBasedHealthcareSitesAndStudiesDTO[])origListAssociation.toArray(new RoleBasedHealthcareSitesAndStudiesDTO[origListAssociation.size()]);
				for(int i=0; i < origListAssociation.size(); i++){
					if(!origListAssociation.get(i).getChecked()){
						backupArray[i] = null;
					}
				}
				
				List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation = new ArrayList<RoleBasedHealthcareSitesAndStudiesDTO>();
				for(int j=0; j < backupArray.length; j++){
					if(backupArray[j] != null){
						listAssociation.add((RoleBasedHealthcareSitesAndStudiesDTO)backupArray[j]);
					}
				}
				
				if(StringUtils.equals(flowVar, SAVE_FLOW)){
		        	if(createAsUser && createAsStaff){
	        			personUser.setPersonUserType(PersonUserType.STAFF_USER);
	        			personUser = personUserRepository.createOrModifyResearchStaffWithUserAndAssignRoles(personUser, username, listAssociation);
	        		} else if(createAsUser) {
	        			personUser.setPersonUserType(PersonUserType.USER);
	        			personUser = personUserRepository.createOrModifyUserWithoutResearchStaffAndAssignRoles(personUser, username, listAssociation);
		        	} else if(createAsStaff) {
		        		//if create as research staff and not as user.
		        		personUser.setPersonUserType(PersonUserType.STAFF);
	        			personUser = personUserRepository.createOrModifyResearchStaffWithoutUser(personUser, listAssociation);
		        	}
		        	createAndSaveStudyPersonnel(personUser, listAssociation);
		        } else if (StringUtils.equals(flowVar, SETUP_FLOW)){
		        	// create research staff, csm user and assign org and provide access to all sites
		        	listAssociation = buildSuperUserDTO();
		        	personUser.setPersonUserType(PersonUserType.STAFF_USER);
		        	personUser = personUserRepository.createSuperUser(personUser, username, listAssociation);
		        	
		        } else if(StringUtils.equals(flowVar, EDIT_FLOW)){
		        	if(createAsUser && createAsStaff){
		        		//create research staff and csm user and assign roles if provided
		        		personUser.setPersonUserType(PersonUserType.STAFF_USER);
	        			personUserRepository.createOrModifyResearchStaffWithUserAndAssignRoles(personUser, username, listAssociation);
		        	} else if(createAsUser) {
		        		//update the user without touching the staff(assigned id needs to be empty).
	        			//no need to set PersonType as it should already be set
	        			personUserRepository.createOrModifyUserWithoutResearchStaffAndAssignRoles(personUser, username, listAssociation);
		        	} else if(createAsStaff) {
		        		//update the staff without touching the user.
		        		//no need to set PersonType as it should already be set
		        		personUser = personUserRepository.createOrModifyResearchStaffWithoutUser(personUser, listAssociation);
		        	}
		        	createAndSaveStudyPersonnel(personUser, listAssociation);
		        }
			}
        } catch (C3PRBaseRuntimeException e) {
	        if (e.getRootCause().getMessage().contains("MailException")) {
	            log.info("Error saving Research staff. Probably failed to send email", e);
	        } else {
	        	errors.reject(e.getMessage());
	        }
      }
	  if(saveExternalPersonUser){
		  wrapper.setPersonUser(remotPersonUserSelected);
		  if(remotPersonUserSelected.getUserStatus().equalsIgnoreCase(StatusType.AC.getName())){
			  wrapper.setUserStatus(StatusType.AC.getCode());
		  } else {
			  wrapper.setUserStatus(StatusType.IN.getCode());
		  }
	  } else {
		  wrapper.setPersonUser(personUser);  
		  if(personUser.getUserStatus().equalsIgnoreCase(StatusType.AC.getName())){
			  wrapper.setUserStatus(StatusType.AC.getCode());
		  } else {
			  wrapper.setUserStatus(StatusType.IN.getCode());
		  }
	  }

	  if (!errors.hasErrors()) {
		  map.put("command", wrapper);
		  ModelAndView mv = new ModelAndView(getSuccessView(), map);
		  return mv;
	  } else {
		  return super.processFormSubmission(request, response, command, errors);
	  }
	  
    }

	/**
	 * Creates and saves study personnel along with study organization for study scoped roles.
	 *
	 * @param personUser the person user
	 * @param listAssociation the list association
	 */
	private void createAndSaveStudyPersonnel(PersonUser personUser,
			List<RoleBasedHealthcareSitesAndStudiesDTO> listAssociation) {
		
		StudySite studySite;
		List<StudySite> studySiteList;
		StudyPersonnel sPersonnel;
		List<StudyPersonnel> sPersonnelList;
		
		List<StudyPersonnel> existingStudyPersonnel = studyPersonnelDao.getAllForPersonUserId(personUser.getId());
		String hcsPrimaryIdentifier;
		String studyCCAI;
		List<Integer> studyPersonnelIdList = new ArrayList<Integer>();
		List<Integer> studyOrganizationIdList = new ArrayList<Integer>();
		//deleting the ones that were removed from the screen
		for(StudyPersonnel studyPersonnel: existingStudyPersonnel){
			hcsPrimaryIdentifier = studyPersonnel.getStudyOrganization().getHealthcareSite().getPrimaryIdentifier();
			studyCCAI = studyPersonnel.getStudyOrganization().getStudy().getCoordinatingCenterAssignedIdentifier().getValue();
			for(RoleBasedHealthcareSitesAndStudiesDTO dto : listAssociation){
				if(dto.getChecked() && SecurityUtils.getStudyScopedRoles().contains(dto.getGroup().getCode())){
					for(StudyPersonnelRole spr: studyPersonnel.getStudyPersonnelRolesInternal()){
						if(spr.getRole().equals(dto.getGroup().getCode())){
							if( (!dto.getHasAllSiteAccess() && !dto.getSites().contains(hcsPrimaryIdentifier) ) ||
								(!dto.getHasAllStudyAccess() && !dto.getStudies().contains(studyCCAI))){
								//build a spId and soId list of studyPersonnel to be deleted,
								studyPersonnelIdList.add(studyPersonnel.getId());
								studyOrganizationIdList.add(studyPersonnel.getStudyOrganization().getId());
							}
						}
					}
				}
			}
		}
		
		//delete the sp from the generated idList
		for(int i = 0; i < studyOrganizationIdList.size(); i++){
			sPersonnel = studyPersonnelDao.getById(studyPersonnelIdList.get(i));
			studySite = studySiteDao.getById(studyOrganizationIdList.get(i));
			studySite.getStudyPersonnel().remove(sPersonnel);
			studySiteDao.save(studySite);
		}
		
		//adding the new ones
		for(RoleBasedHealthcareSitesAndStudiesDTO dto : listAssociation){
			//only process the user's study scoped roles
			if(dto.getChecked() && SecurityUtils.getStudyScopedRoles().contains(dto.getGroup().getCode())){
				for(String primaryIdentifier:dto.getSites()){
					for(String studyPrimaryId: dto.getStudies()){
						studySiteList = studySiteDao.getBySitePrimaryIdentifierAndStudyCoordinatingCenterIdentifier(studyPrimaryId, primaryIdentifier);
						sPersonnelList = studyPersonnelDao.getByExample(primaryIdentifier, studyPrimaryId, personUser.getId(), dto.getGroup().getCode());
						//only process if the studySite exists and the studyPersonnel with the specified role doesn't. 
						if(studySiteList.size() > 0 && (sPersonnelList == null || sPersonnelList.size() == 0)){
							studySite = studySiteList.get(0);
							sPersonnel = new StudyPersonnel();
							
							sPersonnel.setPersonUser(personUser);
	                        sPersonnel.setStatusCode("Active");
	                        sPersonnel.setStudyOrganization(studySite);
	                        sPersonnel.getStudyPersonnelRoles().add(new StudyPersonnelRole(dto.getGroup().getCode()));
	                        studySite.getStudyPersonnel().add(sPersonnel);
	                        
	                        log.debug("Saving StudyPersonnel with studyPrimaryId:"+studyPrimaryId+" and site:"+primaryIdentifier+" for role:"+dto.getGroup().getCode());
	                        studySiteDao.save(studySite);
						}
					}
				}
			}
		}
		
	}

	/**
	 * Builds the super user dto. Used for ths setup flow.
	 * Adds all roles with all study and all site access.
	 *
	 * @return the list
	 */
	private List<RoleBasedHealthcareSitesAndStudiesDTO> buildSuperUserDTO() {
		List<RoleBasedHealthcareSitesAndStudiesDTO> dtoList = new ArrayList<RoleBasedHealthcareSitesAndStudiesDTO>();
		RoleBasedHealthcareSitesAndStudiesDTO rolesHolder;
		for(C3PRUserGroupType group: C3PRUserGroupType.values()){
			rolesHolder = new RoleBasedHealthcareSitesAndStudiesDTO(group);
			rolesHolder.setGroup(group);
         	rolesHolder.setChecked(true);
         	if(group.getIsSiteScoped()){
         		rolesHolder.setHasAllSiteAccess(true);
         	}
         	if(group.getIsStudyScoped()){
         		rolesHolder.setHasAllStudyAccess(true);
         	}
         	dtoList.add(rolesHolder);
    	}
		return dtoList;
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

	public StudyDao getStudyDao() {
		return studyDao;
	}

	public void setStudyDao(StudyDao studyDao) {
		this.studyDao = studyDao;
	}

	public StudySiteDao getStudySiteDao() {
		return studySiteDao;
	}

	public void setStudySiteDao(StudySiteDao studySiteDao) {
		this.studySiteDao = studySiteDao;
	}

	public StudyPersonnelDao getStudyPersonnelDao() {
		return studyPersonnelDao;
	}

	public void setStudyPersonnelDao(StudyPersonnelDao studyPersonnelDao) {
		this.studyPersonnelDao = studyPersonnelDao;
	}
	
}
