/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.tasks.impl;

import org.gridlab.gridsphere.services.file.tasks.FileListingSpec;
import org.gridlab.gridsphere.services.file.tasks.FileListing;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Vector;

/**
 * Provides a base implementation for file listing tasks.
 */
public class BaseFileListing
        extends BaseFileBrowserTask
        implements FileListing {

    private static PortletLog log = SportletLog.getInstance(BaseFileListing.class);

    private List fileLocations = new Vector(20);

    /**
     * Default constructor
     */
    public BaseFileListing() {
        super();
    }

    /**
     * Constructs a base file listing task containing the
     * given file listing spec.
     * @param spec The file listing spec.
     */
    public BaseFileListing(FileListingSpec spec) {
        super(spec);
    }

    public List getFileLocations() {
        return fileLocations;
    }

    public void setFileLocations(List fileList) {
        this.fileLocations = fileList;
    }
}
