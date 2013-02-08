/*******************************************************************************
* Copyright Duke Comprehensive Cancer Center and SemanticBits
* 
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/c3pr/LICENSE.txt for details.
*******************************************************************************/
package edu.duke.cabig.c3pr.web.admin;

import edu.duke.cabig.c3pr.utils.web.spring.tabbedflow.InPlaceEditableTab;

/**
 * @author Ramakrishna
 */
public class NotificationTab<C> extends InPlaceEditableTab<C> {

    public NotificationTab() {
        super("Notifications", "Notifications", "admin/notification_details");
    }

    public NotificationTab(String longTitle, String shortTitle, String viewName) {
        super(longTitle, shortTitle, viewName);
    }

    public NotificationTab(String longTitle, String shortTitle) {
        super(longTitle, shortTitle, "");
    }


}
