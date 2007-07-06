package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.TreatmentEpoch;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for EpochDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class EpochDaoTest extends ContextDaoTestCase<EpochDao> {

	private StudyDao studyDao = (StudyDao) getApplicationContext().getBean(
			"studyDao");
	private EpochDao epochDao = (EpochDao) getApplicationContext().getBean(
	"epochDao");

	/**
	 * Test for loading an Epoch by Id
	 * 
	 * @throws Exception
	 * @testType unit
	 */
	public void testGetByIdForTreatmentEpoch() throws Exception {
		TreatmentEpoch loaded = (TreatmentEpoch) getDao().getById(1000);
		assertEquals("Wrong name", "Treatment", loaded.getName());
		assertEquals("Wrong number of arms", 2, loaded.getArms().size());
	}

	/**
	 * Test for loading all Epochs
	 * 
	 * @throws Exception
	 */
	public void testGetAll() throws Exception {
		List<Epoch> actual = getDao().getAll();
		assertEquals(4, actual.size());
		List<Integer> ids = collectIds(actual);
		assertContains("Wrong epoch found", ids, 1000);
		assertContains("Wrong epoch found", ids, 1001);
		assertContains("Wrong epoch found", ids, 1002);
		assertContains("Wrong epoch found", ids, 1003);
	}

	/**
	 * Test for loading all the Arms associated with this Epoch
	 * 
	 * @throws Exception
	 */
	public void testGetArmsForTreatmentEpoch() throws Exception {
		TreatmentEpoch epoch = (TreatmentEpoch) getDao().getById(1000);
		List<Arm> arms = epoch.getArms();
		assertEquals("Wrong number of Arms", 2, arms.size());
		List<Integer> ids = collectIds(arms);

		assertContains("Missing expected Arm", ids, 1000);
		assertContains("Missing expected Arm", ids, 1001);
	}

	/**
	 * Test for loading all the EligibilityCriteria associated with this Epoch
	 * 
	 * @throws Exception
	 */
	public void testGetEligibilityCriteriaForTreatmentEpoch() throws Exception {
		TreatmentEpoch epoch = (TreatmentEpoch) getDao().getById(1000);
		List<EligibilityCriteria> crit = epoch.getEligibilityCriteria();
		assertEquals("Wrong number of criterion", 3, crit.size());
		List<Integer> ids = collectIds(crit);

		assertContains("Missing expected EligibiltyCriterion", ids, 2000);
		assertContains("Missing expected EligibiltyCriterion", ids, 2001);
		assertContains("Missing expected EligibiltyCriterion", ids, 2002);
	}

	public void testGetInclusionEligibilityCriteriaForTreatmentEpoch()
			throws Exception {
		TreatmentEpoch epoch = (TreatmentEpoch) getDao().getById(1000);
		List<InclusionEligibilityCriteria> crit = epoch
				.getInclusionEligibilityCriteria();
		assertEquals("Wrong number of inclusion criterion", 2, crit.size());

	}

	public void testGetExclusionEligibilityCriteriaForTreatmentEpoch()
			throws Exception {
		TreatmentEpoch epoch = (TreatmentEpoch) getDao().getById(1000);
		List<ExclusionEligibilityCriteria> crit = epoch
				.getExclusionEligibilityCriteria();
		assertEquals("Wrong number of exclusion criterion", 1, crit.size());

	}

	public void testSaveTreatmentEpochWithEligibilityCriteria()
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
			studyDao.save(study);
			savedId = study.getId();
		}

		interruptSession();
		{

			Study loadedStudy = studyDao.getById(savedId);
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

	public void testSaveTreatmentEpoch() throws Exception {
		Integer savedId;
		{
			Study loadedStudy = studyDao.getById(1000);
			TreatmentEpoch epoch = new TreatmentEpoch();

			epoch.setName("Anoter Treatment Epoch");
			epoch.setStudy(loadedStudy);
			getDao().save(epoch);
			savedId = epoch.getId();
		}

		interruptSession();
		{
			Epoch loadedEpoch = getDao().getById(savedId);
			// TreatmentEpoch loaded = (TreatmentEpoch)
			// getDao().getById(savedId);
			assertNotNull("Could not reload epoch id " + savedId, loadedEpoch);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertEquals("Wrong Epoch Name:", "Anoter Treatment Epoch",
					loadedEpoch.getName());
		}

	}

	public void testSaveNewTreatmentEpochWithStratificationCriteria()
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
			StratificationCriterion stratCrit = new StratificationCriterion();
			stratCrit.setQuestionText("Stratificaiton question text");
			stratCrit.setQuestionNumber(2);
			// incCrit.setStudy(study);
			study.addStratificationCriteria(stratCrit);
			epoch.addStratificationCriterion(stratCrit);
			epoch.setName("Stratified Treatment Epoch");
			study.addEpoch(epoch);
			// getDao().save(epoch);
			studyDao.save(study);
			savedId = study.getId();
		}

		interruptSession();
		{

			Study loadedStudy = studyDao.getById(savedId);
			TreatmentEpoch loadedEpoch = (TreatmentEpoch) loadedStudy
					.getTreatmentEpochs().get(0);
			// TreatmentEpoch loaded = (TreatmentEpoch)
			// getDao().getById(savedId);
			assertNotNull("Could not reload epoch id " + savedId, loadedEpoch);
			// assertNotNull("GridId not updated", loaded.getGridId());
			assertEquals("Wrong question text:",
					"Stratificaiton question text", loadedEpoch
							.getStratificationCriteria().get(0)
							.getQuestionText());
			assertEquals("Wrong question number:", 2, loadedEpoch
					.getStratificationCriteria().get(0).getQuestionNumber());
		}

	}

	public void testSaveTreatmentEpochWithStratificationCriteria()
			throws Exception {
		Integer savedId;
		{
			Study study = studyDao.getById(1000);
			
			TreatmentEpoch epoch = study.getTreatmentEpochs().get(0);
			StratificationCriterion stratCrit = new StratificationCriterion();
			stratCrit.setQuestionText("Stratification question");
			stratCrit.setQuestionNumber(2);
			study.addStratificationCriteria(stratCrit);
			epoch.addStratificationCriterion(stratCrit);
			epoch.setName("Stratified Treatment Epoch");
			epochDao.save(epoch);
			savedId = epoch.getId();
						
		}

		interruptSession();
		{

			TreatmentEpoch loadedEpoch = (TreatmentEpoch) epochDao.getById(savedId);
			assertNotNull("Could not reload epoch id " + savedId, loadedEpoch);
			// assertNotNull("GridId not updated", loadedEpoch.getGridId());
			assertEquals("Wrong question text:",
					"Stratification question", loadedEpoch
							.getStratificationCriteria().get(1)
							.getQuestionText());
			assertEquals("Wrong question Number:", 2, loadedEpoch
					.getStratificationCriteria().get(1).getQuestionNumber());
		}

	}

}
