package edu.duke.cabig.c3pr.utils.web;

import java.io.ByteArrayInputStream;

import org.globus.gsi.GlobusCredential;

/**
 * Utility class for a Grid Proxy
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jan 31, 2007
 * Time: 11:37:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class GridProxyUtils {

    /**
     * Will return the username from a grid proxy
     * @param proxyString
     * @return
     */
    public static String getUsernameFromProxy(String proxyString) throws Exception{

        try {
            GlobusCredential cred = new GlobusCredential(new ByteArrayInputStream(proxyString.getBytes()));
            String identity = cred.getIdentity();
            return identity.substring(identity.indexOf("CN=")+3);

        } catch (Exception ex) {
            throw new Exception(ex);

        }
    }


}
