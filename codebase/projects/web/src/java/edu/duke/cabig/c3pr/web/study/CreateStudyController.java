package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

/**
 * Controller class to handle the work flow in the Creation of a Study Design This uses
 * AbstractWizardController to implement tabbed workflow
 * 
 * @author Priyatam
 */
public class CreateStudyController<C extends Study> extends StudyController<C> {
	
    public CreateStudyController() {
        super("Create Study");
        setBindOnNewForm(true);
    }
    
    private StudyRepository studyRepository;

    /**
     * Create a nested object graph that Create Study Design needs
     * 
     * @param request -
     *            HttpServletRequest
     * @throws ServletException
     */
    protected Object formBackingObject(HttpServletRequest request) throws ServletException {
    	String studyId = request.getParameter("studyId") ; 
    	if(!StringUtils.isBlank(studyId)){
    		Study study = studyDao.getById(Integer.parseInt(request.getParameter("studyId")));
            studyDao.initialize(study);
            if (study != null) {
                log.debug("Retrieving Study Details for Id: " + study.getId());
            }
            return study;
    	}else{
    		return createDefaultStudyWithDesign();	
    	}
    }

    /**
     * Layout Tabs
     * 
     * @param request -
     *            flow the Flow object
     */
    protected void layoutTabs(Flow flow) {
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
        flow.addTab(new StudyOverviewTab("Overview", "Overview", "study/study_summary_create"));
    }

    @Override
    protected Map referenceData(HttpServletRequest request, int arg1) throws Exception {
        request.setAttribute("flowType", "CREATE_STUDY");
        return super.referenceData(request, arg1);
    }

    @Override
    protected boolean suppressValidation(HttpServletRequest request, Object study) {
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
     * @see org.springframework.web.servlet.mvc.AbstractWizardFormController#processFinish
     *      (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
     *      java.lang.Object, org.springframework.validation.BindException)
     */
    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
        Study study = (Study) command;
        study = studyRepository.merge(study);
        response.sendRedirect("confirm?studyId="+study.getId());
        return null;

    }
    
    protected boolean shouldSave(HttpServletRequest request, C command, Tab<C> tab) {
    	Study study = (Study) command ;
    	if(study.getId() == null){
    		return true;
    	}else{
    		return super.shouldSave(request, command, tab);
    	}
    }

	public void setStudyRepository(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}
	
	

}