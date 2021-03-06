/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.admin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;

/**
 * @author Ramakrishna
 */
public class InvestigatorsGroupsTab<C> extends InPlaceEditableTab<C> {

    public InvestigatorsGroupsTab() {
        super("Investigator Groups", "Investigator Groups", "admin/investigator_groups_create");
    }

    public InvestigatorsGroupsTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public InvestigatorsGroupsTab(String longTitle, String shortTitle) {
        super(longTitle, shortTitle, "");
    }

    private HealthcareSiteDao healthcareSiteDao;

    @Override
    protected ModelAndView postProcessInPlaceEditing(HttpServletRequest request, C command,
                    String property, String value, Errors errors) throws Exception {
        InvestigatorGroupsCommand investigatorGroupsCommand = (InvestigatorGroupsCommand) command;
        Map<String, String> map = new HashMap<String, String>();
        String retValue = "";
        int groupIndex = 0;
        if (WebUtils.hasSubmitParameter(request, "groupIndex")) {
            groupIndex = Integer.parseInt(request.getParameter("groupIndex"));
        }
        if (property.startsWith("changedSiteAffiliationEndDate")) {
            int siteAffiliationIndex = Integer.parseInt(property.split("_")[1]);
            Date currentEndDate = investigatorGroupsCommand.getHealthcareSite()
                            .getInvestigatorGroups().get(groupIndex)
                            .getSiteInvestigatorGroupAffiliations().get(siteAffiliationIndex)
                            .getEndDate();
            try {
                Date targetEndDate = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().get(
                                groupIndex).getSiteInvestigatorGroupAffiliations().get(
                                siteAffiliationIndex).setEndDate(targetEndDate);
                retValue += investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups()
                                .get(groupIndex).getSiteInvestigatorGroupAffiliations().get(
                                                siteAffiliationIndex).getEndDateStr();
            }
            catch (ParseException e) {
                e.printStackTrace();
                retValue += currentEndDate;
            }
        }
        if (property.startsWith("changedSiteAffiliationStartDate")) {
            int siteAffiliationIndex = Integer.parseInt(property.split("_")[1]);
            Date currentStartDate = investigatorGroupsCommand.getHealthcareSite()
                            .getInvestigatorGroups().get(groupIndex)
                            .getSiteInvestigatorGroupAffiliations().get(siteAffiliationIndex)
                            .getStartDate();
            try {
                Date targetStartDate = new SimpleDateFormat("MM/dd/yyyy").parse(value);
                investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups().get(
                                groupIndex).getSiteInvestigatorGroupAffiliations().get(
                                siteAffiliationIndex).setStartDate(targetStartDate);
                retValue += investigatorGroupsCommand.getHealthcareSite().getInvestigatorGroups()
                                .get(groupIndex).getSiteInvestigatorGroupAffiliations().get(
                                                siteAffiliationIndex).getStartDateStr();
            }
            catch (ParseException e) {
                e.printStackTrace();
                retValue += currentStartDate;
            }
        }
        map.put(AjaxableUtils.getFreeTextModelName(), retValue);
        return new ModelAndView("", map);
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

}
