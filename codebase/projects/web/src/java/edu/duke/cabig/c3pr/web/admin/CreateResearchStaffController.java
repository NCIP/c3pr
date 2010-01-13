package edu.duke.cabig.c3pr.web.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.constants.C3PRUserGroupType;
import edu.duke.cabig.c3pr.dao.C3PRBaseDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalResearchStaff;
import edu.duke.cabig.c3pr.domain.RemoteResearchStaff;
import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.CommonUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.WebUtils;

/**
 * @author Ramakrishna
 */
public class CreateResearchStaffController<C extends ResearchStaff> extends
                AbstractCreateC3PRUserController<C, C3PRBaseDao<C>> {

    private PersonnelService personnelService;

    private ResearchStaffDao researchStaffDao;
    
    private CSMUserRepository csmUserRepository;
    
    private Configuration configuration;

    private String EDIT_FLOW = "EDIT_FLOW";

    private String SAVE_FLOW = "SAVE_FLOW";

    private String FLOW = "FLOW";

    private Logger log = Logger.getLogger(CreateResearchStaffController.class);
    
    public CreateResearchStaffController() {
    }

    /**
     * Create a nested object graph that Create Research Staff needs
     * 
     * @param request -
     *            HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        ResearchStaff researchStaff;
        String email = request.getParameter("emailId") ;
        if (!StringUtils.isBlank(email)) {
            researchStaff = researchStaffDao.getByEmailAddress(email);
            researchStaff.setGroups(personnelService.getGroups(researchStaff));
            researchStaffDao.initialize(researchStaff);
            request.getSession().setAttribute(FLOW, EDIT_FLOW);
        }
        else {
            researchStaff = new LocalResearchStaff();
            researchStaff.setVersion(Integer.parseInt("1"));

            //addContactsToResearchStaff(researchStaff);
            request.getSession().setAttribute(FLOW, SAVE_FLOW);
        }
        return researchStaff;
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object command,
    		Errors errors) throws Exception {
    	// TODO Auto-generated method stub
    	Map<String, Object> model = super.referenceData(request, command, errors);
        model.put("groups", C3PRUserGroupType.values());
        model.put("isAdmin", WebUtils.isAdmin());
        ResearchStaff researchStaff = (ResearchStaff)command;
        if(!StringUtils.isBlank(researchStaff.getLoginId())){
        	String username = csmUserRepository.getUsernameById(researchStaff.getLoginId());
        	model.put("isLoggedInUser", CommonUtils.getLoggedInUsername().equals(username));
            model.put("username", username);
        }else{
        	model.put("isLoggedInUser", false);
        }
        model.put("coppaEnable", configuration.get(Configuration.COPPA_ENABLE));
        return model;
    }
    
    @Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		super.onBindAndValidate(request, command, errors);
		ResearchStaff researchStaff = (ResearchStaff) command;
		if(WebUtils.hasSubmitParameter(request, "copyEmailAdress")){
			researchStaff.setLoginId(researchStaff.getEmail());
		}
		if(!request.getParameter("_action").equals("saveRemoteRStaff") || request.getParameter("_action").equals("syncResearchStaff") && request.getSession().getAttribute(FLOW).equals(EDIT_FLOW)){
			if (! request.getParameter("_action").equals("syncResearchStaff")) {
				ResearchStaff rStaffFromDB = researchStaffDao
						.getByEmailAddressFromLocal(researchStaff
								.getEmail());
				if (rStaffFromDB != null) {
					// This check is already being done in the UsernameDuplicate Validator.
					//errors.reject("RSTAFF_EXISTS","Research Staff with Email " +researchStaff.getEmailAsString()+ " already exists");
					return;
				}
			}
			List<ResearchStaff> remoteResearchStaff = researchStaffDao.getRemoteResearchStaff(researchStaff);
    		boolean matchingExternalResearchStaffPresent = false;
    		for(ResearchStaff remoteRStaff : remoteResearchStaff){
    			if(remoteRStaff.getEmail().equals(researchStaff.getEmail())){
    				researchStaff.addExternalResearchStaff(remoteRStaff);
    				matchingExternalResearchStaffPresent = true;
    			}
    		}
    		if(matchingExternalResearchStaffPresent){
    			errors.reject("REMOTE_RSTAFF_EXISTS","Research Staff with Email " +researchStaff.getEmail()+ " exists in external system");
    		}
    	}
	}
    
    

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit (HttpServletRequest
     *      request, HttpServletResponse response, Object command, BindException errors)
     */

    @Override
	protected boolean suppressValidation(HttpServletRequest request,
			Object command) {
    	return (request.getParameter("_action").equals("saveRemoteRStaff") || request.getParameter("_action").equals("syncResearchStaff"));
	}

	@Override
    protected ModelAndView onSynchronousSubmit(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors)
                    throws Exception {
        ResearchStaff researchStaff = (ResearchStaff) command;
        
        RemoteResearchStaff remoteRStaffSelected = null;
        boolean saveExternalResearchStaff = false;

        try {
            if (request.getSession().getAttribute(FLOW).equals(SAVE_FLOW)) {
            	
            	if("saveRemoteRStaff".equals(request.getParameter("_action"))){
            		saveExternalResearchStaff = true;
            		remoteRStaffSelected = (RemoteResearchStaff)researchStaff.getExternalResearchStaff().get(Integer.parseInt(request.getParameter("_selected")));
            		
            		if(remoteRStaffSelected.getHealthcareSite() != null){
            			//get the corresponding hcs from the dto object and save that organization and then save this staff
            			HealthcareSite matchingHealthcareSiteFromDb = getHealthcareSiteDao().getByPrimaryIdentifier(remoteRStaffSelected.getHealthcareSite().getPrimaryIdentifier());
            			if(matchingHealthcareSiteFromDb == null){
            				getHealthcareSiteDao().save(remoteRStaffSelected.getHealthcareSite());
            			} else{
            				//we have the retrieved staff's Org in our db...link up with the same and persist
            				remoteRStaffSelected.setHealthcareSite(matchingHealthcareSiteFromDb);
            			}
            			 personnelService.save(remoteRStaffSelected);
            		}else{
            			errors.reject("REMOTE_RS_ORG_NULL","There is no Organization associated with the external Research Staff");
            		}
            	}
            	personnelService.save(researchStaff);
            } else if ("saveRemoteRStaff".equals(request.getParameter("_action"))) {
            	
            	researchStaffDao.evict(researchStaff);
            	
				if(researchStaff.getExternalResearchStaff()!=null && researchStaff.getExternalResearchStaff().size()>0){
					saveExternalResearchStaff = true;
					remoteRStaffSelected = (RemoteResearchStaff) researchStaff
							.getExternalResearchStaff().get(
									Integer.parseInt(request
											.getParameter("_selected")));
					personnelService.convertLocalResearchStaffToRemoteResearchStaff((LocalResearchStaff)researchStaff, remoteRStaffSelected);
				}
			}  else {
                personnelService.merge(researchStaff);
            }
        }
        catch (C3PRBaseException e) {
            logger.error(e);
            // TODO should be in validator
            if (e.getLocalizedMessage().contains("uq_login_name")) {
                errors.reject("Username already exists");
            }
        }
        catch (C3PRBaseRuntimeException e) {
            if (e.getRootCause().getMessage().contains("MailException")) {
                // no problem
                log.info("Error saving Research staff.Probably failed to send email", e);
            }
        }

        Map map = errors.getModel();
        map.put("command", saveExternalResearchStaff?remoteRStaffSelected:researchStaff);
        String studyflow = request.getParameter("studyflow") ; 
        if(!StringUtils.isBlank(studyflow)){
        	map.put("studyflow", studyflow);
        }
        ModelAndView mv = new ModelAndView(getSuccessView(), map);
        return mv;
    }

    public PersonnelService getPersonnelService() {
        return personnelService;
    }

    public void setPersonnelService(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    public ResearchStaffDao getResearchStaffDao() {
        return researchStaffDao;
    }

    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
    }

    @Override
    protected C3PRBaseDao getDao() {
        // TODO Auto-generated method stub
        return this.researchStaffDao;
    }

    @Override
    protected C getPrimaryDomainObject(C command) {
        return command;
    }

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
		this.csmUserRepository = csmUserRepository;
	}

}