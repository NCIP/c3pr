/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: BaseBean.java,v 1.1.1.1 2007-02-01 20:51:05 kherm Exp $
 */
package org.gridlab.gridsphere.provider.portletui.beans;

import org.gridlab.gridsphere.portlet.impl.SportletProperties;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * The abstract <code>BaseBean</code> is an implementation of the <code>TagBean</code> interface.
 * <code>BaseBean</code> provides the basic functionality for all visual beans.
 */
public abstract class BaseBean implements TagBean {

    protected String beanId = "";
    protected String vbName = "undefined";
    //protected HttpServletRequest request = null;
    //protected PortletRequest portletRequest = null;
    protected Locale locale = null;
    protected Map params = new HashMap();

    /**
     * Constructs default base bean
     */
    public BaseBean() {
        super();
    }

    /**
     * Constructs a base bean for the supplied visual bean type
     *
     * @param vbName a name identifying the type of visual bean
     */
    public BaseBean(String vbName) {
        this.vbName = vbName;
    }

    public void addParam(String name, String value) {
        params.put(name, value);
    }

    public void removeParam(String name) {
        params.remove(name);
    }

    protected String createTagName(String name) {
        String sname = "";
        String pname = (name == null) ? "" : name;
        String compId = (String)params.get(SportletProperties.GP_COMPONENT_ID);
        if (beanId.equals("")) return pname;
        if (compId == null) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pname;
        } else {
            String cid = (String)params.get(SportletProperties.COMPONENT_ID);
            sname = "ui_" + vbName + "_" + compId + "%" + beanId + "_" + pname + cid;
        }
        return sname;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * Returns the bean identifier
     *
     * @return the bean identifier
     */
    public String getBeanId() {
        return this.beanId;
    }

    /**
     * Sets the bean identifier
     *
     * @param beanId the bean identifier
     */
    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }


    public abstract String toStartString();

    public abstract String toEndString();

    public String getBeanKey() {
        String cid = (String)params.get(SportletProperties.COMPONENT_ID);
        String compId = (String)params.get(SportletProperties.GP_COMPONENT_ID);

        String beanKey = null;
        if (compId == null) {
            beanKey = beanId + "_" + cid;
        } else {
            beanKey = compId + "%" + beanId + "_" + cid;
        }
        System.err.println("getBeanKey(" + beanId + ") = " + beanKey);
        return beanKey;
    }

    protected String getLocalizedText(String key) {
        return getLocalizedText(key, "gridsphere.resources.Portlet");
    }

    protected String getLocalizedText(String key, String base) {
        ResourceBundle bundle = ResourceBundle.getBundle(base, locale);
        return bundle.getString(key);
    }

}
