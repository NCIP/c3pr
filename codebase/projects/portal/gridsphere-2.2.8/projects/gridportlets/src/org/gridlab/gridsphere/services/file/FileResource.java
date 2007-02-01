/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileResource.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.services.resource.ServiceResource;
import org.gridlab.gridsphere.services.file.tasks.FileBrowser;
import org.gridlab.gridsphere.portlet.User;

/**
 * Describes a resource for accessing file objects. For example,
 * <code>GridFtpResource</code> is a file resource for accessing
 * file objects with the <i>Grid FTP</i> protocol.
 */
public interface FileResource extends ServiceResource {

    public FileBrowser createFileBrowser(User user) throws FileException;
}
