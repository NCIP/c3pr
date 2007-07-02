package edu.duke.cabig.c3pr.xml;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Identifier;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;

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
    XmlMarshaller marshaller;
    SAXParser parser;
    final String schemaFileName = "c3pr-domain.xsd";

    String strValue;
    boolean boolValue;
    Integer intValue = 0;
    Date dateValue;
    String studyGridId;
    String siteGridId;
    static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    protected void setUp() throws Exception {
        marshaller = new XmlMarshaller();
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
            Identifier ident = new Identifier();
            ident.setPrimaryIndicator(boolValue);
            ident.setSource(strValue + temp);
            ident.setType(strValue + temp);
            ident.setValue(strValue + temp);

            identifiers.add(ident);
        }
        return identifiers;
    }

    protected Address getAddress(){
        Address address = new Address();
        address.setCity("Reston");
        address.setCountryCode("USA");
        address.setPostalCode("20191");
        address.setStateCode("VA");
        address.setStreetAddress("12359 Sunrise Valley Dr");
        return address;
    }

    public abstract void testSerializationDeserializationTest();

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
