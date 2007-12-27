package edu.duke.cabig.c3pr.web.admin;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;

/**
 * @author Ramakrishna
 */
public class InvestigatorsGroupsTab<C> extends InPlaceEditableTab<C>{

	public InvestigatorsGroupsTab() {
		super("Investigator Groups","Investigator Groups","admin/investigator_groups_create");
	}
	
	public InvestigatorsGroupsTab(String longTitle, String shortTitle,
			String viewName) {
		super(longTitle, shortTitle, viewName);
	}

	public InvestigatorsGroupsTab(String longTitle, String shortTitle) {
		super(longTitle, shortTitle, "");
	}
	
	private HealthcareSiteDao healthcareSiteDao;
	
	public ModelAndView getInvestigatorGroups(HttpServletRequest request,
			Object commandObj, Errors error) {

		InvestigatorGroupsCommand investigatorGroupsCommand = (InvestigatorGroupsCommand) commandObj;
		HashMap map = new HashMap();
		if (request.getParameter("organizationId") != null) {
			int organizationId = Integer.parseInt(request
					.getParameter("organizationId"));
			HealthcareSite healthcareSite = healthcareSiteDao
					.getById(organizationId);
			investigatorGroupsCommand.setHealthcareSite(healthcareSite);
			map.put("command", investigatorGroupsCommand);
			ModelAndView  mv = new ModelAndView("admin/show_investigator_groups", map);
			return mv;
		}else {
			return new ModelAndView("", map);
		}
	}
	
	public ModelAndView getSiteAffiliations(HttpServletRequest request,
			Object commandObj, Errors error) {

		InvestigatorGroupsCommand investigatorGroupsCommand = (InvestigatorGroupsCommand) commandObj;
		HashMap map = new HashMap();
		if (request.getParameter("selected_site") != null) {
			int siteId = Integer.parseInt("selected_site");
			if(investigatorGroupsCommand.getHealthcareSite()!=null && investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups()!=null &&
					investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().get(siteId)!=null){
				map.put("siteAffiliations", investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().get(siteId).getSiteInvestigatorGroupAffiliations());
			}
			ModelAndView  mv = new ModelAndView("admin/show_investigator_affiliations", map);
			
			return mv;
		}else {
			return new ModelAndView("", map);
		}
	}

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

}
