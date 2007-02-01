/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileDeletionSpec.java,v 1.1.1.1 2007-02-01 20:40:31 kherm Exp $
 * <p>
 * Specifies a deletion operation
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.FileResource;

/**
 * Specifies a a task for deleting a set of files.
 */
public interface FileDeletionSpec extends FileBrowserTaskSpec {

    /**
     * Returns the file set to delete.
     * @return The file set to delete
     */
    public FileSet getFileSet();

    /**
     * Sets the file set to delete
     * @param fileSet The file set
     */
    public void setFileSet(FileSet fileSet);
}
