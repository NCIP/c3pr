/**
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: MyProxyResource.java,v 1.1.1.1 2007-02-01 20:41:22 kherm Exp $
 */
package org.gridlab.gridsphere.services.resources.myproxy;

import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.gridlab.gridsphere.services.security.gss.CredentialRetrievalException;
import org.gridlab.gridsphere.services.security.gss.CredentialException;
import org.gridlab.gridsphere.services.security.gss.CredentialRepository;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.impl.BaseServiceResource;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.globus.myproxy.*;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.gsi.GlobusCredential;
import org.gridforum.jgss.ExtendedGSSManager;
import org.gridforum.jgss.ExtendedGSSCredential;

import java.io.File;
import java.io.FileInputStream;

/**
 * Represents a MyProxy "resource" on the Grid. MyProxy is a credential repository
 * that may be used by a credential retrieval service and other services that
 * require access to remote user credentials.
 * @see CredentialRepository
 * @see org.gridlab.gridsphere.services.security.gss.CredentialRetrievalService
 */
public class MyProxyResource extends BaseServiceResource implements CredentialRepository {

    private static PortletLog log = SportletLog.getInstance(MyProxyResource.class);

    public static final String DEFAULT_PORT = "7512";
    public static final String DEFAULT_PROTOCOL = "myproxy";
    private transient MyProxy myproxyclient = null;
    private transient GSSCredential portalCredential = null;
    private String portalProxyFile = null;
    private String portalCertFile = null;
    private String portalKeyFile = null;
    private long portalCredentialFileDate = 0;
    private boolean usePortalCredential = true;

    public MyProxyResource() {
        super();
        setPort(DEFAULT_PORT);
        setProtocol(DEFAULT_PROTOCOL);
        setResourceType(MyProxyResourceType.INSTANCE);
    }

    public void init() {
        log.debug("Init myproxy resource on "+ getHost());
    }

    public MyProxy createMyProxyClient() {
        if (myproxyclient == null) {
            int portNum = Integer.parseInt(DEFAULT_PORT);
            try {
                portNum = Integer.parseInt(getPort());
            } catch (Exception e) {
                log.error("MyProxyResource port is invalid " + getPort() + ". Using default port " + portNum);
            }
            log.debug("Creating new myproxy client for " + getHost() + " on port " + portNum);
            myproxyclient = new MyProxy(getHost(), portNum);
        }
        return myproxyclient;
    }

    public GSSCredential getPortalCredential() {
        log.debug("Getting portal credential");
        if (isPortalCrentialDead() || hasPortalCredentialFileChanged()) {
            log.info("Creating portal credential...");
            if (portalProxyFile == null || portalProxyFile.length() == 0) {
                log.info("No portalProxyFile defined, using portalCertFile and portalKeyFile to " +
                          "create the portal credential...");
                try {
                    portalCredential = createPortalCredential(portalCertFile, portalKeyFile);
                } catch (CredentialException e) {
                    log.error("Unable to create portal credential with cert and key files!", e);
                }
            } else {
                try {
                    portalCredential = createPortalCredential(portalProxyFile);
                } catch (CredentialException e) {
                    log.error("Unable to create portal credental with proxy file!", e);
                }
            }
            log.info("Successfully created portal credential");
        }
        return portalCredential;
    }

    public String getPortalProxyFile() {
        return portalProxyFile;
    }

    public void setPortalProxyFile(String proxyFile) {
        portalProxyFile = proxyFile;
    }

    public String getPortalCertificateFile() {
        return portalCertFile;
    }

    public void setPortalCertificateFile(String certFile) {
        portalCertFile = certFile;
    }

    public String getPortalKeyFile() {
        return portalKeyFile;
    }

    public void setPortalKeyFile(String keyFile) {
        portalKeyFile = keyFile;
    }

    public boolean getUsePortalCredential() {
        return usePortalCredential;
    }

    public void setUsePortalCredential(boolean flag) {
        usePortalCredential = flag;
    }

    public GSSCredential retrieveCredential(String userName,
                                            String credName,
                                            int lifetime,
                                            String passphrase)
            throws CredentialRetrievalException {
        log.info("retrieveCredential (" + userName+','+credName+","+lifetime+",********)");
//        if (true)  {
//            log.error("Hack so you can edit on plane... don't forget to remove this!!!");
//            return getPortalCredential();
//        }
        GSSCredential credential = null;
        MyProxy myProxy = createMyProxyClient();

        // retrieve a portal credential from cert and key or proxyfile and use it to authenticate
        // to the myproxy resource when retrieving user proxy
        if (usePortalCredential) {
            GetParams getParams = new GetParams();
            // Username
            getParams.setUserName(userName);
            // Credential name
            if (credName != null && ! credName.equals("") ) {
                getParams.setCredentialName(credName);
            }
            // Passphrase
            try {
                getParams.setPassphrase(passphrase);
            } catch (IllegalArgumentException e) {
                String m = "Error retrieving Globus proxy with MyProxy client";
                log.error(m, e);
                throw new CredentialRetrievalException(e.getMessage());
            }
            // Lifetime
            getParams.setLifetime(lifetime);
            // Portal credential
            GSSCredential portalCredential = getPortalCredential();
            if (portalCredential == null) {
                throw new CredentialRetrievalException("Unable to obtain portal credential for retrieving credentials");
            }
            // Retrieve credential
            try {
                credential = myProxy.get(portalCredential, getParams);

            } catch (MyProxyException e) {
                String m = "Error retrieving Globus proxy with MyProxy client";
                log.error(m, e);
                throw new CredentialRetrievalException(e.getMessage());
            } catch (IllegalArgumentException e) {
                String m = "Error retrieving Globus proxy with MyProxy client";
                log.error(m, e);
                throw new CredentialRetrievalException(e.getMessage());
            }
        // do NOT use a portal credential to authenticate to myproxy server when retrieving user proxy
        } else {
            try {
                credential = myProxy.get(userName, passphrase, lifetime);
            } catch (MyProxyException e) {
                String m = "Error retrieving Globus proxy with MyProxy client";
                log.error(m, e);
                throw new CredentialRetrievalException(e.getMessage());
            } catch (IllegalArgumentException e) {
                String m = "Error retrieving Globus proxy with MyProxy client";
                log.error(m, e);
                throw new CredentialRetrievalException(e.getMessage());
            }
        }
        if (log.isDebugEnabled()) {
            GlobusGSSCredentialImpl globusCredential = (GlobusGSSCredentialImpl)credential;
            log.debug("Retrieved user proxy dn = " + globusCredential.getGlobusCredential().getSubject()
                    + "\nTime left on proxy = " + globusCredential.getGlobusCredential().getTimeLeft());
        }
        log.debug("Exiting myProxyGet(username, passphrase, lifetime)");
        return credential;
    }

    public void storeCredential(String userName,
                                         String credName,
                                         int lifetime,
                                         GSSCredential credential,
                                         String passphrase)
            throws CredentialRetrievalException {
        log.info("storeCredential (" + userName+','+credName+","+lifetime+",********)");
        MyProxy myProxy = createMyProxyClient();
        InitParams initParams = new InitParams();
        // Username
        initParams.setUserName(userName);
        // Credential name
        if (credName != null) {
            initParams.setCredentialName(credName);
        }
        // Passphrase
        try {
            initParams.setPassphrase(passphrase);
        } catch (IllegalArgumentException e) {
            String m = "Error retrieving Globus proxy with MyProxy client";
            log.error(m, e);
            throw new CredentialRetrievalException(e.getMessage());
        }
        // Lifetime
        initParams.setLifetime(lifetime);
        // Retrieve credential
        try {
           myProxy.put(credential, initParams);
        } catch (MyProxyException e) {
            String m = "Error putting credential with MyProxy client";
            log.error(m, e);
            throw new CredentialRetrievalException(e.getMessage());
        }
   }

    public void destroyCredential(String userName,
                                  String credName,
                                  String passphrase)
            throws CredentialRetrievalException {
        log.info("destroyCredential (" + userName+','+credName+",********)");
        // Get old credential
        GSSCredential credential = retrieveCredential(userName, credName, 60*45, passphrase);
        // MyProxy client
        MyProxy myProxy = createMyProxyClient();
        // MyProxy parameters
        DestroyParams params = new DestroyParams(userName, passphrase);
        if (credName != null) {
            params.setCredentialName(credName);
        }
        try {
            myProxy.destroy(credential, params);
        } catch (MyProxyException e) {
            log.error("Unable to destroy credentials", e);
            throw new CredentialRetrievalException("Unable to destroy credentials");
        }
    }

    public void changeCredentialPassword(String userName,
                                         String credName,
                                         String oldPassword,
                                         String newPassword)
            throws CredentialRetrievalException {
        // Get credential with old password
        GSSCredential credential = retrieveCredential(userName, credName, 60, oldPassword);
        // Set up myproxy client and change password params
        MyProxy myProxy = createMyProxyClient();
        ChangePasswordParams chgParams = new ChangePasswordParams();
        // Username
        chgParams.setUserName(userName);
        // Credential name
        if (credName != null) {
            chgParams.setCredentialName(credName);
        }
        // New passphrase
        try {
            chgParams.setPassphrase(oldPassword);
            chgParams.setNewPassphrase(newPassword);
        } catch (IllegalArgumentException e) {
            String m = "Error retrieving Globus proxy with MyProxy client";
            log.error(m, e);
            throw new CredentialRetrievalException(e.getMessage());
        }

        // Portal credential
        GSSCredential portalCredential = null;
        if (usePortalCredential) {
            portalCredential = getPortalCredential();
            if (portalCredential == null) {
                throw new CredentialRetrievalException("Unable to obtain portal credential for retrieving credentials");
            }
        }

        // Change password
        try {
            myProxy.changePassword(credential, chgParams);
        } catch (MyProxyException e) {
            String m = "Error changing credential password with MyProxy client";
            log.error(m, e);
            throw new CredentialRetrievalException(e.getMessage());
        }
    }

    public CredentialInfo getCredentialInfo(GSSCredential credential, String username, String password)
            throws MyProxyException {
        MyProxy myProxy = createMyProxyClient();
        return myProxy.info(credential, username, password);
    }

    private boolean isPortalCrentialDead() {
        try {
            return (portalCredential == null || portalCredential.getRemainingLifetime() == 0);
        } catch (GSSException e) {
            log.error("Error getting lifetime of portal credential", e);
        }
        return false;
    }

    private boolean hasPortalCredentialFileChanged() {
        String fileName = (portalProxyFile == null || portalProxyFile.length() == 0) ? portalCertFile : portalProxyFile;
        if (fileName == null) {
            log.warn("No files have been specified for creating a portal credential");
            return false;
        }
        try {
            File f = new File(fileName);
            return (f.lastModified() > portalCredentialFileDate);
        } catch (Exception e) {
            log.error("Error checking date of portal credential file " + fileName, e);
        }
        return false;
    }

    private GSSCredential createPortalCredential(String proxyFile)
            throws CredentialException {
        if (proxyFile == null || proxyFile.length() == 0) {
            throw new CredentialException("Cannot create portal credential: portalProxyFile is null!");
        }
        try {
            log.info("Creating portal credential with proxy file " + proxyFile);
            File file = new File(proxyFile);
            portalCredentialFileDate = file.lastModified();
            byte[] data = new byte[(int) file.length()];
            FileInputStream in = new FileInputStream(file);
            // read in the credential data
            in.read(data);
            in.close();

            ExtendedGSSManager manager = (ExtendedGSSManager) ExtendedGSSManager.getInstance();
            return
                    manager.createCredential(data,
                            ExtendedGSSCredential.IMPEXP_OPAQUE,
                            GSSCredential.DEFAULT_LIFETIME,
                            null, // use default mechanism - GSI
                            GSSCredential.INITIATE_AND_ACCEPT);
        } catch (Exception e) {
            log.error("Unable to create portal credential with " + proxyFile, e);
            throw new CredentialException(e.getMessage());
        }
    }

    private GSSCredential createPortalCredential(String portalCertFile, String portalKeyFile)
            throws CredentialException {
        if (portalCertFile == null) {
            throw new CredentialException("Cannot create portal credential: portalCertFile is null!");
        }
        if (portalKeyFile == null) {
            throw new CredentialException("Cannot create portal credential: portalKeyFile is null!");
        }
        try {
            log.info("Creating portal credential with certificate file "
                    + portalCertFile + " and private key file " + portalKeyFile);
            File file = new File(portalCertFile);
            portalCredentialFileDate = file.lastModified();
            GlobusCredential hostCred = new GlobusCredential(portalCertFile, portalKeyFile);
            return new GlobusGSSCredentialImpl(hostCred,
                    GSSCredential.INITIATE_AND_ACCEPT);
        } catch (Exception e) {
            log.error("Unable to create portal credential with " + portalCertFile + " and " + portalKeyFile, e);
            throw new CredentialException(e.getMessage());
        }
    }

    public void copy(Resource resource) {
        super.copy(resource);
        MyProxyResource myproxyResource = (MyProxyResource)resource;
        this.portalProxyFile = myproxyResource.getPortalProxyFile();
        this.portalCertFile = myproxyResource.getPortalCertificateFile();
        this.portalKeyFile = myproxyResource.getPortalKeyFile();
    }


    public String getAuthorizedProxyFile() {
        return portalProxyFile;
    }

    public void setAuthorizedProxyFile(String proxyFile) {
        portalProxyFile = proxyFile;
    }

    public String getAuthorizedCertFile() {
        return portalCertFile;
    }

    public void setAuthorizedCertFile(String certFile) {
        portalCertFile = certFile;
    }

    public String getAuthorizedKeyFile() {
        return portalKeyFile;
    }

    public void setAuthorizedKeyFile(String keyFile) {
        portalKeyFile = keyFile;
    }
}
