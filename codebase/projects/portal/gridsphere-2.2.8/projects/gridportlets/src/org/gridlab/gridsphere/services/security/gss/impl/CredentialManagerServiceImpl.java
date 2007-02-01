/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialManagerServiceImpl.java,v 1.1.1.1 2007-02-01 20:41:45 kherm Exp $
 * <p>
 * Implements the credentialContext manager service interface
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.security.gss.CredentialContext;
import org.gridlab.gridsphere.services.security.gss.CredentialException;
import org.gridlab.gridsphere.services.security.gss.CredentialManagerService;
import org.gridlab.gridsphere.services.security.gss.GssEnabledResource;
import org.gridlab.gridsphere.services.util.GridPortletsDatabase;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;

import java.util.*;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.security.cert.X509Certificate;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Implements the credential manager service interface.
 */
public class CredentialManagerServiceImpl
        implements CredentialManagerService,
                   PortletServiceProvider {

    private static PortletLog log = SportletLog.getInstance(CredentialManagerServiceImpl.class);
    private static Hashtable credentials = new Hashtable();
    private static Hashtable credentialFiles = new Hashtable();
    private static boolean saveCredentialsToFile = false;
    private static String activeCredentialDir = null;
    private GridPortletsDatabase pm = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        pm = GridPortletsDatabase.getInstance();
        String configCredentialToFile = config.getInitParameter("SaveCredentialsToFile");
        log.debug("configCredentialToFile = " + configCredentialToFile);
        if (configCredentialToFile == null || configCredentialToFile.equals("") ||
            configCredentialToFile.equalsIgnoreCase("false") || configCredentialToFile.equalsIgnoreCase("no"))  {
            log.info("Credentials will not be saved to files");
            return;
        }
        saveCredentialsToFile = true;
        String configCredentialDir = config.getInitParameter("ActiveCredentialDir");
        if (configCredentialDir == null || configCredentialDir.equals(""))  {
            try {
                File tempFile = File.createTempFile("temp", "temp");
                activeCredentialDir = tempFile.getParent();
            } catch (IOException e) {
                throw new PortletServiceUnavailableException(e.getMessage());
            }
        } else {
            activeCredentialDir = configCredentialDir;
        }
        if (!activeCredentialDir.endsWith(File.separator)) {
            activeCredentialDir += File.separator;
        }
        log.info("Credentials will be saved to files in " + activeCredentialDir);
    }

    public void destroy() {
    }

    public List getCredentialContexts(User user) {
        List result = null;
        try {
            result = pm.restoreList("from " + CredentialContextImpl.class.getName()
                                  + " as cred where cred.UserOid='" + user.getID() + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: select from CredentialContextImpl failed", e);
        }
        return result;
    }

    public CredentialContext getCredentialContext(String oid) {
        CredentialContext context = null;
        try {
            context = (CredentialContext)
                    pm.restore("from " + CredentialContextImpl.class.getName()
                             + " as cred where cred.oid='" + oid + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: Select CredentialContextImpl failed", e);
        }
        return context;
    }

    public CredentialContext getCredentialContextByDn(String dn) {
        CredentialContext context = null;
        try {
            context = (CredentialContext)
                    pm.restore("from " + CredentialContextImpl.class.getName()
                             + " as cred where cred.Dn='" + dn + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: Select CredentialContextImpl failed", e);
        }
        return context;
    }

    public CredentialContext createCredentialContext(User user, String dn)
            throws CredentialException {
        return new CredentialContextImpl(user, dn);
    }

    public CredentialContext createCredentialContext(User user, GSSCredential credential)
            throws CredentialException {
        return new CredentialContextImpl(user, credential);
    }

    public void saveCredentialContext(CredentialContext context)
            throws CredentialException {
        CredentialContextImpl contextImpl = (CredentialContextImpl)context;
        Date now = new Date();
        if (context.getOid() == null) {
            log.debug("Creating credential context for " + context.getDn());
            contextImpl.setDateCreated(now);
            try {
                pm.create(context);
            } catch (PersistenceManagerException e) {
                log.error("FATAL: Create credential context failed!");
                e.printStackTrace();
            }
            // If has a credential already, save a reference to it
            GSSCredential credential = context.getCredential();
            if (credential != null) {
                setCredential(context.getDn(), credential);
            }
        } else {
            log.debug("Updating credential context for " + context.getDn());
            contextImpl.setDateLastUpdated(now);
            try {
                pm.update(context);
            } catch (PersistenceManagerException e) {
                log.error("FATAL: Update credential context failed! oid" + context.getOid() + e);
            }
        }
    }

    public void deleteCredentialContext(CredentialContext context) {
        try {
            pm.delete(context);
        } catch (PersistenceManagerException e) {
            log.error("FATAL: Delete CredentialContext failed!" + context.getOid());
        }
    }

    public List activateCredentialContexts(List credentials)
            throws CredentialException {
        List contexts = new Vector(1);
        for (int ii = 0; ii < credentials.size(); ++ii) {
            GSSCredential credential = (GSSCredential)credentials.get(ii);
            try {
                CredentialContext context = activateCredentialContext(credential);
                contexts.add(context);
            } catch (CredentialException e) {
                log.warn(e.getMessage());
            }
        }
        return contexts;
    }

    public CredentialContext activateCredentialContext(GSSCredential credential)
            throws CredentialException {
        String credDn = null;
        try {
            credDn = credential.getName().toString();
        } catch (GSSException e) {
            log.warn(e.getMessage());
            throw new CredentialException("Unknown error occured", e);
        }
        CredentialContext context = getCredentialContextByDn(credDn);
        if (context == null) {
            throw new CredentialException("No credentialContext context found with dn " + credDn);
        }
        context.activate(credential);
        saveCredentialContext(context);
        return context;
   }

    public void deactivateCredentialContexts(User user) {
        List userContexts = getCredentialContexts(user);
        for (Iterator contexts = userContexts.iterator(); contexts.hasNext();) {
            CredentialContextImpl contextImpl = (CredentialContextImpl) contexts.next();
            contextImpl.deactivate();
        }
    }

    public void deactivateCredentialContext(CredentialContext context) {
        CredentialContextImpl contextImpl = (CredentialContextImpl)context;
        contextImpl.deactivate();
    }

    public List getActiveCredentialContexts(User user) {
        List userContexts = getCredentialContexts(user);
        List activeContexts = new Vector(1);
        for (Iterator contexts = userContexts.iterator(); contexts.hasNext();) {
            CredentialContext context = (CredentialContext) contexts.next();
            if (context.isActive()) {
                activeContexts.add(context);
            }
        }
        return activeContexts;
    }

    public List getActiveCredentialContexts(User user, GssEnabledResource resource) {
        List userContexts = getCredentialContexts(user);
        List activeContexts = new Vector(1);
        for (Iterator contexts = userContexts.iterator(); contexts.hasNext();) {
            CredentialContext context = (CredentialContext) contexts.next();
            if (context.isActive() && context.hasCredentialMapping(resource.getDn())) {
                activeContexts.add(context);
            }
        }
        return activeContexts;
    }

    public List getActiveCredentials(User user) {
        List userContexts = getCredentialContexts(user);
        List activeCredentials = new Vector(1);
        for (Iterator contexts = userContexts.iterator(); contexts.hasNext();) {
            CredentialContext context = (CredentialContext) contexts.next();
            if (context.isActive()) {
                GSSCredential credential = context.getCredential();
                activeCredentials.add(credential);
            }
        }
        return activeCredentials;
    }

    public List getActiveCredentials(User user, GssEnabledResource resource) {
        List userContexts = getCredentialContexts(user);
        List activeCredentials = new Vector(1);
        for (Iterator contexts = userContexts.iterator(); contexts.hasNext();) {
            CredentialContext context = (CredentialContext) contexts.next();
            if (context.isActive() && context.hasCredentialMapping(resource.getDn())) {
                GSSCredential credential = context.getCredential();
                activeCredentials.add(credential);
            }
        }
        return activeCredentials;
    }

    public CredentialContext getDefaultCredentialContext(User user) {
        List credentials = getActiveCredentialContexts(user);
        if (credentials.size() > 0) {
            return (CredentialContext)credentials.get(0);
        }
        return null;
    }

    public CredentialContext getDefaultCredentialContext(User user, GssEnabledResource resource) {
        List credentials = getActiveCredentialContexts(user, resource);
        if (credentials.size() > 0) {
            return (CredentialContext)credentials.get(0);
        }
        return null;
    }

    public GSSCredential getDefaultCredential(User user) {
        List credentials = getActiveCredentials(user);
        if (credentials.size() > 0) {
            return (GSSCredential)credentials.get(0);
        }
        return null;
    }

    public GSSCredential getDefaultCredential(User user, GssEnabledResource resource) {
        List credentials = getActiveCredentials(user, resource);
        if (credentials.size() > 0) {
            return (GSSCredential)credentials.get(0);
        }
        return null;
    }

    public List getCredentialResourceMappings(User user) {
        List userContexts = getCredentialContexts(user);
        List resourceMappings = new Vector();
        for (Iterator contexts = userContexts.iterator(); contexts.hasNext();) {
            CredentialContext context = (CredentialContext) contexts.next();
            resourceMappings.addAll(context.getCredentialMappings());
        }
        return resourceMappings;
    }

    public List getActiveCredentialResourceMappings(User user) {
        List userContexts = getCredentialContexts(user);
        List resourceMappings = new Vector();
        for (Iterator contexts = userContexts.iterator(); contexts.hasNext();) {
            CredentialContext context = (CredentialContext) contexts.next();
            if (context.isActive()) {
                resourceMappings.addAll(context.getCredentialMappings());
            }
        }
        return resourceMappings;
    }

    public List getActiveCredentialFileNames(User user) {
        List activeCredentialFileNames = new Vector(1);
        if (saveCredentialsToFile) {
            List userContexts = getCredentialContexts(user);
            for (Iterator contexts = userContexts.iterator(); contexts.hasNext();) {
                CredentialContext context = (CredentialContext) contexts.next();
                if (context.isActive()) {
                    String fileName = getCredentialFileName(context.getDn());
                    if (fileName != null) {
                        activeCredentialFileNames.add(fileName);
                    }
                }
            }
        }
        return activeCredentialFileNames;
    }

    public boolean getSaveActiveCredentialsToFile() {
        return saveCredentialsToFile;
    }

    public void setSaveActiveCredentialsToFile(boolean flag) {
        saveCredentialsToFile = flag;
    }

    public String getActiveCredentialDirectory() {
        return activeCredentialDir;
    }

    public void setActiveCredentialDirectory(String dir) {
        activeCredentialDir = dir;
    }

    public String getActiveCredentialFileName(String dn) {
        if (saveCredentialsToFile) {
            CredentialContext context = getCredentialContextByDn(dn);
            if (context.isActive()) {
                return getCredentialFileName(context.getDn());
            }
        }
        return null;
    }

    /**
     * Returns the credential in our hash table with the given DN as the key.
     * @param dn The DN.
     * @return The credential, null if not found.
     */
    protected static GSSCredential getCredential(String dn) {
        return (GSSCredential)credentials.get(dn);
    }

    /**
     * Stores the credential in our hash table with the given DN as the key.
     * @param dn The DN.
     * @param credential The credential.
     */
    protected static void setCredential(String dn, GSSCredential credential) throws CredentialException {
        if (saveCredentialsToFile) {
            try {
                String fileName = createFile(dn, credential);
                credentialFiles.put(dn, fileName);
            } catch (IOException e) {
                log.error("Unable to save credential to file", e);
                throw new CredentialException("Unable to save credential to file");
            }
        }
        credentials.put(dn, credential);
    }

    /**
     * Stores the credential from our hash table with the DN.
     * @param dn The DN.
     */
    protected static void removeCredential(String dn) {
        credentials.remove(dn);
        credentialFiles.remove(dn);
    }

    /**
     * Returns the name of the file to which credentials with the given dn are stored.
     * @param dn The DN.
     * @return The file name, null if not found.
     */
    protected static String getCredentialFileName(String dn) {
        return (String)credentialFiles.get(dn);
    }

    protected static String createFile(String dn, GSSCredential credential) throws IOException {
        if (! (credential instanceof GlobusGSSCredentialImpl) ) {
            return null;
        }
        GlobusGSSCredentialImpl gssCredential = (GlobusGSSCredentialImpl)credential;
        String fileName = (String)credentialFiles.get(dn);
        File file = null;
        // If file doesn't exist then create it...
        if (fileName == null) {
            // Get file name in which to store credential
            fileName = activeCredentialDir + DN2MD5Filename(gssCredential.getCertificateChain()[0]);
            // Create file instance...
            file = new File(fileName);
        } else {
            file = new File(fileName);
        }
        // Delete file if exists...
        if (file.exists()) {
            file.delete();
        }
        log.info("Saving credential [" + dn + "] to file " + fileName);
        // Write credential to file output stream
        FileOutputStream fileOut = new FileOutputStream(file);
        gssCredential.getGlobusCredential().save(fileOut);
        fileOut.close();
        return fileName;
    }

    protected static String DN2MD5Filename(X509Certificate cert) {

        byte[] issuer = cert.getIssuerDN().getName().getBytes();
        byte[] user = cert.getSubjectDN().getName().getBytes();


        String token = null;

        /* Create a message digest from the certificate DN */
        /* Crunches 32 byte hash into 8 byte non-unique hash */
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(issuer);
            md5.update(user);
            token = toHex(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("No such algorithm " + e.getMessage(), e);
        }
        return token;
    }

    /**
     * Return an 8 byte representation of the 32 byte MD5 digest
     *
     * @param digest the message digest
     * @return String 8 byte hexadecimal
     */
    protected static String toHex(byte[] digest) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < digest.length; i++) {
            buf.append(Integer.toHexString((int) digest[i] & 0x00FF));
        }
        return buf.toString();
    }
}
