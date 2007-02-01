/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.security.gss;

/**
 * Describes a connection to an http service resource.
 */
public interface HttpgServiceConnection extends GssConnection {

    /**
     * Returns the service url
     * @return The service url
     */
    public String getUrl();

    /**
     * Sets the service url
     * @param url The service url
     */
    public void setUrl(String url);

    /**
     * Returns the type of delegation used for this connection.
     * Values can be:
     *   "none" - for no delegation
     *   "limited" - for limited delegation
     *   "full" - for full delegation
     * @return The delegation used for this connection
     */
    public String getDelegation();

    /**
     * Sets the type of delegation used for this connection.
     * Values can be:
     *   "none" - for no delegation
     *   "limited" - for limited delegation
     *   "full" - for full delegation
     * @param deleg The delegation used for this connection
     */
    public void setDelegation(String deleg);

    /**
     * Returns the type of authorization used for this connection.
     * Values can be:
     *   "none" - for no authorization
     *   "identity" - for identity authorization
     *   "host" - for host authorization
     * @return The authorization used for this connection
     */
    public String getAuthorization();

    /**
     * Sets the type of authorization used for this connection.
     * Values can be:
     *   "none" - for no authorization
     *   "identity" - for identity authorization
     *   "host" - for host authorization
     * @param authorization The authorization used for this connection
     */
    public void setAuthorization(String authorization);

    /**
     * Returns the dn of this service
     * @return The dn of this service
     */
    public String getServiceDn();

    /**
     * Sets the dn of this service
     * @param dn The dn of this service
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

