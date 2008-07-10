package edu.duke.cabig.c3pr.web;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;
import java.util.Iterator;
import java.util.List;

import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.service.impl.PersonnelServiceImpl;
import edu.duke.cabig.c3pr.service.impl.OrganizationServiceImpl;
import edu.duke.cabig.c3pr.domain.repository.impl.CSMUserRepositoryImpl;
import edu.duke.cabig.c3pr.domain.C3PRUser;
import edu.duke.cabig.c3pr.domain.C3PRUserGroupType;
import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import gov.nih.nci.security.authorization.domainobjects.Group;

/**
 * Created by IntelliJ IDEA.
 * User: ion
 * Date: Jun 30, 2008
 * Time: 2:04:43 PM
 */

public class SkinFilter implements Filter, ApplicationContextAware {
    private FilterConfig filterConfig = null;
    private ApplicationContext applicationContext;
    private Logger log = Logger.getLogger(SkinFilter.class);

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (((HttpServletRequest)servletRequest).getSession().getAttribute("userObject") == null) {

            PersonnelServiceImpl ps = (PersonnelServiceImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("personnelService");
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication auth = context.getAuthentication();

            if (auth != null) {
                gov.nih.nci.security.authorization.domainobjects.User user = ps.getCSMUserByUsername(auth.getName());
                ((HttpServletRequest)servletRequest).getSession().setAttribute("userObject", user);
           }
        }

        if (filterConfig.getServletContext().getAttribute("skinName") == null) {
            Configuration configuration = (Configuration)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("configuration");
            filterConfig.getServletContext().setAttribute("skinName", configuration.getMap().get("skinPath").toString());
        }

        if (filterConfig.getServletContext().getAttribute("siteName") == null) {
            Configuration configuration = (Configuration)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("configuration");
            filterConfig.getServletContext().setAttribute("siteName", configuration.getMap().get("siteName").toString());            
        }

        if (filterConfig.getServletContext().getAttribute("instName") == null) {
            OrganizationServiceImpl os = (OrganizationServiceImpl)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("organizationService");
            Configuration configuration = (Configuration)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("configuration");
            try {
                filterConfig.getServletContext().setAttribute("instName", os.getSiteNameByNciIdentifier(configuration.getMap().get("localNciInstituteCode").toString()));
            } catch (Exception e) {
                log.warn("The site name could not be retrieved by NCI ID Code. Check its DB value.");
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }
}
