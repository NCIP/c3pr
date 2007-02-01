package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.task.TaskSpec;

import java.util.List;

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingSpec.java,v 1.1.1.1 2007-02-01 20:41:30 kherm Exp $
 */
public interface CredentialMappingSpec extends TaskSpec {

    public String getCredentialDn();

    public void setCredentialDn(String credentialDn);

    public boolean getTestAllResources();

    public void setTestAllResources(boolean flag);

    public List getResourceDns();

    public void setResourceDns(List resourceDns);
}
