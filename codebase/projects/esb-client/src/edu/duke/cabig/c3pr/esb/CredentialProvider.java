package edu.duke.cabig.c3pr.esb;

import org.globus.gsi.GlobusCredential;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 14, 2007
 * Time: 5:14:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CredentialProvider {

    public GlobusCredential provideCredentials();
}
