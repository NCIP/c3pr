/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileSetType.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 */
package org.gridlab.gridsphere.services.file;

/**
 * Describes a type of file set.
 */
public class FileSetType  {

    public static final FileSetType INSTANCE = new FileSetType();

    protected FileSetType() {
    }

    public String getLabel() {
        return "FileSet";
    }
}
