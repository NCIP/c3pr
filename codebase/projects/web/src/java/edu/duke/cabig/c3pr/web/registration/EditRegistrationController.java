package edu.duke.cabig.c3pr.web.registration;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectConsentVersion;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.exception.MultisiteException;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.utils.web.WebUtils;
import edu.duke.cabig.c3pr.web.registration.tabs.AssignArmTab;
import edu.duke.cabig.c3pr.web.registration.tabs.CompanionRegistrationTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EligibilityCriteriaTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EnrollmentDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ReviewSubmitTab;
import edu.duke.cabig.c3pr.web.registration.tabs.StratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * @author Ramakrishna
 * 
 */

public class EditRegistrationController<C extends StudySubjectWrapper> extends RegistrationController<C> {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(EditRegistrationController.class);

	public EditRegistrationController() {
        super("Edit Registration");
    }
    
    @Override
    protected void intializeFlows(Flow flow) {
        flow.addTab(new EnrollmentDetailsTab());
        flow.addTab(new EligibilityCriteriaTab());
        flow.addTab(new StratificationTab());
        flow.addTab(new AssignArmTab());
        flow.addTab(new CompanionRegistrationTab());
        flow.addTab(new ReviewSubmitTab());
        setFlow(flow);
    }
    
    @Override
	protected boolean suppressValidation(HttpServletRequest request,
			Object command, BindException errors) {
    	if(WebUtils.getPreviousPage(request) == -1){
    		return true;
    	}
		if (WebUtils.getPreviousPage(request)== 0){
			return !WebUtils.hasSubmitParameter(request, "_validateForm");
		}
		return super.suppressValidation(request, command, errors);
	}
    
    

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
        StudySubject studySubject = wrapper.getStudySubject();
        
     // remove armNotAvailable request attribute if already present
    	if(request.getAttribute("armNotAvailable")!=null){
        	request.removeAttribute("armNotAvailable");
        }
        
        if(wrapper.getShouldReserve()==null){
        	studySubject=studySubjectRepository.save(studySubject);
        }else if(wrapper.getShouldReserve()){
        	studySubject=studySubjectRepository.reserve(studySubject.getUniqueIdentifier());
        }else if(wrapper.getShouldRegister()){
        	studySubject=studySubjectRepository.register(studySubject.getUniqueIdentifier());
        }else if(wrapper.getShouldTransfer()){
        	try {
        		studySubject=studySubjectRepository.transferSubject(studySubject);
			} catch (MultisiteException e) {
				//eating the multisite error. This error will be shown on the confirmation page.
				logger.error(e);
			}catch (C3PRCodedRuntimeException e) {
				
				// Book exhausted message is non-recoverable. It displays an error on the UI
				if(e.getExceptionCode()==234){
					request.setAttribute("armNotAvailable", true);
					return showPage(request, errors, 5);
				}
			}
        }else if(wrapper.getShouldEnroll()){
        	try {
				studySubject=studySubjectRepository.enroll(studySubject);
        	} catch (MultisiteException e) {
				//eating the multisite error. This error will be shown on the confirmation page.
				logger.error(e);
			}catch (C3PRCodedRuntimeException e) {
				
				// Book exhausted message is non-recoverable. It displays an error on the UI
				if(e.getExceptionCode()==234){
					request.setAttribute("armNotAvailable", true);
					return showPage(request, errors, 5);
				}
			}
        	
        }
        if (logger.isDebugEnabled()) {
            logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        	return new ModelAndView("redirect:confirm?"+ControllerTools.createParameterString(studySubject.getSystemAssignedIdentifiers().get(0)));	
    }
}