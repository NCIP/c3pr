/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.constants.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
import edu.duke.cabig.c3pr.web.registration.tabs.AssignArmTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EligibilityCriteriaTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EnrollmentDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.InformedConsentsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.ReviewSubmitTab;
import edu.duke.cabig.c3pr.web.registration.tabs.StratificationTab;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;

public class EditCompanionRegistrationController<C extends StudySubjectWrapper> extends EditRegistrationController<C> {

	@Override
    protected void intializeFlows(Flow flow) {
		flow.addTab(new InformedConsentsTab());
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
        if(!(studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.PENDING_ON_STUDY || studySubject.getRegWorkflowStatus() == RegistrationWorkFlowStatus.RESERVED)){
	        if(wrapper.getShouldReserve()){
	        	studySubject=studySubjectRepository.reserve(studySubject.getUniqueIdentifier());
	        }else if(wrapper.getShouldRegister() ||(wrapper.getShouldEnroll() && wrapper.getShouldRandomize()) ){
	        	studySubject=studySubjectRepository.register(studySubject.getUniqueIdentifier());
	        }else if(wrapper.getShouldEnroll() && !wrapper.getShouldRandomize()){
	        	try{
	        		studySubject=studySubjectRepository.enroll(studySubject.getUniqueIdentifier());
	        	}catch (C3PRCodedRuntimeException e) {
				
	        	}
	        }else{
	        	studySubject=studySubjectRepository.save(studySubject);
	        }
	    }else{
	       	studySubject=studySubjectRepository.save(studySubject);
	    }
        if(WebUtils.hasSubmitParameter(request, "companionRegistration")){
        	return new ModelAndView("/registration/close_popup");	
        }else{
        	return new ModelAndView("redirect:confirm?"+ControllerTools.createParameterString(studySubject.getSystemAssignedIdentifiers().get(0)));
        }
        
        
    }

}
