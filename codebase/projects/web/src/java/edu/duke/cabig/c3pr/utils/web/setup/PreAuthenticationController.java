/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.setup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.domain.PersonUser;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.service.passwordpolicy.PasswordManagerService;
import edu.duke.cabig.c3pr.setup.SetupStatus;
import edu.duke.cabig.c3pr.web.admin.CreatePersonOrUserController;
import edu.duke.cabig.c3pr.web.admin.PersonOrUserWrapper;

/**
 * @author Kruttik Aggarwal
 */
public class PreAuthenticationController extends CreatePersonOrUserController {

    private Logger log = Logger.getLogger(PreAuthenticationController.class);
    
    private PasswordManagerService passwordManagerService;
    
	private SetupStatus setupStatus;
    
	@Override
    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		PersonOrUserWrapper wrapper = (PersonOrUserWrapper)command;
    	PersonUser personUser = wrapper.getPersonUser();
    	String userName = wrapper.getUserName();

    	String password = request.getParameter("password");
		String confirmedPassword = request.getParameter("confirmPassword");
		
		if(!WebUtils.hasSubmitParameter(request, "errorPassword")){
			ModelAndView mv = super.onSubmit(request, response, command, errors);
			personUserDao.flush();
			if(errors.hasErrors()){
				return showForm(request, response, errors);
	        }
			personUser = ((PersonOrUserWrapper)mv.getModel().get("command")).getPersonUser();
		}else{
			// just set the password
		}
		userName = userName.toLowerCase();
        try {
        	confirmedPassword(password, confirmedPassword);
            passwordManagerService.setPassword(userName, confirmedPassword, personUser.getToken());
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
