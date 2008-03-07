package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import gov.nih.nci.cabig.ctms.web.tabs.Tab;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Rhett Sutphin
 */
public abstract class AjaxableTab<C> extends Tab<C> {

    public AjaxableTab() {
        super();
    }

    public AjaxableTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public AjaxableTab(String longTitle, String shortTitle) {
        super(longTitle, shortTitle, "");
    }

    protected ModelAndView postProcessAsynchronous(HttpServletRequest request, C command,
                    Errors error) throws Exception {
        return new ModelAndView(getAjaxViewName(request));
    }

    protected String getFreeTextModelName() {
        return "free_text";
    }

    protected String getAjaxViewName(HttpServletRequest request) {
        return request.getParameter(getAjaxViewParamName());
    }

    protected String getAjaxViewParamName() {
        return "_asyncViewName";
    }
}
