/*******************************************************************************
 * Copyright Duke Comprehensive Cancer Center and SemanticBits
 * 
 *   Distributed under the OSI-approved BSD 3-Clause License.
 *   See https://github.com/NCIP/c3pr/LICENSE.txt for details.
 ******************************************************************************/
package edu.duke.cabig.c3pr.web.security;

import edu.duke.cabig.c3pr.utils.web.navigation.Task;
import gov.nih.nci.cabig.ctms.acegi.csm.authorization.AbstractPrivilegeAndObjectIdGenerator;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Apr 24, 2007 Time: 11:28:36 AM To change this
 * template use File | Settings | File Templates.
 */
public class TaskPrivilegeAndObjectIdGenerator extends AbstractPrivilegeAndObjectIdGenerator {

    protected String getKeyValue(Object object) {
        return ((Task) object).getUrl();
    }

    protected boolean supports(Object object) {
        return object instanceof Task;
    }

}
