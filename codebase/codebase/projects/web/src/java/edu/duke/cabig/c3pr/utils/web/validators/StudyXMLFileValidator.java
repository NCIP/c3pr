package edu.duke.cabig.c3pr.utils.web.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.duke.cabig.c3pr.exception.XMLValidationException;
import edu.duke.cabig.c3pr.web.beans.FileBean;
import edu.duke.cabig.c3pr.xml.XMLParser;

/**
 * Will validate the xml import with the schema
 * 
 * Created by IntelliJ IDEA. User: kherm Date: Oct 16, 2007 Time: 2:46:33 PM To change this template
 * use File | Settings | File Templates.
 */
public class StudyXMLFileValidator implements Validator {

    public XMLParser xmlParser;

    public boolean supports(Class aClass) {
        return aClass.isAssignableFrom(FileBean.class);
    }

    public void validate(Object object, Errors errors) {
        FileBean xmlBean = (FileBean) object;
        try {
            xmlParser.validate(xmlBean.getFile());
        }
        catch (XMLValidationException e) {
            errors.reject("XML is Invalid " + e.getMessage());
        }
    }

    public XMLParser getXmlParser() {
        return xmlParser;
    }

    public void setXmlParser(XMLParser xmlParser) {
        this.xmlParser = xmlParser;
    }
}
