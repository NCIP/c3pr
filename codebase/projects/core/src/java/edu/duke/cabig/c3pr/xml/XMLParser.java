/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.XMLValidationException;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Oct 16, 2007 Time: 1:38:44 PM To change this template
 * use File | Settings | File Templates.
 */
public class XMLParser {
    private Logger log = Logger.getLogger(XmlMarshaller.class.getName());

    SAXParser parser;

    SAXParserFactory parserFactory;

    protected static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

    protected static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

    protected static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

    public XMLParser(String schemaFileName) throws Exception {
        org.xml.sax.InputSource in = new org.xml.sax.InputSource(Thread.currentThread()
                        .getContextClassLoader().getResourceAsStream(schemaFileName));

        parserFactory = SAXParserFactory.newInstance();
        parserFactory.setValidating(true);
        parserFactory.setNamespaceAware(true);

        parser = parserFactory.newSAXParser();
        parser.setProperty(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
        parser.setProperty(JAXP_SCHEMA_SOURCE, in);
    }

    public void validate(byte[] messageBytes) throws XMLValidationException,
                    C3PRBaseRuntimeException {
        try {
            parser.parse(new ByteArrayInputStream(messageBytes), new XMLParserErrorHandler());
        }
        catch (SAXParseException spe) {
        	StringBuffer sb = new StringBuffer( spe.toString() );
        	sb.append("\n Line number: " + spe.getLineNumber());
        	sb.append("\nColumn number: " + spe.getColumnNumber() );
        	sb.append("\n Public ID: " + spe.getPublicId() );
        	sb.append("\n System ID: " + spe.getSystemId() + "\n");
        	log.error("Invalid XML imported" +  sb.toString() );
           throw new XMLValidationException("XML is invalid against the expected schema", spe);
        }  
        catch (IOException e) {
            throw new C3PRBaseRuntimeException("Runtime exception", e);
        } catch (SAXException e) {
        	log.error("Invalid XML imported" +  e.toString() );
			e.printStackTrace();
		}

    }

    private class XMLParserErrorHandler extends DefaultHandler {

        public void error(SAXParseException saxParseException) throws SAXException {
            throw saxParseException;
        }

        public void fatalError(SAXParseException saxParseException) throws SAXException {
            throw saxParseException;
        }
    }
}
