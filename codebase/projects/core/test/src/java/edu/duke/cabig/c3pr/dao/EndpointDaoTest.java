package edu.duke.cabig.c3pr.dao;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Error;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for StudySiteDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class EndpointDaoTest extends DaoTestCase {
    private EndpointDao dao;
    private StudySiteDao studySiteDao;

    /**
     * Test for loading a Study by Id
     * 
     * @throws Exception
     */
    
    protected void setUp() throws Exception {
        super.setUp();
        dao = (EndpointDao) getApplicationContext().getBean("endpointDao");
        studySiteDao = (StudySiteDao) getApplicationContext().getBean("studySiteDao");
    }
    
    public void testSaveStudySiteWithEndPoint1() throws Exception {
        List<StudySite> sites = studySiteDao.getByNciInstituteCode("code");
        assertTrue(sites.size() == 2);
        for (StudySite site : sites) {
            assertEquals(site.getHealthcareSite().getNciInstituteCode(), "code");
        }
        StudyOrganization studySite=sites.get(0);
        Date date1=new Date();
        Thread.sleep(3000);
        Date date2=new Date();
        EndPoint endPoint=new GridEndPoint();
        endPoint.setEndPointProperty(studySite.getHealthcareSite().getStudyEndPointProperty());
        endPoint.setApiName(APIName.OPEN_STUDY);
        endPoint.setServiceName(ServiceName.STUDY);
        endPoint.setAttemptDate(date1);
        studySite.getEndpoints().add(endPoint);
        endPoint.setStudyOrganization(studySite);
        dao.save(endPoint);
        interruptSession();
        studySite=studySiteDao.getById(studySite.getId());
        
        endPoint=new GridEndPoint();
        endPoint.setEndPointProperty(studySite.getHealthcareSite().getStudyEndPointProperty());
        endPoint.setApiName(APIName.OPEN_STUDY);
        endPoint.setServiceName(ServiceName.STUDY);
        endPoint.setAttemptDate(date2);
        studySite.getEndpoints().add(endPoint);
        endPoint.setStudyOrganization(studySite);
        dao.save(endPoint);
        interruptSession();
        studySite=studySiteDao.getById(studySite.getId());
        
        assertNotNull("Empty Endpoint", studySite.getEndpoints().get(0));
        assertEquals("Wrong Service", ServiceName.STUDY, studySite.getEndpoints().get(0).getServiceName());
        assertEquals("Wrong Service", APIName.OPEN_STUDY, studySite.getEndpoints().get(0).getApiName());
        assertEquals("Wrong Date", date2, studySite.getLastAttemptedEndpoint().getAttemptDate());
    }
    
    public void testSaveStudySiteWithEndPoint2() throws Exception {
        List<StudySite> sites = studySiteDao.getByNciInstituteCode("code");
        assertTrue(sites.size() == 2);
        for (StudySite site : sites) {
            assertEquals(site.getHealthcareSite().getNciInstituteCode(), "code");
        }
        StudyOrganization studySite=sites.get(0);
        Date date1=new Date();
        Thread.sleep(3000);
        Date date2=new Date();
        EndPoint endPoint=new GridEndPoint();
        endPoint.setEndPointProperty(studySite.getHealthcareSite().getStudyEndPointProperty());
        endPoint.setApiName(APIName.OPEN_STUDY);
        endPoint.setServiceName(ServiceName.STUDY);
        endPoint.setAttemptDate(date2);
        studySite.getEndpoints().add(endPoint);
        endPoint.setStudyOrganization(studySite);
        dao.save(endPoint);
        interruptSession();
        studySite=studySiteDao.getById(studySite.getId());
        
        endPoint=new GridEndPoint();
        endPoint.setEndPointProperty(studySite.getHealthcareSite().getStudyEndPointProperty());
        endPoint.setApiName(APIName.OPEN_STUDY);
        endPoint.setServiceName(ServiceName.STUDY);
        
        endPoint.setAttemptDate(date1);
        studySite.getEndpoints().add(endPoint);
        endPoint.setStudyOrganization(studySite);
        dao.save(endPoint);
        interruptSession();
        studySite=studySiteDao.getById(studySite.getId());
        
        assertNotNull("Empty Endpoint", studySite.getEndpoints().get(0));
        assertEquals("Wrong Service", ServiceName.STUDY, studySite.getEndpoints().get(0).getServiceName());
        assertEquals("Wrong Service", APIName.OPEN_STUDY, studySite.getEndpoints().get(0).getApiName());
        assertEquals("Wrong Date", date2, studySite.getLastAttemptedEndpoint().getAttemptDate());
    }
    
    public void testMergeStudySiteWithEndPoint() throws Exception {
        List<StudySite> sites = studySiteDao.getByNciInstituteCode("code");
        assertTrue(sites.size() == 2);
        for (StudySite site : sites) {
            assertEquals(site.getHealthcareSite().getNciInstituteCode(), "code");
        }
        StudyOrganization studySite=sites.get(0);
        EndPoint endPoint=new GridEndPoint();
        endPoint.setEndPointProperty(studySite.getHealthcareSite().getStudyEndPointProperty());
        endPoint.setApiName(APIName.OPEN_STUDY);
        endPoint.setServiceName(ServiceName.STUDY);
        //studySite.getEndpoints().add(endPoint);
        endPoint.setStudyOrganization(studySite);
        dao.merge(endPoint);
        interruptSession();
        studySite=studySiteDao.getById(studySite.getId());
        assertEquals("Wrong Size", 1, studySite.getEndpoints().size());
        assertNotNull("Empty Endpoint", studySite.getEndpoints().get(0));
        assertEquals("Wrong Service", ServiceName.STUDY, studySite.getEndpoints().get(0).getServiceName());
        assertEquals("Wrong Service", APIName.OPEN_STUDY, studySite.getEndpoints().get(0).getApiName());
    }
    
    public void testMergeStudySiteWithEndPointAndErrors() throws Exception {
        List<StudySite> sites = studySiteDao.getByNciInstituteCode("code");
        assertTrue(sites.size() == 2);
        for (StudySite site : sites) {
            assertEquals(site.getHealthcareSite().getNciInstituteCode(), "code");
        }
        StudyOrganization studySite=sites.get(0);
        EndPoint endPoint=new GridEndPoint();
        endPoint.setEndPointProperty(studySite.getHealthcareSite().getStudyEndPointProperty());
        endPoint.setApiName(APIName.OPEN_STUDY);
        endPoint.setServiceName(ServiceName.STUDY);
        studySite.getEndpoints().add(endPoint);
        endPoint.setStudyOrganization(studySite);
        dao.save(endPoint);
        interruptSession();
        studySite=studySiteDao.getById(studySite.getId());
        assertEquals("Wrong Size", 1, studySite.getEndpoints().size());
        assertNotNull("Empty Endpoint", studySite.getEndpoints().get(0));
        assertEquals("Wrong Service", ServiceName.STUDY, studySite.getEndpoints().get(0).getServiceName());
        assertEquals("Wrong Service", APIName.OPEN_STUDY, studySite.getEndpoints().get(0).getApiName());
        endPoint=studySite.getEndpoints().get(0);
        Error error=new Error();
        error.setErrorCode("1");
        error.setErrorDate(new Date());
        error.setErrorMessage("Test");
        error.setErrorSource("Test");
        studySite.getEndpoints().get(0).getErrors().add(error);
        assertNotNull("Empty Endpoint", studySite.getEndpoints().get(0));
        dao.merge(endPoint);
        interruptSession();
        studySite=studySiteDao.getById(studySite.getId());
        assertEquals("Wrong Size", 1, studySite.getEndpoints().get(0).getErrors().size());
    }
}