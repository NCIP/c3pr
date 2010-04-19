package edu.duke.cabig.c3pr.utils.web.spring.tabbedflow;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.Errors;
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

    public ModelAndView doInPlaceEdit(HttpServletRequest request, Object command, Errors error)
                    throws Exception {
        String name = request.getParameter(IN_PLACE_PARAM_NAME);
        String value = request.getParameter(name);
        return postProcessInPlaceEditing(request, (C) command, name, value);
    }

    protected ModelAndView postProcessInPlaceEditing(HttpServletRequest request, C command,
                    String property, String value) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String pathToGet = request.getParameter(PATH_TO_GET);
        if (!StringUtils.getBlankIfNull(pathToGet).equals("")) {
        	value = (String) new BeanWrapperImpl(command).getPropertyValue(pathToGet);
        }
        map.put(AjaxableUtils.getFreeTextModelName(), value);
        return new ModelAndView("", map);
    }
}
