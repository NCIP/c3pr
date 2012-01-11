package edu.duke.cabig.c3pr.esb.test;

import org.globus.gsi.GlobusCredential;


/**
 * The Class TestMultisiteDelegatedCredentialProvider.
 * This is a credential provide created for the test classes.
 * It gets credentails without using a role.
 * (Sample usage is getting creds for "ccts@nih.gov" in TestCaXchangeMessageBroadcasterImpl)
 */
public class TestMultisiteDelegatedCredentialProvider extends MultisiteDelegatedCredentialProvider{
	
	public String username = "";
	public String password = "";
	
	public TestMultisiteDelegatedCredentialProvider() {
		this.username = "cctsdev1";
		this.password = "An010101!!";
		setIdpUrl("https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian");
		setIfsUrl("https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian");
	}
	
	public TestMultisiteDelegatedCredentialProvider(String username, String password) {
		this.username = username;
		this.password = password;
		setIdpUrl("https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian");
		setIfsUrl("https://cagrid-dorian-stage.nci.nih.gov:8443/wsrf/services/cagrid/Dorian");
	}
	
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.esb.infrastructure.MultisiteDelegatedCredentialProvider#getCredential()
	 */
	public GlobusCredential getCredential() {
        return getCredential(username,password);
    }

}
