package edu.duke.cabig.c3pr.utils.web.filter;

import edu.duke.cabig.c3pr.setup.SetupStatus;
import gov.nih.nci.cabig.ctms.web.filters.ContextRetainingFilterAdapter;

import java.io.IOException;
import java.util.Date;

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
 * @author Rhett Sutphin
 */
public class PreAuthenticationSetupFilter extends ContextRetainingFilterAdapter {

	private SetupStatus status;
    
	private Logger log = Logger.getLogger(PreAuthenticationSetupFilter.class);
	
    public void init(FilterConfig filterConfig) throws ServletException {
        super.init(filterConfig);
        status = (SetupStatus) getApplicationContext().getBean("setupStatus");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (status.isPreAuthenticationSetupNeeded()) {
        	setAnonymousAudit(request);
            log.debug("Initial setup for administrator is required.  Redirecting.");
            try {
                new RedirectView("/setup/preAuthenticationSetup", true).render(null, (HttpServletRequest) request, (HttpServletResponse) response);
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
            log.debug("Initial setup of user is complete.  Proceeding.");
            chain.doFilter(request, response);
        }
    }
    
    private void setAnonymousAudit(ServletRequest request){
    	gov.nih.nci.cabig.ctms.audit.DataAuditInfo
        .setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
        		"anonymous", request.getRemoteAddr(), new Date(),
                        ((HttpServletRequest)request).getRequestURI()));
    }
}
