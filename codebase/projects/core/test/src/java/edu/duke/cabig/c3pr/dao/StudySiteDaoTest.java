package edu.duke.cabig.c3pr.dao;

import java.util.List;

import edu.duke.cabig.c3pr.constants.APIName;
import edu.duke.cabig.c3pr.constants.ServiceName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for StudySiteDao.
 * 
 * @author Priyatam
 * @testType unit
 */
public class StudySiteDaoTest extends DaoTestCase {
    
    /** The dao. */
    private StudySiteDao dao;
    
    /** The endpoint dao. */
    private EndpointDao endpointDao;

    /**
     * Test for loading a Study by Id.
     * 
     * @throws Exception the exception
     */
    
    protected void setUp() throws Exception {
    	super.setUp();
    	dao = (StudySiteDao) getApplicationContext().getBean("studySiteDao");
    	endpointDao = (EndpointDao) getApplicationContext().getBean("endpointDao");
    }
    
    /**
     * Test get by id.
     * 
     * @throws Exception the exception
     */
    public void testGetById() throws Exception {
        StudySite studySite = dao.getById(1000);
        assertNotNull("StudySite 1000 not found", studySite);
        assertEquals("StudySiteStudyVersions not found for studysite",1,studySite.getStudySiteStudyVersions().size());
    }

    /**
     * Test get by nci institute code.
     * 
     * @throws Exception the exception
     */
    public void testGetByNciInstituteCode() throws Exception {
        List<StudySite> sites = dao.getByCtepCode("code");
        assertTrue(sites.size() == 2);
        for (StudySite site : sites) {
            assertEquals(site.getHealthcareSite().getCtepCode(), "code");
        }
    }
    
    /**
     * Test save study site with end point.
     * 
     * @throws Exception the exception
     */
    public void testSaveStudySiteWithEndPoint() throws Exception {
        List<StudySite> sites = dao.getByCtepCode("code");
        assertTrue(sites.size() == 2);
        for (StudySite site : sites) {
            assertEquals(site.getHealthcareSite().getCtepCode(), "code");
        }
        StudySite studySite=sites.get(0);
        EndPoint endPoint=new GridEndPoint();
        endPoint.setStudyOrganization(studySite);
        endPoint.setEndPointProperty(studySite.getHealthcareSite().getStudyEndPointProperty());
        endPoint.setApiName(APIName.OPEN_STUDY);
        endPoint.setServiceName(ServiceName.STUDY);
        studySite.getEndpoints().add(endPoint);
        endpointDao.save(endPoint);
        interruptSession();
        endPoint=endpointDao.getById(endPoint.getId());
        assertNotNull("Empty Endpoint", endPoint);
        assertEquals("Wrong Service", ServiceName.STUDY, endPoint.getServiceName());
        assertEquals("Wrong Service", APIName.OPEN_STUDY, endPoint.getApiName());
    }
}