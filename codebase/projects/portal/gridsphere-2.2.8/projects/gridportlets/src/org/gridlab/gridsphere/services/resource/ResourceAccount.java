package org.gridlab.gridsphere.services.resource;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ResourceAccount.java,v 1.1.1.1 2007-02-01 20:40:51 kherm Exp $
 */

public interface ResourceAccount extends Resource {

    public final static String USER_ID_ATTRIBUTE = "UserId";

    public String getUserId();

    public void setUserId(String userId);
}
