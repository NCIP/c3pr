package edu.duke.cabig.c3pr.service;

import java.util.ArrayList;
import java.util.List;

import org.easymock.classextension.EasyMock;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.service.impl.StudyServiceImpl;

public class StudyServiceUnitTest extends AbstractTestCase{

    private StudyDao studyDao;
    private StudySiteDao studySiteDao;
    private StudyService studyService;
    private Study study;
    StudySite studySite;
    List<Study> list;
    List<Identifier> ids;
    
    public StudyServiceUnitTest() {
        studyDao=registerMockFor(StudyDao.class);
        studySiteDao=registerMockFor(StudySiteDao.class);
        studyService=new StudyServiceImpl();
        ((StudyServiceImpl)studyService).setStudyDao(studyDao);
        ((StudyServiceImpl)studyService).setStudySiteDao(studySiteDao);
        study=new Study();
        studySite=new StudySite();
        study.addStudySite(studySite);
        study.getStudySites().get(0).setHealthcareSite(new HealthcareSite());
        study.getStudySites().get(0).getHealthcareSite().setNciInstituteCode("Test");
        list= new ArrayList<Study>();
        list.add(study);
        ids=new ArrayList<Identifier>();
    }
    
    public void testCreateStudyCompleteDataEntry(){
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        studyDao.save(study);
        replayMocks();
        studyService.createStudy(study);
        verifyMocks();
    }
    
    public void testCreateStudyIncompleteDataEntry(){
        replayMocks();
        try{
            studyService.createStudy(study);
        }catch(RuntimeException e){
            assertEquals("Wrong exception message", e.getMessage(),"cannot create incomplete study");
        }catch(Exception e){
            fail("Wrong Exception thrown");
        }
        verifyMocks();
    }
    
    public void testOpenStudyNoStudyFound(){
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(new ArrayList<Study>());
        replayMocks();
        try{
            studyService.openStudy(ids);
        }catch(C3PRCodedException e){
            e.printStackTrace();
            fail("Wrong Exception thrown");
        }catch(RuntimeException e){
            e.printStackTrace();
            assertEquals("Wrong exception message", e.getMessage(),"study not found");
        }catch(Exception e){
            e.printStackTrace();
            fail("Wrong Exception thrown");
        }
        verifyMocks();
    }
    
    public void testOpenStudyMultipleStudiesFound(){
        list.add(new Study());
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        replayMocks();
        try{
            studyService.openStudy(ids);
        }catch(C3PRCodedException e){
            fail("Wrong Exception thrown");
        }catch(RuntimeException e){
            assertEquals("Wrong exception message", e.getMessage(),"more than one study with the same identifier found");
        }catch(Exception e){
            fail("Wrong Exception thrown");
        }
        verifyMocks();
    }
    
    public void testOpenStudy() throws C3PRCodedException{
        study=registerMockFor(Study.class);
        list.clear();
        list.add(0, study);
        EasyMock.expect(study.setStatuses(CoordinatingCenterStudyStatus.OPEN)).andReturn(study);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        studyDao.save(study);
        replayMocks();
        studyService.openStudy(ids);
        verifyMocks();
    }
    
    public void testApproveStudySiteForActivationInvaidNCICode() {
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        replayMocks();
        try {
            studyService.approveStudySiteForActivation(ids, "wrong");
        }catch(C3PRCodedException e){
            fail("Wrong Exception thrown");
        }catch(RuntimeException e){
            assertEquals("Wrong exception message", e.getMessage(),"cannot find a study site with the given nci institute code");
        }catch(Exception e){
            fail("Wrong Exception thrown");
        }
        verifyMocks();
    }
    
    public void testApproveStudySiteForActivation() throws C3PRCodedException{
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        studySiteDao.save(studySite);
        replayMocks();
        studyService.approveStudySiteForActivation(ids, "Test");
        verifyMocks();
    }
    
    public void testActivateStudySite() throws C3PRCodedException{
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        studySiteDao.save(studySite);
        replayMocks();
        studyService.approveStudySiteForActivation(ids, "Test");
        verifyMocks();
    }
    
    public void testCloseStudy() throws C3PRCodedException{
        study=registerMockFor(Study.class);
        list.clear();
        list.add(0, study);
        EasyMock.expect(study.setStatuses(CoordinatingCenterStudyStatus.CLOSED_TO_ACCRUAL)).andReturn(study);
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        studyDao.save(study);
        replayMocks();
        studyService.closeStudy(ids);
        verifyMocks();
    }
    
    public void testCloseStudySite() throws C3PRCodedException{
        EasyMock.expect(studyDao.getByIdentifiers(ids)).andReturn(list);
        studySiteDao.save(studySite);
        replayMocks();
        studyService.closeStudySite(ids, "Test");
        verifyMocks();
    }
}
