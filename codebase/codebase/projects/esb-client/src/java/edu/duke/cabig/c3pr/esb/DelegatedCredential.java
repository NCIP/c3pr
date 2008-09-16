package edu.duke.cabig.c3pr.esb;

import org.globus.gsi.GlobusCredential;

/**
 * Bean represents a delegated credential
 * and will provide the credential retreived from
 * CDSand delegated
 * EPR
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 16, 2007
 * Time: 3:10:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DelegatedCredential {

    public GlobusCredential getCredential();

    public String getDelegatedEPR();
}
