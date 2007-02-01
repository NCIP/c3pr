/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileUtil.java,v 1.1.1.1 2007-02-01 20:40:03 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.services.util.ServiceUtil;
import org.gridlab.gridsphere.services.file.tasks.FileBrowserService;
import org.gridlab.gridsphere.portlet.User;

/**
 * This utility class simplifies the use of the file package by
 * providing convenient methods for methods commonly used by
 * other portlet services.
 */
public class FileUtil extends ServiceUtil {

    public static FileBrowserService getFileBrowserService(User user) {
        return (FileBrowserService)getPortletService(user, FileBrowserService.class);
    }
}
