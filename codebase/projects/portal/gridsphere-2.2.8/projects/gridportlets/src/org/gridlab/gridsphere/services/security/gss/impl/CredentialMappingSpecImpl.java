/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingSpecImpl.java,v 1.1.1.1 2007-02-01 20:41:45 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss.impl;

import org.gridlab.gridsphere.services.task.impl.BaseTaskSpec;
import org.gridlab.gridsphere.services.task.TaskType;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingTaskType;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingSpec;

import java.util.List;
import java.util.ArrayList;

public class CredentialMappingSpecImpl extends BaseTaskSpec implements CredentialMappingSpec {

    private boolean testAllResources = false;
    private String credentialDn = null;
    private List resourceDns = new ArrayList();

    public CredentialMappingSpecImpl() {
        super();
    }

    public CredentialMappingSpecImpl(CredentialMappingSpecImpl spec) {
        super(spec);
    }

    public TaskType getTaskType() {
        return CredentialMappingTaskType.INSTANCE;
    }

    public String getCredentialDn() {
        return credentialDn;
    }

    public void setCredentialDn(String credentialDn) {
        this.credentialDn = credentialDn;
    }

    public List getResourceDns() {
        return resourceDns;
    }

    public void setResourceDns(List resourceDns) {
        this.resourceDns = resourceDns;
    }

    public boolean getTestAllResources() {
        return testAllResources;
    }

    public void setTestAllResources(boolean testAllResources) {
        this.testAllResources = testAllResources;
    }
}
