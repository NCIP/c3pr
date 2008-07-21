package edu.duke.cabig.c3pr.web.security;

import org.acegisecurity.intercept.web.FilterInvocation;

/**
 *
 */

public class UrlPrivilegeAndObjectIdGenerator extends RegexPrivilegeAndObjectIdGenerator {

    protected boolean supports(Object object) {
        return object instanceof String;
    }

    protected String getKeyValue(Object object) {
        return (String)object;
    }

}