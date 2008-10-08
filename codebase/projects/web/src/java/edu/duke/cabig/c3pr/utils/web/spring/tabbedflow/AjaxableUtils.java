package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.utils.StringUtils;

public static class AjaxableUtils {

    public static final boolean isAjaxRequest(HttpServletRequest request) {
        if (StringUtils.getBlankIfNull(request.getParameter(getAjaxRequestParamName()))
                        .equalsIgnoreCase("true")) return true;
        return false;
    }

    public static final void setAjaxModelAndView(HttpServletRequest request, ModelAndView modelAndView) {
        request.setAttribute(getAjaxModelAndViewAttr(), modelAndView);
    }

    public static final ModelAndView getAjaxModelAndView(HttpServletRequest request) {
        return (ModelAndView) request.getAttribute(getAjaxModelAndViewAttr());
    }

    public static boolean isAjaxResponseFreeText(ModelAndView modelAndView) {
        if (StringUtils.getBlankIfNull(modelAndView.getViewName()).equals("")) {
            return true;
        }
        return false;
    }

    public static final void respondAjaxFreeText(ModelAndView modelAndView, HttpServletResponse response)
                    throws Exception {
        PrintWriter pr = response.getWriter();
        pr.println(modelAndView.getModel().get(getFreeTextModelName()));
        pr.flush();
    }

    public static final String getAjaxRequestParamName() {
        return "_asynchronous";
    }

    public static final String getAjaxModelAndViewAttr() {
        return "async_model_and_view";
    }

    public static final String getFreeTextModelName() {
        return "free_text";
    }
    
    public static final boolean methodInvocationRequest(HttpServletRequest request) {
        if (WebUtils.hasSubmitParameter(request, getAJAXMethodInvAttrName())) {
            return true;
        }
        return false;
    }

    public static final String getAJAXMethodInvAttrName() {
        return "_asyncMethodName";
    }

    public static final String getMethodName(HttpServletRequest request) {
        return (String) request.getParameter(getAJAXMethodInvAttrName());
    }
    
    public static final String getAjaxViewName(HttpServletRequest request) {
        return request.getParameter(getAjaxViewParamName());
    }

    public static final String getAjaxViewParamName() {
        return "_asyncViewName";
    }
}
