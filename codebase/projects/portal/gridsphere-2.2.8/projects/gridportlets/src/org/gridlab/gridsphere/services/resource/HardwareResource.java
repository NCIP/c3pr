/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: HardwareResource.java,v 1.1.1.1 2007-02-01 20:40:50 kherm Exp $
 * <p>
 * Hardware resources represent computers and have the attributes typically
 * associated with computers. Hardware resources contain software resources
 * and service resources.
 */
package org.gridlab.gridsphere.services.resource;

import java.util.List;

public interface HardwareResource extends HostResource {

    /**
     * Returns the path of image 1 of this hardware resource.
     * @return The image url
     */
    public String getImage1Url();

    /**
     * Sets the url of image 1 of this hardware resource.
     * @param url The image url
     */
    public void setImage1Url(String url);

    /**
     * Returns the label of image 1 of this hardware resource.
     * @return The image url
     */
    public String getImage1Label();

    /**
     * Sets the label of image 1 of this hardware resource.
     * @param label The image label
     */
    public void setImage1Label(String label);

    /**
     * Returns the html description of this hardware resource. It is
     * recommended to use get/setHtmlUrl(...) instead but is provided
     * as a matter of convenience. This method is used by the
     * ResourceBrowserPortlet to present an html description of
     * a hardware resource.
     * @return The html description
     */
    public String getHtml();

    /**
     * Sets the html description of this hardware resource.
     * @param html The html description
     */
    public void setHtml(String html);

    /**
     * Returns the path to a file containing an html description of this hardware resource.
     * This is recommended over using get/setHtml(...), because it keeps the resource
     * registry file clean of too much clutter. This method is used by the
     * ResourceBrowserPortlet to present an html description of a hardware resource.
     * @return The html description
     */
    public String getHtmlUrl();

    /**
     * Sets the path to a file containing an html description of this hardware resource.
     * This is recommended over using get/setHtml(...), because it keeps the resource
     * registry file clean of too much clutter. The url must be relative to
     * the gridportlets web appliction directory (e.g., gridportlets/WEB-INF/html/)
     * since gridportlets will perform a jsp include to render the html.
     * @param relativeUrl The path to a file containing html description
     */
    public void setHtmlUrl(String relativeUrl);
}
