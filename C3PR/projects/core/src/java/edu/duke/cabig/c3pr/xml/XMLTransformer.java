package edu.duke.cabig.c3pr.xml;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import edu.duke.cabig.c3pr.exception.C3PRBaseException;
import edu.duke.cabig.c3pr.exception.C3PRBaseRuntimeException;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;

public class XMLTransformer {

    public void transform(Source xsltSource, Source xmlSource, Result result){
        try {
            Transformer transformer=TransformerFactory.newInstance().newTransformer(xsltSource);
            transformer.transform(xmlSource, result);
        }
        catch (Exception e) {
            throw new C3PRBaseRuntimeException(e.getMessage());
        }
    }
    
    public String transform(String xsltSource, String xmlSource){
        OutputStream outputStream=new ByteArrayOutputStream();
        transform(new StreamSource(new StringReader(xsltSource)), new StreamSource(new StringReader(xmlSource)), new StreamResult(outputStream));
        return outputStream.toString();
    }
}
