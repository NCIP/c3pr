package edu.duke.cabig.c3pr.web.study;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.duke.cabig.c3pr.domain.Study;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

public class AmendStudyController extends StudyController<Study> {

	
	protected static final Log log = LogFactory.getLog(AmendStudyController.class);

    public AmendStudyController() {
        super("Amend Study");
        setBindOnNewForm(true);
    }
    
    @Override
    protected Map referenceData(HttpServletRequest request, int arg1) throws Exception {
    	// TODO Auto-generated method stub
    	request.setAttribute("flowType", "AMEND_STUDY");
    	request.setAttribute("amendFlow", "true");
    	return super.referenceData(request, arg1);
    }
    
    @Override
    protected void layoutTabs(Flow flow) {
        
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
        flow.addTab(new StudyEmptyTab("Summary", "Summary", "study/study_summary_view"));
    }

    @Override
	protected void initBinder(HttpServletRequest req,
			ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("MM/dd/yyyy"), true));
		binder.registerCustomEditor(Boolean.class, "epochAndArmsIndicator", new CustomBooleanEditor(false));
    }
    
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	request.getSession().removeAttribute(getReplacedCommandSessionAttributeName(request));
        Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
        if (study != null) {
            log.debug("Retrieving Study Details for Id: " + study.getId());
        }
        study.getStudyAmendments().size();
        return study;
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
        }
        return sessionFormObject;
    }
    
    @Override
    protected ModelAndView processFinish(
            HttpServletRequest request, HttpServletResponse response, Object command, BindException errors
    ) throws Exception {
        // Redirect to Search page
        ModelAndView modelAndView = new ModelAndView(new RedirectView("searchStudy"));
        return modelAndView;
    }
    
}
