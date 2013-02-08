/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.admin;

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
import edu.duke.cabig.c3pr.service.OrganizationService;
import edu.duke.cabig.c3pr.service.PersonnelService;
import edu.duke.cabig.c3pr.tools.Configuration;
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
    
    private Configuration configuration;
    
    private OrganizationService organizationService;

    public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Required
    public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

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
        String assignedIdentifier = request.getParameter("assignedIdentifier") ;
        if (!StringUtils.isBlank(assignedIdentifier)) {
            log.info(" Request URl  is:" + request.getRequestURL().toString());
            inv = investigatorDao.getByAssignedIdentifierFromLocal(assignedIdentifier);
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
        model.put("coppaEnable", configuration.get(Configuration.COPPA_ENABLE));
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
		if((!StringUtils.isBlank(request.getParameter("_action")) && !"saveRemoteInvestigator".equals(request.getParameter("_action"))) || (request.getParameter("_action").equals("syncInvestigator") && request.getSession().getAttribute(FLOW).equals(EDIT_FLOW))){
			if ((request.getParameter("_action") != null) && !request.getParameter("_action").equals("syncInvestigator")) {
				Investigator invFromDB = investigatorDao
						.getByAssignedIdentifierFromLocal(investigator
								.getAssignedIdentifier());
				if (invFromDB != null) {
					return;
				}
			}
			List<Investigator> remoteInvestigators = investigatorDao.getRemoteInvestigators(investigator);
    		boolean matchingExternalInvestigatorPresent = false;
    		investigator.getExternalInvestigators().clear();
    		for(Investigator remoteInv : remoteInvestigators){
    			if(remoteInv.getAssignedIdentifier().equals(investigator.getAssignedIdentifier())){
    				investigator.addExternalInvestigator(remoteInv);
    				matchingExternalInvestigatorPresent = true;
    			}
    		}
    		if(!matchingExternalInvestigatorPresent){
    			errors.reject("REMOTE_INV_EXISTS","Investigator with assigned identifier " +investigator.getAssignedIdentifier()+ " does not exist in external system");
    		}else{
    			errors.reject("REMOTE_INV_EXISTS","Investigator with assigned identifier " +investigator.getAssignedIdentifier()+ " exists in external system");
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
				
				if(investigator.getExternalInvestigators()!=null && investigator.getExternalInvestigators().size()>0){
					saveExternalInvestigator = true;
					remoteInvSelected = (RemoteInvestigator) investigator
							.getExternalInvestigators().get(
									Integer.parseInt(request
											.getParameter("_selected")));
					if(remoteInvSelected.getHealthcareSiteInvestigators().size() > 0){
					
					for(HealthcareSiteInvestigator hcsInvestigator : remoteInvSelected.getHealthcareSiteInvestigators()){
	    				//	get the corresponding hcs from the dto object and save that organization and then save this staff
	        				HealthcareSite matchingHealthcareSiteFromDb = getHealthcareSiteDao().getByPrimaryIdentifier(hcsInvestigator.getHealthcareSite().getPrimaryIdentifier());
	        				if(matchingHealthcareSiteFromDb == null){
	        					organizationService.save(hcsInvestigator.getHealthcareSite());
	        				} else{
	    					//	we have the retrieved staff's Org in our db...link up with the same and persist
	        					hcsInvestigator.setHealthcareSite(matchingHealthcareSiteFromDb);
	        				}
	        			}
						investigatorDao.clear();
	        			personnelService.convertLocalInvestigatorToRemoteInvestigator((LocalInvestigator)investigator, remoteInvSelected);
						// add organizations of selected remote investigator  to the remote investigator in Db( which is just converted from local)
						//first load the remote investigator just converted
						RemoteInvestigator remoteInvestigatorFromDb = (RemoteInvestigator) investigatorDao.getByAssignedIdentifierFromLocal
							(remoteInvSelected.getAssignedIdentifier());
						
						// add organizations from selected remote research staff that the converted research staff doesn't have
						
						for(HealthcareSite hcs: remoteInvSelected.getHealthcareSites()){
							if(!remoteInvestigatorFromDb.getHealthcareSites().contains(hcs)){
								HealthcareSiteInvestigator hcsInv= new HealthcareSiteInvestigator();
								hcsInv.setHealthcareSite(hcs);
								hcsInv.setInvestigator(remoteInvestigatorFromDb);
							}
						}
					}else{
		      			errors.reject("REMOTE_INV_ORG_NULL","There is no Organization associated with the external Investigator");
		      		}
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
        HealthcareSite healthcareSite = healthcareSiteDao.getById(healthcareSiteId) ;
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
