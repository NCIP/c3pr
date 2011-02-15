package edu.duke.cabig.c3pr.accesscontrol;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.acegisecurity.AuthenticationException;
import org.acegisecurity.providers.cas.CasAuthoritiesPopulator;
import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UserDetailsService;
import org.apache.log4j.Logger;
import org.cagrid.gaards.cds.client.CredentialDelegationServiceClient;
import org.cagrid.gaards.cds.client.DelegatedCredentialUserClient;
import org.cagrid.gaards.cds.delegated.stubs.types.DelegatedCredentialReference;
import org.globus.gsi.GlobusCredential;
import org.springframework.beans.factory.annotation.Required;

import edu.duke.cabig.c3pr.dao.RolePrivilegeDao;

import gov.nih.nci.cagrid.common.Utils;

/**
 * Will create a WebSSOUser object from CAS assertion <p/> Created by IntelliJ IDEA. User: kherm
 * Date: Dec 11, 2007 Time: 6:59:57 PM To change this template use File | Settings | File Templates.
 */
public class WebSSOAuthoritiesPopulator implements CasAuthoritiesPopulator {

    private UserDetailsService userDetailsService;
    
    private RolePrivilegeDao rolePrivilegeDao;

    public static final String ATTRIBUTE_DELIMITER = "$";

    public static final String KEY_VALUE_PAIR_DELIMITER = "^";

    public static final String CCTS_USER_ID_KEY = "CAGRID_SSO_EMAIL_ID";

    public static final String CAGRID_SSO_FIRST_NAME = "CAGRID_SSO_FIRST_NAME";

    public static final String CAGRID_SSO_LAST_NAME = "CAGRID_SSO_LAST_NAME";

    public static final String CAGRID_SSO_GRID_IDENTITY = "CAGRID_SSO_GRID_IDENTITY";

    public static final String CAGRID_SSO_DELEGATION_SERVICE_EPR = "CAGRID_SSO_DELEGATION_SERVICE_EPR";

    Logger log = Logger.getLogger(WebSSOAuthoritiesPopulator.class);

    private String hostCertificate;

    private String hostKey;

    /**
     * Obtains the granted authorities for the specified user.
     * <P>
     * May throw any <code>AuthenticationException</code> or return <code>null</code> if the
     * authorities are unavailable.
     * </p>
     * 
     * @param casUserId
     *                as obtained from the webSSO CAS validation service
     * @return the details of the indicated user (at minimum the granted authorities and the
     *         username)
     * @throws org.acegisecurity.AuthenticationException
     *                 DOCUMENT ME!
     */
    public UserDetails getUserDetails(String casUserId) throws AuthenticationException {

        Map<String, String> attrMap = new HashMap<String, String>();
        StringTokenizer stringTokenizer = new StringTokenizer(casUserId, ATTRIBUTE_DELIMITER);
        while (stringTokenizer.hasMoreTokens()) {
            String attributeKeyValuePair = stringTokenizer.nextToken();
            attrMap.put(attributeKeyValuePair.substring(0, attributeKeyValuePair
                            .indexOf(KEY_VALUE_PAIR_DELIMITER)), attributeKeyValuePair.substring(
                            attributeKeyValuePair.indexOf(KEY_VALUE_PAIR_DELIMITER) + 1,
                            attributeKeyValuePair.length()));
        }

        WebSSOUser user = new WebSSOUser((AuthorizedUser)userDetailsService.loadUserByUsername(getUserIdFromGridIdentity(attrMap
                        .get(CAGRID_SSO_GRID_IDENTITY))));
        user.setGridId(getUserIdFromGridIdentity(attrMap.get(CAGRID_SSO_GRID_IDENTITY)));
        user.setDelegatedEPR(attrMap.get(CAGRID_SSO_DELEGATION_SERVICE_EPR));
        user.setFirstName(attrMap.get(CAGRID_SSO_FIRST_NAME));
        user.setLastName(attrMap.get(CAGRID_SSO_LAST_NAME));

        // Get the delegated credential and store it in the UserDetails object
        // This will be available later in the Authenticaiton object
        try {
            GlobusCredential hostCredential = new GlobusCredential(hostCertificate, hostKey);
            DelegatedCredentialReference delegatedCredentialReference = (DelegatedCredentialReference) Utils
                            .deserializeObject(
                                            new StringReader(attrMap
                                                            .get(CAGRID_SSO_DELEGATION_SERVICE_EPR)),
                                            DelegatedCredentialReference.class,
                                            CredentialDelegationServiceClient.class
                                                            .getResourceAsStream("client-config.wsdd"));
            log.debug("hostCertificate: "+hostCertificate);
            log.debug("hostKey: "+hostKey);
            log.debug("delegatedCredentialReference.toString():"+ delegatedCredentialReference.toString());
            log.debug("delegatedCredentialReference.getEndpointReference().toString():" +delegatedCredentialReference.getEndpointReference().toString());
            log.debug("delegatedCredentialReference.getEndpointReference().getAddress().toString(): "+delegatedCredentialReference.getEndpointReference().getAddress().toString());
            log.debug("delegatedCredentialReference.getEndpointReference().getAddress().getHost(): "+delegatedCredentialReference.getEndpointReference().getAddress().getHost());
            log.debug("delegatedCredentialReference.getEndpointReference().getAddress().getPath(): "+delegatedCredentialReference.getEndpointReference().getAddress().getPath());
            DelegatedCredentialUserClient delegatedCredentialUserClient = new DelegatedCredentialUserClient(
                            delegatedCredentialReference, hostCredential);

            GlobusCredential userCredential = delegatedCredentialUserClient
                            .getDelegatedCredential();
            log.debug("Identitiy: "+userCredential.getIdentity());
            log.debug("Issuer: "+userCredential.getIssuer());
            log.debug("Subject: "+userCredential.getSubject());
            log.debug(userCredential);
            System.out.println("Identitiy: "+userCredential.getIdentity());
            System.out.println("Issuer: "+userCredential.getIssuer());
            System.out.println("Subject: "+userCredential.getSubject());
            System.out.println(userCredential);
            user.setGridCredential(userCredential);

        }
        catch (Exception e) {
            // just log it and move on for now
            log.error("Could not retreive user credential from CDS service", e);
        }
        return user;
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    @Required
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String getHostCertificate() {
        return hostCertificate;
    }

    public void setHostCertificate(String hostCertificate) {
        this.hostCertificate = hostCertificate;
    }

    public String getHostKey() {
        return hostKey;
    }

    public void setHostKey(String hostKey) {
        this.hostKey = hostKey;
    }
    
    public String getUserIdFromGridIdentity(String gridIdentity){
        String[] sections=gridIdentity.split("=");
        return sections[sections.length-1].toLowerCase();
    }

	public RolePrivilegeDao getRolePrivilegeDao() {
		return rolePrivilegeDao;
	}

	public void setRolePrivilegeDao(RolePrivilegeDao rolePrivilegeDao) {
		this.rolePrivilegeDao = rolePrivilegeDao;
	}
}
