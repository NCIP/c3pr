package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;
import gov.nih.nci.cabig.ctms.domain.MutableDomainObject;
import gov.nih.nci.cabig.ctms.web.tabs.AutomaticSaveFlowFormController;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kruttik
 * 
 */
public abstract class AutomaticSaveAjaxableFormController<C, D extends MutableDomainObject, A extends MutableDomainObjectDao<D>>
                extends AutomaticSaveFlowFormController<C, D, A> {

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                    HttpServletResponse response) throws Exception {
        if (AjaxableUtils.isAjaxRequest(request)) {
            synchronized (this) {
                ModelAndView superModelAndView = super.handleRequestInternal(request, response);
                ModelAndView modelAndView = AjaxableUtils.getAjaxModelAndView(request);
                modelAndView.getModel().putAll(superModelAndView.getModel());
                if (AjaxableUtils.isAjaxResponseFreeText(modelAndView)) {
                    AjaxableUtils.respondAjaxFreeText(modelAndView, response);
                    return null;
                }
                return modelAndView;
            }
        }
        return super.handleRequestInternal(request, response);
    }

    @Override
    protected Map referenceData(HttpServletRequest request, Object oCommand, Errors errors, int page)
                    throws Exception {
        C command = (C) oCommand;
        Map refdata = super.referenceData(request, command, errors, page);
        WorkFlowTab<C> current = (WorkFlowTab<C>) getFlow(command).getTab(page);
        refdata.putAll(current.referenceData(request, (C) command));
        return refdata;
    }

    @Override
    protected void postProcessPage(HttpServletRequest request, Object command, Errors errors,
                    int page) throws Exception {
        if (AjaxableUtils.isAjaxRequest(request)) {
            AjaxableTab<C> ajaxTab = (AjaxableTab<C>) getFlow((C) command).getTab(page);
            ModelAndView modelAndView = ajaxTab.postProcessAsynchronous(request, (C) command,
                            errors);
            AjaxableUtils.setAjaxModelAndView(request, modelAndView);
            if (!errors.hasErrors() && shouldSave(request, (C) command, getTab((C) command, page))) {
                C newCommand = save((C) command, errors);
                if (newCommand != null) {
                    request.getSession().setAttribute(
                                    getReplacedCommandSessionAttributeName(request), newCommand);
                }
            }
        }
        else {
            super.postProcessPage(request, command, errors, page);
        }
    }

}
