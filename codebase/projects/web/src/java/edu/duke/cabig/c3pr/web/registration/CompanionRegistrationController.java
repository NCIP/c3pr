package edu.duke.cabig.c3pr.web.registration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
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
    protected void intializeFlows(Flow flow) {
        flow.addTab(new EnrollmentDetailsTab());
        flow.addTab(new EligibilityCriteriaTab());
        flow.addTab(new StratificationTab());
        flow.addTab(new AssignArmTab());
        flow.addTab(new ReviewSubmitTab());
        setFlow(flow);
    }
    
//    @Override
//    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
//    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
//        StudySubject studySubject = wrapper.getStudySubject();
//        if (logger.isDebugEnabled()) {
//            logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
//        }
//        if(WebUtils.hasSubmitParameter(request, "decorator") && "noheaderDecorator".equals(request.getParameter("decorator"))){
//        	 return new ModelAndView("redirect:confirm?registrationId=" + studySubject.getId() +"&decorator=" + request.getParameter("decorator"));
//        }else{
//        	return new ModelAndView("redirect:confirm?registrationId=" + studySubject.getId());	
//        }
//    }
    
    @Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
        StudySubject studySubject = wrapper.getStudySubject();
        if(wrapper.getShouldReserve()==null){
        	studySubject=studySubjectRepository.save(studySubject);
        }else if(wrapper.getShouldReserve()){
        	studySubject=studySubjectRepository.reserve(studySubject.getIdentifiers());
        }else if(wrapper.getShouldRegister() ||(wrapper.getShouldEnroll() && wrapper.getShouldRandomize()) ){
        	studySubject=studySubjectRepository.register(studySubject.getIdentifiers());
        }else if(wrapper.getShouldEnroll() && !wrapper.getShouldRandomize()){
        	studySubject=studySubjectRepository.enroll(studySubject.getIdentifiers());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        return new ModelAndView("redirect:confirm?"+ControllerTools.createParameterString(studySubject.getSystemAssignedIdentifiers().get(0)));	
    }
    
    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
    		StudySubjectWrapper wrapper = (StudySubjectWrapper) super.formBackingObject(request);
    		StudySubject studySubject = wrapper.getStudySubject();
    		
    		String participantId = request.getParameter("studySubject.participant");
    		String studySiteId = request.getParameter("studySubject.studySite");
    		String parentRegistrationId = request.getParameter("parentRegistrationId");
    		String epochId = request.getParameter("epoch");
    		
    		Participant participant = participantDao.getById(Integer.parseInt(participantId));
    		StudySite studySite =  studySiteDao.getById(Integer.parseInt(studySiteId));
    		Epoch epoch = epochDao.getById(Integer.parseInt(epochId));
    		StudySubject parentStudySubject = studySubjectDao.getById(Integer.parseInt(parentRegistrationId));
    		
    		studySubject.setParticipant(participant);
    		studySubject.setStudySite(studySite);
    		studySubject.setParentStudySubject(parentStudySubject);
    		
            epochDao.initialize(epoch);
            ScheduledEpoch scheduledEpoch;
            if (epoch.getTreatmentIndicator()) {
                (epoch).getArms().size();
                scheduledEpoch = new ScheduledEpoch();
            }else {
                scheduledEpoch = new ScheduledEpoch();
            }
            scheduledEpoch.setEpoch(epoch);
            if(studySubject.getScheduledEpochs().size() == 0){
            	studySubject.getScheduledEpochs().add(0,scheduledEpoch);
            }else{
            	studySubject.getScheduledEpochs().set(0, scheduledEpoch);
            }
            registrationControllerUtils.buildCommandObject(studySubject);
            studySiteDao.initialize(studySubject.getStudySite());
    		studySubjectDao.initialize(studySubject.getParentStudySubject());
    		return  wrapper;	
    }
}