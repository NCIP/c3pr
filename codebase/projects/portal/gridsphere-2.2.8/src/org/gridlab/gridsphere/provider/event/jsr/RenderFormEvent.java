/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: RenderFormEvent.java,v 1.1.1.1 2007-02-01 20:51:00 kherm Exp $
 */
package org.gridlab.gridsphere.provider.event.jsr;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * An <code>ActionEvent</code> is sent by the portlet container when an HTTP request is received that is
 * associated with an action.
 */
public interface RenderFormEvent extends FormEvent {

    /**
     * Return the render request associated with this action event
     *
     * @return the <code>RenderRequest</code>
     */
    public RenderRequest getRenderRequest();

    /**
     * Return the render response associated with this action event
     *
     * @return the <code>RenderResponse</code>
     */
    public RenderResponse getRenderResponse();

}
