package edu.duke.cabig.c3pr.web;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;

import edu.duke.cabig.c3pr.utils.ApplicationTestCase;

/**
 * @author Priyatam
 */
public abstract class WebTestCase extends ApplicationTestCase {
    protected MockHttpServletRequest request;
    protected MockHttpServletResponse response;
    protected MockServletContext servletContext;
    protected MockHttpSession session;

    protected void setUp() throws Exception {
        super.setUp();
        servletContext = new MockServletContext();
        session = new MockHttpSession(servletContext);
        request = new MockHttpServletRequest(servletContext);
        request.setMethod("POST");
        request.setSession(session);
        response = new MockHttpServletResponse();
    }
}
