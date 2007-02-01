/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileType.java,v 1.1.1.1 2007-02-01 20:40:02 kherm Exp $
 * <p>
 * Describes a method for running an excecutable. Typical
 * choices include a single processs or as an mpi job.
 * A number of constants are provided here, while job
 * implemenations can provide their own custom methods
 * as constants. One can iterate through the constants with
 * iterateConstants().
 * <p>
 */
package org.gridlab.gridsphere.services.file;

import java.util.HashMap;
import java.util.Iterator;

public class FileType {

    private static HashMap fileTypeMap = new HashMap(4);

    public static final FileType FILE = new FileType(0, "file");
    public static final FileType DIRECTORY = new FileType(1, "directory");
    public static final FileType SOFT_LINK = new FileType(2, "softlink");
    public static final FileType DEVICE = new FileType(3, "device");

    private static final FileType fileTypeArray[] = { FILE, DIRECTORY, SOFT_LINK, DEVICE };

    private int value = -1;
    private String name;

    private FileType(int value, String name) {
        this.value = value;
        this.name = name;
        fileTypeMap.put(name, this);
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof FileType) {
            FileType type = (FileType)o;
            return (value == type.value);
        }
        if (o instanceof String) {
            String type = (String)o;
            return name.equalsIgnoreCase(type);
        }
        return false;
    }

    public int hashCode() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static Iterator iterateConstants() {
        return fileTypeMap.values().iterator();
    }

    public static FileType toFileType(int value) {
        return fileTypeArray[value];
    }

    public static FileType toFileType(String name) {
        return (FileType)fileTypeMap.get(name);
    }
}
