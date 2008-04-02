package edu.duke.cabig.c3pr.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Comment;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;

import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.repository.StudySubjectRepository;
import edu.duke.cabig.c3pr.exception.C3PRCodedException;
import edu.duke.cabig.c3pr.exception.C3PRExceptionHelper;
import edu.duke.cabig.c3pr.service.StudySubjectXMLImporterService;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Utility class to import XML extracts of study
 * 
 * Created by IntelliJ IDEA. User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com Date: Jun 4, 2007 Time: 1:18:10 PM To change this
 *         template use File | Settings | File Templates.
 */

public class StudySubjectXMLImporterServiceImpl implements StudySubjectXMLImporterService {

    private C3PRExceptionHelper exceptionHelper;

    private MessageSource c3prErrorMessages;

    private XmlMarshaller marshaller;
    
    private StudySubjectRepository studySubjectRepository;

    private Logger log = Logger.getLogger(StudySubjectXMLImporterServiceImpl.class.getName());

    /**
     * Will parse an xml stream and create 1..many study subjects XML should have one or many
     * registration elements <registration> //registration serialization </registration>
     * 
     * Container to the <registration/> element is not important
     * 
     * @param xmlStream
     * @return
     * @throws Exception
     */
    @Transactional
    public List<StudySubject> importStudySubjects(InputStream xmlStream, File importXMLResult)
                    throws C3PRCodedException {
        List<StudySubject> studySubjectList = null;
        org.jdom.Document document = null;
        try {
            studySubjectList = new ArrayList<StudySubject>();
            document = new SAXBuilder().build(xmlStream);
            List<Element> elements = document
                            .getRootElement()
                            .getChildren(
                                            "registration",
                                            Namespace
                                                            .getNamespace("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain"));
            StudySubject studySubject = null;
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                try {
                    studySubject = importStudySubject(new XMLOutputter().outputString(element));
                    // once saved retreive persisted study
                    studySubjectList.add(studySubject);
                    element.addContent(new Comment("Successfull Import"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                    log.debug(e.getMessage());
                    element.addContent(new Comment("Error while importing: " + e.getMessage()));
                }
                new XMLOutputter(Format.getPrettyFormat()).output(document, new FileWriter(
                                importXMLResult));
            }
        }
        catch (Exception e) {
            throw this.exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.ERROR_UNMARSHALLING"), e);
        }
        return studySubjectList;
    }

    public StudySubject importStudySubject(String registrationXml) throws C3PRCodedException {
        StudySubject studySubject = null;
        try {
            studySubject = (StudySubject) marshaller.fromXML(new StringReader(registrationXml));
        }
        catch (XMLUtilityException e) {
            throw this.exceptionHelper.getException(
                            getCode("C3PR.EXCEPTION.REGISTRATION.IMPORT.ERROR_UNMARSHALLING"), e);
        }
//        this.validate(studySubject);
        return studySubjectRepository.importStudySubject(studySubject);
    }

    
    // setters for spring
    public XmlMarshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(XmlMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setC3prErrorMessages(MessageSource errorMessages) {
        c3prErrorMessages = errorMessages;
    }

    public void setExceptionHelper(C3PRExceptionHelper exceptionHelper) {
        this.exceptionHelper = exceptionHelper;
    }

    private int getCode(String errortypeString) {
        return Integer.parseInt(this.c3prErrorMessages.getMessage(errortypeString, null, null));
    }

    public void setStudySubjectRepositoryNew(StudySubjectRepository studySubjectRepository) {
        this.studySubjectRepository = studySubjectRepository;
    }

}
