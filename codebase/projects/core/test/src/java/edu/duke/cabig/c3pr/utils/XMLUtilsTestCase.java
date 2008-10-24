package edu.duke.cabig.c3pr.utils;

import java.io.StringReader;
import java.util.List;

import org.apache.axis.message.MessageElement;
import org.w3c.dom.Document;

import junit.framework.TestCase;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.OrganizationAssignedIdentifier;
import edu.duke.cabig.c3pr.domain.Participant;
import edu.duke.cabig.c3pr.domain.ScheduledArm;
import edu.duke.cabig.c3pr.domain.ScheduledEpoch;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.domain.StudyCoordinatingCenter;
import edu.duke.cabig.c3pr.domain.StudyInvestigator;
import edu.duke.cabig.c3pr.domain.StudySite;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class XMLUtilsTestCase extends TestCase {

    public void testExtractDomainObjectsFromRegistrationCastorXML() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-registration-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-registration-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        assertEquals("Wrong size of list", domainObjects.size(),4);
        assertEquals("Wrong deserialized domain object", StudySubject.class, domainObjects.get(0).getClass());
        assertEquals("Wrong deserialized domain object", Participant.class, domainObjects.get(1).getClass());
        assertEquals("Wrong deserialized domain object", ScheduledArm.class, domainObjects.get(2).getClass());
        assertEquals("Wrong deserialized domain object", ScheduledEpoch.class, domainObjects.get(3).getClass());
    }
    
    public void testExtractDomainObjectsFromStudyCastorXML1() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        assertEquals("Wrong size of list", domainObjects.size(),5);
        assertEquals("Wrong deserialized domain object", Study.class, domainObjects.get(0).getClass());
        assertEquals("Wrong deserialized domain object", OrganizationAssignedIdentifier.class, domainObjects.get(1).getClass());
        assertEquals("Wrong deserialized domain object", StudySite.class, domainObjects.get(2).getClass());
        assertEquals("Wrong deserialized domain object", StudyInvestigator.class, domainObjects.get(3).getClass());
        assertEquals("Wrong deserialized domain object", HealthcareSite.class, domainObjects.get(4).getClass());
    }
    
    public void testGetXMLElementsForDomainObjects0() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-registration-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-registration-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<Document> documents=xmUtils.getXMLElementsForDomainObjects(domainObjects);
        assertEquals("Wrong size of list", documents.size(),4);
        assertEquals("Wrong serialized domain object", "registration", documents.get(0).getDocumentElement().getNodeName());
        assertEquals("Wrong serialized domain object", "participant", documents.get(1).getDocumentElement().getNodeName());
        assertEquals("Wrong serialized domain object", "scheduledArm", documents.get(2).getDocumentElement().getNodeName());
        assertEquals("Wrong serialized domain object", "scheduledEpoch", documents.get(3).getDocumentElement().getNodeName());
    }
    
    public void testGetXMLElementsForDomainObjects1() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<Document> documents=xmUtils.getXMLElementsForDomainObjects(domainObjects);
        assertEquals("Wrong size of list", documents.size(),5);
        assertEquals("Wrong serialized domain object", "study", documents.get(0).getDocumentElement().getNodeName());
        assertEquals("Wrong serialized domain object", "organizationAssignedIdentifier", documents.get(1).getDocumentElement().getNodeName());
        assertEquals("Wrong serialized domain object", "studySite", documents.get(2).getDocumentElement().getNodeName());
        assertEquals("Wrong serialized domain object", "studyInvestigator", documents.get(3).getDocumentElement().getNodeName());
        assertEquals("Wrong serialized domain object", "healthcareSite", documents.get(4).getDocumentElement().getNodeName());
    }
    
    public void testGetMessageElementsForDomainObjects0() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-registration-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-registration-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<MessageElement> messageElements=xmUtils.getMessageElementsForDomainObjects(domainObjects);
        assertEquals("Wrong size of list", messageElements.size(),4);
        assertEquals("Wrong serialized domain object", "registration", messageElements.get(0).getName());
        assertEquals("Wrong serialized domain object", "participant", messageElements.get(1).getName());
        assertEquals("Wrong serialized domain object", "scheduledArm", messageElements.get(2).getName());
        assertEquals("Wrong serialized domain object", "scheduledEpoch", messageElements.get(3).getName());
    }
    
    public void testGetMessageElementsForDomainObjects1() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<MessageElement> messageElements=xmUtils.getMessageElementsForDomainObjects(domainObjects);
        assertEquals("Wrong size of list", messageElements.size(),5);
        assertEquals("Wrong serialized domain object", "study", messageElements.get(0).getName());
        assertEquals("Wrong serialized domain object", "organizationAssignedIdentifier", messageElements.get(1).getName());
        assertEquals("Wrong serialized domain object", "studySite", messageElements.get(2).getName());
        assertEquals("Wrong serialized domain object", "studyInvestigator", messageElements.get(3).getName());
        assertEquals("Wrong serialized domain object", "healthcareSite", messageElements.get(4).getName());
    }
}
