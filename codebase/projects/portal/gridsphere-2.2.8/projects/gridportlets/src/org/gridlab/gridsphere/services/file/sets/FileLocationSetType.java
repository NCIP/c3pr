/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileLocationSetType.java,v 1.1.1.1 2007-02-01 20:40:23 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.sets;

import org.gridlab.gridsphere.services.file.FileSetType;

/**
 * Describes a type of file handle set.
 */
public class FileLocationSetType extends FileSetType {

    public static final FileLocationSetType INSTANCE = new FileLocationSetType();

    public String getLabel() {
        return "FileLocationSet";
    }
}
