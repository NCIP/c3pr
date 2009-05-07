package edu.duke.cabig.c3pr.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;

import edu.duke.cabig.c3pr.dao.DiseaseCategoryDao;
import edu.duke.cabig.c3pr.dao.DiseaseTermDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteDao;
import edu.duke.cabig.c3pr.dao.HealthcareSiteInvestigatorDao;
import edu.duke.cabig.c3pr.dao.InvestigatorDao;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.dao.StudySiteDao;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.DiseaseCategory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.repository.StudyRepository;
import edu.duke.cabig.c3pr.tools.Configuration;

public abstract class StudyDaoTestCaseTemplate extends DaoTestCase {

	protected StudyDao studyDao;

    protected StudySiteDao studySiteDao;

    protected StudyRepository studyRepository;

    protected ApplicationContext applicationContext;

    protected HealthcareSiteDao healthcareSitedao;

    protected HealthcareSiteInvestigatorDao hcsidao;

    protected InvestigatorDao investigatorDao;

    protected DiseaseTermDao diseaseTermDao;

    protected DiseaseCategoryDao diseaseCategoryDao;

    protected StudyCreationHelper studyCreationHelper;

    protected void setUp() throws Exception {
        super.setUp();
        applicationContext = getApplicationContext();
        studyDao = (StudyDao) applicationContext.getBean("studyDao");
        studySiteDao = (StudySiteDao) applicationContext.getBean("studySiteDao");
        studyRepository = (StudyRepository) applicationContext.getBean("studyRepository");
        studyCreationHelper = new StudyCreationHelper();
        healthcareSitedao = (HealthcareSiteDao) getApplicationContext()
                        .getBean("healthcareSiteDao");

        hcsidao = (HealthcareSiteInvestigatorDao) applicationContext
                        .getBean("healthcareSiteInvestigatorDao");

        investigatorDao = (InvestigatorDao) applicationContext.getBean("investigatorDao");

        diseaseTermDao = (DiseaseTermDao) applicationContext.getBean("diseaseTermDao");

        diseaseCategoryDao = (DiseaseCategoryDao) applicationContext.getBean("diseaseCategoryDao");
        
    }
    
    protected Study createDefaultStudyWithDesign(Study study) {
        study.setStratificationIndicator(false);
        int disId;
        int hcsId;
        int invId;
        int hcsiId;
        int term1Id;
        int term2Id;
        {
            DiseaseCategory disCatSaved = new DiseaseCategory();
            disCatSaved.setName("AIDS-related Human Papillomavirus");
            diseaseCategoryDao.save(disCatSaved);
            diseaseCategoryDao.initialize(disCatSaved);
            // Investigators
            Investigator invSave = new LocalInvestigator();
            invSave.setFirstName("Investigator first name");
            invSave.setLastName("last name");
            invSave.setNciIdentifier("nci-identifier-inv");
            investigatorDao.save(invSave);

            // healthcare site
            HealthcareSite healthcaresite = new LocalHealthcareSite();
            Address address = new Address();
            address.setCity("Reston");
            address.setCountryCode("USA");
            address.setPostalCode("20191");
            address.setStateCode("VA");
            address.setStreetAddress("12359 Sunrise Valley Dr");
            healthcaresite.setAddress(address);
            healthcaresite.setName("duke healthcare");
            healthcaresite.setDescriptionText("duke healthcare");
            healthcaresite.setNciInstituteCode("Nci duke");
            healthcareSitedao.save(healthcaresite);

            // HCSI
            HealthcareSiteInvestigator hcsiSave = new HealthcareSiteInvestigator();
            invSave.addHealthcareSiteInvestigator(hcsiSave);
            hcsiSave.setHealthcareSite(healthcaresite);
            hcsidao.save(hcsiSave);

            DiseaseTerm term1 = new DiseaseTerm();
            term1.setTerm("AIDS-related anal cancer");
            term1.setCtepTerm("AIDS-related anal cancer");
            term1.setMedraCode(1033333);
            term1.setCategory(disCatSaved);
            DiseaseTerm term2 = new DiseaseTerm();
            term2.setTerm("AIDS-related cervical cancer");
            term2.setCtepTerm("AIDS-related cervical cancer");
            term2.setMedraCode(10322);
            term2.setCategory(disCatSaved);
            diseaseTermDao.save(term1);
            System.out.println("disease term1 id ************" + term1.getId());
            diseaseTermDao.save(term2);
            System.out.println("disease term2 id ************" + term2.getId());

            interruptSession();
            System.out.println("hc site id ************" + healthcaresite.getId());

            disId = disCatSaved.getId();
            invId = invSave.getId();
            hcsId = healthcaresite.getId();
            hcsiId = hcsiSave.getId();
            term1Id = term1.getId();
            term2Id = term2.getId();
            System.out.println("hcsi id ************" + hcsiId);
        }

        DiseaseCategory disCat = diseaseCategoryDao.getById(disId);
        diseaseCategoryDao.initialize(disCat);
        Investigator inv = investigatorDao.getById(invId);

        study.addEpoch(Epoch.createEpoch("Screening"));
        study.addEpoch(Epoch.createEpochWithArms("Treatment", "Arm A", "Arm B", "Arm C"));
        study.addEpoch(Epoch.createEpoch("Follow up"));
        study.getEpochs().get(1).setEnrollmentIndicator(true);

        // Study Site
        StudySite studySite = new StudySite();
        study.addStudySite(studySite);
        studySite.setHealthcareSite(healthcareSitedao.getById(hcsId)); //
        studySite.setStartDate(new Date());
        studySite.setIrbApprovalDate(new Date());
        studySite.setRoleCode("role");
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);

        // Study Investigator
        StudyInvestigator studyInvestigator = studySite.getStudyInvestigators().get(
                        studySite.getStudyInvestigators().size());
        studyInvestigator.setRoleCode("role");
        studyInvestigator.setStartDate(new Date());

        HealthcareSiteInvestigator hcsi = hcsidao.getById(hcsiId);
        hcsi.addStudyInvestigator(studyInvestigator);

        // Identifiers
        List<SystemAssignedIdentifier> identifiers = new ArrayList<SystemAssignedIdentifier>();
        SystemAssignedIdentifier id = new SystemAssignedIdentifier();
        id.setPrimaryIndicator(true);
        id.setSystemName("nci");
        id.setValue("123456");
        id.setType("local");
        identifiers.add(id);
        study.getIdentifiers().addAll(identifiers);

        // Diseases
        System.out.println("disease disCat id ************" + disCat.getId());

        StudyDisease studyDisease = new StudyDisease();
        studyDisease.setDiseaseTerm(diseaseTermDao.getById(term1Id));
        studyDisease.setDiseaseTerm(diseaseTermDao.getById(term2Id));
        studyDisease.setStudy(study);

        study.addStudyDisease(studyDisease);

        return study;
    }

    protected Study getPersistedStudy() {
        Study study = studyCreationHelper.createBasicStudy();
        study = createDefaultStudyWithDesign(study);
        studyCreationHelper.addStudySiteAsCooordinatingCenter(study);
        study = studyRepository.createStudy(study);
        interruptSession();
        study=studyDao.getById(study.getId());;
        studyDao.initialize(study);
        return study;
    }
    
    protected Study getDiffCoCenterPersistedStudy() {
        Study study = studyCreationHelper.createBasicStudy();
        study = createDefaultStudyWithDesign(study);
        addNewCooordinatingCenter(study);
        study = studyRepository.createStudy(study);
        interruptSession();
        return studyDao.getById(study.getId());
    }
    
    protected Study getOpenedStudy() throws Exception{
        Study study=getPersistedStudy();
        study=studyRepository.openStudy(study.getIdentifiers());
        interruptSession();
        study=studyDao.getById(study.getId());;
        studyDao.initialize(study);
        return study;
    }
    
    protected void addNewCooordinatingCenter(Study study) {
    	StudyCoordinatingCenter studyCoordinatingCenter = study.getStudyCoordinatingCenters()
        .get(0);
		HealthcareSite healthcaresite = new LocalHealthcareSite();
		Address address = new Address();
		address.setCity("Reston");
		address.setCountryCode("USA");
		address.setPostalCode("20191");
		address.setStateCode("VA");
		address.setStreetAddress("12359 Sunrise Valley Dr");
		healthcaresite.setAddress(address);
		healthcaresite.setName("duke healthcare");
		healthcaresite.setDescriptionText("duke healthcare");
		healthcaresite.setNciInstituteCode("Nci-Cood");
		healthcareSitedao.save(healthcaresite);
		studyCoordinatingCenter.setHealthcareSite(healthcaresite);
		studyCoordinatingCenter.setStudy(study);
	}
    
    protected void addNewStudySite(Study study, String nciCode) {
    	// healthcare site
        HealthcareSite healthcaresite = new LocalHealthcareSite();
        Address address = new Address();
        address.setCity("Reston");
        address.setCountryCode("USA");
        address.setPostalCode("20191");
        address.setStateCode("VA");
        address.setStreetAddress("12359 Sunrise Valley Dr");
        healthcaresite.setAddress(address);
        healthcaresite.setName("duke healthcare");
        healthcaresite.setDescriptionText("duke healthcare");
        healthcaresite.setNciInstituteCode(nciCode);
        healthcareSitedao.save(healthcaresite);
        StudySite studySite = new StudySite();
        study.addStudySite(studySite);
        studySite.setHealthcareSite(healthcaresite); //
        studySite.setStartDate(new Date());
        studySite.setIrbApprovalDate(new Date());
        studySite.setRoleCode("role");
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.PENDING);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
	}
}
