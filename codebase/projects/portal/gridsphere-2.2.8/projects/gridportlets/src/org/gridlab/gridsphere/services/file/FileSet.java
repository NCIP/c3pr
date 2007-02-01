/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileSet.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

/**
 * Provides a base implementation for (all) file sets
 */
public abstract class FileSet {

    /**
     * Default constructor
     */
    public FileSet() {
        
    }
    
    public FileSetType getFileSetType() {
        return FileSetType.INSTANCE;
    }

    /**
     * Base implementation returns true for all urls.
     * @param url The url
     * @return true
     */
    public boolean accepts(String url) {
        return true;
    }
}
