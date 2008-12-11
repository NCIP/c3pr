package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.tabs.AssignArmTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EligibilityCriteriaTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EnrollmentDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ReviewSubmitTab;
import edu.duke.cabig.c3pr.web.registration.tabs.StratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * @author Ramakrishna
 * 
 */

public class CompanionRegistrationController<C extends StudySubjectWrapper> extends RegistrationController<C> {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CompanionRegistrationController.class);

	public CompanionRegistrationController() {
        super("Create Registration");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    	if(WebUtils.hasSubmitParameter(request, "studySite") && WebUtils.hasSubmitParameter(request, "participant") && WebUtils.hasSubmitParameter(request, "parentRegistrationId") && WebUtils.hasSubmitParameter(request, "create_companion")){
    		StudySubjectWrapper wrapper = (StudySubjectWrapper)super.formBackingObject(request);
    		wrapper.getStudySubject().setParentStudySubject(studySubjectDao.getById(Integer.parseInt(request.getParameter("parentRegistrationId")), true));
    		studySubjectDao.initialize(wrapper.getStudySubject().getParentStudySubject());
    		return  wrapper;	
    	}else{
    		throw new RuntimeException("Cannot create command object instance");
    	}
    }
    
    @Override
    protected void intializeFlows(Flow flow) {
        flow.addTab(new EnrollmentDetailsTab());
        flow.addTab(new EligibilityCriteriaTab());
        flow.addTab(new StratificationTab());
        flow.addTab(new AssignArmTab());
        flow.addTab(new ReviewSubmitTab());
        setFlow(flow);
    }

    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
        StudySubject studySubject = wrapper.getStudySubject();
//        if(registrationControllerUtils.isRegisterableOnPage(studySubject))
//        	studySubject = studySubjectService.register(studySubject);
//        else{
//            registrationControllerUtils.updateStatusForEmbeddedStudySubjet(studySubject);
//            studySubject=studySubjectRepository.save(studySubject);
//        }
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