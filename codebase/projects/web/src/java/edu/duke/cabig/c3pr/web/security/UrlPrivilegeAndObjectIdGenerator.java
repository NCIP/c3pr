/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
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
