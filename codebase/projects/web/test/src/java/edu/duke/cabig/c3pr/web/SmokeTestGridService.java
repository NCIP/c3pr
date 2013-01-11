/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web;

import java.io.FileInputStream;

import org.globus.gsi.GlobusCredential;

import gov.nih.nci.ccts.grid.smoketest.client.SmokeTestServiceClient;
import junit.framework.TestCase;

public class SmokeTestGridService extends TestCase {

    //private String proxyFileName="/Users/kruttikagarwal/KrLocalProxy";
    //private String serviceURL="https://localhost:28443/wsrf/services/cagrid/SmokeTestService";
    
    private String proxyFileName="/Users/kruttikagarwal/LocalProxy";
    //private String proxyFileName="/Users/kruttikagarwal/localproxy2";
    private String serviceURL="https://cbvapp-d1017.nci.nih.gov:28445/wsrf-smoketestservice/services/cagrid/SmokeTestService";
    
    public void testSmokeTest() throws Exception{
        SmokeTestServiceClient smokeTestServiceClient=new SmokeTestServiceClient(serviceURL,new GlobusCredential(new FileInputStream(proxyFileName)));
        smokeTestServiceClient.ping();
    }
    
}
