/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: CheckBoxBean.java,v 1.1.1.1 2007-02-01 20:51:05 kherm Exp $
 */

package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * A <code>CheckBoxBean</code> provides a check box element
 */
public class CheckBoxBean extends SelectElementBean {

    public static final String CHECKBOX_STYLE = "portlet-form-field";

    public static final String NAME = "cb";

    /**
     * Constructs a default check box bean
     */
    public CheckBoxBean() {
        super(NAME);
        this.cssClass = CHECKBOX_STYLE;
    }

    /**
     * Constructs a check box bean with a supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public CheckBoxBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
        this.cssClass = CHECKBOX_STYLE;
    }

    public String toStartString() {
        return super.toStartString("checkbox");
    }

}
