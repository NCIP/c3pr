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

// TODO: Auto-generated Javadoc
/**
 * JUnit Tests for StudySiteDao.
 * 
 * @author Priyatam
 * @testType unit
 */
/**
 * @author kruttikagarwal
 *
 */
public class EndpointDaoTest extends DaoTestCase {
    
    /** The dao. */
    private EndpointDao dao;
    
    /** The study site dao. */
    private StudySiteDao studySiteDao;

    /**
     * Test for loading a Study by Id.
     * 
     * @throws Exception the exception
     */
    
    protected void setUp() throws Exception {
        super.setUp();
        dao = (EndpointDao) getApplicationContext().getBean("endpointDao");
        studySiteDao = (StudySiteDao) getApplicationContext().getBean("studySiteDao");
    }
    
    /**
     * Test save end point.
     * 
     * @throws Exception the exception
     */
    public void testSaveEndPoint() throws Exception {
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
        assertEquals("Wrong Date", date2.toLocaleString(), studySite.getLastAttemptedEndpoint().getAttemptDate().toLocaleString());
    }
    
    /**
     * Test save two end points with different attempted dates.
     * 
     * @throws Exception the exception
     */
    public void testSaveTwoEndPointsWithDifferentAttemptedDates() throws Exception {
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
        assertEquals("Wrong Date", date2.toLocaleString(), studySite.getLastAttemptedEndpoint().getAttemptDate().toLocaleString());
    }
    
    /**
     * Test merge end point.
     * 
     * @throws Exception the exception
     */
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
    
    /**
     * Test merge end point and errors.
     * 
     * @throws Exception the exception
     */
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
    
    /**
     * Test get all endpoints.
     */
    public void testGetAll(){
    	List<EndPoint> endpoints=dao.getAll();
    	assertNotNull("List should not be null"+endpoints);
    	assertEquals("Shouldnt be empty", false, endpoints.isEmpty());
    	assertEquals("Size should be 1", 1, endpoints.size());
    }
}