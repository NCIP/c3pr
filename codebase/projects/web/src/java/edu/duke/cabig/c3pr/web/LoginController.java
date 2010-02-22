package edu.duke.cabig.c3pr.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

/**
 * User: ion
 * Date: Jun 9, 2008
 * Time: 5:41:45 PM
 */

public class LoginController extends ParameterizableViewController {

    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
    	if(SecurityContextHolder.getContext().getAuthentication()!=null){
    		return new ModelAndView("redirect:/pages/dashboard");
    	}
        return super.handleRequestInternal(httpServletRequest, httpServletResponse);    //To change body of overridden methods use File | Settings | File Templates.
    }

}
