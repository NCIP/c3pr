package edu.duke.cabig.c3pr.esb.test;

import java.io.IOException;
import java.util.Properties;

import org.acegisecurity.Authentication;
import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.esb.DelegatedCredential;
import edu.duke.cabig.c3pr.esb.DelegatedCredentialProvider;
import gov.nih.nci.cagrid.authentication.bean.BasicAuthenticationCredential;
import gov.nih.nci.cagrid.authentication.bean.Credential;
import gov.nih.nci.cagrid.authentication.client.AuthenticationClient;
import gov.nih.nci.cagrid.dorian.client.IFSUserClient;
import gov.nih.nci.cagrid.dorian.ifs.bean.ProxyLifetime;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;

public class MultisiteDelegatedCredentialProvider implements DelegatedCredentialProvider,
                DelegatedCredential {

    //private String proxyFilePath;

//    public void setProxyFilePath(String proxyFilePath) {
//        this.proxyFilePath = proxyFilePath;
//    }
    
    private String idpUrl;

    private String ifsUrl;
    
    private int proxyLifetimeHours = 12;

    public GlobusCredential getCredential(String username, String password) {
        try {
            // Create credential

            Credential cred = new Credential();
            BasicAuthenticationCredential bac = new BasicAuthenticationCredential();
            bac.setUserId(username);
            bac.setPassword(password);
            cred.setBasicAuthenticationCredential(bac);

            // Authenticate to the IdP (DorianIdP) using credential

            AuthenticationClient authClient = new AuthenticationClient(idpUrl, cred);
            SAMLAssertion saml = authClient.authenticate();

            // Requested Grid Credential lifetime (12 hours)

            ProxyLifetime lifetime = new ProxyLifetime();
            lifetime.setHours(proxyLifetimeHours);

            // Delegation Path Length

            int delegationLifetime = 0;

            // Request Grid Credential

            IFSUserClient dorian = new IFSUserClient(ifsUrl);
            GlobusCredential proxy = dorian.createProxy(saml, lifetime, delegationLifetime);

            return proxy;
            //return new GlobusCredential(new FileInputStream(new File(proxyFilePath)));
        }
        catch (Exception e) {
            throw new RuntimeException("username: "+ username + " password: " + password + " " +e);
        }
    }
    
    public static GlobusCredential getGlobusCredential(String username, String password, String idpUrl, String ifsUrl, int proxyLifetimeHours) {
        try {
            // Create credential

            Credential cred = new Credential();
            BasicAuthenticationCredential bac = new BasicAuthenticationCredential();
            bac.setUserId(username);
            bac.setPassword(password);
            cred.setBasicAuthenticationCredential(bac);

            // Authenticate to the IdP (DorianIdP) using credential

            AuthenticationClient authClient = new AuthenticationClient(idpUrl, cred);
            SAMLAssertion saml = authClient.authenticate();

            // Requested Grid Credential lifetime (12 hours)

            ProxyLifetime lifetime = new ProxyLifetime();
            lifetime.setHours(proxyLifetimeHours);

            // Delegation Path Length

            int delegationLifetime = 0;

            // Request Grid Credential

            IFSUserClient dorian = new IFSUserClient(ifsUrl);
            GlobusCredential proxy = dorian.createProxy(saml, lifetime, delegationLifetime);

            return proxy;
            //return new GlobusCredential(new FileInputStream(new File(proxyFilePath)));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    

    public String getDelegatedEPR() {
        // TODO Auto-generated method stub
        return null;
    }

    public DelegatedCredential provideDelegatedCredentials() {
        return this;
    }

    public GlobusCredential getCredential() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        GrantedAuthority[] groups = auth.getAuthorities();
        String role=groups[0].getAuthority();
        //String role="ROLE_c3pr_admin";
        String username;
        String password;
        String filename="default.grid.login.properties";
        Properties p = new Properties();
        try {
            p.load(this.getClass().getClassLoader().getResourceAsStream(filename));
            System.out.println("The links file has " + p.keySet().size() + " elements.");
            username=p.getProperty(role+".username");
            password=p.getProperty(role+".password");
        }
        catch (IOException e) {
            System.out.println("Error while trying to read the property file: [" + filename + "]");
            throw new RuntimeException(e);
        }
        return getCredential(username,password);
        //return getCredential("SmokeTest", "P@ssw0rd123");
    }

    public void setIdpUrl(String idpUrl) {
        this.idpUrl = idpUrl;
    }

    public void setIfsUrl(String ifsUrl) {
        this.ifsUrl = ifsUrl;
    }
}
