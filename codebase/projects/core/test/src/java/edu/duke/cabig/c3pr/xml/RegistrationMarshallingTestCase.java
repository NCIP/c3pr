package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.domain.*;
import gov.nih.nci.common.exception.XMLUtilityException;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.io.StringReader;

/**
 * Will test the XML marshalling framework
 * for c3prv2
 *
 * @testType unit
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 11, 2007
 * Time: 2:08:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class RegistrationMarshallingTestCase extends AbstractXMLMarshalling {


    String marshalledRegistration;


    /**
     * @throws Exception
     * @Before
     */
    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
    }


    /**
     * @Test main test method. Runs methods in a sequence.
     * @throws Throwable
     */
    public void testSerializationDeserializationTest(){
            registrationSerializationTest();
           // schemaValidationTest();
            registrationDeserializationTest();
    }

    /**
     * Test serialization of message
     */
    private void registrationSerializationTest() {
        StudyParticipantAssignment registration = new StudyParticipantAssignment();
        registration.setName(strValue);

        registration.setStudyParticipantIdentifier(strValue);
        registration.setInformedConsentSignedDate(dateValue);
        registration.setStartDate(dateValue);
        registration.setEligibilityIndicator(boolValue);
        registration.setIdentifiers(getIdentifiers());

        StudySite studySite = new StudySite();

        HealthcareSite site = new HealthcareSite();
        site.setGridId(siteGridId);
        studySite.setSite(site);

        Study study = new Study();
        study.setGridId(studyGridId);
        study.setIdentifiers(getIdentifiers());
        studySite.setStudy(study);
        registration.setStudySite(studySite);

        Participant patient = new Participant();
        patient.setAdministrativeGenderCode(strValue);
        patient.setBirthDate(dateValue);
        patient.setEthnicGroupCode(strValue);
        patient.setFirstName(strValue);
        patient.setLastName(strValue);
        patient.setMaritalStatusCode(strValue);
        patient.setRaceCode(strValue);
        patient.setIdentifiers(getIdentifiers());

        registration.setParticipant(patient);



        patient.setAddress(getAddress());

        try {
            marshalledRegistration = marshaller.toXML(registration);
            System.out.println(marshalledRegistration);
            assertNotNull(marshalledRegistration);
        } catch (XMLUtilityException e) {
            fail(e.getMessage());
        }

    }

    /**
     * Tests if the message generated can be validated
     * against the schema
     */
    private void schemaValidationTest() {

        try {

            //validate the marshalled message
            byte[] messageBytes = marshalledRegistration.getBytes();
            parser.parse(new ByteArrayInputStream(messageBytes), new MyHandler());
        }
        catch (Exception x) {
            fail(x.getMessage());
        }
    }


    /**
     * Deserialize the message to test
     * deserialization process
     */
    private void registrationDeserializationTest() {
        Reader messageReader = new StringReader(marshalledRegistration);
        try {
            StudyParticipantAssignment unmarshalledRegistration = (StudyParticipantAssignment) marshaller.fromXML(messageReader);
            unmarshalledRegistration = (StudyParticipantAssignment) marshaller.fromXML(new StringReader(marshalledRegistration));
            assertNotNull(unmarshalledRegistration);

            assertNotNull(unmarshalledRegistration.getParticipant());

            assertEquals(unmarshalledRegistration.getStudySite().getSite().getGridId(), siteGridId);
            assertEquals(unmarshalledRegistration.getStudySite().getStudy().getGridId(), studyGridId);

        } catch (XMLUtilityException e) {
            fail(e.getMessage());
        }
    }



}