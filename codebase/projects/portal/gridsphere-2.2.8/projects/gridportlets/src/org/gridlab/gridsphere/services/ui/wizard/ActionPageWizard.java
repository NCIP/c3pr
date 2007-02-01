/**
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: ActionPageWizard.java,v 1.1.1.1 2007-02-01 20:42:23 kherm Exp $
 */
package org.gridlab.gridsphere.services.ui.wizard;

public interface ActionPageWizard {

    public static final String PAGE_BEAN_ID = "pageBean";
    public static final String PAGE_MENU_ID = "pageMenu";

    public static final int NUMBER_OF_PAGES_UNKNOWN = -1;
    public static final int PAGE_NUMBER_UNKNOWN = -1;

    public ActionPage getCurrentPage();

    public boolean hasNextPage();

    public boolean hasPreviousPage();

    public int getNumberOfPages();

    public int getPageNumber();
}
