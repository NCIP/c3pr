/*
 * @author <a href="mailto:russell@aei-potsdam.mpg.de">Michael Paul Russell</a>
 * @version $Id: CredentialRepository.java,v 1.1.1.1 2007-02-01 20:41:31 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.resource.ServiceResource;
import org.ietf.jgss.GSSCredential;

/**
 * Describes a credential repository. A credential repository is a service
 * resource that enables clients to "store" and later "retrieve" credentials
 * with a username and pasword. This process can be used to setup single
 * sign-on to the Grid, where credential retrieval can be used to authenticate
 * users to a portal at login and simulatenously "sign-on" to the Grid.
 */
public interface CredentialRepository extends ServiceResource {

    /**
     * Returns the portal credential. A credential repository
     * may be configured to require a portal to authenticate itself
     * with a credential in order to perform operations on behalf
     * of users. Thus, this method returns the credential the portal
     * will use to authenticate itself to the credential repository.
     * If either the credential repository is not configured to require
     * a credential or the credential was not obtained for some reason,
     * this method will return null.
     * @return The portal's credential, null if one does not exist.
     */
    public GSSCredential getPortalCredential();

    /**
     * Returns whether to use a portal credential to authenticate to credential repository.
     * @return The current value.
     */
    public boolean getUsePortalCredential();

    /**
     * Sets whether to use a portal credential to authenticate to credential repository
     * @param flag
     */
    public void setUsePortalCredential(boolean flag);

    /**
     * Returns the path to the portal proxy file. The portal credential
     * may be stored in a "proxy" file or it may be generated with an
     * X509 certificate and private key. This is a portal configuration
     * option (see the "Grid Portlets Administrator's Guide" for more details).
     * This method will return null if no portal credential file path was specified.
     * @return The path to the credential file
     */
    public String getPortalProxyFile();

    /**
     * Sets the path to the portal proxy file.
     * @param proxyFile The path to the credential file
     */
    public void setPortalProxyFile(String proxyFile);

    /**
     * Returns the path to the portal certificate file. The portal credential
     * may be stored in a "proxy" file or it may be generated with an
     * X509 certificate and private key. This is a portal configuration
     * option (see the "Grid Portlets Administrator's Guide" for more details).
     * This method will return null if no portal certificate file path was specified.
     * @return The path to the certificate file
     */
    public String getPortalCertificateFile();

    /**
     * Sets the path to the portal certificate file.
     * @param certFile The path to the certificate file
     */
    public void setPortalCertificateFile(String certFile);

    /**
     * Returns the path to the portal private key file. The portal credential
     * may be stored in a "proxy" file or it may be generated with an
     * X509 certificate and private key. This is a portal configuration
     * option (see the "Grid Portlets Administrator's Guide" for more details).
     * This method will return null if no portal private key file path was specified.
     * @return The path to the private key file
     */
    public String getPortalKeyFile();

    /**
     * Sets the private key file that is used
     * to generate the portal credential.
     * @param keyFile The path to the private key file
     */
    public void setPortalKeyFile(String keyFile);

    /**
     * "Retrieves" a credential with the given parameters. This
     * method delegates a credential from the repository that
     * was stored with the same user name, credential name and
     * password, to the client for the given lifetime. The username
     * and password are required parameters, while the crdential name is
     * an optional parameter (can be null), generally
     * intended for users who want to store multiple credentials
     * under one user name. Specifying a lifetime of 0 or less will
     * result in a credential with some default lifetime, determined
     * by the credential repository.
     * @param username The user name under which the credential was stored
     * @param credname The credential name under which the credential was stored
     * @param lifetime The lifetime of the retrieved credential
     * @param password The password uner which the credential was stored
     * @return The retrieved credential
     * @see GSSCredential
     * @throws CredentialRetrievalException
     */
    public GSSCredential retrieveCredential(String username,
                                            String credname,
                                            int lifetime,
                                            String password)
            throws CredentialRetrievalException;

    /**
     * "Stores" a credential with the given parameters. Upon successful
     * return of this method, the given credential will be delegated
     * to the credential repository for the given lifetime. The
     * credential can later be "retrieved" with the given user name,
     * credential name and password. The user name and password are
     * required parameters, while the credential name is an optional
     * parameter (can be null), generally intended for users
     * who want to store multiple credentials under one user name.
     * Specifying a lifetime of 0 or less will result in a credential with
     * some default lifetime, determined by the credential repository.
     * @param username The user name under which to store the credential
     * @param credname The credential name under which to store the credential
     * @param lifetime The lifetime of the stored credential
     * @param password The password uner which to store the credential
     * @throws CredentialRetrievalException
     */
    public void storeCredential(String username,
                                String credname,
                                int lifetime,
                                GSSCredential credential,
                                String password)
            throws CredentialRetrievalException;

    /**
     * Destroys the "stored" credential with the given parameters. Upon a
     * successful return of this method, the credential that was "stored"
     * with the given parameters will no longer exist on the redential repository.
     * @param username The user name under which the credential was stored
     * @param credname The credential name under which the credential was stored
     * @param password The password uner which the credential was stored
     * @throws CredentialRetrievalException
     */
    public void destroyCredential(String username,
                                  String credname,
                                  String password)
            throws CredentialRetrievalException;

    /**
     * Changes the password under which a credential is "stored".
     * @param username The user name under which the credential was stored
     * @param credname The credential name under which the credential was stored
     * @param oldPassword The old password under which the credential was stored
     * @param newPassword The new password under which to store the credential
     * @throws CredentialRetrievalException
     */
    public void changeCredentialPassword(String username,
                                         String credname,
                                         String oldPassword,
                                         String newPassword)
            throws CredentialRetrievalException;
}
