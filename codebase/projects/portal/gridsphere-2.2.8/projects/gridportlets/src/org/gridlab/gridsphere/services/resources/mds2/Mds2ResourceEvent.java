/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: Mds2ResourceEvent.java,v 1.1.1.1 2007-02-01 20:41:20 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.mds2;

import org.gridlab.gridsphere.services.resource.ResourceEvent;

public class Mds2ResourceEvent extends ResourceEvent {

    public static final Mds2ResourceEvent INSTANCE = new Mds2ResourceEvent();

    public static final String NAME = "Mds2ResourceEvent";

    public Mds2ResourceEvent() {
        setName(NAME);
    }
}
