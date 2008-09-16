package edu.duke.cabig.c3pr.xml;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.io.StringReader;

import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Will test the XML marshalling framework for c3prv2
 * 
 * @testType unit <p/> Created by IntelliJ IDEA. User: kherm Date: Mar 11, 2007 Time: 2:08:16 PM To
 *           change this template use File | Settings | File Templates.
 */
public class RegistrationMarshallingTestCase extends AbstractXMLMarshalling {

    String marshalledRegistration;

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
    }

    /**
     * Test serialization of message
     */
    private void registrationSerializationTest() {
        StudySubject registration = new StudySubject();
        registration.setName(strValue);

        registration.setInformedConsentSignedDate(dateValue);
        registration.setStartDate(dateValue);

        registration.getIdentifiers().addAll(getIdentifiers());

        StudySite studySite = new StudySite();

        HealthcareSite site = new HealthcareSite();
        site.setGridId(siteGridId);
        studySite.setHealthcareSite(site);

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

        ScheduledEpoch epoch = new ScheduledEpoch();
        registration.addScheduledEpoch(epoch);
        registration.setParticipant(patient);

        patient.fillAddress(getAddress());

        try {
            marshalledRegistration = getMarshaller().toXML(registration);
            System.out.println(marshalledRegistration);
            assertNotNull(marshalledRegistration);
        }
        catch (XMLUtilityException e) {
            fail(e.getMessage());
        }

    }

    /**
     * Tests if the message generated can be validated against the schema
     */
    private void schemaValidationTest() {

        try {

            // validate the marshalled message
            byte[] messageBytes = marshalledRegistration.getBytes();
            parser.parse(new ByteArrayInputStream(messageBytes), new MyHandler());
        }
        catch (Exception x) {
            fail(x.getMessage());
        }
    }

    /**
     * Deserialize the message to test deserialization process
     */
    private void registrationDeserializationTest() {
        Reader messageReader = new StringReader(marshalledRegistration);
        try {
            StudySubject unmarshalledRegistration = (StudySubject) getMarshaller().fromXML(
                            messageReader);
            assertNotNull(unmarshalledRegistration);

            assertNotNull(unmarshalledRegistration.getParticipant());

            assertEquals(unmarshalledRegistration.getStudySite().getHealthcareSite().getGridId(),
                            siteGridId);
            assertEquals(unmarshalledRegistration.getStudySite().getStudy().getGridId(),
                            studyGridId);

        }
        catch (XMLUtilityException e) {
            fail(e.getMessage());
        }
    }

    // subclasses can override the marshaller
    public XmlMarshaller getMarshaller() {
        return new XmlMarshaller("ccts-registration-castor-mapping.xml");
    }
}