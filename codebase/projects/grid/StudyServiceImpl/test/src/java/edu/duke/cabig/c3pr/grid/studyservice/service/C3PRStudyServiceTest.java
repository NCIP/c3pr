package edu.duke.cabig.c3pr.grid.studyservice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.axis.message.MessageElement;
import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.esb.DelegatedCredential;
import edu.duke.cabig.c3pr.grid.client.StudyServiceClient;
import edu.duke.cabig.c3pr.infrastructure.MultisiteDelegatedCredentialProvider;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import gov.nih.nci.cagrid.common.Utils;

public class C3PRStudyServiceTest extends TestCase {

    private String proxyFilePath="/Users/kruttikagarwal/KrLocalProxy";
    private String url="https://localhost:28443/wsrf/services/cagrid/StudyService";
    
    public void testGlobusCredential() throws Exception{
        MultisiteDelegatedCredentialProvider multisiteDelegatedCredentialProvider=new MultisiteDelegatedCredentialProvider();
        multisiteDelegatedCredentialProvider.setIdpUrl("https://dorian.training.cagrid.org:8443/wsrf/services/cagrid/Dorian");
        multisiteDelegatedCredentialProvider.setIfsUrl("https://dorian.training.cagrid.org:8443/wsrf/services/cagrid/Dorian");
        DelegatedCredential credentialProvider=multisiteDelegatedCredentialProvider;
        StudyServiceClient studyServiceClient=new StudyServiceClient(url,credentialProvider.getCredential());
        studyServiceClient.createStudy(new Message());
    }
    
    public void testCreateStudy() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<MessageElement> messageElements=xmUtils.getMessageElementsForDomainObjects(domainObjects);
        Message message=new Message();
        MessageElement[] messageElementsArray=new MessageElement[messageElements.size()];
        for (int i=0 ; i< messageElements.size() ; i++)
            messageElementsArray[i]=messageElements.get(i);
        message.set_any(messageElementsArray);
        getStudyServiceClient().createStudy(message);
        
    }
    
    public void testOpenStudy() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<MessageElement> messageElements=xmUtils.getMessageElementsForDomainObjects(domainObjects);
        Message message=new Message();
        MessageElement[] messageElementsArray=new MessageElement[messageElements.size()];
        for (int i=0 ; i< messageElements.size() ; i++)
            messageElementsArray[i]=messageElements.get(i);
        message.set_any(messageElementsArray);
        getStudyServiceClient().openStudy(message);
        
    }
    
    public void testApproveStudySiteForActivation() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<MessageElement> messageElements=xmUtils.getMessageElementsForDomainObjects(domainObjects);
        Message message=new Message();
        MessageElement[] messageElementsArray=new MessageElement[messageElements.size()];
        for (int i=0 ; i< messageElements.size() ; i++)
            messageElementsArray[i]=messageElements.get(i);
        message.set_any(messageElementsArray);
        getStudyServiceClient().approveStudySiteForActivation(message);
        
    }
    
    public void testActivateStudySite() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<MessageElement> messageElements=xmUtils.getMessageElementsForDomainObjects(domainObjects);
        Message message=new Message();
        MessageElement[] messageElementsArray=new MessageElement[messageElements.size()];
        for (int i=0 ; i< messageElements.size() ; i++)
            messageElementsArray[i]=messageElements.get(i);
        message.set_any(messageElementsArray);
        getStudyServiceClient().activateStudySite(message);
        
    }
    
    public void testCloseStudy() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<MessageElement> messageElements=xmUtils.getMessageElementsForDomainObjects(domainObjects);
        Message message=new Message();
        MessageElement[] messageElementsArray=new MessageElement[messageElements.size()];
        for (int i=0 ; i< messageElements.size() ; i++)
            messageElementsArray[i]=messageElements.get(i);
        message.set_any(messageElementsArray);
        getStudyServiceClient().closeStudy(message);
        
    }
    
    public void testCloseStudySite() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<MessageElement> messageElements=xmUtils.getMessageElementsForDomainObjects(domainObjects);
        Message message=new Message();
        MessageElement[] messageElementsArray=new MessageElement[messageElements.size()];
        for (int i=0 ; i< messageElements.size() ; i++)
            messageElementsArray[i]=messageElements.get(i);
        message.set_any(messageElementsArray);
        getStudyServiceClient().closeStudySite(message);
        
    }
    
    public void testDeserialization() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        List<MessageElement> messageElements=xmUtils.getMessageElementsForDomainObjects(domainObjects);
        Message message=new Message();
        MessageElement[] messageElementsArray=new MessageElement[messageElements.size()];
        for (int i=0 ; i< messageElements.size() ; i++)
            messageElementsArray[i]=messageElements.get(i);
        message.set_any(messageElementsArray);
        StringWriter stringWriter=new StringWriter();
        Utils.serializeObject(message, new QName("gme://ccts.cabig/1.0/gov.nih.nci.cabig.ccts.domain"), stringWriter);
        String serialized=stringWriter.toString();
        System.out.println("serialized---------");
        System.out.println(serialized);
        System.out.println("serialized end---------");
        String domainXml=serialized.substring(serialized.indexOf('>')+1, serialized.lastIndexOf('<'));
        System.out.println("domainXml---------");
        System.out.println(domainXml);
        System.out.println("domainXml end---------");
        String messageXML="<message>"+domainXml+"</message>";
        System.out.println("messageXML---------");
        System.out.println(messageXML);
        System.out.println("serialized end---------");
        Message deserialized=(Message)Utils.deserializeObject(new StringReader(messageXML), Message.class);
        List<AbstractMutableDomainObject> list=xmUtils.extractDomainObjectsFromXML(messageXML);
        assertEquals("Wrong size", 5, list.size());
    }
    
    public void testFinally(){
        try{
            List list=new ArrayList();
            list.get(5);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }finally{
            System.out.println("in finally");
        }
        System.out.println("in end");
    }
    
    private StudyServiceClient getStudyServiceClient() throws Exception{
        return new StudyServiceClient(url,
                        new GlobusCredential(new FileInputStream(
                                        new File(proxyFilePath))));
    }
    
    
}
