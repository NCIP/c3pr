package org.gridlab.gridsphere.extras.services.wiki;

import org.radeox.api.engine.WikiRenderEngine;
import org.radeox.engine.BaseRenderEngine;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: PortletWikiRenderEngine.java,v 1.1.1.1 2007-02-01 20:08:32 kherm Exp $
 */

public class PortletWikiRenderEngine extends BaseRenderEngine implements WikiRenderEngine {


    private String link = "";
    private SnipStorageService snipStorage = null;

    public void setSnipStorage(SnipStorageService snipStorage) {
        this.snipStorage = snipStorage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean exists(String name) {
        // make a lookup in your wiki if the page exists

        return snipStorage.snipExists(name);
    }

    public boolean showCreate() {
        return true;
    }

    public void appendLink(StringBuffer buffer, String name, String view) {
        buffer.append("<a href=\""+link+"&snipname=");
        buffer.append(name);
        buffer.append("\">");
        buffer.append(view);
        buffer.append("</a>");
    }

    public void appendLink(StringBuffer buffer, String name, String view, String anchor) {
        // the same but with # HTML anchor
        buffer.append("<a href=\""+link+"&snipname=");
        buffer.append(name);
        buffer.append("#");
        buffer.append(anchor);
        buffer.append("\">");
        buffer.append(view);
        buffer.append("</a>");

    }

    public void appendCreateLink(StringBuffer buffer, String name, String view) {
        buffer.append("[create ");
        // some HTML href code as above
        buffer.append("<a href=\""+link+"&snipname=");
        buffer.append(name);
        buffer.append("\">");
        buffer.append(view);
        buffer.append("</a>");

        buffer.append("]");
    }

    public String getName() {
        return "portlet-wiki";
    }
}
