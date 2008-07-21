package edu.duke.cabig.c3pr.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XsltTransformer {

	/**
	 * 
	 * @param inXml
	 * @param xsltFile
	 * @return
	 * @throws Exception
	 */
	public String toXml(String inXml, String xsltFile) throws Exception {

		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(xsltFile);

		Source xmlSource = new StreamSource(new ByteArrayInputStream(inXml
				.getBytes()));
		// File xslt = new File(xsltFile);

		Source xsltSource = new StreamSource(stream);

		StringWriter outStr = new StringWriter();
		StreamResult result = new StreamResult(outStr);

		// the factory pattern supports different XSLT processors
		TransformerFactory transFact = TransformerFactory.newInstance();
		Transformer trans = transFact.newTransformer(xsltSource);

		trans.transform(xmlSource, result);// transform(xmlSource, new
											// StreamResult(System.out));

		return outStr.toString();
	}

	

}