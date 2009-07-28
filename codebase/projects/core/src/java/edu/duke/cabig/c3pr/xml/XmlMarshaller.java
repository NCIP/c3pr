package edu.duke.cabig.c3pr.xml;

import gov.nih.nci.common.exception.XMLUtilityException;
import gov.nih.nci.common.util.Marshaller;
import gov.nih.nci.common.util.Unmarshaller;
import gov.nih.nci.common.util.caCOREMarshaller;
import gov.nih.nci.common.util.caCOREUnmarshaller;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * This is a utility class to marshall and unmarshall domain objects. <p/> Implements caCORE
 * Marshaller and UnMarshaller interface <p/> to/from XML <p/> Created by IntelliJ IDEA. User: kherm
 * Date: Dec 7, 2006 Time: 11:52:37 AM To change this template use File | Settings | File Templates.
 */
public class XmlMarshaller implements Marshaller, Unmarshaller {

    private Logger log = Logger.getLogger(XmlMarshaller.class.getName());
    
    private 

    gov.nih.nci.common.util.caCOREMarshaller marshaller;

    gov.nih.nci.common.util.caCOREUnmarshaller unmarshaller;

    // Override the default by calling setMappingFile
    private String mappingFile = "c3pr-study-xml-castor-mapping.xml";
    
    /** The common mapping files. The default value is set. This can be overriden */
    private List<String> commonMappingFiles= Arrays.asList("common-mapping.xml");

	public XmlMarshaller() {
        marshaller = new caCOREMarshaller();
        unmarshaller = new caCOREUnmarshaller();
    }

    public XmlMarshaller(String castorMappingFile) {
        this();
        this.mappingFile = castorMappingFile;
    }

    /**
     * Will serialize a c3prv2 domain object into XML
     * 
     * @param beanObject
     *                c3prv2 Object to be serailzed
     * @return XML String
     * @throws XMLUtilityException
     */
    public String toXML(Object beanObject) throws XMLUtilityException {
        StringWriter sw = new StringWriter();
        this.toXML(beanObject, sw);
        return sw.toString();
    }

    /**
     * Will serialize a c3prv2 domain object into XML
     * 
     * @param beanObject
     * @throws XMLUtilityException
     */
    public void toXML(Object beanObject, Writer stream) throws XMLUtilityException {
        // set mapping before marshalling
        getMarshaller().toXML(beanObject, stream);
    }

    /**
     * Instantiates an object from xml input that contains the serialized output of that object.
     * 
     * @param input
     *                Reader type
     * @return Instantiated object
     * @throws XMLUtilityException
     */
    public Object fromXML(Reader input) throws XMLUtilityException {
        return getUnmarshaller().fromXML(input);
    }

    public Object fromXML(File file) throws XMLUtilityException {
        return getUnmarshaller().fromXML(file);
    }

    private Marshaller getMarshaller() throws XMLUtilityException {
        marshaller.setMapping(getMapping());
        return marshaller;
    }

    private Unmarshaller getUnmarshaller() throws XMLUtilityException {
        unmarshaller.setMapping(getMapping());
        return unmarshaller;
    }

    public void setMappingFile(String mappingFile) {
        this.mappingFile = mappingFile;
    }

    /**
     * @return default mapping file being used for xml serialziation/deserialization
     * @throws XMLUtilityException
     */
    private Mapping getMapping() throws XMLUtilityException {
        try {
            EntityResolver resolver = new EntityResolver() {
                public InputSource resolveEntity(String publicId, String systemId) {
                    if (publicId.equals("-//EXOLAB/Castor Object Mapping DTD Version 1.0//EN")) {
                        InputStream in = Thread.currentThread().getContextClassLoader()
                                        .getResourceAsStream("mapping.dtd");
                        return new InputSource(in);
                    }
                    return null;
                }
            };
            Mapping localMapping = new Mapping();
            localMapping.setEntityResolver(resolver);
            log.debug("Loading common mappinf files.");
            for(String commonMappingFileName: commonMappingFiles){
            	InputSource commonMappingSource = new InputSource(Thread.currentThread()
                        .getContextClassLoader().getResourceAsStream(commonMappingFileName));
            	localMapping.loadMapping(commonMappingSource);
            }
            InputSource mappingFileSource = new InputSource(Thread.currentThread()
                            .getContextClassLoader().getResourceAsStream(this.mappingFile));
            log.debug("Using " + mappingFile + " mapping file.");
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(
                            Thread.currentThread().getContextClassLoader().getResourceAsStream(
                                            this.mappingFile)));
            StringBuffer sb = new StringBuffer();
            try {
                String newline = System.getProperty("line.separator");
                String tmp = null;
                while ((tmp = br.readLine()) != null) {
                    sb.append(tmp);
                    sb.append(newline);
                }
            }
            finally {
                if (br != null) br.close();
                log.debug(sb.toString());
            }
            localMapping.loadMapping(mappingFileSource);
            return localMapping;
        }
        catch (Exception e) {
            log.error("Error reading default xml mapping file " + e.getMessage()); // To change
            System.out.println(e);
            // body of catch
            // statement use
            // File |
            // Settings |
            // File
            // Templates.
            throw new XMLUtilityException("Error reading default xml mapping file "
                            + e.getMessage(), e);
        }
    }

    /**
     * @return Underlying Castor marshaller
     * @throws XMLUtilityException
     */
    public Object getBaseMarshaller() throws XMLUtilityException {
        return marshaller.getBaseMarshaller();
    }

    /**
     * @return Underlying Castor Unmarshaller
     */
    public Object getBaseUnmarshaller() {
        return unmarshaller.getBaseUnmarshaller();
    }
    
    /**
     * Sets the common mapping files that will be loaded each time.
     * 
     * @param commonMappingFiles the new common mapping files
     */
    public void setCommonMappingFiles(List<String> commonMappingFiles) {
		this.commonMappingFiles = commonMappingFiles;
	}
}
