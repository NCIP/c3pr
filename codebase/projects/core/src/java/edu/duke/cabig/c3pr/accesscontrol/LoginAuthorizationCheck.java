package edu.duke.cabig.c3pr.accesscontrol;

import gov.nih.nci.security.acegi.csm.web.CSMAccessControlTag;
import gov.nih.nci.security.acegi.csm.authorization.CSMAuthorizationCheck;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;

/**
 * User: ion
 * Date: Jul 3, 2008
 * Time: 3:14:12 PM
 */

public class LoginAuthorizationCheck implements CSMAuthorizationCheck {

    public boolean checkAuthorization(Authentication authentication, String s, Object o) {
       return (authentication != null);
    }

    public boolean checkAuthorizationForObjectId(Authentication authentication, String s, String s1) {
        return false;
    }

    public boolean checkAuthorizationForObjectIds(Authentication authentication, String s, String[] strings) {
        return false;  
    }
}

