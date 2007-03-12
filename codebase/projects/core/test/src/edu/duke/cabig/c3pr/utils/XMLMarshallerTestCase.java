package edu.duke.cabig.c3pr.utils;

import junit.framework.TestCase;
import org.exolab.castor.dsml.XML;
import edu.duke.cabig.c3pr.domain.StudyParticipantAssignment;
import edu.duke.cabig.c3pr.domain.HealthcareSite;
import edu.duke.cabig.c3pr.domain.StudySite;
import gov.nih.nci.common.exception.XMLUtilityException;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Mar 11, 2007
 * Time: 2:08:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class XMLMarshallerTestCase extends TestCase {
    XmlMarshaller marshaller;


    protected void setUp() throws Exception {
        marshaller = new XmlMarshaller();
    }

    public void testRegistrationSeriazlier(){
        StudyParticipantAssignment registration = new StudyParticipantAssignment();
        registration.setGridId("testGridID");
        registration.setName("testName");

        HealthcareSite site = new HealthcareSite();
        site.setGridId("HealthcareSite");

        StudySite studySite = new StudySite();
        studySite.setSite(site);

        registration.setStudySite(studySite);
        try {
            System.out.println(marshaller.toXML(registration));
        } catch (XMLUtilityException e) {
            fail(e.getMessage());
        }

    }




}
