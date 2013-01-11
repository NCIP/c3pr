/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web.filter;

import edu.duke.cabig.c3pr.setup.SetupStatus;
import gov.nih.nci.cabig.ctms.web.filters.ContextRetainingFilterAdapter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author Jalpa Patel
 */
public class PostAuthenticationSetupFilter extends ContextRetainingFilterAdapter {
    
	private SetupStatus status;

	private Logger log = Logger.getLogger(PostAuthenticationSetupFilter.class);
	
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        status = (SetupStatus) getApplicationContext().getBean("setupStatus");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (status.isPostAuthenticationSetupNeeded()) {
            log.debug("Initial setup for site is required.  Redirecting.");
            try {
                new RedirectView("/setup/configureC3PR", true).render(null, (HttpServletRequest) request, (HttpServletResponse) response);
            } catch (RuntimeException e) {
                throw e;
            } catch (ServletException e) {
                throw e;
            } catch (IOException e) {
                throw e;
            } catch (Exception e) {
                throw new ServletException("Redirect view rending failed", e);
            }
        } else {
            log.debug("Initial setup complete.  Proceeding.");
            chain.doFilter(request, response);
        }
    }
}
