package edu.duke.cabig.c3pr.web.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.domain.repository.ResearchStaffRepository;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl.C3PRNoSuchUserException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
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

    protected ResearchStaffDao researchStaffDao;
    private HealthcareSiteDao healthcareSiteDao ;
    
    private PersonnelService personnelService ;

	public PersonnelService getPersonnelService() {
		return personnelService;
	}

	public void setPersonnelService(PersonnelService personnelService) {
		this.personnelService = personnelService;
	}
	private ResearchStaffRepository researchStaffRepository;
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
        ResearchStaff researchStaff;
        String assignedIdentifier = request.getParameter("assignedIdentifier") ;
        
        if (StringUtils.isNotBlank(assignedIdentifier)) {
            researchStaff = researchStaffRepository.getByAssignedIdentifier(assignedIdentifier);
            researchStaffRepository.initialize(researchStaff);
            gov.nih.nci.security.authorization.domainobjects.User csmUser = researchStaffRepository.getCSMUser(researchStaff);
            if(csmUser != null){
            	wrapper.setUserName(csmUser.getLoginName());
            	wrapper.setHasAccessToAllSites(researchStaffRepository.getHasAccessToAllSites(csmUser));
            	for(HealthcareSite hcSite : researchStaff.getHealthcareSites()){
            		HealthcareSiteRolesHolder object = new HealthcareSiteRolesHolder();
            		object.setHealthcareSite(hcSite);
            		object.setGroups(researchStaffRepository.getGroups(csmUser, hcSite));
            		wrapper.addHealthcareSiteRolesHolder(object);
            	}
            }
            request.getSession().setAttribute(FLOW, EDIT_FLOW);
        }else {
            researchStaff = new LocalResearchStaff();
            researchStaff.setVersion(Integer.parseInt("1"));
            request.getSession().setAttribute(FLOW, SAVE_FLOW);
        }
        wrapper.setResearchStaff(researchStaff);
        return wrapper;
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    	ResearchStaffWrapper wrapper = (ResearchStaffWrapper)command;
    	ResearchStaff researchStaff = wrapper.getResearchStaff();
    	
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
        model.put("isLoggedInUser", researchStaffRepository.isLoggedInUser(researchStaff));
        model.put("coppaEnable", configuration.get(Configuration.COPPA_ENABLE));
        return model;
    }
    
    @Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
    	super.onBindAndValidate(request, command, errors);
    	ResearchStaffWrapper wrapper = (ResearchStaffWrapper) command ;
		ResearchStaff researchStaff = wrapper.getResearchStaff();
		List<HealthcareSiteRolesHolder> listAssociation = wrapper.getHealthcareSiteRolesHolderList();
		if(listAssociation.size() == 0){
			errors.reject("organization.not.present.error");
		}
		
    	String actionParam = request.getParameter("_action");
		String flowVar = request.getSession().getAttribute(FLOW).toString();
		
		String createUser = request.getParameter(CREATE_USER);
		if(StringUtils.isNotBlank(createUser)  && StringUtils.equals(createUser, TRUE)){
			String username = wrapper.getUserName();
			if(StringUtils.isNotBlank(username) ){
				try{
					edu.duke.cabig.c3pr.domain.User user = csmUserRepository.getUserByName(username);
					if(user != null){
						errors.reject("duplicate.username.error");
					}
				}catch(C3PRNoSuchUserException e){
				}
			}
		}

		if(!StringUtils.equals(actionParam, "saveRemoteRStaff") && StringUtils.equals(flowVar ,EDIT_FLOW)){
			if (!StringUtils.equals(actionParam ,"syncResearchStaff")) {
				ResearchStaff rStaffFromDB = researchStaffRepository.getByAssignedIdentifierFromLocal(researchStaff.getAssignedIdentifier());
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

	private boolean externalResearchStaffExists(ResearchStaff researchStaff) {
		List<ResearchStaff> remoteResearchStaff = researchStaffRepository.getRemoteResearchStaff(researchStaff);
		boolean matchingExternalResearchStaffPresent = false;
		for(ResearchStaff remoteRStaff : remoteResearchStaff){
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
        ResearchStaffWrapper wrapper = (ResearchStaffWrapper) command;
        ResearchStaff researchStaff = wrapper.getResearchStaff() ;
        
        String actionParam = request.getParameter("_action");
		String selectedParam = request.getParameter("_selected");
		String username = wrapper.getUserName();
		String flowVar = request.getSession().getAttribute(FLOW).toString();
		String createUser = request.getParameter(CREATE_USER);
		
        Map map = errors.getModel();

        String studyflow = request.getParameter("studyflow") ; 
        if(StringUtils.isNotBlank(studyflow)){
        	map.put("studyflow", studyflow);
        }
        
        RemoteResearchStaff remoteRStaffSelected = null;
        boolean saveExternalResearchStaff = false;

        try {
			if (!StringUtils.equals(EDIT_FLOW, flowVar) && StringUtils.equals("saveRemoteRStaff", actionParam)) {
        		saveExternalResearchStaff = true;
        		remoteRStaffSelected = (RemoteResearchStaff)researchStaff.getExternalResearchStaff().get(Integer.parseInt(selectedParam));
        		if(remoteRStaffSelected.getHealthcareSites().size() > 0){
        			for(HealthcareSite hcSite : remoteRStaffSelected.getHealthcareSites()){
    				//	get the corresponding hcs from the dto object and save that organization and then save this staff
        				HealthcareSite matchingHealthcareSiteFromDb = getHealthcareSiteDao().getByPrimaryIdentifier(hcSite.getPrimaryIdentifier());
        				if(matchingHealthcareSiteFromDb == null){
        					getHealthcareSiteDao().save(hcSite);
        				} else{
    					//	we have the retrieved staff's Org in our db...link up with the same and persist
        					remoteRStaffSelected.removeHealthcareSite(hcSite);
        					remoteRStaffSelected.addHealthcareSite(matchingHealthcareSiteFromDb);
        				}
        			}
        			//TODO create RS and assign organizations to it
        			researchStaffRepository.createResearchStaff(remoteRStaffSelected);
	      		}else{
	      			errors.reject("REMOTE_RS_ORG_NULL","There is no Organization associated with the external Research Staff");
	      		}
          	
        	}else if(StringUtils.equals(EDIT_FLOW, flowVar) && StringUtils.equals("saveRemoteRStaff", actionParam)) {
        		researchStaffDao.evict(researchStaff);
				if(researchStaff.getExternalResearchStaff()!= null && researchStaff.getExternalResearchStaff().size() > 0){
					saveExternalResearchStaff = true;
					remoteRStaffSelected = (RemoteResearchStaff) researchStaff.getExternalResearchStaff().get(Integer.parseInt(selectedParam));
					personnelService.convertLocalResearchStaffToRemoteResearchStaff((LocalResearchStaff)researchStaff, remoteRStaffSelected);
				}
			}  else {
				boolean hasAccessToAllSites = wrapper.getHasAccessToAllSites() ;

				List<HealthcareSiteRolesHolder> listAssociation = wrapper.getHealthcareSiteRolesHolderList();
				Map<HealthcareSite, List<C3PRUserGroupType>> associationMap = new HashMap<HealthcareSite, List<C3PRUserGroupType>>();
				for(HealthcareSiteRolesHolder associationObject : listAssociation){
					if(associationObject != null){
					associationMap.put(associationObject.getHealthcareSite(), associationObject.getGroups());
						if(!researchStaff.getHealthcareSites().contains(associationObject.getHealthcareSite())){
							researchStaff.addHealthcareSite(associationObject.getHealthcareSite());
						}
					}
				}
				if(StringUtils.equals(flowVar, SAVE_FLOW)){
		        	if(StringUtils.isNotBlank(createUser) && StringUtils.equals(createUser, TRUE)){
		        		if(associationMap.isEmpty()){
		        			researchStaff = researchStaffRepository.createResearchStaffWithCSMUser(researchStaff, username, hasAccessToAllSites);
		        		}else{
		        			researchStaff = researchStaffRepository.createResearchStaffWithCSMUserAndAssignRoles(researchStaff, username, associationMap, hasAccessToAllSites);
		        		}
		        		// create research staff and csm user and assign roles if provided
		        	}else{
		        		if(associationMap.isEmpty()){
		        			researchStaff = researchStaffRepository.createResearchStaff(researchStaff);
		        		}else{
		        			researchStaff = researchStaffRepository.createResearchStaff(researchStaff, associationMap);
		        		}
		        	}
		        }else if (StringUtils.equals(flowVar, SETUP_FLOW)){
		        	// create research staff, csm user and assign org and provide access to all sites
		        	researchStaff = researchStaffRepository.createSuperUser(researchStaff, username, associationMap);
		        }else if(StringUtils.equals(flowVar, EDIT_FLOW)){
		        	if(StringUtils.isNotBlank(createUser)  && StringUtils.equals(createUser, TRUE)){
		        		if(associationMap.isEmpty()){
		        			researchStaff = researchStaffRepository.createCSMUser(researchStaff, username, hasAccessToAllSites);
		        		}else{
		        			researchStaff = researchStaffRepository.createCSMUserAndAssignRoles(researchStaff, username, associationMap, hasAccessToAllSites);
		        		}
		        		// create research staff and csm user and assign roles if provided
		        	}else{
		        		researchStaff = researchStaffRepository.createOrModifyResearchStaff(researchStaff, associationMap, hasAccessToAllSites);
		        	}
		        }
			}
        }catch (C3PRBaseRuntimeException e) {
          if (e.getRootCause().getMessage().contains("MailException")) {
              log.info("Error saving Research staff.Probably failed to send email", e);
          }else{
        	  errors.reject(e.getMessage());
          }
          
      }
	  if(saveExternalResearchStaff){
		  wrapper.setResearchStaff(remoteRStaffSelected);
	  }else{
		  wrapper.setResearchStaff(researchStaff);  
	  }
	  map.put("command", wrapper);
	  ModelAndView mv = new ModelAndView(getSuccessView(), map);
	  return mv;
    }

	@Required
    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
    }

    @Required
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
    
    @Required
	public void setResearchStaffRepository(ResearchStaffRepository researchStaffRepository) {
		this.researchStaffRepository = researchStaffRepository;
	}

	public ResearchStaffRepository getResearchStaffRepository() {
		return researchStaffRepository;
	}
	
	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}
	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}
	
}