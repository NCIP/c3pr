package edu.duke.cabig.c3pr.utils.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.apache.commons.lang.StringUtils;

public class AuditInfoFilter extends
                gov.nih.nci.cabig.ctms.web.filters.ContextRetainingFilterAdapter {

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                    final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) request;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = null;
        if (authentication != null){
        	userName = SecurityContextHolder.getContext().getAuthentication().getName();
        }
        if(StringUtils.isBlank(userName)){
        	userName=request.getParameter("userName");
        }
        if (!StringUtils.isBlank(userName)) {
            gov.nih.nci.cabig.ctms.audit.DataAuditInfo
                            .setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(
                            		userName, request.getRemoteAddr(), new Date(),
                                            httpReq.getRequestURI()));
        }
        chain.doFilter(request, response);
        edu.nwu.bioinformatics.commons.DataAuditInfo.setLocal(null);
    }
}
