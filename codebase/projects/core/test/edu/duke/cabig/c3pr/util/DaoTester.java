package edu.duke.cabig.c3pr.util;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;

public class DaoTester {
    private StudyDao dao;
    private HealthcareSiteDao siteDao;
    private ApplicationContext context;
    private StudySiteDao studySiteDao;

    public DaoTester() {
        context = ContextTools.createDeployedApplicationContext();
        init();
        
    }
    
    private void init(){
        dao = (StudyDao)context.getBean("studyDao");
        siteDao = (HealthcareSiteDao)context.getBean("healthcareSiteDao");
        studySiteDao = (StudySiteDao)context.getBean("studySiteDao");
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
        Study retrievedStudy = (Study)dao.getById(studyId);
        System.out.println("Short Title for retrieved study is "+retrievedStudy.getShortTitleText());
    }
    
    public void testCreateStudyWithDefaultDesign(){
        Study newStudy = new Study();
        newStudy.setShortTitleText("D Short Title");
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
        firstEpoch.setName("Screening");
        firstEpoch.setStudy(newStudy);
        defaultEpochs.add(firstEpoch);
        
        List <Arm> firstEpochArms = new ArrayList <Arm>();
        Arm noArm = new Arm();
        noArm.setName("No Arm");
        noArm.setEpoch(firstEpoch);
        firstEpochArms.add(noArm);
        
        firstEpoch.setArms(firstEpochArms);
        
        dao.save(newStudy);
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
//        stuSite.setTargetAccrualNumber(20);
        stuSite.setRoleCode("Test");
        stuSite.setStatusCode("Done");
        stuSite.setStartDate(new Date());
        studySiteDao.save(stuSite);
    }

    public static void main(String[] args) {
        DaoTester studyDaoTest = new DaoTester();
        //studyDaoTest.testSaveStudy();
         //studyDaoTest.testGetStudyById(1);
         
        //studyDaoTest.testCreateStudyWithDefaultDesign();
//        studyDaoTest.testSaveHealthcareSite();
        
         studyDaoTest.testSaveStudySite();
    }
}
