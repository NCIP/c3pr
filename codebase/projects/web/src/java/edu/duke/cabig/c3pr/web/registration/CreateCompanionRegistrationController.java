package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.web.registration.tabs.AssignArmTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EligibilityCriteriaTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EnrollmentDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ReviewSubmitTab;
import edu.duke.cabig.c3pr.web.registration.tabs.SelectStudySiteAndEpochTab;
import edu.duke.cabig.c3pr.web.registration.tabs.StratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

/**
 * @author Ramakrishna
 * 
 */

public class CreateCompanionRegistrationController<C extends StudySubjectWrapper> extends RegistrationController<C> {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(CreateCompanionRegistrationController.class);

	public CreateCompanionRegistrationController() {
        super("Create Registration");
    }

    @Override
    protected void intializeFlows(Flow flow) {
    	flow.addTab(new SelectStudySiteAndEpochTab());
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
        if(wrapper.getShouldReserve()){
        	studySubject=studySubjectRepository.reserve(studySubject.getIdentifiers());
        }else if(wrapper.getShouldRegister() ||(wrapper.getShouldEnroll() && wrapper.getShouldRandomize()) ){
        	studySubject=studySubjectRepository.register(studySubject.getIdentifiers());
        }else if(wrapper.getShouldEnroll() && !wrapper.getShouldRandomize()){
        	try{
        		studySubject=studySubjectRepository.enroll(studySubject.getIdentifiers());
        	}catch (C3PRCodedRuntimeException e) {
			
        	}
        }else{
        	studySubject=studySubjectRepository.save(studySubject);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        return new ModelAndView("/registration/close_popup");
        
    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    		StudySubjectWrapper wrapper = (StudySubjectWrapper) super.formBackingObject(request);
    		StudySubject studySubject = wrapper.getStudySubject();
    		
    		String participantId = request.getParameter("participant");
    		String parentRegistrationId = request.getParameter("parentRegistrationId");
    		
    		Participant participant = participantDao.getById(Integer.parseInt(participantId));
    		StudySubject parentStudySubject = studySubjectDao.getById(Integer.parseInt(parentRegistrationId));
    		
    		studySubject.setParticipant(participant);
    		studySubject.setParentStudySubject(parentStudySubject);

    		return  wrapper;	
    }
}