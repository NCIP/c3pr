package edu.duke.cabig.c3pr.web.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordManagerService;
import edu.duke.cabig.c3pr.tools.Configuration;

/**
 * @author Jared Flatow
 */
public class ResetPasswordController extends SimpleFormController {

    private PasswordManagerService passwordManagerService;

    private CSMUserRepository csmUserRepository;

    private String emailPretext, emailPosttext;

    public ResetPasswordController() {
        setBindOnNewForm(true);
        initEmailText();
    }

    protected Object formBackingObject(HttpServletRequest request) throws Exception {
        return new UserName(request.getScheme(), request.getServerName(), request.getServerPort(),
                request.getContextPath());
    }

    @Override
    protected ModelAndView onSubmit(Object command, BindException errors) throws Exception {
        UserName userName = (UserName) command;
        String token = passwordManagerService.requestToken(userName.getUserName());
        csmUserRepository.sendUserEmail(userName.getUserName(), "Reset C3PR Password", emailPretext
                + userName.getURL() + "&token=" + token + emailPosttext);
        System.out.println("########### URL ############### " + userName.getURL() + "&token=" + token);
        return new ModelAndView("user/emailSent");
    }

    private void initEmailText() {
        emailPretext = ""
                + "Did you forgot your password?\n"
                + "Do you want to select a new password?\n"
                + "\n"
                + "We cannot send you your password, because we don't know what it is. However, we can help you change your password to something new.\n"
                + "\n" + "\n" + "To change your password go to the Change Password page:\n"
                + "\n";
        emailPosttext = "\n"
                + "\n"
                + "Enter a new password for yourself once you get there. After you choose a new password, you can log into C3PR using the new password.\n"
                + "\n"
                + "\n"
                + "Regards,\n"
                + "The C3PR Notification System.\n"
                + "\n"
                + "(Note: If you did not request a new password, please disregard this message.)";
    }
    private Configuration configuration;
    
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
    
    @Required
    public void setPasswordManagerService(PasswordManagerService passwordManagerService) {
        this.passwordManagerService = passwordManagerService;
    }
    
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletRequest.setAttribute("skinName", configuration.getMap().get("skinPath").toString());
        return super.handleRequestInternal(httpServletRequest, httpServletResponse);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Required
    public void setCsmUserRepository(final CSMUserRepository csmUserRepository) {
        this.csmUserRepository = csmUserRepository;
    }


    public static String getURL(String scheme, String serverName, int serverPort, String contextPath) {
        return scheme + "://" + serverName + ":" + serverPort + contextPath + UserName.CHANGE_PATH;
    }

    public class UserName {
        private static final String CHANGE_PATH = "/public/user/changePassword?";

        private String userName;

        private String url;

        public UserName(String scheme, String serverName, int serverPort, String contextPath) {
            url = ResetPasswordController.getURL(scheme, serverName, serverPort, contextPath);
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
            this.url += "userName=" + userName;
        }

        public String getURL() {
            return url;
        }
    }
}
