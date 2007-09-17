package edu.duke.cabig.c3pr.security;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.acegi.csm.authorization.CSMPrivilegeGenerator;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 13, 2007
 * Time: 3:05:32 PM
 * To change this template use File | Settings | File Templates.
 */
public final class SitePrivilegeAndObjectIdGenerator implements CSMPrivilegeGenerator, CSMObjectIdGenerator {

    private String accessPrivilege;
    //default value
    private String pathSeperator = ".";

    /**
     * Returns a CSM privilege name, given an object.
     *
     * @param object from which to determine privilege
     * @return CSM privilege name
     */
    public String generatePrivilege(Object object) {
        assertSupports(object);
        return object.getClass().getName() + pathSeperator + accessPrivilege;
    }

    /**
     * Returns a CSM objectId, given an object.
     *
     * @param object from which ID should be generated
     * @return CSM objectId
     */
    public String generateId(Object object) {
        assertSupports(object);
        HealthcareSite site = (HealthcareSite) object;

        return object.getClass().getName() + pathSeperator + site.getNciInstituteCode();
    }


    public String getPathSeperator() {
        return pathSeperator;
    }

    public void setPathSeperator(String pathSeperator) {
        this.pathSeperator = pathSeperator;
    }

    public String getAccessPrivilege() {
        return accessPrivilege;
    }

    public void setAccessPrivilege(String accessPrivilege) {
        this.accessPrivilege = accessPrivilege;
    }

    private boolean supports(Object object) {
        return object instanceof HealthcareSite;
    }

    private void assertSupports(Object object) {
        if (object == null) {
            throw new NullPointerException("Object is null");
        }
        if (!supports(object)) {
            throw new IllegalArgumentException("unsupported object "
                    + object.getClass().getName());
        }
    }

}
