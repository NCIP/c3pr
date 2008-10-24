package edu.duke.cabig.c3pr.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.output.XMLOutputter;
import org.xml.sax.InputSource;

import edu.duke.cabig.c3pr.domain.Address;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.domain.SystemAssignedIdentifier;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class XMLUtils {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(XMLUtils.class);

    private XmlMarshaller xmlMarshaller;

    public XMLUtils(XmlMarshaller xmlMarshaller) {
        this.xmlMarshaller = xmlMarshaller;
    }

    public static String toXml(StudySubject studySubject) throws RuntimeException {
        String ns = "http://semanticbits.com/registration.xsd";
        String xml = "";
        Element rootElement = new Element("registration", "p1", ns);
        rootElement.setAttribute("healthCareSiteGridId", studySubject.getStudySite()
                        .getHealthcareSite().getGridId());
        rootElement.setAttribute("studyGridId", studySubject.getStudySite().getStudy().getGridId());

        rootElement.addContent(new Element("informedConsentFormSignedDate", "p1", ns)
                        .setText(new SimpleDateFormat("yyyy-MM-dd").format(studySubject
                                        .getInformedConsentSignedDate())));
        rootElement.addContent(new Element("enrollmentDate", "p1", ns)
                        .setText(new SimpleDateFormat("yyyy-MM-dd").format(studySubject
                                        .getStartDate())));
        rootElement.addContent(new Element("studyParticipantIdentifier", "p1", ns)
                        .setText(StringUtils.getBlankIfNull(studySubject.getGridId())));
        ScheduledEpoch current = studySubject.getScheduledEpoch();
        if (current instanceof ScheduledEpoch) {
            ScheduledEpoch scheduledTreatmentEpoch = current;
            rootElement.addContent(new Element("eligibilityIndicator", "p1", ns)
                            .setText(StringUtils.getBlankIfNull(scheduledTreatmentEpoch
                                            .getEligibilityIndicator())));
        }
        Participant stPart = studySubject.getParticipant();
        Element participant = new Element("participant", "p1", ns);
        participant.setAttribute("participantGridId", StringUtils
                        .getBlankIfNull(stPart.getGridId()));
        participant.addContent(new Element("administrativeGenderCode", "p1", ns)
                        .setText(StringUtils.getBlankIfNull(stPart.getAdministrativeGenderCode())));
        participant.addContent(new Element("birthDate", "p1", ns).setText(new SimpleDateFormat(
                        "yyyy-MM-dd").format(stPart.getBirthDate())));
        participant.addContent(new Element("ethnicGroupCode", "p1", ns).setText(StringUtils
                        .getBlankIfNull(stPart.getEthnicGroupCode())));
        participant.addContent(new Element("firstName", "p1", ns).setText(StringUtils
                        .getBlankIfNull(stPart.getFirstName())));
        participant.addContent(new Element("lastName", "p1", ns).setText(StringUtils
                        .getBlankIfNull(stPart.getLastName())));
        participant.addContent(new Element("maritalStatusCode", "p1", ns).setText(""));
        participant.addContent(new Element("raceCode", "p1", ns).setText(StringUtils
                        .getBlankIfNull(stPart.getRaceCode())));
        List<SystemAssignedIdentifier> identifiers = stPart.getSystemAssignedIdentifiers();
        if (identifiers.size() == 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("toXml(StudySubject) - Participant Identifiers size is 0");
            }
            Element idTemp = new Element("identifier", "p1", ns);
            idTemp.addContent(new Element("source", "p1", ns).setText("NONE"));
            idTemp.addContent(new Element("type", "p1", ns).setText("NONE"));
            idTemp.addContent(new Element("value", "p1", ns).setText("NONE"));
            idTemp.addContent(new Element("isprimary", "p1", ns).setText("true"));
            participant.addContent(idTemp);
        }
        for (int i = 0; i < identifiers.size(); i++) {
            SystemAssignedIdentifier id = identifiers.get(i);
            Element idTemp = new Element("identifier", "p1", ns);
            idTemp.addContent(new Element("systemName", "p1", ns).setText(StringUtils
                            .getBlankIfNull(id.getSystemName())));
            idTemp.addContent(new Element("type", "p1", ns).setText(StringUtils.getBlankIfNull(id
                            .getType())));
            idTemp.addContent(new Element("value", "p1", ns).setText(StringUtils.getBlankIfNull(id
                            .getValue())));
            idTemp.addContent(new Element("isprimary", "p1", ns).setText(StringUtils
                            .getBlankIfNull(id.getPrimaryIndicator())));
            participant.addContent(idTemp);
        }
        Address add = stPart.getAddress();
        Element address = new Element("address", "p1", ns);
        address.addContent(new Element("city", "p1", ns).setText(StringUtils.getBlankIfNull(add
                        .getCity())));
        address.addContent(new Element("countryCode", "p1", ns).setText(StringUtils
                        .getBlankIfNull(add.getCountryCode())));
        address.addContent(new Element("postalCode", "p1", ns).setText(StringUtils
                        .getBlankIfNull(add.getPostalCode())));
        address.addContent(new Element("stateCode", "p1", ns).setText(StringUtils
                        .getBlankIfNull(add.getStateCode())));
        address.addContent(new Element("streetAddress", "p1", ns).setText(StringUtils
                        .getBlankIfNull(add.getStreetAddress())));
        participant.addContent(address);
        rootElement.addContent(participant);

        Study st = studySubject.getStudySite().getStudy();
        Element study = new Element("study", "p1", ns);
        identifiers = st.getSystemAssignedIdentifiers();
        if (identifiers.size() == 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("toXml(StudySubject) - Study Identifiers size is 0");
            }
            Element idTemp = new Element("identifier", "p1", ns);
            idTemp.addContent(new Element("source", "p1", ns).setText("NONE"));
            idTemp.addContent(new Element("type", "p1", ns).setText("NONE"));
            idTemp.addContent(new Element("value", "p1", ns).setText("NONE"));
            idTemp.addContent(new Element("isprimary", "p1", ns).setText("true"));
            study.addContent(idTemp);
        }
        for (int i = 0; i < identifiers.size(); i++) {
            SystemAssignedIdentifier id = identifiers.get(i);
            Element idTemp = new Element("identifier", "p1", ns);
            idTemp.addContent(new Element("systemName", "p1", ns).setText(StringUtils
                            .getBlankIfNull(id.getSystemName())));
            idTemp.addContent(new Element("type", "p1", ns).setText(StringUtils.getBlankIfNull(id
                            .getType())));
            idTemp.addContent(new Element("value", "p1", ns).setText(StringUtils.getBlankIfNull(id
                            .getValue())));
            idTemp.addContent(new Element("isprimary", "p1", ns).setText(StringUtils
                            .getBlankIfNull(id.getPrimaryIndicator())));
            study.addContent(idTemp);
        }
        rootElement.addContent(study);

        Element identifier = new Element("identifier", "p1", ns);
        identifier.addContent(new Element("source", "p1", ns).setText("c3pr"));
        identifier.addContent(new Element("type", "p1", ns).setText("Grid Identifier"));
        identifier.addContent(new Element("value", "p1", ns).setText(StringUtils
                        .getBlankIfNull(studySubject.getGridId())));
        identifier.addContent(new Element("isprimary", "p1", ns).setText("false"));
        rootElement.addContent(identifier);
        identifiers = studySubject.getSystemAssignedIdentifiers();
        for (int i = 0; i < identifiers.size(); i++) {
            SystemAssignedIdentifier id = identifiers.get(i);
            Element idTemp = new Element("identifier", "p1", ns);
            idTemp.addContent(new Element("systemName", "p1", ns).setText(StringUtils
                            .getBlankIfNull(id.getSystemName())));
            idTemp.addContent(new Element("type", "p1", ns).setText(StringUtils.getBlankIfNull(id
                            .getType())));
            idTemp.addContent(new Element("value", "p1", ns).setText(StringUtils.getBlankIfNull(id
                            .getValue())));
            idTemp.addContent(new Element("isprimary", "p1", ns).setText(StringUtils
                            .getBlankIfNull(id.getPrimaryIndicator())));
            rootElement.addContent(idTemp);
        }

        Document document = new Document(rootElement);
        try {
            xml = new XMLOutputter().outputString(document);
        }
        catch (RuntimeException e) {
            logger.error("toXml(StudySubject)", e);
        }
        return xml;
    }

    public <T extends AbstractMutableDomainObject> List<T> extractDomainObjectsFromXML(String xml) {
        List<T> domainObjects = new ArrayList<T>();
        org.jdom.Document document = null;
        try {
            document = new SAXBuilder().build(new StringReader(xml));
            List<Element> elements = document.getRootElement().getChildren();
            for (int i = 0; i < elements.size(); i++) {
                Element element = elements.get(i);
                OutputStream outputStream = new ByteArrayOutputStream();
                new XMLOutputter().output(element, outputStream);
                domainObjects.add((T) xmlMarshaller.fromXML(new StringReader(outputStream
                                .toString())));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return domainObjects;
    }

    public <T extends AbstractMutableDomainObject> List<org.w3c.dom.Document> getXMLElementsForDomainObjects(
                    List<T> domainObjects) {
        List<org.w3c.dom.Document> elements = new ArrayList<org.w3c.dom.Document>();
        try {
            for (T t : domainObjects) {
                DOMParser domParser=new DOMParser();
                domParser.parse(new InputSource(new StringReader(xmlMarshaller.toXML(t))));
                elements.add(domParser.getDocument());
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return elements;
    }

    public <T extends AbstractMutableDomainObject> List<MessageElement> getMessageElementsForDomainObjects(
                    List<T> domainObjects) {
        List<org.w3c.dom.Document> elements = getXMLElementsForDomainObjects(domainObjects);
        List<MessageElement> messageElements = new ArrayList<MessageElement>();
        try {
            for (org.w3c.dom.Document d : elements) {
                messageElements.add(new MessageElement(d.getDocumentElement()));
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        return messageElements;
    }
}
