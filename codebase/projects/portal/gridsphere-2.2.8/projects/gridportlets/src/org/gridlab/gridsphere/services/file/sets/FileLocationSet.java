/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.sets;

import org.gridlab.gridsphere.services.file.FileSetType;
import org.gridlab.gridsphere.services.file.FileSet;

import java.util.Vector;
import java.util.List;

/**
 * Base implementation of (all) file handle sets.
 */
public class FileLocationSet extends FileSet {

    private List fileLocations = new Vector(5);

    /**
     * Default constructor.
     */
    public FileLocationSet() {
        super();
    }

    public FileLocationSet(List locations) {
        this.fileLocations = locations;
    }

    public FileSetType getFileSetType() {
        return FileLocationSetType.INSTANCE;
    }

    public List getFileLocations() {
        return fileLocations;
    }

    public void setFileLocations(List locations) {
        fileLocations = locations;
    }
}
