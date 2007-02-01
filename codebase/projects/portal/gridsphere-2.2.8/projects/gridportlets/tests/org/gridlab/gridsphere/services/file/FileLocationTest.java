/*
 * Created on Jan 8, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.gridlab.gridsphere.services.file;

import java.net.MalformedURLException;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.gridlab.gridsphere.portletcontainer.GridSphereServletTest;
import org.gridlab.gridsphere.services.file.tasks.impl.BaseFileBrowserService;
import org.gridlab.gridsphere.services.file.impl.BaseFileLocation;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;

/**
 * @author Coteje
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FileLocationTest extends GridSphereServletTest {

    /**
     * Constructor for FileLocationTest.
     * @param arg0
     */
    public FileLocationTest(String arg0) {
        super(arg0);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(FileLocationTest.class);
    }

    public void testFileLocationClass() {
        FileBrowserService fileManager = new BaseFileBrowserService();
        
        try {
            BaseFileLocation testLoc = null;
    
            testLoc = (BaseFileLocation)fileManager.createFileLocation("file:///mydir");
            if( testLoc.getFilePath().compareTo("/mydir") != 0 ) {
                fail("getFilePath returned " + testLoc.getFilePath() + "expected  " + "/mydir");
            }
            testLoc.setFilePath("/mydir");
            if( testLoc.getFilePath().compareTo("/mydir") != 0 ) {
                fail("getFilePath returned " + testLoc.getFilePath() + "expected  " + "/mydir");
            }
            testLoc = (BaseFileLocation)fileManager.createFileLocation("file://host//mydir");
            if( testLoc.getFilePath().compareTo("/mydir") != 0 ) {
                fail("getFilePath returned " + testLoc.getFilePath() + "expected  " + "/mydir");
            }
            testLoc = (BaseFileLocation)fileManager.createFileLocation("file://host/mydir");
            if( testLoc.getFilePath().compareTo("mydir") != 0 ) {
                fail("getFilePath returned " + testLoc.getFilePath() + "expected  " + "mydir");
            }
            testLoc.setHost("host");
            testLoc.setFilePath("/mydir");
            if( testLoc.getFilePath().compareTo("/mydir") != 0 ) {
                fail("getFilePath returned " + testLoc.getFilePath() + "expected  " + "/mydir");
            }
            testLoc.setHost("host");
            testLoc.setFilePath("mydir");
            if( testLoc.getFilePath().compareTo("mydir") != 0 ) {
                fail("getFilePath returned " + testLoc.getFilePath() + "expected  " + "mydir");
            }
    
            testLoc.setPort(22);
            testLoc.setProtocol("http");
            testLoc.setHost("host");
            testLoc.setFilePath("mydir");
            if( testLoc.getFilePath().compareTo("mydir") != 0 ) {
                fail("getFilePath returned " + testLoc.getFilePath() + "expected  " + "mydir");
            }
    
            testLoc.setPort(22);
            testLoc.setProtocol("http");
            testLoc.setHost("host");
            testLoc.setFilePath("/mydir");
            if( testLoc.getFilePath().compareTo("/mydir") != 0 ) {
                fail("getFilePath returned " + testLoc.getFilePath() + "expected  " + "/mydir");
            }
        }
        catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            fail( "MalformedURLException: " + e1.getMessage() );
        }

    }
    

    public static Test suite() {
        return new TestSuite(FileLocationTest.class);
    }

    protected void setUp() {
       super.testInitGridSphere();
    }

    protected void tearDown() {

    }

}
