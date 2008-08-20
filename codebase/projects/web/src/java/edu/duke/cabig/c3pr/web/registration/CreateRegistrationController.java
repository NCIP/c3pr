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

public class CreateRegistrationController<C extends StudySubject> extends RegistrationController<C> {
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
    	if(WebUtils.hasSubmitParameter(request, "studySite") && WebUtils.hasSubmitParameter(request, "participant") && WebUtils.hasSubmitParameter(request, "parentRegistrationId") && WebUtils.hasSubmitParameter(request, "create_companion")){
    		return false;
    	}
        if (WebUtils.hasSubmitParameter(request, "registrationId")) {
            if (request.getSession(false).getAttribute(getFormSessionAttributeName()) == null) {
                try {
                    request.getSession(false).setAttribute(getFormSessionAttributeName(),
                                    formBackingObject(request));
                    return true;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.isFormSubmission(request);
    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	if(WebUtils.hasSubmitParameter(request, "studySite") && WebUtils.hasSubmitParameter(request, "participant") && WebUtils.hasSubmitParameter(request, "parentRegistrationId") && WebUtils.hasSubmitParameter(request, "create_companion")){
    		StudySubject studySubject = new StudySubject();
    		studySubject.setParentStudySubject(studySubjectDao.getById(Integer.parseInt(request.getParameter("parentRegistrationId")), true));
    		studySubjectDao.initialize(studySubject.getParentStudySubject());
    		return studySubject ;	
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
        StudySubject studySubject = (StudySubject) command;
        super.postProcessPage(request, command, errors, page);
        if (studySubject.getScheduledEpoch() != null) {
            studySubject.updateDataEntryStatus();
        }
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
        StudySubject studySubject = (StudySubject) command;
        if(registrationControllerUtils.isRegisterableOnPage(studySubject))
        	studySubject = studySubjectService.register(studySubject);
        else{
            registrationControllerUtils.updateStatusForEmbeddedStudySubjet(studySubject);
            studySubject=studySubjectRepository.save(studySubject);
        }
        if (logger.isDebugEnabled()) {
            logger
                            .debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        return new ModelAndView("redirect:confirm?registrationId=" + studySubject.getId());
    }
    
}