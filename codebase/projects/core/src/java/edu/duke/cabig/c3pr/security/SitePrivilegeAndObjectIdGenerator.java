package edu.duke.cabig.c3pr.security;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.acegi.csm.authorization.CSMPrivilegeGenerator;

/**
 * WIll generate the objectId and privilege for HealthcareSite domain object.
 * THis is needed to make authorization desicisions
 *
 *  Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 13, 2007
 * Time: 3:05:32 PM
 * To change this template use File | Settings | File Templates.
 */
public final class SitePrivilegeAndObjectIdGenerator extends DomainObjectPrivilegeAndObjectIdGenerator {
    
    /**
     * Returns a CSM objectId, given an object.
     *
     * @param object from which ID should be generated
     * @return CSM objectId
     */
    @Override
    public String generateId(Object object) {
        assertSupports(object);
        HealthcareSite site = (HealthcareSite) object;

        return object.getClass().getName() + getPathSeperator() + site.getNciInstituteCode();
    }


    @Override
    protected boolean supports(Object object) {
        return object instanceof HealthcareSite;
    }

}
