package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.domain.Study;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller class to handle the work flow in the Creation of a Study Design
 * This uses AbstractWizardController to implement tabbed workflow
 *
 * @author Priyatam
 */
public class CreateStudyController extends StudyController {


    /**
     * Create a nested object graph that Create Study Design needs
     *
     * @param request - HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        return createDefaultStudyWithDesign();
    }

    /**
     * Layout Tabs
     *
     * @param request - flow the Flow object
     */
    protected void layoutTabs(Flow flow) {
        flow.addTab(new StudyDetailsTab());
        flow.addTab(new StudyIdentifiersTab());
        flow.addTab(new StudySitesTab());
        flow.addTab(new StudyInvestigatorsTab());
        flow.addTab(new StudyPersonnelTab());
        flow.addTab(new StudyEligibilityChecklistTab());
        flow.addTab(new StudyDiseasesTab());
        flow.addTab(new StudyStratificationTab());
        flow.addTab(new StudyDesignTab());
        flow.addTab(new StudyEmptyTab("Overview", "Overview", "study/study_reviewsummary"));
    }


    /* (non-Javadoc)
      * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
      * (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
      * java.lang.Object, org.springframework.validation.BindException)
      */
    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                                         Object command, BindException errors) throws Exception {
        Study study = (Study) command;

        studyService.save(study);

        return new ModelAndView("forward:confirm?type=confirm", errors.getModel());

    }

}