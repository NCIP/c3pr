/**
 * Copyright (c) 2004 Grad-Soft Ltd, Kiev, Ukraine
 * http://www.gradsoft.ua
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.gridlab.gridsphere.filters;

import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletRequest;
import org.gridlab.gridsphere.portlet.impl.SportletResponse;
import org.gridlab.gridsphere.services.core.registry.impl.PortletManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * GridSphereFilter is used for first time portal initialization including portlets
 */
public class GridSphereFilter implements Filter {

    private static Boolean firstDoGet = Boolean.TRUE;
    private static PortletLog log = SportletLog.getInstance(GridSphereFilter.class);

    public void init(FilterConfig filterConfig) {}

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        // If first time being called, instantiate all portlets
        if (firstDoGet.equals(Boolean.TRUE)) {
            firstDoGet = Boolean.FALSE;
            log.debug("Initializing portlets");
            try {
                // initialize needed services
                PortletManager portletManager = PortletManager.getInstance();
                portletManager.init();
                // initialize all portlets
                if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
                    PortletRequest portletRequest = new SportletRequest((HttpServletRequest)request);
                    PortletResponse portletResponse = new SportletResponse((HttpServletResponse)response, portletRequest);
                    portletManager.initAllPortletWebApplications(portletRequest, portletResponse);
                }
            } catch (Exception e) {
                log.error("GridSphere initialization failed!", e);
                RequestDispatcher rd = request.getRequestDispatcher("/jsp/errors/init_error.jsp");
                request.setAttribute("error", e);
                rd.forward(request, response);
                return;
            }
        }
        chain.doFilter(request, response);
    }

}