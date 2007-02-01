/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialContext.java,v 1.1.1.1 2007-02-01 20:41:28 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.portlet.User;
import org.ietf.jgss.GSSCredential;

import java.util.List;
import java.util.Date;

/**
 * Provides a persistent context for GSS credentials. A credential context
 * describes a credential that belongs to a portal user. A user can have
 * multiple credential contexts but no two credential contexts can have the
 * same distinguished name (DN). That is, each credential context describes the
 * use of a credential with a unique DN within a portal.
 * <p>
 * Credential contexts are activated by supplying a credential and will
 * maintain a handle to that credential until the credential expires.
 * Credential contexts are created and maintained by the credential
 * manager service. Moreover, credential contexts can contain zero or more
 * resource mappings. That is, credential contexts can be used to describe
 * for which resources a credential may be used in a portal.
 * @see GSSCredential
 * @see CredentialManagerService
 */
public interface CredentialContext {

    /**
     * Returns the object id of the context.
     * @return The object id
     */
    public String getOid();

    /**
     * Returns the distinguished name of the credential described by this context.
     * @return The distinguished name for this context
     */
    public String getDn();

    /**
     * Returns the user who owns this credential context.
     * @return The user that owns this credential
     */
    public User getUser();

    /**
     * Returns the credential described by this context.
     * Returns null if the context is not active.
     * @return This context's credential if is active, null otherwise.
     */
    public GSSCredential getCredential();

    /**
     * Returns the amount of time remaining, in seconds, before this context's credential will expire.
     * @return The remaining lifetime in seconds
     */
    public int getRemainingLifetime();

    /**
     * Returns the user-defined label for this context
     * @return The label for this context
     */
    public String getLabel();

    /**
     * Sets the user-defined label for this context
     * @param label The label for this context
     */
    public void setLabel(String label);

    /**
     * Returns the name of the file containing the credentials for this context.
     * If the credential manager service is not configured to save credentials
     * to the filesystem or if this context is not active, this method will return null.
     * @return The name of the file containing the credentials for this context.
     */
    public String getFileName();

    /**
     * Returns the credential resource mappings contained by this context.
     * @return The list of credential mappings.
     * @see CredentialMapping
      */
    public List getCredentialMappings();

    /**
     * Returns the credential resource mapping for the given resource dn. Returns
     * null if no mapping exists for the given resource.
     * @param resourceDn The location in question
     * @return The credential  mapping
     */
    public CredentialMapping getCredentialMapping(String resourceDn);

    /**
     * Returns whether this context is mapped to the given resource dn.
     * @param resourceDn The location in question
     * @return True if mapped, false otherwise
     */
    public boolean hasCredentialMapping(String resourceDn);

    /**
     * Adds a credential mapping with the given resource dn if one
     * does not already exist in the list of credential mappings.
     * @param resourceDn The resource
     * @return The credential mapping with this location
     */
    public CredentialMapping putCredentialMapping(String resourceDn);

    /**
     * Removes the credential mapping with the given resource dn.
     * @param resourceDn The resource
     * @return The credential mapping that was removed
     */
    public CredentialMapping removeCredentialMapping(String resourceDn);

    /**
     * Returns true if the associated credential is not null and is not yet expired.
     * @return True if active, false otherwise
     */
    public boolean isActive();

    /**
     * Activates this context with the given credential. Throws an exception if
     * the credential is null, if it is not active or if it is not accepted by
     * this context (has an incorrect distinguished name, for example).
     * @param credential The credential
     * @throws CredentialException
     */
    public void activate(GSSCredential credential)
            throws CredentialException;

    /**
     * Deactivates this credential context by nullifying the credential.
     */
    public void deactivate();

    /**
     * Returns the date this context was created.
     * @return The date created
     */
    public Date getDateCreated();

    /**
     * Returns the date this context was last updated.
     * @return The date last updated
     */
    public Date getDateLastUpdated();

    /**
     * Returns the date this context was last activated.
     * @return The date last activated
     */
    public Date getDateLastActivated();
}
