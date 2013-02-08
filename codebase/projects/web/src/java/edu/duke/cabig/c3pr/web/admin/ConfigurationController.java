/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.tools.Configuration;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationProperty;
import gov.nih.nci.cabig.ctms.tools.configuration.ConfigurationPropertyEditor;

/**
 * @author Rhett Sutphin
 */
public class ConfigurationController extends SimpleFormController {
    private Configuration configuration;

    public ConfigurationController() {
        setCommandClass(ConfigurationCommand.class);
        setFormView("admin/configure");
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return new ConfigurationCommand(configuration);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder)
                    throws Exception {
        super.initBinder(request, binder);
        for (ConfigurationProperty<?> property : configuration.getProperties().getAll()) {
            binder.registerCustomEditor(Object.class, "conf[" + property.getKey() + "].value",
                            new ConfigurationPropertyEditor(property));
        }
    }

    @Override
    protected ModelAndView onSubmit(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, BindException e) throws Exception {
        httpServletRequest.getSession().getServletContext().removeAttribute("siteName");
        httpServletRequest.getSession().getServletContext().removeAttribute("instName");
        return new ModelAndView("redirect:configure").addObject("updated", true);
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
