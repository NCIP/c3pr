/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.service.impl.StudyServiceImpl;

public class StudyServiceTestCase extends AbstractTestCase{

    private StudyService studyService;
    
    
    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
        StudyServiceImpl studyServiceImpl=new StudyServiceImpl();
        studyService=studyServiceImpl;        
    }
    
    public void testHandleMultiSiteBroadcast(){
        HealthcareSite healthcareSite=new LocalHealthcareSite();
        healthcareSite.setStudyEndPointProperty(new EndPointConnectionProperty());
        healthcareSite.setStudyEndPointProperty(new EndPointConnectionProperty());
    }
}
