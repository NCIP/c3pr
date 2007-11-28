package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.domain.*;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: May 11, 2007
 * Time: 11:41:32 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractXMLMarshalling extends ApplicationTestCase {
    protected static final String W3C_XML_SCHEMA =
            "http://www.w3.org/2001/XMLSchema";
    protected static final String JAXP_SCHEMA_LANGUAGE =
            "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
    protected static final String JAXP_SCHEMA_SOURCE =
            "http://java.sun.com/xml/jaxp/properties/schemaSource";

    SchemaFactory schemaFactory;
    SAXParserFactory parserFactory;

    SAXParser parser;
    String schemaFileName = "c3pr-domain.xsd";

    String strValue;
    boolean boolValue;
    Integer intValue = 0;
    Date dateValue;
    protected String studyGridId;
    String siteGridId;
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    protected void setUp() throws Exception {

        //set values for parameters
        strValue = "tempStr";
        boolValue = true;
        dateValue = sdf.parse("2009/01/20");
        studyGridId = "testStudyGridId";
        siteGridId = "siteGridId";

        org.xml.sax.InputSource in = new org.xml.sax.InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(schemaFileName));

        parserFactory = SAXParserFactory.newInstance();
        parserFactory.setValidating(true);
        parserFactory.setNamespaceAware(true);

        assertNotNull(in);

        parser = parserFactory.newSAXParser();
        parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        parser.setProperty(JAXP_SCHEMA_SOURCE, in);

    }

    protected List<Identifier> getIdentifiers() {
        java.util.List<Identifier> identifiers = new ArrayList<Identifier>();

        for (int temp = 0; temp < 3; temp++) {
            SystemAssignedIdentifier ident = new SystemAssignedIdentifier();
            ident.setPrimaryIndicator(boolValue);
            ident.setSystemName(strValue + temp);
            ident.setType(strValue + temp);
            ident.setValue(strValue + temp);

            identifiers.add(ident);
        }
        return identifiers;
    }

    protected Address getAddress() {
        Address address = new Address();
        address.setCity("Reston");
        address.setCountryCode("USA");
        address.setPostalCode("20191");
        address.setStateCode("VA");
        address.setStreetAddress("12359 Sunrise Valley Dr");
        return address;
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
        studyObject.setMultiInstitutionIndicator(new Boolean(boolValue));
        studyObject.setLongTitleText(strValue);
        studyObject.setPhaseCode("0");
        studyObject.setPrecisText(strValue);
        studyObject.setCoordinatingCenterStudyStatus(CoordinatingCenterStudyStatus.ACTIVE);
        studyObject.setDataEntryStatus(StudyDataEntryStatus.COMPLETE);
        studyObject.setType(strValue);
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

        studyObject.addEpoch(Epoch.create("Screening"));
        studyObject.addEpoch(Epoch.create("Treatment", "Arm A", "Arm B", "Arm C"));
        studyObject.addEpoch(Epoch.create("Follow up"));

        // healthcare site
        HealthcareSite healthcaresite = new HealthcareSite();

        healthcaresite.setAddress(getAddress());
        healthcaresite.setName("duke healthcare");
        healthcaresite.setDescriptionText("duke healthcare");
        healthcaresite.setNciInstituteCode("Nci duke");

        StudySite studySite = new StudySite();
        studyObject.addStudySite(studySite);
        studySite.setHealthcareSite(healthcaresite); //
        studySite.setStartDate(dateValue);
        studySite.setIrbApprovalDate(dateValue);
        studySite.setRoleCode("role");
        studySite.setSiteStudyStatus(SiteStudyStatus.ACTIVE);

        StratificationCriterionPermissibleAnswer ans = new StratificationCriterionPermissibleAnswer();
        ans.setPermissibleAnswer("it is valid");
        StratificationCriterionPermissibleAnswer ans2 = new StratificationCriterionPermissibleAnswer();
        ans.setPermissibleAnswer("it is valid");
        return studyObject;
    }


    // subclasses can override the marshaller
    public XmlMarshaller getMarshaller() {
        return new XmlMarshaller();
    }

    /**
     * inner class. Will fail test if any
     * exception is thrown during parsing
     */

    protected class MyHandler extends DefaultHandler {

        public void error(SAXParseException saxParseException) throws SAXException {
            fail(saxParseException.getMessage());
        }


        public void fatalError(SAXParseException saxParseException) throws SAXException {
            fail(saxParseException.getMessage());
        }
    }

}
