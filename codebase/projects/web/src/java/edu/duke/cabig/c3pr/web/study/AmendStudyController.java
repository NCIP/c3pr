package edu.duke.cabig.c3pr.web.study;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyVersion;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.navigation.Task;
import edu.duke.cabig.c3pr.web.study.tabs.CompanionStudyTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyAmendmentTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDesignTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDetailsTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDiseasesTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyEligibilityChecklistTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyOverviewTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyRandomizationTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyStratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

public class AmendStudyController extends StudyController<StudyWrapper> {

    protected static final Log log = LogFactory.getLog(AmendStudyController.class);

    public AmendStudyController() {
        super("Amend Study");
        setBindOnNewForm(true);
    }

    public AmendStudyController(String s) {
        super(s);
        setBindOnNewForm(true);
    }

    private Task editTask;
    private StudyRepository studyRepository;

    public Task getEditTask() {
        return editTask;
    }

    public void setEditTask(Task editTask) {
        this.editTask = editTask;
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors e, int page) throws Exception {
        request.setAttribute(FLOW_TYPE, AMEND_STUDY);
        request.setAttribute("amendFlow", "true");

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        GrantedAuthority[] groups = auth.getAuthorities();
        String isAdmin = "false";
        for (GrantedAuthority ga : groups) {
            if (ga.getAuthority().endsWith("admin")) {
                isAdmin = "true";
            }
        }

        request.setAttribute("softDelete", "true");
        request.setAttribute("editAuthorizationTask", editTask);
        request.setAttribute("isAdmin", isAdmin);
        return super.referenceData(request,command, e , page);
    }

    @Override
    protected void layoutTabs(Flow flow) {
        boolean editMode = false;
        flow.addTab(new StudyAmendmentTab());
        flow.addTab(new StudyDetailsTab());
        flow.addTab(new StudyDesignTab());
        flow.addTab(new StudyEligibilityChecklistTab());
        flow.addTab(new StudyStratificationTab());
        flow.addTab(new StudyRandomizationTab());
        flow.addTab(new StudyDiseasesTab());
        flow.addTab(new CompanionStudyTab());
        flow.addTab(new StudyOverviewTab("Summary", "Summary", "study/study_summary_view"));
    }

    @Override
    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(req, binder);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("MM/dd/yyyy"), true));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        StudyWrapper wrapper = new StudyWrapper();
        request.getSession().removeAttribute(getReplacedCommandSessionAttributeName(request));
        Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
        studyDao.initialize(study);
        if (study != null) {
            log.debug("Retrieving Study Details for Id: " + study.getId());
        }
        wrapper.setStudy(study);
        boolean resumeAmendment = wrapper.resumeAmendment();
        if(!resumeAmendment){
        	studyRepository.createAmendment(study.getIdentifiers());
        }
        request.setAttribute("resumeAmendment", resumeAmendment);
        return wrapper;
    }

    @Override
    protected boolean shouldPersist(HttpServletRequest request, StudyWrapper command, Tab<StudyWrapper> tab) {
        return super.shouldSave(request, command, tab) && StringUtils.isBlank(request.getParameter("_action"));
    }

    @Override
    protected boolean isSummaryEnabled() {
        return true;
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
        ModelAndView modelAndView = new ModelAndView(new RedirectView("searchStudy"));
        return modelAndView;
    }

    @Override
    protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
        super.postProcessPage(request, command, errors, page);
    }

	public void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	public StudyRepository getStudyRepository() {
		return studyRepository;
	}

}
