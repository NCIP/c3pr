package edu.duke.cabig.c3pr.web.study;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.OrganizationDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:02:39 PM To change this template
 * use File | Settings | File Templates.
 */
class StudyInvestigatorsTab extends StudyTab {

    private StudyValidator studyValidator;

    private HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

    private OrganizationDao organizationDao;

    StudySiteDao studySiteDao = null;

    public StudyInvestigatorsTab() {
        this(false);
    }

    public StudyInvestigatorsTab(boolean editMode) {
        super("Investigators", "Investigators", editMode ? "study/study_investigators_edit"
                        : "study/study_investigators");
    }

    @Override
    public Map<String, Object> referenceData(Study study) {
        Map<String, Object> refdata = super.referenceData(study); // To change body of overridden
                                                                    // methods use File | Settings |
                                                                    // File Templates.
        addConfigMapToRefdata(refdata, "studyInvestigatorRoleRefData");
        addConfigMapToRefdata(refdata, "studyInvestigatorStatusRefData");

        return refdata;
    }

    @Override
    public void validate(Study study, Errors errors) {
        super.validate(study, errors);
        this.studyValidator.validateStudyInvestigators(study, errors);
    }

    @Override
    public void postProcessOnValidation(HttpServletRequest request, Study study,
                    Errors errors) {

        String selected = request.getParameter("_selected");
        String action = request.getParameter("_actionx");
        String selectedSite = request.getParameter("_selectedSite");
        StudyOrganization studyOrg = null;

        // get the StudyOrganization to which we will add/remove investigator.
        List<StudyOrganization> studyOrgList = study.getStudyOrganizations();
        if (!StringUtils.isBlank(selectedSite)) {
            studyOrg = studyOrgList.get(Integer.parseInt(selectedSite));
        }

        if (!errors.hasErrors()) {

            if ("siteChange".equals(action)) {
                request.getSession().setAttribute("_selectedSite", selectedSite);
                return;
            }

            if ("addStudyDisease".equals(action) && studyOrg != null) {
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
                            studyOrg.getStudyInvestigators().add(studyInvestigator);
                            studyValidator.validateStudyInvestigators(study, errors);
                            if (errors.hasErrors()) {
                                studyOrg.getStudyInvestigators().remove(studyInvestigator);
                            }
                        }
                        else {
                            log.error("StudyInvestigatorTab - postProcessOnValidation(): healthcareSiteInvestigatorDao.getById() returned null");
                        }
                    }
                }
                return;
            }
        }

        if ("removeStudyDisease".equals(action) && studyOrg != null) {
            studyOrg.getStudyInvestigators().remove(Integer.parseInt(selected));
            return;
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
