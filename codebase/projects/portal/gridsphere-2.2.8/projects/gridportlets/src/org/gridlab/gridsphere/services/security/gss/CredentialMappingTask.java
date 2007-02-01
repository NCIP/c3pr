package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.services.task.Task;

import java.util.List;

/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTask.java,v 1.1.1.1 2007-02-01 20:41:30 kherm Exp $
 */
public interface CredentialMappingTask extends Task {

    public List getCredentialMappingTests();
}
