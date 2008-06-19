package edu.duke.cabig.c3pr.web;

import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.ModelAndView;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.web.PropertyWrapper;
import edu.duke.cabig.c3pr.domain.repository.CSMUserRepository;

import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * User: ion
 * Date: Jun 11, 2008
 * Time: 1:56:21 PM
 */

public class DashboardController extends ParameterizableViewController {

    protected static final Log log = LogFactory.getLog(DashboardController.class);
    private Configuration configuration;
    private String filename;
    
    CSMUserRepository csmUserRepository;

    public void setCsmUserRepository(CSMUserRepository csmUserRepository) {
        this.csmUserRepository = csmUserRepository;
    }

    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        httpServletRequest.setAttribute("skinName", configuration.getMap().get("skinPath").toString());

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        GrantedAuthority[] groups = auth.getAuthorities();

        filename = "default.links.properties";

        for (GrantedAuthority ga : groups) {
            System.out.println("ga: " + ga.getAuthority());
            if (DashboardController.class.getClassLoader().getResource(ga.getAuthority() + ".links.properties") != null) {
                filename = ga.getAuthority() + ".links.properties";
                log.debug("Found rolebased links file: " + filename);
                break;
            }
        }

//        filename = "default.links.property";
        Properties p = new Properties();
        System.out.println("Filename: " + filename);                         
        try {
            p.load(DashboardController.class.getClassLoader().getResourceAsStream(filename));
            log.debug("The links file has " + p.keySet().size() + " elements.");
            httpServletRequest.setAttribute("links", new PropertyWrapper(p));
        } catch (IOException e) {
            log.error("Error while trying to read the property file: [" + filename + "]");
        }

/*
        List l = new ArrayList(p.keySet());
        Collections.sort(l);
*/

        return super.handleRequestInternal(httpServletRequest, httpServletResponse);
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
