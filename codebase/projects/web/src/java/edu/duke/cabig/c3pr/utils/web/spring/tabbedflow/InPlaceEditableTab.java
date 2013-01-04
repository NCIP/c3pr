/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * @author Rhett Sutphin
 */
public class InPlaceEditableTab<C> extends WorkFlowTab<C> {

    public static final String IN_PLACE_PARAM_NAME = "_ajaxInPlaceEditParam";

    public static final String PATH_TO_GET = "_pathToGet";

    public InPlaceEditableTab() {

    }

    public InPlaceEditableTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }
    
    public InPlaceEditableTab(String longTitle, String shortTitle, String viewName, Boolean willSave) {
        super(longTitle, shortTitle, viewName,willSave);
    }

    public InPlaceEditableTab(String longTitle, String shortTitle) {
        super(longTitle, shortTitle, "");
    }

    public ModelAndView doInPlaceEdit(HttpServletRequest request, Object command, Errors errors)
                    throws Exception {
        String name = request.getParameter(IN_PLACE_PARAM_NAME);
        String value = request.getParameter(name);
        return postProcessInPlaceEditing(request, (C) command, name, value, errors);
    }

    protected ModelAndView postProcessInPlaceEditing(HttpServletRequest request, C command,
                    String property, String value,  Errors errors) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String pathToGet = request.getParameter(PATH_TO_GET);
        if (!StringUtils.getBlankIfNull(pathToGet).equals("")) {
        	value = (String) new BeanWrapperImpl(command).getPropertyValue(pathToGet);
        	 map.put(AjaxableUtils.getFreeTextModelName(), value);
        }
        
        if(errors.hasErrors()){
        	StringBuilder sb = new StringBuilder(value);
        	for(ObjectError error : errors.getAllErrors()){
        		sb.append("<ul class=\"errors\"> <li>"+ error.getDefaultMessage() + "</li> </ul>");
        	}
        	 map.put(AjaxableUtils.getFreeTextModelName(), sb.toString());
        } else {
        	 map.put(AjaxableUtils.getFreeTextModelName(), value);
        }
       
        return new ModelAndView("", map);
    }
}
