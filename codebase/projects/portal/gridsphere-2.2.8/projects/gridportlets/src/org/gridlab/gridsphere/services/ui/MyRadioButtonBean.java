/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: MyRadioButtonBean.java,v 1.1.1.1 2007-02-01 20:41:58 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui;

import org.gridlab.gridsphere.provider.portletui.beans.RadioButtonBean;

import javax.servlet.http.HttpServletRequest;

public class MyRadioButtonBean extends RadioButtonBean {

    /**
     * Constructs a default radio button bean
     */
    public MyRadioButtonBean() {
        super();
    }

    /**
     * Constructs a radio button bean using a supplied portlet request and bean identifier
     *
     * @param id the bean identifier
     */
    public MyRadioButtonBean(String id) {
        super(id);
    }

    /**
     * Returns a String used in the final markup indicating if this bean is selected or not
     *
     * @param select the selected String
     * @return the String used in the final markup indicating if this bean is selected or not
     */
    protected String checkSelected(String select) {
        if (!beanId.equals("") && value != null) {
            for (int ii = 0; ii < results.size(); ++ii) {
                String selectedValue = (String)results.get(ii);
                if (value.equals(selectedValue)) {
                    return ' ' + select + "='" + select + "' ";
                }
            }
            return "";
        }
        if (selected) {
            return ' ' + select + "='" + select + "' ";
        } else {
            return "";
        }
    }
}
