package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.*;
import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.DiseaseCategory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.NonTreatmentEpoch;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for StudyDao
 * 
 * @author Priyatam
 * @author kherm
 * @testType unit
 */

@C3PRUseCases({ CREATE_STUDY,UPDATE_STUDY, SEARCH_STUDY, CREATE_STUDY_INVESTIGATOR,
                 ADD_STRATIFICATION, ADD_DISEASE, VERIFY_SUBJECT })
public class StudyDaoTest extends DaoTestCase {
	private StudyDao dao = (StudyDao) getApplicationContext().getBean(
			"studyDao");

	private HealthcareSiteDao healthcareSitedao = (HealthcareSiteDao) getApplicationContext()
			.getBean("healthcareSiteDao");

	private HealthcareSiteInvestigatorDao hcsidao = (HealthcareSiteInvestigatorDao) getApplicationContext()
			.getBean("healthcareSiteInvestigatorDao");

	private InvestigatorDao investigatorDao = (InvestigatorDao) getApplicationContext()
			.getBean("investigatorDao");

	private DiseaseTermDao diseaseTermDao = (DiseaseTermDao) getApplicationContext()
			.getBean("diseaseTermDao");

	private DiseaseCategoryDao diseaseCategoryDao = (DiseaseCategoryDao) getApplicationContext()
			.getBean("diseaseCategoryDao");

	/**
	 * Test for loading a Study by Id
	 * 
	 * @throws Exception
	 */
	public void testGetById() throws Exception {
		Study study = dao.getById(1000);
		assertNotNull("Study 1000 not found", study);
		assertEquals("Wrong name", "precis_text", study.getPrecisText());
	}

	/**
	 * Test for loading all Studies
	 * 
	 * @throws Exception
	 */
	public void testGetAll() throws Exception {
		List<Study> actual = dao.getAll();
		assertEquals(3, actual.size());
		List<Integer> ids = collectIds(actual);
		assertContains("Wrong study found", ids, 1000);
		assertContains("Wrong study found", ids, 1001);
		assertContains("Wrong study found", ids, 1002);
	}

	/**
	 * Test Saving of a Study with all associations present
	 * 
	 * @throws Exception
	 */
	public void testSaveNewStudy() throws Exception {
		Integer savedId;
		{
			Study study = new Study();
			study.setPrecisText("New study");
			study.setShortTitleText("ShortTitleText");
			study.setLongTitleText("LongTitleText");
			study.setPhaseCode("PhaseCode");
			study.setStatus("Status");
			study.setTargetAccrualNumber(150);
			study.setType("Type");
			study.setMultiInstitutionIndicator("true");
			dao.save(study);
			savedId = study.getId();
			assertNotNull("The saved study didn't get an id", savedId);
		}

		interruptSession();
		{
			Study loaded = dao.getById(savedId);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertNotNull("Could not reload study with id " + savedId, loaded);
			assertEquals("Wrong name", "ShortTitleText", loaded
					.getShortTitleText());
		}
	}

	/**
	 * Test Saving of a Study with all associations present
	 * 
	 * @throws Exception
	 */
	public void testSaveNewStudyWithAssociations() throws Exception {
		Integer savedId;
		{
			Study study = new Study();
			study.setPrecisText("New study");
			study.setShortTitleText("ShortTitleText");
			study.setLongTitleText("LongTitleText");
			study.setPhaseCode("PhaseCode");
			study.setStatus("Status");
			study.setTargetAccrualNumber(150);
			study.setType("Type");
			study.setMultiInstitutionIndicator("true");

			createDefaultStudyWithDesign(study);
			dao.save(study);
			savedId = study.getId();
			assertNotNull("The saved study didn't get an id", savedId);
		}

		interruptSession();
		{
			Study loaded = dao.getById(savedId);
			assertNotNull("Could not reload study with id " + savedId, loaded);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertEquals("Wrong name", "New study", loaded.getPrecisText());
		}
	}

	public void testHibernateBug() throws Exception {
		Integer savedId;
		{
			// healthcare site
			HealthcareSite healthcaresite = new HealthcareSite();
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
			System.out.println("hc site id ************"
					+ healthcaresite.getId());
			HealthcareSite hcsiteloaded = healthcareSitedao
					.getById(healthcaresite.getId());

			// Investigators
			Investigator inv = new Investigator();
			inv.setFirstName("Investigator first name");
			investigatorDao.save(inv);
			Investigator invloaded = investigatorDao.getById(inv.getId());

			// HCSI
			HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();
			invloaded.addHealthcareSiteInvestigator(hcsi);
			hcsiteloaded.addHealthcareSiteInvestigator(hcsi);
			hcsidao.save(hcsi);
			System.out.println("hcsi id ************" + hcsi.getId());
			HealthcareSiteInvestigator hcsiloaded = hcsidao.getById(hcsi
					.getId());

			Study study = new Study();
			study.setShortTitleText("ShortTitleText");
			study.setLongTitleText("LongTitleText");
			study.setPhaseCode("PhaseCode");
			study.setStatus("Status");
			study.setTargetAccrualNumber(150);
			study.setType("Type");
			study.setMultiInstitutionIndicator("true");

			study.addEpoch(Epoch.create("Screening"));
			study
					.addEpoch(Epoch.create("Treatment", "Arm A", "Arm B",
							"Arm C"));
			study.addEpoch(Epoch.create("Follow up"));

			// Identifiers
			SystemAssignedIdentifier id = new SystemAssignedIdentifier();
			id.setPrimaryIndicator(true);
			id.setSystemName("nci");
			id.setValue("123456");
			id.setType("local");
			study.addIdentifier(id);

			// Study Site
			StudySite studySite = new StudySite();
			studySite.setHealthcareSite(hcsiteloaded);
			studySite.setRoleCode("role");
			studySite.setStatusCode("active");

			study.addStudySite(studySite);

			// Study Investigator
			StudyInvestigator studyInvestigator = new StudyInvestigator();
			studyInvestigator.setRoleCode("role");
			studyInvestigator.setStatusCode("active");

			studySite.addStudyInvestigator(studyInvestigator);

			hcsi.addStudyInvestigator(studyInvestigator);

			// Stratifications
			StratificationCriterionPermissibleAnswer ans = new StratificationCriterionPermissibleAnswer();
			ans.setPermissibleAnswer("it is valid");
	
			dao.save(study);

			savedId = study.getId();
			assertNotNull("The saved study didn't get an id", savedId);
		}

		interruptSession();
		{
			Study loaded = dao.getById(savedId);
			assertNotNull("Could not reload study with id " + savedId, loaded);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertEquals("Wrong name", "ShortTitleText", loaded
					.getShortTitleText());
		}
	}
	
	/**
	 * Test for retrieving all study sites associated with this Study
	 * 
	 * @throws Exception
	 */
	
	public void testGetStudySites() throws Exception {
		Study study = dao.getById(1000);
		List<StudySite> sites = study.getStudySites();
		assertEquals("Wrong number of study sites", 1, sites.size());
		List<Integer> ids = collectIds(sites);

		assertContains("Missing expected study site", ids, 1000);
	}

	/**
	 * Test for retrieving all Epochs associated with this Study
	 * 
	 * @throws Exception
	 */
	public void testGetEpochs() throws Exception {
		Study study = dao.getById(1000);
		List<Epoch> epochs = study.getEpochs();
		assertEquals("Wrong number of Epochs", 6, epochs.size());
		 List<Integer> ids = collectIds(epochs);

		 assertContains("Missing expected Epoch", ids, 1000);
		 assertContains("Missing expected Epoch", ids, 1001);
	}

	public void testGetTreatmentEpochs() throws Exception {

        Study study = dao.getById(1000);
		List<Epoch> el = study.getEpochs();

		TreatmentEpoch te = new TreatmentEpoch();
		te.setName("test");
		te.setStudy(study);
		el.add(te);
		study.setEpochs(el);

		dao.save(study);

		interruptSession();
		study = dao.getById(1000);

		 List<Epoch> epochs = study.getEpochs();
		 assertEquals("Wrong number of Epochs",7, epochs.size());
		 List<Integer> ids = collectIds(epochs);

		 assertContains("Missing expected Epoch", ids, 1000);
		 assertContains("Missing expected Epoch", ids, 1001);
    }

	/**
	 * Test for retrieving all Arms associated with this Studies' epochs
	 * 
	 * @throws Exception
	 */


	/**
	 * Test for Study Paticipant Assignments for a given Study
	 * 
	 * @throws Exception
	 */
	public void testGetStudySubjectsForStudy() throws Exception {
		List<StudySubject> spa = dao
				.getStudySubjectsForStudy(1000);
		assertEquals(2, spa.size());
		List<Integer> ids = collectIds(spa);
		assertContains("Wrong study found", ids, 1000);
	}

	/**
	 * Test for searching Studies without wildcards
	 * 
	 * @throws Exception
	 */
	public void testSearchStudySimple() {
		Study studySearchCriteria = new Study();
		studySearchCriteria.setShortTitleText("short_title_text");
		List<Study> results = dao.searchByExample(studySearchCriteria);
		assertEquals("Wrong number of Studies", 2, results.size());
		assertEquals("short_title_text", results.get(0).getShortTitleText());
		assertEquals("short_title_text", results.get(1).getShortTitleText());
	}

    /**
     * Make sure DAO returns unique results
     */
    public void testIdentifierUniqueResults(){
         Study studySearchCriteria = new Study();
		studySearchCriteria.setShortTitleText("short_title_text2");
		List<Study> results = dao.searchByExample(studySearchCriteria);
		assertEquals("Wrong number of Studies", 1, results.size());
    }

    private Study createDefaultStudyWithDesign(Study study) {
		// Investigators
		Investigator inv = new Investigator();
		inv.setFirstName("Investigator first name");

		investigatorDao.save(inv);

		study.addEpoch(Epoch.create("Screening"));
		study.addEpoch(Epoch.create("Treatment", "Arm A", "Arm B", "Arm C"));
		study.addEpoch(Epoch.create("Follow up"));

		// healthcare site
		HealthcareSite healthcaresite = new HealthcareSite();
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
		System.out.println("hc site id ************" + healthcaresite.getId());

		// Study Site
		StudySite studySite = new StudySite();
		study.addStudySite(studySite);
		studySite.setHealthcareSite(healthcaresite); //
		studySite.setStartDate(new Date());
		studySite.setIrbApprovalDate(new Date());
		studySite.setRoleCode("role");
		studySite.setStatusCode("active");

		// HCSI
		HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();
		inv.addHealthcareSiteInvestigator(hcsi);
		healthcaresite.addHealthcareSiteInvestigator(hcsi);
		hcsidao.save(hcsi);
		System.out.println("hcsi id ************" + hcsi.getId());

		// Study Investigator
		StudyInvestigator studyInvestigator = studySite.getStudyInvestigators().get(studySite.getStudyInvestigators().size());
		studyInvestigator.setRoleCode("role");
		studyInvestigator.setStartDate(new Date());

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
		DiseaseCategory disCat = new DiseaseCategory();
		disCat.setName("AIDS-related Human Papillomavirus");
		diseaseCategoryDao.save(disCat);
		System.out.println("disease disCat id ************" + disCat.getId());

		DiseaseTerm term1 = new DiseaseTerm();
		term1.setTerm("AIDS-related anal cancer");
		term1.setCtepTerm("AIDS-related anal cancer");
		term1.setMedraCode(1033333);
		term1.setCategory(disCat);
		DiseaseTerm term2 = new DiseaseTerm();
		term2.setTerm("AIDS-related cervical cancer");
		term2.setCtepTerm("AIDS-related cervical cancer");
		term2.setMedraCode(10322);
		term2.setCategory(disCat);
		diseaseTermDao.save(term1);
		System.out.println("disease term1 id ************" + term1.getId());
		diseaseTermDao.save(term2);
		System.out.println("disease term2 id ************" + term2.getId());

		StudyDisease studyDisease = new StudyDisease();
		studyDisease.setDiseaseTerm(term1);
		studyDisease.setDiseaseTerm(term2);
		studyDisease.setStudy(study);

		study.addStudyDisease(studyDisease);

		return study;
	}

	private List<HealthcareSite> getHealthcareSites() {
		return healthcareSitedao.getAll();
	}

	public void testSaveNewStudyWithTreatmentEpochs() throws Exception {
		Integer savedId;
		{
			Study study = new Study();
			study.setShortTitleText("ShortTitleText");
			study.setLongTitleText("LongTitleText");
			study.setPhaseCode("PhaseCode");
			study.setStatus("Status");
			study.setTargetAccrualNumber(150);
			study.setType("Type");
			study.setMultiInstitutionIndicator("true");

			study.addEpoch(Epoch.createTreatmentEpoch("TestTreatmentEpoch1",
					"Arm A", "Arm B", "Arm C"));
			study.addEpoch(Epoch.createTreatmentEpoch("TestTreatmentEpoch2"));

			dao.save(study);

			savedId = study.getId();
			assertNotNull("The saved study didn't get an id", savedId);
		}

		interruptSession();
		{
			Study loaded = dao.getById(savedId);
			assertNotNull("Could not reload study with id " + 1000, loaded);
			List<TreatmentEpoch> newTreatmentEpochs = loaded
					.getTreatmentEpochs();
			assertEquals("Wrong number of Treatment Epochs are retreived", 2,
					newTreatmentEpochs.size());
			for (Epoch newEpoch : newTreatmentEpochs) {
				assertEquals("Expected to get TreatmentEpoch: ",
						"edu.duke.cabig.c3pr.domain.TreatmentEpoch", newEpoch
								.getClass().getName());
			}

		}
	}

	public void testSaveNewStudyWithNonTreatmentEpochs() throws Exception {
		Integer savedId;
		{

			Study study = new Study();
			study.setShortTitleText("ShortTitleText");
			study.setLongTitleText("LongTitleText");
			study.setPhaseCode("PhaseCode");
			study.setStatus("Status");
			study.setTargetAccrualNumber(150);
			study.setType("Type");
			study.setMultiInstitutionIndicator("true");

			study.addEpoch(Epoch.createNonTreatmentEpoch("TestTreatmentEpoch"));

			NonTreatmentEpoch newEpoch = new NonTreatmentEpoch();
			newEpoch.setName("Test Non Treatment Epoch");
			newEpoch.setAccrualCeiling(10);
			newEpoch.setAccrualIndicator("YES");
			newEpoch.setReservationIndicator("NO");
			newEpoch.setEnrollmentIndicator("NO");
			study.addEpoch(newEpoch);

			dao.save(study);

			savedId = study.getId();
			assertNotNull("The saved study didn't get an id", savedId);
		}

		interruptSession();
		{
			Study loaded = dao.getById(savedId);
			assertNotNull("Could not reload study with id " + savedId, loaded);
			List<NonTreatmentEpoch> newNonTreatmentEpochs = loaded
					.getNonTreatmentEpochs();
			assertEquals("Wrong number of NonTreatment Epochs are retreived",
					2, newNonTreatmentEpochs.size());
			for (Epoch newEpoch : newNonTreatmentEpochs) {
				assertEquals("Expected to get NonTreatmentEpoch: ",
						"edu.duke.cabig.c3pr.domain.NonTreatmentEpoch",
						newEpoch.getClass().getName());
			}
			assertEquals("Wrong enrollment indicator", "NO",
					((NonTreatmentEpoch) newNonTreatmentEpochs.get(1))
							.getEnrollmentIndicator());
			assertEquals("Wrong accrual indicator", "YES",
					((NonTreatmentEpoch) newNonTreatmentEpochs.get(1))
							.getAccrualIndicator());
			assertEquals("Wrong reservation indicator", "NO",
					((NonTreatmentEpoch) newNonTreatmentEpochs.get(1))
							.getReservationIndicator());
			assertEquals("Wrong accrual ceiling", 10,
					((NonTreatmentEpoch) newNonTreatmentEpochs.get(1))
							.getAccrualCeiling().intValue());

		}
	}

	public void testSaveNewStudyWithTreatmentEpochsAndArms() throws Exception {
		Integer savedId;
		{
			Study study = new Study();
			study.setShortTitleText("ShortTitleText");
			study.setLongTitleText("LongTitleText");
			study.setPhaseCode("PhaseCode");
			study.setStatus("Status");
			study.setTargetAccrualNumber(150);
			study.setType("Type");
			study.setMultiInstitutionIndicator("true");

			study.addEpoch(Epoch.createTreatmentEpoch("TestTreatmentEpoch1",
					"Arm A", "Arm B", "Arm C"));
			TreatmentEpoch epoch = new TreatmentEpoch();
			epoch.setName("TestTreatmentEpoch2");
			Arm arm = new Arm();
			arm.setName("Test Arm");
			epoch.addArm(arm);
			study.addEpoch(epoch);

			dao.save(study);
			savedId = study.getId();
			assertNotNull("The saved study didn't get an id", savedId);
		}

		interruptSession();
		{
			Study loaded = dao.getById(savedId);
			assertNotNull("Could not reload study with id " + 1000, loaded);
			List<TreatmentEpoch> newTreatmentEpochs = loaded
					.getTreatmentEpochs();
			assertEquals("Wrong number of Treatment Epochs are retreived", 2,
					newTreatmentEpochs.size());
			for (TreatmentEpoch newEpoch : newTreatmentEpochs) {
				assertEquals("Expected to get TreatmentEpoch: ",
						"edu.duke.cabig.c3pr.domain.TreatmentEpoch", newEpoch
								.getClass().getName());
			}

			assertEquals("Expected to get 3 Arms: ", 3, newTreatmentEpochs.get(
					0).getArms().size());
			assertEquals("Expected to get 1 Arm: ", 1, newTreatmentEpochs
					.get(1).getArms().size());
			assertEquals("Expected to get Arm with name, Arm A: ", "Arm A",
					newTreatmentEpochs.get(0).getArms().get(0).getName());
			assertEquals("Expected to get Arm with name, Test Arm: ",
					"Test Arm", newTreatmentEpochs.get(1).getArms().get(0)
							.getName());

		}
	}

	/**
	 * Test for searching Studies using wildcards
	 * 
	 * @throws Exception
	 */
	public void testSearchStudyByWildCards() throws Exception {
		Study studySearchCriteria = new Study();
		studySearchCriteria.setShortTitleText("ti%e");
		List<Study> results = dao.searchByExample(studySearchCriteria, true);
		assertEquals("Wrong number of Studies", 3, results.size());
	}

	/**
	 * Test for retrieving all study funding sponsors associated with this Study
	 * 
	 * @throws Exception
	 */
	public void testGetStudyFundingSponsors() throws Exception {
		Study study = dao.getById(1000);
		List<StudyFundingSponsor> sponsors = study.getStudyFundingSponsors();
		assertEquals("Wrong number of study funding sponsors", 1, sponsors.size());
		System.out.println("Study funding sponsor is: "+sponsors.get(0).getHealthcareSite().getName());
		List<Integer> ids = collectIds(sponsors);

		assertContains("Missing expected study funding sponsor", ids, 1001);
	}
	
	/**
	 * Test for retrieving all study coordinating centers associated with this Study
	 * 
	 * @throws Exception
	 */
	public void testGetStudyCoordinatingCenters() throws Exception {
		Study study = dao.getById(1000);
		List<StudyCoordinatingCenter> centers = study.getStudyCoordinatingCenters();
		assertEquals("Wrong number of study coordinating centers", 1, centers.size());
		List<Integer> ids = collectIds(centers);

		assertContains("Missing expected study funding sponsor", ids, 1004);
	}
	
	public void testSaveNewStudyWithFundingSponsor() throws Exception {
		Integer savedId;
		{
			HealthcareSite sponsor = healthcareSitedao.getById(1001);
			HealthcareSite site = healthcareSitedao.getById(1000);
			
			
			Study study = new Study();
			study.setShortTitleText("ShortTitleText");
			study.setLongTitleText("LongTitleText");
			study.setPhaseCode("PhaseCode");
			study.setStatus("Status");
			study.setTargetAccrualNumber(150);
			study.setType("Type");
			study.setMultiInstitutionIndicator("true");
			
			
			// Study Site
			StudySite studySite = new StudySite();
			studySite.setHealthcareSite(site);
			studySite.setRoleCode("role");
			studySite.setStatusCode("active");
			
			study.addStudySite(studySite);
			
			// Study funding sponsor
			StudyFundingSponsor fundingSponsor = new StudyFundingSponsor();
			fundingSponsor.setHealthcareSite(sponsor);
			study.addStudyOrganization(fundingSponsor);
			
			dao.save(study);
			
			savedId = study.getId();
			assertNotNull("The saved study didn't get an id", savedId);
		}

		interruptSession();
		{
			Study loaded = dao.getById(savedId);
			assertNotNull("Could not reload study with id " + savedId, loaded);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertEquals("Wrong name", "ShortTitleText", loaded.getShortTitleText());
			assertEquals("Wrong study funding sponsor", "National Cancer Institute", loaded.getStudyFundingSponsors().get(0).getHealthcareSite().getName());
		}
	}
	
	public void testSaveNewStudyWithCoordinatingCenter() throws Exception {
		Integer savedId;
		{
			HealthcareSite sponsor = healthcareSitedao.getById(1001);
			HealthcareSite site = healthcareSitedao.getById(1000);
			HealthcareSite center = healthcareSitedao.getById(1002);
			
			
			Study study = new Study();
			study.setShortTitleText("ShortTitleText");
			study.setLongTitleText("LongTitleText");
			study.setPhaseCode("PhaseCode");
			study.setStatus("Status");
			study.setTargetAccrualNumber(150);
			study.setType("Type");
			study.setMultiInstitutionIndicator("true");
			
			
			// Study Site
			StudySite studySite = new StudySite();
			studySite.setHealthcareSite(site);
			studySite.setRoleCode("role");
			studySite.setStatusCode("active");
			
			study.addStudySite(studySite);
			
			// Study funding sponsor
			StudyFundingSponsor fundingSponsor = new StudyFundingSponsor();
			fundingSponsor.setHealthcareSite(sponsor);
			study.addStudyOrganization(fundingSponsor);
			
			// Study coordinating center
			StudyCoordinatingCenter coCenter = new StudyCoordinatingCenter();
			coCenter.setHealthcareSite(center);
			study.addStudyOrganization(coCenter);
			
			dao.save(study);
			
			savedId = study.getId();
			assertNotNull("The saved study didn't get an id", savedId);
		}

		interruptSession();
		{
			Study loaded = dao.getById(savedId);
			assertNotNull("Could not reload study with id " + savedId, loaded);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertEquals("Wrong name", "ShortTitleText", loaded.getShortTitleText());
			assertEquals("Wrong study funding sponsor", "National Cancer Institute", loaded.getStudyFundingSponsors().get(0).getHealthcareSite().getName());
			assertEquals("Wrong study coordinating center", "CALGB", loaded.getStudyCoordinatingCenters().get(0).getHealthcareSite().getName());
		}
	}


}