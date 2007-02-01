/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: ListBoxItemBean.java,v 1.1.1.1 2007-02-01 20:51:07 kherm Exp $
 */

package org.gridlab.gridsphere.provider.portletui.beans;

/**
 * A <code>ListBoxItemBean</code> defines the elements contained within a <code>ListBoxBean</code>
 */
public class ListBoxItemBean extends SelectElementBean {

    public static final String NAME = "li";

    /**
     * Constructs a default listbox item bean
     */
    public ListBoxItemBean() {
        super(NAME);
    }

    /**
     * Constructs a listbox item bean with a supplied bean identifier
     *
     * @param beanId the listbox item bean identifier
     */
    public ListBoxItemBean(String beanId) {
        super(NAME);
        this.beanId = beanId;
    }

    public String toStartString() {
        String pval = (value == null) ? "" : value;
        pval = (name == null) ? pval : name;
        String sname = pval;
        if (!beanId.equals("")) {
            sname = "ui_" + vbName + "_" + beanId + "_" + pval;
        }
        // 'selected' replaced by 'selected="selected"' for XHTML 1.0 Strict compliance
        return "<option value='" + sname + "' " + checkDisabled() + " " + checkSelected("selected=\"selected\"") + ">" + value + "</option>";
    }

    public String toEndString() {
        return "";
    }
}
