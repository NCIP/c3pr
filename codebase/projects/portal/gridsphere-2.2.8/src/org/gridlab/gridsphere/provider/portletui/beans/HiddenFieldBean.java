/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: HiddenFieldBean.java,v 1.1.1.1 2007-02-01 20:51:06 kherm Exp $
 */
package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * A <code>HiddenFieldBean</code> represents a hidden field element
 */
public class HiddenFieldBean extends TextFieldBean {

    public static final String NAME = "hf";

    /**
     * Constructs a default hidden field bean
     */
    public HiddenFieldBean() {
        this.vbName = NAME;
    }

    /**
     * Constructs a hidden field bean with the supplied bean identifier
     *
     * @param beanId the bean identifier
     */
    public HiddenFieldBean(String beanId) {
        super(NAME, beanId);
    }

    public String toStartString() {
        this.inputtype = "hidden";
        return super.toStartString();
    }

}
