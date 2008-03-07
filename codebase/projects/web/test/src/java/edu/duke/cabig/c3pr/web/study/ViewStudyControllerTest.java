package edu.duke.cabig.c3pr.web.study;

import static edu.duke.cabig.c3pr.C3PRUseCase.VIEW_STUDY;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;

import org.easymock.classextension.EasyMock;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import edu.duke.cabig.c3pr.C3PRUseCases;
import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;

/**
 * Created by IntelliJ IDEA. User: kherm
 * 
 * @author kherm manav.kher@semanticbits.com Date: Jul 18, 2007 Time: 1:48:37 PM To change this
 *         template use File | Settings | File Templates.
 */
@C3PRUseCases( { VIEW_STUDY })
public class ViewStudyControllerTest extends AbstractStudyControllerTest {

    private ViewStudyController controller;

    private XmlMarshaller mockMarshaller;

    @Override
    protected void setUp() throws Exception {
        super.setUp(); // To change body of overridden methods use File | Settings | File
                        // Templates.

        mockMarshaller = registerMockFor(XmlMarshaller.class, XmlMarshaller.class.getMethod(
                        "toXML", Object.class, Writer.class));

        controller = new ViewStudyController() {
            @Override
            protected StudyDao getDao() {
                return studyDao;
            }

            @Override
            protected Object currentFormObject(HttpServletRequest request, Object sessionFormObject)
                            throws Exception {
                return command;
            }
        };

        controller.setXmlUtility(mockMarshaller);
    }

    /**
     * Test the controller to see if it is in view mode
     */
    public void testViewMode() {

        assertFalse(controller.shouldSave(request, null, null));

        // as expected not a form submission to start with
        // assertFalse(controller.isFormSubmission(request));
        // request.addParameter("_action", "export");
        // should now be a form submission
        // assertTrue(controller.isFormSubmission(request));
    }

    /**
     * Test Study export is working as expected
     * 
     * @throws Exception
     */
    public void testFileExport() throws Exception {
        request.addParameter("_action", "export");

        EasyMock.expect(command.getId()).andReturn((Integer) 2);
        mockMarshaller.toXML(command, response.getWriter());

        replayMocks();

        WebUtils.setSessionAttribute(request, controller.getFormSessionAttributeName(request),
                        command);
        ModelAndView mv = controller.handleRequest(request, response);

        // make sure xml content is being returned
        assertTrue(response.getContentType().indexOf("xml") > -1);
        assertTrue(response.getContentLength() > -1);
        assertNull(mv);
        verifyMocks();
    }
}
