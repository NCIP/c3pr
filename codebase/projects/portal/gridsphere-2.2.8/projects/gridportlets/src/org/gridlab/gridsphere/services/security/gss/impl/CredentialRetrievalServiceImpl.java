/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialRetrievalServiceImpl.java,v 1.1.1.1 2007-02-01 20:41:47 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceConfig;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceProvider;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.security.gss.*;
import org.gridlab.gridsphere.services.util.GridPortletsDatabase;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

import java.util.*;

/**
 * Implements the credentialContext retrieval service interface
 */
public class CredentialRetrievalServiceImpl
        implements CredentialRetrievalService,
                   PortletServiceProvider {

    public static int RETRIEVE_LIFETIME = 180 * 45;
    public static int STORE_LIFETIME = 180 * 45;
    private int retrievalLifetime  = RETRIEVE_LIFETIME;

    private static PortletLog log = SportletLog.getInstance(CredentialRetrievalServiceImpl.class);
    private GridPortletsDatabase pm = null;
    private ResourceRegistryService resourceRegistry = null;

    public void init(PortletServiceConfig config) throws PortletServiceUnavailableException {
        log.debug("in init of service");
        pm = GridPortletsDatabase.getInstance();
        PortletServiceFactory portletServiceFactory = SportletServiceFactory.getInstance();
        try {
            resourceRegistry = (ResourceRegistryService)portletServiceFactory.createPortletService(ResourceRegistryService.class, null, true);
        } catch (PortletServiceException e) {
            throw new PortletServiceUnavailableException("Unable to get instance of resource registry service", e);
        }
        try {
            String rl = config.getInitParameter("RetrievalLifetime");
            if (rl != null && rl.length() > 0) {
                retrievalLifetime = Integer.parseInt(rl);
            }
        } catch(Exception e) {
            log.warn("Unable to get retrieval lifetime parameter " + e.getMessage());
        }
    }

    public void destroy() {
    }

    public List getCredentialRepositories() {
        return resourceRegistry.getResources(CredentialRepositoryType.INSTANCE);
    }

    public CredentialRepository getActiveCredentialRepository() {
        CredentialRepository repository = null;
        List repositories =  resourceRegistry.getResources(CredentialRepositoryType.INSTANCE);
        if (repositories.size() > 0) {
            repository = (CredentialRepository)repositories.get(0);
        }
        return repository;
    }

    public List getCredentialRetrievalContexts(User user) {
        List result = null;
        try {
            result = pm.restoreList("from " + CredentialRetrievalContextImpl.class.getName()
                                  + " as cr where cr.UserOid='" + user.getID() + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: select from CredentialRetrievalContextImpl failed", e);
            result = new ArrayList(0);
        }
        return result;
    }

    public CredentialRetrievalContext getCredentialRetrievalContext(String oid) {
        CredentialRetrievalContext context = null;
        try {
            context = (CredentialRetrievalContext)
                    pm.restore("from " + CredentialRetrievalContextImpl.class.getName()
                             + " as cr where cr.oid='" + oid + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: Select CredentialContextImpl failed", e);
        }
        return context;
    }

    public CredentialRetrievalContext getCredentialRetrievalContextByDn(String dn) {
        CredentialRetrievalContext context = null;
        try {
            context = (CredentialRetrievalContext)
                    pm.restore("from " + CredentialRetrievalContextImpl.class.getName()
                             + " as cr where cr.Dn='" + dn + "'");
        } catch (PersistenceManagerException e) {
            log.error("FATAL: Select CredentialContextImpl failed", e);
        }
        return context;
    }

    public CredentialRetrievalContext createCredentialRetrievalContext(User user)
            throws CredentialRetrievalException {
        return new CredentialRetrievalContextImpl(user);
    }

    public GSSCredential saveCredentialRetrievalContext(CredentialRetrievalContext context, String password)
            throws CredentialRetrievalException {
        GSSCredential credential = null;
        CredentialRetrievalContextImpl contextImpl = (CredentialRetrievalContextImpl)context;
        Date now = new Date();
        if (context.getOid() == null) {
            log.debug("Creating retrieval context for " + context.getUserName());
            contextImpl.setDateCreated(now);
            credential = retrieveOnly(context, password);
            try {
                pm.create(context);
            } catch (PersistenceManagerException e) {
                log.error("FATAL: Create CredentialContext failed!");
                e.printStackTrace();
            }
        } else {
            log.debug("Updating retrieval context for " + context.getDn());
            credential = retrieveOnly(context, password);
            contextImpl.setDateLastUpdated(now);
            try {
                pm.update(context);
            } catch (PersistenceManagerException e) {
                log.error("FATAL: Update CredentialContext failed! oid" + context.getOid() + e);
            }
        }
        // If single sign-on
        /*
        if (context.getIsSingleSignOnEnabled()) {
            User user = context.getUser();
            if (!loginService.hasActiveAuthModule(user, CredentialAuthModule.class.getName())) {
                LoginAuthModule credAuthModule = loginService.getAuthModule(CredentialAuthModule.class.getName());
                if (credAuthModule != null) {
                    System.out.println("User = " + user.getUserName());
                    System.out.println("Module = " + credAuthModule.getModuleName());
                    loginService.addActiveAuthModule(user, credAuthModule);
                }
            }
            System.out.println("User = " + user.getUserID());
        }
        */
        return credential;
    }

    public void deleteCredentialRetrievalContext(CredentialRetrievalContext context) {
        try {
            pm.delete(context);
        } catch (PersistenceManagerException e) {
            log.error("FATAL: Delete CredentialContext failed!" + context.getOid());
        }
     }

    public List retrieveCredentials(User user, String password)
            throws CredentialRetrievalException {
        List credentials = new Vector();
        List contextList = getCredentialRetrievalContexts(user);
        StringBuffer errors = new StringBuffer();
        for (Iterator contexts = contextList.iterator(); contexts.hasNext();) {
            CredentialRetrievalContext context = (CredentialRetrievalContext) contexts.next();
            GSSCredential credential = null;
            try {
                credential = retrieveCredential(context, password);
                credentials.add(credential);
            } catch (CredentialRetrievalException e) {
                log.error("Unable to retrieve credential", e);
                errors.append(e);
                errors.append(" ");
            }
        }
        if (errors.length() > 0) {
            throw new CredentialRetrievalException(errors.toString());
        }
        return credentials;
    }

    public GSSCredential retrieveCredential(CredentialRetrievalContext context, String password)
            throws CredentialRetrievalException {
        GSSCredential credential = retrieveOnly(context, password);
        CredentialRetrievalContextImpl contextImpl = (CredentialRetrievalContextImpl)context;
        try {
            pm.update(context);
        } catch (PersistenceManagerException e) {
            log.error("FATAL: Update CredentialContext failed! oid" + context.getOid() + e);
        }
        contextImpl.setDateLastRetrieved(new Date());
        return credential;
    }

    /**
     * Retrieves the credential for the given retrieval context with saving the updated context.
     * @param context The credential retrieval context
     * @param password The credential retrieval password
     * @return The credential
     * @throws CredentialRetrievalException
     */
    public GSSCredential retrieveOnly(CredentialRetrievalContext context, String password)
            throws CredentialRetrievalException {
        CredentialRetrievalContextImpl contextImpl = (CredentialRetrievalContextImpl)context;
        String userName = context.getUserName();
        String credName = context.getCredentialName();
        int lifetime = context.getCredentialLifetime();
        if (lifetime <= 0) lifetime = retrievalLifetime;
        CredentialRepository repository = getActiveCredentialRepository();
        if (repository == null)
            throw new CredentialRetrievalException("No credential repository exists in registry!");
        GSSCredential credential
                = repository.retrieveCredential(userName, credName, lifetime, password);
        if (credential == null) {
            throw new CredentialRetrievalException("Credential retrieval failed");
        }
        try  {
            contextImpl.setDn(credential.getName().toString());
        } catch (GSSException e) {
            log.error("Unknown error occurred", e);
            throw new CredentialRetrievalException("Unknown error occured", e);
        }
        Date now = new Date();
        contextImpl.setDateLastRetrieved(now);
        return credential;
    }

    public void destroyCredential(CredentialRetrievalContext context, String password)
            throws CredentialRetrievalException {
        String userName = context.getUserName();
        String credName = context.getCredentialName();
        CredentialRepository repository = getActiveCredentialRepository();
        repository.destroyCredential(userName, credName, password);
    }

    public void changeCredentialPasswords(User user, String oldPassword, String newPassword)
            throws CredentialRetrievalException {
        List contextList = getCredentialRetrievalContexts(user);
        for (Iterator contexts = contextList.iterator(); contexts.hasNext();) {
            CredentialRetrievalContext context = (CredentialRetrievalContext) contexts.next();
            try {
                changeCredentialPassword(context, oldPassword, newPassword);
            } catch (CredentialRetrievalException e) {
                log.error("Unable to retrieve credentialContext", e);
            }
        }
    }

    public void changeCredentialPassword(CredentialRetrievalContext context, String oldPassword, String newPassword)
            throws CredentialRetrievalException {
        String userName = context.getUserName();
        String credName = context.getCredentialName();
        CredentialRepository repository = getActiveCredentialRepository();
        repository.changeCredentialPassword(userName, credName, oldPassword, newPassword);
        saveCredentialRetrievalContext(context, newPassword);
    }

    public int getDefaultCredentialLifetime() {
        return retrievalLifetime;
    }

    public void setDefaultCredentialLifetime(int lifetime) {
        retrievalLifetime = lifetime;
    }
}
