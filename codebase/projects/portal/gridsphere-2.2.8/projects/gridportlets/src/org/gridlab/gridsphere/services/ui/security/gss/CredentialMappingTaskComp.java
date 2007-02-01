/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: CredentialMappingTaskComp.java,v 1.1.1.1 2007-02-01 20:42:18 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.security.gss;

import org.gridlab.gridsphere.services.ui.ActionComponent;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.ui.ActionComponentFrame;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingService;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingTask;
import org.gridlab.gridsphere.services.security.gss.CredentialMappingSpec;
import org.gridlab.gridsphere.services.security.gss.CredentialManagerService;
import javax.portlet.PortletException;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.provider.portletui.beans.CheckBoxBean;

import java.util.Map;

public class CredentialMappingTaskComp extends ActionComponent {

    private transient static PortletLog log = SportletLog.getInstance(CredentialMappingTaskComp.class);

    public final static String CMT_PARAM = "cmtParam";
    public final static String CMT_OID_PARAM = "cmtOidParam";
    public final static String CMT_TYPE_PARAM = "cmtTypeParam";
    public final static String CMT_COMP_PARAM = "cmtCompParam";
    public final static String CMT_SPEC_PARAM = "cmtSpecParam";
    public final static String CMT_SPEC_OID_PARAM = "cmtSpecOidParam";
    public final static String CMT_OID_LIST_PARAM = "cmtOidListParam";

    protected CredentialManagerService credentialManagerService = null;
    protected CredentialMappingService credentialMappingService = null;
    protected CheckBoxBean cmtOidCheckBox = null;
    protected String cmtOid = null;

    public CredentialMappingTaskComp(ActionComponentFrame container, String compName) {
        super(container, compName);
    }

    public void onInit()
            throws PortletException {
        super.onInit();
        credentialManagerService = (CredentialManagerService)
                getPortletService(CredentialManagerService.class);
        credentialMappingService = (CredentialMappingService)
                getPortletService(CredentialMappingService.class);
    }

    public void onStore() throws PortletException {
        setPageAttribute("cmtOid", cmtOid);
    }

    public CredentialMappingTask getCredentialMappingTask(Map parameters) {
        CredentialMappingTask cmt = getCredentialMappingTask(credentialMappingService, parameters);
        if (cmt != null) {
            // Save job oid and job type for use in jsps
            cmtOid = cmt.getOid();
        }
        return cmt;
    }

    public static CredentialMappingTask getCredentialMappingTask(CredentialMappingService credentialMappingService, Map parameters) {
        // Get cmt parameter
        CredentialMappingTask cmt = (CredentialMappingTask)parameters.get(CMT_PARAM);
        if (cmt == null) {
            // Get cmt oid parameter...
            String cmtOid = (String)parameters.get(CMT_OID_PARAM);
            if (cmtOid != null) {
                cmt = (CredentialMappingTask)
                        credentialMappingService.getTask(cmtOid);
                parameters.put(CMT_PARAM, cmt);
            }
        }
        return cmt;
    }

    public CredentialMappingSpec getCredentialMappingTaskSpec(Map parameters) {
        return getCredentialMappingTaskSpec(credentialMappingService, parameters);
    }

    public static CredentialMappingSpec getCredentialMappingTaskSpec(CredentialMappingService credentialMappingService, Map parameters) {
        // Get cmt spec parameter
        CredentialMappingSpec cmtSpec = (CredentialMappingSpec)parameters.get(CMT_SPEC_PARAM);
        // If cmt spec is null...
        if (cmtSpec == null) {
            // Get cmt spec parameter
            String cmtSpecOid = (String)parameters.get(CMT_SPEC_OID_PARAM);
            // If cmt spec oid is null...
            if (cmtSpecOid == null) {
                // Get cmt parameter
                CredentialMappingTask cmt = (CredentialMappingTask)parameters.get(CMT_PARAM);
                if (cmt == null) {
                    // Get cmt oid parameter...
                    String cmtOid = (String)parameters.get(CMT_OID_PARAM);
                    if (cmtOid != null) {
                        cmt = (CredentialMappingTask)credentialMappingService.getTask(cmtOid);
                        cmtSpec = (CredentialMappingSpec)cmt.getTaskSpec();
                    }
                } else {
                    cmtSpec = (CredentialMappingSpec)cmt.getTaskSpec();
                    parameters.put(CMT_SPEC_PARAM, cmtSpec);
                }
            }
        }
        return cmtSpec;
    }
}
