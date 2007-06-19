package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.web.propertyeditors.ObjectGraphBasedEditor;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


    /**
     * Create a nested object graph that Create Study Design needs
     *
     * @param request - HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        Study study = studyDao.getStudyDesignById(Integer.parseInt(request.getParameter("studyId")));
        if (study != null) {
            log.debug("Retrieving Study Details for Id: " + study.getId());
        }
        return study;
    }

    @Override
    protected void layoutTabs(Flow flow) {
        flow.addTab(new StudyDetailsTab());
        flow.addTab(new StudyIdentifiersTab());
        flow.addTab(new StudySitesTab());
        flow.addTab(new StudyInvestigatorsTab());
        flow.addTab(new StudyPersonnelTab());
        flow.addTab(new StudyEligibilityChecklistTab());
        flow.addTab(new StudyStratificationTab());
        flow.addTab(new StudyDiseasesTab());
        flow.addTab(new StudyDesignTab());
        flow.addTab(new StudyEmptyTab("Summary", "Summary", "study/study_view_summary"));
    }

    
    @Override
    protected boolean shouldSave(HttpServletRequest request, Study command, Tab<Study> tab) {
        return super.shouldSave(request, command, tab)
            && (request.getParameter("_action") == null || "".equals(request.getParameter("_action")));
    }

    

    @Override
    protected Object currentFormObject(HttpServletRequest request, Object sessionFormObject) throws Exception {
        if (sessionFormObject != null) {
            getDao().reassociate((Study) sessionFormObject);
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


}