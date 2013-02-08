/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.security;

import org.acegisecurity.intercept.web.FilterInvocation;

/**
 * @author <a href="mailto:joshua.phillips@semanticbits.com">Joshua Phillips</a>
 * 
 */
public class FilterInvocationPrivilegeAndObjectIdGenerator extends
                RegexPrivilegeAndObjectIdGenerator {

    protected boolean supports(Object object) {
        return object instanceof FilterInvocation;
    }

    protected String getKeyValue(Object object) {
        return ((FilterInvocation) object).getRequestUrl();
    }

}
