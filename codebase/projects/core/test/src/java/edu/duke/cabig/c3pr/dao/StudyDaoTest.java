package edu.duke.cabig.c3pr.dao;

import static edu.duke.cabig.c3pr.C3PRUseCase.ADD_DISEASE;
import static edu.duke.cabig.c3pr.C3PRUseCase.ADD_STRATIFICATION;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_STUDY;
import static edu.duke.cabig.c3pr.C3PRUseCase.CREATE_STUDY_INVESTIGATOR;
import static edu.duke.cabig.c3pr.C3PRUseCase.SEARCH_STUDY;
import static edu.duke.cabig.c3pr.C3PRUseCase.UPDATE_STUDY;
import static edu.duke.cabig.c3pr.C3PRUseCase.VERIFY_SUBJECT;
import static edu.nwu.bioinformatics.commons.testing.CoreTestCase.assertContains;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Arm;
import edu.duke.cabig.c3pr.domain.BookRandomization;
import edu.duke.cabig.c3pr.domain.BookRandomizationEntry;
import edu.duke.cabig.c3pr.domain.CalloutRandomization;
import edu.duke.cabig.c3pr.domain.CompanionStudyAssociation;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.DiseaseCategory;
import edu.duke.cabig.c3pr.domain.DiseaseTerm;
import edu.duke.cabig.c3pr.domain.EmailBasedRecipient;
import edu.duke.cabig.c3pr.domain.Epoch;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.HealthcareSiteInvestigator;
import edu.duke.cabig.c3pr.domain.Investigator;
import edu.duke.cabig.c3pr.domain.Notification;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Randomization;
import edu.duke.cabig.c3pr.domain.RoleBasedRecipient;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.StratificationCriterion;
import edu.duke.cabig.c3pr.domain.StratificationCriterionAnswerCombination;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.StratumGroup;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyAmendment;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudyDisease;
import edu.duke.cabig.c3pr.domain.StudyFundingSponsor;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.utils.DaoTestCase;
import edu.duke.cabig.c3pr.utils.SecurityContextTestUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * JUnit Tests for StudyDao
 * 
 * @author Priyatam
 * @author kherm
 * @testType unit
 */

@C3PRUseCases( { CREATE_STUDY, UPDATE_STUDY, SEARCH_STUDY, CREATE_STUDY_INVESTIGATOR,
        ADD_STRATIFICATION, ADD_DISEASE, VERIFY_SUBJECT })
public class StudyDaoTest extends DaoTestCase {
    private StudyDao dao = (StudyDao) getApplicationContext().getBean("studyDao");

    private HealthcareSiteDao healthcareSitedao = (HealthcareSiteDao) getApplicationContext()
                    .getBean("healthcareSiteDao");
    

    private HealthcareSiteInvestigatorDao hcsidao = (HealthcareSiteInvestigatorDao) getApplicationContext()
                    .getBean("healthcareSiteInvestigatorDao");

    private InvestigatorDao investigatorDao = (InvestigatorDao) getApplicationContext().getBean(
                    "investigatorDao");

    private DiseaseTermDao diseaseTermDao = (DiseaseTermDao) getApplicationContext().getBean(
                    "diseaseTermDao");

    private DiseaseCategoryDao diseaseCategoryDao = (DiseaseCategoryDao) getApplicationContext()
                    .getBean("diseaseCategoryDao");

    private XmlMarshaller xmlUtility;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        xmlUtility = new XmlMarshaller((String) getApplicationContext().getBean(
                        "ccts-study-castorMapping"));
    }

   /* public void testForReport() {

        try {
            String outputFileName = "TestReportStudy.txt";

            // Create FileReader Object
            FileWriter outputFileReader = new FileWriter(outputFileName);
            PrintWriter outputStream = new PrintWriter(outputFileReader);
            outputStream
                            .println("+---------- Auto generated report based on data retrieved from the c3pr database. ----------+");
            outputStream.println("");
            outputStream.println("");
            outputStream.println("--- Retrieving the details for a Disease Term. ---");
            List<DiseaseTerm> dTermList = diseaseTermDao
                            .getByCtepTerm("AIDS-related cervical cancer");
            // display disease term data and now fetch studies with this disease term
            outputStream.println("Ctep Term: " + dTermList.get(0).getCtepTerm());
            outputStream.println("Disease Category Name: "
                            + dTermList.get(0).getCategory().getName());
            outputStream.println("Term: " + dTermList.get(0).getTerm());

            List<StudyDisease> sdList = dao.getByDiseaseTermId(dTermList.get(0).getId());
            Study study = dao.getById(sdList.get(0).getStudy().getId());
            outputStream.println("");
            outputStream.println("--- Retrieving the details for a Study. ---");

            try {
                String xml = xmlUtility.toXML(study);
                String newXml = new XMLOutputter(Format.getPrettyFormat())
                                .outputString(new SAXBuilder().build(new StringReader(xml)));
                outputStream.println(newXml);
            }
            catch (XMLUtilityException xue) {
                log.error(xue.getMessage());
            }
            catch (JDOMException je) {
                log.error(je.getMessage());
            }

            outputStream.close();
        }
        catch (IOException e) {
            System.out.println("IOException:");
            e.printStackTrace();
        }
    }
*/
    /*
     * Test the where retired indicator clause for inclusionCriteria.
     */
    public void testWhereAndWhere() throws Exception {

        Study loadedStudy = dao.getById(1000);
        List list = loadedStudy.getEpochs().get(0)
                        .getInclusionEligibilityCriteriaInternal();
        assertTrue(list.size() > 0);
    }

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

    public void testAmendAnExistingStudy() throws Exception {
        Study loadedStudy1 = dao.getById(1002);
        StudyAmendment amendment = new StudyAmendment();
        Date amendmentDate = new Date();
        amendment.setAmendmentDate(amendmentDate);
        amendment.setComments("This is the first change in the study");
        loadedStudy1.addAmendment(amendment);

        dao.save(loadedStudy1);

        interruptSession();

        Study amendedStudy = dao.getById(1002);
        assertEquals("Could not save study with amendment", 1, amendedStudy.getStudyAmendments()
                        .size());
        assertEquals("Amendment comments null or wrong", "This is the first change in the study",
                        amendedStudy.getStudyAmendments().get(0).getComments());

    }

    /**
     * Test Saving of a Study with all associations present Also tests the Security aspect
     * 
     * @throws Exception
     */
    public void testSaveNewStudyWithSecurity() throws Exception {

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

    public ArrayList<StratumGroup> buildStratumGroupWithScac() {
        StratificationCriterionAnswerCombination scac = new StratificationCriterionAnswerCombination();
        List<StratificationCriterionAnswerCombination> scacList = new ArrayList<StratificationCriterionAnswerCombination>();
        scacList.add(scac);

        StratumGroup stratumGroup = new StratumGroup();
        stratumGroup.getStratificationCriterionAnswerCombination().addAll(scacList);
        stratumGroup.setCurrentPosition(1);
        stratumGroup.setStratumGroupNumber(2);
        ArrayList<StratumGroup> sgList = new ArrayList<StratumGroup>();
        sgList.add(stratumGroup);
        return sgList;
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

    public Study buildStudy() {
        Study study = new Study();
        study.setPrecisText("Study with randomization");
        study.setShortTitleText("ShortTitleText1");
        study.setLongTitleText("LongTitleText1");
        study.setPhaseCode("PhaseCode1");
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Type");
        study.setMultiInstitutionIndicator(Boolean.TRUE);
        return study;
    }

    public void testSaveCalloutRandomizations() throws Exception {
        Integer savedId;
        {
            Study study = buildStudy();
            Arm armA = new Arm();
            armA.setName("A");

            Epoch epoch1 = new Epoch();
            armA.setEpoch(epoch1);
            ArrayList<Arm> aList = new ArrayList<Arm>();
            aList.add(armA);
            epoch1.getArms().addAll(aList);
            epoch1.setName("epoch1");
            Randomization cRandomization = new CalloutRandomization();
            ((CalloutRandomization) cRandomization).setCalloutUrl("trialUrl.com");
            epoch1.setRandomization(cRandomization);
            study.addEpoch(epoch1);
            study = dao.merge(study);

            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }
        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            CalloutRandomization cr = (CalloutRandomization) loaded.getEpochs().get(0)
                            .getRandomization();
            String i = cr.getCalloutUrl();
            assertEquals(i, "trialUrl.com");
        }
    }

    public void testSaveStudyWithAnsComb() throws Exception {
        Integer savedId;
        {
            Study study = buildStudy();
            Arm armA = new Arm();
            armA.setName("A");

            Epoch epoch1 = new Epoch();
            armA.setEpoch(epoch1);
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
            sg1.getStratificationCriterionAnswerCombination().addAll(cloneScac(scac1List));
            StratumGroup sg2 = new StratumGroup();
            sg2.getStratificationCriterionAnswerCombination().addAll(cloneScac(scac2List));

            epoch1.getStratumGroups().add(sg1);
            epoch1.getStratumGroups().add(sg2);
            study.addEpoch(epoch1);
            study = dao.merge(study);
            savedId = study.getId();
        }
        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            StratumGroup sg1 = (loaded.getEpochs().get(0)).getStratumGroups().get(
                            0);
            StratumGroup sg2 = (loaded.getEpochs().get(0)).getStratumGroups().get(
                            1);

            assertEquals((loaded.getEpochs().get(0)).getStratumGroups().size(), 2);
            assertNotNull(sg1.getStratificationCriterionAnswerCombination());
            assertEquals(2, sg1.getStratificationCriterionAnswerCombination().size());

            assertNotNull(sg2.getStratificationCriterionAnswerCombination());
            assertEquals(2, sg2.getStratificationCriterionAnswerCombination().size());
        }
    }

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
     * Test Saving of a Study with all Randomization associations present
     * 
     * @throws Exception
     */
    public void testSaveStudyWithRandomizations() throws Exception {
        Integer savedId;
        {
            Study study = buildStudy();
            Arm armA = new Arm();
            armA.setName("A");

            Epoch epoch1 = new Epoch();
            armA.setEpoch(epoch1);
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
            BookRandomization br = (BookRandomization) loaded.getEpochs().get(0)
                            .getRandomization();
            int i = br.getBookRandomizationEntry().get(0).getStratumGroup().getCurrentPosition();
            assertEquals(i, 1);
        }
    }

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
        sc.setPermissibleAnswers(scpaList);
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
        sc2.setPermissibleAnswers(scpa2List);
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
                sg.getStratificationCriterionAnswerCombination().addAll(strMyLine);
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
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);

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

    public void testSaveStudyWithNotifications() throws Exception {
        Integer savedId;
        {
            Study study = new Study();
            study.setPrecisText("Study with Notifications");
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(100);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);

            createDefaultStudyWithDesign(study);

            // notification specific code.
            EmailBasedRecipient ebr = new EmailBasedRecipient();
            ebr.setEmailAddress("vinay.gangoli@semanticbits.com");
            RoleBasedRecipient rbr = new RoleBasedRecipient();
            rbr.setRole("admin");

            Notification notification = new Notification();
            notification.setThreshold(90);
            notification.getEmailBasedRecipient().add(ebr);
            notification.getRoleBasedRecipient().add(rbr);
            study.getNotifications().add(notification);
            dao.save(study);
            savedId = study.getId();
            assertNotNull("The saved study didn't get an id", savedId);
        }
        interruptSession();
        {
            Study loaded = dao.getById(savedId);
            assertNotNull("Could not reload study with id " + savedId, loaded);
            assertEquals("Wrong Threshold", 90, loaded.getNotifications().get(0).getThreshold()
                            .intValue());
            assertEquals("Wrong role", "admin", loaded.getNotifications().get(0)
                            .getRoleBasedRecipient().get(0).getRole());
            assertEquals("Wrong emailAddress", "vinay.gangoli@semanticbits.com", loaded
                            .getNotifications().get(0).getEmailBasedRecipient().get(0)
                            .getEmailAddress());
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
            System.out.println("hc site id ************" + healthcaresite.getId());
            HealthcareSite hcsiteloaded = healthcareSitedao.getById(healthcaresite.getId());

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
            HealthcareSiteInvestigator hcsiloaded = hcsidao.getById(hcsi.getId());

            Study study = new Study();
            study.setShortTitleText("ShortTitleText");
            study.setLongTitleText("LongTitleText");
            study.setPhaseCode("PhaseCode");
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);

            study.addEpoch(Epoch.createEpoch("Screening"));
            study.addEpoch(Epoch.createEpochWithArms("Treatment", "Arm A", "Arm B", "Arm C"));
            study.addEpoch(Epoch.createEpoch("Follow up"));

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
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);

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
            assertEquals("Wrong name", "ShortTitleText", loaded.getShortTitleText());
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

    public void testGetEpochsAgain() throws Exception {

        Study study = dao.getById(1000);

        Epoch te = new Epoch();
        te.setName("test");
        te.setStudy(study);
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
        List<StudySubject> spa = dao.getStudySubjectsForStudy(1000);
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
        Study studySearchCriteria = new Study(true);
        studySearchCriteria.setShortTitleText("short_title_text");
        List<Study> results = dao.searchByExample(studySearchCriteria);
        assertEquals("Wrong number of Studies", 2, results.size());
        assertEquals("short_title_text", results.get(0).getShortTitleText());
        assertEquals("short_title_text", results.get(1).getShortTitleText());
    }

    /**
     * Make sure DAO returns unique results
     */
    public void testIdentifierUniqueResults() {
        Study studySearchCriteria = new Study(true);
        studySearchCriteria.setShortTitleText("short_title_text2");
        List<Study> results = dao.searchByExample(studySearchCriteria);
        assertEquals("Wrong number of Studies", 1, results.size());
    }

    private Study createDefaultStudyWithDesign(Study study) {
        // Investigators
        Investigator inv = new Investigator();
        inv.setFirstName("Investigator first name");

        investigatorDao.save(inv);

        study.addEpoch(Epoch.createEpoch("Screening"));
        study.addEpoch(Epoch.createEpochWithArms("Treatment", "Arm A", "Arm B", "Arm C"));
        study.addEpoch(Epoch.createEpoch("Follow up"));

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
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);

        // HCSI
        HealthcareSiteInvestigator hcsi = new HealthcareSiteInvestigator();
        inv.addHealthcareSiteInvestigator(hcsi);
        healthcaresite.addHealthcareSiteInvestigator(hcsi);
        hcsidao.save(hcsi);
        System.out.println("hcsi id ************" + hcsi.getId());

        // Study Investigator
        StudyInvestigator studyInvestigator = studySite.getStudyInvestigators().get(
                        studySite.getStudyInvestigators().size());
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

    public void testSaveNewStudyWithEpochAndArms() throws Exception {
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

            study.addEpoch(Epoch.createEpochWithArms("TestTreatmentEpoch1", "Arm A", "Arm B",
                            "Arm C"));
            study.addEpoch(Epoch.createEpoch("TestTreatmentEpoch2"));

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
            assertEquals("Wrong number of Arms are retreived", 3, newEpoch1.getArms()
                            .size());
            assertEquals("Wrong name of epoch retrieved", "TestTreatmentEpoch2", newEpoch2.getName()
                    );
            }
        }

    public void testSaveNewStudyWithEpochs() throws Exception {
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

            study.addEpoch(Epoch.createEpoch("TestNonTreatmentEpoch"));

            Epoch newEpoch = new Epoch();
            newEpoch.setName("Test Non Treatment Epoch");
            newEpoch.setAccrualCeiling(10);
            newEpoch.setAccrualIndicator(Boolean.TRUE);
            newEpoch.setReservationIndicator(Boolean.TRUE);
            newEpoch.setEnrollmentIndicator(Boolean.FALSE);
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
            assertEquals("Wrong number of Epochs are retreived", 2,
                            totalEpochs.size());
            for (Epoch newEpoch : totalEpochs) {
                assertEquals("Wrong Treatment Indicator: ",
                                "false", newEpoch.getTreatmentIndicator());
            }
            assertEquals("Wrong epoch name", "Test Non Treatment Epoch",
                            (totalEpochs.get(1))
                                            .getName());
            assertEquals("Wrong enrollment indicator", Boolean.FALSE,
                            (totalEpochs.get(1))
                                            .getEnrollmentIndicator());
            assertEquals("Wrong accrual indicator", Boolean.TRUE,
                            (totalEpochs.get(1))
                                            .getAccrualIndicator());
            assertEquals("Wrong reservation indicator", Boolean.TRUE,
                            (totalEpochs.get(1))
                                            .getReservationIndicator());
            assertEquals("Wrong accrual ceiling", 10, (totalEpochs
                            .get(1)).getAccrualCeiling().intValue());

        }
    }

    public void testSaveNewStudyWithTreatmentEpochsAndArms() throws Exception {
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

            study.addEpoch(Epoch.createEpochWithArms("TestTreatmentEpoch1", "Arm A", "Arm B",
                            "Arm C"));
            Epoch epoch = new Epoch();
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
     * Test for searching Studies using wildcards
     * 
     * @throws Exception
     */
    public void testSearchStudyByWildCards() throws Exception {
        Study studySearchCriteria = new Study(true);
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
        System.out.println("Study funding sponsor is: "
                        + sponsors.get(0).getHealthcareSite().getName());
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
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);

            // Study Site
            StudySite studySite = new StudySite();
            studySite.setHealthcareSite(site);
            studySite.setRoleCode("role");
            studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);

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
            study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
            study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
            study.setTargetAccrualNumber(150);
            study.setType("Type");
            study.setMultiInstitutionIndicator(Boolean.TRUE);

            // Study Site
            StudySite studySite = new StudySite();
            studySite.setHealthcareSite(site);
            studySite.setRoleCode("role");
            studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);

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

    public void testFailureSaveTwoIdentifiersWithSameOrgAndSameTypeInStudy() throws Exception {
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
        interruptSession();

    }

    public void testFailureSaveTwoStudyOrganizationsWithSameOrgAndSameType() throws Exception {
        Study loadedStudy = dao.getById(1000);
        HealthcareSite site = healthcareSitedao.getById(1000);
        StudySite studySite = new StudySite();
        studySite.setHealthcareSite(site);
        loadedStudy.addStudyOrganization(studySite);

        try {
            dao.save(loadedStudy);
            fail("Save should fail");
        }
        catch (RuntimeException e) {
        }

    }

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

    public void testFailureSaveTwoStudyFundingSponsorIdentifiersWithSameValue() throws Exception {
        Study loadedStudy1 = dao.getById(1002);
        Study loadedStudy2 = dao.getById(1001);
        HealthcareSite site = healthcareSitedao.getById(1000);
        OrganizationAssignedIdentifier orgId1 = new OrganizationAssignedIdentifier();
        orgId1.setHealthcareSite(site);
        orgId1.setType("Study Funding Sponsor");
        orgId1.setValue("abc");

        OrganizationAssignedIdentifier orgId2 = new OrganizationAssignedIdentifier();
        orgId2.setHealthcareSite(site);
        orgId2.setType("Study Funding Sponsor");
        orgId2.setValue("abc");
        loadedStudy1.addIdentifier(orgId1);
        loadedStudy2.addIdentifier(orgId2);

        dao.save(loadedStudy1);

    }

    public void testSaveNewStudyWithAmendment() throws Exception {

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
        StudyAmendment amendment = new StudyAmendment();
        Date amendmentDate = new Date();
        amendment.setAmendmentDate(amendmentDate);
        amendment.setComments("Capturing a study with an existing amendment");
        study.addAmendment(amendment);

        dao.save(study);
        interruptSession();

        Study amendedStudy = dao.getById(study.getId());
        assertEquals("Could not save study with amendment", 1, amendedStudy.getStudyAmendments()
                        .size());
        assertEquals("Amendment comments null or wrong",
                        "Capturing a study with an existing amendment", amendedStudy
                                        .getStudyAmendments().get(0).getComments());

    }

    public void testEpochOrder() {
        Study study = dao.getById(1000);
        assertEquals("Wrong Epoch order", "Treatment1000", study.getEpochs().get(0).getName());
        assertEquals("Wrong Epoch order", "NonTreatment1004", study.getEpochs().get(1).getName());
        assertEquals("Wrong Epoch order", "Treatment1003", study.getEpochs().get(2).getName());
        assertEquals("Wrong Epoch order", "NonTreatment1005", study.getEpochs().get(3).getName());
        assertEquals("Wrong Epoch order", "Treatment1001", study.getEpochs().get(4).getName());
        assertEquals("Wrong Epoch order", "Treatment1002", study.getEpochs().get(5).getName());
    }
    
 public void testCompanionStudy(){
    	
    	HealthcareSite sponsor = healthcareSitedao.getById(1001);
        HealthcareSite site = healthcareSitedao.getById(1000);
        HealthcareSite center = healthcareSitedao.getById(1002);

        Study study = new Study();
        study.setShortTitleText("ShortTitleText");
        study.setLongTitleText("LongTitleText");
        study.setPhaseCode("PhaseCode");
        study.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
        study.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        study.setTargetAccrualNumber(150);
        study.setType("Type");
        study.setMultiInstitutionIndicator(Boolean.TRUE);

        // Study Site
        StudySite studySite = new StudySite();
        studySite.setHealthcareSite(site);
        studySite.setRoleCode("role");
        studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);

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
        csa.setParentStudy(study);
        csa.setMandatoryIndicator(true);
        study.getCompanionStudyAssociations().add(csa);

        dao.save(study);
        int savedId = study.getId();
        assertNotNull("The saved study didn't get an id", savedId);
        
    	interruptSession();
    	
    	assertNotNull("Companion Association exists",  dao.getById(savedId));
    	assertNotNull("Companion Association has Parent Study",  dao.getById(savedId).getCompanionStudyAssociations());
    }
}
