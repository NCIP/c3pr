package edu.duke.cabig.c3pr.web.registration.tabs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.RegistrationWorkFlowStatus;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.web.registration.RegistrationControllerUtils;
import edu.duke.cabig.c3pr.web.registration.StudySubjectWrapper;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jun 15, 2007 Time: 3:30:05 PM To change this template
 * use File | Settings | File Templates.
 */
public class ReviewSubmitTab extends RegistrationTab<StudySubjectWrapper> {

	private RegistrationControllerUtils registrationControllerUtils;
    public void setRegistrationControllerUtils(
			RegistrationControllerUtils registrationControllerUtils) {
		this.registrationControllerUtils = registrationControllerUtils;
	}

	public ReviewSubmitTab() {
        super("Review & Submit", "Review & Submit", "registration/reg_submit");
        setShowSummary("false");
    }

    @Override
    public Map referenceData(StudySubjectWrapper command) {
		Map map = new HashMap();
		map.put("actionLabel", getActionButtonLabel(command));
		map.put("tabTitle", getTabTitle(command));
		map.put("registerable", registrationControllerUtils.isRegisterableOnPage(command.getStudySubject()));
        return map;
    }
    
    private String getActionButtonLabel(StudySubjectWrapper wrapper){
    	StudySubject studySubject = wrapper.getStudySubject();
    	
    	String actionLabel = "" ;
    	if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED) {
    		if(wrapper.getShouldReserve()){
	    		actionLabel = "Reserve" ;
	    	}else if(wrapper.getShouldRegister()){
	    		actionLabel = "Register" ;
	    	}else if(wrapper.getShouldRandomize()){
	    		actionLabel = "Enroll & Randomize" ;
	    	}else if(wrapper.getShouldEnroll()){
	    		actionLabel = "Enroll" ;
	    	}
    	}else{
    		actionLabel = "Transfer" ;
    		if(wrapper.getShouldReserve()){
	    		actionLabel += " & Reserve" ;
	    	}else if(wrapper.getShouldRegister()){
	    		actionLabel += " & Register" ;
	    	}else if(wrapper.getShouldRandomize()){
	    		actionLabel += " & Randomize" ;
	    	}
    	}
    	return actionLabel ;
    }
    
    private String getTabTitle(StudySubjectWrapper wrapper){
    	StudySubject studySubject = wrapper.getStudySubject();
    	String tabTitle = "" ;
    	if (studySubject.getRegWorkflowStatus() != RegistrationWorkFlowStatus.ENROLLED) {
    		if(wrapper.getShouldReserve()){
	    		tabTitle = "Review & Reserve" ;
	    	}else if(wrapper.getShouldRegister()){
	    		tabTitle = "Review & Register" ;
	    	}else if(wrapper.getShouldRandomize()){
	    		tabTitle = "Enroll & Randomize" ;
	    	}else if(wrapper.getShouldEnroll()){
	    		tabTitle = "Review & Enroll" ;
	    	}
    	}else{
    		tabTitle = "Transfer" ;
    		if(wrapper.getShouldReserve()){
	    		tabTitle += " & Reserve" ;
	    	}else if(wrapper.getShouldRegister()){
	    		tabTitle += " & Register" ;
	    	}else if(wrapper.getShouldRandomize()){
	    		tabTitle += " & Randomize" ;
	    	}
    	}
    	return tabTitle ;
    }
    
    @Override
    public void postProcess(HttpServletRequest request, StudySubjectWrapper command, Errors errors) {
    	super.postProcess(request, command, errors);
    }
}
