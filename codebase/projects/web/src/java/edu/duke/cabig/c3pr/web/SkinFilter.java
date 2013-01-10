/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.duke.cabig.c3pr.constants.RoleTypes;
import edu.duke.cabig.c3pr.service.impl.OrganizationServiceImpl;
import edu.duke.cabig.c3pr.service.impl.PersonnelServiceImpl;
import edu.duke.cabig.c3pr.tools.Configuration;
import edu.duke.cabig.c3pr.utils.SecurityUtils;
import edu.duke.cabig.c3pr.utils.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: ion
 * Date: Jun 30, 2008
 * Time: 2:04:43 PM
 */

public class SkinFilter implements Filter {
    private FilterConfig filterConfig = null;
    
    private Logger log = Logger.getLogger(SkinFilter.class);

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
                ((HttpServletRequest)servletRequest).getSession().setAttribute("userRoles", SecurityUtils.getRoleTypes());
                ((HttpServletRequest)servletRequest).getSession().setAttribute("isSuperUser", SecurityUtils.isSuperUser());
           }
        }

        if (filterConfig.getServletContext().getAttribute("skinName") == null) {
            Configuration configuration = (Configuration)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("configuration");
            filterConfig.getServletContext().setAttribute("skinName", configuration.getMap().get("skinPath").toString());
        }

        if (StringUtils.getBlankIfNull((String)filterConfig.getServletContext().getAttribute("siteName")).equalsIgnoreCase("")) {
            Configuration configuration = (Configuration)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("configuration");
            try {
            	filterConfig.getServletContext().setAttribute("siteName", configuration.getMap().get("siteName").toString());
            } catch (Exception e) {
                log.warn("The site banner url could not be retrieved. Check its DB value.");
            }
        }

        if (StringUtils.getBlankIfNull((String)filterConfig.getServletContext().getAttribute("instName")).equalsIgnoreCase("")) {
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
