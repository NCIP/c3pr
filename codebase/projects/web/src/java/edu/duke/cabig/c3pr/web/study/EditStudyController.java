package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.service.StudyService;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * Controller class to handle the work flow in the Updation of a Study Design
 * This uses AbstractWizardController to implement tabbed workflow
 *
 * @author kherm
 */
public class EditStudyController extends StudyController<Study> {

    protected static final Log log = LogFactory.getLog(EditStudyController.class);

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
     * @param request - HttpServletRequest
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
        flow.addTab(new StudyEmptyTab("Summary", "Summary", "study/study_summary_view"));
    }

//    @Override
//    protected Map referenceData(HttpServletRequest request, int arg1) throws Exception {
//    	// TODO Auto-generated method stub
//    	request.setAttribute("flowType", "EDIT_STUDY");
//    	request.setAttribute("editFlow", "true");
//    	return super.referenceData(request, arg1);
//    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object o, Errors e, int arg1) throws Exception {
    	// TODO Auto-generated method stub
    	
    	String softDelete = "false";
    	request.setAttribute("flowType", "EDIT_STUDY");
    	request.setAttribute("editFlow", "true");
    	
    	if(((Study)o).getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.PENDING){
    		softDelete = "true";
    	}
    	request.setAttribute("softDelete", softDelete);
    	return super.referenceData(request,  o,  e, arg1);
    }
    
    @Override
    protected boolean shouldSave(HttpServletRequest request, Study command, Tab<Study> tab) {
        return super.shouldSave(request, command, tab)
                && (request.getParameter("_action") == null || "".equals(request.getParameter("_action")));
    }


    @Override
    protected boolean isSummaryEnabled() {
        return true;
    }

    @Override
    protected Object currentFormObject(HttpServletRequest request, Object sessionFormObject) throws Exception {
        if (sessionFormObject != null) {
            getDao().reassociate((Study) sessionFormObject);
            getDao().refresh((Study) sessionFormObject);
        }

        return sessionFormObject;
    }

    /* (non-Javadoc)
      * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
      * (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
      * java.lang.Object, org.springframework.validation.BindException)
      */
    @Override
    protected ModelAndView processFinish(
            HttpServletRequest request, HttpServletResponse response, Object command, BindException errors
    ) throws Exception {
        // Redirect to Search page
        ModelAndView modelAndView = new ModelAndView(new RedirectView("searchStudy"));
        return modelAndView;
    }

	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		// TODO Auto-generated method stub
		super.postProcessPage(request, command, errors, page);
		studyService.setDataEntryStatus((Study)command, false);
	}

}