package edu.duke.cabig.c3pr.security;

import edu.duke.cabig.c3pr.domain.AbstractMutableDeletableDomainObject;
import gov.nih.nci.security.acegi.csm.authorization.AbstractPrivilegeAndObjectIdGenerator;
import gov.nih.nci.security.acegi.csm.authorization.CSMObjectIdGenerator;
import gov.nih.nci.security.acegi.csm.authorization.CSMPrivilegeGenerator;

/**
 * Will generate id and privilege for any c3pr domain object.
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jan 30, 2008
 * Time: 6:20:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class DomainObjectPrivilegeAndObjectIdGenerator implements CSMPrivilegeGenerator, CSMObjectIdGenerator {

    private String accessPrivilege;
    //default value
    private String pathSeperator = ".";

    /**
     * Returns a CSM privilege name, given an object.
     * Uses default privilege supplied to generate the access privilege
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
        return object.getClass().getName();
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

    protected boolean supports(Object object) {
        return  AbstractMutableDeletableDomainObject.class.isAssignableFrom(object.getClass());
    }

    protected void assertSupports(Object object) {
        if (object == null) {
            throw new NullPointerException("Object is null");
        }
        if (!supports(object)) {
            throw new IllegalArgumentException("unsupported object "
                    + object.getClass().getName());
        }
    }

}
