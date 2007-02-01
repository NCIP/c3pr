/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: PersistedFileSet.java,v 1.1.1.1 2007-02-01 20:40:13 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.impl;

import org.gridlab.gridsphere.services.file.FileSetType;
import org.gridlab.gridsphere.services.file.FileSet;
import org.gridlab.gridsphere.services.file.sets.FilePatternSet;
import org.gridlab.gridsphere.services.file.sets.impl.PersistedFileLocationSet;
import org.gridlab.gridsphere.services.file.sets.impl.PersistedFilePatternSet;
import org.gridlab.gridsphere.portlet.impl.SportletLog;
import org.gridlab.gridsphere.portlet.PortletLog;

/**
 * Provides a base implementation for (all) file sets
 */
public abstract class PersistedFileSet {

    private PortletLog log = SportletLog.getInstance(PersistedFileSet.class);

    protected String oid = null;

    /**
     * Default constructor
     */
    public PersistedFileSet() {
        
    }

    public FileSetType getFileSetType() {
        return FileSetType.INSTANCE;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    /**
     * Base implementation returns true for all urls.
     * @param url The url
     * @return true
     */
    public boolean accepts(String url) {
        return true;
    }

    public abstract void fromFileSet(FileSet fileSet);

    public abstract FileSet toFileSet();

    public abstract void updateFileSet(FileSet fileSet);

    public static PersistedFileSet createPersistedFileSet(FileSet fileSet) {
        if (fileSet == null) return null;
        PersistedFileSet persistedFileSet = null;
        if (fileSet instanceof FilePatternSet) {
            persistedFileSet = new PersistedFilePatternSet();
        } else {
            persistedFileSet = new PersistedFileLocationSet();
        }
        persistedFileSet.fromFileSet(fileSet);
        return persistedFileSet;
    }
}
