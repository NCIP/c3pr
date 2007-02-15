package edu.duke.cabig.c3pr.utils.web;

import gov.nih.nci.cagrid.common.Utils;

import java.io.File;

import junit.framework.TestCase;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jan 31, 2007
 * Time: 12:13:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class GridProxyUtilsTest extends TestCase {

    public void testGetUsername(){
        try {
            StringBuffer proxyStr = Utils.fileToStringBuffer(new File("test/resources/GridProxy_sample.txt"));
            String username = GridProxyUtils.getUsernameFromProxy(proxyStr.toString());
            assertNotNull(username);
            assertFalse(username.contains(new String("=")));
            System.out.println(username);

        } catch (Exception e) {
            fail(e.getMessage());
        }


    }


    
}
