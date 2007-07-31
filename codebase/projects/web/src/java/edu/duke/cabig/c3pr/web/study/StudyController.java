package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.dao.*;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.RandomizationType;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.validator.StudyValidator;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.RowManager;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.CustomDaoEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.EnumByNameEditor;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.NullIdDaoBasedEditor;
import edu.duke.cabig.c3pr.web.beans.DefaultObjectPropertyReader;
import gov.nih.nci.cabig.ctms.web.tabs.AutomaticSaveFlowFormController;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Base Controller class to handle the basic work flow in the Creation / Updation of a Study Design
 * This uses AbstractTabbedFlowFormController to implement tabbed workflow
 *
 * @author Priyatam
 * @author kherm
 */
public abstract class StudyController<C extends Study> extends AutomaticSaveFlowFormController<C, Study, StudyDao> {
    protected static final Log log = LogFactory.getLog(StudyController.class);
    private RowManager rowManager;
    protected StudyService studyService;
    protected StudyDao studyDao;
    protected HealthcareSiteDao healthcareSiteDao;
    protected HealthcareSiteInvestigatorDao healthcareSiteInvestigatorDao;
    protected ResearchStaffDao researchStaffDao;
    private DiseaseTermDao diseaseTermDao;

    protected StudyValidator studyValidator;
    protected static List<HealthcareSite> healthcareSites;

    public StudyController(String title) {
        setCommandClass(Study.class);
        Flow<C> flow = new Flow<C>(title);
        layoutTabs(flow);
        setFlow(flow);
        rowManager = new RowManager();
    }

    @Override
    protected Study getPrimaryDomainObject(C command) {
        return command;
    }

    @Override
    protected StudyDao getDao() {
        return studyDao;
    }

    @Override
    protected void onBind(HttpServletRequest request, Object command, BindException errors) throws Exception {
        // TODO Auto-generated method stub
        super.onBind(request, command, errors);
//        handleRowDeletion(request, command);
        rowManager.handleRowDeletion(request, command);
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
        binder.registerCustomEditor(RandomizationType.class, new EnumByNameEditor(RandomizationType.class));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class,true));
    }

    @Override
    protected String getFormSessionAttributeName(HttpServletRequest httpServletRequest) {
        return super.getFormSessionAttributeName(httpServletRequest);    //To change body of overridden methods use File | Settings | File Templates.
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
        return new Study();
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