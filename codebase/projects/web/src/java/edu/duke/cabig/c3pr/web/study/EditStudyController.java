package edu.duke.cabig.c3pr.web.study;

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
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.utils.web.navigation.Task;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * Controller class to handle the work flow in the Updation of a Study Design This uses
 * AbstractWizardController to implement tabbed workflow
 * 
 * @author kherm
 */
public class EditStudyController extends StudyController<Study> {

    protected static final Log log = LogFactory.getLog(EditStudyController.class);
    private Task editTask;

    public Task getEditTask() {
		return editTask;
	}

	public void setEditTask(Task editTask) {
		this.editTask = editTask;
	}

	public EditStudyController() {
        super("Edit Study");
        setBindOnNewForm(true);
    }

    public EditStudyController(String s) {
        super(s);
        setBindOnNewForm(true);
    }

    /**
     * Create a nested object graph that Create Study Design needs
     * 
     * @param request -
     *            HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
        if (study != null) {
            log.debug("Retrieving Study Details for Id: " + study.getId());
        }
        return study;
    }

    @Override
    protected void layoutTabs(Flow flow) {
        boolean editMode = true;
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
        flow.addTab(new StudyOverviewTab("Summary", "Summary", "study/study_summary_view"));
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object o, Errors e, int arg1)
                    throws Exception {
        // TODO Auto-generated method stub

    	String softDelete = "false";
        request.setAttribute("flowType", "EDIT_STUDY");
        request.setAttribute("editFlow", "true");

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        GrantedAuthority[] groups = auth.getAuthorities();
        String isAdmin = "false";
        for (GrantedAuthority ga : groups) {
            if (ga.getAuthority().endsWith("admin")) {
                isAdmin = "true";
            }
        }

        if (((Study) o).getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.PENDING) {
            softDelete = "true";
        }
        
        request.setAttribute("editAuthorizationTask", editTask);
        
        
        request.setAttribute("softDelete", softDelete);
        request.setAttribute("isAdmin", isAdmin);
        return super.referenceData(request, o, e, arg1);
    }

    @Override
    protected boolean shouldSave(HttpServletRequest request, Study command, Tab<Study> tab) {
        return super.shouldSave(request, command, tab)
                        && (request.getParameter("_action") == null || "".equals(request
                                        .getParameter("_action")));
    }

    @Override
    protected boolean isSummaryEnabled() {
        return true;
    }

    @Override
    protected Object currentFormObject(HttpServletRequest request, Object sessionFormObject)
                    throws Exception {
        if (((Study) sessionFormObject).getId() != null) {
            Study study = studyDao.getById(((Study) sessionFormObject).getId());
            studyDao.initialize(study);
            return study;
        }

        throw new C3PRCodedException(-1, "Unable to retrieve study");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
     *      (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     *      java.lang.Object, org.springframework.validation.BindException)
     */
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
        ((Study) (command)).setDataEntryStatus( false);
    }

}