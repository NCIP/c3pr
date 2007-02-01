/*
 * @author <a href="mailto:oliver@wehrens.de">Oliver Wehrens</a>
 * @author <a href="mailto:novotny@aei.mpg.de">Jason Novotny</a>
 * @version $Id: PersistenceManagerXmlImpl.java 4496 2006-02-08 20:27:04Z wehrens $
 */
package org.gridlab.gridsphere.core.persistence.castor;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerException;
import org.gridlab.gridsphere.core.persistence.PersistenceManagerXml;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.net.URL;

/**
 * The PersistenceManagerXmlImpl provides easy access to marshal/unmarshal Java objects to XML files
 */
public class PersistenceManagerXmlImpl implements PersistenceManagerXml {

    protected final static PortletLog log = SportletLog.getInstance(PersistenceManagerXmlImpl.class);

    private List mappingPaths = new ArrayList();
    private String descriptorPath = null;
    private URL mappingURL = null;

    /**
     * PersistenceManagerXmlImpl default constructor
     */
    private PersistenceManagerXmlImpl() {
    }

    /**
     * Creates an instance of PersistenceManagerXmlImpl from a descriptor and mapping URL
     *
     * @param descriptorPath the descriptor location
     * @param mappingPath    the mapping location
     */
    public PersistenceManagerXmlImpl(String descriptorPath, String mappingPath) {
        this.descriptorPath = descriptorPath;
        addMappingPath(mappingPath);
    }

    /**
     * Creates an instance of PersistenceManagerXmlImpl from a descriptor and mapping URL
     *
     * @param descriptorPath the descriptor location
     * @param mappingURL    the mapping location expressed as a URL
     */
    public PersistenceManagerXmlImpl(String descriptorPath, URL mappingURL) {
        this.descriptorPath = descriptorPath;
        this.mappingURL = mappingURL;
    }

    public void addMappingPath(String path) {
        mappingPaths.add(path);
    }

    /**
     * Sets the mapping file path
     *
     * @param mappingPath the mapping file path
     */
    public void setMappingPath(String mappingPath) {
        mappingPaths.clear();
        mappingPaths.add(mappingPath);
    }

    /**
     * Return the mapping file path
     *
     * @return the mapping file path
     */
    public List getMappingPaths() {
        return mappingPaths;
    }

    /**
     * Sets the descriptor file path
     *
     * @param descriptorPath the file path of the descriptor
     */
    public void setDescriptorPath(String descriptorPath) {
        this.descriptorPath = descriptorPath;
    }

    /**
     * Returns the filename of the mappingfile
     *
     * @return name of the mappingfile
     */
    public String getDescriptorPath() {
        return descriptorPath;
    }

    /**
     * Marshals the given object to an xml file
     *
     * @param object object to be marshalled
     * @throws org.gridlab.gridsphere.core.persistence.PersistenceManagerException
     *                     if the configuration was wrong
     * @throws IOException if an I/O error occurs
     */
    public void save(Object object) throws PersistenceManagerException, IOException {
        try {
            Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(descriptorPath), "UTF-8"));
            FileWriter filewriter = new FileWriter(descriptorPath);
            Marshaller marshal = new Marshaller(w);
            Mapping map = new Mapping();

            Iterator iterMappingPaths = mappingPaths.iterator();
            while (iterMappingPaths.hasNext()) {
                String mappingPath = (String)iterMappingPaths.next();
                log.debug("Loading mapping path " + mappingPath);
                map.loadMapping(mappingPath);
            }

            marshal.setMapping(map);
            marshal.marshal(object);
            filewriter.close();
            Class cl = object.getClass();
            log.debug("Wrote object of type " + cl.getName() + " to XMLFile " + descriptorPath);
        } catch (ValidationException e) {
            log.error("Unable to marshal object: ", e);
            throw new PersistenceManagerException("Validation Error", e);
        } catch (MarshalException e) {
            log.error("Unable to marshal object: ", e);
            throw new PersistenceManagerException("Marshal Error: ", e);
        } catch (MappingException e) {
            log.error("Unable to marshal object: ", e.getException());
            e.printStackTrace();
            throw new PersistenceManagerException("Mapping Error", e);
        }
    }

    /**
     * restores an object from an xml file
     *
     * @return object which was unmarshalled
     * @throws PersistenceManagerException if restore was not succsessful
     * @throws IOException                 if there was a configurationerror
     */
    public Object load() throws IOException, PersistenceManagerException {
        Object object;
        try {
            log.debug("Using getConnectionURL() " + descriptorPath);
            InputSource xmlSource = new InputSource(descriptorPath);
            Mapping mapping = new Mapping();
            if (mappingURL != null) {
                mapping.loadMapping(mappingURL);
            } else {
                Iterator iterMappingPaths = mappingPaths.iterator();
                while (iterMappingPaths.hasNext()) {
                    String mappingPath = (String)iterMappingPaths.next();
                    log.debug("Loading mapping path " + mappingPath);
                    mapping.loadMapping(mappingPath);
                }
            }
            Unmarshaller unmarshal = new Unmarshaller(mapping);
            unmarshal.setValidation(true);
            unmarshal.setIgnoreExtraElements(true);
            unmarshal.setIgnoreExtraAttributes(true);
            object = unmarshal.unmarshal(xmlSource);
        } catch (MappingException e) {
            log.error("MappingException using " + descriptorPath + ": " + e.getException().toString());
            throw new PersistenceManagerException("Mapping Error" + e.getException().toString());
        } catch (MarshalException e) {
            log.error("MarshalException " + descriptorPath + "" + e.getMessage());
            throw new PersistenceManagerException("Marshal Error" + e.getMessage());
        } catch (ValidationException e) {
            log.error("ValidationException " + descriptorPath + "" + e.getMessage());
            throw new PersistenceManagerException("Validation Error" + e.getMessage());
        }
        return object;
    }


}


