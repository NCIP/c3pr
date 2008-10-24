package edu.duke.cabig.c3pr.infrastructure;

import junit.framework.TestCase;

public class SmokeTestTestCase extends TestCase{

    public void testPing(){
        String serviceUrl="";
        String idpUrl="";
        String ifsUrl="";
        MultisiteDelegatedCredentialProvider multisiteDelegatedCredentialProvider=new MultisiteDelegatedCredentialProvider();
        multisiteDelegatedCredentialProvider.setIdpUrl(idpUrl);
        multisiteDelegatedCredentialProvider.setIfsUrl(ifsUrl);
        
    }
}
