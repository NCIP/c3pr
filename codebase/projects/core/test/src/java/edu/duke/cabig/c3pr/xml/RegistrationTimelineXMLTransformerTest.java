/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.AbstractTestCase;


public class RegistrationTimelineXMLTransformerTest extends AbstractTestCase {

	private XMLParser xmlParser;

	/**
	 * @throws Exception
	 * @Before
	 */
	protected void setUp() throws Exception {
		super.setUp(); // To change body of overridden methods use File |
						// Settings | File
		// Templates.
		xmlParser= new XMLParser("registration-timeline.xsd");
	}

	public void testRegistrationTransformationFromString() throws Exception {
		String xslName = "registration-timeline.xsl";
		String sampleXml = "c3pr-sample-registration.xml";
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
