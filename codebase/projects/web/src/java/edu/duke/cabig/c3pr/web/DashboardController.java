package edu.duke.cabig.c3pr.web;

import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.duke.cabig.c3pr.tools.Configuration;

/**
 * User: ion
 * Date: Jun 11, 2008
 * Time: 1:56:21 PM
 */

public class DashboardController extends ParameterizableViewController {
//    private Configuration configuration;

    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
//        httpServletRequest.setAttribute("skinName", configuration.getMap().get("skinPath").toString());
        return super.handleRequestInternal(httpServletRequest, httpServletResponse);
    }

/*
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
*/
}
