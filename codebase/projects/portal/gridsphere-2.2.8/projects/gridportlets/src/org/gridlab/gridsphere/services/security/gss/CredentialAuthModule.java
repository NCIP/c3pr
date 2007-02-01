/**
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: CredentialAuthModule.java,v 1.1.1.1 2007-02-01 20:41:28 kherm Exp $
 */
package org.gridlab.gridsphere.services.security.gss;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.User;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.service.spi.PortletServiceFactory;
import org.gridlab.gridsphere.portlet.service.spi.impl.SportletServiceFactory;
import org.gridlab.gridsphere.services.core.security.auth.AuthenticationException;
import org.gridlab.gridsphere.services.core.security.auth.modules.LoginAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.BaseAuthModule;
import org.gridlab.gridsphere.services.core.security.auth.modules.impl.descriptor.AuthModuleDefinition;
import org.gridlab.gridsphere.services.core.security.password.PasswordManagerService;
import org.gridlab.gridsphere.services.core.security.password.Password;
import org.gridlab.gridsphere.services.core.security.password.PasswordEditor;
import org.gridlab.gridsphere.services.resources.myproxy.MyProxyResource;
import org.gridlab.gridsphere.services.resources.myproxy.MyProxyResourceType;
import org.gridlab.gridsphere.services.resource.ResourceRegistryService;
import org.ietf.jgss.GSSCredential;

import java.util.List;
import java.util.Iterator;

/**
 * Implements the login authentication module interface to support user authentication
 * with the credential retrieval mechanisms provided in this package.
 * @see LoginAuthModule
 * @see CredentialManagerService
 * @see CredentialRetrievalService
 */
public class CredentialAuthModule extends BaseAuthModule implements LoginAuthModule {

    private static PortletLog log = SportletLog.getInstance(CredentialAuthModule.class);

    private String CREATE_DEF_MAPPING = "CREATE_DEFAULT_MAPPING";
    private String CREATE_GS_PASSWD = "CREATE_GS_PASSWORD";

    public CredentialAuthModule(AuthModuleDefinition authModuleDef)  {
        super(authModuleDef);
    }

    /**
     * Checks authentication by attempting to use the given password to retrieve the credentials
     * for all the single sign-on enabled credential retrieval contexts that the given user owns.
     * @param user
     * @param password
     * @throws AuthenticationException
     */
    public void checkAuthentication(User user, String password)
            throws AuthenticationException {
        PortletServiceFactory factory = SportletServiceFactory.getInstance();
        ResourceRegistryService resourceRegistryService;
        CredentialManagerService credentialManagerService;
        CredentialRetrievalService credentialRetrievalService;
        try {
            resourceRegistryService =
                    (ResourceRegistryService)
                    factory.createPortletService(ResourceRegistryService.class, null, true);
            credentialManagerService =
                    (CredentialManagerService)
                    factory.createPortletService(CredentialManagerService.class,  null, true);
            credentialRetrievalService =
                    (CredentialRetrievalService)
                    factory.createPortletService(CredentialRetrievalService.class,  null, true);
        } catch (Exception e) {
            log.error("Unable to get instances of credential services!", e);
            throw new AuthenticationException("Unable to get instances of credential services!");
        }
        // Check that password is not null
        StringBuffer errors = new StringBuffer();

        String defMapping = getAttribute(CREATE_DEF_MAPPING);

        List retrievalContexts = credentialRetrievalService.getCredentialRetrievalContexts(user);


        if (retrievalContexts.size() == 0) {
            String message = "No credential retrieval contexts exist for " +  user.getUserID();
            if ((Boolean.valueOf(defMapping)).booleanValue()) {
                    List myproxyResourceList = resourceRegistryService.getResources(MyProxyResourceType.INSTANCE);
                    if (myproxyResourceList.size() == 0) {
                        log.error("No myproxy resource exists in resource registry");
                        throw new AuthenticationException("key1");
                    } else {
                        MyProxyResource myProxyResource = (MyProxyResource)myproxyResourceList.get(0);
                        try {
                            CredentialRetrievalContext retrievalContext = credentialRetrievalService.createCredentialRetrievalContext(user);
                            retrievalContext.setUserName(user.getUserName());

                            GSSCredential credential =
                                    credentialRetrievalService.saveCredentialRetrievalContext(retrievalContext, password);
                            // Get credentialContext dn
                            String dn = retrievalContext.getDn();

                            // Create credentialContext context
                            CredentialContext credentialContext
                                    = credentialManagerService.createCredentialContext(user, dn);
                            credentialContext.setLabel("My Grid Credential");
                            credentialManagerService.saveCredentialContext(credentialContext);

                            // Activate the context
                            credentialManagerService.activateCredentialContext(credential);
                        } catch (CredentialException e) {
                            log.error("Unable to create a new credential context");
                            throw new AuthenticationException("Unable to create a new credential context");
                        }
                    }
            } else {
                log.debug(message);
                throw new AuthenticationException(message);
            }
        }

        // If no (more) retrieval contexts enabled for single sign-on, then
        // default to password auth module for now...
        //PasswordAuthModule passAuthModule = new PasswordAuthModule("defaultAuthorization");
        //passAuthModule.checkAuthentication(user, password);
        retrievalContexts = credentialRetrievalService.getCredentialRetrievalContexts(user);

        log.info("Attempting to authenticate with retrieval contexts");
        boolean isAuthorized = false;
        for (Iterator contexts = retrievalContexts.iterator(); contexts.hasNext();) {
            CredentialRetrievalContext retrievalContext = (CredentialRetrievalContext) contexts.next();
            try {
                GSSCredential credential = credentialRetrievalService.retrieveCredential(retrievalContext, password);
                credentialManagerService.activateCredentialContext(credential);
                isAuthorized = true;
            } catch (CredentialException e) {
                errors.append(e.getMessage());
            }
        }
        if (!isAuthorized) {
            log.debug(errors.toString());
            throw new AuthenticationException(errors.toString());
        }

        // if CREATE_GS_PASSWD enabled then create a password in GS password manager as well using same password
        String createGSPasswd = getAttribute(CREATE_GS_PASSWD);
        if ((Boolean.valueOf(createGSPasswd)).booleanValue()) {
            try {
                PasswordManagerService passwordService =
                        (PasswordManagerService)factory.createPortletService(PasswordManagerService.class, null, true);
                Password pass = passwordService.getPassword(user);
                if (pass == null) {
                    PasswordEditor passEditor = passwordService.editPassword(user);
                    passEditor.setValue(password);
                    passwordService.savePassword(passEditor);
                    log.debug("Saving created password for user: " + user.getUserName());
                }
            } catch (Exception e) {
                log.error("Unable to get instance of password services!", e);
            }
        }

    }
}
