/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id
 */
package org.gridlab.gridsphere.services.file.sets.impl;

import org.gridlab.gridsphere.services.file.FileSetType;
import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.impl.PersistedFileSet;
import org.gridlab.gridsphere.services.file.sets.FileLocationSetType;
import org.gridlab.gridsphere.services.file.sets.FileLocationSet;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.Vector;
import java.util.List;


/**
 * Base implementation of (all) file handle sets.
 */
public class PersistedFileLocationSet extends PersistedFileSet {

    private PortletLog log = SportletLog.getInstance(PersistedFileLocationSet.class);

    protected List fileLocations = new Vector(1);

    /**
     * Default constructor.
     */
    public PersistedFileLocationSet() {
        super();
    }

    public void fromFileSet(FileSet fileSet) {
        FileLocationSet fileLocationSet = (FileLocationSet)fileSet;
        List fileSetLocations = fileLocationSet.getFileLocations();

        //log.debug("From location file set with " + fileSetLocations.size() + " file locations");

        fileLocations.clear();
        fileLocations.addAll(fileSetLocations);
    }

    public FileSet toFileSet() {
        //log.debug("To location file set with " + fileLocations.size() + " file locations");

        FileLocationSet fileLocationSet = new FileLocationSet();
        updateFileSet(fileLocationSet);
        return fileLocationSet;
    }

    public void updateFileSet(FileSet fileSet) {
        FileLocationSet fileLocationSet = (FileLocationSet)fileSet;
        List fileSetLocations = fileLocationSet.getFileLocations();
        fileSetLocations.clear();
        fileSetLocations.addAll(fileLocations);

        //log.debug("Update file location set with " + fileSetLocations.size() + " file locations");
    }


    public FileSetType getFileSetType() {
        return FileLocationSetType.INSTANCE;
    }

    public List getFileLocations() {
        return fileLocations;
    }

    public void setFileLocations(List locations) {
        this.fileLocations = locations;
    }
}
