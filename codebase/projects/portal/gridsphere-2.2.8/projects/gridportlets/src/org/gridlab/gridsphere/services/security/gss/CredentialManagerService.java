/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialManagerService.java,v 1.1.1.1 2007-02-01 20:41:29 kherm Exp $
 */

package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletService;
import org.ietf.jgss.GSSCredential;

import java.util.List;

/**
 * Describes a service for creating and maintaining credential contexts, where a credential
 * context maintains a handle to a GSS credential for a given user. This service also provides
 * convenient methods for accessing credential contexts, the credentials and credential
 * resource mappings contained therein.
 * @see GSSCredential
 * @see CredentialContext
 * @see CredentialMapping
 */
public interface CredentialManagerService extends PortletService {

    /**
     * Returns the credential contexts associated with the given user.
     * @param user
     * @return The list of credential contexts
     * @see CredentialContext
     */
    public List getCredentialContexts(User user);

    /**
     * Returns the credential context with the given object id.
     * @param oid The object id.
     * @return The credential context with the given object id
     */
    public CredentialContext getCredentialContext(String oid);

    /**
     * Returns the credential context with the given distinguished name.
     * @param dn The distinguished name.
     * @return The credential context.
     */
    public CredentialContext getCredentialContextByDn(String dn);

    /**
     * Creates a credential context for the given user and distinguished name.
     * Throws an exception if a credential context already exists for the
     * given user and distinguished name.
     * @param user The user
     * @param dn The distinguished name
     * @return The credential context for the given user and dn
     */
    public CredentialContext createCredentialContext(User user, String dn)
            throws CredentialException;

    /**
     * Creates a credential context for the given user and credential.
     * Throws an exception if a credential context already exists for the
     * given user and distinguished name.
     * @param user The user
     * @param credential The credential
     * @return The credential context
     */
    public CredentialContext createCredentialContext(User user, GSSCredential credential)
            throws CredentialException;

    /**
     * Saves the given credential context. Throws an exception if
     * there is a conflict with an existing credential context.
     * @param context The credential context
     */
    public void saveCredentialContext(CredentialContext context)
            throws CredentialException;

    /**
     * Deletes the given credential context.
     * @param context The credential context to delete
     */
    public void deleteCredentialContext(CredentialContext context);

    /**
     * Activates the credential contexts that are associated with the given credentials.
     * @param credentials The list of credentials
     * @return The credential contexts that were activated
     * @see GSSCredential
     * @see CredentialContext
     */
    public List activateCredentialContexts(List credentials)
            throws CredentialException;

    /**
     * Activates the credential context that is associated with the given credential.
     * Returns the activated context if one exists for the credential, throws an exception
     * otherwise.
     * @param credential The credential
     * @return The activated credential context
     */
    public CredentialContext activateCredentialContext(GSSCredential credential)
            throws CredentialException;

    /**
     * Deactivates the credential contexts associated with the given user.
     * @param user The user
     */
    public void deactivateCredentialContexts(User user);

    /**
     * Deactivates the given credential context.
     * @param context The credential context
     */
    public void deactivateCredentialContext(CredentialContext context);

    /**
     * Returns the active credential contexts for the given user.
     * If no active credential contexts are found, returns an empty list.
     * @param user
     * @return The list of active credential contexts
     * @see CredentialContext
     */
    public List getActiveCredentialContexts(User user);

    /**
     * Returns the active credential contexts for the given user and resource.
     * If no specific contexts are mapped to the given resource, returns an empty list.
     * @param user The user
     * @param resource The resource
     */
    public List getActiveCredentialContexts(User user, GssEnabledResource resource);

    /**
     * Returns the first active credential context found for the given user.
     * Returns null if no active credential contexts found.
     * @param user The user
     * @return The active credential context if found, null otherwise
     */
    public CredentialContext getDefaultCredentialContext(User user);

    /**
     * Returns the first active credential context found for the given user and resource.
     * Returns null if no active credential context found.
     * @param user The user
     * @param resource The resource
     * @return The credential context
     */
    public CredentialContext getDefaultCredentialContext(User user, GssEnabledResource resource);

    /**
     * Returns the list of active credentials found for the given user.
     * @param user The user
     * @return The list of active credentials
     * @see GSSCredential
     */
    public List getActiveCredentials(User user);

    /**
     * Returns the active credentials found for the given user and resource.
     * If no specific credentials are mapped to the given resource, returns
     * an empty list.
     * @param user The user
     * @param resource The resource
     * @return The list of active credentials
     * @see GSSCredential
     */
    public List getActiveCredentials(User user, GssEnabledResource resource);

    /**
     * Returns the first active credential found for the given user.
     * If no active credential found, returns null.
     * @param user
     * @return The first active credential if one exists, null othrewise.
     */
    public GSSCredential getDefaultCredential(User user);

    /**
     * Returns the first active credential found for the given user and resource.
     * If no specific credential is associated with the given resource, returns
     * null.
     * @param user The user
     * @return The first active credential if one exists, null otherwise.
     */
    public GSSCredential getDefaultCredential(User user, GssEnabledResource resource);

    /**
     * Returns the union of all the credential resource mappings that
     * are associated with the credential contexts that
     * belong to the user. Returns an empty list if no
     * resource mappings are found.
     * @param user
     * @return The list of credential resource mappings.
     * @see CredentialMapping
     */
    public List getCredentialResourceMappings(User user);

    /**
     * Returns the union of all the credential resource mappings that
     * are associated with active credential contexts that
     * belong to the user. Returns an empty list if no
     * credential resource mappings are found.
     * @param user
     * @return The list of credential resource mappings.
     * @see CredentialMapping
     */
    public List getActiveCredentialResourceMappings(User user);

    /**
     * Returns true if this service is configured to save active credentials to files.
     * @return True if configured to save credentials to files, false otherwise.
     */
    public boolean getSaveActiveCredentialsToFile();

    /**
     * Returns the directory to which active credentials are saved as files.
     * @return The active credental directory.
     */
    public String getActiveCredentialDirectory();

    /**
     * Sets the directory to which active credentials are saved as files.
     * @param dir The active credental directory.
     */
    public void setActiveCredentialDirectory(String dir);

    /**
     * Returns the list of active credential file names found for the given user.
     * @param user The user
     * @return The list of active credential file names
     * @see GSSCredential
     */
    public List getActiveCredentialFileNames(User user);

    /**
     * Returns the name of the file where the active credential with the given dn is stored.
     * @param  dn The credential dn
     * @return The file name.
     */
    public String getActiveCredentialFileName(String dn);
}
