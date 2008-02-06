package edu.duke.cabig.c3pr.web.admin;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorGroupDao;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.SimpleFormAjaxableController;
import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

public class CreateInvestigatorGroupsController extends SimpleFormController{
	
	InvestigatorGroupDao investigatorGroupDao;
	HealthcareSiteDao healthcareSiteDao;
	private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
	private Logger log = Logger.getLogger(CreateInvestigatorGroupsController.class);
	private InPlaceEditableTab<InvestigatorGroupsCommand> page;

	public CreateInvestigatorGroupsController() {
		super();
	}

	public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
		return healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteInvestigatorDao(
			HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
		this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

	public void setInvestigatorGroupDao(InvestigatorGroupDao investigatorGroupDao) {
		this.investigatorGroupDao = investigatorGroupDao;
	}
	@Override
	 protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
			super.initBinder(request, binder);
			binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(true));
			binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
			binder.registerCustomEditor(HealthcareSiteInvestigator.class, new CustomDaoEditor(
					healthcareSiteInvestigatorDao));
	 	}
	@Override
	protected Map referenceData(HttpServletRequest request, Object command, Errors error) throws Exception {
		Map map=new HashMap();
		InvestigatorGroupsCommand investigatorGroupsCommand=(InvestigatorGroupsCommand)command;
		if(investigatorGroupsCommand.getHealthcareSite()!=null){
			if(investigatorGroupsCommand.getHealthcareSite().getId()!=null && request.getParameter("groupId")!="" && request.getParameter("groupId")!=null){
				for(int i=0 ; i<investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().size() ; i++){
					if(investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().get(i).getId().equals(Integer.parseInt(request.getParameter("groupId")))){
						map.put("groupIndex", i);
						map.put("newGroup", new Boolean(false));
						return map;
					}
				}
			}
			map.put("groupIndex", investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().size());
			map.put("newGroup", new Boolean(true));
		}
		return map;
	}
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		//this.c3PRDefaultTabConfigurer.injectDependencies(this.getPage());
		return super.formBackingObject(request);
	}
	
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		if(isAjaxRequest(request)){
			request.getParameter("_asynchronous");
    		ModelAndView modelAndView =page.postProcessAsynchronous(request, (InvestigatorGroupsCommand)command, errors);
   			//setAjaxModelAndView(request, modelAndView);
   	        if (!errors.hasErrors() && shouldSave(request, (InvestigatorGroupsCommand)command)) {
   	        	command = save((InvestigatorGroupsCommand)command, errors);
   	        }
   	        request.setAttribute(getFormSessionAttributeName(), command);
   	        if(isAjaxResponseFreeText(modelAndView)){
				respondAjaxFreeText(modelAndView, response);
				return null;
			}   	        
   	        return modelAndView;
	}		InvestigatorGroupsCommand investigatorGroupsCommand=(InvestigatorGroupsCommand)command;
		
		int id;
		if(WebUtils.hasSubmitParameter(request, "groupId")){
			id=Integer.parseInt(request.getParameter("groupId"));
			log.debug("groupId:"+id);
			
			if (errors.hasErrors()){
				response.getWriter().println(id);
				response.getWriter().append("/*");
				response.getWriter().append("yes");
				response.getWriter().append("/*");
				response.getWriter().println(errors.getErrorCount());
				response.getWriter().append("/*");
				response.getWriter().append(errors.getMessage());
				response.getWriter().close();
			}
			else {

				healthcareSiteDao.save(investigatorGroupsCommand.getHealthcareSite());
				response.getWriter().println(id);
				response.getWriter().append("/*");
				response.getWriter().append("no");
				response.getWriter().close();
		}
		}
		
		return null;
	}
	
	
	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command, BindException errors) throws Exception {
		int id;
		super.onBindAndValidate(request, command, errors);
		if(((InvestigatorGroupsCommand)command).getHealthcareSite()!=null){
		if(((InvestigatorGroupsCommand)command).getHealthcareSite().getId()!=null && WebUtils.hasSubmitParameter(request, "groupId")){
			for(int i=0 ; i<((InvestigatorGroupsCommand)command).getHealthcareSite().getInvestigatorGroups().size() ; i++){
				if(((InvestigatorGroupsCommand)command).getHealthcareSite().getInvestigatorGroups().get(i).getId().equals(Integer.parseInt(request.getParameter("groupId")))){
					InvestigatorGroup investigatorGroup = ((InvestigatorGroupsCommand)command).getHealthcareSite().getInvestigatorGroups().get(i);
					if(request.getParameter("newGroup")!=null && request.getParameter("newGroup").equals("true")){
						validateInvestigatorGroup(((InvestigatorGroupsCommand)command).getHealthcareSite(), investigatorGroup, errors,true);
					}
					else {
						validateInvestigatorGroup(((InvestigatorGroupsCommand)command).getHealthcareSite(), investigatorGroup, errors,false);
					}
						
					validateSiteInvestigatorGroupAffiliations(((InvestigatorGroupsCommand)command).getHealthcareSite().getInvestigatorGroups().get(i), errors);
					
				}
			}
		}
		}
	}
	
	public void validateInvestigatorGroup(HealthcareSite healthcareSite, InvestigatorGroup investigatorGroup, Errors errors,Boolean newGroup) {
		if ((investigatorGroup.getStartDate()!=null && investigatorGroup.getEndDate()!=null && investigatorGroup.getStartDate().after(investigatorGroup.getEndDate()))) {
			errors.reject("tempProperty", "/*End Date of the group cannot be earlier than the start Date/*");
		}
		if ( newGroup){
			List<InvestigatorGroup> allGroups = healthcareSite.getInvestigatorGroups();
			Set uniqueGroups = new TreeSet<InvestigatorGroup>();
			uniqueGroups.addAll(allGroups);
			if (allGroups.size() > uniqueGroups.size()) {
				errors.reject("tempProperty","/*Investigator Group already exists/*");
			}
		}
		}

	public void validateSiteInvestigatorGroupAffiliations(InvestigatorGroup investigatorGroup, Errors errors) {
		for (SiteInvestigatorGroupAffiliation siteInvGrAffiliation : investigatorGroup.getSiteInvestigatorGroupAffiliations()) {
			if (siteInvGrAffiliation.getStartDate()!=null && investigatorGroup.getStartDate()!=null && siteInvGrAffiliation.getStartDate().before(investigatorGroup.getStartDate())){
				errors.reject("tempProperty", "/*Start date of the investigator group affiliation cannot be earlier than the start date of the investigator group/*");
			}
			if (siteInvGrAffiliation.getStartDate()!=null && siteInvGrAffiliation.getEndDate()!=null && siteInvGrAffiliation.getStartDate().after(siteInvGrAffiliation.getEndDate())){
				errors.reject("tempProperty", "/*End date of the investigator group affiliation cannot be earlier than the start Date/*");
			}
		} 
		List<SiteInvestigatorGroupAffiliation> allAffiliations = investigatorGroup.getSiteInvestigatorGroupAffiliations();

					Set uniqueAffiliations = new TreeSet<SiteInvestigatorGroupAffiliation>();
					uniqueAffiliations.addAll(allAffiliations);
						if (allAffiliations.size() > uniqueAffiliations.size()) {
							errors.reject("tempProperty","/*Investigator already affiliated to the group/*");
						}
		}
	
	protected HealthcareSiteDao getDao() {
		return healthcareSiteDao;
	}

	protected InvestigatorGroupsCommand getPrimaryDomainObject(InvestigatorGroupsCommand command) {
		return command;
	}
	
	protected InvestigatorGroupsCommand save(InvestigatorGroupsCommand command, BindException errors) {
		this.healthcareSiteDao.save(command.getHealthcareSite());
		return command;
	}
	
	public InPlaceEditableTab<InvestigatorGroupsCommand> getPage() {
		return page;
	}
	
	public void setPage(InPlaceEditableTab<InvestigatorGroupsCommand> page) {
		this.page = page;
	}
	
	protected ModelAndView onSynchronousSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors) throws Exception{
		if (getSuccessView() == null) {
			 throw new ServletException("successView isn't set");
		}
		return new ModelAndView(getSuccessView(), errors.getModel());
	}
	
	 protected boolean isAjaxRequest(HttpServletRequest request){
	    	if(StringUtils.getBlankIfNull(request.getParameter(getAjaxRequestParamName())).equalsIgnoreCase("true"))
	    		return true;
	    	return false;
	    }
	    
	    protected void setAjaxModelAndView(HttpServletRequest request, ModelAndView modelAndView){
	    	request.setAttribute(getAjaxModelAndViewAttr(), modelAndView);
	    }
	    
	    protected ModelAndView getAjaxModelAndView(HttpServletRequest request){
	    	return (ModelAndView)request.getAttribute(getAjaxModelAndViewAttr());
	    }
	    
	    protected boolean isAjaxResponseFreeText(ModelAndView modelAndView){
	    	if(StringUtils.getBlankIfNull(modelAndView.getViewName()).equals("")){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    protected void respondAjaxFreeText(ModelAndView modelAndView, HttpServletResponse response)throws Exception{
	    	PrintWriter pr=response.getWriter();
	    	pr.println(modelAndView.getModel().get(getFreeTextModelName()));
	    	pr.flush();
	    }
	    
	    protected String getAjaxRequestParamName(){
	    	return "_asynchronous";
	    }
	    
	    protected String getAjaxModelAndViewAttr(){
	    	return "async_model_and_view";
	    }

	    protected String getFreeTextModelName(){
	    	return "free_text";
	    }
	    
	    protected ModelAndView postProcessAsynchronous(HttpServletRequest request,InvestigatorGroupsCommand command, Errors error) throws Exception{
	    	return new ModelAndView(getAjaxViewName(request));
	    }
	    
		protected String getAjaxViewName(HttpServletRequest request){
			return request.getParameter(getAjaxViewParamName());
		}

		protected String getAjaxViewParamName(){
			return "_asyncViewName";
		}
		
		protected boolean shouldSave(HttpServletRequest request, InvestigatorGroupsCommand command){
			return true;
		}
	
}
	
