/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.resource.WebServiceResource;

/**
 * Describes a httpg service resource. The httpg protocol is not formally
 * supported by Globus anymore, but it is still used in some projects,
 * notably the GridLab Project.
 */
public interface HttpgServiceResource extends WebServiceResource, GssConnectionResource {

    /**
     * Returns the type of delegation used to connect to this resource.
     * Values can be:
     *   "none" - for no delegation
     *   "limited" - for limited delegation
     *   "full" - for full delegation
     * @return The delegation used to connect this resource
     */
    public String getDelegation();

    /**
     * Sets the  type of delegation used to connect to this resource.
     * Values can be:
     *   "none" - for no delegation
     *   "limited" - for limited delegation
     *   "full" - for full delegation
     * @param deleg The delegation used to connect this resource
     */
    public void setDelegation(String deleg);

    /**
     * Returns the type of authorization used to connect to this resource.
     * Values can be:
     *   "none" - for no authorization
     *   "identity" - for identity authorization
     *   "host" - for host authorization
     * @return The authorization used to connect this resource
     */
    public String getAuthorization();

    /**
     * Sets the type of authorization used to connect to this resource.
     * Values can be:
     *   "none" - for no authorization
     *   "identity" - for identity authorization
     *   "host" - for host authorization
     * @param authorization The authorization used to connect this resource
     */
    public void setAuthorization(String authorization);

    /**
     * Returns the dn used to verify this service
     * @return The distinguished name
     */
    public String getServiceDn();

    /**
     * Sets the dn used to verify this servic
     * @param dn The distinguished name
     */
    public void setServiceDn(String dn);

    /**
     * Returns the timeout in minutes
     * @return The timeout in minutes
     */
    public String getTimeOut();

    /**
     * Sets the timeout in minutes
     * @param min The timeout in minutes
     */
    public void setTimeOut(String min);
}
