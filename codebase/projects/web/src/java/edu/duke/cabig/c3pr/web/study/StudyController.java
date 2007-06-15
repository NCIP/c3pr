package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.dao.*;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.NullIdDaoBasedEditor;
import gov.nih.nci.cabig.ctms.web.tabs.AutomaticSaveFlowFormController;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Base Controller class to handle the basic work flow in the Creation / Updation of a Study Design
 * This uses AbstractTabbedFlowFormController to implement tabbed workflow
 *
 *  @author Priyatam
 * @author kherm
 */
public abstract class StudyController<C extends Study> extends AutomaticSaveFlowFormController<C, Study, StudyDao> {
    protected static final Log log = LogFactory.getLog(StudyController.class);
    protected StudyService studyService;
    protected StudyDao studyDao;
    protected HealthcareSiteDao healthcareSiteDao;
    protected HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    protected ResearchStaffDao researchStaffDao;
    private DiseaseTermDao diseaseTermDao;

    protected StudyValidator studyValidator;
    protected static List<HealthcareSite> healthcareSites;

    public StudyController() {
        setCommandClass(Study.class);
        Flow<C> flow = new Flow<C>("Create Study");
        layoutTabs(flow);
        setFlow(flow);
    }

    @Override
    protected Study getPrimaryDomainObject(C command) {
        return command;
    }

    @Override
    protected StudyDao getDao() {
        return studyDao;
    }

    /**
     * Template method to let the subclass decide the order of tab
     */
    protected abstract void layoutTabs(Flow flow);

    @Override
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);
        binder.registerCustomEditor(Date.class, ControllerTools.getDateEditor(false));
        binder.registerCustomEditor(healthcareSiteDao.domainClass(),
                new CustomDaoEditor(healthcareSiteDao));
        binder.registerCustomEditor(healthcareSiteInvestigatorDao.domainClass(),
                new NullIdDaoBasedEditor(healthcareSiteInvestigatorDao));
        binder.registerCustomEditor(researchStaffDao.domainClass(),
                new NullIdDaoBasedEditor(researchStaffDao));
    }




    /**
     * Override this in sub controller if summary is needed
     *
     * @return
     */
    protected boolean isSummaryEnabled() {
        return false;
    }



    /**
     * Need to create a default study with default associated Collection objects
     * Need this to bind empty collections in Spring <form> tag
     *
     * @return Study with the defaults
     */
    protected Study createDefaultStudyWithDesign() {
        Study study = new Study();

        StudySite studySite = new StudySite();
        study.addStudySite(studySite);

        createDefaultIdentifiers(study);

        return study;
    }


    protected void createDefaultIdentifiers(Study study)
    {
        List<Identifier> identifiers = new ArrayList<Identifier>();
        Identifier id1 = new Identifier();
        id1.setPrimaryIndicator(true);
        Identifier id2 = new Identifier();
        identifiers.add(id1);
        identifiers.add(id2);
        study.setIdentifiers(identifiers);
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

    public void setHealthcareSiteInvestigatorDao(HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao) {
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
}