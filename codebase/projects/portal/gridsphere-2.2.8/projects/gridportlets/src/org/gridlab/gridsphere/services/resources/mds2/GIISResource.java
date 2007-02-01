/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.mds2;

/**
 * Implements a giis resource.
 */
public class GIISResource extends Mds2Resource {

    public GIISResource() {
        super();
        setResourceType(GIISResourceType.INSTANCE);
    }
}
