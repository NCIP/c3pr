/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: GridSphereEventImpl.java,v 1.1.1.1 2007-02-01 20:50:27 kherm Exp $
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.layout.event.PortletComponentEvent;
import org.gridlab.gridsphere.portlet.*;
import org.gridlab.gridsphere.portlet.impl.*;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * The <code>GridSphereEventImpl</code> is an implementation of the <code>GridSphereEvent</code> interface.
 * <p/>
 * A <code>GridSphereEvent</code> represents a general portlet container
 * event. The <code>GridSphereEvent</code> is passed into components that
 * need to access the <code>PortletRequest</code>
 * <code>PortletResponse</code> objects, such as the layout components.
 */
public class GridSphereEventImpl implements GridSphereEvent {

    protected static PortletLog log = SportletLog.getInstance(GridSphereEventImpl.class);
    protected SportletRequest portletRequest;
    protected SportletResponse portletResponse;
    protected PortletContext portletContext;

    protected String portletComponentID = null;
    protected DefaultPortletAction action = null;
    protected PortletMessage message = null;

    protected Stack events = null;

    public GridSphereEventImpl(PortletContext ctx, HttpServletRequest req, HttpServletResponse res) {

        portletRequest = new SportletRequest(req);
        portletResponse = new SportletResponse(res, portletRequest);
        portletContext = ctx;

        events = new Stack();

        // Set the context path and servlet path to be used when creating url's
        req.setAttribute(SportletProperties.CONTEXT_PATH, req.getContextPath()); // contextPath;
        req.setAttribute(SportletProperties.SERVLET_PATH, req.getServletPath());

        /*
        String[] portletNames = req.getParameterValues("portletName");
        if ( portletNames != null ) {
        System.err.println("have a TCK POrtlet!!");
        StringTokenizer tokenizer;
        if (portletNames.length > 1) System.err.println("Arggh multiple portlets!!");
        for (int i = 0; i < portletNames.length; i++) {
        tokenizer =  new StringTokenizer(portletNames[i], "/");
        String appName = tokenizer.nextToken();
        portletComponentID = tokenizer.nextToken();
        }


        } else {
        */
        portletComponentID = req.getParameter(SportletProperties.COMPONENT_ID);
        if (portletComponentID == null) {
            log.debug("Received a null component ID");
            portletComponentID = "";
        }


        log.debug("Received cid= " + portletComponentID);

        action = createAction(portletRequest);

        log.debug("Received action=" + action);
        /* This is where a DefaultPortletMessage gets put together if one exists */
        String messageStr = portletRequest.getParameter(SportletProperties.DEFAULT_PORTLET_MESSAGE);
        if (messageStr != null) {
            log.debug("Received message: " + messageStr);
            message = new DefaultPortletMessage(messageStr);
        }

    }

    public static DefaultPortletAction createAction(Object request) {
        /* This is where a DefaultPortletAction gets put together if one exists */
        DefaultPortletAction myaction = null;
        Enumeration e = null;
        String name, newname, value;
        String actionStr = null;
        if (request instanceof PortletRequest) {
            actionStr = ((PortletRequest)request).getParameter(SportletProperties.DEFAULT_PORTLET_ACTION);
        }
        if (request instanceof javax.portlet.PortletRequest) {
            actionStr = ((javax.portlet.PortletRequest)request).getParameter(SportletProperties.DEFAULT_PORTLET_ACTION);
        }
        if (actionStr != null) {



            myaction = new DefaultPortletAction(actionStr);
            String prefix = null;
            if (request instanceof PortletRequest) {
                prefix = ((PortletRequest)request).getParameter(SportletProperties.PREFIX);
                e =  ((PortletRequest)request).getParameterNames();
            }
            if (request instanceof javax.portlet.PortletRequest) {
                prefix = ((javax.portlet.PortletRequest)request).getParameter(SportletProperties.PREFIX);
                e = ((javax.portlet.PortletRequest)request).getParameterNames();
            }
            if ((prefix != null) && (e != null)) {
                while (e.hasMoreElements()) {
                    name = ((String) e.nextElement());
                    if (name.startsWith(prefix)) {
                        newname = name.substring(prefix.length() + 1);
                        value = "";
                        if (request instanceof PortletRequest) {
                            value =  ((PortletRequest)request).getParameter(name);
                        }
                        if (request instanceof javax.portlet.PortletRequest) {
                            value = ((javax.portlet.PortletRequest)request).getParameter(name);
                        }
                        //newname = decodeUTF8(newname);
                        //value = decodeUTF8(newname);
                        myaction.addParameter(newname, value);
                    }
                }
            }
        } else {
            if (request instanceof PortletRequest) {
                e =  ((PortletRequest)request).getParameterNames();
            }
            if (request instanceof javax.portlet.PortletRequest) {
                e = ((javax.portlet.PortletRequest)request).getParameterNames();
            }
            if (e != null) {

                /// Check to see if action is of form action_name generated by submit button
                while (e.hasMoreElements()) {
                    name = (String) e.nextElement();
                    if (name.startsWith(SportletProperties.DEFAULT_PORTLET_ACTION)) {
                        // check for parameter names and values

                        name = name.substring(SportletProperties.DEFAULT_PORTLET_ACTION.length() + 1);

                        StringTokenizer st = new StringTokenizer(name, "&");
                        if (st.hasMoreTokens()) {
                            newname = st.nextToken();
                        } else {
                            newname = "";
                        }
                        myaction = new DefaultPortletAction(newname);
                        log.debug("Received " + myaction);
                        String paramName;
                        String paramVal = "";
                        Map tmpParams = new HashMap();
                        String prefix = "";
                        while (st.hasMoreTokens()) {
                            // now check for "=" separating name and value
                            String namevalue = st.nextToken();
                            int hasvalue = namevalue.indexOf("=");
                            if (hasvalue > 0) {
                                paramName = namevalue.substring(0, hasvalue);
                                paramVal = namevalue.substring(hasvalue + 1);
                                if (paramName.equals(SportletProperties.PREFIX)) {
                                    prefix = paramVal;
                                } else {
                                    tmpParams.put(paramName, paramVal);
                                }
                            } else {
                                tmpParams.put(namevalue, paramVal);
                            }
                        }
                        // put unprefixed params in action
                        Iterator it = tmpParams.keySet().iterator();
                        while (it.hasNext()) {
                            String n = (String) it.next();
                            String v = (String) tmpParams.get(n);
                            if (!prefix.equals("")) {
                                n = n.substring(prefix.length() + 1);
                            }

                            myaction.addParameter(n, v);
                        }
                    }
                }
            }
        }
        return myaction;
    }

    public PortletRequest getPortletRequest() {
        return portletRequest;
    }

    public PortletResponse getPortletResponse() {
        return portletResponse;
    }

    public PortletContext getPortletContext() {
        return portletContext;
    }

    public DefaultPortletAction getAction() {
        return action;
    }

    public boolean hasAction() {
        return (action != null);
    }

    public boolean hasMessage() {
        return false;
    }

    public PortletMessage getMessage() {
        return message;
    }

    public String getPortletComponentID() {
        return portletComponentID;
    }

    public void addNewRenderEvent(PortletComponentEvent evt) {
        if (evt != null) events.push(evt);
    }

    public PortletComponentEvent getLastRenderEvent() {
        return (events.isEmpty() ? null : (PortletComponentEvent) events.pop());
    }
}
