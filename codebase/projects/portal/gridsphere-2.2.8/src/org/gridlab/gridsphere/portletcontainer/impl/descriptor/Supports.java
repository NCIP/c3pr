/*
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: Supports.java,v 1.1.1.1 2007-02-01 20:50:31 kherm Exp $
 */
package org.gridlab.gridsphere.portletcontainer.impl.descriptor;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>Supports</code> class is used by the portlet descriptor
 * to specify which portlet modes are supported for the specified portlet
 * If no modes are specified, then it is assumed that all modes are
 * supported.
 */
public class Supports {

    private List markups = new ArrayList();
    private PortletLog log = SportletLog.getInstance(Supports.class);

    /**
     * Constructs an instance of Supports
     */
    public Supports() {
    }

    /**
     * Returns the supported list of markups
     *
     * @return the supported list of markups
     */
    public List getMarkups() {
        return markups;
    }


    /**
     * Sets the supported list of markups
     *
     * @param markups the supported list of markups
     */
    public void setMarkups(ArrayList markups) {
        this.markups = markups;
    }

}

