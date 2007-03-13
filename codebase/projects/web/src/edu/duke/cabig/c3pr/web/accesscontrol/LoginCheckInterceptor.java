package edu.duke.cabig.c3pr.web.accesscontrol;

//import gov.nih.nci.cabig.caaers.web.ControllerTools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.semanticbits.security.grid.GridLoginContext;

/**
 * @author Padmaja Vedula
 * @author Rhett Sutphin
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    private static Log log = LogFactory.getLog(LoginCheckInterceptor.class);
    private String enableLogin = "true";

    public static final String REQUESTED_URL_ATTRIBUTE = LoginCheckInterceptor.class.getName() + ".REQUESTED_URL";

    /**
     * Retrieves the URL requested by the user when he or she was not logged in and then
     * immediately clears it from the session.
     *
     * @param session
     * @return the requested URL
     */
    public static String getRequestedUrlOnce(HttpSession session) {
        String requestedUrl = (String) session.getAttribute(REQUESTED_URL_ATTRIBUTE);
        session.setAttribute(REQUESTED_URL_ATTRIBUTE, null);
        return requestedUrl;
    }

    public boolean preHandle(
        HttpServletRequest request, HttpServletResponse response, Object handler) 
    	throws Exception {
    	if (enableLogin.equals("true")) {
	    	// GridLoginContext gridLoginContext = (GridLoginContext)request.getSession().getAttribute("login-context");
	    	String proxy = (String)request.getSession().getAttribute("gridProxy");
	        if (proxy == null) {
	        	response.sendRedirect("/c3pr/invalidLogin.jsp");
	        	return false;
	        }
	        return true;
	    }    
    	return true;
    }

    private String getFullPath(HttpServletRequest request) {
        StringBuffer fullPath = request.getRequestURL();
        if (request.getQueryString() != null) fullPath.append('?').append(request.getQueryString());
        return fullPath.toString();
    }

	public String getEnableLogin() {
		return enableLogin;
	}

	public void setEnableLogin(String enableLogin) {
		this.enableLogin = enableLogin;
	}
       
}