/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FilePatternSetType.java,v 1.1.1.1 2007-02-01 20:40:24 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.sets;

import org.gridlab.gridsphere.services.file.FileSetType;

/**
 * Describes a type of regular expression file set.
 */
public class FilePatternSetType extends FileSetType {

    public static final FilePatternSetType INSTANCE = new FilePatternSetType();

    public String getLabel() {
        return "FilePatternSet";
    }
}
