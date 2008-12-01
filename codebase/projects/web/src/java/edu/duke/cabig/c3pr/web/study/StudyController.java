package edu.duke.cabig.c3pr.web.study;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.multipart.support.StringMultipartFileEditor;

import edu.duke.cabig.c3pr.dao.CompanionStudyAssociationDao;
import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.ResearchStaffDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.NullIdDaoBasedEditor;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AutomaticSaveAjaxableFormController;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * Base Controller class to handle the basic work flow in the Creation / Updation of a Study Design
 * This uses AbstractTabbedFlowFormController to implement tabbed workflow
 *
 * @author Priyatam
 * @author kherm
 * @author Himanshu
 */
public abstract class StudyController<C extends StudyWrapper> extends
        AutomaticSaveAjaxableFormController<C, Study, StudyDao> {
    protected static final Log log = LogFactory.getLog(StudyController.class);

    // private RowManager rowManager;
    protected StudyService studyService;

    protected StudyDao studyDao;

    protected HealthcareSiteDao healthcareSiteDao;

    protected HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;

    protected ResearchStaffDao researchStaffDao;

    private DiseaseTermDao diseaseTermDao;

    protected StudyValidator studyValidator;

    protected Boolean companionIndicator;

    protected CompanionStudyAssociationDao companionStudyAssociationDao;
    protected static List<HealthcareSite> healthcareSites;

    public StudyController(String title) {
        setCommandClass(StudyWrapper.class);
        Flow<C> flow = new Flow<C>(title);
        layoutTabs(flow);
        setFlow(flow);
    }

    @Override
    protected Study getPrimaryDomainObject(C command) {
        return command.getStudy();
    }

    @Override
    protected StudyDao getDao() {
        return studyDao;
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors)
            throws Exception {
        super.onBind(request, command, errors);
    }
    
    @Override
    protected Object currentFormObject(HttpServletRequest request,
    		Object command) throws Exception {
    	return command;
    }
    
    /**
     * Template method to let the subclass decide the order of tab
     */
    protected abstract void layoutTabs(Flow flow);

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
            throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(false));
        binder.registerCustomEditor(healthcareSiteDao.domainClass(), new CustomDaoEditor(
                healthcareSiteDao));
        binder.registerCustomEditor(healthcareSiteInvestigatorDao.domainClass(),
                new NullIdDaoBasedEditor(healthcareSiteInvestigatorDao));
        binder.registerCustomEditor(researchStaffDao.domainClass(), new NullIdDaoBasedEditor(
                researchStaffDao));
        binder.registerCustomEditor(RandomizationType.class, new EnumByNameEditor(
                RandomizationType.class));
        binder.registerCustomEditor(CoordinatingCenterStudyStatus.class, new EnumByNameEditor(
                CoordinatingCenterStudyStatus.class));
        binder.registerCustomEditor(SiteStudyStatus.class, new EnumByNameEditor(
                SiteStudyStatus.class));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(String.class, "file", new StringMultipartFileEditor());
        binder.registerCustomEditor(byte[].class, "study.criteriaFile",
                new ByteArrayMultipartFileEditor());
        binder.registerCustomEditor(studyDao.domainClass(), new CustomDaoEditor(
                studyDao));
    }

    @Override
    protected String getFormSessionAttributeName(HttpServletRequest httpServletRequest) {
        return super.getFormSessionAttributeName(httpServletRequest);
    }

    /**
     * Override this in sub controller if summary is needed
     *
     * @return
     */
    protected boolean isSummaryEnabled() {
        return false;
    }

    protected Study createDefaultStudyWithDesign() {
        Study study = new Study();
        if (companionIndicator != null) {
            study.setCompanionIndicator(companionIndicator);
            if (!companionIndicator) {
                study.setStandaloneIndicator(true);
            } else {
                study.setStandaloneIndicator(false);
            }
        }
        return study;
    }

    @Override
    protected C save(C command, Errors errors) {
    	StudyWrapper wrapper = (StudyWrapper) command ;
        Study study = studyDao.merge(wrapper.getStudy());
        study.getParentStudyAssociations().size();
        wrapper.setStudy(study);
        return (C) wrapper;
    }
    
    @Override
    protected final boolean isNextPageSavable(HttpServletRequest request, C command, Tab<C> tab) {
    	return true;
    }

    public StudyService getStudyService() {
        return studyService;
    }

    public void setStudyService(StudyService studyService) {
        this.studyService = studyService;
    }

    public StudyDao getStudyDao() {
        return studyDao;
    }

    public void setStudyDao(StudyDao studyDao) {
        this.studyDao = studyDao;
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

    public ResearchStaffDao getResearchStaffDao() {
        return researchStaffDao;
    }

    public void setResearchStaffDao(ResearchStaffDao researchStaffDao) {
        this.researchStaffDao = researchStaffDao;
    }

    public DiseaseTermDao getDiseaseTermDao() {
        return diseaseTermDao;
    }

    public void setDiseaseTermDao(DiseaseTermDao diseaseTermDao) {
        this.diseaseTermDao = diseaseTermDao;
    }

    public StudyValidator getStudyValidator() {
        return studyValidator;
    }

    public void setStudyValidator(StudyValidator studyValidator) {
        this.studyValidator = studyValidator;
    }

    public static List<HealthcareSite> getHealthcareSites() {
        return healthcareSites;
    }

    public static void setHealthcareSites(List<HealthcareSite> healthcareSites) {
        StudyController.healthcareSites = healthcareSites;
    }

    public void setCompanionStudyAssociationDao(
            CompanionStudyAssociationDao companionStudyAssociationDao) {
        this.companionStudyAssociationDao = companionStudyAssociationDao;
    }

    public void setCompanionIndicator(Boolean companionIndicator) {
        this.companionIndicator = companionIndicator;
    }
}