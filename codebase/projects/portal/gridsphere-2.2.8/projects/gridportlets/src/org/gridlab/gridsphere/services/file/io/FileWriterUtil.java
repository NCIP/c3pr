/*
 * @author <a href="mailto:michael.russell@aei.mpg.de">Michael Russell</a>
 * @version $Id: FileWriterUtil.java,v 1.1.1.1 2007-02-01 20:40:16 kherm Exp $
 */
package org.gridlab.gridsphere.services.file.io;

import java.io.*;

import org.gridlab.gridsphere.portlet.PortletLog;
import org.gridlab.gridsphere.portlet.impl.SportletLog;

/**
 * The file writer utility can be used to write the contents of a file
 * object described by a file location.
 */
public class FileWriterUtil {

    private transient static PortletLog log = SportletLog.getInstance(FileWriterUtil.class);

    private final static int BUFFER_SIZE = 8192; //8 kB

    /**
     * Writes the given input stream to the given file.
     * @param input The input stream
     * @param file The file
     * @throws java.io.IOException If an IO error occcurs.
     */
    public static void toFile(InputStream input, File file)
        throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file);
        write(input, outputStream);
        outputStream.close();
    }

    /**
     * Writes the given string to the given file.
     * @param text The string to write
     * @param file The file
     * @throws java.io.IOException If an IO error occcurs.
     */
    public static void toFile(String text, File file)
        throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(text);
        fileWriter.close();
    }

    /**
     * Writes the contents of the input stream text to the given output stream.
     * @param input The input stream
     * @param output The output stream
     * @throws java.io.IOException If an IO error occcurs.
     */
    public static void write(InputStream input, OutputStream output) throws IOException {
        int numRead;
        byte[] buf = new byte[BUFFER_SIZE];
        while (!((numRead = input.read(buf)) < 0)) {
            output.write(buf, 0, numRead);
        }
    }

    /**
     * Writes the given string to the given output stream.
     * @param text The string to write
     * @param output The output stream
     * @throws java.io.IOException If an IO error occcurs.
     */
    public static void write(String text, OutputStream output) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(output);
        writer.write(text);
        writer.flush();
    }
}
