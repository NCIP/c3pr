package edu.duke.cabig.c3pr.esb.impl;

import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.esb.DelegatedCredential;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 16, 2007
 * Time: 3:12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class DelegatedCredentialImpl implements DelegatedCredential {

    private GlobusCredential credential;
    private String delegatedEPR;


    public DelegatedCredentialImpl(GlobusCredential credential, String delegatedEPR) {
        this.credential = credential;
        this.delegatedEPR = delegatedEPR;
    }

    public GlobusCredential getCredential() {
        return credential;
    }

    public void setCredential(GlobusCredential credential) {
        this.credential = credential;
    }

    public String getDelegatedEPR() {
        return delegatedEPR;
    }

    public void setDelegatedEPR(String delegatedEPR) {
        this.delegatedEPR = delegatedEPR;
    }
}
