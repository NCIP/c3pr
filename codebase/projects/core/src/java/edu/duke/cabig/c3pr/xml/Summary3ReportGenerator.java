package edu.duke.cabig.c3pr.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.duke.cabig.c3pr.utils.XsltTransformer;

public class Summary3ReportGenerator {
    
    protected final Log log = LogFactory.getLog(getClass());

    private String xslFOXsltFile = "summary3Report.xslt";

    public void generatePdf(String summary3ReportXml, String pdfOutFileName) throws Exception {

        XsltTransformer xsltTrans = new XsltTransformer();
        xsltTrans.toPdf(summary3ReportXml, pdfOutFileName, xslFOXsltFile);
    }

}
