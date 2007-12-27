package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import edu.duke.cabig.c3pr.utils.web.CustomMethodInvocater;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author Rhett Sutphin
 */
public abstract class ReflexiveAjaxableTab<C> extends AjaxableTab<C> {

    protected Class[] paramTypes;
    Logger log = Logger.getLogger(ReflexiveAjaxableTab.class);

    public ReflexiveAjaxableTab() {
    }

    public ReflexiveAjaxableTab(String longTitle, String shortTitle, String viewName) {
        this(longTitle, shortTitle, viewName, new Class[]{HttpServletRequest.class, Object.class, Errors.class});
    }

    public ReflexiveAjaxableTab(String longTitle, String shortTitle, String viewName, Class[] params) {
        super(longTitle, shortTitle, viewName);
        this.paramTypes = params;
    }

    public ModelAndView postProcessAsynchronous(HttpServletRequest request, C command, Errors error) throws Exception {
        if (methodInvocationRequest(request)) {
            Method method = getMethod(this, getMethodName(request));
            return (ModelAndView) new CustomMethodInvocater(this, method, new Object[]{request, command, error}).invoke();
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

    protected Method getMethod(Object obj, String methodName) throws Exception {
        for (Method m : obj.getClass().getMethods())
            log.debug("Found Method:" + m);
        return obj.getClass().getMethod(methodName, paramTypes);
    }
}
