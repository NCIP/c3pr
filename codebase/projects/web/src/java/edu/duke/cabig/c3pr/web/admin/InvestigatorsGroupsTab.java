package edu.duke.cabig.c3pr.web.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

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
	
	@Override
	protected ModelAndView postProcessInPlaceEditing(HttpServletRequest request, C command, String property, String value) throws Exception {
		InvestigatorGroupsCommand investigatorGroupsCommand = (InvestigatorGroupsCommand)command;
		Map<String, String> map = new HashMap<String, String>();
        String retValue = "";
        int groupIndex =0;
        if(WebUtils.hasSubmitParameter(request, "groupIndex")){
        	groupIndex = Integer.parseInt(request.getParameter("groupIndex"));
        }
		if (property.startsWith("changedSiteAffiliationEndDate")) {
			 int siteAffiliationIndex = Integer.parseInt(property.split("_")[1]);
			 Date currentEndDate = investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().get(groupIndex).getSiteInvestigatorGroupAffiliations().get(siteAffiliationIndex).getEndDate();
            try {
                Date targetEndDate = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().get(groupIndex).getSiteInvestigatorGroupAffiliations().get(siteAffiliationIndex).setEndDate(targetEndDate);
                retValue += investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().get(groupIndex).getSiteInvestigatorGroupAffiliations().get(siteAffiliationIndex).getEndDateStr();
            } catch (ParseException e) {
                  e.printStackTrace();
                  retValue +=currentEndDate;
            }
        }
		map.put(getFreeTextModelName(), retValue);
        return new ModelAndView("", map);
	}
	

	public HealthcareSiteDao getHealthcareSiteDao() {
		return healthcareSiteDao;
	}

	public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
		this.healthcareSiteDao = healthcareSiteDao;
	}

}
