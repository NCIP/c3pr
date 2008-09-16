package edu.duke.cabig.c3pr.web.admin;

import java.io.PrintWriter;
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
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorGroupDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;

public class CreateInvestigatorGroupsController extends SimpleFormController {

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
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
	throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(false));
		binder.registerCustomEditor(HealthcareSite.class, new CustomDaoEditor(healthcareSiteDao));
		binder.registerCustomEditor(HealthcareSiteInvestigator.class, new CustomDaoEditor(
				healthcareSiteInvestigatorDao));
	}

	@Override
	protected Map referenceData(HttpServletRequest request, Object command, Errors error) throws Exception {
		Map map = new HashMap();
		InvestigatorGroupsCommand investigatorGroupsCommand = (InvestigatorGroupsCommand) command;
		HealthcareSite healthcareSite = investigatorGroupsCommand.getHealthcareSite() ;
		if ( healthcareSite != null) {
			String sGroupId = request.getParameter("groupId") ;
			if (healthcareSite.getId() != null && !StringUtils.isBlank(sGroupId)) {
				List<InvestigatorGroup> investigatorGroups = healthcareSite.getInvestigatorGroups() ; 
				for (InvestigatorGroup investigatorGroup : investigatorGroups) {
					if (investigatorGroup.getId().equals(Integer.parseInt(sGroupId))) {
						map.put("groupIndex", investigatorGroups.indexOf(investigatorGroup));
						map.put("newGroup", new Boolean(false));
						return map;
					}
				}
			}else{
				map.put("groupIndex", healthcareSite.getInvestigatorGroups().size());
				map.put("newGroup", new Boolean(true));
			}
		}
		return map;
	}

	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		// this.c3PRDefaultTabConfigurer.injectDependencies(this.getPage());
		return super.formBackingObject(request);
	}

	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
	throws Exception {

		if (isAjaxRequest(request)) {
			request.getParameter("_asynchronous");
			ModelAndView modelAndView = page.postProcessAsynchronous(request,
					(InvestigatorGroupsCommand) command, errors);
			// setAjaxModelAndView(request, modelAndView);
			if (!errors.hasErrors() && shouldSave(request, (InvestigatorGroupsCommand) command)) {
				command = save((InvestigatorGroupsCommand) command, errors);
			}
			request.setAttribute(getFormSessionAttributeName(), command);
			if (isAjaxResponseFreeText(modelAndView)) {
				respondAjaxFreeText(modelAndView, response);
				return null;
			}
			return modelAndView;
		}

		if (errors.hasErrors()) {
			healthcareSiteDao.clear();
			response.getWriter().append("/*");
			response.getWriter().append("yes");
			response.getWriter().append("/*");
			response.getWriter().println(errors.getErrorCount());
			response.getWriter().append("/*");
			response.getWriter().append(errors.getMessage());
			response.getWriter().close();
		}
		else {
			InvestigatorGroupsCommand investigatorGroupsCommand = (InvestigatorGroupsCommand) command;
			healthcareSiteDao.save(investigatorGroupsCommand.getHealthcareSite());
			response.getWriter().append("/*");
			response.getWriter().append("no");
			response.getWriter().close();
		}
		return null;
	}

	@Override
	protected void onBindAndValidate(HttpServletRequest request, Object command,
			BindException errors) throws Exception {
		super.onBindAndValidate(request, command, errors);
		InvestigatorGroupsCommand investigatorGroupsCommand = (InvestigatorGroupsCommand) command ;
		HealthcareSite healthCareSite = investigatorGroupsCommand.getHealthcareSite() ;
		if (healthCareSite != null && healthCareSite.getId() != null) {
			List<InvestigatorGroup> investigatorGroups = healthCareSite.getInvestigatorGroups() ;
			for (InvestigatorGroup investigatorGroup : investigatorGroups ) {
				String sGroupId = request.getParameter("groupId") ; 
				if(StringUtils.isBlank(sGroupId)){
					validateInvestigatorGroup(healthCareSite, investigatorGroup, errors, true);
					validateSiteInvestigatorGroupAffiliations(investigatorGroup, errors);
				}else if(investigatorGroup.getId() != null  && !StringUtils.isBlank(sGroupId) && investigatorGroup.getId().equals(Integer.parseInt(sGroupId))){
					validateInvestigatorGroup(healthCareSite, investigatorGroup, errors, false);
					validateSiteInvestigatorGroupAffiliations(investigatorGroup, errors);
				}
			}
		}
	}

	public void validateInvestigatorGroup(HealthcareSite healthcareSite,
			InvestigatorGroup investigatorGroup, Errors errors, Boolean newGroup) {
		if ((investigatorGroup.getStartDate() != null && investigatorGroup.getEndDate() != null && investigatorGroup
				.getStartDate().after(investigatorGroup.getEndDate()))) {
			errors.reject("tempProperty",
			"/*End date of the group cannot be earlier than the start date/*");
		}
		if (newGroup) {
			List<InvestigatorGroup> allGroups = healthcareSite.getInvestigatorGroups();
			Set<InvestigatorGroup> uniqueGroups = new HashSet<InvestigatorGroup>();
			uniqueGroups.addAll(allGroups);
			if (allGroups.size() > uniqueGroups.size()) {
				errors.reject("tempProperty", "/*Investigator Group already exists/*");
			}
		}
	}

	public void validateSiteInvestigatorGroupAffiliations(InvestigatorGroup investigatorGroup, Errors errors) {
		for (SiteInvestigatorGroupAffiliation siteInvGrAffiliation : investigatorGroup.getSiteInvestigatorGroupAffiliations()) {
			if (siteInvGrAffiliation.getStartDate() != null && investigatorGroup.getStartDate() != null 
					&& siteInvGrAffiliation.getStartDate().before(investigatorGroup.getStartDate())) {
				errors.reject("tempProperty",
				"/*Start date of the investigator group affiliation cannot be earlier than the start date of the investigator group/*");
			}
			if (siteInvGrAffiliation.getStartDate() != null && siteInvGrAffiliation.getEndDate() != null
					&& siteInvGrAffiliation.getStartDate().after(siteInvGrAffiliation.getEndDate())) {
				errors.reject("tempProperty",
				"/*End date of the investigator group affiliation cannot be earlier than the start date/*");
			}
		}
		List<SiteInvestigatorGroupAffiliation> allAffiliations = investigatorGroup.getSiteInvestigatorGroupAffiliations();
		Set uniqueAffiliations = new HashSet<SiteInvestigatorGroupAffiliation>();
		uniqueAffiliations.addAll(allAffiliations);
		if (allAffiliations.size() > uniqueAffiliations.size()) {
			errors.reject("tempProperty", "/*Investigator already affiliated to the group/*");
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
			HttpServletResponse response, Object command, BindException errors)
	throws Exception {
		if (getSuccessView() == null) {
			throw new ServletException("successView isn't set");
		}
		return new ModelAndView(getSuccessView(), errors.getModel());
	}

	protected boolean isAjaxRequest(HttpServletRequest request) {
		if ("true".equalsIgnoreCase(request.getParameter(getAjaxRequestParamName()))){
			return true;
		}
		return false;
	}

	protected void setAjaxModelAndView(HttpServletRequest request, ModelAndView modelAndView) {
		request.setAttribute(getAjaxModelAndViewAttr(), modelAndView);
	}

	protected ModelAndView getAjaxModelAndView(HttpServletRequest request) {
		return (ModelAndView) request.getAttribute(getAjaxModelAndViewAttr());
	}

	protected boolean isAjaxResponseFreeText(ModelAndView modelAndView) {
		if (StringUtils.isBlank(modelAndView.getViewName())) {
			return true;
		}
		return false;
	}

	protected void respondAjaxFreeText(ModelAndView modelAndView, HttpServletResponse response)
	throws Exception {
		PrintWriter pr = response.getWriter();
		pr.println(modelAndView.getModel().get(getFreeTextModelName()));
		pr.flush();
	}

	protected String getAjaxRequestParamName() {
		return "_asynchronous";
	}

	protected String getAjaxModelAndViewAttr() {
		return "async_model_and_view";
	}

	protected String getFreeTextModelName() {
		return "free_text";
	}

	protected ModelAndView postProcessAsynchronous(HttpServletRequest request,
			InvestigatorGroupsCommand command, Errors error) throws Exception {
		return new ModelAndView(getAjaxViewName(request));
	}

	protected String getAjaxViewName(HttpServletRequest request) {
		return request.getParameter(getAjaxViewParamName());
	}

	protected String getAjaxViewParamName() {
		return "_asyncViewName";
	}

	protected boolean shouldSave(HttpServletRequest request, InvestigatorGroupsCommand command) {
		return true;
	}

}
