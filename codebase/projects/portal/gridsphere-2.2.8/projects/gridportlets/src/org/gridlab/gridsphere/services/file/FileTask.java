/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileTask.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

import org.gridlab.gridsphere.services.task.Task;

/**
 * Describes a file related task. Typical file tasks include
 * copying or moving files from one location to another,
 * listing the files in a particular directory, or deleting
 * files.
 */
public interface FileTask extends Task {

    /**
     * Returns the file task id. This
     * is generate by a file operation service
     * and should not be confused with the object id.
     * @return The file task id
     */
    public String getFileTaskId();
}
