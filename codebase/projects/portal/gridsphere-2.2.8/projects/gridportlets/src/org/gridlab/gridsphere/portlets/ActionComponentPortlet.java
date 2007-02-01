/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentPortlet.java,v 1.1.1.1 2007-02-01 20:39:46 kherm Exp $
 */
package org.gridlab.gridsphere.portlets;

import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.impl.SportletProperties;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.DefaultPortletAction;
import org.gridlab.gridsphere.portlet.jsrimpl.PortletRequestImpl;
import org.gridlab.gridsphere.portlet.jsrimpl.PortletResponseImpl;
import org.gridlab.gridsphere.portlet.jsrimpl.ActionRequestImpl;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.services.ui.*;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.provider.portletui.beans.MessageBoxBean;
import org.gridlab.gridsphere.portletcontainer.impl.GridSphereEventImpl;

import javax.portlet.*;
import java.io.*;
import java.util.*;

import javax.portlet.*;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;

public class ActionComponentPortlet extends ActionPortlet {

    public static final String DISPLAY_ACTION_FORM = "actionComponentDisplayForm";
    public static final String ACTION_COMPONENT_BEAN_ID = "actionComponentBeanId";
    public static final String ACTION_COMPONENT_JSP = "/jsp/action/ActionComponent.jsp";
    private static PortletLog log = SportletLog.getInstance(ActionComponentPortlet.class);

    private Hashtable actionCompIds = new Hashtable(3);
    private Hashtable portletModeComps = new Hashtable(3);

    public static String getActionComponentBeanId(HttpServletRequest request) {
        return (String)request.getAttribute(ACTION_COMPONENT_BEAN_ID);
    }

    public static boolean displayActionForm(HttpServletRequest request) {
        Boolean value = (Boolean)request.getAttribute(DISPLAY_ACTION_FORM);
        if (value == null) {
            return true;
        }
        return value.booleanValue();
    }

    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        DEFAULT_VIEW_PAGE = null;
        DEFAULT_EDIT_PAGE = null;
        DEFAULT_CONFIGURE_PAGE = null;
        DEFAULT_HELP_PAGE = null;
        setViewModeComp(DefaultViewComp.class);
        setEditModeComp(DefaultEditComp.class);
        setHelpModeComp(DefaultHelpComp.class);
        setConfigModeComp(DefaultConfigComp.class);
        // Just making sure action component factory is started
        try {
            createPortletService(ActionComponentFactory.class);
        } catch (PortletServiceException e) {
            log.error("Unable to get instance of action component factory", e);
        }
    }

    /**
     * Uses getNextState to either render a JSP or invoke the specified render action method
     *
     * @param request the portlet request
     * @param response the portlet response
     * @throws PortletException if a portlet exception occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        log.debug("ActionComponentPortlet.doView()");
        doMode(request, response);
    }

    public void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        log.debug("ActionComponentPortlet.doEdit()");
        doMode(request, response);
    }

    public void doConfigure(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        log.debug("ActionComponentPortlet.doConfigure()");
        doMode(request, response);
    }

    public void doHelp(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        log.debug("ActionComponentPortlet.doHelp()");
        doMode(request, response);
    }

    protected void doMode(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        String next = getNextState(request);
        log.debug("ActionComponentPortlet.doMode() Next state is" + next);
        if (next == null) {
            next = performRender(request, response);
        } else if (! next.endsWith(".jsp") )  {
            next = performAction(request, response, next, new HashMap());
        }
        log.debug("ActionComponentPortlet.doMode() Next page is " + next);
        setNextState(request, next);
        doViewJSP(request, response, next);

        removeTagBeans(request);
        removeNextTitle(request);
        removeNextState(request);
    }

    public void doViewJSP(RenderRequest request, RenderResponse response, String jsp) throws PortletException {

        String cid = (String) request.getAttribute(SportletProperties.COMPONENT_ID);
        if (cid == null) {
            request.setAttribute(SportletProperties.COMPONENT_ID, getUniqueId());
        }

        log.debug("ActionComponentPortlet.doViewJSP() cid " + cid);

        String id = request.getPortletSession().getId();
        String compId = (String)actionCompIds.get(id);
        request.setAttribute(SportletProperties.GP_COMPONENT_ID, compId);

        log.debug("ActionComponentPortlet.doViewJSP() compId " + compId);

        try {
            ActionComponentFrame bean = getActiveModeFrame(request);
            if (bean == null) {

                super.doViewJSP(request, response, jsp);

            } else {

                ServletContext beanContext = bean.getServletContext();

                boolean createForm = !bean.containsForms();

                response.setContentType("text/html; charset=utf-8");

                try {

                    if (createForm) {
                        startForm(request, response, compId);
                    }

                    messageBox(response, bean);

                    if (beanContext == null) {
                        super.doViewJSP(request, response, jsp);
                    } else {
                        log.debug("Including JSP page:" + jsp);

                        if (request instanceof PortletRequestImpl) {

                            RequestDispatcher dispatcher = null;

                            if (jsp.startsWith("/")) {
                                dispatcher = beanContext.getRequestDispatcher(jsp);
                            } else {
                                dispatcher = beanContext.getRequestDispatcher("/jsp/" + jsp);
                            }

                            try {
                                ((PortletRequestImpl) request).setIncluded(true);
                                dispatcher.include((PortletRequestImpl)request, (PortletResponseImpl)response);
                            } catch (java.io.IOException e) {
                                throw e;
                            } catch (javax.servlet.ServletException e) {
                                if (e.getRootCause() != null) {
                                    throw new PortletException(e.getRootCause());
                                } else {
                                    throw new PortletException(e);
                                }
                            } finally {
                                ((PortletRequestImpl) request).setIncluded(false);
                            }
                        } else {

                            if (jsp.startsWith("/")) {
                                getPortletContext().getRequestDispatcher(jsp).include(request, response);
                            } else {
                                getPortletContext().getRequestDispatcher("/jsp/" + jsp).include(request, response);
                            }

                        }

                    }

                    if (createForm) {
                        endForm(response);
                    }

                } catch (Exception e) {
                    request.setAttribute(SportletProperties.PORTLETERROR + request.getAttribute(SportletProperties.PORTLETID), e);
                    throw new PortletException(e);
                }
            }

            request.removeAttribute(SportletProperties.GP_COMPONENT_ID);
            actionCompIds.remove(id);
        } catch (PortletException e) {
            if (cid == null) {
                request.removeAttribute(SportletProperties.COMPONENT_ID);
            }
            request.removeAttribute(SportletProperties.GP_COMPONENT_ID);
            actionCompIds.remove(id);
            throw e;
        } catch (Exception e) {
            if (cid == null) {
                request.removeAttribute(SportletProperties.COMPONENT_ID);
            }
            request.removeAttribute(SportletProperties.GP_COMPONENT_ID);
            actionCompIds.remove(id);
            throw new PortletException(e);
        }

        if (cid == null) {
            request.removeAttribute(SportletProperties.COMPONENT_ID);
        }
    }

    protected static void messageBox(RenderResponse res, ActionComponentFrame bean) throws JspException {
        ActionComponent comp = bean.getActiveComponent();
        if (comp != null) {
            MessageBoxBean messageBoxBean = (MessageBoxBean)comp.getTagBeans().get("messageBox");
            if (messageBoxBean != null) {
                try {
                    PrintWriter out = res.getWriter();
                    out.print(messageBoxBean.toEndString());
                } catch (Exception e) {
                    throw new JspException(e.getMessage());
                }
            }
        }
    }

    protected void startForm(RenderRequest req, RenderResponse res, String compId) throws JspTagException {

        try {
            PrintWriter out = res.getWriter();

            out.print("<form ");
            out.print("action=\"");

            // TODO: This should be createActionURL, but require solution for
            // TODO: passing action request attributes to render request
            // TODO: In order to display results properly in JSP
            out.print(createJSRActionURI(req, res.createRenderURL()));
            //out.print(createJSRActionURI(req, res.createActionURL()));

            out.print("\" method=\"");
            out.print("POST");
            out.print("\"");

            out.print(" name=\"" + compId + "\"");

            out.println(">");
            // add JS info
            out.println("<input name=\"JavaScript\" value=\"\" type=\"hidden\">");

            out.println("<script language=\"JavaScript\">");
            out.println("document." + compId + ".JavaScript.value = \"enabled\";");
            out.println("</script>");

        } catch (Exception e) {
            log.error("Unable to start form", e);
            throw new JspTagException(e.getMessage());
        }
    }

    protected static String createJSRActionURI(RenderRequest req, PortletURL url) throws JspException {
        // action is a required attribute except for FormTag
        String windowState = req.getWindowState().toString();
        String portletMode = req.getPortletMode().toString();
        PortletURL actionURL = url;
        if (windowState != null) {
            WindowState state = new WindowState(windowState);
            try {
                //actionURL = res.createRenderURL();
                log.debug("set state to:" + state);
                actionURL.setWindowState(state);
            } catch (WindowStateException e) {
                throw new JspException("Unknown window state in renderURL tag: " + windowState);
            }
        }
        if (portletMode != null) {
            PortletMode mode = new PortletMode(portletMode);
            try {
                //actionURL = res.createRenderURL();
                actionURL.setPortletMode(mode);
                log.debug("set mode to:" + mode);
            } catch (PortletModeException e) {
                throw new JspException("Unknown portlet mode in renderURL tag: " + portletMode);
            }
        }

        log.debug("printing action  URL = " + actionURL.toString());
        return actionURL.toString();
    }

    protected static void endForm(RenderResponse res) throws JspTagException {

        try {
            PrintWriter out = res.getWriter();
            // end form
            out.print("</form>");
        } catch (Exception e) {
            throw new JspTagException(e.getMessage());
        }
    }

    protected void doDispatch(RenderRequest request,
                              RenderResponse response) throws PortletException, java.io.IOException {

        log.debug("ActionComponentPortlet.doDispatch() cid is " + getUniqueId());

        // TODO: This check should be not be performed, but require solution for
        // TODO: passing action request attributes to render request
        // TODO: In order to display results properly in JSP
        if (! ( request instanceof PortletRequestImpl) ) {
            DefaultPortletAction action = GridSphereEventImpl.createAction(request);
            if (action != null) log.debug("ActionComponentPortlet.doDispatch() action " + action);
            if (action != null && action.getName().startsWith(getUniqueId())) {
                processAction(action, request, response);
            }
        }

        WindowState state = request.getWindowState();
        try {
            super.doDispatch(request, response);
        } catch (PortletException e) {
            if (!state.equals(WindowState.MINIMIZED)) {
                PortletMode mode = request.getPortletMode();
                if (mode.toString().equalsIgnoreCase("CONFIG")) {
                    doConfigure(request, response);
                    return;
                }
            }
            throw e;
        }

    }

    protected String performRender(PortletRequest request, PortletResponse response) throws PortletException {

        String cid = (String) request.getAttribute(SportletProperties.COMPONENT_ID);
        if (cid == null) {
            request.setAttribute(SportletProperties.COMPONENT_ID, getUniqueId());
        }

        log.debug("ActionComponentPortlet.performRender() cid is " + cid);
        ActionComponentFrame bean = getActiveModeFrame(request);
        bean.init(request, response);
        bean.load(request, response);
        String jspPage = null;
        String id = request.getPortletSession().getId();
        try {
            bean.doRender(new HashMap(0));
            String compId = bean.getActiveComponent().getComponentId();
            log.debug("ActionComponentPortlet.performRender() compId " + compId);
            request.setAttribute(SportletProperties.GP_COMPONENT_ID, compId);
            bean.store();
            request.removeAttribute(SportletProperties.GP_COMPONENT_ID);
            actionCompIds.put(id, compId);
            jspPage = bean.getPage();
        } catch (PortletException e) {
            String msg = "Unable to perform render ";
            log.error(msg, e);
            request.removeAttribute(SportletProperties.GP_COMPONENT_ID);
            actionCompIds.remove(id);
            if (cid == null) {
                request.removeAttribute(SportletProperties.COMPONENT_ID);
            }
            throw e;
        } catch (Exception e) {
            String msg = "Unable to perform render ";
            log.error(msg, e);
            request.removeAttribute(SportletProperties.GP_COMPONENT_ID);
            actionCompIds.remove(id);
            if (cid == null) {
                request.removeAttribute(SportletProperties.COMPONENT_ID);
            }
            throw new PortletException(msg);
        }

        if (cid == null) {
            request.removeAttribute(SportletProperties.COMPONENT_ID);
        }

        return jspPage;
    }

    public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {

        DefaultPortletAction action = null;
        if (! (actionRequest instanceof ActionRequestImpl) ) {
            action = GridSphereEventImpl.createAction(actionRequest);
        } else {
            action = (DefaultPortletAction) actionRequest.getAttribute(SportletProperties.ACTION_EVENT);
        }

        processAction(action, actionRequest, actionResponse);
    }

    public void processAction(DefaultPortletAction action, PortletRequest request, PortletResponse response) throws PortletException {

        log.debug("ActionComponentPortlet.processAction() cid is " + getUniqueId());

        String methodName = action.getName();
        Map parameters = action.getParameters();

        String next = performAction(request, response, methodName, parameters);
        setNextState(request, next);
    }

    public String performAction(PortletRequest request,
                                PortletResponse response,
                                String method,
                                Map parameters) throws PortletException {

        log.debug("ActionComponentPortlet.performAction( " + method + ")");


        // Attempt to perform method
        int index = method.indexOf("%");
        if (index < -1) {
            throw new PortletException("Invalid action " + method);
        }

        String cid = (String) request.getAttribute(SportletProperties.COMPONENT_ID);
        if (cid == null) {
            request.setAttribute(SportletProperties.COMPONENT_ID, getUniqueId());
        }

        log.debug("ActionComponentPortlet.performAction() cid is " + cid);

        ActionComponentFrame bean = getActiveModeFrame(request);
        bean.init(request, response);
        bean.load(request, response);
        String jspPage = null;
        String id = request.getPortletSession().getId();
        log.debug("ActionComponentPortlet.performAction() Portlet session id is " + id);
        try {
            String action = method.substring(index+1);
            bean.doAction(action, parameters);
            String compId = bean.getActiveComponent().getComponentId();
            log.debug("ActionComponentPortlet.performAction() compId is " + compId);
            request.setAttribute(SportletProperties.GP_COMPONENT_ID, compId);
            bean.store();
            request.removeAttribute(SportletProperties.GP_COMPONENT_ID);
            actionCompIds.put(id, compId);
            jspPage = bean.getPage();
        } catch (PortletException e) {
            e.printStackTrace();
            String msg = "Unable to perform render ";
            log.error(msg, e);
            if (cid == null) {
                request.removeAttribute(SportletProperties.COMPONENT_ID);
            }
            request.removeAttribute(SportletProperties.GP_COMPONENT_ID);
            actionCompIds.remove(id);
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "Unable to perform render ";
            log.error(msg, e);
            if (cid == null) {
                request.removeAttribute(SportletProperties.COMPONENT_ID);
            }
            request.removeAttribute(SportletProperties.GP_COMPONENT_ID);
            actionCompIds.remove(id);
            throw new PortletException(msg);
        }

        if (cid == null) {
            request.removeAttribute(SportletProperties.COMPONENT_ID);
        }

        return jspPage;
    }

    public Class getViewModeComp() {
        return (Class)portletModeComps.get(PortletMode.VIEW);
    }

    public void setViewModeComp(Class actionClass) {
        portletModeComps.put(PortletMode.VIEW, actionClass);
    }

    public Class getEditModeComp() {
        return (Class)portletModeComps.get(PortletMode.EDIT);
    }

    public void setEditModeComp(Class actionClass) {
        portletModeComps.put(PortletMode.EDIT, actionClass);
    }

    public Class getConfigModeComp() {
        return (Class)portletModeComps.get("config");
    }

    public void setConfigModeComp(Class actionClass) {
        portletModeComps.put("config", actionClass);
    }

    public Class getHelpModeComp() {
        return (Class)portletModeComps.get(PortletMode.HELP);
    }

    public void setHelpModeComp(Class actionClass) {
        portletModeComps.put(PortletMode.HELP, actionClass);
    }

    private ActionComponentFrame getActiveModeFrame(PortletRequest portletRequest) {
        PortletMode portletMode = portletRequest.getPortletMode();
        return getPortletModeFrame(portletRequest, portletMode);
    }

    private ActionComponentFrame getPortletModeFrame(PortletRequest portletRequest,
                                                       PortletMode portletMode) {
        PortletSession session = portletRequest.getPortletSession();
        String cid = (String) portletRequest.getAttribute(SportletProperties.COMPONENT_ID);
        if (cid == null) {
            cid = getUniqueId();
        }
        String beanId = cid + ".MODE_" + portletMode.toString();
        ActionComponentFrame bean = (ActionComponentFrame)session.getAttribute(beanId);
        if (bean == null) {
            bean = createActionComponentFrame(portletRequest, cid );
            Class formClass = (Class)portletModeComps.get(portletMode);
            bean.setNextState(formClass);
            session.setAttribute(beanId, bean);
        }
        return bean;
    }

    protected ActionComponentFrame createActionComponentFrame(PortletRequest portletRequest, String cid) {
        return new ActionComponentFrame(portletRequest, cid);
    }
}
