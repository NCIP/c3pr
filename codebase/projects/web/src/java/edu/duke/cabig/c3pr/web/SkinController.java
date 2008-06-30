package edu.duke.cabig.c3pr.web;

/**
 * User: IO
 * Date: Jun 5, 2008 2:13:09 PM
 */

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.validation.BindException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.web.admin.ConfigurationCommand;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationPropertyEditor;

public class SkinController extends org.springframework.web.servlet.mvc.SimpleFormController {

    private Configuration configuration;

    public SkinController() {
        setCommandClass(ConfigurationCommand.class);
        setFormView("skin");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest httpServletRequest) throws Exception {
        return new ConfigurationCommand(configuration);
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, BindException e) throws Exception {
        httpServletRequest.getSession().getServletContext().setAttribute("skinName", configuration.getMap().get("skinPath"));
        return new ModelAndView("redirect:skin");
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        super.initBinder(request, binder);

        ConfigurationProperty configurationProperty = configuration.getProperties().get("skinPath");
        binder.registerCustomEditor(Object.class, "conf[" + configurationProperty.getKey() + "].value", new ConfigurationPropertyEditor(configurationProperty));
    }

}
