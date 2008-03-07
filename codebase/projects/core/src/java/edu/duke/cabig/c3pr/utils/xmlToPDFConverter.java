package edu.duke.cabig.c3pr.utils;


//Java
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.ExceptionUtil;
import org.apache.avalon.framework.logger.ConsoleLogger;
import org.apache.avalon.framework.logger.Logger;
import org.apache.fop.apps.Driver;
import org.apache.fop.apps.FOPException;
import org.apache.fop.messaging.MessageHandler;

public class xmlToPDFConverter {
    /**
     * Logger for this class
     */
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(xmlToPDFConverter.class);

    public xmlToPDFConverter() {
    }

    public void convertXML2PDF(File xml, File xslt, File pdf) throws IOException, FOPException,
                    TransformerException {
        // Construct driver
        Driver driver = new Driver();

        // Setup logger
        Logger loggerLocal = new ConsoleLogger(ConsoleLogger.LEVEL_INFO);
        driver.setLogger(loggerLocal);
        MessageHandler.setScreenLogger(loggerLocal);

        // Setup Renderer (output format)
        driver.setRenderer(Driver.RENDER_PDF);

        // Setup output
        OutputStream out = new java.io.FileOutputStream(pdf);
        try {
            driver.setOutputStream(out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslt));

            // Setup input for XSLT transformation
            Source src = new StreamSource(xml);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(driver.getContentHandler());

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        }
        finally {
            out.close();
        }
    }

    public void convertXML2PDF_v1(File xml, File xslt, File pdf) throws IOException, FOPException,
                    TransformerException {
        // Construct driver
        System.setProperty("org.xml.sax.parser", "org.apache.xerces.parsers.SAXParser");
        Driver driver = new Driver();

        // Setup logger
        Logger logger = new ConsoleLogger(ConsoleLogger.LEVEL_INFO);
        driver.setLogger(logger);
        MessageHandler.setScreenLogger(logger);

        // Setup Renderer (output format)
        driver.setRenderer(Driver.RENDER_PDF);

        // Setup output
        OutputStream out = new java.io.FileOutputStream(pdf);
        try {
            driver.setOutputStream(out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslt));

            // Setup input for XSLT transformation
            Source src = new StreamSource(xml);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(driver.getContentHandler());

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        }
        finally {
            out.close();
        }
    }

    public void convertXML2PDF_v1(Source src, File xslt, File pdf) throws IOException,
                    FOPException, TransformerException {
        // Construct driver
        System.setProperty("org.xml.sax.parser", "org.apache.xerces.parsers.SAXParser");
        Driver driver = new Driver();

        // Setup logger
        Logger logger = new ConsoleLogger(ConsoleLogger.LEVEL_INFO);
        driver.setLogger(logger);
        MessageHandler.setScreenLogger(logger);

        // Setup Renderer (output format)
        driver.setRenderer(Driver.RENDER_PDF);

        // Setup output
        OutputStream out = new java.io.FileOutputStream(pdf);
        try {
            driver.setOutputStream(out);

            // Setup XSLT
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xslt));

            // Setup input for XSLT transformation
            // Source src = new StreamSource(xml);

            // Resulting SAX events (the generated FO) must be piped through to FOP
            Result res = new SAXResult(driver.getContentHandler());

            // Start XSLT transformation and FOP processing
            transformer.transform(src, res);
        }
        finally {
            out.close();
        }
    }

    public static void main(String[] args) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("main(String[]) - FOP ExampleXML2PDF\n");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("main(String[]) - Preparing...");
            }

            // Setup directories
            File baseDir = null;
            if (ApplicationUtils.isUnix()) baseDir = new File("/local/content/c3pr/letter/");
            else baseDir = new File("C:\\c3pr\\letter");
            File outDir = new File(baseDir, "out");
            outDir.mkdirs();

            // Setup input and output files
            File xmlfile = new File(baseDir, "letter.xml");
            File xsltfile = new File(baseDir, "letter.xsl");
            File pdffile = new File(outDir, "ResultLetter.pdf");

            if (logger.isDebugEnabled()) {
                logger.debug("main(String[]) - Input: XML (" + xmlfile + ")");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("main(String[]) - Stylesheet: " + xsltfile);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("main(String[]) - Output: PDF (" + pdffile + ")");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("main(String[])");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("main(String[]) - Transforming...");
            }

            xmlToPDFConverter app = new xmlToPDFConverter();
            app.convertXML2PDF_v1(xmlfile, xsltfile, pdffile);

            if (logger.isDebugEnabled()) {
                logger.debug("main(String[]) - Success!");
            }
        }
        catch (Exception e) {
            logger.error("main(String[]) - " + ExceptionUtil.printStackTrace(e), e);
            System.exit(-1);
        }
    }

}
