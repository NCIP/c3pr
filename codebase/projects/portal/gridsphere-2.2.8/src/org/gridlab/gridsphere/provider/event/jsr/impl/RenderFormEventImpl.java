/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: RenderFormEventImpl.java,v 1.1.1.1 2007-02-01 20:51:01 kherm Exp $
 */
package org.gridlab.gridsphere.provider.event.jsr.impl;

import org.gridlab.gridsphere.provider.event.jsr.RenderFormEvent;
import org.gridlab.gridsphere.provider.event.impl.BaseFormEventImpl;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * An <code>ActionEvent</code> is sent by the portlet container when an HTTP request is
 * received that is associated with an action.
 */
public class RenderFormEventImpl extends BaseFormEventImpl implements RenderFormEvent {

    private RenderRequest request;
    private RenderResponse response;

    /**
     * Constructs an instance of RenderFormEventImpl given a render request and response
     *
     * @param request  the <code>RenderRequest</code>
     * @param response the <code>RenderResponse</code>
     * @param tagBeans a collection of tag beans
     */
    public RenderFormEventImpl(RenderRequest request, RenderResponse response, Map tagBeans) {
        super(request, response);
        this.request = request;
        this.response = response;
        this.tagBeans = tagBeans;
        // Unless tagBeans is null, don't recreate them
        if (tagBeans == null) {
            tagBeans = new HashMap();
            createTagBeans(getRequest());
        }
        //logRequestParameters();
        //logTagBeans();
    }

    /**
     * Return the render request associated with this render event
     *
     * @return the <code>RenderRequest</code>
     */
    public RenderRequest getRenderRequest() {
        return request;
    }

    /**
     * Return the render response associated with this render event
     *
     * @return the <code>RenderResponse</code>
     */
    public RenderResponse getRenderResponse() {
        return response;
    }

}
