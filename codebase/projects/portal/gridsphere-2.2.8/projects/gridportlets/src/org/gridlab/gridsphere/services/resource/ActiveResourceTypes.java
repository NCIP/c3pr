package org.gridlab.gridsphere.services.resource;

import java.util.ArrayList;
import java.util.List;

/*
 * @author <a href="mailto:oliver.wehrens@aei.mpg.de">Oliver Wehrens</a>
 * @version $Id: ActiveResourceTypes.java,v 1.1.1.1 2007-02-01 20:40:50 kherm Exp $
 */

/**
 * This object contains a list of <code>ResourceTypeDescriptions</code> of resourcetypes which are available.
 */
public class ActiveResourceTypes {

    private List resourcetypes = new ArrayList();

    public List getResourcetypes() {
        return resourcetypes;
    }

    public void setResourcetypes(List resourcetypes) {
        this.resourcetypes = resourcetypes;
    }

    public ResourceTypeDescription getResourceTypeDescriptionById(String id) {
        for (int i = 0; i < resourcetypes.size(); i++) {
            ResourceTypeDescription rtd = (ResourceTypeDescription) resourcetypes.get(i);
            if (rtd.getId().equals(id)) {
                return rtd;
            }
        }
        return null;
    }

    public String toString() {
        return "Number of ResourceTypes : " + resourcetypes.size();
    }
}
