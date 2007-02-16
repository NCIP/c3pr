package edu.duke.cabig.c3pr.utils.web;

import gov.nih.nci.cagrid.common.Utils;

import java.io.File;

import junit.framework.TestCase;

/**
 * This unit test validates the GridProxyUtils class by pulling a user name from a grid proxy.
 * @testType unit
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
