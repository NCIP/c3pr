/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialRetrievalContext.java,v 1.1.1.1 2007-02-01 20:41:31 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.portlet.User;

import java.util.Date;

/**
 * Describes a retrieval context for a GSS credential. Credential
 * retrieval contexts are created and maintained by a credential
 * retrieval service. Credential retrieval contexts record the
 * user name and credential name that are used to retrieve a credential
 * from the "credential repository" the credential retrieval service
 * is configured to use.
 * <p>
 * A user can have multiple credential retrieval contexts but no two credential
 * retrieval contexts can have the same distinguished name (DN). That is, each
 * credential retrieval context describes the retrieval of a credential with a
 * unique DN within a portal.
 * <p>
 * @see CredentialRetrievalService
 */
public interface CredentialRetrievalContext {

    /**
     * Returns the object id for this context.
     * @return The object id
     */
    public String getOid();

    /**
     * Returns the distinguished name (DN) of the credential in this context.
     * @return The credential dn
     */
    public String getDn();

    /**
     * Returns the user to which this context belongs.
     * @return The user
     */
    public User getUser();

    /**
     * Returns the credential retrieval user name. The credential retrieval user name need not be
     * the same as the user's portal user name but it is recommended that they are the same
     * to avoid ambiguity.
     * @return The credential retrieval user name
     */
    public String getUserName();

    /**
     * Sets the credential retrieval user name. The credential retrieval user name need not be
     * the same as the user's portal user name but it is recommended that they are the same
     * to avoid ambiguity.
     * @param userName The credential retrieval user name
     */
    public void setUserName(String userName);

    /**
     * Returns the credential name assigned to the credential in this context. This is an
     * optional parameter (can be null), generally intended for users who want to store
     * multiple credentials under one user name.
     * @return The credential name, null if none assigned.
     */
    public String getCredentialName();

    /**
     * Sets the credential name assigned to the credential in this context. This is an
     * optional parameter (can be null), generally intended for users who want to store
     * multiple credentials under one user name.
     * @param credName The credential name
     */
    public void setCredentialName(String credName);

    /**
     * Returns the credential lifetime assigned to the credential in this context. This is an
     * optional parameter.
     * @return The credential lifetime, -1 if none assigned.
     */
    public int getCredentialLifetime();

    /**
     * Sets the credential lifetime assigned to the credential in this context. This is an
     * optional parameter.
     * @param lifetime The credential lifetime
     */
    public void setCredentialLifetime(int lifetime);

    /**
     * Returns the date this retrieval context was created.
     * @return The date this context was created
     */
    public Date getDateCreated();

    /**
     * Returns the date this context was last updated.
     * @return The date this context was last updated
     */
    public Date getDateLastUpdated();

    /**
     * Returns the date this retrieval context was last successfully used to retrieve a credential.
     * @return The date a credential was last retrieved with this context
     */
    public Date getDateLastRetrieved();
}
