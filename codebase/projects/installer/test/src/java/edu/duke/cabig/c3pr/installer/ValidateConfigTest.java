package edu.duke.cabig.c3pr.installer;

import junit.framework.TestCase;
import org.tp23.antinstaller.runtime.ConfigurationLoader;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;

/**
 * Test cases to validate the installer config
 * and build files
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jul 3, 2007
 * Time: 11:24:06 AM
 * To change this template use File | Settings | File Templates.
 *
 * @testType unit
 */


public class ValidateConfigTest extends TestCase {

    File configFile;
    InputStream buildFile;


    @Override
    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.

        //read the config file and build file from the classpath
        configFile = new File(Thread.currentThread().getContextClassLoader().getResource(ConfigurationLoader.INSTALLER_CONFIG_FILE).getFile());
        buildFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("build.xml");
    }


    public void testConfigFileExists(){
        try {
            assertTrue(configFile.exists());
            assertTrue(buildFile.available()>-1);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
    /**
     * Validate the installer config file
     */
    public void testConfigFileValid() {
        int ret = 1;
        try {
            ConfigurationLoader configurationLoader = new ConfigurationLoader();
            configurationLoader.readConfig(configFile.getParentFile(), ConfigurationLoader.INSTALLER_CONFIG_FILE);
            ret = configurationLoader.validate();

            if (ret != 0) {
                fail("Installer config file is invalid");
            }
        }
        catch (Exception ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Validate the installer build file
     */
    public void testBuildFileValid() {
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();
            parser.parse(buildFile, new DefaultHandler() {
                public void error(SAXParseException e) throws SAXException {
                    throw e;
                }

                public void fatalError(SAXParseException e) throws SAXException {
                    throw e;
                }
            });

        }
        catch (Exception ex) {
            fail(ex.getMessage());
        }

    }


}
