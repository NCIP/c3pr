package edu.duke.cabig.c3pr.xml;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

import java.util.*;
import java.io.StringReader;
import java.io.File;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * Will test the XML marshalling framework
 * for c3prv2
 *
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 11, 2007
 * Time: 2:08:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLMarshallerTestCase extends TestCase {

    XmlMarshaller marshaller;

    String strValue;
    boolean boolValue;
    Date dateValue;

    StudyParticipantAssignment unmarshalledRegistration;
    String marshalledRegistration;
    
    static final SimpleDateFormat sdf = new SimpleDateFormat( "yyyy/MM/dd" );

    protected void setUp() throws Exception {
        marshaller = new XmlMarshaller();
        //set values for parameters
        strValue= "tempStr";
        boolValue = true;
        dateValue = sdf.parse("2009/01/20");

    }

    public void testRegistrationSeriazlier(){
        StudyParticipantAssignment registration = new StudyParticipantAssignment();
        registration.setName("testName");

        registration.setStudyParticipantIdentifier(strValue);
        registration.setInformedConsentSignedDate(dateValue);
        registration.setStartDate(dateValue);
        registration.setGridId(strValue);
        registration.setEligibilityIndicator(boolValue);

        StudySite site = new StudySite();
        Study study = new Study();
        study.setIdentifiers(getIdentifiers());
        site.setStudy(study);
        registration.setStudySite(site);

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

        Address address = new Address();
        address.setCity(strValue);
        address.setCountryCode(strValue);
        address.setPostalCode(strValue);
        address.setStateCode(strValue);
        address.setStateCode(strValue);
        address.setStreetAddress(strValue);

        patient.setAddress(address);

        try {
            marshalledRegistration = marshaller.toXML(registration);
            System.out.println(marshalledRegistration);
            assertNotNull(marshalledRegistration);

            unmarshalledRegistration = (StudyParticipantAssignment)marshaller.fromXML(new StringReader(marshalledRegistration));
            assertNotNull(unmarshalledRegistration);
            assertEquals(registration.getGridId(),unmarshalledRegistration.getGridId());
            assertNotNull(unmarshalledRegistration.getParticipant());
        } catch (XMLUtilityException e) {
            fail(e.getMessage());
        }

    }

    public void testDeserialization(){
        File message = new File("resources/SampleMessage.xml");
        try {
            marshaller.fromXML(message);
        } catch (XMLUtilityException e) {
            fail(e.getMessage());
        }
    }


    private List<Identifier> getIdentifiers(){
       java.util.List<Identifier> identifiers = new ArrayList<Identifier>();

        for(int temp=0;temp<3;temp++){
                    Identifier ident = new Identifier();
                    ident.setPrimaryIndicator(boolValue);
                    ident.setSource(strValue+temp);
                    ident.setType(strValue+temp);
                    ident.setValue(strValue+temp);

                    identifiers.add(ident);
                }
        return identifiers;
    }

    private Date getCurrentDate()throws ParseException{

        String dateString = "2009/01/20";

        TimeZone est = TimeZone.getTimeZone( "America/New_York" );
        GregorianCalendar inauguration = new GregorianCalendar( est );
        sdf.setCalendar( inauguration );

        Date date = sdf.parse( dateString );
        inauguration.setTime( date );

        return date;
    }
}