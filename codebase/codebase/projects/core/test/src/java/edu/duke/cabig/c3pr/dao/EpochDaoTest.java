package edu.duke.cabig.c3pr.dao;

import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.List;

import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.EligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionAnswerCombination;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.utils.ContextDaoTestCase;

/**
 * JUnit Tests for EpochDao
 * 
 * @author Priyatam
 * @testType unit
 */
public class EpochDaoTest extends ContextDaoTestCase<EpochDao> {

    private StudyDao studyDao = (StudyDao) getApplicationContext().getBean("studyDao");

    private EpochDao epochDao = (EpochDao) getApplicationContext().getBean("epochDao");

    public void testRetiredIndicator() throws Exception {
        Integer savedId;
        {
            Study study = new Study();
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);

            Epoch epoch1 = new Epoch();
            Arm armA = new Arm();
            armA.setName("A");
            armA.setEpoch(epoch1);
            armA.setName("Arm name");
            ArrayList<Arm> aList = new ArrayList<Arm>();
            aList.add(armA);
            epoch1.getArms().addAll(aList);
            epoch1.setName("epoch1");
            epoch1.setStudy(study);
            BookRandomization bRandomization = new BookRandomization();
            BookRandomizationEntry bre = new BookRandomizationEntry();
            bre.setPosition(10);

            if (epoch1 != null) {
                List<Arm> armList = epoch1.getArms();
                for (Arm individualArm : armList) {
                    if (individualArm.getName().equals("Arm name")) {
                        bre.setArm(individualArm);
                    }
                }
            }
            addStratumGroupToEpoch(epoch1);
            bre.setStratumGroup(epoch1.getStratumGroups().get(0));

            List<BookRandomizationEntry> breList = new ArrayList<BookRandomizationEntry>();
            breList.add(bre);
            bRandomization.getBookRandomizationEntry().addAll(breList);
            epoch1.setRandomization(bRandomization);

            InclusionEligibilityCriteria incCrit = new InclusionEligibilityCriteria();
            incCrit.setQuestionText("questionText");
            incCrit.setQuestionNumber(1);
            epoch1.getInclusionEligibilityCriteria().add(incCrit);

            ExclusionEligibilityCriteria excCrit = new ExclusionEligibilityCriteria();
            excCrit.setQuestionText("questionText for exc");
            excCrit.setQuestionNumber(1);
            epoch1.getExclusionEligibilityCriteria().add(excCrit);

            study.getEpochs().add(epoch1);
            study.getEpochs().get(0).setRetiredIndicatorAsTrue();
            // intentionally undoing the retired flag of the epoch so that it may be retrieved.
            // for the test to clear...the retrieved study must not contain its children
            study.getEpochs().get(0).setRetiredIndicatorAsFalse();
            studyDao.save(study);
            savedId = study.getId();
        }

        interruptSession();
        {
            Study loadedStudy = studyDao.getById(savedId);
            Epoch te = loadedStudy.getEpochs().get(0);
            assertEquals(1, loadedStudy.getEpochs().size());
            assertEquals(0, te.getArms().size());
            assertEquals(0, te.getInclusionEligibilityCriteria().size());
            assertEquals(0, te.getEligibilityCriteria().size());
            assertEquals(0, te.getStratificationCriteria().size());
            assertEquals(0, te.getStratumGroups().size());
        }
    }

    public void addStratumGroupToEpoch(Epoch epoch1) {
        StratificationCriterion sc = new StratificationCriterion();
        sc.setQuestionText("will I work?");
        StratificationCriterionPermissibleAnswer scpa = new StratificationCriterionPermissibleAnswer();
        scpa.setPermissibleAnswer("lets find out");
        ArrayList scpaList = new ArrayList();
        scpaList.add(scpa);
        sc.setPermissibleAnswers(scpaList);
        ArrayList scList = new ArrayList();
        scList.add(sc);
        epoch1.setStratificationCriteria(scList);

        StratificationCriterionAnswerCombination scac = new StratificationCriterionAnswerCombination();
        scac.setStratificationCriterion(sc);
        scac.setStratificationCriterionPermissibleAnswer(scpa);
        List<StratificationCriterionAnswerCombination> scacList = new ArrayList<StratificationCriterionAnswerCombination>();
        scacList.add(scac);

        StratumGroup stratumGroup = new StratumGroup();
        stratumGroup.getStratificationCriterionAnswerCombination().addAll(scacList);
        stratumGroup.setCurrentPosition(1);
        stratumGroup.setStratumGroupNumber(2);
        ArrayList<StratumGroup> sgList = new ArrayList<StratumGroup>();
        sgList.add(stratumGroup);

        epoch1.getStratumGroups().addAll(sgList);
    }

    public void testSaveTreatmentEpochWithEligibilityCriteria() throws Exception {
        Integer savedId;
        {
            Study study = new Study();
            study.setPrecisText("New study");
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            Epoch epoch = new Epoch();
            InclusionEligibilityCriteria incCrit = new InclusionEligibilityCriteria();
            incCrit.setQuestionText("questionText");
            incCrit.setQuestionNumber(1);
            epoch.getInclusionEligibilityCriteria().add(incCrit);
            epoch.setName("Anoter Treatment Epoch");
            study.addEpoch(epoch);
            studyDao.save(study);
            savedId = study.getId();
        }

        interruptSession();
        {

            Study loadedStudy = studyDao.getById(savedId);
            Epoch loadedEpoch = loadedStudy.getEpochs().get(0);
            assertNotNull("Could not reload epoch id " + savedId, loadedEpoch);
            assertEquals("Wrong question text:", "questionText", loadedEpoch
                            .getInclusionEligibilityCriteria().get(0).getQuestionText());
        }

    }

    /**
     * Test for loading an Epoch by Id
     * 
     * @throws Exception
     * @testType unit
     */
    public void testGetByIdForTreatmentEpoch() throws Exception {
        Epoch loaded =  getDao().getById(1000);
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
        Epoch epoch = getDao().getById(1000);
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
        Epoch epoch = getDao().getById(1000);
        List<EligibilityCriteria> crit = epoch.getEligibilityCriteria();
        assertEquals("Wrong number of criterion", 3, crit.size());
        List<Integer> ids = collectIds(crit);

        assertContains("Missing expected EligibiltyCriterion", ids, 2000);
        assertContains("Missing expected EligibiltyCriterion", ids, 2001);
        assertContains("Missing expected EligibiltyCriterion", ids, 2002);
    }

    public void testGetInclusionEligibilityCriteriaForTreatmentEpoch() throws Exception {
        Epoch epoch =  getDao().getById(1000);
        List<InclusionEligibilityCriteria> crit = epoch.getInclusionEligibilityCriteria();
        assertEquals("Wrong number of inclusion criterion", 2, crit.size());

    }

    public void testGetExclusionEligibilityCriteriaForTreatmentEpoch() throws Exception {
        Epoch epoch = getDao().getById(1000);
        List<ExclusionEligibilityCriteria> crit = epoch.getExclusionEligibilityCriteria();
        assertEquals("Wrong number of exclusion criterion", 1, crit.size());

    }

    public void testSaveTreatmentEpoch() throws Exception {
        Integer savedId;
        {
            Study loadedStudy = studyDao.getById(1000);
            Epoch epoch = new Epoch();

            epoch.setName("Anoter Treatment Epoch");
            epoch.setStudy(loadedStudy);
            getDao().save(epoch);
            savedId = epoch.getId();
        }

        interruptSession();
        {
            Epoch loadedEpoch = getDao().getById(savedId);
            assertNotNull("Could not reload epoch id " + savedId, loadedEpoch);
            assertEquals("Wrong Epoch Name:", "Anoter Treatment Epoch", loadedEpoch.getName());
        }

    }

    public void testSaveNewTreatmentEpochWithStratificationCriteria() throws Exception {
        Integer savedId;
        {
            Study study = new Study();
            study.setPrecisText("New study");
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            Epoch epoch = new Epoch();
            StratificationCriterion stratCrit = new StratificationCriterion();
            stratCrit.setQuestionText("Stratificaiton question text");
            stratCrit.setQuestionNumber(2);
            epoch.getStratificationCriteria().add(stratCrit);
            epoch.setName("Stratified Treatment Epoch");
            study.addEpoch(epoch);
            studyDao.save(study);
            savedId = study.getId();
        }

        interruptSession();
        {

            Study loadedStudy = studyDao.getById(savedId);
            Epoch loadedEpoch = loadedStudy.getEpochs().get(0);
            assertNotNull("Could not reload epoch id " + savedId, loadedEpoch);

            assertEquals("Wrong question number:", 2, loadedEpoch.getStratificationCriteria()
                            .get(0).getQuestionNumber().intValue());
            assertEquals("Wrong question text:", "Stratificaiton question text", loadedEpoch
                            .getStratificationCriteria().get(0).getQuestionText());
        }

    }

    public void testSaveTreatmentEpochWithStratificationCriteria() throws Exception {
        Integer savedId;
        {
            Study study = new Study();
            study.setPrecisText("New study");
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);

            Epoch epoch = new Epoch();
            epoch.setName("epoch Name");
            epoch.setStudy(study);
            StratificationCriterion stratCrit = new StratificationCriterion();
            stratCrit.setQuestionText("Stratification question");
            stratCrit.setQuestionNumber(2);
            epoch.getStratificationCriteria().add(stratCrit);
            study.getEpochs().add(epoch);

            studyDao.save(study);
            savedId = study.getId();

        }

        interruptSession();
        {
            Study updatedStudy = studyDao.getById(savedId);

            Epoch loadedEpoch = (updatedStudy.getEpochs().get(0));
            assertNotNull("Could not reload epoch id " + savedId, loadedEpoch);
            assertEquals("Wrong question text:", "Stratification question", loadedEpoch
                            .getStratificationCriteria().get(0).getQuestionText());
            assertEquals("Wrong question Number:", 2, loadedEpoch.getStratificationCriteria()
                            .get(0).getQuestionNumber().intValue());
        }

    }

    public void testFailureAddingArmsWithSameNameToTreatmentEpoch() {
        Epoch loadedEpoch = getDao().getById(1000);
        Arm arm = new Arm();
        arm.setName("Arm 1001");
        try {
            loadedEpoch.addArm(arm);
        }
        catch (Exception e) {
        }

    }

    public void testFailureSavingTwoEpochsWithSameNameInOneStudy() throws Exception {
        Integer savedId;
        {

            Study loadedStudy = studyDao.getById(1000);
            Epoch epoch = new Epoch();

            epoch.setName("Treatment");
            epoch.setDescriptionText("descriptionText");
          /*  epoch.setStudy(loadedStudy);
            getDao().save(epoch);*/
        try{
            loadedStudy.addEpoch(epoch);
            fail("it should fail because we have epoch with same name in the database");
        }catch(Exception e){
        }

        interruptSession();

        }

    }

}