package edu.duke.cabig.c3pr.web.registration;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.utils.DateUtil;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.ControllerTools;
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

public class TransferEpochRegistrationController<C extends StudySubjectWrapper> extends RegistrationController<C> {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TransferEpochRegistrationController.class);

	public TransferEpochRegistrationController() {
        super("Change Epoch");
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
    protected Object formBackingObject(HttpServletRequest request)
    		throws Exception {
    	StudySubjectWrapper wrapper= (StudySubjectWrapper)super.formBackingObject(request);
    	ScheduledEpoch scheduledEpoch;
    	
    	if (WebUtils.hasSubmitParameter(request, "studySubject.scheduledEpoch.offEpochDate") && 
    			!StringUtils.isBlank(request.getParameter("studySubject.scheduledEpoch.offEpochReasonText"))){
    		Date offEpochDate;
    		offEpochDate = DateUtil.getUtilDateFromString(request.getParameter("studySubject.scheduledEpoch.offEpochDate"),"mm/dd/yyyy");
    		wrapper.getStudySubject().getScheduledEpoch().setOffEpochDate(offEpochDate);
    	}
    	
    	
    	if (WebUtils.hasSubmitParameter(request, "studySubject.scheduledEpoch.offEpochReasonText") &&
    			!StringUtils.isBlank(request.getParameter("studySubject.scheduledEpoch.offEpochReasonText"))){
    		String offEpochReasonText = request.getParameter("studySubject.scheduledEpoch.offEpochReasonText");
    		wrapper.getStudySubject().getScheduledEpoch().setOffEpochReasonText(offEpochReasonText);
    	}
    	
    	if(WebUtils.hasSubmitParameter(request, "epoch")){
	        Integer id = Integer.parseInt(request.getParameter("epoch"));
	        Epoch epoch = epochDao.getById(id);
	        epochDao.initialize(epoch);
	        if (epoch.getTreatmentIndicator()) {
	            (epoch).getArms().size();
	            scheduledEpoch = new ScheduledEpoch();
	        }
	        else {
	            scheduledEpoch = new ScheduledEpoch();
	        }
	        scheduledEpoch.setEpoch(epoch);
	        wrapper.getStudySubject().addScheduledEpoch(scheduledEpoch);
	        registrationControllerUtils.buildCommandObject(wrapper.getStudySubject());
    	}
    	return wrapper;
    }
    @Override
	protected void onBindOnNewForm(HttpServletRequest request, Object command)
			throws Exception {
		// TODO Auto-generated method stub
		super.onBindOnNewForm(request, command);
	}

	@Override
    protected ModelAndView processFinish(HttpServletRequest request, HttpServletResponse response,
                    Object command, BindException errors) throws Exception {
    	StudySubjectWrapper wrapper = (StudySubjectWrapper) command;
        StudySubject studySubject = wrapper.getStudySubject();
        if(wrapper.getShouldTransfer())
        	studySubject = studySubjectRepository.transferSubject(studySubject);
        else if(wrapper.getShouldEnroll()){
        	studySubject=studySubjectRepository.enroll(studySubject);
        }else if(wrapper.getShouldRegister()){
        	studySubject=studySubjectRepository.register(studySubject.getIdentifiers());
        }else if(wrapper.getShouldReserve()){
        	studySubject=studySubjectRepository.reserve(studySubject.getIdentifiers());
        }else{
            studySubject=studySubjectRepository.save(studySubject);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("processFinish(HttpServletRequest, HttpServletResponse, Object, BindException) - registration service call over"); //$NON-NLS-1$
        }
        if(WebUtils.hasSubmitParameter(request, "decorator") && "noheaderDecorator".equals(request.getParameter("decorator"))){
        	 return new ModelAndView("redirect:confirm?" + ControllerTools.createParameterString(studySubject.getSystemAssignedIdentifiers().get(0))+"&decorator=" + request.getParameter("decorator"));
        }else{
        	return new ModelAndView("redirect:confirm?" + ControllerTools.createParameterString(studySubject.getSystemAssignedIdentifiers().get(0)));	
        }
    }
}