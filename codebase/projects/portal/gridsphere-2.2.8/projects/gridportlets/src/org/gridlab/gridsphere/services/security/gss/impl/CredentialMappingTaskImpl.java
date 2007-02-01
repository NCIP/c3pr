/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTaskImpl.java,v 1.1.1.1 2007-02-01 20:41:47 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.task.impl.BaseTask;
import org.gridlab.gridsphere.services.task.TaskStatus;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.gridlab.gridsphere.services.resource.Resource;
import org.gridlab.gridsphere.services.resource.ResourceException;
import org.gridlab.gridsphere.services.security.gss.*;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.portlet.service.PortletServiceException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.ietf.jgss.GSSCredential;
import org.ietf.jgss.GSSException;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class CredentialMappingTaskImpl extends BaseTask implements Runnable, CredentialMappingTask {

    private static PortletLog log = SportletLog.getInstance(CredentialMappingTaskImpl.class);

    private Thread thread = null;
    private List credentialMappingTests = new ArrayList();
    private Long lock = new Long(0);
    private boolean cancelFlag = false;

    public CredentialMappingTaskImpl() {
        super();
    }

    public CredentialMappingTaskImpl(CredentialMappingSpecImpl taskSpec) {
        super(taskSpec);
    }

    public List getCredentialMappingTests() {
        return credentialMappingTests;
    }

    public void setCredentialMappingTests(List credentialMappingTests) {
        this.credentialMappingTests = credentialMappingTests;
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, "CredentialMappingTaskImpl");
            thread.start();
        }
    }

    public void cancel() {
        synchronized(lock) {
            cancelFlag = true;
        }
    }

    public void run() {
        log.debug("Running credential mapping task");
        // Get credential manager service
        CredentialManagerService credentialManagerService = null;
        try {
            credentialManagerService = (CredentialManagerService)
                    SportletServiceFactory.getInstance().createPortletService(
                            CredentialManagerService.class, null, true);
        } catch (PortletServiceException e) {
            setTaskStatus(TaskStatus.FAILED, "Unable to get instance of credential manager service");
            return;
        }
        // Get resource registry service
        ResourceRegistryService resourceRegistryService = null;
        try {
            resourceRegistryService = (ResourceRegistryService)
                    SportletServiceFactory.getInstance().createPortletService(
                            ResourceRegistryService.class, null, true);
        } catch (PortletServiceException e) {
            setTaskStatus(TaskStatus.FAILED, "Unable to get instance of resource register service");
            return;
        }
        CredentialMappingSpec credentialMappingSpec = (CredentialMappingSpec)taskSpec;
        // Get dn
        String credentialDn = credentialMappingSpec.getCredentialDn();
        if (credentialDn == null) {
            setTaskStatus(TaskStatus.FAILED, "Credential distinguished name must be provided");
            return;
        }
        // Get locations
        List resourceDns = null;
        boolean testAllResources = credentialMappingSpec.getTestAllResources();
        if (testAllResources) {
            log.debug("Testing all gss enabled resources");
            resourceDns = new ArrayList();
            Iterator gssResources = resourceRegistryService.getResources(GssEnabledResourceType.INSTANCE).iterator();
            while (gssResources.hasNext()) {
                GssEnabledResource gssResource = (GssEnabledResource)gssResources.next();
                String resourceDn = gssResource.getDn();
                log.debug("Adding gss enabled resource " + resourceDn + " to list of resources to test");
                resourceDns.add(resourceDn);
            }

        } else {
            resourceDns = credentialMappingSpec.getResourceDns();
            if (resourceDns == null || resourceDns.size() == 0) {
                setTaskStatus(TaskStatus.COMPLETED, "No resource distinguished names provided, exiting immediately");
                return;
            }
        }
        log.debug("Getting credential context with dn " + credentialDn);
        // Get credential context
        CredentialContext context = credentialManagerService.getCredentialContextByDn(credentialDn);
        if (context == null) {
            setTaskStatus(TaskStatus.FAILED, "No credential context exists for given dn");
            return;
        }
        log.debug("Getting credential from context " + credentialDn);
        // Make sure context is active
        GSSCredential credential = context.getCredential();
        if (credential == null) {
            setTaskStatus(TaskStatus.FAILED, "Credential for given dn not active");
            return;
        }
        // Perform test for each given location
        int numberTests = resourceDns.size();
        int numberSucceeded = 0;
        Iterator iterator = resourceDns.iterator();
        while (iterator.hasNext()) {
            synchronized(lock) {
                if (cancelFlag) {
                    setTaskStatus(TaskStatus.CANCELED, "Task canceled after " + numberTests + " credential mapping attempts");
                    return;
                }
            }
            ++numberTests;
            // Get next resource dn
            String resourceDn = (String)iterator.next();
            // Create credential test
            CredentialMappingTestImpl test = new CredentialMappingTestImpl();
            test.setCredentialDn(credentialDn);
            test.setResourceDn(resourceDn);
            credentialMappingTests.add(test);
            // Get resource for given location
            log.debug("Getting resource with dn " + resourceDn);
            Resource resource = resourceRegistryService.getResourceByDn(resourceDn);
            if (resource == null) {
                test.setErrorCode(CredentialMappingTest.ERROR_RESOURCE_NOT_FOUND);
                test.setErrorMessage("Resource not found for given location");
            } else if (!resource.isResourceType(GssEnabledResourceType.INSTANCE)) {
                test.setErrorCode(CredentialMappingTest.ERROR_RESOURCE_NOT_GSS_ENABLED);
                test.setErrorMessage("Resource at given location is not a gss enabled resource");
            } else {
                log.debug("Testing gss enabled resource " + resourceDn);
                GssEnabledResource gssResource = (GssEnabledResource)resource;
                try {
                    gssResource.authenticates(credential);
                    test.setErrorCode(CredentialMappingTest.SUCCESS);
                    ++numberSucceeded;
                    context.putCredentialMapping(resourceDn);
                } catch (GSSException e) {
                    test.setErrorCode(CredentialMappingTest.ERROR_CREDENTIAL_EXCEPTION);
                    test.setErrorMessage(e.getMessage());
                    context.removeCredentialMapping(resourceDn);
                } catch (ResourceException e) {
                    test.setErrorCode(CredentialMappingTest.ERROR_RESOURCE_EXCEPTION);
                    test.setErrorMessage(e.getMessage());
                    context.removeCredentialMapping(resourceDn);
                } catch (Exception e) {
                    test.setErrorCode(CredentialMappingTest.ERROR_UNKNOWN);
                    test.setErrorMessage(e.getMessage());
                    context.removeCredentialMapping(resourceDn);
                }
            }
            try {
                credentialManagerService.saveCredentialContext(context);
            } catch (Exception e) {
                log.error("Failed to save credential context", e);
                setTaskStatus(TaskStatus.FAILED, e.getMessage());
                return;
            }
        }
        log.debug("Completed credential mapping task");
        setTaskStatus(TaskStatus.COMPLETED, "" + numberSucceeded + " of " + numberTests + " credential mappings attempts succeeded");
    }
}
