package edu.duke.cabig.c3pr.web.registration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.tabs.AssignArmTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EligibilityCriteriaTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EnrollmentDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ReviewSubmitTab;
import edu.duke.cabig.c3pr.web.registration.tabs.SearchStudySubjectTab;
import edu.duke.cabig.c3pr.web.registration.tabs.StratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * @author Ramakrishna
 * 
 */

public class CreateRegistrationController<C extends StudySubjectWrapper> extends RegistrationController<C> {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CreateRegistrationController.class);

    private RegistrationControllerUtils registrationControllerUtils;
    public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}

	public CreateRegistrationController() {
        super("Create Registration");
    }

    @Override
    protected boolean isFormSubmission(HttpServletRequest request) {
    	if(WebUtils.hasSubmitParameter(request, "studySubject.studySite") && WebUtils.hasSubmitParameter(request, "studySubject.participant") && WebUtils.hasSubmitParameter(request, "studySubject.parentRegistrationId") && WebUtils.hasSubmitParameter(request, "create_companion")){
    		return false;
    	}
        return super.isFormSubmission(request);
    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	if(WebUtils.hasSubmitParameter(request, "studySubject.studySite") && WebUtils.hasSubmitParameter(request, "studySubject.participant") && WebUtils.hasSubmitParameter(request, "parentRegistrationId") && WebUtils.hasSubmitParameter(request, "create_companion")){
    		StudySubjectWrapper wrapper = new StudySubjectWrapper() ;
    		StudySubject studySubject = new StudySubject();
    		studySubject.setParentStudySubject(studySubjectDao.getById(Integer.parseInt(request.getParameter("parentRegistrationId")), true));
    		studySubjectDao.initialize(studySubject.getParentStudySubject());
    		wrapper.setStudySubject(studySubject) ;
    		return  wrapper;	
    	}else{
    		return super.formBackingObject(request);
    	}
    }
    
    @Override
    protected void intializeFlows(Flow flow) {
        flow.addTab(new SearchStudySubjectTab());
        flow.addTab(new EnrollmentDetailsTab());
        flow.addTab(new EligibilityCriteriaTab());
        flow.addTab(new StratificationTab());
        flow.addTab(new AssignArmTab());
        flow.addTab(new ReviewSubmitTab());
        setFlow(flow);
    }

    @Override
    protected void postProcessPage(HttpServletRequest request, Object command, Errors errors,
                    int page) throws Exception {
        // TODO Auto-generated method stub
    	super.postProcessPage(request, command, errors, page);
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
        StudySubject studySubject = wrapper.getStudySubject();
        if (studySubject.getScheduledEpoch() != null) {
            studySubject.updateDataEntryStatus();
        }
        wrapper.setStudySubject(studySubject);
    }
    
    @Override
    protected Map<String, Object> referenceData(HttpServletRequest httpServletRequest, int page)
                    throws Exception {
        // Currently the static data is a hack, once DB design is approved for
        // an LOV this will be
        // replaced with LOVDao to get the static data from individual tables
        Map<String, Object> refdata = new HashMap<String, Object>();
        Map configMap = configurationProperty.getMap();
        refdata.put("paymentMethods", configMap.get("paymentMethods"));
        return refdata;
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
        StudySubject studySubject = wrapper.getStudySubject();
        if(registrationControllerUtils.isRegisterableOnPage(studySubject))
        	studySubject = studySubjectService.register(studySubject);
        else{
            registrationControllerUtils.updateStatusForEmbeddedStudySubjet(studySubject);
            studySubject=studySubjectRepository.save(studySubject);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        if(WebUtils.hasSubmitParameter(request, "decorator") && "noheaderDecorator".equals(request.getParameter("decorator"))){
        	 return new ModelAndView("redirect:confirm?registrationId=" + studySubject.getId() +"&decorator=" + request.getParameter("decorator"));
        }else{
        	return new ModelAndView("redirect:confirm?registrationId=" + studySubject.getId());	
        }
    }
    
}