package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

/**
 * @author Rhett Sutphin
*/
public abstract class InPlaceEditableTab<C> extends WorkFlowTab<C>{

	private static final String IN_PLACE_PARAM_NAME="_ajaxInPlaceEditParam";

	public InPlaceEditableTab(){
		
	}
	
	public InPlaceEditableTab(String longTitle, String shortTitle, String viewName) {
		super(longTitle, shortTitle, viewName);
    }
    public InPlaceEditableTab(String longTitle, String shortTitle) {
    	super(longTitle, shortTitle, "");
    }
    
    public ModelAndView doInPlaceEdit(HttpServletRequest request, Object command, Errors error) throws Exception {
		String name=request.getParameter(IN_PLACE_PARAM_NAME);
		String value=request.getParameter(name);
		return postProcessInPlaceEditing(request, (C)command, name, value);
    }
    
    protected ModelAndView postProcessInPlaceEditing(HttpServletRequest request, C command, String property, String value){
    	Map<String, String> map=new HashMap<String, String>();
    	map.put(getFreeTextModelName(), value);
    	return new ModelAndView("",map);
    }
}
