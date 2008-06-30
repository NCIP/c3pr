package edu.duke.cabig.c3pr.web;

import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.duke.cabig.c3pr.tools.Configuration;

/**
 * User: ion
 * Date: Jun 9, 2008
 * Time: 5:41:45 PM
 */

public class LoginController extends ParameterizableViewController {

     private Configuration configuration;

    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        return super.handleRequestInternal(httpServletRequest, httpServletResponse);    //To change body of overridden methods use File | Settings | File Templates.
    }

}
