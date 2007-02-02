package com.semanticbits.ctms.portlets.caers;

import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Feb 2, 2007
 * Time: 11:17:12 AM
 * To change this template use File | Settings | File Templates.
 */
public class CaersPortlet extends ActionPortlet {


    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
         PrintWriter pw = response.getWriter();
        pw.println("<h2>CAERS Portlet</h2>");
    }
}
