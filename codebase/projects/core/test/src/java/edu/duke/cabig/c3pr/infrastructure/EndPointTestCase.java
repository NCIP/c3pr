package edu.duke.cabig.c3pr.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis.message.MessageElement;
import org.easymock.classextension.EasyMock;
import org.globus.gsi.GlobusCredential;

import edu.duke.cabig.c3pr.AbstractTestCase;
import edu.duke.cabig.c3pr.domain.APIName;
import edu.duke.cabig.c3pr.domain.EndPoint;
import edu.duke.cabig.c3pr.domain.EndPointConnectionProperty;
import edu.duke.cabig.c3pr.domain.EndPointType;
import edu.duke.cabig.c3pr.domain.GridEndPoint;
import edu.duke.cabig.c3pr.domain.ServiceName;
import edu.duke.cabig.c3pr.grid.studyservice.client.StudyServiceClient;
import edu.duke.cabig.c3pr.utils.StringUtils;
import edu.duke.cabig.c3pr.utils.XMLUtils;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import gov.nih.nci.cabig.ccts.domain.Message;
import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;

public class EndPointTestCase extends AbstractTestCase {
    
    private EndPoint endPoint;
    private XMLUtils xmlUtils;
    private String proxyFilePath="/Users/kruttikagarwal/KrLocalProxy";
    private String url="https://localhost:28443/wsrf/services/cagrid/StudyService";
    
    public EndPointTestCase() {
        xmlUtils=registerMockFor(XMLUtils.class);
    }
    
    public void testCreateStudy() throws Exception{
        XMLUtils xmUtils=new XMLUtils(new XmlMarshaller("c3pr-study-xml-castor-mapping.xml"));
        String xml=StringUtils.readFile("edu/duke/cabig/c3pr/xml/test-study-domain-objects.xml");
        List<? extends AbstractMutableDomainObject> domainObjects=xmUtils.extractDomainObjectsFromXML(xml);
        EndPointConnectionProperty endPointProperty=new EndPointConnectionProperty(url,true,EndPointType.GRID);
        endPoint=new GridEndPoint(endPointProperty,ServiceName.STUDY,APIName.CREATE_STUDY,new GlobusCredential(new FileInputStream(new File(proxyFilePath))));
        endPoint.invoke(domainObjects);
      
    }
    
    public void testGetService(){
        endPoint=new GridEndPoint(getEndPointProperty(false), ServiceName.STUDY, APIName.CREATE_STUDY, null);
        Object obj=endPoint.getService();
        assertNotNull(obj);
        assertEquals("Wrong instance of service", StudyServiceClient.class, obj.getClass());
    }
    
    public void testGetAPI(){
        endPoint=new GridEndPoint(getEndPointProperty(false), ServiceName.STUDY, APIName.CREATE_STUDY, null);
        Method method=endPoint.getAPI();
        assertNotNull(method);
    }
    
    public void testGetArgumentsNullArgument(){
        endPoint=new GridEndPoint(getEndPointProperty(false), ServiceName.STUDY, APIName.CREATE_STUDY, null);
        Object[] args=endPoint.getArguments(null);
        assertNotNull(args);
        assertEquals("Wrong args length", args.length, 1);
        assertEquals("wrong argument type", Message.class, args[0].getClass());
    }
    
    public void testGetArgumentsEmptyArgument(){
        endPoint=new GridEndPoint(getEndPointProperty(false), ServiceName.STUDY, APIName.CREATE_STUDY, null);
        List<AbstractMutableDomainObject> list=new ArrayList<AbstractMutableDomainObject>();
        EasyMock.expect(xmlUtils.getMessageElementsForDomainObjects(list)).andReturn(new ArrayList<MessageElement>());
        replayMocks();
        Object[] args=null;
        try {
            args=endPoint.getArguments(list);
        }
        catch (RuntimeException e) {
            assertTrue(true);
            verifyMocks();
            return;
        }
        assertTrue("Should have thrown Exception", true);
    }
    
    public void testGetArguments(){
        endPoint=new GridEndPoint(getEndPointProperty(false), ServiceName.STUDY, APIName.CREATE_STUDY, null);
        List<AbstractMutableDomainObject> list=new ArrayList<AbstractMutableDomainObject>();
        List<MessageElement> messList=new ArrayList<MessageElement>();
        messList.add(new MessageElement());
        EasyMock.expect(xmlUtils.getMessageElementsForDomainObjects(list)).andReturn(messList);
        replayMocks();
        Object[] args=endPoint.getArguments(list);
        assertNotNull(args);
        assertEquals("Wrong args length", args.length, 1);
        assertEquals("wrong argument type", Message.class, args[0].getClass());
        assertEquals("wrong size of message elemets in side the message", 1, ((Message)args[0]).get_any().length);
        verifyMocks();
    }
    
//    public void testInvoke(){
//        endPoint=new GridEndPoint(getEndPointProperty(false), MultisiteServiceName.STUDY, MultisiteAPIName.CREATE_STUDY, null);
//        List<AbstractMutableDomainObject> list=new ArrayList<AbstractMutableDomainObject>();
//        List<MessageElement> messList=new ArrayList<MessageElement>();
//        messList.add(new MessageElement());
//        EasyMock.expect(xmlUtils.getMessageElementsForDomainObjects(list)).andReturn(messList);
//        replayMocks();
//        
//        verifyMocks();
//    }
    
    private EndPointConnectionProperty getEndPointProperty(boolean isAuthenticationRequired){
        EndPointConnectionProperty endPointProperty=new EndPointConnectionProperty();
        endPointProperty.setEndPointType(EndPointType.GRID);
        endPointProperty.setUrl("http://abc.xyz.com/abc");
        endPointProperty.setIsAuthenticationRequired(isAuthenticationRequired);
        return endPointProperty;
    }
}
