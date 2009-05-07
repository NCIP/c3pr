package edu.duke.cabig.c3pr.esb.infrastructure;

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
		this.username = "ccts@nih.gov";
		this.password = "!Ccts@nih.gov1";
		setIdpUrl("https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian");
		setIfsUrl("https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian");
	}
	
	public TestMultisiteDelegatedCredentialProvider(String username, String password) {
		this.username = username;
		this.password = password;
		setIdpUrl("https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian");
		setIfsUrl("https://cbvapp-d1017.nci.nih.gov:38443/wsrf/services/cagrid/Dorian");
	}
	
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.esb.infrastructure.MultisiteDelegatedCredentialProvider#getCredential()
	 */
	public GlobusCredential getCredential() {
        return getCredential(username,password);
    }

}
