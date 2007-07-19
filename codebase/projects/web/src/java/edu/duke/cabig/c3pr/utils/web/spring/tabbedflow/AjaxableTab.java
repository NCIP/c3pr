package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;

/**
 * @author Rhett Sutphin
*/
public abstract class AjaxableTab<C> extends Tab<C>{

	public AjaxableTab() {
		super();
	}
	public AjaxableTab(String longTitle, String shortTitle, String viewName) {
		super(longTitle, shortTitle, viewName);
    }
    public AjaxableTab(String longTitle, String shortTitle) {
    	super(longTitle, shortTitle, "");
    }
    
    @Override
    public void postProcess(HttpServletRequest arg0, C arg1, Errors arg2) {
    }
    
    protected abstract void postProcessSynchronous(HttpServletRequest request, C command, Errors error);
    protected abstract String postProcessAsynchronous(HttpServletRequest request, C command, Errors error);
}
