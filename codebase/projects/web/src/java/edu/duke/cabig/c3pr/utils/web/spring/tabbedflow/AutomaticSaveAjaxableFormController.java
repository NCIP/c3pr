package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import edu.duke.cabig.c3pr.utils.StringUtils;
import gov.nih.nci.cabig.ctms.dao.MutableDomainObjectDao;
import gov.nih.nci.cabig.ctms.domain.MutableDomainObject;
import gov.nih.nci.cabig.ctms.web.tabs.AutomaticSaveFlowFormController;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kruttik
 *
 */
public abstract class AutomaticSaveAjaxableFormController<C, D extends MutableDomainObject, A extends MutableDomainObjectDao<D>> extends AutomaticSaveFlowFormController<C, D, A> {

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		ModelAndView modelAndView= super.handleRequestInternal(request, response);
		if(isAjaxRequest(request)){
			if(isAjaxResponseFreeText(request)){
				respondAjaxFreeText(request,response);
				return null;
			}
			
		}
		return modelAndView;
		
	}
	@Override
    protected void postProcessPage(HttpServletRequest request, Object command, Errors errors, int page) throws Exception {
    	// TODO Auto-generated method stub
    	if(isAjaxRequest(request)){
    		String freeText=((AjaxableTab<C>)getFlow((C)command).getTab(page)).postProcessAsynchronous(request, (C)command, errors);
    		if(freeText!=null)
    			setAjaxFreeText(request, freeText);
    	}else{
    		((AjaxableTab<C>)getFlow((C)command).getTab(page)).postProcessSynchronous(request, (C)command, errors);
    	}
    	super.postProcessPage(request, command, errors, page);
    }
	
    protected boolean isAjaxRequest(HttpServletRequest request){
    	if(StringUtils.getBlankIfNull(request.getParameter(getAjaxRequestParamName())).equalsIgnoreCase("true"))
    		return true;
    	return false;
    }
    
    protected void setAjaxFreeText(HttpServletRequest request, String text){
    	request.setAttribute(getAjaxFreeTextAttr(), text);
    }
    
    protected String getAjaxFreeText(HttpServletRequest request){
    	return (String)request.getAttribute(getAjaxFreeTextAttr());
    }
    
    protected boolean isAjaxResponseFreeText(HttpServletRequest request){
    	if(request.getAttribute(getAjaxFreeTextAttr())!=null){
    		return true;
    	}
    	return false;
    }
    
    protected String getAjaxViewName(HttpServletRequest request){
    	return request.getParameter(getAjaxViewParamName());
    }
 
    protected void respondAjaxFreeText(HttpServletRequest request, HttpServletResponse response)throws Exception{
    	PrintWriter pr=response.getWriter();
    	pr.println(getAjaxFreeText(request));
    	pr.flush();
    }
    
    protected String getAjaxRequestParamName(){
    	return "asynchronous";
    }
    
    protected String getAjaxFreeTextAttr(){
    	return "async_free_text";
    }

    protected String getAjaxViewParamName(){
    	return "asysnchronous_viewName";
    }
}
