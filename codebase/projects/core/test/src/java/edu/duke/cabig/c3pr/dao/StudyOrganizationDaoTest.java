package edu.duke.cabig.c3pr.dao;

import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.Error;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyOrganization;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.customfield.CustomFieldDefinition;
import edu.duke.cabig.c3pr.domain.customfield.StudyCustomFieldDefinition;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for StudySiteDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class StudyOrganizationDaoTest extends DaoTestCase {
    private StudyOrganizationDao dao;
    private StudySiteDao studySiteDao;

    /**
     * Test for loading a Study by Id
     * 
     * @throws Exception
     */
    
    private HealthcareSiteDao healthcareSiteDao = (HealthcareSiteDao) getApplicationContext().getBean("healthcareSiteDao");
    private StudyDao studyDao = (StudyDao) getApplicationContext().getBean("studyDao");
    
    protected void setUp() throws Exception {
        super.setUp();
        dao = (StudyOrganizationDao) getApplicationContext().getBean("studyOrganizationDao");
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
        endPoint=new GridEndPoint();
        endPoint.setEndPointProperty(studySite.getHealthcareSite().getStudyEndPointProperty());
        endPoint.setApiName(APIName.OPEN_STUDY);
        endPoint.setServiceName(ServiceName.STUDY);
        
        endPoint.setAttemptDate(date2);
        studySite.getEndpoints().add(endPoint);

        dao.save(studySite);
        interruptSession();
        studySite=dao.getById(studySite.getId());
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
        endPoint=new GridEndPoint();
        endPoint.setEndPointProperty(studySite.getHealthcareSite().getStudyEndPointProperty());
        endPoint.setApiName(APIName.OPEN_STUDY);
        endPoint.setServiceName(ServiceName.STUDY);
        
        endPoint.setAttemptDate(date1);
        studySite.getEndpoints().add(endPoint);

        dao.save(studySite);
        interruptSession();
        studySite=dao.getById(studySite.getId());
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
        studySite.getEndpoints().add(endPoint);
        studySite=dao.merge(studySite);
        interruptSession();
        studySite=dao.getById(studySite.getId());
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
        studySite=dao.merge(studySite);
        interruptSession();
        studySite=dao.getById(studySite.getId());
        Error error=new Error();
        error.setErrorCode("1");
        error.setErrorDate(new Date());
        error.setErrorMessage("Test");
        error.setErrorSource("Test");
        studySite.getEndpoints().get(0).getErrors().add(error);
        assertNotNull("Empty Endpoint", studySite.getEndpoints().get(0));
        studySite=dao.merge(studySite);
        interruptSession();
        studySite=dao.getById(studySite.getId());
        assertEquals("Wrong Size", 1, studySite.getEndpoints().get(0).getErrors().size());
    }
    
    public void testCreateCustomField(){
    	StudyOrganization coordinatingCenter = dao.getById(1002);
    	
    	CustomFieldDefinition studyCustFieldDef = new StudyCustomFieldDefinition();
    	studyCustFieldDef.setDisplayLabel("Parent Concent");
    	studyCustFieldDef.setDisplayName("parent_concent");
    	studyCustFieldDef.setMandatoryIndicator(false);
    	studyCustFieldDef.setFieldType("Text");
    	studyCustFieldDef.setDataType("String");
    	studyCustFieldDef.setDisplayPage("STUDY_DETAILS");
    	studyCustFieldDef.setMaxLength(15);
    	coordinatingCenter.addCustomFieldDefinition(studyCustFieldDef);
    	studyCustFieldDef.setStudyOrganization(coordinatingCenter);
    	
    	interruptSession() ;
    	
    	Study study = studyDao.getById(1000);
    	List<CustomFieldDefinition> custFieldDefns = study.getStudyCoordinatingCenter().getCustomFieldDefinitions();
    	assertEquals("Wrong Size", 1, custFieldDefns.size());
		assertNotNull("Null Definition", custFieldDefns.get(0));
	    assertEquals("Correct Label", "Parent Concent", custFieldDefns.get(0).getDisplayLabel());
    }
}