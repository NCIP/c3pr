/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionComponentType.java,v 1.1.1.1 2007-02-01 20:41:57 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

public class ActionComponentType {

    private static PortletLog log = SportletLog.getInstance(ActionComponentType.class);

    protected String description = null;
    protected String className = null;
    protected boolean hasServletContext = true;
    protected String extendsClass = null;
    protected Class componentClass = null;
    protected String defaultAction = null;
    protected String renderAction = null;
    protected String defaultJspPage = null;

    public ActionComponentType() {
    }

    public boolean getHasServletContext() {
        return hasServletContext;
    }

    public void setHasServletContext(boolean hasServletContext) {
        this.hasServletContext = hasServletContext;
    }

    public String getExtendsClass() {
        return extendsClass;
    }

    public void setExtendsClass(String extendsClass) {
        this.extendsClass = extendsClass;
    }

    public Class getComponentClass() {
        return componentClass;
    }

    public void setComponentClass(Class componentClass) {
        this.componentClass = componentClass;
        className = componentClass.getName();
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
        try {
            componentClass = Class.forName(className, true, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            log.error("Action component class not found: " + className);
        }
    }

    public String getDefaultAction() {
        return defaultAction;
    }

    public void setDefaultAction(String defaultAction) {
        this.defaultAction = defaultAction;
    }

    public String getRenderAction() {
        return renderAction;
    }

    public void setRenderAction(String renderAction) {
        this.renderAction = renderAction;
    }

    public String getDefaultJspPage() {
        return defaultJspPage;
    }

    public void setDefaultJspPage(String defaultJspPage) {
        this.defaultJspPage = defaultJspPage;
    }
}
