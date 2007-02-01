package com.semanticbits.ctms.portal;

import org.gridlab.gridsphere.services.core.security.auth.modules.impl.BaseAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.apache.axis.types.URI;
import gov.nih.nci.cagrid.authentication.client.AuthenticationServiceClient;
import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;

import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jan 31, 2007
 * Time: 8:02:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class GridAuthModule extends BaseAuthModule implements LoginAuthModule {

    private PortletLog log = SportletLog.getInstance(GridAuthModule.class);


    public GridAuthModule(AuthModuleDefinition moduleDef) {
        super(moduleDef);
    }

    public void checkAuthentication(User user, String password) throws AuthenticationException {
          String idpUrl = getAttribute("idpURL");

        AuthenticationServiceClient authClient = null;
        try {
            authClient = new AuthenticationServiceClient(idpUrl);
        } catch (Exception e) {
            String errMsg = "Could not create authentication client for Grid IDP with url " + idpUrl;
            log.error(errMsg + e.getMessage());
            throw new AuthenticationException(errMsg,e);
        }

        BasicAuthenticationCredential bac = new BasicAuthenticationCredential();
        bac.setUserId(user.getUserName());
        bac.setPassword(password);

        Credential cred = new Credential();
           cred.setBasicAuthenticationCredential(bac);

        try {
            authClient.authenticate(cred);
        } catch (RemoteException e) {
            throw new AuthenticationException(e.getMessage());
        }


    }
}
