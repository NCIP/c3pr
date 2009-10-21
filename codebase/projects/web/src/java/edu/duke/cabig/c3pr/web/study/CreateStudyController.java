package edu.duke.cabig.c3pr.web.study;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.web.study.tabs.CompanionStudyTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyConsentTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDesignTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDetailsTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDiseasesTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyEligibilityChecklistTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyOverviewTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyRandomizationTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyStratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * Controller class to handle the work flow in the Creation of a Study Design
 * This uses AbstractWizardController to implement tabbed workflow
 *
 * @author Priyatam, Himanshu
 */
public class CreateStudyController<C extends StudyWrapper> extends StudyController<C> {

    public CreateStudyController() {
        super("Create Study");
        setBindOnNewForm(true);
    }

    private StudyRepository studyRepository;
    private final String DO_NOT_SAVE = "_doNotSave" ;

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        StudyWrapper wrapper = new StudyWrapper();
        String studyId = request.getParameter("studyId");
        if (!StringUtils.isBlank(studyId)) {
            Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
            studyDao.initialize(study);
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
        flow.addTab(new StudyConsentTab());
        flow.addTab(new StudyDesignTab());
        flow.addTab(new StudyEligibilityChecklistTab());
        flow.addTab(new StudyStratificationTab());
        flow.addTab(new StudyRandomizationTab());
        flow.addTab(new StudyDiseasesTab());
        flow.addTab(new CompanionStudyTab());
        flow.addTab(new StudyOverviewTab("Overview", "Overview", "study/study_summary_create",false));
    }

    @Override
    protected Map referenceData(HttpServletRequest request, int page) throws Exception {
        request.setAttribute(FLOW_TYPE, CREATE_STUDY);
        return super.referenceData(request, page);
    }

    protected boolean suppressValidation(HttpServletRequest request, Object command) {
        if (request.getParameter("_finish") != null
                && request.getParameter("_finish").equals("true")
                && request.getParameter("_activate") != null
                && request.getParameter("_activate").equals("false")) {
            return true;
        }
        return false;
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        Study study = ((StudyWrapper) command).getStudy();
        if(request.getParameter("_action").equals("open")){
        	/*
        	 * TODO: Adding a temporary fix to capture open date. Need to remove this.
        	 */
        	Date versionDate = study.getStudyVersion().getVersionDate();
        	study = studyDao.getById(study.getId());
        	study.getStudyVersion().setVersionDate(versionDate);
        	studyRepository.merge(study);
        	
            study= studyRepository.openStudy(study.getIdentifiers());
        }else if(request.getParameter("_action").equals("create")){
            study=studyRepository.createStudy(study.getIdentifiers());
        }
        ((StudyWrapper) command).setStudy(study);
        response.sendRedirect("confirm?studyId=" + study.getId());
        return null;
    }

    @Override
    protected boolean shouldPersist(HttpServletRequest request, C command, Tab<C> tab) {
    	Study study = command.getStudy();
		if (WebUtils.hasSubmitParameter(request, DO_NOT_SAVE) && StringUtils.equals(request.getParameter(DO_NOT_SAVE), "true")) {
			return false;
		}
		if (study.getId() == null) {
			boolean shouldSave = !WebUtils.hasSubmitParameter(request, "embeddedStudy");
			return true && shouldSave;
		} else {
			return super.shouldSave(request, command, tab);
		}
	}

    public void setStudyRepository(StudyRepository studyRepository) {
        this.studyRepository = studyRepository;
    }

    @Override
    protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
        Study study = ((StudyWrapper) command).getStudy();
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        super.postProcessPage(request, command, errors, page);
    }
}