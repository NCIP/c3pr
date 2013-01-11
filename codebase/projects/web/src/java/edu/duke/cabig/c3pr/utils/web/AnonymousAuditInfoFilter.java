/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import edu.duke.cabig.c3pr.utils.web.filter.PreAuthenticationSetupFilter;

public class AnonymousAuditInfoFilter extends
                gov.nih.nci.cabig.ctms.web.filters.ContextRetainingFilterAdapter {

	private Logger log = Logger.getLogger(PreAuthenticationSetupFilter.class);
	
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                    final FilterChain chain) throws IOException, ServletException {
    	log.debug("setting anonymous audit info");
    	HttpServletRequest httpReq = (HttpServletRequest) request;
        gov.nih.nci.cabig.ctms.audit.DataAuditInfo
        .setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
        		"anonymous", request.getRemoteAddr(), new Date(),
                        ((HttpServletRequest)request).getRequestURI()));
        chain.doFilter(request, response);
        edu.nwu.bioinformatics.commons.DataAuditInfo.setLocal(null);
    }
}
