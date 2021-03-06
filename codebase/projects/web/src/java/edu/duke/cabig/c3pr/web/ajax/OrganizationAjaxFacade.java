/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.ajax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.HttpSessionRequiredException;

import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorGroupDao;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.InvestigatorGroup;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.RemoteHealthcareSite;
import edu.duke.cabig.c3pr.domain.SiteInvestigatorGroupAffiliation;

/**
 * @author Ramakrishna
 */
public class OrganizationAjaxFacade {
    private HealthcareSiteDao healthcareSiteDao;

    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

    private InvestigatorGroupDao investigatorGroupDao;

    private static Log log = LogFactory.getLog(OrganizationAjaxFacade.class);

    @SuppressWarnings("unchecked")
    private <T> T buildReduced(T src, List<String> properties) {
        T dst = null;
        try {
            // it doesn't seem like this cast should be necessary
            dst = (T) src.getClass().newInstance();
        }
        catch (InstantiationException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to instantiate " + src.getClass().getName(), e);
        }

        BeanWrapper source = new BeanWrapperImpl(src);
        BeanWrapper destination = new BeanWrapperImpl(dst);
        for (String property : properties) {
            destination.setPropertyValue(property, source.getPropertyValue(property));
        }
        return dst;
    }

    public List<HealthcareSite> matchHealthcareSites(String text) throws Exception {

        List<HealthcareSite> healthcareSites = healthcareSiteDao
                        .getBySubnames(extractSubnames(text));

        List<HealthcareSite> reducedHealthcareSites = new ArrayList<HealthcareSite>(healthcareSites
                        .size());
        for (HealthcareSite healthcareSite : healthcareSites) {
        	if(healthcareSite instanceof RemoteHealthcareSite){
        		reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name", "identifiersAssignedToOrganization",
                "externalId")));
        	}
        	else {reducedHealthcareSites.add(buildReduced(healthcareSite, Arrays.asList("id", "name", "identifiersAssignedToOrganization")));
        	}
        }
        return reducedHealthcareSites;

    }

    public List<HealthcareSiteInvestigator> matchOrganizationInvestigatorsGivenOrganizationId(
                    String text, int organizationId, HttpServletRequest request) throws Exception {
        List<HealthcareSiteInvestigator> inv = healthcareSiteInvestigatorDao.getBySubnames(
                        extractSubnames(text), organizationId);
        List<HealthcareSiteInvestigator> reducedInv = new ArrayList<HealthcareSiteInvestigator>(inv
                        .size());
        for (HealthcareSiteInvestigator hcInv : inv) {
            if (hcInv.getStatusCode() != null && hcInv.getStatusCode().equals(InvestigatorStatusCodeEnum.AC)) {
                HealthcareSiteInvestigator temp;
                temp = buildReduced(hcInv, Arrays.asList("id"));
                temp.setInvestigator(buildReduced(hcInv.getInvestigator(), Arrays.asList(
                                "firstName", "lastName", "maidenName", "assignedIdentifier")));
                reducedInv.add(temp);
            }

        }

        return reducedInv;
    }

    public List<InvestigatorGroup> getInvestigatorGroups(int organizationId,
                    HttpServletRequest request) throws Exception {

        HealthcareSite healthcareSite = healthcareSiteDao.getById(organizationId);
        if (healthcareSite.getInvestigatorGroups() != null
                        && healthcareSite.getInvestigatorGroups().size() > 0) {

            List<InvestigatorGroup> reducedInvestigatorGroups = new ArrayList<InvestigatorGroup>();
            for (InvestigatorGroup investigatorGroup : healthcareSite.getInvestigatorGroups()) {
                reducedInvestigatorGroups.add(buildReduced(investigatorGroup, Arrays.asList("id",
                                "name", "descriptionText", "startDate", "endDate")));
            }
            return reducedInvestigatorGroups;
        }
        return null;
    }

    public List<Investigator> getAffiliatedInvestigatorsForGroup(int groupId,
                    HttpServletRequest request) throws Exception {

        List<Investigator> reducedInvestigators = new ArrayList<Investigator>();
        for (SiteInvestigatorGroupAffiliation siteInvestigatorGroupAffiliation : investigatorGroupDao
                        .getAffiliationsByGroupId(groupId)) {
            if (siteInvestigatorGroupAffiliation.getHealthcareSiteInvestigator().getStatusCode() != null
                            && siteInvestigatorGroupAffiliation.getHealthcareSiteInvestigator()
                                            .getStatusCode().equals(InvestigatorStatusCodeEnum.AC)) {
                reducedInvestigators.add(buildReduced(siteInvestigatorGroupAffiliation
                                .getHealthcareSiteInvestigator().getInvestigator(), Arrays.asList(
                                "lastName", "firstName", "id")));
            }
        }
        return reducedInvestigators;
    }

    private final Object getCommandOnly(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new HttpSessionRequiredException(
                            "Must have session when trying to bind (in session-form mode)");
        }
        String formAttrName = getFormSessionAttributeName();
        Object sessionFormObject = session.getAttribute(formAttrName);

        return sessionFormObject;
    }

    // //// CONFIGURATION

    @Required
    private String getFormSessionAttributeName() {
        return "edu.duke.cabig.c3pr.web.admin.CreateInvestigatorGroupsController.FORM.command";
    }

    private String[] extractSubnames(String text) {
        return text.split("\\s+");
    }

    public HealthcareSiteDao getHealthcareSiteDao() {
        return healthcareSiteDao;
    }

    public void setHealthcareSiteDao(HealthcareSiteDao healthcareSiteDao) {
        this.healthcareSiteDao = healthcareSiteDao;
    }

    public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
        return healthcareSiteInvestigatorDao;
    }

    public void setHealthcareSiteInvestigatorDao(
                    HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
        this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
    }

    public InvestigatorGroupDao getInvestigatorGroupDao() {
        return investigatorGroupDao;
    }

    public void setInvestigatorGroupDao(InvestigatorGroupDao investigatorGroupDao) {
        this.investigatorGroupDao = investigatorGroupDao;
    }

}
