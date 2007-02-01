/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: GssExamples.java,v 1.1.1.1 2007-02-01 20:39:33 kherm Exp $
 */
package org.gridlab.gridsphere.examples.services.security.gss;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.service.PortletServiceUnavailableException;
import org.gridlab.gridsphere.portlet.service.PortletServiceNotFoundException;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.services.security.gss.*;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.ietf.jgss.GSSCredential;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Provides a number of useful examples illustrating how to use Grid Portlets
 * to retrieve and manage GSS credentials as well as test if they can be used
 * to authenticate GSS enabled resources.
 */
public class GssExamples {

    private PortletLog log = SportletLog.getInstance(GssExamples.class);
    private CredentialManagerService credentialManagerService = null;
    private ResourceRegistryService resourceRegistryService = null;

//    private CredentialRetrievalService credentialRetrievalService = null;
//    private CredentialMappingService credentialMappingService = null;

    /**
     * Constructs an instance of GssExamples.
     * @throws PortletServiceUnavailableException If unable to get required portlet services.
     */
    public GssExamples() throws PortletServiceUnavailableException {
        log.info("Creating JobExamples");
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        try {
            credentialManagerService = (CredentialManagerService)
                    factory.createPortletService(CredentialManagerService.class, null, true);
            resourceRegistryService = (ResourceRegistryService)
                    factory.createPortletService(ResourceRegistryService.class, null, true);

//            credentialRetrievalService = (CredentialRetrievalService)
//                    factory.createPortletService(CredentialRetrievalService.class, null, true);
//            credentialMappingService = (CredentialMappingService)
//                    factory.createPortletService(CredentialMappingService.class, null, true);

        } catch (PortletServiceNotFoundException e) {
            log.error("Unable to initialize required portlet services", e);
            throw new PortletServiceUnavailableException(e);
        }
    }

    /**
     * Returns the distinguished names of all the resources to which the given user
     * is able to authenticate with his/her active credentials. If the user
     * has no active credentials or there are no gss enabled resources in
     * the resource registry, this method will return an empty list.
     * @param user The user
     * @return The list of resource of dns (strings)
     */
    public List getValidGssEnabledResourceDns(User user) {
        List validResourceList = new ArrayList(1);
        Iterator credentialIter = credentialManagerService.getActiveCredentials(user).iterator();
        while (credentialIter.hasNext()) {
            GSSCredential credential = (GSSCredential)credentialIter.next();
            validResourceList.addAll(getValidGssEnabledResourceDns(credential));
        }
        return validResourceList;
    }

    /**
     * Returns the distinguished names of all the resources to which the given
     * credential successfully authenticates. If there are no gss enabled
     * resources in the resource registry, this method will return an empty list.
     * @param credential The credential
     * @return The list of resource of dns (strings)
     */
    public List getValidGssEnabledResourceDns(GSSCredential credential) {
        List validResourceList = new ArrayList(1);
        Iterator gssResourceIter = resourceRegistryService.getResources(GssEnabledResourceType.INSTANCE).iterator();
        while (gssResourceIter.hasNext()) {
            GssEnabledResource gssResource = (GssEnabledResource)gssResourceIter.next();
            String resourceDn = gssResource.getDn();
            log.debug("Testing whether credential authenticates to " + resourceDn);
            try {
                gssResource.authenticates(credential);
                validResourceList.add(resourceDn);
            } catch (Exception e) {
                log.debug("Credential failed to authenticate to resource " + resourceDn + " with error " + e.getMessage());
            }
        }
        return validResourceList;
    }
}
