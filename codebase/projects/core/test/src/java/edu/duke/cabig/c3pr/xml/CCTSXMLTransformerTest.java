package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.AbstractTestCase;


public class CCTSXMLTransformerTest extends AbstractTestCase {

	private XMLParser xmlParser;

	/**
	 * @throws Exception
	 * @Before
	 */
	protected void setUp() throws Exception {
		super.setUp(); // To change body of overridden methods use File |
						// Settings | File
		// Templates.
		xmlParser= new XMLParser("ccts-domain.xsd");
	}

	public void testRegistrationTransformationFromString() throws Exception {
		String xslName = "ccts-registration-transformer.xsl";
		String sampleXml = "c3pr-sample-registration.xml";
		String cctsXml= new XMLTransformer().transform(readFile(xslName),
				readFile(sampleXml));
		System.out.println(cctsXml);
		xmlParser.validate(cctsXml.getBytes());
	}
	
	public void testStudyTransformationFromString() throws Exception {
		String xslName = "ccts-study-transformer.xsl";
		String sampleXml = "c3pr-sample-study.xml";
		String cctsXml= new XMLTransformer().transform(readFile(xslName),
				readFile(sampleXml));
		System.out.println(cctsXml);
		xmlParser.validate(cctsXml.getBytes());
	}

	private String readFile(String filename) throws Exception {
		java.io.BufferedReader br = new java.io.BufferedReader(
				new java.io.InputStreamReader(Thread.currentThread()
						.getContextClassLoader().getResourceAsStream(filename)));
		StringBuffer sb = new StringBuffer();
		try {
			String newline = System.getProperty("line.separator");
			String tmp = null;
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
				sb.append(newline);
			}
		} finally {
			if (br != null)
				br.close();
			return (sb.toString());
		}
	}

}
