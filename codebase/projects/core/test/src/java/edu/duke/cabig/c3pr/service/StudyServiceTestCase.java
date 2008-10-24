package edu.duke.cabig.c3pr.service;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.service.impl.StudyServiceImpl;

public class StudyServiceTestCase extends AbstractTestCase{

    private StudyService studyService;
    private MultiSiteHandlerService multiSiteHandlerService;
    
    
    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
        StudyServiceImpl studyServiceImpl=new StudyServiceImpl();
        multiSiteHandlerService=registerMockFor(MultiSiteHandlerService.class);
        studyServiceImpl.setMultiSiteHandlerService(multiSiteHandlerService);
        studyService=studyServiceImpl;        
    }
    
    public void testHandleMultiSiteBroadcast(){
        HealthcareSite healthcareSite=new HealthcareSite();
        healthcareSite.setStudyEndPointProperty(new EndPointConnectionProperty());
        healthcareSite.setStudyEndPointProperty(new EndPointConnectionProperty());
    }
}
