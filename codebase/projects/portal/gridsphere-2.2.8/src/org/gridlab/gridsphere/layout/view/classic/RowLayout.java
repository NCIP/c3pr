/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: RowLayout.java,v 1.1.1.1 2007-02-01 20:49:59 kherm Exp $
 */
package org.gridlab.gridsphere.layout.view.classic;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.view.BaseRender;
import org.gridlab.gridsphere.layout.view.Render;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

public class RowLayout extends BaseRender implements Render {

    protected static final StringBuffer TOP_ROW =
            new StringBuffer("\n<!-- START ROW --><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\"><tbody><tr>");

    protected static final StringBuffer BOTTOM_ROW_BORDER = new StringBuffer("</td>\n");

    protected static final StringBuffer BOTTOM_ROW = new StringBuffer("</tr></tbody></table><!-- END ROW -->\n");

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        return TOP_ROW;
    }

    public StringBuffer doStartBorder(GridSphereEvent event, PortletComponent p) {
        return new StringBuffer("\n<td valign=\"top\" width=\"" + p.getWidth() + "\">");
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_ROW_BORDER;
    }

    public StringBuffer doEnd(GridSphereEvent event, PortletComponent comp) {
        return BOTTOM_ROW;
    }

}
 


