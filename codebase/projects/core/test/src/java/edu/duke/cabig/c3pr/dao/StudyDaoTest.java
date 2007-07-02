package edu.duke.cabig.c3pr.dao;

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
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.DaoTestCase;

/**
 * JUnit Tests for StudyDao
 * 
 * @author Priyatam
 * @testType unit
 */
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
			Identifier id = new Identifier();
			id.setPrimaryIndicator(true);
			id.setSource("nci");
			id.setValue("123456");
			id.setType("local");
			study.addIdentifier(id);

			// Study Site
			StudySite studySite = new StudySite();
			studySite.setSite(hcsiteloaded);
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
			StratificationCriterion cri = new StratificationCriterion();
			cri.setQuestionNumber(1);
			cri.setQuestionText("is criterion valid");
			cri.addPermissibleAnswer(ans);

			study.addStratificationCriteria(cri);

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
	 * Test for retrieving all Epochs associated with this Study
	 * 
	 * @throws Exception
	 */
	public void testGetEpochs() throws Exception {
		Study study = dao.getById(1000);
		List<Epoch> epochs = study.getEpochs();
		assertEquals("Wrong number of Epochs", 6, epochs.size());
		// List<Integer> ids = collectIds(epochs);

		// assertContains("Missing expected Epoch", ids, 1000);
		// assertContains("Missing expected Epoch", ids, 1001);
	}

	public void testGetTreatmentEpochs() throws Exception {
		Study study = dao.getById(1000);
		List<Epoch> el = study.getEpochs();
		System.out.println(el);
		TreatmentEpoch te = new TreatmentEpoch();
		te.setName("test");
		te.setStudy(study);
		el.add(te);
		study.setEpochs(el);

		dao.save(study);

		interruptSession();
		study = dao.getById(1000);
		for (Epoch e : study.getEpochs()) {
			System.out.println(e.getId());
		}

		// List<Epoch> epochs = study.getEpochs();
		// assertEquals("Wrong number of Epochs", 2, epochs.size());
		// List<Integer> ids = collectIds(epochs);

		// assertContains("Missing expected Epoch", ids, 1000);
		// assertContains("Missing expected Epoch", ids, 1001);
	}

	/**
	 * Test for retrieving all Arms associated with this Studies' epochs
	 * 
	 * @throws Exception
	 */
	public void testGetArms() throws Exception {
		List<Arm> actual = dao.getArmsForStudy(1000);

		assertEquals("Wrong number of assigments", 8, actual.size());
		List<Integer> ids = collectIds(actual);

		assertContains("Missing expected Arm", ids, 1000);
		assertContains("Missing expected Arm", ids, 1001);
		assertContains("Missing expected Arm", ids, 1002);
		assertContains("Missing expected Arm", ids, 1003);
	}

	/**
	 * Test for Study Paticipant Assignments for a given Study
	 * 
	 * @throws Exception
	 */
	public void testGetStudyParticipantAssignmentsForStudy() throws Exception {
		List<StudyParticipantAssignment> spa = dao
				.getStudyParticipantAssignmentsForStudy(1000);
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
		studySite.setSite(healthcaresite); //
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
		StudyInvestigator studyInvestigator = new StudyInvestigator();
		studyInvestigator.setRoleCode("role");
		studyInvestigator.setStartDate(new Date());
		studySite.addStudyInvestigator(studyInvestigator);

		hcsi.addStudyInvestigator(studyInvestigator);

		// Identifiers
		List<Identifier> identifiers = new ArrayList<Identifier>();
		Identifier id = new Identifier();
		id.setPrimaryIndicator(true);
		id.setSource("nci");
		id.setValue("123456");
		id.setType("local");
		identifiers.add(id);
		study.setIdentifiers(identifiers);

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

		// Stratifications
		StratificationCriterionPermissibleAnswer ans = new StratificationCriterionPermissibleAnswer();
		ans.setPermissibleAnswer("it is valid");
		StratificationCriterionPermissibleAnswer ans2 = new StratificationCriterionPermissibleAnswer();
		ans.setPermissibleAnswer("it is valid");
		StratificationCriterion cri = new StratificationCriterion();
		cri.setQuestionNumber(1);
		cri.setQuestionText("is criterion valid");
		cri.addPermissibleAnswer(ans);
		StratificationCriterion cri2 = new StratificationCriterion();
		cri.setQuestionNumber(2);
		cri.setQuestionText("is criterion valid 2");
		cri.addPermissibleAnswer(ans2);

		study.addStratificationCriteria(cri);
		study.addStratificationCriteria(cri2);

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
							.getAccrualCeiling());

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

	public void testSaveStudyWithTreatmentEpochAndInclusionEligibilityCriteria()
			throws Exception {
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
			TreatmentEpoch epoch = new TreatmentEpoch();
			InclusionEligibilityCriteria incCrit = new InclusionEligibilityCriteria();
			incCrit.setQuestionText("questionText");
			incCrit.setQuestionNumber(1);
			// incCrit.setStudy(study);
			study.addInclusionEligibilityCriteria(incCrit);
			epoch.addEligibilityCriterion(incCrit);
			epoch.setName("Anoter Treatment Epoch");
			study.addEpoch(epoch);
			// getDao().save(epoch);
			dao.save(study);
			savedId = study.getId();
		}

		interruptSession();
		{

			Study loadedStudy = dao.getById(savedId);
			TreatmentEpoch loadedEpoch = (TreatmentEpoch) loadedStudy
					.getEpochs().get(0);
			// TreatmentEpoch loaded = (TreatmentEpoch)
			// getDao().getById(savedId);
			assertNotNull("Could not reload epoch id " + savedId, loadedEpoch);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertEquals("Wrong question text:", "questionText", loadedEpoch
					.getInclusionEligibilityCriteria().get(0).getQuestionText());
		}

	}

	public void testSaveStudyWithInclusionEligibilityCriteria()
			throws Exception {
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
			InclusionEligibilityCriteria incCrit = new InclusionEligibilityCriteria();
			incCrit.setQuestionText("questionText");
			incCrit.setQuestionNumber(1);
			// incCrit.setStudy(study);
			study.addInclusionEligibilityCriteria(incCrit);
			// getDao().save(epoch);
			dao.save(study);
			savedId = study.getId();
		}

		interruptSession();
		{

			Study loadedStudy = dao.getById(savedId);
			assertNotNull("Could not reload study:" + savedId, loadedStudy);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertEquals("Wrong question text:", "questionText", loadedStudy.getIncCriterias().get(0).getQuestionText());
		}

	}

}