package edu.duke.cabig.c3pr.xml;

import static edu.duke.cabig.c3pr.C3PRUseCase.EXPORT_STUDY;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.xml.sax.InputSource;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.domain.CoordinatingCenterStudyStatus;
import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.SiteStudyStatus;
import edu.duke.cabig.c3pr.domain.StratificationCriterionPermissibleAnswer;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyDataEntryStatus;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Test serialization for the Study object
 * 
 * @testType unit <p/> Created by IntelliJ IDEA. User: kherm Date: May 11, 2007 Time: 11:30:03 AM To
 *           change this template use File | Settings | File Templates.
 */
@C3PRUseCases(EXPORT_STUDY)
public class StudyMarshallingTestCase extends AbstractXMLMarshalling {

    String marshalledStudy;

    /**
     * @throws Exception
     * @Before
     */
    protected void setUp() throws Exception {
        super.setUp(); // To change body of overridden methods use File | Settings | File
                        // Templates.
    }

    /**
     * @throws Throwable
     * @Test main test method. Runs methods in a sequence.
     */
    public void testSerializationDeserializationTest() {
        // have to be run in order
        studySerializationTest();
        //schemaValidationTest();
        studyDeserializationTest();
    }

    private void studySerializationTest() {
        try {
            marshalledStudy = getMarshaller().toXML(createDummyStudy(studyGridId));
            System.out.println(marshalledStudy);
            assertNotNull(marshalledStudy);
        }
        catch (XMLUtilityException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests if the message generated can be validated against the schema
     */
    private void schemaValidationTest() {
    	byte[] messageBytes = marshalledStudy.getBytes();
        //ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(messageBytes);
        //System.out.println(byteArrayInputStream.read());
    	System.out.println("Using input source...");
        try {
            // validate the marshalled message
            //byteArrayInputStream.reset();
            parser.parse(new InputSource(new StringReader(marshalledStudy)), new MyHandler());
            //parser.parse(byteArrayInputStream, new MyHandler());
        }
        catch (Exception x) {
            fail(x.getMessage());
        }finally{
//        	try {
//				byteArrayInputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//				fail(e.getMessage());
//			}
        }
    }

    private void studyDeserializationTest() {
        Reader messageReader = new StringReader(marshalledStudy);

        try {
            Study unmarshalledStudy = (Study) getMarshaller().fromXML(messageReader);
            assertNotNull(unmarshalledStudy);

            assertEquals(unmarshalledStudy.getGridId(), studyGridId);
            // we never set this so it should be null
            assertNull(unmarshalledStudy.getDiseaseCategoryAsText());
        }
        catch (XMLUtilityException e) {
            fail(e.getMessage());
        }

    }

    private List<InclusionEligibilityCriteria> getInclusionEligibilityCriterias() {
        List<InclusionEligibilityCriteria> criterias = new ArrayList<InclusionEligibilityCriteria>();

        for (int i = 0; i <= 2; i++) {
            InclusionEligibilityCriteria criteria = new InclusionEligibilityCriteria();
            criteria.setGridId(strValue);
            criteria.setQuestionNumber(i);
            criteria.setQuestionText(strValue);
            criterias.add(criteria);
        }
        return criterias;
    }

    private List<ExclusionEligibilityCriteria> getExclusionEligibilityCriterias() {
        List<ExclusionEligibilityCriteria> criterias = new ArrayList<ExclusionEligibilityCriteria>();

        for (int i = 1; i <= 2; i++) {
            ExclusionEligibilityCriteria criteria = new ExclusionEligibilityCriteria();
            criteria.setGridId(strValue);
            criteria.setQuestionNumber(i);
            criteria.setQuestionText(strValue);
            criterias.add(criteria);
        }
        return criterias;
    }

    /**
     * Will create a dummy study for the provided gridId
     * 
     * @param gridId
     * @return
     */
    protected Study createDummyStudy(String gridId) {
        Study studyObject = new Study();

        studyObject.setGridId(gridId);
        studyObject.setShortTitleText(strValue);
        studyObject.setRandomizedIndicator(new Boolean(boolValue));
        studyObject.setStratificationIndicator(new Boolean(boolValue));
        studyObject.setMultiInstitutionIndicator(new Boolean(boolValue));
        studyObject.setLongTitleText(strValue);
        studyObject.setPhaseCode("Phase 0 Trial");
        studyObject.setPrecisText(strValue);
        studyObject.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.OPEN);
        studyObject.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        studyObject.setType("Diagnostic");
        studyObject.setTargetAccrualNumber(intValue);
        studyObject.setVersion(intValue);

        studyObject.setDescriptionText(strValue);

        StudySite studySiteObject = new StudySite();
        studySiteObject.setGridId(strValue);
        studySiteObject.setIrbApprovalDate(dateValue);
        studySiteObject.setRoleCode(strValue);

        SystemAssignedIdentifier identifierObject = new SystemAssignedIdentifier();
        identifierObject.setSystemName(strValue);
        identifierObject.setType(strValue);

        studyObject.addIdentifier(identifierObject);
        studyObject.addStudySite(studySiteObject);

       /* studyObject.addEpoch(Epoch.createEpoch("Screening"));
        studyObject.addEpoch(Epoch.createEpochWithArms("Treatment", "Arm A", "Arm B", "Arm C"));
        studyObject.addEpoch(Epoch.createEpoch("Follow up"));*/

        // healthcare site
        HealthcareSite healthcaresite = new LocalHealthcareSite();

        healthcaresite.setAddress(getAddress());
        healthcaresite.setName("duke healthcare");
        healthcaresite.setDescriptionText("duke healthcare");
        healthcaresite.setNciInstituteCode("Nci duke");

        StudySite studySite = new StudySite();
        studyObject.addStudySite(studySite);
        studySite.setHealthcareSite(healthcaresite); //
        studySite.setStartDate(new Date());
        studySite.setIrbApprovalDate(new Date());
        studySite.setRoleCode("role");
        studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);

        StratificationCriterionPermissibleAnswer ans = new StratificationCriterionPermissibleAnswer();
        ans.setPermissibleAnswer("it is valid");
        StratificationCriterionPermissibleAnswer ans2 = new StratificationCriterionPermissibleAnswer();
        ans.setPermissibleAnswer("it is valid");
        return studyObject;
    }
    
    public XmlMarshaller getMarshaller() {
        return new XmlMarshaller("c3pr-study-xml-castor-mapping.xml");
    }

}