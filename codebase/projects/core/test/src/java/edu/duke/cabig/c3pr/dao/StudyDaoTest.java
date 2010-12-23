package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.ADD_DISEASE;
import static edu.duke.cabig.c3pr.C3PRUseCase.ADD_STRATIFICATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_STUDY;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_STUDY_INVESTIGATOR;
import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_STUDY;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_STUDY;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateSystemException;

import com.semanticbits.querybuilder.AdvancedSearchCriteriaParameter;
import com.semanticbits.querybuilder.AdvancedSearchHelper;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.constants.ConsentingMethod;
import edu.duke.cabig.c3pr.constants.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.constants.EpochType;
import edu.duke.cabig.c3pr.constants.InvestigatorStatusCodeEnum;
import edu.duke.cabig.c3pr.constants.OrganizationIdentifierTypeEnum;
import edu.duke.cabig.c3pr.constants.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.Consent;
import edu.duke.cabig.c3pr.domain.ConsentQuestion;
import edu.duke.cabig.c3pr.domain.DiseaseCategory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalInvestigator;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.PermissibleStudySubjectRegistryStatus;
import edu.duke.cabig.c3pr.domain.PlannedNotification;
import edu.duke.cabig.c3pr.domain.RegistryStatusReason;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionAnswerCombination;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.UserBasedRecipient;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.SecurityContextTestUtils;
import edu.duke.cabig.c3pr.utils.StudyCreationHelper;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

/**
 * JUnit Tests for StudyDao.
 *
 * @author Priyatam
 * @author kherm
 * @testType unit
 */

@C3PRUseCases( { CREATE_STUDY, UPDATE_STUDY, SEARCH_STUDY, CREATE_STUDY_INVESTIGATOR,
        ADD_STRATIFICATION, ADD_DISEASE, VERIFY_SUBJECT })
public class StudyDaoTest extends DaoTestCase {

    /** The dao. */
    private StudyDao dao;

    /** The healthcare sitedao. */
    private HealthcareSiteDao healthcareSitedao;

    /** The hcsidao. */
    private HealthcareSiteInvestigatorDao hcsidao;

    /** The investigator dao. */
    private InvestigatorDao investigatorDao;

    /** The disease term dao. */
    private DiseaseTermDao diseaseTermDao;

    /** The disease category dao. */
    private DiseaseCategoryDao diseaseCategoryDao;

    /** The xml utility. */
    private XmlMarshaller xmlUtility;
    
    private RegistryStatusDao registryStatusDao;
    
    private ReasonDao reasonDao;

    StudyCreationHelper studyCreationHelper = new StudyCreationHelper();

    /* (non-Javadoc)
     * @see edu.duke.cabig.c3pr.utils.DaoTestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        dao = (StudyDao) getApplicationContext().getBean("studyDao");
        healthcareSitedao = (HealthcareSiteDao) getApplicationContext()
        	.getBean("healthcareSiteDao");
        hcsidao = (HealthcareSiteInvestigatorDao) getApplicationContext()
        	.getBean("healthcareSiteInvestigatorDao");
        investigatorDao = (InvestigatorDao) getApplicationContext().getBean("investigatorDao");
        diseaseTermDao = (DiseaseTermDao) getApplicationContext().getBean("diseaseTermDao");
        diseaseCategoryDao = (DiseaseCategoryDao) getApplicationContext()
        	.getBean("diseaseCategoryDao");
        registryStatusDao = (RegistryStatusDao) getApplicationContext()
    	.getBean("registryStatusDao");
        reasonDao = (ReasonDao) getApplicationContext()
    	.getBean("reasonDao");
        xmlUtility = new XmlMarshaller((String) getApplicationContext().getBean(
                        "c3pr-study-xml-castorMapping"));
    }


    /*
     * Test the where retired indicator clause for inclusionCriteria.
     */
    /**
     * Test where and where.
     *
     * @throws Exception the exception
     */
    public void testWhereAndWhere() throws Exception {

        Study loadedStudy = dao.getById(1000);
        List list = loadedStudy.getEpochs().get(0).getInclusionEligibilityCriteria();
        assertTrue(list.size() > 0);
    }

    /**
     * Test for loading a Study by Id.
     *
     * @throws Exception the exception
     */
    public void testGetById() throws Exception {
        Study study = dao.getById(1000);
        assertNotNull("Study 1000 not found", study);
        assertEquals("Wrong name", "precis_text", study.getPrecisText());
    }

    /**
     * Test for loading all Studies.
     *
     * @throws Exception the exception
     */
    public void testGetAll() throws Exception {
        List<Study> actual = dao.getAll();
        assertEquals(5, actual.size());
        List<Integer> ids = collectIds(actual);
        assertContains("Wrong study found", ids, 1000);
        assertContains("Wrong study found", ids, 1001);
        assertContains("Wrong study found", ids, 1002);
    }

    /**
     * Test Saving of a Study with all associations present Also tests the Security aspect.
     *
     * @throws Exception the exception
     */
    public void testSaveNewStudyWithSecurity() throws Exception {

        Integer savedId;
        {
            Study study = new LocalStudy();
            study.setPrecisText("New study");
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);
            SecurityContextTestUtils.switchToNobody();

            try {
                dao.save(study);
            }
            catch (Exception ex) {
                // expected
            }
            SecurityContextTestUtils.switchToSuperuser();
            dao.save(study);

            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + savedId, loaded);
            assertEquals("Wrong name", "ShortTitleText", loaded.getShortTitleText());
        }
    }

    /**
     * Adds the stratum group to epoch.
     *
     * @param epoch1 the epoch1
     */
    public void addStratumGroupToEpoch(Epoch epoch1) {
        StratificationCriterion sc = new StratificationCriterion();
        sc.setQuestionText("will I work?");
        StratificationCriterionPermissibleAnswer scpa = new StratificationCriterionPermissibleAnswer();
        scpa.setPermissibleAnswer("lets find out");
        ArrayList scpaList = new ArrayList();
        scpaList.add(scpa);
        sc.getPermissibleAnswers().addAll(scpaList);
        ArrayList scList = new ArrayList();
        scList.add(sc);
        epoch1.setStratificationCriteria(scList);

        StratificationCriterionAnswerCombination scac = new StratificationCriterionAnswerCombination();
        scac.setStratificationCriterion(sc);
        scac.setStratificationCriterionPermissibleAnswer(scpa);
        List<StratificationCriterionAnswerCombination> scacList = new ArrayList<StratificationCriterionAnswerCombination>();
        scacList.add(scac);

        StratumGroup stratumGroup = new StratumGroup();
        stratumGroup.getStratificationCriterionAnswerCombinations().addAll(scacList);
        stratumGroup.setCurrentPosition(1);
        stratumGroup.setStratumGroupNumber(2);
        ArrayList<StratumGroup> sgList = new ArrayList<StratumGroup>();
        sgList.add(stratumGroup);

        epoch1.getStratumGroups().addAll(sgList);
    }

    /**
     * Builds the study.
     *
     * @return the study
     */
    public Study buildStudy() {
        Study study = new LocalStudy();
        study.setPrecisText("Study with randomization");
        study.setShortTitleText("ShortTitleText1");
        study.setLongTitleText("LongTitleText1");
        study.setPhaseCode("PhaseCode1");
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Type");
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        study.setOriginalIndicator(true);
        return study;
    }

    /**
     * Test save study with ans comb.
     *
     * @throws Exception the exception
     */
    public void testSaveStudyWithAnsComb() throws Exception {
        Integer savedId;
        {
            Study study = buildStudy();
            Arm armA = new Arm();
            armA.setName("A");

            Epoch epoch1 = new Epoch();
            armA.setName("Arm name");
            ArrayList<Arm> aList = new ArrayList<Arm>();
            aList.add(armA);
            epoch1.getArms().addAll(aList);
            epoch1.setName("epoch1");

            List<StratificationCriterionPermissibleAnswer> scpa1List = new ArrayList<StratificationCriterionPermissibleAnswer>();
            StratificationCriterion sc1 = new StratificationCriterion();
            sc1.setQuestionText("q1");
            StratificationCriterionPermissibleAnswer scpa;
            for (int i = 0; i < 3; i++) {
                scpa = new StratificationCriterionPermissibleAnswer();
                scpa.setPermissibleAnswer("a1" + i);
                scpa1List.add(scpa);
            }
            sc1.getPermissibleAnswers().addAll(scpa1List);

            List<StratificationCriterionPermissibleAnswer> scpa2List = new ArrayList<StratificationCriterionPermissibleAnswer>();
            StratificationCriterion sc2 = new StratificationCriterion();
            sc2.setQuestionText("q2");
            StratificationCriterionPermissibleAnswer scpa2;
            for (int i = 0; i < 3; i++) {
                scpa2 = new StratificationCriterionPermissibleAnswer();
                scpa2.setPermissibleAnswer("a2" + i);
                scpa2List.add(scpa2);
            }
            sc2.getPermissibleAnswers().addAll(scpa2List);

            List<StratificationCriterion> scList = new ArrayList<StratificationCriterion>();
            scList.add(sc1);
            scList.add(sc2);

            List<StratificationCriterionAnswerCombination> scac1List = new ArrayList<StratificationCriterionAnswerCombination>();

            StratificationCriterionAnswerCombination scac1 = new StratificationCriterionAnswerCombination();
            scac1.setStratificationCriterion(sc1);
            scac1.setStratificationCriterionPermissibleAnswer(sc1.getPermissibleAnswers().get(0));

            StratificationCriterionAnswerCombination scac2 = new StratificationCriterionAnswerCombination();
            scac2.setStratificationCriterion(sc1);
            scac2.setStratificationCriterionPermissibleAnswer(sc1.getPermissibleAnswers().get(1));

            StratificationCriterionAnswerCombination scac3 = new StratificationCriterionAnswerCombination();
            scac3.setStratificationCriterion(sc1);
            scac3.setStratificationCriterionPermissibleAnswer(sc1.getPermissibleAnswers().get(2));

            StratificationCriterionAnswerCombination scac4 = new StratificationCriterionAnswerCombination();
            scac4.setStratificationCriterion(sc2);
            scac4.setStratificationCriterionPermissibleAnswer(sc2.getPermissibleAnswers().get(0));

            StratificationCriterionAnswerCombination scac5 = new StratificationCriterionAnswerCombination();
            scac5.setStratificationCriterion(sc2);
            scac5.setStratificationCriterionPermissibleAnswer(sc2.getPermissibleAnswers().get(1));

            StratificationCriterionAnswerCombination scac6 = new StratificationCriterionAnswerCombination();
            scac6.setStratificationCriterion(sc2);
            scac6.setStratificationCriterionPermissibleAnswer(sc2.getPermissibleAnswers().get(2));

            List<StratificationCriterionAnswerCombination> scac2List = new ArrayList<StratificationCriterionAnswerCombination>();

            scac1List.add(scac1);
            scac1List.add(scac4);

            scac2List.add(scac1);
            scac2List.add(scac5);

            StratumGroup sg1 = new StratumGroup();
            sg1.getStratificationCriterionAnswerCombinations().addAll(cloneScac(scac1List));
            StratumGroup sg2 = new StratumGroup();
            sg2.getStratificationCriterionAnswerCombinations().addAll(cloneScac(scac2List));

            epoch1.getStratumGroups().add(sg1);
            epoch1.getStratumGroups().add(sg2);
            study.addEpoch(epoch1);
            study = dao.merge(study);
            savedId = study.getId();
        }
        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            StratumGroup sg1 = (loaded.getEpochs().get(0)).getStratumGroups().get(0);
            StratumGroup sg2 = (loaded.getEpochs().get(0)).getStratumGroups().get(1);

            assertEquals((loaded.getEpochs().get(0)).getStratumGroups().size(), 2);
            assertNotNull(sg1.getStratificationCriterionAnswerCombinations());
            assertEquals(2, sg1.getStratificationCriterionAnswerCombinations().size());

            assertNotNull(sg2.getStratificationCriterionAnswerCombinations());
            assertEquals(2, sg2.getStratificationCriterionAnswerCombinations().size());
        }
    }

    /**
     * Clone scac.
     *
     * @param scacList the scac list
     *
     * @return the list< stratification criterion answer combination>
     */
    public List<StratificationCriterionAnswerCombination> cloneScac(
                    List<StratificationCriterionAnswerCombination> scacList) {

        List<StratificationCriterionAnswerCombination> clonedList = new ArrayList<StratificationCriterionAnswerCombination>();
        Iterator<StratificationCriterionAnswerCombination> iter = scacList.iterator();
        while (iter.hasNext()) {
            clonedList.add(new StratificationCriterionAnswerCombination(iter.next()));
        }
        return clonedList;
    }

    /**
     * Test Saving of a Study with all Randomization associations present.
     *
     * @throws Exception the exception
     */
    public void testSaveStudyWithRandomizations() throws Exception {
        Integer savedId;
        {
            Study study = buildStudy();
            Arm armA = new Arm();
            armA.setName("A");

            Epoch epoch1 = new Epoch();
            armA.setName("Arm name");
            ArrayList<Arm> aList = new ArrayList<Arm>();
            aList.add(armA);
            epoch1.getArms().addAll(aList);
            epoch1.setName("epoch1");
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
            study.addEpoch(epoch1);
            study = dao.merge(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }
        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            BookRandomization br = (BookRandomization) loaded.getEpochs().get(0).getRandomization();
            int i = br.getBookRandomizationEntry().get(0).getStratumGroup().getCurrentPosition();
            assertEquals(i, 1);
        }
    }

    /**
     * Test combo generator.
     */
    public void testComboGenerator() {

        ArrayList<StratificationCriterion> scList = new ArrayList<StratificationCriterion>();
        Epoch te = new Epoch();
        StratificationCriterion sc = new StratificationCriterion();
        ArrayList<StratificationCriterionPermissibleAnswer> scpaList = new ArrayList<StratificationCriterionPermissibleAnswer>();
        StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
        scpa1.setPermissibleAnswer("<65");
        scpaList.add(scpa1);
        StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
        scpa2.setPermissibleAnswer("<45");
        scpaList.add(scpa2);
        StratificationCriterionPermissibleAnswer scpa3 = new StratificationCriterionPermissibleAnswer();
        scpa3.setPermissibleAnswer(">18");
        scpaList.add(scpa3);

        sc.setQuestionText("age?");
        sc.getPermissibleAnswers().addAll(scpaList);
        scList.add(sc);

        ArrayList<StratificationCriterionPermissibleAnswer> scpa2List = new ArrayList<StratificationCriterionPermissibleAnswer>();
        StratificationCriterion sc2 = new StratificationCriterion();
        StratificationCriterionPermissibleAnswer scpa4 = new StratificationCriterionPermissibleAnswer();
        scpa4.setPermissibleAnswer("Male");
        scpa2List.add(scpa4);
        StratificationCriterionPermissibleAnswer scpa5 = new StratificationCriterionPermissibleAnswer();
        scpa5.setPermissibleAnswer("Female");
        scpa2List.add(scpa5);

        sc2.setQuestionText("gender?");
        sc2.getPermissibleAnswers().addAll(scpa2List);
        scList.add(sc2);

        te.setStratificationCriteria(scList);
        // At this point we have a te with 2 sc each having a set of scpa's.
        int scSize = te.getStratificationCriteria().size();
        StratificationCriterionPermissibleAnswer doubleArr[][] = new StratificationCriterionPermissibleAnswer[scSize][];
        List<StratificationCriterionPermissibleAnswer> tempList;
        for (int i = 0; i < scSize; i++) {
            tempList = te.getStratificationCriteria().get(i).getPermissibleAnswers();
            doubleArr[i] = new StratificationCriterionPermissibleAnswer[tempList.size()];
            for (int j = 0; j < tempList.size(); j++) {
                doubleArr[i][j] = tempList.get(j);
            }
        }
        // at this point we have a 2d array of answers.
        ArrayList<StratumGroup> sgList = comboGenerator(te, doubleArr, 0,
                        new ArrayList<StratumGroup>(),
                        new ArrayList<StratificationCriterionAnswerCombination>());
        System.out.println("");
    }

    /**
     * Combo generator.
     *
     * @param te the te
     * @param myArr the my arr
     * @param intRecurseLevel the int recurse level
     * @param sgList the sg list
     * @param strLine the str line
     *
     * @return the array list< stratum group>
     */
    public ArrayList<StratumGroup> comboGenerator(Epoch te,
                    StratificationCriterionPermissibleAnswer[][] myArr, int intRecurseLevel,
                    ArrayList<StratumGroup> sgList,
                    ArrayList<StratificationCriterionAnswerCombination> strLine) {
        StratificationCriterionAnswerCombination strPositionVal = new StratificationCriterionAnswerCombination();
        ArrayList<StratificationCriterionAnswerCombination> strMyLine = new ArrayList<StratificationCriterionAnswerCombination>();

        for (int i = 0; i < myArr[intRecurseLevel].length; i++) {
            strPositionVal.setStratificationCriterionPermissibleAnswer(myArr[intRecurseLevel][i]);
            strPositionVal.setStratificationCriterion(te.getStratificationCriteria().get(
                            intRecurseLevel));
            strMyLine = new ArrayList<StratificationCriterionAnswerCombination>();
            if (!strLine.isEmpty()) {
                strMyLine.addAll(strLine);
            }
            strMyLine.add(strPositionVal);

            if (intRecurseLevel < myArr.length - 1) {
                sgList = comboGenerator(te, myArr, intRecurseLevel + 1, sgList, strMyLine);
            }
            else {
                StratumGroup sg = new StratumGroup();
                sg.getStratificationCriterionAnswerCombinations().addAll(strMyLine);
                sgList.add(sg);
            }
        }
        return sgList;
    }

    /*
     * //working version with simple strings public String comboGenerator(String[][] myArr, int
     * intRecurseLevel, String strResult, String strLine){ String strPositionVal = ""; String
     * strMyLine = ""; for(int i= 0; i< myArr[intRecurseLevel].length ; i++){ strPositionVal =
     * myArr[intRecurseLevel][i]; if(strLine.length() > 0){ strMyLine = strLine +"/"+
     * strPositionVal; } else { strMyLine = strPositionVal; }
     *
     * if(intRecurseLevel < myArr.length-1){ strResult = comboGenerator(myArr, intRecurseLevel + 1,
     * strResult, strMyLine); } else { strResult = strResult + "\n" + strMyLine; } } return
     * strResult; }
     */

    /**
     * Test Saving of a Study with all associations present.
     *
     * @throws Exception the exception
     */
    public void testSaveNewStudyWithAssociations() throws Exception {
        Integer savedId;
        {
            Study study = new LocalStudy();
            study.setPrecisText("New study");
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);

            createDefaultStudyWithDesign(study);
            dao.save(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + savedId, loaded);
            assertEquals("Wrong name", "New study", loaded.getPrecisText());
        }
    }

    /**
     * Test save study with notifications.
     *
     * @throws Exception the exception
     */
    public void testSaveStudyWithNotifications() throws Exception {
        Integer savedId;
        {
            Study study = new LocalStudy();
            study.setPrecisText("Study with Notifications");
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(100);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);

            createDefaultStudyWithDesign(study);

            // notification specific code.
            UserBasedRecipient ebr = new UserBasedRecipient();
            //            ebr.setEmailAddress("vinay.gangoli@semanticbits.com");
            RoleBasedRecipient rbr = new RoleBasedRecipient();
            rbr.setRole("admin");

            PlannedNotification notification = new PlannedNotification();
            notification.setStudyThreshold(90);
            notification.getUserBasedRecipient().add(ebr);
            notification.getRoleBasedRecipient().add(rbr);
            study.getPlannedNotifications().add(notification);
            dao.save(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }
        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + savedId, loaded);
            assertEquals("Wrong Threshold", 90, loaded.getPlannedNotifications().get(0)
                            .getStudyThreshold().intValue());
            assertEquals("Wrong role", "admin", loaded.getPlannedNotifications().get(0)
                            .getRoleBasedRecipient().get(0).getRole());
            /*assertEquals("Wrong emailAddress", "vinay.gangoli@semanticbits.com", loaded
                            .getNotifications().get(0).getEmailBasedRecipient().get(0)
                            .getEmailAddress());*/
        }
    }

    /**
     * Test hibernate bug.
     *
     * @throws Exception the exception
     */
    public void testHibernateBug() throws Exception {
        Integer savedId;
        {
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
            healthcaresite.setCtepCode("Nci duke");

            healthcareSitedao.save(healthcaresite);
            System.out.println("hc site id ************" + healthcaresite.getId());
            HealthcareSite hcsiteloaded = healthcareSitedao.getById(healthcaresite.getId());

            // Investigators
            Investigator inv = new LocalInvestigator();
            inv.setFirstName("Investigator first name");
            inv.setAssignedIdentifier("x"+Math.ceil((Math.random()*1000)));
            investigatorDao.save(inv);
            Investigator invloaded = investigatorDao.getById(inv.getId());

            // HCSI
            HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();
            invloaded.addHealthcareSiteInvestigator(hcsi);
            hcsiteloaded.addHealthcareSiteInvestigator(hcsi);
            hcsidao.save(hcsi);
            System.out.println("hcsi id ************" + hcsi.getId());
            HealthcareSiteInvestigator hcsiloaded = hcsidao.getById(hcsi.getId());

            Study study = new LocalStudy();
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);

            study.addEpoch(studyCreationHelper.createEpoch("Screening"));
            study.addEpoch(studyCreationHelper.createEpochWithArms("Treatment", "Arm A", "Arm B", "Arm C"));
            study.addEpoch(studyCreationHelper.createEpoch("Follow up"));

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
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);

            study.addStudySite(studySite);

            // Study Investigator
            StudyInvestigator studyInvestigator = new StudyInvestigator();
            studyInvestigator.setRoleCode("role");
            studyInvestigator.setStatusCode(InvestigatorStatusCodeEnum.AC);

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
            assertEquals("Wrong name", "ShortTitleText", loaded.getShortTitleText());
        }
    }

    /**
     * Test for retrieving all study sites associated with this Study.
     *
     * @throws Exception the exception
     */

    public void testGetStudySites() throws Exception {
        Study study = dao.getById(1000);
        List<StudySite> sites = study.getStudySites();
        assertEquals("Wrong number of study sites", 1, sites.size());
        List<Integer> ids = collectIds(sites);

        assertContains("Missing expected study site", ids, 1000);
    }

    /**
     * Test for retrieving all Epochs associated with this Study.
     *
     * @throws Exception the exception
     */
    public void testGetEpochs() throws Exception {
        Study study = dao.getById(1000);
        List<Epoch> epochs = study.getEpochs();
        assertEquals("Wrong number of Epochs", 6, epochs.size());
        List<Integer> ids = collectIds(epochs);

        assertContains("Missing expected Epoch", ids, 1000);
        assertContains("Missing expected Epoch", ids, 1001);
    }

    /**
     * Test get epochs again.
     *
     * @throws Exception the exception
     */
    public void testGetEpochsAgain() throws Exception {

        Study study = dao.getById(1000);

        Epoch te = new Epoch();
        te.setName("test");
        study.addEpoch(te);

        dao.save(study);
        interruptSession();
        study = dao.getById(1000);

        List<Epoch> epochs = study.getEpochs();
        assertEquals("Wrong number of Epochs", 7, epochs.size());
        List<Integer> ids = collectIds(epochs);

        assertContains("Missing expected Epoch", ids, 1000);
        assertContains("Missing expected Epoch", ids, 1001);
    }

    /**
     * Test for retrieving all Arms associated with this Studies' epochs.
     *
     * @throws Exception the exception
     */

    /**
     * Test for Study Paticipant Assignments for a given Study
     *
     * @throws Exception
     */
    public void testGetStudySubjectsForStudy() throws Exception {
        List<StudySubject> spa = dao.getStudySubjectsForStudy(1000);
        assertEquals(2, spa.size());
        List<Integer> ids = collectIds(spa);
        assertContains("Wrong study found", ids, 1000);
    }

    /**
     * Test for searching Studies without wildcards.
     *
     * @throws Exception      */
    public void testSearchStudySimple() {
        Study studySearchCriteria = new LocalStudy(true);
        studySearchCriteria.setShortTitleText("short_title_text");
        List<Study> results = dao.searchByExample(studySearchCriteria);
        assertEquals("Wrong number of Studies", 3, results.size());
        assertEquals("short_title_text", results.get(0).getShortTitleText());
        assertEquals("short_title_text", results.get(1).getShortTitleText());
    }

    /**
     * Make sure DAO returns unique results.
     */
    public void testIdentifierUniqueResults() {
        Study studySearchCriteria = new LocalStudy(true);
        studySearchCriteria.setShortTitleText("short_title_text2");
        List<Study> results = dao.searchByExample(studySearchCriteria);
        assertEquals("Wrong number of Studies", 1, results.size());
    }

    /**
     * Creates the default study with design.
     *
     * @param study the study
     *
     * @return the study
     */
    private Study createDefaultStudyWithDesign(Study study) {
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
            // Investigators
            Investigator invSave = new LocalInvestigator();
            invSave.setFirstName("Investigator first name");
            invSave.setAssignedIdentifier("x"+Math.ceil((Math.random()*1000)));
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
            healthcaresite.setCtepCode("Nci duke");
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

            disId=disCatSaved.getId();
            invId=invSave.getId();
            hcsId=healthcaresite.getId();
            hcsiId=hcsiSave.getId();
            term1Id=term1.getId();
            term2Id=term2.getId();
            System.out.println("hcsi id ************" + hcsiId);
        }

        DiseaseCategory disCat=diseaseCategoryDao.getById(disId);
        Investigator inv=investigatorDao.getById(invId);

        study.addEpoch(studyCreationHelper.createEpoch("Screening"));
        study.addEpoch(studyCreationHelper.createEpochWithArms("Treatment", "Arm A", "Arm B", "Arm C"));
        study.addEpoch(studyCreationHelper.createEpoch("Follow up"));

        // Study Site
        StudySite studySite = new StudySite();
        study.addStudySite(studySite);
        studySite.setHealthcareSite(healthcareSitedao.getById(hcsId)); //
      //TODO fix it later
//        studySite.setStartDate(new Date());
        studySite.setIrbApprovalDate(new Date());
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);

        // Study Investigator
        StudyInvestigator studyInvestigator = studySite.getStudyInvestigators().get(
                        studySite.getStudyInvestigators().size());
        studyInvestigator.setRoleCode("role");
        studyInvestigator.setStartDate(new Date());

        HealthcareSiteInvestigator hcsi=hcsidao.getById(hcsiId);
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

        StudyDisease studyDisease = new StudyDisease();
        studyDisease.setDiseaseTerm(diseaseTermDao.getById(term1Id));
        studyDisease.setDiseaseTerm(diseaseTermDao.getById(term2Id));

        study.addStudyDisease(studyDisease);

        return study;
    }

    /**
     * Test save new study with epoch and arms.
     *
     * @throws Exception the exception
     */
    public void testSaveNewStudyWithEpochAndArms() throws Exception {
        Integer savedId;
        {
            Study study = new LocalStudy();
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);
            study.addEpoch(studyCreationHelper.createEpochWithArms("TestTreatmentEpoch1", "Arm A", "Arm B",
                            "Arm C"));
            study.getEpochs().get(0).setEpochOrder(0);
            study.addEpoch(studyCreationHelper.createEpoch("TestTreatmentEpoch2"));
            study.getEpochs().get(1).setEpochOrder(1);
            dao.save(study);

            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + 1000, loaded);
            Epoch newEpoch1 = loaded.getEpochs().get(0);
            Epoch newEpoch2 = loaded.getEpochs().get(1);
            assertEquals("Wrong number of Arms are retreived", 3, newEpoch1.getArms().size());
            assertEquals("Wrong name of epoch retrieved", "TestTreatmentEpoch2", newEpoch2
                            .getName());
        }
    }

    /**
     * Test save new study with epochs.
     *
     * @throws Exception the exception
     */
    public void testSaveNewStudyWithEpochs() throws Exception {
        Integer savedId;
        {

            Study study = new LocalStudy();
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);

            study.addEpoch(studyCreationHelper.createEpoch("TestNonTreatmentEpoch"));
            study.getEpochs().get(0).setEpochOrder(2);
            study.getEpochs().get(0).setType(EpochType.RESERVING);

            Epoch newEpoch = new Epoch();
            newEpoch.setName("Test Non Treatment Epoch");
            newEpoch.setAccrualCeiling(10);
            newEpoch.setType(EpochType.RESERVING);
            newEpoch.setEnrollmentIndicator(Boolean.FALSE);
            newEpoch.setEpochOrder(1);
            study.addEpoch(newEpoch);

            dao.save(study);

            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + savedId, loaded);
            List<Epoch> totalEpochs = loaded.getEpochs();
            assertEquals("Wrong number of Epochs are retreived", 2, totalEpochs.size());
            for (Epoch newEpoch : totalEpochs) {
                assertEquals("Wrong Treatment Indicator: ", EpochType.RESERVING, newEpoch.getType());
            }
            assertEquals("Wrong epoch name", "Test Non Treatment Epoch", (totalEpochs.get(0))
                            .getName());
            assertEquals("Wrong enrollment indicator", Boolean.FALSE, (totalEpochs.get(0))
                            .getEnrollmentIndicator());
//            assertEquals("Wrong reservation indicator", Boolean.TRUE, (totalEpochs.get(0))
//                            .getReservationIndicator());
            assertEquals("Wrong accrual ceiling", 10, (totalEpochs.get(0)).getAccrualCeiling()
                            .intValue());

        }
    }

    /**
     * Test save new study with treatment epochs and arms.
     *
     * @throws Exception the exception
     */
    public void testSaveNewStudyWithTreatmentEpochsAndArms() throws Exception {
        Integer savedId;
        {
            Study study = new LocalStudy();
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);

            study.addEpoch(studyCreationHelper.createEpochWithArms("TestTreatmentEpoch1", "Arm A", "Arm B",
                            "Arm C"));
            study.getEpochs().get(0).setEpochOrder(0);
            Epoch epoch = new Epoch();
            epoch.setName("TestTreatmentEpoch2");
            Arm arm = new Arm();
            arm.setName("Test Arm");
            epoch.addArm(arm);
            epoch.setEpochOrder(1);
            study.addEpoch(epoch);

            dao.save(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + 1000, loaded);
            List<Epoch> newTreatmentEpochs = loaded.getEpochs();
            assertEquals("Wrong number of Treatment Epochs are retreived", 2, newTreatmentEpochs
                            .size());
            assertEquals("Expected to get 3 Arms: ", 3, newTreatmentEpochs.get(0).getArms().size());
            assertEquals("Expected to get 1 Arm: ", 1, newTreatmentEpochs.get(1).getArms().size());
            assertEquals("Expected to get Arm with name, Arm A: ", "Arm A", newTreatmentEpochs.get(
                            0).getArms().get(0).getName());
            assertEquals("Expected to get Arm with name, Test Arm: ", "Test Arm",
                            newTreatmentEpochs.get(1).getArms().get(0).getName());

        }
    }

    /**
     * Test for searching Studies using wildcards.
     *
     * @throws Exception the exception
     */
    public void testSearchStudyByWildCards() throws Exception {
        Study studySearchCriteria = new LocalStudy(true);
        studySearchCriteria.setShortTitleText("ti%e");
        List<Study> results = dao.searchByExample(studySearchCriteria, true);
        assertEquals("Wrong number of Studies", 4, results.size());
    }

    /**
     * Test for searching Studies using wildcards for Coppa.
     *
     * @throws Exception the exception
     
    public void testSearchStudyByWildCardsForCoppa() throws Exception {
        Study studySearchCriteria = new LocalStudy();
        //studySearchCriteria.setShortTitleText("Evaluation");
        OrganizationAssignedIdentifier organizationAssignedIdentifier = new OrganizationAssignedIdentifier();
        organizationAssignedIdentifier.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
        organizationAssignedIdentifier.setValue("NCI-2009-00004");
        studySearchCriteria.getIdentifiers().add(organizationAssignedIdentifier);
        
        List<Study> results = dao.searchByExample(studySearchCriteria, true);
        assertEquals("Wrong number of Studies", 1, results.size());
    }*/
    
    /**
     * Test for retrieving all study funding sponsors associated with this Study.
     *
     * @throws Exception the exception
     */
    public void testGetStudyFundingSponsors() throws Exception {
        Study study = dao.getById(1000);
        List<StudyFundingSponsor> sponsors = study.getStudyFundingSponsors();
        assertEquals("Wrong number of study funding sponsors", 1, sponsors.size());
        System.out.println("Study funding sponsor is: "
                        + sponsors.get(0).getHealthcareSite().getName());
        List<Integer> ids = collectIds(sponsors);

        assertContains("Missing expected study funding sponsor", ids, 1001);
    }

    /**
     * Test for retrieving all study coordinating centers associated with this Study.
     *
     * @throws Exception the exception
     */
    public void testGetStudyCoordinatingCenters() throws Exception {
        Study study = dao.getById(1000);
        List<StudyCoordinatingCenter> centers = study.getStudyCoordinatingCenters();
        assertEquals("Wrong number of study coordinating centers", 1, centers.size());
        List<Integer> ids = collectIds(centers);

        assertContains("Missing expected study funding sponsor", ids, 1004);
    }

    /**
     * Test save new study with funding sponsor.
     *
     * @throws Exception the exception
     */
    public void testSaveNewStudyWithFundingSponsor() throws Exception {
        Integer savedId;
        {
            HealthcareSite sponsor = healthcareSitedao.getById(1001);
            HealthcareSite site = healthcareSitedao.getById(1000);

            Study study = new LocalStudy();
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);

            // Study Site
            StudySite studySite = new StudySite();
            studySite.setHealthcareSite(site);
          //TODO fix it later
//            studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);

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
            assertEquals("Wrong name", "ShortTitleText", loaded.getShortTitleText());
            assertEquals("Wrong study funding sponsor", "National Cancer Institute", loaded
                            .getStudyFundingSponsors().get(0).getHealthcareSite().getName());
        }
    }

    /**
     * Test save new study with coordinating center.
     *
     * @throws Exception the exception
     */
    public void testSaveNewStudyWithCoordinatingCenter() throws Exception {
        Integer savedId;
        {
            HealthcareSite sponsor = healthcareSitedao.getById(1001);
            HealthcareSite site = healthcareSitedao.getById(1000);
            HealthcareSite center = healthcareSitedao.getById(1002);

            Study study = new LocalStudy();
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);

            // Study Site
            StudySite studySite = new StudySite();
            studySite.setHealthcareSite(site);
          //TODO fix it later
//            studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);

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
            assertEquals("Wrong name", "ShortTitleText", loaded.getShortTitleText());
            assertEquals("Wrong study funding sponsor", "National Cancer Institute", loaded
                            .getStudyFundingSponsors().get(0).getHealthcareSite().getName());
            assertEquals("Wrong study coordinating center", "CALGB", loaded
                            .getStudyCoordinatingCenters().get(0).getHealthcareSite().getName());
        }
    }

    /**
     * Test failure save two identifiers with same org and same type in study.
     *
     * @throws Exception the exception
     */
   /* public void testFailureSaveTwoIdentifiersWithSameOrgAndSameTypeInStudy() throws Exception {
        Study loadedStudy = dao.getById(1000);
        OrganizationAssignedIdentifier identifier1 = new OrganizationAssignedIdentifier();
        OrganizationAssignedIdentifier identifier2 = new OrganizationAssignedIdentifier();
        SystemAssignedIdentifier sysId = new SystemAssignedIdentifier();
        sysId.setSystemName("sys_name");
        sysId.setValue("sys_val");
        sysId.setType("sys_type");
        HealthcareSite loadedSite = healthcareSitedao.getById(1000);

        identifier1.setHealthcareSite(loadedSite);
        identifier1.setType("MRN");
        identifier1.setValue("123");

        identifier2.setHealthcareSite(loadedSite);
        identifier2.setType("MRN");
        identifier2.setValue("abc");

        loadedStudy.addIdentifier(identifier1);
        loadedStudy.addIdentifier(identifier2);
        try {
            dao.save(loadedStudy);
            fail("Save should fail");
        }
        catch (RuntimeException e) {
        }

    }*/

    /**
     * Test failure save two study organizations with same org and same type.
     *
     * @throws Exception the exception
     */
    public void testFailureSaveTwoStudyOrganizationsWithSameOrgAndSameType() throws Exception {
        Study loadedStudy = dao.getById(1000);
        HealthcareSite site = healthcareSitedao.getById(1000);
        StudySite studySite = new StudySite();
        studySite.setHealthcareSite(site);
        loadedStudy.addStudyOrganization(studySite);

        try {
            dao.save(loadedStudy);
            //TODO to handle uniqueness constraints on study site, type and study
 //           fail("Save should fail");
        }
        catch (RuntimeException e) {
        	System.out.println(e.getMessage());
        }

    }

    /**
     * Test failure two epochs with same name in study.
     *
     * @throws Exception the exception
     */
    public void testFailureTwoEpochsWithSameNameInStudy() throws Exception {
        Study loadedStudy = dao.getById(1000);
        Epoch epoch = new Epoch();
        epoch.setName("Treatment1003");
        try {
            loadedStudy.addEpoch(epoch);
            fail("Save should fail");
        }
        catch (RuntimeException e) {

        }

    }

    /**
     * Test failure save two study funding sponsor identifiers with same value.
     *
     * @throws Exception the exception
     */
    public void testFailureSaveTwoStudyFundingSponsorIdentifiersWithSameValue() throws Exception {
        Study loadedStudy1 = dao.getById(1002);
        Study loadedStudy2 = dao.getById(1001);
        HealthcareSite site = healthcareSitedao.getById(1000);
        OrganizationAssignedIdentifier orgId1 = new OrganizationAssignedIdentifier();
        orgId1.setHealthcareSite(site);
        orgId1.setType(OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR);
        orgId1.setValue("abc");

        OrganizationAssignedIdentifier orgId2 = new OrganizationAssignedIdentifier();
        orgId2.setHealthcareSite(site);
        orgId2.setType(OrganizationIdentifierTypeEnum.STUDY_FUNDING_SPONSOR);
        orgId2.setValue("abcd");
        loadedStudy1.addIdentifier(orgId1);

        loadedStudy1.getSystemAssignedIdentifiers();
        dao.save(loadedStudy1);

        loadedStudy2.addIdentifier(orgId2);
        dao.save(loadedStudy2);

    }

    /**
     * Test epoch order.
     */
    public void testEpochOrder() {
        Study study = dao.getById(1000);
        assertEquals("Wrong Epoch order", "epoch 1", study.getEpochs().get(0).getName());
        assertEquals("Wrong Epoch order", "NonTreatment1004", study.getEpochs().get(1).getName());
        assertEquals("Wrong Epoch order", "Treatment1003", study.getEpochs().get(2).getName());
        assertEquals("Wrong Epoch order", "NonTreatment1005", study.getEpochs().get(3).getName());
        assertEquals("Wrong Epoch order", "Test epoch", study.getEpochs().get(4).getName());
        assertEquals("Wrong Epoch order", "Treatment1002", study.getEpochs().get(5).getName());
    }

    /**
     * Test companion study.
     */
    public void testCompanionStudy() {

        HealthcareSite sponsor = healthcareSitedao.getById(1001);
        HealthcareSite site = healthcareSitedao.getById(1000);
        HealthcareSite center = healthcareSitedao.getById(1002);

        Study study = new LocalStudy();
        study.setShortTitleText("ShortTitleText");
        study.setLongTitleText("LongTitleText");
        study.setPhaseCode("PhaseCode");
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Type");
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        study.setOriginalIndicator(true);
        // Study Site
        StudySite studySite = new StudySite();
        studySite.setHealthcareSite(site);
      //TODO fix it later
//        studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);

        study.addStudySite(studySite);

        // Study funding sponsor
        StudyFundingSponsor fundingSponsor = new StudyFundingSponsor();
        fundingSponsor.setHealthcareSite(sponsor);
        study.addStudyOrganization(fundingSponsor);

        // Study coordinating center
        StudyCoordinatingCenter coCenter = new StudyCoordinatingCenter();
        coCenter.setHealthcareSite(center);
        study.addStudyOrganization(coCenter);

        CompanionStudyAssociation csa = new CompanionStudyAssociation();
        csa.setCompanionStudy(dao.getById(1001));
        csa.setParentStudyVersion(study.getStudyVersion());
        csa.setMandatoryIndicator(true);
        study.getStudyVersion().getCompanionStudyAssociations().add(csa);

        dao.save(study);
        int savedId = study.getId();
        assertNotNull("The saved study didn't get an id", savedId);

        interruptSession();

        assertNotNull("Companion Association exists", dao.getById(savedId));
        assertNotNull("Companion Association has Parent Study", dao.getById(savedId).getStudyVersion().getCompanionStudyAssociations());
    }

    /**
     * Test get parent study from companion study.
     */
    public void testGetParentStudyFromCompanionStudy() {
        Study companionStudy = dao.getById(1002);
        Study parentStudy = companionStudy.getParentStudyAssociations().get(0).getParentStudy();
        assertNotNull("parent study is not null ", parentStudy);

        int id = parentStudy.getId();
        assertEquals(1001, id);

    }

    /**
     * Test delete companion assoc from parent study.
     */
    public void testDeleteCompanionAssocFromParentStudy() {
        Study parentStudy = dao.getById(1001);
        dao.initialize(parentStudy);
        interruptSession();
        List<CompanionStudyAssociation> assocList = parentStudy.getStudyVersion().getCompanionStudyAssociations();
        assocList.remove(0);
        dao.merge(parentStudy);
        assertNotNull("parent study is not null ", parentStudy);
        assertEquals(parentStudy.getStudyVersion().getCompanionStudyAssociations().size(), 0);

    }

    /**
     * Test get by identifiers single study.
     */
    public void testGetByIdentifiersSingleStudy(){
        //List<Identifier> identifiers=new ArrayList<Identifier>();
        //Study study=dao.getById(1000);
        //identifiers.addAll(study.getIdentifiers());
        //identifiers=dao.getById(1000).getIdentifiers();
        List<Study> studies=dao.getByIdentifiers(dao.getById(1000).getIdentifiers());
        assertEquals("Wrong size of list", 1, studies.size());
        assertEquals("Wrong study", new Integer(1000), studies.get(0).getId());
    }

    public void testGetStudyBySystemAssignedIdentifier(){
        SystemAssignedIdentifier systemAssignedIdentifier=new SystemAssignedIdentifier();
        systemAssignedIdentifier.setSystemName("nci");
        systemAssignedIdentifier.setValue("nci");
        systemAssignedIdentifier.setType("local");
        List<Study> studies=dao.searchBySysIdentifier(systemAssignedIdentifier);
        assertEquals("Wrong size of list",1, studies.size());
    }

    public void testGetStudyByOrganizationAssignedIdentifier(){
        OrganizationAssignedIdentifier organizationAssignedId=new OrganizationAssignedIdentifier();
        organizationAssignedId.setHealthcareSite(new LocalHealthcareSite());
        organizationAssignedId.getHealthcareSite().setCtepCode("Duke");
        organizationAssignedId.setValue("nci1");
        organizationAssignedId.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
        List<Study> studies=dao.searchByOrgIdentifier(organizationAssignedId);
        assertEquals("Wrong size of list",1, studies.size());
    }


    /**
     * Test get by identifiers multiple study.
     */
    public void testGetByIdentifiersMultipleStudy(){
        List<Identifier> identifiers=new ArrayList<Identifier>();
        for(Study study: dao.getAll())
            identifiers.addAll(study.getIdentifiers());
        List<Study> studies=dao.getByIdentifiers(identifiers);
        assertEquals("Wrong size of list",4, studies.size());
    }


    /**
     * Test get by sub name with extra conditions for primary identifier.
     * used by study autocompleters
     */
    public void testGetBySubNameWithExtraConditionsForPrimaryIdentifier(){
    	String[] subName = new String[]{"nci1"};

    	List<Study> studies=dao.getStudiesBySubnamesWithExtraConditionsForPrimaryIdentifier(subName);
    	assertEquals("Wrong number is studies", 1, studies.size());
    }

    public void testGetBySubNameMultipleStudies(){
    	String[] subName=new String[]{"short_title_text"};
    	List<Study> studies=dao.getBySubnames(subName);
    	interruptSession();
    	assertEquals("Wrong number is studies", 4, studies.size());
    }

    public void testGetBySubNameSingleStudy(){
    	String[] subName=new String[]{"short_title_text2"};
    	List<Study> studies=dao.getBySubnames(subName);
    	interruptSession();
    	assertEquals("Wrong number is studies", 1, studies.size());
    }

    public void testGetBySubNameNoStudy(){
    	String[] subName=new String[]{"TestNotAvailable"};
    	List<Study> studies=dao.getBySubnames(subName);
    	interruptSession();
    	assertEquals("Wrong number is studies", 0, studies.size());
    }

    /**
     * Test search by example using study status.
     */
    public void testSearchByExampleUsingStudyStatus(){
        Study exampleStudy = new LocalStudy();
        List<Study> studySearchResults = dao.searchByStatus(exampleStudy, "Open", true);
        assertEquals("Wrong Number of studies retrieved",5,studySearchResults.size());

    }

    public void testGetCoordinatingCenterIdentifiersWithValue(){
    	List<OrganizationAssignedIdentifier> orgList=dao.getCoordinatingCenterIdentifiersWithValue("nci1", healthcareSitedao.getById(1000));
    	assertEquals("Wrong number of identifiers",1, orgList.size());
    	orgList=dao.getCoordinatingCenterIdentifiersWithValue("nci2", healthcareSitedao.getById(1000));
    	assertEquals("Wrong number of identifiers",1, orgList.size());
    }

    public void testGetCoordinatingCenterIdentifiersWithValueNoIdentifier(){
    	List<OrganizationAssignedIdentifier> orgList=dao.getCoordinatingCenterIdentifiersWithValue("nci", healthcareSitedao.getById(1000));
    	assertEquals("Wrong number of identifiers",0, orgList.size());
    }

    public void testGetFundingSponsorIdentifiersWithValue(){
    	List<OrganizationAssignedIdentifier> orgList=dao.getFundingSponsorIdentifiersWithValue("pai1", healthcareSitedao.getById(1000));
    	assertEquals("Wrong number of identifiers",1, orgList.size());
    	orgList=dao.getFundingSponsorIdentifiersWithValue("pai2", healthcareSitedao.getById(1000));
    	assertEquals("Wrong number of identifiers",1, orgList.size());
    }

    public void testGetFundingSponsorIdentifiersWithValueNoIdentifier(){
    	List<OrganizationAssignedIdentifier> orgList=dao.getFundingSponsorIdentifiersWithValue("pai", healthcareSitedao.getById(1000));
    	assertEquals("Wrong number of identifiers",0, orgList.size());
    }

    public void testCountAcrrualsByDate1(){
    	Study study=dao.getById(1000);
    	Calendar startCalendar=Calendar.getInstance();
    	startCalendar.set(1999, Calendar.DECEMBER, 31);
    	Calendar endCalendar=Calendar.getInstance();
    	endCalendar.set(2000, Calendar.JANUARY, 2);
    	int accruals=dao.countAcrrualsByDate(study, startCalendar.getTime(), endCalendar.getTime());

    	assertEquals("Wrong acrual count", 1, accruals);
    }

    public void testCountAcrrualsByDate2(){
    	Study study=dao.getById(1000);
    	Calendar startCalendar=Calendar.getInstance();
    	startCalendar.set(1999, Calendar.DECEMBER, 31);
    	Calendar endCalendar=Calendar.getInstance();
    	endCalendar.set(2003, Calendar.JANUARY, 2);
    	int accruals=dao.countAcrrualsByDate(study, startCalendar.getTime(), endCalendar.getTime());
    	assertEquals("Wrong acrual count", 2, accruals);
    }

    public void testGetByDiseaseTermId(){
    	List<StudyDisease> stuList=dao.getByDiseaseTermId(1000);
    	assertEquals("Wrong number of study diseases",1, stuList.size());
    }

    public void testRefresh(){
    	Study study=dao.getById(1001);
    	dao.refresh(study);
    }

    public void testSearchByIdentifier(){
    	List<Study> studies=dao.searchByIdentifier(1000);
    	assertEquals("Wronf number of studies", 1, studies.size());
    }

    public void testSaveOneOrganizationIdentifiersAndOneSystemAssignedIdentifier() throws Exception {
        Study loadedStudy = dao.getById(1000);

        OrganizationAssignedIdentifier identifier1 = new OrganizationAssignedIdentifier();
        HealthcareSite loadedSite = healthcareSitedao.getById(1000);
        identifier1.setHealthcareSite(loadedSite);
        identifier1.setType(OrganizationIdentifierTypeEnum.COOPERATIVE_GROUP_IDENTIFIER);
        identifier1.setValue("123");

        SystemAssignedIdentifier sysId = new SystemAssignedIdentifier();
        sysId.setSystemName("sys_name");
        sysId.setValue("123");
        sysId.setType("Coordinating Center Assigned Identifier");

        loadedStudy.addIdentifier(identifier1);
        loadedStudy.addIdentifier(sysId);
        dao.save(loadedStudy);
        Study reloadedStudy = dao.getById(1000);
        assertEquals("Wrong number of identifiers for study",5,reloadedStudy.getIdentifiers().size());
    }

    public void testGetStudySubjectsForCompanionStudy() throws Exception {
    	int studyId = 1000 ;
    	List<StudySubject> list = dao.getStudySubjectsForStudy(studyId);
    	assertEquals("Wrong number of records",2,list.size());
    }

    public void testSaveStudyWithStudyVersionAndStudySite() throws Exception {
    	Study study = new LocalStudy();
    	study.setPhaseCode("PHASE I");
    	study.setShortTitleText("short title");
    	study.setType("type");
    	study.getStudyVersion().setPrecisText("");
    	study.setOriginalIndicator(true);
    	StudySite studySite = new StudySite();
    	HealthcareSite healthcareSite = healthcareSitedao.getById(1000);
    	studySite.setHealthcareSite(healthcareSite);
    	studySite.setStudy(study);
    	study.addStudySite(studySite);
    	dao.save(study);
    	interruptSession();
    	assertNotNull("id is null",study.getId());
    	assertEquals("wrong number of study sites",1,study.getStudySites().size());

    }

    public void testDifferentCoordinatngCenterAndStudySite() throws Exception {

        Integer savedId;
        {
            Study study = new LocalStudy();
            study.setPrecisText("New study");
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);
            SecurityContextTestUtils.switchToNobody();


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
            healthcaresite.setCtepCode("Nci duke");
            healthcareSitedao.save(healthcaresite);

            HealthcareSite healthcaresite1 = new LocalHealthcareSite();
            Address address1 = new Address();
            address1.setCity("Reston");
            address1.setCountryCode("USA");
            address1.setPostalCode("20192");
            address1.setStateCode("VA");
            address1.setStreetAddress("12359 Sunrise Valley Dr");
            healthcaresite1.setAddress(address);
            healthcaresite1.setName("duke healthcare");
            healthcaresite1.setDescriptionText("duke healthcare1");
            healthcaresite1.setCtepCode("Nci duke1");
            healthcareSitedao.save(healthcaresite1);

            StudyCoordinatingCenter coordinatingCenter = new StudyCoordinatingCenter();
            coordinatingCenter.setHealthcareSite(healthcaresite);

            StudySite studySite = new StudySite();
            studySite.setHealthcareSite(healthcaresite1);


            study.addStudyOrganization(studySite);
            study.addStudyOrganization(coordinatingCenter);

            try {
                dao.save(study);
            }
            catch (Exception ex) {
                // expected
            }
            SecurityContextTestUtils.switchToSuperuser();
            dao.save(study);

            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + savedId, loaded);
            assertEquals("Wrong name", "ShortTitleText", loaded.getShortTitleText());
        }
    }
    
    public void testStudyVersionStudySiteStudyVersionAssociationBug(){
		Study study = studyCreationHelper.createBasicStudy();

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
		healthcaresite.setCtepCode("Nci duke");
		healthcareSitedao.save(healthcaresite);

		HealthcareSite healthcaresite1 = new LocalHealthcareSite();
		Address address1 = new Address();
		address1.setCity("Reston");
		address1.setCountryCode("USA");
		address1.setPostalCode("20192");
		address1.setStateCode("VA");
		address1.setStreetAddress("12359 Sunrise Valley Dr");
		healthcaresite1.setAddress(address);
		healthcaresite1.setName("duke healthcare");
		healthcaresite1.setDescriptionText("duke healthcare1");
		healthcaresite1.setCtepCode("Nci duke1");
		healthcareSitedao.save(healthcaresite1);

		OrganizationAssignedIdentifier orgId1 = new OrganizationAssignedIdentifier();
        orgId1.setHealthcareSite(healthcaresite);
        orgId1.setType(OrganizationIdentifierTypeEnum.COORDINATING_CENTER_IDENTIFIER);
        orgId1.setValue("abc");
        
        study.addIdentifier(orgId1);

		StudySite studySite = new StudySite();
		studySite.setHealthcareSite(healthcaresite1);
		
		StudySite studySite1 = new StudySite();
		studySite1.setHealthcareSite(healthcaresite);
		
		study.addStudySite(studySite);
		study.addStudySite(studySite1);

		dao.save(study);
		
		int id = study.getId();
		
		study = dao.getById(id);
		dao.initialize(study);
		
		assertEquals(2, study.getStudySites().size());
		assertEquals(2, study.getStudyVersion().getStudySiteStudyVersions().size());
		
		study.removeStudySite(studySite);
		
		dao.flush();
		dao.evict(study);
		
		study = dao.getById(id);
		dao.initialize(study);
		
		assertEquals(1, study.getStudySites().size());
		assertEquals(1, study.getStudyVersion().getStudySiteStudyVersions().size());
		
		study.removeStudySite(studySite1);

		dao.flush();
		dao.evict(study);
		
		study = dao.getById(id);
		dao.initialize(study);
		
		assertEquals(0, study.getStudySites().size());
		assertEquals(0, study.getStudyVersion().getStudySiteStudyVersions().size());
    }
    
    public void testGetStudiesByStatus(){
    	List<Study> studies= dao.getStudiesByStatus(CoordinatingCenterStudyStatus.OPEN);
    	assertEquals(5, studies.size());
    	studies= dao.getStudiesByStatus(CoordinatingCenterStudyStatus.PENDING);
    	assertEquals(0, studies.size());
    }
    
    /**
     * Test deleting stratum group with book randomization entries.
     * Related to CPR-718.
     * @throws Exception the exception
     */
    public void testDeleteStratumGrpWithRandomizations() throws Exception {
        Integer savedId;
        {
            Study study = buildStudy();

            Epoch epoch1 = new Epoch();
            epoch1.setName("epoch1");
            
            Arm arm1= epoch1.getArms().get(0);
            arm1.setName("A");
            Arm arm2= epoch1.getArms().get(1);
            arm2.setName("B");
            
            StratificationCriterion sc = new StratificationCriterion();
            sc.setQuestionText("will I work?");
            StratificationCriterionPermissibleAnswer scpa1 = new StratificationCriterionPermissibleAnswer();
            scpa1.setPermissibleAnswer("lets find out");
            StratificationCriterionPermissibleAnswer scpa2 = new StratificationCriterionPermissibleAnswer();
            scpa2.setPermissibleAnswer("no");
            sc.addPermissibleAnswer(scpa1);
            sc.addPermissibleAnswer(scpa2);
            epoch1.addStratificationCriterion(sc);

            epoch1.generateStratumGroups();

            BookRandomization bRandomization = new BookRandomization();
            BookRandomizationEntry bre1 = new BookRandomizationEntry();
            bre1.setPosition(10);
            bre1.setArm(arm1);
            bre1.setStratumGroup(epoch1.getStratumGroups().get(0));
            bRandomization.addBookRandomizationEntry(bre1);
            BookRandomizationEntry bre2 = new BookRandomizationEntry();
            bre2.setPosition(10);
            bre2.setArm(arm1);
            bre2.setStratumGroup(epoch1.getStratumGroups().get(0));
            bRandomization.addBookRandomizationEntry(bre2);
            
            epoch1.setRandomization(bRandomization);
            study.addEpoch(epoch1);
            study = dao.merge(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }
        interruptSession();
        Study loaded = dao.getById(savedId);
        dao.initialize(loaded);
        interruptSession();
		BookRandomization br = (BookRandomization) loaded.getEpochs().get(0).getRandomization();
		assertEquals(2, loaded.getEpochs().get(0).getStratumGroups().size());
		assertEquals(2, br.getBookRandomizationEntry().size());
		
		loaded.getEpochs().get(0).getStratumGroups().remove(0);
		
		((BookRandomization)loaded.getEpochs().get(0).getRandomization()).getBookRandomizationEntry().clear();
		//for(StratumGroup stratumGroup: loaded.getEpochs().get(0).getStratumGroups()){
		//	stratumGroup.getBookRandomizationEntry().clear();
		//}
		loaded = dao.merge(loaded);
		try {
			dao.initialize(loaded);
		} catch (HibernateSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		interruptSession();
		br = (BookRandomization) loaded.getEpochs().get(0).getRandomization();
		assertEquals(1, loaded.getEpochs().get(0).getStratumGroups().size());
		assertEquals(0, br.getBookRandomizationEntry().size());
		interruptSession();
    }
    
    public void testSaveStudyWithConsentQuestion() throws Exception{
    	Study study = dao.getById(1000);
    	assertEquals("expected 1 consent",1,study.getConsents().size());
    	Consent consent = new Consent();
    	consent.setName("1 consent 1");
    	consent.setMandatoryIndicator(true);
    	consent.setVersionId("version 1");
    	
    	ConsentQuestion consentQuestion = new ConsentQuestion();
    	consentQuestion.setText("Question1");
    	consentQuestion.setCode("Question1");
    	
    	consent.addQuestion(consentQuestion);
    	
    	study.addConsent(consent);
    	
    	dao.save(study);
    	
    	Study reloaded = dao.getById(1000);
    	
    	assertEquals("Consent not saved",2,reloaded.getConsents().size());
    	assertEquals("Wrong consent saved","1 consent 1",reloaded.getConsents().get(1).getName());
    	assertEquals("Wrong consent version id","version 1",reloaded.getConsents().get(1).getVersionId());
    	
    	assertEquals("Consent question not saved",1,reloaded.getConsents().get(1).getQuestions().size());
    	assertEquals("Wrong consent question saved","Question1",reloaded.getConsents().get(1).getQuestions().get(0).getText());
    
    }
    
    public void testSaveWithConsentingMethods() throws Exception{
    	Study study = dao.getById(1000);
    	assertEquals("expected 1 consent",1,study.getConsents().size());
    	Consent consent = new Consent();
    	consent.setName("1 consent 1");
    	consent.setMandatoryIndicator(true);
    	consent.setVersionId("version 1");
    	
    	List<ConsentingMethod> consentingMethods = new ArrayList<ConsentingMethod>();
    	consentingMethods.add(ConsentingMethod.VERBAL);
    	consentingMethods.add(ConsentingMethod.WRITTEN);
    	consent.setConsentingMethods(consentingMethods);
    	
    	study.addConsent(consent);
    	
    	dao.save(study);
    	
    	Study reloaded = dao.getById(1000);
    	
    	assertEquals("Consent not saved",2,reloaded.getConsents().size());
    	assertEquals("Wrong consent saved","1 consent 1",reloaded.getConsents().get(1).getName());
    	assertEquals("Wrong consenting methods string","VERBAL : WRITTEN",reloaded.getConsents().get(1).getConsentingMethodsString());
    	
    }
    
    /**
     * Test Saving of a Study with Permissible Registry Statuses.
     *
     * @throws Exception the exception
     */
    public void testSaveStudyWithPrmissibleRegistryStatuses() throws Exception {
        Integer savedId;
        {
            Study study = new LocalStudy();
            study.setPrecisText("New study");
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);
            study.setOriginalIndicator(true);

            createDefaultStudyWithDesign(study);
            PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus1 = study.getPermissibleStudySubjectRegistryStatuses().get(0);
            permissibleStudySubjectRegistryStatus1.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Pre-Enrolled"));
            PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus2 = study.getPermissibleStudySubjectRegistryStatuses().get(1);
            permissibleStudySubjectRegistryStatus2.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Enrolled"));
            PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus3 = study.getPermissibleStudySubjectRegistryStatuses().get(2);
            permissibleStudySubjectRegistryStatus3.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Screen Failed"));
            RegistryStatusReason registryStatusReason1 = new RegistryStatusReason("lab out of rance1","lab out of range1", reasonDao.getReasonByCode("FAILED INCLUSION"), false);
            RegistryStatusReason registryStatusReason2 = new RegistryStatusReason("lab out of rance2","lab out of range2", reasonDao.getReasonByCode("FAILED EXCLUSION"), false);
            permissibleStudySubjectRegistryStatus3.setSecondaryReasons(Arrays.asList(new RegistryStatusReason[]{registryStatusReason1, registryStatusReason2}));
            
            PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus4 = study.getPermissibleStudySubjectRegistryStatuses().get(3);
            permissibleStudySubjectRegistryStatus4.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Accrued"));
            PermissibleStudySubjectRegistryStatus permissibleStudySubjectRegistryStatus5 = study.getPermissibleStudySubjectRegistryStatuses().get(4);
            permissibleStudySubjectRegistryStatus5.setRegistryStatus(registryStatusDao.getRegistryStatusByCode("Consent Withdrawn"));
            RegistryStatusReason registryStatusReason3 = new RegistryStatusReason("distance","distance", reasonDao.getReasonByCode("Unwilling"), false);
            RegistryStatusReason registryStatusReason4 = new RegistryStatusReason("schedule","schedule", reasonDao.getReasonByCode("Unwilling"), false);
            permissibleStudySubjectRegistryStatus5.setSecondaryReasons(Arrays.asList(new RegistryStatusReason[]{registryStatusReason3, registryStatusReason4}));
            
            dao.save(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }

        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + savedId, loaded);
            assertEquals("Wrong name", "New study", loaded.getPrecisText());
            assertEquals(5, loaded.getPermissibleStudySubjectRegistryStatuses().size());
            Collections.sort(loaded.getPermissibleStudySubjectRegistryStatuses(), new Comparator<PermissibleStudySubjectRegistryStatus>(){
            	public int compare(PermissibleStudySubjectRegistryStatus o1,
            			PermissibleStudySubjectRegistryStatus o2) {
            		return o1.getRegistryStatus().getId().compareTo(o2.getRegistryStatus().getId());
            	}
            });
            assertEquals(1000, loaded.getPermissibleStudySubjectRegistryStatuses().get(0).getRegistryStatus().getId().intValue());
            assertEquals(0, loaded.getPermissibleStudySubjectRegistryStatuses().get(0).getRegistryStatus().getPrimaryReasons().size());
            assertEquals(0, loaded.getPermissibleStudySubjectRegistryStatuses().get(0).getSecondaryReasons().size());
            
            assertEquals(1001, loaded.getPermissibleStudySubjectRegistryStatuses().get(1).getRegistryStatus().getId().intValue());
            assertEquals(0, loaded.getPermissibleStudySubjectRegistryStatuses().get(1).getRegistryStatus().getPrimaryReasons().size());
            assertEquals(0, loaded.getPermissibleStudySubjectRegistryStatuses().get(1).getSecondaryReasons().size());
            
            assertEquals(1002, loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getRegistryStatus().getId().intValue());
            assertEquals(3, loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getRegistryStatus().getPrimaryReasons().size());
            assertEquals(2, loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().size());
            assertNotNull(loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(0).getPrimaryReason());
            assertFalse(loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(0).getPrimaryIndicator());
            assertNotNull(loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(1).getPrimaryReason());
            assertFalse(loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(1).getPrimaryIndicator());
            Collections.sort(loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons(), new Comparator<RegistryStatusReason>(){
            	public int compare(RegistryStatusReason o1,
            			RegistryStatusReason o2) {
            		return o1.getPrimaryReason().getId().compareTo(o2.getPrimaryReason().getId());
            	}
            });
            assertEquals(1000, loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(0).getPrimaryReason().getId().intValue());
            assertEquals("lab out of rance1", loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(0).getCode());
            assertEquals("lab out of range1", loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(0).getDescription());
            assertEquals(1001, loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(1).getPrimaryReason().getId().intValue());
            assertEquals("lab out of rance2", loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(1).getCode());
            assertEquals("lab out of range2", loaded.getPermissibleStudySubjectRegistryStatuses().get(2).getSecondaryReasons().get(1).getDescription());
            
            assertEquals(1003, loaded.getPermissibleStudySubjectRegistryStatuses().get(3).getRegistryStatus().getId().intValue());
            assertEquals(0, loaded.getPermissibleStudySubjectRegistryStatuses().get(3).getRegistryStatus().getPrimaryReasons().size());
            assertEquals(0, loaded.getPermissibleStudySubjectRegistryStatuses().get(3).getSecondaryReasons().size());
            
            assertEquals(1005, loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getRegistryStatus().getId().intValue());
            assertEquals(1, loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getRegistryStatus().getPrimaryReasons().size());
            assertEquals(2, loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().size());
            assertNotNull(loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(0).getPrimaryReason());
            assertFalse(loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(0).getPrimaryIndicator());
            assertNotNull(loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(1).getPrimaryReason());
            assertFalse(loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(1).getPrimaryIndicator());
            Collections.sort(loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons(), new Comparator<RegistryStatusReason>(){
            	public int compare(RegistryStatusReason o1,
            			RegistryStatusReason o2) {
            		return o1.getCode().compareTo(o2.getCode());
            	}
            });
            assertEquals(1003, loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(0).getPrimaryReason().getId().intValue());
            assertEquals("distance", loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(0).getCode());
            assertEquals("distance", loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(0).getDescription());
            assertEquals(1003, loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(1).getPrimaryReason().getId().intValue());
            assertEquals("schedule", loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(1).getCode());
            assertEquals("schedule", loaded.getPermissibleStudySubjectRegistryStatuses().get(4).getSecondaryReasons().get(1).getDescription());
        }
    }
    
    // Advanced Search Related Test Cases
    
    public void testGetResultSetWithCompanionIndicator() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "companionIndicator", "true", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithStratificationIndicator() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "stratificationIndicator", "true", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithTherapeuticIntentIndicator() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "therapeuticIntentIndicator", "true", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithType() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "type", "ty1", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("2 studies not found", 2,  studies.size());
	}
   
    public void testGetResultSetWithRandomizedIndicator() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "randomizedIndicator", "true", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("4 studies not found", 4,  studies.size());
	}
    
    public void testGetResultSetWithBlindedIndicator() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "blindedIndicator", "true", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithStandaloneIndicator() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "standaloneIndicator", "true", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("5  studies not found", 5,  studies.size());
	}
    
    public void testGetResultSetWithPhaseCode() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "phaseCode", "Ph", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("3 studies not found", 3,  studies.size());
	}
    
    public void testGetResultSetWithCoordinatingCenterStatus() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "coordinatingCenterStudyStatus.code", "Open", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("5 studies not found", 5,  studies.size());
	}
    
    public void testGetResultSetWithCoordinatingCenterStatus1() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "coordinatingCenterStudyStatus.code", "Pending", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("0 studies not found", 0,  studies.size());
	}
    
    public void testGetResultSetWithVersionStatus() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudyVersion",  "versionStatus.code", "AC", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("wrong number of active studies", 1,  studies.size());
	}
    
    public void testGetResultSetWithDataEntryStatus() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudyVersion",  "dataEntryStatus.code", "INCOMPLETE", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithAmendmentType() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudyVersion",  "amendmentType.code", "IMMEDIATE", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithRandomizationType() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudyVersion",  "randomizationType.code", "2", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("0 studies not found", 0,  studies.size());
	}
    
    public void testGetResultSetWithVersionDate() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudyVersion",  "versionDate", "10/10/2010", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("0 studies not found", 0,  studies.size());
	}
    
    public void testGetResultSetWithVersionDateRange() throws Exception {
    	List<String> values = new ArrayList<String>();
    	values.add("12/31/2000");
    	values.add("01/02/2001");
    	
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudyVersion",  "versionDate", values, "between");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter2 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "coordinatingCenterStudyStatus.code", "Pending", "!=");
		
		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		criteriaParameters.add(advancedSearchCriteriaParameter2);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("Wrong number of studies", 4,  studies.size());
	}
    
    public void testGetResultSetAfterAVersionDate() throws Exception {
    	List<String> values = new ArrayList<String>();
    	values.add("12/31/2000");
    	
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudyVersion",  "versionDate", values, ">");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter2 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Study",  "coordinatingCenterStudyStatus.code", "Pending", "!=");
		
		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		criteriaParameters.add(advancedSearchCriteriaParameter2);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("Wrong number of studies", 4,  studies.size());
	}
    public void testGetResultSetWithVersionName() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudyVersion",  "name", "version 1", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("0 studies not found", 0,  studies.size());
	}
    
    
    public void testGetResultSetWithShortTitleText() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.StudyVersion",  "shortTitleText", "%short%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("4 studies not found", 4,  studies.size());
	}
    
    public void testGetResultSetWithConsentMandatoryIndicator() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Consent",  "mandatoryIndicator", "true", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithConsentMethod() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Consent",  "consentingMethodsString", "%WRITTEN%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithConsentName() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Consent",  "name", "consent%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithConsentVersionId() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Consent",  "versionId", "1000", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithEpochName() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Epoch",  "name", "%epoch%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithEpochType() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Epoch",  "type.code", "TREATMENT", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithEpochEnrollmentIndicator() throws Exception {
		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Epoch",  "enrollmentIndicator", "true", "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 studies not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithDiseaseCategory() throws Exception {
    	List<String> values = new ArrayList<String>();
       	values.add("%Human Papillomavirus%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.DiseaseCategory",  "name",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 study not found", 1,  studies.size());
	}
    
    public void testGetResultSetWithHQLForWildSearch() throws Exception {
		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("5 study not found", 5,  studies.size());
	}
    
    public void testGetResultSetWithHQLForHealthcareSiteIdentifier() throws Exception {
    	AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier",  "Organization",  "value", "%nci%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("3 studies not found", 3,  studies.size());
	}
    
    public void testGetResultSetWithHQLForStudyIdentifier() throws Exception {
    	AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
		.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Identifier",  "Study",  "value", "%nci%", "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("3 studies not found", 3,  studies.size());
	}
    
  //test cases for disease term
	public void testGetResultSetWithHQLForStudyDiseaseTerm() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("%anal cancer%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.DiseaseTerm",  "term",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 study not found", 1,  studies.size());
	}
	
	public void testGetResultSetWithHQLForPersonnelFirstName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("%Bill%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ResearchStaff",  "firstName",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 study not found", 1,  studies.size());
	}
	
	public void testGetResultSetWithHQLForPersonnelLastName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("Staff2");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ResearchStaff",  "lastName",
						values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 study not found", 1,  studies.size());
	}
	
	public void testGetResultSetWithHQLForPersonnelAssignedIdentifier() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("x1");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.ResearchStaff",  "assignedIdentifier",
						values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 study not found", 1,  studies.size());
	}
    
	
	// test case for treating physician
	public void testGetResultSetWithHQLForInvestigatorAssignedIdentifier() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("bill");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter( "edu.duke.cabig.c3pr.domain.Investigator","assignedIdentifier", values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 study not found", 1,  studies.size());
	}
	
	public void testGetResultSetWithHQLForInvestigatorFirstName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("%Bill%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.Investigator",  "firstName", values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("1 study not found", 1,  studies.size());
	}
	
	public void testAdvancedSearchGivenCoordinatingCenterName() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("%caLgb%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.HealthcareSite","StudyCoordinatingCenter", "name", values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("Wrong number of studies", 3,  studies.size());
	}
	
	public void testAdvancedSearchGivenStudySite() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("%duke%");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.HealthcareSite","StudySite", "name", values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("Wrong number of studies", 3,  studies.size());
	}
	
	public void testAdvancedSearchForStudySiteaSttusDate() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("11/11/1999");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.SiteStatusHistory","startDate", values, ">");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("Wrong number of studies", 0,  studies.size());
	}
	
	public void testAdvancedSearchForStudySiteStatus() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("ACTIVE");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.SiteStatusHistory","siteStudyStatus.code", values, "like");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("Wrong number of studies", 0,  studies.size());
	}
	
	
	public void testAdvancedSearchGivenFundingSponsor() throws Exception {
		List<String> values = new ArrayList<String>();
       	values.add("1001");

		AdvancedSearchCriteriaParameter advancedSearchCriteriaParameter1 = AdvancedSearchHelper
				.buildAdvancedSearchCriteriaParameter(
						"edu.duke.cabig.c3pr.domain.HealthcareSite","StudyFundingSponsor", "id", values, "=");

		List<AdvancedSearchCriteriaParameter> criteriaParameters = new ArrayList<AdvancedSearchCriteriaParameter>();
		criteriaParameters.add(advancedSearchCriteriaParameter1);
		
		List<Study> studies = dao.search(criteriaParameters);
		assertEquals("Wrong number of studies", 3,  studies.size());
	}
	
}


