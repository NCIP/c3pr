/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See https://github.com/NCIP/c3pr/blob/gh-pages/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.AbstractTestCase;


public class XMLXSDValidationTest extends AbstractTestCase {

	private XMLParser xmlParser;

	/**
	 * @throws Exception
	 * @Before
	 */
	protected void setUp() throws Exception {
		super.setUp(); // To change body of overridden methods use File |
						// Settings | File
		// Templates.
		xmlParser= new XMLParser("c3pr-domain.xsd");
	}

	public void testRegistrationXML() throws Exception {
		String sampleXmlFileName = "c3pr-sample-registration.xml";
		String sampleXml= readFile(sampleXmlFileName);
		System.out.println(sampleXml);
		xmlParser.validate(sampleXml.getBytes());
	}
	
	public void testStudyXML() throws Exception {
		String sampleXmlFileName = "c3pr-sample-study.xml";
		String sampleXml= readFile(sampleXmlFileName);
		System.out.println(sampleXml);
		xmlParser.validate(sampleXml.getBytes());
	}
	
	public void testRegistrationImportXML() throws Exception {
		String sampleXmlFileName = "c3pr-sample-registration-import.xml";
		String sampleXml= readFile(sampleXmlFileName);
		System.out.println(sampleXml);
		xmlParser.validate(sampleXml.getBytes());
	}
	
	public void testStudyImportXML() throws Exception {
		String sampleXmlFileName = "c3pr-sample-study-import.xml";
		String sampleXml= readFile(sampleXmlFileName);
		System.out.println(sampleXml);
		xmlParser.validate(sampleXml.getBytes());
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
