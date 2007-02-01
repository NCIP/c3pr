/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PortletRegistry.java,v 1.1.1.1 2007-02-01 20:50:26 kherm Exp $
 */
package org.gridlab.gridsphere.portletcontainer;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.*;

/**
 * The <code>PortletRegistry</code> is simply a static <code>Hashtable</code> that keeps track
 * of all the application portlets known to the container.
 */
public class PortletRegistry {

    private static PortletRegistry instance = new PortletRegistry();
    private static PortletLog log = SportletLog.getInstance(PortletRegistry.class);

    private static Map allApplicationPortlets = new Hashtable();

    /**
     * Default instantiation disallowed
     */
    private PortletRegistry() {
    }

    /**
     * Return an instance of PortletRegistry
     */
    public static PortletRegistry getInstance() {
        return instance;
    }

    /**
     * Adds an application portlet to the registry
     *
     * @param appPortlet an <code>ApplicationPortlet</code>
     */
    public void addApplicationPortlet(ApplicationPortlet appPortlet) {
        String key = appPortlet.getApplicationPortletID();
        if (key.indexOf("#") < 0) {
            key = appPortlet.getWebApplicationName() + "#" + appPortlet.getApplicationPortletID();
        }
        if (allApplicationPortlets.get(key) != null) {
            log.debug("Replacing existing app portlet: " + key);
        }
        allApplicationPortlets.put(key, appPortlet);
    }

    /**
     * Removes an application portlet from the registry
     *
     * @param applicationPortlet the application portlet
     */
    public void removeApplicationPortlet(ApplicationPortlet applicationPortlet) {
        allApplicationPortlets.remove(applicationPortlet.getApplicationPortletID());
    }

    /**
     * Return the application portlet with the corresponding id
     *
     * @param applicationPortletID the application portlet id
     * @return an application portlet
     */
    public ApplicationPortlet getApplicationPortlet(String applicationPortletID) {
        int idx = applicationPortletID.indexOf("#");
        //String portletName = "";
        //String webapp = "";
        if (idx > 0) {
            //webapp = applicationPortletID.substring(0, idx);
            //portletName = applicationPortletID.substring(idx+1);
            //System.err.println("webapp= " + webapp + " pname= " + portletName);
            return (ApplicationPortlet)allApplicationPortlets.get(applicationPortletID);
        }
        Collection coll = allApplicationPortlets.values();
        Iterator it = coll.iterator();
        while (it.hasNext()) {
            ApplicationPortlet app = (ApplicationPortlet) it.next();
            if (app.getApplicationPortletID().equals(applicationPortletID)) return app;
            if (app.getApplicationPortletClassName().equals(applicationPortletID)) return app;
        }
        log.debug("Unable to find " + applicationPortletID + " in registry");
        return null;
    }

 
    /**
     * Returns all application portlets from the registry
     *
     * @return the collection of application portlets
     */
    public Collection getAllApplicationPortlets() {
        return allApplicationPortlets.values();
    }


    /**
     * Returns all application portlets from the supplied portlet web application
     *
     * @param webApplicationName the portlet web application name
     * @return the list of application portlets
     */
    public List getApplicationPortlets(String webApplicationName) {
        List webappPortlets = new Vector();
        Set set = allApplicationPortlets.keySet();
        ApplicationPortlet appPortlet;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String concretePortletID = (String) it.next();
            appPortlet = (ApplicationPortlet) allApplicationPortlets.get(concretePortletID);
            if (appPortlet.getWebApplicationName().equals((webApplicationName))) {
                webappPortlets.add(appPortlet);
            }
        }
        return webappPortlets;
    }

    /**
     * Returns the application portlet id given a concrete portlet id
     *
     * @param concretePortletID the concrete portlet id
     * @return the application portlet id or an empty string
     */
    public static final String getApplicationPortletID(String concretePortletID) {
        int i = concretePortletID.lastIndexOf(".");
        // do nothing if id is on form <web app name>#<portlet name>
        if (concretePortletID.indexOf("#") > 0) return concretePortletID;
        if (i < 0) return concretePortletID;
        // check to see if it has number at the end
        String numStr = concretePortletID.substring(i + 1);
        String appID = "";
        try {
            Integer.parseInt(numStr);
            appID = concretePortletID.substring(0, i);
        } catch (NumberFormatException e) {
            appID = concretePortletID;
        }
        return appID;
    }

    public static final void logRegistry() {
        log.debug("Displaying Portlet registry contents:\n");
        Iterator it = allApplicationPortlets.keySet().iterator();
        while (it.hasNext()) {
            String appID = (String) it.next();
            ApplicationPortlet appPortlet = (ApplicationPortlet) allApplicationPortlets.get(appID);
            log.debug("\tApplication portlet : " + appPortlet.getApplicationPortletID() + "\n");
            log.debug("\t" + appPortlet + "\n");
            List concPortlets = appPortlet.getConcretePortlets();
            Iterator concIt = concPortlets.iterator();
            while (concIt.hasNext()) {
                ConcretePortlet conc = (ConcretePortlet) concIt.next();
                log.debug("\t\tConcrete portlet : " + conc.getConcretePortletID() + "\n");
                log.debug("\t" + conc + "\n");
            }
        }


    }
}
