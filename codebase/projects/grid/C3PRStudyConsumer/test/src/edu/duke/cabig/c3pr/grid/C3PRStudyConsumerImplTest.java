package edu.duke.cabig.c3pr.grid;

import gov.nih.nci.cagrid.common.Utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;

import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.grid.service.C3PRStudyConsumerImpl;
import edu.duke.cabig.c3pr.service.StudyService;
import edu.duke.cabig.c3pr.utils.ApplicationTestCase;
import org.xml.sax.SAXException;
import org.globus.wsrf.encoding.DeserializationException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Oct 10, 2007
 * Time: 9:58:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class C3PRStudyConsumerImplTest extends ApplicationTestCase {


    C3PRStudyConsumerImpl gridService;
    private String sampleMessage="SampleStudy.xml";


    protected void setUp() throws Exception {
        gridService = new C3PRStudyConsumerImpl();
    }

    public void testCreateOrUpdateStudy(){


        try {
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(sampleMessage);
            InputStreamReader reader = new InputStreamReader(is);
            InputStream resourceAsStream =Thread.currentThread().getContextClassLoader().getResourceAsStream("edu/duke/cabig/c3pr/grid/client/client-config.wsdd");

            Study study =
                    (Study)
                            Utils.deserializeObject(reader, Study.class,resourceAsStream);
            gridService.createOrUpdate(study);
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (DeserializationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RemoteException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


    }


}
