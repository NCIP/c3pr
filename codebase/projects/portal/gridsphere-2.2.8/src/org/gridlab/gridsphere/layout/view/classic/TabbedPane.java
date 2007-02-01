/*
* @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
* @author <a href="mailto:wehrens@aei.mpg.de">Oliver Wehrens</a>
* @version $Id: TabbedPane.java,v 1.1.1.1 2007-02-01 20:49:59 kherm Exp $
*/

package org.gridlab.gridsphere.layout.view.classic;

import org.gridlab.gridsphere.layout.PortletComponent;
import org.gridlab.gridsphere.layout.PortletTab;
import org.gridlab.gridsphere.layout.PortletTabbedPane;
import org.gridlab.gridsphere.layout.view.BaseRender;
import org.gridlab.gridsphere.layout.view.TabbedPaneView;
import org.gridlab.gridsphere.portletcontainer.GridSphereEvent;

import java.util.List;
import java.util.StringTokenizer;

public class TabbedPane extends BaseRender implements TabbedPaneView {

    protected static final StringBuffer TAB_START =
            new StringBuffer("\n<!-- START TABBED PANE --><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr>");

    protected static final StringBuffer TAB_END =
            new StringBuffer("<td class=\"tab-fillup\">&nbsp;</td></tr></table><!-- END TABBED PANE -->\n");
    /**
     * Constructs an instance of PortletTabbedPane
     */
    public TabbedPane() {
    }


    /**
     * Creates the portlet tab link URIs that are used to send events to
     * the portlet tabs.
     *
     * @param event the gridsphere event
     */
    protected static String[] createTabLinks(GridSphereEvent event, PortletTabbedPane pane) {
        // Make tab links
        String[] tabLinks = new String[pane.getPortletTabs().size()];
        List tabs = pane.getPortletTabs();
        for (int i = 0; i < tabs.size(); i++) {
            PortletTab tab = (PortletTab) tabs.get(i);
            tabLinks[i] = tab.createTabTitleLink(event);
        }
        return tabLinks;
    }

    /**
     * Replace blank spaces in title with '&nbsp;'
     *
     * @param title the tab title
     * @return a title without blank spaces
     */
    private static String replaceBlanks(String title) {
        String result = "&nbsp;";
        StringTokenizer st = new StringTokenizer(title);
        while (st.hasMoreTokens()) {
            result += st.nextToken() + "&nbsp;";
        }
        return result;
    }

    public StringBuffer doStart(GridSphereEvent event, PortletComponent comp) {
        PortletTabbedPane pane = (PortletTabbedPane)comp;
        if (pane.getStyle().equalsIgnoreCase("sub-menu")) {
           StringBuffer sb = new StringBuffer();
            sb.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" class=\"tab-sub-pane" /*+ pane.getTheme()*/ + "\" width=\"100%\"><tr><td>"); ///
            sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr>");
            return sb;
        } else {
            return TAB_START;
        }
    }

    public StringBuffer doRenderTab(GridSphereEvent event, PortletTabbedPane tabPane, PortletTab tab) {
        // this really creates the individual tabs
        StringBuffer pane = new StringBuffer();
        String path = event.getPortletRequest().getContextPath() + "/themes/" + tabPane.getRenderKit() + "/" + tabPane.getTheme() + "/images/"; /// Removed File.separator(s)
        String link = tab.createTabTitleLink(event);
        String lang = event.getPortletRequest().getLocale().getLanguage();
        String title = tab.getTitle(lang);
        if (tabPane.getStyle().equals("sub-menu")) {
            pane.append("\n<!-- START SUB MENU TAB --><td nowrap=\"nowrap\" background=\"" + path + "subtab-middle.gif\" height=\"24\">");
            if (tab.isSelected()) {
                pane.append("<span class=\"tab-sub-active\">");
                pane.append("<a class=\"tab-sub-menu-active\" href=\"" + link + "\">" + replaceBlanks(title) + "</a></span>");
            } else {
                pane.append("<span class=\"tab-sub-inactive\">");
                pane.append("<a class=\"tab-sub-menu\" href=\"" + link + "\">" + replaceBlanks(title) + "</a>");
                pane.append("</span>");
            }
            pane.append("</td>\n");
        } else {
            if (tab.isSelected()) {
                pane.append("\n<td height=\"24\" width=\"6\"><img src=\"" + path + "tab-active-left.gif\" alt=\"tab active left border\" /></td>");
                pane.append("<td height=\"24\" nowrap=\"nowrap\" background=\"" + path + "tab-active-middle.gif\">");
                pane.append("<span class=\"tab-active\">" + replaceBlanks(title) + "</span>");
                pane.append("<td height=\"24\" width=\"6\"><img src=\"" + path + "tab-active-right.gif\" alt=\"tab active right border\" /></td>");
            } else {
                pane.append("\n<td height=\"24\" width=\"6\"><img src=\"" + path + "tab-inactive-left.gif\"  alt=\"tab inactive left border\" /></td>");
                pane.append("<td height=\"24\" nowrap=\"nowrap\" background=\"" + path + "tab-inactive-middle.gif\">");
                pane.append("<a class=\"tab-menu\" href=\"" + link + "\">" + replaceBlanks(title) + "</a>");
                pane.append("<td height=\"24\" width=\"6\"><img src=\"" + path + "tab-inactive-right.gif\" alt=\"tab inactive right border\" /></td>");
            }
        }
        return pane;
    }

    public StringBuffer doEndBorder(GridSphereEvent event, PortletComponent component) {
        // this ends the indivual tabs
        PortletTabbedPane tabPane = (PortletTabbedPane)component;
        StringBuffer pane = new StringBuffer();
        if (tabPane.getStyle().equals("sub-menu")) {
            String path = event.getPortletRequest().getContextPath() + "/themes/" + tabPane.getRenderKit() + "/" + tabPane.getTheme() + "/images/"; /// Removed File.separator(s)
            pane.append("</tr></table>");
            pane.append("<td background=\"" + path + "subtab-middle.gif\" style=\"width:100%\">&nbsp;</td>");
            pane.append("</tr></table><!-- end SUB MENU tabbed pane -->\n");
            return pane;
        } else {
            return TAB_END;
        }
    }

}
