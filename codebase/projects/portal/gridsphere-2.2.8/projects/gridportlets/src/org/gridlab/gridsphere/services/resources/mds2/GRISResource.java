/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.resources.mds2;

/**
 * Implements a gris resource.
 */
public class GRISResource extends Mds2Resource {

    public GRISResource() {
        super();
        setResourceType(GRISResourceType.INSTANCE);
    }
}
