package edu.duke.cabig.c3pr.web.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorGroupDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;

public class CreateInvestigatorGroupsController extends SimpleFormController{
	InvestigatorGroupDao investigatorGroupDao;
	HealthcareSiteDao healthcareSiteDao;
	private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
	
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
//			binder.registerCustomEditor(InvestigatorGroup.class, new CustomDaoEditor(investigatorGroupDao));
	 	}
	@Override
	protected Map referenceData(HttpServletRequest request, Object command, Errors error) throws Exception {
		Map map=new HashMap();
		InvestigatorGroupsCommand investigatorGroupsCommand=(InvestigatorGroupsCommand)command;
		if(investigatorGroupsCommand.getHealthcareSite().getId()!=null && WebUtils.hasSubmitParameter(request, "groupId")){
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
		return map;
	}
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		return super.formBackingObject(request);
	}
	@Override
	protected ModelAndView processFormSubmission(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		InvestigatorGroupsCommand investigatorGroupsCommand=(InvestigatorGroupsCommand)command;
		healthcareSiteDao.save(investigatorGroupsCommand.getHealthcareSite());
		int id;
		if(WebUtils.hasSubmitParameter(request, "groupId")){
			id=Integer.parseInt(request.getParameter("groupId"));
			System.out.println("groupId:"+id);
			response.getWriter().println(id);
			response.getWriter().close();
		}
		return null;
	}
}
