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
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.navigation.Task;
import edu.duke.cabig.c3pr.web.study.tabs.CompanionStudyTab;
import edu.duke.cabig.c3pr.web.study.tabs.EditStudyOverviewTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyAmendmentTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDesignTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDetailsTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyDiseasesTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyEligibilityChecklistTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyIdentifiersTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyInvestigatorsTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyNotificationTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyPersonnelTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyRandomizationTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudySitesTab;
import edu.duke.cabig.c3pr.web.study.tabs.StudyStratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

public class AmendStudyController extends StudyController<Study> {

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

    public Task getEditTask() {
		return editTask;
	}

	public void setEditTask(Task editTask) {
		this.editTask = editTask;
	}

    @Override
    protected Map referenceData(HttpServletRequest request, int arg1) throws Exception {
        request.setAttribute("flowType", "AMEND_STUDY");
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
        return super.referenceData(request, arg1);
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
        flow.addTab(new StudySitesTab());
        flow.addTab(new StudyIdentifiersTab());
        flow.addTab(new StudyInvestigatorsTab());
        flow.addTab(new StudyPersonnelTab());
        flow.addTab(new StudyNotificationTab());
        flow.addTab(new CompanionStudyTab());
        flow.addTab(new EditStudyOverviewTab("Summary", "Summary", "study/study_summary_view"));
    }

    @Override
    protected void initBinder(HttpServletRequest req, ServletRequestDataBinder binder)
                    throws Exception {
        super.initBinder(req, binder);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(
                        "MM/dd/yyyy"), true));
        binder.registerCustomEditor(Boolean.class, "epochAndArmsIndicator",
                        new CustomBooleanEditor(false));
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        request.getSession().removeAttribute(getReplacedCommandSessionAttributeName(request));
        Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
        studyDao.initialize(study);
        if (study != null) {
            log.debug("Retrieving Study Details for Id: " + study.getId());
        }
        study.getStudyAmendments().size();
        return study;
    }

    @Override
    protected boolean shouldSave(HttpServletRequest request, Study command, Tab<Study> tab) {
    	return super.shouldSave(request, command, tab) && StringUtils.isBlank(request.getParameter("_action"));
    }

    @Override
    protected boolean isSummaryEnabled() {
        return true;
    }

//    @Override
//    protected Object currentFormObject(HttpServletRequest request, Object sessionFormObject)
//                    throws Exception {
//        if (((Study) sessionFormObject).getId() != null) {
//            Study study = studyDao.getById(((Study) sessionFormObject).getId());
//            studyDao.initialize(study);
//            return study;
//        }
//
//        throw new C3PRCodedException(-1, "Unable to retrieve study");
//    }
    
    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
        // Redirect to Search page
        ModelAndView modelAndView = new ModelAndView(new RedirectView("searchStudy"));
        return modelAndView;
    }

    @Override
    protected void postProcessPage(HttpServletRequest request, Object command, Errors errors,
                    int page) throws Exception {
        super.postProcessPage(request, command, errors, page);
    }

}
