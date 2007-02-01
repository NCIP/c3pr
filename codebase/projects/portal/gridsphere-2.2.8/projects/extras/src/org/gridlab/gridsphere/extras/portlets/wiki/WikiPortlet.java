package org.gridlab.gridsphere.extras.portlets.wiki;

import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.jsr.ActionFormEvent;
import org.gridlab.gridsphere.provider.portlet.jsr.ActionPortlet;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.extras.services.wiki.SnipStorageService;
import org.gridlab.gridsphere.extras.services.wiki.Snip;
import org.gridlab.gridsphere.extras.services.wiki.PortletWikiRenderEngine;
import org.radeox.engine.context.BaseRenderContext;
import org.radeox.api.engine.context.RenderContext;
import org.radeox.api.engine.RenderEngine;

import javax.portlet.*;
import java.util.Map;
import java.util.List;
import java.util.Iterator;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: WikiPortlet.java,v 1.1.1.1 2007-02-01 20:07:51 kherm Exp $
 */

public class WikiPortlet extends ActionPortlet {

    private static PortletLog log = SportletLog.getInstance(WikiPortlet.class);
    private SnipStorageService snipservice = null;

    public void init(PortletConfig config) throws PortletException {
        super.init(config);

        try {
            snipservice = (SnipStorageService) createPortletService(SnipStorageService.class);
        } catch (PortletServiceException e) {
            log.error("Unable to initialize WikiService", e);
        }
        DEFAULT_VIEW_PAGE = "prepareView";
        DEFAULT_EDIT_PAGE = "wiki/showAdd.jsp";
        DEFAULT_HELP_PAGE = "wiki/help.jsp";
    }


    public void prepareView(RenderFormEvent event) throws PortletException {

        RenderRequest request = event.getRenderRequest();
        RenderResponse response = event.getRenderResponse();

        String val = request.getParameter("test");

        System.err.println("val = " + val);

        /*
        Map map = request.getParameterMap();
        Iterator it = map.keySet().iterator();
        System.err.println("printing map");
        while (it.hasNext()) {
            String name = (String)it.next();
            String val = (String)map.get(name);
            System.err.println("name= " + name + " val= " + val);
        }
        */

        String snipname = request.getParameter("snipname");
        PortletSession ps = request.getPortletSession();
        if (snipname==null) {

            snipname = (String)ps.getAttribute("snipname", PortletSession.PORTLET_SCOPE);
            // if it is not in session make it start
            if (snipname==null) {
                snipname = "start";
            }
        }

        String snipaction = (String)ps.getAttribute("snipaction");
        if (snipaction==null) {
            // if not defined it is show
            snipaction="show";
        }

        // remove from session
        ps.removeAttribute("snipname");
        ps.removeAttribute("snipaction");

        Snip snip;
        String content = "";
        String name = "";

        if (snipaction.equals("search")) {
            // get the hits
            List snips = (List)ps.getAttribute("snipsearch");
            for (int i=0;i<snips.size();i++) {
                Snip s = (Snip)snips.get(i);
                content += "- ["+s.getName()+"] \n";
                name = snipname;
            }
            name = "search";

        } else {
            snip = snipservice.getSnip(snipname);
            // so we have the name there if it is a new one
            if (snip.getOid()==null) {
                // is not in db set the name for fake 'edit' mode
                name = snipname;
                snipaction="create";
            } else {
                content = snip.getContent();
                name = snip.getName();
            }
        }

        // render the content
        RenderEngine e = new PortletWikiRenderEngine();
        RenderContext context = new BaseRenderContext();
        context.setRenderEngine(e);
        PortletURL portleturl = response.createRenderURL();
        ((PortletWikiRenderEngine)e).setLink(portleturl.toString());
        ((PortletWikiRenderEngine)e).setSnipStorage(snipservice);

        String result = e.render(content, context);

        request.setAttribute("snipaction", snipaction);
        request.setAttribute("snipname", name);
        request.setAttribute("snipraw", content);
        request.setAttribute("snipcontent", result);
        setNextState(request, "wiki/showSnip.jsp");
    }

    public void createSnip(ActionFormEvent event) throws PortletException {
        ActionRequest request = event.getActionRequest();
        String snipname = event.getTextFieldBean("snipname").getValue();
        String snipcontent = event.getTextAreaBean("snipcontent").getValue();
        Map userInfo = (Map) request.getAttribute(PortletRequest.USER_INFO);
        String username = (String)userInfo.get("user.name");
        snipservice.createSnip(username, snipname, snipcontent);
        PortletSession ps = request.getPortletSession(true);
        ps.setAttribute("snipaction", "show", PortletSession.PORTLET_SCOPE);
        ps.setAttribute("snipname", snipname, PortletSession.PORTLET_SCOPE);
        setNextState(request, "prepareView");
    }


    public void editSnip(ActionFormEvent event) throws PortletException {
        ActionRequest request = event.getActionRequest();
        String snipname = request.getParameter("snipname");
        PortletSession ps = request.getPortletSession(true);
        ps.setAttribute("snipaction", "edit", PortletSession.PORTLET_SCOPE);
        ps.setAttribute("snipname", snipname, PortletSession.PORTLET_SCOPE);
        setNextState(request, "prepareView");
    }

    public void updateSnip(ActionFormEvent event)  throws PortletException {
        ActionRequest request = event.getActionRequest();
        String snipname = event.getTextFieldBean("snipname").getValue();
        Snip snip = snipservice.getSnip(snipname);
        String content = event.getTextAreaBean("snipcontent").getValue();
        snip.setContent(content);
        snipservice.update(snip);
        PortletSession ps = request.getPortletSession(true);
        ps.setAttribute("snipaction", "show", PortletSession.PORTLET_SCOPE);
        ps.setAttribute("snipname", snipname, PortletSession.PORTLET_SCOPE);
        setNextState(request, "prepareView");
    }

    public void deleteSnip(ActionFormEvent event) throws PortletException {
        ActionRequest request = event.getActionRequest();
        String snipname = request.getParameter("snipname");
        snipservice.deleteSnip(snipname);
        PortletSession ps = request.getPortletSession(true);
        ps.setAttribute("snipaction", "show", PortletSession.PORTLET_SCOPE);
        ps.setAttribute("snipname", "start", PortletSession.PORTLET_SCOPE);
        setNextState(request, "prepareView");
    }

    public void searchSnip(ActionFormEvent event) throws PortletException {
        ActionRequest request = event.getActionRequest();
        String search = event.getTextFieldBean("search").getValue();
        List snips = snipservice.searchSnips(search);
        PortletSession ps = request.getPortletSession(true);
        ps.setAttribute("snipaction", "search", PortletSession.PORTLET_SCOPE);
        ps.setAttribute("snipsearch", snips, PortletSession.PORTLET_SCOPE);
        ps.setAttribute("snipname", search, PortletSession.PORTLET_SCOPE);
        setNextState(request, "prepareView");
    }

    public void cancelEdit(ActionFormEvent event) throws PortletException {
        ActionRequest request = event.getActionRequest();
        String snipname = event.getTextFieldBean("snipname").getValue();
        PortletSession ps = request.getPortletSession(true);
        ps.setAttribute("snipname", snipname, PortletSession.PORTLET_SCOPE);
        ps.setAttribute("snipaction", "show", PortletSession.PORTLET_SCOPE);
        setNextState(request, "prepareView");
    }

    public void cancelCreate(ActionFormEvent event) throws PortletException {
        ActionRequest request = event.getActionRequest();
        PortletSession ps = request.getPortletSession(true);
        ps.setAttribute("snipname", "start", PortletSession.PORTLET_SCOPE);
        ps.setAttribute("snipaction", "show", PortletSession.PORTLET_SCOPE);
        setNextState(request, "prepareView");
    }
}
