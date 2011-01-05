/**
 * 
 */
package edu.duke.cabig.c3pr.domain;

import gov.nih.nci.cabig.ctms.domain.MutableDomainObject;

/**
 * A marker interface to be used by mutable domain objects, changes to which
 * should be broadcasted (via notifications) to other applications of the Suite,
 * e.g. caTissue.
 * 
 * @author Denis G. Krylov
 * 
 */
public interface CCTSBroadcastEnabledDomainObject extends MutableDomainObject {

}
