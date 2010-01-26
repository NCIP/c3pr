package edu.duke.cabig.c3pr.utils.web.setup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.ResearchStaff;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordManagerService;
import edu.duke.cabig.c3pr.setup.SetupStatus;
import edu.duke.cabig.c3pr.web.admin.CreateResearchStaffController;

/**
 * @author Kruttik Aggarwal
 */
public class PreAuthenticationController<C extends ResearchStaff> extends
                CreateResearchStaffController<ResearchStaff> {

    private Logger log = Logger.getLogger(PreAuthenticationController.class);
    
    private PasswordManagerService passwordManagerService;
    
    private SetupStatus setupStatus;
    
	@Override
    protected ModelAndView onSynchronousSubmit(HttpServletRequest request,
                    HttpServletResponse response, Object command, BindException errors)
                    throws Exception {
		String userName = "";
		String password = request.getParameter("password");
		String confirmedPassword = request.getParameter("confirmPassword");
		ResearchStaff researchStaff = null;
		if(WebUtils.hasSubmitParameter(request, "errorPassword")){
			userName = request.getParameter("username");
			researchStaff = (ResearchStaff)command;
		}else{
			userName = ((ResearchStaff)command).getLoginId();
			ModelAndView mv = super.onSynchronousSubmit(request, response, command, errors);
			researchStaffDao.flush();
			if(errors.hasErrors()){
				return showForm(request, response, errors);
	        }
			researchStaff = (ResearchStaff)mv.getModel().get("command");
		}
		userName = userName.toLowerCase();
        try {
        	confirmedPassword(password, confirmedPassword);
            passwordManagerService.setPassword(userName, confirmedPassword, researchStaff.getToken());
            setupStatus.recheck();
        } catch (C3PRBaseRuntimeException e) {
            errors.reject("password",e.getMessage());
            request.setAttribute("errorPassword", "true");
            request.setAttribute("username", userName);
            return showForm(request, response, errors);
        }catch(C3PRBaseException e){
        	errors.reject("password",e.getMessage());
        	request.setAttribute("errorPassword", "true");
        	request.setAttribute("username", userName);
        	return showForm(request, response, errors);
        }
		return new ModelAndView("redirect:"+getSuccessView());
    }
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		Object obj = super.formBackingObject(request);
		request.getSession().setAttribute(FLOW, SETUP_FLOW);
		return obj;
	}

	private void confirmedPassword(String password , String passwordConfirm) {
        if (!password.equals(passwordConfirm))
        	throw new C3PRBaseRuntimeException("The two passwords entered are not the same,");
    }
	
	@Required
	public void setPasswordManagerService(
			PasswordManagerService passwordManagerService) {
		this.passwordManagerService = passwordManagerService;
	}

	@Required
	public void setSetupStatus(SetupStatus setupStatus) {
		this.setupStatus = setupStatus;
	}

}