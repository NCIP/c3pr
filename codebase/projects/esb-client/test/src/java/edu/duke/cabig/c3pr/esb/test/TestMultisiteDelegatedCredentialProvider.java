package edu.duke.cabig.c3pr.esb.test;

import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.esb.infrastructure.MultisiteDelegatedCredentialProvider;

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
	}
	
	public TestMultisiteDelegatedCredentialProvider(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.duke.cabig.c3pr.esb.infrastructure.MultisiteDelegatedCredentialProvider#getCredential()
	 */
	public GlobusCredential getCredential() {
        return getCredential(username,password);
    }

}
