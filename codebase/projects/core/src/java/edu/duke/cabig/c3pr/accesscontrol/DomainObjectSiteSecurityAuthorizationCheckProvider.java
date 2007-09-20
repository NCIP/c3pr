package edu.duke.cabig.c3pr.accesscontrol;

import gov.nih.nci.cabig.ctms.domain.AbstractMutableDomainObject;
import org.acegisecurity.Authentication;

/**
 * Created by IntelliJ IDEA.
 * User: kherm
 * Date: Sep 20, 2007
 * Time: 12:57:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DomainObjectSiteSecurityAuthorizationCheckProvider {
    boolean checkAuthorization(Authentication authentication, String permission, AbstractMutableDomainObject domainObject);
}
