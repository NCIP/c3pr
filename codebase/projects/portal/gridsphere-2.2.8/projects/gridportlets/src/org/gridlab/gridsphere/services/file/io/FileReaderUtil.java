/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileReaderUtil.java,v 1.1.1.1 2007-02-01 20:40:14 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.gridlab.gridsphere.services.file.FileLocation;
import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/**
 * The file reader utility can be used to read the contents of a file
 * object described by a file location.
 */
public class FileReaderUtil {

    private transient static PortletLog log = SportletLog.getInstance(FileReaderUtil.class);

    /**
     * @deprecated Use the readContents method on the FileHandle class instead.
     */
    public static String toString(FileLocation location)
        throws IOException {
        InputStream inStream = getInputStream(location);
        return toString(inStream);
    }

    /**
     * @deprecated Use the createInputStream method on the FileHandle class instead.
     */
    public static InputStream getInputStream(FileLocation location)
            throws IOException {
        if (location.isLocalHost()) {
            File file = new File(location.getFilePath());
            FileInputStream inputStream = new FileInputStream(file);
            return inputStream;
        }
        throw new IOException("File location is not readable");
    }

    /**
     * Returns the contents of the local file at the given file path as a string.
     * @param path The file path
     * @return The file contents as a string.
     * @throws IOException If an IO error occurs.
     */
    public static String toString(String path)
        throws IOException {
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        return toString(inputStream);
    }

    /**
     * Returns the contents of the given input stream to a string.
     * @param inputStream The input stream
     * @return The contents as a string
     * @throws IOException If an IO error occurs.
     */
    public static String toString(InputStream inputStream)
        throws IOException {
        InputStreamReader insReader = new InputStreamReader(inputStream);
        BufferedReader bufReader = new BufferedReader(insReader);
        String line = bufReader.readLine();
        StringBuffer contents = new StringBuffer();
        while (line != null) {
            contents.append(line);
            contents.append('\n');
            line = bufReader.readLine();
        }
        return contents.toString();
    }

}
