/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileNameChangeSpec.java,v 1.1.1.1 2007-02-01 20:40:34 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.tasks;

import org.gridlab.gridsphere.services.file.FileLocation;

/**
 * Describes a file name change task to perform. The file name
 * change task will the change the file name of a file object
 * at a specified file location to the specified file name.
 * This is sometimes referred to as a file "rename" task spec.
 */
public interface FileNameChangeSpec extends FileBrowserTaskSpec {

    /**
     * Returns the location of the file object to rename.
     * @return The location of the file to rename
     */
    public FileLocation getFileLocation();

    /**
     * Sets the file to remame
     * @param location The location of the file to rename
     */
    public void setFileLocation(FileLocation location);

    /**
     * Returns the new file name.
     * @return The new file name
     */
    public String getNewFileName();

    /**
     * The new file name.
     * @param name  The new file name
     */
    public void setNewFileName(String name);
}
