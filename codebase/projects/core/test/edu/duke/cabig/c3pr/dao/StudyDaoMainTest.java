package edu.duke.cabig.c3pr.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.util.ContextTools;

/**
 * Handy Test Class for Daos
 * @author Priyatam
 *
 */
public class StudyDaoMainTest extends TestCase{
    StudyDao dao;
    HealthcareSiteDao siteDao;
    ApplicationContext context;
    StudySiteDao studySiteDao;
    EpochDao epochDao;
    ArmDao armDao;
    static Study newStudy = new Study();

    public StudyDaoMainTest() {
        context = ContextTools.createDeployedApplicationContext();
        setup();        
    }
    
    private void setup(){
        dao = (StudyDao)context.getBean("studyDao");
        siteDao = (HealthcareSiteDao)context.getBean("healthcareSiteDao");
        studySiteDao = (StudySiteDao)context.getBean("studySiteDao");
        epochDao = (EpochDao)context.getBean("epochDao");
        armDao = (ArmDao) context.getBean("armDao");
        
        newStudy.setShortTitleText("Priyatam-Study");
        newStudy.setLongTitleText("D Long Title");
        newStudy.setPhaseCode("Phase 3");
        newStudy.setSponsorCode("CALGB");
        newStudy.setStatus("Active");
        newStudy.setTargetAccrualNumber(150);
        newStudy.setType("Treatment");
        newStudy.setMultiInstitutionIndicator("No");
        
        List <Epoch> defaultEpochs = new ArrayList <Epoch>();
        newStudy.setEpochs(defaultEpochs);
        Epoch firstEpoch = new Epoch ();
        firstEpoch.setName("Screening-Priyatam");
        firstEpoch.setStudy(newStudy);
        defaultEpochs.add(firstEpoch);        
        List <Arm> firstEpochArms = new ArrayList <Arm>();
        Arm noArm = new Arm();
        noArm.setName("No Arm");
        noArm.setEpoch(firstEpoch);
        firstEpochArms.add(noArm);
        firstEpoch.setArms(firstEpochArms);
        
        List <StudySite> defaultStudySites = new ArrayList <StudySite>();
        newStudy.setStudySites(defaultStudySites);
        
        StudySite studySite = new StudySite();
        studySite.setEndDate(new Date("01/01/2007"));
        studySite.setStatusCode("open");
        studySite.setStartDate(new Date("01/01/2007"));
        studySite.setIrbApprovalDate(new Date("01/01/2007"));
        studySite.setRoleCode("123");
        studySite.setName("Duke-Priyatam");
        studySite.setStudy(newStudy);
        
        HealthcareSite site = new HealthcareSite();
        studySite.setSite(site);  
        site.setName("HealthcareName");	
        Address address = new Address();
        address.setCity("reston");
        address.setPostalCode("20191");
        address.setCountryCode("US");
        address.setStateCode("VA");
        address.setStreetAddress("12359 Sunrise Valley drive");        
        site.setAddress(address);
        
        defaultStudySites.add(studySite);     
        
    }

    public void test_SaveStudy() {
        dao.save(newStudy);
        Integer savedId = newStudy.getId();
        assertNotNull(savedId);
    }
    
    public void testGetStudyById(){
    	System.out.println(" 232432 "+newStudy.getId());
    	Study obj = (Study)dao.getById(newStudy.getId());
    	assertNotNull(newStudy.getId());
    }
    
    public void testGetStudySiteById(){
    	int id = (newStudy.getStudySites().get(0)).getId();
    	StudySite obj = (StudySite)studySiteDao.getById(id);
    	assertNotNull(id);    
    } 
   
    public void testSaveHealthcareSite(){
        HealthcareSite site = new HealthcareSite();
        Address add = new Address();
        add.setStreetAddress("675 Main Street");
        add.setCity("Chicago");
        add.setStateCode("Illinois");
        add.setCountryCode("USA");
        add.setPostalCode("12345");
        
        site.setName("Northwestern University Medical Center");
        site.setAddress(add);
        
        siteDao.save(site);
        
    }
    
    private void testSaveStudySite(){
        Study study = (Study)dao.getById(0);
        HealthcareSite site = (HealthcareSite)siteDao.getById(10);
        
        StudySite stuSite = new StudySite();
        stuSite.setSite(site);
        stuSite.setStudy(study);
        stuSite.setIrbApprovalDate(new Date());
        stuSite.setRoleCode("Test");
        stuSite.setStatusCode("Done");
        stuSite.setStartDate(new Date());
        studySiteDao.save(stuSite);
        assertNotNull(stuSite.getId());
    }
    
    public void testSearchStudy()
    {
    	  Study study = new Study();
          study.setShortTitleText("Priyatam-Study");
          study.setType("Treatment");
          List<Study> results = dao.searchByExample(study);
          assertNotNull(results.size());         
          assertEquals("Priyatam-Study", results.get(0).getShortTitleText());
          assertEquals("Priyatam-Treatment", results.get(0).getType());          
    }
    
    public static void main(String[] args) {
       StudyDaoMainTest studyDaoTest = new StudyDaoMainTest();
   }
}
