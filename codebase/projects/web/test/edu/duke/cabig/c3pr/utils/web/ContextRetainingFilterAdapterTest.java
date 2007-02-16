package edu.duke.cabig.c3pr.utils.web;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;

import edu.duke.cabig.c3pr.web.WebTestCase;

/**
 * This unit test validates teh ContextRetainingFilterAdapter.
 * @testType unit
 * @author Rhett Sutphin
 */
/* TODO: much of this class is shared with PSC.  Refactor into a shared library. */
public class ContextRetainingFilterAdapterTest extends WebTestCase {
    private ContextRetainingFilterAdapter adapter = new ContextRetainingFilterAdapter() { };

    public void testServletContextRetained() throws Exception {
        expectRetainServletContext();

        assertSame("Wrong servlet context retained", servletContext, adapter.getServletContext());
    }

    public void testGetApplicationContext() throws Exception {
        expectRetainServletContext();
        WebApplicationContext applicationContext = createMock(WebApplicationContext.class);
        replay(applicationContext);
        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext);

        assertSame("Application context not correct", applicationContext, adapter.getApplicationContext());
        verify(applicationContext);
    }

    public void testGetApplicationContextFailsWhenMissing() throws Exception {
        expectRetainServletContext();
        try {
            adapter.getApplicationContext();
            fail("Exception not thrown");
        } catch (IllegalStateException iae) {
            // good
        }
    }

    private void expectRetainServletContext() throws ServletException {
        FilterConfig config = createMock(FilterConfig.class);
        expect(config.getServletContext()).andReturn(servletContext);
        replay(config);

        adapter.init(config);
        verify(config);
    }
}
