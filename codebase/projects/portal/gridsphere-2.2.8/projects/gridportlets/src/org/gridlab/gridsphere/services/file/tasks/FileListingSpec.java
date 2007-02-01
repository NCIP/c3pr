/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileListingSpec.java,v 1.1.1.1 2007-02-01 20:40:32 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileSet;

/**
 * Describes a file listing task to perform. The file listing task
 * will list the files at the specified file location.
 */
public interface FileListingSpec extends FileBrowserTaskSpec {

    /**
     * Returns whether to return "." and ".." in file list" or not.
     * @return True to return "." and "..", false otherwise.
     */
    public boolean getBrowserFlag();

    /**
     * Sets whether to return "." and ".." in file list" or not.
     * @param flag Set true to return "." and "..", false otherwise.
     */
    public void setBrowserFlag(boolean flag);

    /**
     * Returns the file set to be listed.
     * @return The files set
     */
    public FileSet getFileSet();

    /**
     * Sets the file set to be listed.
     * @param fileSet The filse set
     */
    public void setFileSet(FileSet fileSet);
}
