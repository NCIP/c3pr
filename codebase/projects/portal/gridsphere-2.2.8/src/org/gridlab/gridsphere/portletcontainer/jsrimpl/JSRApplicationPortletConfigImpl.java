/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: JSRApplicationPortletConfigImpl.java,v 1.1.1.1 2007-02-01 20:50:31 kherm Exp $
 */

package org.gridlab.gridsphere.portletcontainer.jsrimpl;

import org.gridlab.gridsphere.portlet.Portlet;
import org.gridlab.gridsphere.portlet.PortletWindow;
import org.gridlab.gridsphere.portletcontainer.ApplicationPortletConfig;
import org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.*;

import java.util.*;

/**
 * The <code>ApplicationSportletConfig</code> is the implementation of
 * <code>ApplicationPortletConfig</code> using Castor for XML to Java
 * bindings.
 */
public class JSRApplicationPortletConfigImpl implements ApplicationPortletConfig {

    private String id = "";
    private String portletName = "";
    private int expiration = 0;

    private Map markupModes = new HashMap();
    private List states = new ArrayList();

    /**
     * Constructs an instance of ApplicationSportletConfig
     */
    public JSRApplicationPortletConfigImpl(PortletApp app, PortletDefinition portletDef) {
        this.id = portletDef.getPortletClass().getContent();
        this.portletName = portletDef.getPortletName().getContent();
        if (portletDef.getExpirationCache() != null) {
            expiration = portletDef.getExpirationCache().getContent();
        }

        Supports[] supports = portletDef.getSupports();

        Map tmpMarkups = new HashMap();

        // defined portlet modes
        for (int i = 0; i < supports.length; i++) {
            List modesAllowed = new ArrayList();
            Supports s = (Supports) supports[i];
            org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode[] modes = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode[]) s.getPortletMode();
            for (int j = 0; j < modes.length; j++) {
                org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.PortletMode m = modes[j];
                modesAllowed.add(m.getContent());
            }
            String mimeType = (String) s.getMimeType().getContent();
            //modesAllowed.addAll(cModes);
            tmpMarkups.put(mimeType, modesAllowed);
        }

        // replace javax.portlet.PortletMode with GS modes

        Iterator it = tmpMarkups.keySet().iterator();
        while (it.hasNext()) {
            String mtype = (String) it.next();
            List tmpModes = (List) tmpMarkups.get(mtype);
            List modes = new ArrayList();
            for (int i = 0; i < tmpModes.size(); i++) {
                String m = (String) tmpModes.get(i);
                if (m.equalsIgnoreCase("CONFIG")) {
                    if (!modes.contains(Portlet.Mode.CONFIGURE)) modes.add(Portlet.Mode.CONFIGURE);
                } else {
                    try {
                        modes.add(Portlet.Mode.toMode(m));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Unknown mode defined in portlet.xml: " + m.toString());
                    }
                }
            }
            if (!modes.contains(Portlet.Mode.VIEW)) modes.add(Portlet.Mode.VIEW);
            markupModes.put(mtype, modes);
        }
        tmpMarkups = null;

        List customStatesList = new ArrayList();
        CustomWindowState[] customStates = app.getCustomWindowState();
        if (customStates != null) {
            for (int i = 0; i < customStates.length; i++) {
                customStatesList.add(customStates[i].getWindowState().getContent());
            }
        }

        // defined window states
        if (!customStatesList.isEmpty()) {
            for (int i = 0; i < supports.length; i++) {
                Supports s = (Supports) supports[i];
                org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.WindowState[] statesAllowed = (org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.WindowState[]) s.getWindowState();
                if (statesAllowed != null) {
                    for (int j = 0; j < statesAllowed.length; j++) {
                        org.gridlab.gridsphere.portletcontainer.jsrimpl.descriptor.WindowState w = statesAllowed[j];
                        if (customStatesList.contains(w.getContent())) states.add(PortletWindow.State.toState(w.getContent()));
                    }
                }
            }
        }
        states.add(PortletWindow.State.MAXIMIZED);
        states.add(PortletWindow.State.MINIMIZED);
        states.add(PortletWindow.State.RESIZING);

    }

    /**
     * Returns the portlet application id
     *
     * @return the portlet application id
     */
    public String getApplicationPortletID() {
        return id;
    }

    /**
     * Returns the portlet application name
     *
     * @return the portlet application name
     */
    public String getPortletName() {
        return portletName;
    }

    /**
     * Sets the name of a PortletApplication
     *
     * @param portletName name of a PortletApplication
     */
    public void setPortletName(String portletName) {
        this.portletName = portletName;
    }

    /**
     * Returns the allowed window states supported by this portlet
     *
     * @return the <code>List</code> of
     *         <code>PortletWindow.State</code> elements allowed for this portlet
     */
    public List getAllowedWindowStates() {

        // TODO support custom states
        /*
        CustomWindowState[] customStates = portletApp.getCustomWindowState();
        if (customStates != null) {
            for (int i = 0; i < customStates.length; i++) {
                String newstate = customStates[i].getWindowState().getContent();
                PortletWindow.State s = PortletWindow.State.toState(newstate);
                states.add(s);
            }
        }
        */
        return Collections.unmodifiableList(states);
    }

    /**
     * Returns the supported modes for this portlet
     *
     * @return the supported modes for this portlet
     */
    public List getSupportedModes(String markup) {
        Iterator it = markupModes.keySet().iterator();
        while (it.hasNext()) {
            String mimeType = (String) it.next();
            int idx1 = mimeType.indexOf(markup);
            int idx2 = markup.indexOf(mimeType);
            if ((idx1 > 0) || (idx2 > 0) || (mimeType.equalsIgnoreCase(markup))) {
                return (List) markupModes.get(mimeType);
            }
        }
        return new ArrayList();
    }

    /**
     * returns the amount of time in seconds that a portlet's content should be cached
     *
     * @return the amount of time in seconds that a portlet's content should be cached
     */
    public long getCacheExpires() {
        return expiration;
    }

}

