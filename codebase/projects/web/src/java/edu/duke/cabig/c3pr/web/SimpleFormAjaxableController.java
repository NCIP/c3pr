package edu.duke.cabig.c3pr.web;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContextAware;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.AjaxableTab;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;
import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.WorkFlowTab;
import gov.nih.nci.cabig.ctms.domain.MutableDomainObject;
import gov.nih.nci.cabig.ctms.web.tabs.Flow;
import gov.nih.nci.cabig.ctms.web.tabs.Tab;

public abstract class SimpleFormAjaxableController<C> extends SimpleFormController{
	
	private InPlaceEditableTab<C> page;
	
	public InPlaceEditableTab<C> getPage() {
		return page;
	}
	
	public SimpleFormAjaxableController() {
		super();
	}

	public void setPage(InPlaceEditableTab<C> page) {
		this.page = page;
	}
	@Override
	 protected ModelAndView onSubmit(HttpServletRequest request,
             HttpServletResponse response, Object command, BindException errors) throws Exception {
    	// TODO Auto-generated method stub
		if(isAjaxRequest(request)){
    		ModelAndView modelAndView =page.postProcessAsynchronous(request, (C)command, errors);
   			setAjaxModelAndView(request, modelAndView);
   	        if (!errors.hasErrors() && shouldSave(request, (C)command)) {
   	        	C newCommand = save((C)command, errors);
   	            if (newCommand != null) {
   	                request.getSession().setAttribute(getReplacedCommandSessionAttributeName(request), newCommand);
   	            }
   	        }
   	        return modelAndView;
    	}else{
        	super. onSubmit(request,
                    response,(C)command, errors);
    	}
    	return new ModelAndView();
    }
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if(isAjaxRequest(request)){
			synchronized (this){
				ModelAndView superModelAndView= super.handleRequestInternal(request, response);
				ModelAndView modelAndView=getAjaxModelAndView(request);
				if(superModelAndView!=null && superModelAndView.getModel()!=null && modelAndView!=null && modelAndView.getModel()!=null){
					modelAndView.getModel().putAll(superModelAndView.getModel());
				}
				if(isAjaxResponseFreeText(modelAndView)){
					respondAjaxFreeText(modelAndView, response);
					return null;
				}
				return modelAndView;
			}
		}
		return super.handleRequestInternal(request, response);
	}	
	
	
	 protected boolean isAjaxRequest(HttpServletRequest request){
	    	if(StringUtils.getBlankIfNull(request.getParameter(getAjaxRequestParamName())).equalsIgnoreCase("true"))
	    		return true;
	    	return false;
	    }
	    
	    protected void setAjaxModelAndView(HttpServletRequest request, ModelAndView modelAndView){
	    	request.setAttribute(getAjaxModelAndViewAttr(), modelAndView);
	    }
	    
	    protected ModelAndView getAjaxModelAndView(HttpServletRequest request){
	    	return (ModelAndView)request.getAttribute(getAjaxModelAndViewAttr());
	    }
	    
	    protected boolean isAjaxResponseFreeText(ModelAndView modelAndView){
	    	if(StringUtils.getBlankIfNull(modelAndView.getViewName()).equals("")){
	    		return true;
	    	}
	    	return false;
	    }
	    
	    protected void respondAjaxFreeText(ModelAndView modelAndView, HttpServletResponse response)throws Exception{
	    	PrintWriter pr=response.getWriter();
	    	pr.println(modelAndView.getModel().get(getFreeTextModelName()));
	    	pr.flush();
	    }
	    
	    protected String getAjaxRequestParamName(){
	    	return "_asynchronous";
	    }
	    
	    protected String getAjaxModelAndViewAttr(){
	    	return "async_model_and_view";
	    }

	    protected String getFreeTextModelName(){
	    	return "free_text";
	    }
	    
	    protected ModelAndView postProcessAsynchronous(HttpServletRequest request, C command, Errors error) throws Exception{
	    	return new ModelAndView(getAjaxViewName(request));
	    }
	    
		protected String getAjaxViewName(HttpServletRequest request){
			return request.getParameter(getAjaxViewParamName());
		}

		protected String getAjaxViewParamName(){
			return "_asyncViewName";
		}
		
		protected boolean shouldSave(HttpServletRequest request, Object command){
			return true;
		}
		
		protected String getReplacedCommandSessionAttributeName(HttpServletRequest request) {
	        return getFormSessionAttributeName(request) + ".to-replace";
	    }
	    
	   protected abstract C save(Object command, BindException errors);

}
