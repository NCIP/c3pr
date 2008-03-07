package edu.duke.cabig.c3pr.web.beans;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by IntelliJ IDEA. User: kherm Date: Jul 13, 2007 Time: 10:48:34 AM To change this
 * template use File | Settings | File Templates.
 */
public class FileBean {

    private byte[] file;

    public FileBean() {
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public byte[] getFile() {
        return file;
    }

    public Reader getReader() {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(file)));
    }

    public InputStream getInputStream() {
        return new ByteArrayInputStream(file);
    }

}
