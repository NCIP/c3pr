package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.service.impl.StudySubjectServiceImpl;
import edu.duke.cabig.c3pr.web.registration.tabs.DiseasesDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EligibilityCriteriaTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EnrollmentDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.RandomizationTab;
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

	public CreateRegistrationController() {
		super("Create Registration");
	}

	@Override
	protected boolean isFormSubmission(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if(WebUtils.hasSubmitParameter(request, "registrationId")){
			if(request.getSession(false).getAttribute(getFormSessionAttributeName())==null){
				try {
					request.getSession(false).setAttribute(getFormSessionAttributeName(),formBackingObject(request));
					return true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return super.isFormSubmission(request);
	}

	@Override
	protected void intializeFlows(Flow flow) {
		flow.addTab(new SearchStudySubjectTab());
		flow.addTab(new EnrollmentDetailsTab());
//		flow.addTab(new DiseasesDetailsTab());
		flow.addTab(new EligibilityCriteriaTab());
		flow.addTab(new StratificationTab());
		flow.addTab(new RandomizationTab());
		flow.addTab(new ReviewSubmitTab());
		setFlow(flow);
	}

	@Override
	protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
		// TODO Auto-generated method stub
		StudySubject studySubject=(StudySubject)command;
		super.postProcessPage(request, command, errors, page);
		if(request.getAttribute("alreadyRegistered")!=null&&!(Boolean)request.getAttribute("alreadyRegistered"))
			studySubject.setRegistrationStatus(StudySubjectServiceImpl.evaluateStatus(studySubject));
	}
	
	@Override
	protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		// TODO Auto-generated method stub
		StudySubject studySubject = (StudySubject) command;
		studySubject=studySubjectService.createRegistration(studySubject);
		if (logger.isDebugEnabled()) {
			logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
		}
		request.setAttribute("command", command);
		RequestDispatcher rd = request.getRequestDispatcher("confirm?type=confirm");
    	rd.forward(request, response);
    	return null;

	}
}