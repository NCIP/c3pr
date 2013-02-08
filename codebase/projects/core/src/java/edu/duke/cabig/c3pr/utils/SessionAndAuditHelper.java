/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.utils;

import java.util.Date;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.orm.hibernate3.support.OpenSessionInViewInterceptor;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

public class SessionAndAuditHelper {

    public static WebRequest setupHibernateSessionAndAudit(
                    OpenSessionInViewInterceptor interceptor, String by, String ip, Date date,
                    String url) {
        setupAudit(by, ip, date, url);
        return setupHibernateSession(interceptor);
    }

    public static void setupAudit(String by, String ip, Date date, String url) {
        gov.nih.nci.cabig.ctms.audit.DataAuditInfo
                        .setLocal(new gov.nih.nci.cabig.ctms.audit.domain.DataAuditInfo(by, ip,
                                        date, url));
    }
    
    public static WebRequest setupHibernateSession(OpenSessionInViewInterceptor interceptor){
        WebRequest webRequest = new ServletWebRequest(new MockHttpServletRequest());
        interceptor.preHandle(webRequest);
        return webRequest;
    }
    
    public static void tearDownHibernateSession(OpenSessionInViewInterceptor interceptor,WebRequest webRequest){
        try {
            interceptor.postHandle(webRequest, null);
        }
        finally {
            interceptor.afterCompletion(webRequest, null);
        }
    }
}
