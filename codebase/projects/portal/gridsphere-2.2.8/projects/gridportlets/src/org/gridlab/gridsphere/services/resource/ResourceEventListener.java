/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceEventListener.java,v 1.1.1.1 2007-02-01 20:40:53 kherm Exp $
 */
package org.gridlab.gridsphere.services.resource;

public interface ResourceEventListener {

    public void resourceEventOccured(ResourceEvent event);
}
