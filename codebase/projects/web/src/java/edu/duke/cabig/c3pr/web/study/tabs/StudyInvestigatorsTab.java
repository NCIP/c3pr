package edu.duke.cabig.c3pr.web.study.tabs;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.study.StudyWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:02:39 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyInvestigatorsTab extends StudyTab {

    private StudyValidator studyValidator;

    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

    private OrganizationDao organizationDao;

    StudySiteDao studySiteDao = null;

    public StudyInvestigatorsTab() {
        this(false);
    }

    public StudyInvestigatorsTab(boolean editMode) {
        super("Study Investigators", "Study Investigators", "study/study_investigators");
    }

    @Override
    public Map<String, Object> referenceData(StudyWrapper wrapper) {
        Map<String, Object> refdata = super.referenceData(wrapper);
        addConfigMapToRefdata(refdata, "studyInvestigatorStatusRefData");
        return refdata;
    }

    @Override
    public void validate(StudyWrapper wrapper, Errors errors) {
        super.validate(wrapper, errors);
        this.studyValidator.validateStudyInvestigators(wrapper.getStudy(), errors);
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, StudyWrapper wrapper,
                                        Errors errors) {

        String selected = request.getParameter("_selected");
        String action = request.getParameter("_actionx");
        String selectedSite = request.getParameter("_selectedSite");
        StudySite studyOrg = null;

        // get the StudySite to which we will add/remove investigator.
        List<StudySite> studyOrgList = wrapper.getStudy().getStudySites();
        if (!StringUtils.isBlank(selectedSite)) {
            studyOrg = studyOrgList.get(Integer.parseInt(selectedSite));
        }

        if (!errors.hasErrors()) {

            if (StringUtils.equals("siteChange", action)) {
                request.getSession().setAttribute("_selectedSite", selectedSite);
            }else if (StringUtils.equals("addStudyInvestigator", action)&& studyOrg != null) {
                String[] studyInvestigatorIds = studyOrg.getStudyInvestigatorIds();
                if (studyInvestigatorIds.length > 0) {
                    HealthcareSiteInvestigator healthcareSiteInvestigator = null;
                    log.debug("Study InvestigatorIds Size : " + studyOrg.getStudyInvestigatorIds().length);
                    for (String studyInvestigatorId : studyInvestigatorIds) {
                        log.debug(" Study Investigator Id : " + studyInvestigatorId);
                        StudyInvestigator studyInvestigator = new StudyInvestigator();
                        healthcareSiteInvestigator = healthcareSiteInvestigatorDao.getById(Integer.parseInt(studyInvestigatorId));
                        if (healthcareSiteInvestigator != null) {
                            healthcareSiteInvestigator.getStudyInvestigators().add(studyInvestigator);
                            studyInvestigator.setHealthcareSiteInvestigator(healthcareSiteInvestigator);
                            studyInvestigator.setRoleCode("Site Investigator");
                            studyInvestigator.setStatusCode("Active");
                            studyInvestigator.setStudyOrganization(studyOrg);

                            HashSet<StudyInvestigator> sStudyInvestigator = new HashSet<StudyInvestigator>();
                            sStudyInvestigator.addAll(studyOrg.getStudyInvestigators());
                            if (sStudyInvestigator.add(studyInvestigator)) {
                                studyOrg.getStudyInvestigators().add(studyInvestigator);
                            } else {
                                errors.rejectValue("study.studySites[0].studyInvestigators", new Integer(studyValidator.getCode("C3PR.STUDY.DUPLICATE.STUDY.INVESTIGATOR.ROLE.ERROR")).toString(), studyValidator.getMessageFromCode(
                                        studyValidator.getCode("C3PR.STUDY.DUPLICATE.STUDY.INVESTIGATOR.ROLE.ERROR"),
                                        null, null));
                            }
                        } else {
                            log.error("StudyInvestigatorTab - postProcessOnValidation(): healthcareSiteInvestigatorDao.getById() returned null");
                        }
                    }
                }
            }
        }

        if (StringUtils.equals("removeStudyInvestigator", action) && studyOrg != null) {
            studyOrg.getStudyInvestigators().remove(Integer.parseInt(selected));
        }
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

    public OrganizationDao getOrganizationDao() {
        return organizationDao;
    }

    public void setOrganizationDao(OrganizationDao organizationDao) {
        this.organizationDao = organizationDao;
    }

    public StudySiteDao getStudySiteDao() {
        return studySiteDao;
    }

    public void setStudySiteDao(StudySiteDao studySiteDao) {
        this.studySiteDao = studySiteDao;
    }

    public HealthcareSiteInvestigatorDao getHealthcareSiteInvestigatorDao() {
        return healthcareSiteInvestigatorDao;
    }

    public void setHealthcareSiteInvestigatorDao(
            HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
        this.healthcareSiteInvestigatorDao = healthcareSiteInvestigatorDao;
    }

}
