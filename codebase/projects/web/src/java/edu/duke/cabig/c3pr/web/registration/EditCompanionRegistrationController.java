package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.web.registration.tabs.AssignArmTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EligibilityCriteriaTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EnrollmentDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ReviewSubmitTab;
import edu.duke.cabig.c3pr.web.registration.tabs.StratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

public class EditCompanionRegistrationController<C extends StudySubjectWrapper> extends EditRegistrationController<C> {

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
        if(studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.REGISTERED_BUT_NOT_ENROLLED || studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.RESERVED){
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
	    }else{
	       	studySubject=studySubjectRepository.save(studySubject);
	    }
        return new ModelAndView("/registration/close_popup");
        
    }

}
