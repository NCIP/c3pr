package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * @author Rhett Sutphin
 */
public abstract class ReflexiveAjaxableTab<C> extends AjaxableTab<C> {

    protected Class[] paramTypes;

    Logger log = Logger.getLogger(ReflexiveAjaxableTab.class);

    public ReflexiveAjaxableTab() {
    }

    public ReflexiveAjaxableTab(String longTitle, String shortTitle, String viewName) {
        this(longTitle, shortTitle, viewName, new Class[] { HttpServletRequest.class, Object.class,
                Errors.class });
    }

    public ReflexiveAjaxableTab(String longTitle, String shortTitle, String viewName, Class[] params) {
        super(longTitle, shortTitle, viewName);
        this.paramTypes = params;
    }

    public ModelAndView postProcessAsynchronous(HttpServletRequest request, C command, Errors error)
                    throws Exception {
        if (methodInvocationRequest(request)) {
            return (ModelAndView)ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(this.getClass(), getMethodName(request), paramTypes), this, new Object[] { request, command, error } ); 
        }
        return super.postProcessAsynchronous(request, command, error);
    }

    protected boolean methodInvocationRequest(HttpServletRequest request) {
        if (WebUtils.hasSubmitParameter(request, getAJAXMethodInvAttrName())) {
            return true;
        }
        return false;
    }

    public String getAJAXMethodInvAttrName() {
        return "_asyncMethodName";
    }

    public String getMethodName(HttpServletRequest request) {
        return (String) request.getParameter(getAJAXMethodInvAttrName());
    }
}
