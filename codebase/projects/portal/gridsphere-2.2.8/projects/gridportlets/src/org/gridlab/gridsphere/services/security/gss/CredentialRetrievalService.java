/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialRetrievalService.java,v 1.1.1.1 2007-02-01 20:41:31 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.ietf.jgss.GSSCredential;
import org.gridlab.gridsphere.portlet.User;

import java.util.List;

/**
 * Describes a service for retrieving credentials. A credential retreival service maintains
 * a list of credential retrieval contexts per user and can be used with one or more
 * credential repositories for retrieving credentials on behalf of users.
 * @see CredentialRepository
 * @see CredentialRetrievalContext
 */
public interface CredentialRetrievalService {

    /**
     * Returns the default credential retrieval lifetime
     * @return The default credential retrieval lifetime
     */
    public int getDefaultCredentialLifetime();

    /**
     * Returns a list of credential repositories this retrieval service can use.
     * @return The credential repostiories
     * @see CredentialRepository
     */
    public List getCredentialRepositories();

    /**
     * Returns the active credential repository.
     * @return The active credential repository
     */
    public CredentialRepository getActiveCredentialRepository();

    /**
     * Returns the retrieval contexts for the given user.
     * @return The list of credential retrieval contexts
     * @see CredentialRetrievalContext
     */
    public List getCredentialRetrievalContexts(User user);

    /**
     * Returns the retrieval context with the given object id.
     * @param oid The object id
     */
    public CredentialRetrievalContext getCredentialRetrievalContext(String oid);

    /**
     * Returns the retrieval context with for the given distinguished name.
     * @param dn The distinguished name
     */
    public CredentialRetrievalContext getCredentialRetrievalContextByDn(String dn);

    /**
     * Creates a credential retrieval context for the given user. Throws
     * an exception if a context already exists for the given user.
     * @return The credential retrieval context
     */
    public CredentialRetrievalContext createCredentialRetrievalContext(User user)
            throws CredentialRetrievalException;

    /**
     * Saves the given retrieval context. Attempts to retrieve a credential
     * to insure the context is valid. Throws an exception if the context
     * does not successfully retrieve a credential.
     * @param context The credential retrieval context
     * @param password The credential retrieval password
     * @return The credential associated with this retrieval context.
     */
    public GSSCredential saveCredentialRetrievalContext(CredentialRetrievalContext context, String password)
            throws CredentialRetrievalException;

    /**
     * Deletes the given retrieval context.
     * @param context The credential retrieval context
     */
    public void deleteCredentialRetrievalContext(CredentialRetrievalContext context);

    /**
     * Attempts to use the given password to retrieve the credentials
     * for all the retrieval contexts that belong to the given user.
     *
     * @param user The user to retrieve credentials for
     * @param password The credential retrieval password
     * @return The list of credentials retrieved.
     * @see GSSCredential
     * @throws CredentialRetrievalException if an error occurs
     */
    public List retrieveCredentials(User user, String password)
            throws CredentialRetrievalException;

    /**
     * Retrieves a credential from the credential repository defined in the retrieval context
     *
     * @param context The credentialContext context associated with this credentialContext
     * @param password The password used to authenticate
     * @return The users credentialContext or null if none available
     * @throws CredentialRetrievalException if an error occurs
     */
    public GSSCredential retrieveCredential(CredentialRetrievalContext context, String password)
            throws CredentialRetrievalException;

    /**
     * Destroys credential on the credential repository associated with the given retrieval context.
     *
     * @param context The credential retrieval context.
     * @param password The credential retrieval password
     *
     * @throws CredentialRetrievalException if an error occurs
     */
    public void destroyCredential(CredentialRetrievalContext context, String password)
            throws CredentialRetrievalException;

    /**
     * Changes the credential retrieval password for all the retrieval contexts that belong to the given user.
     *
     * @param user The user
     * @param oldPassword The old credential retrieval password
     * @param newPassword The new credential retrieval password
     * @throws CredentialRetrievalException if an error occurs
     */
    public void changeCredentialPasswords(User user, String oldPassword, String newPassword)
            throws CredentialRetrievalException;

    /**
     * Changes the credential retrieval password for the given retrieval context.
     *
     * @param context The retrieval context
     * @param oldPassword The old credential retieval password
     * @param newPassword The new credential retieval password
     * @throws CredentialRetrievalException if an error occurs
     */
    public void changeCredentialPassword(CredentialRetrievalContext context,
                                         String oldPassword,
                                         String newPassword)
            throws CredentialRetrievalException;
}
