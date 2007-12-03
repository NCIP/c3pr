package edu.duke.cabig.c3pr.web.study;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

import edu.duke.cabig.c3pr.domain.Study;

/**
 * Author:  Vinay Gangoli
 * Date: Nov 26, 2007
 */
public class StudyNotificationTab extends StudyTab {

    public StudyNotificationTab(){
		super("Notification", "Notification", "study/study_notification");
	}

    @Override
	public Map referenceData(HttpServletRequest request, Study study) {
        Map<String, Object> refdata = super.referenceData(study);
        if( (request.getAttribute("amendFlow") != null && request.getAttribute("amendFlow").toString().equals("true")) ||
    		    (request.getAttribute("editFlow") != null && request.getAttribute("editFlow").toString().equals("true")) ) 
    		{
    			if(request.getSession().getAttribute(DISABLE_FORM_NOTIFICATION) != null){
    				refdata.put("disableForm", request.getSession().getAttribute(DISABLE_FORM_NOTIFICATION));
    			} else {
    				refdata.put("disableForm", new Boolean(false));
    			}
    		}
        return refdata;
    }
    
    @Override
    public void postProcess(HttpServletRequest httpServletRequest, Study study, Errors errors) {
    	super.postProcess(httpServletRequest, study, errors);
    }
    
}