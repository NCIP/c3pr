/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
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
 * Controller class to handle the work flow in the Updation of a Study Design This uses
 * AbstractWizardController to implement tabbed workflow
 *
 * @author kherm, Himanshu
 */
public class EditStudyController extends StudyController<StudyWrapper> {

    protected static final Log log = LogFactory.getLog(EditStudyController.class);

    private final String DO_NOT_SAVE = "_doNotSave" ;

    public EditStudyController() {
        super("Edit Study");
        setBindOnNewForm(true);
    }

    public EditStudyController(String s) {
        super(s);
        setBindOnNewForm(true);
    }

    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
        StudyWrapper wrapper = new StudyWrapper();
        Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
        studyDao.initialize(study);
        if (study != null) {
            log.debug("Retrieving Study Details for Id: " + study.getId());
        }
        wrapper.setStudy(study);
        return wrapper;
    }

    @Override
    protected void layoutTabs(Flow flow) {
        boolean editMode = true;
        flow.addTab(new StudyDetailsTab());
        flow.addTab(new StudyConsentTab());
        flow.addTab(new StudyDesignTab());
        flow.addTab(new StudyEligibilityChecklistTab());
        flow.addTab(new StudyStratificationTab());
        flow.addTab(new StudyRandomizationTab());
        flow.addTab(new StudyDiseasesTab());
        flow.addTab(new CompanionStudyTab());
        flow.addTab(new StudyOverviewTab("Summary", "Summary", "study/study_summary_view"));
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object command, Errors e, int page) throws Exception {

    	String softDelete = "false";
        request.setAttribute(FLOW_TYPE, EDIT_STUDY);
        request.setAttribute("editFlow", "true");

        if (((StudyWrapper) command).getStudy().getCoordinatingCenterStudyStatus() != CoordinatingCenterStudyStatus.PENDING) {
            softDelete = "true";
        }



        request.setAttribute("softDelete", softDelete);
        return super.referenceData(request, command, e, page);
    }

    @Override
    protected boolean shouldPersist(HttpServletRequest request, StudyWrapper command, Tab<StudyWrapper> tab) {
    	boolean shouldSave = super.shouldSave(request, command, tab) && StringUtils.isBlank(request.getParameter("_action"));
        if(WebUtils.hasSubmitParameter(request, DO_NOT_SAVE) && StringUtils.equals(request.getParameter(DO_NOT_SAVE), "true")){
            shouldSave = false;
        }
        return shouldSave ;
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
    protected boolean isFormSubmission(HttpServletRequest request) {
    	boolean isFormSumission = super.isFormSubmission(request); 
    	if(isFormSumission && WebUtils.hasSubmitParameter(request, "refreshCommandObject")){
    		try {
				request.getSession().setAttribute(getFormSessionAttributeName(),formBackingObject(request));
			} catch (ServletException e) {
				e.printStackTrace();
			}
    	}
    	return isFormSumission; 
    }

}
