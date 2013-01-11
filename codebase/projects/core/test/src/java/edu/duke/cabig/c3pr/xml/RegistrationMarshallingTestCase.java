/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.io.StringReader;

import edu.duke.cabig.c3pr.constants.RaceCodeEnum;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalHealthcareSite;
import edu.duke.cabig.c3pr.domain.LocalStudy;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.RaceCodeAssociation;
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
        registration.getStudySubjectStudyVersion()
 		.getStudySubjectConsentVersions().get(0).setInformedConsentSignedDate(dateValue);
        registration.setStartDate(dateValue);

        registration.getIdentifiers().addAll(getIdentifiers());

        StudySite studySite = new StudySite();

        HealthcareSite site = new LocalHealthcareSite();
        site.setGridId(siteGridId);
        studySite.setHealthcareSite(site);

        Study study = new LocalStudy();
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
        
		 RaceCodeAssociation  raceCodeAssociation = new RaceCodeAssociation();
         raceCodeAssociation.setRaceCode(RaceCodeEnum.valueOf(strValue));
         patient.addRaceCodeAssociation(raceCodeAssociation);
        patient.setIdentifiers(getIdentifiers());

        ScheduledEpoch epoch = new ScheduledEpoch();
        registration.addScheduledEpoch(epoch);
        registration.setStudySubjectDemographics(patient.createStudySubjectDemographics());


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

            assertNotNull(unmarshalledRegistration.getStudySubjectDemographics().getMasterSubject());

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
