/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: PersistedFilePatternSet.java,v 1.1.1.1 2007-02-01 20:40:27 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.sets.impl;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileSetType;
import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.impl.PersistedFileSet;
import org.gridlab.gridsphere.services.file.sets.FilePatternSetType;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

import java.util.List;
import java.util.Vector;

/**
 * Base implementation of regular expression file set.
 */
public class PersistedFilePatternSet extends PersistedFileSet {

    private PortletLog log = SportletLog.getInstance(PersistedFilePatternSet.class);

    protected FileLocation fileLocation = null;
    protected List includePatterns = new Vector(0);
    protected List excludePatterns = new Vector(0);

    /**
     * Default constructor.
     */
    public PersistedFilePatternSet() {
        super();
    }

    public void fromFileSet(FileSet fileSet) {
        FilePatternSet filePatternSet = (FilePatternSet)fileSet;
        List fileSetIncludes = filePatternSet.getIncludePatterns();
        List fileSetExcludes = filePatternSet.getExcludePatterns();

        fileLocation = filePatternSet.getFileLocation();
        includePatterns.clear();
        includePatterns.addAll(fileSetIncludes);
        excludePatterns.clear();
        excludePatterns.addAll(fileSetExcludes);

        //log.debug("From file pattern set with " + fileLocation.getUrl());
        //log.debug("From file pattern set with " + fileSetIncludes.size() + " include patterns");
        //log.debug("From file pattern set with " + fileSetExcludes.size() + " exclude patterns");
    }

    public FileSet toFileSet() {

        //log.debug("To file pattern set with " + fileLocation.getUrl());
        //log.debug("To file pattern set with " + includePatterns.size() + " include patterns");
        //log.debug("To file pattern set with " + excludePatterns.size() + " exclude patterns");

        FilePatternSet filePatternSet = new FilePatternSet();
        updateFileSet(filePatternSet);
        return filePatternSet;
    }

    public void updateFileSet(FileSet fileSet) {
        FilePatternSet filePatternSet = (FilePatternSet)fileSet;
        filePatternSet.setFileLocation(fileLocation);
        List fileSetIncludes = filePatternSet.getIncludePatterns();
        fileSetIncludes.clear();
        fileSetIncludes.addAll(includePatterns);
        List fileSetExcludes = filePatternSet.getExcludePatterns();
        fileSetExcludes.clear();
        fileSetExcludes.addAll(excludePatterns);

        //log.debug("Update file pattern set with " + fileLocation.getUrl());
        //log.debug("Update file pattern set with " + fileSetIncludes.size() + " include patterns");
        //log.debug("Update file pattern set with " + fileSetExcludes.size() + " exclude patterns");
    }

    public FileSetType getFileSetType() {
        return FilePatternSetType.INSTANCE;
    }

    public FileLocation getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(FileLocation location) {
        fileLocation = (FileLocation) location;
    }

    public List getIncludePatterns() {
        return includePatterns;
    }

    public void setIncludePatterns(List patterns) {
        this.includePatterns = patterns;
    }

    public List getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(List patterns) {
        this.excludePatterns = patterns;
    }
}
