package edu.duke.cabig.c3pr.xml;

import edu.duke.cabig.c3pr.domain.ExclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.InclusionEligibilityCriteria;
import edu.duke.cabig.c3pr.domain.Study;
import gov.nih.nci.common.exception.XMLUtilityException;

import java.io.ByteArrayInputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Test serialization for the Study object
 *
 * @testType unit
 * <p/>
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: May 11, 2007
 * Time: 11:30:03 AM
 * To change this template use File | Settings | File Templates.
 */
public class StudyMarshallingTestCase extends AbstractXMLMarshalling {

    String marshalledStudy;


    /**
     * @throws Exception
     * @Before
     */
    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
    }


    /**
     * @throws Throwable
     * @Test main test method. Runs methods in a sequence.
     */
    public void testSerializationDeserializationTest() {
        //have to be run in order
        studySerializationTest();
        schemaValidationTest();
        studyDeserializationTest();
    }


    private void studySerializationTest() {
        try {
            marshalledStudy = marshaller.toXML(createDummyStudy(studyGridId));
            System.out.println(marshalledStudy);
            assertNotNull(marshalledStudy);
        } catch (XMLUtilityException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests if the message generated can be validated
     * against the schema
     */
    private void schemaValidationTest() {

        try {
            //validate the marshalled message
            byte[] messageBytes = marshalledStudy.getBytes();
            parser.parse(new ByteArrayInputStream(messageBytes), new MyHandler());
        }
        catch (Exception x) {
            fail(x.getMessage());
        }
    }


    private void studyDeserializationTest() {
        Reader messageReader = new StringReader(marshalledStudy);

        try {
            Study unmarshalledStudy = (Study) marshaller.fromXML(messageReader);
            assertNotNull(unmarshalledStudy);

            assertEquals(unmarshalledStudy.getGridId(), studyGridId);
// we never set this so it should be null
            assertNull(unmarshalledStudy.getDiseaseCategoryAsText());
        } catch (XMLUtilityException e) {
            fail(e.getMessage());
        }

    }


    private List<InclusionEligibilityCriteria> getInclusionEligibilityCriterias() {
        List<InclusionEligibilityCriteria> criterias = new ArrayList<InclusionEligibilityCriteria>();

        for (int i = 0; i <= 2; i++) {
            InclusionEligibilityCriteria criteria = new InclusionEligibilityCriteria();
            criteria.setGridId(strValue);
            criteria.setName(strValue);
            criteria.setQuestionNumber(i);
            criteria.setQuestionText(strValue);
            criterias.add(criteria);
        }
        return criterias;
    }

    private List<ExclusionEligibilityCriteria> getExclusionEligibilityCriterias() {
        List<ExclusionEligibilityCriteria> criterias = new ArrayList<ExclusionEligibilityCriteria>();

        for (int i = 1; i <= 2; i++) {
            ExclusionEligibilityCriteria criteria = new ExclusionEligibilityCriteria();
            criteria.setGridId(strValue);
            criteria.setName(strValue);
            criteria.setQuestionNumber(i);
            criteria.setQuestionText(strValue);
            criterias.add(criteria);
        }
        return criterias;
    }


}
