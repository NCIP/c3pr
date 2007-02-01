/*
 * @author <a href="mailto:russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileStageType.java,v 1.1.1.1 2007-02-01 20:40:40 kherm Exp ${NAME}.java, Java 1.3, Oct 6, 2003 8:55:59 PM, russell, Exp $
 * <p>
 */
package org.gridlab.gridsphere.services.job;

public class FileStageType {

    public static final FileStageType IN = new FileStageType(0, "in");
    public static final FileStageType OUT = new FileStageType(1, "out");

    private int value;
    private String name;

    private FileStageType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(FileStageType type) {
        return (value == type.value);
    }

    public static FileStageType toFileStageType(String name) {
        if (name.equals(IN.name)) {
            return IN;
        } else if (name.equals(OUT.name)) {
            return OUT;
        }
        return IN;
    }
}
