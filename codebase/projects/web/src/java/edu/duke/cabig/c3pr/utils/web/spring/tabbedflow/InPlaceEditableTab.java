package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.util.WebUtils;

/**
 * @author Rhett Sutphin
*/
public abstract class InPlaceEditableTab<C> extends AjaxableTab<C>{

	private static final String IN_PLACE_PARAM_NAME="in_place_edit_param";
	public InPlaceEditableTab() {
		super();
	}
	public InPlaceEditableTab(String longTitle, String shortTitle, String viewName) {
		super(longTitle, shortTitle, viewName);
    }
    public InPlaceEditableTab(String longTitle, String shortTitle) {
    	super(longTitle, shortTitle, "");
    }
    
    @Override
    protected String postProcessAsynchronous(HttpServletRequest request, C command, Errors error) throws Exception {
    	if(WebUtils.hasSubmitParameter(request, IN_PLACE_PARAM_NAME)){
    		String name=request.getParameter(IN_PLACE_PARAM_NAME);
    		String value=request.getParameter(name);
    		return postProcessInPlaceEditing(request, command, name, value);
    	}
    	return postProcessAsynchronously(request, command, error);
    }
    
    protected String postProcessInPlaceEditing(HttpServletRequest request, C command, String property, String value){
    	return value;
    }
    
    protected abstract String postProcessAsynchronously(HttpServletRequest request, C command, Errors error);
}
