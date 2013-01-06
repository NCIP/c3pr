package edu.duke.cabig.c3pr.web.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordManagerService;

/**
 * @author Jared Flatow
 */
public class ChangePasswordController extends SimpleFormController {

    private PasswordManagerService passwordManagerService;

    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return new ChangePasswordCommand();
    }
    
    @Override
    protected ModelAndView onSubmit(Object command, BindException errors) throws Exception {
        ModelAndView modelAndView = new ModelAndView(getFormView(), errors.getModel());
        ChangePasswordCommand cmd = (ChangePasswordCommand) command;
        try {
            passwordManagerService.setPassword(cmd.getUserName().toLowerCase(), cmd.confirmedPassword(), cmd
                            .getToken());
            return modelAndView.addObject("updated", true);
        } catch (C3PRBaseRuntimeException e) {
            return modelAndView.addObject("change_pwd_error", e);
        }catch(C3PRBaseException e){
        	return modelAndView.addObject("change_pwd_error", e);
        }
    }

    @Required
    public void setPasswordManagerService(PasswordManagerService passwordManagerService) {
        this.passwordManagerService = passwordManagerService;
    }

    public class ChangePasswordCommand {
        private String userName, password, passwordConfirm, token;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPasswordConfirm() {
            return passwordConfirm;
        }

        public void setPasswordConfirm(String passwordConfirm) {
            this.passwordConfirm = passwordConfirm;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String confirmedPassword() throws C3PRBaseRuntimeException {
            if (password.equals(passwordConfirm)) return password;
            throw new C3PRBaseRuntimeException("The two passwords entered are not the same,");
        }
    }
}
