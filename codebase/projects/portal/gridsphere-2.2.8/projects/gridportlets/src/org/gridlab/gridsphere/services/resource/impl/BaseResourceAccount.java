package org.gridlab.gridsphere.services.resource.impl;

import org.gridlab.gridsphere.services.resource.*;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: BaseResourceAccount.java,v 1.1.1.1 2007-02-01 20:41:00 kherm Exp $
 */

public abstract class BaseResourceAccount extends BaseResource implements ResourceAccount {

    private static PortletLog log = SportletLog.getInstance(BaseResourceAccount.class);

    protected String userId = null;

    public BaseResourceAccount() {
        super();
        setResourceType(ResourceAccountType.INSTANCE);
    }

    public BaseResourceAccount(Resource resource, String userId) {
        super(resource);
        this.userId = userId;
        setResourceType(ResourceAccountType.INSTANCE);
    }

    public String getLabel() {
        return getUserId();
    }

    public void setLabel(String label) {
        setUserId(label);
    }

    public String getUserId() {
        if (userId == null) {
            userId = getResourceAttributeValue(USER_ID_ATTRIBUTE);
        }
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        if (userId == null) {
            removeResourceAttribute(USER_ID_ATTRIBUTE);
        } else {
            putResourceAttribute(USER_ID_ATTRIBUTE, userId);
        }
    }
}
