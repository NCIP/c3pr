package edu.duke.cabig.c3pr.utils;

import gov.nih.nci.common.exception.XMLUtilityException;
import gov.nih.nci.common.util.Marshaller;
import gov.nih.nci.common.util.Unmarshaller;
import gov.nih.nci.common.util.caCOREMarshaller;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * This is a utility class to
 * marshall and unmarshall domain objects.
 *
 * Implements caCORE Marshaller and UnMarshaller interface
 *
 * to/from XML
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Dec 7, 2006
 * Time: 11:52:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class XmlMarshaller implements Marshaller, Unmarshaller{

    private gov.nih.nci.common.util.caCOREMarshaller marshaller;
    private gov.nih.nci.common.util.caCOREUnmarshaller unmarshaller;
    private Logger log = Logger.getLogger(XmlMarshaller.class.getName());

    // Override the default by calling setCastorMappingFile
    private String castorMappingFile = "c3pr-castor-mapping.xml";

    /**
     * Constructor will throw an exception
     * if mapping file is invalid or not found
     *
     * @throws XMLUtilityException
     */
    public XmlMarshaller() throws XMLUtilityException {
        setMarshaller();
        setUnmarshaller();
    }


    private void setMarshaller() throws XMLUtilityException {
        marshaller = new caCOREMarshaller();
        marshaller.setMapping(this.getMapping());
    }


    private void setUnmarshaller() throws XMLUtilityException {
        unmarshaller = new gov.nih.nci.common.util.caCOREUnmarshaller();
        unmarshaller.setMapping(getMapping());
    }


    /**
     * c3prv2 has its own mapping file
     *
     * @return default mapping file being used for xml serialziation/deserialization
     */
    public Mapping getMapping() throws XMLUtilityException {
        try {
            EntityResolver resolver = new EntityResolver() {
                public InputSource resolveEntity(String publicId, String systemId) {
                    if (publicId.equals("-//EXOLAB/Castor Object Mapping DTD Version 1.0//EN")) {
                        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("mapping.dtd");
                        return new InputSource(in);
                    }
                    return null;
                }
            };

            org.xml.sax.InputSource mappIS = new org.xml.sax.InputSource(Thread.currentThread().getContextClassLoader().getResourceAsStream(castorMappingFile));
            Mapping localMapping = new Mapping();
            localMapping.setEntityResolver(resolver);
            localMapping.loadMapping(mappIS);
            return localMapping;
        } catch (Exception e) {
            log.error("Error reading default xml mapping file " + e.getMessage());  //To change body of catch statement use File | Settings | File Templates.
            throw new XMLUtilityException("Error reading default xml mapping file " + e.getMessage(), e);
        }
    }


    /**
     * Will serialize a c3prv2 domain object
     * into XML
     *
     * @param beanObject
     * @return XML String
     * @throws XMLUtilityException
     */
    public String toXML(Object beanObject) throws XMLUtilityException {
        StringWriter strWriter = new StringWriter();
        toXML(beanObject, strWriter);
        return strWriter.toString();

    }

    /**
     * Will serialize a c3prv2 domain object
     * into XML
     *
     * @param beanObject
     * @throws XMLUtilityException
     * */
    public void toXML(Object beanObject, Writer stream) throws XMLUtilityException {
        marshaller.toXML(beanObject, stream);
    }

    /**
     * Instanties an object from an xml File that contains the serialized
     * output of that object.
     *
     * @param xmlFile
     * @return
     * @throws XMLUtilityException
     */
    public Object fromXML(File xmlFile) throws XMLUtilityException {
        Object beanObject = null;
        beanObject = unmarshaller.fromXML(xmlFile);
        return beanObject;
    }


    /**
     * Instantiates an object from xml input
     * that contains the serialized
     * output of that object.
     *
     * @param input Reader type
     * @return Instantiated object
     * @throws XMLUtilityException
     */

    public Object fromXML(Reader input) throws XMLUtilityException {
        Object beanObject;
        beanObject = unmarshaller.fromXML(input);
        return beanObject;
    }

    /**
     * Will return the base castor
     * marshaller
     *
     * @return
     * @throws XMLUtilityException
     */
    public Object getBaseMarshaller() throws XMLUtilityException {
        return marshaller.getBaseMarshaller();
    }

    /**
     *
     * @return Castor Unmarshaller
     */
    public Object getBaseUnmarshaller() {
        return unmarshaller.getBaseUnmarshaller();
    }


	public String getCastorMappingFile() {
		return castorMappingFile;
	}


	public void setCastorMappingFile(String castorMappingFile) {
		this.castorMappingFile = castorMappingFile;
	}

}
