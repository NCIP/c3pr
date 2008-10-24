package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public abstract class SimpleAjaxableFormController extends SimpleFormController {

    protected Class[] paramTypes;

    public SimpleAjaxableFormController() {
        super();
        this.paramTypes = new Class[] { HttpServletRequest.class, Object.class, Errors.class };
    }

    public SimpleAjaxableFormController(Class[] paramTypes) {
        super();
        this.paramTypes = paramTypes;
    }

    @Override
    protected final ModelAndView processFormSubmission(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors)
                    throws Exception {
        if (AjaxableUtils.isAjaxRequest(request)) {
            if (AjaxableUtils.methodInvocationRequest(request)) {
                ModelAndView modelAndView = (ModelAndView) ReflectionUtils.invokeMethod(
                                ReflectionUtils.findMethod(this.getClass(), AjaxableUtils
                                                .getMethodName(request), paramTypes), this,
                                new Object[] { request, command, errors });
                if (AjaxableUtils.isAjaxResponseFreeText(modelAndView)) {
                    AjaxableUtils.respondAjaxFreeText(modelAndView, response);
                    return null;
                }
                return modelAndView;
            }
            else {
                return new ModelAndView(AjaxableUtils.getAjaxViewName(request));
            }
        }
        return processSubmit(request, response, command, errors);
    }

    public abstract ModelAndView processSubmit(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors);

    public ModelAndView deleteRow(HttpServletRequest request, Object command, Errors error)
                    throws Exception {
        String listPath = request.getParameter(getCollectionParamName());
        // List col = (List) new DefaultObjectPropertyReader(command, listPath)
        // .getPropertyValueFromPath();
        List col = (List) new BeanWrapperImpl(command).getPropertyValue(listPath);
        Integer index = null;

        String deletionIdStr = request.getParameter(getDeleteHashCodeParamName());
        if (deletionIdStr.startsWith("ID#")) {
            deletionIdStr = deletionIdStr.substring(3);
            int id = Integer.parseInt(deletionIdStr);
            for (int i = 0; i < col.size(); i++) {
                AbstractMutableDomainObject amdo;
                if (col.get(i) instanceof AbstractMutableDomainObject) {
                    amdo = (AbstractMutableDomainObject) col.get(i);
                    if (amdo.getId() == id) {
                        index = i;
                        break;
                    }
                }
            }
        }
        else if (deletionIdStr.startsWith("HC#")) {
            deletionIdStr = deletionIdStr.substring(3);
            int hashCode = Integer.parseInt(deletionIdStr);
            for (int i = 0; i < col.size(); i++) {
                if (col.get(i).hashCode() == hashCode) {
                    index = i;
                    break;
                }
            }
        }
        Map<String, String> map = new HashMap<String, String>();
        if (index == null) {
            map.put(AjaxableUtils.getFreeTextModelName(), "Unmatched hashCode/Id");
            return new ModelAndView("", map);
        }

        if (this.shouldDelete(request, command, error)) {
            col.remove(index.intValue());
            map.put(AjaxableUtils.getFreeTextModelName(), "deletedIndex=" + index + "||hashCode="
                            + deletionIdStr + "||");
        }
        else {
            // Enabling the retitred_indicator
            AbstractMutableDeletableDomainObject obj = (AbstractMutableDeletableDomainObject) col
                            .get(index);
            obj.setRetiredIndicatorAsTrue();
        }

        return new ModelAndView("", map);
    }
    
    protected String getSoftDeleteParamName() {
        return "softDelete";
    }

//    protected String getDeleteIndexParamName() {
//        return "deleteIndex";
//    }

    protected String getDeleteHashCodeParamName() {
        return "deleteHashCode";
    }

    protected String getCollectionParamName() {
        return "collection";
    }

    // override this method for soft deletes
    protected boolean shouldDelete(HttpServletRequest request, Object command, Errors error) {
        return !WebUtils.hasSubmitParameter(request, this.getSoftDeleteParamName());
    }
}

