package edu.duke.cabig.c3pr.web.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.dao.C3PRBaseDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.Organization;
import edu.duke.cabig.c3pr.domain.RemoteInvestigator;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * @author Ramakrishna
 * @author kherm
 */
public class CreateInvestigatorController<C extends Investigator> extends
                AbstractCreateC3PRUserController<C, C3PRBaseDao<C>> {

    private PersonnelService personnelService;

    private InvestigatorDao investigatorDao;
    
    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

    private String EDIT_FLOW = "EDIT_FLOW";

    private String SAVE_FLOW = "SAVE_FLOW";

    private String FLOW = "FLOW";

    private Logger log = Logger.getLogger(CreateInvestigatorController.class);

    public CreateInvestigatorController() {
    }

    /**
     * Create a nested object graph that Create Investigator Design needs Incase the flow is coming
     * from search...we get the id and get the corresponding investigator obj.
     * 
     * @param request -
     *            HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Investigator inv;
        String email = request.getParameter("emailId") ;
        if (!StringUtils.isBlank(email)) {
            log.info(" Request URl  is:" + request.getRequestURL().toString());
            inv = investigatorDao.getByEmailAddress(email);
            if(inv != null){
            	investigatorDao.initialize(inv);
                request.getSession().setAttribute(FLOW, EDIT_FLOW);
                log.info(" Investigator's ID is:" + inv.getId());
            }
        }
        else {
        	String healthcareSiteId = request.getParameter("healthcareSiteId") ;
        	if(!StringUtils.isBlank(healthcareSiteId)){
        		inv = createInvestigatorWithHealthcareSite(Integer.parseInt(healthcareSiteId));
        	}else{
        		inv = createInvestigatorWithDesign();
        	}
            request.getSession().setAttribute(FLOW, SAVE_FLOW);
        }
        return inv;
    }
    
    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors errors) throws Exception {
    	Map<String, Object> model = super.referenceData(request, command, errors);
        model.put("studyflow", request.getParameter("studyflow"));
        model.put("createPI", request.getParameter("createPI"));
        return model;
    }
    
    

    @Override
    protected boolean shouldSave(HttpServletRequest request, Investigator command) {
        return true;
    }
    
    @Override
	protected void onBindAndValidate(HttpServletRequest request,
			Object command, BindException errors) throws Exception {
		super.onBindAndValidate(request, command, errors);
		Investigator investigator = (Investigator) command;
		Map<String, Organization> uniqueOrganization= new HashMap<String, Organization>();
		for(HealthcareSiteInvestigator healthcareSiteInvestigator : investigator.getHealthcareSiteInvestigators()){
			uniqueOrganization.put(healthcareSiteInvestigator.getHealthcareSite().getPrimaryIdentifier(), healthcareSiteInvestigator.getHealthcareSite());
		}
		if(uniqueOrganization.size() != investigator.getHealthcareSiteInvestigators().size()){
			errors.reject("DUPLICATIE_ORG_EXISTS","Duplicate organization.");
		}
		if(!"saveRemoteInvestigator".equals(request.getParameter("_action")) || (request.getParameter("_action").equals("syncInvestigator") && request.getSession().getAttribute(FLOW).equals(EDIT_FLOW))){
			if (! request.getParameter("_action").equals("syncInvestigator")) {
				Investigator invFromDB = investigatorDao
						.getByEmailAddressFromLocal(investigator
								.getEmail());
				if (invFromDB != null) {
					return;
				}
			}
			List<Investigator> remoteInvestigators = investigatorDao.getRemoteInvestigators(investigator);
    		boolean matchingExternalInvestigatorPresent = false;
    		for(Investigator remoteInv : remoteInvestigators){
    			if(remoteInv.getEmail().equals(investigator.getEmail())){
    				investigator.addExternalInvestigator(remoteInv);
    				matchingExternalInvestigatorPresent = true;
    			}
    		}
    		if(matchingExternalInvestigatorPresent){
    			errors.reject("REMOTE_INV_EXISTS","Investigator with email " +investigator.getEmail()+ " exists in external system");
    		}
    	}
	}
    

    @Override
    protected ModelAndView onSynchronousSubmit(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors)
                    throws Exception {

        Investigator investigator = (Investigator) command;
        
        RemoteInvestigator remoteInvSelected = null;
        boolean saveExternalInvestigator = false;

        try {
            if (request.getSession().getAttribute(FLOW).equals(SAVE_FLOW)) {
            	if("saveRemoteInvestigator".equals(request.getParameter("_action"))){
            		
            		saveExternalInvestigator = true;
            		
            		remoteInvSelected = (RemoteInvestigator) investigator.getExternalInvestigators().get(Integer.parseInt(request.getParameter("_selected")));
            		investigatorDao.buildAndSaveNewRemoteInvestigator(remoteInvSelected);
            	}  else{
            		personnelService.save(investigator);
            	}
            } else if ("saveRemoteInvestigator".equals(request.getParameter("_action"))) {
            	
            	investigatorDao.evict(investigator);
				
				if(investigator.getExternalInvestigators()!=null && investigator.getExternalInvestigators().size()>0){
					saveExternalInvestigator = true;
					remoteInvSelected = (RemoteInvestigator) investigator
							.getExternalInvestigators().get(
									Integer.parseInt(request
											.getParameter("_selected")));
					personnelService.convertLocalInvestigatorToRemoteInvestigator((LocalInvestigator)investigator, remoteInvSelected);
				}
			}
            else {
                for (HealthcareSiteInvestigator hcsInv : investigator.getHealthcareSiteInvestigators()) {
                    if (hcsInv.getStatusCode() != null && !hcsInv.getStatusCode().equals(InvestigatorStatusCodeEnum.AC)) {
                        for (SiteInvestigatorGroupAffiliation sInvGrAff : hcsInv
                                        .getSiteInvestigatorGroupAffiliations()) {
                            sInvGrAff.setEndDate(new Date());
                        }
                        for (StudyInvestigator studyInv : hcsInv.getStudyInvestigators()) {
                            studyInv.setEndDate(new Date());
                        }
                    }
                }

                investigator = personnelService.merge(investigator);
            }

        }
        catch (C3PRBaseException e) {
            e.printStackTrace(); // To change body of catch statement use File | Settings | File
                                    // Templates.
        }
        catch (C3PRBaseRuntimeException e) {
            if (e.getRootCause().getMessage().contains("MailException")) {
                // no problem
                log.info("Error saving Investigator.Probably failed to send email", e);
            }
        }
        Map map = errors.getModel();
        
        map.put("command", saveExternalInvestigator? remoteInvSelected:investigator); 
       
        String studyflow = request.getParameter("studyflow");
        if(!StringUtils.isBlank(studyflow)){
        	map.put("studyflow", studyflow);
        }
        
        String createPI = request.getParameter("createPI");
        if(!StringUtils.isBlank(createPI)){
        	map.put("createPI", createPI);
        }else{
        	map.put("createPI", "false");
        }
        ModelAndView mv = new ModelAndView(getSuccessView(), map);
        return mv;
    }

    private Investigator createInvestigatorWithDesign() {
        LocalInvestigator investigator = new LocalInvestigator();
        HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
        investigator.addHealthcareSiteInvestigator(healthcareSiteInvestigator);
        return investigator;
    }
    
    private Investigator createInvestigatorWithHealthcareSite(int healthcareSiteId) {
        LocalInvestigator investigator = new LocalInvestigator();
        HealthcareSiteInvestigator healthcareSiteInvestigator = new HealthcareSiteInvestigator();
        HealthcareSite healthcareSite = investigatorDao.getHealthcareSiteDao().getById(healthcareSiteId) ;
        healthcareSiteInvestigator.setHealthcareSite(healthcareSite);
        investigator.addHealthcareSiteInvestigator(healthcareSiteInvestigator);
        return investigator;
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
    	if(command instanceof RemoteInvestigator){
    		return true;
    	}
    	return ("saveRemoteInvestigator".equals(request.getParameter("_action")) || "syncInvestigator".equals(request.getParameter("_action")));
	}

    public PersonnelService getPersonnelService() {
        return personnelService;
    }

    public void setPersonnelService(PersonnelService personnelService) {
        this.personnelService = personnelService;
    }

    public InvestigatorDao getInvestigatorDao() {
        return investigatorDao;
    }

    public void setInvestigatorDao(InvestigatorDao investigatorDao) {
        this.investigatorDao = investigatorDao;
    }

    @Override
    protected C3PRBaseDao getDao() {
        return this.investigatorDao;
    }

    @Override
    protected C getPrimaryDomainObject(C command) {
        return command;
    }

	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

}