package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public abstract class SimpleAjaxableFormController extends SimpleFormController {

    protected Class[] paramTypes;
    
    public SimpleAjaxableFormController() {
        super();
        this.paramTypes=new Class[] { HttpServletRequest.class, Object.class,
                        Errors.class };
    }

    public SimpleAjaxableFormController(Class[] paramTypes) {
        super();
        this.paramTypes = paramTypes;
    }

    @Override
    protected final ModelAndView processFormSubmission(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors)
                    throws Exception {
        if(AjaxableUtils.isAjaxRequest(request)){
            if (AjaxableUtils.methodInvocationRequest(request)) {
                ModelAndView modelAndView=(ModelAndView)ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(this.getClass(), AjaxableUtils.getMethodName(request), paramTypes), this, new Object[] { request, command, errors } );
                if (AjaxableUtils.isAjaxResponseFreeText(modelAndView)) {
                    AjaxableUtils.respondAjaxFreeText(modelAndView, response);
                    return null;
                }
                return modelAndView;
            }else{
                return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
            }
        }
        return processSubmit(request, response, command, errors);
    }
    
    public abstract ModelAndView processSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors);
    
}
