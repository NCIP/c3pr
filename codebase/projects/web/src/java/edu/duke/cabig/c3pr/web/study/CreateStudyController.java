package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.study.tabs.*;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Controller class to handle the work flow in the Creation of a Study Design
 * This uses AbstractWizardController to implement tabbed workflow
 *
 * @author Priyatam
 */
public class CreateStudyController<C extends StudyWrapper> extends StudyController<C> {

    public CreateStudyController() {
        super("Create Study");
        setBindOnNewForm(true);
    }

    private StudyRepository studyRepository;

    /**
     * Create a nested object graph that Create Study Design needs
     *
     * @param request - HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request)
            throws ServletException {
        StudyWrapper wrapper = new StudyWrapper();
        String studyId = request.getParameter("studyId");
        if (!StringUtils.isBlank(studyId)) {
            Study study = studyDao.getById(Integer.parseInt(request
                    .getParameter("studyId")));
            studyDao.initialize(study);
            if (study != null) {
                log.debug("Retrieving Study Details for Id: " + study.getId());
            }
            wrapper.setStudy(study);
            return wrapper;
        } else {
            Study study = createDefaultStudyWithDesign();
            wrapper.setStudy(study);
            return wrapper;
        }
    }

    /**
     * Layout Tabs
     *
     * @param request - flow the Flow object
     */
    protected void layoutTabs(Flow flow) {
        flow.addTab(new StudyDetailsTab());
        flow.addTab(new StudyDesignTab());
        flow.addTab(new StudyEligibilityChecklistTab());
        flow.addTab(new StudyStratificationTab());
        flow.addTab(new StudyRandomizationTab());
        flow.addTab(new StudyDiseasesTab());
        flow.addTab(new CompanionStudyTab());
        flow.addTab(new StudyOverviewTab("Overview", "Overview",
                "study/study_summary_create",false));
    }

    @Override
    protected Map referenceData(HttpServletRequest request, int arg1)
            throws Exception {
        request.setAttribute("flowType", "CREATE_STUDY");
        return super.referenceData(request, arg1);
    }

    protected boolean suppressValidation(HttpServletRequest request,
                                         Object command) {
        if (request.getParameter("_finish") != null
                && request.getParameter("_finish").equals("true")
                && request.getParameter("_activate") != null
                && request.getParameter("_activate").equals("false")) {
            return true;
        }
        return false;
    }

    /*
      * (non-Javadoc)
      *
      * @seeorg.springframework.web.servlet.mvc.AbstractWizardFormController#
      * processFinish (javax.servlet.http.HttpServletRequest,
      * javax.servlet.http.HttpServletResponse, java.lang.Object,
      * org.springframework.validation.BindException)
      */
    @Override
    protected ModelAndView processFinish(HttpServletRequest request,
                                         HttpServletResponse response, Object command, BindException errors)
            throws Exception {
        Study study = ((StudyWrapper) command).getStudy();
        if(request.getParameter("_action").equals("open")){
            study= studyRepository.openStudy(study.getIdentifiers());
        }else if(request.getParameter("_action").equals("create")){
            study=studyRepository.createStudy(study.getIdentifiers());
        }
        ((StudyWrapper) command).setStudy(study);
        response.sendRedirect("confirm?studyId=" + study.getId());
        return null;

    }

    @Override
    protected boolean shouldSave(HttpServletRequest request, C command,
                                 Tab<C> tab) {
        Study study = command.getStudy();
        if (study.getId() == null) {
            boolean shouldSave = !WebUtils.hasSubmitParameter(request,
                    "embeddedStudy");
            return true && shouldSave;
        } else {
            return super.shouldSave(request, command, tab);
        }
    }

    public void setStudyRepository(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    @Override
    protected void postProcessPage(HttpServletRequest request, Object command,
                                   Errors errors, int page) throws Exception {
        Study study = ((StudyWrapper) command).getStudy();
  //      study.setDataEntryStatus(false);
        study
                .setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        for (StudySite studySite : study.getStudySites()) {
            studySite.setSiteStudyStatus(SiteStudyStatus.PENDING);
        }
        super.postProcessPage(request, command, errors, page);

    }

}