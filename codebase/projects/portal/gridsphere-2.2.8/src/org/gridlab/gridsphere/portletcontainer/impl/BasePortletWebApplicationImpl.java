/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: BasePortletWebApplicationImpl.java,v 1.1.1.1 2007-02-01 20:50:27 kherm Exp $
 */
package org.gridlab.gridsphere.portletcontainer.impl;

import org.gridlab.gridsphere.layout.PortletTabRegistry;
import org.gridlab.gridsphere.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletGroup;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.PortletRole;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portletcontainer.PortletWebApplication;
import org.gridlab.gridsphere.services.core.security.group.impl.descriptor.PortletGroupDescriptor;
import org.gridlab.gridsphere.services.core.security.group.GroupManagerService;
import org.gridlab.gridsphere.services.core.security.role.impl.descriptor.PortletRoleDescriptor;
import org.gridlab.gridsphere.services.core.security.role.RoleManagerService;

import javax.servlet.ServletContext;
import java.io.File;
import java.net.URLEncoder;
import java.util.*;

/**
 * The <code>PortletWebApplicationImpl</code> is an implementation of a <code>PortletWebApplication</code> that
 * represents a collection of portlets contained in a packaged WAR file. Currently
 * under development is the notion of dynamically managing portlet web applications.
 */
public abstract class BasePortletWebApplicationImpl implements PortletWebApplication {

    private PortletLog log = SportletLog.getInstance(BasePortletWebApplicationImpl.class);

    protected Map appPortlets = new Hashtable();

    protected boolean isJSR = false;

    protected String webApplicationName = "Unknown portlet web application";
    protected String webAppDescription = "Unknown portlet web application description";
    protected RoleManagerService roleManager = null;
    protected GroupManagerService groupManager = null;

    /**
     * Constructs an instance of a BasePortletWebApplicationImpl from a supplied
     * <code>ServletContext</code>
     *
     * @param context the <code>ServletContext</code>
     */
    public BasePortletWebApplicationImpl(ServletContext context) throws PortletException {
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        try {
            roleManager = (RoleManagerService)factory.createPortletService(RoleManagerService.class, true);
            groupManager = (GroupManagerService)factory.createPortletService(GroupManagerService.class, true);
        } catch (PortletServiceException e) {
            throw new PortletException("Unable to get instance of GroupManagerService and/or RoleManagerService", e);
        }
    }

    public abstract void init();

    /**
     * Under development. A portlet web application can unregister itself from the application server
     */
    public abstract void destroy();

    /**
     * Loads collection of portlets from portlet descriptor file using the associated <code>ServletContext</code>
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected abstract void loadPortlets(ServletContext ctx, ClassLoader loader) throws PortletException;

    /**
     * Loads in a layout descriptor file from the associated servlet context
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected void loadLayout(ServletContext ctx, PortletGroup group) throws PortletException {
        // load in the portlet.xml file
        String layoutXMLfile = ctx.getRealPath("/WEB-INF/layout.xml");
        File fin = new File(layoutXMLfile);
        String pgroupName = "unknown group";
        if (fin.exists()) {
            try {
                pgroupName = URLEncoder.encode(group.getName(), "UTF-8");
                PortletTabRegistry.copyFile(fin, group);
                log.info("Loaded a layout descriptor " + pgroupName);
            } catch (Exception e) {
                throw new PortletException("Unable to deserialize layout.xml for: " + pgroupName + "!", e);
            }
        } else {
            log.debug("Did not find layout.xml for: " + ctx.getServletContextName());
        }
    }

    /**
     * Loads in a group descriptor file from the associated servlet context
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected void loadGroup(ServletContext ctx) throws PortletException {
        // load in the portlet.xml file
        String groupXMLfile = ctx.getRealPath("/WEB-INF/group.xml");
        File f = new File(groupXMLfile);
        if (f.exists()) {
            try {
                PortletGroupDescriptor groupDescriptor = new PortletGroupDescriptor(groupXMLfile);
                PortletGroup group = groupDescriptor.getPortletGroup();
                PortletGroup g = groupManager.getGroup(group.getName());
                if (g == null) {
                    log.info("Saving group: " + group.getName());
                    groupManager.saveGroup(group);
                    g = groupManager.getGroup(group.getName());
                }
                log.info("Loaded a group descriptor " + g.getOid());
                // now load layout
                loadLayout(ctx, g);
            } catch (Exception e) {
                throw new PortletException("Unable to deserialize group.xml for: " + webApplicationName, e);
            }
        } else {
            log.debug("Did not find group.xml for: " + ctx.getServletContextName());
        }
    }

    /**
     * Loads in a role descriptor file from the associated servlet context
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected void loadRoles(ServletContext ctx) throws PortletException {
        String roleXMLfile = ctx.getRealPath("/WEB-INF/roles.xml");
        File f = new File(roleXMLfile);
        if (f.exists()) {
            try {
                PortletRoleDescriptor roleDescriptor = new PortletRoleDescriptor(roleXMLfile);
                List portletRoles = roleDescriptor.getPortletRoles();
                Iterator it = portletRoles.iterator();
                while (it.hasNext()) {
                    PortletRole role = (PortletRole)it.next();
                    PortletRole existingRole = roleManager.getRole(role.getName());
                    if (existingRole != null) {
                        existingRole.setDescription(role.getDescription());
                        roleManager.saveRole(existingRole);
                    } else {
                        roleManager.saveRole(role);
                    }
                }
                log.info("Loaded a role descriptor");
            } catch (Exception e) {
                throw new PortletException("Unable to deserialize role.xml for: " + webApplicationName, e);
            }
        } else {
            log.debug("Did not find role.xml for: " + ctx.getServletContextName());
        }
    }

    /**
     * Loads in a service descriptor file from the associated servlet context
     *
     * @param ctx the <code>ServletContext</code>
     */
    protected void loadServices(ServletContext ctx, ClassLoader loader) throws PortletException {
        // load in the portlet-services.xml file
        SportletServiceFactory factory = SportletServiceFactory.getInstance();
        String descriptor = ctx.getRealPath("/WEB-INF/PortletServices.xml");
        File f = new File(descriptor);
        if (f.exists()) {
            //if (loader != null) {
                factory.addServices(webApplicationName, ctx, descriptor, loader);
            //} else {
            //    factory.addServices(ctx, descriptor);
            //}
        } else {
            descriptor = ctx.getRealPath("/WEB-INF/portlet-services");
            f = new File(descriptor);
            if (f.exists()) {
                //if (loader != null) {
                    factory.addServices(webApplicationName, ctx, descriptor, loader);
                //} else {
                //    factory.addServices(ctx, descriptor);
                //}
            } else {
                log.debug("Did not find PortletServices.xml or portlet-services directory for: " + ctx.getServletContextName());
            }
        }
        // add Spring Services if any are defined
        String springDescriptor = ctx.getRealPath("/WEB-INF/applicationContext.xml");
        f = new File(springDescriptor);
        if (f.exists()) {
            factory.addSpringServices(ctx);
        }
    }

    /**
     * Returns the portlet web application name
     *
     * @return the portlet web application name
     */
    public String getWebApplicationName() {
        return webApplicationName;
    }

    /**
     * Returns the portlet web application description
     *
     * @return the portlet web application description
     */
    public String getWebApplicationDescription() {
        return webAppDescription;
    }

    /**
     * Returns the collection of application portlets contained by this portlet web application
     *
     * @return the collection of application portlets
     */
    public Collection getAllApplicationPortlets() {
        return ((appPortlets != null ? appPortlets.values() : new ArrayList()));
    }

}
