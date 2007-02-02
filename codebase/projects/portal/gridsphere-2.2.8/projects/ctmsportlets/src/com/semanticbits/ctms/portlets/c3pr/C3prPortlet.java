package com.semanticbits.ctms.portlets.c3pr;

import org.gridlab.gridsphere.portlet.AbstractPortlet;
import org.gridlab.gridsphere.portlet.PortletRequest;
import org.gridlab.gridsphere.portlet.PortletResponse;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletConfig;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Feb 2, 2007
 * Time: 11:02:42 AM
 * To change this template use File | Settings | File Templates.
 */
public class C3prPortlet extends ActionPortlet {


    public void init(PortletConfig config) throws javax.portlet.PortletException {
        super.init(config);    //To change body of overridden methods use File | Settings | File Templates.
    }
}
