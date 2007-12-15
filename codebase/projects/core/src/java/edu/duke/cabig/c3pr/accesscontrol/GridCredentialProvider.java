package edu.duke.cabig.c3pr.accesscontrol;

import edu.duke.cabig.c3pr.esb.CredentialProvider;
import org.globus.gsi.GlobusCredential;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.Authentication;
import org.acegisecurity.providers.cas.CasAuthenticationToken;
import org.apache.log4j.Logger;

/**
 * Acts a credential provider and
 * will provide the grid credential
 * saved in the session.
 *
 * The credential is only available in
 * webSSO mode
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 14, 2007
 * Time: 5:31:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class GridCredentialProvider implements CredentialProvider {
      Logger log = Logger.getLogger(getClass());


    /**
     * @return Will retreive the credential retreived from cds
     * when running in webSSO mode or null if running in local mode
     *
     */
    public GlobusCredential provideCredentials() {
        GlobusCredential cred = null;
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();

        if(auth instanceof CasAuthenticationToken){
            CasAuthenticationToken token = (CasAuthenticationToken)auth;
            WebSSOUser user =  (WebSSOUser)token.getUserDetails();
            cred = user.getGridCredential();
            log.info("Providing valid credential for user");
        }
        else
            log.warn("Cannot provide valid credential when running in local mode.");

        return cred;
    }
}
