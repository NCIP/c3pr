package edu.duke.cabig.c3pr.utils.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.utils.SecurityUtils;

public class AuditInfoFilter extends
                gov.nih.nci.cabig.ctms.web.filters.ContextRetainingFilterAdapter {

	private static Logger log = Logger.getLogger(AuditInfoFilter.class);
	
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                    final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        setAuditInfo(httpReq);
        chain.doFilter(request, response);
        unsetAuditInfo();
    }

	/**
	 * 
	 */
	public static void unsetAuditInfo() {
		edu.nwu.bioinformatics.commons.DataAuditInfo.setLocal(null);
	}

	/**
	 * @param httpReq
	 */
	public static void setAuditInfo(HttpServletRequest httpReq) {
		String userName = SecurityUtils.getUserName();
        if (!StringUtils.isBlank(userName)) {
        	log.debug("setting audit info for "+ userName);
            gov.nih.nci.cabig.ctms.audit.DataAuditInfo
                            .setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
                            		userName, httpReq.getRemoteAddr(), new Date(),
                                            httpReq.getRequestURI()));
        }else{
        	log.debug("no authentication found in SecurityContext. Skipping audit info setup");
        }
	}
}
