/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FilePatternSet.java,v 1.1.1.1 2007-02-01 20:40:24 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.sets;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.services.file.FileSetType;
import org.gridlab.gridsphere.services.file.FileSet;

import java.util.List;
import java.util.Vector;

/**
 * Base implementation of regular expression file set.
 * TODO: Use regular expression library
 */
public class FilePatternSet extends FileSet {

    protected FileLocation fileLocation = null;
    protected List includePatterns = new Vector(0);
    protected List excludePatterns = new Vector(0);

    /**
     * Default constructor.
     */
    public FilePatternSet() {
        super();
    }

    public FilePatternSet(FileLocation location) {
        fileLocation = location;
    }

    public FilePatternSet(FileLocation location, String pattern) {
        fileLocation = location;
        setIncludePattern(pattern);
    }

    public FilePatternSet(FileLocation location, String include, String exclude) {
        fileLocation = location;
        setIncludePattern(include);
        setExcludePattern(exclude);
    }

    public FilePatternSet(FileLocation location, List includes, List excludes) {
        fileLocation = location;
        setIncludePatterns(includes);
        setExcludePatterns(excludes);
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

    public String getIncludePattern() {
        if (includePatterns.size() > 0) {
            return (String)includePatterns.get(0);
        }
        return null;
    }

    public void setIncludePattern(String pattern) {
        if (includePatterns.size() > 0) {
            includePatterns.set(0, pattern);
        } else {
            includePatterns.add(pattern);
        }
    }

    public List getExcludePatterns() {
        return excludePatterns;
    }

    public void setExcludePatterns(List patterns) {
        this.excludePatterns = patterns;
    }

    public String getExcludePattern() {
        if (excludePatterns.size() > 0) {
            return (String)excludePatterns.get(0);
        }
        return null;
    }

    public void setExcludePattern(String pattern) {
        if (excludePatterns.size() > 0) {
            excludePatterns.set(0, pattern);
        } else {
            excludePatterns.add(pattern);
        }
    }

    /**
     * Returns true only if the url of our file location is equal to the given url
     * TODO: Use a regular expression library to actually implement this!
     */
    public boolean accepts(String url) {
        return url.equals(fileLocation.getUrl());
    }
}
