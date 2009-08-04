package edu.duke.cabig.c3pr.xml;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XMLTransformerTest extends AbstractXMLMarshalling{
	
	
		String schemaFileName = "ccts-domain.xsd";
	 
	 /**
	     * @throws Exception
	     * @Before
	     */
	    protected void setUp() throws Exception {
	        super.setUp(); // To change body of overridden methods use File | Settings | File
	                        // Templates.
	    }
    
    public void testStudyTransformation() throws Exception{
        String xslName="ccts-study-transformer.xsl";
        String sampleXml="ccts-sample-study.xml";
        try {
            // validate the marshalled message
//        	 byte[] messageBytes = new XMLTransformer().transform(readFile(xslName), readFile(sampleXml)).getBytes();
//             parser.parse(new ByteArrayInputStream(messageBytes), new MyHandler());
             
             // Added these after multi-site
             
	        InputStream xslInputStream=getClass().getClassLoader().getResourceAsStream(xslName);
	        InputStream xmlInputStream=getClass().getClassLoader().getResourceAsStream(sampleXml);
	        OutputStream outputStream=new ByteArrayOutputStream();
	        new XMLTransformer().transform(new StreamSource(xslInputStream), new StreamSource(xmlInputStream), new StreamResult(outputStream));
        }
        catch (Exception x) {
            fail(x.getMessage());
        }
        System.out.println(new XMLTransformer().transform(readFile(xslName), readFile(sampleXml)));
        // System.out.println(outputStream.toString());
    }
    public void testStudyTransformationFromString() throws Exception{
        String xslName="ccts-study-transformer.xsl";
        String sampleXml="c3pr-sample-study.xml";
        System.out.println(new XMLTransformer().transform(readFile(xslName), readFile(sampleXml)));
    }
    
    public void testRegistrationTransformationFromString() throws Exception{
        String xslName="ccts-registration-transformer.xsl";
        String sampleXml="c3pr-sample-registration.xml";
        System.out.println(new XMLTransformer().transform(readFile(xslName), readFile(sampleXml)));
    }
    
    private String readFile(String filename) throws Exception{
        java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(
                        Thread.currentThread().getContextClassLoader().getResourceAsStream(
                                        filename)));
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
            return(sb.toString());
        }
    }
    
}
