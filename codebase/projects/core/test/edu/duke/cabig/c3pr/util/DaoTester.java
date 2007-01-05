package edu.duke.cabig.c3pr.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.dao.ArmDao;
import edu.duke.cabig.c3pr.dao.EpochDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;

/**
 * Handy Test Class for Daos
 * @author Ram Chilukuri, Priyatam
 *
 */
public class DaoTester {
    private static StudyDao dao;
    private HealthcareSiteDao siteDao;
    private ApplicationContext context;
    private StudySiteDao studySiteDao;
    private EpochDao epochDao;
    private ArmDao armDao;
    

    public DaoTester() {
        context = ContextTools.createDeployedApplicationContext();
        init();        
    }
    
    private void init(){
        dao = (StudyDao)context.getBean("studyDao");
        siteDao = (HealthcareSiteDao)context.getBean("healthcareSiteDao");
        studySiteDao = (StudySiteDao)context.getBean("studySiteDao");
        epochDao = (EpochDao)context.getBean("epochDao");
        armDao = (ArmDao) context.getBean("armDao");
    }

    public void testSaveStudy() {
        Integer savedId;
        Study newStudy = new Study();
        newStudy.setShortTitleText("Short Title Inserted");
        newStudy.setLongTitleText("Long Title Inserted");
        newStudy.setPhaseCode("Phase 3");
        newStudy.setSponsorCode("CALGB");
        newStudy.setStatus("Active");
        newStudy.setTargetAccrualNumber(150);
        newStudy.setType("Treatment");
        newStudy.setMultiInstitutionIndicator("No");
        dao.save(newStudy);

        savedId = newStudy.getId();
        System.out.println("Id for saved study is: "+savedId);
    }
    
    public void testGetStudyById(int studyId){
        Study obj = (Study)dao.getById(studyId);
        System.out.println("Retrieved Object "+obj);
    }
    
    public void testGetStudySiteById(int studysiteId){
    	StudySite obj = (StudySite)studySiteDao.getById(studysiteId);
    	System.out.println("Retrieved Object "+obj);
    }
    
    public void testGetEpochById(int epochId){
    	Epoch obj = (Epoch) epochDao.getById(epochId);
    	System.out.println("Retrieved Object "+obj);;
    }
    
    public void testGetArmById(int ArmId){
    	Arm retrievedStudy = (Arm)armDao.getById(ArmId);
    	System.out.println("Short Title for retrieved study is "+retrievedStudy.getId());
    }
    
    public Study testCreateStudyWithDefaultDesign(){
        Study newStudy = new Study();
        
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
               
        dao.save(newStudy);
        
        return newStudy;
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
    }
    
    public void testSearchStudy()
    {
    	  Study study = new Study();
          study.setShortTitleText("Priyatam-Study");
          study.setType("Treatment");
          List<Study> results = dao.searchByExample(study);
          System.out.println(results.size());
          for (Study study2 : results) {
    		System.out.println(study2.getShortTitleText());
    	}    	
    }
    
    public static void main(String[] args) {
       DaoTester studyDaoTest = new DaoTester();
       Study newStudy = studyDaoTest.testCreateStudyWithDefaultDesign();
       
       System.out.println("getting te;stGetStudyById - ");
       studyDaoTest.testGetStudyById(newStudy.getId());       
       System.out.println("getting testGetStudySiteById - ");       
       studyDaoTest.testGetStudySiteById((newStudy.getStudySites().get(0)).getId());
       StudySite studySite = newStudy.getStudySites().get(0);
       System.out.println("Arms " +studySite.getStudy().getEpochs().get(0).getArms()); 
      
       studyDaoTest.testSearchStudy();       
    }
}
