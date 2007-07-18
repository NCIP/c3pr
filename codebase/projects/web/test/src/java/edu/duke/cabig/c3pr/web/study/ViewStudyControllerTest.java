package edu.duke.cabig.c3pr.web.study;

import edu.duke.cabig.c3pr.dao.StudyDao;
import edu.duke.cabig.c3pr.domain.Study;
import edu.duke.cabig.c3pr.utils.ContextTools;
import edu.duke.cabig.c3pr.web.ControllerTestCase;
import edu.duke.cabig.c3pr.xml.XmlMarshaller;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;
import org.easymock.classextension.EasyMock;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Jul 18, 2007
 * Time: 1:48:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewStudyControllerTest extends ControllerTestCase {

    private ViewStudyController controller;

    private XmlMarshaller mockMarshaller;
    private Study mockStudy;
    private StudyDao mockStudyDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();    //To change body of overridden methods use File | Settings | File Templates.

        mockStudyDao = registerDaoMockFor(StudyDao.class);
        mockStudy = registerMockFor(Study.class);
        mockMarshaller = registerMockFor(XmlMarshaller.class, XmlMarshaller.class.getMethod("toXML", Object.class, Writer.class));

        controller = new ViewStudyController(){
            @Override
            protected StudyDao getDao() {
                return mockStudyDao;
            }
        };

        controller.setXmlUtility(mockMarshaller);
    }

    /**
     * Test the controller to see if it
     * is in view mode
      */
    public void testViewMode(){

        assertFalse(controller.shouldSave(request,null,null));

        //as expected not a form submission to start with
        assertFalse(controller.isFormSubmission(request));
        request.addParameter("_action","export");
        //should now be a form submission
        assertTrue(controller.isFormSubmission(request));
    }

    /**
     * Test Study export is working as expected
      * @throws Exception
     */
    public void testFileExport() throws Exception{
        request.addParameter("_action","export");

        EasyMock.expect(mockStudy.getId()).andReturn((Integer)2);
        mockMarshaller.toXML(mockStudy,response.getWriter());
     
        replayMocks();

        WebUtils.setSessionAttribute(request,controller.getFormSessionAttributeName(request),mockStudy);
        ModelAndView mv  = controller.handleRequest(request,response);

        //make sure xml content is being returned
        assertTrue(response.getContentType().indexOf("xml")>-1);
        assertTrue(response.getContentLength()>-1);
        assertNull(mv);
        verifyMocks();         
        

    }
}
