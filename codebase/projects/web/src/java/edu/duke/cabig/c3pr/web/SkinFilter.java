package edu.duke.cabig.c3pr.web;

import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import java.io.IOException;

import edu.duke.cabig.c3pr.tools.Configuration;
/**
 * Created by IntelliJ IDEA.
 * User: ion
 * Date: Jun 30, 2008
 * Time: 2:04:43 PM
 */

public class SkinFilter implements Filter, ApplicationContextAware {
    private FilterConfig filterConfig = null;
    private ApplicationContext applicationContext;
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (filterConfig.getServletContext().getAttribute("skinName") == null) {
            Configuration configuration = (Configuration)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("configuration");
            filterConfig.getServletContext().setAttribute("skinName", configuration.getMap().get("skinPath").toString());
        }

        if (filterConfig.getServletContext().getAttribute("siteName") == null) {
            Configuration configuration = (Configuration)WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext()).getBean("configuration");
            filterConfig.getServletContext().setAttribute("siteName", configuration.getMap().get("siteName").toString());            
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }
}
