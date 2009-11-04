
package edu.duke.cabig.c3pr.web.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.domain.passwordpolicy.PasswordPolicy;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordPolicyService;

/**
 * 
 */
public class PasswordPolicyConfigurationController extends SimpleFormController {

    private PasswordPolicyService passwordPolicyService;

    public PasswordPolicyConfigurationController() {
        setFormView("admin/password_policy_configure");
        setBindOnNewForm(true);
    }

    @Override
    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return passwordPolicyService.getPasswordPolicy();
    }

    @Override
    protected ModelAndView onSubmit(Object command, BindException errors) throws Exception {
        ModelAndView modelAndView = new ModelAndView(getFormView(), errors.getModel());
        passwordPolicyService.setPasswordPolicy((PasswordPolicy) command);
        return modelAndView.addObject("updated", true);
    }

    @Required
    public void setPasswordPolicyService(PasswordPolicyService passwordPolicyService) {
        this.passwordPolicyService = passwordPolicyService;
    }
}
