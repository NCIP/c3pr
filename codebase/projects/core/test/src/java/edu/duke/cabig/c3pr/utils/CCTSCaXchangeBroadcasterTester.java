package edu.duke.cabig.c3pr.utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import edu.duke.cabig.c3pr.dao.StudySubjectDao;
import edu.duke.cabig.c3pr.domain.StudySubject;
import edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Nov 21, 2007 Time: 3:20:35 PM To change this template
 * use File | Settings | File Templates.
 */
public class CCTSCaXchangeBroadcasterTester extends MasqueradingDaoTestCase<StudySubjectDao> {

    private DefaultCCTSMessageWorkflowCallbackFactory cctsMessageWorkflowCallbackFactory;

    private edu.duke.cabig.c3pr.esb.CCTSMessageBroadcaster messageBroadcaster;

    XmlMarshaller marshaller;

    protected void setUp() throws Exception {
        super.setUp(); // To change body of overridden methods use File | Settings | File
                        // Templates.
        cctsMessageWorkflowCallbackFactory = (DefaultCCTSMessageWorkflowCallbackFactory) getApplicationContext()
                        .getBean("cctsMessageWorkflowCallbackFactory");
        messageBroadcaster = (CCTSMessageBroadcaster) getApplicationContext().getBean(
                        "messageBroadcaster");
        marshaller = new XmlMarshaller((String) getApplicationContext().getBean(
                        "c3pr-xml-castorMapping"));
    }

    public void testCaXchangeMessageSender() throws Exception {
        for (StudySubject subject : getDao().getAll()) {
            messageBroadcaster.setNotificationHandler(cctsMessageWorkflowCallbackFactory
                            .createWorkflowCallback(subject));
            String message = marshaller.toXML(subject);
            System.out.println(message);
            Document messageDOM = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
                            new InputSource(new StringReader(message)));
            StringWriter stringOut = new StringWriter();
            OutputFormat format = new OutputFormat(messageDOM);
            XMLSerializer serial = new XMLSerializer(stringOut, format);
            serial.serialize(messageDOM);
            // Display the XML
            System.out.println(stringOut.toString());
            System.out.println(messageDOM.getDocumentElement().toString());
            messageBroadcaster.broadcast(message);
        }
    }

    /**
     * What dao class is the test trying to Masquerade
     * 
     * @return
     */
    public Class<StudySubjectDao> getMasqueradingDaoClassName() {
        return StudySubjectDao.class;
    }

}
