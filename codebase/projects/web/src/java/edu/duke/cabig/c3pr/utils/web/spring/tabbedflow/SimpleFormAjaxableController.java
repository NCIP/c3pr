package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.dao.C3PRBaseDao;
import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.utils.StringUtils;

public abstract class SimpleFormAjaxableController<C extends AbstractMutableDeletableDomainObject, A extends C3PRBaseDao<C>> extends SimpleFormController{
	
	private InPlaceEditableTab<C> page;
	
	protected abstract A getDao();
	
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
   			//setAjaxModelAndView(request, modelAndView);
   	        if (!errors.hasErrors() && shouldSave(request, (C)command)) {
   	        	command = save((C)command, errors);
   	        }
   	        request.setAttribute(getFormSessionAttributeName(), command);
   	        if(isAjaxResponseFreeText(modelAndView)){
				respondAjaxFreeText(modelAndView, response);
				return null;
			}   	        
   	        return modelAndView;
    	}else{
    		return onSynchronousSubmit(request,response,(C)command, errors);
    	}
    }
	
	protected ModelAndView onSynchronousSubmit(HttpServletRequest request,
            HttpServletResponse response, Object command, BindException errors) throws Exception{
		if (getSuccessView() == null) {
			 throw new ServletException("successView isn't set");
		}
		return new ModelAndView(getSuccessView(), errors.getModel());
	}

/*	@Override
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
	}	*/
	
	
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
		
		protected boolean shouldSave(HttpServletRequest request, C command){
			return true;
		}
		
	    
	    protected C save(C command, BindException errors){
//	    	if(command.getId() == null){
	    		getDao().save(getPrimaryDomainObject(command));
//	    	}
	    		return command;
	    }

	    protected abstract C getPrimaryDomainObject(C command);
}
