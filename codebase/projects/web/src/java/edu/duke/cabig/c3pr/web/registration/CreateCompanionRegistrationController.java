/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySubjectDemographics;
import edu.duke.cabig.c3pr.exception.C3PRCodedRuntimeException;
import edu.duke.cabig.c3pr.web.registration.tabs.AssignArmTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EligibilityCriteriaTab;
import edu.duke.cabig.c3pr.web.registration.tabs.EnrollmentDetailsTab;
import edu.duke.cabig.c3pr.web.registration.tabs.InformedConsentsTab;
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
        if (logger.isDebugEnabled()) {
            logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        return new ModelAndView("/registration/close_popup");
        
    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    		StudySubjectWrapper wrapper = (StudySubjectWrapper) super.formBackingObject(request);
    		StudySubject studySubject = wrapper.getStudySubject();
    		
    		// for companion registrations, setting the study subject demographics same as the parent 
    		
    		String parentRegistrationId = request.getParameter("parentRegistrationId");
    		
    		StudySubject parentStudySubject = studySubjectDao.getById(Integer.parseInt(parentRegistrationId));
    		
    		studySubject.setStudySubjectDemographics(parentStudySubject.getStudySubjectDemographics());
    		
    		if (!studySubject.getStudySubjectDemographics().getValid()){
    			Participant participant = parentStudySubject.getStudySubjectDemographics().getMasterSubject();
    			participantDao.initialize(participant);
        		wrapper.setParticipant(participant);
        		
        	}else{
        		StudySubjectDemographics studySubjectDemographics = studySubject.getStudySubjectDemographics();
        		studySubjectDemographicsDao.initialize(studySubjectDemographics);
        		wrapper.setParticipant(studySubjectDemographics);
        	}
    		studySubject.setParentStudySubject(parentStudySubject);
    		studySubject.setStartDate(parentStudySubject.getStartDate());
    		studySubject.setPaymentMethod(parentStudySubject.getPaymentMethod());
    		return  wrapper;	
    }
}
